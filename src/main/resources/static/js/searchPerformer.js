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
    }
});

let dateToPicker = flatpickr("#to-date-picker", {
    enableTime: true,
    minDate: "today",
    altInput: true,
    altFormat: "M d at H:i",
    dateFormat: "Y-m-d H:i:S",
});
