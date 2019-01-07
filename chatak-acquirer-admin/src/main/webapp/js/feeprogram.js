
var feeProgramNameAlreadyExist = null;

function editFeeProgram(feeProgramId) {
	get('getFeeProgramId').value = feeProgramId;
	document.forms["editFeeProgramForm"].submit();
}

function viewFeeProgram(feeProgramId) {
	get('getViewFeeProgramId').value = feeProgramId;
	document.forms["viewFeeProgramForm"].submit();
}

function deleteFeeProgram(feeProgramId) {
	get('getFeeProgramId').value = feeProgramId;
	document.forms["deleteFeeProgramForm"].submit();
}

function formatAmount(amount) {

	var value = parseFloat(amount).toFixed(2);
	if (isNaN(value)) {
		amount = '';
		return amount;
	} else {

		return parseFloat(value).toFixed(2);
	}

}

var statusflag = true;

function validateFeeProgramFormData() {
	var flag = true;
	if (!feeProgramNameMethod() | !feeprogramDescription()
			| !validateProcessor() | !validateMultiSelect()) {

		return false;

	}

	if (tableContentCounter > 0) {
		for ( var i = 1; i <= tableContentCounter; i++) {
			if (!validateValue("feeValL1" + i)
					| !validateMinimuVal("feeValL2" + i)
					| !validateMaxVal("feeValL3" + i))
				flag = false;

			if (i == tableContentCounter) {
				return flag;
			}

		}

	}

	return flag;
}

function validateFeeProgramFormDataEdit() {
	var flag = true;
	if (!feeProgramNameMethod() | !feeprogramDescription()
			| !validateProcessor() | !validateMultiSelect() | !validateStatus()) {

		return false;

	}

	if (tableContentCounter > 0) {
		for ( var i = 1; i <= tableContentCounter; i++) {
			if (!validateValue("feeValL1" + i)
					| !validateMinimuVal("feeValL2" + i)
					| !validateMaxVal("feeValL3" + i))
				flag = false;

			if (i == tableContentCounter) {
				return flag;
			}

		}

	}

	return flag;
}

function validateForm() {
	$("#feeProgramCreate").validate();
}

function feeProgramNameMethod() {
	var feeProgram = get('feeProgramName').value.trim();
	if (isEmpty(feeProgram)) {
		setError(get('feeProgramName'), webMessages.feeProgramFeeprogram);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('feeProgramName'), '');
		return true;
	}
}

function feeprogramDescription() {
	var feeProgramDescription = get('feeProgramDescription').value.trim();
	if (isEmpty(feeProgramDescription)) {
		setError(get('feeProgramDescription'),  webMessages.feeProgramfeeProgramDesc);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('feeProgramDescription'), '');
		return true;
	}
}

function validateProcessor() {
	var processor = get('processorId').value.trim();
	if (isEmpty(processor)) {
		setError(get('processorId'),  webMessages.feeProgramProcessor);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('processorId'), '');
		return true;
	}
}

function validateStatus() {
	var status = get('status').value.trim();
	if (isEmpty(status)) {
		setError(get('status'), webMessages.feeProgramStatus);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('status'), '');
		return true;
	}
}
function validateMultiSelect() {
	/* if (ValidationRules.fee_Short_Code.mandatory == true) { */
	var multiEle = get('selectedFeeShortCodes');
	if (multiEle.options.length <= 0) {
		setDiv('selectedFeeShortCodes_ErrorDiv', webMessages.feeProgramMandatory);
		loadMsgTitleText();
		return false;
	} else {
		setDiv('selectedFeeShortCodes_ErrorDiv', "");
		return true;
	}
	/*
	 * } return true;
	 */
}

function validateValue(id) {

	$("#" + id).next().children().text("");
	var flag = true;
	var val = getVal(id);
	var number = Number(val);
	if (val == "") {
		$("#" + id).next().children().text(webMessages.feeProgramValue);
		loadMsgTitleText();
		statusflag = false;
		return false;
	}

	else if (number < 0) {
		$("#" + id).next().children().text(webMessages.feeProgramPositiveNumber);
		loadMsgTitleText();
		statusflag = false;
		return false;
	} else if (!numericDecimal(val, id))
		return false;

	statusflag = true;
	return flag;

}

function validateMinimuVal(id) {

	$("#" + id).next().children().text("");
	var flag = true;
	var val = getVal(id);
	var number = Number(val);
	if (val == "") {
		$("#" + id).next().children().text(webMessages.feeProgramValue);
		loadMsgTitleText();
		statusflag = false;
		return false;
	} else if (number < 0) {
		$("#" + id).next().children().text(webMessages.feeProgramPositiveNumber);
		loadMsgTitleText();
		statusflag = false;
		return false;
	} else if (!numericDecimal(val, id))
		return false;
	statusflag = true;
	return flag;
}

