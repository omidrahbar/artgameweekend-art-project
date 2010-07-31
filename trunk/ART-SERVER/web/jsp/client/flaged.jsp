<%-- 
    Document   : flaged
    Created on : 17 juil. 2010, 02:26:18
    Author     : pierre
--%>

<jsp:useBean id="flag" scope="request" class="com.artgameweekend.projects.art.web.RatingJspBean" />

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thanks for helping us!</title>
        <link href="/css/client.css" type="text/css" rel="stylesheet"/>
    </head>
    <body>
       <div class="box">
            <h1>Thanks for helping us!</h1>
            <p>
                <%= flag.doFlag(request) %>
            </p>
            <p>
                Press the Back button to go back to Layar.
            </p>
        </div>
    </body>
</html>
