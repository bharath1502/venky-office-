<!DOCTYPE html>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.merchant.constants.StatusConstants"%>

<html lang="en">
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><spring:message code="common.lable.title"/></title>
    <!-- Bootstrap -->
    <link rel="icon" href="../images/favicon.png" type="image/png">
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link href="../css/style.css" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<!--Body Wrapper block Start -->	
    <div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<jsp:include page="header.jsp"></jsp:include>
			<!--Header Block End -->
			<!--Article Block Start-->
			<article >
				<div class="col-xs-12 content-wrapper login-page-content">
					<h3><spring:message code="change-password.label.changepassword"/></h3>
					<div class="row">
						<div class="col-sm-12">
							<!--Success and Failure Message Start-->
							<div class="col-xs-12">
								<div class="discriptionMsg" id="discriptionErrorMsgDiv">
									<span class="red-error">${error}</span>
									<span class="green-error">${sucess}</span>
								</div>
							</div>
							<!--Success and Failure Message End-->
							<!-- Page Form Start -->
							<form:form action="new-user-password-change" modelAttribute="changePasswordRequest" method="post">
							<input type="hidden" name="CSRFToken" value="${tokenval}">
								<div class="col-sm-9 login-elements-holder"  style="width: 72%;">
								<fieldset class="col-sm-5"><legend></legend></fieldset>
									<fieldset class="col-sm-7">
									<legend></legend>
									<span class="green-error">
										<span style="font-weight: bold;"><spring:message code="change-password.label.note"/> </span>
										<spring:message code="change-password.label.pwdmsg1"/><spring:message code="change-password.label.pwdmsg2"/><br>
										<spring:message code="change-password.label.pwdmsg3"/><br><spring:message code="change-password.label.pwdmsg4"/>
									</span>
										<div class="input-group">
											<span class="input-group-addon"><img src="../images/pass_icon.png"></span>
											<input type="password" name="currentPassword" id="currentPassword" class="form-control" placeholder="Current Password" onblur="validateCurrentPassword()"/>
										</div>
										<div class="descriptionMsg" data-toggle="tooltip" data-placement="top" title="">
											<span class="red-error" id="currentPasswordDiv">&nbsp;</span>
										</div> 
									</fieldset>
									<fieldset class="col-sm-5"><legend></legend></fieldset>
									<fieldset class="col-sm-7">
									<legend></legend>
										<div class="input-group">
											<span class="input-group-addon"><img src="../images/pass_icon.png"></span>
											<input type="password" name="newPassword" id="newPassword" class="form-control" placeholder="New Password" onblur="validateNewPassword()" />
										</div>
										<div class="descriptionMsg" data-toggle="tooltip" data-placement="top" title="">
											<span class="red-error" id="newPasswordDiv">&nbsp;</span>
										</div> 
									</fieldset>
									<fieldset class="col-sm-5"><legend></legend></fieldset>
									<fieldset class="col-sm-7">
									<legend></legend>
										<div class="input-group">
											<span class="input-group-addon"><img src="../images/pass_icon.png"></span>
											<input type="password" name="confirmPassword" id="confirmPassword" class="form-control" placeholder="Confirm Password" onblur="validateConfirmPassword()"/>
										</div>
										<div class="descriptionMsg" data-toggle="tooltip" data-placement="top" title="">
											<span class="red-error" id="confirmPasswordDiv">&nbsp;</span>
										</div> 
									</fieldset>
								</div>								
								<!--login Action Button Start -->
								<div class="col-sm-12 login-elements-holder" style="margin-bottom: 10px;">
								<div class="col-sm-4"></div>
									<div class="col-sm-2" style="margin-left: 80px;">
										<input type="button" class="form-control button login-main-button" value='<spring:message code="common.label.cancel"/>' onclick="cancelNewUserChangePassword('${forgotPasswordRequest.isNewUser}')">
									</div>
									<div class="col-sm-2">
										<input type="submit" class="form-control button login-main-button" value="Submit" onclick="return validSubmit();return trimUserPassword()">
									</div>	
									<div class="col-sm-3"></div>
								</div>
								<!--login Action Button End -->
							</form:form>
						</div>
					</div>		
				</div>	
			</article>	
			<!--Article Block End-->
			<jsp:include page="footer.jsp"></jsp:include>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->	

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="../js/jquery.min.js"></script>
    <script src="../js/changePassword.js"></script>
     <script src="../js/common-lib.js"></script>
     
    
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../js/bootstrap.min.js"></script> <script src="../js/utils.js"></script>	
	<script>		
		/* Select li full area function Start */
		$("li").click(function(){
			window.location=$(this).find("a").attr("href"); 
			return false;
		});
		/* Select li full area function End */		
	</script>
	<script src="../js/backbutton.js"></script>	
  </body>  
</html>