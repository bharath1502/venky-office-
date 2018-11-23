var firstNameRegx = /^[A-Za-z0-9@][A-Za-z0-9. ]*$/;

var lastNameRegx = /^[A-Za-z0-9@][A-Za-z0-9. ]*$/;

function validateProfileSubmit() {
	if (!validateAddress1() 
			| !validateCity()
			| !validateURL() /*&& validatefederalTaxId()*/ 
			| !validateState()
			| !validateCountry() 
			| !validatePin()
			| !validateBusinessName() 
			| !validateFirstName() 
			| !validateLastName()
			/*&& validateFax() && validatestateTaxId()
		    && validateBusinessStartDate()
			&& validateEstimatedYearlySale() && validateNoOfEmployees()
			| !validateMailingAddress1()
			| !validateMailingCity() 
			| !validateMailingState()
			| !validateMailingCountry() 
			| !validateMailingPin()*/) 
	{
		return false;
	}
	return true;
}

function validateAddress1() {
	var address1 = get('address1').value.trim();
	/* var addRegx = /^[A-Za-z0-9\#\$\&\,]+(\s{0,1}[a-zA-Z0-9,])*$/; */
	if (isEmpty(address1)) {
		setError(get('address1'), webMessages.pleasenteraddress1);
		loadMsgTitleText();
		return false;
	} else if (address1.length < 5) {
		setError(get('address1'), webMessages.invalidaddress1length);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('address1'), '');
		/*setLable('confirmMaddress1', address1);*/
		return true;
	}
}

function validateAddress2() {
	var address2 = get('address2').value.trim();
	if (address2.length > 0 && !isValidAddressChars(address2)) {
		setError(get('address2'), webMessages.invalidAddress2);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('address2'), '');
		/*setLable('confirmMaddress2', address2);*/
		return true;
	}
}
function validateCity() {
	var city = get('city').value.trim();
	var cityRegx = /^[A-Za-z0-9\#\$\&]+(\s{0,1}[a-zA-Z0-9,])*$/;
	if (isEmpty(city)) {
		setError(get('city'), webMessages.pleasentercity);
		loadMsgTitleText();
		return false;
	} else if (!cityRegx.test(city)) {
		setError(get('city'), webMessages.invalidcity);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('city'), '');
	/*	setLable('confirmMcity', city);*/
		return true;
	}
}

function validateState() {
	var state = get('state').value.trim();
	if (isEmpty(state)) {
		setError(get('state'), webMessages.pleaseselectstate);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('state'), '');
		/*setLable('confirmMstate', state);*/
		return true;
	}
}

function validateCountry() {
	var country = get('country').value.trim();
	if (isEmpty(country)) {
		setError(get('country'), webMessages.pleaseselectcountry);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('country'), '');
		/*setLable('confirmMcountry', country);*/
		return true;
	}
}

function validatePin() {
	var pin = getVal('pin');
	if (isEmpty(pin)) {
		setError(get('pin'), webMessages.pleasenterZipcode);
		loadMsgTitleText();
		return false;
	} else if ((pin.length < 3) || (pin.length > 9)) {
		setError(get('pin'), webMessages.invalidZipcode);
		loadMsgTitleText();
		return false;
	} else if (!isValidZipChars(pin)) {
		setError(get('pin'), webMessages.invalidZipcode);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('pin'), '');
		/*setLable('confirmMpin', pin);*/
		return true;
	}
}

function isValidZipChars(val) {
	var firstCharAlphaRegex = /^[A-Za-z0-9][  A-Za-z0-9 -_@ {}:?#$!()`\\*=]*$/;
	return firstCharAlphaRegex.test(val);
}

function submitCreateMerchant() {
	return true;
}

function editMerchant(merchantId) {
	get('getMerchantId').value = merchantId;
	document.forms["editMercahntForm"].submit();
}

function deleteMerchant(merchantId) {
	get('getMerchantsId').value = merchantId;
	document.forms["deleteMercahntForm"].submit();
}

function resetAllFields() {
	get('merchantCode').value = "";
	get('businessName').value = "";
	get('firstName').value = "";
	get('lastName').value = "";
	get('phone').value = "";
	get('status').value = "";
	get('city').value = "";
	get('country').value = "";
	get('emailId').value = "";
}

function validateMerchantCode() {
	var val = /[0-9]{6,16}$/;
	var merchantCode = get('merchantCode').value.trim();
	if (isEmpty(merchantCode)) {
		setError(get('merchantCode'), webMessages.pleaseEnterMerchantCode);
		loadMsgTitleText();
		return false;
	} else if (!val.test(merchantCode)) {
		setError(get('merchantCode'), webMessages.shouldbe6to16numerics);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('merchantCode'), '');
		/*setLable('confirmMmerchantCode', merchantCode);*/
		return true;
	}
}

