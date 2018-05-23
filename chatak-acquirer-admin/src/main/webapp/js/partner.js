var MAX_PARTNER_FILE_SIZE = 300 * 1024 * 1;
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

function PartnerValidation() {
	var imageData = get('partnerLogo');
	var flag = true;
	if (!readPartnerLogo(imageData, 'partnerLogoErrorDiv')
			| !clientValidation('zip', 'zip', 'zip_ErrorDiv')
			| !clientValidation('city', 'bank_city', 'city_ErrorDiv')
			| !clientValidation('state', 'state', 'state_ErrorDiv')
			| !clientValidation('country', 'country', 'country_ErrorDiv')
			| !clientValidation('address2', 'address2', 'address2_ErrorDiv')
			| !clientValidation('address1', 'address', 'address1_ErrorDiv')
			| !clientValidation('contactPhone', 'partner_phone',
					'contactphoneerrormsg')
			| !clientValidation('contactPerson', 'contact_person',
					'contactpersonerrormsg')
			| !clientValidation('partnerName', 'partner_name',
					'partneriderrormsg')
			| !clientValidation('businessEntityName', 'business_entity_name',
					'businessentityerrormsg')
			| !clientValidation('programManagerId', 'bank_name_dropdown',
					'pgmngerrormsg')
			| !clientValidation('companyName', 'company_name',
					'companynameerrormsg')
			| !clientValidation('partnerClientId', 'partner_client_id',
					'partnerClientIdError')
			| !clientValidation('bankName', 'bank_name_dropdown',
					'bankiderrormsg')
			| !clientValidation('programManagerId', 'bank_name_dropdown',
					'pgmngerrormsg')
			| !clientValidation('currencyName', 'orderType',
					'currencyNameErrormsg')) {
		clearValidationType();
		flag = false;
		return flag;
	}
	return flag;

}

function IntegrationPartnerValidation() {
	var imageData = get('partnerLogo');
	var flag = true;
	assigValidationType('SUBMIT');
	if (!readPartnerLogo(imageData, 'partnerLogoErrorDiv')
			| !clientValidation('programManagerId', 'bank_name_dropdown',
					'pgmngerrormsg')
			| !clientValidation('bankName', 'bank_name_dropdown',
					'bankiderrormsg')
			| !clientValidation('partnerClientId', 'company_name',
					'partnerClientIdError')
			| !clientValidation('companyName', 'company_name',
					'companynameerrormsg')
			| !ipaddressvalidation()
			| !clientValidation('businessEntityName', 'business_entity_name',
					'businessentityerrormsg')
			| !clientValidation('partnerName', 'partner_name',
					'partneriderrormsg')
			| !clientValidation('contactPerson', 'contact_person',
					'contactpersonerrormsg')
			| !clientValidation('contactPhone', 'partner_phone',
					'contactphoneerrormsg')
			| !clientValidation('address1', 'address', 'address1_ErrorDiv')
			| !clientValidation('city', 'bank_city', 'city_ErrorDiv')
			| !clientValidation('state', 'state', 'state_ErrorDiv')
			| !clientValidation('country', 'country', 'country_ErrorDiv')
			| !clientValidation('zip', 'zip', 'zip_ErrorDiv')) {
		clearValidationType();
		flag = false;
		return flag;
	}
	return flag;

}

function autoreplenishvalidation() {
	var flag1 = true;

	var autoreplenish = document.getElementById("autorepenish").checked;
	if (autoreplenish == true) {
		if (!clientValidation('accountThresholdamount', 'amount',
				'accountThresholdamountError'))
			flag1 = false;
		else
			flag1 = true;
	}
	return flag1;
}

function ipaddressvalidation() {
	var flag1 = true;

	var ipAddress = document.getElementById("defaultPartnerAPI").checked;
	if (ipAddress == true) {
		if (!clientValidation('whiteListIPAddress', 'whiteList_IP_Address',
				'whiteListIPAddressError'))
			flag1 = false;
		else
			flag1 = true;
	}
	return flag1;
}

function programManagerPartnerValidation() {
	var imageData = get('partnerLogo');
	var flag = true;
	assigValidationType('SUBMIT');
	if (!readPartnerLogo(imageData, 'partnerLogoErrorDiv')
			| !clientValidation('zip', 'zip', 'zip_ErrorDiv')
			| !clientValidation('city', 'bank_city', 'city_ErrorDiv')
			| !clientValidation('state', 'state', 'state_ErrorDiv')
			| !clientValidation('country', 'country', 'country_ErrorDiv')
			| !clientValidation('address2', 'address2', 'address2_ErrorDiv')
			| !clientValidation('address1', 'address', 'address1_ErrorDiv')
			| !clientValidation('contactPhone', 'partner_phone',
					'contactphoneerrormsg')
			| !clientValidation('contactPerson', 'contact_person',
					'contactpersonerrormsg')
			| !clientValidation('partnerName', 'partner_name',
					'partneriderrormsg')
			| !clientValidation('businessEntityName', 'business_entity_name',
					'businessentityerrormsg')
			| !clientValidation('companyName', 'company_name',
					'companynameerrormsg')
			| !clientValidation('bankName', 'bank_name_dropdown',
					'bankiderrormsg')
			| !clientValidation('programManagerName', 'program_manager_name',
					'pgmngerrormsg')) {
		clearValidationType();
		flag = false;
		return flag;

	}
	return flag;

}

