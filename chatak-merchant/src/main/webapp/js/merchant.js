String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g, "");
};
var usernameFlag = null;

var mCodeFlag = null;

function cancelCreateMerchant() {
	window.location.href = 'search-sub-merchant';
}
function openCancelConfirmationPopup() {

	if ((isEmpty(get('businessName').value))
			&& (isEmpty(get('businessURL').value.trim()))
			&& (isEmpty(get('appMode').value.trim()))
			&& (isEmpty(get('firstName').value.trim()))
			&& (isEmpty(get('lastName').value.trim()))
			&& (isEmpty(get('phone').value.trim()))
			&& (isEmpty(get('city').value.trim()))
			&& (isEmpty(get('country').value.trim()))
			&& (isEmpty(get('emailId').value.trim()))
			&& (isEmpty(get('address1').value.trim()))
			&& (isEmpty(getVal('pin')))
			&& (isEmpty(get('state').value.trim()))) {
		window.location.href = 'search-sub-merchant';
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
			| !validatePhone()// | !validateFax()
			| !validateAddress1()
			| !validateCity() | !validateEmailId() | !validateState()
			| !validateCountry() | !validatePin() /*|!validateStatus()*/ | !validateAppMode() |!validateURL() |!vlalidateUserName()) {
		return false;
	}else{
		var faxValue = getVal('fax').trim();
		var lookingForValue = getVal('lookingFor').trim();
		var businessTypeValue = getVal('businessType').trim();
		if(faxValue != ""){
			setLable('confirmMfax', getVal('fax').trim());
		}else{
			setLable('confirmMfax', "");
		}if (!isCharacter(lookingForValue)) {
			setError(get('lookingFor'), webMessages.cancontainonlyalphabetsandnumerics);
			loadMsgTitleText();
			return false;
		} else if(lookingForValue != ""){
			setLable('confirmLookingFor', lookingForValue);
		}else{
			setLable('confirmLookingFor', "");
		}if(businessTypeValue != ""){
			setLable('confirmBusinessType', businessTypeValue);
		}else{
			setLable('confirmBusinessType', "");
		}
	}
	setError(get('lookingFor'), " ");
	return flag;
}

function validateCreateMerchantStep1SignUp() {
	var flag = true;
	if (!validateBusinessName() | !validateFirstName() | !validateLastName() | !validateMerchantType()
			| !validatePhone()// | !validateFax()
			| !validateAddress1()
			| !validateCity() | !validateEmailId() | !validateState()
			| !validateCountry() | !validatePin() |!validateStatus() | !validateAppMode() |!validateURL()) {
		return false;
	}else{
		var faxValue = getVal('fax').trim();
		var lookingForValue = getVal('lookingFor').trim();
		var businessTypeValue = getVal('businessType').trim();
		if(faxValue != ""){
			setLable('confirmMfax', getVal('fax').trim());
		}else{
			setLable('confirmMfax', "");
		}if (!isCharacter(lookingForValue)) {
			setError(get('lookingFor'), webMessages.cancontainonlyalphabetsandnumerics);
			loadMsgTitleText();
			return false;
		} else if(lookingForValue != ""){
			setLable('confirmLookingFor', lookingForValue);
		}else{
			setLable('confirmLookingFor', "");
		}if(businessTypeValue != ""){
			setLable('confirmBusinessType', businessTypeValue);
		}else{
			setLable('confirmBusinessType', "");
		}
	}
	setError(get('lookingFor'), "");
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
			|!clientValidation('bankState', 'state','bankStateErrorDiv')
		    |!(zipCodeNotEmpty('bankPin'))
		    |!clientValidation('bankNameOnAccount', 'first_name_SplChar','bankNameOnAccountErrorDiv')){
		flag = false;
		return flag;
	}else{
		setLable('confirmbankAccountName', get('bankAccountName').value.trim());
		setLable('confirmbankRoutingNumber', get('bankRoutingNumber').value.trim());
		setLable('confirmbankAccountNumber', get('bankAccountNumber').value.trim());
		//setLable('confirmbankAccountType', get('bankAccountType').value.trim());
		setTypeValueToConfirmPage();
		setLable('confirmbankAddress1', get('bankAddress1').value.trim());
		setLable('confirmbankAddress2', get('bankAddress2').value.trim());
		setLable('confirmbankCity', get('bankCity').value.trim());
		setLable('confirmbankCountry', get('bankCountry').value.trim());
		setLable('confirmbankState', get('bankState').value.trim());
		setLable('confirmbankPin', get('bankPin').value.trim());
		setLable('confirmbankNameOnAccount', get('bankNameOnAccount').value.trim());
		setLable('confirmCurrency', get('currencyId').value.trim());
		
		return flag;
	}
}

function validateCreateMerchantStep3() {
	var flag = true;
	
	if(!clientValidation('legalName', 'first_name_SplChar','legalNameErrorDiv')
			|!clientValidation('legalTaxId', 'eIN_taxId','legalTaxIdErrorDiv')
			|!clientValidation('legalType', 'state','legalTypeErrorDiv')
			|!clientValidation('legalAnnualCard', 'dollar_amount','legalAnnualCardErrorDiv')
		/*	|!clientValidation('legalFirstName', 'first_name_NotMand','legalFirstNameErrorDiv')
			|!clientValidation('legalLastName', 'first_name_NotMand','legalLastNameErrorDiv')
			|!clientValidation('legalMobilePhone', 'mobile_phone','legalMobilePhoneErrorDiv')*/
			|!clientValidation('legalAddress1', 'bank_address1','legalAddress1ErrorDiv')
			|!clientValidation('legalAddress2', 'bank_address2','legalAddress2ErrorDiv')
			|!clientValidation('legalCity', 'bank_city','legalCityErrorDiv')
			|!clientValidation('legalCountry', 'country','legalCountryErrorDiv')
			|!clientValidation('legalState', 'state','legalStateErrorDiv')
			|!zipCodeNotEmpty('legalPin')){
		flag = false;
		return flag;
	}else{
		setLable('confirmlegalName', get('legalName').value.trim());
		setLable('confirmlegalTaxId', get('legalTaxId').value.trim());
	//	setLable('confirmlegalType', get('legalType').value.trim());
		setValuetoConfirmPage();
		setLable('confirmlegalAnnualCard', get('legalAnnualCard').value.trim());
		setLable('confirmlegalFirstName', get('legalFirstName').value.trim());
		setLable('confirmlegalLastName', get('legalLastName').value.trim());
		setLable('confirmlegalMobilePhone', get('legalMobilePhone').value.trim());
		setLable('confirmlegalAddress1', get('legalAddress1').value.trim());
		setLable('confirmlegalAddress2', get('legalAddress2').value.trim());
		setLable('confirmlegalCity', get('legalCity').value.trim());
		setLable('confirmlegalCountry', get('legalCountry').value.trim());
		setLable('confirmlegalState', get('legalState').value.trim());
		setLable('confirmlegalPin', get('legalPin').value.trim());
		return flag;
	}
}

