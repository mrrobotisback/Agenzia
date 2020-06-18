<%@page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="model.session.mo.LoggedUser"%>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    boolean admin = (Boolean) request.getAttribute("admin");
    LoggedUser loggedUser = (LoggedUser) request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Agenzia";
    boolean registration = false;
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="jsLib/jquery.js" type="text/javascript"></script>
    <title>
        Catalogo
    </title>
    <%@include file="/include/htmlHead.inc"%>
    <style>
        .catalog {
            background-color: #FFFFFF;
            display: flex;
        }
        .main {
            flex: 85;
            padding: 10px 10px 10px 10px;
        }

    </style>
</head>
<body>
<%@include file="/include/header.inc"%>
<div class="catalog">
    <div class="sidenav">
        <a id="Cat1" href="Dispatcher?controllerAction=AdminManagement.catalog">Cat 1</a>
        <a id="Cat2" href="Dispatcher?controllerAction=AdminManagement.user">Cat 2 </a>
        <a id="Cat3" href="Dispatcher?controllerAction=AdminManagement.order">Cat 3</a>
        <a id="Cat4" href="Dispatcher?controllerAction=AdminManagement.report">Cat 4</a>
    </div>
    <div class="main">
        Ciao
    </div>
</div>
<div class="footer">
    <%@include file="/include/footer.inc"%>
</div>
</html>