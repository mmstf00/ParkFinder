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

        addParentDivStyle(e);
        addChildDivStyle(e);

        const targetId = e.target.id;
        const isInfoParent = hasClassName(e, "info-parent");
        const isReviewParent = hasClassName(e, "review-parent");
        const isDirectionsParent = hasClassName(e, "directions-parent");

        // Switching details tabs
        informationDetails.style.display = (targetId === "information" || isInfoParent) ? "block" : "none";
        reservationDetails.style.display = (targetId === "reviews" || isReviewParent) ? "block" : "none";
        directionsDetails.style.display = (targetId === "directions" || isDirectionsParent) ? "block" : "none";
    }
}

function addParentDivStyle(element) {
    removeActiveState('.c-tab__item--active');

    if (hasClassName(element, "c-tab__item")) {
        element.target.classList.add("c-tab__item--active");
        element.target.childNodes[1].classList.remove("c-typography--h3-rLKwZ")
        element.target.childNodes[1].classList.add("c-typography--h3-rLKwZ-black");
    } else {
        element.target.parentNode.classList.add("c-tab__item--active");
    }
}

function addChildDivStyle(element) {
    removeActiveState('.c-tab__tab-item--active');
    element.target.classList.add("c-tab__tab-item--active");
}

function removeActiveState(querySelectorName) {
    let elements = document.querySelectorAll(querySelectorName);
    elements.forEach(function (div) {
        div.classList.remove(querySelectorName.slice(1));
    });
}

function hasClassName(element, className) {
    return element.target.classList.contains(className);
}

// Redirects the user to reservation page when RESERVE button is pressed.
function redirectToReservationPage() {
    let allParkingButtons = document.querySelectorAll(".reservation-button-wrapper");
    let parkingFrom = document.getElementById("from-date-picker").value;
    let parkingUntil = document.getElementById("to-date-picker").value;

    let parkingDateFrom = getFormattedDate(parkingFrom);
    let parkingTimeFrom = getFormattedTime(parkingFrom);
    let parkingDateTo = getFormattedDate(parkingUntil);
    let parkingTimeTo = getFormattedTime(parkingUntil);

    allParkingButtons.forEach(function (button) {
        button.addEventListener("click", function (event) {
            let reserveButton = event.target;
            // Preventing redirection for reserved parkings
            if (reserveButton.id === "reservation-button") {
                window.open(`/confirmation?parkingId=${reserveButton.value}&parkingDateFrom=${parkingDateFrom}&parkingTimeFrom=${parkingTimeFrom}&parkingDateTo=${parkingDateTo}&parkingTimeTo=${parkingTimeTo}`, "_self");
            }
        });
    });
}

function getFormattedDate(dateAndTime) {
    const parkingDate = new Date(dateAndTime);
    const year = parkingDate.getFullYear();
    const month = parkingDate.getMonth() + 1;
    const day = parkingDate.getDate();

    // Returns formatted date in format YYYY-MM-DD
    return `${year}-${month < 10 ? '0' : ''}${month}-${day < 10 ? '0' : ''}${day}`;
}

function getFormattedTime(dateAndTime) {
    const parkingTime = new Date(dateAndTime);
    const hours = parkingTime.getHours();
    const minutes = parkingTime.getMinutes();

    // Returns formatted time in format HH:MM
    return `${hours < 10 ? '0' : ''}${hours}:${minutes < 10 ? '0' : ''}${minutes}`;
}
