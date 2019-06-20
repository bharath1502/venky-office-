function validateRoleName()
{
			var roleName = get('roleName').value.trim();
			if (isEmpty(roleName)) 
			{
				setDiv('errorDiv', webMessages.pleaseEnterBusinessName);
				loadMsgTitleText();
				return false;
			} else if (!isCharacter(roleName)) {
				setDiv('errorDiv', webMessages.pleaseEnterBusinessName);
				loadMsgTitleText();
				return false;
			} else {
				setDiv('errorDiv', '');
				return true;
			}
}

function validateCurrentPassword()
{
	setDiv('discriptionErrorMsgDiv','');
	var currentPassword = get('currentPassword').value.trim();
	if (isEmpty(currentPassword)) 
	{
		setDiv('currentPasswordDiv', webMessages.pleaseEnterCurrentPassword);
		loadMsgTitleText();
		return false;
	} 
	else {
		setDiv('currentPasswordDiv', '');
		return true;
	}
	
}

function validateNewPassword()
{
	setDiv('discriptionErrorMsgDiv','');
	var newPassword = get('newPassword').value.trim();
	if (isEmpty(newPassword)) 
	{
		
		setDiv('newPasswordDiv', webMessages.pleaseEnterNewPassword);
		loadMsgTitleText();
		return false;
	} 
	else {
		setDiv('newPasswordDiv', '');
		return true;
	}
}

function validateConfirmPassword()
{
	setDiv('discriptionErrorMsgDiv','');
	var confirmPassword = get('confirmPassword').value.trim();
	if (isEmpty(confirmPassword)) 
	{
		setDiv('confirmPasswordDiv', webMessages.pleaseEnterConfirmPassword);
		loadMsgTitleText();
		return false;
	} 
	else {
		setDiv('confirmPasswordDiv', '');
		return true;
	}
}

function validPassword()
{
	setDiv('discriptionErrorMsgDiv','');
	var confirmPassword = get('confirmPassword').value.trim();
	var newPassword = get('newPassword').value.trim();
	if(validateNewPassword() && validateConfirmPassword() && confirmPassword != newPassword)
	{
		setDiv('confirmPasswordDiv', webMessages.newPasswordandConfirmPasswordmustbesame);
		loadMsgTitleText();
		return false;
	}
	return true;
}


function validSubmit()
{
	trimUserPassword();
	if(validateCurrentPassword() & validateNewPassword() & validateConfirmPassword() & validPassword())
	{
		return true;
	}
	return false;
}


function validResetPassword()
{
	if(validateNewPassword() & validateConfirmPassword() & validPassword())
	{
		return true;
	}
	return false;
}

function cancelChangePassword()
{
	window.location.href = "dash-board";
}
function backTodashBoard()
{
	window.location.href = "dash-board";
}
function transactionSearch()
{
	window.location.href = "transactions-search?req-from=dash-board";
	return false;
}

$('#newPassword').keypress(function( e ) {
    if(e.which === 32) 
        return false;
});

$('#confirmPassword').keypress(function( e ) {
    if(e.which === 32) 
        return false;
});

$('#currentPassword').keypress(function( e ) {
    if(e.which === 32) 
        return false;
});

function cancelNewUserChangePassword(isNewUser)
 {
	if (isNewUser !== 'true') {
		window.location.href = "dash-board";
	} else {
		window.location.href = "login";
	}
}

function trimUserPassword(){
	$('input[type="password"]').each(function (){
		$(this).val($(this).val().trim());
		
	});	
}