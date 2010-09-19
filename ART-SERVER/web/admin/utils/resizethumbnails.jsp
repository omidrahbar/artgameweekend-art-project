<%-- 
    Document   : resizethumbnails
    Created on : 20 sept. 2010, 00:36:03
    Author     : pierre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="resize" scope="request" class="org.artags.server.web.ToolsJspBean" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resize Thumbnails</title>
    </head>
    <body>
        <h1>Resizing thumbnails</h1>
        <%= resize.resizeThumbnail() %>
    </body>
</html>
