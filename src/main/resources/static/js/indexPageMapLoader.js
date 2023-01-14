function initMap() {

    // TODO: Change center when place is searched
    const center = {
        lat: 37.43238031167444, lng: -122.16795397128632,
    };

    const map = new google.maps.Map(document.getElementById("map"), {
        center: center,
        zoom: 11,
        mapId: "4504f8b37365c3d0",
    });
    // Allows to scroll without pressing CTRL button.
    map.setOptions({scrollwheel: true});

    setSearchLogic(map);
    initMarkers(map);
}

function initMarkers(map) {
    fetch(`api/v1`)
        .then(response => response.json())
        .then(markers => {
            for (let marker of markers) {
                let advancedMarker = new google.maps.marker.AdvancedMarkerView({
                    map,
                    content: setMarkerPrice(marker.priceTag.toFixed(2)),
                    position: {lat: marker.latitude, lng: marker.longitude},
                });

                // Adding zoom event to the marker.
                advancedMarker.addListener('click', function () {
                    // Pan to the marker's position and set the zoom level
                    map.panTo(advancedMarker.position);
                    map.setZoom(14); // TODO: Calculate Map zoom and marker zoom for smooth zoom result.
                });
            }
        });
}

function setMarkerPrice(price) {
    const priceTag = document.createElement("div");
    priceTag.className = "price-tag";
    priceTag.textContent = "$" + price;
    return priceTag;
}


// Fixes form submitting on page reload.
if (window.history.replaceState) {
    window.history.replaceState(null, null, window.location.href);
}

function setSearchLogic(map) {

    const input = document.getElementById("input");
    const autocomplete = new google.maps.places.Autocomplete(input);

    // Bind the map's bounds (viewport) property to the autocomplete object,
    // so that the autocomplete requests use the current map bounds for the
    // bounds option in the request.
    autocomplete.bindTo("bounds", map);

    // Current logic is to search only by cities
    autocomplete.setTypes(["(cities)"]);


    const marker = new google.maps.Marker({
        map,
        anchorPoint: new google.maps.Point(0, -29),
    });

    autocomplete.addListener("place_changed", () => {
        marker.setVisible(false);

        const place = autocomplete.getPlace();

        if (!place.geometry || !place.geometry.location) {
            /* User entered the name of a Place that was not suggested and
            // pressed the Enter key, or the Place Details request failed. */
            window.alert(
                "No details available for input: '" + place.name + "'"
            );
            return;
        }

        // If the place has a geometry, then present it on a map.
        if (place.geometry.viewport) {
            map.fitBounds(place.geometry.viewport);
        } else {
            map.setCenter(place.geometry.location);
            map.setZoom(16);
        }

        marker.setPosition(place.geometry.location);
        marker.setVisible(true);
    });
}


window.initMap = initMap;