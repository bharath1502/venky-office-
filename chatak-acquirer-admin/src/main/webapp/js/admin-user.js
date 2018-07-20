 function validRoleName() {
	var roleName = get('roleName').value.trim();
	if (isEmpty(roleName)) {
		setDiv('roleNameDiv', webMessages.adminUserRoleType);
		loadMsgTitleText();
		return false;
	} else {
		setDiv('roleNameDiv', '');
		setLable('confirmRoleName', roleName);
		return true;
	}
}

function validUserName() {
	var reg = /^[A-Za-z0-9]{8,16}$/;
	var userName = get('userName').value.trim();
	
	if (isEmpty(userName)) {
		setDiv('userNameDiv', webMessages.adminUserUserName);
		loadMsgTitleText();
		return false;
	} else if (reg.test(userName) == false) {
		setDiv('userNameDiv', webMessages.canContainAlphanumerics);
		loadMsgTitleText();
		return false;
	} else {
		setDiv('userNameDiv', '');
		checkAjaxUserNameAvailability();
		if (usernameFlag == false) {
			return true;
		} else {
			loadMsgTitleText();
			return false;
		}
	}

}
function validFirstName() {
	var firstName = get('firstName').value.trim();
	if (isEmpty(firstName)) {
		setDiv('firstNameDiv', webMessages.adminUserFirstName);
		loadMsgTitleText();
		return false;
	} else if (!isCharacter(firstName)) {
		setDiv('firstNameDiv', webMessages.invalidFirstName);
		loadMsgTitleText();
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
		setDiv('lastNameDiv', webMessages.adminUserLastName);
		loadMsgTitleText();
		return false;
	} else if (!isCharacter(lastName)) {
		setDiv('lastNameDiv', webMessages.invalidLastName);
		loadMsgTitleText();
		return false;
	} else {
		setDiv('lastNameDiv', '');
		return true;
	}
}

function validPhone() {
	var phone = getVal('phone').trim();
	if (isEmpty(phone)) {
		setDiv('phoneDiv', webMessages.adminUserPhoneNumber);
		loadMsgTitleText();
		return false;
	} else if (!isDigit(phone) || phone.length < 10 || phone.length > 13
			|| phone == 0) {
		setDiv('phoneDiv', webMessages.adminUserInvalidPhoneNumber);
		loadMsgTitleText();
		return false;
	} else if (phone.charAt(parseInt("0")) == "0") {
		setDiv('phoneDiv', webMessages.adminUserPhoneNumberNotStartWithZero);
		loadMsgTitleText();
		return false;
	} else {
		setDiv('phoneDiv', '');
		return true;
	}
}

function validAddress() {
	var name = get('address').value.trim();
	if (isEmpty(name)) {
		setDiv('addressDiv',  webMessages.adminUserAddress);
		loadMsgTitleText();
		return false;
	}/* else if (!isCharacter(name)) {
		setDiv('addressDiv', 'Invalid Address ');
		return false;
	} */else if (address.length < 5) {
		setDiv('addressDiv', webMessages.adminUserInvalidAddressLength);
		loadMsgTitleText();
		return false;
	} else {
		setDiv('addressDiv', '');
		return true;
	} 
}

function validateEmail() {
	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var emailAddress = get('emailId').value.trim();
	if (isEmpty(emailAddress)) {
		setDiv('emialDiv',  webMessages.adminUserEmailId);
		return false;
	} else if (reg.test(emailAddress) == false) {
		setDiv('emialDiv', webMessages.adminUserInvalidEmail);
		return false;
	} else {
		if (alpha.indexOf(emailAddress.charAt(0)) == -1) {
			setDiv('emialDiv',  webMessages.adminUserInvalidEmail);
			return false;
		} else {
			setDiv('emialDiv', " ");
		}
	}
}

