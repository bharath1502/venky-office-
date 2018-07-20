/**
 * System constants
 */
var sys_constants = {
		ASCII_EXTENDED : true,
};

var isMultiButtonsDisabled = false;
String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g, "");
};

function isCharacter(value) {
	var characterValue = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	for ( var i = 0; i <= value.length; i++) {
		if (characterValue.indexOf(value.charAt(i)) == -1)
			return false;
	}
	return true;
}

function isChar(value) {
	var characterValue = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	for ( var i = 0; i <= value.length; i++) {
		if (characterValue.indexOf(value.charAt(i)) == -1)
			return false;
	}
	return true;
}

function trimUserData(){
	$('input[type="text"]').each(function (){
		$(this).val($(this).val().trim());

	});
	$('input[type="password"]').each(function (){
		$(this).val($(this).val().trim());

	});
}

function get(id) {
	return document.getElementById(id);
}

function getVal(id) {
	return get(id).value;
}

function hide(id) {
	if (get(id)) {
		get(id).style.display = 'none';
	}
}

function printCustomerDetails() {
	window.print();
}

function show(id) {
	if (get(id)) {
		get(id).style.display = 'block';
	}
}

function showInline(id) {
	if (get(id)) {
		get(id).style.display = 'inline-block';
	}
}

function setValue(id, val) {
	if (get(id)) {

		console.log(id);
		console.log(val);
		get(id).value = val;
	}

}

function setPlaceholder(id, val) {
	var ele = get(id);
	if (ele) {
		ele.placeholder = val;
		ele.value = '';
	}

}

function setDiv(id, val) {
	if (get(id)) {
		get(id).innerHTML = val;
	}

}

function setElemValue(id, val) {
	if (get(id)) {
		get(id).value = val;
	}

}

function isEmpty(id) {
	if (!id || id === "" || id.length === 0)
		return true;
	else
		return false;
}

function validName(value) {
	var characterValue = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	for ( var i = 0; i <= value.length; i++) {
		if (characterValue.indexOf(value.charAt(i)) == -1)
			return false;
	}
	return true;
}

function validUserNameData(value) {
	var characterValue = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	for ( var i = 0; i <= value.length; i++) {
		if (characterValue.indexOf(value.charAt(i)) == -1)
			return false;
	}
	return true;
}

function validWalletName(id, divId, failureMessage) {
	var walletName = getVal(id);
	if (isEmpty(walletName)) {
		setDiv(divId, "&nbsp;");
		return true;
	} else if (!validName(walletName)) {
		setDiv(divId, failureMessage);
		return false;
	}
	setDiv(divId, "&nbsp;");
	return true;

}

function validWalletUserName(id, divId, failureMessage) {
	var walletName = getVal(id);
	if (isEmpty(walletName)) {
		setDiv(divId, "&nbsp;");
		return true;
	} else if (!validUserNameData(walletName)) {
		setDiv(divId, failureMessage);
		return false;
	}
	setDiv(divId, "&nbsp;");
	return true;

}

function validUserId(id, divId, failureMessage) {
	var userId = getVal(id);
	if (isEmpty(userId)) {
		setDiv(divId, "&nbsp;");
		return true;
	}

	else if (!isDigit(userId)) {
		setDiv(divId, failureMessage);
	}
	return true;
}

function validWalletMobileNumber(id, divId) {
	var val = getVal(id);
	if (isEmpty(val)) {
		setDiv(divId, "&nbsp;");
		return true;
	} else if (!isDigit(val) || val.length < 8 || val.length > 18 || val == 0) {
		setDiv(divId, message.REQ_MOBILE_NUMBER);
		return false;
	} else if (val.charAt(parseInt("0")) == "0") {

		setDiv(divId, message.IN_MOBILE_NUMBER_START);
		return false;
	}

	else {
		setDiv(divId, "&nbsp;");
		return true;
	}
}

function validWalletEmail(id, divId) {
	var flag = false;
	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var emailAddress = getVal(id);

	if (isEmpty(emailAddress)) {
		setDiv(divId, "&nbsp;");
		return true;
	}

	else if (reg.test(emailAddress) == false) {

		setDiv(divId, message.IN_EMAIL);
		return false;
	} else {

		if (alpha.indexOf(emailAddress.charAt(0)) == -1) {
			setDiv(divId, message.IN_EMAIL);
			return false;
		}

		flag = true;
		setDiv(divId, "&nbsp;");
	}

	return flag;

}