function editPartnerValidation() {
	var imageData = get('partnerLogo');
	var flag = true;
	assigValidationType('SUBMIT');
	if (!clientValidation('reason', 'nonmandatorycomment', 'remarksErrorDiv')
			| !readPartnerLogoedit(imageData, 'partnerLogoErrorDiv')
			| !clientValidation('zip', 'zip', 'zip_ErrorDiv')
			| !clientValidation('city', 'bank_city', 'city_ErrorDiv')
			| !clientValidation('state', 'state', 'state_ErrorDiv')
			| !clientValidation('country', 'country', 'country_ErrorDiv')
			| !clientValidation('address1', 'address', 'address1_ErrorDiv')
			| !clientValidation('address2', 'address2', 'address2_ErrorDiv')
			| !clientValidation('contactPhone', 'partner_phone',
					'contactphoneerrormsg')
			| !clientValidation('contactPerson', 'contact_person',
					'contactpersonerrormsg')
			| !clientValidation('partnerName', 'partner_name',
					'partneriderrormsg')
			| !clientValidation('businessEntityName', 'business_entity_name',
					'businessentityerrormsg')
			| !clientValidation('partnerClientId', 'partner_client_id',
					'partnerClientIdError')
			| !clientValidation('companyName', 'company_name',
					'companynameerrormsg')
			| !clientValidation('bankName', 'bank_name_dropdown',
					'bankiderrormsg')
			| !clientValidation('currencyName', 'orderType',
					'currencyNameErrormsg')) {

		clearValidationType();
		flag = false;
		return flag;

	}
	return flag;

}

