
var MAX_PROGRAM_MANAGER_FILE_SIZE = 300 * 1024 * 1;
function Logo() {
	openPopup();
}
function readURL(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function(e) {
			$('#logoEdit').attr('src', e.target.result).width(150).height(200);
		};
		$('#load').val(input.value.substring(input.value.lastIndexOf('\\')+1, input.value.length));
		reader.readAsDataURL(input.files[0]);
	}
}

function ProgramManagerValidation() {
	var imageData = get('programManagerLogo');
	var flag = true;

	if (!readPartnerLogo(imageData, 'programManagerLogoErrorDiv')
			| !clientValidation('programManagerEmailId', 'email','programManagerEmailIdEmailId_ErrorDiv')
			| !clientValidation('contactPhone', 'partner_phone','pgmmgrcontactphoneerrormsg')
			| !clientValidation('contactPerson', 'contact_person','pgmmgrcontactpersonerrormsg')
			| !clientValidation('businessEntityName', 'business_entity_name','pgmmgrbusinessentityerrormsg')
			| !clientValidation('companyName','company_name','pgmmgrcompanynameerrormsg')
			| !clientValidation('country','country','countryNameErrormsg')
	        | !clientValidation('state','state','stateNameErrormsg')
	        | !clientValidation('extension','extension_not_mandatory','extensionerr')
	        | !clientValidation('timezone','state','timezone_ErrorDiv')
	        | !clientValidation('batchPrefix','batch_prefix','pgmmgrbatchPrefixerrormsg')
	        | !clientValidation('schedulerRunTime','txn_date','pgmmgrschedulerRunTimeerrormsg')
			| !clientValidation('programMangerName', 'program_manager_name','pgmmgrNameErrormsg')
			| !validateProgramManagerId('programManagerType','programManagerTypeError')
			| !validateByPmType()) {
		clearValidationType();
		flag = false;
		return flag;
	}
	if (flag = true) {
		var obj = programManagerDetailsForm.bankName, options = obj.options, selected = [], i;

		for (i = 0; i < options.length; i++) {
			options[i].selected && selected.push(obj[i].value);
		}
		document.forms["programManagerDetailsForm"].submit();
	}
	return flag;
}

function validateByPmType(){
	var pmType = $('#programManagerType :selected').val();
	if(pmType =='' || pmType == 'onboarded'){
		if(!clientValidation('currencyName', 'orderType','currencyNameErrormsg')
				| !validateCardProgram('cardprogramId','OnboardCardProgramerrormsg')
				| !validateBank('bankName','pgmmgrbankiderrormsg')
				| !validateProgramManagerId('programManagerId','programManagerIdEr'))
			return false;
	}else if(pmType =='' || pmType == 'CreateIndependent'){
		if(!clientValidation('acquirerCurrencyName','orderType','acquirerCurrencyNameErrormsg')
				| !validateCardProgram('acquirerCardprogramId','pgmmgrCardProgramerrormsg')
				| !validateBank('acquirerBankName','acquirerBankNameerrormsg'))
			return false;
	}
	return true;
}

function validateBank(fieldId, errorId) {
	if ($('#' + fieldId).has('option').length <= 0) {
		setDiv(errorId, webMessages.validationthisfieldismandatory);
		return false;
	}
	if ($('select#' + fieldId).val() == null) {
		setDiv(errorId, webMessages.select_bank);
		return false;
	}
	setDiv(errorId, '');
	return true;
}

function validateCardProgram(fieldId, errorId) {
	if ($('#' + fieldId).has('option').length <= 0) {
		setDiv(errorId, webMessages.validationthisfieldismandatory);
		return false;
	}
	if ($('select#' + fieldId).val() == null) {
		setDiv(errorId, webMessages.select_card_program);
		return false;
	}
	setDiv(errorId, '');
	return true;
}

function validateProgramManagerId(fieldId,errorId) {
	var programManagerId = $('#'+fieldId+' :selected').val();
	if (isEmpty(programManagerId)) {
		$('#'+errorId).text(webMessages.validationthisfieldismandatory);
		return false;
	} else {
		$('#'+errorId).text('');
		return true;
	}
}

function readPartnerLogo(input, div_id) {
	setValue(div_id, '');
	$('#load').val(input.value.substring(input.value.lastIndexOf('\\')+1, input.value.length));
	var flag = true;
	if (isEmpty(input.value)) {
		setValue(div_id, '');
		return flag;
	}

	if (!isValidImage(input.value)) {
		document.getElementById(div_id).innerHTML = webMessages.invalidImageFormat;
		flag = false;
		return flag;
	}
	document.getElementById(div_id).innerHTML = '';
	if (input.files && input.files[0]) {
		if (parseInt(MAX_PROGRAM_MANAGER_FILE_SIZE) < parseInt(input.files[0].size)) {
			document.getElementById(div_id).innerHTML = webMessages.ImgSizeLessThan300kb;
			flag = false;
			return flag;
		}
	}
	if (flag) {
		setValue(div_id, '');
	}
	return flag;
}

function isValidImage(imageSrc) {
	var value = imageSrc.toUpperCase();
	if (value.indexOf('.PNG') != -1 || value.indexOf('.JPG') != -1
			|| value.indexOf('.JPEG') != -1 || value.indexOf('.GIF') != -1
			|| value.indexOf('.BMP') != -1) {
		return true;
	}
	return false;
}