function setValuetoConfirmPage(){
	var typeValue = document.getElementById("legalType");
	var typeValueText = typeValue.options[typeValue.selectedIndex].text;
	setLable('confirmlegalType', typeValueText);
}

function setTypeValueToConfirmPage(){
//	setLable('confirmbankAccountType', get('bankAccountType').value.trim());
	var accTypeValue = document.getElementById("bankAccountType");
	var accTypeValueText = accTypeValue.options[accTypeValue.selectedIndex].text;
	setLable('confirmbankAccountType', accTypeValueText);
}

function validateLegalEntity(){
	var flag = true;
	if(!clientValidation('legalFirstName', 'first_name_NotMand','legalFirstNameErrorDiv')
			|!clientValidation('legalLastName', 'first_name_NotMand','legalLastNameErrorDiv')
			|!clientValidation('legalMobilePhone', 'mobile_optional','legalMobilePhoneErrorDiv')
			|!clientValidation('legalPassport', 'passport_number','legalPassportErrorDiv')
		//	|!clientValidation('legalCitizen', 'bank_country','legalCitizenErrorDiv')
		//	|!clientValidation('legalHomePhone', 'contact_phone','legalHomePhoneErrorDiv')
			){
		flag = false;
		return flag;
	}else{
		setLable('confirmlegalSSN', get('legalSSN').value.trim());
		setLable('confirmlegalFirstName', get('legalFirstName').value.trim());
		setLable('confirmlegalLastName', get('legalLastName').value.trim());
		setLable('confirmlegalMobilePhone', get('legalMobilePhone').value.trim());
		setLable('confirmlegalDOB', get('legalDOB').value.trim());
		setLable('confirmlegalPassport', get('legalPassport').value.trim());
		setLable('confirmlegalCountryResidence', get('legalCountryResidence').value.trim());
		setLable('confirmlegalCitizen', get('legalCitizen').value.trim());
		setLable('confirmlegalHomePhone', get('legalHomePhone').value.trim());
		return flag;
	}
}

function validateCreateMerchantStep4() {
	var flag = true;
	if (!vlalidateUserName() // | !validatefederalTaxId() //| !validatestateTaxId() | !validatesalesTaxId()
			//| !validateOwnership()
		//	| !validateURL()
			/*| !validateBusinessStartDate() | !validateEstimatedYearlySale()
			| !validateNoOfEmployees() */) {

		return false;
	}
	return flag;
}

function validateCreateMerchantStep1edit() {
	var flag = true;
	if (!validateBusinessName() | !validateFirstName() | !validateLastName()
			| !validatePhone() | !validateAddress1() | !validateEmailId()
			| !validateCity() | !validateState() | !validateCountry()
			| !validatePin() /*| !validateStatus()*/ | !validateAppMode() |!validateURL() |!vlalidateUserNameEdit()) {
		
		return false;
	}else{
		var faxValue = getVal('fax').trim();
		var lookingForValue = getVal('lookingFor').trim();
		var businessTypeValue = getVal('businessType').trim();
		if(faxValue != ""){
		setLable('confirmMfax', faxValue);
		}else{
			setLable('confirmMfax', "");
		}if (!isCharacter(lookingForValue)) {
			setError(get('lookingFor'), webMessages.cancontainonlyalphabetsandnumerics);
			loadMsgTitleText();
			return false;
		} else if(lookingForValue != ""){
			setLable('confirmLookingFor', lookingForValue);
		}else{
			setLable('confirmLookingFor', "");
		}if(businessTypeValue != ""){
			setLable('confirmBusinessType', businessTypeValue);
		}else{
			setLable('confirmBusinessType', "");
		}
	}
	setError(get('lookingFor'), "");
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
			|!clientValidation('bankState', 'state','bankStateErrorDiv')
		    |!zipCodeNotEmpty('bankPin')
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
		setLable('confirmCurrency', get('currencyId').value.trim());
		return flag;
	}
}

