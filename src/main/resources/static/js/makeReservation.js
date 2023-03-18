let vehiclesHeader = document.getElementById("vehicles-header");

let addVehicleButton = document.getElementById("add-vehicle-button");
let vehiclePlateInput = document.getElementById("vehicle-input");

let addVehicleContainer = document.getElementById("add-vehicle-container");
let editVehicleLink = document.getElementById("edit-vehicle");
let addedVehicleContainer = document.getElementById("added-vehicle");
let vehiclePlateSpan = document.getElementById("vehicle-plate-span");

let paymentContainerBeforeVehicle = document.getElementById("payment-container-before-vehicle");
let paymentContainerAfterVehicle = document.getElementById("payment-container-after-vehicle");

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


(function makeReservation() {
    let payNowButton = document.getElementById("pay-now-button");

    const urlParams = new URLSearchParams(window.location.search);
    const parkingId = urlParams.get('parkingId');
    const parkingTimeFrom = urlParams.get('parkingFrom');
    const parkingTimeUntil = urlParams.get('parkingUntil');

    console.log(parkingId);
    console.log(parkingTimeFrom);
    console.log(parkingTimeUntil);

    payNowButton.addEventListener("click", () => {
        // makePutRequestById(parkingId);
        // TODO: Redirect to successful reservation page
        window.open(`/`, "_self");
    });
}())


// TODO: Add also the reservation times
function makePutRequestById(parkingId) {
    fetch('/api/v1', {
        method: 'PUT', headers: {
            'Content-Type': 'application/json'
        }, body: JSON.stringify({
            id: parkingId, isNotReserved: false
        })
    }).catch(error => console.error(error));
}
