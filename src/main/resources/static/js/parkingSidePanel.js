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

// Redirects the user to reservation page when RESERVE button is pressed.
function redirectToReservationPage() {
    let allParkingButtons = document.querySelectorAll(".reservation-button-wrapper");
    let parkingTimeFrom = document.getElementById("from-date-picker").value;
    let parkingTimeUntil = document.getElementById("to-date-picker").value;

    allParkingButtons.forEach(function (button) {
        button.addEventListener("click", function (event) {
            let reserveButton = event.target;
            // Preventing redirection for reserved parkings
            if (reserveButton.id === "reservation-button") {
                window.open(`/confirmation?parkingId=${reserveButton.value}&parkingFrom=${parkingTimeFrom}&parkingUntil=${parkingTimeUntil}`, "_self");
            }
        });
    });
}