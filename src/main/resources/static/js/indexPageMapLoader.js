function initMap() {

    // TODO: Change center when place is searched
    const center = {
        lat: 37.43238031167444,
        lng: -122.16795397128632,
    };

    const map = new google.maps.Map(document.getElementById("map"), {
        center: center,
        zoom: 11,
        mapId: "4504f8b37365c3d0",
    });
    // Allows to scroll without pressing CTRL button.
    map.setOptions({scrollwheel: true});

    initMarkers(map);
}

function initMarkers(map) {
    for (let marker of markers) {
        let advancedMarker = new google.maps.marker.AdvancedMarkerView({
            map,
            content: setMarkerPrice(marker.price),
            position: marker.position,
        });

        // Adding zoom event to the marker.
        advancedMarker.addListener('click', function () {
            // Pan to the marker's position and set the zoom level
            map.panTo(advancedMarker.position);
            map.setZoom(14); // TODO: Calculate Map zoom and marker zoom for smooth zoom result.
        });
    }
}

function setMarkerPrice(price) {
    const priceTag = document.createElement("div");
    priceTag.className = "price-tag";
    priceTag.textContent = price;
    return priceTag;
}

const markers = [
    {
        address: "215 Emily St, MountainView, CA",
        price: "$3.80",
        position: {
            lat: 37.50024109655184,
            lng: -122.28528451834352,
        },
    },
    {
        address: "108 Squirrel Ln &#128063;, Menlo Park, CA",
        price: "$6.40",
        position: {
            lat: 37.44440882321596,
            lng: -122.2160620727,
        },
    },
    {
        address: "100 Chris St, Portola Valley, CA",
        price: "$3.10",
        position: {
            lat: 37.39561833718522,
            lng: -122.21855116258479,
        },
    },
    {
        address: "98 Aleh Ave, Palo Alto, CA",
        price: "$4.20",
        position: {
            lat: 37.423928529779644,
            lng: -122.1087629822001,
        },
    },
    {
        address: "2117 Su St, MountainView, CA",
        price: "$14.00",
        position: {
            lat: 37.40578635332598,
            lng: -122.15043378466069,
        },
    },
    {
        address: "197 Alicia Dr, Santa Clara, CA",
        price: "$6.00",
        position: {
            lat: 37.36399747905774,
            lng: -122.10465384268522,
        },
    },
    {
        address: "700 Jose Ave, Sunnyvale, CA",
        price: "$3.00",
        position: {
            lat: 37.38343706184458,
            lng: -122.02340436985183,
        },
    },
    {
        address: "868 Will Ct, Cupertino, CA",
        price: "$10.00",
        position: {
            lat: 37.34576403052,
            lng: -122.04455090047453,
        },
    },
    {
        address: "655 Haylee St, Santa Clara, CA",
        price: "$5.00",
        position: {
            lat: 37.362863347890716,
            lng: -121.97802139023555,
        },
    },
    {
        address: "2019 Natasha Dr, San Jose, CA",
        price: "$5.00",
        position: {
            lat: 37.41391636421949,
            lng: -121.94592071575907,
        },
    },
];

window.initMap = initMap;