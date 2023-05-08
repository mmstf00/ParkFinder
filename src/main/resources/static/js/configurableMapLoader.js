function initConfigurableMap() {

    // Dummy center if current location is not enabled.
    const center = {
        lat: 43.835571, lng: 25.965654,
    };

    const configurableMap = new google.maps.Map(document.getElementById("map"), {
        center: center, zoom: 11, mapId: "4504f8b37365c3d0",
    });

    // Allows to scroll without pressing CTRL button.
    configurableMap.setOptions({scrollwheel: true});

    // Method is called from indexPageMapLoader.js
    populateAllMarkers(configurableMap);
    getCurrentLocation(configurableMap);

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
        document.getElementById("place-id").value = data.results[0].place_id;
    }).catch(error => console.error(error));

    // These values are setting hidden, they are not needed for the user.
    document.getElementById("lat-input").value = lat;
    document.getElementById("lng-input").value = lng;
}

function getAddressData(lat, lng) {
    return fetch(`https://maps.googleapis.com/maps/api/geocode/json?latlng=${lat},${lng}&key=AIzaSyCTtV6EOMg0cshyQe2h6G_UYUUOlx8Kc5g`)
        .then(response => response.json());
}

window.initMap = initConfigurableMap;