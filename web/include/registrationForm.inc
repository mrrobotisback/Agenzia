<section id="insFormSection">
    <form name="insForm" onsubmit="redirect()" id="myForm" action="?controllerAction=RegistrationManagement.insert" method="post">

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
            <input type="text" id="username" name="username" value="" required size="20" maxlength="50"/><span class="status"></span>
        </div>
        <div class="field clearfix">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" value="" placeholder="Password" required size="20" maxlength="50"/>
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
        <%if (loggedOn && admin) {%>
        <div class="field clearfix">
            <label for="admin">Admin</label>
            <select id="admin" name="admin">
                <option value="admin">Admin</option>
                <option value="user">User</option>
            </select>
        </div>
        <%}%>
        <div class="field clearfix">
            <label>&#160;</label>
            <input type="submit" class="button" value="Invia"/>
            <input type="button" name="backButton" class="button" value="Annulla"/>
            <input type="button" onclick="emptyField()" name="resetButton" class="button" value="Reset"/>
        </div>

        <input type="hidden" name="controllerAction"/>

    </form>
</section>
<%if (!admin && !loggedOn) {%>
<form name="backForm" method="post" action="Dispatcher">
    <input type="hidden" name="controllerAction" value="HomeManagement.view"/>
</form>
<%} else {%>
<form name="backForm" method="post" action="Dispatcher">
    <input type="hidden" name="controllerAction" value="AdminManagement.user"/>
</form>
<%}%>