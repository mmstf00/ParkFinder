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
        // Set the minimum date of the dateToPicker to the selected date of the dateFromPicker
        dateToPicker.set("minDate", selectedDates[0]);

        // TODO: Improve the validation if picking time less than the 1st picker
        // TODO: There should not be reservations with 0 minutes.

        // If the selected date of the dateToPicker is before the selected date of the dateFromPicker,
        // set the value of the dateToPicker to the selected date of the dateFromPicker
        if (dateToPicker.selectedDates[0] < selectedDates[0]) {
            dateToPicker.setDate(selectedDates[0]);
        }
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
