function cancelCreateTerminal() {
	window.location.href='terminal-search';
}
function validateCreateTerminalStep1() {
	var flag = true;

	if (!validateMerchantCode() | !validateTerminalCode() | !validateMake()
			| !validateModel() | !validateTerminalType() | !validateCategory()
			| !validateCurrency() | !checkFromDateForEdit() | !checkToDateForEdit()
			| !compareDate()) {
		return false;
	}

	return flag;
}

function validateCreateTerminalStep2() {
	var flag = true;
	if (!validateCity() | !validateState() | !validateCountry()
			| !validateZip()) {
		return false;
	}
	return flag;
}

function validateMerchantCode(){
	var merchantCode = get('merchantId').value;
	if(merchantCode=="-1"){
		setError(get('merchantId'), 'Please select Merchant Code');
		return false;
	}else{
		setError(get('merchantId'), '');
		var e = document.getElementById('merchantId');
		var code= e.options[e.selectedIndex].text;
		setLable('confirmMmerchantId', code);
		return true;
	}
}
function validateTerminalCode(){
	var terminalCode = get('terminalId').value.trim();
	if (isEmpty(terminalCode)) {
		setError(get('terminalId'), 'Please enter Terminal Code');
		return false;
	} else if (!isDigit(terminalCode)) {
		setError(get('terminalId'), 'Invalid Terminal Code');
		return false;
	} else {
		setError(get('terminalId'), '');
		setLable('confirmMterminalId', terminalCode);
		return true;
	}
}
function validateMake(){
	var make = get('make').value.trim();
	if(isEmpty(make)) {
		setError(get('make'), 'Please enter Make');
		return false;
	} else if(!isCharacter(make)){
		setError(get('make'), 'Invalid Make');
		return false;
	}else {
		setError(get('make'), '');
		setLable('confirmMmake', make);
		return true;
	}
}
function validateModel(){
	var model = get('model').value.trim();
	if(isEmpty(model)) {
		setError(get('model'), 'Please enter Model');
		return false;
	} else if(!isCharacter(model)){
		setError(get('model'), 'Invalid Model');
		return false;
	}else {
		setError(get('model'), '');
		setLable('confirmMmodel', model);
		return true;
	}
}
function validateTerminalType(){
	var terminalType = get('terminalType').value.trim();
	if(isEmpty(terminalType)) {
		setError(get('terminalType'), 'Please enter Terminal Type');
		return false;
	} else if(!isCharacter(terminalType)){
		setError(get('terminalType'), 'Invalid Terminal Type');
		return false;
	}else {
		setError(get('terminalType'), '');
		setLable('confirmMterminalType', terminalType);
		return true;
	}
}
function validateCategory(){
	var category = get('category').value.trim();
	if(isEmpty(category)) {
		setError(get('category'), 'Please enter Category');
		return false;
	} else if(!isCharacter(category)){
		setError(get('category'), 'Invalid Category');
		return false;
	}else {
		setError(get('category'), '');
		setLable('confirmMcategory', category);
		return true;
	}
}

function validateCurrency(){
	var currency = get('currency').value;
	if(currency=="0"){
		setError(get('currency'), 'Please select currency');
		return false;
	}else{
		setError(get('currency'), '');
		setLable('confirmMcurrency', currency);
		return true;
	}
}

function checkFromDate() {
	var flag = false;
	var date1 = getVal('validFrom').trim();
	var editStartDate = '';
	if (!isEmpty(date1)) {
		if (editStartDate == date1) {
			setError(get('validFrom'), 'Please enter the Valid from date');
			return true;
		}
	}

	var cdate = new Date();
	var bdate = cdate.getFullYear() + "/" + (cdate.getMonth() + 1) + "/"
			+ cdate.getDate();

	var arr = date1.split("/");
	var dateString = arr[2] + "/" + arr[1] + "/" + arr[0];

	var givdate = new Date(dateString);
	var currentDate = new Date(bdate);

	if (date1 == "" || date1.length == 0) {
		setError(get('validFrom'), 'Please enter the Valid from date');
		flag = false;
	} else if (currentDate > givdate) {
		setError(get('validFrom'), 'Should be greater than current date');
		flag = false;
	} else {
		setError(get('validFrom'), '');
		setLable('confirmMvalidFromDate', date1);
		flag = true;
	}
	return flag;
}

