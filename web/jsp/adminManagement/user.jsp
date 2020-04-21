<%@page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="model.session.mo.LoggedUser"%>
<%@page import="model.mo.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

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
                <%for (i = 0; i < users.size(); i++) {%>
                <article>
                    <h1>
                        <a href="javascript:modifyUser(<%=users.get(i).getUserId()%>)">
                            <%=users.get(i).getSurname()%>, <%=users.get(i).getFirstname()%>
                        </a>
                    </h1>
                    <span class="phone"><%= users.get(i).getPhone()%></span>
                    <br/>
                    <span class="email"><%= users.get(i).getEmail()%></span>
                    <address>
                        <%= users.get(i).getVia()%><br/>
                        <%= users.get(i).getCitta()%><br/>
                    </address>
                    <a href="javascript:deleteUser(<%=users.get(i).getUserId()%>)">
                        <img id="trashcan" src="images/trashcan.png" width="22" height="22"/>
                    </a>
                </article>
                <%}%>
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