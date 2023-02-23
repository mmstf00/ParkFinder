const defaultDatePlus30 = new Date();
defaultDatePlus30.setMinutes(defaultDatePlus30.getMinutes() + 30);

let dateFromPicker = flatpickr("#from-date-picker", {
    defaultDate: new Date(),
    enableTime: true,
    minDate: "today",
    altInput: true,
    altFormat: "M d at H:i",
    dateFormat: "Y-m-d H:i:S",
    onChange: function (selectedDates, dateStr, instance) {
        let newTime = new Date(selectedDates[0].getTime() + 1800000);
        let minTime = new Date(selectedDates[0].getTime() + 300000);
        dateToPicker.setDate(newTime);
        dateToPicker.set("minDate", minTime);
    }
});

let dateToPicker = flatpickr("#to-date-picker", {
    defaultDate: defaultDatePlus30,
    enableTime: true,
    minDate: "today",
    altInput: true,
    altFormat: "M d at H:i",
    dateFormat: "Y-m-d H:i:S",
});
