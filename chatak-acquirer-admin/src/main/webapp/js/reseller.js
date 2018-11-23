function editReseller(resellerId) {
	get('getResellerId').value = resellerId;
	document.forms["editResellerForm"].submit();
}


function validateEmailId() {
	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var emailAddress = get('emailId').value.trim();
	if (isEmpty(emailAddress)) {
		setError(get('emailId'), webMessages.pleaseEnterEmailID);
		loadMsgTitleText();
		return false;
	} else if (reg.test(emailAddress) == false) {
		setError(get('emailId'), webMessages.invalidEmailID);
		loadMsgTitleText();
		return false;
	} else {
		if (alpha.indexOf(emailAddress.charAt(0)) == -1) {
			setError(get('emailId'), webMessages.invalidEmail);
			loadMsgTitleText();
			return false;
		} else {
			doAjaxFetchMailIdAvailable();
			if (usernameFlag == false) {
				return true;
			} else {
				return false;
			}
		}
	}
}

function doAjaxFetchMailIdAvailable() {
	var mailId = get('emailId').value.trim();
	$.ajax({
		type : "GET",
		url : "uniqueEmailId?emailId=" + mailId,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorCode === '00') {
				setError(get('emailId'), '');
				// document.getElementById('userNamegreenEr').innerHTML =
				// "username available";
				setLable('confirmMemailId', get('emailId').value.trim());
				usernameFlag = false;
			} else {
				setError(get('emailId'), 'Email id already in use');
				usernameFlag = true;
			}
		},
		error : function(e) {
		}
	});
}




function validateResellerName() {
	var resellerName = get('resellerName').value.trim();
	//var spaceRegx = /^[a-zA-Z0-9]+(\s{0,1}[a-zA-Z0-9])*$/;
	var spaceRegx = /^[A-Za-z0-9@][A-Za-z0-9!#$%^&*'()+\=\[\]{};:"\\|,.<>\/? ]*$/;
	if (isEmpty(resellerName)) {
		setError(get('resellerName'), webMessages.pleaseEnterResellerName);
		loadMsgTitleText();
		return false;
	} else if (!spaceRegx.test(resellerName)) {
		setError(get('resellerName'), webMessages.invalidResellerName);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('resellerName'), '');
		return true;
	}
}


function confirmDeleteReseller(resellerId) {
	var r = confirm("Press Ok to confirm deletion");
	if (r == true) {
		get('getResellerId1').value = resellerId;
		document.forms["deleteResellerForm"].submit();

	} else {
		return;
	}
}



function viewResellerInfo(resellerId) {
	get('resellerViewresellerId').value = resellerId;
	document.forms["viewResellerForm"].submit();
}


function validateContactName() {
	var contactName = get('contactName').value.trim();
	var spaceRegx = /^[A-Za-z0-9@][A-Za-z0-9!#$%^&*'()+\=\[\]{};:"\\|,.<>\/? ]*$/;
	//var firstCharRegx = /^[a-zA-Z].*$/;
	//var spaceRegx =  /^[a-zA-Z'&.- ]*$/;
	if (isEmpty(contactName)) {
		setError(get('contactName'), webMessages.pleaseEnterContactName);
		loadMsgTitleText();
		return false;
	}else if (!spaceRegx.test(contactName)) {
		setError(get('contactName'), webMessages.invalidContactName);
		loadMsgTitleText();
		return false;
	}/*else if (!firstCharRegx.test(firstName.charAt(0))) {
		setError(get('firstName'), 'Can not start with Special Char');
		return false;
	}*/ else {
		setError(get('contactName'), '');
		return true;
	}
}


function validateEmailId() {
	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var emailAddress = get('emailId').value.trim();
	if (isEmpty(emailAddress)) {
		setError(get('emailId'), webMessages.pleaseEnterEmailID);
		loadMsgTitleText();
		return false;
	} else if (reg.test(emailAddress) == false) {
		setError(get('emailId'), webMessages.invalidEmailID);
		loadMsgTitleText();
		return false;
	} else {
		if (alpha.indexOf(emailAddress.charAt(0)) == -1) {
			setError(get('emailId'), webMessages.invalidEmail);
			loadMsgTitleText();
			return false;
		} else {
			doAjaxFetchMailIdAvailable();
			if (usernameFlag == false) {
				return true;
			} else {
				return false;
			}
		}
	}
}


function validateStatus() {
	var status = get('status').value.trim();

	var statusValue = document.getElementById("status");

	var x = statusValue.options[statusValue.selectedIndex].text;

	if (isEmpty(status)) {
		setError(get('status'), webMessages.pleaseSelectStatus);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('status'), '');
		return true;
	}
}