function validateUserEmailId() {
	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var emailAddress = get('emailId').value.trim();
	if (isEmpty(emailAddress)) {
		setDiv('emialDiv',  webMessages.adminUserEmailId);
		loadMsgTitleText();
		return false;
	} else if (reg.test(emailAddress) == false) {
		setDiv('emialDiv', webMessages.adminUserInvalidEmail);
		loadMsgTitleText();
		return false;
	} else {
		if (alpha.indexOf(emailAddress.charAt(0)) == -1) {
			setDiv('emialDiv',  webMessages.adminUserInvalidEmail);
			loadMsgTitleText();
			return false;
		} else {
			setDiv('emialDiv', " ");
			doAjaxFetchMailIdAvailable();
			if (usernameFlag == false) {
				return true;
			} else {
				loadMsgTitleText();
				return false;
			}
		}
	}
}

function doAjaxFetchMailIdAvailable() {
	var mailId = get('emailId').value.trim();
	var requestType = $('#requestType').val();
	$.ajax({
		type : "GET",
		url : "uniqueUserEmailId?emailId=" + mailId +"&requestType="+ requestType,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (null == obj.emailId) {
				setDiv('emialDiv', " ");
				setLable('confirmMemailId', get('emailId').value.trim());
				usernameFlag = false;
			} else {
				setDiv('emialDiv',  webMessages.adminUserAlreadyUseMail);
				usernameFlag = true;
			}
		},
		error : function(e) {
		}
	});
}

function checkAjaxUserNameAvailability() {
	var userName = get('userName').value.trim();
	var requestType = $('#requestType').val();
	$.ajax({
		type : "GET",
		url : "uniqueUserName?userName=" + userName +"&requestType="+ requestType,
		async : false,
		success : function(response) {
			var obj = JSON.parse(response);
			if (null == obj.userName) {
				setDiv('userNameDiv', " ");
				setLable('confirmMuserName', get('userName').value.trim());
				usernameFlag = false;
			} else {
				setDiv('userNameDiv',  webMessages.adminUserAlreadyUseUserName);
				usernameFlag = true;
			}
		},
		error : function(e) {
		}
	});
}

function validateUserEmail() {
	var isMailUnique = $('#userMail').val();
	var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var emailAddress = get('emailId').value.trim();
	if (isEmpty(emailAddress)) {
		setDiv('emialDiv',  webMessages.adminUserEmailId);
		loadMsgTitleText();
		return false;
	} else if (reg.test(emailAddress) == false) {
		setDiv('emialDiv', webMessages.adminUserInvalidEmail);
		loadMsgTitleText();
		return false;
	} else if (alpha.indexOf(emailAddress.charAt(0)) == -1) {
			setDiv('emialDiv', webMessages.adminUserInvalidEmail);
			loadMsgTitleText();
			return false;
		} 
	setDiv('emialDiv', '');
	fetchMailIdAvailable(isMailUnique);
	if (usernameFlag == false){
		return true;
	} else {
		loadMsgTitleText();
		return false;
	}
}


function fetchMailIdAvailable(isMailUnique) {
	if (isMailUnique == "true") { // If mail is need to unique this if() code will Execute.
		var mailId = get('emailId').value.trim();
		var requestType = get('roleType').value.trim();
		$.ajax({
			type : "GET",
			url : "uniqueUserEmailId?emailId=" + mailId + "&requestType=" + requestType,
			async : false,
			success : function(response) {
				var obj = JSON.parse(response);
				if (null == obj.emailId) {
					setDiv('emialDiv', '');
					setLable('confirmMemailId', get('emailId').value.trim());
					usernameFlag = false;
				} else {
					setDiv('emialDiv', webMessages.adminUserAlreadyUseMail);
					usernameFlag = true;
				}
			},
			error : function(e) {
			}
		});
	} else {
		setLable('confirmMemailId', get('emailId').value.trim());
		usernameFlag = false;
	}
}



function validUser() {
	if (!validRoleName() | !validFirstName()
			| !validLastName() | !validPhone()) {
		return false;
	}
	return true;
}