function validateCreateMerchantStep3edit() {
	var flag = true;
	
	if(!clientValidation('legalName', 'first_name_SplChar','legalNameErrorDiv')
			|!clientValidation('legalTaxId', 'eIN_taxId','legalTaxIdErrorDiv')
			|!clientValidation('legalType', 'state','legalTypeErrorDiv')
			|!clientValidation('legalAnnualCard', 'dollar_amount','legalAnnualCardErrorDiv')
			/*|!clientValidation('legalFirstName', 'first_name_NotMand','legalFirstNameErrorDiv')
			|!clientValidation('legalLastName', 'first_name_NotMand','legalLastNameErrorDiv')
			|!clientValidation('legalMobilePhone', 'mobile_phone','legalMobilePhoneErrorDiv')*/
			|!clientValidation('legalAddress1', 'bank_address1','legalAddress1ErrorDiv')
			|!clientValidation('legalAddress2', 'bank_address2','legalAddress2ErrorDiv')
			|!clientValidation('legalCity', 'bank_city','legalCityErrorDiv')
			|!clientValidation('legalCountry', 'country','legalCountryErrorDiv')
			|!clientValidation('legalState', 'state','legalStateErrorDiv')
			|!zipCodeNotEmpty('legalPin')){
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
	if (!vlalidateUserNameEdit() // | !validatestateTaxId() | !validatesalesTaxId()
			//!validateOwnership() | 
			//!validateURL()
			) {
		return false;
	}
	setLable('confirmMemailId', get('emailId').value.trim());
//	setLable('confirmMmerchantCode', get('merchantCode').value.trim());
	setLable('confirmMuserName', get('userName').value.trim());

	return flag;
}

function validateCreateMerchantStep5() {
	var flag = true;
	if (!validateProcessor() | !validateVirtualTerminal()
			| !validateOnlineOptions() 
		    | !validateCallbackURL() | !validateCategory()
			| !validateAutoPaymentMethod() | !validateAutoTransferLimit()
			| !continueBtnValidateForOnline()
			| !continueBtnValidateVirtualTerminal())
			 {
		if($('#autoTransferDay').prev().find('.required-field').is(':visible')) {
			validateAutoTransferDayFields();
		}
		return false;
	}
	if(get('processor')) {
		var processor = get('processor').value.trim();
		if(processor == "LITLE"){
			return validatelitleMID();
		} else {
			setLable('confirmLitleMID', '');
		}
	}
	var fieldsValue = get('autoTransferDay').value;
	
	if(fieldsValue == ""){
		setLable('confirmMautoTransferDay', "");
	}
	
	var currencyId = get('currencyId').value.trim();
	if (currencyId != "") {
		setLable('confirmCurrency', currencyId);
	}else {
		setLable('confirmCurrency', "");
	}
	
	if(fieldsValue == "D"){
		setLable('confirmMautoTransferDay', "Daily");
		$('#hideDayTable').hide();
		$('#hideWeekyTable').hide();
		setLable('confirmAutoTransferWeeklyDay',"");
		setLable('confirmAutoTransferMonthlyDay',"");
	}
	if(fieldsValue == "W"){
		if(!clientValidation('autoTransferWeeklyDay', 'state','autoTransferWeeklyDayEr')){
			return false;
		}else{
			setLable('confirmMautoTransferDay', "Weekly");
			$('#hideDayTable').show();
			$('#hideWeekyTable').hide();
			setDiv('autoTransferWeeklyDayEr',"");
			
			var weekValue = document.getElementById("autoTransferWeeklyDay");
			var weekDay = weekValue.options[weekValue.selectedIndex].text;
			setLable('confirmAutoTransferWeeklyDay', weekDay);
			
		}
	}
	if(fieldsValue == "M"){
		if(!clientValidation('autoTransferMonthlyDay', 'state','autoTransferMonthlyDayEr')){
			return false;
		}else{
			setLable('confirmMautoTransferDay', "Monthly");
			$('#hideDayTable').hide();
			$('#hideWeekyTable').show();
			setDiv('autoTransferMonthlyDayEr',"");
			setLable('confirmAutoTransferMonthlyDay', get('autoTransferMonthlyDay').value.trim());
		}
	}
	
	/*var dayValue = get('autoTransferDay').value.trim();
	if(dayValue == "D"){
		setLable('confirmMautoTransferDay', "Daily");
	}
	if(dayValue == "M"){
		setLable('confirmMautoTransferDay', "Monthly");
	}
	if(dayValue == "W"){
		setLable('confirmMautoTransferDay', "Weekly");
	}*/
	
	//setLable('confirmMautoTransferDay', get('autoTransferDay').value.trim());
	return flag;
}

function validateAddress1() {
	var address1 = get('address1').value.trim();
	 var addRegx = /^[A-Za-z0-9,-._\/\s#]{1,60}$/; 
	if (isEmpty(address1)) {
		setError(get('address1'), webMessages.pleasenteraddress1);
		loadMsgTitleText();
		return false;
	}  else if (address1.length < 5) {
		setError(get('address1'), webMessages.invalidaddress1length);
	}else if (!addRegx.test(address1)) {
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
		setLable('confirmMcity', city);
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
		setLable('confirmMstate', state);
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
		setLable('confirmMcountry', country);
		return true;
	}
}

function validatePin() {
	var pin = getVal('pin');
	if (isEmpty(pin)) {
		setError(get('pin'), webMessages.pleasenterZipcode);
		loadMsgTitleText();
		return false;
	} else if ((pin.length < 3) || (pin.length > 7)) {
		setError(get('pin'), webMessages.invalidZipcode );
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
	/*get('status').value = "";*/
	get('city').value = "";
	get('country').value = "";
	get('emailId').value = "";
}

function validateMerchantCode() {
	var val = /[0-9]{6,16}$/;
	var merchantCode = get('merchantCode').value.trim();
	if (isEmpty(merchantCode)) {
		setError(get('merchantCode'),webMessages.pleasentermerchantcode);
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
	var spaceRegx = /^[A-Za-z0-9][A-Za-z0-9. ]*$/;
	if (isEmpty(businessName)) {
		setError(get('businessName'), webMessages.pleasentercompanyname);
		loadMsgTitleText();
		return false;
	} else if (!spaceRegx.test(businessName)) {
		setError(get('businessName'), webMessages.invalidcompanyname);
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
	var spaceRegx = /^[A-Za-z0-9@][A-Za-z0-9. ]*$/;
	if (isEmpty(firstName)) {
		setError(get('firstName'), webMessages.pleasenterfirstname);
		loadMsgTitleText();
		return false;
	} else if (!spaceRegx.test(firstName)) {
		setError(get('firstName'), webMessages.invalidfirstname);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('firstName'), '');
		setLable('confirmMfirstName', firstName);
		return true;
	}
}

function validateLastName() {
	var lastName = get('lastName').value.trim();
	var spaceRegx = /^[A-Za-z0-9@][A-Za-z0-9. ]*$/;
	if (isEmpty(lastName)) {
		setError(get('lastName'), webMessages.pleasenterlastname);
		loadMsgTitleText();
		return false;
	} else if (!spaceRegx.test(lastName)) {
		setError(get('lastName'), webMessages.invalidlastname);
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
		setError(get('phone'),webMessages.pleasenterphonenumber );
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
		setLable('confirmMphone', phone);
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
		setError(get('fax'), webMessages.invalidfax);
		loadMsgTitleText();
		return false;
	} else if (fax.charAt(parseInt("0")) == "0") {
		setError(get('fax'), webMessages.faxcannotstartwithzero);
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
			setError(get('emailId'), '');
			doAjaxFetchMailIdAvailable(isMailUnique);
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
		setError(get('timeZone'), 'Please enter Time Zone ');
		return false;
	} else {
		setError(get('timeZone'), '');
		setLable('confirmMtimeZone', timeZone);
		return true;
	}
}

function validateAppMode() {
	var appMode = get('appMode').value.trim();
	if (isEmpty(appMode)) {
		setError(get('appMode'),webMessages.pleaseselectappmode );
		loadMsgTitleText();
		return false;
	} else if (!isChar(appMode)) {
		setError(get('appMode'),webMessages.invalidappmode );
		loadMsgTitleText();
		return false;
	} else {
		setError(get('appMode'), '');
		setLable('confirmMappMode', appMode);
		return true;
	}
}

function validateStatus() {
	var status = get('status').value.trim();
	var statusValue = document.getElementById("status");
	var x = statusValue.options[statusValue.selectedIndex].text;
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
		setLable('confirmMstatus', x);
		return true;
	}
}

function validatefederalTaxId() {
	var federalTaxId = get('federalTaxId').value.trim();
	if (isEmpty(federalTaxId)) {
		setError(get('federalTaxId'),webMessages.pleaseEnterFederalTaxId);
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
			setError(get('businessStartDate'),webMessages.pleaseEntertheValidBusinessStartDate);
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
		setError(get('businessStartDate'),webMessages.pleaseEntertheValidBusinessStartDate);
		loadMsgTitleText();
		flag = false;
	} else if (givdate < currentDate) {
		setError(get('businessStartDate'),webMessages.businessStartDateShouldnotbepastDate);
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
		setError(get('estimatedYearlySale'),webMessages.pleaseEnterEstimatedYearlySale);
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
		setError(get('noOfEmployee'), 'Please enter No. of Employee ');
		return false;
	} else if (!isDigit(noOfEmployee)) {
		setError(get('noOfEmployee'), 'Invalid No. of Employee');
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
		setError(get('role'), 'Please enter Role');
		return false;
	} else {
		setError(get('role'), '');
		setLable('confirmMrole', role);
		return true;
	}
}

function validatefeeProgram() {
	if(get('feeProgram')) {
		var feeProgram = get('feeProgram').value.trim();
		if (isEmpty(feeProgram)) {
			setError(get('feeProgram'), webMessages.thisfieldismandatory);
			loadMsgTitleText();
			return false;
		} else {
			setError(get('feeProgram'), '');
			setLable('confirmMfeeProgram', feeProgram);
			return true;
		}
	}
	return true;
}

function validateProcessor() {
	if(get('processor')) {
		var processor = get('processor').value.trim();
		var processorValue = document.getElementById("processor");
		var x = processorValue.options[processorValue.selectedIndex].text;
		if (isEmpty(processor)) {
			setError(get('processor'), webMessages.thisfieldismandatory);
			loadMsgTitleText();
			return false;
		} else {
			setError(get('processor'), '');
			setLable('confirmMprocessor', x);
			if(processor == "LITLE"){
				$('#vantivMerchantId').show();
				setError(get('litleMID'), '');
			} else {
				setValue('litleMID', '');
				setLable('confirmLitleMID', '');
				$('#vantivMerchantId').hide();
			}
		}
	}
	return true;
}

function validateURL() {
	//var reg = /[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&\/=]*)/;
	if(get('businessURL')) {
		var reg = /(http|https:\/\/[^\s\.]+\.[^\s]{2,}|www\.[^\s]+\.[^\s]{2,})/;
		var businessURL = get('businessURL').value.trim();

		if (isEmpty(businessURL)) {
			setError(get('businessURL'), webMessages.pleasenterbusinessURL);
			loadMsgTitleText();
			return false;
		} else if (reg.test(businessURL) == false) {
			setError(get('businessURL'), webMessages.invalidbusinessURL);
			loadMsgTitleText();
			return false;
		} else {
			setError(get('businessURL'), '');
			setLable('confirmMbusinessURL', businessURL);
		}
	}
	return true;
}

function validateCallbackURL() {
//	var reg = /[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&\/=]*)/;
	if(get('merchantCallBackURL')) {
		var reg = /(http|https:\/\/[^\s\.]+\.[^\s]{2,}|www\.[^\s]+\.[^\s]{2,})/;
		var merchantCallBackURL = get('merchantCallBackURL').value.trim();
		if (isEmpty(merchantCallBackURL)) {
			setError(get('merchantCallBackURL'), '');
			setLable('confirmMmerchantCallBackURL', merchantCallBackURL);
			return true;
		} else if (reg.test(merchantCallBackURL) == false) {
			setError(get('merchantCallBackURL'), webMessages.invalidmerchantcallbackURL);
			return false;
		} else {
			setError(get('merchantCallBackURL'), '');
			setLable('confirmMmerchantCallBackURL',webMessages.merchantCallBackURL);
			loadMsgTitleText();
		}
	}
	return true;
}

function validateCategory(){
	if(get('category')) {
		var category = get('category').value.trim();
		if (isEmpty(category)) {
			setError(get('category'), webMessages.thisfieldismandatory);
			loadMsgTitleText();
			return false;
		}  else {
			setError(get('category'), '');
			setLable('confirmMcategory', category);
		}
	}
	return true;
}

function validateAutoPaymentMethod() {
	var autoPaymentMethod = get('autoPaymentMethod').value.trim();
//	var autoPaymentMethodConfirm = get('autoPaymentMethod').value.trim();
	if(isEmpty(autoPaymentMethod)) {
		setError(get('autoPaymentMethod'), webMessages.thisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else if (isEmpty(autoPaymentMethod) && $('#allowAutoSettlement').is(':checked')) {
		setError(get('autoPaymentMethod'), webMessages.pleaseselectautopaymentmethod);
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
		if($('#autoTransferLimit').prev().find('.required-field').is(':visible')) {
			setError(get('autoTransferLimit'), webMessages.pleasenterautotransferlimit);
			loadMsgTitleText();
		} else {
			setError(get('autoTransferLimit'), '');
		}
		return true;
	} else if (reg.test(autoTransferLimit) == false) {
		setError(get('autoTransferLimit'), webMessages.invalidautotransferlimit);
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
		setError(get('litleMID'), webMessages.invalidvantivmerchantid);
		loadMsgTitleText();
		return false;
	} else if (!isDigit(litleMID)) {
		setError(get('litleMID'), webMessages.invalidvantivmerchantid);
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
		setError(get('noAutoSettlement'), webMessages.pleaseselectone);
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
			list = list + document.getElementById("allowRepeatSale").name + '\n';
		else
			list = list + document.getElementById("allowRepeatSale").name + ",";
	}
	if (get('showRecurringBilling').checked)
		list = list + document.getElementById("showRecurringBilling").name
				+ " ";*/

	if (list != "") {
		list = list.substring(0, list.length - 1);
		setError(get('refunds'), '');
		setLable('confirmMvirtualTerminalList', list);
		return true;
	} else {
		setError(get('refunds'), webMessages.pleaseselectatleastonevirtualterminal);
		loadMsgTitleText();
		setLable('confirmMvirtualTerminalList', list);
		return false;
	}
}

function vlalidateUserName() {
	var userName = get('userName').value.trim();
	if (isEmpty(userName)) {
		setError(get('userName'), webMessages.pleasenterusername);
		loadMsgTitleText();
		document.getElementById('userNamegreenEr').innerHTML = "";
		return false;
	} else if (userNameRegex.test(userName) == false) {
		setError(get('userName'), webMessages.cancontainalphanumerics);
		loadMsgTitleText();
		document.getElementById('userNamegreenEr').innerHTML = "";
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
						document.getElementById('userNamegreenEr').innerHTML = webMessages.userNameavailable;
						loadMsgTitleText();
						setLable('confirmMuserName', get('userName').value
								.trim());
						usernameFlag = true;

					} else {
						setError(get('userName'), webMessages.userNameNotAvailable);
						document.getElementById('userNamegreenEr').innerHTML = "";
						usernameFlag = false;
						;
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
				$(("#"+elementId)).append(selectOption);

				if (obj.errorMessage == "SUCCESS") {
					var data = obj.responseList;

					for (var i = 0; i < data.length; i++) {
						var state = data[i].label;

						var newOption = document.createElement("option");
						newOption.value = data[i].value;
						newOption.innerHTML = state;

						$(("#"+elementId)).append(newOption);
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

	$("#"+elementId).append(selectOption);
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

						$("#"+elementId).append(newOption);
					}
				}
			}
		},
		error : function(e) {
		}
	});

}

var merchantsID;
function confirmDeleteMerchant(merchantId) {
	$('#deletePopup').popup("show");
	merchantsID = merchantId;
	loadMsgTitleText();
}

function deleteData() {
	get('getMerchantsId').value = merchantsID;
	document.forms["deleteMercahntForm"].submit();
}

function doAjaxFetchMailIdAvailable(isMailUnique) {
	var mailId = get('emailId').value.trim();
	if (isMailUnique == "true") { // If mail is need to unique this if() code will Execute.
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
					setError(get('emailId'), webMessages.emailidalreadyinuse);
					loadMsgTitleText();
					usernameFlag = true;
				}
			},
			error : function(e) {
			}
		});
	} else {
		setLable('confirmMemailId', mailId);
		usernameFlag = false;
	}
}

