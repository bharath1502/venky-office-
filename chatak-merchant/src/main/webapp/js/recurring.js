var usernameFlag = null;

function resetAll() {
	document.forms["resubmitForm"].submit();
}

function getMonth(id) {
	var month = getVal(id);
}

function getYear(id) {
	var year = getVal(id);
}

function getMonthYear() {
	var month = getVal('month');
	var year = getVal('year');
	var monthYear = month.concat("/" + year);
	setValue('expDt', monthYear);
}

function setTotal() {
	var amount = getVal('amount');
	var tax = getVal('tax');
	var total = Number(amount) + Number(tax);

	setValue('total', total);
}

function validateRecurringCustmer() {
	var flag = true;
	if (!clientValidation('firstName', 'first_name', 'firstName_ErrorDiv')
			| !clientValidation('lastName', 'last_name', 'lastName_ErrorDiv')
			| !validateBusinessName()
			| !validateEmailId()
			| !validateMobilePhone('mobileNumber')
			| !clientValidation('status', 'status', 'status_ErrorDiv')
			| !clientValidation('address1', 'address', 'address1_ErrorDiv')
			| !clientValidation('address2', 'address', 'address2_ErrorDiv')
			| !clientValidation('area', 'address', 'area_ErrorDiv')
			| !clientValidation('city', 'pcity', 'city_ErrorDiv')
			| !clientValidation('country', 'country', 'country_ErrorDiv')
			| !clientValidation('state', 'state', 'state_ErrorDiv')
			| !zipCodeNotEmpty('zipCode') | !isTermsChecked()
			| !validateDayTimePhone('daytimePhone')
			| !validateEveningTimePhone('eveningPhone')) 
	{
		flag = false;
		return flag;
	}
	return flag;
}

function validateRecurringCustmerUpdate() {
	var flag = true;
	if (!clientValidation('firstName', 'first_name', 'firstName_ErrorDiv')
			| !clientValidation('lastName', 'last_name', 'lastName_ErrorDiv')
			| !validateBusinessName()
			| !validateUpdateEmailId()
			| !validateMobilePhone('mobileNumber')
			| !clientValidation('status', 'status', 'status_ErrorDiv')
			| !clientValidation('address1', 'address', 'address1_ErrorDiv')
			| !clientValidation('address2', 'address', 'address2_ErrorDiv')
			| !clientValidation('area', 'address', 'area_ErrorDiv')
			| !clientValidation('city', 'pcity', 'city_ErrorDiv')
			| !clientValidation('country', 'country', 'country_ErrorDiv')
			| !clientValidation('state', 'state', 'state_ErrorDiv')
			| !zipCodeNotEmpty('zipCode')
			| !validateDayTimePhone('daytimePhone')
			| !validateEveningTimePhone('eveningPhone')) {
		flag = false;
		return flag;
	}
	return flag;
}

function validateRecurringPayment() {
	var cardType = $('#cardType').val();
	var keychar = $('#cardNumber').val();
	/* alert(keychar); */
	/* alert(cardType) */
	if (cardType === "") {
		alert(webMessages.pleaseselectcardtype);
		return false;
	}
	if (cardType === "JCB") {
		if ((!(keychar.charAt(0) == "3"))) {
			alert(webMessages.jcbcardnumbershouldstartwith3);
			return false;
		}

	} else if (cardType === "Visa") {
		if ((!(keychar.charAt(0) == "4"))) {
			alert(webMessages.visacardnumbershouldstartwith4);
			return false;
		}
	} else if (cardType === "MC") {
		if ((!(keychar.charAt(0) == "5"))) {
			alert(webMessages.mcshouldstartwith5);
			return false;
		}

	} else {
		alert("failed");
	}
	var flag = true;
	if (      !clientValidation('cardType', 'cardType', 'cardType_ErrorDiv')
		   |	!clientValidation('cardNumber', 'card_Number','cardNumber_ErrorDiv')
		   |!clientValidation('nameOnCard', 'card_Prog_Desc',
					'nameOnCard_ErrorDiv') | !zipCodeNotEmpty('zipCode')
			| !validateExpDate() | !validateAmount()
			| !validateRadio('radio_err')) {
		flag = false;
		return flag;
	}
	return flag;
}

