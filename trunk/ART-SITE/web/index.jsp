<%-- 
    Document   : index
    Created on : 7 déc. 2010, 22:00:06
    Author     : pierre
--%>
<%@include file="header.jsp" %>

<div id="video">
        <h1>Discover the project</h1>
 <object width="640" height="385">
     <param name="movie" value="http://www.youtube.com/v/RKxOy08POc0?fs=1&amp;hl=fr_FR"></param>
     <param name="allowFullScreen" value="true"></param>
     <param name="allowscriptaccess" value="always"></param>
     <embed src="http://www.youtube.com/v/RKxOy08POc0?fs=1&amp;hl=fr_FR" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="640" height="385">

     </embed></object>
</div>

<div id="right">
    <h1>Tag galleries</h1>
    <p><a href="best.jsp">Best tags</a></p>
    <p><a href="latest.jsp">Latest tags</a></p>
    
    
    <h1>Tags over <br/>the world</h1>
    <a href="map.jsp">
        <img src="images/map.png" alt="map" />
    </a>
    
</div>

<%@include file="footer.jsp" %>