function editProgramManager(programManagerId) {
	get('programManagerId').value = programManagerId;
	document.forms["editProgramManagerForm"].submit();
}

function editProgramManagerAccount(programManagerId, accountType) {
	get('programManagerId').value = programManagerId;
	get('accountType').value = accountType;
	document.forms["editProgramManagerAccountForm"].submit();
}

function editProgramManagerValidation() {

	var imageData = get('programManagerLogo');
	var flag = true;

	if (!readPartnerLogoedit(imageData, 'programManagerLogoErrorDiv')
			| !clientValidation('bankName', 'bank_name_dropdown',
					'bankiderrormsg')
			| !clientValidation('programManagerEmailId', 'email',
					'programManagerEmailIdEmailId_ErrorDiv')
			| !clientValidation('contactPhone', 'partner_phone',
					'pgmmgrcontactphoneerrormsg')
			| !clientValidation('contactPerson', 'contact_person',
					'pgmmgrcontactpersonerrormsg')
			| !clientValidation('contactPerson', 'contact_person',
					'pgmmgrcontactpersonerrormsg')
			| !clientValidation('extension','extension_not_mandatory','extensionerr')
			| !clientValidation('businessEntityName', 'business_entity_name',
					'pgmmgrbusinessentityerrormsg')
			| !clientValidation('companyName', 'company_name',
					'pgmmgrcompanynameerrormsg')
			| !clientValidation('programManagerName', 'program_manager_name',
					'pgmmgrnameerrormsg')) {
		clearValidationType();
		flag = false;
		return flag;

	}
	editProgramManager();
	return flag;

}

function editPMValidation() {

	var imageData = get('programManagerLogo');
	var flag = true;

	if (!readPartnerLogo(imageData, 'programManagerLogoErrorDiv')
			| !validateBank('bankName','pgmmgrbankiderrormsg')
			| !clientValidation('programManagerEmailId', 'email',
					'programManagerEmailIdEmailId_ErrorDiv')
			| !clientValidation('contactPhone', 'partner_phone',
					'pgmmgrcontactphoneerrormsg')
			| !clientValidation('contactPerson', 'contact_person',
					'pgmmgrcontactpersonerrormsg')
			| !clientValidation('businessEntityName', 'business_entity_name',
					'pgmmgrbusinessentityerrormsg')
			| !clientValidation('companyName', 'company_name',
					'pgmmgrcompanynameerrormsg')
			| !clientValidation('programManagerName', 'program_manager_name',
					'pgmmgrnameerrormsg')
			| !clientValidation('accountCurrency', 'program_manager_name',
					'currencyNameErrormsg')
					
			| !clientValidation('schedulerRunTime','txn_date','pgmmgrschedulerRunTimeerrormsg')
			| !clientValidation('batchPrefix','batch_prefix','pgmmgrbatchPrefixerrormsg')
			| !clientValidation('timezone','state','timezone_ErrorDiv')
			| !clientValidation('state','state','stateNameErrormsg')
			| !clientValidation('country','country','countryNameErrormsg')
			| !validateCardProgram('cardprogramId','pgmmgrCardProgramerrormsg')) {
		clearValidationType();
		flag = false;
		return flag;
	}
	if (flag = true) {
		var obj = programManagerEditDetailsForm.bankName, options = obj.options, selected = [], i;

		for (i = 0; i < options.length; i++) {
			options[i].selected && selected.push(obj[i].value);
		}
		document.forms["programManagerEditDetailsForm"].submit();
	}
	return flag;
}
function editProgramManagerAccountValidation() {

	var flag = true;

	if (!autoreplenishvalidationOfAccountEdit()) {
		clearValidationType();
		flag = false;
		return flag;

	}
	return flag;

}

function editPartnerAccountValidation() {

	var flag = true;

	if (!autoreplenishvalidationOfAccountEdit()) {
		clearValidationType();
		flag = false;
		return flag;
	}
	return flag;

}

function readPartnerLogoedit(input, div_id) {
	setValue(div_id, '');
	var flag = true;
	if (isEmpty(input.value)) {
		return flag;
	}
	if (!isValidImage(input.value)) {
		document.getElementById(div_id).innerHTML = webMessages.invalidImageFormat;
		flag = false;
		return flag;
	}
	document.getElementById(div_id).innerHTML = '';
	if (input.files && input.files[0]) {
		if (parseInt(MAX_PROGRAM_MANAGER_FILE_SIZE) < parseInt(input.files[0].size)) {
			document.getElementById(div_id).innerHTML = webMessages.ImgSizeLessThan300kb;
			errorFieldFocus('programManagerLogo');
			flag = false;
			return flag;
		}

	}
	if (flag) {
		setValue(div_id, '');
	}
	return flag;
}

function changeProgramManagerStatus(id, status, statusName) {
	clearPopupDesc();
	$('#programManagerDiv').popup('show');
	setDiv("sts", "Do you wish to change Role status to" + status + '?');
	get('manageProgramManagerId').value = id;
	get('manageProgramManagerStatus').value = status;

}

function fetchBankDetailsByBankid(bankId) {
	doAjaxFetchBankDetailsByBankId(bankId);
}

