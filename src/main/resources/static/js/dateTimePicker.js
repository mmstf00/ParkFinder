config = {
    enableTime: true,
    minDate: "today",
    altInput: true,
    altFormat: "M d at H:i",
    dateFormat: "Y-m-d H:i:S"
}

flatpickr("input[type=datetime-local]", config);