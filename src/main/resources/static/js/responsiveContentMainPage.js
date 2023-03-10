let header = document.getElementById("main-header-div");
let map = document.getElementById("map");
let parksList = document.getElementById("parks-list");
let headerHeight = header.offsetHeight;

// Margin from top as much as the header length
map.style.top = headerHeight - 60 + "px";
parksList.style.top = headerHeight - 60 + "px";

// Fixing map and park list size to be exactly fullscreen
let viewportHeight = window.innerHeight;
let valueInPixels = (100 * viewportHeight) / 100 - headerHeight;
map.style.height = valueInPixels + "px";
parksList.style.height = valueInPixels + "px";

// When screen is resized, resize elements also as above
window.onresize = function () {
    headerHeight = header.offsetHeight;

    map.style.top = headerHeight - 60 + "px";
    parksList.style.top = headerHeight - 60 + "px";

    viewportHeight = window.innerHeight;
    valueInPixels = (100 * viewportHeight) / 100 - headerHeight;
    map.style.height = valueInPixels + "px";
    parksList.style.height = valueInPixels + "px";
};
