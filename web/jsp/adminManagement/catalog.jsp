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
    <style>
        .active {
            border-top:solid 1px #210800;
            background: linear-gradient(#621900, #822100);
        }
    </style>
</head>
<body onload="setLabel('adminCatalog')">
<%@include file="/include/header.inc"%>
<div class="admin">
    <div class="sidenav">
        <a id="adminCatalog" href="Dispatcher?controllerAction=AdminManagement.catalog">Catalogo</a>
        <a id="adminUser" href="Dispatcher?controllerAction=AdminManagement.user">Utenti</a>
        <a id="adminOrder" href="Dispatcher?controllerAction=AdminManagement.order">Ordini</a>
        <a id="adminReport" href="Dispatcher?controllerAction=AdminManagement.report">Report</a>
    </div>
    <div class="main">
        Benvenuto <%=loggedUser.getFirstname()%> <%=loggedUser.getSurname()%>!<br/>
        Sezione Catalog.
    </div>
</div>
<div class="footer">
    <%@include file="/include/footer.inc"%>
</div>
</html>