function resetBasicInfo() {
	window.location.href = 'sub-merchant-create';
}

function resetBasicInfoSignUp() {
	
	get('parentMerchantCode').value = "";
	setError(get('parentMerchantCode'), '');
	get('businessName').value = "";
	setError(get('businessName'), '');
	get('firstName').value = "";
	setError(get('firstName'), '');
	get('lastName').value = "";
	setError(get('lastName'), '');
	get('phone').value = "";
	setError(get('phone'), '');
	get('fax').value = "";
	setError(get('fax'), '');
	get('emailId').value = "";
	setError(get('emailId'), '');
	get('address1').value = "";
	setError(get('address1'), '');
	get('country').value = "";
	setError(get('country'), '');
	get('address2').value = "";
	setError(get('address2'), '');
	get('state').value = "";
	setError(get('state'), '');
	get('pin').value = "";
	setError(get('pin'), '');
	get('appMode').value = "";
	setError(get('appMode'), '');
	get('city').value = "";
	setError(get('city'), '');
	get('businessURL').value = "";
	setError(get('businessURL'), '');
	get('businessType').value = "";
	setError(get('businessType'), '');
	get('lookingFor').value = "";
	setError(get('lookingFor'), '');
}

function resetBankInfo() {
	get('bankAccountName').value = "";
	setDiv('bankAccountNameErrorDiv', '');
	get('bankRoutingNumber').value = "";
	setDiv('bankRoutingNumberEr', '');
	get('bankAccountNumber').value = "";
	setDiv('bankAccountNumberErrorDiv', '');
	get('bankRoutingNumber').value = "";
	setDiv('bankRoutingNumberEr', '');
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
	setDiv('bankPinErrorDiv', '');
	get('bankNameOnAccount').value = "";
	setDiv('bankNameOnAccountErrorDiv', '');
	get('bankPin').value = "";
	setError(get('bankPin'),'');
	
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
	setDiv('bankStateErrorDiv', '');
	setDiv('bankPinErrorDiv', '');
	setDiv('bankNameOnAccountErrorDiv', '');
	setError(get('bankPin'),'');
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
	setDiv('legalPinErrorDiv', '');
}

