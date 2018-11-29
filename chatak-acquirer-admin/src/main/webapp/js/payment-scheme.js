var usernameFlag = null;
var paymentSchemeNam = null;
function goToPaymentSchemeSearch() {
	window.location.href = 'payment-scheme-search-urlpage';
}

function openCancelConfirmationPopup() {

	if ((isEmpty(get('paymentSchemeName').value.trim()))
			&& (isEmpty(get('contactName').value.trim()))
			&& (isEmpty(get('typeOfCard').value.trim()))
			&& (isEmpty(get('contactEmail').value.trim()))
			&& (isEmpty(getVal('contactPhone').trim()))
			&& (isEmpty(get('rid').value.trim()))) {
		window.location.href = 'payment-scheme-search-urlpage';
	}

	else {
		$('#my_popup1').popup("show");
	}
}

function closeCancelConfirmationPopup() {
	$('#my_popup1').popup("hide");
}

function viewPaymentSchemeInfo(paymentSchemeId) {
	get('paymentSchemeId').value = paymentSchemeId;
	document.forms["viewPaymentSchemeForm"].submit();
}

function editPaymentScheme(paymentSchemeId) {
	$('#getpaymentschemeId').val(paymentSchemeId);
	document.forms["editPaymentSchemeForm"].submit();
}

function viewPaymentScheme(paymentSchemeId) {
	$('#getViewPaymentSchemeId').val(paymentSchemeId);
	document.forms["viewPaymentSchemeForm"].submit();
}

function resetPaymentScheme(){
	
	window.location.href = 'payment-scheme-search-urlpage';
}

function validatePaymentSchemeName() {
	var paymentSchemeName = get('paymentSchemeName').value.trim();
	var spaceRegx = /^[A-Za-z0-9@][A-Za-z0-9!#$%^&*'()+\=\[\]{};:"\\|,.<>\/? ]*$/;
	if (isEmpty(paymentSchemeName)) {
		setError(get('paymentSchemeName'), webMessages.pleaseEnterPaymentSchemeName);
		loadMsgTitleText();
		return false;
	} else if (!spaceRegx.test(paymentSchemeName)) {
		setError(get('paymentSchemeName'), webMessages.invalidPaymentSchemeName);
		loadMsgTitleText();
		return false;
	} else {
		doAjaxPaymentSchemeNameDuplicate();
		if (paymentSchemeNam == false) {
			return true;
		} else {
			return false;
		}
	}
}

function validateContactName() {
	var contactName = get('contactName').value.trim();
	var spaceRegx = /^[A-Za-z0-9@][A-Za-z0-9!#$%^&*'()+\=\[\]{};:"\\|,.<>\/? ]*$/;
	if (isEmpty(contactName)) {
		setError(get('contactName'), webMessages.pleaseEnterContactName);
		loadMsgTitleText();
		return false;
	} else if (!spaceRegx.test(contactName)) {
		setError(get('contactName'), webMessages.invalidContactName);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('contactName'), '');
		return true;
	}
}

function validateTypeOfCard() {
	var typeOfCard = get('typeOfCard').value.trim();
	if (isEmpty(typeOfCard)) {
		setError(get('typeOfCard'), webMessages.pleaseSelectTypeofCard);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('typeOfCard'), '');
		return true;
	}
}

function validateContactEmail() {
	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var contactEmail = get('contactEmail').value.trim();
	if (isEmpty(contactEmail)) {
		setError(get('contactEmail'), webMessages.pleaseEnterEmailID);
		loadMsgTitleText();
		return false;
	} else if (reg.test(contactEmail) == false) {
		setError(get('contactEmail'), webMessages.invalidEmailID);
		loadMsgTitleText();
		return false;
	} 
	else {
			doAjaxFetchMailIdAvailable();
			
			if (usernameFlag == false) {
				return true;
			} else {
				return false;
			}
		}
  }

