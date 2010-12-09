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
<%@page import="java.util.List" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@include file="header.jsp" %>
<div class="intro">
            <div class="items">

                <ul>
                    <%
                                List<Tag> list = TagService.instance().getLatestTags();
                                list = list.subList(0, 100);
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
<%@include file="footer.jsp" %>
