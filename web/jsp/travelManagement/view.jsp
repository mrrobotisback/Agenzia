<%@page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="model.session.mo.LoggedUser"%>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    boolean admin = (Boolean) request.getAttribute("admin");
    LoggedUser loggedUser = (LoggedUser) request.getAttribute("loggedUser");
    Long idLoggedUser = null;
    if (loggedOn) {
        idLoggedUser = loggedUser.getUserId();
    }
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Agenzia";
    boolean registration = false;
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="jsLib/jquery.js" type="text/javascript"></script>
    <title>
        Catalogo
    </title>
    <%@include file="/include/htmlHead.inc"%>
    <script>
        let historyLevel = 0;
        let historyCat = '';
        let lastId = null;

        function product(idCat, idButton) {
            historyCat = idCat;
            if (lastId != null) {
                document.getElementById(lastId).className = "no-active";
            }
            lastId = idButton;
            document.getElementById(idButton).className = "active-catalog";

            $.ajax({
                url: 'Dispatcher?helperAction=Data.searchTravel',
                type: 'POST',
                data: { field: 'category_id', value: idCat },
                success:function(response){
                    response = JSON.parse(response);
                    let message = JSON.parse(response.message);
                    let content = '<div class="row">';
                    for (let i = 0; i < message.length; i++) {
                        content += '<div class="column">';
                        content += '<div class="card">';
                        content += '<img src="images/travel.jpeg" alt="Travel" style="width:100%">';
                        content += '<h1>' + message[i].name + '</h1>';
                        content += '<p class="price">' + message[i].price + '€</p>';
                        content += '<p><button onclick=productDetails(' + message[i].id + ') >Maggiori dettagli</button></p>';
                        content += '<p><button onclick=addToCart(' + message[i].id + ',' + message[i].price + ') >Acquista</button></p>';
                        content += '</div>';
                        content += '</div>';
                    }
                    content += '</div>';
                    if (message.length > 0) {
                        $('.travel-from-category').html(content);
                    } else {
                        $('.travel-from-category').html('Non ci sono viaggi per questa categoria');
                    }
                }
            });

        }

        function productDetails (idTravel) {
            historyLevel = 1;
            console.log(historyLevel, "historyLevel");
            if (historyLevel === 1) {

                window.history.pushState('forward', null, './#forward');

                $(window).on('popstate', function() {
                    product(historyCat, "Cat" + historyCat);
                });

                historyLevel = 0;
            }

            $.ajax({
                url: 'Dispatcher?helperAction=Data.searchTravel',
                type: 'POST',
                data: { field: 'id', value: idTravel },
                success:function(response){
                    response = JSON.parse(response);
                    let message = JSON.parse(response.message);
                    console.log(message, "message travel");
                    let content = '<div class="product-details-short">';
                    for (let i = 0; i < message.length; i++) {
                        content += '<img class="product-page-img" src="images/travel.jpeg" alt="Travel">';
                        content += '<div class="product-page-info-short">';
                        content += '<h1 class="short-details-title">' + message[i].name + '</h1>';
                        content += '<p class="short-details"> Durata: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + message[i].duration + '</p>';
                        content += '<p class="short-details"> Numero mezzi: &nbsp;&nbsp;&nbsp;&nbsp;' + message[i].means + '</p>';
                        content += '<p class="short-details"> Destinazione: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + message[i].destination + '</p>';
                        content += '<p class="short-details"> Posti disponibili: &nbsp;&nbsp;' + message[i].seatsAvailable + '</p>';
                        content += '<p class="short-details"> Data partenza: &nbsp;&nbsp;&nbsp;&nbsp;' + message[i].startDate + '</p>';
                        content += '<p class="short-details"> Ora partenza: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + message[i].startHour + '</p>';
                        content += '<p class="short-details"> Luogo partenza: &nbsp;&nbsp;&nbsp;' + message[i].startPlace + '</p>';
                        content += '<p class="price short-details-price">' + message[i].price + '€</p>';
                        content += '<p><button class="short-details-button" onclick=addToCart(' + idTravel + ',' + message[i].price + ') >Aggiungi al carrello</button></p>';
                        content += '</div>';
                        content += '</div>';
                        content += '<div class="product-details-long">';
                        content += '<p>' + message[i].description +  '</p>';
                        content += '</div>';
                    }
                    content += '</div>';
                    if (message.length > 0) {
                        $('.travel-from-category').html(content);
                    } else {
                        $('.travel-from-category').html('Non ci sono viaggi per questa categoria');
                    }
                }
            });
        }

        function addToCart (idProduct, price) {
            let userId = <%= idLoggedUser %> ;

            $.ajax({
                type: "POST",
                url: "Dispatcher?controllerAction=CustomerManagement.insertCart",
                data: { userId: userId, productId: idProduct, price: price },
                success: function(response)
                {
                    console.log(response,"response");
                    let result = JSON.parse(response);
                    let message = JSON.parse(result.message);
                    // let content = '';
                    // for (let i = 0; i < message.length; i++) {
                    //     content += '<button id="Cat' + message[i].id + '" ' + 'class="no-active" onclick=product(' + message[i].id + ',' + '"Cat' + message[i].id + '") >' + message[i].name + '</button>';
                    // }
                    $(".sidenav").html('we are champion');
                },
                error: function(response)
                {
                    console.log("Chiamata fallita, si prega di riprovare...", response);
                }
            });
        }

        $(document).ready(function(){
            $.ajax({
                type: "POST",
                url: "Dispatcher?helperAction=Data.allCategory",
                success: function(response)
                {
                    let result = JSON.parse(response);
                    let message = JSON.parse(result.message);
                    let content = '';
                    for (let i = 0; i < message.length; i++) {
                        content += '<button id="Cat' + message[i].id + '" ' + 'class="no-active" onclick=product(' + message[i].id + ',' + '"Cat' + message[i].id + '") >' + message[i].name + '</button>';
                    }
                    $(".sidenav").html(content);
                },
                error: function()
                {
                    console.log("Chiamata fallita, si prega di riprovare...");
                }
            });
        })
    </script>
    <style>

        .column {
            float: left;
            width: 25%;
            padding: 0 10px;
        }

        .row {margin: 0 -5px;}

        .row:after {
            content: "";
            display: table;
            clear: both;
        }

        .card {
            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2); /* this adds the "card" effect */
            padding: 16px;
            text-align: center;
            background-color: #f1f1f1;
        }

        .price {
            color: grey;
            font-size: 22px;
        }

        .card button {
            border: none;
            outline: 0;
            padding: 12px;
            margin-top: 5px;
            color: white;
            background-color: #000;
            text-align: center;
            cursor: pointer;
            width: 100%;
            font-size: 18px;
        }

        .card button:hover {
            opacity: 0.7;
        }

        .active-catalog {
            border-top: solid 1px #fff;
            background: linear-gradient(#621900, #822100);
            display:block;
            color:#fff;
            width:100px;
            text-align:center;
            font-size: 13px;
            text-transform:uppercase;
            padding:4px 0 5px;
            text-decoration:none;
        }

        table {
            display: block;
            overflow-x: auto;
            white-space: nowrap;
            width: 800px;
        }

        .catalog {
            background-color: #FFFFFF;
            display: flex;
        }
        .main {
            flex: 85;
            padding: 10px 10px 10px 10px;
        }

        .no-active {
            display:block;
            background: linear-gradient(#daa999, #EDD4CC);
            color:#fff;
            width:100px;
            border: none;
            text-align:center;
            font-size:14px;
            text-transform:uppercase;
            padding:4px 0 5px;
            text-decoration:none;
        }


        .sidenav button:hover, .sidenav button:active {
            border-top:solid 1px #210800;
            background: linear-gradient(#621900, #822100);
        }

        .product-page-img {
            float:left;
            width: 300px;
            height: 300px;
            margin-right: 5px;
        }

        .product-page-info-short {
            float:left;
            width: 550px;
            height: 300px;
        }

        .short-details-title {
            text-align: center;
        }

        .short-details-price {
            text-align: center;
        }

        .short-details-button  {
            border: none;
            outline: 0;
            padding: 12px;
            margin-top: 5px;
            color: white;
            background-color: #000;
            text-align: center;
            cursor: pointer;
            width: 100%;
            font-size: 18px;
        }

        .short-details-button button:hover {
            opacity: 0.7;
        }

        .short-details {

        }

        .product-details-short {
            height: 300px;
        }

        .product-details-long {
            margin-top: 20px;
        }

    </style>
</head>
<body>
<%@include file="/include/header.inc"%>
<div class="catalog">
    <div class="sidenav">
    </div>
    <div class="main">
        <div class="travel-from-category">
            Seleziona una categoria per visualizzare i viaggi disponibili
        </div>
    </div>
</div>
<div class="footer">
    <%@include file="/include/footer.inc"%>
</div>
</html>