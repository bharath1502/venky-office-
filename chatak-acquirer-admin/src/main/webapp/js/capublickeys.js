var publicKey = null;
$(document).ready(function() {
	$("#navListId10").addClass("active-background");
	$(".focus-field").click(function() {
		$(this).children('.effectiveDate').focus();
	});

	$('.effectiveDate').datetimepicker({
		timepicker : false,
		format : 'd-m-Y',
		formatDate : 'd-m-Y',
		minDate: 0
	});
});

function ishex(val) {
	var re = /[0-9A-Fa-f]{6}/g;
	if (re.test(val)) {
		return false;
	} else {
		return true;
	}
}

function ishexDeci(val) {
	var re = /[0-9A-Fa-f]{10}/g;
	if (re.test(val)) {
		return false;
	} else {
		return true;
	}
}

function ishexDeciindex(val) {

	var re = /[0-9A-Fa-f]{2}/g;
	if (re.test(val)) {
		return false;
	} else {
		return true;
	}
}

function validateRId() {

	var rID = get('rID').value.trim();
	if (isEmpty(rID)) {
		setError(get('rID'), webMessages.pleaseEnterRIDValue);
		loadMsgTitleText();
		return false;
	}
	if (ishexDeci(rID)) {
		setError(get('rID'), webMessages.pleaseEnterValidRIDValue);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('rID'), '');
		return true;
	}

}

function validCAPublicKeyName() {
	var publicKeyName = get('publicKeyName').value.trim();

	if (isEmpty(publicKeyName)) {
		setError(get('publicKeyName'), webMessages.pleaseEnterCAPublicKeysValue);
		loadMsgTitleText();
		return false;
	} else {
		doAjaxPublicKeyNameDuplicate();
		
		if (publicKey == false) {
			return true;
		} else {
			return false;
		}
	}
}

function validCAPublicKeyModulus() {
	var publicKeyModulus = get('publicKeyModulus').value.trim();
	var publicKeyModuluslength = publicKeyModulus.length;
	
	if (isEmpty(publicKeyModulus)) {
		setError(get('publicKeyModulus'),
				webMessages.pleaseEnterCAPublicKeyModulusValue);
		loadMsgTitleText();
		return false;
	}
	if (publicKeyModuluslength % 2 != 0) {
		setError(get('publicKeyModulus'),
				webMessages.pleaseEnterValidCAPublicKeyModulusValue);
		loadMsgTitleText();
		return false;
	}
	if (ishex(publicKeyModulus)) {

		setError(get('publicKeyModulus'),
				webMessages.pleaseEnterValidCAPublicKeyModulusValue);
		loadMsgTitleText();
		return false;
	}
	 else if (publicKeyModuluslength > 200) {
		setError(get('publicKeyModulus'),
				webMessages.pleaseEnterValidCAPublicKeyModulusValue);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('publicKeyModulus'), '');
		return true;
	}
}

function validCAPublicKeyExponent() {
	var publicKeyExponent = get('publicKeyExponent').value.trim();
	if (isEmpty(publicKeyExponent)) {
		setError(get('publicKeyExponent'), webMessages.pleaseEnterCAPublicKeyExponent);
		loadMsgTitleText();
		return false;
	}
	if (!isDigit(publicKeyExponent)) {
		setError(get('publicKeyExponent'),webMessages.pleaseEnterValidCAPublicKeyExponentValue);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('publicKeyExponent'), '');
		return true;
	}
}

function validIndex() {
	var index = get('index').value.trim();
	if (isEmpty(index)) {
		setError(get('index'), webMessages.pleaseEnterCAPublicKeyindex);
		loadMsgTitleText();
		return false;
	}

	if (ishexDeciindex(index)) {
		setError(get('index'), webMessages.invalidCAPublicKeyIndexValue);
		loadMsgTitleText();
		return false;
	} else {
		setError(get('index'), '');
		return true;
	}
}

function editCAPublicKeys(CAPublicKeysId) {

	get('getCAPublicKeysId').value = CAPublicKeysId;
	document.forms["editCAPublicKeysForm"].submit();
}