function validateRecurringContract() {
	var flag = true;
	if (!clientValidation('contractName', 'contractName',
			'contractName_ErrorDiv')
			| !clientValidation('startDate', 'startDate', 'startDate_ErrorDiv')
			| !clientValidation('endDate', 'endDate', 'endDate_ErrorDiv')
			| !clientValidation('amount', 'amount', 'contractAmount_ErrorDiv')
			| !clientValidation('tax', 'tax', 'tax_ErrorDiv')
			| !clientValidation('status', 'status', 'status_ErrorDiv')
			| !validateDates('endDate', 'endDate_ErrorDiv')
			| !validateContractExecutionReprocess()
			| !validateContractExecution()
			| !clientValidation('recurringPaymentId', 'status',
					'recurringPaymentId_ErrorDiv')) {
		flag = false;
		return flag;
	}
	setTotal();
	return flag;
}

function validateAmount() {

	var flag = true;
	var checked = getRadioValue('immidiateCharge');
	if (checked == 'Yes') {
		if (!clientValidation('amount', 'amount', 'amount_Error_Div')) {
			flag = false;
			return flag;
		}
	}
	return flag;
}

function editRecurring(recurringCustInfoId) {
	get('getRecurringCustInfoId').value = recurringCustInfoId;
	document.forms["editRecurringForm"].submit();
}

// recurring previous

function recurringCreatePreviousPage(recurringCustInfoId) {
	get('getRecurringCustInfoId').value = recurringCustInfoId;
	document.forms["editRecurringCreateForm"].submit();
}

function recurringPaymentPreviousPage(recurringCustInfoId) {
	get('getRecurringPaymentInfoId').value = recurringCustInfoId;
	document.forms["recurringPaymentForm"].submit();
}

// recuring previous end

function submitForm() {
	document.forms["updateRecurringForm"].submit();
}

function paymentSubmt() {
	if (validateRecurringPayment()) {
		closePopup();
		return true;
	}
	return false;
	// document.forms["recurringPayment"].submit();
}

function formSubmit() {
	document.forms["contractSubmt"].submit();
}

function editRecurringPayment(recurringPaymentInfoId, recurringCutomerInfoId) {
	get('getRecurringPaymentInfoId').value = recurringPaymentInfoId;
	get('getRecurringCutomerInfoId').value = recurringCutomerInfoId;
	document.forms["editRecurringPaymentForm"].submit();
}

function editRecurringContract(recurringContractInfoId, recurringCustomerInfoId) {
	get('contractInfoId').value = recurringContractInfoId;
	get('customerInfoId').value = recurringCustomerInfoId;
	document.forms["editRecurringContractForm"].submit();
}

function confirmDeleteRecurring(recurringCustInfoId) {
	var r = confirm(webMessages.pressOktoconfirmdeletion);
	if (r == true) {
		get('getCustInfoId').value = recurringCustInfoId;
		document.forms["deleteRecurringForm"].submit();
	} else {
		return;
	}
}

function confirmDeleteRecurringContract(recurringContractInfoId) {
	var r = confirm(webMessages.pressOktoconfirmdeletion);
	if (r == true) {
		get('getContractInfoId').value = recurringContractInfoId;
		document.forms["deleteRecurringContractForm"].submit();
	} else {
		return;
	}
}

function confirmDeleteRecurringPayment(recurringPaymentInfoId) {
	var r = confirm(webMessages.pressOktoconfirmdeletion);
	if (r == true) {
		get('getPaymentInfoId').value = recurringPaymentInfoId;
		document.forms["deleteRecurringPaymentForm"].submit();
	} else {
		return;
	}
}

function validateExpDate() {
	var date = new Date();
	var month = date.getMonth();
	var year = date.getFullYear();

	var exMonth = document.getElementById("month").value;
	var exYear = document.getElementById("year").value;

	if (exMonth == "") {
		alert(webMessages.pleaseselecthemonth);
		return false;
	}
	if (exYear == "") {
		alert(webMessages.pleaseselectheyear);
		return false;
	}
	if (year > exYear || (year == exYear && month >= exMonth)) {
		alert(webMessages.expirydateisbeforetodaydatepleaseselectavalidexpirydate);
		return false;
	}
	return true;
}

function showBox() {

	var amount = getVal('immediate');
	if (amount == 'Yes') {
		$("#acc-charge-content").show();
	} else {
		$("#acc-charge-content").hide();
	}
}