function resetLegalEntityInfo(){
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
	setDiv('legalPinErrorDiv', '');
}

function resetLegalEntityErrorMsg(){
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
	/*get('businessURL').value = "";
	setError(get('businessURL'), '');
	get('federalTaxId').value = "";
	setError(get('federalTaxId'), '');
	get('stateTaxId').value = "";
	setError(get('stateTaxId'), '');
	get('salesTaxId').value = "";
	setError(get('salesTaxId'), '');
	get('ownership').value = "";
	setError(get('ownership'), '');
	get('businessStartDate').value = "";
	setError(get('businessStartDate'), '');
	get('estimatedYearlySale').value = "";
	setError(get('estimatedYearlySale'), '');
	get('noOfEmployee').value = "";
	setError(get('noOfEmployee'), '');*/
}

function resetAdditionalInfoErrorMsg() {
	setError(get('userName'), '');
	//setError(get('businessURL'), '');
	//setError(get('federalTaxId'), '');
	/*setError(get('stateTaxId'), '');
	setError(get('salesTaxId'), '');*/
	//setError(get('ownership'), '');
	//setError(get('businessStartDate'), '');
	//setError(get('estimatedYearlySale'), '');
	//setError(get('noOfEmployee'), '');
}

function resetConfigurationsInfo() {
	/*get('allowAutoSettlement').checked = false;
	get('noAutoSettlement').checked = false;
	setError(get('noAutoSettlement'), '');
	get('feeProgram').value = "";
	setError(get('feeProgram'), '');
	*/
	get('processor').value = "";
	setError(get('processor'), '');
	get('litleMID').value = "";
	setError(get('litleMID'), '');
	setDiv('agentErrorId','');
	setDiv('agentIdErr', '');
	get('agentId').value = "";

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
		get('agentAccountNumber').value = "";
		setError(get('agentAccountNumber'), '');
		get('agentClientId').value = "";
		setError(get('agentClientId'), '');
		get('agentANI').value = "";
		setError(get('agentANI'), '');
		//get('posTerminal').checked = false;
		
		if(get('online').checked) {
			get('webSiteAddress').value = "";
		//	setError(get('webSiteAddress'), '');
			get('returnUrl').value = "";
		//	setError(get('returnUrl'), '');
			get('cancelUrl').value = "";
		//	setError(get('cancelUrl'), '');
			$('#onlineOptions').hide();
			get('online').checked = false;
		}
	
	get('merchantCallBackURL').value = "";
	setError(get('merchantCallBackURL'), '');
	get('autoPaymentMethod').value = "";
	setError(get('autoPaymentMethod'), '');
	get('autoTransferDay').value = "";
	get('autoTransferMonthlyDay').value = "";
	setError(get('autoTransferMonthlyDay'), '');
	get('autoTransferWeeklyDay').value = "";
	setError(get('autoTransferWeeklyDay'), '');
	setLable('autoPaymentMethodEr','');
	setLable('autoTransferDayEr','');
	$('.auto_settlement_opts').hide();
	get('autoTransferLimit').value = "";
	setLable('autoTransferLimitEr','');
	if($('#vantivMerchantId').is(':visible')) {
		$('#vantivMerchantId').val('').hide();
		setLable('litleMIDEr','');
	}
	get('feeProgram').value = "";
	setError(get('autoTransferWeeklyDay'), '');
	setLable('feeProgramEr','');
	get('autoTransferMonthlyDay').value = "";
	$('#monthlySettlement').hide();
	setError(get('autoTransferMonthlyDay'), '');
	get('autoTransferWeeklyDay').value = "";
	$('#weeklySettlement').hide();
	setError(get('autoTransferWeeklyDay'), '');
}

function resetConfigurationsInfoErrorMsg() {
	setError(get('noAutoSettlement'), '');
	setError(get('feeProgram'), '');
	if(get('refunds') && get('processor')) {
		setError(get('refunds'), '');
		setError(get('processor'), '');
		setError(get('category'), '');
		setError(get('litleMID'), '');
	}
	setError(get('autoPaymentMethod'), '');
	setError(get('autoTransferMonthlyDay'), '');
	setError(get('autoTransferWeeklyDay'), '');
	setError(get('autoTransferLimit'), '');
	setError(get('autoTransferDay'), '');
	setError(get('agentAccountNumber'), '');
	setError(get('agentClientId'), '');
	setError(get('agentANI'), '');
}