function validateMaxVal(id) {

	$("#" + id).next().children().text("");
	var flag = true;
	var val = getVal(id);
	var number = Number(val);
	if (val == "") {
		$("#" + id).next().children().text(webMessages.feeProgramValue);
		loadMsgTitleText();
		statusflag = false;
		return false;
	}

	else if (number < 0) {
		$("#" + id).next().children().text(webMessages.feeProgramPositiveNumber);
		loadMsgTitleText();
		statusflag = false;
		return false;
	}

	else if (!numericDecimal(val, id))
		return false;
	statusflag = true;
	return flag;
}

function numericDecimal(data, div_id) {
	var regex = /^(?:0|[1-9]\d*)(?:\.(?!.*000)\d+)?$/;

	if (regex.test(data)) {
		$("#" + div_id).next().children().text("");
		return true;
	} else {
		$("#" + div_id).next().children().text(webMessages.feeProgramOnlyNumber);
		loadMsgTitleText();
		return false;

	}
}
function resetFeeSearch() {
	window.location.href = 'show-fee-program-search';
}

function cancelFeeProgram(){
	window.location.href = 'show-fee-program-search';
}

function resetFeeCreate() {
	window.location.href = 'show-fee-program-create';
}

function openCancelConfirmationPopup() {
	$('#my_popup1').popup("show");
}

function closeCancelConfirmationPopup() {
	$('#my_popup1').popup("hide");
}

function editPartnerFee(merchantId) {
	get('getMerchantId').value = merchantId;
	document.forms["showChatakPartnerFeeEdit"].submit();
}

function validateFeeProgramEdit(){
	var flag = false;
	if(!clientValidation('feePercentageOnly', 'amount','percentageOfTxnErrorDiv')
			| !clientValidation('flatFee', 'amount','flatFeeErrorDiv')){
		return flag;
	}else{
		flag = true;
		return flag;
	}
}

function editAcquirerFee(acquirerId){
	get('getAquirerId').value = acquirerId;
	document.forms["showChatakAquirerFeeEdit"].submit();
}

function getAcquirerFees(partnerId){
	get('partnerId').value = partnerId;
	document.forms["getAcquirerFeesForm"].submit();
}

function validateAcquirerFeeCode(){
	var flag = false;
	if(!clientValidation('acquirerFeeName', 'country','acquirerFeeNameErrorDiv')
			|!clientValidation('feePercentageOnlyAcquirer', 'amount','feePercentageOnlyAcquirerErrorDiv')
			|!clientValidation('flatFeeAcquirer', 'amount','flatFeeAcquirerErrorDiv')){
		return flag;
	}else{
		flag = true;
		return flag;
	}
}

function validateCardType($this) {
	var currentEleId = $($this).attr('id');
	var cardType = $($this).val();
	if (isEmpty(cardType)) {
		setError(get(currentEleId), webMessages.feeProgramCardType);
		loadMsgTitleText();
		return false;
	} else {
		setError(get(currentEleId), '');
		return true;
	}
}

function validateAmountValue($this,inputType) {
	var regex = /^(?:0|[1-9]\d*)(?:\.(?!.*000)\d+)?$/;
	var currentEleId = $($this).attr('id');
	var flag = true;
	var val = getVal(currentEleId);
	if (regex.test(val)) {
		setError(get(currentEleId), '');
		return true;
	} else {
		setError(get(currentEleId), webMessages.feeProgramValid +inputType);
		loadMsgTitleText();
		return false;

	}
	
}
function validateFeeProgram() {
	if (validateFeePrgmName()
			| !clientValidation('cardProgramId', 'fee_Program','cardProgramIdErrDiv')
			| !clientValidation('flatFee', 'fee','flatFeeErr')
			| !validateFeePercentValue()
			| !validateSharing()) {
		return false;
	} else {
		if (feeProgramNameAlreadyExist == true) {
			return false;
		}
	}
	return true;
}

function validateEditFeeProgram() {
	if (!clientValidation('flatFee', 'fee','flatFeeErr')
			| !validateFeePercentValue()
			| !validateSharing()) {
		return false;
	}
	return true;
}

function validateSharing(){
	if(!clientValidation('pmShare', 'fee','pmShareErr')
			| !clientValidation('isoShare', 'fee','isoShareErr')){
		return false;
	}
	if ($('#pmShare').val() >= 0 || $('#isoShare').val() >= 0) {
		var pmShare = Number($('#pmShare').val());
		var isoShare = Number($('#isoShare').val());
		var shareSum = Number(pmShare + isoShare);
    
		if (shareSum == 100) {
			setDiv('errorDiv', "");
			return true;
		} else {
			setDiv('errorDiv', webMessages.Validate_pm_iso_share);
			return false;
		}
	}
}
function validateFeePercentValue(){
	var feePercentage = Number($('#feePercentage').val());
	if(!clientValidation('feePercentage', 'fee','feePercentageErr')){
		return false;
	}
	if (feePercentage > 100) {
		setDiv('feePercentageErr', webMessages.should_not_grt_th_100);
		return false;
	}
	setDiv('feePercentageErr', '');
	return true;
}
function validateFeeAmount($this,inputType,isMandatory){
	var currentEleId = $($this).attr('id');
	var val =getVal(currentEleId);
	//var availableBal=get('availableBalance').value;
	//var regex = /^\s*-?[1-9]\d*(\.\d{2})?\s*$/;
	var regex = /^[0-9]+(\.[0-9][0-9]?)?$/;

	if (isMandatory&&isEmpty(val)) {
		setError(get(currentEleId), webMessages.feeProgramMandatory);
		loadMsgTitleText();
		return false;
	} else if (val.length>0&&regex.test(val) == false) {
		setError(get(currentEleId), inputType);
		loadMsgTitleText();
		return false;
	}
	setError(get(currentEleId), "");
}

