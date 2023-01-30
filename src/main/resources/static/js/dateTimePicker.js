let dateFromPicker = flatpickr("#from-date-picker", {
    enableTime: true,
    minDate: "today",
    altInput: true,
    altFormat: "M d at H:i",
    dateFormat: "Y-m-d H:i:S",
    onChange: function (selectedDates, dateStr, instance) {
        let newTime = new Date(selectedDates[0].getTime() + 1800000);
        let minTime = new Date(selectedDates[0].getTime() + 300000);
        dateToPicker.setDate(newTime, true);
        dateToPicker.set("minDate", minTime);
        localStorage.setItem("dateFrom", dateStr);
    }
});

let dateToPicker = flatpickr("#to-date-picker", {
    enableTime: true,
    minDate: "today",
    altInput: true,
    altFormat: "M d at H:i",
    dateFormat: "Y-m-d H:i:S",
    onChange: function (selectedDates, dateStr, instance) {
        localStorage.setItem("dateTo", dateStr);
    }
});

// When date and time is selected and search is pressed, saves them in localstorage
// to prevent data loss after reload
window.addEventListener("load", function () {

    // Clears dates when index page is loaded.
    if (window.location.pathname === '/') {
        localStorage.clear();
    }

    // Getting data from localStorage
    const savedDateFrom = localStorage.getItem("dateFrom");
    const savedDateTo = localStorage.getItem("dateTo");

    // Sets the date/time to pickers with data from localstorage
    if (savedDateFrom && savedDateTo) {
        dateFromPicker.setDate(new Date(savedDateFrom), true);
        dateToPicker.setDate(new Date(savedDateTo), true);
    }

    // Updating the localstorage with the searched date/time.
    localStorage.setItem("dateFrom", savedDateFrom);
    localStorage.setItem("dateTo", savedDateTo);
});
