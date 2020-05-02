<%@page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="model.session.mo.LoggedUser"%>
<%@page import="model.mo.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page import="java.text.ParseException" %>

<%int i = 0;
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    LoggedUser loggedUser = (LoggedUser) request.getAttribute("loggedUser");
    boolean admin = (Boolean) request.getAttribute("admin");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Gestione";
    String submenuActiveLink = "user";
    boolean registration = true;
    String action="insert";
    List<User> users = (List<User>) request.getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
    <%@include file="/include/htmlHead.inc"%>
    <link href="css/registration.css" type="text/css" rel="stylesheet" />
    <script src="jsLib/jquery.js" type="text/javascript"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/registrationForm.js"></script>
<%--    <script type="text/javascript" src="${pageContext.request.contextPath}/js/modifyUser.js"></script>--%>
    <script>

        $(document).ready(function(){
            let lastValue = null;
            // Add Class
            $('.edit').click(function(){
                // $(this).addClass('editMode');
                lastValue = $(this).text();
                console.log(lastValue, "Hai cliccato edit");


            });

            // Save data
            $(".edit").focusout(function(){
                $(this).removeClass("editMode");
                let id = this.id;
                let split_id = id.split("_");
                let field_name = split_id[0].split("edit-")[1];
                let user_id = split_id[1];
                let field_value = $(this).text();

                if (lastValue !== field_value) {
                    $.ajax({
                        url: 'Dispatcher?helperAction=Data.updateUser',
                        type: 'POST',
                        data: { field:field_name, value:field_value, id:user_id },
                        success:function(response){
                            console.log('Save successfully');
                            // alert('Save successfully');
                        }
                    });
                } else {
                    $(".save-content").hide();
                    $(".not-changed").show();
                }

            });

            $(".detail_button").click(function(){
                let parentTr = $(this).closest("tr");
                let counter = 1;
                let nameid = null;
                $("td", $(parentTr)).each(function(){
                    if(!($(this).hasClass("detail_td"))){
                        if (nameid === null) {
                            nameid = $(this).text();
                        }
                        let selector = $(".modal-body tr td:nth-child("+counter+")");
                        if (selector.attr("id") !== undefined) {
                            if (selector.attr("id") !== undefined && selector.attr("id").includes("_")) {
                                selector.attr("id",  selector.attr("id").substring(0, selector.attr("id").indexOf("_")));
                            }
                            selector.attr("id",  selector.attr("id") + "_" + nameid);
                        } else {
                            selector.attr("id", "_" + nameid);
                        }

                        selector.text($(this).text());
                        counter++;
                    }
                    $(".save-content").show().delay(5000).queue(function(n) {
                        $(this).hide(); n();
                    });
                    $(".modal-body").show();
                    $("#bodytable").hide();

                });
            });

            $("#hide_popup").click(function(){
                $(".modal-body").hide();
                $("#bodytable").show();
                $(".save-content").hide();
                $(".not-changed").hide();
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

        function deleteContact(code) {
            document.deleteForm.userId.value = code;
            document.deleteForm.submit();
        }
    </script>
    <title> Utenti</title>
    <style>

        .modal-body{
            display:none;
            position:absolute;
            top:0;
            left:0;
        }

        .not-changed {
            display:none;
            position:absolute;
            top:0;
            left:0;
        }
        .save-content {
            display:none;
            position:absolute;
            top:0;
            left:0;
        }

        .white_content {
            display: none;
            position: absolute;
            top: 25%;
            left: 25%;
            width: 50%;
            height: 50%;
            padding: 16px;
            border: 16px solid #daa999;
            background-color: white;
            z-index:1002;
            overflow: auto;
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

        .sectionUser {
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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="setButton();setLabel('adminUser')">
<%@include file="/include/header.inc"%>
<div class="admin">
    <div class="sidenav">
        <a id="adminCatalog" href="Dispatcher?controllerAction=AdminManagement.catalog">Catalogo</a>
        <a id="adminUser" href="Dispatcher?controllerAction=AdminManagement.user">Utenti</a>
        <a id="adminOrder" href="Dispatcher?controllerAction=AdminManagement.order">Ordini</a>
        <a id="adminReport" href="Dispatcher?controllerAction=AdminManagement.report">Report</a>
    </div>
    <div class="main">
        <h2 class="sectionUser first">
            <button aria-expanded="false">
                Inserimento
                <svg viewBox="0 0 10 10" aria-hidden="true" focusable="false">
                    <rect class="vert" height="8" width="2" y="1" x="4"></rect>
                    <rect height="2" width="8" y="4" x="1"></rect>
                </svg>
            </button>
        </h2>
        <div class="sectionUser" hidden>
            <%@include file="/include/registrationForm.inc"%>
        </div>
        <h2 class="sectionUser">
            <button aria-expanded="false">
                Cancellazione
                <svg viewBox="0 0 10 10" aria-hidden="true" focusable="false">
                    <rect class="vert" height="8" width="2" y="1" x="4"></rect>
                    <rect height="2" width="8" y="4" x="1"></rect>
                </svg>
            </button>
        </h2>
        <div class="sectionUser" hidden>
            <div class="search-table-outter">

                <div class="modal-body white_content">
                    <input type="button" id="hide_popup" value="Nascondi"/>
                    <div class="save-content">I contenuti si salvano se clicchi fuori dalla cella appena modificata ed il valore è cambiato</div>
                    <div class="not-changed">Valore non cambiato</div>
                    <table id="mytable" class="search-table">
                        <thead>
                        <th scope="col">Id utente</th>
                        <th scope="col">Username</th>
                        <th scope="col">Nome</th>
                        <th scope="col">Cognome</th>
                        <th scope="col">Email</th>
                        <th scope="col">Data nascita</th>
                        <th scope="col">Sesso</th>
                        <th scope="col">Telefono</th>
                        <th scope="col">Via</th>
                        <th scope="col">Numero</th>
                        <th scope="col">Città</th>
                        <th scope="col">Provincia</th>
                        <th scope="col">Cap</th>
                        <th scope="col">Professione</th>
                        <th scope="col">Codice Fiscale</th>
                        <th scope="col">Ruolo</th>
                        </thead>
                        <tbody>
                        <tr>
                            <td></td>
                            <td contenteditable='true' class='edit' id="edit-username"></td>
                            <td contenteditable='true' class='edit' id="edit-name"></td>
                            <td contenteditable='true' class='edit' id="edit-surname"></td>
                            <td contenteditable='true' class='edit' id="edit-email"></td>
                            <td contenteditable='true' class='edit' id="edit-data"></td>
                            <td contenteditable='true' class='edit' id="edit-sex"></td>
                            <td contenteditable='true' class='edit' id="edit-tel"></td>
                            <td contenteditable='true' class='edit' id="edit-via"></td>
                            <td contenteditable='true' class='edit' id="edit-number"></td>
                            <td contenteditable='true' class='edit' id="edit-citta"></td>
                            <td contenteditable='true' class='edit' id="edit-province"></td>
                            <td contenteditable='true' class='edit' id="edit-cap"></td>
                            <td contenteditable='true' class='edit' id="edit-profession"></td>
                            <td contenteditable='true' class='edit' id="edit-cf"></td>
                            <td contenteditable='true' class='edit' id="edit-role"></td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <table id="bodytable" class="search-table">
                    <caption>Tabella di tutti gli utenti</caption>
                    <thead>
                    <tr>
                        <th scope="col">Dettagli</th>
                        <th scope="col">Elimina</th>
                        <th scope="col">Seleziona</th>
                        <th scope="col">Id utente</th>
                        <th scope="col">Username</th>
                        <th scope="col">Nome</th>
                        <th scope="col">Cognome</th>
                        <th scope="col">Email</th>
                        <th scope="col">Data nascita</th>
                        <th scope="col">Sesso</th>
                        <th scope="col">Telefono</th>
                        <th scope="col">Via</th>
                        <th scope="col">Numero</th>
                        <th scope="col">Città</th>
                        <th scope="col">Provincia</th>
                        <th scope="col">Cap</th>
                        <th scope="col">Professione</th>
                        <th scope="col">Codice Fiscale</th>
                        <th scope="col">Ruolo</th>
                    </tr>
                    </thead>
                    <tbody>

                    <%for (i = 0; i < users.size(); i++) {%>
                    <tr>
                        <td class="detail_td">
                            <p data-placement="top" data-toggle="tooltip" title="Details">
                                <button class="detail_button" data-title="Details" data-toggle="modal" data-target="#Details">Dettagli
                                </button>
                            </p>
                        </td>
                        <td class="detail_td">
                            <a href="javascript:deleteContact(<%=users.get(i).getUserId()%>)">
                                <img id="trashcan" src="images/trashcan.png" width="22" height="22"/>
                            </a>
                        </td>
                        <td class="detail_td"><input type="checkbox" name="<%=i%>" value="<%=users.get(i).getUserId()%>"/></td>
                        <td><%=users.get(i).getUserId()%></td>
                        <td><%=users.get(i).getUsername()%></td>
                        <td><%=users.get(i).getFirstname()%></td>
                        <td><%=users.get(i).getSurname()%></td>
                        <td><%= users.get(i).getEmail()%></td>
                        <td> <%try {%>
                            <%= users.get(i).getBirthday()%><%} catch (java.text.ParseException e) {%>
                            <%e.printStackTrace();%>
                            <%}%></td>
                        <td><%=users.get(i).getSex()%></td>
                        <td><%= users.get(i).getPhone()%></td>
                        <td><%= users.get(i).getVia()%></td>
                        <td><%= users.get(i).getVia()%></td>
                        <td><%= users.get(i).getCitta()%></td>
                        <td><%= users.get(i).getProvincia()%></td>
                        <td><%= users.get(i).getCap()%></td>
                        <td><%= users.get(i).getWork()%></td>
                        <td><%= users.get(i).getCf()%></td>
                        <td><%=users.get(i).getRole()%></td>
                    </tr>
                <%}%>
                    </tbody>
                </table>
            </div>
            <form name="deleteForm" method="post" action="Dispatcher">
                <input type="hidden" name="userId"/>
                <input type="hidden" name="controllerAction" value="AdminManagement.delete"/>
            </form>
        </div>
        <h2 class="sectionUser">
            <button aria-expanded="false">
                Ricerca
                <svg viewBox="0 0 10 10" aria-hidden="true" focusable="false">
                    <rect class="vert" height="8" width="2" y="1" x="4"></rect>
                    <rect height="2" width="8" y="4" x="1"></rect>
                </svg>
            </button>
        </h2>
        <div class="sectionUser" hidden>
            Test
        </div>
    </div>
</div>
<div class="footer">
    <%@include file="/include/footer.inc"%>
</div>
</html>