function validatePhone() {
	var phone = getVal('phone').trim();
	if (isEmpty(phone)) {
		setError(get('phone'), webMessages.pleaseEnterPhoneNumber);
		loadMsgTitleText();
		return false;
	} else if (!isDigit(phone) || phone.length < 10 || phone.length > 13
			|| phone == 0) {
		setError(get('phone'), webMessages.invalidPhoneNumber);
		loadMsgTitleText();
		return false;
	} else if (phone.charAt(parseInt("0")) == "0") {
		setError(get('phone'), webMessages.phoneCannotStartwithZero);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('phone'), '');
		return true;
	}
}


function validateAddress1() {
	var address1 = get('address1').value.trim();
	/* var addRegx = /^[A-Za-z0-9\#\$\&\,]+(\s{0,1}[a-zA-Z0-9,])*$/; */
	if (isEmpty(address1)) {
		setError(get('address1'), webMessages.pleaseEnterAddress1);
		loadMsgTitleText();
		return false;
	} else if (address1.length < 5) {
		setError(get('address1'), webMessages.invalidAddress1Length);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('address1'), '');
		return true;
	}
}
function validateAddress2() {
	var address2 = get('address2').value.trim();
		setError(get('address2'), '');
		return true;
}

function validateCity() {
	var city = get('city').value.trim();
	var cityRegx = /^[A-Za-z0-9\#\$\&]+(\s{0,1}[a-zA-Z0-9,])*$/;
	if (isEmpty(city)) {
		setError(get('city'), webMessages.pleaseEnterCity);
		loadMsgTitleText();
		return false;
	} else if (!cityRegx.test(city)) {
		setError(get('city'), webMessages.invalidCity);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('city'), '');
		return true;
	}
}

function validateCountry() {
	var country = get('country').value.trim();
	if (isEmpty(country)) {
		setError(get('country'), webMessages.pleaseSelectCountry);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('country'), '');
		return true;
	}
}

function validateState() {
	var state = get('state').value.trim();
	if (isEmpty(state)) {
		setError(get('state'), webMessages.pleaseSelectState);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('state'), '');
		return true;
	}
}


function validateZip() {
	var zip = getVal('zip');
	if (isEmpty(zip)) {
		setError(get('zip'), webMessages.pleaseEnterZipCode);
		loadMsgTitleText();
		return false;
	} else if ((zip.length < 3) || (zip.length > 6)) {
		setError(get('zip'), webMessages.invalidZipCode);
		loadMsgTitleText();
		return false;
	} else if (!isValidZipChars(zip)) {
		setError(get('zip'), webMessages.invalidZipCode);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('zip'), '');
		return true;
	}
}


function resetBasicInfo() {
	resetSuccessAndErrorMsg();
	get('status').value = "";
	setError(get('status'), '');
	get('resellerName').value = "";
	setError(get('resellerName'), '');
	get('contactName').value = "";
	setError(get('contactName'), '');
	get('emailId').value = "";
	setError(get('emailId'), '');
	get('phone').value = "";
	setError(get('phone'), '');
	get('address1').value = "";
	setError(get('address1'), '');
	get('country').value = "";
	setError(get('country'), '');
	get('address2').value = "";
	setError(get('address2'), '');
	get('state').value = "";
	setError(get('state'), '');
	get('zip').value = "";
	setError(get('zip'), '');
	get('city').value = "";
	setError(get('city'), '');
}


function cancelCreateReseller() {
	window.location.href = 'reseller-search-page';
}

function validateCreateReseller() {
	
	if ( !validateCountry() | !validateState() |!validateZip() 
			| !validateCity() | !validateAddress1() |!validatePhone()
			| !validateResellerName()|!validateContactName()|!validateEmailId() | !validateStatus()) {
		return false;
	}
	return true;
}


function resetEditInfo() {
	resetSuccessAndErrorMsg();
	get('contactName').value = "";
	setError(get('contactName'), '');
	get('emailId').value = "";
	setError(get('emailId'), '');
	get('phone').value = "";
	setError(get('phone'), '');
	get('address1').value = "";
	setError(get('address1'), '');
	get('country').value = "";
	setError(get('country'), '');
	get('address2').value = "";
	setError(get('address2'), '');
	get('state').value = "";
	setError(get('state'), '');
	get('zip').value = "";
	setError(get('zip'), '');
	get('city').value = "";
	setError(get('city'), '');
}

function cancelEditReseller() {
	window.location.href = 'reseller-search-page';
}

function goToRsellerSearch() {
	window.location.href = 'reseller-search-page';
}

function backReseller() {
	window.location.href = 'reseller-search-page';
}


function validateUpdateReseller() {
	
	if ( !validateCountry() | !validateState() |!validateZip() 
			| !validateCity() | !validateAddress1() |!validatePhone()
			| !validateResellerName()|!validateContactName()|!validateEmailId()|!validateStatus()) {
		return false;
	}
	return true;
}

/*var elmt = document.getElementById('resellerName');

elmt.addEventListener('keydown', function (event) {
    if (event.which === 32) {
        elmt.value = elmt.value.replace(/^\s+/, '');
        if (elmt.value.length < 1) {
            event.preventDefault();
        }
    }
});*/