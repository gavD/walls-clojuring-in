document.onkeydown = function(evt) {
    evt = evt || window.event;
console.log(evt.keyCode);
    switch (evt.keyCode) {
        case 37:
            window.location =  $('#cardinalWest a').attr('href');
            break;

        case 38:
            window.location =  $('#cardinalNorth a').attr('href');
            break;

        case 39:
            window.location =  $('#cardinalEast a').attr('href');
            break;

        case 40:
            window.location =  $('#cardinalSouth a').attr('href');
            break;
    }
};
