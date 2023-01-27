let parkingListItems = document.getElementsByClassName("parking-space-class");
let parkingDetails = document.getElementsByClassName("detailed-park-information");


// Logic to open details for parking when is clicked.
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

function openDetailsMenu(parkingDetail) {
    hideParkListItems();
    parkingDetail.style.transform = 'translateY(100%)';
    parkingDetail.style.display = "block";
    animateDivFromBottomToTop(parkingDetail);
}

function hideParkListItems() {
    for (let i = 0; i < parkingListItems.length; i++) {
        parkingListItems[i].style.display = "none";
    }
}

function closeDetailsMenu(parkingDetail) {
    parkingDetail.style.transform = 'translateY(-100%)';
    parkingDetail.style.display = "none";
    animateDivFromTopToBottom(parkingDetail);
    returnParkListItems();
}

function returnParkListItems() {
    for (let i = 0; i < parkingListItems.length; i++) {
        parkingListItems[i].style.display = "block";
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
    let howToParkDetails = elements.namedItem("how-to-park-details");

    parkingDetail.onclick = e => {
        if (e.target.id === "information") {
            informationDetails.style.display = "block";
            reservationDetails.style.display = "none";
            howToParkDetails.style.display = "none";
        }
        if (e.target.id === "reviews") {
            informationDetails.style.display = "none";
            reservationDetails.style.display = "block";
            howToParkDetails.style.display = "none";
        }
        if (e.target.id === "how-to-park") {
            informationDetails.style.display = "none";
            reservationDetails.style.display = "none";
            howToParkDetails.style.display = "block";
        }
    }
}

// Logic to make reservation when button is pressed.
for (let i = 0; i < parkingDetails.length; i++) {

    let button = parkingDetails[i]
        .getElementsByClassName("reservation-button-class").namedItem("reservation-button-id")
        .getElementsByClassName("btn-success").namedItem("reservation-button");

    if (button != null) {
        button.addEventListener("click", function () {
            let parking = parkingDetails[i];
            makeReservation(parking, button);
        });
    }
}

function makeReservation(parking, button) {
    let reservableText = parking
        .getElementsByClassName("location-details").item(0)
        .getElementsByClassName("parking-reservable").item(0)
        .getElementsByClassName("span-reservable").item(0);

    updateStyle(reservableText, button);

    let parkingId = parking
        .getElementsByClassName("location-details").item(0)
        .getElementsByClassName("parking-address")
        .item(0).getAttribute("block");

    makePutRequestById(parkingId);
}

function updateStyle(reservableText, button) {
    reservableText.innerHTML = "RESERVED";
    reservableText.style.color = "red";

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
