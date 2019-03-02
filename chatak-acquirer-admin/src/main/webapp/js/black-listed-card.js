var usernameFlag = null;

function cancelCreateBlackListedCard() {
	window.location.href = 'black-listed-card-search';
}

function goToBlackListedCardSearch() {
	window.location.href = 'black-listed-card-search';
}

function openCancelConfirmationPopup() {

	if (isEmpty(getVal('cardNumber').trim())) {
		window.location.href = 'black-listed-card-search';
	}

	else {
		$('#my_popup1').popup("show");
	}
}

function closeCancelConfirmationPopup() {
	$('#my_popup1').popup("hide");
}

function editBlackListedCard(BlackListedCardId) {
	get('getBlackListedCardId').value = BlackListedCardId;
	document.forms["editBlackListedCardForm"].submit();
}

function viewBlackListedCard(BlackListedCardId) {
	get('getViewBlackListedCardId').value = BlackListedCardId;
	document.forms["viewBlackListedCard"].submit();
}

var cardNumberval = null;
function validateCardNumber() {
	setDiv("descriptionMsg", '');
	var cardNumber = getVal('cardNumber').trim();
	if (isEmpty(cardNumber)) {
		setError(get('cardNumber'), webMessages.pleaseEnterCardNumber);
		loadMsgTitleText();
		return false;
	} else if (cardNumber.length < 12 || cardNumber.length > 19
			|| cardNumber == 0) {
		setError(get('cardNumber'), webMessages.invalidCardNumber);
		loadMsgTitleText();
		return false;
	} else {
		doAjaxCardNumberDuplicate();
		if (cardNumberval == false) {
			return true;
		} else {
			return false;
		}
	}
}

function validateStatus() {
	var status = get('status').value.trim();
	var statusValue = document.getElementById("status");
	var x = statusValue.options[statusValue.selectedIndex].text;
	if (isEmpty(status)) {
		setError(get('status'), webMessages.pleaseSelectStatus);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('status'), '');
		return true;
	}
}

function resetBlackListedCardInfo() {
	resetSuccessAndErrorMsg();
	get('cardNumber').value = "";
	setError(get('cardNumber'), '');
	setDiv("descriptionMsg", '');
}

function doAjaxCardNumberDuplicate() {
	var cardId = get('cardNumber').value.trim();

	$.ajax({
		type : "GET",
		url : "uniquecardNumber?cardId=" + cardId,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorCode === '00') {
				setError(get('cardNumber'), '');
				setLable('confirmMcardNumber', get('cardNumber').value.trim());
				loadMsgTitleText();
				cardNumberval = false;
			} else if (obj.errorCode === '201') {
				setError(get('cardNumber'), '');
				setDiv("descriptionMsg",
						webMessages.BinNotMatchedEnterValidCardNumber);
				var cardNumber = true;
			} else {
				setDiv("cardNumberEr", webMessages.cardNumberAlreadyinUse);
				cardNumber = true;
			}
		},
		error : function(e) {
		}
	});
}

function validateCreateBlackListedCard() {
	var flag = true;
	if (!validateCardNum()) {
		return false;
	}
	return flag;
}

function validateCardNum() {
	var cardNumber = getVal('cardNumber').trim();
	if (isEmpty(cardNumber)) {
		setError(get('cardNumber'), webMessages.pleaseEnterCardNumber);
		loadMsgTitleText();
		return false;
	} else if (cardNumber.length < 12 || cardNumber.length > 19
			|| cardNumber == 0) {
		setError(get('cardNumber'), webMessages.invalidCardNumber);
		loadMsgTitleText();
		return false;

	} else {
		setError(get('cardNumber'), '');
		return true;
	}
}

function validateSearchCardNumber() {
	if(!clientValidation('cardNumber','card_Number_search','cardhNumberEr')) {
		return false;
	}
	return true;
}
