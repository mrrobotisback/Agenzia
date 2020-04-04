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

        let status="<%=action%>";

        function submitUser() {
            let f;
            f = document.insForm;
            f.selectedInitial.value = f.surname.value.substring(0, 1).toUpperCase();
            f.controllerAction.value = "RegistrationManagement."+status;
        }

        function goback() {
            document.backForm.submit();
        }

        function mainOnLoadHandler() {
            document.insForm.addEventListener("submit", submitUser);
            document.insForm.backButton.addEventListener("click", goback);

        }
        document.addEventListener('DOMContentLoaded', (event) => {
            Date.prototype.toDateInputValue = (function() {
                var local = new Date(this);
                local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
                local.setFullYear( local.getFullYear() - 18 );
                return local.toJSON().slice(0,10);
            });
            document.getElementById('birthday').max = new Date().toDateInputValue();
        });

        function emptyField() {
            document.getElementById("myForm").reset();
        }

        function redirect() {
            controllerAction.value = "RegistrationManagement.insert";
        }

    </script>
    <script src="jsLib/jquery.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#username").change(function(){
                let username = $(this).val();
                if(username.length >= 3){
                    $(".status").html("<img src='images/loading.gif'><span style=\"color:grey\"> Checking availability...</span>");
                    $.ajax({
                        headers: {
                            Accept: "application/json; charset=utf-8",
                            "Content-Type": "application/json; charset=utf-8"
                        },
                        type: "POST",
                        url: "Dispatcher?helperAction=Data.checkUsername",
                        data: "username="+ username,
                        success: function(response){
                            let parse = JSON.parse(response);

                            $(".status").ajaxComplete(function(event, request, settings){
                                if ((/true/i).test(parse.response) === true) {
                                    $(".status").html("<span style=\"color:green\"><b>" + parse.message + "</span>");
                                    $(document).ready(function() {
                                        $("input[type=submit]").removeAttr("disabled");
                                    });
                                } else {

                                    $(".status").html("<span style=\"color:red\"><b>" + parse.message + "</span>");
                                    $(document).ready(function() {
                                        $("input[type=submit]").attr("disabled", "disabled");
                                    });
                                }


                            });
                        },
                        error:  function(data, status, er){
                            alert(data+"_"+status+"_"+er);
                        }
                    });
                }
                else{

                    $(".status").html("<span style=\"color:red\">Username should be <b>3</b> character long.</span>");
                }

            });
        });
    </script>
</head>
<body>
<%@include file="/include/header.inc"%>
<main>
    <jsp:include page="/include/registrationForm.html"/>
</main>
<%@include file="/include/footer.inc"%>
</body>
</html>