function validateContactPhone() {
	var contactPhone = getVal('contactPhone').trim();
	if (isEmpty(contactPhone)) {
		setError(get('contactPhone'), webMessages.pleaseEnterPhoneNumber);
		loadMsgTitleText();
		return false;
	} else if (!isDigit(contactPhone) || contactPhone.length < 10 || contactPhone.length > 13
			|| contactPhone == 0) {
		setError(get('contactPhone'), webMessages.invalidPhoneNumber);
		loadMsgTitleText();
		return false;
	} else if (contactPhone.charAt(parseInt("0")) == "0") {
		setError(get('contactPhone'), webMessages.phoneCannotStartwithZero);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('contactPhone'), '');
		return true;
	}
}

/*function validateStatus() {
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
}*/

function validateCreatePaymentScheme() {
	  
	if (!validatePaymentSchemeName() 
			    | !validateContactName()
				| !validateTypeOfCard()
				| !validateContactEmail()
				| !validateContactPhone()
				| !validateRid()) {
			return false;
		}
	return true;
}
	
function resetPaymentSchemeInfo() {
	resetSuccessAndErrorMsg();
	get('typeOfCard').value = "";
	setError(get('typeOfCard'), '');
	get('paymentSchemeName').value = "";
	setError(get('paymentSchemeName'), '');
	get('contactName').value = "";
	setError(get('contactName'), '');
	get('contactEmail').value = "";
	setError(get('contactEmail'), '');
	get('contactPhone').value = "";
	setError(get('contactPhone'), '');
	get('rid').value = "";
	setError(get('rid'), '');
}

function editPaymentScheme(paymentschemeId) {
	get('getpaymentschemeId').value = paymentschemeId;
	document.forms["editPaymentSchemeForm"].submit();
	
}
function validateUpdatePaymentScheme() {   
	if (!validateContactName() 
			| !validateUpdateContactEmail() 
			| !validateContactPhone() 
			| !validateRid()) {
		return false;
	} 
		return true;
	} 

function validateRid() {
	var rid = get('rid').value.trim();
	if (isEmpty(rid)) {
		setError(get('rid'), webMessages.pleaseEnterRID);
		loadMsgTitleText();
		return false;
	} else if(ishexDeci(rid)) {
		
	setError(get('rid'), webMessages.pleaseEnterValidRID);
	loadMsgTitleText();
	return false;
}
	else {
		setError(get('rid'), '');
		return true;
	}
}

function ishexDeci(val) {
	var regex = /[0-9A-Fa-f]{10}/g;
	if (regex.test(val)) {
		return false;
	} else {
		return true;
	}
}

function doAjaxFetchMailIdAvailable() {
	var mailId = get('contactEmail').value.trim();
	$.ajax({
		type : "GET",
		url : "uniqueContactEmail?contactEmail=" + mailId,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorCode === '00') {
				setError(get('contactEmail'), '');
				setLable('confirmMcontactEmail', get('contactEmail').value.trim());
				usernameFlag = false;
			} else {
				setError(get('contactEmail'), webMessages.emailidAlreadyinUse);
				loadMsgTitleText();
				usernameFlag = true;
			}
		},
		error : function(e) {
		}
	});

}

function validateUpdateContactEmail() {
	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var contactEmail = get('contactEmail').value.trim();
	if (isEmpty(contactEmail)) {
		setError(get('contactEmail'), webMessages.thisFieldisMandatory);
		loadMsgTitleText();
		return false;
	} else if (reg.test(contactEmail) == false) {
		setError(get('contactEmail'), webMessages.invalidEmailID);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('contactEmail'), '');
		return true;
	}
}

function doAjaxPaymentSchemeNameDuplicate() {
	var paymentSchemeId = get('paymentSchemeName').value.trim();

	$.ajax({
		type : "GET",
		url : "uniquePaymentSchemeName?paymentSchemeId=" + paymentSchemeId,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorCode === '00') {
				setError(get('paymentSchemeName'), '');
				setLable('confirmMpaymentSchemeName', 	get('paymentSchemeName').value.trim());
				loadMsgTitleText();
				paymentSchemeNam = false;

			} else {
				setDiv("paymentSchemeNameEr", webMessages.paymentSchemeNameAlreadyinUse);
				paymentSchemeName = true;
			}
		},
		error : function(e) {
		}
	});
}