function validateBusinessName() {
	var businessName = get('businessName').value.trim();
	var spaceRegx = /^[A-Za-z0-9@][A-Za-z0-9!#$%^&*'()+\=\[\]{};:"\\|,.<>\/? ]*$/;
	if (isEmpty(businessName)) {
		setError(get('businessName'), webMessages.pleaseEnterBusinessName);
		loadMsgTitleText();
		return false;
	} else if (!spaceRegx.test(businessName)) {
		setError(get('businessName'), webMessages.invalidBusinessName);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('businessName'), '');
		/*setLable('confirmMbusinessName', businessName);*/
		return true;
	}
}

function validateFirstName() {
	var firstName = get('firstName').value.trim();
	if (isEmpty(firstName)) {
		setError(get('firstName'), webMessages.pleasenterfirstname);
		loadMsgTitleText();
		return false;
	} else if (!firstNameRegx.test(firstName)) {
		setError(get('firstName'), webMessages.invalidfirstname);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('firstName'), '');
		/*setLable('confirmMfirstName', firstName);*/
		return true;
	}
}

function validateLastName() {
	var lastName = get('lastName').value.trim();
	if (isEmpty(lastName)) {
		setError(get('lastName'), webMessages.pleasenterlastname);
		loadMsgTitleText();
		return false;
	} else if (!lastNameRegx.test(lastName)) {
		setError(get('lastName'), webMessages.invalidlastname);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('lastName'), '');
		/*setLable('confirmMlastName', lastName);*/
		return true;
	}
}

function validatePhone() {
	var phone = getVal('phone').trim();
	if (isEmpty(phone)) {
		setError(get('phone'), webMessages.pleasenterphonenumber);
		loadMsgTitleText();
		return false;
	} else if (!isDigit(phone) || phone.length < 10 || phone.length > 13
			|| phone == 0) {
		setError(get('phone'), webMessages.invalidphonenumber);
		loadMsgTitleText();
		return false;
	} else if (phone.charAt(parseInt("0")) == "0") {
		setError(get('phone'), webMessages.phonecannotstartwithzero);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('phone'), '');
		/*setLable('confirmMphone', phone);*/
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
		setError(get('fax'),webMessages.invalidfax);
		loadMsgTitleText();
		return false;
	} else if (fax.charAt(parseInt("0")) == "0") {
		setError(get('fax'), webMessages.faxcannotstartwithzero);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('fax'), '');
		/*setLable('confirmMfax', fax);*/
		return true;
	}
}

function validateEmailId() {

	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var emailAddress = get('emailId').value.trim();

	if (isEmpty(emailAddress)) {
		setError(get('emailId'), webMessages.pleasenteremailid);
		loadMsgTitleText();
		return false;
	}

	else if (reg.test(emailAddress) == false) {

		setError(get('emailId'), webMessages.invalidemailid);
		loadMsgTitleText();
		return false;
	} else {
		if (alpha.indexOf(emailAddress.charAt(0)) == -1) {
			setError(get('emailId'), webMessages.invalidemailid);
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

		/*
		 * setError(get('emailId'), ''); setLable('confirmMemailId',
		 * emailAddress); return true;
		 */
	}

}

function validateTimeZone() {
	var timeZone = get('timeZone').value.trim();
	if (isEmpty(timeZone)) {
		setError(get('timeZone'), webMessages.pleaseEnterTimeZone);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('timeZone'), '');
		/*setLable('confirmMtimeZone', timeZone);*/
		return true;
	}
}

function validateAppMode() {
	var appMode = get('appMode').value.trim();
	if (isEmpty(appMode)) {
		setError(get('appMode'), webMessages.pleaseselectappmode);
		loadMsgTitleText();
		return false;
	} else if (!isChar(appMode)) {
		setError(get('appMode'), webMessages.invalidappmode);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('appMode'), '');
		/*setLable('confirmMappMode', appMode);*/
		return true;
	}
}

