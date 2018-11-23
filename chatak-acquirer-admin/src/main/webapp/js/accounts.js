var slideDownFlag1 = 0;

function validateAccMerchantCode() {
	if (!clientValidation('merchantIdDiv', 'merchant_Code',
			'merchantIdErrorDiv')) {
		slideDownFlag1 = 0;
		return false;
	} else {
		slideDownFlag1 = 1;
		get('inputAmount').value = "";
		get('descriptionDiv').value = "";
		return true;
	}
}
var currencyExponent;
var currencySeparatorPosition;
var currencyMinorUnit;
var currencyThousandsUnit;
var currencyId; 
var currentbalId;
function doAjaxFetchAccountDetails() {
	var merchantId = get('merchantIdDiv').value.trim();
	$
			.ajax({
				type : "GET",
				url : "getMerchantAccBalanceDetails?merchantId=" + merchantId,
				async : false,
				success : function(response) {
					var obj = JSON.parse(response);
					if (obj.errorCode === '00') {
						setDiv("errorMsgDiv", "");
						get('merchantName').value = obj.merchantName;
						get('accountNumber').value = obj.accountNumber;
						get('availableBalanceString').value = obj.availableBalanceString;
						currencyId = "availableBalanceString";
						get('currentBalanceString').value = obj.currentBalanceString;
						currentbalId = "currentBalanceString";
						get('availableBalCurrencyAlpha').innerHTML = obj.merchantCurrencyAlpha;
						get('currentBalCurrencyAlpha').innerHTML = obj.merchantCurrencyAlpha;
						get('inputAmtCurrencyAlpha').innerHTML = obj.merchantCurrencyAlpha;
						currencyExponent = obj.currencyExponent;
						currencySeparatorPosition = obj.currencySeparatorPosition;
						currencyMinorUnit = obj.currencyMinorUnit;
						currencyThousandsUnit = obj.currencyThousandsUnit;
						$("#"+currencyId).val(obj.availableBalanceString);
						$("#"+currentbalId).val(obj.currentBalanceString);
						if($("#errorMsgDiv").text() != ""){
							setDiv("errorMsgDiv", $("#errorMsgDiv").text());
						}
						$(".fetch-details").slideDown();
						$("#hideSearchButton").fadeOut();
						document.getElementById("merchantIdDiv").readOnly = true;
						} else {
						setDiv("errorMsgDiv",obj.errorMessage);
					}
				},
				error : function(e) {
				}
			});
}

function validInputAmount(id, divId) {
	var val = getVal(id);
	if (isEmpty(val)) {
		setDiv(divId, webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else if (val == 0) {
		setDiv(divId, webMessages.shouldbegreaterthanzero);
		return false;
	}
	
	else {
		setDiv(divId, "");
		return true;
	}
}

function validateCredit() {
	$('#processBtn').hide();
	if (!validInputAmount('inputAmount', 'inputAmountErrorDiv')
			| !clientValidation('descriptionDiv', 'fee_Description',
					'descriptionErrorDiv')) {
		$('#processBtn').show();
		return false;
	} else {
		convertToLongValue();
		setValue('timeZoneOffset', new Date().toString().match(/([A-Z]+[\+-][0-9]+)/)[1]);
		setValue('timeZoneRegion', jstz.determine().name());
		return true;
	}
}

function validateDebit() {
	$('#processBtn').hide();
	if (!validInputAmount('inputAmount', 'inputAmountErrorDiv')
			| !clientValidation('descriptionDiv', 'fee_Description',
					'descriptionErrorDiv')) {
		$('#processBtn').show();
		return false;
	} else {
		convertToLongValue();
		setValue('timeZoneOffset', new Date().toString().match(/([A-Z]+[\+-][0-9]+)/)[1]);
		setValue('timeZoneRegion', jstz.determine().name());
		return true;
	}
}

function convertToLongValue() {
	var availableBal = (get('availableBalance').value);
	availableBal = availableBal.replace(/[,.']+/g,"");
	get('availableBal').value = Math.trunc(availableBal);
	var currentBal = (get('currentBalance').value);
	currentBal = currentBal.replace(/[,.']+/g,"");
	get('currentBal').value = Math.trunc(currentBal);
	//var inputVal = (get('inputAmount').value);
	var globalInput = (get('inputAmount').value);
	globalInput = globalInput.replace(/[,.']+/g,"");
	get('inputAmt').value = Math.trunc(globalInput);
	return true;

}

function validInputDebitAmount(id, divId) {
	var val = getVal(id);
	var availableBal = get('availableBalance').value;
	var regex = /^[0-9]*\.[0-9]{2}$/;

	if (isEmpty(val)) {
		setDiv(divId, webMessages.validationthisfieldismandatory);
		loadMsgTitleText();
		return false;
	} else if (regex.test(val) == false) {
		setDiv(divId, webMessages.entervalidamount);
		loadMsgTitleText();
		return false;
	} else if (Number(val) > Number(availableBal)) {
		loadMsgTitleText();
		setDiv("inputAmountErrorDiv",
				"Debit Amount should be lesser then Available Balance");
		return false;
	} else {
		setDiv(divId, "");
		return true;
	}
}

function resetValues() {
	get('merchantIdDiv').value = "";
	setDiv("merchantIdErrorDiv", "");
	setDiv("errorMsgDiv", "");
}


var globalInput;
function formatNum(testid) {
	var inputAmount = $("#"+testid).val();
	if(inputAmount == 0,00 || inputAmount == "" || inputAmount == 0.000 ||inputAmount == 0.00)
		{
		return $("#"+testid).val("0");
		}	
	var inputAmount = $("#"+testid).val().split(currencyThousandsUnit)
	.join('');
	globalInput = inputAmount;
	
	var inputAmount = ('' + parseFloat(inputAmount).toFixed(currencyExponent)
			.toString()).split('.');
	var num = inputAmount[0];
	var dec = inputAmount[1];
	var r, s, t;

	if (num.length > currencySeparatorPosition) {
		var test = "(\\d{" + currencySeparatorPosition + "})";
		var regex = new RegExp(test, "g");
		s = num.length % currencySeparatorPosition;
		t = num.substring(0, s);
		num = t + num.substring(s).replace(regex, currencyThousandsUnit + "$1");
		if (s == 0) {
			num = num.substring(1);
		}
	}
	if (dec > 0) {
		$("#"+testid).val(num + currencyMinorUnit + dec);
	} else {
		$("#"+testid).val(num + currencyMinorUnit + inputAmount[1]);
	}
	if(testid == "avlamt" || testid == "curamt" ){
		if(testid == "avlamt")
		$("#avlBal").text(":"+$("#"+testid).val());
		if(testid == "curamt")
		$("#curBal").text(":"+$("#"+testid).val());
	}
}
function amountFmt()
{
	var amtval = $("#avlamt").val();
	var curamt = $("#curamt").val();
	
	if(null != amtval && amtval != "" && null != curamt && curamt != ""){
		formatNum("avlamt");
		formatNum("curamt");
		$("#hideAllFields").hide();
	}
	
}

