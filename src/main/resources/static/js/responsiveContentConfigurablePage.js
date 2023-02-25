let header = document.getElementById("configurable-page-header");
let map = document.getElementById("map");
let markerAdderContainer = document.getElementById("marker-adder-container");
let headerHeight = header.offsetHeight;

// Margin from top as much as the header length
map.style.top = headerHeight - 59 + "px";
markerAdderContainer.style.top = headerHeight - 59 + "px";

// Fixing map and park list size to be exactly fullscreen
let viewportHeight = window.innerHeight;
let valueInPixels = (100 * viewportHeight) / 100 - headerHeight;
map.style.height = valueInPixels + "px";
markerAdderContainer.style.height = valueInPixels + "px";

// When screen is resized, resize elements also as above
window.onresize = function () {
    headerHeight = header.offsetHeight;

    map.style.top = headerHeight - 59 + "px";
    markerAdderContainer.style.top = headerHeight - 59 + "px";

    viewportHeight = window.innerHeight;
    valueInPixels = (100 * viewportHeight) / 100 - headerHeight;
    map.style.height = valueInPixels + "px";
    markerAdderContainer.style.height = valueInPixels + "px";
};