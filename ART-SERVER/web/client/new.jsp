<%--
    Document   : new.jsp
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
    Tag tag = dao.findById( Long.parseLong( tagId ));

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ARTags - New Tag</title>
        <script src="js/jquery/jquery-1.4.2.min.js" type="text/javascript" language="javascript"></script>
        <script src="js/jquery/rating/jquery.rating.pack.js" type="text/javascript" language="javascript"></script>
        <link href="js/jquery/rating/jquery.rating.css" type="text/css" rel="stylesheet"/>
        <link href="css/client.css" type="text/css" rel="stylesheet"/>
        <script type="text/javascript" language="javascript">
         </script>
    </head>
    <body>
        <div class="box">
            <form name="formRating" action="rated.jsp" >
                <input name="id" type="hidden" value="<%= tagId%>" />
                <br/>

                <h1><img class="logo" src="images/icon.png" alt="logo" /> New tag</h1>
                <br/>
                Title : <%= tag.getName() %><br />
                Posted : <%= tag.getFormatedDate( request.getLocale()) %>
                <img src="/thumbnail?id=<%= tag.getKeyThumbnail().getId() %>" alt="thumbnail" width="350"/><br/>
                Location 
                <img src="http://maps.google.com/maps/api/staticmap?zoom=6&size=350x350&markers=<%= tag.getLat() %>,<%= tag.getLon() %>&sensor=false" alt="map" />
                <br />
                <div align="center">
                    Your rating 
                    <input name="rating" type="radio" class="star" value="1"/>
                    <input name="rating" type="radio" class="star" value="2"/>
                    <input name="rating" type="radio" class="star" value="3"/>
                    <input name="rating" type="radio" class="star" value="4"/>
                    <input name="rating" type="radio" class="star" value="5"/>
                </div>

                <br/>
                <br/>
                <input type="submit" value="Send" />
            </form>
        </div>
    </body>
</html>
