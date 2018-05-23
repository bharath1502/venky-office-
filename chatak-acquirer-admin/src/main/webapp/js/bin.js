var binNumber = null;
/*function validateStatus() {
	var status = get('status').value.trim();
	if (isEmpty(status)) {
		setDiv("statusEr", webMessages.pleaseSelectStatus);
		loadMsgTitleText();
		return false;
	} else {
		setDiv("statusEr", '');
		return true;
	}
} */

function validateBinNumber(){
	var bin = getVal('binNumber').trim();
	if (isEmpty(bin)) {
		setDiv("binNumberEr",webMessages.pleaseEnterBinNO);
		loadMsgTitleText();
		return false;
	} else if (!isDigit(bin) || bin.length < 6
			|| bin == 0) {
		setDiv("binNumberEr", webMessages.invalidBinNumber);
		loadMsgTitleText();
		return false;
	} else if (bin.charAt(parseInt("0")) == "0") {
		setDiv("binNumberEr", webMessages.bincannotstartwithZero);
		loadMsgTitleText();
		return false;
	}
	
	else 
	{
		doAjaxBinIdAvailable();
		
		if(binNumber == false)
			{
			return true;
	}
		
	else {

		return false;
	}
	}
}


function validateBinCreate(){
	var flag = false;
	if(!validateBinNumber() | !validateRadio1() |!validateRadio2() |!validateSwitchForBin()){
		return flag;
	}else{
		flag = true;
		return flag;
	}
		
		
}
function validateBinUpdate(){
	var flag = false;
	if(!validateRadio1() |!validateRadio2() |!validateSwitchForBin()){
		return flag;
	}else{
		flag = true;
		return flag;
	}
		
		
}

function editBin(binId){
	get('getBinId').value = binId;
	document.forms["editBin"].submit();
}

function viewBin(binId){
	get('getViewBinId').value = binId;
	document.forms["viewBin"].submit();
}

var binID;
function confirmDeleteBIN(merchantId) {
	$('#deletePopup').popup("show");
	binID = merchantId;
	
}

function deleteData() {
	get('getBinID').value = binID;
	document.forms["deleteBin"].submit();
}

function cancelCreateBin() {
	window.location.href = 'bin-search-show';
}

function openCancelConfirmationPopup() {

	if ((isEmpty(get('binNumber').value.trim()))
			&& (isEmpty(get('emvSupported').checked))
			&& (isEmpty(get('dccSupported').checked))
			&& (isEmpty(getVal('switchId').trim()))) {
		window.location.href = 'bin-search-show';
	}

	else {
		$('#my_popup1').popup("show");
	}
}

function closeCancelConfirmationPopup() {
	$('#my_popup1').popup("hide");
}


function loadRadioForEMV(data) {
	if (data == 0) {
		$("#noEmvSupported").prop("checked", true);
		$("#emvSupported").prop("checked", false);
	} else if (data == 1) {
		$("#noEmvSupported").prop("checked", false);
		$("#emvSupported").prop("checked", true);
	}
}

function loadRadioforDcc(data){
	if (data == 0) {
		$("#noDccSupported").prop("checked", true);
		$("#dccSupported").prop("checked", false);
	} else if (data == 1) {
		$("#noDccSupported").prop("checked", false);
		$("#dccSupported").prop("checked", true);
	}
}

function validateRadio1() {
	if (get('dccSupported').checked == false
			&& get('noDccSupported').checked == false) {
		setDiv("dccSupportedErr", webMessages.pleaseSelectOne);
		loadMsgTitleText();
		return false;
	}else{
		setDiv("dccSupportedErr", '');
		return true;
		
	}
}

function validateRadio2() {
	if (get('emvSupported').checked == false
			&& get('noEmvSupported').checked == false) {
		setDiv("emvSupportedErr",webMessages.pleaseSelectOne);
		loadMsgTitleText();
		return false;
	}else{
		setDiv("emvSupportedErr", '');
		return true;
		
	}
}

function validateSwitchForBin(){
	var switchName = getVal('switchId').trim();
	if (isEmpty(switchName)) {
		setDiv("switchNameErr",webMessages.pleaseSelectSwitch);
		loadMsgTitleText();
		return false;
	}
	
	else{
		setDiv("switchNameErr","");
		return true;
	}
}

function resetBin() {
	resetSuccessAndErrorMsg();
	get('binNumber').value = "";
	setError(get('binNumberEr'), '');
	get('dccSupported').value.text = "";
	setError(get('dccSupportedErr'), '');
	get('noDccSupported').value.text = "";
	setError(get('dccSupportedErr'), '');
	get('emvSupported').value = "";
	setError(get('emvSupportedErr'), '');
	get('noEmvSupported').value = "";
	setError(get('emvSupportedErr'), '');
	get('switchId').value = "";
	setError(get('switchNameErr'), '');
}

function doAjaxBinIdAvailable() {
	var binId = get('binNumber').value.trim();

	$.ajax({
		type : "GET",
		url : "uniqueBinId?binId=" + binId,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorCode === '00') {
				setError(get('binNumber'), '');
				setLable('confirmMbinNumber', 	get('binNumber').value.trim());
				loadMsgTitleText();
				binNumber = false;

			} else {
				setError(get('binNumber'), webMessages.binNumberAlreadyinUse);
				loadMsgTitleText();
				binNumber = true;
			}
		},
		error : function(e) {
		}
	});
}


