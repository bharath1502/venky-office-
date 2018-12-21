function getUserAccountDetails(merchantId, merchantCode) {
	get('getMerchantId').value = merchantId;
	get('getMerchantCode').value = merchantCode;
	document.forms["showUserAccountDetails"].submit();
}

function getUserAccountTransDetails(merchantId, merchantCode) {
	get('getMerchantId').value = merchantId;
	get('getMerchantCode').value = merchantCode;
	$('#transactionTable').hide();
	$('#showAllTrans').hide();
	$('#showDates').show();
//	document.forms["showUserAccTransDetails"].submit();
}

function submitDateRange(){
	var flag = false;
	if(!clientValidation('transFromDate', 'startDate','transFromDateErrorDiv')
			|!clientValidation('transToDate', 'endDate','transToDateErrorDiv')
			|!validateReportsDates()){
		return flag;
	}else{
	get('getMerchantId').value = getVal('getMerchantId').trim();
	get('getMerchantCode').value = getVal('getMerchantCode').trim();
	get('fromDate').value = getVal('transFromDate');
	get('toDate').value = getVal('transToDate');
	document.forms["showUserAccTransDetails"].submit();
	return true;
	}
}

function getStatementAccountDetails(merchantId, merchantCode) {
	get('getMerchantId').value = merchantId;
	get('getMerchantCode').value = merchantCode;
	document.forms["showStatementAccountDetails"].submit();
}

function getStatementTransDetails(merchantId, merchantCode) {
	get('getMerchantId').value = merchantId;
	get('getMerchantCode').value = merchantCode;
	$('#transactionTable').hide();
	$('#showAllTrans').hide();
	$('#showDates').show();
//	document.forms["showUserAccTransDetails"].submit();
}

function submitStatementDateRange(){
	var flag = false;
	if(!clientValidation('transFromDate', 'startDate','transFromDateErrorDiv')
			|!clientValidation('transToDate', 'endDate','transToDateErrorDiv')
			|!validateReportsDates()){
		return flag;
	}else{
	get('getMerchantId').value = getVal('getMerchantId').trim();
	get('getMerchantCode').value = getVal('getMerchantCode').trim();
	get('fromDate').value = getVal('transFromDate');
	get('toDate').value = getVal('transToDate');
	document.forms["showStatementTransDetails"].submit();
	return true;
	}
}

function submitGlobalExecutedDateRange(){
	var flag = false;
	if(!clientValidation('transFromDate', 'startDate','transFromDateErrorDiv')
			|!clientValidation('transToDate', 'endDate','transToDateErrorDiv')
			|!validateReportsDates()){
		return flag;
	}else{
	get('fromDate').value = getVal('transFromDate');
	get('toDate').value = getVal('transToDate');
	document.forms["all-accounts-executed-transactions"].submit();
	return true;
	}
}

function submitbankEFTDateRange(){
	var flag = false;
	if(!clientValidation('transFromDate', 'startDate','transFromDateErrorDiv')
			|!clientValidation('transToDate', 'endDate','transToDateErrorDiv')
			|!validateReportsDates()){
		return flag;
	}else{
	get('fromDate').value = getVal('transFromDate');
	get('toDate').value = getVal('transToDate');
	get('currency').value = getVal('currencyType');
	document.forms["global-bank-eft-report"].submit();
	return true;
	}
}
	
function submitManualTransferDateRange(){
	var flag = false;
	if(!clientValidation('transFromDate', 'startDate','transFromDateErrorDiv')
			|!clientValidation('transToDate', 'endDate','transToDateErrorDiv')
			|!validateReportsDates()){
		return flag;
	}else{
	return true;
	}
}

function submitRevenueGeneratedRange(){
	var flag = false;
	if(!clientValidation('transFromDate', 'startDate','transFromDateErrorDiv')
			|!clientValidation('transToDate', 'endDate','transToDateErrorDiv')
			|!validateReportsDates()){
		return flag;
	}else{
	get('fromDate').value = getVal('transFromDate');
	get('toDate').value = getVal('transToDate');
	get('currency').value = getVal('currencyType');
	get('revenueType').value = getVal('revenue');
	get('merchant_Code').value = getVal('merchantCode');
	document.forms["globalRevenueGeneratedReports"].submit();
	return true;
	}
}

function submitGlobalPendingDateRange(){
	var flag = false;
	if(!clientValidation('transFromDate', 'startDate','transFromDateErrorDiv')
			|!clientValidation('transToDate', 'endDate','transToDateErrorDiv')
			|!validateReportsDates()){
		return flag;
	}else{
	get('fromDate').value = getVal('transFromDate');
	get('toDate').value = getVal('transToDate');
	document.forms["showGlobalPendingTransactionReport"].submit();
	return true;
	}
}

