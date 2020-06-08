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
    <link href="css/registration.css" type="text/css" rel="stylesheet" />
    <script src="jsLib/jquery.js" type="text/javascript"></script>
    <script>

        function setLabel(id) {
            document.getElementById(id).classList.add('active');
        }

        $(document).ready(function() {
            $.ajax({
                url: 'Dispatcher?controllerAction=AdminManagement.allOrder',
                type: 'POST',
                success:function(response){
                    response = JSON.parse(response);
                    let message = JSON.parse(response.message);
                    let content = '<table id="order-table">';
                    content += ' <thead>\n';
                    content += '<th scope="col">Number</th>';
                    content += '<th scope="col">Data</th>';
                    content += '<th scope="col">Total</th>';
                    content += '<th scope="col">Pagamento</th>';
                    content += '</thead>';
                    content += '<tbody>';

                    for (let i = 0; i < message.length; i++) {
                        content += '<tr><td>' + message[i].number  + '</td>'
                        content += '<td >' + message[i].date + '</td>'
                        content += '<td>' + message[i].total + '</td>'
                        content += '<td>' + message[i].with + '</td>'
                    }
                    content += "</tbody>"
                    content += "</table>"
                    if (message.length > 0) {
                        $('.search-result-order').html(content);
                    } else {
                        $('.search-result-order').html('<p>Non ci sono ordini per adesso</p>');
                    }
                }
            });
        });
    </script>
    <style>
        .active {
            border-top:solid 1px #210800;
            background: linear-gradient(#621900, #822100);
        }
    </style>
</head>
<body onload="setLabel('adminOrder')">
<%@include file="/include/header.inc"%>
<div class="admin">
    <div class="sidenav">
        <%@include file="/include/sidemenuAdmin.inc"%>
    </div>
    <div class="main">
        <div class="search-result-order"></div>
    </div>
</div>
<div class="footer">
    <%@include file="/include/footer.inc"%>
</div>
</html>