<%@page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.google.gson.Gson"%>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="model.session.mo.LoggedUser" %>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    boolean admin = (Boolean) request.getAttribute("admin");
    LoggedUser loggedUser = (LoggedUser) request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Gestione";
    boolean registration = false;
    String data = (String) request.getAttribute("data");
//    GsonBuilder gsonBuilder = new GsonBuilder();
//    Gson gson = gsonBuilder.create();


%>

<!DOCTYPE html>
<html>
<head>
    <%@include file="/include/htmlHead.inc"%>
    <link href="css/registration.css" type="text/css" rel="stylesheet" />
    <script src="jsLib/jquery.js" type="text/javascript"></script>
    <script>
        function setLabel(id) {
            document.getElementById(id).classList.add('active');
        }
        console.log((<%=data%>), "data");

        $(document).ready(function() {
            $('#firstname').val((<%=data%>).firstname);
            $('#firstname').val((<%=data%>).surname);
            $('#firstname').val((<%=data%>).birthday);
            $('#firstname').val((<%=data%>).cap);
            $('#firstname').val((<%=data%>).firstname);
            $('#firstname').val((<%=data%>).firstname);
            $('#firstname').val((<%=data%>).firstname);
            $('#firstname').val((<%=data%>).firstname);
            $('#firstname').val((<%=data%>).firstname);
            $('#firstname').val((<%=data%>).firstname);
            $('#firstname').val((<%=data%>).firstname);
            $('#firstname').val((<%=data%>).firstname);
            $('#firstname').val((<%=data%>).firstname);
            $('#firstname').val((<%=data%>).firstname);
        });

        function print() {
            controllerAction.value = "RegistrationManagement.insert";
        }

        function emptyField() {
            document.getElementById("myForm").reset();
        }
    </script>
    <style>
        .active {
            border-top:solid 1px #210800;
            background: linear-gradient(#621900, #822100);
        }
    </style>
</head>
<body onload="setLabel('customerInfo')">
<%@include file="/include/header.inc"%>
<div class="admin">
    <div class="sidenav">
        <%@include file="/include/sidemenuCustomer.inc"%>
    </div>
    <div class="main">
        <div class="success-info-submit"></div>
        <section id="insFormSection">
            <form name="insForm" onsubmit="print()" id="myForm" action="?controllerAction=customerManagement.info" method="post">

                <div class="field clearfix">
                    <label for="firstname">Nome</label>
                    <input type="text" id="firstname" name="firstname"
                           value=""
                           required size="20" maxlength="50"/>
                </div>
                <div class="field clearfix">
                    <label for="surname">Cognome</label>
                    <input type="text" id="surname" name="surname"
                           value=""
                           required size="20" maxlength="50"/>
                </div>
                <div class="field clearfix">
                    <label for="password">inserici la nuova password</label>
                    <input type="password" id="password" name="password" value="" placeholder="Password" required size="20" maxlength="50"/>
                </div>
                <div class="field clearfix">
                    <label for="birthday">Data di nascita</label>
                    <input type="date" id="birthday" placeholder="DD/MM/YYYY" max="" name="birthday" value="" required/>
                </div>
                <div class="field clearfix">
                    <label>Sesso</label>
                    <input type="radio" name="sex" value="M"
                    /> M
                    <input type="radio" name="sex" value="F"
                    /> F
                </div>
                <div class="field clearfix">
                    <div id="address">
                        <div style="float:left;margin-right:20px;">
                            <label class="indirizzo" for="via">Via</label>
                            <input class="indirizzo" type="text" id="via" name="via" value="" required size="20" maxlength="50"/>
                        </div>
                        <div style="float:left;margin-right:20px;">
                            <label class="indirizzo" for="numero">Numero</label>
                            <input class="indirizzo" type="text" id="numero" name="numero" value="" required size="20" maxlength="50"/>
                        </div>
                        <div style="float:left;margin-right:20px;">
                            <label class="indirizzo" for="citta">Citt&agrave;</label>
                            <input class="indirizzo" type="text" id="citta" name="citta" value="" required size="20" maxlength="50"/>
                        </div>
                        <div style="float:left;margin-right:20px;margin-top:5px">
                            <label class="indirizzo" for="provincia">Provincia</label>
                            <input class="indirizzo" type="text" id="provincia" name="provincia" value="" required size="20" maxlength="50"/>
                        </div>
                        <div style="float:left;margin-right:20px;margin-top:5px">
                            <label class="indirizzo" for="cap">Cap</label>
                            <input class="indirizzo" type="text" id="cap" name="cap" value="" required size="20" maxlength="50"/>
                        </div>
                    </div>
                </div>
                <div class="field clearfix">
                    <label for="phone">Telefono</label>
                    <input type="tel" id="phone" name="phone"
                           value=""
                           required size="20" maxlength="50"/>
                </div>
                <div class="field clearfix">
                    <label for="email">E-Mail</label>
                    <input type="email" id="email" name="email"
                           value=""
                           required size="20" maxlength="50"/>
                </div>
                <div class="field clearfix">
                    <label for="work">Professione</label>
                    <input type="text" id="work" name="work"
                           value=""
                           required size="20" maxlength="50"/>
                </div>
                <div class="field clearfix">
                    <label for="cf">Codice fiscale</label>
                    <input type="text" id="cf" name="cf"
                           value=""
                           required size="20" maxlength="50" pattern="^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$"/>
                </div>
                <%if (loggedOn && admin) {%>
                <div class="field clearfix">
                    <label for="admin">Admin</label>
                    <select id="admin" name="admin">
                        <option value="admin">Admin</option>
                        <option value="user">User</option>
                    </select>
                </div>
                <%}%>
                <div class="field clearfix">
                    <label>&#160;</label>
                    <input type="submit" class="button" value="Invia"/>
                    <input type="button" name="backButton" class="button" value="Annulla"/>
                    <input type="button" onclick="emptyField()" name="resetButton" class="button" value="Reset"/>
                </div>

                <input type="hidden" name="controllerAction"/>

            </form>
        </section>
    </div>
</div>
<div class="footer">
    <%@include file="/include/footer.inc"%>
</div>
</html>