function submitGlobalAllDateRange(){
	var flag = false;
	if(!clientValidation('transFromDate', 'startDate','transFromDateErrorDiv')
			|!clientValidation('transToDate', 'endDate','transToDateErrorDiv')
			|!validateReportsDates()){
		return flag;
	}else{
	get('fromDate').value = getVal('transFromDate');
	get('toDate').value = getVal('transToDate');
	document.forms["showGlobalAllTransactionReport"].submit();
	return true;
	}
}

function validateReportsDates() {
	var flag = true;
	var toDate = document.getElementById('transToDate').value;
	var fromDate = document.getElementById('transFromDate').value;

	var toDay = parseInt(toDate.split("/")[0]);
	var toMonth = parseInt(toDate.split("/")[1]);
	var toYear = parseInt(toDate.split("/")[2]);
	var newToDate = new Date(toYear, toMonth-1, toDay);

	var fromDay = parseInt(fromDate.split("/")[0]);
	var fromMonth = parseInt(fromDate.split("/")[1]);
	var fromYear = parseInt(fromDate.split("/")[2]);
	var newFromDate = new Date(fromYear, fromMonth-1, fromDay);
	
	var today = new Date();
	var todayDate = today.getDate();
	var todayMonth = today.getMonth()+1;
	var todayYear = today.getFullYear();
	var currentDate = new Date(todayYear, todayMonth-1, todayDate);
	
	if (toDate != "") {
		if (newFromDate > newToDate) {
			flag = false;
			setDiv('transFromDateErrorDiv', webMessages.reportsFromDateshouldnotbegreaterthanToDate);
			loadMsgTitleText();
		} else if (newFromDate > currentDate) {
			flag = false;
			setDiv('transFromDateErrorDiv', webMessages.reportsFromdatecannotbethefuturedate);
			loadMsgTitleText();
		} else if (newToDate > currentDate) {
			flag = false;
			setDiv('transToDateErrorDiv',
					webMessages.reportsTodatecannotbethefuturedate);
			loadMsgTitleText();
		} else  if (newFromDate == "Invalid Date") {	//If we won't select date then Value will become as "Invalid Date" String
			flag = false;
			setDiv('transFromDateErrorDiv', webMessages.validationthisfieldismandatory);
			loadMsgTitleText();
		} else {
			setDiv('transFromDateErrorDiv', "");
		}
	}
	return flag;
}

function submitSpecificEFTTransfersDateRange(){
	var flag = false;
	if(!clientValidation('transFromDate', 'startDate','transFromDateErrorDiv')
			|!clientValidation('transToDate', 'endDate','transToDateErrorDiv')
			|!validateReportsDates()){
		return flag;
	}else{
	get('fromDate').value = getVal('transFromDate');
	get('toDate').value = getVal('transToDate');
	get('currency').value = getVal('currencyType');
	get('merchant_Code').value = getVal('merchantCode');
	document.forms["processSpecificEFTTransfers"].submit();
	return true;
	}
}
function doAjaxFetchTxnIdList(transferId) {
	
	var refId=transferId;
	$.ajax({
		type : "GET",
		url : "doAjaxFetchTxnIdList?refId=" + refId,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorCode === '00') {
				openPopup();
				$('#popUpTxnResult').html(constructTxnList(obj.transactionIdsList));
			} else {
				openPopup();
				$('#popUpTxnResult').html(constructTxnListError());
			}
		},
		error : function(e) {
		}
	});
}
function closePopup() {
	$('#my_popup').popup("hide");
}
function openPopup() {

	$('#my_popup').popup("show");
}

