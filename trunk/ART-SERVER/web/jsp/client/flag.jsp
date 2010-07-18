<%-- 
    Document   : flag
    Created on : 17 juil. 2010, 02:25:40
    Author     : pierre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%
    String tagId = request.getParameter("id");
    String thumbnailId = request.getParameter("id_thumbnail");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Rate this tag</title>
        <link href="/css/client.css" type="text/css" rel="stylesheet"/>
    </head>
    <body>
        <div class="box">
            <form action="flaged.jsp" >
                <input name="id" type="hidden" value="<%= tagId%>" />
                <h1>Flag this tag as inappropriate</h1>
                <img src="/thumbnail?id=<%= thumbnailId %>" alt="thumbnail" />
                <p>
                    If you consider this tag contains some offending purpose, please
                    let us know in order to keep this experience a pleasure for anyone.
                </p>
                <br/>

                <input type="submit" value="Flag this tag" />
            </form>
        </div>
    </body>
</html>
