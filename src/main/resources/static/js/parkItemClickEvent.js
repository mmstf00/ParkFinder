let parkingDetails = document.getElementsByClassName("detailed-park-information");


// TODO implement logic to open parking details by scroll-snap from bottom to top


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
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: parkingId,
            isNotReserved: false
        })
    }).catch(error => console.error(error));
}
