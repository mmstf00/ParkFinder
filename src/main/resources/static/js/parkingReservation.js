let parkingListItems = document.getElementsByClassName("parking-space-class");
let parkingDetails = document.getElementsByClassName("detailed-park-information");

function listenForItemClick() {
    // Open details for parking when is clicked.
    for (let i = 0; i < parkingListItems.length; i++) {
        let parkingItem = parkingListItems[i];
        let parkingDetail = parkingDetails[i];

        parkingItem.addEventListener("click", function () {
            openDetailsMenu(parkingDetail);
        });

        let closeButton = parkingDetail
            .getElementsByClassName("parking-details-close").item(0);
        closeButton.addEventListener("click", function () {
            closeDetailsMenu(parkingDetail);
        })

        listenForOpenSubMenu(parkingDetail);
    }
}

function openDetailsMenu(parkingDetail) {
    hideParkListItems();
    parkingDetail.style.transform = 'translateY(100%)';
    parkingDetail.style.display = "block";
    animateDivFromBottomToTop(parkingDetail);
}

function hideParkListItems() {
    for (const parkingListItem of parkingListItems) {
        parkingListItem.style.display = "none";
    }
}

function closeDetailsMenu(parkingDetail) {
    parkingDetail.style.transform = 'translateY(-100%)';
    parkingDetail.style.display = "none";
    animateDivFromTopToBottom(parkingDetail);
    returnParkListItems();
}

function returnParkListItems() {
    for (const parkingListItem of parkingListItems) {
        parkingListItem.style.display = "block";
    }
}

function animateDivFromBottomToTop(parkingDetail) {
    let pos = 70;
    let animateSpeed = 4;
    const id = setInterval(frame, animateSpeed);

    function frame() {
        if (pos === 0) {
            clearInterval(id);
        } else {
            pos--;
            parkingDetail.style.transform = `translateY(${pos}%)`;
        }
    }
}

function animateDivFromTopToBottom(parkingDetail) {
    let pos = -50;
    let animateSpeed = 4;
    parkingDetail.style.display = 'block';
    const id = setInterval(frame, animateSpeed);

    function frame() {
        if (pos === 30) {
            clearInterval(id);
            parkingDetail.style.display = 'none';
        } else {
            pos++;
            parkingDetail.style.transform = `translateY(${pos}%)`;
        }
    }
}

// Logic to open location details sub-menu
function listenForOpenSubMenu(parkingDetail) {
    let elements = parkingDetail
        .getElementsByClassName("c-location-information").item(0)
        .getElementsByClassName("detail-element");
    let informationDetails = elements.namedItem("information-details");
    let reservationDetails = elements.namedItem("reservation-details");
    let directionsDetails = elements.namedItem("directions-details");

    parkingDetail.onclick = e => {
        if (e.target.id === "information") {
            informationDetails.style.display = "block";
            reservationDetails.style.display = "none";
            directionsDetails.style.display = "none";
        }
        if (e.target.id === "reviews") {
            informationDetails.style.display = "none";
            reservationDetails.style.display = "block";
            directionsDetails.style.display = "none";
        }
        if (e.target.id === "directions") {
            informationDetails.style.display = "none";
            reservationDetails.style.display = "none";
            directionsDetails.style.display = "block";
        }
    }
}

function makeReservationWhenButtonPressed() {
    for (let i = 0; i < parkingDetails.length; i++) {
        let button = parkingDetails[i]
            .getElementsByClassName("reservation-button-class").namedItem("reservation-button-id")
            .getElementsByClassName("btn-success").namedItem("reservation-button");
        if (button != null) {
            button.addEventListener("click", function (event) {
                let parkingDetail = parkingDetails[i];
                let parkingSpace = parkingListItems[i];
                makeReservation(parkingSpace, parkingDetail, event.target);
            });
        }
    }
}

// To prevent multiple request, saving Ids of parkings to check later.
let parkingIds = [];

function makeReservation(parkingSpace, parkingDetail, button) {

    updateStyle(parkingSpace, parkingDetail, button);

    let parkingId = parkingDetail
        .getElementsByClassName("location-details").item(0)
        .getElementsByClassName("parking-address")
        .item(0).getAttribute("id");

    if (!parkingIds.includes(parkingId)) {
        makePutRequestById(parkingId);
    }
    parkingIds.push(parkingId);
}

function updateStyle(parkingSpace, parkingDetail, button) {
    let reservableTextOfDetail = parkingDetail
        .getElementsByClassName("location-details").item(0)
        .getElementsByClassName("parking-reservable").item(0)
        .getElementsByClassName("span-reservable").item(0);

    let reservableTextOfElement = parkingSpace
        .getElementsByClassName("parking-details").item(0)
        .getElementsByClassName("parking-reservable").item(0)
        .getElementsByClassName("span-reservable").item(0);

    reservableTextOfDetail.innerHTML = "RESERVED";
    reservableTextOfDetail.style.color = "red";

    reservableTextOfElement.innerHTML = "RESERVED";
    reservableTextOfElement.style.color = "red";

    button.innerHTML = "RESERVED";
    button.style.backgroundColor = "red";
}

function makePutRequestById(parkingId) {
    fetch('/api/v1', {
        method: 'PUT', headers: {
            'Content-Type': 'application/json'
        }, body: JSON.stringify({
            id: parkingId, isNotReserved: false
        })
    }).catch(error => console.error(error));
}
