C_MERCHANT_MSG = {
		
};
   var slideDownFlag = 0;
function doAjaxFetchTransactionForAdjust() {

	var refId=get('refNumberDiv').value.trim();
	var txnType='sale';
	$.ajax({
		type : "GET",
		url : "getTransaction?refId=" + refId+"&txnType="+txnType,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorCode === '00') {
				get('invoiceNumberDiv1').value = zeroFill(obj.invoiceNumber, 6);
				get('cardNumberDiv1').value = obj.cardNum;
				get('authNumberDiv1').value = obj.authId;
				get('subTotalDiv').value = parseFloat(obj.subTotal).toFixed(2);
				
				get('txnRefNum').value =obj.txnRefNum;
				get('expDate').value = obj.expDate;
				get('totalAmtDiv').value =  parseFloat(obj.txnAmount).toFixed(2);
				$(".fetch-content").slideDown();
				$("#refNumberDiv").attr("readOnly",true);
				setDiv("errorDescDiv", "");
			} else {
				setDiv("errorDescDiv", webMessages.virtualTerminalTransactionDataFound);
				loadMsgTitleText();
			}
		},
		error : function(e) {
		}
	});
}

function doAjaxFetchTransactionForVoid() {
	var refId=get('refNumberDiv').value.trim();
	var merchantId=get('merchantIdDiv').value.trim();
	var txnType='sale';
	$.ajax({
		type : "GET",
		url : "getTransaction?refId=" + refId+"&txnType="+txnType+"&merchantId="+merchantId,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorCode === '00' && obj.settlementStatus=='Pending') {
				get('invoiceNumberDiv1').value = zeroFill(obj.invoiceNumber, 6);
				get('cardNumberDiv1').value = obj.cardNumMasked;
				get('cardNumPlain').value = obj.cardNum;

				get('authNumberDiv1').value = obj.authId;
				get('expDateDiv').value = obj.expDate;
				get('subTotalDiv').value = parseFloat(obj.subTotal).toFixed(2);
				get('tipAmountDiv').value = obj.tipAmount;
				get('shippingAmtDiv').value = obj.shippingAmt;
				get('totalAmtDiv').value =  parseFloat(obj.txnAmount).toFixed(2);
				get('feeAmountDiv').value = parseFloat(obj.feeAmount).toFixed(2);
				if(obj.cardHolderName === undefined){
					get('cardHolderNameDiv').value = "";
				}else{
					get('cardHolderNameDiv').value = obj.cardHolderName;
				}
				get('streetDiv').value = "";
				get('cityDiv').value = "";
				get('zipcodeDiv').value = "";
				get('txnRefNumberDiv').value = obj.txnRefNum;
				get('cgRefNumberDiv').value=obj.cgRefNumber;
				get('merchantIdHDiv').value=obj.merchantId;

				$(".fetch-content").slideDown();
				$("#refNumberDiv").attr("readOnly",true);
				setDiv("errorDescDiv", "");
			} else if(obj.settlementStatus == 'Executed') { 
				setDiv("errorDescDiv", webMessages.virtualTerminalCantVoidExecutedTransaction);
				loadMsgTitleText();
			} else {
				setDiv("errorDescDiv", webMessages.virtualTerminalAlreadyVoided);
				loadMsgTitleText();
			}
		},
		error : function(e) {
		}
	});
}

/**
 * 
 */