function change() {

	$("#contractId").removeAttr("disabled");
}

function confirmDeleteMerchant(merchantId) {
	var r = confirm(webMessages.pressOktoconfirmdeletion);
	if (r == true) {
		get('getMerchantsId').value = merchantId;
		document.forms["deleteMercahntForm"].submit();

	} else {
		return;
	}
}

function cancel() {
	window.location.href = "recurring-search";
}

function isTermsChecked() {
	/* alert(id); */
	var checked = $('#terms:checkbox:checked').length;
	if (checked == 0) {
		setDiv("terms_ErrorDiv", webMessages.pleaseacceptthetermsandconditions);
		loadMsgTitleText();
		return false;
	} else {
		setDiv("terms_ErrorDiv", "");
		return true;
	}
}

function validateFax() {
	var fax = getVal('fax').trim();
	if (isEmpty(fax)) {
		setError(get('fax'), webMessages.pleasenterfax);
		loadMsgTitleText();
		return false;
	} else if (!isDigit(fax) || fax.length < 10 || fax.length > 13 || fax == 0) {
		setError(get('fax'), 'Invalid fax');
		return false;
	} else if (fax.charAt(parseInt("0")) == "0") {
		setError(get('fax'),webMessages.faxcannotstartwithzero);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('fax'), '');
		setLable('confirmMfax', fax);
		return true;
	}
}

function validateStatus() {
	var status = get('status').value.trim();
	var statusValue = document.getElementById("status");
	var x = statusValue.options[statusValue.selectedIndex].text;
	if (isEmpty(status)) {
		setError(get('status'),  webMessages.pleaseselectstatus);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('status'), '');
		return true;
	}
}

function validateBusinessName() {
	var businessName = get('businessName').value.trim();
	var spaceRegx = /^[A-Za-z0-9@][A-Za-z0-9!#$%^&*'()+\=\[\]{};:"\\|,.<>\/? ]*$/;
	if (isEmpty(businessName)) {
		setError(get('businessName'),webMessages.thisfieldismandatory );
		loadMsgTitleText();
		return false;
	} else if (!spaceRegx.test(businessName)) {
		setError(get('businessName'), webMessages.invalidcompanyname);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('businessName'), '');
		return true;
	}
}

function validateContractExecutionReprocess() {
	var contractExecutionReprocess = get('contractExecutionReprocess').value
			.trim();

	if (isEmpty(contractExecutionReprocess)) {
		setError(get('contractExecutionReprocess'),webMessages.thisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('contractExecutionReprocess'), '');
		return true;
	}
}

function validateContractExecution() {
	var contractExecution = get('contractExecution').value.trim();

	if (isEmpty(contractExecution)) {
		setError(get('contractExecution'), webMessages.thisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('contractExecution'), '');
		return true;
	}
}

function validateDayTimePhone(id) {
	var daytimePhone = getVal('daytimePhone').trim();
	if (daytimePhone == '___-___-____' || daytimePhone == '') {
		setError(get('daytimePhone'), '');
		return true;
	} else if (daytimePhone == '000-000-0000') {
		setError(get('daytimePhone'),webMessages.invalidphonenumber);
		loadMsgTitleText();
		return false;
	} else if ((daytimePhone.charAt(parseInt("0")) == "0")
			|| daytimePhone.length > 13) {
		setError(get('daytimePhone'), webMessages.phonecannotstartwithzero);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('daytimePhone'), '');
		return true;
	}
}

function validateEveningTimePhone(id) {
	var eveningPhone = getVal('eveningPhone').trim();
	if (eveningPhone == '___-___-____' || eveningPhone == '') {
		setError(get('eveningPhone'), '');
		return true;
	} else if (eveningPhone == '000-000-0000') {
		setError(get('eveningPhone'), webMessages.invalidphonenumber);
		loadMsgTitleText();
		return false;
	} else if ((eveningPhone.charAt(parseInt("0")) == "0")
			|| eveningPhone.length > 13) {
		setError(get('eveningPhone'), webMessages.phonecannotstartwithzero);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('eveningPhone'), '');
		return true;
	}
}

