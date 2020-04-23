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
    <script>
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
    <title> Utenti</title>
    <style>


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

        .search-table-outter {
            border:2px solid red;
        }
        .search-table{
            table-layout: fixed;
            margin:40px auto 0px auto;
        }
        .search-table, td, th{
            border-collapse:collapse;
            border:1px solid #777;
        }
        th{
            padding:20px 7px;
            font-size:15px;
            color:#444;
            background:#66C2E0;
        }
        td{
            padding:5px 10px; height:35px;
        }

        .search-table-outter {
            overflow-x: scroll;
        }
        th, td {
            min-width: 200px;
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
            <section id="contacts" class="clearfix">
                <div class="search-table-outter wrapper">
                    <table class="search-table inner">
                        <caption>Tabella di tutti gli utenti</caption>
                        <thead>
                        <tr>
                            <th scope="col">Username</th>
                            <th scope="col">Firstname</th>
                            <th scope="col">Surname</th>
                            <th scope="col">Email</th>
                            <th scope="col">Birthday</th>
                            <th scope="col">Sex</th>
                            <th scope="col">Phone</th>
                            <th scope="col">Street</th>
                            <th scope="col">Number</th>
                            <th scope="col">City</th>
                            <th scope="col">Province</th>
                            <th scope="col">Cap</th>
                            <th scope="col">Profession</th>
                            <th scope="col">Codice Fiscale</th>
                            <th scope="col">Role</th>
                            <th scope="col">User Id</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%for (i = 0; i < users.size(); i++) {%>
                        <tr>
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
                            <td><%=users.get(i).getUserId()%></td>
                        </tr>
                    <%}%>
                        </tbody>
                    </table>
                </div>
            </section>
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