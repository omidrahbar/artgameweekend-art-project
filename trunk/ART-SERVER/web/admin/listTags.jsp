<%@page import="com.artgameweekend.projects.art.business.TagDAO" %>
<%@page import="com.artgameweekend.projects.art.business.Tag" %>
<%@page import="java.util.List" %>
<%@include file="header.jsp" %>

<%
            TagDAO dao = new TagDAO();
            List<Tag> list = dao.findAll();
%>

<div class="box">
    <h2>Tag list</h2>
    Number of tag : <%= list.size()%>
</div>

<div class="box">
    <table width="100%">
        <tr>
            <th>Nom</th>
            <th>Date</th>
            <th>Rating</th>
            <th>Latitude</th>
            <th>Longitude</th>
            <th>Preview</th>
            <th>Actions</th>
        </tr>
        <%
                    for (Tag tag : list)
                    {
        %>
        <tr>
            <td><%= tag.getName()%></td>
            <td align="center"><%= tag.getFormatedDate(request.getLocale())%></td>
            <td align="center"><%= tag.getRating()%></td>
            <td align="center"><%= tag.getLat()%></td>
            <td align="center"><%= tag.getLon()%></td>
            <td align="center">
                <a href="/display?id=<%= tag.getId()%>">
                    <img src="/thumbnail?id=<%= tag.getKeyThumbnail().getId()%>" alt="thumbnail" height="64"/></td>
                </a>
            <td><a href="delete?id=<%=tag.getId()%>">delete</a>
        </tr>
        <%
                    }

        %>
    </table>
</div>
<%@include file="footer.jsp" %>