function readPartnerLogo(input, div_id) {
	setValue(div_id, '');
	$('#load').val(input.value.substring(input.value.lastIndexOf('\\')+1, input.value.length));
	var flag = true;
	if (isEmpty(input.value)) {
		return flag;
	}

	if (!isValidImage(input.value)) {
		document.getElementById(div_id).innerHTML = webMessages.invalidImageFormat;
		errorFieldFocus('partnerLogo');
		flag = false;
		return flag;
	}
	document.getElementById(div_id).innerHTML = '';
	if (input.files && input.files[0]) {
		if (parseInt(MAX_PARTNER_FILE_SIZE) < parseInt(input.files[0].size)) {
			document.getElementById(div_id).innerHTML = webMessages.ImgSizeLessThan300kb;
			errorFieldFocus('partnerLogo');
			flag = false;
			return flag;
		}

	}
	if (flag) {
		setValue(div_id, '');
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
		errorFieldFocus('partnerLogo');
		document.getElementById(div_id).innerHTML = webMessages.invalidImageFormat;
		flag = false;
		return flag;
	}
	document.getElementById(div_id).innerHTML = '';
	if (input.files && input.files[0]) {
		if (parseInt(MAX_PARTNER_FILE_SIZE) < parseInt(input.files[0].size)) {
			errorFieldFocus('partnerLogo');
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

function resetPartner() {
	setDiv("companynameerrormsg", "&nbsp");
	setDiv("businessentityerrormsg", "&nbsp");
	setDiv("partneriderrormsg", "&nbsp");
	setDiv("bankiderrormsg", "&nbsp");
	setDiv("contactpersonerrormsg", "&nbsp");
	setDiv("contactphoneerrormsg", "&nbsp");
	setDiv("partnerLogoErrorDiv", "&nbsp");
}

function resetEditPartner() {
	window.location.href = "prepaid-admin-partner-search";
}

function changePartnerStatus(id, status, statusName) {
	clearPopupDesc();
	$('#partnerDiv').popup('show');
	setDiv("sts", "Do you wish to change Role status to" + status + '?');
	get('managePartnerId').value = id;
	get('partnerStatus').value = status;

}

function changeIntegrationPartnerStatus(id, status, statusName) {
	clearPopupDesc();
	get('managePartnerId').value = id;
	get('partnerStatus').value = status;
	$('#partnerDiv').popup('show');
	setDiv("sts", "Do you wish to change Role status to" + status + '?');

}

function fetchBankNameforPgmng(id, page) {

	if (id == "") {
		return;
	}
	doAjaxForBank(id)
}

function fetchBankNameforPgmngtoIntegrationPartner(id, page) {

	if (id == "") {
		return;
	}
	doAjaxForBanktoPgm(id)
}

function doAjaxForBank(programManagerId) {

	$.ajax({
		type : "GET",
		url : "fetchBanksofPgmng?programManagerId=" + programManagerId,
		success : function(response) {
			var obj = JSON.parse(response);
			document.getElementById('bankName').options.length = 0;
			var selectOption1 = document.createElement("option");
			selectOption1.innerHTML = "..:Select:..";
			selectOption1.value = "";
			$("#bankName").append(selectOption1);

			if (obj.errorMessage == "SUCCESS") {
				var bankData = obj.bankRequests;

				for (var i = 0; i < bankData.length; i++) {

					var bankNames = bankData[i].bankName;
					var bankIds = bankData[i].id;

					var newOption = document.createElement("option");
					newOption.value = bankIds;
					newOption.innerHTML = bankNames;

					$("#bankName").append(newOption);
				}
			}
		},
		error : function(e) {
		}
	});

}

function doAjaxForBanktoPgm(programManagerId) {

	$.ajax({
		type : "GET",
		url : "fetchBanksofPgmngtoIntgPartner?programManagerId="
				+ programManagerId,
		success : function(response) {
			var obj = JSON.parse(response);
			document.getElementById('bankName').options.length = 0;
			var selectOption1 = document.createElement("option");
			selectOption1.innerHTML = "..:Select:..";
			selectOption1.value = "";
			$("#bankName").append(selectOption1);

			if (obj.errorMessage == "SUCCESS") {
				var bankData = obj.bankRequests;

				for (var i = 0; i < bankData.length; i++) {

					var bankNames = bankData[i].bankName;
					var bankIds = bankData[i].bankId;

					var newOption = document.createElement("option");
					newOption.value = bankIds;
					newOption.innerHTML = bankNames;

					$("#bankName").append(newOption);
				}
			}
		},
		error : function(e) {
		}
	});

}

function validatePartnerGroupCreateFormData() {
	var flag = true;
	assigValidationType('SUBMIT');
	if (!validateMultiSelect()
			| !clientValidation('description', 'description', 'reasonerrormsg')
			| !clientValidation('name', 'partner_group_name',
					'groupnameerrormsg')) {
		clearValidationType();
		flag = false;
		return flag;
	}
	selectedPartners();
	return flag;

}

function validatePartnerGroupEditFormData() {
	var flag = true;
	assigValidationType('SUBMIT');
	if (!clientValidation('reason', 'nonmandatorycomment', 'remarksErrorDiv')
			| !validateMultiSelect()
			| !clientValidation('description', 'description', 'reasonerrormsg')
			| !clientValidation('name', 'partner_group_name',
					'groupnameerrormsg')) {
		clearValidationType();
		flag = false;
		return flag;
	}
	selectedPartners();
	return flag;

}

function selectedPartners() {
	var partners = $.map($('#partners option'), function(e) {
		return e.value;
	});
	get('partnerId').value = partners;
}

function validateMultiSelect() {
	if (ValidationRules.common_name.mandatory == true) {
		var multiEle = get('partners');
		if (multiEle.options.length <= 0) {
			errorFieldFocus('partners');
			setDiv('selectedPartners_ErrorDiv', webMessages.REQ_MANDATORY);
			return false;
		} else {
			setDiv('selectedPartners_ErrorDiv', "");
			return true;
		}
	}
	return true;
}

function editPartnerGroup(partnerGroupId) {
	get('partnerGroupId').value = partnerGroupId;
	document.forms["editPartnerGroup"].submit();
}

function showPartnerGroup(partnerGroupId) {
	get('groupId').value = partnerGroupId;
	document.forms["showPartnerGroup"].submit();
}

function changePartnerGroupStatus(id, status, statusName) {

	clearPopupDesc();
	openPopup();
	setDiv("sts", webMessages.News_status + status + '?');
	get('partnerGroupId').value = id;
	get('partnerGroupStatus').value = status;
}

function validateProgramManager() {
	var flag = true;
	if (!clientValidation('programManagerId', 'program_Manager_Id',
			'programManagerNameErrorMsgDiv')) {
		clearValidationType();
		flag = false;
		return flag;
	}
	return flag;

}

function editPartnerAccount(partnerAccountId, accountType) {
	get('partnerAccountId').value = partnerAccountId;
	get('accountType').value = accountType;
	document.forms["editPartnerAccountForm"].submit();

}

function fetchCurrencyForPM(entityId, entityType) {
	document.getElementById('currencyName').options.length = 0;
	var selectOption = document.createElement("option");
	selectOption.value = "";
	selectOption.innerHTML = "..:Select:..";
	$("#currencyName").append(selectOption);
	if (entityId == "") {
		return;
	}
	doAjaxForCurrency(entityId, entityType);
}

function doAjaxForCurrency(entityId, entityType) {
	$.ajax({
		type : "GET",
		url : "fetchCurrency?entityId=" + entityId + "&entityType="
				+ entityType,
		success : function(response) {
			var obj = JSON.parse(response);
			document.getElementById('currencyName').options.length = 0;

			var selectOption1 = document.createElement("option");
			selectOption1.innerHTML = "..:Select:..";
			selectOption1.value = "";
			$("#currencyName").append(selectOption1);

			var data = obj.accountCurrency;
			var newOption = document.createElement("option");
			newOption.value = data;
			newOption.innerHTML = data;
			$("#currencyName").append(newOption);

			$('#currencyName').val(data);
		},
		error : function(e) {
		}
	});
}

function editPartner(partnerId, managerId) {
	get('partnerId').value = partnerId;
	get('managerId').value = managerId;
	document.forms["editPartnerForm"].submit();

}