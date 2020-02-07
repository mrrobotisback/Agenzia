<%@page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="model.session.mo.LoggedUser"%>

<%int i = 0;
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    LoggedUser loggedUser = (LoggedUser) request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Home";
    boolean registration = true;

%>

<!DOCTYPE html>
<html>
<head>
    <%@include file="/include/htmlHead.inc"%>
    <style>

        .indirizzo {
            display:block;
        }

        .field {
            margin: 5px 0;
        }

        label {
            float: left;
            width: 56px;
            font-size: 0.8em;
            margin-right: 10px;
            padding-top: 3px;
            text-align: left;
        }

        input[type="text"], input[type="tel"], input[type="email"] {
            border: none;
            border-radius: 4px;
            padding: 3px;
            background-color: #e8eeef;
            color:#8a97a0;
            box-shadow: 0 1px 0 rgba(0,0,0,0.03) inset;
        }

        input[type="text"]:focus, input[type="tel"]:focus, input[type="email"]:focus {
            background: #d2d9dd;
            outline-color: #a3271f;
        }

    </style>
    <script>


        function submitContact() {
            let f;
            f = document.insModForm;
            f.selectedInitial.value = f.surname.value.substring(0, 1).toUpperCase();
            f.controllerAction.value = "HomeManagement."+status;
        }

        function goback() {
            document.backForm.submit();
        }

        function mainOnLoadHandler() {
            document.insModForm.addEventListener("submit", submitContact);
            document.insModForm.backButton.addEventListener("click", goback);

        }
        document.addEventListener('DOMContentLoaded', (event) => {
            Date.prototype.toDateInputValue = (function() {
                var local = new Date(this);
                local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
                local.setFullYear( local.getFullYear() - 18 );
                return local.toJSON().slice(0,10);
            });
            document.getElementById('account_birthday').max = new Date().toDateInputValue();
        });

    </script>
</head>
<body>
<%@include file="/include/header.inc"%>
<main>
    <section id="insModFormSection">
        <form name="insModForm" action="Dispatcher" method="post">

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
                <label for="username">Username</label>
                <input type="text" id="username" name="username" value="" required size="20" maxlength="50"/>
            </div>
            <div class="field clearfix">
                <label for="account_birthday">Data di nascita</label>
                <input type="date" id="account_birthday" placeholder="DD/MM/YYYY" max="" name="account_birthday" value=""/>
            </div>
            <div class="field clearfix">
                <label>Sesso</label>
                <input type="radio" name="sex" value="M"
                /> M
                <input type="radio" name="sex" value="F"
                /> F
            </div>
            <div class="field clearfix">
                <label for="address">Indirizzo</label>
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
                    <label class="indirizzo" for="citta">Città</label>
                    <input class="indirizzo" type="text" id="citta" name="citta" value="" required size="20" maxlength="50"/>
                    </div>
                    <div style="float:left;margin-right:20px;">
                    <label class="indirizzo" for="provincia">Provincia</label>
                    <input class="indirizzo" type="text" id="provincia" name="provincia" value="" required size="20" maxlength="50"/>
                    </div>
                    <div style="float:left;margin-right:20px;">
                    <label class="indirizzo" for="cap">Cap</label>
                    <input class="indirizzo" type="text" id="cap" name="cap" value="" required size="20" maxlength="50"/>
                    </div>
                </div>
            </div>
            <div class="field clearfix">
                <label for="city">Città</label>
                <input type="text" id="city" name="city"
                       value=""
                       required size="20" maxlength="50"/>
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
                <input type="button" name="backButton" class="button" value="Annulla"/>
            </div>

            <input type="hidden" name="controllerAction"/>

        </form>
    </section>

    <form name="backForm" method="post" action="Dispatcher">
        <input type="hidden" name="controllerAction" value="HomeManagement.view"/>
    </form>

</main>
<%@include file="/include/footer.inc"%>
</body>

</html>
