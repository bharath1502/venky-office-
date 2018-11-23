function validateMDRBinCreate(){
	var flag = false;
	if( !validateMDRBinNumber() | !validatePaymentScheme() | !validateBank() | !validateRadio1() |!validateRadio2() | !validateProduct() |  !validateSlab()){
		return flag;
	}else{
		flag = true;
		return flag;
	}
		
		
}

function validateMDRBinNumber(){
	var bin = getVal('binNumber').trim();
	if (isEmpty(bin)) {
		setDiv("binNumberEr",webMessages.pleaseEnterBinNumber);
		loadMsgTitleText();
		return false;
	}else if ( !isDigit(bin) ||  bin == 0){
		setDiv("binNumberEr", webMessages.invalidBinNumber);
		loadMsgTitleText();
		return false;
	}
	else if (bin.length < 6) {
		setDiv("binNumberEr", webMessages.invalidlengthBinNumber);
		loadMsgTitleText();
		return false;
	} else if (bin.charAt(parseInt("0")) == "0") {
		setDiv("binNumberEr", webMessages.binNumbercannotstartwithzero);
		loadMsgTitleText();
		return false;
	} else {
		setDiv("binNumberEr"," ");
		return true;
	}
}

function editMDR(mdrId){
	get('getMDRId').value = mdrId;
	document.forms["editMDR"].submit();
}

/*function confirmDeleteMDRBin(mdrId) {
	var r = confirm("Press Ok to confirm deletion");
	if (r == true) {
		get('getMDRBinID').value = mdrId;
		document.forms["deleteMDRDynamicBin"].submit();

	} else {
		return;
	}
}*/

function loadRadio(data) {
	if (data == 'debit') {
		$("#noAccountType").prop("checked", true);
		$("#accountType").prop("checked", false);
	} else if (data == 'credit') {
		$("#noAccountType").prop("checked", false);
		$("#accountType").prop("checked", true);
	}
}

function loadRadioforMDR(data){
	if (data == 'offus') {
		$("#noTransactionType").prop("checked", true);
		$("#transactionType").prop("checked", false);
	} else if (data == 'onus') {
		$("#noTransactionType").prop("checked", false);
		$("#transactionType").prop("checked", true);
	}
}

function validateRadio1() {
	if (get('accountType').checked == false
			&& get('noAccountType').checked == false) {
		setDiv("accountTypeErr", webMessages.pleaseselectoneoption);
		loadMsgTitleText();
		return false;
	}else{
		setDiv("accountTypeErr", '');
		return true;
		
	}
}

function validateRadio2() {
	if (get('transactionType').checked == false
			&& get('noTransactionType').checked == false) {
		setDiv("transactionTypeErr", webMessages.pleaseselectoneoption);
		loadMsgTitleText();
		return false;
	}else{
		setDiv("transactionTypeErr", '');
		return true;
		
	}
}

function validatePaymentScheme(){
	var paymentSchemeName = getVal('paymentSchemeName').trim();
	if (isEmpty(paymentSchemeName)) {
		setDiv("paymentSchemeNameErr",webMessages.pleaseselectPaymentScheme);
		loadMsgTitleText();
		return false;
	}else{
		setDiv("paymentSchemeNameErr","");
		return true;
	}
}

function validateBank(){
	var bankName = getVal('bankName').trim(); 
	if (isEmpty(bankName)) {
		setDiv("bankErr",webMessages.pleaseselectBankName);
		loadMsgTitleText();
		return false;
	}else{
		setDiv("bankErr","");
		return true;
	}
	
}

function validateProduct(){
	var productType = getVal('productType').trim();
	if (isEmpty(productType)) {
		setDiv("productTypeErr",webMessages.pleaseselectProductType);
		loadMsgTitleText();
		return false;
	}else{
		setDiv("productTypeErr","");
		return true;
	}
}

function validateSlab(){
	var regex =/^[0-9]+(\.[0-9][0-9]?)?$/;
	var slab = getVal('slab').trim();
	
	if (isEmpty(slab)) {
		setDiv("slabEr",webMessages.pleaseEnterSlabFee);
		loadMsgTitleText();
		return false;
	}else if (slab < 1  || slab > 100) {
		setDiv("slabEr", webMessages.slabFeeShouldbe);
		loadMsgTitleText();
		return false;
	} else if(regex.test(slab)) {
		setDiv("slabEr", "");
		return true;
	}else{
		//setDiv("slabEr","");
		//return true;
		
		setDiv("slabEr", webMessages.Enterpropervalue);
		return false;
		
	}
}

function resetMDRInfo() {
	
	window.location.href = 'show-dynamic-MDR-create';
}