function checkStatusAndMerchantType() {
	var status = get('status').value.trim();
	var type = get('merchantType').value.trim();
	if ("0" == status && type == "Merchant") {
		return true;
	} else {
		return false;
	}
}
function editSubMerchant(merchantId) {
	get('getSubMerchantId').value = merchantId;
	document.forms["editSubMercahntForm"].submit();
}
function getSubMerchantList(merchantId) {
	get('getSubMerchantIdForView').value = merchantId;
	document.forms["getSubMerchantListForm"].submit();
}

function showAutoTransferDayFields(){
	var fieldsValue = get('autoTransferDay').value;
	var flag = false;
	if(fieldsValue == "W"){
		$('#weeklySettlement').show();
		$('#monthlySettlement').hide();
		get('autoTransferMonthlyDay').value = "";
		}
	if(fieldsValue == "M"){
		$('#monthlySettlement').show();
		$('#weeklySettlement').hide();
		get('autoTransferWeeklyDay').value = "";
	}
	if(fieldsValue == "D" || fieldsValue == ""){
		$('#monthlySettlement').hide();
		$('#weeklySettlement').hide();
		get('autoTransferMonthlyDay').value = "";
		get('autoTransferWeeklyDay').value = "";
	}
}

function validateEmailIdEdit(){
	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var emailAddress = get('emailId').value.trim();

	if (isEmpty(emailAddress)) {
		setError(get('emailId'), webMessages.pleasenteremailid );
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
		url : "uniqueEmailIdEdit?emailId=" + mailId+ "&merchantCode=" + merchantCode,
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
				setError(get('emailId'), webMessages.emailidalreadyinuse);
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
		setError(get('userName'),webMessages.pleasenterusername);
		loadMsgTitleText();
		document.getElementById('userNamegreenEr').innerHTML = "";
		return false;
	} else if (reg.test(userName) == false) {
		setError(get('userName'), webMessages.cancontainalphanumerics);
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
				url : "uniqueUserEdit?userName=" + userName+ "&merchantCode=" + merchantCode,
				async : false,
				success : function(response) {
					var obj = JSON.parse(response);
					if (obj.errorCode === '00') {
						setError(get('userName'), '');
						document.getElementById('userNamegreenEr').innerHTML = webMessages.userNameavailable;
						loadMsgTitleText();
						setLable('confirmMuserName', get('userName').value
								.trim());
						usernameFlag = true;

					} else {
						setError(get('userName'), webMessages.userNameNotAvailable);
						document.getElementById('userNamegreenEr').innerHTML = "";
						usernameFlag = false;
						;
					}
				},
				error : function(e) {
				}
			});
}
function cancelRegisterMerchant() {
	window.location.href = 'login';
}

function validateAutoTransferDayFields() {
	if($('#autoTransferDay').val() == "") {
		setError(get('autoTransferDay'), 'Please Select Auto Transfer');
	} else {
		setError(get('autoTransferDay'), '');
	}
}

function doAjaxFetchParentMerchantId() {
	var parentMerchantCode = get('parentMerchantCode').value.trim();

	$
			.ajax({
				type : "GET",
				url : "validateMerchantCode?parentMerchantCode=" + parentMerchantCode,
				async : false,
				success : function(response) {
					var obj = JSON.parse(response);
					if (obj.errorCode === '00') {
						setError(get('parentMerchantCode'), '');
						setLable('confirmParentMerchantCode', get('parentMerchantCode').value
								.trim());
						get('parentMerchantId').value = obj.parentMerchantId;
						get('merchantTypeId').value = "SubMerchant";
						mCodeFlag = true;

					} else {
						get('parentMerchantId').value = null;
						setError(get('parentMerchantCode'), "Merchant Not Available or Is Not Active");
						document.getElementById('parentMerchantCodeEr').innerHTML = "Merchant Not Available or Is Not Active";
						mCodeFlag =  false;
					}
				},
				error : function(e) {
					setError(get('parentMerchantCode'), "Merchant Not Available or Is Not Active");
					mCodeFlag = false;
				}
			});
}

function validateRadioMerchantButton(){
	var flag=false;
	if(document.getElementById("merchant").checked){
		setLable('parentMerchantId', null);
		//setLable('parentMerchantCode', "qwe");
		document.getElementById('parentMerchantCode').value = "";
		setError(get('parentMerchantCode'), '');
		$('#parentMerchantCodeField').hide();
		
		flag=true;
	}else{
		$('#parentMerchantCodeField').show();
		/*if (!validParentMerchantCode('parentMerchantCode','parentMerchantCodeEr')){
			flag=false;
		}else{
			flag=true;
		}*/
		flag=true;
	}
	return flag;
}
	

function validParentMerchantCode(id,divId) {
	var val =getVal(id);
	var len = val.length;
	if (isEmpty(val)) {
		setDiv(divId, "Should not be empty");
		return false;
	} else if (!isDigit(val) || len < 4 || len > 15) {
		setDiv(divId, "Should be numeric and 4-15 digit");
		return false;
	}
	else {
		doAjaxFetchParentMerchantId();
		if (mCodeFlag == true) {
			setDiv(divId, "");
			return true;
		} else {
			return false;
		}
	}
}

function validateMerchantType(){
	var flag = false; 
	if(document.getElementById("merchant").checked){
		flag = true;
	}else{
		var merchantType = document.getElementById('parentMerchantCode').value;
		if("" == merchantType){
			setDiv('parentMerchantCodeEr', "Should not be empty");
			flag = false;
		}else{
			flag = true;
		}
	}
	return flag;
}


/////////////////////

/*function fetchAgentOnAppMode(applicationMode, agentId) {
	var flag = false;
	var appMode = get('appMode').value.trim();
	if (isEmpty(appMode)) {
		setError(get('appMode'), 'Please select App Mode ');
		flag = false;
		return flag;
	} else if (!isChar(appMode)) {
		setError(get('appMode'), 'Invalid App Mode');
		flag = false;
		return flag;
	} else {
		setError(get('appMode'), '');
		setLable('confirmMappMode', appMode);
		
		fetchAgentsList(applicationMode, agentId);
		
		flag = true;
		return flag;
		
		
	}
}*/




/*function fetchAgentsList(applicationMode, agentId) {

	if (applicationMode == '') {
		clearAgentsList(agentId);
		return;
	}

	getAgentsList(applicationMode, agentId);
}*/

