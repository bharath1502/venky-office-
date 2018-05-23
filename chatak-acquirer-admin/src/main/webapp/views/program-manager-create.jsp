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
									code="admin.pm.message" /> </a></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message
								code="common.label.create" /></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="marginL40">
						<c:if
							test="${fn:contains(existingFeatures,programmanagerView)||fn:contains(existingFeatures,programmanagerEdit)||fn:contains(existingFeatures,programmanagerSuspend)||fn:contains(existingFeatures,programmanagerActivate)}">
							<div class="tab-header-container">
								<a href="showProgramManager"><spring:message
										code="common.label.search" /> </a>
							</div>
						</c:if>
						<c:if test="${fn:contains(existingFeatures,programmanagerCreate)}">
							<div class="tab-header-container active-background">
								<a href="#"><spring:message code="common.label.create" /></a>
							</div>
						</c:if>
					</div>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<span class="green-error" id="sucessDiv">${sucess}</span> <span
									class="red-error" id="errorDiv">${error}</span>

								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="createProgramManager"
									name="programManagerDetailsForm"
									modelAttribute="programManagerRequest" method="post"
									onsubmit="buttonDisabled()" enctype="multipart/form-data">
									<input type="hidden" id="programManagerBankId"
										name="programManagerBankId" />
									<input type="hidden" id="checkDefaultPMValue"
										name="checkDefaultPMValue" value="false">
									<div
										class="col-md-100 col-sm-12 padding100px border-style-section">
										<div class="field-element-row">
											<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="admin.PM.OnBoarding.message" /><span class="required-field">*</span></label>
														<form:select path="programManagerType" cssClass="form-control" id="programManagerType" onchange="fetchPmDetailsByPmType(this.value)" onblur="validateProgramManagerId()">
															<option><spring:message code="commission-program-create.label.select" /></option>
													        <option value="OnBoardFromIssuance"><spring:message code="admin.PM.OnBoarding.issuance.message"/></option>
													        <option value="CreateIndependent"><spring:message code="admin.PM.OnBoarding.create.independent.message"/></option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip"
															data-placement="top" title="">
															<span id="programManagerTypeError" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3 issuanceProgramManager">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="admin.pm.Name.message" /><span class="required-field">*</span></label>
														<form:select path="" id="programManagerId" cssClass="form-control" onchange="fetchPmDetailsByPmId(this.value);fetchPmCardProgramByPmId(this.value)" onblur="validateProgramManagerId()">
															<form:option value=""><spring:message code="commission-program-create.label.select" /></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip"
															data-placement="top" title="">
															<span id="programManagerIdEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
										</div>
									</div>
									<div
										class="col-md-100 col-sm-12 padding100px border-style-section">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
											<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="admin.pm.Name.message" /><span
														class="required-field">*</span></label>
													<form:input path="programManagerName" cssClass="form-control"
														id="programMangerName" maxlength="50"
														onblur="clientValidation('programMangerId','company_name','pgmmgrcompanynameerrormsg')"
														onclick="clearErrorMsg('pgmmgrAquirerPMerrormsg');" />

													<div class="discriptionErrorMsg">
														<span id="pgmmgrcompanynameerrormsg" class="red-error">&nbsp;</span>
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
												<spring:message code="search.label.numericsOnly"
													var="placeholder" />
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="label.extension" /></label>
													<form:input path="extension" maxlength="5"
														cssClass="form-control" id="extension"
														onkeypress="return numbersonly(this, event);" />
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
												<fieldset class="col-md-3 col-sm-6 acquirerCurrencyNames">
													<label><spring:message code="reports.label.balancereports.currency"/><span class="required-field">*</span></label>
													 <form:select path="acquirerCurrencyName" id="acquirerCurrencyName"
														cssClass="form-control" 
														onblur = "clientValidation('acquirerCurrencyName','orderType','currencyNameErrormsg')"
														onchange="fetchBankDetailsByCurrency(this.value)">
													 </form:select>
													<div class="discriptionErrorMsg">
														<span id="acquirerCurrencyNameErrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6 currencyNames">
													<label><spring:message code="reports.label.balancereports.currency"/><span class="required-field">*</span></label>
													 <form:select path="accountCurrency" id="currencyName"
														cssClass="form-control"
														onblur = "clientValidation('currencyName','orderType','currencyNameErrormsg')"
														onchange="return clientValidation('currencyName','orderType','currencyNameErrormsg')">
													 </form:select>
													<div class="discriptionErrorMsg">
														<span id="currencyNameErrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6 acquirerBankNames">
													<label><spring:message code="admin.bank.label.bankname"/><span class="required-field">*</span></label>
													 <select id="acquirerBankName" name="acquirerBankName"
														class="form-control" 
														onblur="clientValidation('acquirerBankName','bank_name_dropdown','acquirerBankNameerrormsg')"
														onclick="clearErrorMsg('pgmmgrbankiderrormsg');banksForEFTORCheck();" multiple="multiple">
													 </select>
													<div class="discriptionErrorMsg">
														<span id="acquirerBankNameerrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6 bankNames">
													<label><spring:message code="admin.bank.label.bankname"/><span class="required-field">*</span></label>
													 <select id="bankName" name="bankNames"
														class="form-control" 
														onblur="clientValidation('bankName','bank_name_dropdown','pgmmgrbankiderrormsg')"
														onclick="clearErrorMsg('pgmmgrbankiderrormsg');banksForEFTORCheck();" multiple="multiple">
													 </select>
													<div class="discriptionErrorMsg">
														<span id="pgmmgrbankiderrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="admin.bank.label.card.program"/><span class="required-field">*</span></label>
													 <select id="cardprogramId" name=""
														class="form-control" 
														onblur="clientValidation('cardprogramId','bank_name_dropdown','pgmmgrCardProgramerrormsg')"
														 multiple="multiple">
													 </select>
													<div class="discriptionErrorMsg">
														<span id="pgmmgrCardProgramerrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="label.program.manager.logo" /></label>
													<div class="input-group">
														<span class="input-group-btn"> <span
															class="btn btn-primary btn-file"><spring:message code="search.label.Browse" />&hellip; <input
																type="file" name="programManagerLogo"
																id="programManagerLogo" onchange="readURL(this);"
																onblur="return readPartnerLogo(this,'programManagerLogoErrorDiv')"
																onclick="clearErrorMsg('programManagerLogoErrorDiv');">
														</span>
														</span> <input type="text" id="load" class="form-control readonly" readonly>
													</div>
													<div class="discriptionErrorMsg">
														<span id="programManagerLogoErrorDiv" class="red-error">&nbsp;</span>
													</div>
													
													<div  class="discriptionErrorMsg">
															<span id="programManagerLogoErrorDiv" class="red-error">&nbsp;</span></div>
														<div class="issuancePMlogo"><a href="#" onclick="openPopup()" ><spring:message code="admin-programmanager-search-label.ViewProgramManager"/></a></div>
												</fieldset>
											</div>
											</div>
											</div>
											</div>
											<!--Panel Action Button Start -->
											<div class="col-sm-12 form-action-buttons">
												<div class="col-sm-5"></div>
												<div class="col-sm-7">
													<input type="submit" class="form-control button pull-right"
														id="myButton"
														value="<spring:message code="common.label.create"/>"
														onclick="return ProgramManagerValidation()"> <a
														href="showCreateProgramManager"
														class="form-control button pull-right"><spring:message
															code="common.label.reset" /></a>
												</div>
											</div>
											<!--Panel Action Button End -->
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
	<div id="LogoDiv" class="locatioin-list-popup">
		<span class="glyphicon glyphicon-remove" onclick="closePopup()"></span>
		<h2><spring:message code="label.program.manager.logo"/></h2>
		<form:form action="updateProgramManagerLogo" id="popupForm"
			modelAttribute="programManagerRequest" method="post">
			<c:choose>
			 <c:when test="">
			   <img id="logoDisp"  src="" width="50%" height="50%" name="programManagerLogo"/>
			 </c:when>
			 <c:otherwise>
			   <spring:message code="admin-card-search-label.NoImagefound"/>
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
		/* Select li full area function Start */
		$("li").click(function() {
			window.location = $(this).find("a").attr("href");
			return false;
		});

		$(document).ready(function() {
			if ($('#autorepenish').prop('checked') == true) {
				$(".thresholdAmount").show();
			} else if ($('#autorepenish').prop('checked') == false) {
				$(".thresholdAmount").hide();
			}
		});

		$(".check").click(function() {
			if ($('#autorepenish').prop('checked') == true) {
				$(".thresholdAmount").show();
			} else if ($('#autorepenish').prop('checked') == false) {
				setValue('accountThresholdamount', '');
				setValue('sendFund', '');
				setValue('bankId', '');
				setValue('accountThresholdamount', '');
				$(".thresholdAmount").hide();
			}
		});

		/* Select li full area function End */
		/* Common Navigation Include Start */
		$(function() {
			$("#main-navigation").load("main-navigation.html");
		});
		function highlightMainContent() {
			$("#navListId2").addClass("active-background");
		}
		/* Common Navigation Include End */
	</script>
	<script>
		var MAX_PROGRAM_MANAGER_LOGO_FILE_SIZE = 1024 * 1024 * 1;

		function readImageURL(input) {
			if (!isValidImage(input.value)) {
				document.getElementById('image_div').innerHTML = webMessages.ALLOWED_IMAGES;
				return;
			}
			document.getElementById('image_div').innerHTML = '';
			if (input.files && input.files[0]) {
				if (parseInt(MAX_PROGRAM_MANAGER_LOGO_FILE_SIZE) < parseInt(input.files[0].size)) {
					document.getElementById('image_div').innerHTML = webMessages.IMAGE_SIZE;
					return;
				}
			}
		}

		function isValidImage(imageSrc) {
			var value = imageSrc.toUpperCase();
			if (value.indexOf('.PNG') != -1 || value.indexOf('.JPG') != -1
					|| value.indexOf('.JPEG') != -1
					|| value.indexOf('.GIF') != -1
					|| value.indexOf('.BMP') != -1) {
				return true;
			}
			return false;
		}

		
		$(document).ready(function() {
			$(".issuanceProgramManager").hide();
			$(".issuancePMlogo").hide();
			$(".acquirerCurrencyNames").hide();
			$(".acquirerBankNames").hide();
			 });
		
		function closePopup(){
			$('#LogoDiv').popup("hide");
		}
		function openPopup(){
			$('#LogoDiv').popup("show");
		}
		$("#contactPhone").mask("999-999-9999");

		function changeProgramManager() {
			if ($('#checkDefaultProgramManager').prop('checked') == true) {
				$('#checkDefaultPMValue').val(true);
			} else {
				$('#checkDefaultPMValue').val(false);
				setDiv('sucessDiv', '');
			}
		}
	</script>
</body>
</html>