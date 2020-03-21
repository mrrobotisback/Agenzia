<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Linking styles -->
<link rel="stylesheet" href="css/agenzia.scss" type="text/css" media="screen">
<link rel="shortcut icon" type="image/x-icon" href="images/favicon/favicon.ico">
<title>Agenzia: <%=menuActiveLink%></title>
<script>
  let applicationMessage;
  <%if (applicationMessage != null) {%>
    applicationMessage="<%=applicationMessage%>";
  <%}%>
  function onLoadHandler() {
    headerOnLoadHandler();
    try { mainOnLoadHandler(); } catch (e) {}
    if (applicationMessage!=undefined) alert(applicationMessage);
  }
  window.addEventListener("load", onLoadHandler);
</script> 