function isDigit(val) {
	var characterValue = "0123456789";
	for ( var i = 0; i <= val.length; i++) {
		if (characterValue.indexOf(val.charAt(i)) == -1)
			return false;
	}
	return true;
}

function validateToDate(divId, errorMsg) {
	var flag = true;

	var toDate = document.getElementById('startDate').value;
	if (futureDate(toDate)) {
		setDiv(divId, errorMsg);
		flag = false;
		return flag;
	} else {
		setDiv(divId, "&nbsp;");
		return flag;
	}
	setDiv(divId, "&nbsp;");
	return flag;
}

function validateEndDate(divId, errorMsg) {

	var flag = true;
	var endDate = document.getElementById('endDate').value;
	if (futureDate(endDate)) {
		setDiv(divId, errorMsg);
		flag = false;
		return flag;
	} else {
		setDiv(divId, "&nbsp;");
	}
	if (flag) {
		flag = validateDates(divId, message.IN_END_DATE_FUTURE_DATE);
		if (!flag)
			return flag;
	}
	setDiv(divId, "&nbsp;");
	return flag;
}

function validateDates(divId, errorMsg) {
	var flag = true;
	var toDate = document.getElementById('startDate').value;
	var endDate = document.getElementById('endDate').value;
	if (toDate != "") {
		toDate = toDate.split("-");
		toDate = new Date(toDate[0], toDate[1] - 1, toDate[2]).getTime();
		endDate = endDate.split("-");
		endDate = new Date(endDate[0], endDate[1] - 1, endDate[2]).getTime();
		if (toDate > endDate) {
			flag = false;
			setDiv(divId, errorMsg);
		} else {
			setDiv(divId, "&nbsp;");
		}
	}
	return flag;
}

function futureDate(idate) {

	var today = new Date().getTime();
	idate = idate.split("-");

	idate = new Date(idate[0], idate[1] - 1, idate[2]).getTime();
	return (today - idate) < 0 ? true : false;
}

function validMessage(value) {
	var characterValue = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890 .,!?@$%&_-'\n";
	for ( var i = 0; i <= value.length; i++) {
		if (characterValue.indexOf(value.charAt(i)) == -1)
			return false;
	}
	return true;
}

/**
 * Common pagination for all related screens
 */
function getPortalOnPage(pageNumber) {
	get('pageNumberId').value = pageNumber;
	document.forms["paginationForm"].submit();

}

function getPortalPrevPage(curPageNumber) {
	getPortalOnPage(parseInt(curPageNumber) - 1);
}

function getPortalNextPage(curPageNumber) {
	getPortalOnPage(parseInt(curPageNumber) + 1);
}

function hasDoubleSpace(val) {
	return (val.indexOf('  ') != -1);
}

/**
 * Function to check ASCII characters
 * 
 * @param str
 * @returns
 */
function isASCII(str) {
	var isValid = (sys_constants.ASCII_EXTENDED ? /^[\x00-\xFF]*$/
			: /^[\x00-\x7F]*$/).test(str);
	if (!isValid) {
		return validateLatinCharacters(str);
	}
	return true;
}

function isValidMinCharsAndSpecial(val) {
	var firstCharAlphaRegex = /^[A-Za-z0-9][A-Za-z0-9 -]*$/;
	return firstCharAlphaRegex.test(val);
}