function doAjaxFetchTransactionForRefund() {
	var refId=get('refNumberDiv').value.trim();
	var merchantId=get('merchantIdDiv').value.trim();
	var txnType='sale';
	$.ajax({
		type : "GET",
		url : "getTransaction?refId=" + refId+"&txnType="+txnType+"&merchantId="+merchantId,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorCode === '00' && obj.settlementStatus !== 'Rejected') {
				get('invoiceNumberDiv1').value = zeroFill(obj.invoiceNumber, 6);
				get('cardNumberDiv1').value = obj.cardNumMasked;
				get('cardNumPlain').value = obj.cardNum;
				get('authNumberDiv1').value = obj.authId;
				get('expDateDiv').value = obj.expDate;
				get('subTotalDiv').value = parseFloat(obj.subTotal).toFixed(2);
				get('tipAmountDiv').value = obj.tipAmount;
				get('shippingAmtDiv').value = obj.shippingAmt;
				get('totalAmtDiv').value = parseFloat(obj.txnAmount).toFixed(2);
				get('feeAmountDiv').value = parseFloat(obj.feeAmount).toFixed(2);
				if(obj.cardHolderName === undefined){
					get('cardHolderNameDiv').value = "";
				}else{
					get('cardHolderNameDiv').value = obj.cardHolderName;
				}
				get('streetDiv').value = "";
				get('cityDiv').value = "";
				get('zipcodeDiv').value = "";
				get('refNumberDiv').value = obj.txnRefNum;
				get('cgRefNumberDiv').value=obj.cgRefNumber;
				get('merchantIdDiv').value=obj.merchantId;
				$(".fetch-content").slideDown();
				$("#refNumberDiv").attr("readOnly",true);
				$("#merchantIdDiv").attr("readOnly",true);
				setDiv("errorDescDiv", "");
			} else {
				setDiv("errorDescDiv", webMessages.virtualTerminalAlreadyRefunded);
				loadMsgTitleText();
			}
		},
		error : function(e) {
		}
	});
}

function doAjaxFetchTransactionForCapture() {
	var refId=get('refNumberDiv').value.trim();
	var txnType='auth';
	$.ajax({
		type : "GET",
		url : "getTransaction?refId=" + refId+"&txnType="+txnType,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorCode === '00') {
				get('invoiceNumberDiv1').value = zeroFill(obj.invoiceNumber, 6);
				get('cardNumberDiv1').value = obj.cardNum;
				get('authNumberDiv1').value = obj.authId;
				get('expDateDiv').value = obj.expDate;
				get('subTotalDiv').value = parseFloat(obj.subTotal).toFixed(2);
				get('tipAmountDiv').value = obj.tipAmount;
				get('shippingAmtDiv').value = obj.shippingAmt;
				get('totalAmtDiv').value = parseFloat(obj.txnAmount).toFixed(2);
				get('feeAmountDiv').value = parseFloat(obj.feeAmount).toFixed(2);
				get('cardHolderNameDiv').value = obj.cardHolderName;
				get('streetDiv').value = obj.street;
				get('cityDiv').value = obj.city;
				get('zipcodeDiv').value = obj.zip;
				get('txnRefNumberDiv').value = obj.txnRefNum;
				get('cgRefNumberDiv').value=obj.cgRefNumber;
				$(".fetch-content").slideDown();
				$("#refNumberDiv").attr("readOnly",true);
				setDiv("errorDescDiv", "");
			} else {
				setDiv("errorDescDiv", webMessages.virtualTerminalAlreadyLookupDone);
				loadMsgTitleText();
			}
		},
		error : function(e) {
		}
	});
}

function doAdd() {

	var subTot = get('subTotalDiv').value ? get('subTotalDiv').value : 0;
	var tipAmt = get('tipAmountDiv').value ? get('tipAmountDiv').value : 0;
	var taxAmt = get('taxAmtDiv').value ? get('taxAmtDiv').value : 0;
	var shipAmp = get('shippingAmtDiv').value ? get('shippingAmtDiv').value : 0;

	var total = parseFloat(subTot) + parseFloat(tipAmt) + parseFloat(taxAmt)
			+ parseFloat(shipAmp);
	if (parseFloat(total).toFixed(2) == 'NaN') {
		get('totalAmtDiv').value = '';
	} else {
		get('totalAmtDiv').value = parseFloat(total).toFixed(2);
	}
}

function doRefundAdd() {

	var subTot = get('subTotalDiv').value ? get('subTotalDiv').value : 0;
	var feeAmp = get('feeAmountDiv').value ? get('feeAmountDiv').value : 0;
	var total = parseFloat(subTot) + parseFloat(feeAmp);
	if (parseFloat(total).toFixed(2) == 'NaN') {
		get('totalAmtDiv').value = '';
	} else {
		get('totalAmtDiv').value = parseFloat(total).toFixed(2);
	}
}

