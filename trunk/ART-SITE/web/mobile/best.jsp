<%--
/* Copyright (c) 2010-2012 ARTags project owners (see http://www.artags.org)
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
--%><%@page import="org.artags.site.business.Tag" %>
<%@page import="org.artags.site.service.TagService" %>
<%@page import="java.util.List" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta id="viewport" name="viewport" content="width=400;"/>
        <title>Best rated tags</title>
        <link href="css/mobile.css" type="text/css" rel="stylesheet"/>
    </head>
    <body>
        <div class="header">
            <a href="index.jsp" alt="home"
            <img src="images/logo.png" alt="logo" />
            </a>
        </div>
        <div class="intro">
            <div class="items">

                <ul>
                    <%
                                List<Tag> list = TagService.instance().getBestRatedTags();
                                for (Tag t : list)
                                {
                    %>
                    <li><a href="tag.jsp?id=<%= t.getId()%>">
                           <img src="/thumbnail?id=<%= t.getThumbnailId()%>" alt="thumbnail" align="left" />
                            <%= t.getName()%><br />
                            <%= t.getRating()%><br />
                            <%= t.getDate()%><br />
                            Votes <%= t.getRatingCount()%><br />
                        </a>
                    </li>
                    <%
                                }

                    %>
                </ul>
            </div>
        </div>
    </body>
</html>
