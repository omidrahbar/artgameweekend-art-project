<%@page import="org.artags.server.web.Security" %>
<%@include file="header.jsp" %>

<div class="box">
    <h2>ARTags Server - Administration Home</h2>
<ul>
    <li>
        <a href="listTags.jsp">Tag list</a>
    </li>
    <li>
        <a href="viewTags.jsp">View tags on the map</a>
    </li>
    <li>
        <a href="createTag.jsp">Create a tag from this web interface</a>
    </li>
    <li>
        <a href="/tags">Get tags (XML format)</a>
    </li>
    <li>
        <a href="/layar?developerId=<%=Security.LAYAR_DEVELOPER_ID%>">Layar </a>
    </li>
    <li>
        <a href="layarParams.jsp">Layar params</a>
    </li>
    <li>
        <a href="http://publishing.layar.com/publishing/test/artags" target="Layar">Test at Layar</a>
    </li>
    <li>
        <a href="/arml">ARML (Wikitude)</a>
    </li>
    <li>
        <a href="/junaio/pois/search/?adminKey=<%=Security.ADMIN_KEY%>">Junaio </a>
    </li>
    <li>
        <a href="http://www.junaio.com/publisher/mychannels" target="Junaio">Test at Junaio</a>
    </li>
    <li>
        <a href="/mixare?latitude=48.82701346168149&longitude=2.361450568455775&altitude=106.0&radius=20.75" target="Mixare">Mixare</a>
    </li>
    <li>
        <a href="kml">KML Export</a>
    </li>
</ul>
</div>

<%@include file="footer.jsp" %>