function doAjaxFetchBankDetailsByBankId(bankId) {

	$
			.ajax({
				type : "GET",
				url : "fetchBankDetailsByBankId?bankId=" + bankId,
				success : function(response) {
					var obj = JSON.parse(response);
					if (obj.errorMessage == "SUCCESS") {
						for (var i = 0; i < obj.customerBankRequest.length; i++) {
							var bankName = obj.customerBankRequest[i].bankName;
							var routingNumber = obj.customerBankRequest[i].routingNumber;
							var nameOnAccount = obj.customerBankRequest[i].nameOnAccount;
							var address = obj.customerBankRequest[i].address;
							var country = obj.customerBankRequest[i].country;
							var city = obj.customerBankRequest[i].city;
							var state = obj.customerBankRequest[i].state;
							var accountType = obj.customerBankRequest[i].accountType;
							var zip = obj.customerBankRequest[i].zip;
							setValue('bankName', bankName);
							setValue('routingNumber', routingNumber);
							setValue('bankAccountName', nameOnAccount);
							setValue('bankAddress', address);
							setValue('bankCountry', country);
							setValue('bankCity', city);
							setValue('bankState', state);
							setValue('bankAccountType', accountType);
							setValue('zipCode1', zip);
						}
					}
				},
				error : function(e) {

				}
			});

}
function fetchAmountOfPMSystemOrRevenueAccountOrTransferAccount(accountType) {
	var programManagerId = getVal('pmId');
	doAjaxFetchAmountForAccountNo(accountType, programManagerId);
}

function fetchAmountOfPartnerSystemOrRevenueAccount(accountType) {
	var partnerId = getVal('ptnerId');
	doAjaxFetchAmountForAccountTypeAndPartnerId(accountType, partnerId);
}

function doAjaxFetchAmountForAccountNo(accountType, programManagerId) {
	$.ajax({
		type : "GET",
		url : "getPMAccountBalance?accountType=" + accountType
				+ "&programManagerId=" + programManagerId,
		success : function(response) {
			if (response != "") {
				setValue('currentBalance', response);
			} else {
				setValue('currentBalance', '');
			}
		},
		failure : function(e) {
		}
	});
}

function doAjaxFetchAmountForAccountTypeAndPartnerId(accountType, partnerId) {
	$.ajax({
		type : "GET",
		url : "getPartnerAccountBalance?accountType=" + accountType
				+ "&partnerId=" + partnerId,
		success : function(response) {
			if (response != "") {
				setValue('currentBalance', response);
			} else {
				setValue('currentBalance', '');
			}
		},
		failure : function(e) {
		}
	});
}

// PM EFT
function validateBankEFTData() {
	var flag = true;
	if (!validatePMEFTForm()) {
		flag = false;
	}
	return flag;
}

// PM Check
function validateBankCheckData() {
	var flag = true;
	if (!validatePMCheckForm()) {
		flag = false;
	}
	return flag;
}
// Partner EFT
function validatePartnerBankEFTData() {
	var flag = true;
	if (!validatePartnerAccountBankEFT()) {
		flag = false;
	}
	return flag;
}
// Partner Check
function validatePartnerBankCheckData() {
	var flag = true;
	if (!validatePartnerAccountBankCheck()) {
		flag = false;
	}
	return flag;

}

function validatePartnerAccountBankEFT() {
	var flag = true;
	setValue('sucessDiv', '');
	setValue('errorDiv', '');

	if (!clientValidation('debitAccount', 'bank_name',
			'debitAccountErrormsgDiv')
			| !clientValidation('banKNames', 'bank_name',
					'bankAccountNumberErrormsgDiv')
			| !clientValidation('txnAmount', 'amount',
					'creditTxnAmountErrorDiv')) {
		flag = false;
	} else {
		openPopup();

		// set details to popup
		$('#programManagerNameValue_spid').text(getVal('partnerName'));

		$('#accountTypeValue_spid').text(getVal('debitAccount'));

		$('#avialableBal_spid').text(getVal('currentBalance'));

		$('#bankNameValue_spid').text($("#banKNames option:selected").text());

		$('#countryPopup_spid').text(getVal('bankCountry'));

		$('#cityPopup_spid').text(getVal('bankCity'));

		$('#statePopup_spid').text(getVal('bankState'));

		$('#zipCodeValue_spid').text(getVal('zipCode1'));

		$('#amountValue_spid').text(formatAmount(getVal('txnAmount')));

		$('#commetsValue_spid').text(getVal('txncomments'));

		setValue('partnerName', getVal('partnerName'));
		setValue('partnerId', getVal('ptnerId'));
		setValue('bankId', getVal('banKNames'));
		setValue('accountType', getVal('debitAccount'));
		setValue('zipCodeValue', getVal('zipCode1'));
		setValue('commetsValue', getVal('txncomments'));
		setValue('cityPopup', getVal('bankCity'));
		setValue('statePopup', getVal('bankState'));
		setValue('amountValue', getVal('txnAmount'));
	}

	return flag;
}