function validateStatus() {
	var status = get('status').value.trim();
	if (isEmpty(status)) {
		setError(get('status'), webMessages.pleaseselectstatus);
		loadMsgTitleText();
		return false;
	}
	/*
	 * else if(!isChar(status)){ setError(get('status'), 'Invalid status');
	 * return false; }
	 */
	else {
		setError(get('status'), '');
		/*setLable('confirmMstatus', status);*/
		return true;
	}
}

function validatefederalTaxId() {
	var federalTaxId = get('federalTaxId').value.trim();
	if (isEmpty(federalTaxId)) {
		setError(get('federalTaxId'), webMessages.pleaseEnterFederalTaxId);
		loadMsgTitleText();
		return false;
	} else if (!isCharacter(federalTaxId)) {
		setError(get('federalTaxId'), webMessages.invalidFederalTaxId);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('federalTaxId'), '');
		/*setLable('confirmMfederalTaxId', federalTaxId);*/
		return true;
	}
}

function validatestateTaxId() {
	var stateTaxId = get('stateTaxId').value.trim();
	if (isEmpty(stateTaxId)) {
		setError(get('stateTaxId'), webMessages.pleaseEnterStateTaxId);
		loadMsgTitleText();
		return false;
	} else if (!isCharacter(stateTaxId)) {
		setError(get('stateTaxId'), webMessages.invalidStateTaxId);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('stateTaxId'), '');
		/*setLable('confirmMstateTaxId', stateTaxId);*/
		return true;
	}
}

function validatesalesTaxId() {
	var salesTaxId = get('salesTaxId').value.trim();
	if (isEmpty(salesTaxId)) {
		setError(get('salesTaxId'), webMessages.pleaseEnterSalesTaxId);
		loadMsgTitleText();
		return false;
	} else if (!isCharacter(salesTaxId)) {
		setError(get('salesTaxId'),webMessages.invalidSalesTaxId);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('salesTaxId'), '');
		/*setLable('confirmMsalesTaxId', salesTaxId);*/
		return true;
	}
}

function validateOwnership() {
	var ownership = get('ownership').value.trim();
	if (isEmpty(ownership)) {
		setError(get('ownership'),webMessages.pleaseSelectOwnership);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('ownership'), '');
		/*setLable('confirmMownership', ownership);*/
		return true;
	}
}

function validateBusinessStartDate() {
	var flag = false;
	var date1 = getVal('businessStartDate').trim();
	var editStartDate = '';
	if (!isEmpty(date1)) {
		if (editStartDate == date1) {
			setError(get('businessStartDate'),
					webMessages.pleaseEntertheValidBusinessStartDate);
					loadMsgTitleText();
			return true;
		}
	}

	var cdate = new Date();
	var bdate = cdate.getFullYear() + "/" + (cdate.getMonth() + 1) + "/"
			+ cdate.getDate();

	var arr = date1.split("/");
	var dateString = arr[2] + "/" + arr[1] + "/" + arr[0];

	var givdate = new Date(dateString);
	var currentDate = new Date(bdate);

	if (date1 == "" || date1.length == 0) {
		setError(get('businessStartDate'),
				webMessages.pleaseEntertheValidBusinessStartDate);
				loadMsgTitleText();
		flag = false;
	} else if(givdate<currentDate){
		setError(get('businessStartDate'),
				webMessages.businessStartDateShouldnotbepastDate);
				loadMsgTitleText();
flag = false;
	}
	else {
		setError(get('businessStartDate'), '');
		/*setLable('confirmMbusinessStartDate', date1);*/
		flag = true;
	}
	return flag;
}

function validateEstimatedYearlySale() {
	var estimatedYearlySale = get('estimatedYearlySale').value.trim();
	if (isEmpty(estimatedYearlySale)) {
		setError(get('estimatedYearlySale'),
				webMessages.pleaseEnterEstimatedYearlySale);
				loadMsgTitleText();
		return false;
	} else if (!isDigit(estimatedYearlySale)) {
		setError(get('estimatedYearlySale'), webMessages.invalidEstimatedYearlySale);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('estimatedYearlySale'), '');
		/*setLable('confirmMestimatedYearlySale', estimatedYearlySale);*/
		return true;
	}
}

function validateNoOfEmployees() {
	var noOfEmployee = get('noOfEmployee').value.trim();
	if (isEmpty(noOfEmployee)) {
		setError(get('noOfEmployee'), webMessages.pleaseEnterNoofEmployee);
		loadMsgTitleText();
		return false;
	} else if (!isDigit(noOfEmployee)) {
		setError(get('noOfEmployee'), webMessages.invalidNoofEmployee);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('noOfEmployee'), '');
		/*setLable('confirmMnoOfEmployee', noOfEmployee);*/
		return true;
	}
}

