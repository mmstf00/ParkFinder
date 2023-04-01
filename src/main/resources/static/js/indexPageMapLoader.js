function initMap() {

    // Dummy center if current location is not enabled.
    let center = {
        lat: 43.835571, lng: 25.965654,
    };

    let map = new google.maps.Map(document.getElementById("map"), {
        center: center,
        zoom: 12,
        mapId: "4504f8b37365c3d0",
    });
    // Allows to scroll without pressing CTRL button.
    map.setOptions({scrollwheel: true});

    getCurrentLocation(map);
    setSearchLogic(map);
    populateAllMarkers(map);
    updateMarkersOnSearch(map);
}

// Getting all data from certain endpoint.
function populateAllMarkers(map) {
    let endpoint = "api/v1";
    populateMarkersFromEndpointIntoMap(map, endpoint);
    loadParkListItems(endpoint);
}

// Updating markers when search is performed.
function updateMarkersOnSearch(map) {
    document.getElementById("search-form").addEventListener("submit", function (event) {
        event.preventDefault();
        const form = event.target;
        const dateFrom = form.elements.dateFrom.value;
        const dateTo = form.elements.dateTo.value;
        const endpoint = `api/v1/search?dateFrom=${dateFrom}&dateTo=${dateTo}`;
        clearMap();
        populateMarkersFromEndpointIntoMap(map, endpoint);
        loadParkListItems(endpoint);
    });
}

function clearMap() {
    let divsToRemove = document.querySelectorAll(".yNHHyP-marker-view");
    for (const div of divsToRemove) {
        div.style.display = "none";
    }
}

function loadParkListItems(endpoint) {
    fetch(endpoint)
        .then(response => response.json())
        .then(data => {
            loadAllParkings(data);
        });
}

function loadAllParkings(data) {
    // Getting the reference to the parent element where we will add the new elements.
    let parksList = document.querySelector('#parks-list');
    if (parksList) {
        parksList.innerHTML = ""; // Clearing older elements.
    }

    let filteredData = filterMarkersWithinRadius(data);

    showMessageWhenNoParkingsFound(filteredData, parksList);

    filteredData.forEach(markerData => {
        let parkingSpace = loadParking(markerData);
        let parkingSpaceInformation = loadDetailsForParking(markerData);

        // To prevent errors in configuration page, checking if parksList exists.
        if (parksList) {
            // Append the element to the parent
            parksList.appendChild(parkingSpace);
            parksList.appendChild(parkingSpaceInformation);

            listenForItemClick();
        }
    });

    // To prevent ReferenceError in Admin page
    if (parksList != null) {
        redirectToReservationPage();
    }
}

function showMessageWhenNoParkingsFound(filteredData, parksList) {
    if (filteredData.length === 0) {
        let emptyParkingList = document.createElement('div');
        emptyParkingList.classList.add('parking-space-class');
        emptyParkingList.id = 'parking-space';
        emptyParkingList.style.backgroundColor = "#e8f0fb";
        emptyParkingList.style.color = "#4a90e2";
        emptyParkingList.style.border = "1px solid";
        emptyParkingList.innerHTML = `
            <div class="parking-details empty-parkings-list">
              <span>
                  We couldn't find any matches for your search criteria. 
                  All spaces might be fully booked or no listings available 
                  in the searched area yet.
               </span>
            </div>
        `;
        parksList.appendChild(emptyParkingList)
    }
}

function loadParking(markerData) {
    // Create the parking HTML element
    let parkingSpace = document.createElement('div');
    parkingSpace.classList.add('parking-space-class');
    parkingSpace.id = 'parking-space';
    parkingSpace.innerHTML = `
        <div class="parking-details" id="${markerData.id}">
          <div class="parking-address">${markerData.address}</div>
          <div class="parking-reservable">
            <span class="span-reservable" style="color: green">RESERVABLE</span>
          </div>
          <div id="parking-details-row">
            <div id="price-wrapper">
              <div class="parking-price">$${markerData.priceTag.toFixed(2)}</div>
              <div class="parking-fee">parking fee</div>
            </div>
            <div id="parking-distance">
              <div class="distance-wrapper">
                <img alt="walk-icon" src="/images/walk-icon.png" height="20" width="20"/>
                <span>13 mins</span>
              </div>
              <div class="to-destination">to destination</div>
            </div>
          </div>
        </div>
    `;
    return parkingSpace;
}