function validateMobilePhone(id) {
	var mobileNumber = getVal('mobileNumber').trim();
	if (mobileNumber == '___-___-____' || mobileNumber == '') {
		setError(get('mobileNumber'), webMessages.thisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else if (mobileNumber == '000-000-0000') {
		setError(get('mobileNumber'), webMessages.invalidphonenumber);
		loadMsgTitleText();
		return false;
	} else if ((mobileNumber.charAt(parseInt("0")) == "0")
			|| mobileNumber.length > 13) {
		setError(get('mobileNumber'), webMessages.phonecannotstartwithzero);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('mobileNumber'), '');
		return true;
	}
}

function resetRecurringCreateCustomer() {
	
	get('firstName').value = "";
	setDiv('firstName_ErrorDiv', '');
	get('lastName').value = "";
	setDiv('lastName_ErrorDiv', '');
	get('businessName').value = "";
	setError(get('businessName'), '');
	get('title').value = "";
	setDiv('title_ErrorDiv', '');
	get('department').value = "";
	setDiv('department_ErrorDiv', '');
	get('emailId').value = "";
	setError(get('emailId'), '');
	get('mobileNumber').value = "";
	setError(get('mobileNumber'), '');
	get('daytimePhone').value = "";
	setError(get('daytimePhone'), '');
	get('eveningPhone').value = "";
	setError(get('eveningPhone'), '');
	get('fax').value = "";
	setError(get('fax'), '');
	get('status').value = "";
	setDiv('status_ErrorDiv', '');
	get('address1').value = "";
	setDiv('address1_ErrorDiv', '');
	get('address2').value = "";
	setDiv('address2_ErrorDiv', '');
	get('address2').value = "";
	setDiv('address2_ErrorDiv', '');
	get('address3').value = "";
	setDiv('address3_ErrorDiv', '');
	get('area').value = "";
	setDiv('area_ErrorDiv', '');
	get('city').value = "";
	setDiv('city_ErrorDiv', '');
	get('country').value = "";
	setDiv('country_ErrorDiv', '');
	get('state').value = "";
	setDiv('state_ErrorDiv', '');
	get('zipCode').value = "";
	setError(get('zipCode'), '');
	get('terms').value = "";
	setDiv('terms_ErrorDiv', '');
	$('input').filter(':checkbox').prop('checked',false);
}

function validateEmailId() {
	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var emailId = get('emailId').value.trim();
	if (isEmpty(emailId)) {
		setError(get('emailId'), webMessages.thisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else if (reg.test(emailId) == false) {
		setError(get('emailId'), webMessages.invalidemailid);
		loadMsgTitleText();
		return false;
	} 
	else {
			doAjaxFetchMailIdAvailable();
			
			if (usernameFlag == false) {
				return false;
			} else {
				return true;
			}
		}
  }

function doAjaxFetchMailIdAvailable() {
	var emailId = get('emailId').value.trim();
	$.ajax({
		type : "GET",
		url : "uniqueRecurringEmailId?emailId=" + emailId,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorCode === '00') {
				setError(get('emailId'), '');
				usernameFlag = true;
			} else {
				setError(get('emailId'), webMessages.emailidalreadyinuse);
				loadMsgTitleText();
				usernameFlag = false;
			}
		},
		error : function(e) {
		}
	});
}

function validateUpdateEmailId() {
	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var emailId = get('emailId').value.trim();
	if (isEmpty(emailId)) {
		setError(get('emailId'), webMessages.thisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else if (reg.test(emailId) == false) {
		setError(get('emailId'), webMessages.invalidemailid);
		loadMsgTitleText();
		return false;
	} 
	else {
		doAjaxFetchMailIdAvailableUpdate();
		if (usernameFlag == false) {
			return false;
		} else {
			return true;
		}
  }
}

function doAjaxFetchMailIdAvailableUpdate() {
	var updatEmailId = get('emailId').value.trim();
	var customerId = get('customerId').value.trim();
	$.ajax({
		type : "GET",
		url : "uniqueRecurringUpdateEmailId?emailId=" + updatEmailId+ "&customerId="
		+ customerId,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorCode === '00') {
				setError(get('emailId'), '');
				usernameFlag = true;
			} else {
				setError(get('emailId'), webMessages.emailidalreadyinuse);
				loadMsgTitleText();
				usernameFlag = false;
			}
		},
		error : function(e) {
		}
	});
}
