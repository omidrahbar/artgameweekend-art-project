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
--%>

<%@page import="org.artags.site.business.Tag" %>
<%@page import="org.artags.site.service.TagService" %>



<%
            String tagId = request.getParameter("id");
            Tag tag = TagService.instance().getTag(tagId);

%>
<%@include file="header.jsp" %>
<div class="inner center">
    <div class="center">

        <div class="intro">
            <h1>Title : <strong><%= tag.getName()%></strong><br /></h1>
            <br/>
            Posted : <%= tag.getDate()%><br />
            Rating : <%= tag.getRating()%><br />
            Votes : <%= tag.getRatingCount()%><br />
        </div>
        <div id="box">

            <div id="preview">
                <img src="/display?id=<%= tag.getId()%>" alt="thumbnail" width="400"/>
            </div>
            <div id="location">
                <h2>Location</h2>
                <img src="http://maps.google.com/maps/api/staticmap?zoom=6&size=400x400&markers=<%= tag.getLatitude()%>,<%= tag.getLongitude()%>&sensor=false" alt="map" />
            </div>
        </div>
    </div>
</div>
<%@include file="footer.jsp" %>
