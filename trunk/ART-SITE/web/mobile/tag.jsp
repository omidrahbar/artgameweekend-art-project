<%--
/* Copyright (c) 2010 ARTags project owners (see http://www.artags.org)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

 Author     : Pierre LEVY
--%>

<%@page import="org.artags.site.business.Tag" %>
<%@page import="org.artags.site.service.TagService" %>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%
            String tagId = request.getParameter("id");
            Tag tag = TagService.instance().getTag(tagId);

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta id="viewport" name="viewport" content="width=400;"/>
        <title>ARTags - View Tag</title>
        <script src="http://art-server.appspot.com/client/js/jquery/jquery-1.4.2.min.js" type="text/javascript" language="javascript"></script>
        <script src="http://art-server.appspot.com/client/js/jquery/rating/jquery.rating.pack.js" type="text/javascript" language="javascript"></script>
        <link href="http://art-server.appspot.com/client/js/jquery/rating/jquery.rating.css" type="text/css" rel="stylesheet"/>

        <link href="css/mobile.css" type="text/css" rel="stylesheet"/>
        <script type="text/javascript" language="javascript">
        </script>
    </head>
    <body>
        <div class="header">
            <a href="index.html" alt="home"
               <img src="images/logo.png" alt="logo" />
            </a>
        </div>
        <div class="intro">
            Title : <strong><%= tag.getName()%></strong><br />
            Posted : <%= tag.getDate()%><br />
            Rating : <%= tag.getRating()%><br />
            Votes : <%= tag.getRatingCount()%><br />
        </div>
        <div class="preview">
            <h2>Preview</h2>
            <img src="/display?id=<%= tag.getId()%>" alt="thumbnail" width="400"/>
        </div>
        <div class="map">
            <h2>Location</h2>
            <img src="http://maps.google.com/maps/api/staticmap?zoom=6&size=400x400&markers=<%= tag.getLatitude()%>,<%= tag.getLongitude()%>&sensor=false" alt="map" />
        </div>
        <div class="rating">
            <br/>
            <br/>
            <h2>Rate this tag</h2>
            <form name="formRating" action="http://art-server.appspot.com/client/rated.jsp" >
                <input name="id" type="hidden" value="<%= tagId%>" />
                <br/>
                <div class="stars">
                    <input name="rating" type="radio" class="star" value="1"/>
                    <input name="rating" type="radio" class="star" value="2"/>
                    <input name="rating" type="radio" class="star" value="3"/>
                    <input name="rating" type="radio" class="star" value="4"/>
                    <input name="rating" type="radio" class="star" value="5"/>
                </div>
                <br/>
                <br/>
                <input class="button" type="submit" value="Send" />
            </form>
        </div>

    </body>
</html>
