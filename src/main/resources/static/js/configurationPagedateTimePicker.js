let configDateFromPicker = flatpickr("#config-date-from", {
    enableTime: true,
    minDate: "today",
    altInput: true,
    altFormat: "M d at H:i",
    dateFormat: "Y-m-d H:i:S",
    onChange: function (selectedDates, dateStr, instance) {
        let newTime = new Date(selectedDates[0].getTime() + 1800000);
        let minTime = new Date(selectedDates[0].getTime() + 300000);
        configDateToPicker.setDate(newTime, true);
        configDateToPicker.set("minDate", minTime);
    }
});

let configDateToPicker = flatpickr("#config-date-to", {
    enableTime: true,
    minDate: "today",
    altInput: true,
    altFormat: "M d at H:i",
    dateFormat: "Y-m-d H:i:S"
});