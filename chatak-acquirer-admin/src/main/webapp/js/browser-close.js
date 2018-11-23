var spcKey = false;
var hover = true;
var contextMenu = false;

function spc(e) {
    return ((e.altKey || e.ctrlKey || e.keyCode == 91 || e.keyCode==87) && e.keyCode!=82 && e.keyCode!=116);
}

$(document).hover(function () {
    hover = true;
    contextMenu = false;
    spcKey = false;
}, function () {
    hover = false;
}).keydown(function (e) {
    if (spc(e) == false) {
        hover = true;
        spcKey = false;
    }
    else {
        spcKey = true;
    }
}).keyup(function (e) {
    if (spc(e)) {
        spcKey = false;
    }
}).contextmenu(function (e) {
    contextMenu = true;
}).click(function () {
    hover = true;
    contextMenu = false;
});

window.addEventListener('focus', function () {
    spcKey = false;
});
window.addEventListener('blur', function () {
    hover = false;
});

/*window.onbeforeunload = function (e) {
    if ((hover == false || spcKey == true) && contextMenu==false) {
        $.ajax({
        	type : 'GET',
			url : 'logout'
        });
    }
};*/

function goToLoginPage() {
    hover = true;
    spcKey = false;
    contextMenu = false;
    location.href = "/Account/Login";
}