function viewCAPublicKeys(CAPublicKeysId) {

	get('getViewCAPublicKeysId').value = CAPublicKeysId;
	document.forms["viewCAPublicKeysForm"].submit();
}

function openConfirmPageSale() {
	if (!validCAPublicKeyName() | !validateRId('rID')
			| !validCAPublicKeyModulus('publicKeyModulus')
			| !validIndex('index')
			| !validCAPublicKeyExponent('publicKeyExponent')
			| !validateCAPublicKeysExpDate('expiryDate')) {
		return false;
	}
	return true;
}

function updateCapublickey() {
	if (!validateRId('rID')
			| !validCAPublicKeyModulus('publicKeyModulus')
			| !validIndex('index')
			| !validCAPublicKeyExponent('publicKeyExponent')
			| !validateCAPublicKeysExpDate('expiryDate')) {
		return false;
	}
	return true;
}

function resetCAPublicKeysInfo() {

	resetSuccessAndErrorMsg();
	get('publicKeyName').value = "";
	setError(get('publicKeyName'), '');
	get('rID').value = "";
	setError(get('rID'), '');
	get('publicKeyModulus').value = "";
	setError(get('publicKeyModulus'), '');
	get('index').value = "";
	setError(get('index'), '');
	get('publicKeyExponent').value = "";
	setError(get('publicKeyExponent'), '');
	get('expiryDate').value = "";
	setError(get('expiryDate'), '');
}

function resetCAPublicKeysSearch() {
	window.location.href = 'show-ca-public-keys-search-page';
}

function openCancelConfirmationPopup() {

	if ((isEmpty(get('publicKeyName').value.trim()))
			&& (isEmpty(get('rID').value))
			&& (isEmpty(get('publicKeyModulus').value))
			&& (isEmpty(get('publicKeyExponent').value))
			&& (isEmpty(get('expiryDate').value))
			&& (isEmpty(get('index').value))) {
		window.location.href = 'show-ca-public-keys-search-page';
	}

	else {
		$('#my_popup1').popup("show");
	}
}

function closeCancelConfirmationPopup() {
	$('#my_popup1').popup("hide");
}


function validateCAPublicKeysExpDate() {
	var expDate = get('expiryDate').value.trim();
	
	if (isEmpty(expDate)) {
		setError(get('expiryDate'), webMessages.pleaseEnterValidExpiryDate);
		loadMsgTitleText();
		return false;
	}
	
	var currentdd=new Date().getDate(); 
	var currentmm=new Date().getMonth();
	var currentyyyy=new Date().getFullYear();
	var monthNames = ["January", "February", "March", "April", "May", "June",
	                  "July", "August", "September", "October", "November", "December" ];

	var currentDate = monthNames[currentmm] + " " + currentdd + ", " + currentyyyy;
	
	var str_array = expDate.split('-');
	var formattedExpDate = monthNames[str_array[1]-1] + " " + str_array[0] + ", " + str_array[2];
	
	if(Date.parse(formattedExpDate) < Date.parse(currentDate)) {
		setError(get('expiryDate'), webMessages.pleaseEnterValidExpiryDate);
		loadMsgTitleText();
		return false;
    }else{
    	setError(get('expiryDate'), '');
    }
	return true;
}

function doAjaxPublicKeyNameDuplicate() {
	var publicKeyNameId = get('publicKeyName').value.trim();

	$.ajax({
		type : "GET",
		url : "uniquepublicKeyName?publicKeyNameId=" + publicKeyNameId,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorCode === '00') {
				setError(get('publicKeyName'), '');
				setLable('confirmMpublicKeyName', get('publicKeyName').value.trim());
				loadMsgTitleText();
				publicKey = false;

			} else {
				setDiv("publicKeyNameEr", webMessages.publicKeyNameAlreadyinUse);
				publicKeyName = true;
			}
		},
		error : function(e) {
		}
	});
}

var CAPublicKeyID;
function confirmDeleteCAPublicKey(merchantId) {
	$('#deletePopup').popup("show");
	CAPublicKeyID = merchantId;
}

function deleteData() {
	get('getCAPublicKeyID').value = CAPublicKeyID;
	document.forms["deleteCAPublicKey"].submit();
}