function validatePartnerAccountBankCheck() {
	var flag = true;
	setValue('sucessDiv', '');
	setValue('errorDiv', '');

	if (!clientValidation('debitAccount', 'bank_name', 'debitAccountErrorDiv')
			| !clientValidation('name', 'first_name', 'nameErrormsgDiv')
			| !clientValidation('addressLine1', 'address', 'addressErrormsgDiv')
			| !clientValidation('country', 'country', 'countryErrormsgDiv')
			| !clientValidation('state', 'state', 'stateErrormsgDiv')
			| !clientValidation('city', 'city', 'cityErrormsgDiv')
			| !clientValidation('zip', 'zip', 'zipErrormsgDiv')
			| !clientValidation('txnAmount', 'amount',
					'creditTxnAmountErrorDiv')) {
		flag = false;
	} else {
		openPopup();

		// set details to popup
		$('#programManagerNameValue_spid').text(getVal('partnerName'));

		$('#accountTypeValue_spid').text(getVal('debitAccount'));

		$('#avialableBal_spid').text(getVal('currentBalance'));

		var selectedCountry = $("#country option:selected").text();
		var selectedState = $("#state option:selected").text();

		$('#nameValue_spid').text(getVal('name'));

		$('#addressValue_spid').text(getVal('addressLine1'));

		$('#country_spid').text(selectedCountry);

		$('#state_spid').text(selectedState);

		$('#city_spid').text(getVal('city'));

		$('#zip_spid').text(getVal('zip'));

		setValue('"countryVal"', selectedCountry);
		setValue('stateVal', selectedState);

		$('#amountValue_spid').text(formatAmount(getVal('txnAmount')));

		$('#commetsValue_spid').text(getVal('txncomments'));

	}
	return flag;
}

function validatePMEFTForm() {
	var flag = true;
	setValue('sucessDiv', '');
	setValue('errorDiv', '');

	if (!clientValidation('debitAccount', 'bank_name',
			'bankAccountNumberErrormsgDiv')
			| !clientValidation('banKNames', 'bank_name',
					'bankAccountNumberErrormsgDiv')
			| !clientValidation('txnAmount', 'amount',
					'creditTxnAmountErrorDiv')) {
		flag = false;
	} else {
		openPopup();

		// set details to popup
		$('#programManagerNameValue_spid').text(getVal('PManagerName'));

		$('#accountTypeValue_spid').text(
				$('#debitAccount option:selected').text());

		$('#avialableBal_spid').text(getVal('currentBalance'));

		$('#bankNameValue_spid').text($('#banKNames option:selected').text());

		$('#countryPopup_spid').text(getVal('bankCountry'));

		$('#cityPopup_spid').text(getVal('bankCity'));

		$('#statePopup_spid').text(getVal('bankState'));

		$('#zipCodeValue_spid').text(getVal('zipCode1'));

		$('#amountValue_spid').text(formatAmount(getVal('txnAmount')));

		$('#commetsValue_spid').text(getVal('txncomments'));

		setValue('programManagerName', getVal('PManagerName'));
		setValue('programManagerId', getVal('pmId'));
		setValue('bankId', getVal('banKNames'));
		setValue('accountType', getVal('debitAccount'));
		setValue('zipCodeValue', getVal('zipCode1'));
		setValue('commetsValue', getVal('txncomments'));
		setValue('cityPopup', getVal('bankCity'));
		setValue('statePopup', getVal('bankState'));
		setValue('amountValue', getVal('txnAmount'));
	}

	return flag;
}

function validatePMCheckForm() {
	var flag = true;
	setValue('sucessDiv', '');
	setValue('errorDiv', '');

	if (!clientValidation('debitAccount', 'bank_name', 'debitAccountErrorDiv')
			| !clientValidation('name', 'first_name', 'nameErrormsgDiv')
			| !clientValidation('addressLine1', 'address', 'addressErrormsgDiv')
			| !clientValidation('country', 'country', 'countryErrormsgDiv')
			| !clientValidation('state', 'state', 'stateErrormsgDiv')
			| !clientValidation('city', 'city', 'cityErrormsgDiv')
			| !clientValidation('zip', 'zip', 'zipErrormsgDiv')
			| !clientValidation('txnAmount', 'amount',
					'creditTxnAmountErrorDiv')) {
		flag = false;
	} else {
		openPopup();

		// set details to popup
		$('#programManagerNameValue_spid').text(getVal('PManagerName'));

		$('#accountTypeValue_spid').text(
				$('#debitAccount option:selected').text());

		$('#avialableBal_spid').text(getVal('currentBalance'));

		var selectedCountry = $("#country option:selected").text();
		var selectedState = $("#state option:selected").text();

		$('#nameValue_spid').text(getVal('name'));

		$('#addressValue_spid').text(getVal('addressLine1'));

		$('#country_spid').text(selectedCountry);

		$('#state_spid').text(selectedState);

		$('#city_spid').text(getVal('city'));

		$('#zip_spid').text(getVal('zip'));

		setValue('"countryVal"', selectedCountry);
		setValue('stateVal', selectedState);

		$('#amountValue_spid').text(formatAmount(getVal('txnAmount')));

		$('#commetsValue_spid').text(getVal('txncomments'));
	}
	return flag;
}

