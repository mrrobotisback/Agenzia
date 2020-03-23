<style>
    .admin {
        background-color: #FFFFFF;
    }

    .sidenav {
        height: auto;
        width: 100px;
        position: fixed;
        z-index: 1;
        top: auto;
        left: auto;
        border-top:solid 1px #daa999;
        background: linear-gradient(#daa999, #EDD4CC);
        overflow-x: hidden;
    }

    .sidenav a {
        display:block;
        color:#fff;
        width:100px;
        text-align:center;
        font-size:14px;
        text-transform:uppercase;
        padding:4px 0 5px;
        text-decoration:none;
    }

    .sidenav a:hover, .sidenav a:active  {
        border-top:solid 1px #210800;
        background: linear-gradient(#621900, #822100);
    }

    .main {
        margin-left: 100px; /* Same as the width of the sidenav */
        padding: 10px 10px 10px 10px;
    }

    @media screen and (max-height: 450px) {
        .sidenav {padding-top: 15px;}
        .sidenav a {font-size: 18px;}
    }

    .footer {
        margin-top: 9px;
    }
</style>
<div class="admin">
    <div class="sidenav">
        <a href="Dispatcher?controllerAction=AdminManagement.catalog">Catalog</a>
        <a href="Dispatcher?controllerAction=AdminManagement.user">User</a>
        <a href="Dispatcher?controllerAction=AdminManagement.order">Order</a>
        <a href="Dispatcher?controllerAction=AdminManagement.report">Report</a>
    </div>
    <div class="main">
        Benvenuto <%=loggedUser.getFirstname()%> <%=loggedUser.getSurname()%>!<br/>
        Qui puoi inserire e rimuovere nuovi aritcoli, admin e utenti, controllare ordini e report vendite.
        Qui puoi inserire e rimuovere nuovi aritcoli, admin e utenti, controllare ordini e report vendite.
        Qui puoi inserire e rimuovere nuovi aritcoli, admin e utenti, controllare ordini e report vendite.
    </div>
    <div style="clear:both;"/>
</div>