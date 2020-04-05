<%@page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="model.session.mo.LoggedUser"%>

<%int i = 0;
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    LoggedUser loggedUser = (LoggedUser) request.getAttribute("loggedUser");
    boolean admin = (Boolean) request.getAttribute("admin");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Home";
    boolean registration = true;
    String action="insert";

%>

<!DOCTYPE html>
<html>
<head>
    <%@include file="/include/htmlHead.inc"%>
    <link href="css/registration.css" type="text/css" rel="stylesheet" />
    <script src="jsLib/jquery.js" type="text/javascript"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/registrationForm.js"></script>
    <title> Utenti</title>
</head>
<body>
<%@include file="/include/header.inc"%>
<div class="admin">
    <div class="sidenav">
        <a href="Dispatcher?controllerAction=AdminManagement.catalog">Catalog</a>
        <a href="Dispatcher?controllerAction=AdminManagement.user">User</a>
        <a href="Dispatcher?controllerAction=AdminManagement.order">Order</a>
        <a href="Dispatcher?controllerAction=AdminManagement.report">Report</a>
    </div>
    <div class="main">
        <%@include file="/include/registrationForm.inc"%>
    </div>
</div>
<div class="footer">
    <%@include file="/include/footer.inc"%>
</div>
</html>