function autoreplenishvalidationOfAccountEdit() {
	var autoreplenish = document.getElementById("autorepenish").checked;
	if (autoreplenish) {
		if (!clientValidation('accountThresholdamount', 'amount',
				'accountThresholdamountError')
				| !clientValidation('sendFund', 'modeOFTransfer',
						'modeOfTransferErrorMsgDiv')
				| !clientValidation('bankId', 'modeOFTransfer',
						'bankIdErrorMsgDiv'))
			return false;
	}
	return true;
}

function autoreplenishvalidation() {
	var autoreplenish = document.getElementById("autorepenish").checked;
	if (autoreplenish) {
		if (!clientValidation('accountThresholdamount', 'amount',
				'accountThresholdamountError')
				| !clientValidation('sendFund', 'modeOFTransfer',
						'modeOfTransferErrorMsgDiv')
				| !clientValidation('bankId', 'modeOFTransfer',
						'bankIdErrorMsgDiv'))
			return false;
	}
	return true;
}

function doAjaxForState(countryId) {

	if (countryId == "") {
		return;
	}

	$.ajax({
		type : "GET",
		url : "fetchState?countryId=" + countryId,

		success : function(response) {

			var obj = JSON.parse(response);

			// remove the previous option from element
			document.getElementById('state').options.length = 0;

			// create select option
			var selectOption = document.createElement("option");
			selectOption.innerHTML = "..:Select:..";
			selectOption.value = "";
			$("#state").append(selectOption);

			if (obj.errorMessage == "SUCCESS") {
				var data = obj.listOfStateRequests;

				for (var i = 0; i < data.length; i++) {
					var state = data[i].name;

					var newOption = document.createElement("option");
					newOption.value = data[i].id;
					newOption.innerHTML = state;

					$("#state").append(newOption);
				}
			}

		},
		failure : function(e) {
		}

	});
}

function fetchPmDetailsByPmType(programManagerType) {
	resetAllFields();
	if (programManagerType == 'onboarded') {
		$(".issuanceProgramManager").show();
		$(".issuancePMlogo").show();
		$(".acquirerCurrencyNames").hide();
		$(".acquirerCardProgram").hide();
		$(".issuanceCardProgram").show();
		$(".acquirerBankNames").hide();
		$(".currencyNames").show();
		$(".bankNames").show();
		/*document.getElementById('country').options.length = 0;
		var selectOption = document.createElement("option");
		selectOption.innerHTML = "..:Select:..";
		selectOption.value = "";
		$("#country").append(selectOption);*/
		document.getElementById('state').options.length = 0;
		var selectOption = document.createElement("option");
		selectOption.innerHTML = "..:Select:..";
		selectOption.value = "";
		$("#state").append(selectOption);
		document.getElementById('timezone').options.length = 0;
		var selectOption = document.createElement("option");
		selectOption.innerHTML = "..:Select:..";
		selectOption.value = "";
		$("#timezone").append(selectOption);
		doAjaxFetchAllIssunacePMDetails(programManagerType);
	} else if(programManagerType == 'CreateIndependent') {
		$(".acquirerCurrencyNames").show();
		$(".acquirerBankNames").show();
		$(".acquirerCardProgram").show();
		$(".issuanceCardProgram").hide();
		$(".issuanceProgramManager").hide();
		$(".issuancePMlogo").hide();
		$(".currencyNames").hide();
		$(".bankNames").hide();
		getIndependentPMDetails(programManagerType);
		document.getElementById('programManagerId').options.length = 0;
		var selectOption = document.createElement("option");
		selectOption.innerHTML = "..:Select:..";
		selectOption.value = "";
		$("#programManagerId").append(selectOption);
	}else{
		document.getElementById('programManagerId').options.length = 0;
		$(".issuanceProgramManager").hide();
		$(".issuancePMlogo").hide();
		var selectOption = document.createElement("option");
		selectOption.innerHTML = "..:Select:..";
		selectOption.value = "";
		$("#programManagerId").append(selectOption);
		return;
	}
}

function getIndependentPMDetails(programManagerType) {
	$.ajax({

		type : "GET",
		url : "getIndependentPMDetails?programManagerType=" + programManagerType,
		success : function(response) {
			// we have the response
			if (response != "") {
				var obj = JSON.parse(response);
				
				$("#programMangerName").val('');
				$("#companyName").val('');
				$("#businessEntityName").val('');
				$("#contactPerson").val('');
				$("#contactPhone").val('');
				$("#extension").val('');
				$("#programManagerEmailId").val('');
				$("#country").val('');
				$("#state").val('');
				$("#timezone").val('');
				$("#batchPrefix").val('');
				$("#LogoDiv").val('');
				// Remove previous options from the dropdown
				document.getElementById('acquirerCurrencyName').options.length = 0;

				var selectOption = document.createElement("option");
				selectOption.innerHTML = "..:Select:..";
				selectOption.value = "";
				$("#acquirerCurrencyName").append(selectOption);
				
				for (var i = 0; i < obj.length; i++) {

					var value = obj[i].value;
					var label = obj[i].label;
					{
						var newOption = document.createElement("option");
						newOption.value = value;
						newOption.innerHTML = label;
						$("#acquirerCurrencyName").append(newOption);
					}
				}
				$(".acquirerCurrencyNames").show();
				$(".acquirerBankNames").show();
				

			} else {
				document.getElementById('currencyName').options.length = 0;
				var selectOption = document.createElement("option");
				selectOption.innerHTML = "..:Select:..";
				selectOption.value = "";
				$("#currencyName").append(selectOption);
			}
		},
		failure : function(e) {
		}
	});
}


