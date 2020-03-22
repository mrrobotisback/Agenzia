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
    <%@include file="/include/subMenuAdmin.inc"%>
</head>
<body>
<%@include file="/include/header.inc"%>
<div class="admin">
    <div class="sidenav">
        <a href="#">Catalog</a>
        <a href="#">User</a>
        <a href="#">Order</a>
        <a href="#">Report</a>
    </div>
    <div class="main">
        Benvenuto <%=loggedUser.getFirstname()%> <%=loggedUser.getSurname()%>!<br/>
        Qui puoi inserire e rimuovere nuovi aritcoli, admin e utenti, controllare ordini e report vendite.
        Qui puoi inserire e rimuovere nuovi aritcoli, admin e utenti, controllare ordini e report vendite.
        Qui puoi inserire e rimuovere nuovi aritcoli, admin e utenti, controllare ordini e report vendite.
    </div>
    <div style="clear:both;"/>
</div>
<div class="footer">
<%@include file="/include/footer.inc"%>
</div>
</html>