function loadDetailsForParking(markerData) {
    // Create the parking details HTML element
    let parkingSpaceInformation = document.createElement('div');
    parkingSpaceInformation.classList.add('detailed-park-information');
    parkingSpaceInformation.innerHTML = `
        <div id="close-button" class="parking-details-close"></div>
        <div class="location-details">
            <div class="parking-address" id="${markerData.id}">${markerData.address}</div>
            <div class="parking-reservable">
                <span class="span-reservable" style="color: green">RESERVABLE</span>
            </div>
        </div>
        <div class="standout-details">
            <div class="standout-details-element">
                <div id="standout-duration">
                    4d 19h
                </div>
                <div class="total-duration"> Total duration</div>
            </div>
            <div class="standout-details-element">
                <div id="standout-price" class="parking-price">
                    $${markerData.priceTag.toFixed(2)}
                </div>
                <div id="parking-fee-id" class="parking-fee">parking fee</div>
            </div>
            <div class="standout-details-element">
                <div id="standout-to-destination">
                    <img alt="walk-icon" src="/images/walk-icon.png" height="20" width="20"/>
                    <span>13 mins</span>
                </div>
                <div class="to-destination">to destination</div>
            </div>
        </div>
        <div class="location-information-menu">
            <div class="info-navigation-element">
                <div id="information">
                    Information
                </div>
            </div>
            <div class="info-navigation-element">
                <div id="reviews">
                    Reviews
                </div>
            </div>
            <div class="info-navigation-element">
                <div id="directions">
                    Directions
                </div>
            </div>
        </div>
        <div id="location-information" class="c-location-information">
            <div id="information-details" class="detail-element">
                <div>
                    ${markerData.detailedInformation}
                </div>
            </div>
            <div id="reservation-details" class="detail-element">
                <p>
                    Contrary to popular belief, Lorem Ipsum is not simply random text.
                    It has roots in a piece of classical Latin literature from 45 BC,
                    making it over 2000 years old. Richard McClintock, a Latin professor
                    at Hampden-Sydney College in Virginia.
                </p>
            </div>
            <div id="directions-details" class="detail-element">
                <p>
                    There are many variations of passages of Lorem Ipsum available,
                    but the majority have suffered alteration in some form, by injected humour,
                    or randomised words which don't look even slightly believable.
                    If you are going to use a passage of Lorem Ipsum, you need to be sure there
                    isn't anything embarrassing hidden in the middle of text.
                </p>
                <button id="directions-button" 
                        class="btn btn-warning" 
                        onclick="redirectToDirectionsPage('${markerData.placeId}')"> 
                    Show directions
                </button>
            </div>
        </div>
        <div class="reservation-button-wrapper">
            <button id="reservation-button" 
                    class="btn btn-success" 
                    style="background-color: green" 
                    value="${markerData.id}">
                Reserve for
                <span style="display: inline">
                    $${markerData.priceTag.toFixed(2)}
                </span>
            </button>
        </div>
    `;
    return parkingSpaceInformation;
}

// Redirects the user to directions page with selected park location.
// In directions page is shown the path to the location.
function redirectToDirectionsPage(placeId) {
    window.open(`/directions?placeId=${placeId}`, "_blank");
}

// Saves current location of user in localstorage for later usage
(function setUserCurrentLocationToLocalstorage() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            localStorage.setItem("lat", position.coords.latitude.toString());
            localStorage.setItem("lng", position.coords.longitude.toString());
        });
    }
})()

// Returns current location of the user and zooms in the map.
function getCurrentLocation(map) {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            let center = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };
            map.setCenter(center);
            map.setZoom(12);
        });
    }
}

function populateMarkersFromEndpointIntoMap(map, endpoint) {
    fetch(endpoint)
        .then(response => response.json())
        .then(markers => {
            let filteredData = filterMarkersWithinRadius(markers);
            if (filteredData) {
                for (let marker of filteredData) {
                    addMarker(marker, map);
                }
            }
        })
        .catch(error => console.error(error));
}

function addMarker(marker, map) {
    let advancedMarker = new google.maps.marker.AdvancedMarkerView({
        map,
        content: setMarkerPrice(marker.priceTag.toFixed(2)),
        position: {lat: marker.latitude, lng: marker.longitude},
    });

    advancedMarker.metadata = {id: marker.id};

    // Adding zoom event to the marker.
    advancedMarker.addListener('click', function () {
        // Pan to the marker's position and set the zoom level
        map.panTo(advancedMarker.position);
        map.setZoom(14);
        addOpenParkDetailsFunctionality(advancedMarker);
    });
}

