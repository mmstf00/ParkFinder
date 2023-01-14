function initConfigurableMap() {

    // TODO: Change center when place is searched
    const center = {
        lat: 37.43238031167444, lng: -122.16795397128632,
    };

    const configurableMap = new google.maps.Map(document.getElementById("map"), {
        center: center, zoom: 11, mapId: "4504f8b37365c3d0",
    });

    // Allows to scroll without pressing CTRL button.
    configurableMap.setOptions({scrollwheel: true});

    // Method is called from indexPageMapLoader.js
    initMarkers(configurableMap);

    let marker = new google.maps.Marker({
        position: {lat: 37.7749, lng: -122.4194},
        map: configurableMap
    });

    marker.setIcon({
        url: "/images/park-icon.png",
        scaledSize: new google.maps.Size(40, 43),
    });

    // Configure the click listener.
    configurableMap.addListener("click", (mapsMouseEvent) => {

        marker.setVisible(false);

        let lat = mapsMouseEvent.latLng.lat();
        let lng = mapsMouseEvent.latLng.lng();
        let position = new google.maps.LatLng(lat, lng);
        marker.setPosition(position);

        marker.setVisible(true);

        // Sets the lat and lng of input form
        setFormData(mapsMouseEvent);
    });
}

function setFormData(mapsMouseEvent) {

    let lat = mapsMouseEvent.latLng.lat();
    let lng = mapsMouseEvent.latLng.lng();

    getAddressData(lat, lng).then(data => {
        document.getElementById('address-input').value = data.results[0].formatted_address;
    });

    // These values are setting hidden inputs, lat and lng are hidden from user.
    document.getElementById("lat-input").value = lat;
    document.getElementById("lng-input").value = lng;
}

function getAddressData(lat, lng) {
    return fetch(`https://maps.googleapis.com/maps/api/geocode/json?latlng=${lat},${lng}&key=AIzaSyBPBjwslCsB5pffDskDq-2EfEgzJec_UqI`)
        .then(response => response.json());
}

window.initMap = initConfigurableMap;