function doAjaxFetchAllIssunacePMDetails(programManagerType) {
	$.ajax({

		type : "GET",
		url : "getIssunacePMDetailsByPmType?programManagerType=" + programManagerType,
		success : function(response) {
			// we have the response
			if (response != "") {
				var obj = JSON.parse(response);
				$("#programMangerName").val('');
				$("#companyName").val('');
				$("#businessEntityName").val('');
				$("#contactPerson").val('');
				$("#contactPhone").val('');
				$("#extension").val('');
				$("#programManagerEmailId").val('');
				$("#LogoDiv").val('');
				// Remove previous options from the dropdown
				document.getElementById('programManagerId').options.length = 0;

				var selectOption = document.createElement("option");
				selectOption.innerHTML = "..:Select:..";
				selectOption.value = "";
				$("#programManagerId").append(selectOption);
				
				for (var i = 0; i < obj.length; i++) {

					var programManagerId = obj[i].id;
					var programManagerName = obj[i].programManagerName;
					{
						var newOption = document.createElement("option");
						newOption.value = programManagerId;
						newOption.innerHTML = programManagerName;

						$("#programManagerId").append(newOption);
					}
				}
				$(".acquirerCurrencyNames").hide();
				$(".acquirerBankNames").hide();

			} else {
				document.getElementById('programManagerId').options.length = 0;

				var selectOption = document.createElement("option");
				selectOption.innerHTML = "..:Select:..";
				selectOption.value = "";
				$("#programManagerId").append(selectOption);
			}
		},

		failure : function(e) {
		}
	});
}

function fetchPmDetailsByPmId(programManagerId) {
	if (programManagerId == '') {
		resetAllFields();
		return;
	}
	doAjaxFetchIssunacePMDetailsById(programManagerId);
	
}

function doAjaxFetchIssunacePMDetailsById(programManagerId) {
	document.getElementById('bankName').options.length = 0;
	document.getElementById('cardprogramId').options.length = 0;
	$.ajax({

		type : "GET",
		url : "getIssunacePMDetailsByPmId?programManagerId=" + programManagerId,
		success : function(response) {
			// we have the response
			if (response != "") {
				var obj = JSON.parse(response);
				
				$("#programMangerName").val(obj[0].programManagerName);
				$("#companyName").val(obj[0].companyName);
				$("#businessEntityName").val(obj[0].businessName);
				$("#contactPerson").val(obj[0].contactName);
				$("#contactPhone").val(obj[0].contactPhone);
				$("#extension").val(obj[0].extension);
				$("#batchPrefix").val(obj[0].batchPrefix);
				$("#schedulerRunTime").val(obj[0].schedulerRunTime);
				$("#standardTimeOffset").val(obj[0].standardTimeOffset);
				$("#programManagerEmailId").val(obj[0].contactEmail);
				$("#LogoDiv").val(obj[0].issuanceProgramManagerLogo);
				$("#currencyName").val(obj[0].currencyConfigRequest.currencyCodeAlpha);
				document.getElementById('country').options.length = 0;
				var country = obj[0].country;
				{
					var newOption = document.createElement("option");
					newOption.value = country;
					newOption.innerHTML = country;
					$("#country").append(newOption);
				}
				document.getElementById('state').options.length = 0;
				var state = obj[0].state;
				{
					var newOption = document.createElement("option");
					newOption.value = state;
					newOption.innerHTML = state;
					$("#state").append(newOption);
				}
				document.getElementById('timezone').options.length = 0;
				var pmTimeZone = obj[0].pmTimeZone;
				{
					var newOption = document.createElement("option");
					newOption.value = pmTimeZone;
					newOption.innerHTML = pmTimeZone;
					$("#timezone").append(newOption);
				}
				document.getElementById('currencyName').options.length = 0;
					var currencyCodeAlpha = obj[0].currencyConfigRequest.currencyCodeAlpha;
					{
						var newOption = document.createElement("option");
						newOption.value = currencyCodeAlpha;
						newOption.innerHTML = currencyCodeAlpha;
						$("#currencyName").append(newOption);
					}
				
				document.getElementById('bankName').options.length = 0;
				for (var i = 0; i < obj[0].bankRequest.length; i++) {
					var bankId = obj[0].bankRequest[i].bankId;
					var bankName = obj[0].bankRequest[i].bankName;
					{
						var newOption = document.createElement("option");
						newOption.value = bankId;
						newOption.innerHTML = bankName;

						$("#bankName").append(newOption);
					}
				}
				document.getElementById('programMangerName').readOnly = true;
				document.getElementById('companyName').readOnly = true;
				document.getElementById('businessEntityName').readOnly = true;
				document.getElementById('contactPerson').readOnly = true;
				document.getElementById('extension').readOnly = true;
				document.getElementById('batchPrefix').readOnly = true;
				document.getElementById('schedulerRunTime').readOnly = true;
				document.getElementById('contactPhone').readOnly = true;
				document.getElementById('programManagerEmailId').readOnly = true;
				$('#currencyName').attr("readonly", "readonly"); 
				$('#country').attr("readonly", "readonly");
				$('#state').attr("readonly", "readonly");
				$('#timezone').attr("readonly", "readonly");
			}
		},
		failure : function(e) {
		}
	});
}