function validateRole() {
	var role = get('role').value.trim();
	if (isEmpty(role)) {
		setError(get('role'), webMessages.pleaseEnterRole);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('role'), '');
		/*setLable('confirmMrole', role);*/
		return true;
	}
}

function validatefeeProgram() {
	var feeProgram = get('feeProgram').value.trim();
	if (isEmpty(feeProgram)) {
		setError(get('feeProgram'), webMessages.pleaseSelectFeeProgram);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('feeProgram'), '');
		setLable('confirmMfeeProgram', feeProgram);
		return true;
	}
}

function validateProcessor() {
	var processor = get('processor').value.trim();
	if (isEmpty(processor)) {
		setError(get('processor'), webMessages.pleaseSelectProcessor);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('processor'), '');
		/*setLable('confirmMprocessor', processor);*/
		return true;
	}
}

function validateURL() {
	var reg = /(http|https:\/\/[^\s\.]+\.[^\s]{2,}|www\.[^\s]+\.[^\s]{2,})/;
	var businessURL = get('businessURL').value.trim();

	if (isEmpty(businessURL)) {
		setError(get('businessURL'), webMessages.pleaseEnterBusinessURL);
		loadMsgTitleText();
		return false;
	} else if (reg.test(businessURL) == false) {
		setError(get('businessURL'), webMessages.invalidBusinessURL);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('businessURL'), '');
		/*setLable('confirmMbusinessURL', businessURL);*/
		return true;
	}

}

function validateCallbackURL() {
	var reg = /[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&\/=]*)/;
	var merchantCallBackURL = get('merchantCallBackURL').value.trim();
	if (isEmpty(merchantCallBackURL)) {
		setError(get('merchantCallBackURL'), '');
		/*setLable('confirmMmerchantCallBackURL', merchantCallBackURL);*/
		return true;
	} else if (reg.test(merchantCallBackURL) == false) {
		setError(get('merchantCallBackURL'), webMessages.invalidMerchantCallBackURL);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('merchantCallBackURL'), '');
		/*setLable('confirmMmerchantCallBackURL', merchantCallBackURL);*/
		return true;
	}

}

function validateAutoPaymentMethod() {
	var autoPaymentMethod = get('autoPaymentMethod').value.trim();
	if (isEmpty(autoPaymentMethod)) {
		setError(get('autoPaymentMethod'), webMessages.pleaseSelectPayMentMethod);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('autoPaymentMethod'), '');
		/*setLable('confirmMautoPaymentMethod', autoPaymentMethod);*/
		return true;
	}
}

function validateAutoTransferLimit() {
	var autoTransferLimit = get('autoTransferLimit').value.trim();
	if (isEmpty(autoTransferLimit)) {
		setError(get('autoTransferLimit'), '');
		return true;
	} else if (!isDigit(autoTransferLimit)) {
		setError(get('autoTransferLimit'), webMessages.invalidEstimatedYearlySale);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('autoTransferLimit'), '');
		return true;
	}
}

function validateRadio() {
	var autoSettlement = document.getElementsByName('autoSettlement');

	if ((autoSettlement[0].checked == false)
			&& (autoSettlement[1].checked == false)) {
		setError(get('noAutoSettlement'), webMessages.pleaseselectone);
		loadMsgTitleText();
		return false;
	} else {
		if (autoSettlement[0].checked == true) {
			setError(get('noAutoSettlement'), '');
			setLable('confirmMautoSettlement', 'yes');
			return true;
		}

		else {
			setError(get('noAutoSettlement'), '');
			setLable('confirmMautoSettlement', 'no');
			return true;
		}
	}
}

function validateCheckBox() {

	var list = "";
	if (get('refunds').checked)
		list = list + document.getElementById("refunds").name + ",";
	if (get('tipAmount').checked)
		list = list + document.getElementById("tipAmount").name + ",";
	if (get('taxAmount').checked)
		list = list + document.getElementById("taxAmount").name + ",";
	if (get('shippingAmount').checked)
		list = list + document.getElementById("shippingAmount").name + ",";
	/*if (get('allowRepeatSale').checked) {
		if (list.length > 42)
			list = list + document.getElementById("allowRepeatSale").name
					+ '\n';
		else
			list = list + document.getElementById("allowRepeatSale").name + ",";
	}
	if (get('showRecurringBilling').checked)
		list = list + document.getElementById("showRecurringBilling").name
				+ " ";*/

	/*
	 * if (get('refunds').checked | get('tipAmount').checked |
	 * get('taxAmount').checked | get('shippingAmount').checked |
	 * get('allowRepeatSale').checked | get('showRecurringBilling').checked) {
	 */
	if (list != "") {
		list = list.substring(0, list.length - 1);
		setError(get('refunds'), '');

		setLable('confirmMvirtualTerminal', list);
		return true;
	} else {
		setError(get('refunds'), webMessages.pleaseselectone);
		loadMsgTitleText();
		return false;
	}

}

