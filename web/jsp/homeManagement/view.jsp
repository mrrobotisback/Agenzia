<%@page session="false"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" pageEncoding="UTF-8"%>
<%@page import="model.session.mo.LoggedUser"%>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    boolean admin = (Boolean) request.getAttribute("admin");
    LoggedUser loggedUser = (LoggedUser) request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Home";
    boolean registration = false;
%>

<!DOCTYPE html>
<html>
<head>
    <%@include file="/include/htmlHead.inc"%>
</head>
<body>
<%@include file="/include/header.inc"%>
<main>
    <%if (loggedOn) {%>
    Benvenuto <%=loggedUser.getFirstname()%> <%=loggedUser.getSurname()%>!<br/>
    Clicca sulla voce agenzia del menù per visualizzare il catalogo.
    <%} else {%>
    Benvenuto.
    Fai il logon per gestire le tue prenotazioni oppure visulizza il catalogo premendo su agenzia.
    Oppure registrati <a href="Dispatcher?controllerAction=RegistrationManagement.view">qui</a>
    <%}%>
</main>
<%@include file="/include/footer.inc"%>
</html>
