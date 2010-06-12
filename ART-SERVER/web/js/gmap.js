
$(document).ready(function () 
{
	var x = 48.857572;
	var y = 2.351675;
	var ZOOM = 14;
	var X = document.getElementById("lat");
	var Y = document.getElementById("lon");

	if( X.value.length > 0 )
		x = eval(X.value.replace(",","."));
	else
		X.value = x;

	if( Y.value.length > 0 )
		y = eval(Y.value.replace(",","."));
	else
		Y.value = y;

	var latlng = new google.maps.LatLng( x, y );
	var myOptions = { zoom: ZOOM, center: latlng, mapTypeId: google.maps.MapTypeId.ROADMAP };
	var map = new google.maps.Map( document.getElementById("gmap"), myOptions );
	var marker = new google.maps.Marker({ map: map, position: latlng });
	google.maps.event.addListener(map, 'click', function(event) {
		X.value = event.latLng.lat();
		Y.value = event.latLng.lng();
	});
	var address;
	$(".form-label").each(function () {
		if( $(this).text().indexOf("dress") > 0 )
		{
			var block = $(this).parent();
			var button = document.createElement("span");
			button.innerHTML = "Gmap me?";
			block.append(button);
			$(button).click(function () {
				address = block.find("input, textarea").val();
				geocoder = new google.maps.Geocoder();
				geocoder.geocode( { 'address': address}, function(results, status) {
					if (status == google.maps.GeocoderStatus.OK) {
						var latlng = results[0].geometry.location;
						map.setCenter(latlng);
						var marker = new google.maps.Marker({ map: map, position: latlng });
						X.value = latlng.lat();
						Y.value = latlng.lng();
					} else {
						alert("Geocode was not successful for the following reason: " + status);
					}
				});

			});
		}
	});
});