function validCreateUser() {
	if($('#roleType').val() == 'Merchant') {
		if (!validRoleName()  | !validFirstName() //
				| !validLastName() | !validPhone() | !validateUserEmail()
			    | !validMerchantId() | !validUserName()) {
			return false;
		}
	} else if($('#roleType').val() == 'Program Manager' || $('#roleType').val() == 'ISO') {
		if (!validEntityName() | !validRoleName() | !validFirstName() //
				| !validLastName() | !validPhone() | !validateUserEmail()
			    | !validUserName()) {
			return false;
		}
	} else {
		if (!validRoleName() | !validFirstName() //
				| !validLastName() | !validPhone() | !validateUserEmail()
			    | !validUserName()) {
			return false;
		}
	}
	
	return true;
}

function validEditUser() {
	if($('#roleType').val() == 'Merchant') {
		if (!validRoleName()  | !validFirstName() | !validLastName() | !validPhone()  | !validMerchantId() | !validateUserEmail()) {
			return false;
		}
	} else if($('#roleType').val() == 'Program Manager' || $('#roleType').val() == 'ISO') {
		if (!validEntityName() | !validRoleName() | !validFirstName() //
				| !validLastName() | !validPhone() | !validateUserEmail()
			    | !validUserName()) {
			return false;
		}
	} else {
		if (!validRoleName()  | !validFirstName() | !validLastName() | !validPhone()  | !validateUserEmail()) {
			return false;
		}
	}
	return true;
}

function cancelChangeProfile() {
	window.location.href = "home";
}

/**
 * 
 */
function cancelAccessUserEdit() {
	window.location.href = "access-user-search?requestType="+$('#requestType').val();
}

function cancelCreateUser() {
	window.location.href = "access-user-search?requestType="+$('#requestType').val();
}

function openCancelConfirmationPopup() {

	if ((isEmpty(get('roleName').value.trim()))
			&& (isEmpty(get('firstName').value.trim()))
			&& (isEmpty(get('lastName').value.trim()))
			&& (isEmpty(get('emailId').value.trim()))
			&& (isEmpty(get('phone').value.trim()))
			&& (isEmpty(get('address').value.trim()))
			&& (isEmpty(get('merchantName').value.trim()))
			&& (isEmpty(get('merchantName').value.trim()))
			&& (isEmpty(document.getElementById("roleType").value))) {
		window.location.href = "access-user-search?requestType="+$('#requestType').val();
	}

	else {
		$('#my_popup1').popup("show");
	}
}

function closeCancelConfirmationPopup() {
	$('#my_popup1').popup("hide");
}

function resetChangeProfile()
{
	window.location.href = "chatak_admin_myprofile";

}

function editUser(userId) {
	get('userIdData').value = userId;
	document.forms["userEditForm"].submit();
}
function editUser(userId,usersGroupType) {
	get('userIdData').value = userId;
	get('usersGroupType').value = usersGroupType;
	get('editRequestType').value = get('requestType').value;
	document.forms["userEditForm"].submit();
}
function viewUser(userId) {
	get('userViewIdData').value = userId;
	document.forms["userViewForm"].submit();
}
function viewUser(userId,usersGroupType) {
	get('userViewIdData').value = userId;
	get('usersViewGroupType').value = usersGroupType;
	get('viewRequestType').value = get('requestType').value;
	document.forms["userViewForm"].submit();
}
var merchId,merchtype;
function confirmDeleteMerchantUser(merchantUserId,usersGroupTypes) {
	$('#deletePopup').popup("show");
	merchId = merchantUserId;
	merchtype = usersGroupTypes;

}
function deleteData() {
	get('getMerchantUserId').value = merchId;
	get('usersGroupTypes').value = merchtype;
	get('deleteRequestType').value = get('requestType').value;
	document.forms["deleteMercahntUserForm"].submit();
	
}


/*function validMerchantSelect() {
	var merchantName = get('merchantName');
	var merchntTypeValueText = merchantName.options[merchantName.selectedIndex].text;
	
	if (isEmpty(merchntTypeValueText)) {
		setDiv('merchantNameDiv', 'Please select Merchant!');
		return false;
	} else {
		setDiv('merchantNameDiv', '');
		return true;
	}
}*/

