function saveUser(){
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
}

function submitRowAsForm(idRow) {
    console.log(document.getElementById (idRow ).innerText, "value");
    let value = document.getElementById (idRow ).innerText;

    $.ajax({
        headers: {
            Accept: "application/json; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
        type: "POST",
        url: "Dispatcher?helperAction=Data.checkUsername",
        data: idRow + "="+ value,
        success: function(response){
            let parse = JSON.parse(response);
            alert("Aggiornato " + idRow.charAt(0).toUpperCase() + idRow.slice(1) + "Con valore: " + value);
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
