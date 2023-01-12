let header = document.getElementById("main-header-div");
let map = document.getElementById("map");
let parksList = document.getElementById("parks-list");
let headerHeight = header.offsetHeight;

map.style.top = headerHeight + "px";
parksList.style.top = headerHeight - 60 + "px";

window.onresize = function () {
    let headerHeight = header.offsetHeight;
    map.style.top = headerHeight + "px";
    parksList.style.top = headerHeight - 60 + "px";
};
