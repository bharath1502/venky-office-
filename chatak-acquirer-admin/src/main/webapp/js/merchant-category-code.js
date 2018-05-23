function validateMcc() {
	var mcc = get('merchantCategoryCode').value.trim();
	if (isEmpty(mcc)) {
		setError(get('merchantCategoryCode'),webMessages.pleaseEnterMerchantCategoryCode);
		loadMsgTitleText();
		return false;
	} else if (!/^[a-zA-Z0-9]+$/.test(mcc)) {
		setError(get('merchantCategoryCode'), webMessages.alphaNumericsOnly);
		loadMsgTitleText();
		return false;
	} else if (mcc.length < 4 | mcc.length > 4) {
		setError(get('merchantCategoryCode'), webMessages.lengthofMCCshouldbe4Digit);
		loadMsgTitleText();
		return false;
	} else if (mcc === '0000' | mcc === '9999') {
		setError(get('merchantCategoryCode'), webMessages.invalidMCC);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('merchantCategoryCode'), '');
		return true;
	}
}

function validateTCCMultiSelect() {
	if ($('#tccMultiple option:selected').length == 0) {
		setDiv('tccMultipleEr', webMessages.pleaseSelectTransactionCategoryCodes);
		loadMsgTitleText();
		return false;
	} else if ($('#tccMultiple option:selected').length > 2) {
		setDiv('tccMultipleEr', webMessages.Only2TransactionCategoryCodesAllowed);
		loadMsgTitleText();
		return false;
	} else {
		setDiv('tccMultipleEr', "");
		return true;
	}
}

function validateDescription() {
	var description = get('description').value.trim();

	if (isEmpty(description)) {
		setError(get('description'), webMessages.pleaseEnterDescription);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('description'), '');
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
		setLable('confirmMstatus', x);
		return true;
	}
}*/

function validCreateMcc() {
	var value1 = $.map($('#tccMultiple option:selected'), function(e) {
		return e.value;
	});
	document.getElementById("selectedTcc").value = value1.join(',');

	if (!validateMcc() | !validateTCCMultiSelect() | !validateDescription()) {
		return false;
	} else
		return true;
}

function validUpdateMcc() {
	var value1 = $.map($('#tccMultiple option:selected'), function(e) {
		return e.value;
	});
	document.getElementById("selectedTcc").value = value1.join(',');

	if (!validateMcc() | !validateTCCMultiSelect() | !validateDescription()) {
		return false;
	} else
		return true;
}

function editMCC(mcc) {
	get('editId').value = mcc;
	document.forms["editMCCForm"].submit();
}

function viewMCCInfo(mcc) {
	get('viewId').value = mcc;
	document.forms["viewMCCForm"].submit();
}

var mccId;
function confirmDeleteMCC(mcc) {
	$('#deletePopup').popup("show");
	mccId = mcc;	
}

function deleteData(){
	get('getDeleteMCCId').value = mccId;
	document.forms["deleteMCCForm"].submit();
}

function cancelCreateOrUpdateMcc() {
	window.location.href = 'merchant-category-code-search';

}

function resetMerchantCategoryCode() {
	
	window.location.href = 'merchant-category-code-create';

}
function openCancelConfirmationPopup() {

	if ((isEmpty(get('merchantCategoryCode').value.trim()))
			&& (isEmpty(get('description').value.trim()))) {
		window.location.href = 'merchant-category-code-search';
	}

	else {
		$('#my_popup1').popup("show");
	}
}

function closeCancelConfirmationPopup() {
	$('#my_popup1').popup("hide");
}
