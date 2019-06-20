function doAjaxFetchTransactionForAdjust() {
	/*var cardNum = get('cardNumberDiv').value.trim();
	var invoiceNum = get('invoiceNumberDiv').value.trim();
	var authId = get('authNumberDiv').value.trim();
*/
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

				setDiv("errorDescDiv", webMessages.transactiondatanotfound);
			}
		},
		error : function(e) {
		}
	});
}

function doAjaxFetchTransactionForVoid() {
	/*var cardNum = get('cardNumberDiv').value.trim();
	var invoiceNum = get('invoiceNumberDiv').value.trim();
	var authId = get('authNumberDiv').value.trim();
*/
	var refId=get('refNumberDiv').value.trim();
	var txnType='sale';
	$.ajax({
		type : "GET",
		url : "getTransaction?refId=" + refId+"&txnType="+txnType,
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
				/*get('taxAmtDiv').value = obj.taxAmt;*/
				get('tipAmountDiv').value = obj.tipAmount;
				get('shippingAmtDiv').value = obj.shippingAmt;
				get('totalAmtDiv').value =  parseFloat(obj.txnAmount).toFixed(2);
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
			} else if(obj.settlementStatus == 'Executed') { 
				setDiv("errorDescDiv", webMessages.cannotvoidtransaction);
			} else {
				setDiv("errorDescDiv", webMessages.transactionalreadyvoided);
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
	/*var cardNum = get('cardNumberDiv').value.trim();
	var invoiceNum = get('invoiceNumberDiv').value.trim();
	var authId = get('authNumberDiv').value.trim();
*/
	var refId=get('refNumberDiv').value.trim();
	var txnType='sale';
	$.ajax({
		type : "GET",
		url : "getTransaction?refId=" + refId+"&txnType="+txnType,
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
				/*get('taxAmtDiv').value = obj.taxAmt;*/
				get('shippingAmtDiv').value = obj.shippingAmt;
				get('totalAmtDiv').value = parseFloat(obj.txnAmount).toFixed(2);
				get('feeAmountDiv').value = parseFloat(obj.feeAmount).toFixed(2);
				get('cardHolderNameDiv').value = obj.cardHolderName;
				get('streetDiv').value = obj.street;
				get('cityDiv').value = obj.city;
				get('zipcodeDiv').value = obj.zip;
				get('refNumberDiv').value = obj.txnRefNum;
				get('cgRefNumberDiv').value=obj.cgRefNumber;
				$(".fetch-content").slideDown();
				$("#refNumberDiv").attr("readOnly",true);
				setDiv("errorDescDiv", "");

			} else {

				setDiv("errorDescDiv", webMessages.transactionalreadyvoided); 
			}
		},
		error : function(e) {
			showAlertPopup(e);
		}
	});
}

function doAjaxFetchTransactionForCapture() {
	/*var cardNum = get('cardNumberDiv').value.trim();
	var invoiceNum = get('invoiceNumberDiv').value.trim();
	var authId = get('authNumberDiv').value.trim();*/
	var refId=get('refNumberDiv').value.trim();
	var txnType='auth';
	$.ajax({
		type : "GET",
		url : "getTransaction?refId=" + refId+"&txnType="+txnType,
		/*url : "getTransactionForAdjust?cardNum=" + cardNum + "&authId="
				+ authId + "&invoiceNum=" + invoiceNum,*/
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorCode === '00') {
				get('invoiceNumberDiv1').value = zeroFill(obj.invoiceNumber, 6);
				get('cardNumberDiv1').value = obj.cardNumMasked;
				get('cardNumPlain').value = obj.cardNum;
				get('authNumberDiv1').value = obj.authId;
				get('expDateDiv').value = obj.expDate;
				get('subTotalDiv').value = parseFloat(obj.subTotal).toFixed(2);
				get('tipAmountDiv').value = obj.tipAmount;
				get('').value = obj.tipAmount;
				/*get('taxAmtDiv').value = obj.taxAmt;*/
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
				setDiv("errorDescDiv", webMessages.transactionalreadylookupdone);
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
		setDiv("expiryDateErr", webMessages.shouldnotbeemty);
		return false;
	} else if (year > thisyear) {
		setDiv("expiryDateErr", "");
		return true;
	} else if ((year == thisyear)) {
		if ((month < thismonth)) {
			setDiv("expiryDateErr", webMessages.invaliddate);
			return false;
		}
		setDiv("expiryDateErr", "");
		return true;
	} else {
		setDiv("expiryDateErr", "");
		return true;
	}

}

function zeroFill( number, width )
{
  width -= number.toString().length;
  if ( width > 0 )
  {
    return new Array( width + (/\./.test( number ) ? 2 : 1) ).join( '0' ) + number;
  }
  return number + ""; // always return a string
}

function doTransactionVoid(txnId,merchantId,terminalID){
	get('transactionID').value = txnId;
	get('merchantID').value = merchantId;
	get('terminalID').value = terminalID;
	document.forms["voidIndividualTransaction"].submit();
}

function doTransactonRefund(txnId,merchantId,terminalID){
	get('merchantRefundID').value = merchantId;
	get('getRefundTxnId').value = txnId;
	get('terminalRefundID').value = terminalID;
	document.forms["doRefundForm"].submit();
}

function validateDoRefund(){
	if ( !validAmount('subTotalDiv','subTotalErrorDiv') 
			| !validAmount('totalAmtDiv','totalAmtErrorDiv') 
			| !clientValidation('feeAmountDiv', 'fee_amount','feeAmountErrorDiv')
			| !validateDesc()) {
		return false;
	}else{
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

function searchValidationForTransaction(){
	if(!clientValidation('transactionId','gateway_id','transactionIdError')
			| !clientValidation('processCode','mobile_optional','processCodeError')
			| !clientValidation('cardHolderName','passport_number','cardHolderNameError')
			| !clientValidation('fromAmtRange','amount_False','fromAmtRangeError')
			| !clientValidation('toAmtRange','amount_False','toAmtRangeError')){
		return false;
	}
	return true;
}