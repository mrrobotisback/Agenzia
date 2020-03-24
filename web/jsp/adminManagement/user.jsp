<%@page session="false"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" pageEncoding="UTF-8"%>
<%@page import="model.session.mo.LoggedUser"%>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    boolean admin = (Boolean) request.getAttribute("admin");
    LoggedUser loggedUser = (LoggedUser) request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Gestione";
    boolean registration = false;
%>

<!DOCTYPE html>
<html>
<head>
    <%@include file="/include/htmlHead.inc"%>
</head>
<body>
<%@include file="/include/header.inc"%>
<div class="admin">
    <div class="sidenav">
        <a href="Dispatcher?controllerAction=AdminManagement.catalog">Catalog</a>
        <a href="Dispatcher?controllerAction=AdminManagement.user">User</a>
        <a href="Dispatcher?controllerAction=AdminManagement.order">Order</a>
        <a href="Dispatcher?controllerAction=AdminManagement.report">Report</a>
    </div>
    <div class="main">
        Benvenuto <%=loggedUser.getFirstname()%> <%=loggedUser.getSurname()%>!<br/>
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque dapibus erat ac justo finibus, id tempus nulla sodales. Nullam lacinia posuere risus, a congue massa lacinia maximus. Phasellus ut mi eleifend, condimentum eros a, tincidunt sapien. Quisque laoreet tempus eros nec tristique. Cras et sagittis massa, vel pellentesque augue. Aenean congue tempor ligula, id rutrum velit. Donec sagittis eros mi, ac sagittis urna blandit non. Cras sollicitudin aliquam nulla ut pharetra. Aenean ligula est, congue quis velit non, posuere pulvinar risus. Aliquam tristique ante eu nulla pulvinar, tincidunt mattis lacus sollicitudin. Suspendisse tempor tempor nulla quis facilisis. Phasellus posuere leo quis fringilla viverra. Suspendisse ullamcorper, neque vel egestas lacinia, est est luctus tortor, non egestas nisi arcu vitae elit. Sed vehicula lorem et mattis porttitor. Etiam vel dui at diam facilisis rutrum. Nulla vestibulum lacinia diam sit amet tincidunt.

        Nullam vel quam id odio pulvinar egestas quis in ipsum. Quisque et tellus ultricies, semper lacus non, convallis arcu. Pellentesque sed lacus posuere leo molestie porttitor. Vestibulum iaculis, risus at vulputate condimentum, nisl neque commodo tortor, quis venenatis metus mi non dolor. Sed sodales metus sed dui mattis, at rhoncus purus eleifend. Maecenas blandit imperdiet volutpat. Praesent aliquet, arcu eget commodo sollicitudin, orci dolor pharetra leo, eget interdum mauris nibh et urna. Integer posuere turpis nisi, vitae porttitor purus vehicula eget. Donec porta feugiat nunc, vel malesuada massa facilisis a. Pellentesque in urna sit amet risus tempus accumsan eget ut libero. Integer consequat commodo aliquet. Curabitur vel lectus sit amet eros rhoncus scelerisque. Curabitur ut ligula tincidunt, venenatis erat quis, condimentum nisl.

        Sed eleifend a nunc et pretium. Nulla ullamcorper diam tortor. Curabitur et odio diam. Nullam lectus tellus, sagittis sed malesuada ac, volutpat non justo. Praesent leo ipsum, volutpat id condimentum nec, pellentesque eu elit. Pellentesque tempor neque in orci lobortis, nec lacinia eros placerat. Sed commodo in neque ut placerat.

        Integer et consequat arcu, vitae elementum nunc. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nunc imperdiet nulla id nisi fringilla, a volutpat dui tempus. Curabitur risus lectus, vulputate eu neque non, blandit lobortis tortor. Nulla dignissim lacinia malesuada. Aliquam mi enim, fermentum id arcu sit amet, sollicitudin porta leo. Quisque vestibulum eget nisl vitae pellentesque. Donec porttitor fringilla nisl in dignissim. Sed et augue vitae eros gravida laoreet. Suspendisse nec diam faucibus, faucibus mauris quis, pulvinar mauris.

        Aliquam hendrerit tellus non accumsan euismod. Praesent non sapien congue, tincidunt sem in, pulvinar magna. Vivamus tempus tortor enim, in elementum risus varius ut. Nunc feugiat ligula ante, posuere fermentum ex mollis vel. Nam finibus varius libero non cursus. Maecenas et risus libero. Nam a molestie eros.
    </div>
</div>
<div class="footer">
    <%@include file="/include/footer.inc"%>
</div>
</html>