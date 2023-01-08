function initConfigurableMap() {

    // TODO: Change center when place is searched
    const center = {
        lat: 37.43238031167444,
        lng: -122.16795397128632,
    };

    const configurableMap = new google.maps.Map(document.getElementById("map"), {
        center: center,
        zoom: 11,
        mapId: "4504f8b37365c3d0",
    });

    // Allows to scroll without pressing CTRL button.
    configurableMap.setOptions({scrollwheel: true});

    // Method is called from indexPageMapLoader.js
    initMarkers(configurableMap);

    // Create the initial InfoWindow.
    let infoWindow = new google.maps.InfoWindow({
        content: "Click the map to get Lat/Lng!",
        position: center,
    });

    infoWindow.open(configurableMap);
    // Configure the click listener.
    configurableMap.addListener("click", (mapsMouseEvent) => {
        // Close the current InfoWindow.
        infoWindow.close();
        // Create a new InfoWindow.
        infoWindow = new google.maps.InfoWindow({
            position: mapsMouseEvent.latLng,
        });
        infoWindow.setContent(
            JSON.stringify(mapsMouseEvent.latLng.toJSON(), null, 2)
        );
        // Sets the lat and lng of input form
        setFormData(mapsMouseEvent);
        infoWindow.open(configurableMap);
    });
}

function setFormData(mapsMouseEvent) {

    let lat = mapsMouseEvent.latLng.lat();
    let lng = mapsMouseEvent.latLng.lng();

    fetch(`https://maps.googleapis.com/maps/api/geocode/json?latlng=${lat},${lng}&key=AIzaSyBPBjwslCsB5pffDskDq-2EfEgzJec_UqI`)
        .then(response => response.json())
        .then(data => {
            let address = data.results[0].formatted_address;
            document.getElementById('address-input').value = address;
        });


    //document.getElementById('address-input').value = address;
    document.getElementById("lat-input").value = lat;
    document.getElementById("lng-input").value = lng;
}

window.initMap = initConfigurableMap;