function checkFromDateForEdit() {
	var flag = false;
	var date1 = getVal('validFrom').trim();
	var editStartDate = '';
	if (!isEmpty(date1)) {
		if (editStartDate == date1) {
			setError(get('validFrom'), 'Please enter the Valid from date');
			return true;
		}
	}

	var cdate = new Date();
	var bdate = cdate.getFullYear() + "/" + (cdate.getMonth() + 1) + "/"
			+ cdate.getDate();

	var arr = date1.split("/");
	var dateString = arr[2] + "/" + arr[1] + "/" + arr[0];

	var givdate = new Date(dateString);
	var currentDate = new Date(bdate);

	if (date1 == "" || date1.length == 0) {
		setError(get('validFrom'), 'Please enter the Valid from date');
		flag = false;
	} else {
		setError(get('validFrom'), '');
		setLable('confirmMvalidFromDate', date1);
		flag = true;
	}
	return flag;
}
function checkToDate() {
	var flag = false;
	var date1 = getVal('validTo').trim();
	var editStartDate = '';
	if (!isEmpty(date1)) {
		if (editStartDate == date1) {
			setError(get('validTo'), 'Please enter the Valid Till date');
			return true;
		}
	}

	var cdate = new Date();
	var bdate = cdate.getFullYear() + "/" + (cdate.getMonth() + 1) + "/"
			+ cdate.getDate();

	var arr = date1.split("/");
	var dateString = arr[2] + "/" + arr[1] + "/" + arr[0];

	var givdate = new Date(dateString);
	var currentDate = new Date(bdate);
	if (date1 == "" || date1.length == 0) {
		setError(get('validTo'), 'Please enter the Valid Till date');
		flag = false;
	} else if (currentDate > givdate) {
		setError(get('validTo'), 'Should be greater than current date');
		flag = false;
	} else {
		setError(get('validTo'), '');
		setLable('confirmMvalidToDate', date1);
		flag = true;
	}
	return flag;
}
function checkToDateForEdit() {
	var flag = false;
	var date1 = getVal('validTo').trim();
	var editStartDate = '';
	if (!isEmpty(date1)) {
		if (editStartDate == date1) {
			setError(get('validTo'), 'Please enter the Valid Till date');
			return true;
		}
	}

	var cdate = new Date();
	var bdate = cdate.getFullYear() + "/" + (cdate.getMonth() + 1) + "/"
			+ cdate.getDate();

	var arr = date1.split("/");
	var dateString = arr[2] + "/" + arr[1] + "/" + arr[0];

	var givdate = new Date(dateString);
	var currentDate = new Date(bdate);
	if (date1 == "" || date1.length == 0) {
		setError(get('validTo'), 'Please enter the Valid Till date');
		flag = false;
	} else {
		setError(get('validTo'), '');
		setLable('confirmMvalidToDate', date1);
		flag = true;
	}
	return flag;
}

function compareDate() {
	var date2 = getVal('validTo').trim();
	var date1 = getVal('validFrom');
	var arr1 = date1.split("/");
	var arr2 = date2.split("/");
	var fromDateString = arr1[2] + "/" + arr1[1] + "/" + arr1[0];
	var toDateString = arr2[2] + "/" + arr2[1] + "/" + arr2[0];
	var fromDate = new Date(fromDateString);
	var toDate = new Date(toDateString);
	if (checkFromDate() && checkToDate() && !isEmpty(date2)) {
		if (toDate >= fromDate) {
			setError(get('validTo'), '');
			return true;
		} else {
			setError(get('validTo'),
					'Valid Till date should be greater or equal to Valid from Date');
			return false;
		}
	}
	return true;
}

function validateCity(){
	var city = get('city').value.trim();
	if(isEmpty(city)) {
		setError(get('city'), 'Please enter City');
		return false;
	} else if(!isChar(city)){
		setError(get('city'), 'Invalid City');
		return false;
	}else {
		setError(get('city'), '');
		setLable('confirmMcity', city);
		return true;
	}
}

function validateState(){
	var state = get('state').value.trim();
	if(isEmpty(state)) {
		setError(get('state'), 'Please enter State');
		return false;
	} else if(!isChar(state)){
		setError(get('state'), 'Invalid State');
		return false;
	}else {
		setError(get('state'), '');
		setLable('confirmMstate', state);
		return true;
	}
}

function validateCountry(){
	var country = get('country').value.trim();
	if(isEmpty(country)) {
		setError(get('country'), 'Please enter Country');
		return false;
	} else if(!isChar(country)){
		setError(get('country'), 'Invalid Country');
		return false;
	}else {
		setError(get('country'), '');
		setLable('confirmMcountry', country);
		return true;
	}
}

function validateZip() {
	var zip = getVal('zip');
	if (isEmpty(zip)) {
		setError(get('zip'), 'Please enter zip');
		return false;
	} else if ((zip.length < 3)
			|| (zip.length > 9)) {
		setError(get('zip'), 'Invalid zip');
		return false;
	} else if (!isValidZipChars(zip)) {
		setError(get('zip'), 'Invalid zip');
		return false;
	} else{
		setError(get('zip'), '');
		setLable('confirmMzip', zip);
		return true;
	}
}
function isValidZipChars(val) {
	var firstCharAlphaRegex = /^[A-Za-z0-9][A-Za-z0-9 -]*$/;
	return firstCharAlphaRegex.test(val);
}

function validateCreateMerchantStep3() {
	return true; //TODO: 
}

function submitCreateMerchant() {
	return true;
}

function compareFromAndToDates() {
	var date2 = getVal('toDate').trim();
	var date1 = getVal('fromDate');
	var arr1 = date1.split("/");
	var arr2 = date2.split("/");
	var fromDateString = arr1[2] + "/" + arr1[1] + "/" + arr1[0];
	var toDateString = arr2[2] + "/" + arr2[1] + "/" + arr2[0];
	var fromDate = new Date(fromDateString);
	var toDate = new Date(toDateString);
	if (!isEmpty(date1) && !isEmpty(date2)) {
		if (toDate >= fromDate) {
			setError(get('toDate'), '');
			return true;
		} else {
			setError(get('toDate'),
					'Valid Till date should be greater or equal to Valid from Date');
			return false;
		}
	}
	return true;
}

function editTerminal(teminalId) {
	get('getTerminalId').value = teminalId;
	document.forms["editTerminalForm"].submit();
}

function resetAll() {
	get('merchantId').value = "-1";
	get('terminalCode').value = "";
	get('status').value = "-1";
	get('fromDate').value = "";
	get('toDate').value = "";
}