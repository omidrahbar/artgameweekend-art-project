<%-- 
    Document   : convert
    Created on : 4 juil. 2010, 02:34:48
    Author     : pierre
--%>

<%@page import="com.artgameweekend.projects.art.business.Tag" %>
<%@page import="com.artgameweekend.projects.art.business.Tag2" %>
<%@page import="com.artgameweekend.projects.art.business.TagDAO" %>
<%@page import="com.artgameweekend.projects.art.business.Tag2DAO" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Converting tags</h1>
        <%
        TagDAO dao = new TagDAO();
        Tag2DAO dao2 = new Tag2DAO();
        for ( Tag tag : dao.findAll())
        {
            Tag2 tag2 = new Tag2( tag );
            dao2.create(tag2);
            %>
            <p><%= tag.getName() %></p>
            <%
        }
        %>
    </body>
</html>
