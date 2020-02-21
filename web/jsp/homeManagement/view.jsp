<%@page session="false"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" pageEncoding="UTF-8"%>
<%@page import="model.session.mo.LoggedUser"%>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
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
    Clicca sulla voce ordini del menù per gestire i tuoi Viaggi.
    <%} else {%>
    Benvenuto.
    Fai il logon per gestire le tue prenotazioni.
    Oppure registrati <a href="Dispatcher?controllerAction=RegistrationManagement.view">qui</a>
    <%}%>
</main>
<%@include file="/include/footer.inc"%>
</html>