function doAddAdjust() {

	var subTot = get('subTotalDiv').value ? get('subTotalDiv').value : 0;
	var tipAmt = get('tipAmountDiv').value ? get('tipAmountDiv').value : 0;

	var total = parseFloat(subTot).toFixed(2) + parseFloat(tipAmt).toFixed(2);

	get('totalAmtDiv').value = parseFloat(total).toFixed(2);
}

function validateExpiryDate() {
	var today = new Date();
	var thisyear = today.getFullYear();
	var thismonth = parseInt(today.getMonth()) + 1;
	var month = get('monthdropdown').value;
	var year = get('yeardropdown').value;
	if (isEmpty(month) && isEmpty(year)) {
		setDiv("expiryDateErr", webMessages.virtualTerminalNotEmpty);
		loadMsgTitleText();
		return false;
	} else if (year > thisyear) {
		setDiv("expiryDateErr", "");
		return true;
	} else if ((year == thisyear)) {
		if ((month < thismonth)) {
			setDiv("expiryDateErr", webMessages.virtualTerminalInvalidDate);
			loadMsgTitleText();
			return false;
		}
		setDiv("expiryDateErr", "");
		return true;
	} else {
		setDiv("expiryDateErr", "");
		return true;
	}
}

function validInvoiceNumber(id,divId) {
	var val =getVal(id);
	var len = val.length;
	if (isEmpty(val)) {
		setDiv(divId, webMessages.virtualTerminalInvoiceNumber);
		loadMsgTitleText();
		return false;
	} else if (!isDigit(val) || len < 4 || len > 16) {
		setDiv(divId, webMessages.virtualTerminalNumericUpto16);
		loadMsgTitleText();
		return false;
	} else {
		setDiv(divId, "");
		return true;
	}
}

function validRefNumber(id,divId) {
	var val =getVal(id);
	var len = val.length;
	if (isEmpty(val)) {
		setDiv(divId, webMessages.virtualTerminalTransactionId);
		loadMsgTitleText();
		return false;
	} else if (!isDigit(val) || len < 4 || len > 16) {
		setDiv(divId, webMessages.virtualTerminalNumericUpto16);
		loadMsgTitleText();
		return false;
	} else {
		setDiv(divId, "");
		return true;
	}
}

function validCardNumber(id,divId) {
	var val =getVal(id);
	var len = val.length;
	if (isEmpty(val)) {
		setDiv(divId, webMessages.virtualTerminalCardNumber);
		loadMsgTitleText();
		return false;
	} else if (!isDigit(val) || len != 19) {
		setDiv(divId, webMessages.virtualTerminalNumericUpto19);
		loadMsgTitleText();
		return false;
	} else {
		setDiv(divId, "");
		return true;
	}
}

function validAuthNumber(id,divId) {
	var val =getVal(id);
	var len = val.length;
	if (isEmpty(val)) {
		setDiv(divId, webMessages.virtualTerminalNotEmpty);
		loadMsgTitleText();
		return false;
	} else if (!isDigit(val) || len < 4 || len > 19) {
		setDiv(divId, webMessages.virtualTerminalNumericUpto16);
		loadMsgTitleText();
		return false;
	} else {
		setDiv(divId, "");
		return true;
	}
}

function validCv2() {
	var val =getVal("cv2Div");
	var len = val.length;
	if (isEmpty(val)) {
		setDiv("cv2ErrorDiv", webMessages. virtualTerminalCV2);
		loadMsgTitleText();
		return false;
	} else if (!isDigit(val) || (len>4)||(len<3)) {
		setDiv("cv2ErrorDiv", webMessages.virtualTerminalInvalidCV2);
		loadMsgTitleText();
		return false;
	} else {
		setDiv("cv2ErrorDiv", "");
		return true;
	}
}

