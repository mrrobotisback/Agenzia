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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/registrationForm.js"></script>
    <link href="css/registration.css" type="text/css" rel="stylesheet" />
    <script src="jsLib/jquery.js" type="text/javascript"></script>
    <script>

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
                        selectorResult.show().delay(3000).queue(function(n) {
                            $(this).hide(); n();
                        });
                    },
                    error: function()
                    {
                        alert("Chiamata fallita, si prega di riprovare...");
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
            <form name="insert-category">
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
            Space for modify delete search Category
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