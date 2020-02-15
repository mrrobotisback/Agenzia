<meta charset="utf-8"/>

<!-- Linking styles -->
<link rel="stylesheet" href="css/viaggio.css" type="text/css" media="screen">
<link rel="shortcut icon" type="image/x-icon" href="images/favicon/favicon.ico">
<title>Agenzia: <%=menuActiveLink%></title>
<script>
  var applicationMessage;
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