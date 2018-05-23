<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.pg.util.Constants"%>
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
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
</head>
<body>

	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<!--Header Block End -->
			<!--Navigation Block Start -->
			 <jsp:include page="navigation-panel.jsp"></jsp:include> 			
			<!--Navigation Block Start  -->

			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<form:form action="pending-nmas-download" name="downloadNMASToShow" method="post">
									<input type="hidden" id="merchantDownloadId" name="merchantDownloadId" />
					</form:form>
					
					<form:form action="merchant-pending-to-active" name="merchantActive" method="post">
									<input type="hidden" id="getMerchantId" name="getMerchantId" />
					</form:form>
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="pending-merchant-show.label.manage"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="pending-merchant-show.label.merchant"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span>
						<span class="breadcrumb-text"><spring:message code="pending-merchant-show.label.view"/></span>
					</div>
					<!-- <div class=" pull-right" style="margin-right: 40px"
						id="subMerchant">
						<img alt="Create sub merchant" src="../images/user_icon.png"><a
							href="sub-merchant-create">Add Sub Merchant</a>
					</div> -->
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="pending-merchant-show.label.viewtab"/></a>
						</div>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder padding0">
						<!-- Page Menu Start -->
						<div class="col-sm-12 padding0">
							<div class="sub-nav">
								<ul>
									<li class="account-details-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="merchant-circle-tab active-circle"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.basicinfo"/></label>
										<div class="arrow-down merchant-arrow"></div>
									</li>
									<li class="bank-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="bank-info-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.bankinfo"/></label>
										<div class="arrow-down bank-info-arrow"></div>
									</li>
									<li class="legal-entiy-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="legal-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.entityrepresentative"/></label>
										<div class="arrow-down legal-arrow"></div>
									</li>
									<li class="legal-entiy-rep-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="legal-circle-rep-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.entity"/></label>
										<div class="arrow-down legal-rep-arrow"></div>
									</li>
									<li class="free-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="contact-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.additional"/></label>
										<div class="arrow-down contact-arrow"></div>
									</li>
									<li class="atm-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="bank-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.configurations"/></label>
										<div class="arrow-down configuration-arrow"></div>
									</li>
								</ul>
							</div>
						</div>
						<!-- Page Menu End -->
						<div class="row margin0">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionMsg col-xs-12">
										<span class="red-error">&nbsp;${error }</span> <span
											class="green-error">&nbsp;${sucess }</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="merchant-pending-to-active" modelAttribute="merchant" name="merchant" method="post">
									<div class="col-sm-12 paddingT20">
										<div class="row">
											<!-- Account Details Content Start -->
											<jsp:include page="pending-merchant-show-remaining.jsp"></jsp:include>
											<!-- Account Details Content End -->
											<!-- Free Transactions Content Start -->
											<section class="field-element-row bank-info-details-content"
												style="display: none;">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.name"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="bankAccountName"
															id="bankAccountName" maxlength="50"
															onblur="return clientValidation('bankAccountName', 'first_name_SplChar','bankAccountNameErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAccountNameErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.bankrountingnumber"/><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control"
															path="bankRoutingNumber" id="bankRoutingNumber"
															maxlength="9"
															onkeypress="return amountValidate(this,event)"
															onblur="return clientValidation('bankRoutingNumber', 'routing_number','bankRoutingNumberEr');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankRoutingNumberEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.bankaccountnumber"/><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control"
															path="bankAccountNumber" id="bankAccountNumber"
															onblur="return clientValidation('bankAccountNumber', 'account_numberBank','bankAccountNumberErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAccountNumberErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.type"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control"
															path="bankAccountType" id="bankAccountType"
															onblur="return clientValidation('bankAccountType', 'account_type','bankAccountTypeErrorDiv');">
															<form:option value="">..:<spring:message code="pending-merchant-show.label.select"/>:..</form:option>
															<form:option value="S"><spring:message code="pending-merchant-show.label.savings"/></form:option>
															<form:option value="C"><spring:message code="pending-merchant-show.label.checking"/></form:option>
														</form:select>

														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAccountTypeErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.address1"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="bankAddress1"
															id="bankAddress1" maxlength="50"
															onblur="return clientValidation('bankAddress1', 'bank_address2','bankAddress1ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAddress1ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.address2"/></label>
														<form:input cssClass="form-control" path="bankAddress2"
															id="bankAddress2" maxlength="50"
															onblur="return clientValidation('bankAddress2', 'bank_address2','bankAddress2ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAddress2ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.city"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="bankCity"
															id="bankCity" maxlength="50"
															onblur="return clientValidation('bankCity', 'bank_address2','bankCityErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankCityErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.country"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="bankCountry"
															id="bankCountry"
															onblur="return clientValidation('bankCountry', 'country','bankCountryErrorDiv');"
															onchange="fetchMerchantState(this.value, 'bankState')">
															<form:option value="">..:<spring:message code="pending-merchant-show.label.select"/>:..</form:option>
															<c:forEach items="${countryList}" var="country">
																<form:option value="${country.label}">${country.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankCountryErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.state"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="bankState"
															id="bankState"
															onblur="return clientValidation('bankState', 'state','bankStateErrorDiv');">
															<form:option value="">..:<spring:message code="pending-merchant-show.label.select"/>:..</form:option>
															<c:forEach items="${bankStateList}" var="item">
																<form:option value="${item.label}">${item.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankStateErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.zipcode"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="bankPin"
															id="bankPin" maxlength="10"
															onblur="return clientValidation('bankPin', 'zip_code','bankPinErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankPinErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.nameonaccount"/><span class="required-field">*</span></label>

														<form:input cssClass="form-control"
															path="bankNameOnAccount" id="bankNameOnAccount"
															maxlength="10"
															onblur="return clientValidation('bankNameOnAccount', 'first_name_SplChar','bankNameOnAccountErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankNameOnAccountErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label><spring:message code="merchant.label.currency"/><span class="required-field">*</span></label>
														<form:input path="currencyId" id="currencyId" readonly="true" cssClass="form-control"/>
														<div class="discriptionErrorMsg">
															<span id="countryEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															class="form-control button pull-right bank-next"
															value="<spring:message code="pending-merchant-show.label.continuebutton"/>"> <input type="button"
															class="form-control button pull-right marginL10 bank-prev"
															value="<spring:message code="pending-merchant-show.label.previousbutton"/>"> <input type="button"
															class="form-control button pull-right marginL10"
															value="<spring:message code="pending-merchant-show.label.cancelbutton"/>" onclick="goToDashBoard()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<!-- Free Transactions Content End -->
											<!-- ATM Transactions Content Start -->
											<section class="field-element-row legal-details-content"
												style="display: none;">
												<fieldset class="col-sm-12">

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.ssn"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalSSN"
															onkeypress="return amountValidate(this,event)"
															id="legalSSN" maxlength="20" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalSSNErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.firstname"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalFirstName"
															id="legalFirstName" maxlength="50" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalFirstNameErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.lastname"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalLastName"
															id="legalLastName" maxlength="50" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalLastNameErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.mobilephone"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control"
															path="legalMobilePhone" id="legalMobilePhone" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalMobilePhoneErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.dateofbirth"/><!-- <span class="required-field">*</span> --></label>
														<div class="input-group focus-field">
															<form:input cssClass="form-control effectiveDate"
																path="legalDOB" id="legalDOB" />
															<span class="input-group-addon"><span
																class="glyphicon glyphicon-calendar"></span></span>
														</div>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalDOBErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.passportnumber"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalPassport"
															id="legalPassport" maxlength="20" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalPassportErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.countryofresidence"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control"
															path="legalCountryResidence" id="legalCountryResidence"
															maxlength="50" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalCountryResidenceErrorDiv"
																class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.countryofcitizenship"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalCitizen"
															id="legalCitizen" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalCitizenErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.homephone"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalHomePhone"
															onkeypress="return amountValidate(this,event)"
															id="legalHomePhone" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalHomePhoneErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															class="form-control button pull-right legal-next"
															value="<spring:message code="pending-merchant-show.label.continuebutton"/>"> <input type="button"
															class="form-control button pull-right marginL10 legal-prev"
															value="<spring:message code="pending-merchant-show.label.previousbutton"/>"> <input type="button"
															class="form-control button pull-right marginL10"
															value="<spring:message code="pending-merchant-show.label.cancelbutton"/>" onclick="goToDashBoard()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<!-- ATM Transactions Content End -->
											<!-- POS Transactions Content Start -->
											<section class="field-element-row legal-details-rep-content"
												style="display: none;">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.entityleganame"/><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalName"
															id="legalName" maxlength="50"
															onblur="return clientValidation('legalName', 'first_name_SplChar','legalNameErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalNameErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.eintaxid"/>:<span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalTaxId"
															id="legalTaxId" maxlength="50"
															onblur="return clientValidation('legalTaxId', 'eIN_taxId','legalTaxIdErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalTaxIdErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.type"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="legalType"
															id="legalType"
															onblur="return clientValidation('legalType', 'state','legalTypeErrorDiv');">
															<form:option value="">..:<spring:message code="pending-merchant-show.label.select"/>:..</form:option>
															<form:option value="1"><spring:message code="pending-merchant-show.label.associationestatetrust"/></form:option>
															<form:option value="2"><spring:message code="pending-merchant-show.label.corporation"/></form:option>
															<form:option value="3"><spring:message code="pending-merchant-show.label.governmentagency"/></form:option>
															<form:option value="4"><spring:message code="pending-merchant-show.label.individual"/></form:option>
															<form:option value="5"><spring:message code="pending-merchant-show.label.internationalorganization"/></form:option>
															<form:option value="6"><spring:message code="pending-merchant-show.label.llc"/></form:option>
															<form:option value="7"><spring:message code="pending-merchant-show.label.partnership"/></form:option>
															<form:option value="8"><spring:message code="pending-merchant-show.label.taxexemptorganization"/></form:option>
															<form:option value="11"><spring:message code="pending-merchant-show.label.testing"/></form:option>
														</form:select>

														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalTypeErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">
															<spring:message code="merchant.label.expectedannualcardsales" />
															<span class="required-field">*</span>
														</label> 
														<fmt:formatNumber type="currency" value="${merchant.legalAnnualCard}" pattern="<%=Constants.AMOUNT_FORMAT%>" var="legalAnnualCard" />
														<input name="legalAnnualCard" id="legalAnnualCard" disabled="disabled"
															cssClass="form-control" value="${ legalAnnualCard}" maxlength="12"
															onblur="this.value=this.value.trim();appendDollarSymbol();return clientValidation('legalAnnualCard', 'dollar_amount','legalAnnualCardErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalAnnualCardErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.address1"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalAddress1"
															id="legalAddress1"
															onblur="return clientValidation('legalAddress1', 'bank_address1','legalAddress1ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalAddress1ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.address2"/></label>
														<form:input cssClass="form-control" path="legalAddress2"
															id="legalAddress2"
															onblur="return clientValidation('legalAddress2', 'bank_address2','legalAddress2ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalAddress2ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.city"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalCity"
															id="legalCity"
															onblur="return clientValidation('legalCity', 'bank_city','legalCityErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalCityErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.country"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="legalCountry"
															id="legalCountry"
															onblur="return clientValidation('legalCountry', 'country','legalCountryErrorDiv');"
															onchange="fetchMerchantState(this.value, 'legalState')">
															<form:option value="">..:<spring:message code="pending-merchant-show.label.select"/>:..</form:option>
															<c:forEach items="${countryList}" var="country">
																<form:option value="${country.label}">${country.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalCountryErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.state"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="legalState"
															id="legalState"
															onblur="return clientValidation('legalState', 'state','legalStateErrorDiv');">
															<form:option value="">..:<spring:message code="pending-merchant-show.label.select"/>:..</form:option>
															<c:forEach items="${legalStateList}" var="state">
																<form:option value="${state.label}">${state.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalStateErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.zipcode"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalPin"
															id="legalPin"
															onblur="return clientValidation('legalPin', 'zip_code','legalPinErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalPinErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															class="form-control button pull-right legal-rep-next"
															value="<spring:message code="pending-merchant-show.label.continuebutton"/>"> <input type="button"
															class="form-control button pull-right marginL10 legal-rep-prev"
															value="<spring:message code="pending-merchant-show.label.previousbutton"/>"> <input type="button"
															class="form-control button pull-right marginL10"
															value="<spring:message code="pending-merchant-show.label.cancelbutton"/>" onclick="goToDashBoard()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<section class="field-element-row free-transactions-content"
												style="display: none;">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.username"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="userName"
															id="userName" maxlength="50" readonly="true" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="userNameEr" class="red-error">&nbsp;</span> <span
																id="userNamegreenEr" class="green-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															class="form-control button pull-right free-next"
															value="<spring:message code="pending-merchant-show.label.continuebutton"/>"> <input type="button"
															class="form-control button pull-right marginL10 free-prev"
															value="<spring:message code="pending-merchant-show.label.previousbutton"/>"> <input type="button"
															class="form-control button pull-right marginL10"
															value="<spring:message code="pending-merchant-show.label.cancelbutton"/>" onclick="goToDashBoard()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<section class="field-element-row atm-transaction-content"
												style="display: none;">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-12">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.autosettlementoptions"/><span
															class="required-field">*</span></label><br> <input
															type="radio" id="allowAutoSettlement"
															name="autoSettlement" value="<spring:message code="pending-merchant-show.label.1"/>" onclick="validateRadio()"><spring:message code="manage.option.radio.sub-merchant.yes"/>
														<input type="radio" id="noAutoSettlement"
															name="autoSettlement" value="<spring:message code="pending-merchant-show.label.0"/>" onclick="validateRadio()"><spring:message code="manage.option.radio.sub-merchant.no"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="noAutoSettlementEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.merchanttype"/><span class="required-field">*</span></label>
													<form:select cssClass="form-control"
														path="merchantCategory" id="merchantCategory"
														onblur="validateMerchantCategory()">
														<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<form:option value="Individual"><spring:message code="merchant.label.individual"/></form:option>
															<form:option value="Group"><spring:message code="merchant.label.group"/></form:option>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="merchantCategoryEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
													
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.feeprogram"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="feeProgram"
															id="feeProgram" onblur="validatefeeProgram()">
															<form:option value="">..:<spring:message code="pending-merchant-show.label.select"/>:..</form:option>
															<c:forEach items="${feeprogramnames}" var="feename">
																<form:option value="${feename.label}">${feename.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="feeProgramEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.processor"/><span class="required-field">*</span></label>
														<!-- <select class="form-control">
															<option>..:Select:..</option>
														</select> -->
														<form:select cssClass="form-control" path="processor"
															id="processor" onblur="validateProcessor()">
															<form:option value="">..:<spring:message code="pending-merchant-show.label.select"/>:..</form:option>
															<c:forEach items="${processorNames}" var="processorName">
																<form:option value="${processorName.value}">${processorName.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="processorEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.merchantcallbackurl"/><!-- <span
															class="required-field">*</span> --></label>
														<form:input cssClass="form-control"
															path="merchantCallBackURL" id="merchantCallBackURL"
															onblur="validateCallbackURL()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="merchantCallBackURLEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.category"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="category"
															id="category">
															<form:option value="primary"><spring:message code="pending-merchant-show.label.primary"/></form:option>
															<form:option value="secondary"><spring:message code="pending-merchant-show.label.secondary"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="categoryEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.autotransferlimit"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control"
															path="autoTransferLimit" id="autoTransferLimit"
															onblur="validateAutoTransferLimit()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoTransferLimitEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.autopaymentmethod"/><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control"
															path="autoPaymentMethod" id="autoPaymentMethod"
															onblur="validateAutoPaymentMethod()">
															<form:option value="">..:<spring:message code="pending-merchant-show.label.select"/>:..</form:option>
															<form:option value="EFT"><spring:message code="pending-merchant-show.label.eft"/></form:option>
															<form:option value="Cheque"><spring:message code="pending-merchant-show.label.cheque"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoPaymentMethodEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.autotransfer"/><!-- <span class="required-field">*</span> --></label>
														<form:select cssClass="form-control"
															onchange="showAutoTransferDayFields()"
															path="autoTransferDay" id="autoTransferDay">
															<form:option value="">..:<spring:message code="pending-merchant-show.label.select"/>:..</form:option>
															<form:option value="D"><spring:message code="pending-merchant-show.label.daily"/></form:option>
															<form:option value="W"><spring:message code="pending-merchant-show.label.weekly"/></form:option>
															<form:option value="M"><spring:message code="pending-merchant-show.label.monthly"/></form:option>

														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoTransferDayEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3" id="weeklySettlement"
														style="display: none;">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.selectdayoftheweek"/><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control"
															onblur="return clientValidation('autoTransferWeeklyDay', 'state','autoTransferWeeklyDayEr');"
															path="autoTransferWeeklyDay" id="autoTransferWeeklyDay">
															<form:option value="">.:<spring:message code="pending-merchant-show.label.select"/>:.</form:option>
															<form:option value="1"><spring:message code="pending-merchant-show.label.monday"/></form:option>
															<form:option value="2"><spring:message code="pending-merchant-show.label.tuesday"/></form:option>
															<form:option value="3"><spring:message code="pending-merchant-show.label.wednesday"/></form:option>
															<form:option value="4"><spring:message code="pending-merchant-show.label.thrusday"/></form:option>
															<form:option value="5"><spring:message code="pending-merchant-show.label.friday"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoTransferWeeklyDayEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3" id="monthlySettlement"
														style="display: none;">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.selectdayofmonth"/><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control"
															onblur="return clientValidation('autoTransferMonthlyDay', 'state','autoTransferMonthlyDayEr');"
															path="autoTransferMonthlyDay" id="autoTransferMonthlyDay">
															<form:option value="">.:<spring:message code="pending-merchant-show.label.select"/>:.</form:option>
															<form:option value="1"><spring:message code="pending-merchant-show.label.1"/></form:option>
															<form:option value="2"><spring:message code="pending-merchant-show.label.2"/></form:option>
															<form:option value="3"><spring:message code="pending-merchant-show.label.3"/></form:option>
															<form:option value="4"><spring:message code="pending-merchant-show.label.4"/></form:option>
															<form:option value="5"><spring:message code="pending-merchant-show.label.5"/></form:option>
															<form:option value="6"><spring:message code="pending-merchant-show.label.6"/></form:option>
															<form:option value="7"><spring:message code="pending-merchant-show.label.7"/></form:option>
															<form:option value="8"><spring:message code="pending-merchant-show.label.8"/></form:option>
															<form:option value="9"><spring:message code="pending-merchant-show.label.9"/></form:option>
															<form:option value="10"><spring:message code="pending-merchant-show.label.10"/></form:option>
															<form:option value="11"><spring:message code="pending-merchant-show.label.11"/></form:option>
															<form:option value="12"><spring:message code="pending-merchant-show.label.12"/></form:option>
															<form:option value="13"><spring:message code="pending-merchant-show.label.13"/></form:option>
															<form:option value="14"><spring:message code="pending-merchant-show.label.14"/></form:option>
															<form:option value="15"><spring:message code="pending-merchant-show.label.15"/></form:option>
															<form:option value="16"><spring:message code="pending-merchant-show.label.16"/></form:option>
															<form:option value="17"><spring:message code="pending-merchant-show.label.17"/></form:option>
															<form:option value="18"><spring:message code="pending-merchant-show.label.18"/></form:option>
															<form:option value="19"><spring:message code="pending-merchant-show.label.19"/></form:option>
															<form:option value="20"><spring:message code="pending-merchant-show.label.20"/></form:option>
															<form:option value="21"><spring:message code="pending-merchant-show.label.21"/></form:option>
															<form:option value="22"><spring:message code="pending-merchant-show.label.22"/></form:option>
															<form:option value="23"><spring:message code="pending-merchant-show.label.23"/></form:option>
															<form:option value="24"><spring:message code="pending-merchant-show.label.24"/></form:option>
															<form:option value="25"><spring:message code="pending-merchant-show.label.25"/></form:option>
															<form:option value="26"><spring:message code="pending-merchant-show.label.26"/></form:option>
															<form:option value="27"><spring:message code="pending-merchant-show.label.27"/></form:option>
															<form:option value="28"><spring:message code="pending-merchant-show.label.28"/></form:option>
															<form:option value="29"><spring:message code="pending-merchant-show.label.29"/></form:option>
															<form:option value="30"><spring:message code="pending-merchant-show.label.30"/></form:option>
															<form:option value="31"><spring:message code="pending-merchant-show.label.31"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoTransferMonthlyDayEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3" id="vantivMerchantId" style="display: none;">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.vantivmerchantid"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control"
															path="litleMID" id="litleMID" maxlength="50"
															onblur="validatelitleMID()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="litleMIDEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.bank"/></label>
												<form:select cssClass="form-control" path="bankId" id="bankId">
														<form:option value="">..:<spring:message code="pending-merchant-show.label.select"/>:..</form:option>
														<c:forEach items="${bankList}" var="bankData">
															<form:option value="${bankData.value}">${bankData.label}</form:option>
														</c:forEach>
													</form:select>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="bankIdEr">&nbsp;</span>
												</div>
											</fieldset>
								<fieldset class="col-sm-3">
								<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.merchantcategorycode"/> 
								<span class="required-field">*</span></label>
							<form:input path="mcc" id="mcc" cssClass="form-control"/>
							<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
									<span class="red-error" id="mccErr">&nbsp;</span>
							</div>
							</fieldset>
							<fieldset class="col-sm-3 hide-localCurrency">
								<label><spring:message code="merchant.label.localcurrency"/></label>
									<form:input path="localCurrency" id="localCurrency" readonly="true" cssClass="form-control"/>
										<div class="discriptionErrorMsg">
											<span id="localCurrencyEr" class="red-error">&nbsp;</span>
										</div>
							</fieldset>
							<!-- Add Issuance Agent Configuration START -->
													
							<fieldset class="col-sm-12" id="issuanceAgentSettings">
								<fieldset class="col-sm-12 padding0 border-style-section">
									<fieldset class="col-sm-12">
										<div class="container-heading-separator">
											<span><spring:message code="merchant.label.issuer.agent.configuration"/></span>
										</div>
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title="">
														<spring:message code="common.label.agentName"/>
													</label>
													<form:select cssClass="form-control" path="agentId"
														id="agentId" onblur="this.value=this.value.trim();return validateAgentName()"
														onchange="fetchAgentData(this.value)">
														<form:option value=""><spring:message code="reports.option.select"/></form:option>
														<c:forEach items="${agentnamesList}" var="agentnames">
															<form:option value="${agentnames.label}">${agentnames.value}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="agentIdErr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-4">
													<label>
													<spring:message code="merchant.label.issuer.agent.accnumber"/></label>
													<form:input cssClass="form-control"
																path="agentAccountNumber" id="agentAccountNumber" 
																onblur="validateAgentAccountNumber()"/>
													<div class="discriptionErrorMsg">
																<span id="agentAccountNumberErrorDiv"
																	class="red-error">&nbsp;</span>
													</div>
													
												</fieldset>
												<fieldset class="col-sm-4">
													<label>
													<spring:message code="merchant.label.issuer.agent.clientid"/></label>
													<form:input cssClass="form-control"
																path="agentClientId" id="agentClientId" 
																onblur="validateAgentClientId()"/>
													<div class="discriptionErrorMsg">
																<span id="agentClientIdErrorDiv"
																	class="red-error">&nbsp;</span>
													</div>
													
												</fieldset>
												<fieldset class="col-sm-4">
													<label>
													<spring:message code="merchant.label.issuer.agent.ani"/></label>
													<form:input cssClass="form-control"
																path="agentANI" id="agentANI" 
																onblur="validateAgentANI()"/>
													<div class="discriptionErrorMsg">
																<span id="agentANIErrorDiv"
																	class="red-error">&nbsp;</span>
													</div>
													
												</fieldset>


												
											</div>
										</div>
									</fieldset>
								</fieldset>
							</fieldset>
													
					<!-- Add Issuance Agent Configuration END-->
													
<!-- ADDED VIRTUAL, POS and ONLINE TERMINALS START-->
													
													<fieldset class="col-sm-12" id="">
														<fieldset class="col-sm-12 padding0 border-style-section">
															<fieldset class="col-sm-12">
																<div class="container-heading-separator">
																	<span><spring:message code="pending-merchant-show.label.supportterminals"/></span>
																</div>
																<div class="row">
																	<div class="field-element-row">

																		<fieldset class="col-sm-5">

																			<form:checkbox path="virtualTerminal" id="virtualTerminal" value="0"
																				onclick="validateVirtualTerminal()" />
																			<spring:message code="pending-merchant-show.label.virtualterminaloptions"/><br>

																			<fieldset id="virtualTerminalOptions">

																				<fieldset class="col-sm-12"><form:checkbox path="refunds" id="refunds" value="0"
																					onclick="validateCheckBox()" />
																				<spring:message code="pending-merchant-show.label.refunds"/></fieldset>
																				<fieldset class="col-sm-12"><form:checkbox path="tipAmount" id="tipAmount"
																					value="0" onclick="validateCheckBox()" />
																				<spring:message code="pending-merchant-show.label.tipamount"/></fieldset>
																				<fieldset class="col-sm-12"><form:checkbox path="taxAmount" id="taxAmount"
																					value="0" onclick="validateCheckBox()" />
																				<spring:message code="pending-merchant-show.label.taxamount"/></fieldset>
																				<fieldset class="col-sm-12"><form:checkbox path="shippingAmount"
																					id="shippingAmount" value="0"
																					onclick="validateCheckBox()" />
																				<spring:message code="pending-merchant-show.label.shippingamount"/></fieldset>
																				<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																					<span id="refundsEr" class="red-error">&nbsp;</span>
																				</div>
																			</fieldset>
																		</fieldset>

																		<fieldset class="col-sm-4">
																			<form:checkbox path="online" id="online" value="0" onclick="validateOnlineOptions()" />
																			<spring:message code="pending-merchant-show.label.online"/>
																			
																			<fieldset id="onlineOptions">

																				<fieldset class="col-sm-12">
																					<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.websiteaddress"/><span class="required-field">*</span></label>
																					<form:input cssClass="form-control"
																						path="webSiteAddress" id="webSiteAddress" 
																						onblur="validateWebSiteAddressURL()"/>
																					<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																						<span id="webSiteAddressErrorDiv"
																							class="red-error">&nbsp;</span>
																					</div>
																				</fieldset>
																				
																				<fieldset class="col-sm-12">
																					<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.returnurl"/><span class="required-field">*</span></label>
																					<form:input cssClass="form-control validate"
																						path="returnUrl" id="returnUrl" 
																						onblur="validateReturnURL()"/>
																					<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																						<span id="returnURLErrorDiv"
																							class="red-error">&nbsp;</span>
																					</div>
																				</fieldset>

																				<fieldset class="col-sm-12 ">
																					<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.cancelurl"/><span class="required-field">*</span></label>
																					<form:input cssClass="form-control validate"
																						path="cancelUrl" id="cancelUrl" 
																						onblur="validateCancelURL()" />
																					<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																						<span id="cancelURLErrorDiv"
																							class="red-error">&nbsp;</span>
																					</div>
																				</fieldset>
																				<fieldset class="col-sm-12">
																					<form:checkbox path="payPageConfig" id="payPageConfig" value="0"/>
																					<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.paypageconfiguration"/></label>
																					<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																						<span id="payPageConfigErr" class="red-error">&nbsp;</span>
																					</div>
																				</fieldset>
																			</fieldset>
																		</fieldset>&nbsp;
																	</div>
																</div>
															</fieldset>
														</fieldset>
													</fieldset>
													
<!-- ADDED VIRTUAL, POS and ONLINE TERMINALS END-->
													
													
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.payoutat"/></label>
														<form:select cssClass="form-control" path="payOutAt"
															id="payOutAt"  onblur="doCheckPayoutAt()">
															<form:option value="">..:<spring:message code="pending-merchant-show.label.select"/>:..</form:option>
															<form:option value="Merchant"><spring:message code="pending-merchant-show.label.merchant"/></form:option>
															<form:option value="Sub-Merchant"><spring:message code="pending-merchant-show.label.submerchant"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="MerchantErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset> 
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input id="showForPos" style="display: none;" type="button"
															class="form-control button pull-right marginL10"
															value="<spring:message code="pending-merchant-show.label.downloadnmasbutton"/>" onclick="downloadNMAS('${merchant.id}')"> 
														<input id="showForRemaining" style="display: none;" type="button"
															class="form-control button pull-right marginL10"
															value="<spring:message code="pending-merchant-show.label.approvebutton"/>" onclick="makeMerchantActive('${merchant.id}')">
														<input type="button"
															class="form-control button pull-right marginL10"
															value="<spring:message code="pending-merchant-show.label.cancelbutton"/>" onclick="goToDashBoard()">
														<input id= "declineButton" type="button"
															class="form-control button pull-right marginL10"
															value="<spring:message code="pending-merchant-show.label.declinebutton"/>" onclick="declineMerchantPopUp('${merchant.id}')">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<input type="hidden" id="linkedPartnerId"
												value=${merchant.issuancePartnerId } /> <input type="hidden"
												id="linkedAgentId" value=${merchant.agentId } />
											<!-- Page Form End -->
										</div>
									</div>
								</form:form>
							</div>
							<!-- Content Block End -->
						</div>
			</article>
			<!--Article Block End-->
			<div class="footer-container">
				<jsp:include page="footer.jsp"></jsp:include>
			</div>
		</div>
		<!--Container block End -->
	</div>
	<div id="declineMerchantDiv" class="locatioin-list-popup">
		<span class="glyphicon glyphicon-remove" onclick="closePopup()"></span>
		<h2><spring:message code="pending-merchant-show.label.declinemerchant"/></h2>
		<form:form action="merchant-decline" name="declineMerchant" method="post">
		<input type="hidden" id="merchantId" name="merchantId" />
        <input type="hidden" id="declineReason" name="declineReason" />
		<label data-toggle="tooltip" data-placement="top" title=""><span class="requiredFiled">*</span><spring:message code="pending-merchant-show.label.reason"/></label>
		<textarea id="reason" name="declineReason" maxlength="100" onblur="validatePopupDesc();clientValidation('reason', 'declineReason','popDescError_div')"></textarea>
		<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
			<span class="red-error" id="popDescError_div">&nbsp;</span>
		</div> 
		<!--Panel Action Button Start -->
		<div class="col-sm-12 form-action-buttons">											
			<div class="col-sm-12">							
				<input type="submit" class="form-control button pull-right" value="<spring:message code="pending-merchant-show.label.submitbutton"/>" onclick="return validatePopupDesc();">																								
			</div>	
		</div>
		</form:form>
	</div>
	
	<!--Body Wrapper block End -->
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/sorting.js"></script>
	<script src="../js/tablesorter.js"></script>
	<script src="../js/tablesorter.widgets.js"></script>
	<script src="../js/merchant.js"></script>
	<script src="../js/chatak-ajax.js"></script>
	<script src="../js/backbutton.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/jquery.popupoverlay.js" type="text/javascript"></script>
	<script src="../js/messages.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>

		/* DatePicker Javascript Strat*/
		$(document).ready(function() {
			showButtonForPosAndRemaining();
			validateVirtualTerminal();
			validateOnlineOptions();
			$("#navListId1").addClass("active-background");
			$(".form-control, input[type=radio], input[type=checkbox], input[type=file]").attr("disabled", "disabled");
			$(".button").removeAttr("disabled");
			loadRadio('${merchant.autoSettlement}');

			if("${merchant.processor}" =="LITLE"){
				$('#vantivMerchantId').show();
			}
			
			 if("${merchant.autoTransferDay}" =="M"){
				 $('#monthlySettlement').show();
			 }
			 
			 if("${merchant.autoTransferDay}" =="W"){
				 $('#weeklySettlement').show();
			 }

			$(".focus-field").click(function() {
				$(this).children('.effectiveDate').focus();
			});
			$('.effectiveDate').datetimepicker({
				timepicker : false,
				format : 'm/d/Y',
				formatDate : 'Y/m/d',
			});
			//showAddSubMerchant();
		});
		/* DatePicker Javascript End*/
		$(".bank-info-details-content, .legal-details-content, .legal-details-rep-content, .free-transactions-content, .atm-transaction-content, .pos-transaction-content").hide();
		$(".account-details-content").show();
		$(".merchant-arrow").show();
		$(".contact-arrow, .bank-info-arrow, .legal-arrow, .legal-rep-arrow, .bank-legal-arrow, .bank-arrow, .configuration-arrow, .final-arrow").hide();
		
		$(".account-details-list, .bank-prev").click(function() {
			$(".merchant-circle-tab").addClass("active-circle");
			$(".bank-info-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .contact-circle-tab, .bank-circle-tab, .final-circle-tab").removeClass("active-circle");
			$(".merchant-arrow").show();
			$(".bank-info-arrow, .legal-arrow, .legal-rep-arrow, .contact-arrow, .bank-arrow, .final-arrow").hide();
			$(".account-details-content").show();
			$(".atm-transaction-content,.bank-info-details-content, .legal-details-content, .legal-details-rep-content, .pos-transaction-content, .free-transactions-content").hide();
		});

		$(".bank-list, .acc-next, .legal-prev").click(function() {
			$(".bank-info-circle-tab").addClass("active-circle");
			$(".merchant-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .contact-circle-tab, .bank-circle-tab, .final-circle-tab").removeClass("active-circle");
			$(".bank-info-arrow").show();
			$(".merchant-arrow, .legal-arrow, .legal-rep-arrow, .contact-arrow, .configuration-arrow, .bank-arrow, .configuration-arrow, .final-arrow").hide();
			$(".bank-info-details-content").show();
			$(".account-details-content, .legal-details-content, .legal-details-rep-content, .atm-transaction-content, .pos-transaction-content, .free-transactions-content").hide();
		});

		$(".legal-entiy-list, .bank-next, .legal-rep-prev").click(function() {
			$(".legal-circle-tab").addClass("active-circle");
			$(".merchant-circle-tab, .bank-info-circle-tab, .legal-circle-rep-tab, .contact-circle-tab, .bank-circle-tab, .final-circle-tab").removeClass("active-circle");
			$(".legal-arrow").show();
			$(".merchant-arrow, .legal-rep-arrow, .bank-info-arrow, .contact-arrow, .configuration-arrow, .bank-arrow, .configuration-arrow, .final-arrow").hide();
			$(".legal-details-content").show();
			$(".account-details-content, .legal-details-rep-content, .bank-info-details-content, .atm-transaction-content, .pos-transaction-content, .free-transactions-content").hide();
		});
		
		$(".legal-entiy-rep-list, .legal-next, .free-prev").click(function() {
			$(".legal-circle-rep-tab").addClass("active-circle");
			$(".merchant-circle-tab, .bank-info-circle-tab, .legal-circle-tab, .contact-circle-tab, .bank-circle-tab, .final-circle-tab").removeClass("active-circle");
			$(".legal-rep-arrow").show();
			$(".merchant-arrow, .bank-info-arrow, .legal-arrow, .contact-arrow, .configuration-arrow, .bank-arrow, .configuration-arrow, .final-arrow").hide();
			$(".legal-details-rep-content").show();
			$(".account-details-content, .bank-info-details-content, .legal-details-content, .atm-transaction-content, .pos-transaction-content, .free-transactions-content").hide();
		});
		
		$(".free-transactions-list, .legal-rep-next, .atm-prev").click(function() {
			$(".contact-circle-tab").addClass("active-circle");
			$(".merchant-circle-tab,.bank-info-circle-tab, .bank-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .final-circle-tab").removeClass("active-circle");
			$(".contact-arrow").show();
			$(".merchant-arrow, .legal-arrow, .legal-rep-arrow, .bank-info-arrow, .configuration-arrow, .bank-arrow, .final-arrow").hide()
			$(".free-transactions-content").show();
			$(".atm-transaction-content, .legal-details-content, .legal-details-rep-content, .bank-info-details-content, .pos-transaction-content, .account-details-content").hide();
		});
		
		$(".atm-transactions-list, .free-next, .pos-prev").click(function() {
			$(".bank-circle-tab").addClass("active-circle");
			$(".merchant-circle-tab,.bank-info-circle-tab, .contact-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .final-circle-tab").removeClass("active-circle");
			$(".configuration-arrow").show();
			$(".contact-arrow, .merchant-arrow, .legal-arrow, .legal-rep-arrow, .bank-info-arrow, .final-arrow").hide()
			$(".atm-transaction-content").show();
			$(".free-transactions-content, .bank-info-details-content, .legal-details-content, .legal-details-rep-content, .pos-transaction-content, .account-details-content").hide();
		});
		
		$(".pos-transactions-list, .atm-next").click(function() {
			$(".final-circle-tab").addClass("active-circle");
			$(".merchant-circle-tab, .bank-info-circle-tab, .contact-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .bank-circle-tab").removeClass("active-circle");
			$(".final-arrow").show();
			$(".contact-arrow, .bank-arrow,.configuration-arrow, .bank-info-arrow, .legal-arrow, .legal-rep-arrow, .merchant-arrow").hide()
			$(".pos-transaction-content").show();
			$(".free-transactions-content, .bank-info-details-content, .legal-details-content, .legal-details-rep-content, .atm-transaction-content, .account-details-content").hide();
		});

		function loadRadio(data) {
			if (data == '0') {
				$("#noAutoSettlement").prop("checked", true);
				$("#allowAutoSettlement").prop("checked", false);
			} else if (data == '1') {
				$("#noAutoSettlement").prop("checked", false);
				$("#allowAutoSettlement").prop("checked", true);
			}
		}
		
		function showAddSubMerchant() {
			if (checkStatusAndMerchantType()) {
				$('#subMerchant').show();
			} else {
				$('#subMerchant').hide();
			}
		}
		
		function makeMerchantActive(id){
			get('getMerchantId').value = id;
			document.forms["merchantActive"].submit();
		}
		
		function closePopup() {
		
			$('#declineMerchantDiv').find("[id$='_ErrorDiv']").each(function() {
				$(this).text('');
			});
		 
			$('#declineMerchantDiv').popup("hide");
		}
		
	</script>
</body>
</html>
