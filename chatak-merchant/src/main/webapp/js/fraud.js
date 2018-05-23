function validateIP() {
	var reg = /^(\d|[1-9]\d|1\d\d|2([0-4]\d|5[0-5]))\.(\d|[1-9]\d|1\d\d|2([0-4]\d|5[0-5]))\.(\d|[1-9]\d|1\d\d|2([0-4]\d|5[0-5]))\.(\d|[1-9]\d|1\d\d|2([0-4]\d|5[0-5]))$/;
	var addIP = get('addIP').value.trim();

	if (isEmpty(addIP)) {
		setError(get('addIP'), webMessages.pleaseenterIP);
		loadMsgTitleText();
		return false;
	} else if (reg.test(addIP) == false) {
		setError(get('addIP'), webMessages.invalidIP);
		loadMsgTitleText();
		return false;
	} else if (isDuplicateIp()) {
		setError(get('addIP'), webMessages.duplicateIP);
		loadMsgTitleText();
	} else {
		setError(get('addIP'), '');
		return true;
	}
}

function validateEmailId() {

	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var emailAddress = get('emailId').value.trim();

	if (isEmpty(emailAddress)) {
		setError(get('emailId'),webMessages.pleasenteremailid );
		loadMsgTitleText();
		return false;
	}

	else if (reg.test(emailAddress) == false) {

		setError(get('emailId'), webMessages.invalidemailid);
		loadMsgTitleText();
		return false;
	} else if (isDuplicateEmailId()) {
		setError(get('emailId'), webMessages.duplicateemailid);
		loadMsgTitleText();
	} else {
		if (alpha.indexOf(emailAddress.charAt(0)) == -1) {
			setError(get('emailId'), webMessages.invalidemailid);
			loadMsgTitleText();
			return false;
		}

		setError(get('emailId'), '');

		return true;
	}

}

function validateCountry() {
	var country = get('country').value.trim();
	if (isEmpty(country)) {
		setError(get('country'), webMessages.pleaseentercountry);
		loadMsgTitleText();
		return false;
	} else if (isDuplicateCountry()) {
		setError(get('country'), webMessages.duplicatecountry);
		loadMsgTitleText();
	} else {
		setError(get('country'), '');

		return true;
	}
}

/*
 * else if (!isChar(country)) { setError(get('country'), 'Invalid Country');
 * return false; }
 */

function validateBin() {
	var bin = get('bin').value.trim();
	if (isEmpty(bin)) {
		setError(get('bin'), webMessages.pleaseenterbIN);
		loadMsgTitleText();
		return false;
	} else if (!isDigit(bin)) {
		setError(get('bin'), webMessages.invalidBIN);
		loadMsgTitleText();
		return false;
	} else if ((bin.length < 4) || (bin.length > 6)) {
		setError(get('bin'), webMessages.invalidbINshould);
		loadMsgTitleText();
		return false;
	} else if (isDuplicateBin()) {
		setError(get('bin'), webMessages.duplicatebin);
		loadMsgTitleText();
	} else {
		setError(get('bin'), '');

		return true;
	}
}

function validateSave() {
	var value1 = $.map($('#iPMultiple option'), function(e) {
		return e.value;
	});
	document.getElementById("deniedIp").value = value1.join(',');

	var value1 = $.map($('#countryMultiple option'), function(e) {
		return e.value;
	});
	document.getElementById("deniedCountry").value = value1.join(',');

	var value1 = $.map($('#eMailMultiple option'), function(e) {
		return e.value;
	});
	document.getElementById("deniedEMail").value = value1.join(',');

	var value1 = $.map($('#binMultiple option'), function(e) {
		return e.value;
	});
	document.getElementById("deniedBin").value = value1.join(',');

	if (!validateSave2() | !validateSave1()) {
		return false;
	} else
		return true;
}

function validateSave2() {
	if (!get('ipCheckbox').checked) {
		if (!get('countryCheckbox').checked) {
			if (!get('binCheckbox').checked) {
				if (!get('emailCheckbox').checked) {
					if (get('iPMultiple').options.length <= 0
							&& get('countryMultiple').options.length <= 0
							&& get('countryMultiple').options.length <= 0
							&& get('binMultiple').options.length <= 0) {
						return false;
					} else
						return true;
				} else
					return true;
			} else
				return true;
		} else
			return true;
	} else
		return true;
}

function validateSave1() {
	var flag = true;
	if (!validateIPMultiSelect() | !validateCountryMultiSelect()
			| !validateBinMultiSelect() | !validateEmailMultiSelect()) {

		return false;
	}
	return flag;
}

