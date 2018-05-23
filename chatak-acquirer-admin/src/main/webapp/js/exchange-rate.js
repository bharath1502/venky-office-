function cancelCreateExchangeRate() {
	window.location.href = 'exchange-rate-create';
}

function editExchangeRate(exchangeRateId) {
	get('getExchangeId').value = exchangeRateId;
	document.forms["editExchangeRateForm"].submit();
}

function deleteExchangeRate(exchangeRateId) {
	var r = confirm("Press Ok to confirm deletion");
	if (r == true) {
		get('getExchangeRateId').value = exchangeRateId;
		document.forms["deleteExchangeRateForm"].submit();
	} else {
		return;
	}
}

function validateCreateExchangeRate() {
	var flag = true;
	if (!validateSourceCurrency() | !validateDestinationCurrency()
			| !validateExchangeRate()
			| !validateSouCurDecPos()
			| !validateDestCurDecPos()) {
		return false;
	} 
	return flag;
}

function validateSourceCurrency() {
	var sourceCurrency = get('sourceCurrency').value.trim();
	if (isEmpty(sourceCurrency)) {
		setError(get('sourceCurrency'), webMessages.pleaseSelectSourceCurrency);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('sourceCurrency'), '');
		return true;
	}
}

function validateDestinationCurrency() {
	var destCurrency = get('destCurrency').value.trim();
	if (isEmpty(destCurrency)) {
		setError(get('destCurrency'), webMessages.pleaseSelectDestinationCurrency);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('destCurrency'), '');
		return true;
	}
}

function validateExchangeRate() {
	var exchangeRate = get('exRate').value.trim();
	var reg = /^[0-9]+([.][0-9]+)?$/;
	if (isEmpty(exchangeRate)) {
		setError(get('exRate'), webMessages.pleaseEnterExchangeRate);
		loadMsgTitleText();
		return false;
	} else if (reg.test(exchangeRate) == false) {
		setError(get('exRate'), webMessages.invalidExchangeRate);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('exRate'), '');
		return true;
	}
}

function validateSouCurDecPos() {
	var souCurDecPos = get('souCurDecPos').value.trim();
	if (isEmpty(souCurDecPos)) {
		setError(get('souCurDecPos'), webMessages.pleaseEnterSourceCurrencyDecimalPosition);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('souCurDecPos'), '');
		return true;
	}
}

function validateDestCurDecPos() {
	var destCurDecPos = get('destCurDecPos').value.trim();
	if (isEmpty(destCurDecPos)) {
		setError(get('destCurDecPos'), webMessages.pleaseEnterDestinationCurrencyDecimalPosition);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('destCurDecPos'), '');
		return true;
	}
}

function resetExchangeRateInfo() {
	resetSuccessAndErrorMsg();
	get('sourceCurrency').value = "";
	setError(get('sourceCurrency'), '');
	get('destCurrency').value = "";
	setError(get('destCurrency'), '');
	get('exRate').value = "";
	setError(get('exRate'), '');
	get('souCurDecPos').value = "";
	setError(get('souCurDecPos'), '');
	get('destCurDecPos').value = "";
	setError(get('destCurDecPos'), '');
}
