<script>
  function headerOnLoadHandler() {
    let usernameTextField = document.querySelector("#username");
    let usernameTextFieldMsg = "Lo username \xE8 obbligatorio.";
    let passwordTextField = document.querySelector("#password");
    let passwordTextFieldMsg = "La password \xE8 obbligatoria.";

    if (usernameTextField !== undefined && passwordTextField !== undefined ) {
      usernameTextField.setCustomValidity(usernameTextFieldMsg);
      usernameTextField.addEventListener("change", function () {
        this.setCustomValidity(this.validity.valueMissing ? usernameTextFieldMsg : "");
      });
      passwordTextField.setCustomValidity(passwordTextFieldMsg);
      passwordTextField.addEventListener("change", function () {
       this.setCustomValidity(this.validity.valueMissing ? passwordTextFieldMsg : "");
      });
    }
  }
</script>

<header class="clearfix"><!-- Defining the header section of the page -->

  <h1 class="logo"><!-- Defining the logo element -->
    Agenzia
  </h1>

  <form name="logoutForm" action="Dispatcher" method="post">
    <input type="hidden" name="controllerAction" value="HomeManagement.logout"/>
  </form>

  <nav><!-- Defining the navigation menu -->
    <ul>
      <li <%=menuActiveLink.equals("Home")?"class=\"active\"":""%>>
        <a href="Dispatcher?controllerAction=HomeManagement.view">Home</a>
      </li>
      <%if (loggedOn) {%>
        <li <%=menuActiveLink.equals("Agenzia")?"class=\"active\"":""%>>
          <a href="Dispatcher?controllerAction=TravelManagement.view">Agenzia</a>
        </li>
        <li><a href="javascript:logoutForm.submit()">Logout</a></li>
      <%}%>
      <%if (loggedOn && admin) {%>
      <li <%=menuActiveLink.equals("Gestione")?"class=\"active\"":""%>>
        <a href="Dispatcher?controllerAction=adminManagement.view">Gestione</a>
      </li>
      <%}%>
    </ul>
  </nav>

  <%if (!loggedOn && !registration) {%>
    <section id="login" class="clearfix">
      <form name="logonForm" action="Dispatcher" method="post">
        <label for="username">Utente</label>
        <input type="text" id="username"  name="username" maxlength="40" required>
        <label for="password">Password</label>
        <input type="password" id="password" name="password" maxlength="40" required>
        <input type="hidden" name="controllerAction" value="HomeManagement.logon"/>
        <input type="submit" value="Ok">
      </form>
    </section>
  <%}%>

</header>