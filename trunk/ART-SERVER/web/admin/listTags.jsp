<%@page import="org.artags.server.business.Tag" %>
<%@page import="org.artags.server.service.TagService" %>
<%@page import="java.util.List" %>
<%@include file="header.jsp" %>

<%
            String startParam = request.getParameter("start");
            int start = (startParam != null) ? Integer.parseInt(startParam) : 0;
            String countParam = request.getParameter("count");
            int count = (countParam != null) ? Integer.parseInt(countParam) : 3;
            List<Tag> list = TagService.getTagsByCriteria();
            int end = start + count;
            int max = (end > list.size()) ? list.size() : end;
            List<Tag> sublist = list.subList(start, max);

%>

<div class="box">
    <h2>Tag list</h2>
    <table width="100%">
        <tr>
            <td width="33%">
                <form action="">
                    <input type="hidden" name="start" value="0" />
                    Items per page : <select name="count">
                        <option>10</option>
                        <option>50</option>
                        <option>100</option>
                    </select>
                    <input type="submit" />
                </form><br />
                Sort order : More recent tags first
            </td>
            <td width="33%" align="center">         

                Page(s) :
                <%
                            int pagecount = 1 + list.size() / count;
                            int pagecurrent = start / count;
                            for (int i = 0; i < pagecount; i++)
                            {
                                if (i == pagecurrent)
                                {
                                    out.print("<b>" + (i + 1) + "</b> &nbsp;");
                                } else
                                {
                                    out.print("<a href=\"?start=" + (i * count) + "&count=" + count + "\">" + (i + 1) + "</a> &nbsp;");
                                }
                            }
                %><br />
            </td>
            <td width="33%" align="right">
                Number of tags : <b><%= list.size()%></b>
            </td>
        </tr>
    </table>

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
                    for (Tag tag : sublist)
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
