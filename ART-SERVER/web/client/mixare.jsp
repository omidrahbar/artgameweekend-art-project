<%--
    Document   : tag.jsp
    Created on : 16 juil. 2010, 15:24:00
    Author     : pierre
--%>
<%@page import="org.artags.server.business.TagDAO" %>
<%@page import="org.artags.server.business.Tag" %>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%
    String tagId = request.getParameter("id");
    TagDAO dao = new TagDAO();
    Tag tag = dao.findById(Long.parseLong(tagId));

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ARTags</title>
    </head>
    <body>
        <a href="/display?id=<%= tag.getId()%>"
        <img src="/thumbnail?id=<%= tag.getKeyThumbnail().getId()%>" alt="thumbnail" align="left" hspace="10" border=""0"/>
        </a>
        <br/>
        <br/>
        <b><%= tag.getName()%></b><br/>
        <%= tag.getFormatedDate(request.getLocale())%><br/>
        Rating : <%= tag.getRating()%>
        <br/>
        <br/>
        <br/>
        <i><small>Tap on the thumbnail to display the full image</small></i>
    </body>
</html>