function validCardHolderName() {
	var val =getVal("cardHolderNameDiv");
	if (isEmpty(val)) {
		setDiv("cardHolderNameErrorDiv", webMessages.virtualTerminalCardHolderName);
		loadMsgTitleText();
		return false;
	} else if (!isCharacter(val)) {
		setDiv("cardHolderNameErrorDiv", webMessages.alphaNumericsOnly);
		loadMsgTitleText();
		return false;
	} else {
		setDiv("cardHolderNameErrorDiv", "");
		return true;
	}
}

function validAmount(id,divId) {
	var val =getVal(id);
	var regex = /^[0-9]+(\.[0-9][0-9]?)?$/;
	if (isEmpty(val)) {
	setDiv(divId, webMessages.virtualTerminalAmount);
	loadMsgTitleText();
		return false;
	} else if (regex.test(val) == false) {
	setDiv(divId, webMessages.virtualTerminalValidData);
	loadMsgTitleText();
		return false;
	} else {
	setDiv(divId, "");
		return true;
	}
}

function validStreet() {
	var val = getVal("streetDiv");
	var regex = /^[A-Za-z0-9,-._\/\s#]{1,60}$/;
	console.log(val);
	if (val === null) {
		setDiv("streetErrorDiv", webMessages.virtualTerminalStreet);
		loadMsgTitleText();
		return false;
	}
	if (isEmpty(val)) {
		setDiv("streetErrorDiv", webMessages.virtualTerminalStreet);
		loadMsgTitleText();
		return false;
	}else if (!regex.test(val)) {
		setDiv("streetErrorDiv", webMessages.invalidStreet);
		loadMsgTitleText();
		return false;
	} else {
		setDiv("streetErrorDiv", "");
		return true;
	}
}
function validZipcode() {
	var val =getVal("zipcodeDiv");
	var len = val.length;
	if(null == val){
		setDiv("zipcodeErrorDiv", webMessages.virtualTerminalZipCode);
		loadMsgTitleText();
		return false;
	}
	if (isEmpty(val)) {
		setDiv("zipcodeErrorDiv", webMessages.virtualTerminalZipCode);
		loadMsgTitleText();
		return false;
	} else if (!isCharacter(val) || len < 3 || len > 8) {
		setDiv("zipcodeErrorDiv", webMessages.virtualTerminalAlphaNumericUpto8);
		loadMsgTitleText();
		return false;
	} else {
		setDiv("zipcodeErrorDiv", "");
		return true;
	}
}

function validCity() {
	var val =getVal("cityDiv");
	var regex = /^[a-zA-Z0-9#\-\s]+$/;
	if(null===val){
		setDiv("streetErrorDiv", webMessages.virtualTerminalCity);
		loadMsgTitleText();
		return false;
	}
	if (isEmpty(val)) {
		setDiv("cityErrorDiv", webMessages.virtualTerminalCity);
		loadMsgTitleText();
		return false;
	} else if (regex.test(val) == false) {
		setDiv("cityErrorDiv", webMessages.invalidCity);
		loadMsgTitleText();
		return false;
	} else {
		setDiv("cityErrorDiv", "");
		return true;
	}
}


function validateAuth() {
	if (!validCardNumber('cardNumberDiv','cardNumberErrorDiv') | !validCv2() | !validCardHolderName() | !validAmount('subTotalDiv','subTotalErrorDiv') | 
	!validAmount('totalAmtDiv','totalAmtErrorDiv') | !validStreet() | !validCity() | !validZipcode() | !validInvoiceNumber('invoiceNumberDiv','invoiceNumberErrorDiv')
	|!validateDesc()|!validState()|!clientValidation('feeAmountDiv', 'fee_amount','feeAmountErrorDiv')|!validMerchantCode('merchantIdDiv','merchantIdErrorDiv')) {
		return false;
	} else {
		return true;
	}
}

function resetAuth(monthfield, yearfield){	
	window.location.href = 'virtual-terminal-sale';
}

function validatePreAuth() {
	if (!validCardNumber('cardNumberDiv1','cardNumberErrorDiv1') | !validInvoiceNumber('invoiceNumberDiv1','invoiceNumberErrorDiv1') | 
	!validCardNumber('cardNumberDiv1','cardNumberErrorDiv1') | !validAuthNumber('authNumberDiv1','authNumberErrorDiv1') | !validAmount('subTotalDiv','subTotalErrorDiv') |
	!validCardHolderName() | !validStreet() | !validCity() | !validZipcode()  | !validAmount('totalAmtDiv','totalAmtErrorDiv') |!validExpDate()  ) {
		return false;
	} else {
		return true;
	}
}

function resetPreAuth(){
	setDiv("invoiceNumberErrorDiv", "");
	setDiv("cardNumberErrorDiv", "");
	setDiv("authNumberErrorDiv", "");
	setDiv("invoiceNumberErrorDiv1", "");
	setDiv("cardNumberErrorDiv1", "");
	setDiv("authNumberErrorDiv1", "");
	setDiv("subTotalErrorDiv", "");
	setDiv("totalAmtErrorDiv", "");
	setDiv("cardHolderNameErrorDiv", "");
	setDiv("streetErrorDiv", "");
	setDiv("cityErrorDiv", "");
	setDiv("zipcodeErrorDiv", "");
	setDiv("expDateErrorDiv", "");
	setDiv("errorDescDiv","");
	setDiv("responseDiv","");
	setDiv("stateErrorDiv","");
	setDiv("descriptionErrorDiv","");
	setDiv("merchantIdErrorDiv","");
	$("#refNumberDiv").attr("readOnly",false);
	$("#descriptionDiv").attr("readOnly",false);
	document.getElementById("txnForm").reset();

}

function validateAdjust() {
	if (!validCardNumber('cardNumberDiv','cardNumberErrorDiv') | !validInvoiceNumber('invoiceNumberDiv','invoiceNumberErrorDiv') | !validAuthNumber('authNumberDiv','authNumberErrorDiv')
	| !validInvoiceNumber('invoiceNumberDiv1','invoiceNumberErrorDiv1') | 
	!validCardNumber('cardNumberDiv1','cardNumberErrorDiv1') | !validAuthNumber('authNumberDiv1','authNumberErrorDiv1') | !validAmount('subTotalDiv','subTotalErrorDiv') |
	!validAmount('totalDiv','totalErrorDiv')  ) {
		return false;
	} else {
		return true;
	}
}

function resetAdjust(){
	setDiv("invoiceNumberErrorDiv", "");
	setDiv("cardNumberErrorDiv", "");
	setDiv("authNumberErrorDiv", "");
	setDiv("invoiceNumberErrorDiv1", "");
	setDiv("cardNumberErrorDiv1", "");
	setDiv("authNumberErrorDiv1", "");
	setDiv("subTotalErrorDiv", "");
	setDiv("totalErrorDiv", "");
	setDiv("errorDescDiv","");
	setDiv("responseDiv","");
	document.getElementById("txnForm").reset();
	$("#refNumberDiv").attr("readOnly",false);
}

function validExpDate() {
	var val =getVal("expDateDiv");
	var len = val.length;
	if (isEmpty(val)) {
		setDiv("expDateErrorDiv", webMessages.virtualTerminalNotEmpty);
		loadMsgTitleText();
		return false;
	} else if (!isDigit(val) || len < 4 || len > 4 ) {
		setDiv("expDateErrorDiv", webMessages.virtualTerminalNotNumericAnd4Digit);
		loadMsgTitleText();
		return false;
	} else {
		setDiv("expDateErrorDiv", "");
		return true;
	}
}

function validatePreAuthFetch() {
	if (!validRefNumber('refNumberDiv','refNumberErrorDiv')
			|!validMerchantCode('merchantIdDiv','merchantIdErrorDiv')) {
		slideDownFlag = 0;
		return false;
	} else {		
		slideDownFlag = 1;
		return true;		
	}
}

function resetPreAuthFetch(){
	setDiv("invoiceNumberErrorDiv", "");
	setDiv("cardNumberErrorDiv", "");
	setDiv("authNumberErrorDiv", "");
	setDiv("refNumberErrorDiv","");
	setDiv("errorDescDiv","");
	setDiv("responseDiv","");
	setDiv("descriptionErrorDiv","");
	setDiv("merchantIdErrorDiv","");
	$("#merchantIdDiv").attr("readOnly",false);
	$("#refNumberDiv").attr("readOnly",false);
	$("#descriptionDiv").attr("readOnly",false);

	$("#merchantIdDiv").val('');
	$("#refNumberDiv").val('');
	document.getElementById("txnForm").reset();
}

function validState() {
	var val =getVal("stateDiv");
	var regex = /^[a-zA-Z0-9#\-\s]+$/;
	if(null===val){
		setDiv("stateErrorDiv", webMessages.virtualTerminalState);
		loadMsgTitleText();
		return false;
	}
	if (isEmpty(val)) {
	setDiv("stateErrorDiv", webMessages.virtualTerminalState);
	loadMsgTitleText();
		return false;
	} else if (regex.test(val) == false) {
	setDiv("stateErrorDiv", webMessages.invalidState);
	loadMsgTitleText();
		return false;
	} else {
	setDiv("stateErrorDiv", "");
		return true;
	}
}

function validateDesc() {
	var val =getVal("descriptionDiv");
	console.log(val);
	if(val===null){
		setDiv("descriptionErrorDiv", webMessages.virtualTerminalDescription);
		loadMsgTitleText();
		return false;
	}
	if (isEmpty(val)) {
	setDiv("descriptionErrorDiv", webMessages.virtualTerminalDescription);
	loadMsgTitleText();
		return false;
	} else {
	setDiv("descriptionErrorDiv", "");
		return true;
	}
}

function zeroFill( number, width ){
	width -= number.toString().length;
	if ( width > 0 ) {
		return new Array( width + (/\./.test( number ) ? 2 : 1) ).join( '0' ) + number;
	}
	return number + ""; // always return a string
}

function validMerchantCode(id,divId) {
	var val =getVal(id);
	var len = val.length;
	if (isEmpty(val)) {
		setDiv(divId, webMessages.virtualTerminalMerchantCode);
		loadMsgTitleText();
		return false;
	} else if (!isDigit(val) || len < 4 || len > 15) {
		setDiv(divId, webMessages.virtualTerminalNumericAndUpto15);
		return false;
		loadMsgTitleText();
	} else {
		setDiv(divId, "");
		return true;
	}
}

function openConfirmPage(){
	if ( !validInvoiceNumber('invoiceNumberDiv1','invoiceNumberErrorDiv1') | !validAuthNumber('authNumberDiv1','authNumberErrorDiv1') | !validAmount('subTotalDiv','subTotalErrorDiv') |
			!validCardHolderName() | !validStreet() | !validCity() | !validZipcode()  | !validAmount('totalAmtDiv','totalAmtErrorDiv') |!validExpDate()  ) {
		return false;
	} else {
		setLable('confirmMerchantIdDiv', get('merchantIdDiv').value.trim());
		setLable('confirmRefNumberDiv', get('refNumberDiv').value.trim());
		setLable('confirmDescriptionDiv', get('descriptionDiv').value.trim());
		setLable('confirmInvoiceNumberDiv1', get('invoiceNumberDiv1').value.trim());
		setLable('confirmCardNumberDiv1', get('cardNumberDiv1').value.trim());
		setLable('confirmAuthNumberDiv1', get('authNumberDiv1').value.trim());
		setLable('confirmExpDateDiv', get('expDateDiv').value.trim());
		setLable('confirmSubTotalDiv',(get('subTotalDiv').value.trim()));
		setLable('confirmFeeAmountDiv',( get('feeAmountDiv').value.trim()));
		setLable('confirmTotalAmtDiv', ( get('totalAmtDiv').value.trim()));
		setLable('confirmCardHolderNameDiv', get('cardHolderNameDiv').value.trim());
		setLable('confirmStreetDiv', get('streetDiv').value.trim());
		setLable('confirmCityDiv', get('cityDiv').value.trim());
		setLable('confirmZipcodeDiv', get('zipcodeDiv').value.trim());
		setLable('errorDescDiv', "");

		$("#hideAllFields").hide();
		$("#responseDiv").hide();
		$("#confirmPage").show();
		
		setValue('timeZoneOffset', new Date().toString().match(/([A-Z]+[\+-][0-9]+)/)[1]);
		setValue('timeZoneRegion', jstz.determine().name());
		
		return true;
	}
}

function showEditPage(){
	$("#hideAllFields").show();
	$("#responseDiv").hide();
	$("#confirmPage").hide();
}

function openConfirmPageSale(){

	var data = get('cardNumberDiv').value.trim();
	var test;
	test = data.replace(data.substring(0, 12), "**** **** **** ");
	
	if (!validCardNumber('cardNumberDiv','cardNumberErrorDiv') | !validCv2() | !validCardHolderName() | !validAmount('subTotalDiv','subTotalErrorDiv') | 
	!validStreet() | !validCity() | !validZipcode() | !validInvoiceNumber('invoiceNumberDiv','invoiceNumberErrorDiv')
	|!validState()|!clientValidation('feeAmountDiv', 'fee_amount','feeAmountErrorDiv')|!validMerchantCode('merchantIdDiv','merchantIdErrorDiv')) {
		return false;
	} else {
		setLable('confirmMerchantIdDiv', get('merchantIdDiv').value.trim());
		setLable('confirmCardNumberDiv', test);
		setLable('confirmYeardropdown', (get('yeardropdown').value.trim())+"/"+get('monthdropdown').value.trim());
		setLable('confirmCv2Div', get('cv2Div').value.trim());
		setLable('confirmCardHolderNameDiv', get('cardHolderNameDiv').value.trim());
		setLable('confirmSubTotalDiv', get('subTotalDiv').value.trim());
		setLable('confirmTaxAmtDiv', get('taxAmtDiv').value.trim());
		setLable('confirmTipAmountDiv', get('tipAmountDiv').value.trim());
		setLable('confirmShippingAmtDiv', get('shippingAmtDiv').value.trim());
		setLable('confirmTotalAmtDiv', get('totalAmtDiv').value.trim());
		setLable('confirmStreetDiv', get('streetDiv').value.trim());
		setLable('confirmCityDiv', get('cityDiv').value.trim());
		setLable('confirmStateDiv', get('stateDiv').value.trim());
		setLable('confirmZipcodeDiv', get('zipcodeDiv').value.trim());
		setLable('confirmInvoiceNumberDiv', get('invoiceNumberDiv').value.trim());
		setLable('confirmDescriptionDiv', get('descriptionDiv').value.trim());
		setLable('errorDescDiv', "");

		$("#hideAllFields").hide();
		$("#responseDiv").hide();
		$("#confirmPage").show();
		
		setValue('timeZoneOffset', new Date().toString().match(/([A-Z]+[\+-][0-9]+)/)[1]);
		setValue('timeZoneRegion', jstz.determine().name());
		return true;
	}
}

function showEditPageSale(){
	$("#hideAllFields").show();
	$("#responseDiv").hide();
	$("#confirmPage").hide();
}

function openConfirmPageVoid(){
	if (!validInvoiceNumber('invoiceNumberDiv1','invoiceNumberErrorDiv1')  | !validAuthNumber('authNumberDiv1','authNumberErrorDiv1') | !validAmount('subTotalDiv','subTotalErrorDiv') |
	!validCardHolderName() | !validStreet() | !validCity() | !validZipcode()  | !validAmount('totalAmtDiv','totalAmtErrorDiv') |!validExpDate()  ) {
		return false;
	} else {
		setLable('confirmMerchantIdDiv', get('merchantIdDiv').value.trim());
		setLable('confirmRefNumberDiv', get('refNumberDiv').value.trim());
		setLable('confirmDescriptionDiv', get('descriptionDiv').value.trim());
		setLable('confirmInvoiceNumberDiv1', get('invoiceNumberDiv1').value.trim());
		setLable('confirmCardNumberDiv1', get('cardNumberDiv1').value.trim());
		setLable('confirmAuthNumberDiv1', get('authNumberDiv1').value.trim());
		setLable('confirmExpDateDiv', get('expDateDiv').value.trim());
		setLable('confirmSubTotalDiv', get('subTotalDiv').value.trim());
		setLable('confirmFeeAmountDiv', get('feeAmountDiv').value.trim());
		setLable('confirmTotalAmtDiv', get('totalAmtDiv').value.trim());
		setLable('confirmCardHolderNameDiv', get('cardHolderNameDiv').value.trim());
		setLable('confirmStreetDiv', get('streetDiv').value.trim());
		setLable('confirmCityDiv', get('cityDiv').value.trim());
		setLable('confirmZipcodeDiv', get('zipcodeDiv').value.trim());
		setLable('errorDescDiv', "");

		$("#hideAllFields").hide();
		$("#responseDiv").hide();
		$("#confirmPage").show();
		setValue('timeZoneOffset', new Date().toString().match(/([A-Z]+[\+-][0-9]+)/)[1]);
		setValue('timeZoneRegion', jstz.determine().name());
		return true;
	}
}

function showEditPageVoid(){
	$("#hideAllFields").show();
	$("#responseDiv").hide();
	$("#confirmPage").hide();
}

function doTransactonRefund(txnId, merchantId){
	get('getRefundMerchantId').value = merchantId;
	get('getRefundTxnId').value = txnId;
	document.forms["doRefundForm"].submit();
}

function doTransactionVoid(txnId, merchantId){
	get('transactionID').value = txnId;
	get('merchantID').value = merchantId;
	document.forms["voidIndividualTransaction"].submit();
}

function validateDoRefund(){
	if ( !validAmount('subTotalDiv','subTotalErrorDiv') 
			| !validAmount('totalAmtDiv','totalAmtErrorDiv') 
			| !clientValidation('feeAmountDiv', 'fee_amount','feeAmountErrorDiv')
			| !validateDesc()) {
		return false;
	} else {
		setValue('timeZoneOffset', new Date().toString().match(/([A-Z]+[\+-][0-9]+)/)[1]);
		setValue('timeZoneRegion', jstz.determine().name());
		return true;
	}
}

function validateVoidTrans() {
	if (!validateDesc()) {
		return false;
	} else {		
		return true;		
	}
}
function fetchMerchantCurrency() {

	var merchantCode=get('merchantIdDiv').value.trim();
	$.ajax({
		type : "GET",
		url : "getMerchantCurrency?merchantCode=" + merchantCode,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorCode === '00') {
				$('.currencyAlpha').text("("+obj.currencyCodeAlpha+"):");
			} else {
				$('.currencyAlpha').text(":");
				loadMsgTitleText();
			}
		},
		error : function(e) {
		}
	});
	
}

var monthtext = [ '01', '02', '03', '04', '05', '06', '07', '08', '09',
        			'10', '11', '12' ];
  function resetDate(monthfield, yearfield) {
  	var today = new Date()

  	var monthfield = document.getElementById(monthfield);
  	var yearfield = document.getElementById(yearfield);
  	for (var m = 0; m < 12; m++)
  		monthfield.options[m] = new Option(monthtext[m], monthtext[m])
  	monthfield.options[today.getMonth()] = new Option(monthtext[today
  			.getMonth()], monthtext[today.getMonth()], true, true) //select today's month
  	var thisyear = today.getFullYear()
  	for (var y = 0; y < 20; y++) {
  		yearfield.options[y] = new Option(thisyear, thisyear)
  		thisyear += 1
  	}
  	yearfield.options[0] = new Option(today.getFullYear(), today
  			.getFullYear(), true, true) //select today's year
  }
  

  function searchValidationForTransactions(){
		if(!clientValidation('transactionId','bank_Code','transactionIdEr')
				| !clientValidation('processCode','mobile_optional','processCodeEr')
				| !clientValidation('fromAmtRange','amount_False','fromAmtRangeEr')
				| !clientValidation('toAmtRange','amount_False','toAmtRangeEr')
				| !clientValidation('merchantCode','merchant_ID','merchantCodeError')
				| !clientValidation('merchantName','companyname_not_mandatory','merchantNameEr')){
			return false;
		}
		return true;
	}
