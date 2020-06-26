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

        $(document).ready(function() {
            let modalTitle = (<%=data%>).firstname + ' ' + (<%=data%>).surname;
            $('.success-info-submit').hide();
            $('.modal').hide();
            $('#firstname').val((<%=data%>).firstname);
            $('#surname').val((<%=data%>).surname);
            $('#birthday').val((<%=data%>).birthday);
            $('#cap').val((<%=data%>).cap);
            $('#via').val((<%=data%>).via);
            $('#numero').val((<%=data%>).numero);
            $('#citta').val((<%=data%>).citta);
            $('#provincia').val((<%=data%>).provincia);
            $('#phone').val((<%=data%>).phone);
            $('#email').val((<%=data%>).email);
            $('#work').val((<%=data%>).work);
            $('#cf').val((<%=data%>).cf);
            $('.modal-title').html(modalTitle);
            $("input[name=sex][value='" + (<%=data%>).sex + "']").prop("checked",true);
        });

        function print() {
            let firstname = $('#firstname').val() !== (<%=data%>).surname ? $('#firstname').val() : '';
            let surname = $('#surname').val() !== (<%=data%>).surname ? $('#surname').val() : '';
            let birthday = $('#birthday').val() !== (<%=data%>).birthday ? $('#surname').val() : '';
            let cap = $('#cap').val() !== (<%=data%>).cap ? $('#cap').val() : '';
            let via = $('#via').val() !== (<%=data%>).via ? $('#via').val() : '';
            let numero = $('#numero').val() !== (<%=data%>).numero ? $('#numero').val() : '';
            let citta = $('#citta').val() !== (<%=data%>).citta ? $('#citta').val() : '';
            let provincia = $('#provincia').val() !== (<%=data%>).provincia ? $('#provincia').val() : '';
            let phone = $('#phone').val() !== (<%=data%>).phone ? $('#phone').val() : '';
            let email = $('#email').val() !== (<%=data%>).email ? $('#email').val() : '';
            let work = $('#work').val() !== (<%=data%>).work ? $('#work').val() : '';
            let cf = $('#cf').val() !== (<%=data%>).cf ? $('#cf').val() : '';
            let password = $('#password').val() !== '' ? $('#surname').val() : '';

            $.ajax({
                url: 'Dispatcher?controllerAction=CustomerManagement.updateCustomer',
                type: 'POST',
                data: { field: 'id', value: idTravel },
                success:function(response){
                    $('#myModal').show();
                    $('.close').click(function() {
                        $('#myModal').hide();
                    })
                }
            })
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

        /* The Modal (background) */
        .modal {
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgb(0,0,0); /* Fallback color */
            background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
        }

        /* Modal Content/Box */
        .modal-content {
            background-color: #fefefe;
            margin: 15% auto; /* 15% from the top and centered */
            padding: 20px;
            border: 1px solid #888;
            width: 80%; /* Could be more or less, depending on screen size */
        }

        /* The Close Button */
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }

        /* Modal Header */
        .modal-header {
            padding: 2px 16px;
            background-color: #EDD4CC;
            color: white;
        }

        /* Modal Body */
        .modal-body {padding: 2px 16px;}

        /* Modal Footer */
        .modal-footer {
            padding: 2px 16px;
            background-color: #EDD4CC;
            color: white;
        }

        /* Modal Content */
        .modal-content {
            position: relative;
            background-color: #fefefe;
            margin: auto;
            padding: 0;
            border: 1px solid #888;
            width: 80%;
            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19);
            animation-name: animatetop;
            animation-duration: 0.4s
        }

        /* Add Animation */
        @keyframes animatetop {
            from {top: -300px; opacity: 0}
            to {top: 0; opacity: 1}
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
        <div id="myModal" class="modal">
            <div class="modal-content">
                <div class="modal-header">
                    <span class="close">&times;</span>
                    <h2 class="modal-title"></h2>
                </div>
                <div class="modal-body">
                    <p>Informazioni aggiornate</p>
                </div>
                <div class="modal-footer">
                    <h3>Grazie</h3>
                </div>
            </div>
        </div>
        <section id="insFormSection">
            <form name="insForm" onsubmit="print()" id="myForm" action="?controllerAction=CustomerManagement.info" method="post">

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
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" value="" placeholder="Nuova password" size="20" maxlength="50"/>
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
                <div class="field clearfix">
                    <label>&#160;</label>
                    <input type="submit" class="button" value="Invia"/>
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