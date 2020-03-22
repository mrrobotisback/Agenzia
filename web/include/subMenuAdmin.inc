<style>
    .admin {
        background-color: #FFFFFF;
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
        margin-left: 200px; /* Same as the width of the sidenav */
    }

    @media screen and (max-height: 450px) {
        .sidenav {padding-top: 15px;}
        .sidenav a {font-size: 18px;}
    }
</style>
