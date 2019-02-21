String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g, "");
};
var usernameFlag = null;
function cancelCreateMerchant() {
	window.location.href = 'merchant-search-page';
}

function cancelCreateSubMerchant() {
	window.location.href = 'sub-merchant-search-page';
}

function goToMerchantSearch() {
	window.location.href = 'merchant-search-page';
}

function goToSubMerchantSearch() {
	window.location.href = 'sub-merchant-search-page';
}

function openCancelConfirmationPopup() {

	if ((isEmpty(get('businessName').value))
			&& (isEmpty(get('firstName').value.trim()))
			&& (isEmpty(get('lastName').value.trim()))
			&& (isEmpty(get('phone').value.trim()))
			&& (isEmpty(get('city').value.trim()))
			&& (isEmpty(get('country').value.trim()))
			&& (isEmpty(get('emailId').value.trim()))
			&& (isEmpty(get('address1').value.trim()))
			&& (isEmpty(getVal('pin')))
			&& (isEmpty(get('state').value.trim()))) {
		window.location.href = 'merchant-search-page';
	}

	else {
		$('#my_popup1').popup("show");
	}
}
function openCancelConfirmationPopup1() {

	if ((isEmpty(get('businessName').value))
			&& (isEmpty(get('firstName').value.trim()))
			&& (isEmpty(get('lastName').value.trim()))
			&& (isEmpty(get('phone').value.trim()))
			&& (isEmpty(get('city').value.trim()))
			&& (isEmpty(get('country').value.trim()))
			&& (isEmpty(get('emailId').value.trim()))
			&& (isEmpty(get('address1').value.trim()))
			&& (isEmpty(getVal('pin')))
			&& (isEmpty(get('state').value.trim()))) {
		window.location.href = 'sub-merchant-search-page';
	}

	else {
		$('#my_popup1').popup("show");
	}
}

function closeCancelConfirmationPopup() {
	$('#my_popup1').popup("hide");
}

function validateCreateMerchantStep1() {
	var flag = true;
	if (!validateBusinessName() | !validateFirstName() | !validateLastName()
			| !validatePhone() 	| !validateAddress1() | !validateCity() | !validateEmailId()
			| !validateState() | !validateCountry() | !validatePin()
		    | !vlalidateUserName() | !createValidationForBasicInfo()) {
		return false;
	} else {
		var faxValue = getVal('fax').trim();
		var businessTypeValue = getVal('businessType').trim();
		if (faxValue != "") {
			setLable('confirmMfax', faxValue);
		} else {
			setLable('confirmMfax', "");
		}
		if (businessTypeValue != "") {
			setLable('confirmBusinessType', businessTypeValue);
		} else {
			setLable('confirmBusinessType', "");
		}
	}
	return flag;
}

function validateCreateMerchantStep2() {
	var flag = true;
	if(!clientValidation('bankAccountName', 'first_name_SplChar','bankAccountNameErrorDiv')
		//	|!validRoutingNumber()
			|!clientValidation('bankAccountNumber', 'account_numberBank','bankAccountNumberErrorDiv')
			|!clientValidation('bankRoutingNumber', 'routing_number','bankRoutingNumberEr')
			|!clientValidation('bankAccountType', 'account_type','bankAccountTypeErrorDiv')
			|!clientValidation('bankAddress1', 'bank_address2','bankAddress1ErrorDiv')
			|!clientValidation('bankAddress2', 'bank_address2','bankAddress2ErrorDiv')
			|!clientValidation('bankCity', 'bank_address2','bankCityErrorDiv')
			|!clientValidation('bankCountry', 'country','bankCountryErrorDiv')
			|!clientValidation('currencyId', 'currencyValue','currencyEr')
			|!clientValidation('bankState', 'state','bankStateErrorDiv')
		    |!clientValidation('bankNameOnAccount', 'first_name_SplChar','bankNameOnAccountErrorDiv')
			|!validateRoutingNumber()){
		flag = false;
		return flag;
	} else {
		setLable('confirmbankAccountName', get('bankAccountName').value.trim());
		setLable('confirmbankRoutingNumber', get('bankRoutingNumber').value.trim());
		setLable('confirmbankAccountNumber', get('bankAccountNumber').value.trim());
	//	setLable('confirmbankAccountType', get('bankAccountType').value.trim());
		setTypeValueToConfirmPage();
		setLable('confirmbankAddress1', get('bankAddress1').value.trim());
		setLable('confirmbankAddress2', get('bankAddress2').value.trim());
		setLable('confirmbankCity', get('bankCity').value.trim());
		setLable('confirmbankCountry', get('bankCountry').value.trim());
		setLable('confirmbankState', get('bankState').value.trim());
		setLable('confirmbankPin', get('bankPin').value.trim());
		setLable('confirmbankNameOnAccount', get('bankNameOnAccount').value.trim());
		setLable('confirmCurrencyIdValue', get('localCurrency').value.trim());
		return flag;
	}
}
function validateCreateMerchantStep3() {
	var flag = true;
	if(!clientValidation('legalName', 'first_name_SplChar','legalNameErrorDiv')
			|!clientValidation('legalTaxId', 'eIN_taxId','legalTaxIdErrorDiv')
			|!clientValidation('legalType', 'state','legalTypeErrorDiv')
			|!clientValidation('legalAnnualCard', 'dollar_amount','legalAnnualCardErrorDiv')
		//	|!clientValidation('legalFirstName', 'first_name_NotMand','legalFirstNameErrorDiv')
		//	|!clientValidation('legalLastName', 'first_name_NotMand','legalLastNameErrorDiv')
		//	|!clientValidation('legalMobilePhone', 'mobile_phone','legalMobilePhoneErrorDiv')
			|!clientValidation('legalAddress1', 'bank_address1','legalAddress1ErrorDiv')
			|!clientValidation('legalAddress2', 'bank_address2','legalAddress2ErrorDiv')
			|!clientValidation('legalCity', 'bank_city','legalCityErrorDiv')
			|!clientValidation('legalCountry', 'country','legalCountryErrorDiv')
			|!clientValidation('legalState', 'state','legalStateErrorDiv')
			|!zipCodeNotEmpty('legalPin')){
		flag = false
		return flag;
	}else{
		setLable('confirmlegalName', get('legalName').value.trim());
		setLable('confirmlegalTaxId', get('legalTaxId').value.trim());
	//	setLable('confirmlegalType', get('legalType').value.trim());
		setValuetoConfirmPage();
		setLable('confirmlegalAnnualCard', get('legalAnnualCard').value.trim());
		/*setLable('confirmlegalFirstName', get('legalFirstName').value.trim());
		setLable('confirmlegalLastName', get('legalLastName').value.trim());
		setLable('confirmlegalMobilePhone', get('legalMobilePhone').value.trim());*/
		setLable('confirmlegalAddress1', get('legalAddress1').value.trim());
		setLable('confirmlegalAddress2', get('legalAddress2').value.trim());
		setLable('confirmlegalCity', get('legalCity').value.trim());
		setLable('confirmlegalCountry', get('legalCountry').value.trim());
		setLable('confirmlegalState', get('legalState').value.trim());
		setLable('confirmlegalPin', get('legalPin').value.trim());
		return flag;
	}
}

function setTypeValueToConfirmPage() {
	// setLable('confirmbankAccountType', get('bankAccountType').value.trim());
	var accTypeValue = document.getElementById("bankAccountType");
	var accTypeValueText = accTypeValue.options[accTypeValue.selectedIndex].text;
	setLable('confirmbankAccountType', accTypeValueText);
}

function setValuetoConfirmPage() {
	// var typeValue = get('legalType').value.trim();
	var typeValue = document.getElementById("legalType");
	var typeValueText = typeValue.options[typeValue.selectedIndex].text;
	setLable('confirmlegalType', typeValueText);
}

function validateLegalEntity() {
	var flag = true;
	if (!clientValidation('legalFirstName', 'first_name_NotMand',
			'legalFirstNameErrorDiv')
			| !clientValidation('legalLastName', 'first_name_NotMand',
					'legalLastNameErrorDiv')
			| !clientValidation('legalMobilePhone', 'mobile_optional',
					'legalMobilePhoneErrorDiv')
			| !clientValidation('legalPassport', 'passport_number',
					'legalPassportErrorDiv')
	// |!clientValidation('legalCitizen', 'bank_country','legalCitizenErrorDiv')
	// |!clientValidation('legalHomePhone',
	// 'contact_phone','legalHomePhoneErrorDiv')
	) {
		flag = false;
		return flag;
	} else {
		setLable('confirmlegalSSN', get('legalSSN').value.trim());
		setLable('confirmlegalFirstName', get('legalFirstName').value.trim());
		setLable('confirmlegalLastName', get('legalLastName').value.trim());
		setLable('confirmlegalMobilePhone', get('legalMobilePhone').value
				.trim());
		setLable('confirmlegalDOB', get('legalDOB').value.trim());
		setLable('confirmlegalPassport', get('legalPassport').value.trim());
		setLable('confirmlegalCountryResidence',
				get('legalCountryResidence').value.trim());
		setLable('confirmlegalCitizen', get('legalCitizen').value.trim());
		setLable('confirmlegalHomePhone', get('legalHomePhone').value.trim());
		return flag;
	}
}

function validateCreateMerchantStep4() {
	var flag = true;
	if (!vlalidateUserName()// | !validatefederalTaxId() //|
							// !validatestateTaxId() | !validatesalesTaxId()
	// | !validateOwnership()
	// | !validateURL()
	/*
	 * | !validateBusinessStartDate() | !validateEstimatedYearlySale() |
	 * !validateNoOfEmployees()
	 */) {
		return false;
	}
	return flag;
}

function validateCreateMerchantStep1edit() {
	var flag = true;
	if (!validateBusinessName() | !validateFirstName() | !validateLastName()
			| !validatePhone() | !validateAddress1() | !validateEmailId()
			| !validateCity() | !validateState() | !validateCountry()
			| !validatePin() | !createValidationForBasicInfo()) {
		return false;
	} else {
		var faxValue = getVal('fax').trim();
		var businessTypeValue = getVal('businessType').trim();
		if (faxValue != "") {
			setLable('confirmMfax', getVal('fax').trim());
		} else {
			setLable('confirmMfax', "");
		} if (businessTypeValue != "") {
			setLable('confirmBusinessType', businessTypeValue);
		} else {
			setLable('confirmBusinessType', "");
		}
	}
	setLable('confirmMemailId', get('emailId').value.trim());
	setLable('confirmMmerchantCode', get('merchantCode').value.trim());
	setLable('confirmMuserName', get('userName').value.trim());
	return flag;
}