function setMarkerPrice(price) {
    const priceTag = document.createElement("div");
    priceTag.className = "price-tag";
    priceTag.textContent = "$" + price;
    return priceTag;
}

// When marker is clicked, opens details to corresponding marker.
function addOpenParkDetailsFunctionality(advancedMarker) {
    let parksList = document.querySelector('#parks-list');

    for (let parking of parksList.children) {
        let idOfParkListElement = parseInt(parking.children[0].id);
        if (advancedMarker.metadata.id === idOfParkListElement) {
            let parkListElement = document.getElementById(idOfParkListElement.toString());
            parkListElement.click();
        }
    }
}

function filterMarkersWithinRadius(data) {
    let radius = 10; // It will render only markers in 10km range.
    let centerLat = parseFloat(localStorage.getItem("lat"));
    let centerLng = parseFloat(localStorage.getItem("lng"));
    let filteredMarkers = [];
    data.forEach(marker => {
        const distance = getDistanceFromLatLngInKm(centerLat, centerLng, marker.latitude, marker.longitude);
        if (distance <= radius) {
            filteredMarkers.push(marker);
        }
    });
    return filteredMarkers;
}

function getDistanceFromLatLngInKm(centerLat, centerLng, markerLat, markerLng) {
    const earthRadiusKm = 6371;
    const latDifference = degreesToRadians(markerLat - centerLat);
    const lngDifference = degreesToRadians(markerLng - centerLng);
    const squareLen = Math.sin(latDifference / 2) * Math.sin(latDifference / 2)
        + Math.cos(degreesToRadians(centerLat)) * Math.cos(degreesToRadians(markerLat))
        * Math.sin(lngDifference / 2) * Math.sin(lngDifference / 2);
    const centralAngle = 2 * Math.atan2(Math.sqrt(squareLen), Math.sqrt(1 - squareLen));
    return earthRadiusKm * centralAngle;
}

function degreesToRadians(degrees) {
    return degrees * (Math.PI / 180);
}

// Fixes form submitting on page reload.
if (window.history.replaceState) {
    window.history.replaceState(null, null, window.location.href);
}

function setSearchLogic(map) {

    setDefaultSearchPlace();

    const submitButton = document.getElementById("submit-button");
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

    submitButton.addEventListener("click", function () {
        marker.setVisible(false);

        const place = autocomplete.getPlace();

        if (!place.geometry || !place.geometry.location) {
            /* User entered the name of a Place that was not suggested and
             pressed the Enter key, or the Place Details request failed. */
            window.alert("No details available for input: '" + place.name + "'");
            return;
        }

        localStorage.setItem("lat", place.geometry.location.lat());
        localStorage.setItem("lng", place.geometry.location.lng());

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

    // To prevent center changing when search result is clicked, we only update localstorage.
    // Actual changing happens when Search button is clicked.
    autocomplete.addListener("place_changed", () => {
        const searchedPlace = autocomplete.getPlace();
        if (!searchedPlace.geometry || !searchedPlace.geometry.location) {
            /* User entered the name of a Place that was not suggested and
             pressed the Enter key, or the Place Details request failed. */
            window.alert("No details available for input: '" + searchedPlace.name + "'");
            return;
        }
        localStorage.setItem("lat", searchedPlace.geometry.location.lat());
        localStorage.setItem("lng", searchedPlace.geometry.location.lng());
    });
}

// Set default value, which is current location of the user
function setDefaultSearchPlace() {
    let lat = parseFloat(localStorage.getItem("lat"));
    let lng = parseFloat(localStorage.getItem("lng"));

    // Define the Google Maps Geocoding API endpoint URL
    const apiUrl = `https://maps.googleapis.com/maps/api/geocode/json?latlng=${lat},${lng}&key=AIzaSyCTtV6EOMg0cshyQe2h6G_UYUUOlx8Kc5g`;

    // Send a request to the API endpoint
    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            // Assuming place is a PlaceResult object returned by the Google Maps API
            const addressComponents = data.results[0].address_components;

            // Find the city and country components in the address components array
            const cityComponent = addressComponents.find(component => component.types.includes('locality'));
            const countryComponent = addressComponents.find(component => component.types.includes('country'));

            // Extract the long names of the city and country components
            const city = cityComponent && cityComponent.long_name || '';
            const country = countryComponent && countryComponent.long_name || '';

            // Combine the city and country names
            document.getElementById("input").value = `${city}, ${country}`;
        })
        .catch(error => {
            console.error(error);
        });
}

window.initMap = initMap;