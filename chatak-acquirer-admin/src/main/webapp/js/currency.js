/**
 * 
 */

function goToCurrencySearch() {

	 window.location.href = 'show-currency-search';
}

function openCancelConfirmationPopup() {
	$('#my_popup1').popup("show");
}

function closeCancelConfirmationPopup() {
	$('#my_popup1').popup("hide");
}


function resetCurrencyCreate() {

	window.location.href = 'currency-create-page';
}

function editCurrency(id) {
	get('getId').value = id;
	document.forms["editCurrencyForm"].submit();
}
function viewCurrency(id) {
	get('getViewId').value = id;
	document.forms["viewCurrencyForm"].submit();

}
var deleteId;
function confirmDeleteCurrency(id) {
	$('#deletePopup').popup("show");
	deleteId=id;
}
function deleteData() {
	get('getDeleteId').value = deleteId;
	document.forms["deleteCurrencyForm"].submit();
}

function deleteMerchant(id) {
	get('getDeleteId').value = id;
	document.forms["deleteCurrencyForm"].submit();
}

function validteCurrencyName() {
	var currencyName = get('currencyName').value.trim();
	var spaceRegx = /^[A-Za-z0-9@][A-Za-z0-9!#$%^&*'()+\=\[\]{};:"\\|,.<>\/? ]*$/;
	if (isEmpty(currencyName)) {
		setError(get('currencyName'),
				webMessages.validationthisfieldismandatory);
		return false;
	} else if (!spaceRegx.test(currencyName)) {
		setError(get('currencyName'), webMessages.pleaseentervalidcurrencyname);
		return false;
	} else {
		setError(get('currencyName'), '');
		return true;
	}
}

function validateCurrencyCodeNumeric() {
	var currencyCodeNumeric = get('currencyCodeNumeric').value.trim();
	var spaceRegx = /[0-9]|\./;

	if (isEmpty(currencyCodeNumeric)) {
		setError(get('currencyCodeNumeric'),
				webMessages.validationthisfieldismandatory);
		return false;
	} else if (!spaceRegx.test(currencyCodeNumeric)) {
		setError(get('currencyCodeNumeric'),
				webMessages.pleaseentervalidvurrencycodenumeric);
		return false;
	} else if (!isDigit(currencyCodeNumeric) || currencyCodeNumeric.length < 3
			|| currencyCodeNumeric.length > 3 || currencyCodeNumeric == 0) {
		setError(get('currencyCodeNumeric'), webMessages.providecurrencycode);
		return false;
	} else {
		setError(get('currencyCodeNumeric'), '');
		return true;
	}
}

function validateCurrencyCodeAlpha() {
	var currencyCodeAlpha = get('currencyCodeAlpha').value.trim();
	var spaceRegx = /^[a-zA-Z]+$/;

	if (isEmpty(currencyCodeAlpha)) {
		setError(get('currencyCodeAlpha'),
				webMessages.validationthisfieldismandatory);
		return false;
	} else if (!spaceRegx.test(currencyCodeAlpha)) {
		setError(get('currencyCodeAlpha'),	webMessages.validatecurrencycodealpha);
		return false;
	} else if (currencyCodeAlpha.length < 3 || currencyCodeAlpha.length > 3
			|| currencyCodeAlpha == 0) {
		setError(get('currencyCodeAlpha'), webMessages.providecurrencyalphcode);
		return false;
	} else {
		setError(get('currencyCodeAlpha'), '');
		return true;
	}
}

function validateCurrencySymbol() {
	var currencySymbol = get('currencySymbol').value.trim();

	if (isEmpty(currencySymbol)) {
		setError(get('currencySymbol'),
				webMessages.validationthisfieldismandatory);
		return false;
	} else {
		setError(get('currencySymbol'), '');
		return true;
	}

}

function validateCurrencySeparatorPosition() {

	var currencySeparatorPosition = get('currencySeparatorPosition').value.trim();
	var spaceRegx = /[0-4]|\./;

	if (isEmpty(currencySeparatorPosition)) {
		setError(get('currencySeparatorPosition'),
				webMessages.validationthisfieldismandatory);
		return false;
	} 
	else if (!spaceRegx.test(currencySeparatorPosition)) {
		setError(get('currencySeparatorPosition'),	webMessages.providebetweenzerotofour);
		return false;
	}
	else {
		setError(get('currencySeparatorPosition'), '');
		return true;
	}
}

function validateStatus() {
	var status = get('status').value.trim();
	if (isEmpty(status)) {
		setError(get('status'), webMessages.validationthisfieldismandatory);
		return false;
	} else {
		setError(get('status'), '');
		return true;
	}
}

function validatecurrencyThousSeparatorUnit() {
	var regex = /^[.,]+$/;
	var currencyThousandsUnit = get('currencyThousandsUnit').value.trim();
	if (isEmpty(currencyThousandsUnit)) {
		setError(get('currencyThousandsUnit'),
				webMessages.validationthisfieldismandatory);
		return false;
	} else if (!regex.test(currencyThousandsUnit)) {
		setError(get('currencyThousandsUnit'),
				webMessages.provideonlycommaordecemal);
		return false;
	} else {
		setError(get('currencyThousandsUnit'), '');
		return true;
	}
}

function validatecurrencyMinorSeparatorUnit() {
	var regex = /^[.,]+$/;
	var currencyMinorUnit = get('currencyMinorUnit').value.trim();
	if (isEmpty(currencyMinorUnit)) {
		setError(get('currencyMinorUnit'),
				webMessages.validationthisfieldismandatory);
		return false;
	} else if (!regex.test(currencyMinorUnit)) {
		setError(get('currencyMinorUnit'),
				webMessages.provideonlycommaordecemal);
		return false;
	} else {
		setError(get('currencyMinorUnit'), '');
		return true;
	}
}

function validateCreateCurrency() {
	if (!validteCurrencyName() | !validateCurrencyCodeNumeric()
			| !validateCurrencySeparatorPosition()
			| !validatecurrencyMinorSeparatorUnit()
			| !validatecurrencyThousSeparatorUnit()
			| !validateCurrencyExport()) {
		return false;
	}
	return true;
}

function isNumberKey(evt) {
	var charCode = (evt.which) ? evt.which : evt.keyCode
	return !(charCode > 31 && (charCode < 48 || charCode > 57));
}

function fetchCurrencyData(currencyCodeNumeric) {
	if (currencyCodeNumeric != '') {
		getCurrencyData(currencyCodeNumeric);
	}
}

function getCurrencyData(currencyName) {
	$.ajax({
		type : "GET",
		url : "getCurrencyDataByCurrencyName?currencyName=" + currencyName,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorMessage == "SUCCESS") {
				setValue('currencyCodeNumeric', obj.currencyCodeNumeric);
				setValue('currencyCodeAlpha',obj.currencyCodeAlpha);
			}
		},
		error : function(e) {
		}
	});
}

