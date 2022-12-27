function initMap() {
    const map = new google.maps.Map(document.getElementById("map"), {
        center: {lat: 37.42, lng: -122.1},
        zoom: 14,
        mapId: "4504f8b37365c3d0",
    });
    // Allows to scroll without pressing CTRL button.
    map.setOptions({scrollwheel: true});

    const priceTag = document.createElement("div");
    priceTag.className = "price-tag";
    priceTag.textContent = "$2.5";

    const markerView = new google.maps.marker.AdvancedMarkerView({
        map,
        position: {lat: 37.42, lng: -122.1},
        content: priceTag,
    });
}

window.initMap = initMap;