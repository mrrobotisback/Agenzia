<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/aes.js"></script>
<script src="http://crypto-js.googlecode.com/svn/tags/3.1.2/build/rollups/md5.js"></script>
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

  function cryptPassword() {
      let password = document.getElementById('password').value;
      document.getElementById('password').value = CryptoJS.MD5(password);
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
          <a href="Dispatcher?controllerAction=AddressBookManagement.view">Agenzia</a>
        </li>
        <li><a href="javascript:logoutForm.submit()">Logout</a></li>
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
        <input type="submit" onclick="cryptPassword()" value="Ok">
      </form>
    </section>
  <%}%>

</header>