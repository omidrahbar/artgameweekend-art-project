<%@include file="header.jsp" %>

<div class="box">
<h2>View Tags</h2>
</div>

<div id="map" style="padding:0;width: 100%;min-height: 85%;"></div>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=true"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">

    var LAT = 48.862;
    var LNG = 2.3415;
    var ZOOM = 5;

    /**
     * Google map conf
     */
    var MAP_OPTIONS = { zoom: ZOOM, center: new google.maps.LatLng( LAT, LNG ), mapTypeId: google.maps.MapTypeId.SATELLITE };
    var MAP = new google.maps.Map( document.getElementById("map"), MAP_OPTIONS );

    google.maps.event.addListener(MAP, 'click', getTags );
    google.maps.event.addListener(MAP, 'zoom_changed', getTags );
    google.maps.event.addListener(MAP, 'dragend', getTags );

    function centerMap( lat, lng ) {
        MAP.setCenter( new google.maps.LatLng( lat, lng ) );
    }
    var THE_INFO_WINDOW = null;
    var markers = Array();
    function addMarker( id, lat, lng, title, date ) {
        var myLatlng = new google.maps.LatLng(lat, lng);
        var marker = new google.maps.Marker({ position: myLatlng, map: MAP });
        markers.push( marker );
        google.maps.event.addListener(marker, 'click', function ( event ) {
            var content = "ID #" + id + "<br />" + title + "<br />" + "<img src=\"display?id="+id+"\" />"+ date + "<br/><a href=\"delete?id=" + id +"\">Delete</a>";
            var myInfoWindow = new google.maps.InfoWindow( { content: content } );
            if( THE_INFO_WINDOW != null ) { THE_INFO_WINDOW.close(); }
            myInfoWindow.open( MAP, marker );
            THE_INFO_WINDOW = myInfoWindow;
        });
    }
    function flushMap( ) {
        if( THE_INFO_WINDOW != null ) { THE_INFO_WINDOW.close(); }
        for( var i=0; i < markers.length; i++ ) {
            markers[i].setMap(null);
        }
    }
    function getTags( ) {
        getTagsFromWebService( MAP.getCenter().lat(), MAP.getCenter().lng(), MAP.getZoom() );
    }

 
    var URL_GET = "tags";
    function getTagsFromWebService( _lat, _lng, _zoom ) {
        $.get( URL_GET, {lat:_lat, lng:_lng, zoom:_zoom}, function( xml ) {
            flushMap( );
            $(xml).find("tags>tag").each( function() {
                var id= $(this).find("id").text();
                var lat = $(this).find("lat").text();
                var lng = $(this).find("lon").text();
                var title = $(this).find("name").text();
                var date = "11/06/2006";
                addMarker( id, lat, lng, title, date );
            });
        });
    }
    getTagsFromWebService( LAT, LNG, ZOOM );

</script>

<%@include file="footer.jsp" %>

