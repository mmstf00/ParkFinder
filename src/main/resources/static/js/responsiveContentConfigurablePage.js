let header = document.getElementById("configurable-page-header");
let map = document.getElementById("map");
let markerAdderContainer = document.getElementById("marker-adder-container");
let headerHeight = header.offsetHeight;

map.style.top = headerHeight + "px";
markerAdderContainer.style.top = headerHeight - 60 + "px";

// Fixing map and park list size to be exactly fullscreen
let viewportHeight = window.innerHeight;
let valueInPixels = (100 * viewportHeight) / 100 - headerHeight;
map.style.height = valueInPixels + "px";
markerAdderContainer.style.height = valueInPixels + "px";

window.onresize = function () {
    let headerHeight = header.offsetHeight;
    map.style.top = headerHeight + "px";
    markerAdderContainer.style.top = headerHeight - 60 + "px";

    viewportHeight = window.innerHeight;
    valueInPixels = (100 * viewportHeight) / 100 - headerHeight;
    map.style.height = valueInPixels + "px";
    markerAdderContainer.style.height = valueInPixels + "px";
};