<%@page session="false"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" pageEncoding="UTF-8"%>
<%@page import="model.session.mo.LoggedUser"%>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    boolean admin = (Boolean) request.getAttribute("admin");
    LoggedUser loggedUser = (LoggedUser) request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Gestione";
    boolean registration = false;
%>

<!DOCTYPE html>
<html>
<head>
    <%@include file="/include/htmlHead.inc"%>
    <script src="jsLib/jquery.js" type="text/javascript"></script>
    <link href="css/registration.css" type="text/css" rel="stylesheet" />
    <script>

        $(document).on('focusout', '.edit-category', function() {
            console.log("sono su edit category");
            let id = this.id;
            let split_id = id.split("_");
            let field_name = split_id[0].split("edit-category-")[1];
            let category_id = split_id[1];
            let field_value = $(this).text();

            $.ajax({
                url: 'Dispatcher?controllerAction=AdminManagement.updateCategory',
                type: 'POST',
                data: { field:field_name, value:field_value, id:category_id },
                success:function(response){
                    console.log('Save successfully');
                }
            });
        })

        $(document).ready(function() {
            let selectorResult = $("#risultato");
            selectorResult.hide();

            $("#button-category").click(function(){
                let name = $("#category-name").val();
                let description = $("#category-description").val();
                $.ajax({
                    type: "POST",
                    url: "Dispatcher?controllerAction=AdminManagement.insertCategory",
                    data: "name=" + name + "&description=" + description,
                    dataType: "html",
                    success: function(response)
                    {
                        let result = JSON.parse(response);
                        selectorResult.html(result.message);
                        if (parseInt(result.clear)) {
                            $('#insert-category').trigger("reset");
                        }
                        selectorResult.show().delay(3000).queue(function(n) {
                            $(this).hide(); n();
                        });
                        $('.input-search-category').keyup();
                    },
                    error: function()
                    {
                        alert("Chiamata fallita, si prega di riprovare...");
                    }
                });
            });

            $(function() {
                $('.input-search-category').keyup();
            });

            $(".input-search-category").keyup(function(){
                let field = $('#search-category').find(":selected").val();
                let value = $(this).val();
                $.ajax({
                    url: 'Dispatcher?helperAction=Data.searchCategory',
                    type: 'POST',
                    data: { field:field, value:value },
                    success:function(response){
                        response = JSON.parse(response);
                        let message = JSON.parse(response.message);
                        let content = '<table id="category-table">';
                        content += ' <thead>\n';
                        content += '<th scope="col">Id categoria</th>';
                        content += '<th scope="col">Elimina</th>';
                        content += '<th scope="col">Nome</th>';
                        content += '<th scope="col">Descrizione</th>';
                        content += '</thead>';
                        content += '<tbody>';
                        for (let i = 0; i < message.length; i++) {
                            content += '<tr><td>' + message[i].id  + '</td>'
                            content += '<td>\n' +
                                '                            <a href="javascript:deleteCategory(' + message[i].id + ')">\n' +
                                '                                <img id="trashcan" src="images/trashcan.png" width="22" height="22"/>\n' +
                                '                            </a>\n' +
                                '                        </td>'
                            content += '<td contenteditable=\'true\' class=\'edit-category\' id="edit-category-name_'+ message[i].id +'">' + message[i].name + '</td>'
                            content += '<td contenteditable=\'true\' class=\'edit-category\' id="edit-category-description_'+ message[i].id +'">' + message[i].description + '</td>'
                        }
                        content += "</tbody>"
                        content += "</table>"
                        if (message.length > 0) $('.search-result-category').html(content);
                    }
                });
            });

        });

        function setButton() {
            const headings = document.querySelectorAll('h2');
            Array.prototype.forEach.call(headings, h => {

                let btn = h.querySelector('button');
                let target = h.nextElementSibling;

                btn.onclick = () => {
                    let expanded = btn.getAttribute('aria-expanded') === 'true';

                    btn.setAttribute('aria-expanded', !expanded);
                    target.hidden = expanded;
                }
            });
        }

        function deleteCategory(code) {
            $.ajax({
                url: 'Dispatcher?controllerAction=AdminManagement.deleteCategory',
                type: 'POST',
                data: { 'categoryId': code },
                success:function(response){
                    response = JSON.parse(response);
                    if (parseInt(response.message) === 1) {
                        $('.input-search-category').keyup();
                    }
                }
            });
        }

        function setLabel(id) {
            document.getElementById(id).classList.add('active');
        }

    </script>
    <style>

        textarea {
            resize:vertical;
            max-width: 100%;
        }

        h2 button {
            all: inherit;
            border: 0;
            display: flex;
            justify-content: space-between;
            width: 100%;
            padding: 0.5em 0;
        }

        h2 button:focus svg {
            outline: 2px solid;
        }

        button svg {
            height: 1em;
            margin-left: 0.5em;
        }

        [aria-expanded="true"] .vert {
            display: none;
        }

        [aria-expanded] rect {
            fill: currentColor;
        }

        .sectionCatalog {
            border-bottom: 2px solid;
        }
        .first {
            border-top: 2px solid;
        }

        .active {
            border-top:solid 1px #210800;
            background: linear-gradient(#621900, #822100);
        }

        table {
            display: block;
            overflow-x: auto;
            white-space: nowrap;
            width: 800px;
        }

        th, td {
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even){
            background-color: #f2f2f2
        }
    </style>
</head>
<body onload="setButton();setLabel('adminCatalog')">
<%@include file="/include/header.inc"%>
<div class="admin">
    <div class="sidenav">
        <%@include file="/include/sidemenuAdmin.inc"%>
    </div>
    <div class="main">

        <h2 class="sectionCatalog first">
            <button aria-expanded="false">
                Inserimento Categorie
                <svg viewBox="0 0 10 10" aria-hidden="true" focusable="false">
                    <rect class="vert" height="8" width="2" y="1" x="4"></rect>
                    <rect height="2" width="8" y="4" x="1"></rect>
                </svg>
            </button>
        </h2>
        <div class="sectionCatalog" hidden>
            <form name="insert-category" id="insert-category">
                <div class="field clearfix">
                    <label for="category-name">Nome</label>
                    <input type="text" id="category-name" name="category-name" value="" placeholder="Nome categoria" required size="20" maxlength="50"/>
                </div>
                <div class="field clearfix">
                    <label for="category-description">Descrizione</label>
                    <textarea rows="5" cols="100" id="category-description" name="description"></textarea>
                </div>
                <input type="button" id="button-category" value="Salva la categoria">
            </form>

            <div id="risultato"></div>
        </div>

        <h2 class="sectionCatalog">
            <button aria-expanded="false">
                Gestione Categorie
                <svg viewBox="0 0 10 10" aria-hidden="true" focusable="false">
                    <rect class="vert" height="8" width="2" y="1" x="4"></rect>
                    <rect height="2" width="8" y="4" x="1"></rect>
                </svg>
            </button>
        </h2>
        <div class="sectionCatalog" hidden>
            <div class="field clearfix">
                <select id="search-category" name="search-category">
                    <option value="name">Nome</option>
                    <option value="description">Descrizione</option>
                </select>
                <input class="input-search-category" type="text"/>
            </div>
            <div class="search-result-category"></div>
        </div>

        <h2 class="sectionCatalog">
            <button aria-expanded="false">
                Inserimento Viaggi
                <svg viewBox="0 0 10 10" aria-hidden="true" focusable="false">
                    <rect class="vert" height="8" width="2" y="1" x="4"></rect>
                    <rect height="2" width="8" y="4" x="1"></rect>
                </svg>
            </button>
        </h2>
        <div class="sectionCatalog" hidden>
            Space for insert Travel
        </div>

        <h2 class="sectionCatalog">
            <button aria-expanded="false">
                Gestione Viaggi
                <svg viewBox="0 0 10 10" aria-hidden="true" focusable="false">
                    <rect class="vert" height="8" width="2" y="1" x="4"></rect>
                    <rect height="2" width="8" y="4" x="1"></rect>
                </svg>
            </button>
        </h2>
        <div class="sectionCatalog" hidden>
            Space for Modify Delete Search travel
        </div>

    </div>
</div>
<div class="footer">
    <%@include file="/include/footer.inc"%>
</div>
</html>