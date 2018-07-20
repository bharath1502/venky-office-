function validateRoleName()
{
			var roleName = get('roleName').value.trim();
			if (isEmpty(roleName)) 
			{
				setDiv('errorDiv', webMessages.changePasswordBusinessName);
				loadMsgTitleText();
				return false;
			} else if (!isCharacter(roleName)) {
				setDiv('errorDiv', webMessages.changePasswordBusinessName);
				loadMsgTitleText();
				return false;
			} else {
				setDiv('errorDiv', '');
				return true;
			}
}

function validateCurrentPassword()
{
	setDiv('successid', '');
	var currentPassword = get('currentPassword').value.trim();
	if (isEmpty(currentPassword)) 
	{
		setDiv('currentPasswordDiv', webMessages.changePasswordCurrentPassword);
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
	setDiv('successid', '');
	var newPassword = get('newPassword').value.trim();
	if (isEmpty(newPassword)) 
	{
		setDiv('newPasswordDiv', webMessages.changePasswordNewPassword);
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
	setDiv('successid', '');
	var confirmPassword = get('confirmPassword').value.trim();
	if (isEmpty(confirmPassword)) 
	{
		setDiv('confirmPasswordDiv', webMessages.changePasswordConfirmPassword);
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
	
	var confirmPassword = get('confirmPassword').value.trim();
	var newPassword = get('newPassword').value.trim();
	if(validateNewPassword() && validateConfirmPassword() && confirmPassword != newPassword)
	{
		setDiv('confirmPasswordDiv', webMessages.changePasswordNewConfirmSame);
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
	trimUserPassword();
	if(validateNewPassword() & validateConfirmPassword() & validPassword())
	{
		return true;
	}
	return false;
}

function cancelChangePassword(isNewUser) {
	if(isNewUser !== 'true') {
		window.location.href = "home";
	} else {
		window.location.href = "login";
	}
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

function trimUserPassword(){
	$('input[type="password"]').each(function (){
		$(this).val($(this).val().trim());
		
	});	
}