function resetAllFields() {
	setValue('programMangerName', '');
	setValue('companyName', '');
	setValue('businessEntityName', '');
	setValue('contactPerson', '');
	setValue('contactPhone', '');
	setValue('extension', '');
	setValue('programManagerEmailId', '');
	setValue('country', '');
	setValue('state', '');
	setValue('timezone', '');
	setValue('batchPrefix', '');
	setValue('schedulerRunTime', '');
	setValue('load', '');
	document.getElementById('bankName').options.length = 0;
	document.getElementById('cardprogramId').options.length = 0;
	document.getElementById('currencyName').options.length = 0;
	$('#currencyName').text('');
	document.getElementById('programMangerName').readOnly = false;
	document.getElementById('companyName').readOnly = false;
	document.getElementById('businessEntityName').readOnly = false;
	document.getElementById('contactPerson').readOnly = false;
	document.getElementById('extension').readOnly = false;
	document.getElementById('batchPrefix').readOnly = false;
	document.getElementById('schedulerRunTime').readOnly = false;
	document.getElementById('contactPhone').readOnly = false;
	document.getElementById('programManagerEmailId').readOnly = false;
	$('#currencyName').attr("readonly", false); 
	$('#country').attr("readonly", false);
	$('#state').attr("readonly", false);
	$('#timezone').attr("readonly", false);
}

function fetchPmCardProgramByPmId(programManagerId) {
	if (programManagerId == '') {
		return;
	}
	doAjaxFetchIssunacePMCardProgramsById(programManagerId);
	
}

function doAjaxFetchIssunacePMCardProgramsById(programManagerId) {
	$.ajax({

		type : "GET",
		url : "getIssunacePMCardProgramsByPmId?programManagerId=" + programManagerId,
		success : function(response) {
			// we have the response
			if (response != "") {
				var obj = JSON.parse(response);
				
				document.getElementById('cardprogramId').options.length = 0;
				for (var i = 0; i < obj.length; i++) {
					var cardProgramId = obj[i].cardProgramId;
					var cardProgramName = obj[i].cardProgramName;
					{
						var newOption = document.createElement("option");
						newOption.value = cardProgramId;
						newOption.innerHTML = cardProgramName;
						$("#cardprogramId").append(newOption);
					}
				}
			}
		},
		failure : function(e) {
		}
	});
}

function fetchBankDetailsByCurrency(currency) {
	if (currency == '') {
		return;
	}
	doAjaxFetchBankDetailsByCurrency(currency);
	doAjaxFetchAcquirerCardProgramDetailsByCurrency(currency);
	
}

function doAjaxFetchBankDetailsByCurrency(currency) {
	$.ajax({

		type : "GET",
		url : "getBankDetailsByCurrency?currency=" + currency,
		success : function(response) {
			// we have the response
			if (response != "") {
				var obj = JSON.parse(response);
				document.getElementById('acquirerBankName').options.length = 0;
				for (var i = 0; i < obj.length; i++) {
					var cardProgramId = obj[i].cardProgramId;
					var cardProgramName = obj[i].cardProgramName;
					{
						var newOption = document.createElement("option");
						newOption.value = obj[i].value;
						newOption.innerHTML = obj[i].label;
						$("#acquirerBankName").append(newOption);
					}
				}
			}
		},
		failure : function(e) {
		}
	});
}

function doAjaxFetchAcquirerCardProgramDetailsByCurrency(currency) {
	$.ajax({

		type : "GET",
		url : "getAcquirerCardProgramDetailsByCurrency?currency=" + currency,
		success : function(response) {
			// we have the response
			if (response != "") {
				var obj = JSON.parse(response);
			
				document.getElementById('acquirerCardprogramId').options.length = 0;
				for (var i = 0; i < obj.length; i++) {
					var cardProgramId = obj[i].cardProgramId;
					var cardProgramName = obj[i].cardProgramName;
					{
						var newOption = document.createElement("option");
						newOption.value = obj[i].value;
						newOption.innerHTML = obj[i].label;
						$("#acquirerCardprogramId").append(newOption);
					}
				}
			}
		},
		failure : function(e) {
		}
	});
}

function fetchTimeZone(id) {
	if(id == '') {
		return;
	}
	doAjaxForTimeZone(id);
}

function doAjaxForTimeZone(countryId) {
	
	$.ajax({
			type : "GET",
			url : "fetchTimeZone?countryId="+countryId,
			
			success : function(response) {
						
					var obj = JSON.parse(response);

					if (response != "") {
						var obj = JSON.parse(response);
					
						document.getElementById('timezone').options.length = 0;
						var selectOption = document.createElement("option");
						selectOption.innerHTML = "..:Select:..";
						selectOption.value = "";
						$("#timezone").append(selectOption);
						for (var i = 0; i < obj.length; i++) {
							var cardProgramId = obj[i].cardProgramId;
							var cardProgramName = obj[i].cardProgramName;
							{
								var newOption = document.createElement("option");
								newOption.value = obj[i].id;
								newOption.innerHTML = obj[i].standardTimeOffset;
								$("#timezone").append(newOption);
							}
						}
					}
		},
		failure :	function(e) {
		  }
				
	});
}

