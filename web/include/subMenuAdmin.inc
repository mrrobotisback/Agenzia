<style>
    .admin {
        background-color: #FFFFFF;
        display: flex;
        flex-wrap: nowrap;
    }

    .sidenav {
        width: 100px;
        position: sticky;
        z-index: 1;
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
        padding: 10px 10px 10px 10px;
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
    </div>
</div>