function isValidChars(val) {
	var firstCharAlphaRegex = /^[A-Za-z][-\'A-Za-z0-9 ]*$/;
	return firstCharAlphaRegex.test(val);
}

function isValidNameChars(val) {
	var firstCharAlphaRegex = /^[A-Za-z][-\'&\/.()-,A-Za-z0-9 ]*$/;
	return firstCharAlphaRegex.test(val);
}

function isValidAddressChars(val) {
	var firstCharAlphaRegex = /^[A-Za-z0-9#][ -$#@\/,'.;:'&A-Za-z0-9 ]*$/;
	return firstCharAlphaRegex.test(val);
}

function isValidCityChars(val) {
	var firstCharAlphaRegex = /^[A-Za-z0-9][-,.;'&\/.()A-Za-z0-9 ]*$/;
	return firstCharAlphaRegex.test(val);
}

function isValidZipChars(val) {
	var firstCharAlphaRegex = /^[A-Za-z0-9][A-Za-z0-9 ]*$/;
	return firstCharAlphaRegex.test(val);
}

function isValidCharsExtended(val) {
	var firstCharAlphaRegex = /^[A-Za-z0-9#][A-Za-z0-9 -$#@,']*$/;
	return firstCharAlphaRegex.test(val);
}

function validateChangeAdminPassword(password, errorDiv, errorMessage,
		errormessage1) {
	// Check for the following:
	// - include one lower case
	// - one upper case
	// - one special char; any one of @#$%`!^&()\-_+=[]{};:'",.<>/?
	// - no white spaces
	// - may include numbers
	if (isEmpty(getVal(password))) {
		setDiv(errorDiv, errormessage1);
		return false;
	}
	var passwordReg = /^(?!.*[\s])((?=.*[a-z])(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%`!^&()\-_+=\[\]{};:'\",.<>\/?|\\\\*]).{6,20})/;
	var result = passwordReg.test(getVal(password));
	if (result == false) {
		setDiv(errorDiv, errorMessage);
		return false;
	}
	return result;
}

function resetErrorSuccessMessage() {

	document.getElementById("sucessDiv").innerHTML = "";
	document.getElementById("errorDiv").innerHTML = "";

}

function clearErrorMsg(divName) {
	setDiv(divName, "&nbsp;");

	resetErrorSuccessMessage();
}

function invalidWords(validMessage) {
	validMessage = validMessage.trim();
	var messageLength = validMessage.split(' ');
	for ( var i = 0; i < messageLength.length; i++) {
		if (messageLength[i].length > parseFloat(20))
			return false;

	}

	return true;
}

function validatePopupDesc() {

	var text = getVal("reason");

	// Since description is not mandatory
	// check for empty text.
	// If so return true.
	if (isEmpty(text)) {
		setDiv("popDescError_div", message.REQ_POPUP_REASON);
		return false;
	}
	if (!invalidWords(text)) {
		setDiv("popDescError_div", message.IN_POPUP_REASON_WORD_LENGTH);
		return false;
	}
	if (text.length > 250) {
		setDiv("popDescError_div", message.IN_POUP_REASON_LENGTH);
		return false;
	} else {
		setDiv("popDescError_div", "&nbsp;");
		return true;
	}

	$('#my_popup').popup('hide');
}

function validateDesc() {

	var text = getVal("description");

	// Since description is not mandatory
	// check for empty text.
	// If so return true.
	if (isEmpty(text)) {
		setDiv("descriptionErrorDiv", message.REQ_POPUP_REASON);
		return false;
	}
	if (!invalidWords(text)) {
		setDiv("descriptionErrorDiv", message.IN_POPUP_REASON_WORD_LENGTH);
		return false;
	}
	if (text.length > 250) {
		setDiv("descriptionErrorDiv", message.IN_POUP_REASON_LENGTH);
		return false;
	} else {
		setDiv("descriptionErrorDiv", "&nbsp;");
		return true;
	}

}

function clearPopupDesc() {
	get("reason").value = "";
}

function cbWrapper(data, funct) {
	if ($("#myForm", data).size() > 0)
		top.location.href = "login.htm";//redirection
	else
		funct(data);
}

function getBaseURL() {
	return ('//' + window.location.hostname + (( window.location.port)? ':' + window.location.port:''));
}

function debug(title, msgObj) {
	console.log(title + ': '+ JSON.stringify(msgObj));
}

/**
 * Ajax related functions
 */
HTTP_METHOD = {
		POST : "POST",
		GET : "GET"
};

CONTENT_TYPE = {
		APP_JSON : "application/json",
		JSON : "json"
};

var headers = {
		'Content-Type': CONTENT_TYPE.APP_JSON
};

function invokeREST(httpMethod, url, data, successCallback, failureCallback) {
	$.ajax({
		type : httpMethod,
		dataType : CONTENT_TYPE.JSON,
		url : url,
		data : data,
		success : successCallback,
		error : failureCallback
	});
}

function getValidValue(val) {
	if (!val || val == null || val == 'null') {
		return '';
	}
	return val;
}

function validateLatinCharacters(val) {
	var reg = new RegExp(sys_constants.LATIN, "i");
	if (!reg.test(val)) {
		reg = new RegExp(sys_constants.LATIN_EXT_A, "i");
		if (!reg.test(val)) {
			reg = new RegExp(sys_constants.LATIN_EXT_B, "i");
			return (reg.test(val));
		}
	}
	return true;
}

function highlightMainContent(menuId){
	$(("#"+menuId)).addClass( "active-background" );
}

function setError(ele, errorMsg) {
	get((ele.id+'Er')).innerHTML = errorMsg;
}

function setLable(id, value) {
	get(id).innerHTML = value;
}

/**
 * Common pagination for all related screens
 */
function getPortalOnPage(pageNumber) {
	get('pageNumberId').value = pageNumber;
	document.forms["paginationForm"].submit();
}

function getPortalOnPageWithRecords(pageNumber, totalRecords) {
	get('pageNumberId').value = pageNumber;
	get('totalRecordsId').value = totalRecords;
	if($('#sortProperty')) {
		var sortProperty = "";
		$('#serviceResults > thead > tr > th').each(function() {
			if($(this).attr('aria-sort') != "none") {
				var sortOrder = 0;
				if($(this).attr('aria-sort') == 'descending') {
					sortOrder = 1;
				} 
				sortProperty = $(this).attr('data-column') + "," + sortOrder;
				return false;
			}
		});
		$('#sortProperty').val(sortProperty);
	}
	document.forms["paginationForm"].submit();
}

function getPortalOnPageWithRecordsForRequest(pageNumber, totalRecords, status, transferMode) {
	get('pageNumberId').value = pageNumber;
	get('totalRecordsId').value = totalRecords;
	get('transferModePagination').value=transferMode;
	get('statusPagination').value=status;
	document.forms["paginationForm"].submit();
}

function getPortalPrevPage(curPageNumber) {
	getPortalOnPage(parseInt(curPageNumber) - 1);
}

function getPortalPrevPageWithRecords(curPageNumber, totalRecords) {
	getPortalOnPageWithRecords(parseInt(curPageNumber) - 1, totalRecords);
}

function getPortalNextPage(curPageNumber) {
	getPortalOnPage(parseInt(curPageNumber) + 1);
}

function getPortalNextPageWithRecords(curPageNumber, totalRecords) {
	getPortalOnPageWithRecords(parseInt(curPageNumber) + 1, totalRecords);
}

function getNextPageWithRecordsForRequest(curPageNumber, totalRecords, status, transferMode) {
	getPortalOnPageWithRecordsForRequest(parseInt(curPageNumber)+1, totalRecords, status, transferMode);
}

function getLastPageWithRecordsForRequest(curPageNumber, totalRecords, status, transferMode) {
	getPortalOnPageWithRecordsForRequest(parseInt(curPageNumber), totalRecords, status, transferMode);
}

function getPortalPrevPageWithRecordsForRequest(curPageNumber, totalRecords, status, transferMode) {
	getPortalOnPageWithRecordsForRequest(parseInt(curPageNumber) - 1, totalRecords, status, transferMode);
}

function formReset(id) {
	document.getElementsByName(id).reset();
}

function downloadReport(curPageNumber, type, totalRecords) {
	get('downloadPageNumberId').value = curPageNumber;
	get('downloadTypeId').value = type;
	get('totalRecords').value = totalRecords;
	if($('#totalRecordsDownload').prop('checked')== true){
		setValue('downloadAllRecords', true );
	}
	else{
		setValue('downloadAllRecords', false );
	}
	document.forms["downloadReport"].submit();
}

function numbersonly(myfield, keyevent){
	var key;
	var keychar;
	if (window.event)
		key = window.event.keyCode;
	else if (keyevent)
		key = keyevent.which;
	else
		return true;
	keychar = String.fromCharCode(key);

	// control keys
	if ((key==null) || (key==0) || (key==8) || 
			(key==9) || (key==13) || (key==27) ){
		return true;
	}else if ((("0123456789").indexOf(keychar) > -1)){
		return true;
	}else{
		return false;
	}
}

function amountValidate(el, evt) {
	var charCode = (evt.which) ? evt.which : event.keyCode;
	if (evt.which===8)
	{ 
		return;                                          
	}
	var number = el.value.split('.');
	if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	}

	var caratPos = getSelectionStart(el);
	var dotPos = el.value.indexOf(".");
	if( caratPos > dotPos && dotPos>-1 && (number[1].length > 1)){
		return false;
	}    
	return true;
}

function getRadioValue(theRadioGroup)
{
	var elements = document.getElementsByName(theRadioGroup);
	for (var i = 0, l = elements.length; i < l; i++)
	{
		if (elements[i].checked)
		{
			return elements[i].value;
		}
	}
}

function cardnumbersonly(myfield, keyevent){


	var key;
	var keychar;
	if (window.event)
		key = window.event.keyCode;
	else if (keyevent)
		key = keyevent.which;
	else
		return true;
	keychar = String.fromCharCode(key);

	// control keys
	if ((key==null) || (key==0) || (key==8) || 
			(key==9) || (key==13) || (key==27) ){
		return true;
	}else if ((("0123456789").indexOf(keychar) > -1)){
		return true;
	}else{
		return false;
	}
}

function isValidAmount(value) {
	try{
		if(isNaN(value)) {
			return false;
		}
		value = parseFloat(value);
	}catch(e){return false;}
	return true;
}

function getSelectionStart(o) {
	if (o.createTextRange) {
		var r = document.selection.createRange().duplicate();
		r.moveEnd('character', o.value.length);
		if (r.text == '') return o.value.length;
		return o.value.lastIndexOf(r.text);
	} else return o.selectionStart;
}

function validateFromAndToDates() {
	var flag = true;
	var toDate = document.getElementById('toDate').value;
	var fromDate = document.getElementById('fromDate').value;

	var toDay = parseInt(toDate.split("/")[1]);
	var toMonth = parseInt(toDate.split("/")[0]);
	var toYear = parseInt(toDate.split("/")[2]);
	var newToDate = new Date(toYear, toMonth, toDay);

	var fromDay = parseInt(fromDate.split("/")[1]);
	var fromMonth = parseInt(fromDate.split("/")[0]);
	var fromYear = parseInt(fromDate.split("/")[2]);
	var newFromDate = new Date(fromYear, fromMonth, fromDay);

	var today = new Date();
	var todayDate = today.getDate();
	var todayMonth = today.getMonth() + 1;
	var todayYear = today.getFullYear();
	var currentDate = new Date(todayYear, todayMonth, todayDate);

	if (fromDate != "" && toDate == ""){
		flag = false;
		setDiv('tranToDateErrorDiv', "Please select 'To Date'");
	} else if (toDate != "") {
		if (newFromDate > newToDate) {
			flag = false;
			setDiv('tranFromDateErrorDiv', "Should not be greater than 'To Date'");
		} else if (newFromDate > currentDate) {
			flag = false;
			setDiv('tranFromDateErrorDiv', "Can not be the future date");
		}else {
			setDiv('tranFromDateErrorDiv', "");
		}
	}
	return flag;
}

function closeTxnPopup() {
	$('#txn-popup').popup("hide");
}

function validateComment(){
	var comment=get('comment').value;
	if(isEmpty(comment)){
		setDiv('commentErr', '*Please enter your comment*')
		return false;
	}else {
		setDiv('commentErr', '')
		return true;
	}
}

function goToDashboard() {
	window.location.href = 'dash-board';
}

function downloadDashboardReport(curPageNumber, type, reportType, requestFrom) {
	if(reportType == 'processingTxn') {
		get('processingTxnDownloadPageNumberId').value = curPageNumber;
		get('processingTxnDownloadTypeId').value = type;
		get('requestFromId1').value = requestFrom;
		document.forms["downloadProcessingTxnReport"].submit();
	} else if(reportType == 'exetutedTxn') {
		get('executedTxnDownloadPageNumberId').value = curPageNumber;
		get('executedTxnDownloadTypeId').value = type;
		get('requestFromId').value = requestFrom;
		document.forms["downloadExecutedTxnReport"].submit();
	}
	else if(reportType == 'manualTxn') {	
		get('downloadManualTxnPageNumberId').value = curPageNumber;
		get('downloadManualTxnTypeId').value = type;
		get('requestManualTxnFromId').value = requestFrom;
		if($('#totalRecordsDownload').prop('checked')== true){
			setValue('downloadAllRecords', true );
		}
		else{
			setValue('downloadAllRecords', false );
		}
		document.forms["downloadDashboardReport"].submit();
	}
}

function validateRefundAmount(id) {
	var refundamnt = $('#refundAmt').text();
	var refundAmt = refundamnt.substring(refundamnt.indexOf(" ") + 1);
	if($('#'+id).val().trim().length == 0) {
		$('#'+id+'Er').text(webMessages.enterTheAmountToRefund);
		return false;
	} else if(parseFloat($('#'+id).val()) == 0.0) {
		$('#'+id+'Er').text('Enter Valid Amount To Refund.');
		return false;
	} else if((parseFloat($('#'+id).val()) * 100) > (parseFloat(refundAmt) * 100)) {
		$('#'+id+'Er').text('Entered Amount Exceeds Refund Amount.');
		return false;
	} else {
		$('#'+id+'Er').text('');
	}
	return true;

}

$(document).ready(function() {

	$('body').on('click','.txn-id',function() {
		var txnObjString = $(this).closest('tr').attr('data-txn-obj');
		var txnObj = JSON.parse(txnObjString);
		var keys = Object.keys(txnObj);
		var deviceLocalTxnTime = txnObj.deviceLocalTxnTime;
		var timeZoneOffset = txnObj.timeZoneOffset;
		if(null == deviceLocalTxnTime || "" == deviceLocalTxnTime || deviceLocalTxnTime == undefined){
			 deviceLocalTxnTime ="";
		}
		if(null == timeZoneOffset || "" == timeZoneOffset || timeZoneOffset == undefined){
			timeZoneOffset = "";
		}
		var popupData = "<div><span class='glyphicon glyphicon-remove' onclick='closeTxnPopup()'></span><span align='center'><h2>"+webMessages.transactionsDetails+"</h2></span><hr/></div><div class='row'><div class='col-sm-6'>";

		popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.transactionsType+" :</b></label><span class='col-sm-6'> " + txnObj.transaction_type.toUpperCase() + " </span></div>";
		popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.authsId+" :</b></label><span class='col-sm-6'> " + txnObj.auth_id + " </span></div>";
		popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.gatewayTxnID+" :</b></label><span class='col-sm-6'> " + txnObj.transactionId + " </span></div>";
		popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.RefsTransactionId+" :</b></label><span class='col-sm-6'> " + txnObj.ref_transaction_id + " </span></div>";
		popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.terminalsId+" :</b></label><span class='col-sm-6'> " + txnObj.terminal_id + " </span></div>";
		popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.invoiceNo+" :</b></label><span class='col-sm-6'> " + txnObj.invoice_number + " </span></div>";
		popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.maskedCardNo+" :</b></label><span class='col-sm-6'> " + txnObj.maskCardNumber + " </span></div>";
		popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.merchantsCode+" :</b></label><span class='col-sm-6'> " + txnObj.merchant_code + " </span></div>";
		popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.merchantProcessor+" :</b></label><span class='col-sm-6'> " + txnObj.processor + " </span></div>";
		popupData += "</div><div class='col-sm-6'>";

		popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.systemTxnTime+" :</b></label><span class='col-sm-6'> " + txnObj.transactionDate + " </span></div>";
		popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.deviceLocalTxnTime+" :</b></label><span class='col-sm-6'> " + deviceLocalTxnTime +" "+ timeZoneOffset + " </span></div>";
		popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.merchantsAmount+" :</b></label><span class='col-sm-6'> " + txnObj.transactionAmount + " </span></div>";
		popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.feesAmount+" :</b></label><span class='col-sm-6'> " + txnObj.fee_amount + " </span></div>";
		popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.transactionTotalAmount+" :</b></label><span class='col-sm-6'> " + txnObj.txn_total_amount + " </span></div>";
		popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.acquirerTransactionMode+" :</b></label><span class='col-sm-6'> " + txnObj.acqTxnMode.toUpperCase() + " </span></div>";
		popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.acquirerChannel+" :</b></label><span class='col-sm-6'> " + txnObj.acqChannel.toUpperCase() + " </span></div>";
		popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.statusMessages+" :</b></label><span class='col-sm-6'> " + txnObj.status + " </span></div>";
		popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.merchantsSettlementsStatus+" :</b></label><span class='col-sm-6'> " + txnObj.merchantSettlementStatus + " </span></div>";
		popupData += "</div></div><div><input class='form-control button pull-right' value='OK' onclick='closeTxnPopup()' type='button'></div>";

		$('#txn-popup').empty();
		$('#txn-popup').append(popupData);
		$('#txn-popup').popup("show");
	});

	$('body').on('click', '.t-txn-id', function() {
		var csrfToken = $("input[name=CSRFToken]").val();
		$.ajax({
			type : "POST",
			url : "get-transaction-popup",
			data: {accountTransactionId: $(this).text().trim(), CSRFToken: csrfToken},
			success : function(response) {

				if(undefined != response) {

					var popupData = "<div><span class='glyphicon glyphicon-remove' onclick='closeTxnPopup()'></span><span style='text-align:center;'><h2>"+webMessages.transactionsDetails+"</h2></span><hr/></div><div class='row'>";
					txnObj = response;
					if(response.dtoType == "TXN_CC") {
						var txnAmtLabel = "Merchant Amount :";
						if(txnObj.merchantType == 'SubMerchant') {
							txnAmtLabel = "Sub-Merchant Amount :";
						}
						var deviceLocalTxnTime = txnObj.deviceLocalTxnTime;
						var timeZoneOffset = txnObj.timeZoneOffset;
						if(null == deviceLocalTxnTime || "" == deviceLocalTxnTime || deviceLocalTxnTime == undefined){
							 deviceLocalTxnTime ="";
						}
						if(null == timeZoneOffset || "" == timeZoneOffset || timeZoneOffset == undefined){
							timeZoneOffset = "";
						}
						popupData += "<div class='col-sm-6'>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.transactionsType+" :</b></label><span class='col-sm-6'> " + txnObj.transaction_type.toUpperCase() + " </span></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.authsId+" :</b></label><span class='col-sm-6'> " + txnObj.auth_id + " </span></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.accountsTransactionId+" :</b></label><span class='col-sm-6'> " + txnObj.accountTransactionId + " </span></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.gatewayTxnID+" :</b></label><span class='col-sm-6'> " + txnObj.transactionId + " </span></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.RefsTransactionId+" :</b></label><span class='col-sm-6'> " + txnObj.ref_transaction_id + " </span></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.terminalsId+" :</b></label><span class='col-sm-6'> " + txnObj.terminal_id + " </span></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.invoiceNo+" :</b></label><span class='col-sm-6'> " + txnObj.invoice_number + " </span></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.cardsHolderName+" :</b></label><span class='col-sm-6'> " + txnObj.cardHolderName + " </span></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.maskedCardNo+" :</b></label><span class='col-sm-6'> " + txnObj.maskCardNumber + " </span></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.merchantsName+" :</b></label><span class='col-sm-6'> " + txnObj.merchantBusinessName + " </span></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.merchantsCode+" :</b></label><span class='col-sm-6'> " + txnObj.merchant_code + " </span></div>";

						popupData += "</div><div class='col-sm-6'>";

						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.systemTxnTime+" :</b></label><span class='col-sm-6'> " + txnObj.transactionDate + " </span></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.deviceLocalTxnTime+" :</b></label><span class='col-sm-6'> " + deviceLocalTxnTime +" "+ timeZoneOffset + " </span></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.merchantsAmount+" :</b></label><span class='col-sm-6'> " + txnObj.transactionAmount + " </span></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.feesAmount+" :</b></label><span class='col-sm-6'> " + txnObj.fee_amount + " </span></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.transactionTotalAmount+" :</b></label><span class='col-sm-6'> " + txnObj.txn_total_amount + " </span></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.merchantProcessor+" :</b></label><span class='col-sm-6'> " + txnObj.processor + " </span></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.acquirerTransactionMode+" :</b></label><span class='col-sm-6'> " + txnObj.acqTxnMode.toUpperCase() + " </span></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.acquirerChannel+" :</b></label><span class='col-sm-6'> " + txnObj.acqChannel.toUpperCase() + " </span></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.statusMessages+" :</b></label><span class='col-sm-6'> " + txnObj.statusMessage + " </span></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>"+webMessages.merchantsSettlementsStatus+" :</b></label><span class='col-sm-6'> " + txnObj.merchantSettlementStatus + " </span></div>";

					}
					else if(response.dtoType == "ACCOUNT_DEBIT" || response.dtoType == "TXN_ACCOUNT") {
						popupData += "<div class='col-sm-12'>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>From Account :</b></label><span class='col-sm-6'> " + txnObj.toAccount + " </span></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>To Account :</b></label><span class='col-sm-6'> " + txnObj.fromAccount + " </span></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>Transaction Amount :</b></label><span class='col-sm-6'> " + txnObj.transactionAmount + " </span></div>";
					}
					else if(response.dtoType == "TXN_MANUAL_CREDIT" || response.dtoType == "TXN_ACCOUNT") {
						popupData += "<div class='col-sm-12'>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>To Account :</b></label><span class='col-sm-6'> " + txnObj.toAccount + " </span></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>Transaction Amount :</b></label><span class='col-sm-6'> " + txnObj.transactionAmount + " </span></div>";
					} else if(response.dtoType == "TXN_MANUAL_DEBIT" || response.dtoType == "TXN_EFT") {
						popupData += "<div class='col-sm-12'>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>From Account :</b></label><span class='col-sm-6'> " + txnObj.fromAccount + " </span></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-6'><b>Transaction Amount :</b></label><span class='col-sm-6'> " + txnObj.transactionAmount + " </span></div>";
					} else if(response.dtoType == "TXN_UNKNOWN") {
						popupData += "<div class='col-sm-12'>";
						popupData += "<div class='col-sm-12'><label class='col-sm-12'><b>Unable To Fetch Transaction Details.</b></div>";
						popupData += "<div class='col-sm-12'><label class='col-sm-12'></div>";
					} else {
						window.location.href = 'session-invalid';
					}

					popupData += "</div></div><div><input class='form-control button pull-right' value='OK' onclick='closeTxnPopup()' type='button'></div>";

					$('#txn-popup').empty();
					$('#txn-popup').append(popupData);
					$('#txn-popup').popup("show");
				}

			},
			error : function(e) {
			}
		});
	});
});

function showCurrentTime(serverTimeStamp){

	var x = document.getElementById("time");
	var date = new Date();
	var year=date.getFullYear();
	var month=(date.getMonth() + 1);
	var hours=date.getHours();
	var mins=date.getMinutes();
	var sec=date.getSeconds();
	var day=date.getDate();

	if(month <= 9){
		month="0"+month;
	}

	if(day <= 9){
		day="0"+day;
	}

	if(mins <= 9){
		mins="0"+mins;
	}

	if(sec <= 9){
		sec="0"+sec;
	}

	var ampm = hours >= 12 ? 'PM' : 'AM';
	hours = hours % 12;
	hours = hours ? hours : 12;

	if(hours <= 9){
		hours="0"+hours;
	}

	var dateTimeString = hours + ":" + mins + ":"+ sec + " " + ampm + " - " + month  + "/" + day + "/" +year  ;
	x.innerHTML = webMessages.theCurrentTime+": " + dateTimeString;
	setTimeout('showCurrentTime(serverTimeStamp)', 1000);
}

function validateSpace($this) {
	if (($($this).val().length) == 0 && event.keyCode == 32) {

		event.returnValue = false;
		return false;
	}
}

function generalZipCode(){

	var e = window.event;
	var valid = (e.which >= 48 && e.which <= 57) || (e.which >= 65 && e.which <= 90) || (e.which >= 97 && e.which <= 122)||(e.which == 32);
	if(!valid)
	{ 
		event.preventDefault();
	}
}

function zipCodeNotEmpty(id) {
	var pin = getVal(id);
	if (isEmpty(pin)) {
		setError(get(id), webMessages.thisfieldismandatory);
		loadMsgTitleText();
		return false;
	}else if ((pin.length < 3) || (pin.length > 7)) {
		setError(get(id), webMessages.invalidZipcode);
		loadMsgTitleText();
		return false;
	}
	else {
		setError(get(id), '');
		return true;
	}
}

function getUserLocale() {
	var cookieVal = $.cookie('chatakACQCookie');
	if(typeof cookieVal != 'undefined' && cookieVal != null && cookieVal != '') {
		return cookieVal;
	} else {
		return 'en';
	}
}

function setUserLocale(selectedValue) {
	$.removeCookie('chatakACQCookie', { path: '/' });
	$.cookie("chatakACQCookie", selectedValue, { path: '/' });
	location.reload();
	document.forms["login"].submit();
};

function closePopup() {
	$('#deletePopup').popup("hide");
}

function disableRightClick(data){
	if(data == true){
		return true;
	}else{
		window.oncontextmenu = function () {
			alert(webMessages.RIGHT_CLICK_NOT_ALLOWED);
			return false;
	 };
	}
}
