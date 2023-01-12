let header = document.getElementById("configurable-page-header");
let map = document.getElementById("map");
let markerAdderContainer = document.getElementById("marker-adder-container");
let headerHeight = header.offsetHeight;

map.style.top = headerHeight + "px";
markerAdderContainer.style.top = headerHeight - 60 + "px";

window.onresize = function () {
    let headerHeight = header.offsetHeight;
    map.style.top = headerHeight + "px";
    markerAdderContainer.style.top = headerHeight - 60 + "px";
};