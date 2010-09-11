<%@include file="header.jsp" %>

<%@page import="org.artags.server.service.LayarParamsService" %>


<%
    LayarParamsService instance = LayarParamsService.instance();
%>
<form action="updateLayarParams" method="post">
    <div style="float:left; width:50%">
        <div class="box">
            <h2>Layar params</h2>
            <label for="distance">Distance : </label><input type="text" name="distance" value="<%= instance.getDistance() %>"/>
            <br/>
            <br/>
            <label for="type">Type : </label><input type="text" name="type" id="type" value="<%= instance.getType() %>"/>
            <br/>
            <br/>
            <label for="dimension">Dimension : </label><input type="text" name="dimension" id="dimension" value="<%= instance.getDimension() %>"/>
            <br/>
            <br/>
            <label for="rel">Rel : </label><input type="text" name="rel" id="rel" value="<%= instance.getRel() %>"/>
            <br/>
            <br/>
            <label for="angle">Angle : </label><input type="text" name="angle" id="angle" value="<%= instance.getAngle() %>"/>
            <br/>
            <br/>
            <label for="scale">Scale : </label><input type="text" name="scale" id="scale" value="<%= instance.getScale() %>"/>
            <br/>
            <br/>
            <input type="submit" value="Update params" />
        </div>
    </div>
</form>

<%@include file="footer.jsp" %>
