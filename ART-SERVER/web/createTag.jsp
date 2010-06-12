<%--

--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Tag</title>
    </head>
    <body>
        <h1>Create a Tag</h1>
        <form action="upload" method="post" enctype="multipart/form-data">
            <div style="float:left; width:45%">
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
            <div id="gmap" style="float:right; width:45%; height:300px; border:1px solid black;"></div>
            <div style="clear:both;"></div>
        </form>

        <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=true"></script>
        <script src="http://code.jquery.com/jquery-latest.js" type="text/javascript"></script>
        <script type="text/javascript" src="js/gmap.js"></script>

    </body>
</html>
