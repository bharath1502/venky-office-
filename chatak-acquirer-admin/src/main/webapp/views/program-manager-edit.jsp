<!DOCTYPE html>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.Calendar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
  int year = Calendar.getInstance().get(Calendar.YEAR);
%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><spring:message code="common.lable.title" /></title>
<!-- Bootstrap -->
<link rel="icon" href="../images/favicon.png" type="image/png">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Navigation Block Start -->
			<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message
								code="manage.label.manage" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><a href="showProgramManager"><spring:message
									code="admin.pm.message" /></a></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message
								code="common.label.edit" /></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first">
						<a href="showProgramManager"><spring:message
								code="common.label.search" /> </a>
					</div>
					<div class="tab-header-container active-background">
						<a href="#"><spring:message code="common.label.edit" /></a>
					</div>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-sm-12">
									<div class="discriptionErrorMsg">
										<span class="green-error" id="sucessDiv">${sucess}</span> <span
											class="red-error" id="errorDiv">${error}</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="updateProgramManager"
									modelAttribute="programManagerRequest" method="post"
									enctype="multipart/form-data" onsubmit="buttonDisabled()">
									<form:hidden id="id" path="id" />
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="admin.pm.Name.message" /><span
														class="required-field">*</span></label>
													<form:input id="programManagerName"
														path="programManagerName" maxlength="100"
														cssClass="form-control"
														onblur="clientValidation('programManagerName','program_manager_name','pgmmgrnameerrormsg')"
														onclick="clearErrorMsg('pgmmgrnameerrormsg');" />
													<div class="discriptionErrorMsg">
														<span id="pgmmgrnameerrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="home.label.companyname" /><span
														class="required-field">*</span></label>
													<form:input path="companyName" cssClass="form-control"
														id="companyName" maxlength="50"
														onblur="clientValidation('companyName','company_name','pgmmgrcompanynameerrormsg')"
														onclick="clearErrorMsg('pgmmgrcompanynameerrormsg');" />

													<div class="discriptionErrorMsg">
														<span id="pgmmgrcompanynameerrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="admin.BusinessEntityName.message" /><span
														class="required-field">*</span></label>
													<form:input path="businessName" maxlength="50"
														cssClass="form-control" id="businessEntityName"
														onblur="clientValidation('businessEntityName','business_entity_name','pgmmgrbusinessentityerrormsg')"
														onclick="clearErrorMsg('pgmmgrbusinessentityerrormsg');" />
													<div class="discriptionErrorMsg">
														<span id="pgmmgrbusinessentityerrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="merchant.label.contactperson" /><span
														class="required-field">*</span></label>
													<form:input path="contactName" maxlength="50"
														cssClass="form-control" id="contactPerson"
														onblur="clientValidation('contactPerson','contact_person','pgmmgrcontactpersonerrormsg')"
														onclick="clearErrorMsg('pgmmgrcontactpersonerrormsg');" />
													<div class="discriptionErrorMsg">
														<span id="pgmmgrcontactpersonerrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="users.label.phonenumber" /><span
														class="required-field">*</span></label>
													<form:input path="contactPhone" maxlength="10"
														cssClass="form-control" id="contactPhone"
														onblur="clientValidation('contactPhone','partner_phone','pgmmgrcontactphoneerrormsg')"
														onclick="clearErrorMsg('contactphoneerrormsg');" />
													<div class="discriptionErrorMsg">
														<span id="pgmmgrcontactphoneerrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="label.extension" /></label> <input
														type="text" name="extension"
														value="${programManagerRequest.extension}" maxlength="50"
														class="form-control" id="extension"
														onkeypress="return numbersonly(this, event);"
														placeholder="Numerics only" />
													<div class="discriptionErrorMsg">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="userList-file-exportutil-emailId" /><span
														class="required-field">*</span></label>
													<form:input path="contactEmail" id="programManagerEmailId"
														cssClass="form-control" maxlength="50"
														onblur="clientValidation('programManagerEmailId', 'email','programManagerEmailIdEmailId_ErrorDiv')" />
													<div class="discriptionErrorMsg">
														<span id="programManagerEmailIdEmailId_ErrorDiv"
															class="red-error">&nbsp;</span>
													</div>
												</fieldset>

												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="bank.label.bankname" /><span
														class="required-field">*</span></label>
													<form:select id="bankName" path="bankNames"
														cssClass="form-control"
														onblur="clientValidation('bankName','bank_name_dropdown','pgmmgrbankiderrormsg')"
														onclick="clearErrorMsg('pgmmgrbankiderrormsg');">

														<c:forEach var="current" items="${bankList}">
															<c:set var="contains" value="false" />
															<c:forEach var="currentselect"
																items="${selectedBankList}">
																<c:if test="${currentselect.bankId eq current.value}">
																	<c:set var="contains" value="true" />
																</c:if>
															</c:forEach>
															<c:choose>
																<c:when test="${contains eq true}">
																	<option value="${current.value}" selected="selected">
																		${current.label}</option>
																</c:when>
																<c:otherwise>
																	<option value="${current.value}">
																		${current.label}</option>
																</c:otherwise>
															</c:choose>

														</c:forEach>

													</form:select>
													<div class="discriptionErrorMsg">
														<span id="pgmmgrbankiderrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="reports.label.balancereports.currency" /></label>
													<form:select cssClass="form-control" id="currencyName"
														path="accountCurrency" disabled="true"
														onchange="return clientValidation('currencyName','orderType','currencyNameErrormsg')">
														<form:option value="">
															<spring:message code="fee-program-create.label.select" />
														</form:option>
														<c:forEach items="${currencyList}" var="currency">
															<form:option value="${currency.label}">${currency.label}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg">
														<span id="currencyNameErrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="label.program.manager.logo" /></label>
													<div class="input-group">
														<span class="input-group-btn"> <span
															class="btn btn-primary btn-file"> <spring:message
																	code="search.label.Browse" />&hellip; <input
																type="file" name="programManagerLogo"
																id="programManagerLogo" onchange="readURL(this);"
																onblur=" return readPartnerLogoedit(this,'partnerLogoErrorDiv')"
																onclick="clearErrorMsg('programManagerLogoErrorDiv');">
														</span>
														</span> <input type="text" class="form-control readonly"
															id="load" name="partnerLogo" readonly />
													</div>
													<div>
														<span id="partnerLogoErrorDiv" class="red-error">&nbsp;</span>
														<div>
															<a href="#" onclick="openPopup()"><spring:message code="pm.logo.image.view" /></a>
														</div>
													</div>
												</fieldset>
											</div>
										</div>
										<!--Panel Action Button Start -->
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit" class="form-control button pull-right"
													id="myButton" value="Update"
													onclick="return editPMValidation()"> <a
													href="showProgramManager"
													class="form-control button pull-right"><spring:message
														code="common.label.cancel" /></a>
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
			</article>
			<!--Article Block End-->
			<jsp:include page="footer.jsp" />
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->
	<!-- Pop Up box information starts here -->
	<div id="LogoDiv" class="locatioin-list-popup">
		<span class="glyphicon glyphicon-remove" onclick="closePopup()"></span>
		<h2><spring:message code="label.program.manager.logo" /></h2>
		<form:form action="updateProgramManagerLogo" id="popupForm"
			modelAttribute="programManagerRequest" method="post">
			<c:choose>
				<c:when test="${not empty imageData}">
					<img id="logoDisp" src="${image}" width="50%" height="50%"
						name="programManagerLogo" />
				</c:when>
				<c:otherwise>
			   <spring:message code="no.logo.image.found" />
			 </c:otherwise>
			</c:choose>
		</form:form>
		<!--Panel Action Button End -->
	</div>

	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/utils.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/sorting.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/tablesorter.js"></script>
	<script src="../js/tablesorter.widgets.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/program-manager.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		/* Select li full area function End */
		/* Common Navigation Include Start */
		$(function() {
			$("#main-navigation").load("main-navigation.html");
		});
		function highlightMainContent() {
			$("#navListId2").addClass("active-background");
		}
		/* Common Navigation Include End */

		$(document).ready(function() {
			$('#LogoDiv').popup({
				blur : false
			});
		});

		function closePopup() {
			$('#LogoDiv').popup("hide");
		}
		function openPopup() {
			$('#LogoDiv').popup("show");
		}
	</script>

	<script type="text/javascript">
		window.imageText = function(div, msgDiv) {
			var a = document.getElementById(div);
			if (a.value == "") {
				document.getElementById(msgDiv).innerHTML = webMessages.CHOOSE_FILE;
			} else {
				var theSplit = a.value.split('\\');
				document.getElementById(msgDiv).innerHTML = theSplit[theSplit.length - 1];
			}
		};
		/* Function for xxx-xxx-xxxx format for mobile */
		$("#contactPhone").mask("999-999-9999");
	</script>
</body>
</html>