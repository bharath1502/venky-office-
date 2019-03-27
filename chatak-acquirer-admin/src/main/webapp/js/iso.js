
function getCardProgramByPmId(pmId){
	if(pmId !=null && pmId != ""){
		doAjaxToGetCardProgramByPmId(pmId);
	}
}

function doAjaxToGetCardProgramByPmId(pmId){
	var entityType = 'Program Manager';
	$.ajax({
		type : "GET",
		url : "getCardProgramByPmId?pmId=" + pmId + "&entityType=" + entityType,
		success : function(response) {
			
			if(response !=null && response != ""){
				var obj = JSON.parse(response);
				
				if (obj.errorMessage == "SUCCESS" && obj.cardProgramList != null) {
					for (var i = 0; i < obj.cardProgramList.length; i++) {
						var programManagerName = obj.cardProgramList[i].programManagerName;
						var panLow = obj.cardProgramList[i].panLow;
						var panHigh = obj.cardProgramList[i].panHigh;
						var currency = obj.cardProgramList[i].currency;
						var cardProgramId = obj.cardProgramList[i].cardProgramId;
						var recRow = '<tr id="rowId'+cardProgramId+pmId+'">'
							+'<td>'+panLow+'</td>'
							+'<td>'+panHigh+'</td>'
							+'<td>'+programManagerName+'</td>'
							+'<td>'+currency+'</td>'
							+'<td data-title="Action" ><input id="cpId'+cardProgramId+pmId+'" type="checkbox" onclick="addCardProgram('+cardProgramId+','+pmId+')"></td>'
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

function removeCardProgramFromList(pmId){
	if(pmId !=null && pmId != ""){
		doAjaxToRemoveCardProgramByPmId(pmId);
	}
}

function doAjaxToRemoveCardProgramByPmId(pmId){
	var entityType = 'Program Manager';
	$.ajax({
		type : "GET",
		url : "getCardProgramByPmId?pmId=" + pmId + "&entityType=" + entityType,
		success : function(response) {
			
			if(response !=null && response != ""){
				var obj = JSON.parse(response);
				
				if (obj.errorMessage == "SUCCESS") {
					for (var i = 0; i < obj.cardProgramList.length; i++) {
						var cardProgramId = obj.cardProgramList[i].cardProgramId;
						
						var rowId = 'rowId'+ cardProgramId+pmId;
							$('#'+rowId).remove();	
							doUnCheckedToCardProgram(cardProgramId,pmId);
					}
				}	
			}
		},
		error : function(e) {

		}
	});
}

function validateIsoMultiSelect() {
	if (ValidationRules.fee_Short_Code.mandatory == true) {
		var multiEle = get('selectedProgramManager');
		if(multiEle == null || multiEle.options.length <= 0) {
			setDiv('selectedProgramManager_ErrorDiv', webMessages.validationthisfieldismandatory);
			errorFieldFocus('selectedProgramManager');
			return false;
		}
		else
			{
			setDiv('selectedProgramManager_ErrorDiv', "");
			return true;
			}
	}
	return true;
}

function validateCreateIso(){
	var imageData = get('isoLogo');
	var flag = true;

	if (!clientValidation('isoEmailId', 'email',
					'isoEmailId_ErrorDiv')
			| !clientValidation('contactPhone', 'partner_phone',
					'isocontactphoneerrormsg')
			| !clientValidation('contactPerson', 'contact_person',
					'isocontactpersonerrormsg')
			| !clientValidation('businessEntityName', 'business_entity_name',
					'isobusinessentityerrormsg')
			| !clientValidation('extension','extension_not_mandatory','extensionerr')
			| !clientValidation('isoName', 'program_manager_name',
					'isonameerrormsg')
		    | !clientValidation('programManagerId','partner_name_dropdown','isoPmerrormsg')
			| !clientValidation('currency','contact_person','isoCurrencyerrormsg')
			| !clientValidation('bankName','companyname_not_mandatory','banknameerr')
			| !clientValidation('bankAccNum','bank_Code','bankaccerr')
			| !clientValidation('routingNumber','bank_Code','routingNumbererr')
			| !validateAddress()
			| !validateCity()
			| !validateCountry()
			| !validateState()
			| !validateZip()
			| !readPartnerLogo(imageData,'isoLogoErrorDiv')) {
		clearValidationType();
		flag = false;
		return flag;
	}
	return flag;
}

function editIso(isoId){
	get('isoId').value = isoId;
	document.forms["editIsoForm"].submit();
}

function validateUpdateIso(){
	var imageData = get('isoLogo');
	setSelectedPmAndCpId();
	var flag = true;

	if (!clientValidation('isoEmailId', 'email',
					'isoEmailId_ErrorDiv')
			| !clientValidation('contactPhone', 'partner_phone',
					'isocontactphoneerrormsg')
			| !clientValidation('contactPerson', 'contact_person',
					'isocontactpersonerrormsg')
			| !clientValidation('businessEntityName', 'business_entity_name',
					'isobusinessentityerrormsg')
			| !clientValidation('extension','extension_not_mandatory','extensionerr')
			| !clientValidation('isoName', 'program_manager_name',
					'isonameerrormsg')
			| !clientValidation('programManagerId','partner_name_dropdown','isoPmerrormsg')
			| !clientValidation('currency','contact_person','isoCurrencyerrormsg')
			| !clientValidation('bankName','companyname_not_mandatory','banknameerr')
			| !clientValidation('bankAccNum','bank_Code','bankaccerr')
			| !clientValidation('routingNumber','bank_Code','routingNumbererr')
			| !validateAddress()
			| !validateCity()
			| !validateCountry()
			| !validateState()
			| !zipCodeNotEmpty('zip')
			| !readPartnerLogo(imageData,'isoLogoErrorDiv')) {
		clearValidationType();
		flag = false;
		return flag;
	}
	return flag;
}

function buttonDisabled() {
	
	 document.getElementById('buttonCreate').value='Processing...';
	 
	 document.getElementById('buttonCreate').disabled=true;
}

function fetchCardProgramByIso(isoId){
	$.ajax({
		type : "GET",
		url : "fetchCardProgramByIso?isoId=" + isoId,
		success : function(response) {
			
			if(response !=null && response != ""){
				var obj = JSON.parse(response);
				
				if (obj.errorMessage == "SUCCESS" && obj.cardProgramRequestList != null) {
					for (var i = 0; i < obj.cardProgramRequestList.length; i++) {
						var cardProgramId = obj.cardProgramRequestList[i].cardProgramId;
						var programManagerName = obj.cardProgramRequestList[i].programManagerName;
						var panLow = obj.cardProgramList[i].panLow;
						var panHigh = obj.cardProgramList[i].panHigh;
						var currency = obj.cardProgramRequestList[i].currency;
						var cardProgramId = obj.cardProgramList[i].cardProgramId;			
						var recRow = '<tr id="rowId'+cardProgramId+programManagerId+'">'
							+'<td>'+programManagerName+'</td>'
							+'<td>'+panLow+'</td>'
							+'<td>'+panHigh+'</td>'
							+'<td>'+currency+'</td>'
							+'<td data-title="Action" ><input id="cpId'+cardProgramId+programManagerId+'" type="checkbox" onclick="addCardProgram('+cardProgramId+','+programManagerId+')"></td>'
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

 function changeIsoStatus(id, status, statusName) {
	clearPopupDesc();
	$('#programManagerDiv').popup('show');
	setDiv("sts", "Do you wish to change Role status to" + status + '?');
	get('manageProgramManagerId').value = id;
	get('manageProgramManagerStatus').value = status;

}

function fetchPmListByCurrency(currencyName){
	resetSelectedPmCp();
	if(currencyName==null || currencyName==''){
		return;
	}
	var entityType = 'Program Manager';
	$.ajax({
		type : "GET",
		url : "getAllEntityName?entityType=" + entityType + "&currencyId=" + currencyName,
		async : false,
		success : function(responseVal) {
			var obj = JSON.parse(responseVal);
			if (obj.errorCode === '00') {
				document.getElementById('programManagers').options.length = 0;
				var data = obj.responseList;

				for (var i = 0; i < data.length; i++) {
					var programManagerName = data[i].label;
					var newOption = document.createElement("option");
					newOption.value = data[i].value;
					newOption.innerHTML = programManagerName;

					$(("#" + 'programManagers')).append(newOption);
				}
			}
			$(("#" + 'programManagers')).append("");
		},
		error : function(e) {
		}
	});
	
}

function validateAddress() {
	var address1 = get('address1').value.trim();

	if (isEmpty(address1)) {
		setError(get('address1'), webMessages.pleaseEnterAddress);
		loadMsgTitleText();
		return false;
	} else if (address1.length < 5) {
		setError(get('address1'), webMessages.invalidAddressLength);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('address1'), '');
		setLable('confirmMaddress1', address1);
		return true;
	}
}

function validateSpecialCharactersISO() {
	if (!clientValidation('isoName', 'companyname_not_mandatory',
			'isonameerrormsg')
			| !clientValidation('businessEntityName',
					'companyname_not_mandatory', 'isobusinessentityerrormsg')
			| !clientValidation('contactPhone', 'mobile_optional',
					'isocontactphoneerrormsg')
			| !clientValidation('isoEmailId', 'email_Id', 'isoEmailId_ErrorDiv')) {
		return false;
	}
	return true;
}
