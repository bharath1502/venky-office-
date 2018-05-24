 function validRoleName() {
	var roleName = get('roleName').value.trim();
	if (isEmpty(roleName)) {
		setDiv('roleNameDiv', 'Please select Role name');
		return false;
	} else {
		setDiv('roleNameDiv', '');
		return true;
	}
}

function validUserName() {
	var userName = get('userName').value.trim();
	if (isEmpty(userName)) {
		setDiv('userNameDiv', 'Please enter User name');
		return false;
	} else if (!isCharacter(userName)) {
		setDiv('userNameDiv', 'Please enter User name');
		return false;
	} else {
		setDiv('userNameDiv', '');
		return true;
	}

}
function validFirstName() {
	var firstName = get('firstName').value.trim();
	if (isEmpty(firstName)) {
		setDiv('firstNameDiv', 'Please enter User name');
		return false;
	} else if (!isCharacter(firstName)) {
		setDiv('firstNameDiv', 'Please enter User name');
		return false;
	} else {
		setDiv('firstNameDiv', '');
		return true;
	}
}

function validLastName() {
	//lastName
	//lastNameDiv

	var lastName = get('lastName').value.trim();
	if (isEmpty(lastName)) {
		setDiv('lastNameDiv', 'Please enter User name');
		return false;
	} else if (!isCharacter(lastName)) {
		setDiv('lastNameDiv', 'Please enter User name');
		return false;
	} else {
		setDiv('lastNameDiv', '');
		return true;
	}
}

function validPhone() {
	//	phone
	//	phoneDiv

	var phone = getVal('phone').trim();
	if (isEmpty(phone)) {
		setDiv('phoneDiv', 'Please enter Phone Number');
		return false;
	} else if (!isDigit(phone) || phone.length < 10 || phone.length > 13
			|| phone == 0) {
		setDiv('phoneDiv', 'Invalid Phone Number');
		return false;
	} else if (phone.charAt(parseInt("0")) == "0") {
		setDiv('phoneDiv', 'Phone cannot start with zero');
		return false;
	} else {
		setDiv('phoneDiv', '');
		return true;
	}

}
function validateUserEmailId() {
	setDiv('discriptionErrorMsgDiv','');
	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var emailAddress = get('emailId').value.trim();

	if (isEmpty(emailAddress)) {
		setDiv('emialDiv', 'Please enter Email ID');
		return false;
	}

	else if (reg.test(emailAddress) == false) {
		setDiv('emialDiv', 'Invalid Email ID');
		return false;
	} else {
		if (alpha.indexOf(emailAddress.charAt(0)) == -1) {
			setDiv('emialDiv', 'Invalid Email ID');
			return false;
		}

		setDiv('emialDiv', ' ');
		return true;
	}

}

function validName() {
	//lastName
	//lastNameDiv

	var name = get('address').value.trim();
	if (isEmpty(name)) {
		setDiv('addressDiv', 'Please enter Address');
		return false;
	} else if (!isCharacter(name)) {
		setDiv('addressDiv', 'Please enter Adredd');
		return false;
	} else {
		setDiv('addressDiv', '');
		return true;
	}
}

function validUser() {
	if (!validRoleName() | !validUserName() | !validFirstName()
			| !validLastName() | !validPhone() | !validateUserEmailId()) {
		return false;
	}
	return true;
}

function validCreateUser() {
	if (!validRoleName() | !validName() | !validUserName() | !validFirstName()
			| !validLastName() | !validPhone() | !validateUserEmailId()) {
		return false;
	}
	return true;
}

function validEditUser() {
	if (!validRoleName() | !validName() | !validUserName() | !validFirstName()
			| !validLastName() | !validPhone()) {
		return false;
	}
	return true;
}

function cancelChangeProfile() {
	window.location.href = "dash-board";
}

function resetCreateUser() {
	window.location.href = "access-user-create";
}

function resetChangeProfile()
{
	window.location.href = "chatak_merchant_myprofile";

}

function editUser(userId) {
	get('userIdData').value = userId;
	document.forms["userEditForm"].submit();
}

function validateMerchantSignUp(){
	var flag = false;
	if(!clientValidation('firstName', 'first_name','firstNameErrorDiv')
			|!clientValidation('lastName', 'last_name','lastNameErrorDiv')
			|!clientValidation('phone', 'mobile_phone','phoneErrorDiv')
			|!clientValidation('address1', 'address','address1ErrorDiv')
			|!clientValidation('pin', 'zip','pinErrorDiv')
			|!clientValidation('country', 'country','countryErrorDiv')
			|!validateEmailId()
			|!clientValidation('businessType', 'country','businessTypeErrorDiv')
			|!clientValidation('businessName', 'first_name','businessNameErrorDiv')
			|!clientValidation('lookingFor', 'purpose','lookingForErrorDiv')
			|!validateCheckBox()){
		return flag;
	}else{
		return true;
	}
}

function validateCheckBox(){
	var flag = false;
	if(document.getElementById('checkBox').checked == true){
		setDiv("checkBoxErrorDiv","");
		return true;
	}else{
		setDiv("checkBoxErrorDiv","Please accept Terms and Conditions");
		return false;
	}
}

function validateEmailId() {
	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var emailAddress = get('emailId').value.trim();
	if (isEmpty(emailAddress)) {
		setError(get('emailId'), 'Please enter Email ID');
		return false;
	}else if (reg.test(emailAddress) == false) {
		setError(get('emailId'), 'Invalid Email ID');
		return false;
	} else {
		if (alpha.indexOf(emailAddress.charAt(0)) == -1) {
			setError(get('emailId'), 'Invalid Email ID');
			return false;
		} else {
			doAjaxFetchMailIdAvailable();
			if (usernameFlag == false) {
				return true;
			} else {
				return false;
			}
		}
	}
}

function doAjaxFetchMailIdAvailable() {
	var mailId = get('emailId').value.trim();
	$.ajax({
		type : "GET",
		url : "uniqueEmailId?emailId=" + mailId,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (obj.errorCode === '00') {
				setError(get('emailId'), '');
				usernameFlag = false;
			} else {
				setError(get('emailId'), 'Email ID already in use');
				usernameFlag = true;
			}
		},
		error : function(e) {
		}
	});
}

function validateForgotPassWordUserName() {
	var reg = /^[A-Za-z0-9]$/;
	var userName = get('userName').value.trim();
	
	if (isEmpty(userName)) {
		setDiv('userNameDiv', webMessages.loginUsername);
		loadMsgTitleText();
		return false;
	} else {
		setDiv('userNameDiv', '');
		return true;
	}
}