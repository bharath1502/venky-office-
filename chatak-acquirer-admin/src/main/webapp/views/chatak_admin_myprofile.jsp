<!DOCTYPE html>


<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>

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

	<script src="../js/jquery.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
<script src="../js/jquery.cookie.js"></script>
	<script src="../js/common-lib.js"></script>
	 <script src="../js/messages.js"></script>
	<script src="../js/admin-user.js"></script>
	<script src="../js/common-lib.min.js"></script>

	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->

			<!--Header Block End -->
			<!--Navigation Block Start -->
			<%-- <jsp:include page="header.jsp"></jsp:include> --%>
			<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block End -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="chatak-admin-myprofile.label.myprofile"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="chatak-admin-myprofile.label.myprofiletab"/></a>
					</div>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error">${error}</span> <span
											class="green-error">${sucess}</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->

								<form:form action="updateUserProfile"
									modelAttribute="userProfileRequest" method="post">
									<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="chatak-admin-myprofile.label.rolename"/><span class="required-field">*</span></label>
													<form:input path="roleName" id="roleName"
														cssClass="form-control" readonly="true" />


													<%-- 													<form:select cssClass="form-control" path="roleId" --%>
													<%-- 														id="roleName" onblur="validRoleName()"  > --%>

													<%-- 														<c:forEach items="${userRoleListData}" var="userRole"> --%>
													<%-- 															<form:option value="${userRole.roleId}">${userRole.roleName }</form:option> --%>
													<%-- 														</c:forEach> --%>
													<%-- 													</form:select> --%>

													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="roleNameDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="chatak-admin-myprofile.label.username"/><span class="required-field">*</span></label>
													<form:input path="userName" id="userName"
														cssClass="form-control" readonly="true" />

													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="userNameDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="chatak-admin-myprofile.label.firstname"/><span class="required-field">*</span></label>

													<form:input path="firstName" id="firstName"
														cssClass="form-control" onblur="this.value=this.value.trim();validFirstName()"
														maxlength="30" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="firstNameDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="chatak-admin-myprofile.label.lastname"/><span class="required-field">*</span></label>

													<form:input path="lastName" id="lastName"
														cssClass="form-control" onblur="this.value=this.value.trim();validLastName()"
														maxlength="30" />

													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="lastNameDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="chatak-admin-myprofile.label.emailid"/><span class="required-field">*</span></label>

													<form:input path="emailId" id="emailId"
														cssClass="form-control" readonly="true"
														maxlength="30" />


													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="emialDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="chatak-admin-myprofile.label.phonenumber"/><span class="required-field">*</span></label>

													<form:input path="phone" id="phone" cssClass="form-control" onkeypress="return numbersonly(this,event)"
														onblur="this.value=this.value.trim();validPhone()" maxlength="10" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="phoneDiv">&nbsp;</span>
													</div>
												</fieldset>
											</div>
										</div>
										<!--Panel Action Button Start -->
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit" class="form-control button pull-right"
													value="<spring:message code="chatak-admin-myprofile.label.savebutton"/>" onclick="return validUser()">  
												<input type="button" class="form-control button pull-right"
													value="<spring:message code="chatak-admin-myprofile.label.cancelbutton"/>" onclick="openCancelConfirmationPopup()">
												<input type="button" class="form-control button pull-right"
													value="<spring:message code="chatak-admin-myprofile.label.resetbutton"/>" onclick="resetChangeProfile()">
											</div>
										</div>
										<!--Panel Action Button End -->
									</div>
								</form:form>
								<!-- Page Form End -->
							</div>
						</div>
					</div>
					<!-- Content Block End -->
				</div>
				<div id="my_popup1" class="popup-void-refund voidResult">
					<span class="glyphicon glyphicon-remove closePopupMes"
						onclick="closeCancelConfirmationPopup()"></span>
					<div class="fw-b-fs15" style="padding: 20px;">
						<spring:message code="cancle.conformation.lable.currency" />
					</div>
					<div class="col-sm-12">

						<input type="button"
							class="form-control button pull-right margin5 close-btn"
							value="<spring:message code="bin.label.no"/>"
							onclick="closeCancelConfirmationPopup()"> <input
							type="submit" class="form-control button pull-right margin5"
							value="<spring:message code="bin.label.yes"/>"
							onclick="cancelChangeProfile()">
					</div>
				</div>
			</article>
			<!--Article Block End-->
			<div class="footer-container">
				<jsp:include page="footer.jsp"></jsp:include>
			</div>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<!-- 	<script src="../js/common-lib.min.js"></script> -->
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>


	<script>
		/* Common Navigation Include Start */
		$(function() {
			$("#main-navigation").load("main-navigation.html");
			highlightMainContent();
		});
		function highlightMainContent() {
			$("#navListId9").addClass("active-background");
		}
		/* Common Navigation Include End */
		/* Approval drop down functionality start */
		$("#approvalid").change(function() {
			if (this.value == 2) {
				$("#approverDropDown").show();
			} else {
				$("#approverDropDown").hide();
			}
		});
		$("#approverDropDown").hide();
		$('#my_popup1').popup({
			blur : false
		});
		/* Approval drop down functionality End */
	</script>
</body>
</html>