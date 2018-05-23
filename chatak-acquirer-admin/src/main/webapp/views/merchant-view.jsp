<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.Calendar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%
  int year = Calendar.getInstance().get(Calendar.YEAR);
%>
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
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="manage.label.manage"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="merchant.label.merchant"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span>
						<span class="breadcrumb-text"><spring:message code="common.label.view"/></span>
					</div>
					<!-- <div class=" pull-right" style="margin-right: 40px"
						id="subMerchant">
						<img alt="Create sub merchant" src="../images/user_icon.png"><a
							href="sub-merchant-create">Add Sub Merchant</a>
					</div> -->
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first">
						<a href="merchant-search-page"><spring:message code="common.label.search"/></a>
					</div>
					<div class="tab-header-container active-background">
						<a href="#"><spring:message code="common.label.view"/></a>
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
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.basciinfo"/></label>
										<div class="arrow-down merchant-arrow"></div>
									</li>
									<li class="bank-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="bank-info-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.bankinfo"/> </label>
										<div class="arrow-down bank-info-arrow"></div>
									</li>
									<li class="legal-entiy-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="legal-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.legalentityrep"/></label>
										<div class="arrow-down legal-arrow"></div>
									</li>
									<li class="legal-entiy-rep-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="legal-circle-rep-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.legalentity"/></label>
										<div class="arrow-down legal-rep-arrow"></div>
									</li>
									<li class="free-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="contact-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.additonalinfo"/></label>
										<div class="arrow-down contact-arrow"></div>
									</li>
									<li class="atm-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="bank-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.configuration"/></label>
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
									<div class="discriptionErrorMsg col-xs-12">
										<span class="red-error">&nbsp;${error }</span> <span
											class="green-error">&nbsp;${sucess }</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="updateMerchant" commandName="merchant" name="merchant">
									<div class="col-sm-12 paddingT20">
										<div class="row">
											<!-- Account Details Content Start -->
											<jsp:include page="merchant-view-remaining.jsp"></jsp:include>
											<!-- Account Details Content End -->
											<!-- Free Transactions Content Start -->
											<section class="field-element-row bank-info-details-content"
												style="display: none;">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.name"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="bankAccountName"
															id="bankAccountName" maxlength="50"
															onblur="return clientValidation('bankAccountName', 'first_name_SplChar','bankAccountNameErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAccountNameErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.bankroutingnumber"/><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control"
															path="bankRoutingNumber" id="bankRoutingNumber"
															maxlength="9"
															onkeypress="return amountValidate(this,event)"
															onblur="return clientValidation('bankRoutingNumber', 'routing_number','bankRoutingNumberEr');" />
														<!-- onblur="return validRoutingNumber()"  -->
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankRoutingNumberEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.bankaccountnumber"/><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control"
															path="bankAccountNumber" id="bankAccountNumber"
															onblur="return clientValidation('bankAccountNumber', 'account_numberBank','bankAccountNumberErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAccountNumberErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.type"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control"
															path="bankAccountType" id="bankAccountType"
															onblur="return clientValidation('bankAccountType', 'account_type','bankAccountTypeErrorDiv');">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<form:option value="S"><spring:message code="merchantaccount.label.savings"/></form:option>
															<form:option value="C"><spring:message code="merchantaccount.label.checking"/></form:option>
														</form:select>

														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAccountTypeErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.address1"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="bankAddress1"
															id="bankAddress1" maxlength="50"
															onblur="return clientValidation('bankAddress1', 'bank_address2','bankAddress1ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAddress1ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.address2"/></label>
														<form:input cssClass="form-control" path="bankAddress2"
															id="bankAddress2" maxlength="50"
															onblur="return clientValidation('bankAddress2', 'bank_address2','bankAddress2ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAddress2ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.city"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="bankCity"
															id="bankCity" maxlength="50"
															onblur="return clientValidation('bankCity', 'bank_address2','bankCityErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankCityErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.country"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="bankCountry"
															id="bankCountry"
															onblur="return clientValidation('bankCountry', 'country','bankCountryErrorDiv');"
															onchange="fetchMerchantState(this.value, 'bankState')">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<c:forEach items="${countryList}" var="country">
																<form:option value="${country.label}">${country.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankCountryErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.state"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="bankState"
															id="bankState"
															onblur="return clientValidation('bankState', 'state','bankStateErrorDiv');">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<c:forEach items="${bankStateList}" var="item">
																<form:option value="${item.label}">${item.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankStateErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.zipcode"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="bankPin"
															id="bankPin" maxlength="10"
															onblur="return clientValidation('bankPin', 'zip_code','bankPinErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankPinErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.nameonaccount"/><span class="required-field">*</span></label>

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
														<form:select cssClass="form-control" path="currencyId"
															id="currencyId">
															<form:option  value ="${merchant.currencyId}">${merchant.currencyId}</form:option>
														</form:select>
														<div class="discriptionErrorMsg">
															<span id="countryEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															value='<spring:message code="common.label.continue"/>' class="form-control button pull-right bank-next"
															> <input type="button"
															value='<spring:message code="common.label.previous"/>' class="form-control button pull-right marginL10 bank-prev"
															> <input type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.cancel"/>' onclick="goToMerchantSearch()">
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
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.SSN"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalSSN"
															onkeypress="return amountValidate(this,event)"
															id="legalSSN" maxlength="20" />
														<%-- onblur="return clientValidation('legalSSN', 'ssn','legalSSNErrorDiv');" --%>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalSSNErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.firstname"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalFirstName"
															id="legalFirstName" maxlength="50" />
														<!-- onblur="return clientValidation('legalFirstName', 'first_name_NotMand','legalFirstNameErrorDiv');" -->
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalFirstNameErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.lastname"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalLastName"
															id="legalLastName" maxlength="50" />
														<!-- onblur="return clientValidation('legalLastName', 'first_name_NotMand','legalLastNameErrorDiv');" -->
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalLastNameErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.mobilephone"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control"
															path="legalMobilePhone" id="legalMobilePhone" />
														<!-- onblur="return clientValidation('legalMobilePhone', 'mobile_phone','legalMobilePhoneErrorDiv');" -->
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalMobilePhoneErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""> <spring:message code="merchant.label.dateofbirth"/><!-- <span class="required-field">*</span> --></label>
														<div class="input-group focus-field">
															<form:input cssClass="form-control effectiveDate"
																path="legalDOB" id="legalDOB" />
															<%-- onblur="return clientValidation('legalDOB', 'startDate','legalDOBErrorDiv');" --%>
															<span class="input-group-addon"><span
																class="glyphicon glyphicon-calendar"></span></span>
														</div>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalDOBErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.passportnumber"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalPassport"
															id="legalPassport" maxlength="20" />
														<%-- onblur="return clientValidation('legalPassport', 'bank_code','legalPassportErrorDiv');" --%>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalPassportErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.countryresidence"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control"
															path="legalCountryResidence" id="legalCountryResidence"
															maxlength="50" />
														<%--  onblur="return clientValidation('legalCountryResidence', 'bank_country','legalCountryResidenceErrorDiv');" --%>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalCountryResidenceErrorDiv"
																class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.countrycitizenship"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalCitizen"
															id="legalCitizen" />
														<%-- onblur="return clientValidation('legalCitizen', 'bank_country','legalCitizenErrorDiv');" --%>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalCitizenErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.homephone"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalHomePhone"
															onkeypress="return amountValidate(this,event)"
															id="legalHomePhone" />
														<%-- onblur="return clientValidation('legalHomePhone', 'contact_phone','legalHomePhoneErrorDiv');" --%>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalHomePhoneErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															value='<spring:message code="common.label.continue"/>' class="form-control button pull-right legal-next"
															> <input type="button"
															value='<spring:message code="common.label.previous"/>' class="form-control button pull-right marginL10 legal-prev"
															> <input type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.cancel"/>' onclick="goToMerchantSearch()">
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
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.entityLegalName"/><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalName"
															id="legalName" maxlength="50"
															onblur="return clientValidation('legalName', 'first_name_SplChar','legalNameErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalNameErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.EIN/TaxID"/>:<span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalTaxId"
															id="legalTaxId" maxlength="50"
															onblur="return clientValidation('legalTaxId', 'eIN_taxId','legalTaxIdErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalTaxIdErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.type"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="legalType"
															id="legalType"
															onblur="return clientValidation('legalType', 'state','legalTypeErrorDiv');">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<form:option value="1"><spring:message code="merchant.label.association"/></form:option>
															<form:option value="2"><spring:message code="merchant.label.corporation"/></form:option>
															<form:option value="3"><spring:message code="merchant.label.governmentagency"/></form:option>
															<form:option value="4"><spring:message code="merchant.label.individualsoleproprietorship"/></form:option>
															<form:option value="5"><spring:message code="merchant.label.internationalorginization"/></form:option>
															<form:option value="6"><spring:message code="merchant.label.limitedliabilitycompany(LLC)"/></form:option>
															<form:option value="7"><spring:message code="merchant.label.partnership"/></form:option>
															<form:option value="8"><spring:message code="merchant.label.taxexemptorganization(501C)"/></form:option>
															<form:option value="11"><spring:message code="merchant.label.testing"/></form:option>
														</form:select>

														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalTypeErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.expectedannualcardsales"/><span
															class="required-field">*</span></label>
															<fmt:formatNumber type="currency" value="${merchant.legalAnnualCard}" pattern="<%=Constants.AMOUNT_FORMAT%>" var="legalAnnualCard" />
															<input name="legalAnnualCard" id="legalAnnualCard" value="${ legalAnnualCard}" cssClass="form-control" onblur="this.value=this.value.trim();appendDollarSymbol();return clientValidation('legalAnnualCard', 'dollar_amount','legalAnnualCardErrorDiv');"disabled="disabled"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalAnnualCardErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.address1"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalAddress1"
															id="legalAddress1"
															onblur="return clientValidation('legalAddress1', 'bank_address1','legalAddress1ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalAddress1ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.address2"/></label>
														<form:input cssClass="form-control" path="legalAddress2"
															id="legalAddress2"
															onblur="return clientValidation('legalAddress2', 'bank_address2','legalAddress2ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalAddress2ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.city"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalCity"
															id="legalCity"
															onblur="return clientValidation('legalCity', 'bank_city','legalCityErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalCityErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.country"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="legalCountry"
															id="legalCountry"
															onblur="return clientValidation('legalCountry', 'country','legalCountryErrorDiv');"
															onchange="fetchMerchantState(this.value, 'legalState')">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<c:forEach items="${countryList}" var="country">
																<form:option value="${country.label}">${country.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalCountryErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.state"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="legalState"
															id="legalState"
															onblur="return clientValidation('legalState', 'state','legalStateErrorDiv');">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<c:forEach items="${legalStateList}" var="state">
																<form:option value="${state.label}">${state.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalStateErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.zipcode"/><span class="required-field">*</span></label>
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
															value='<spring:message code="common.label.continue"/>' class="form-control button pull-right legal-rep-next"
															> <input type="button"
															value='<spring:message code="common.label.previous"/>' class="form-control button pull-right marginL10 legal-rep-prev"
															> <input type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.cancel"/>' onclick="goToMerchantSearch()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<section class="field-element-row free-transactions-content"
												style="display: none;">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.username"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="userName"
															id="userName" maxlength="50" />
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
															value='<spring:message code="common.label.continue"/>' class="form-control button pull-right free-next"
															> <input type="button"
															value='<spring:message code="common.label.previous"/>' class="form-control button pull-right marginL10 free-prev"
															> <input type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.cancel"/>' onclick="goToMerchantSearch()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<section class="field-element-row atm-transaction-content"
												style="display: none;">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-12">
													<fieldset class="col-sm-4">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.autosettlementoptions"/><span
															class="required-field">*</span></label><br> <input
															type="radio" id="allowAutoSettlement"
															name="autoSettlement" value="1" onclick="validateRadio()">Yes
														<input type="radio" id="noAutoSettlement"
															name="autoSettlement" value="0" onclick="validateRadio()">No
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="noAutoSettlementEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<%-- <form:checkbox path="allowAdvancedFraudFilter" id="allowAdvancedFraudFilter"
															value="0" onclick="validateAdvancedFraud()" />
															<spring:message code="merchant.label.allowadvancedfraudsettings"/>
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="" class="red-error">&nbsp;</span>
															</div> --%>
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
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.feeprogram"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="feeProgram"
															id="feeProgram" onblur="validatefeeProgram()">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<c:forEach items="${feeprogramnames}" var="feename">
																<form:option value="${feename.label}">${feename.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="feeProgramEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.processor"/><span class="required-field">*</span></label>
														<!-- <select class="form-control">
															<option>..:Select:..</option>
														</select> -->
														<form:select cssClass="form-control" path="processor"
															id="processor" onblur="validateProcessor()">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<c:forEach items="${processorNames}" var="processorName">
																<form:option value="${processorName.value}">${processorName.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="processorEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.merchantcallbackURL"/><!-- <span
															class="required-field">*</span> --></label>
														<form:input cssClass="form-control"
															path="merchantCallBackURL" id="merchantCallBackURL"
															onblur="validateCallbackURL()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="merchantCallBackURLEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.category"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="category"
															id="category" disabled="true">
														<form:option value="primary"><spring:message code="merchantaccount.label.primary"/></form:option>
														<%-- <form:option value="secondary"><spring:message code="merchantaccount.label.secondary"/></form:option> --%>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="categoryEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.autotransferlimit"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control"
															path="autoTransferLimit" id="autoTransferLimit"
															onblur="validateAutoTransferLimit()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoTransferLimitEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.autopaymentmethod"/> <span
															class="required-field">*</span></label>
														<form:select cssClass="form-control"
															path="autoPaymentMethod" id="autoPaymentMethod"
															onblur="validateAutoPaymentMethod()">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<form:option value="EFT"><spring:message code="merchantaccount.label.EFT"/></form:option>
															<form:option value="Cheque"><spring:message code="merchantaccount.label.cheque"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoPaymentMethodEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.autotransferperiod"/></label>
														<form:select cssClass="form-control"
															onchange="showAutoTransferDayFields()"
															path="autoTransferDay" id="autoTransferDay">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<form:option value="D"><spring:message code="merchantaccount.label.daily"/></form:option>
															<form:option value="W"><spring:message code="merchantaccount.label.weekly"/></form:option>
															<form:option value="M"><spring:message code="merchantaccount.label.monthly"/></form:option>

														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoTransferDayEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3" id="weeklySettlement"
														style="display: none;">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.selectdayoftheweek"/><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control"
															onblur="return clientValidation('autoTransferWeeklyDay', 'state','autoTransferWeeklyDayEr');"
															path="autoTransferWeeklyDay" id="autoTransferWeeklyDay">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<form:option value="2"><spring:message code="merchantaccount.label.monday"/></form:option>
															<form:option value="3"><spring:message code="merchantaccount.label.tuesday"/></form:option>
															<form:option value="4"><spring:message code="merchantaccount.label.wednesday"/></form:option>
															<form:option value="5"><spring:message code="merchantaccount.label.thursday"/></form:option>
															<form:option value="6"><spring:message code="merchantaccount.label.friday"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoTransferWeeklyDayEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3" id="monthlySettlement"
														style="display: none;">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.selectdayofmonth"/><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control"
															onblur="return clientValidation('autoTransferMonthlyDay', 'state','autoTransferMonthlyDayEr');"
															path="autoTransferMonthlyDay" id="autoTransferMonthlyDay">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<form:option value="1">1</form:option>
															<form:option value="2">2</form:option>
															<form:option value="3">3</form:option>
															<form:option value="4">4</form:option>
															<form:option value="5">5</form:option>
															<form:option value="6">6</form:option>
															<form:option value="7">7</form:option>
															<form:option value="8">8</form:option>
															<form:option value="9">9</form:option>
															<form:option value="10">10</form:option>
															<form:option value="11">11</form:option>
															<form:option value="12">12</form:option>
															<form:option value="13">13</form:option>
															<form:option value="14">14</form:option>
															<form:option value="15">15</form:option>
															<form:option value="16">16</form:option>
															<form:option value="17">17</form:option>
															<form:option value="18">18</form:option>
															<form:option value="19">19</form:option>
															<form:option value="20">20</form:option>
															<form:option value="21">21</form:option>
															<form:option value="22">22</form:option>
															<form:option value="23">23</form:option>
															<form:option value="24">24</form:option>
															<form:option value="25">25</form:option>
															<form:option value="26">26</form:option>
															<form:option value="27">27</form:option>
															<form:option value="28">28</form:option>
															<form:option value="29">29</form:option>
															<form:option value="30">30</form:option>
															<form:option value="31">31</form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoTransferMonthlyDayEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3" id="vantivMerchantId" style="display: none;">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.vantivmerchantId"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control"
															path="litleMID" id="litleMID" maxlength="50"
															onblur="validatelitleMID()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="litleMIDEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.bank"/> </label>
												<form:select cssClass="form-control" path="bankId" id="bankId">
														<form:option value=""><spring:message code="reports.option.select"/></form:option>
														<c:forEach items="${bankList}" var="bankData">
															<form:option value="${bankData.value}">${bankData.label}</form:option>
														</c:forEach>
													</form:select>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="bankIdEr">&nbsp;</span>
												</div>
											</fieldset>
											<%-- <fieldset class="col-sm-3">
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.reseller"/> </label>
												<form:select cssClass="form-control" path="resellerId" id="resellerId">
														<form:option value=""><spring:message code="reports.option.select"/></form:option>
														<c:forEach items="${resellerList}" var="resellerData">
															<form:option value="${resellerData.value}">${resellerData.label}</form:option>
														</c:forEach>
													</form:select>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="resellerIdEr">&nbsp;</span>
												</div>
											</fieldset> --%>
											<fieldset class="col-sm-3">
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.merchantcategorycode"/> <span
														class="required-field">*</span> </label>
												<form:select cssClass="form-control" path="mcc" id="mcc" onblur="validateMcc()">
														<form:option value=""><spring:message code="reports.option.select"/></form:option>
														<c:forEach items="${mccList}" var="mccList">
															<form:option value="${mccList}">${mccList}</form:option>
														</c:forEach>
													</form:select>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span id="mccErr" class="red-error">&nbsp;</span>
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
																		<span class="required-field">*</span>
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
																	<spring:message code="merchant.label.issuer.agent.accnumber"/><span class="required-field">*</span></label>
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
																	<spring:message code="merchant.label.issuer.agent.clientid"/><span class="required-field">*</span></label>
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
																	<spring:message code="merchant.label.issuer.agent.ani"/><span class="required-field">*</span></label>
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
																	<span><spring:message code="merchant.label.supportterminals"/></span>
																</div>
																<div class="row">
																	<div class="field-element-row">

																		<fieldset class="col-sm-5">

																			<form:checkbox path="virtualTerminal" id="virtualTerminal" value="0"
																				onclick="validateVirtualTerminal()" />
																			<spring:message code="merchant.label.virtualterminaloptions"/><br>

																			<fieldset id="virtualTerminalOptions">

																				<fieldset class="col-sm-12"><form:checkbox path="refunds" id="refunds" value="0"
																					onclick="validateCheckBox()" />
																				<spring:message code="merchant.label.refunds"/></fieldset>
																				<fieldset class="col-sm-12"><form:checkbox path="tipAmount" id="tipAmount"
																					value="0" onclick="validateCheckBox()" />
																				<spring:message code="merchant.label.tipamount"/></fieldset>
																				<fieldset class="col-sm-12"><form:checkbox path="taxAmount" id="taxAmount"
																					value="0" onclick="validateCheckBox()" />
																				<spring:message code="merchant.label.taxamount"/></fieldset>
																				<fieldset class="col-sm-12"><form:checkbox path="shippingAmount"
																					id="shippingAmount" value="0"
																					onclick="validateCheckBox()" />
																				<spring:message code="merchant.label.shippingamount"/></fieldset>
																				<%-- <fieldset class="col-sm-12"><form:checkbox path="allowRepeatSale"
																					id="allowRepeatSale" value="0"
																					onclick="validateCheckBox()" />
																				<spring:message code="merchant.label.allowrepeatsale"/></fieldset>
																				<fieldset class="col-sm-12"><form:checkbox path="showRecurringBilling"
																					id="showRecurringBilling" value="0"
																					onclick="validateCheckBox()" />
																				<spring:message code="merchant.label.showrecurringbilling"/></fieldset> --%>
																				<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																					<span id="refundsEr" class="red-error">&nbsp;</span>
																				</div>
																			</fieldset>
																		</fieldset>

																		<%-- <fieldset class="col-sm-4">
																			<form:checkbox path="posTerminal" id="posTerminal" value="0" onclick="validatePos()" />
																			<spring:message code="merchant.label.pos"/>
																		</fieldset> --%>
																		

																		<fieldset class="col-sm-4">
																			<form:checkbox path="online" id="online" value="0" onclick="validateOnlineOptions()" />
																			<spring:message code="merchant.label.online"/>
																			
																			<fieldset id="onlineOptions">

																				<fieldset class="col-sm-12">
																					<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.websiteaddress"/><span class="required-field">*</span></label>
																					<form:input cssClass="form-control"
																						path="webSiteAddress" id="webSiteAddress" 
																						onblur="validateWebSiteAddressURL()" />
																					<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																						<span id="webSiteAddressErrorDiv"
																							class="red-error">&nbsp;</span>
																					</div>
																				</fieldset>
																				
																				<fieldset class="col-sm-12">
																					<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.returnURL"/><span class="required-field">*</span></label>
																					<form:input cssClass="form-control validate"
																						path="returnUrl" id="returnUrl" 
																						onblur="validateReturnURL()" />
																					<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																						<span id="returnURLErrorDiv"
																							class="red-error">&nbsp;</span>
																					</div>
																				</fieldset>

																				<fieldset class="col-sm-12 ">
																					<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.cancelURL"/><span class="required-field">*</span></label>
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
																					<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.paypageconfiguration"/></label>
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
													
													
													<%-- <fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Partner Details</label>
														<form:select cssClass="form-control"
															path="issuancePartnerId" id="partnerId">
															<form:option value="">..:Select:..</form:option>
															<c:forEach items="${partners}" var="partner">
																<form:option value="${partner.value}">${partner.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="partnerIdErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>--%>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.payOutat"/></label>
														<form:select cssClass="form-control" path="payOutAt"
															id="payOutAt"  onblur="doCheckPayoutAt()">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<form:option value="Merchant"><spring:message code="merchant.label.merchant"/></form:option>
															<form:option value="Sub-Merchant"><spring:message code="merchant.label.submerchant"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="MerchantErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset> 
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															value='<spring:message code="common.label.previous"/>' class="form-control button pull-right marginL10 atm-prev"
															> <input type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.cancel"/>' onclick="goToMerchantSearch()">
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
	<!--Body Wrapper block End -->
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/sorting.js"></script>
	<script src="../js/tablesorter.js"></script>
	<script src="../js/tablesorter.widgets.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/chatak-ajax.js"></script>
	<script src="../js/backbutton.js"></script>
	<script src="../js/merchant.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>

		/* DatePicker Javascript Strat*/
		$(document).ready(function() {
			validateVirtualTerminal();
			validateOnlineOptions();
			$("#navListId6").addClass("active-background");
			/* populatePartnerAndAgentDetails($('#appMode').val(),'merchant', 'view', false); */
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
			//populatePartnerAndAgentDetails($('#appMode').val(),'merchant','view');
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
	</script>
</body>
</html>
