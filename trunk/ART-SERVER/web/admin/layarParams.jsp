<%@include file="header.jsp" %>

<%@page import="org.artags.server.web.layar.LayarParamsService" %>


<%
    LayarParamsService instance = LayarParamsService.instance();
%>
<form action="updateLayarParams" method="post">
    <div style="float:left; width:50%">
        <div class="box">
            <h2>Layar params</h2>
<!--            <label for="type">Type : </label><input type="text" name="type" id="type" value="<%= instance.getType() %>"/> 
            Default : <%= instance.DEFAULT_TYPE %>
            <br/>
            <br/>
-->
            <label for="dimension">Dimension : </label><input type="text" name="dimension" id="dimension" value="<%= instance.getDimension() %>" size="5"/> 
            Default : <%= instance.DEFAULT_DIMENSION %>
            <br/>
            <br/>
<!--            <label for="rel">Rel : </label><input type="text" name="rel" id="rel" value="<%= instance.getRel() %>"/>
            <br/>
            <br/>
-->
            <label for="angle">Angle : </label><input type="text" name="angle" id="angle" value="<%= instance.getAngle() %>" size="5" /> 
            Default : <%= instance.DEFAULT_ANGLE %>
            <br/>
            <br/>
            <label for="scale">Scale : </label><input type="text" name="scale" id="scale" value="<%= instance.getScale() %>" size="5"/> 
            Default : <%= instance.DEFAULT_SCALE %>
            <br/>
            <br/>
            <label for="scale">Size : </label><input type="text" name="size" id="size" value="<%= instance.getSize() %>" size="5"/> 
            Default : <%= instance.DEFAULT_SIZE %>
            <br/>
            <br/>
            <input type="submit" value="Update params" />
        </div>
    </div>
</form>

<%@include file="footer.jsp" %>