function validateCurrencyAlpha() {
	var regex = /^[a-zA-Z ]*$/;
	var currencyCodeAlpha = get('currencyCodeAlpha').value.trim();
	if (isEmpty(currencyCodeAlpha) | regex.test(currencyCodeAlpha)) {
		setDiv("currencyCodeAlphaEr", "");
		return true;
	} else {
		setDiv("currencyCodeAlphaEr", webMessages.validationShouldcontainonlyalphabets);
		loadMsgTitleText();
		return false;
	}
}

function validateCurrencyNumeric() {
	var regex = /^[0-9]*$/;
	var currencyCodeNumeric = get('currencyCodeNumeric').value.trim();
	if (isEmpty(currencyCodeNumeric) | regex.test(currencyCodeNumeric)) {
		setDiv("currencyCodeNumericEr", "");
		return true;
	} else {
		setDiv("currencyCodeNumericEr", webMessages.PleaseEnterNumericsOnly);
		loadMsgTitleText();
		return false;
	}
}

function validateSearchData() {
	if (!validateCurrencyAlpha() | !validateCurrencyNumeric() | !clientValidation('currencyName','firstlast_name_not_mandatory','currencyName_Error') | !clientValidation('currencyExponent','amount_False','currencyExponentEr')) {
		return false;
	}
	return true;
}

function validateCurrencyExport() {
	var currencyexponent = get('currencyExponent').value.trim();
	var spaceRegx = /^[0-4]/;

	if (isEmpty(currencyexponent)) {
		setError(get('currencyExponent'),	webMessages.validationthisfieldismandatory);
		return false;
	} 
	else if (!spaceRegx.test(currencyexponent)) {
		setError(get('currencyExponent'),	webMessages.providebetweenzerotofour);
		return false;
	}
	else {
		setError(get('currencyExponent'), '');
		return true;
	}
}
