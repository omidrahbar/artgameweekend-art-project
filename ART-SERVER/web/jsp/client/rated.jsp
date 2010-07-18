<%-- 
    Document   : rated
    Created on : 17 juil. 2010, 01:49:19
    Author     : pierre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="rating" scope="session" class="com.artgameweekend.projects.art.web.RatingJspBean" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thanks for rating this tag!</title>
        <link href="/css/client.css" type="text/css" rel="stylesheet"/>
    </head>
    <body>
        <div class="box">
            <h1>Thanks for rating this tag!</h1>
            <p>
                <%= rating.doRating(request) %>
            </p>
            <p>
                Press the Back button to go back to Layar.
            </p>
        </div>
    </body>
</html>