function validateIPMultiSelect() {
	var multiEle = get('iPMultiple');
	if (get('ipCheckbox').checked && multiEle.options.length <= 0) {
		setDiv('iPMultipleEr', webMessages.thisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else {
		setDiv('iPMultipleEr', "");
		return true;
	}
}

function validateCountryMultiSelect() {
	var multiEle = get('countryMultiple');
	if (get('countryCheckbox').checked && multiEle.options.length <= 0) {
		setDiv('countryMultipleEr', webMessages.thisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else {
		setDiv('countryMultipleEr', "");
		return true;
	}
}

function validateBinMultiSelect() {
	var multiEle = get('binMultiple');
	if (get('binCheckbox').checked && multiEle.options.length <= 0) {
		setDiv('binMultipleEr', webMessages.thisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else {
		setDiv('binMultipleEr', "");
		return true;
	}
}

function validateEmailMultiSelect() {
	var multiEle = get('eMailMultiple');
	if (get('emailCheckbox').checked && multiEle.options.length <= 0) {
		setDiv('eMailMultipleEr', webMessages.thisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else {
		setDiv('eMailMultipleEr', "");
		return true;
	}
}
function cancel() {
	window.location.href = "dash-board";
}

function isDuplicateIp() {
	var selectedIp = document.getElementById("addIP").value;
	var select = document.getElementById("iPMultiple");
	var numberOfIps = select.options.length;

	for (var i = 0; i < numberOfIps; i++) {
		var ip = document.getElementById("iPMultiple").options[i].value;

		if (ip === selectedIp) {
			return true;
		} 
	}
	return false;
}

function isDuplicateEmailId() {
	var selectedEmailId = document.getElementById("emailId").value;
	var select = document.getElementById("eMailMultiple");
	var numberOfEmails = select.options.length;

	for (var i = 0; i < numberOfEmails; i++) {
		var email = document.getElementById("eMailMultiple").options[i].value;

		if (email.toUpperCase() === selectedEmailId.toUpperCase()) {
			return true;
		} 
	}
	return false;
}

function isDuplicateBin() {
	var selectedBin = document.getElementById("bin").value;
	var select = document.getElementById("binMultiple");
	var numberOfBins = select.options.length;

	for (var i = 0; i < numberOfBins; i++) {
		var bin = document.getElementById("binMultiple").options[i].value;

		if (bin === selectedBin) {
			return true;
		} 
	}
	return false;
}

function isDuplicateCountry() {
	var compare = document.getElementById("country");
	var selectedCountry = compare.options[compare.selectedIndex].value;
	var select = document.getElementById("countryMultiple");
	var numberOfCountrys = select.options.length;

	for (var i = 0; i < numberOfCountrys; i++) {
		var country = document.getElementById("countryMultiple").options[i].value;

		if (country === selectedCountry) {
			return true;
		}
	}
	return false;
}



//NEW ADDED FOR ADVANCED FRAUD

function validateAdvancedFraud() {
	var flag = true;
	
	var filterType = getVal('filterType');
	if (filterType == "Velocity") {
	
			if (	!clientValidation('filterType', 'acceptance', 'filterType_ErrorDiv')
					| !clientValidation('filterOn', 'acceptance','filterOn_ErrorDiv')
					| !clientValidation('duration', 'acceptance','duration_ErrorDiv')
					| !clientValidation('transactionLimit', 'credit_amount', 'transactionLimit_ErrorDiv')
					| !clientValidation('action', 'acceptance','action_ErrorDiv')) {
				flag = false;
				return flag;
			}
	}	
	
	if (filterType == "Threshold") {
			if (	!clientValidation('filterType', 'acceptance', 'filterType_ErrorDiv')
					| !clientValidation('filterOn', 'acceptance','filterOn_ErrorDiv')
					| !clientValidation('duration', 'acceptance','duration_ErrorDiv')
					| !clientValidation('maxLimit', 'credit_amount', 'maxLimit_ErrorDiv')
					| !clientValidation('action', 'acceptance','action_ErrorDiv')) {
				flag = false;
				return flag;
			}
	}
	
	if (filterType == "") {
		if (	!clientValidation('filterType', 'acceptance', 'filterType_ErrorDiv')
				| !clientValidation('filterOn', 'acceptance','filterOn_ErrorDiv')
				| !clientValidation('duration', 'acceptance','duration_ErrorDiv')
				| !clientValidation('transactionLimit', 'credit_amount', 'transactionLimit_ErrorDiv')
				| !clientValidation('maxLimit', 'credit_amount', 'maxLimit_ErrorDiv')
				| !clientValidation('action', 'acceptance','action_ErrorDiv')) {
			flag = false;
			return flag;
		}
}
	
	return flag;
}

function editAdvancedFraud(id, merchantCode) {
	get('getId').value = id;
	get('getMerchantCode').value = merchantCode;
	document.forms["editAdvancedFraudForm"].submit();
}

function confirmDeleteAdvancedFraud(id, merchantCode) {
	var r = confirm("Press Ok to confirm deletion");
	if (r == true) {
		get('getId1').value = id;
		get('getMerchantCode1').value = merchantCode;
		document.forms["deleteAdvancedFraudForm"].submit();
	} else {
		return;
	}
}

function cancelAdvancedFraud() {
	window.location.href = "fraud-advanced";
}

function showValidateParameters() {
	var filterType = getVal('filterType');
	if (filterType == "Velocity") {
		$("#transactionLimit1").show();
		$("#maxLimit1").hide();
	}

	else if (filterType == "Threshold") {
		$("#transactionLimit1").hide();
		$("#maxLimit1").show();
	}
	
	else if (filterType == "") {
		$("#transactionLimit1").hide();
		$("#maxLimit1").hide();
	}
}