let header = document.getElementById("main-header-div");
let map = document.getElementById("map");
let parksList = document.getElementById("parks-list");
let headerHeight = header.offsetHeight;

map.style.top = headerHeight + "px";
parksList.style.top = headerHeight - 60 + "px";

// Fixing map and park list size to be exactly fullscreen
let viewportHeight = window.innerHeight;
let valueInPixels = (100 * viewportHeight) / 100 - headerHeight;
map.style.height = valueInPixels + "px";
parksList.style.height = valueInPixels + "px";

window.onresize = function () {
    headerHeight = header.offsetHeight;
    map.style.top = headerHeight + "px";
    parksList.style.top = headerHeight - 60 + "px";

    viewportHeight = window.innerHeight;
    valueInPixels = (100 * viewportHeight) / 100 - headerHeight;
    map.style.height = valueInPixels + "px";
    parksList.style.height = valueInPixels + "px";
};
