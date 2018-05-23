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
						<span class="breadcrumb-text"><spring:message code="manage.label.manage"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><a href="showSearchPartner"><spring:message code="admin.partner.message"/></a></span>
						<span class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="common.label.edit"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first">
						<a href="showSearchPartner"><spring:message code="common.label.search"/></a>
					</div>
					<div class="tab-header-container active-background">
						<a href="#"><spring:message code="common.label.edit"/></a>
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
								<form:form action="updatePartner" commandName="partnerRequest"
									method="post" enctype="multipart/form-data"
									onsubmit="buttonDisabled()">
									<input type="hidden" id="partnerId" name="partnerId"
										value="${partnerRequest.partnerId}" />
									<input type="hidden" id="managerId" name="managerId"
										value="${partnerRequest.programManagerRequest.id}" />
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="admin.pm.Name.message"/></label>
													 <input id="pgmngid"
														name="programManagerRequest.id"
														value="${partnerRequest.programManagerRequest.programManagerName }"
														readonly="true" class="form-control" />

													<div class="discriptionErrorMsg">
														<span id="pgmngerrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="bank.label.bankname" /></label>
													<%-- <input id="bankID" name="bankID" readonly="true" maxlength="<%= JSPConstants.BANK_NAME %>" value="${partnerRequest.BANK_NAME}" class="form-control" onblur="clientValidation('bankID','bank_name_dropdown','bankiderrormsg')"/> --%>
													<form:select id="bankName" path="bankName"
														cssClass="form-control"
														onblur="clientValidation('bankName','bank_name_dropdown','bankiderrormsg')">
														<%-- <c:forEach items="${bankList}" var="bank">
															<c:if test="${bank.status eq 'Active' }">
																<form:option value="${bank.label}">${bank.label}</form:option>
													        </c:if>
														</c:forEach>  --%>

														<c:forEach var="current" items="${pgmngBankList}">
															<c:set var="contains" value="false" />
															<c:forEach var="currentselect"
																items="${selectedbankList}">
																<c:if test="${currentselect.id eq current.id}">
																	<c:set var="contains" value="true" />
																</c:if>
															</c:forEach>
															<c:choose>
																<c:when test="${contains eq true}">
																	<option value="${current.id}" selected="selected">
																		${current.bankName}</option>
																</c:when>
																<c:otherwise>
																	<c:if test="${current.status eq 'Active' }">
																		<option value="${current.id}">
																			${current.bankName}</option>
																	</c:if>
																</c:otherwise>
															</c:choose>

														</c:forEach>

													</form:select>
													<div class="discriptionErrorMsg">
														<span id="bankiderrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="reports.label.balancereports.currency" /></label>
													<input id="currencyName" name="accountCurrency"
														value="${partnerRequest.accountCurrency}" readonly="true"
														class="form-control" />
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
														<input class="form-control" name="companyName"
															value="${partnerRequest.companyName}" id="companyName"
															maxlength="50"
															onblur="clientValidation('companyName','company_name','companynameerrormsg')" />
														<div class="discriptionErrorMsg">
															<span id="companynameerrormsg" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="admin.BusinessEntityName.message" /><span
															class="required-field">*</span></label> <input
															class="form-control" maxlength="50"
															name="businessEntityName"
															value="${partnerRequest.businessEntityName}"
															id="businessEntityName"
															onblur="clientValidation('businessEntityName','business_entity_name','businessentityerrormsg')" />
														<div class="discriptionErrorMsg">
															<span id="businessentityerrormsg" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="admin.PartnerName.message"/></label>
														<input id="partnerName" name="partnerName" maxlength="100"
															value="${partnerRequest.partnerName}"
															class="form-control"
															onblur="clientValidation('partnerName', 'partner_name', 'partneriderrormsg')"
															readonly="readonly" />
														<%-- <form:option value=""><spring:message code="fee-program-create.label.select" /></form:option>
															<c:forEach items="${allPartner}" var="partner">
															<form:option value="${partner.PARTNER_NAME}">${partner.PARTNER_NAME}</form:option>
														</c:forEach> 
														</form:select> --%>
														<div class="discriptionErrorMsg">
															<span id="partneriderrormsg" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="merchant.label.contactperson" /><span class="required-field">*</span></label>
														<input class="form-control" maxlength="50"
															name="contactPerson"
															value="${partnerRequest.contactPerson}"
															id="contactPerson"
															onblur="clientValidation('contactPerson','contact_person','contactpersonerrormsg')" />
														<div class="discriptionErrorMsg">
															<span id="contactpersonerrormsg" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="users.label.phonenumber" /><span class="required-field">*</span></label>
														<input class="form-control" maxlength="10"
															name="contactPhone"
															value="${partnerRequest.contactPhone}" id="contactPhone"
															onblur="clientValidation('contactPhone','partner_phone','contactphoneerrormsg')" />
														<div class="discriptionErrorMsg">
															<span id="contactphoneerrormsg" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="label.extension"/></label> <input type="text"
															name="extension" value="${partnerRequest.extension}"
															maxlength="5" class="form-control" id="extension"
															onkeypress="return numbersonly(this, event);"
															placeholder="NumericsOnly" />
														<div class="discriptionErrorMsg">
															<span class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="common.label.address1"/><span class="required-field">*</span></label>
														<form:input id="address1" cssClass="form-control"
															path="address1" maxlength="60"
															onblur="clientValidation('address1', 'address','address1_ErrorDiv')" />
														<div class="discriptionErrorMsg">
															<span id="address1_ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="common.label.address2"/></label>
														<form:input id="address2" cssClass="form-control"
															path="address2"
															onblur="clientValidation('address2', 'address2' ,'address2_ErrorDiv')"
															maxlength="60" />
														<div class="discriptionErrorMsg">
															<span id="address2_ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="common.label.country"/><span class="required-field"></span></label>
														<form:select path="country" id="country"
															cssClass="form-control"
															onblur="clientValidation('country','country','country_ErrorDiv')"
															onchange="fetchMerchantState(this.value, 'state')">
															<option value=""><spring:message code="fee-program-create.label.select" /></option>
															<c:forEach items="${countryList }" var="countryList">
																<c:if
																	test="${countryList.value eq partnerRequest.country}">
																	<option value="${countryList.value}" selected>${countryList.label}</option>
																</c:if>
																<c:if
																	test="${countryList.value ne partnerRequest.country}">
																	<option value="${countryList.value}">${countryList.label}</option>
																</c:if>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg">
															<span id="country_ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="common.label.state"/><span class="required-field">*</span></label>
														<form:select path="state" cssClass="form-control"
															id="state"
															onblur="clientValidation('state','state','state_ErrorDiv')">
															<option value=""><spring:message code="fee-program-create.label.select" /></option>
															<c:forEach items="${stateList }" var="stateList">
																<c:if test="${stateList.value eq partnerRequest.state }">
																	<option value="${stateList.value}" selected>${stateList.label}</option>
																</c:if>
																<c:if test="${stateList.value ne partnerRequest.state }">
																	<option value="${stateList.value}">${stateList.label}</option>
																</c:if>
															</c:forEach>
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
														<label><spring:message code="common.label.zipcode"/><span class="required-field">*</span></label>
														<form:input id="zip" path="zip" cssClass="form-control"
															maxlength="10"
															onblur="clientValidation('zip', 'zip','zip_ErrorDiv')" />
														<div class="discriptionErrorMsg">
															<span id="zip_ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>


													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="label.partner.logo"/> </label>
														<div class="input-group">
															<span class="input-group-btn"> <span
																class="btn btn-primary btn-file"> <spring:message code="search.label.Browse"/>&hellip;
																	<input type="file" name="partnerLogo" id="partnerLogo"
																	onchange="readURL(this);"
																	onblur=" return readPartnerLogoedit(this,'partnerLogoErrorDiv')"
																	onclick="show();clearErrorMsg('partnerLogoErrorDiv');">
															</span>
															</span> <input type="text" class="form-control readonly"
																id="load" name="partnerLogo" readonly />
														</div>
														<div>
															<span id="partnerLogoErrorDiv" class="red-error">&nbsp;</span>
															<div>
																<a href="#" class="feeDescDiv" onclick="openPopup()"><spring:message code="partner.logo.image.view" /></a>
															</div>
														</div>
													</fieldset>
													<fieldset class="col-sm-12"></fieldset>
												</div>
											</div>
										</div>
										<!--Panel Action Button Start -->
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit" class="form-control button pull-right"
													id="myButton" value="Update"
													onclick="return editPartnerValidation()"> <a
													href="showSearchPartner"
													class="form-control button pull-right"><spring:message code="common.label.cancel" /></a>
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
		<h2><spring:message code="label.partner.logo"/></h2>
		<form:form action="updateManagePartner" id="popupForm"
			modelAttribute="partnerRequest" method="post">
			<c:choose>
				<c:when test="${not empty imageData}">
					<img id="logoDisp" src="${image}" width="50%" height="50%"
						name="partnerLogo" />
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
	<script src="../js/partner.js"></script>
	<script src="../js/merchant.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		/* Select li full area function Start */
		/* $("li").click(function(){
			window.location=$(this).find("a").attr("href"); 
			return false;
		}); */
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
				//setValue('whiteListIPAddress','');
				$(".iPAddress").hide();
			}
		});
	</script>

	<script>
		var MAX_FILE_SIZE = 1024 * 1024 * 1;

		function readImageURL(input) {
			if (!isValidImage(input.value)) {
				document.getElementById('image_div').innerHTML = webMessages.ALLOWED_IMAGES;
				return;
			}
			document.getElementById('image_div').innerHTML = '';
			if (input.files && input.files[0]) {
				if (parseInt(MAX_FILE_SIZE) < parseInt(input.files[0].size)) {
					document.getElementById('image_div').innerHTML = webMessages.IMAGE_1MB;
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