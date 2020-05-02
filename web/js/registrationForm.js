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

$(document).ready(function(){
    $("#username").change(function(){
        let username = $(this).val();
        if(username.length >= 3){
            console.log("Dentro > 3");
            $(".status").html("<img src='images/loading.gif'><span style=\"color:grey\"> Checking availability...</span>");
            $.ajax({
                type: "POST",
                url: "Dispatcher?helperAction=Data.checkUsername",
                data: "username="+ username,
                success: function(response){
                    let parse = JSON.parse(response);
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

function setLabel(id) {
    document.getElementById(id).classList.add('active');
}