/*-----*/

function validateMailingAddress1() {
	var address1 = get('mailingAddress1').value.trim();
	/* var addRegx = /^[A-Za-z0-9\#\$\&\,]+(\s{0,1}[a-zA-Z0-9,])*$/; */
	if (isEmpty(address1)) {
		setError(get('mailingAddress1'), webMessages.pleasenteraddress1);
		loadMsgTitleText();
		return false;
	} else if (address1.length < 5) {
		setError(get('mailingAddress1'), webMessages.invalidaddress1length);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('mailingAddress1'), '');
		/*setLable('confirmMaddress1', address1);*/
		return true;
	}
}

function validateMailingAddress2() {
	var address2 = get('mailingAddress2').value.trim();
	if (!isCharacter(address2)) {
		setError(get('mailingAddress2'), webMessages.invalidAddress2);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('mailingAddress2'), '');
		/*setLable('confirmMaddress2', address2);*/
		return true;
	}
}
function validateMailingCity() {
	var city = get('mailingCity').value.trim();
	var cityRegx = /^[A-Za-z0-9\#\$\&]+(\s{0,1}[a-zA-Z0-9,])*$/;
	if (isEmpty(city)) {
		setError(get('mailingCity'), webMessages.pleasentercity);
		loadMsgTitleText();
		return false;
	} else if (!cityRegx.test(city)) {
		setError(get('city'), webMessages.invalidcity);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('mailingCity'), '');
		/*setLable('confirmMcity', city);*/
		return true;
	}
}

function validateMailingState() {
	var state = get('mailingState').value.trim();
	if (isEmpty(state)) {
		setError(get('mailingState'), webMessages.pleaseselectstate);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('mailingState'), '');
		/*setLable('confirmMstate', state);*/
		return true;
	}
}

function validateMailingCountry() {
	var country = get('mailingCountry').value.trim();
	if (isEmpty(country)) {
		setError(get('mailingCountry'), webMessages.pleaseselectcountry);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('mailingCountry'), '');
		/*setLable('confirmMcountry', country);*/
		return true;
	}
}

/*function validateMailingPin() {
	var pin = getVal('mailingPin');
	if (isEmpty(pin)) {
		setError(get('mailingPin'), 'Please enter Mailing zip');
		return false;
	} else if ((pin.length < 3) || (pin.length > 6)) {
		setError(get('mailingPin'), 'Invalid zip');
		return false;
	} else if (!isValidZipChars(pin)) {
		setError(get('mailingPin'), 'Invalid zip');
		return false;
	} else {
		setError(get('mailingPin'), '');
		setLable('confirmPin', pin);
		return true;
	}
}*/
function validateMailingPin() {
	var pin = getVal('mailingPin');
	if (isEmpty(pin)) {
		setError(get('mailingPin'), webMessages.pleaseEnterMailingZip);
		loadMsgTitleText();
		return false;
	} else if ((pin.length < 3) || (pin.length > 6)) {
		setError(get('mailingPin'), webMessages.invalidMailingZip);
		loadMsgTitleText();
		return false;
	} else if (!isValidZipChars(pin)) {
		setError(get('mailingPin'), webMessages.invalidMailingZip);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('mailingPin'), '');
		/*setLable('confirmMpin', pin);*/
		return true;
	}
}

$("#myEdit").click(function() {
	var country = get('mailingCountry').value.trim();
	if (isEmpty(country)) {
		document.getElementById('mailingState').options.length = 0;
		var selectOption = document.createElement("option");
		selectOption.innerHTML = "..:Select:..";
		selectOption.value = "";
		$(("#"+'mailingState')).append(selectOption);
	}
})
function clearMsg(){
	document.getElementById("sucessDiv").innerHTML = "";
	document.getElementById("errorDiv").innerHTML = "";
}
