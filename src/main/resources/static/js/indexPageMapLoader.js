function initMap() {

    // Default center if search is not performed.
    let center = {
        lat: 37.43238031167444, lng: -122.16795397128632,
    };

    let map = new google.maps.Map(document.getElementById("map"), {
        center: center,
        zoom: 11,
        mapId: "4504f8b37365c3d0",
    });
    // Allows to scroll without pressing CTRL button.
    map.setOptions({scrollwheel: true});

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
    for (let i = 0; i < divsToRemove.length; i++) {
        divsToRemove[i].style.display = "none";
    }
}

function loadParkListItems(endpoint) {
    fetch(endpoint)
        .then(response => response.json())
        .then(data => {
            // Getting the reference to the parent element where we will add the new elements.
            let parksList = document.querySelector('#parks-list');
            if (parksList) {
                parksList.innerHTML = ""; // Clearing older elements.
            }

            data.forEach(marker => {

                let isReservable = marker.reservable;
                let reservableClass = isReservable ? 'span-reservable' : 'span-reserved';
                let reservableStyle = isReservable ? 'color: green' : 'color: red';
                let reservableText = isReservable ? 'RESERVABLE' : 'RESERVED';

                // Create the parking HTML element
                let parkingSpace = document.createElement('div');
                parkingSpace.classList.add('parking-space-class');
                parkingSpace.id = 'parking-space';
                parkingSpace.innerHTML = `
                    <div class="parking-details" id="${marker.id}">
                      <div class="parking-address">${marker.address}</div>
                      <div class="parking-reservable">
                        <span class="${reservableClass}" style="${reservableStyle}">${reservableText}</span>
                      </div>
                      <div id="parking-details-row">
                        <div id="price-wrapper">
                          <div class="parking-price">$${marker.priceTag.toFixed(2)}</div>
                          <div class="parking-fee">parking fee</div>
                        </div>
                        <div id="parking-distance">
                          <div class="distance-wrapper">
                            <img src="/images/walk-icon.png" height="20" width="20"/>
                            <span>13 mins</span>
                          </div>
                          <div class="to-destination">to destination</div>
                        </div>
                      </div>
                    </div>
                `;


                let reservationButtonClass = isReservable ? 'btn btn-success' : 'btn btn-danger';
                let reservationButtonId = isReservable ? 'reservation-button' : 'reserved-button';
                let reservationButtonText = isReservable ? 'Reserve for' : 'RESERVED';
                let reservationButtonStyle = isReservable ? 'background-color: green' : 'background-color: red';

                let reservationButtonSpanStyle = isReservable ? 'display: inline' : 'display: none';

                // Create the parking details HTML element
                let parkingSpaceInformation = document.createElement('div');
                parkingSpaceInformation.classList.add('detailed-park-information');
                parkingSpaceInformation.innerHTML = `
                    <div class="parking-details-close"></div>
                    <div class="location-details">
                        <div class="parking-address" id="${marker.id}">${marker.address}</div>
                        <div class="parking-reservable">
                            <span class="${reservableClass}" style="${reservableStyle}">${reservableText}</span>
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
                                $${marker.priceTag.toFixed(2)}
                            </div>
                            <div id="parking-fee-id" class="parking-fee">parking fee</div>
                        </div>
                        <div class="standout-details-element">
                            <div id="standout-to-destination">
                                <img src="/images/walk-icon.png" height="20" width="20"/>
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
                            <div id="how-to-park">
                                How to park
                            </div>
                        </div>
                    </div>
                    <div id="location-information" class="c-location-information">
                        <div id="information-details" class="detail-element">
                            Lorem Ipsum is simply dummy text of the printing and typesetting industry.
                            Lorem Ipsum has been the industry's standard dummy text ever since the
                            1500s,
                            when an unknown printer took a galley of type and scrambled
                            it to make a type specimen book. It has survived not only five centuries,
                            but also the leap into electronic typesetting, remaining essentially
                            unchanged.
                        </div>
                        <div id="reservation-details" class="detail-element">
                            Contrary to popular belief, Lorem Ipsum is not simply random text.
                            It has roots in a piece of classical Latin literature from 45 BC,
                            making it over 2000 years old. Richard McClintock, a Latin professor
                            at Hampden-Sydney College in Virginia.
                        </div>
                        <div id="how-to-park-details" class="detail-element">
                            There are many variations of passages of Lorem Ipsum available,
                            but the majority have suffered alteration in some form, by injected humour,
                            or randomised words which don't look even slightly believable.
                            If you are going to use a passage of Lorem Ipsum, you need to be sure there
                            isn't anything embarrassing hidden in the middle of text.
                        </div>
                    </div>
                    <div id="reservation-button-id" class="reservation-button-class">
                        <button id="${reservationButtonId}" 
                                class="${reservationButtonClass}" 
                                style="${reservationButtonStyle}">
                            ${reservationButtonText}
                            <span style="${reservationButtonSpanStyle}">
                                $${marker.priceTag.toFixed(2)}
                            </span>
                        </button>
                    </div>
                `;

                // To prevent errors in configuration page, checking if parksList exists.
                if (parksList) {

                    // Append the element to the parent
                    parksList.appendChild(parkingSpace);
                    parksList.appendChild(parkingSpaceInformation);

                    listenForItemClick();
                    makeReservationWhenButtonPressed();
                }
            });
        });
}

function populateMarkersFromEndpointIntoMap(map, endpoint) {
    fetch(endpoint)
        .then(response => response.json())
        .then(markers => {
            if (markers) {
                for (let marker of markers) {
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

    // Adding zoom event to the marker.
    advancedMarker.addListener('click', function () {
        // Pan to the marker's position and set the zoom level
        map.panTo(advancedMarker.position);
        map.setZoom(14); // TODO: Calculate Map zoom and marker zoom for smooth zoom result.
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