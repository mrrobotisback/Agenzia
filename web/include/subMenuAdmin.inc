<style>
    body {
        font-family: "Lato", sans-serif;
    }

    .sidenav {
        height: auto;
        width: auto;
        position: fixed;
        z-index: 1;
        top: auto;
        left: auto;
        border-top:solid 1px #daa999;
        background: linear-gradient(#daa999, #EDD4CC);
        overflow-x: hidden;
    }

    .sidenav a {
        padding: 6px 10px 6px 10px;
        text-decoration: none;
        font-size: 25px;
        color:#fff;
        display: block;
    }

    .sidenav a:hover, .sidenav a:active  {
        border-top:solid 1px #210800;
        background: linear-gradient(#621900, #822100);
    }

    .main {
        margin-left: 200px; /* Same as the width of the sidenav */
    }

    @media screen and (max-height: 450px) {
        .sidenav {padding-top: 15px;}
        .sidenav a {font-size: 18px;}
    }
</style>
<div class="sidenav">
    <a href="#">About</a>
    <a href="#">Services</a>
    <a href="#">Clients</a>
    <a href="#">Contact</a>
</div>
<div class="main">
    Benvenuto <%=loggedUser.getFirstname()%> <%=loggedUser.getSurname()%>!<br/>
    Qui puoi inserire e rimuovere nuovi aritcoli, admin e utenti, controllare ordini e report vendite.
    Qui puoi inserire e rimuovere nuovi aritcoli, admin e utenti, controllare ordini e report vendite.
    Qui puoi inserire e rimuovere nuovi aritcoli, admin e utenti, controllare ordini e report vendite.
</div>