function fetchTimeZoneForEdit(id) {
	if(id == '') {
		return;
	}
	doAjaxForTimeZoneForEdit(id);
}

function doAjaxForTimeZoneForEdit(countryId) {
	
	$.ajax({
			type : "GET",
			url : "fetchTimeZone?countryId="+countryId,
			
			success : function(response) {
						
					var obj = JSON.parse(response);

					if (response != "") {
						var obj = JSON.parse(response);
					
						document.getElementById('timezone').options.length = 0;
						var selectOption = document.createElement("option");
						selectOption.innerHTML = "..:Select:..";
						selectOption.value = "";
						$("#timezone").append(selectOption);
						for (var i = 0; i < obj.length; i++) {
							var cardProgramId = obj[i].cardProgramId;
							var cardProgramName = obj[i].cardProgramName;
							{
								var newOption = document.createElement("option");
								newOption.value = obj[i].standardTimeOffset;
								newOption.innerHTML = obj[i].standardTimeOffset;
								$("#timezone").append(newOption);
							}
						}
					}
		},
		failure :	function(e) {
		  }
				
	});
}

function goToDashBoard() {
	window.location.href = 'home';
}

function updateFileAndReadFileSettlementReport(input, div_id){
	updateFileName();
	readFileData(input, div_id);
}

function updateFileName() {
	var fileData = get('browseFile');
	if (document.getElementById('fileData' == null)) {
		setDiv('dataFile_errDiv', webMessages.feeProgramMandatory);

	}
	var fileName = $('input[type=file]').val().split('/').pop().split('\\')
			.pop();
	$('#dataFile').val(fileName);
}

function validateUserBulkFormData() {
	var fileData = get('browseFile');
	var flag = true;
	if (!readFileData(fileData, 'dataFile_errDiv')) {
		flag = false;
		return flag;
	}
	return flag;
}

var MAX_FILE_SIZE = 1024 * 1024 * 3;

function readFileData(input, div_id) {

	if (!mandatorytrue(input.value, div_id)) {
		return false;
	}

	if (!isValidFile(input.value)) {
		document.getElementById(div_id).innerHTML =  webMessages.WRONG_FILE_FORMATE;
		return false;
	}
	document.getElementById(div_id).innerHTML = '';
	if (input.files && input.files[0]) {
		if (parseInt(MAX_FILE_SIZE) < parseInt(input.files[0].size)) {
			document.getElementById(div_id).innerHTML = webMessages.FILE_SIZE;
			return;
		}
	}
	return true;
}

function isValidFile(fileSrc) {
	var value = fileSrc.toUpperCase();
	if (value.indexOf('.CSV') != -1) {
		return true;
	}
	return false;
}
function validateIncomingSettlementReportData() {
	var fileData = get('browseFile');
	var flag = true;
	if (!readFileData(fileData, 'dataFile_errDiv')){
	    	flag = false;
			return flag;
	    }
	document.getElementById(div_id).innerHTML = '';
	return flag;
}

function showSettlementPage() {
	window.location.href = 'show-incoming-settlement-report';
}

function setTimeZone() {
	setValue('timeZoneOffset', new Date().toString().match(
			/([A-Z]+[\+-][0-9]+)/)[1]);
	setValue('timeZoneRegion', jstz.determine().name());
	return true;
}

function fetchCardProgramByPmId() {
	var issuancePmId = $('#issuancePmId').val();
	var acquirerPmId = $('#id').val();
	var pmCurrency = $('#accountCurrency').val();
	if(issuancePmId == '' || issuancePmId == null)
		return;
	doAjaxFetchIssunaceCardProgramsByPmId(acquirerPmId,issuancePmId,pmCurrency);
}

function doAjaxFetchIssunaceCardProgramsByPmId(acquirerPmId,issuancePmId,pmCurrency) {
	$('#errorDiv').text('');
	$.ajax({
		type : "GET",
		url : "getIssuanceCardProgramsByPmId?issuancePmId=" + issuancePmId +"&currency=" +pmCurrency +"&acquirerPmId=" +acquirerPmId,
		success : function(response) {
			// we have the response
			if (response != null && response != "") {
				var obj = JSON.parse(response);
				if(obj.cardProgramList.length > 0){
					for (var i = 0; i < obj.cardProgramList.length; i++) {
						var cardProgramId = obj.cardProgramList[i].cardProgramId;
						var cardProgramName = obj.cardProgramList[i].cardProgramName;
						{
							var newOption = document.createElement("option");
							newOption.value = cardProgramId;
							newOption.innerHTML = cardProgramName;
							$("#cardprogramId").append(newOption);
						}
					}					
				}else if(obj.errorCode == 'CEC_0001'){
					$('#errorDiv').text('No new card programs found');
				}
			}
		},
		failure : function(e) {
		}
	});
}

function validateSpecialCharactersPMSearch() {
	if (!clientValidation('programManagerName','companyname_not_mandatory','pgmmgrNameErrormsg')
			| !clientValidation('companyName','companyname_not_mandatory','pgmmgrcompanynameerrormsg')) {
		return false;
	}
	return true;
}
