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
</head>
<body>
<%@include file="/include/header.inc"%>
<div class="admin">
    <div class="sidenav">
        <%@include file="/include/sidemenuCustomer.inc"%>
    </div>
    <div class="main">
        Benvenuto <%=loggedUser.getFirstname()%> <%=loggedUser.getSurname()%>!<br/>
        Qui puoi visualizzare gli ordini e aggiornare le tue informazioni.
    </div>
</div>
<div class="footer">
    <%@include file="/include/footer.inc"%>
</div>
</html>