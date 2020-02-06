<%@page session="false"%>
<%@page import="model.session.mo.LoggedUser"%>

<%int i = 0;
    boolean loggedOn = true;
    LoggedUser loggedUser = (LoggedUser) request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Home";

    String selectedInitial = (String) request.getAttribute("selectedInitial");
%>

<!DOCTYPE html>
<html>
<head>
    <%@include file="/include/htmlHead.inc"%>
    <style>

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
            f.controllerAction.value = "AddressBookManagement."+status;
        }

        function goback() {
            document.backForm.submit();
        }

        function mainOnLoadHandler() {
            document.insModForm.addEventListener("submit", submitContact);
            document.insModForm.backButton.addEventListener("click", goback);
        }

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
                <label>Sesso</label>
                <input type="radio" name="sex" value="M"
                /> M
                <input type="radio" name="sex" value="F"
                /> F
            </div>
            <div class="field clearfix">
                <label for="address">Indirizzo</label>
                <input type="text" id="address" name="address"
                       value=""
                       required size="20" maxlength="50"/>
            </div>
            <div class="field clearfix">
                <label for="city">Citt&#224;</label>
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
                <label>&#160;</label>
                <input type="submit" class="button" value="Invia"/>
                <input type="button" name="backButton" class="button" value="Annulla"/>
            </div>

            <input type="hidden" name="selectedInitial"/>
            <input type="hidden" name="controllerAction"/>

        </form>
    </section>

    <form name="backForm" method="post" action="Dispatcher">
        <input type="hidden" name="controllerAction" value="AddressBookManagement.view"/>
    </form>

</main>
<%@include file="/include/footer.inc"%>
</body>

</html>
