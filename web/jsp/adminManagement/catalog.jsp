<%@page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

        function loadCategory () {
            $.ajax({
                type: "POST",
                url: "Dispatcher?helperAction=Data.allCategory",
                success: function(response)
                {
                    let result = JSON.parse(response);
                    let message = JSON.parse(result.message);
                    let content = '<label for="category-travel">Categoria</label>';
                    content += '<select id="category-travel" name="category-travel">';
                    for (let i = 0; i < message.length; i++) {
                        content += '<option value='+ message[i].id + '>' + message[i].name + '</option>';
                    }
                    content += '</select>'
                    $("#result-travel-category").html(content);
                },
                error: function()
                {
                    console.log("Chiamata fallita, si prega di riprovare...");
                }
            });
        }

        $(document).on('focusout', '.edit-category', function() {
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

        $(document).on('focusout', '.edit-travel', function() {
            let id = this.id;
            let replace_id = id.replace('edit-travel-', '');
            let field_name = replace_id.split("_");
            let travel_id = '';
            if (field_name.length > 2) {
                travel_id = parseInt(field_name[2]);
                field_name = field_name[0] + '_' + field_name[1];
            } else {
                travel_id = parseInt(field_name[1]);
                field_name = field_name[0];
            }
            let field_value = $(this).text();
            if (field_name == 'hide') {
                if ($(this).text() === "true") {
                    field_value = 1;
                } else {
                    field_value = 0;
                }
            }

            $.ajax({
                url: 'Dispatcher?controllerAction=AdminManagement.updateTravel',
                type: 'POST',
                data: { field:field_name, value:field_value, id:travel_id },
                success:function(response){
                    console.log('Save successfully');
                }
            });
        })

        $(document).ready(function() {
            let selectorResult = $("#risultato-category");
            selectorResult.hide();
            let selectorResultTravel = $("#risultato-travel");
            selectorResultTravel.hide();

            loadCategory();

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
                        loadCategory();
                    },
                    error: function()
                    {
                        console.log("Chiamata fallita, si prega di riprovare...");
                    }
                });
            });

            $("#button-travel").click(function(){
                let name = $("#travel-name").val();
                let description = $("#travel-description").val();
                let hide = false;
                if ($('#travel-hide').is(":checked"))
                {
                   hide = true;
                }
                let destination = $('#travel-destination').val();
                let totalSeats = $('#travel-seats-total').val();
                let availableSeats = $('#travel-seats-available').val();
                let duration = $('#travel-duration').val();
                let startHour = $('#travel-start-hour').val();
                let startPlace = $('#travel-start-place').val();
                let means = $('#travel-means').val();
                let startDate = $('#travel-start-date').val();
                let discount = $('#travel-discount').val();
                let price = $('#travel-price').val();
                let category = $('#category-travel').val();

                $.ajax({
                    type: 'POST',
                    url: 'Dispatcher?controllerAction=AdminManagement.insertTravel',
                    data: 'name=' + name + '&description=' + description + '&hide=' + hide + '&destination='
                        + destination + '&total_seats=' + totalSeats + '&available_seats=' + availableSeats
                        + '&duration=' + duration + '&start_hour=' + startHour + '&start_place=' + startPlace
                        + '&means=' + means + '&start_date=' +startDate + '&discount=' + discount + '&price='
                        + price + '&category=' + category,
                    dataType: 'html',
                    success: function(response)
                    {
                        let result = JSON.parse(response);
                        selectorResultTravel.html(result.message);
                        if (parseInt(result.clear)) {
                            $('#insert-travel').trigger("reset");
                        }
                        selectorResultTravel.show().delay(3000).queue(function(n) {
                            $(this).hide(); n();
                        });
                        $('.input-search-travel').keyup();
                    },
                    error: function()
                    {
                        console.log("Chiamata fallita, si prega di riprovare...");
                    }
                });
            });

            $(function() {
                $('.input-search-category').keyup();
                $('.input-search-travel').keyup();
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

            $(".input-search-travel").keyup(function(){
                let field = $('#search-travel').find(":selected").val();
                let value = $(this).val();
                $.ajax({
                    url: 'Dispatcher?helperAction=Data.searchTravel',
                    type: 'POST',
                    data: { field:field, value:value },
                    success:function(response){
                        response = JSON.parse(response);
                        let message = JSON.parse(response.message);
                        let content = '<table id="travel-table">';
                        content += ' <thead>\n';
                        content += '<th scope="col">Id viaggio</th>';
                        content += '<th scope="col">Elimina</th>';
                        content += '<th scope="col">Nome</th>';
                        content += '<th scope="col">Descrizione</th>';
                        content += '<th scope="col">Id categoria</th>';
                        content += '<th scope="col">Destinazione</th>';
                        content += '<th scope="col">Sconto</th>';
                        content += '<th scope="col">Durata</th>';
                        content += '<th scope="col">Nascosto</th>';
                        content += '<th scope="col">Mezzi</th>';
                        content += '<th scope="col">Prezzo</th>';
                        content += '<th scope="col">Posti disponibili</th>';
                        content += '<th scope="col">Posti totali</th>';
                        content += '<th scope="col">Data partenza</th>';
                        content += '<th scope="col">Ora partenza</th>';
                        content += '<th scope="col">Posta partenza</th>';
                        content += '</thead>';
                        content += '<tbody>';

                        for (let i = 0; i < message.length; i++) {
                            content += '<tr><td>' + message[i].id  + '</td>'
                            content += '<td>\n' +
                                '                            <a href="javascript:deleteTravel(' + message[i].id + ')">\n' +
                                '                                <img id="trashcan" src="images/trashcan.png" width="22" height="22"/>\n' +
                                '                            </a>\n' +
                                '                        </td>'
                            content += '<td contenteditable=\'true\' class=\'edit-travel\' id="edit-travel-name_'+ message[i].id +'">' + message[i].name + '</td>'
                            content += '<td contenteditable=\'true\' class=\'edit-travel\' id="edit-travel-description_'+ message[i].id +'">' + message[i].description + '</td>'
                            content += '<td contenteditable=\'true\' class=\'edit-travel\' id="edit-travel-category_id_'+ message[i].id +'">' + message[i].categoryId + '</td>'
                            content += '<td contenteditable=\'true\' class=\'edit-travel\' id="edit-travel-destination_'+ message[i].id +'">' + message[i].destination + '</td>'
                            content += '<td contenteditable=\'true\' class=\'edit-travel\' id="edit-travel-discount_'+ message[i].id +'">' + message[i].discount + '</td>'
                            content += '<td contenteditable=\'true\' class=\'edit-travel\' id="edit-travel-duration_'+ message[i].id +'">' + message[i].duration + '</td>'
                            content += '<td contenteditable=\'true\' class=\'edit-travel\' id="edit-travel-hide_'+ message[i].id +'">' + message[i].hide + '</td>'
                            content += '<td contenteditable=\'true\' class=\'edit-travel\' id="edit-travel-means_'+ message[i].id +'">' + message[i].means + '</td>'
                            content += '<td contenteditable=\'true\' class=\'edit-travel\' id="edit-travel-price_'+ message[i].id +'">' + message[i].price + '</td>'
                            content += '<td contenteditable=\'true\' class=\'edit-travel\' id="edit-travel-seats_available_'+ message[i].id +'">' + message[i].seatsAvailable + '</td>'
                            content += '<td contenteditable=\'true\' class=\'edit-travel\' id="edit-travel-seats_total_'+ message[i].id +'">' + message[i].seatsTotal + '</td>'
                            content += '<td contenteditable=\'true\' class=\'edit-travel\' id="edit-travel-start_date_'+ message[i].id +'">' + message[i].startDate + '</td>'
                            content += '<td contenteditable=\'true\' class=\'edit-travel\' id="edit-travel-start_hour_'+ message[i].id +'">' + message[i].startHour + '</td>'
                            content += '<td contenteditable=\'true\' class=\'edit-travel\' id="edit-travel-start_place_'+ message[i].id +'">' + message[i].startPlace + '</td>'
                        }
                        content += "</tbody>"
                        content += "</table>"
                        if (message.length > 0) $('.search-result-travel').html(content);
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

        function deleteTravel(code) {
            $.ajax({
                url: 'Dispatcher?controllerAction=AdminManagement.deleteTravel',
                type: 'POST',
                data: { 'travelId': code },
                success:function(response){
                    response = JSON.parse(response);
                    if (parseInt(response.message) === 1) {
                        $('.input-search-travel').keyup();
                    }
                }
            });
        }

        function setLabel(id) {
            document.getElementById(id).classList.add('active');
        }

    </script>
    <style>

        label {
            width: 110px;
        }

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

            <div id="risultato-category"></div>
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
            <form name="insert-travel" id="insert-travel">
                <div class="field clearfix">
                    <label for="travel-name">Nome</label>
                    <input type="text" id="travel-name" name="travel-name" value="" placeholder="Nome Viaggio" required size="20" maxlength="50"/>
                </div>
                <div class="field clearfix">
                    <label for="travel-description">Descrizione</label>
                    <textarea rows="5" cols="100" id="travel-description" name="description"></textarea>
                </div>
                <div class="field clearfix" id="result-travel-category"></div>
                <div class="field clearfix">
                    <label for="travel-price">Prezzo</label>
                    <input type="number" id="travel-price" name="travel-price" value="" placeholder="Prezzo Viaggio" required />
                </div>
                <div class="field clearfix">
                    <label for="travel-discount">Sconto</label>
                    <input type="number" id="travel-discount" name="travel-discount" value="" placeholder="Sconto Viaggio" required />
                </div>
                <div class="field clearfix">
                    <label for="travel-start-date">Data di partenza</label>
                    <input type="date" id="travel-start-date" placeholder="DD/MM/YYYY" max="" name="travel-start-date" value="" required/>
                </div>
                <div class="field clearfix">
                    <label for="travel-means">Numero mezzi di trasporto</label>
                    <input type="text" id="travel-means" placeholder="Numero mezzi" max="" name="travel-means" value="" required/>
                </div>
                <div class="field clearfix">
                    <label for="travel-start-place">Luogo di ritrovo</label>
                    <input type="text" id="travel-start-place" placeholder="Luogo di ritrovo" max="" name="travel-start-place" value="" required/>
                </div>
                <div class="field clearfix">
                    <label for="travel-start-hour">Ora di partenza</label>
                    <input type="time" id="travel-start-hour" placeholder="Ora di ritrovo" max="" name="travel-start-hour" value="" required/>
                </div>
                <div class="field clearfix">
                    <label for="travel-duration">Durata</label>
                    <input type="text" id="travel-duration" placeholder="Durata" max="" name="travel-duration" value="" required/>
                </div>
                <div class="field clearfix">
                    <label for="travel-seats-available">Posti disponibili</label>
                    <input type="number" id="travel-seats-available" placeholder="Posti disponibili" max="" name="travel-seats-available" value="" required/>
                </div>
                <div class="field clearfix">
                    <label for="travel-seats-total">Posti totali</label>
                    <input type="number" id="travel-seats-total" placeholder="Posti totali" max="" name="travel-seats-total" value="" required/>
                </div>
                <div class="field clearfix">
                    <label for="travel-destination">Destinazione</label>
                    <input type="text" id="travel-destination" placeholder="Destinazione" max="" name="travel-destination" value="" required/>
                </div>
                <div class="field clearfix">
                    <label for="travel-hide">Nascondi</label>
                    <input type="checkbox" id="travel-hide" name="travel-hide">
                </div>
                <input type="button" id="button-travel" value="Salva il viaggio">
            </form>

            <div class="field clearfix" id="risultato-travel"></div>
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
            <div class="field clearfix">
                <select id="search-travel" name="search-travel">
                    <option value="name">Nome</option>
                    <option value="description">Descrizione</option>
                </select>
                <input class="input-search-travel" type="text"/>
            </div>
            <div class="search-result-travel"></div>
        </div>

    </div>
</div>
<div class="footer">
    <%@include file="/include/footer.inc"%>
</div>
</html>