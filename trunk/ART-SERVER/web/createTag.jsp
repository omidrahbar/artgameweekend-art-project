<%@include file="header.jsp" %>

<form action="upload" method="post" enctype="multipart/form-data">
    <div style="float:left; width:50%">
        <div class="box">
            <h2>Create a Tag</h2>
            <label for="name">Name : </label><input type="text" name="name" />
            <br/>
            <br/>
            <label for="lat">Latitude : </label><input type="text" name="lat" id="lat"/>
            <br/>
            <br/>
            <label for="lon">Longitude : </label><input type="text" name="lon" id="lon"/>
            <br/>
            <br/>
            <label for="uploadedfile">Image to upload : </label><input name="uploadedfile" type="file" id="uploadedfile"/><br />
            <br/>
            <br/>
            <input type="submit" value="Create Tag" />
        </div>
    </div>
    <div id="gmap" style="float:right; width:45%; height:300px; border:1px solid black;margin-right:20px;margin-top:20px"></div>
    <div style="clear:both;"></div>
</form>

<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=true"></script>
<script src="http://code.jquery.com/jquery-latest.js" type="text/javascript"></script>
<script type="text/javascript" src="js/gmap.js"></script>

<%@include file="footer.jsp" %>