function validateSpecifiedAccountNo($this) {
	var currentEleId = $($this).attr('id');
	var radioId = currentEleId.split('.')[0] + '.accountTypeOTH';
	
	if($("input:radio[id='"+radioId+"']").is(':checked')) {
		var accountNo = $($this).val().trim();
		if (isEmpty(accountNo)) {
			setError(get(currentEleId), webMessages.feeProgramAccountNumber);
			loadMsgTitleText();
			return false;
		} 	
		else if (!isDigit(accountNo) || accountNo == 0) {
			setError(get(currentEleId), webMessages.shouldcontainonlynumbers);
			loadMsgTitleText();
			return false;
		}
		else {
			setError(get(currentEleId), '');
			validateSpecifiedAccountNoFromCI(accountNo, currentEleId);
		}
		return true;
	} else {
		setError(get(currentEleId), '');
		return true;
	}
}

function validateSpecifiedAccountNoFromCI(accountNo, currentEleId) {
	var csrfToken = $("input[name=CSRFToken]").val();
	$.ajax({
		type : "POST",
		url : "validateFeePgmAccNo",
		async : false,
		data: { specificAccountNumber : accountNo, CSRFToken: csrfToken},
		success : function(response) {
				if(response != "true") {
					setError(get(currentEleId), webMessages.feeProgramAccountNumberNotExist);
					loadMsgTitleText();
				} else {
					setError(get(currentEleId), '');
				}
		},
		error : function(e) {
			setError(get(currentEleId), webMessages.feeProgramAccountNumberNotExist);
			loadMsgTitleText();
		}
	});
}

function resetFeeprogramInfo() {
	resetSuccessAndErrorMsg();
	get('feeProgramName').value = "";
	setError(get('feeProgramNameEr'), '');
	get('feeValueList[0].cardType').value.text = "";
	setError(get('feeValueList[0].cardTypeEr'), '');
	get('feeValueList[0].feePercentage').value.text = "";
	setError(get('feeValueList[0].feePercentageEr'), '');
	get('feeValueList[0].flatFee').value = "";
	setError(get('feeValueList[0].flatFeeEr'), '');
	get('feeValueList[0].accountTypeSYS').value = "";
	get('feeValueList[0].accountTypeOTH').value = "";
	setError(get('feeValueList[0].accountTypeOTHEr'), '');
	get('feeValueList[0].accountNumber').value = "";
	setError(get('feeValueList[0].accountNumberEr'), '');
	get('otherFee.setupFee').value = "";
	setError(get('otherFee.setupFeeEr'), '');
	get('otherFee.settlementFee').value = "";
	setError(get('otherFee.settlementFeeEr'), '');
	get('otherFee.chargeFrequency').value = "";
	setError(get('otherFee.chargeFrequencyEr'), '');
	get('otherFee.chargeBacKFee').value = "";
	setError(get('otherFee.chargeBacKFeeEr'), '');
}

function validateFeePrgmName() {
	var feeProgramName = get('feeProgramName').value.trim();
	var regex = /^[a-zA-Z0-9 .]*$/;
	if(isEmpty(feeProgramName)){
		setDiv('feeProgramNameErr',webMessages.feeProgramMandatory);
		loadMsgTitleText();
		return false;
	}else if (!regex.test(feeProgramName)) {
		setDiv('feeProgramNameErr',webMessages.cancontain_alphanumerics_dot_space);
		loadMsgTitleText();
		return false;
	} else {
		doAjaxFeeprogramNameDuplicate();
		if (feeProgramNameAlreadyExist == true) {
			return true;
		} else {
			return false;
		}
	}
}

function doAjaxFeeprogramNameDuplicate() {
	var feeProgramId = get('feeProgramName').value.trim();

	$.ajax({
		type : "GET",
		url : "uniquefeeProgramName?feeProgramId=" + feeProgramId,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorCode === '00') {
				setDiv('feeProgramNameErr','');
				setLable('confirmMfeeProgramName', 	get('feeProgramName').value.trim());
				loadMsgTitleText();
				feeProgramNameAlreadyExist = false;

			} else {
				setDiv("feeProgramNameErr", webMessages.feeProgramNameAlreadyinUse);
				feeProgramNameAlreadyExist = true;
				loadMsgTitleText();
			}
		},
		error : function(e) {
		}
	});
}

function validateFeeProgramName() {
	if (!clientValidation('feeProgramName', 'companyname_not_mandatory','feeProgramNameEr')) {
		return false;
	}
	return true;
}
