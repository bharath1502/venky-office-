var usernameFlag = null;
function cancelCreateSwitch() {
	window.location.href = 'switch-search';
}

function goToSwitchSearch() {
	window.location.href = 'switch-search';
}

function openCancelConfirmationPopup() {

	if ((isEmpty(get('switchName').value.trim()))
			&& (isEmpty(get('switchType').value.trim()))
			&& (isEmpty(get('primarySwitchURL').value.trim()))
			&& (isEmpty(getVal('primarySwitchPort').trim()))
			&& (isEmpty(get('secondarySwitchURL').value.trim()))
			&& (isEmpty(getVal('secondarySwitchPort').trim()))
			&& (isEmpty(getVal('priority').trim()))) {
		$('#my_popup1').popup("show");
	}

	else {
		$('#my_popup1').popup("show");
	}
}

function closeCancelConfirmationPopup() {
	$('#my_popup1').popup("hide");
}

function viewSwitchInfo(switchId) {
	get('switchViewId').value = switchId;
	document.forms["viewSwitchForm"].submit();
}

function editSwitch(switchId) {
	get('getSwitchId').value = switchId;
	document.forms["editSwitchForm"].submit();
}

function validateCreateSwitch() {
	var flag = true;
	if (!clientValidation('switchName','middle_name','switchNameEr') | !validatePrimarySwitchURL()
			| !validatePrimarySwitchPort() | !validateSecondarySwitchURL() | !validateSwitchType()
			| !validateSecondarySwitchPort() | !validatePriority()) {
		return false;
	}
	return flag;
}

function validateSwitchName() {
	var switchName = get('switchName').value.trim();
	var spaceRegx = /^[A-Za-z0-9@][A-Za-z0-9!#$%^&*'()+\=\[\]{};:"\\|,.<>\/? ]*$/;
	if (isEmpty(switchName)) {
		setError(get('switchName'), webMessages.pleaseEnterSwitchName);
		loadMsgTitleText();
		return false;
	} else if (!spaceRegx.test(switchName)) {
		setError(get('switchName'), webMessages.invalidSwitchName);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('switchName'), '');
		return true;
	}
}

function validateSwitchType() {
	var switchType = get('switchType').value.trim();
	var switchValue = document.getElementById("switchType");
	var x = switchValue.options[switchValue.selectedIndex].text;
	if (isEmpty(switchType)) {
		setError(get('switchType'), webMessages.pleaseSelectSwitchType);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('switchType'), '');
		return true;
	}
}

function validatePrimarySwitchURL() {
	var reg = /(\w)+\.(\w)+\.(\w)/;
	var primarySwitchURL = get('primarySwitchURL').value.trim();
	var secondarySwitchURL = get('secondarySwitchURL').value.trim()
	if (isEmpty(primarySwitchURL)) {
		setError(get('primarySwitchURL'), webMessages.pleaseEnterPrimarySwitchURL);
		loadMsgTitleText();
		return false;
	} else if (reg.test(primarySwitchURL) == false) {
		setError(get('primarySwitchURL'), webMessages.invalidPrimarySwitchURL);
		loadMsgTitleText();
		return false;
	} 
	else {
		setError(get('primarySwitchURL'), '');
		return true;
	}
}

function validatePrimarySwitchPort() {
	var primarySwitchPort = getVal('primarySwitchPort').trim();
	if (isEmpty(primarySwitchPort)) {
		setError(get('primarySwitchPort'), webMessages.pleaseEnterPrimarySwitchPortNumber);
		loadMsgTitleText();
		return false;
	} else if (!isDigit(primarySwitchPort) || primarySwitchPort.length < 5 || primarySwitchPort.length > 5
			|| primarySwitchPort == 0) {
		setError(get('primarySwitchPort'), webMessages.invalidPrimarySwitchPortNumber);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('primarySwitchPort'), '');
		return true;
	}
}

function validateSecondarySwitchURL() {
	var reg =  /(\w)+\.(\w)+\.(\w)/;
	var secondarySwitchURL = get('secondarySwitchURL').value.trim();
	var primarySwitchURL = get('primarySwitchURL').value.trim();
	if (isEmpty(secondarySwitchURL)) {
		setError(get('secondarySwitchURL'), webMessages.pleaseEnterSecondarySwitchURL);
		loadMsgTitleText();
		return false;
		
	} else if (reg.test(secondarySwitchURL) == false) {
		setError(get('secondarySwitchURL'), webMessages.invalidSecondarySwitchURL);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('secondarySwitchURL'), '');
		return true;
	}
}

function validateSecondarySwitchPort() {
	var secondarySwitchPort = getVal('secondarySwitchPort').trim();
	if (isEmpty(secondarySwitchPort)) {
		setError(get('secondarySwitchPort'), webMessages.pleaseEnterSecondarySwitchPortNumber);
		loadMsgTitleText();
		return false;
	} else if (!isDigit(secondarySwitchPort) || secondarySwitchPort.length < 5
			|| secondarySwitchPort.length > 5 || secondarySwitchPort == 0) {
		setError(get('secondarySwitchPort'),
				webMessages.invalidSecondarySwitchPortNumber);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('secondarySwitchPort'), '');
		return true;
	}
}

function validatePriority() {
	var priority = getVal('priority').trim();
	if (isEmpty(priority)) {
		var priorityVal = 1;
		get('priority').value = priorityVal;
		return true;
	} else if (!isDigit(priority) || priority.length > 1 || priority == 0) {
		setError(get('priority'), webMessages.invalidPriority);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('priority'), '');
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

function resetSwitchInfo() {
	resetSuccessAndErrorMsg();
	$('#switchType').val('SOCKET');
	get('switchName').value = "";
	setError(get('switchName'), '');
	get('primarySwitchURL').value = "";
	setError(get('primarySwitchURL'), '');
	get('primarySwitchPort').value = "";
	setError(get('primarySwitchPort'), '');
	get('secondarySwitchURL').value = "";
	setError(get('secondarySwitchURL'), '');
	get('secondarySwitchPort').value = "";
	setError(get('secondarySwitchPort'), '');
	get('priority').value = "";
	setError(get('priority'), '');
}

$('#primarySwitchURL').keypress(function(e) {
	if (e.which === 32)
		return false;
});

$('#secondarySwitchURL').keypress(function(e) {
	if (e.which === 32)
		return false;
});

function searchValidation(){
	if(!clientValidation('switchName','middle_name','switchNameEr')
			| !clientValidation('primarySwitchURL','url','primarySwitchURLEr')
			| !clientValidation('primarySwitchPort','switch_port_validation','primarySwitchPortEr')
			| !clientValidation('secondarySwitchURL','url','secondarySwitchURLEr')
			| !clientValidation('secondarySwitchPort','switch_port_validation','secondarySwitchPortEr')
			| !clientValidation('priority','priority_validation','priorityEr')){
		return false;
	}
	return true;
}