function validMerchantSelect1() {
	var merchantName = get('merchantName').value.trim();
	if (isEmpty(merchantName)) {
		setDiv('merchantNameDiv', webMessages.adminUserMerchant);
		loadMsgTitleText();
		return false;
	} else {
		setDiv('merchantNameDiv', '');
		return true;
	}

}

function validateUserType(roleType){
	var roleType = document.getElementById("roleType").value;
	//var roleDiscription = document.getElementById("description").value;
	document.getElementById('rolesType').value = roleType;
	setValue("rolesType",roleType);
	//setValue("roleDiscription",roleDiscription);
	document.forms["roleTypeForm"].submit();
    
}


function resetCreateUser() {
	
	get('roleName').value = "";
	setDiv('roleNameDiv','');
	get('address').value = "";
	setDiv('addressDiv','');
	get('firstName').value = "";
	setDiv('firstNameDiv','');
	get('lastName').value = "";
	setDiv('lastNameDiv','');
	get('emailId').value = "";
	setDiv('emialDiv','');
	get('phone').value = "";
	setDiv('phoneDiv','');
	get('merchantId').value = "";
	setDiv('merchantIdEr','');
	get('userName').value = "";
	setDiv('userNameDiv','');
	get('merchantName').value = "";
	resetSuccessAndErrorMsg();
}

$('#emailId').keypress(function( e ) {
    if(e.which === 32) 
        return false;
});

var userType = $('#userType').val();
function userUnlock(userName) {
	get('userName').value = userName;
	get('entityType').value = userType;
	document.forms["performUserUnblockForm"].submit();
}

function validateReset() {
	window.location.href = 'admin-reset-password-unblock';
}

function validatBack() {
	window.location.href = 'home';
}

function validateForgotPassWordUserName() {
	var reg = /^[A-Za-z0-9]$/;
	var userName = get('userName').value.trim();
	
	if (isEmpty(userName)) {
		setDiv('userNameDiv', webMessages.adminUserUserName);
		loadMsgTitleText();
		return false;
	} else {
		setDiv('userNameDiv', '');
		return true;
	}
}

function validMerchantId() {
	var reg = /^[0-9]{15}$/;
	var merchantId = get('merchantId').value.trim();
	if (isEmpty(merchantId)) {
		get('merchantName').value = "";
		setError(get('merchantId'), webMessages.MerchantOrSubMerchantAccountStatusTerminated);
		loadMsgTitleText();
		return false;
	} else if (reg.test(merchantId) == false) {
		get('merchantName').value = "";
		setError(get('merchantId'), webMessages.MerchantOrSubMerchantAccountStatusTerminated);
		loadMsgTitleText();
		return false;
	} else {
		doAjaxFetchMerchantCodeByMerchantName();
		if (usernameFlag == true) {
			return true;
		} else {
			return false;
		}
	}
}
function doAjaxFetchMerchantCodeByMerchantName() {
	var merchantId = get('merchantId').value.trim();
	$
			.ajax({
				type : "GET",
				url : "fetchMerchantIdByName?merchantId=" + merchantId,
				async : false,
				success : function(response) {
					var obj = JSON.parse(response);
					if (obj.errorCode === '00') {
						document.getElementById('merchantIdEr').innerHTML = "";
						setValue('merchantName', obj.merchantName);
						usernameFlag = true;
					} else {
						setError(get('merchantId'), webMessages.merchantcodenotAvailable);
						setValue('merchantName', "");
						loadMsgTitleText();
						usernameFlag = false;
					}
				},
				error : function(e) {
				}
			});
}

function resetAll() {
	document.forms["resubmitForm"].submit();
}

function validEntityName() {
	var roleName = get('entityId').value.trim();
	if (isEmpty(roleName)) {
		setDiv('entityIdDiv', webMessages.adminUserEntity);
		loadMsgTitleText();
		return false;
	} else {
		setDiv('entityIdDiv', '');
		return true;
	}
}