function validateCreateMerchantStep2edit() {
	var flag = true;
	if(!clientValidation('bankAccountName', 'first_name_SplChar','bankAccountNameErrorDiv')
		//	|!validRoutingNumber()
			|!clientValidation('bankAccountNumber', 'account_numberBank','bankAccountNumberErrorDiv')
			|!clientValidation('bankRoutingNumber', 'routing_number','bankRoutingNumberEr')
			|!clientValidation('bankAccountType', 'account_type','bankAccountTypeErrorDiv')
			|!clientValidation('bankAddress1', 'bank_address2','bankAddress1ErrorDiv')
			|!clientValidation('bankAddress2', 'bank_address2','bankAddress2ErrorDiv')
			|!clientValidation('bankCity', 'bank_address2','bankCityErrorDiv')
			|!clientValidation('bankCountry', 'country','bankCountryErrorDiv')
			|!clientValidation('currencyId', 'currencyValue','currencyEr')
			|!clientValidation('bankState', 'state','bankStateErrorDiv')
		    |!(zipCodeNotEmpty('bankPin'))
		    |!clientValidation('bankNameOnAccount', 'first_name_SplChar','bankNameOnAccountErrorDiv')){
		flag = false;
		return flag;
	}else{
		setLable('confirmbankAccountName', get('bankAccountName').value.trim());
		setLable('confirmbankRoutingNumber', get('bankRoutingNumber').value.trim());
		setLable('confirmbankAccountNumber', get('bankAccountNumber').value.trim());
	//	setLable('confirmbankAccountType', get('bankAccountType').value.trim());
		setTypeValueToConfirmPage();
		setLable('confirmbankAddress1', get('bankAddress1').value.trim());
		setLable('confirmbankAddress2', get('bankAddress2').value.trim());
		setLable('confirmbankCity', get('bankCity').value.trim());
		setLable('confirmbankCountry', get('bankCountry').value.trim());
		setLable('confirmbankState', get('bankState').value.trim());
		setLable('confirmbankPin', get('bankPin').value.trim());
		setLable('confirmbankNameOnAccount', get('bankNameOnAccount').value.trim());
		setLable('confirmcurrencyId', get('localCurrency').value.trim());
		return flag;
	}
}

function validateCreateMerchantStep3edit() {
	var flag = true;
	if(!clientValidation('legalName', 'first_name_SplChar','legalNameErrorDiv')
			|!clientValidation('legalTaxId', 'eIN_taxId','legalTaxIdErrorDiv')
			|!clientValidation('legalType', 'state','legalTypeErrorDiv')
			|!clientValidation('legalAnnualCard', 'dollar_amount','legalAnnualCardErrorDiv')
		//	|!clientValidation('legalFirstName', 'first_name_NotMand','legalFirstNameErrorDiv')
		//	|!clientValidation('legalLastName', 'first_name_NotMand','legalLastNameErrorDiv')
		//	|!clientValidation('legalMobilePhone', 'mobile_phone','legalMobilePhoneErrorDiv')
			|!clientValidation('legalAddress1', 'bank_address1','legalAddress1ErrorDiv')
			|!clientValidation('legalAddress2', 'bank_address2','legalAddress2ErrorDiv')
			|!clientValidation('legalCity', 'bank_city','legalCityErrorDiv')
			|!clientValidation('legalCountry', 'country','legalCountryErrorDiv')
			|!clientValidation('legalState', 'state','legalStateErrorDiv')
			|!(zipCodeNotEmpty('legalPin'))){
		flag = false;
		return flag;
	}else{
		setLable('confirmlegalName', get('legalName').value.trim());
		setLable('confirmlegalTaxId', get('legalTaxId').value.trim());
		//setLable('confirmlegalType', get('legalType').value.trim());
		setValuetoConfirmPage();
		setLable('confirmlegalAnnualCard', get('legalAnnualCard').value.trim());
		
		setLable('confirmlegalAddress1', get('legalAddress1').value.trim());
		setLable('confirmlegalAddress2', get('legalAddress2').value.trim());
		setLable('confirmlegalCity', get('legalCity').value.trim());
		setLable('confirmlegalCountry', get('legalCountry').value.trim());
		setLable('confirmlegalState', get('legalState').value.trim());
		setLable('confirmlegalPin', get('legalPin').value.trim());
		return flag;
	}
}

function validateCreateMerchantStep4edit() {
	var flag = true;
	setLable('confirmMemailId', get('emailId').value.trim());
	setLable('confirmMmerchantCode', get('merchantCode').value.trim());
	setLable('confirmMuserName', get('userName').value.trim());
	return flag;
}

function validateCreateMerchantStep5() {
	var flag = true;
	if (!validateProcessor() | !validateVirtualTerminal()
			| !validateOnlineOptions()
			| !validateCategory()
			| !validateAutoPaymentMethod() | !validateAutoTransferLimit()
			| !continueBtnValidateForOnline()
			| !continueBtnValidateVirtualTerminal() |!doCheckPayoutAt
			| !validateMerchantCategory() |!validateMcc()
			| !validateBank()| !validatelocalCurrency()) {
		return false;
	}
	var fieldsValue = get('autoTransferDay').value;
	var processor = get('processor').value.trim();
	
	 if ($('#dcc_enable').is(':checked')) {
		if(get('confirmDccEnable')) {
			setLable('confirmDccEnable', 'Yes');
		}
	}
	else
		{
		if(get('confirmDccEnable')) {
			setLable('confirmDccEnable', 'No');
		}
	}
	//POS
	 if ($('#posTerminal').is(':checked')) {
			if(get('confirmMposTerminal')) {
				setLable('confirmMposTerminal', 'Yes');
			}
		}

	 else
			{
			if(get('confirmMposTerminal')) {
				setLable('confirmMposTerminal', 'No');
			}
		}
	 //pay page config
	 if ($('#payPageConfig').is(':checked')) {
		if(get('payPageConfiguration')) {
			setLable('payPageConfiguration', 'Yes');
		}
	}
	else
		{
		if(get('payPageConfiguration')) {
			setLable('payPageConfiguration', 'No');
		}
	}
	
	var bankName = get('bankId').value.trim();
	if (bankName != "") {
		var bName = get('bankId');
		setLable('confirmBankName', bName.options[bName.selectedIndex].text);
	}else {
		setLable('confirmBankName', "");
	}
	
	var confirmlocalCurrency = get('localCurrency').value.trim();
	if (confirmlocalCurrency != "") {
		setLable('confirmlocalCurrency', confirmlocalCurrency);
	}else {
		setLable('confirmlocalCurrency', "");
	}
	
	if (fieldsValue == "") {
		setLable('confirmMautoTransferDay', "");
	}
	if (fieldsValue == "D") {
		setLable('confirmMautoTransferDay', "Daily");
		$('#hideDayTable').hide();
		$('#hideWeekyTable').hide();
		setLable('confirmAutoTransferWeeklyDay', "");
		setLable('confirmAutoTransferMonthlyDay', "");
	}
	if (fieldsValue == "W") {
		if (!clientValidation('autoTransferWeeklyDay', 'state',
				'autoTransferWeeklyDayEr')) {
			return false;
		} else {
			setLable('confirmMautoTransferDay', "Weekly");
			$('#hideDayTable').show();
			$('#hideWeekyTable').hide();
			setDiv('autoTransferWeeklyDayEr', "");
			// var weekValue = get('autoTransferWeeklyDay').value.trim();

			var weekValue = document.getElementById("autoTransferWeeklyDay");
			var weekDay = weekValue.options[weekValue.selectedIndex].text;
			setLable('confirmAutoTransferWeeklyDay', weekDay);
		}
	}
	if (fieldsValue == "M") {
		if (!clientValidation('autoTransferMonthlyDay', 'state',
				'autoTransferMonthlyDayEr')) {
			return false;
		} else {
			setLable('confirmMautoTransferDay', "Monthly");
			$('#hideDayTable').hide();
			$('#hideWeekyTable').show();
			setDiv('autoTransferMonthlyDayEr', "");
			setLable('confirmAutoTransferMonthlyDay',
					get('autoTransferMonthlyDay').value.trim());
		}
	}
	if (processor == "LITLE") {
		return validatelitleMID();
	} else {
		setLable('confirmLitleMID', '');
	}
	return flag;
}

