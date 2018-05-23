var broowserName = detectBrowser();

if (broowserName == "Chrome" || broowserName == "Opera") {
	try {
		history.pushState({
			page : 1
		}, "title 1", "#no-back-button");
		window.onhashchange = function(event) {
			window.location.hash = "no-back-button";

		};

	} catch (e) {
		noback();
	}

} else if (broowserName == "Firefox" || broowserName == "MSIE") {

	try {
		window.location.hash = "no-back-button";
		window.location.hash = "Again-No-back-button";
		window.onhashchange = function() {
			window.location.hash = "no-back-button";
		};

	} catch (e) {

		noback();
	}

}

function noback() {

	window.history.forward();

}

function detectBrowser() {
	var N = navigator.appName;
	var UA = navigator.userAgent;
	var temp;
	var browserVersion = UA
			.match(/(opera|chrome|safari|firefox|msie)\/?\s*(\.?\d+(\.\d+)*)/i);
	if (browserVersion && (temp = UA.match(/version\/([\.\d]+)/i)) != null)
		browserVersion[2] = temp[1];
	browserVersion = browserVersion ? [ browserVersion[1], browserVersion[2] ]
			: [ N, navigator.appVersion, '-?' ];
	return browserVersion[0];
};

// Handling the browser close event  start
window.onbeforeunload = function(event) {

	try {
		if (((window.event.clientX || event.clientX) < 0)
				|| ((window.event.clientY || event.clientY) < 0)) // close
		// button
		{
			logoutOnBrowserClose();

		}
	} catch (e) {
	}
	try {
		if ((window.event.clientX < 0) || (window.event.clientY < 0)) // close
		// button
		{
			logoutOnBrowserClose();
		} else if (window.event.altKey == true || window.event.ctrlKey == true) // ALT
		// + F4
		{
			logoutOnBrowserClose();
		} else // for all other unload events
		{

		}
	} catch (e) {
	}

};

// Handling the browser close event  end

function logoutOnBrowserClose() {
	try {
		var xmlhttp = new XMLHttpRequest();
		xmlhttp.open("GET", "logOut", false);
		xmlhttp.send();
	} catch (e) {
	}
}