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

function populateAllMarkers(map) {
    let parkingFrom = document.getElementById("from-date-picker");
    let parkingUntil = document.getElementById("to-date-picker");
    let endpoint = `api/v1`; // For Admin page all markers will be rendered

    if (parkingFrom != null && parkingUntil != null) {
        endpoint = `api/v1/search?dateFrom=${parkingFrom.value}&dateTo=${parkingUntil.value}`;
    }

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
        }).catch(error => console.error(error));
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

    // Create destination time span element
    calculateTravelTime(markerData.placeId, travelTimeInMinutes => {
        const distanceSpan = parkingSpace.querySelector('.distance-wrapper span');
        if (travelTimeInMinutes !== null) {
            // Update the span element's text content with the travel time
            distanceSpan.textContent = `${travelTimeInMinutes} mins`;
        } else {
            console.log('Unable to calculate travel time.');
            distanceSpan.textContent = `${0} mins`;
        }
    });

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
                <span></span>
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

    // Create destination time span element
    calculateTravelTime(markerData.placeId, travelTimeInMinutes => {
        const distanceSpan = parkingSpaceInformation.querySelector('#standout-to-destination span');
        if (travelTimeInMinutes !== null) {
            // Update the span element's text content with the travel time
            distanceSpan.textContent = `${travelTimeInMinutes} mins`;
        } else {
            console.log('Unable to calculate travel time.');
            distanceSpan.textContent = `${0} mins`;
        }
    });

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
                    ${getStandoutDurationForIndexPage()}
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
                    <span></span>
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
                        class="btn btn-info" 
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

function getStandoutDurationForIndexPage() {
    if (window.location.pathname.replace(/\/$/, '') === '/configureMarkers') {
        return "";
    }
    return getStandoutDuration();
}

function getStandoutDuration() {
    let parkingFrom = new Date(document.getElementById("from-date-picker").value);
    let parkingUntil = new Date(document.getElementById("to-date-picker").value);

    let duration = parkingUntil.getTime() - parkingFrom.getTime(); // difference in milliseconds

    let days = Math.floor(duration / (24 * 60 * 60 * 1000)); // convert milliseconds to days
    let hours = Math.floor((duration % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000)); // convert remaining milliseconds to hours
    let minutes = Math.floor((duration % (60 * 60 * 1000)) / (60 * 1000)); // convert remaining milliseconds to minutes

    let durationString = `${days > 0 ? days + 'd ' : ''}${hours > 0 ? hours + 'h ' : ''}${minutes}m`;
    return durationString.trim();
}

function calculateTravelTime(destinationPlaceId, callback) {
    navigator.geolocation.getCurrentPosition(position => {
        const currentLatLng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);

        const placesService = new google.maps.places.PlacesService(document.createElement('div'));
        placesService.getDetails({placeId: destinationPlaceId}, (place, status) => {
            if (status === google.maps.places.PlacesServiceStatus.OK) {
                const destinationLatLng = place.geometry.location;

                const directionsService = new google.maps.DirectionsService();
                directionsService.route(
                    {
                        origin: currentLatLng,
                        destination: destinationLatLng,
                        travelMode: google.maps.TravelMode.DRIVING,
                    },
                    (result, status) => {
                        if (status === google.maps.DirectionsStatus.OK) {
                            const travelTimeInSeconds = result.routes[0].legs[0].duration.value;
                            const travelTimeInMinutes = Math.round(travelTimeInSeconds / 60);
                            callback(travelTimeInMinutes);
                        } else {
                            callback(null);
                        }
                    }
                );
            } else {
                callback(null);
            }
        });
    }, () => {
        callback(null);
    });
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

        // If the endpoint is configureMarkers then show Update/Delete window,
        // otherwise open details of parking
        if (window.location.pathname.replace(/\/$/, '') === '/configureMarkers') {
            openInfoWindow(map, advancedMarker);
        } else {
            addOpenParkDetailsFunctionality(advancedMarker);
        }
    });
}

function openInfoWindow(map, advancedMarker) {
    const contentString = `
                <div id="buttons-container">
                  <button type="button" id="update-button" class="btn btn-warning">
                    Update
                  </button>
                  <button type="button" id="delete-button" class="btn btn-danger">
                    Delete
                  </button>
                </div>
            `;

    const infoWindow = new google.maps.InfoWindow({
        content: contentString
    });

    performRequest(advancedMarker, infoWindow)
    infoWindow.open(map, advancedMarker);
}

// Perform Update / Delete action on button click
function performRequest(advancedMarker, infoWindow) {
    infoWindow.addListener("domready", () => {
        const updateButton = document.querySelector("#update-button");
        const deleteButton = document.querySelector("#delete-button");

        updateButton.addEventListener("click", () => {
            let markerId = advancedMarker.metadata.id;

            updateMarker(markerId);
        });

        deleteButton.addEventListener("click", () => {
            let markerId = advancedMarker.metadata.id;
            deleteMarker(markerId);
            location.reload();
        });
    });
}

function updateMarker(id) {
    document.getElementById("add-parking-button").style.display = "none";
    let updateButton = document.getElementById("update-parking-button");
    updateButton.style.display = "block";


    let marker = fetch(`/api/v1/${id}`, {
        method: 'GET', headers: {
            'Content-Type': 'application/json'
        }
    }).catch(error => console.error(error));

    marker.then(function (result) {
        // Filling the form with current data, that can be updated
        result.json().then(function (data) {
            let address = document.getElementById("address-input");
            address.value = data.address;

            let details = document.getElementById("details");
            details.value = data.detailedInformation;

            let price = document.getElementById("price-input");
            price.value = data.priceTag;

            updateButton.addEventListener('click', () => {
                updateMarkerData(data.id, address.value, details.value, price.value);
                location.reload();
            })
        });
    }).catch(error => console.error(error));
}

function updateMarkerData(id, address, details, price) {
    fetch('/api/v1/update', {
        method: 'PUT', headers: {
            'Content-Type': 'application/json'
        }, body: JSON.stringify({
            id: id,
            address: address,
            details: details,
            price: price,
        })
    }).catch(error => console.error(error));
}

function deleteMarker(id) {
    fetch(`/api/v1/${id}`, {
        method: 'DELETE', headers: {
            'Content-Type': 'application/json'
        }
    }).catch(error => console.error(error));
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

        if (place) {
            if (!place.geometry || !place.geometry.location) {
                /* User entered the name of a Place that was not suggested and if (place.geometry.viewport) {
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
        }

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