let vehiclesHeader = document.getElementById("vehicles-header");

let addVehicleButton = document.getElementById("add-vehicle-button");
let vehiclePlateInput = document.getElementById("vehicle-input");

let addVehicleContainer = document.getElementById("add-vehicle-container");
let editVehicleLink = document.getElementById("edit-vehicle");
let addedVehicleContainer = document.getElementById("added-vehicle");
let vehiclePlateSpan = document.getElementById("vehicle-plate-span");

let paymentContainerBeforeVehicle = document.getElementById("payment-container-before-vehicle");
let paymentContainerAfterVehicle = document.getElementById("payment-container-after-vehicle");

let totalDuration = document.getElementById("totalDuration").innerText;

let finalPrice = document.getElementById("final-price").innerText;
let totalCost = parseFloat(finalPrice.replace("$", ""));

addVehicleButton.addEventListener("click", function () {
    if (vehiclePlateInput.value !== "") {
        updateAddVehicleContainer();

        // Display Payment information only after vehicle register
        paymentContainerBeforeVehicle.style.display = "none";
        paymentContainerAfterVehicle.style.display = "block";
    }
});

function updateAddVehicleContainer() {
    // Hiding Add input field
    addVehicleContainer.style.display = "none";

    // Displaying edit link
    editVehicleLink.style.display = "block";
    vehiclesHeader.style.flexDirection = "row";

    // Get input value and update the added vehicle information.
    vehiclePlateSpan.textContent = vehiclePlateInput.value.toUpperCase();
    addedVehicleContainer.style.display = "block";
}

editVehicleLink.addEventListener("click", function () {
    editVehicleLink.style.display = "none";
    addedVehicleContainer.style.display = "none";
    vehiclesHeader.style.flexDirection = "column";

    addVehicleContainer.style.display = "flex";

    document.getElementById("add-vehicle-span").innerText = "Edit vehicle";
    document.getElementById("add-span").innerText = "Edit";
});


let savePaymentButton = document.getElementById("save-payment-button");
let payNowButton = document.getElementById("pay-now-button");
savePaymentButton.addEventListener("click", function () {
    if (checkInputsNotEmpty()) {
        payNowButton.removeAttribute("disabled");
    }
});

function checkInputsNotEmpty() {
    let paymentData = document.getElementById("payment-data-container");
    let allInputs = paymentData.querySelectorAll("input");

    // Loop through all the input elements and check if their values are empty
    for (const input of allInputs) {
        if (input.value === '') {
            return false; // Return false if any input field is empty
        }
    }
    return true; // Return true if all input fields are not empty
}


payNowButton.addEventListener("click", () => {
    const urlParams = new URLSearchParams(window.location.search);
    let parkingId = urlParams.get('parkingId');
    let parkingDateFrom = urlParams.get('parkingDateFrom');
    let parkingTimeFrom = urlParams.get('parkingTimeFrom');
    let parkingDateTo = urlParams.get('parkingDateTo');
    let parkingTimeTo = urlParams.get('parkingTimeTo');

    let plateNumber = vehiclePlateInput.value;
    makeReservation(parkingId, plateNumber, parkingDateFrom, parkingTimeFrom, parkingDateTo, parkingTimeTo);
    window.open(`/confirmation/success`, "_self");
});

function makeReservation(parkingId, plateNumber, parkingDateFrom, parkingTimeFrom, parkingDateTo, parkingTimeTo) {
    fetch('/api/v1', {
        method: 'PUT', headers: {
            'Content-Type': 'application/json'
        }, body: JSON.stringify({
            id: parkingId,
            plateNumber: plateNumber,
            totalDuration: totalDuration,
            totalCost: totalCost,
            customer: customer,
            dateFrom: parkingDateFrom,
            timeFrom: parkingTimeFrom,
            dateTo: parkingDateTo,
            timeTo: parkingTimeTo
        })
    }).catch(error => console.error(error));
}