function validateAddress1() {
	var address1 = get('address1').value.trim();
	if (isEmpty(address1)) {
		setError(get('address1'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else if (address1.length < 5) {
		setError(get('address1'), webMessages.invalidAddress1Length);
		loadMsgTitleText();
		return false;
	} else if (!addressRegx.test(address1)) {
		setError(get('address1'), webMessages.address_should_contains_message);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('address1'), '');
		setLable('confirmMaddress1', address1);
		return true;
	}
}

function validateAddress2() {
	var address2 = get('address2').value.trim();
	setError(get('address2'), '');
	setLable('confirmMaddress2', address2);
	return true;
}

function validateCity() {
	var city = get('city').value.trim();
	if (isEmpty(city)) {
		setError(get('city'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else if (!cityRegx.test(city)) {
		setError(get('city'), webMessages.invalidCity);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('city'), '');
		setLable('confirmMcity', city);
		return true;
	}
}

function validateState() {
	var state = get('state').value.trim();
	if (isEmpty(state)) {
		setError(get('state'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('state'), '');
		setLable('confirmMstate', state);
		return true;
	}
}

function validateCountry() {
	var country = get('country').value.trim();
	if (isEmpty(country)) {
		setError(get('country'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('country'), '');
		setLable('confirmMcountry', country);
		return true;
	}
}

function validateCurrency() {
	var currencyValue = get('currencyValue').value.trim();
	if (isEmpty(currencyValue)) {
		setError(get('currencyValue'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('currencyValue'), '');
		setLable('confirmCurrencyIdValue', currencyValue);
		return true;
	}
}

function validatePin() {
	var pin = getVal('pin');
	if (isEmpty(pin)) {
		setError(get('pin'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else if ((pin.length < 3) || (pin.length > 7)) {
		setError(get('pin'), webMessages.invalidZipCode);
		loadMsgTitleText();
		return false;
	
	} else {
		setError(get('pin'), '');
		setLable('confirmMpin', pin);
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
		setError(get('merchantCode'), webMessages.shouldbenumerics);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('merchantCode'), '');
		setLable('confirmMmerchantCode', merchantCode);
		return true;
	}
}

function validateBusinessName() {
	var businessName = get('businessName').value.trim();
	if (isEmpty(businessName)) {
		setError(get('businessName'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else if (!businessNameRegx.test(businessName)) {
		setError(get('businessName'), webMessages.invalidCompanyName);
		loadMsgTitleText();
		return false;
	} else if (businessName.length < 2){
		setError(get('businessName'), webMessages.invalidLength);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('businessName'), '');
		setLable('confirmMbusinessName', businessName);
		return true;
	}
}

function validateFirstName() {
	var firstName = get('firstName').value.trim();
	if (isEmpty(firstName)) {
		setError(get('firstName'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else if (!firstNameRegx.test(firstName) ) {
		setError(get('firstName'), webMessages.invalidFirstName);
		loadMsgTitleText();
		return false;
	}else if(firstName.length<2){
		setError(get('firstName'), webMessages.invalidLength);
		loadMsgTitleText();
		return false;
	}/*
		 * else if (!firstCharRegx.test(firstName.charAt(0))) {
		 * setError(get('firstName'), 'Can not start with Special Char'); return
		 * false; }
		 */else {
		setError(get('firstName'), '');
		setLable('confirmMfirstName', firstName);
		return true;
	}
}

function validateLastName() {
	var lastName = get('lastName').value.trim();
	if (isEmpty(lastName)) {
		setError(get('lastName'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else if (!lastNameRegx.test(lastName)) {
		setError(get('lastName'), webMessages.invalidLastName);
		loadMsgTitleText();
		return false;
	} else if(lastName.length<2){
		setError(get('lastName'), webMessages.invalidLength);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('lastName'), '');
		setLable('confirmMlastName', lastName);
		return true;
	}
}

function validatePhone() {
	var phone = getVal('phone').trim();
	if (isEmpty(phone)) {
		setError(get('phone'), webMessages.validationthisfieldismandatory);
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
		setLable('confirmMphone', phone);
		return true;
	}
}

function validateFax() {
	var fax = getVal('fax').trim();
	if (isEmpty(fax)) {
		setError(get('fax'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else if (!isDigit(fax) || fax.length < 10 || fax.length > 13 || fax == 0) {
		setError(get('fax'), webMessages.invalidFax);
		loadMsgTitleText();
		return false;
	} else if (fax.charAt(parseInt("0")) == "0") {
		setError(get('fax'), webMessages.faxcannotStartwithZero);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('fax'), '');
		setLable('confirmMfax', fax);
		return true;
	}
}

function validateEmailId() {
	var isMailUnique = $('#userMail').val();
	var alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var emailAddress = get('emailId').value.trim();
	if (isEmpty(emailAddress)) {
		setError(get('emailId'),webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else if (emailIdReg.test(emailAddress) == false) {
		setError(get('emailId'), webMessages.invalidEmailID);
		loadMsgTitleText();
		return false;
	} else {
		if (alpha.indexOf(emailAddress.charAt(0)) == -1) {
			setError(get('emailId'), webMessages.invalidEmail);
			loadMsgTitleText();
			return false;
		} else {
			setError(get('emailId'), '');
			doAjaxFetchMailIdAvailable(isMailUnique);
			if (usernameFlag == false) {
				return true;
			} else {
				return false;
			}
		}
	}
}

function validateTimeZone() {
	var timeZone = get('timeZone').value.trim();
	if (isEmpty(timeZone)) {
		setError(get('timeZone'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('timeZone'), '');
		setLable('confirmMtimeZone', timeZone);
		return true;
	}
}

function validateStatus() {
	var status = get('status').value.trim();
	var statusValue = document.getElementById("status");
	var x = statusValue.options[statusValue.selectedIndex].text;
	if (isEmpty(status)) {
		setError(get('status'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('status'), '');
		setLable('confirmMstatus', x);
		return true;
	}
}

function validatefederalTaxId() {
	var federalTaxId = get('federalTaxId').value.trim();
	if (isEmpty(federalTaxId)) {
		setError(get('federalTaxId'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else if (!isCharacter(federalTaxId)) {
		setError(get('federalTaxId'), webMessages.invalidFederalTaxId);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('federalTaxId'), '');
		setLable('confirmMfederalTaxId', federalTaxId);
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
		setLable('confirmMstateTaxId', stateTaxId);
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
		setError(get('salesTaxId'), webMessages.invalidSalesTaxId);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('salesTaxId'), '');
		setLable('confirmMsalesTaxId', salesTaxId);
		return true;
	}
}

function validateOwnership() {
	var ownership = get('ownership').value.trim();
	if (isEmpty(ownership)) {
		setError(get('ownership'), webMessages.pleaseSelectOwnership);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('ownership'), '');
		setLable('confirmMownership', ownership);
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
	} else if (givdate < currentDate) {
		setError(get('businessStartDate'),
				webMessages.businessStartDateshouldnotbePastdate);
		loadMsgTitleText();
		flag = false;
	} else {
		setError(get('businessStartDate'), '');
		setLable('confirmMbusinessStartDate', date1);
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
		setLable('confirmMestimatedYearlySale', estimatedYearlySale);
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
		setLable('confirmMnoOfEmployee', noOfEmployee);
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
		setLable('confirmMrole', role);
		return true;
	}
}

function validatefeeProgram() {
	var feeProgram = get('feeProgram').value.trim();
	if (isEmpty(feeProgram)) {
		setError(get('feeProgram'), webMessages.validationthisfieldismandatory);
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
	var processorValue = document.getElementById("processor");
	var x = processorValue.options[processorValue.selectedIndex].text;
	if (isEmpty(processor)) {
		setError(get('processor'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('processor'), '');
		setLable('confirmMprocessor', x);
		if (processor == "LITLE") {
			$('#vantivMerchantId').show();
		} else {
			setValue('litleMID', '');
			setLable('confirmLitleMID', '');
			$('#vantivMerchantId').hide();
		}
		return true;
	}
}

function validateParentMerchantId() {
	var merchantCode = $('#parentMerchantId').val().trim();
	if ($('#parentMerchantId').is(':visible') && merchantCode.length <= 0) {
		setError(get('parentMerchantId'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else if ($('#parentMerchantId').is(':visible')) {
		setError(get('parentMerchantId'), '');
		setLable('confirmMerchantCode', $('#parentMerchantId :selected').text());
		
		var currency = document.getElementById('localCurrency').value;
		
	}
	return true;
}

function validateParentMerchantIdEdit() {
	if (get('confirmMerchantCode')) {
		setLable('confirmMerchantCode', $('#parentMerchantId :selected')
				.text());
	}
	return true;
}

function validateCallbackURL() {
	// var reg =
	// /[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&\/=]*)/;
	var reg = /(http|https:\/\/[^\s\.]+\.[^\s]{2,}|www\.[^\s]+\.[^\s]{2,})/;
	var merchantCallBackURL = get('merchantCallBackURL').value.trim();
	if (isEmpty(merchantCallBackURL)) {
		setError(get('merchantCallBackURL'), '');
		setLable('confirmMmerchantCallBackURL', merchantCallBackURL);
		return true;
	} else if (reg.test(merchantCallBackURL) == false) {
		setError(get('merchantCallBackURL'), webMessages.invalidMerchantCallBackURL);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('merchantCallBackURL'), '');
		setLable('confirmMmerchantCallBackURL', merchantCallBackURL);
		return true;
	}
}

function validateCategory() {
	var category = get('category').value.trim();
	if (isEmpty(category)) {
		setError(get('category'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('category'), '');
		setLable('confirmMcategory', category);
		return true;
	}
}

function validateAutoPaymentMethod() {
	var autoPaymentMethod = get('autoPaymentMethod').value.trim();
	if (isEmpty(autoPaymentMethod)) {
		setError(get('autoPaymentMethod'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('autoPaymentMethod'), '');
		setLable('confirmMautoPaymentMethod', autoPaymentMethod);
		return true;
	}
}

function validateAutoTransferLimit() {
	var reg = /^[0-9]+(\.[0-9][0-9]?)?$/;
	var autoTransferLimit = get('autoTransferLimit').value.trim();
	if (isEmpty(autoTransferLimit)) {
		setError(get('autoTransferLimit'), '');
		return true;
	} else if (reg.test(autoTransferLimit) == false) {
		setError(get('autoTransferLimit'), webMessages.invalidAutoTransferLimit);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('autoTransferLimit'), '');
		setLable('confirmMautoTransferLimit', autoTransferLimit);
		return true;
	}
}

function validatelitleMID() {
	var litleMID = get('litleMID').value.trim();
	if (isEmpty(litleMID)) {
		setError(get('litleMID'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else if (!isDigit(litleMID)) {
		setError(get('litleMID'), webMessages.invalidVantivMerchantId);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('litleMID'), '');
		setLable('confirmLitleMID', litleMID);
		return true;
	}
}

function validateRadio() {
	var autoSettlement = document.getElementsByName('autoSettlement');
	if ((autoSettlement[0].checked == false)
			&& (autoSettlement[1].checked == false)) {
		setError(get('noAutoSettlement'), webMessages.pleaseSelectOne);
	loadMsgTitleText();
		return false;
	} else {
		if (autoSettlement[0].checked == true) {
			setError(get('noAutoSettlement'), '');
			setLable('confirmMautoSettlement', 'yes');
			return true;
		} else {
			setError(get('noAutoSettlement'), '');
			setLable('confirmMautoSettlement', 'no');
			return true;
		}
	}
}

function validateVirtualTerminalCheckBox() {
	var list1 = "";
	if (get('virtualTerminal').checked)
		list1 = list1 + document.getElementById("virtualTerminal").name + ",";
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
		setLable('confirmMvirtualTerminalList', list);
		return true;
	} else {
		setError(get('refunds'), webMessages.pleaseSelectatleastoneVirtualTerminal);
		setLable('confirmMvirtualTerminalList', list);
		loadMsgTitleText();
		return false;
	}
}

function vlalidateUserName() {
	var userName = get('userName').value.trim();
	if (isEmpty(userName)) {
		setError(get('userName'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		document.getElementById('userNamegreenEr').innerHTML = "";
		return false;
	} else if (userNameRegex.test(userName) == false) {
		setError(get('userName'), webMessages.canContainAlphanumerics);
		document.getElementById('userNamegreenEr').innerHTML = "";
		loadMsgTitleText();
		return false;
	} else {
		doAjaxFetchUsernameAvailable();
		if (usernameFlag == true) {
			return true;
		} else {
			return false;
		}
	}
}

function doAjaxFetchUsernameAvailable() {
	var userName = get('userName').value.trim();
	$
			.ajax({
				type : "GET",
				url : "uniqueUser?userName=" + userName,
				async : false,
				success : function(response) {
					var obj = JSON.parse(response);
					if (obj.errorCode === '00') {
						setError(get('userName'), '');
						document.getElementById('userNamegreenEr').innerHTML = "User Name available";
						setLable('confirmMuserName', get('userName').value
								.trim());
						usernameFlag = true;
					} else {
						setError(get('userName'), webMessages.userNamenotAvailable);
						document.getElementById('userNamegreenEr').innerHTML = "";
						loadMsgTitleText();
						usernameFlag = false;
					}
				},
				error : function(e) {
				}
			});
}

function fetchMerchantState(countryid, elementId) {
	if (countryid == '') {
		clearState(elementId);
		return;
	}
	getStates(countryid, elementId);
}

function getStates(countryid, elementId) {
	$.ajax({
		type : "GET",
		url : "getStatesById?countryid=" + countryid,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorCode === '00') {
				// remove the previous option from element
				document.getElementById(elementId).options.length = 0;
				// create select option
				var selectOption = document.createElement("option");
				selectOption.innerHTML = "..:Select:..";
				selectOption.value = "";
				$(("#" + elementId)).append(selectOption);

				if (obj.errorMessage == "SUCCESS") {
					var data = obj.responseList;

					for (var i = 0; i < data.length; i++) {
						var state = data[i].label;

						var newOption = document.createElement("option");
						newOption.value = data[i].value;
						newOption.innerHTML = state;

						$(("#" + elementId)).append(newOption);
					}
				}
			}
		},
		error : function(e) {
		}
	});
}

function clearState(elementId) {
	document.getElementById(elementId).options.length = 0;
	var selectOption = document.createElement("option");
	selectOption.innerHTML = "..:Select:..";
	selectOption.value = "";
	$(("#" + elementId)).append(selectOption);
}

function fetchMerchantStateForUpdate(countryid, elementId) {
	if (countryid == '') {
		clearState(elementId);
		return;
	}
	getStatesForUpdate(countryid, elementId);
}

function getStatesForUpdate(countryid, elementId) {
	if (countryid == '') {
		/* clearState(); */
		return;
	} else if (countryid === "US" || countryid === "us") {
		countryid = "2";
	}
	// var countryid = this.countryid;
	$.ajax({
		type : "GET",
		url : "getStatesById?countryid=" + countryid,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorCode === '00') {
				// remove the previous option from element
				document.getElementById(elementId).options.length = 1;

				// create select option
				/*
				 * var selectOption = document.createElement("option");
				 * selectOption.innerHTML = ""+stateId; selectOption.value =
				 * ""+stateId; $("#state").append(selectOption);
				 */

				if (obj.errorMessage == "SUCCESS") {
					var data = obj.responseList;

					for (var i = 0; i < data.length; i++) {
						var state = data[i].label;

						var newOption = document.createElement("option");
						newOption.value = data[i].value;
						newOption.innerHTML = state;

						$(("#" + elementId)).append(newOption);
					}
				}
			}
		},
		error : function(e) {
		}
	});
}

var merchantsID,merchanType;
function confirmDeleteMerchant(merchantId, merchantsType) {
	$('#deletePopup').popup("show");
	merchantsID = merchantId;
	merchanType = merchantsType;
	
}

function deleteData() {
	get('getMerchantsId').value = merchantsID;
	get('merchantsType').value = merchanType;
	document.forms["deleteMercahntForm"].submit();
}

function doAjaxFetchMailIdAvailable(isMailUnique) {
	if (isMailUnique == "true") { // If mail is need to unique this if() code will Execute.
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
					setError(get('emailId'), webMessages.emailidAlreadyinUse);
					loadMsgTitleText();
					usernameFlag = true;
				}
			},
			error : function(e) {
			}
		});
	} else {
		setLable('confirmMemailId', get('emailId').value.trim());
		usernameFlag = false;
	}
}

function resetBasicInfo() {
	window.location.href = 'merchant-create';
}

function resetBasicInfoSub() {
	window.location.href = 'sub-merchant-create';
}

function resetBankInfo() {
	get('bankAccountName').value = "";
	setDiv('bankAccountNameErrorDiv', '');
	get('bankRoutingNumber').value = "";
	setDiv('bankRoutingNumberEr', '');
	get('bankAccountNumber').value = "";
	setDiv('bankAccountNumberErrorDiv', '');
	get('bankAccountType').value = "";
	setDiv('bankAccountTypeErrorDiv', '');
	get('bankAddress1').value = "";
	setDiv('bankAddress1ErrorDiv', '');
	get('bankAddress2').value = "";
	setDiv('bankAddress2ErrorDiv', '');
	get('bankCity').value = "";
	setDiv('bankCityErrorDiv', '');
	get('bankCountry').value = "";
	setDiv('bankCountryErrorDiv', '');
	get('currencyId').value = "";
	setDiv('currencyEr', '');
	get('bankState').value = "";
	setDiv('bankStateErrorDiv', '');
	get('bankPin').value = "";
	setDiv('bankPinEr', '');
	get('bankNameOnAccount').value = "";
	setDiv('bankNameOnAccountErrorDiv', '');
}

function resetBankInfoSub() {
	get('bankAccountName').value = "";
	setDiv('bankAccountNameErrorDiv', '');
	get('bankRoutingNumber').value = "";
	setDiv('bankRoutingNumberEr', '');
	get('bankAccountNumber').value = "";
	setDiv('bankAccountNumberErrorDiv', '');
	get('bankAccountType').value = "";
	setDiv('bankAccountTypeErrorDiv', '');
	get('bankAddress1').value = "";
	setDiv('bankAddress1ErrorDiv', '');
	get('bankAddress2').value = "";
	setDiv('bankAddress2ErrorDiv', '');
	get('bankCity').value = "";
	setDiv('bankCityErrorDiv', '');
	get('bankCountry').value = "";
	setDiv('bankCountryErrorDiv', '');
	get('bankState').value = "";
	setDiv('bankStateErrorDiv', '');
	get('bankPin').value = "";
	setDiv('bankPinEr', '');
	get('bankNameOnAccount').value = "";
	setDiv('bankNameOnAccountErrorDiv', '');
}

function resetBankInfoErrorMsg() {
	setDiv('bankAccountNameErrorDiv', '');
	setDiv('bankRoutingNumberEr', '');
	setDiv('bankAccountNumberErrorDiv', '');
	setDiv('bankRoutingNumberEr', '');
	setDiv('bankAccountTypeErrorDiv', '');
	setDiv('bankAddress1ErrorDiv', '');
	setDiv('bankAddress2ErrorDiv', '');
	setDiv('bankCityErrorDiv', '');
	setDiv('bankCountryErrorDiv', '');
	setDiv('currencyEr', '');
	setDiv('bankStateErrorDiv', '');
	setDiv('bankPinErrorDiv', '');
	setDiv('bankNameOnAccountErrorDiv', '');
	
}

function resetLegalEntityRepInfo() {
	get('legalName').value = "";
	setDiv('legalNameErrorDiv', '');
	get('legalTaxId').value = "";
	setDiv('legalTaxIdErrorDiv', '');
	get('legalType').value = "";
	setDiv('legalTypeErrorDiv', '');
	get('legalAnnualCard').value = "";
	setDiv('legalAnnualCardErrorDiv', '');
	get('legalAddress1').value = "";
	setDiv('legalAddress1ErrorDiv', '');
	get('legalAddress2').value = "";
	setDiv('legalAddress2ErrorDiv', '');
	get('legalCity').value = "";
	setDiv('legalCityErrorDiv', '');
	get('legalCountry').value = "";
	setDiv('legalCountryErrorDiv', '');
	get('legalState').value = "";
	setDiv('legalStateErrorDiv', '');
	get('legalPin').value = "";
	setDiv('legalPinEr', '');
}

function resetLegalEntityInfo() {
	get('legalSSN').value = "";
	setDiv('legalSSNErrorDiv', '');
	get('legalDOB').value = "";
	setDiv('legalDOBErrorDiv', '');
	get('legalPassport').value = "";
	setDiv('legalPassportErrorDiv', '');
	get('legalCountryResidence').value = "";
	setDiv('legalCountryResidenceErrorDiv', '');
	get('legalCitizen').value = "";
	setDiv('legalCitizenErrorDiv', '');
	get('legalHomePhone').value = "";
	setDiv('legalHomePhoneErrorDiv', '');
	get('legalFirstName').value = "";
	setDiv('legalFirstNameErrorDiv', '');
	get('legalLastName').value = "";
	setDiv('legalLastNameErrorDiv', '');
	get('legalMobilePhone').value = "";
	setDiv('legalMobilePhoneErrorDiv', '');
	get('legalPin').value = "";
	setDiv('legalPinEr', '');
}

function resetLegalEntityInfoErrorMsg() {
	setDiv('legalNameErrorDiv', '');
	setDiv('legalTaxIdErrorDiv', '');
	setDiv('legalTypeErrorDiv', '');
	setDiv('legalAnnualCardErrorDiv', '');
	setDiv('legalAddress1ErrorDiv', '');
	setDiv('legalAddress2ErrorDiv', '');
	setDiv('legalCityErrorDiv', '');
	setDiv('legalCountryErrorDiv', '');
	setDiv('legalStateErrorDiv', '');	
	
}

function resetLegalEntityErrorMsg() {
	setDiv('legalSSNErrorDiv', '');
	setDiv('legalDOBErrorDiv', '');
	setDiv('legalPassportErrorDiv', '');
	setDiv('legalCountryResidenceErrorDiv', '');
	setDiv('legalCitizenErrorDiv', '');
	setDiv('legalHomePhoneErrorDiv', '');
	setDiv('legalFirstNameErrorDiv', '');
	setDiv('legalLastNameErrorDiv', '');
	setDiv('legalMobilePhoneErrorDiv', '');
}

function resetAdditionalInfo() {
	get('userName').value = "";
	setError(get('userName'), '');
	$('#userNamegreenEr').text('');
	/*
	 * get('businessURL').value = ""; setError(get('businessURL'), '');
	 * get('federalTaxId').value = ""; setError(get('federalTaxId'), '');
	 */
	/*
	 * get('stateTaxId').value = ""; setError(get('stateTaxId'), '');
	 * get('salesTaxId').value = ""; setError(get('salesTaxId'), '');
	 */
	/*
	 * get('ownership').value = ""; setError(get('ownership'), '');
	 */
	/*
	 * get('businessStartDate').value = ""; setError(get('businessStartDate'),
	 * ''); get('estimatedYearlySale').value = "";
	 * setError(get('estimatedYearlySale'), ''); get('noOfEmployee').value = "";
	 * setError(get('noOfEmployee'), '');
	 */
	setDiv('userNamegreenEr', '');
}

function resetAdditionalInfoErrorMsg() {
	setError(get('userName'), '');
	// setError(get('businessURL'), '');
	// setError(get('federalTaxId'), '');
	/*
	 * setError(get('stateTaxId'), ''); setError(get('salesTaxId'), '');
	 */
	// setError(get('ownership'), '');
	// setError(get('businessStartDate'), '');
	// setError(get('estimatedYearlySale'), '');
	// setError(get('noOfEmployee'), '');
}

function resetConfigurationsInfo() {
	get('autoTransferWeeklyDay').value = "";
	setError(get('autoTransferWeeklyDay'), '');
	get('processor').value = "";
	setError(get('processor'), '');
	get('autoPaymentMethod').value = "";
	setError(get('autoPaymentMethod'), '');
	get('autoTransferLimit').value = "";
	setError(get('autoTransferLimit'), '');
	get('merchantCallBackURL').value = "";
	setError(get('merchantCallBackURL'), '');
	get('autoTransferDay').value = "";
	setError(get('autoTransferDay'), '');
	get('litleMID').value = "";
	setError(get('litleMID'), '');
	
	if (get('virtualTerminal').checked) {

		if (get('refunds')) { // && get('feeProgram')
			get('refunds').checked = false;
			setError(get('refunds'), '');
			get('tipAmount').checked = false;
			get('taxAmount').checked = false;
			get('shippingAmount').checked = false;
			/*get('allowRepeatSale').checked = false;
			get('showRecurringBilling').checked = false;*/
			get('virtualTerminal').checked = false;
			$("#virtualTerminalOptions").hide();
		}
	}

	//get('posTerminal').checked = false;

	if (get('online').checked) {
		get('webSiteAddress').value = "";
		// setError(get('webSiteAddress'), '');
		get('returnUrl').value = "";
		// setError(get('returnUrl'), '');
		get('cancelUrl').value = "";
		// setError(get('cancelUrl'), '');
		$('#onlineOptions').hide();
		get('online').checked = false;
	}

	setError(get('refunds'), '');
	get('autoTransferDay').value = "";
	get('autoTransferMonthlyDay').value = "";
	setError(get('autoTransferMonthlyDay'), '');
	get('autoTransferWeeklyDay').value = "";
	setError(get('autoTransferWeeklyDay'), '');
	
	// get('category').value = "";
	setError(get('category'), '');
	get('autoTransferMonthlyDay').value = "";
	$('#monthlySettlement').hide();
	setError(get('autoTransferMonthlyDay'), '');
	get('autoTransferWeeklyDay').value = "";
	$('#weeklySettlement').hide();
	setError(get('autoTransferWeeklyDay'), '');
	get('litleMID').value = "";
	setError(get('litleMID'), '');
	$('#vantivMerchantId').hide();
	get('bankId').value = "";
	setError(get('bankId'), '');
	get('mcc').value = "";
	setDiv('mccErr','');
	get('merchantCategory').value = "";
	setError(get('merchantCategory'), '');
}

function resetConfigurationsInfoErrorMsg() {
	setError(get('processor'), '');
	setError(get('refunds'), '');
	setError(get('autoPaymentMethod'), '');
	setError(get('autoTransferMonthlyDay'), '');
	setError(get('autoTransferWeeklyDay'), '');
	setError(get('category'), '');
	setDiv('mccErr','');
	if($('#parentMerchantId').val()==""){
		setError(get('merchantCategory'), '');
	}
}

function checkStatusAndMerchantType() {
	var status;
	if(get('status')!=null && get('status')!=''){
		status = get('status').value.trim();
	}
	var type = get('merchantType')!=null ? get('merchantType').value.trim() : '';
	if ("0" == status && type == "Merchant") {
		return true;
	} else {
		return false;
	}
}

function editSubMerchant(merchantId) {
	get('merchantEditId').value = merchantId;
	document.forms["editSubMercahntForm"].submit();
}

/*
 * function viewSubMerchants(merchantId) { get('merchantViewId').value =
 * merchantId; document.forms["viewSubMercahntForm"].submit(); }
 */

function getSubMerchantList(merchantId) {
	get('getParentMerchantId').value = merchantId;
	document.forms["getSubMerchantListForm"].submit();
}

function validRoutingNumber() {
	var fieldId = document.getElementById('bankRoutingNumber').value;
	if (fieldId == "") {
		setDiv('bankRoutingNumberEr', webMessages.thisFieldisMandatory);
		loadMsgTitleText();
		return false;
	}
	// all valid routing numbers are 9 numbers in length
	if (fieldId.length !== 9) {
		setDiv('bankRoutingNumberEr', webMessages.invalidLength);
		loadMsgTitleText();
		return false;
	}
	// if it aint a number, it aint a routin' number
	if (!$.isNumeric(fieldId)) {
		setDiv('bankRoutingNumberEr', webMessages.shouldContainonlyNumeric);
		loadMsgTitleText();
		return false;
	}
	// routing numbers starting with 5 are internal routing numbers
	// usually found on bank deposit slips
	if (fieldId[0] == '5') {
		setDiv('bankRoutingNumberEr', webMessages.shouldnotStartwith5);
		loadMsgTitleText();
		return false;
	}
	// http://en.wikipedia.org/wiki/Routing_transit_number#MICR_Routing_number_format
	var checksumTotal = (7 * (parseInt(fieldId.charAt(0), 10)
			+ parseInt(fieldId.charAt(3), 10) + parseInt(fieldId.charAt(6), 10)))
			+ (3 * (parseInt(fieldId.charAt(1), 10)
					+ parseInt(fieldId.charAt(4), 10) + parseInt(fieldId
					.charAt(7), 10)))
			+ (9 * (parseInt(fieldId.charAt(2), 10)
					+ parseInt(fieldId.charAt(5), 10) + parseInt(fieldId
					.charAt(8), 10)));
	var checksumMod = checksumTotal % 10;
	if (checksumMod !== 0) {
		setDiv('bankRoutingNumberEr', webMessages.invalidNumber);
		loadMsgTitleText();
		return false;
	} else {
		setDiv('bankRoutingNumberEr', "");
		return true;
	}
}

function showAutoTransferDayFields() {
	var fieldsValue = get('autoTransferDay').value;
	var flag = false;
	if (fieldsValue == "W") {
		$('#weeklySettlement').show();
		$('#monthlySettlement').hide();
		get('autoTransferMonthlyDay').value = "";
	}
	if (fieldsValue == "M") {
		$('#monthlySettlement').show();
		$('#weeklySettlement').hide();
		get('autoTransferWeeklyDay').value = "";
	}
	if (fieldsValue == "D" || fieldsValue == "") {
		$('#monthlySettlement').hide();
		$('#weeklySettlement').hide();
		get('autoTransferMonthlyDay').value = "";
		get('autoTransferWeeklyDay').value = "";
	}
}

function validateEmailIdEdit() {
	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var emailAddress = get('emailId').value.trim();
	if (isEmpty(emailAddress)) {
		setError(get('emailId'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else if (reg.test(emailAddress) == false) {
		setError(get('emailId'), webMessages.invalidEmailID);
		loadMsgTitleText();
		return false;
	} else {
		if (alpha.indexOf(emailAddress.charAt(0)) == -1) {
			setError(get('emailId'), webMessages.invalidEmailID);
			loadMsgTitleText();
			return false;
		} else {
			doAjaxFetchMailIdEdit();
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

function doAjaxFetchMailIdEdit() {
	var mailId = get('emailId').value.trim();
	var merchantCode = get('merchantCode').value.trim();
	$.ajax({
		type : "GET",
		url : "uniqueEmailIdEdit?emailId=" + mailId + "&merchantCode="
				+ merchantCode,
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
				setError(get('emailId'), webMessages.emailidAlreadyinUse);
				loadMsgTitleText();
				usernameFlag = true;
				;
			}
		},
		error : function(e) {
		}
	});
}

function vlalidateUserNameEdit() {
	var reg = /^[A-Za-z0-9 ]{8,16}$/;
	var userName = get('userName').value.trim();
	if (isEmpty(userName)) {
		setError(get('userName'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		document.getElementById('userNamegreenEr').innerHTML = "";
		return false;
	} else if (reg.test(userName) == false) {
		setError(get('userName'), webMessages.canContainAlphanumerics);
		loadMsgTitleText();
		document.getElementById('userNamegreenEr').innerHTML = "";
		return false;
	} else {
		doAjaxFetchUsernameAvailableEdit();
		if (usernameFlag == true) {
			return true;
		} else {
			return false;
		}
	}
}

function doAjaxFetchUsernameAvailableEdit() {
	var userName = get('userName').value.trim();
	var merchantCode = get('merchantCode').value.trim();
	$
			.ajax({
				type : "GET",
				url : "uniqueUserEdit?userName=" + userName + "&merchantCode="
						+ merchantCode,
				async : false,
				success : function(response) {
					var obj = JSON.parse(response);
					if (obj.errorCode === '00') {
						setError(get('userName'), '');
						document.getElementById('userNamegreenEr').innerHTML = "User Name available";
						setLable('confirmMuserName', get('userName').value
								.trim());
						usernameFlag = true;
					} else {
						setError(get('userName'), webMessages.userNamenotAvailable);
						loadMsgTitleText();
						document.getElementById('userNamegreenEr').innerHTML = "";
						usernameFlag = false;
					}
				},
				error : function(e) {
				}
			});
}

var isParentAndAgentDetailsAvailable = false;
function populatePartnerAndAgentDetails(appMode, merchantType, operationType,
		isAppModeChanged, parentMerchantId) {
	if (!isParentAndAgentDetailsAvailable) {
		var csrfToken = $("input[name=CSRFToken]").val();
		$
				.ajax({
					type : "POST",
					url : "get-partner-agent-details",
					data : {
						mode : appMode,
						merchantType : merchantType,
						isAppModeChanged : isAppModeChanged,
						parentMerchantId : parentMerchantId, CSRFToken: csrfToken
					},
					success : function(response) {
						isParentAndAgentDetailsAvailable = true;
						var jsonObj = JSON.parse(response);
						var ciResponse = jsonObj.ciResponse;
						if (ciResponse != undefined) {
							var pgmManagerDetails = ciResponse.ciProgramManagerDetails[0];
							var partnersList = pgmManagerDetails.partnerDetailList;
							var pgmManagerId = pgmManagerDetails.programManagerId;
							var selectObj;
							if (merchantType == 'merchant') {
								var linkedPartnersList;
								function initPartners() {
									linkedPartnersList = jsonObj.linkedPartnersList;
								}
								initPartners();

								selectObj = $('#partnerId');
								var linkedPartnerId = "";
								var linkedPartnerName = "";
								$(partnersList)
										.each(
												function(item) {
													if ($
															.inArray(
																	this.partnerId
																			.toString(),
																	linkedPartnersList) == -1) {
														selectObj
																.append($(
																		"<option />")
																		.val(
																				this.partnerId)
																		.text(
																				this.partnerName));
													} else {
														if (this.partnerId == $(
																'#linkedPartnerId')
																.val()) {
															linkedPartnerId = this.partnerId;
															linkedPartnerName = this.partnerName;
														}
													}
												});

								if (linkedPartnerId.length > 0
										&& linkedPartnerName.length > 0) {
									selectObj.append($("<option />").val(
											linkedPartnerId).text(
											linkedPartnerName));
								}

								if ($('#partnerId option').length == 1) {
									selectObj.addClass('NPA');
								}

								if (operationType == 'update'
										|| operationType == 'view') {

									selectObj.val($('#linkedPartnerId').val());

									if (selectObj.val() == null)
										selectObj.val('');
								}

								$('#programManagerId').val(pgmManagerId);

								selectObj.on('change', function() {
									if (selectObj.hasClass('NPA')
											&& selectObj.val() == '') {
										$('#partnerIdErrorDiv').text(
												'All Partners Are Linked.');
									}
								});
							} else if (merchantType == 'sub-merchant') {
								selectObj = $('#agentId');
								var linkedPartnerId = $('#linkedPartnerId')
										.val();

								var issuancePartnerId = jsonObj.linkedPartnerId;

								if (issuancePartnerId.length > 0) {
									linkedPartnerId = issuancePartnerId;
								}

								if (linkedPartnerId != '') {
									$('#issuancePartnerId')
											.val(linkedPartnerId);
									var linkedAgentsList;
									var merchantLinkedPartnerId;
									function initAgents() {
										linkedAgentsList = jsonObj.linkedAgentsList;
										merchantLinkedPartnerId = linkedPartnerId;
									}
									initAgents();

									var linkedAgentId = "";
									var linkedAgentName = "";
									$(partnersList)
											.each(
													function(partner) {
														if (this.partnerId == merchantLinkedPartnerId) {
															var ciAgentDetails = this.ciAgentDetails;
															$(ciAgentDetails)
																	.each(
																			function(
																					agent) {
																				if ($
																						.inArray(
																								this.agentId,
																								linkedAgentsList) == -1) {
																					selectObj
																							.append($(
																									"<option />")
																									.val(
																											this.agentId)
																									.text(
																											this.agentName));
																				} else {
																					if (this.agentId == $(
																							'#linkedAgentId')
																							.val()) {
																						linkedAgentId = this.agentId;
																						linkedAgentName = this.agentName;
																					}
																				}
																			});
															return false;
														}
													});

									if ((merchantLinkedPartnerId.length > 0
											&& linkedAgentId.length > 0 && linkedAgentName.length > 0)
											|| operationType == 'view') {
										selectObj.append($("<option />").val(
												linkedAgentId).text(
												linkedAgentName));
									}

									$('#agentDetails').show();
									if ($('#agentId option').length == 1) {
										selectObj.addClass('NPA');
									}

									if (operationType == 'update'
											|| operationType == 'view') {
										selectObj
												.val($('#linkedAgentId').val());

										if (selectObj.val() == null)
											selectObj.val('');
									}

									$('#programManagerId').val(pgmManagerId);

									selectObj
											.on(
													'click',
													function() {
														if (selectObj
																.hasClass('NPA')
																&& selectObj
																		.val() == '') {
															$(
																	'#agentIdErrorDiv')
																	.text(
																			'No Agents available or all Agents are already linked.');
														}
													});
								}
							}
						}

					},
					error : function(e) {
					}
				});
	}
}

function viewMerchantInfo(merchantId, merchantType) {
	get('merchantViewId').value = merchantId;
	get('merchantType').value = merchantType;
	document.forms["viewMercahntForm"].submit();
}

function showMerchantAccountCreateForm($this) {
	$('#merchantCodeToCreate').val(
			$($this).closest('tr').attr('data-merchantCode'));
	$('#tempMerchantType').val($('#merchantType').val());
	document.forms["merchantAccountCreate"].submit();
}

function validateAccountDetails() {
	var flag = true;
	if (!clientValidation('bankAccountName', 'first_name_SplChar',
			'bankAccountNameErrorDiv')
			| !clientValidation('bankAccountNumber', 'account_numberBank',
					'bankAccountNumberErrorDiv')
			| !clientValidation('bankRoutingNumber', 'routing_number',
					'bankRoutingNumberEr')
			| !clientValidation('bankAccountType', 'account_type',
					'bankAccountTypeErrorDiv')
			| !clientValidation('bankAddress1', 'bank_address2',
					'bankAddress1ErrorDiv')
			| !clientValidation('bankAddress2', 'bank_address2',
					'bankAddress2ErrorDiv')
			| !clientValidation('bankCity', 'bank_address2', 'bankCityErrorDiv')
			| !clientValidation('bankCountry', 'country', 'bankCountryErrorDiv')
			| !clientValidation('bankState', 'state', 'bankStateErrorDiv')
			| !clientValidation('bankPin', 'zip_code', 'bankPinErrorDiv')
			| !clientValidation('bankNameOnAccount', 'first_name_SplChar',
					'bankNameOnAccountErrorDiv')
			/*| !clientValidation('autoTransferWeeklyDay', 'state',
					'autoTransferWeeklyDayEr')
			| !clientValidation('autoTransferMonthlyDay', 'state',
					'autoTransferMonthlyDayEr')
			| !validateAutoTransferLimit()
			| !validateAutoPaymentMethod()*/
					/*| !validateRadio()*/) {
		flag = false;
		return flag;
	}
}

function changeAccountStatus(id, status) {
	clearPopupDesc();
	$('#my_popup').popup('show');
	setDiv("sts", " Do you wish to change Account status to " + status + '?');
	$('#csAccountId').val(id);
	$('#csAccountStatus').val(status);
	$('#csMerchantType').val($('#merchantType').val());
}

function editMerchantAccount(id, merchantCode, merchantType) {
	$('#editAccountId').val(id);
	$('#editmerchantCode').val(merchantCode);
	$('#editMerchantType').val(merchantType);
	document.forms["editMercahntAccountForm"].submit();
}

function resetMerchantAccountSearch() {
	var merchantType = get('merchantType').value.trim();
	
	if (merchantType == 'SubMerchant') {
		window.location.href = 'sub-merchant-account-search';
	} else {
		window.location.href = 'merchant-account-search';
	}

}

// NEWLY ADDED START FROM HERE

// VIRTUAL TERMINAL
function validateVirtualTerminal() {
	if ($('#virtualTerminal').is(":checked")) {
		$("#virtualTerminalOptions").show();
		setError(get('refunds'), '');
	} else {
		$(
				"#refunds, #tipAmount, #taxAmount, #shippingAmount")//, #allowRepeatSale, #showRecurringBilling
				.prop("checked", false);
		validateCheckBox();
		$("#virtualTerminalOptions").hide();
	}
	return true;
}

// POS TERMINAL
function validatePos() {
	var list1 = "";
		list1 = list1 + document.getElementById('posTerminal').name;
		return true;
}

// ONLINE TERMINAL
function validateOnlineOptions() {
	if ($('#online').is(":checked")) {
		$("#onlineOptions").show();
		setDiv("webSiteAddrErr", "");
		setDiv("returnURLErr", "");
		setDiv("cancelURLErr", "");
		return true;
	} else {
		$("#webSiteAddress, #returnUrl, #cancelUrl").val('');
		validateOnlineOptionsList();
		$("#webSiteAddressErrorDiv, #returnURLErrorDiv, #cancelURLErrorDiv")
				.hide();
		$("#onlineOptions").hide();
		return true;
	}
}

function validateOnlineOptionsList() {
	validateWebSiteAddressURL();
	validateReturnURL();
	validateCancelURL();
}

function continueBtnValidateForOnline() {
	if (document.getElementById('online').checked) {
		if (validateWebSiteAddressURL() && validateReturnURL()
				&& validateCancelURL()) {
			return true;
		}
	} else {
		return true;
	}
}

function validateWebSiteAddressURL() {
	var reg = /(http|https:\/\/[^\s\.]+\.[^\s]{2,}|www\.[^\s]+\.[^\s]{2,})/;
	var webSiteAddress = get('webSiteAddress').value.trim();
	if (isEmpty(webSiteAddress)) {
		setDiv("webSiteAddrErr", webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		setLable('confirmMwebSiteAddress', webSiteAddress);
		return false;
	} else if (reg.test(webSiteAddress) == false) {
		setDiv("webSiteAddrErr", webMessages.invalidWebSiteAddress);
		loadMsgTitleText();
		setLable('confirmMwebSiteAddress', "");
		return false;
	} else {
		setDiv("webSiteAddrErr", "");
		setLable('confirmMwebSiteAddress', webSiteAddress);
		return true;
	}
}

function validateReturnURL() {
	var reg = /(http|https:\/\/[^\s\.]+\.[^\s]{2,}|www\.[^\s]+\.[^\s]{2,})/;
	var returnUrl = get('returnUrl').value.trim();
	if (isEmpty(returnUrl)) {
		setDiv("returnURLErr", webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		setLable('confirmMreturnURL', returnUrl);
		return false;
	} else if (reg.test(returnUrl) == false) {
		setDiv("returnURLErr", webMessages.invalidReturnURL);
		loadMsgTitleText();
		setLable('confirmMreturnURL', "");
		return false;
	} else {
		setDiv("returnURLErr", "");
		setLable('confirmMreturnURL', returnUrl);
		return true;
	}
}

function validateCancelURL() {
	var reg = /(http|https:\/\/[^\s\.]+\.[^\s]{2,}|www\.[^\s]+\.[^\s]{2,})/;
	var cancelUrl = get('cancelUrl').value.trim();
	if (isEmpty(cancelUrl)) {
		setDiv("cancelURLErr", webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		setLable('confirmMcancelURL', cancelUrl);
		return false;
	} else if (reg.test(cancelUrl) == false) {
		setDiv("cancelURLErr", webMessages.invalidCancelURL);
		loadMsgTitleText();
		setLable('confirmMcancelURL', "");
		return false;
	} else {
		setDiv("cancelURLErr", "");
		setLable('confirmMcancelURL', cancelUrl);
		return true;
	}
}

function continueBtnValidateVirtualTerminal() {
	if (document.getElementById('virtualTerminal').checked) {
		if (validateCheckBox()) {
			return true;
		}
	} else {
		return true;
	}
}

function goToDashBoard() {
	window.location.href = 'home';
}

function showButtonForPosAndRemaining() {
	if ($('#posTerminal').is(":checked")) {
		$("#showForPos").show();
		$("#declineButton").hide();
		return true;
	} else {
		$("#showForRemaining").show();
		return true;
	}
}

function downloadNMAS(id) {
	get('merchantDownloadId').value = id;
	document.forms["downloadNMASToShow"].submit();
}

function validateCreateSubMerchantStep5() {
	var flag = true;
	if (!validateProcessor() | !validateVirtualTerminal()
			| !validateOnlineOptions() /*| !validatefeeProgram()*/
			/*| !validateRadio()*/ | !validateCallbackURL() | !validateCategory()
			| !validateAutoPaymentMethod() | !validateAutoTransferLimit()
			| !continueBtnValidateForOnline()
			| !continueBtnValidateVirtualTerminal()) {
		return false;
	}
	var fieldsValue = get('autoTransferDay').value;
	var processor = get('processor').value.trim();
	var parentMerchantId = get('parentMerchantId').value.trim();
	if (get('partnerId')) {
		($('#partnerId').val() == '') ? setLable('confirmPartner', '')
				: setLable('confirmPartner', $('#partnerId  option:selected')
						.text());
	} else if (get('agentId')) {
		($('#agentId').val() == '') ? setLable('confirmAgent', '') : setLable(
				'confirmAgent', $('#agentId  option:selected').text());
	}
	if (fieldsValue == "") {
		setLable('confirmMautoTransferDay', "");
	}
	if (fieldsValue == "D") {
		setLable('confirmMautoTransferDay', "Daily");
		$('#hideDayTable').hide();
		$('#hideWeekyTable').hide();
		setLable('confirmAutoTransferWeeklyDay', "");
		setLable('confirmAutoTransferMonthlyDay', "");
	}
	if (fieldsValue == "W") {
		if (!clientValidation('autoTransferWeeklyDay', 'state',
				'autoTransferWeeklyDayEr')) {
			return false;
		} else {
			setLable('confirmMautoTransferDay', "Weekly");
			$('#hideDayTable').show();
			$('#hideWeekyTable').hide();
			setDiv('autoTransferWeeklyDayEr', "");

			var weekValue = document.getElementById("autoTransferWeeklyDay");
			var weekDay = weekValue.options[weekValue.selectedIndex].text;
			setLable('confirmAutoTransferWeeklyDay', weekDay);
		}
	}
	if (fieldsValue == "M") {
		if (!clientValidation('autoTransferMonthlyDay', 'state',
				'autoTransferMonthlyDayEr')) {
			return false;
		} else {
			setLable('confirmMautoTransferDay', "Monthly");
			$('#hideDayTable').hide();
			$('#hideWeekyTable').show();
			setDiv('autoTransferMonthlyDayEr', "");
			setLable('confirmAutoTransferMonthlyDay',
					get('autoTransferMonthlyDay').value.trim());
		}
	}
	if (processor == "LITLE") {
		return validatelitleMID();
	} else {
		setLable('confirmLitleMID', '');
	}
	if (parentMerchantId != "") {
		setLable('confirmMerchantCode', parentMerchantId);
	}else {
		setLable('confirmMerchantCode', "");
	}
	return flag;

}

function validateLegalDOB() {

	var enteredValue = document.getElementById('legalDOB');
	var enteredAge = getAge(enteredValue.value);
	
	if (isEmpty(enteredValue)) {
		setDiv("legalDOBErrorDiv", "");
		return true;
	}else if (enteredAge < 0) {
		setDiv("legalDOBErrorDiv", webMessages.pleaseEnterValidDOB);
		loadMsgTitleText();
		return false;
	} else if (enteredAge < 18) {
		setDiv("legalDOBErrorDiv", webMessages.DOBisLessThan18years);
		loadMsgTitleText();
		return false;
	} else {
		setDiv("legalDOBErrorDiv", "");
		return true;
	}
}

function getAge(DOB) {
	var today = new Date();
	var birthDate = new Date(DOB);
	var age = today.getFullYear() - birthDate.getFullYear();
	var m = today.getMonth() - birthDate.getMonth();
	if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
		age--;
	}
	return age;
}        

function validateMcc(){              
	var mcc = get('mcc').value.trim();
	if (isEmpty(mcc)) {
		setDiv("mccErr", webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else {
		setDiv("mccErr", '');
		setLable('confirmMcc', mcc);
		return true;
	}  
}

function validateBank(){              
	var bankId = get('bankId').value.trim();
	if (isEmpty(bankId)) {
		setDiv("bankIdEr", webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else {
		setDiv("bankIdEr", '');
		setLable('confirmBankName', bankIdEr);
		return true;
	}
}

function validatelocalCurrency(){              
	var localCurrency = get('localCurrency').value.trim();
	if (isEmpty(localCurrency)) {
		setDiv("localCurrencyEr", webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else {
		setDiv("localCurrencyEr", '');
		setLable('confirmlocalCurrency', localCurrencyEr);
		return true;
	}
}

function validateCheckPayoutAt() {
	var payOutAt = get('payOutAt').value.trim();
	if (isEmpty(payOutAt)) {
		setError(get('payOutAt'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('payOutAt'), '');
		setLable('confirmMpayOutAt', payOutAt);
		return true;
	}
}

function validateMerchantCategory() {
	var merchantCategory = get('merchantCategory').value.trim();
	if (isEmpty(merchantCategory)) {
		setError(get('merchantCategory'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('merchantCategory'), '');
		setLable('confirmMmerchantCategory', merchantCategory);
		return true;
	}
}

function selectedCurrency()
{
	  var currencyCode = $.map($('#currencyCodes option'), function(e) { return e.value; });
	  get('currencyCode').value = currencyCode;
}

function SelectSort(SelList) {
	var ID = '';
	var Text = '';
	for (x = 0; x < SelList.length - 1; x++) {
		for (y = x + 1; y < SelList.length; y++) {
			if (SelList[x].text > SelList[y].text) {
				// Swap rows
				ID = SelList[x].value;
				Text = SelList[x].text;
				SelList[x].value = SelList[y].value;
				SelList[x].text = SelList[y].text;
				SelList[y].value = ID;
				SelList[y].text = Text;
			}
		}
	}
}

function declineMerchantPopUp(id) {
	clearPopupDesc();
	$('#declineMerchantDiv').popup('show');
	get('merchantId').value = id;
}

function ssnValidation(){
	var ssn = get('legalSSN').value.trim();
	if(ssn != ""){
	 if(ssn.length <11){
      	setDiv("legalSSNErrorDiv", webMessages.SSNshouldNotbeLesshan9);
      	loadMsgTitleText();
      	return false;
      }else{
     	 setDiv("legalSSNErrorDiv", " ");
     	 return true;
      }
	 }
	setDiv("legalSSNErrorDiv", " ");
	return true;
}
function doCheckPayoutAt() {
	var payOutAt = get('payOutAt').value.trim();
	var payOutAt1 = $('#payOutAt').text().trim();
	if (isEmpty(payOutAt)) {
		setError(get('payOutAt'), webMessages.pleaseSelectPayoutAt);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('payOutAt'), '');
		setLable('confirmMpayOutAt', payOutAt1);
		return true;
	}
}

function validateCreateAccountDetails() {
	var flag = true;
	if (!clientValidation('bankAccountName', 'first_name_SplChar',
			'bankAccountNameErrorDiv')
			| !clientValidation('bankAccountNumber', 'account_numberBank',
					'bankAccountNumberErrorDiv')
			| !clientValidation('bankRoutingNumber', 'routing_number',
					'bankRoutingNumberEr')
			| !clientValidation('bankAccountType', 'account_type',
					'bankAccountTypeErrorDiv')
			| !clientValidation('bankCountry', 'country', 'bankCountryErrorDiv')
			| !clientValidation('bankState', 'state', 'bankStateErrorDiv')
			| !clientValidation('bankPin', 'zip_code', 'bankPinErrorDiv')
			| !clientValidation('bankNameOnAccount', 'first_name_SplChar',
					'bankNameOnAccountErrorDiv')
			| !validateAutoPaymentMethod()
			| !validateRadio()) {
		flag = false;
		return flag;
	}
}

var isValidAlphaNumeric = function(elemValue) {
	var reg = /[^A-Za-z0-9 ]/;
	return reg.test(elemValue);
};

function validateAgentAccountNumber() {
	var accNum = document.getElementById('agentAccountNumber');
	accNum.value = accNum.value.trim();
	/*if(isEmpty(accNum.value)) {
		setDiv("agentAccountNumberEr", webMessages.InvalidAgentAccNumberEmpty);
		return false;
	} else if(isValidAlphaNumeric(accNum.value)) {
		setDiv("agentAccountNumberEr", webMessages.InvalidAgentAccNumber);
		return false;
	}*/
	setLable('confirmAgentAccountNumber', accNum.value);
	setDiv("agentAccountNumberEr", '');
	return true;
}

function validateAgentClientId() {
	var agentClientId = document.getElementById('agentClientId');
	agentClientId.value = agentClientId.value.trim();
	/*if(isEmpty(agentClientId.value)) {
		setDiv("agentClientIdEr", webMessages.InvalidAgentClientIdEmpty);
		return false;
	} else if(isValidAlphaNumeric(agentClientId.value)) {
		setDiv("agentClientIdEr", webMessages.InvalidAgentClientId);
		return false;
	}*/
	setLable('confirmAgentClientid', agentClientId.value);
	setDiv("agentClientIdEr", '');
	return true;
}

function validateAgentANI() {
	var agentANI = document.getElementById('agentANI');
	agentANI.value = agentANI.value.trim();
	/*if(isEmpty(agentANI.value)) {
		setDiv("agentANIEr", webMessages.InvalidAgentANIEmpty);
		return false;
	} else if(isValidAlphaNumeric(agentANI.value)) {
		setDiv("agentANIEr", webMessages.InvalidAgentANI);
		return false;
	}*/
	setLable('confirmAgentANI', agentANI.value);
	setDiv("agentANIEr", '');
	return true;
}
function appendDollarSymbol(){
	var annualCardValue = document.getElementById('legalAnnualCard').value;
	var tempNumber = annualCardValue;
	var res = annualCardValue.charAt(0);
	if(annualCardValue != ""){
		/*if(res != "$"){
			var tempNumber =  "$" + annualCardValue;
		}*/
		if(annualCardValue.indexOf('.') === -1){
			document.getElementById('legalAnnualCard').value = tempNumber + ".00"
		}else{
			document.getElementById('legalAnnualCard').value = tempNumber;
		}
	}
}

$('#emailId').keypress(function( e ) {
    if(e.which === 32) 
        return false;
});

function fetchCurrency(currencyId,elementId) {
	if (currencyId == '') {
		//clearLocalCurrency(localCurrency);
		return null;
	}
	getfetchCurrency(currencyId,elementId);
}

function getfetchCurrency(currencyId,elementId) {
	$.ajax({
		type : "GET",
		url : "getLocalCurrencyandbankName?currencyId=" + currencyId,
		async : false,
		success : function(responseVal) {
			var obj = JSON.parse(responseVal);
			if (obj.errorCode === '00') {
				document.getElementById(elementId).options.length = 0;
				var data = obj.responseList;

				for (var i = 0; i < data.length; i++) {
					var bankName = data[i].label;

					var newOption = document.createElement("option");
					newOption.value = data[i].value;
					newOption.innerHTML = bankName;

					$(("#" + elementId)).append(newOption);
				}
				setValue('localCurrency', get('currencyId').value.trim())
				setLable('confirmMlocalCurrency', get('localCurrency').value.trim());
			}
		},
		error : function(e) {
		}
	});
}


function fetchMerchantCurrency(parentMerchantId) {
	if (parentMerchantId == '') {
		//clearLocalCurrency(localCurrency);
		return null;
	}
	getfetchMerchantCurrency(parentMerchantId);
}

function getfetchMerchantCurrency(parentMerchantId) {
	$.ajax({
		type : "GET",
		url : "getmerchantCodeByCurrency?parentMerchantId=" + parentMerchantId,
		async : false,
		success : function(responseVal) {
			var obj = JSON.parse(responseVal);
			if (obj.errorCode === '00') {
				
				setValue('localCurrency',obj.currencyCodeAlpha );
				setLable('confirmMcurrencyId', get('localCurrency').value.trim());
			}
		},
		error : function(e) {
		}
	});
}

function validateAgentName() {
	var agentId = get('agentId').value.trim();
	var agentName = $('#agentId option:selected').text();
	if (isEmpty(agentId)) {
		get('agentAccountNumber').value = "";
		get('agentClientId').value = "";
		get('agentANI').value = "";
		setDiv('agentIdErr', webMessages.validationthisfieldismandatory);
		return false;
	} else {
		setDiv('agentIdErr', '');
		setLable('confirmAgentName', agentName);
		return true;
	}
}

function fetchAgentData(currencyId) {
	if (currencyId != '') {
		getAgentData(currencyId);
	} else {
		setValue('agentAccountNumber', "");
		setValue('agentClientId', "");
		setValue('agentANI', "");
	}
}

function getAgentData(agentId) {
	$.ajax({
		type : "GET",
		url : "getAgentDataById?agentId=" + agentId,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorMessage == "SUCCESS") {
				setValue('agentAccountNumber', obj.agentDTOlist[0].agentAccountNumber);
				setValue('agentClientId',obj.agentDTOlist[0].agentClientId);
				setValue('agentANI',obj.agentDTOlist[0].agentAni);
			}
		},
		error : function(e) {
		}
	});
}

function fetchAgentNames(currencyAlpha, elementId) {
	if (currencyAlpha == '') {
		return;
	}
	getAgentNames(currencyAlpha,elementId);
}

function getAgentNames(currencyAlpha,elementId) {
	$.ajax({
		type : "GET",
		url : "getAgentNamesByCurrencyAlpha?currencyAlpha=" + currencyAlpha,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorCode === '00') {
				// remove the previous option from element
				document.getElementById(elementId).options.length = 0;
				// create select option
				var selectOption = document.createElement("option");
				selectOption.innerHTML = "..:Select:..";
				selectOption.value = "";
				$(("#" + elementId)).append(selectOption);

				if (obj.errorMessage == "SUCCESS") {
					var data = obj.responseList;

					for (var i = 0; i < data.length; i++) {
						var state = data[i].value;

						var newOption = document.createElement("option");
						newOption.value = data[i].label;
						newOption.innerHTML = state;

						$(("#" + elementId)).append(newOption);
					}
					}
				}
		},
		error : function(e) {
		}
	});
}

function getProgramManagerDetails(partnerId) {
	if (partnerId != '') {
		getCurrencyData(partnerId);
	}
}

function getCurrencyData(partnerId) {
	$.ajax({
		type : "GET",
		url : "getProgramManagerDetailsByPartner?partnerId=" + partnerId,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorMessage == "SUCCESS") {
				setValue('programManagerId', obj.programManagerName);
			}
		},
		error : function(e) {
		}
	});
}

function validatePartnerId() {
	var partnerId = get('partnerId').value.trim();
	if (isEmpty(partnerId)) {
		setError(get('partnerId'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('partnerId'), '');
		return true;
	}
}

function fetchPartnerNameByPm(pmID, elementId) {
	getPartners(pmID, elementId);
}

function getPartners(pmID, elementId) {
	$.ajax({
		type : "GET",
		url : "getPartnersByProgramManagerId?programManagerId=" + pmID,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorCode === '00') {
				// remove the previous option from element
				document.getElementById(elementId).options.length = 0;
				// create select option
				var selectOption = document.createElement("option");
				selectOption.innerHTML = "..:Select:..";
				selectOption.value = "";
				$(("#" + elementId)).append(selectOption);

				if (obj.errorMessage == "SUCCESS") {
					var data = obj.responseList;

					for (var i = 0; i < data.length; i++) {
						var state = data[i].label;

						var newOption = document.createElement("option");
						newOption.value = data[i].value;
						newOption.innerHTML = state;

						$(("#" + elementId)).append(newOption);
					}
				}
			}
		},
		error : function(e) {
		}
	});
}

function validateCreateSubMerchantStep1() {
	var flag = true;
	if (!validateBusinessName() | !validateFirstName() | !validateLastName()
			| !validatePhone() | !validateAddress1() | !validateCity() | !validateEmailId()
			| !validateState() | !validateCountry() | !validatePin()
			| !validateAppMode() | !validateURL() | !validateParentMerchantId() |!vlalidateUserName() |!createValidationForBasicInfo()) {
		return false;
	} else {
		var faxValue = getVal('fax').trim();
		var businessTypeValue = getVal('businessType').trim();
		if (faxValue != "") {
			setLable('confirmMfax', faxValue);
		} else {
			setLable('confirmMfax', "");
		}
		if (businessTypeValue != "") {
			setLable('confirmBusinessType', businessTypeValue);
		} else {
			setLable('confirmBusinessType', "");
		}
	}
	return flag;
}

function validatePmIsoCardprogram() {
	var flag = true;
	if (!validateAssociatedTo() | !validateAssociatedToEntityName()) {
		return false;
	}
	
	return flag;
}
var entityType;
var elementId;
function validateAssociatedTo() {
	var associatedTo = get('associatedTo').value.trim();
	if (isEmpty(associatedTo)) {
		setError(get('associatedTo'), webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('associatedTo'), '');
		setLable('confirmAssociatedTo', get('associatedTo').value.trim());
		return true;
	}
}

function resetpmIsoCardprogarmErrorMsg() {
	$('#associatedTo').val('');
	document.getElementById("programManagerName").innerHTML = "";
	document.getElementById("programManagerNameId").innerHTML = "";
	$("#serviceResults > tbody").html("");
	setError(get('associatedTo'), '');
	setError(get('programManagerNameId'), '');
	$('#ambiguityFlag').text('');
}

function fetchEntityNameByPmIso(entityType,elementId) {
		getAllEntityName(entityType,elementId);
}


function getAllEntityName(entityType,elementId) {
	var currencyId = get('currencyId').value.trim();
	$.ajax({
		type : "GET",
		url : "getAllEntityName?entityType=" + entityType + "&currencyId=" + currencyId,
		async : false,
		success : function(responseVal) {
			var obj = JSON.parse(responseVal);
			if (obj.errorCode === '00') {
				document.getElementById(elementId).options.length = 0;
				var data = obj.responseList;

				for (var i = 0; i < data.length; i++) {
					var programManagerName = data[i].label;
					var newOption = document.createElement("option");
					newOption.value = data[i].value;
					newOption.innerHTML = programManagerName;

					$(("#" + elementId)).append(newOption);
				}
			}
			$(("#" + elementId)).append("");
		},
		error : function(e) {
		}
	});
}

function validateAssociatedToEntityName() {
	if (ValidationRules.fee_Short_Code.mandatory == true) {
		var entityVal = get('programManagerNameId');
		if(entityVal == null || entityVal.options.length <= 0) {
			setError(get('programManagerNameId'), webMessages.validationthisfieldismandatory);
			loadMsgTitleText();
			return false;
		}
		else
			{
			setError(get('programManagerNameId'), '');
			return true;
			}
}
	return true;
}
var type;
function getCardProgramByPmId(pmId){
	if(pmId !=null && pmId != ""){
		entityType = get('associatedTo').value.trim();
		doAjaxToGetCardProgramByPmId(pmId, entityType);
	}
}

var entityName;
var entityId;
function doAjaxToGetCardProgramByPmId(pmId,entityType){
	var currencyId = get('currencyId').value.trim();
	$.ajax({
		type : "GET",
		url : "getCardProgramByPmId?pmId=" + pmId + "&entityType=" + entityType + "&currencyId=" + currencyId,
		success : function(response) {
			
			if(response !=null && response != ""){
				var obj = JSON.parse(response);
				
				if (obj.errorMessage == "SUCCESS") {
					var count=0;
					for (var i = 0; i < obj.cardProgramList.length; i++) {
						var programManagerName = obj.cardProgramList[i].programManagerName;
						var panLow = obj.cardProgramList[i].panLow;
						var panHigh = obj.cardProgramList[i].panHigh;
						var currency = obj.cardProgramList[i].currency;
						var cardProgramId = obj.cardProgramList[i].cardProgramId;
						if(obj.cardProgramList[i].programManagerName != null){
							entityName = obj.cardProgramList[i].programManagerName;
						}else{
							entityName = obj.cardProgramList[i].isoName;
						}
						var currency = obj.cardProgramList[i].currency;
						if(entityType == 'Program Manager'){
							entityId = obj.cardProgramList[i].programManagerId;
						}else{
							entityId = obj.cardProgramList[i].isoId;
						}
						var recRow = '<tr id="rowId'+cardProgramId+entityId+'">'
							+'<td>'+panLow+'</td>'
							+'<td>'+panHigh+'</td>'
							+'<td>'+programManagerName+'</td>'
							+'<td>'+currency+'</td>'
							+'<td>'+entityName+'</td>'
							+'<td data-title="Action"><input id="cpId'+cardProgramId+''+entityId+'" type="checkbox" onclick="addCardProgram('+cardProgramId+',\''+entityName+'\',\''+entityId+'\')"></td>'
					       +'</tr>';	
							jQuery('#serviceResults').append(recRow);
							count++;
					}
				
				}	
			}
		},
		error : function(e) {

		}
	});
}

function removeCardProgramFromList(pmId){
	if(pmId !=null && pmId != ""){
		entityType = get('associatedTo').value.trim();
		doAjaxToRemoveCardProgramByPmId(pmId, entityType);
	}
}

function doAjaxToRemoveCardProgramByPmId(pmId,entityType){
	var currencyId = get('currencyId').value.trim();
	$.ajax({
		type : "GET",
		url : "getCardProgramByPmId?pmId=" + pmId + "&entityType=" + entityType + "&currencyId=" + currencyId,
		success : function(response) {
			
			if(response !=null && response != ""){
				var obj = JSON.parse(response);
				
				if (obj.errorMessage == "SUCCESS") {
					for (var i = 0; i < obj.cardProgramList.length; i++) {
						var cardProgramId = obj.cardProgramList[i].cardProgramId;
						var cardProgramName = obj.cardProgramList[i].cardProgramName;
						
						var rowId = 'rowId'+ cardProgramId+pmId;
							$('#'+rowId).remove();
							doUnCheckedToCardProgram(cardProgramId,cardProgramName,pmId);
					}
				}	
			}
		},
		error : function(e) {

		}
	});
}

function validateEntityType(){
	validateAssocated();
}

function validateEditPmIsoCardprogram() {
	setSelectedPmAndCpId();
	var flag = true;
	if (!validateAssociatedTo() | !validateAssociatedToEntityName()) {
		return false;
	}
	
	return flag;
}

function fetchCardProgramByMerchantId(merchantId){
	var entityType = get('associatedTo').value.trim();
	$.ajax({
		type : "GET",
		url : "fetchCardProgramByMerchantId?merchantId=" + merchantId + "&entityType=" + entityType,
		success : function(response) {
			
			if(response !=null && response != ""){
				var obj = JSON.parse(response);
				
				if (obj.errorMessage == "SUCCESS") {
					for (var i = 0; i < obj.cardProgramList.length; i++) {
						var cardProgramId = obj.cardProgramList[i].cardProgramId;
						var cardProgramName = obj.cardProgramList[i].cardProgramName;
						var partnerName = obj.cardProgramList[i].partnerName;
						var partnerCode = obj.cardProgramList[i].partnerCode;
						var iin = obj.cardProgramList[i].iin;
						var iinExt = obj.cardProgramList[i].iinExt;
						if(obj.cardProgramList[i].programManagerName != null){
							entityName = obj.cardProgramList[i].programManagerName;
						}else{
							entityName = obj.cardProgramList[i].isoName;
						}
						var currency = obj.cardProgramList[i].currency;
						if(obj.cardProgramList[i].programManagerId != null){
							entityId = obj.cardProgramList[i].programManagerId;
						}else{
							entityId = obj.cardProgramList[i].isoId;
						}
						var recRow = '<tr id="rowId'+cardProgramId+'">'
							+'<td>'+partnerName+'</td>'
							+'<td>'+cardProgramName+'</td>'
							+'<td>'+iin+'</td>'
							+'<td>'+partnerCode+'</td>'
							+'<td>'+iinExt+'</td>'
							+'<td>'+entityName+'</td>'
							+'<td>'+currency+'</td>'
							+'<td data-title="Action"><input id="cpId'+cardProgramId+''+entityId+'" type="checkbox" onclick="addCardProgram('+cardProgramId+',\''+cardProgramName+'\',\''+entityName+'\',\''+entityId+'\')"></td>'
					       +'</tr>';	
							jQuery('#serviceResults').append(recRow);
					}
				}	
			}
		},
		error : function(e) {

		}
	});
}

function openCreateCancelConfirmationPopup() {
	if ((isEmpty(get('businessName').value))
			&& (isEmpty(get('businessURL').value.trim()))
			&& (isEmpty(get('firstName').value.trim()))
			&& (isEmpty(get('lastName').value.trim()))
			&& (isEmpty(get('phone').value.trim()))
			&& (isEmpty(get('city').value.trim()))
			&& (isEmpty(get('country').value.trim()))
			&& (isEmpty(get('emailId').value.trim()))
			&& (isEmpty(get('address1').value.trim()))
			&& (isEmpty(getVal('pin')))
			&& (isEmpty(get('state').value.trim()))) {
		window.location.href = 'sub-merchant-search-page';
	}
	else{
		$('#my_popup1').popup("show");
     }
}

function validateSpecialCharactersMerchant() {
	if (!clientValidation('merchantCode','merchant_ID','merchantCodeEr')
			| !clientValidation('businessName','companyname_not_mandatory','businessNameEr')
			| !clientValidation('entityType','middle_name','entityTypeEr')
			| !clientValidation('isoName','companyname_not_mandatory','isoNameEr')
			| !clientValidation('emailId','email_Id','emailIdEr')
			| !clientValidation('cardProgramName','middle_name','cardProgramNameEr')
			| !clientValidation('programManagerName','companyname_not_mandatory','programManagerNameEr')) {
		return false;
	}
	return true;
}

function validateSpecialCharactersSubMerchant() {
	if (!clientValidation('subMerchantCode','merchant_ID','subMerchantCodeEr')
			| !clientValidation('businessName','companyname_not_mandatory','businessNameEr')
			| !clientValidation('programManagerName','companyname_not_mandatory','programManagerNameEr')
			| !clientValidation('isoName','companyname_not_mandatory','isoNameEr')
			| !clientValidation('cardProgramName','middle_name','cardProgramNameEr')
			| !clientValidation('emailId','email_Id','emailIdEr')
			| !clientValidation('city','middle_name','cityEr')) {
		return false;
	}
	return true;
}

function createValidationForBasicInfo(){
	if(!clientValidation('fax','fax','faxEr')
			| !clientValidation('address2','address2','address2Er')){
		return false;
	}
	return true;
}

function validateMerchantcreates(){
	if(!validateReturnURL() |!validateCancelURL()){
		return false;
	}
	
	return true;
}

function validateRoutingNumber() {
	var bankRoutingNumber = getVal('bankRoutingNumber');
	if (!(bankRoutingNumber != 0)) {
		setDiv('bankRoutingNumberEr', webMessages.InvalidBankRoutingNumber);
		loadMsgTitleText();
		return false;
	}
	return true;
}
