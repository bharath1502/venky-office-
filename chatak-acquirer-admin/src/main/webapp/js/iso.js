
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
						var cardProgramId = obj.cardProgramList[i].cardProgramId;
						var cardProgramName = obj.cardProgramList[i].cardProgramName;
						var partnerName = obj.cardProgramList[i].partnerName;
						var iin = obj.cardProgramList[i].iin;
						var iinExt = obj.cardProgramList[i].iinExt;
						var partnerIIN = obj.cardProgramList[i].partnerCode;
						var programManagerName = obj.cardProgramList[i].programManagerName;
						var currency = obj.cardProgramList[i].currency;
						
						var recRow = '<tr id="rowId'+cardProgramId+'">'
							+'<td>'+partnerName+'</td>'
							+'<td>'+cardProgramName+'</td>'
							+'<td>'+iin+'</td>'
							+'<td>'+partnerIIN+'</td>'
							+'<td>'+iinExt+'</td>'
							+'<td>'+programManagerName+'</td>'
							+'<td>'+currency+'</td>'
							+'<td data-title="Action" ><input id="cpId'+cardProgramId+'" type="checkbox" onclick="addCardProgram('+cardProgramId+')"></td>'
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
						
						var rowId = 'rowId'+ cardProgramId;
							$('#'+rowId).remove();	
							doUnCheckedToCardProgram(cardProgramId);
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
	var flag = true;

	if (!clientValidation('isoEmailId', 'email',
					'isoEmailId_ErrorDiv')
			| !clientValidation('contactPhone', 'partner_phone',
					'isocontactphoneerrormsg')
			| !clientValidation('contactPerson', 'contact_person',
					'isocontactpersonerrormsg')
			| !clientValidation('businessEntityName', 'business_entity_name',
					'isobusinessentityerrormsg')
			| !clientValidation('isoName', 'program_manager_name',
					'isonameerrormsg')
			| !validateIsoMultiSelect()
			| !clientValidation('currency','contact_person','isoCurrencyerrormsg')
			| !validateAddress()
			| !validateCity()
			| !validateCountry()
			| !validateState()
			| !validateZip()) {
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
			| !clientValidation('isoName', 'program_manager_name',
					'isonameerrormsg')
			| !validateIsoMultiSelect()
			| !clientValidation('currency','contact_person','isoCurrencyerrormsg')
			| !validateAddress()
			| !validateCity()
			| !validateCountry()
			| !validateState()
			| !validateZip()) {
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
						var cardProgramName = obj.cardProgramRequestList[i].cardProgramName;
						var partnerName = obj.cardProgramRequestList[i].partnerName;
						var iin = obj.cardProgramRequestList[i].iin;
						var iinExt = obj.cardProgramRequestList[i].iinExt;
						var partnerIIN = obj.cardProgramRequestList[i].partnerCode;
						var programManagerName = obj.cardProgramRequestList[i].programManagerName;
						var currency = obj.cardProgramRequestList[i].currency;
						
						var recRow = '<tr id="rowId'+cardProgramId+'">'
							+'<td>'+partnerName+'</td>'
							+'<td>'+cardProgramName+'</td>'
							+'<td>'+iin+'</td>'
							+'<td>'+partnerIIN+'</td>'
							+'<td>'+iinExt+'</td>'
							+'<td>'+programManagerName+'</td>'
							+'<td>'+currency+'</td>'
							+'<td data-title="Action" ><input id="cpId'+cardProgramId+'" type="checkbox" onclick="addCardProgram('+cardProgramId+')"></td>'
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