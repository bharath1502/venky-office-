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
							class="breadcrumb-text"><a href="showSearchPartner"><spring:message
									code="admin.partner.message" /></a></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message
								code="common.label.create" /></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="marginL40">
						<c:if
							test="${fn:contains(existingFeatures,partnerView)||fn:contains(existingFeatures,partnerEdit)||fn:contains(existingFeatures,partnerSuspend)||fn:contains(existingFeatures,partnerActivate)}">
							<div class="tab-header-container">
								<a href="showSearchPartner"><spring:message code="common.label.search" /></a>
							</div>
						</c:if>
						<c:if test="${fn:contains(existingFeatures,partnerCreate)}">
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
								<form:form action="createPartner"
									modelAttribute="partnerRequest" method="post"
									onsubmit="buttonDisabled()" enctype="multipart/form-data">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="admin.pm.Name.message" /><span
														class="required-field">*</span></label>
													<form:select id="programManagerId"
														path="programManagerRequest.id" cssClass="form-control"
														onblur="clientValidation('programManagerId','bank_name_dropdown','pgmngerrormsg')"
														onchange="fetchBankNameforPgmng(this.value , 'partnerCreate');fetchCurrencyForPM(this.value,'ProgramManager')">
														<form:option value=""><spring:message code="common.label.all"/></form:option>
														<c:if test="${not empty programManagersList}">
															<c:forEach items="${programManagersList}" var="pgmng">
																<c:choose>
																	<c:when
																		test="${(pgmng.defaultProgramManager eq  true) and (flag eq true)}">
																		<option value="${pgmng.id}" selected="selected">${pgmng.programManagerName}</option>
																	</c:when>
																	<c:otherwise>
																		<form:option value="${pgmng.id}">${pgmng.programManagerName}</form:option>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
														</c:if>
													</form:select>
													<div class="discriptionErrorMsg">
														<span id="pgmngerrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="bank.label.bankname" /><span class="required-field">*</span></label>
													<form:select id="bankName" path="bankName"
														cssClass="form-control"
														onblur="clientValidation('bankName','bank_name_dropdown','bankiderrormsg')"
														onclick="clearErrorMsg('bankiderrormsg');banksForEFTORCheck()">
														<form:option value=""><spring:message code="fee-program-create.label.select" /></form:option>
														<c:forEach items="${bankList}" var="bank">
															<option value='${bank.bankName}'>${bank.bankName}</option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg">
														<span id="bankiderrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="reports.label.balancereports.currency" /><span class="required-field">*</span></label>
													<form:select cssClass="form-control" id="currencyName"
														path="accountCurrency"
														onblur = "clientValidation('currencyName','orderType','currencyNameErrormsg')"
														onchange="return clientValidation('currencyName','orderType','currencyNameErrormsg')">
														<c:set var="pmCurr" value="${pmCurrency}" />
														<c:choose>
															<c:when test="${pmCurr != null}">
																<option value="${pmCurrency}">${pmCurrency}</option>
															</c:when>
															<c:otherwise>
																<form:option value="${pmCurrency}"><spring:message code="fee-program-create.label.select" /></form:option>
															</c:otherwise>
														</c:choose>


													</form:select>
													<div class="discriptionErrorMsg">
														<span id="currencyNameErrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="search.label.client.id" /><span class="required-field">*</span></label>
													<form:input path="partnerClientId" maxlength="50"
														id="partnerClientId" cssClass="form-control"
														onblur="clientValidation('partnerClientId','partner_client_id','partnerClientIdError')" />
													<div class="discriptionErrorMsg">
														<span class="red-error" id="partnerClientIdError">&nbsp;</span>
													</div>
												</fieldset>
												<div class="row col-sm-12">
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="home.label.companyname" /><span class="required-field">*</span></label>
														<input type="text" name="companyName" class="form-control"
															id="companyName" maxlength="50"
															onblur="clientValidation('companyName','company_name','companynameerrormsg')"
															onclick="clearErrorMsg('companynameerrormsg');" />

														<div class="discriptionErrorMsg">
															<span id="companynameerrormsg" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="admin.BusinessEntityName.message" /><span
															class="required-field">*</span></label> <input type="text"
															name="businessEntityName" maxlength="50"
															class="form-control" id="businessEntityName"
															onblur="clientValidation('businessEntityName','business_entity_name','businessentityerrormsg')"
															onclick="clearErrorMsg('businessentityerrormsg');" />
														<div class="discriptionErrorMsg">
															<span id="businessentityerrormsg" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="admin.PartnerName.message"/><span class="required-field">*</span></label>
														<form:input id="partnerName" path="partnerName"
															maxlength="100" cssClass="form-control"
															onblur="clientValidation('partnerName','partner_name','partneriderrormsg')"
															onclick="clearErrorMsg('partneriderrormsg');" />
														<%-- <form:option value=""><spring:message code="fee-program-create.label.select" /></form:option>
															<c:forEach items="${partnerList}" var="partner">
															<form:option value="${partner.PARTNER_NAME}">${partner.PARTNER_NAME}</form:option>
														</c:forEach> 
													</form:select> --%>
														<div class="discriptionErrorMsg">
															<span id="partneriderrormsg" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="merchant.label.contactperson" /><span class="required-field">*</span></label>
														<input type="text" name="contactPerson" maxlength="50"
															class="form-control" id="contactPerson"
															onblur="clientValidation('contactPerson','contact_person','contactpersonerrormsg')"
															onclick="clearErrorMsg('contactpersonerrormsg');" />
														<div class="discriptionErrorMsg">
															<span id="contactpersonerrormsg" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="users.label.phonenumber" /><span class="required-field">*</span></label>
														<input type="text" name="contactPhone" maxlength="10"
															class="form-control" id="contactPhone"
															onblur="clientValidation('contactPhone','partner_phone','contactphoneerrormsg')"
															onclick="clearErrorMsg('contactphoneerrormsg');" />
														<div class="discriptionErrorMsg">
															<span id="contactphoneerrormsg" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="label.extension"/></label> <input type="text"
															name="extension" maxlength="5" class="form-control"
															id="extension"
															onkeypress="return numbersonly(this, event);"
															placeholder="Numerics Only" />
														<div class="discriptionErrorMsg">
															<span class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="common.label.address1"/><span class="required-field">*</span></label>
														<form:input path="address1" id="address1"
															cssClass="form-control" maxlength="60"
															onblur="clientValidation('address1', 'address','address1_ErrorDiv')" />
														<div class="discriptionErrorMsg">
															<span id="address1_ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="common.label.address2"/></label>
														<form:input path="address2" id="address2"
															cssClass="form-control" maxlength="60"
															onblur="clientValidation('address2', 'address2' ,'address2_ErrorDiv')" />
														<div class="discriptionErrorMsg">
															<span id="address2_ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="common.label.country"/><span class="required-field">*</span></label>
														<form:select path="country" id="country"
															cssClass="form-control"
															onblur="clientValidation('country','country','country_ErrorDiv')"
															onchange="fetchMerchantState(this.value, 'state')">
															<form:option value=""><spring:message code="fee-program-create.label.select" /></form:option>
															<c:forEach items="${countryList }" var="country">
																<form:option value="${country.value}">${country.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg">
															<span id="country_ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="common.label.state"/><span class="required-field">*</span></label>
														<form:select path="state" id="state"
															cssClass="form-control"
															onblur="clientValidation('state','state','state_ErrorDiv')">
															<form:option value=""><spring:message code="fee-program-create.label.select" /></form:option>
														</form:select>
														<div class="discriptionErrorMsg">
															<span id="state_ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="common.label.city"/><span class="required-field">*</span></label>
														<form:input id="city" path="city" cssClass="form-control"
															maxlength="30"
															onblur="clientValidation('city', 'bank_city','city_ErrorDiv')" />
														<div class="discriptionErrorMsg">
															<span id="city_ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="common.label.zipcode"/><span class="required-field">*</span></label> <input
															name="zip" id="zip" Class="form-control" maxlength="10"
															onblur="clientValidation('zip', 'zip','zip_ErrorDiv')" />
														<div class="discriptionErrorMsg">
															<span id="zip_ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="label.partner.logo"/></label>
														<div class="input-group">
															<span class="input-group-btn"> <span
																class="btn btn-primary btn-file"><spring:message code="search.label.Browse"/>&hellip; <input
																	type="file" name="partnerLogo" id="partnerLogo"
																	onchange="readURL(this);"
																	onblur=" return readPartnerLogo(this,'partnerLogoErrorDiv')"
																	onclick="clearErrorMsg('partnerLogoErrorDiv');">
															</span>
															</span> <input type="text" class="form-control readonly" id="load"
																readonly>
														</div>
														<div class="discriptionErrorMsg">
															<span id="partnerLogoErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</div>
											</div>
											<!--Panel Action Button Start -->
											<div class="col-sm-12 form-action-buttons">
												<div class="col-sm-5"></div>
												<div class="col-sm-7">
													<input type="submit" class="form-control button pull-right"
														id="myButton"
														value="<spring:message code="common.label.create"/>"
														onclick="return PartnerValidation()"> <a
														href="showCreatePartner"
														class="form-control button pull-right"><spring:message
															code="common.label.reset" /></a>
												</div>
											</div>

											<!--Panel Action Button End -->
										</div>
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
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/partner.js"></script>
	<script src="../js/merchant.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		/* Select li full area function Start */
		$("li").click(function() {
			window.location = $(this).find("a").attr("href");
			return false;
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

		$(document).ready(function() {
			if ($('#defaultPartnerAPI').prop('checked') == true) {
				$(".iPAddress").show();
			} else if ($('#defaultPartnerAPI').prop('checked') == false) {
				$(".iPAddress").hide();
			}
		});

		$(".check").click(function() {
			if ($('#defaultPartnerAPI').prop('checked') == true) {
				$(".iPAddress").show();
			} else if ($('#defaultPartnerAPI').prop('checked') == false) {
				setValue('whiteListIPAddress', '');
				$(".iPAddress").hide();
			}
		});
	</script>

	<script>
		var MAX_PARTNER_LOGO_FILE_SIZE = 1024 * 1024 * 1;

		function readImageURL(input) {
			if (!isValidImage(input.value)) {
				document.getElementById('image_div').innerHTML = webMessages.ALLOWED_IMAGES;
				return;
			}
			document.getElementById('image_div').innerHTML = '';
			if (input.files && input.files[0]) {
				if (parseInt(MAX_PARTNER_LOGO_FILE_SIZEE) < parseInt(input.files[0].size)) {
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

		/* Function for xxx-xxx-xxxx format for mobile */
		$("#contactPhone").mask("999-999-9999");

		window.imageText = function(div, msgDiv) {
			var a = document.getElementById(div);
			if (a.value == "") {
				document.getElementById(msgDiv).innerHTML = webMessages.CHOOSE_FILE;
			} else {
				var theSplit = a.value.split('\\');
				document.getElementById(msgDiv).innerHTML = theSplit[theSplit.length - 1];
			}
		};

		function banksForEFTORCheck() {
			document.getElementById('bankId').options.length = 0;
			//create select option
			var selectOption = document.createElement("option");
			selectOption.innerHTML = "<spring:message code="fee-program-create.label.select" />";
			selectOption.value = "";
			$("#bankId").append(selectOption);

			var banks = document.getElementById('bankName');
			var values = [];
			for (var i = 0; i < banks.options.length; i++) {
				if (banks.options[i].selected) {
					var newOption = document.createElement("option");
					newOption.value = banks.options[i].value;
					newOption.innerHTML = banks.options[i].text;
					$("#bankId").append(newOption);
				}
			}
		}
	</script>
</body>
</html>