/*function getAgentsList(applicationMode, agentId) {
	$.ajax({
		type : "GET",
		url : "getAgentListByApplnMode?applicationMode=" + applicationMode,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
		//	if (obj.errorCode === '00') {
				// remove the previous option from element
				document.getElementById(agentId).options.length = 0;

				// create select option
				var selectOption = document.createElement("option");
				selectOption.innerHTML = "..:Select:..";
				selectOption.value = "";
				$(("#"+agentId)).append(selectOption);

			//	if (obj.errorMessage == "SUCCESS") {
					//var data = obj.responseList;

					for (var i = 0; i < obj.length; i++) {
						var state = obj[i].agentName;

						var newOption = document.createElement("option");
						newOption.value = obj[i].agentId;
						newOption.innerHTML = state;

						$(("#"+agentId)).append(newOption);
					}
			//	}
		//	}
		},
		error : function(e) {
		}
	});

}*/

/*function clearAgentsList(agentId) {

	document.getElementById(agentId).options.length = 0;

	var selectOption = document.createElement("option");
	selectOption.innerHTML = "..:Select:..";
	selectOption.value = "";

	$("#"+agentId).append(selectOption);
}*/

var isParentAndAgentDetailsAvailable = false;
function populatePartnerAndAgentDetails(appMode,merchantType,operationType,isAppModeChanged) {
	
	if(!isParentAndAgentDetailsAvailable) {
		$.ajax({
			type : "POST",
			url : "get-partner-agent-details",
			data: { mode: appMode , merchantType: merchantType ,isAppModeChanged: isAppModeChanged},
			async : false,
			success : function(response) {
					isParentAndAgentDetailsAvailable = true;
					
					var jsonObj =  JSON.parse(response);
					var ciResponse = jsonObj.ciResponse;
					if(ciResponse != undefined) {
						var pgmManagerDetails = ciResponse.ciProgramManagerDetails[0];
						var partnersList = pgmManagerDetails.partnerDetailList;
						var pgmManagerId = pgmManagerDetails.programManagerId;
						
						var selectObj;
						
						if(merchantType == 'merchant') {
							
							var linkedPartnersList;
							function initPartners() {
								linkedPartnersList = jsonObj.linkedPartnersList;
						    }
						    initPartners();
							
							selectObj = $('#partnerId');
							var linkedPartnerId = "";
							var linkedPartnerName = "";
							$(partnersList).each(function(item){
								if($.inArray(this.partnerId.toString(),linkedPartnersList) == -1) {
									selectObj.append($("<option />").val(this.partnerId).text(this.partnerName));
								} else {
									if(this.partnerId == $('#linkedPartnerId').val()) {
										linkedPartnerId = this.partnerId;
										linkedPartnerName = this.partnerName;
									}
								}
							});
							
							if(linkedPartnerId.length > 0 && linkedPartnerName.length > 0) {
								selectObj.append($("<option />").val(linkedPartnerId).text(linkedPartnerName));
							}
							
							if($('#partnerId option').length == 1) {
								selectObj.addClass('NPA');
							}
							
							if(operationType == 'update' || operationType == 'view') {
								
								selectObj.val($('#linkedPartnerId').val());
								
								if(selectObj.val() == null) selectObj.val('');
							}
							
							$('#programManagerId').val(pgmManagerId);
							
							selectObj.on('change',function(){
								if(selectObj.hasClass('NPA') && selectObj.val() == '') {
									$('#partnerIdErrorDiv').text('All Partners Are Linked.');
								}
							});
						} else if(merchantType == 'sub-merchant') {
							selectObj = $('#agentId');
							var linkedPartnerId = $('#linkedPartnerId').val();

							if(linkedPartnerId != '') {
								var linkedAgentsList = "";
								var merchantLinkedPartnerId = "";
								function initAgents() {
									linkedAgentsList = jsonObj.linkedAgentsList;
									merchantLinkedPartnerId = linkedPartnerId;
							    }
							    initAgents();
								
							    var linkedAgentId = "";
								var linkedAgentName = "";
							    $(partnersList).each(function(partner) {
									if(this.partnerId == merchantLinkedPartnerId) {
										var ciAgentDetails = this.ciAgentDetails;
										$(ciAgentDetails).each(function(agent) {
											if($.inArray(this.agentId,linkedAgentsList) == -1) {
												selectObj.append($("<option />").val(this.agentId).text(this.agentName));
											} else {
												if(this.agentId == $('#linkedAgentId').val()) {
													linkedAgentId = this.agentId;
													linkedAgentName = this.agentName;
												}
											}
										});
										return false;
									}
								});
							   
							    if((merchantLinkedPartnerId.length > 0 && linkedAgentId.length > 0 && linkedAgentName.length > 0) || operationType == 'view') {
							    	selectObj.append($("<option />").val(linkedAgentId).text(linkedAgentName));
							    }
							    
								$('#agentDetails').show();
								if($('#agentId option').length == 1) {
									selectObj.addClass('NPA');
								}
								
								if(operationType == 'update' || operationType == 'view') {
									
									if(operationType == 'view') {
										
									}
									
									selectObj.val($('#linkedAgentId').val());
									
									if(selectObj.val() == null) selectObj.val('');
								}
								
								$('#programManagerId').val(pgmManagerId);
								
								selectObj.on('click',function(){
									if(selectObj.hasClass('NPA') && selectObj.val() == '') {
										$('#agentIdErrorDiv').text('No Agents available or all Agents are already linked.');
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

//NEWLY ADDED START FROM HERE

//VIRTUAL TERMINAL
function validateVirtualTerminal() {
	$("#virtualTerminalOptions").hide();
	 if($('#virtualTerminal').is(":checked")) { 
	        $("#virtualTerminalOptions").show();
	        setError(get('refunds'), '');
	 } else {
		 	$("#refunds, #tipAmount, #taxAmount, #shippingAmount").prop("checked", false);
		 	validateCheckBox();
	        $("#virtualTerminalOptions").hide();
	    }
	 	return true;
	}

//POS TERMINAL
function validatePos() {
	var list1 = "";
		list1 = list1 + document.getElementById('posTerminal').name;
		return true;
}

//ONLINE TERMINAL
function validateOnlineOptions() {
	$("#onlineOptions").hide();
	if($('#online').is(":checked")) { 
      $("#onlineOptions").show();
      setDiv("webSiteAddrErr", "");
      setDiv("returnURLErr", "");
      setDiv("cancelURLErr", "");
      return true;
} else {
	 	$("#webSiteAddress, #returnUrl, #cancelUrl").val('');
	 	validateOnlineOptionsList();
	 	$("#webSiteAddressErrorDiv, #returnURLErrorDiv, #cancelURLErrorDiv").hide();
	 	$("#onlineOptions").hide();
      return true;	
  }
}

function validateOnlineOptionsList () {
	validateWebSiteAddressURL();
	validateReturnURL();
	validateCancelURL();
}

function continueBtnValidateForOnline(){
	if(document.getElementById('online').checked){
		if(validateWebSiteAddressURL() && validateReturnURL() && validateCancelURL()){
			return true;
		}
	}else{
		return true;
	}
}

function validateWebSiteAddressURL() {
	var reg = /(http|https:\/\/[^\s\.]+\.[^\s]{2,}|www\.[^\s]+\.[^\s]{2,})/;
	var webSiteAddress = get('webSiteAddress').value.trim();
	if (isEmpty(webSiteAddress)) {
		setDiv("webSiteAddrErr", webMessages.pleasenterwebsiteaddress);
		loadMsgTitleText();
		setLable('confirmMwebSiteAddress', webSiteAddress);
		return false;
	} else if (reg.test(webSiteAddress) == false) {
		setDiv("webSiteAddrErr", webMessages.invalidwebsiteaddress);
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
		setDiv("returnURLErr", webMessages.pleasenterreturnURL);
		loadMsgTitleText();
		setLable('confirmMreturnURL', returnUrl);
		return false;
	} else if (reg.test(returnUrl) == false) {
		setDiv("returnURLErr", webMessages.invalidreturnURL);
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
		setDiv("cancelURLErr",webMessages.pleaseentercancelurl);
		setLable('confirmMcancelURL', cancelUrl);
		return false;
	} else if (reg.test(cancelUrl) == false) {
		setDiv("cancelURLErr", webMessages.invalidCancelURL);
		setLable('confirmMcancelURL', "");
		return false;
	} else {
		setDiv("cancelURLErr", "");
		setLable('confirmMcancelURL', cancelUrl);
		return true;
	}
}

function continueBtnValidateVirtualTerminal(){
	if(document.getElementById('virtualTerminal').checked){
		if(validateCheckBox()){
			return true;
		}
	}else{
		return true;
	}
}

function validateParentMerchantId() {
	var merchantCode = $('#parentMerchantId').val().trim();
	if ($('#parentMerchantId').is(':visible') && merchantCode.length <= 0) {
		setError(get('parentMerchantId'), webMessages.pleaseSelectMerchantCode);
		loadMsgTitleText();
		return false;
	} else if ($('#parentMerchantId').is(':visible')) {
		setError(get('parentMerchantId'), '');
		setLable('confirmMerchantCode', $('#parentMerchantId :selected').text());
	}
	return true;
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

function validateAgentData() {
	var agentAccountNumber = document.getElementById('agentAccountNumber').value;
	var agentClientId = document.getElementById('agentClientId').value;
	var agentANI = document.getElementById('agentANI').value;
	var currencyId = document.getElementById('currencyId').value;
	
	if(isEmpty(agentAccountNumber) | isEmpty(agentClientId) | isEmpty(agentANI)) {
		
	} else {
		validateData(agentAccountNumber, agentClientId, agentANI, currencyId);
	}
}

function validateData(agentAccountNumber, agentClientId, agentANI, currencyId) {
	$.ajax({
		type : "GET",
		url : "validateAgentDetails?agentAccountNumber=" + agentAccountNumber + "&agentClientId=" + agentClientId + "&agentANI=" + agentANI + "&currencyId=" + currencyId,
		async : false,
		success : function(response) {

			if(response == 'true') {
				$('#agentErrorId').text('');
			} else {
				$('#agentErrorId').text(response);
			}
			
			
		},
		error : function(e) {
			
		}
	});
}

function validateAgentError() {
	var errorValue = $('#agentErrorId').text();
	if (isEmpty(errorValue)) {
		return true;
	} else {
		return false;
	}
}

function validateAgentName() {
	var agentId = get('agentId').value.trim();
	var agentName = $('#agentId option:selected').text();
	if (isEmpty(agentId)) {
		get('agentAccountNumber').value = "";
		get('agentClientId').value = "";
		get('agentANI').value = "";
		/*setDiv('agentIdErr', webMessages.validationthisfieldismandatory);*/
		return true;
	} else {
		setDiv('agentIdErr', '');
		setLable('confirmAgentName', agentName);
		return true;
	}
}

function fetchAgentData(agentId) {
	if (agentId != '') {
		getAgentData(agentId);
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
			} else {
				
			}
		},
		error : function(e) {
			showAlertPopup(response);
		}
	});
}

function getAgentByCurrency(){
	var currency = document.getElementById('currencyId').value;
	var elementId = "agentId";
	fetchAgentNames(currency, elementId);
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
				document.getElementById(elementId).options.length = 0;
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
function validatePopupDesc() {

	var text = getVal("reason");
	if (isEmpty(text)) {
		setDiv("popDescError_div", "*This field is mandatory*");
		return false;
	}
	if (!invalidWords(text)) {
		setDiv("popDescError_div", "Please Enter Valid Text For Reason");
		return false;
	}
	if (text.length > 250) {
		setDiv("popDescError_div", message.IN_POUP_REASON_LENGTH);
		return false;
	} else {
		setDiv("popDescError_div", "&nbsp;");
		return true;
	}
}
function changeStatus(id, status, statusText,roleType) {
	clearPopupDesc();
	$('#'+roleType).popup('show');
	get('suspendActiveId').value = id;
	get('suspendActiveStatus').value = status;
}
function fetchPartnerName(merchantId) {
	if (merchantId == '') {
		return null;
	}
	getPartnerName(merchantId);
}

function getPartnerName(merchantId) {
	$.ajax({
		type : "GET",
		url : "getPartnerNameByMerchantCode?merchantId=" + merchantId,
		async : false,
		success : function(responseVal) {
			var obj = JSON.parse(responseVal);
			if (obj.errorMessage === "SUCCESS") {
				document.getElementById("partnerId").options.length = 0;
				var newOption = document.createElement("option");
				newOption.value = obj.partnerId;
				newOption.innerHTML = obj.partnerName;
				$(("#partnerId")).append(newOption);
				setValue('programManagerName', obj.programManagerName);
			}
		},
		error : function(e) {
		}
	});
}

function openCreateCancelConfirmationPopup() {
	$('#my_popup1').popup("show");
}

function searchValidationForMerchant(){
	if(!clientValidation('subMerchantCode','merchant_codes','subMerchantCodeError')
			| !clientValidation('businessName','company_name_aphhanumeric_space_dot_NM','businessNameError')
			| !clientValidation('firstName','company_name_aphhanumeric_space_dot_NM','firstNameError')
			| !clientValidation('lastName','company_name_aphhanumeric_space_dot_NM','lastNameError')
			| !clientValidation('emailId','email_id','emailIdError')
			| !clientValidation('phone','mobile_optional','phoneError')
			| !clientValidation('city','name_account','cityError')){
		return false;
	}
	return true;
}

function createValidationForMerchant(){
	if(!clientValidation('fax','fax','faxEr')
			| !clientValidation('address2','address2','address2Er')){
		return false;
	}
	return true;
}

function createValidationStep2(){
	if(!clientValidation('bankAddress1', 'bank_address2','bankAddress1ErrorDiv')
			| !clientValidation('bankAddress2', 'bank_address2','bankAddress2ErrorDiv')
			| !clientValidation('bankCity', 'bank_city_id','bankCityErrorDiv')){
		return false;
	}
	return true;
}