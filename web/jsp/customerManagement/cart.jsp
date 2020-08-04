<%@page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="model.session.mo.LoggedUser"%>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    boolean admin = (Boolean) request.getAttribute("admin");
    LoggedUser loggedUser = (LoggedUser) request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Carrello";
    Long userId = (Long)request.getAttribute("userId");
    boolean registration = false;
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="jsLib/jquery.js" type="text/javascript"></script>
    <title>
        Carrello
    </title>
    <%@include file="/include/htmlHead.inc"%>
    <script>
        function start() {
            $.ajax({
                type: "POST",
                url: "Dispatcher?controllerAction=CustomerManagement.getCart",
                data: {userId: <%=userId%>},
                success: function(response)
                {
                    let result = JSON.parse(response);
                    let haves = JSON.parse(result.haves);
                    let cart = JSON.parse(result.cart);
                    let travelDetails = null;
                    let total = 0.00;
                    let content = ' <div class="title">\n' +
                        '    Carrello\n' +
                        ' </div>';
                    for (let i = 0; i < haves.length; i++) {
                        $.ajax({
                            type: "POST",
                            url: "Dispatcher?controllerAction=CustomerManagement.retriveMinimalTravel",
                            async: false,
                            data: {travelCode: haves[i].travelCode},
                            success: function(response)
                            {
                                let result = JSON.parse(response);
                                travelDetails = JSON.parse(result.message);
                            }
                        });
                        content += '<div class="item">';
                        content += '<div class="buttons">';
                        content += '<input class="delete-btn" type="button" value="" onclick="removeItem(' + <%=userId%> + ',' + haves[i].travelCode + ', ' + (haves[i].quantity * travelDetails.price) + ')" />';
                        content += '</div>';
                        content += '<div class="image">';
                        content += '<img src="images/travel.jpeg" alt="" />';
                        content += '</div>';
                        content += '<div class="description">';
                        content += '<span>' + travelDetails.name + '</span>';
                        content += '<span>' + travelDetails.startDate + '</span>';
                        content += '<span>' + travelDetails.duration + '</span>';
                        content += '</div>';
                        content += '<div class="quantity">';
                        content += '<button class="plus-btn" type="button" name="button" onclick="plusItem(' + haves[i].quantity + ', ' + haves[i].travelCode + ', ' +  travelDetails.price + ')">';
                        content += '<img src="images/plus.svg" alt="" />';
                        content += '</button>';
                        content += '<input type="text" name="name" value="' + haves[i].quantity + '">';
                        content += '<button class="minus-btn" type="button" name="button" onclick="minusItem(' + haves[i].quantity + ', ' + haves[i].travelCode + ', ' + travelDetails.price + ')">';
                        content += '<img src="images/minus.svg" alt="" />';
                        content += '</button>';
                        content += '</div>';
                        content += '<div class="total-price">' + (haves[i].quantity * travelDetails.price) + '€</div>';
                        content += '</div>';
                        total += (haves[i].quantity * travelDetails.price);
                    }
                    content += '<div class="summary-cart">\n' +
                        '  <span class="grand-total-label">Totale: </span>\n' +
                        '  <span class="grand-total-value">' + total.toFixed(2) +'€</span>\n' +
                        '  <button class="checkout-button" onclick="window.location.href=\'/Dispatcher?controllerAction=CustomerManagement.checkout\'"> Checkout </button>\n' +
                        '</div>';
                    $(".shopping-cart").html(content);
                },
                error: function()
                {
                    console.log("Chiamata fallita, si prega di riprovare...");
                }
            });
        }

        function minusItem(quantity, travelCode, price) {
            let resultQty = parseInt(quantity);
            resultQty = resultQty - 1;
            let userId = <%= userId%>;

                $.ajax({
                    type: "POST",
                    url: "Dispatcher?controllerAction=CustomerManagement.updateCart",
                    async: false,
                    data: {userId: userId, quantity: resultQty, travelCode: travelCode, price: -price},
                    success: function(response)
                    {
                        start();
                    },
                    error: function()
                    {
                        console.log("Chiamata fallita, si prega di riprovare...");
                    }
            });
        }

        function plusItem(quantity, travelCode, price) {
            console.log("sono dentro plus");
            let resultQty = parseInt(quantity);
            resultQty = resultQty + 1;
            console.log(resultQty, "resultqty");
            let userId = <%= userId%>;

                $.ajax({
                    type: "POST",
                    url: "Dispatcher?controllerAction=CustomerManagement.updateCart",
                    async: false,
                    data: {userId: userId, quantity: resultQty, travelCode: travelCode, price: price},
                    success: function(response)
                    {
                        let result = JSON.parse(response);
                        start();
                    },
                    error: function()
                    {
                        console.log("Chiamata fallita, si prega di riprovare...");
                    }
            });
        }

        $(document).ready(function(){
            start();
        })

        function removeItem(idUser,idItem, partial) {
            $.ajax({
                type: "POST",
                url: "Dispatcher?controllerAction=CustomerManagement.deleteRowCart",
                async: false,
                data: {userId: idUser, travelCode: idItem, partial: -partial},
                success: function(response)
                {
                    let result = JSON.parse(response);
                    start();
                },
                error: function()
                {
                    console.log("Chiamata fallita, si prega di riprovare...");
                }
        });

            start();
        }
    </script>
    <style>
        .shopping-cart {
            background: #FFFFFF;
            box-shadow: 1px 2px 3px 0px rgba(0,0,0,0.10);
            border-radius: 6px;

            display: flex;
            flex-direction: column;
        }

        .title {
            height: 60px;
            border-bottom: 1px solid #E1E8EE;
            padding: 20px 30px;
            color: #5E6977;
            font-size: 18px;
            font-weight: 400;
        }

        .item {
            padding: 20px 30px;
            height: 200px;
            display: flex;
        }

        .item:nth-child(3) { /* TODO */
            border-top:  1px solid #E1E8EE;
            border-bottom:  1px solid #E1E8EE;
        }

        .buttons {
            position: relative;
            padding-top: 30px;
            margin-right: 60px;
        }

        .delete-btn {
            display: inline-block;
            Cursor: pointer;
            width: 18px;
            border: none;
            height: 17px;
            background: url('images/delete-icn.svg') no-repeat center;
        }

        .image {
            margin-right: 50px;
        }

        .description {
            padding-top: 10px;
            margin-right: 60px;
            width: 115px;
        }

        .description span {
            display: block;
            font-size: 14px;
            color: #43484D;
            font-weight: 400;
        }

        .description span:first-child {
            margin-bottom: 5px;
        }
        .description span:last-child {
            font-weight: 300;
            margin-top: 8px;
            color: #86939E;
        }

        .quantity {
            padding-top: 20px;
            margin-right: 60px;
        }
        .quantity input {
            -webkit-appearance: none;
            border: none;
            text-align: center;
            width: 32px;
            font-size: 16px;
            color: #43484D;
            font-weight: 300;
        }

        button[class*=btn] {
            width: 30px;
            height: 30px;
            background-color: #E1E8EE;
            border-radius: 6px;
            border: none;
            cursor: pointer;
        }
        .minus-btn img {
            margin-bottom: 3px;
        }
        .plus-btn img {
            margin-top: 2px;
        }

        button:focus,
        input:focus {
            outline:0;
        }

        .total-price {
            width: 83px;
            padding-top: 27px;
            text-align: center;
            font-size: 16px;
            color: #43484D;
            font-weight: 300;
        }

        @media (max-width: 800px) {
            .shopping-cart {
                width: 100%;
                height: auto;
                overflow: hidden;
            }
            .item {
                height: auto;
                flex-wrap: wrap;
                justify-content: center;
            }
            .image img {
                width: 50%;
            }
            .image,
            .quantity,
            .description {
                width: 100%;
                text-align: center;
                margin: 6px 0;
            }
            .buttons {
                margin-right: 20px;
            }

            .summary-cart{
                margin-top: 20px;
            }
        }

        .summary-cart {
            margin-top: 20px;
            display: flex;
            justify-content: flex-end;
            padding: 40px;

        }

        .checkout-button {
            margin-left: 20px;

            border: none;
            outline: 0;
            padding: 12px;
            margin-top: 5px;
            color: #86939E;
            background-color: #E1E8EE;
            text-align: center;
            cursor: pointer;
            width: 20%;
            font-size: 18px;
        }

        .grand-total-value {
            margin-left: 10px;
            margin-right: 15px;
            margin-top: 19px;
        }

        .grand-total-label {
            margin-top: 19px;
        }

    </style>
</head>
<body>
<%@include file="/include/header.inc"%>
<div class="admin">
    <div class="main">
        <div class="shopping-cart">
        </div>
    </div>
</div>
<div class="footer">
    <%@include file="/include/footer.inc"%>
</div>
</html>