function constructTxnList(transactionIdsList) {
	var html = '<table class="table table-striped table-bordered table-responsive table-condensed tablesorter">'
				+'<thead>'
				+'<tr>'
				+'<th>Transaction Date</th>'
				+'<th>Transaction Id</th>'
				+'<th>Transaction Amount</th>'
				+'<th>Merchant Code</th>'
				+'<th>Card Number</th>'
				+'</tr>'
				+'</thead>';
	for(var k=0; k<transactionIdsList.length; k++) {
		html += '<tr>';
		html += '<td>'+transactionIdsList[k].dateTime+'</td>';
		html += '<td>'+transactionIdsList[k].transactionId+'</td>';
		html += '<td>'+transactionIdsList[k].txnAmount+'</td>';
		html += '<td>'+transactionIdsList[k].merchantCode+'</td>';
		html += '<td>'+transactionIdsList[k].maskedCardNumber+'</td>';		
		html += '</tr>';
	}
	html += '</table>';
	return html;
}
function constructTxnListError(){

	var html = '<table class="table table-striped table-bordered table-responsive table-condensed tablesorter">'
				+'<thead>'
				+'<tr>'
				+'<th>Transaction Date</th>'
				+'<th>Transaction Id</th>'
				+'<th>Transaction Amount</th>'
				+'<th>Merchant Code</th>'
				+'<th>Card Number</th>'
				+'</tr>'
				+'</thead>';
		html += '<tr>';
		html += '<td colspan="5" class="red-error" align="center">EFT Transfer requested by the Merchant/Sub Merchant on Available Balance in the Merchant Account</td>';
		html += '</tr>';
	html += '</table>';
	return html;
}

function submitVirtualFeeLogDateRange(){
	var flag = false;
	if(!clientValidation('transFromDate', 'startDate','transFromDateErrorDiv')
			|!clientValidation('transToDate', 'endDate','transToDateErrorDiv')
			|!validateReportsDates()){
		return flag;
	}else{
	get('fromDate').value = getVal('transFromDate');
	get('toDate').value = getVal('transToDate');
	document.forms["showVirtualFeeLogReport"].submit();
	return true;
	}
}

function submitBatchFundingDateRange() {
	var flag = false;
	if (!clientValidation('transFromDate', 'startDate', 'transFromDateErrorDiv')
			| !clientValidation('transToDate', 'endDate', 'transToDateErrorDiv')
			| !validateBatchFundingReportsDates()) {
		return flag;
	} else {
		return true;
	}
}

function validateBatchFundingReportsDates() {
	var flag = true;
	var toDate = document.getElementById('transToDate').value;
	var fromDate = document.getElementById('transFromDate').value;

	var toDay = parseInt(toDate.split("/")[0]);
	var toMonth = parseInt(toDate.split("/")[1]);
	var toYear = parseInt(toDate.split("/")[2]);
	var newToDate = new Date(toYear, toMonth-1, toDay);

	var fromDay = parseInt(fromDate.split("/")[0]);
	var fromMonth = parseInt(fromDate.split("/")[1]);
	var fromYear = parseInt(fromDate.split("/")[2]);
	var newFromDate = new Date(fromYear, fromMonth-1, fromDay);

	var today = new Date();
	var todayDate = today.getDate();
	var todayMonth = today.getMonth()+1;
	var todayYear = today.getFullYear();
	var currentDate = new Date(todayYear, todayMonth-1, todayDate);

	if (toDate != "") {
		if (newFromDate > currentDate) {
			flag = false;
			setDiv('transFromDateErrorDiv',
					webMessages.reportsFromdatecannotbethefuturedate);
			loadMsgTitleText();
		}
		else if (newFromDate > newToDate) {
			flag = false;
			setDiv('transFromDateErrorDiv',
					webMessages.reportsFromDateshouldnotbegreaterthanToDate);
			loadMsgTitleText();
		} else if (newToDate > currentDate) {
			flag = false;
			setDiv('transToDateErrorDiv',
					webMessages.reportsTodatecannotbethefuturedate);
			loadMsgTitleText();
		} else if (newFromDate == "Invalid Date") { // If we won't select date
													// then Value will become as
													// "Invalid Date" String
			flag = false;
			setDiv('transFromDateErrorDiv',
					webMessages.validationthisfieldismandatory);
			loadMsgTitleText();
		} else {
			setDiv('transFromDateErrorDiv', "");
		}
	}
	return flag;
}

function fetchSubMerchants(merchantID, elementId) {
	/*if (merchantID == '') {
		clearSubMerchants(elementId);
		return;
	}*/
	getStates(merchantID, elementId);
}

function getStates(merchantID, elementId) {
	$.ajax({
		type : "GET",
		url : "getSubMerchantsByMerchantId?merchantID=" + merchantID,
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

function getTxnDetailsOnMID(merchantCode, batchID) {
	get('batchID').value = batchID;
	get('merchantCode').value = merchantCode;
	document.forms["getTxns"].submit();
}

function showISOTxns(isoId) {
	get('getISOId').value = isoId;
	var fromDate = document.getElementById("transFromDate").value;
	var toDate = document.getElementById("transToDate").value;
	get('getFromDate').value = fromDate;
	get('getToDate').value = toDate;
	document.forms["showISOFeeReport"].submit();
}