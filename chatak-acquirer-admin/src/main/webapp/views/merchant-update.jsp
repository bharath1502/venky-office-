<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.Calendar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page pageEncoding="UTF-8"%>
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
	type="text/css">
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

			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message
								code="manage.label.manage" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message
								code="merchant.label.merchant" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message
								code="common.label.edit" /></span>
					</div>
					<!-- <div class=" pull-right" style="margin-right:40px" id="subMerchant">
						<img alt="Create sub merchant" src="../images/user_icon.png"><a href="sub-merchant-create">Add Sub Merchant</a>
					</div> -->
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first">
						<a href="merchant-search-page"><spring:message
								code="common.label.search" /></a>
					</div>
					<div class="tab-header-container active-background">
						<a href="#"><spring:message code="common.label.edit" /></a>
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
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message
												code="merchant.label.basciinfo" /></label>
										<div class="arrow-down merchant-arrow"></div>
									</li>
									<li class="bank-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="bank-info-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message
												code="merchant.label.bankinfo" /></label>
										<div class="arrow-down bank-info-arrow"></div>
									</li>
									<li class="legal-entiy-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="legal-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message
												code="merchant.label.legalentityrep" /></label>
										<div class="arrow-down legal-arrow"></div>
									</li>
									<li class="legal-entiy-rep-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="legal-circle-rep-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message
												code="merchant.label.legalentity" /></label>
										<div class="arrow-down legal-rep-arrow"></div>
									</li>
									<li class="free-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="contact-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message
												code="merchant.label.additonalinfo" /></label>
										<div class="arrow-down contact-arrow"></div>
									</li>
									<li class="atm-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="bank-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message
												code="merchant.label.configuration" /></label>
										<div class="arrow-down configuration-arrow"></div>
									</li>
									<li class="pos-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="final-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message
												code="merchant.label.confirmation" /></label>
										<div class="arrow-down final-arrow"></div>
									</li>
								</ul>
							</div>
						</div>
						<!-- Page Menu End -->
						<div class="row margin0">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="descriptionMsg col-xs-12">
										<span class="red-error">&nbsp;${error }</span> <span
											class="green-error">&nbsp;${sucess }</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="updateMerchant" commandName="merchant"
									name="merchant">
									<input type="hidden" id="currencyCode" name="currencyCode">
									<div class="col-sm-12 paddingT20">
										<div class="row">
											<!-- Account Details Content Start -->
											<jsp:include page="merchant-update-remaning.jsp"></jsp:include>
											<!-- Account Details Content End -->

											<!-- Legal Details Content Start -->
											<section class="field-element-row legal-details-rep-content"
												style="display: none;">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="merchant.label.entityLegalName" /><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalName"
															id="legalName" maxlength="101"
															onblur="this.value=this.value.trim();return clientValidation('legalName','first_name_SplChar','legalNameErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip"
															data-placement="top" title="">
															<span id="legalNameErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="merchant.label.EIN/TaxID" /><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalTaxId"
															id="legalTaxId" maxlength="50"
															onblur="this.value=this.value.trim();return clientValidation('legalTaxId', 'eIN_taxId','legalTaxIdErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip"
															data-placement="top" title="">
															<span id="legalTaxIdErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="merchant.label.type" /><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control" path="legalType"
															id="legalType"
															onblur="return clientValidation('legalType', 'state','legalTypeErrorDiv');">
															<form:option value="">
																<spring:message code="reports.option.select" />
															</form:option>
															<form:option value="1">
																<spring:message code="merchant.label.association" />
															</form:option>
															<form:option value="2">
																<spring:message code="merchant.label.corporation" />
															</form:option>
															<form:option value="3">
																<spring:message code="merchant.label.governmentagency" />
															</form:option>
															<form:option value="4">
																<spring:message
																	code="merchant.label.individualsoleproprietorship" />
															</form:option>
															<form:option value="5">
																<spring:message
																	code="merchant.label.internationalorginization" />
															</form:option>
															<form:option value="6">
																<spring:message
																	code="merchant.label.limitedliabilitycompany(LLC)" />
															</form:option>
															<form:option value="7">
																<spring:message code="merchant.label.partnership" />
															</form:option>
															<form:option value="8">
																<spring:message
																	code="merchant.label.taxexemptorganization(501C)" />
															</form:option>
															<form:option value="11">
																<spring:message code="merchant.label.testing" />
															</form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip"
															data-placement="top" title="">
															<span id="legalTypeErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="merchant.label.expectedannualcardsales" /><span
															class="required-field">*</span></label> 
															<fmt:formatNumber type="currency" value="${merchant.legalAnnualCard}" pattern="<%=Constants.AMOUNT_FORMAT%>" var="legalAnnualCard" />
														<input name="legalAnnualCard" id="legalAnnualCard"
															cssClass="form-control" value="${ legalAnnualCard}" maxlength="12"
															onblur="this.value=this.value.trim();appendDollarSymbol();return clientValidation('legalAnnualCard', 'dollar_amount','legalAnnualCardErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip"
															data-placement="top" title="">
															<span id="legalAnnualCardErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="common.label.address1" /><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalAddress1"
															id="legalAddress1"
															onblur="this.value=this.value.trim();return clientValidation('legalAddress1', 'bank_address1','legalAddress1ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip"
															data-placement="top" title="">
															<span id="legalAddress1ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="common.label.address2" /></label>
														<form:input cssClass="form-control" path="legalAddress2"
															id="legalAddress2"
															onblur="this.value=this.value.trim();return clientValidation('legalAddress2', 'bank_address2','legalAddress2ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip"
															data-placement="top" title="">
															<span id="legalAddress2ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="common.label.city" /><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalCity"
															id="legalCity"
															onblur="this.value=this.value.trim();return clientValidation('legalCity', 'bank_city','legalCityErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip"
															data-placement="top" title="">
															<span id="legalCityErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="common.label.country" /><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control" path="legalCountry"
															id="legalCountry"
															onblur="return clientValidation('legalCountry', 'country','legalCountryErrorDiv');"
															onchange="fetchMerchantState(this.value, 'legalState')">
															<form:option value="">
																<spring:message code="reports.option.select" />
															</form:option>
															<c:forEach items="${countryList}" var="country">
																<form:option value="${country.label}">${country.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip"
															data-placement="top" title="">
															<span id="legalCountryErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="common.label.state" /><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="legalState"
															id="legalState"
															onblur="return clientValidation('legalState', 'state','legalStateErrorDiv');">
															<form:option value="">
																<spring:message code="reports.option.select" />
															</form:option>
															<c:forEach items="${legalStateList}" var="state">
																<form:option value="${state.label}">${state.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip"
															data-placement="top" title="">
															<span id="legalStateErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="common.label.zipcode" /><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalPin"
															onkeypress="generalZipCode()" id="legalPin" maxlength="7"
															onblur="this.value=this.value.trim();return zipCodeNotEmpty(id)" />
														<div class="discriptionErrorMsg" data-toggle="tooltip"
															data-placement="top" title="">
															<span id="legalPinEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															value='<spring:message code="common.label.continue"/>'
															class="form-control button pull-right legal-rep-next"
															onclick="return zipCodeNotEmpty('legalPin')">
														<input type="button"
															value='<spring:message code="common.label.previous"/>'
															class="form-control button pull-right marginL10 legal-rep-prev">
														<input type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.cancel"/>'
															onclick="openCancelConfirmationPopup()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<!-- Free Transactions Content Start -->
											<section class="field-element-row free-transactions-content"
												style="display: none;">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="merchant.label.username" /><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="userName"
															id="userName" maxlength="50" readonly="true" />
														<div class="discriptionErrorMsg" data-toggle="tooltip"
															data-placement="top" title="">
															<span id="userNameEr" class="red-error">&nbsp;</span> <span
																id="userNamegreenEr" class="green-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															value='<spring:message code="common.label.continue"/>'
															class="form-control button pull-right free-next">
														<input type="button"
															value='<spring:message code="common.label.previous"/>'
															class="form-control button pull-right marginL10 free-prev">
														<input type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.cancel"/>'
															onclick="openCancelConfirmationPopup()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<!-- Free Transactions Content End -->
											<!-- ATM Transactions Content Start -->
											<section class="field-element-row atm-transaction-content"
												style="display: none;">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-12">
														<fieldset class="col-sm-4">
															<label data-toggle="tooltip" data-placement="top"
																title=""><spring:message
																	code="merchant.label.autosettlementoptions" /><span
																class="required-field">*</span></label><br> <input
																type="radio" id="allowAutoSettlement"
																name="autoSettlement" value="1"
																onclick="validateRadio()">Yes <input
																type="radio" id="noAutoSettlement" name="autoSettlement"
																value="0" onclick="validateRadio()">No
															<div class="discriptionErrorMsg" data-toggle="tooltip"
																data-placement="top" title="">
																<span id="noAutoSettlementEr" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
														<%-- <fieldset class="col-sm-3">
														<form:checkbox path="dccEnable" id="dcc_enable" />
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.DCCenable"/></label>
														</fieldset>
														<form:checkbox path="allowAdvancedFraudFilter"
															id="allowAdvancedFraudFilter" value="0"
															onclick="validateAdvancedFraud()" />
														<spring:message code="merchant.label.allowadvancedfraudsettings"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="" class="red-error">&nbsp;</span>
														</div> --%>
													</fieldset>
												</fieldset>

												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="merchant.label.merchanttype" /><span
														class="required-field">*</span></label>
													<form:select cssClass="form-control"
														path="merchantCategory" id="merchantCategory"
														onblur="validateMerchantCategory()">
														<form:option value="">
															<spring:message code="reports.option.select" />
														</form:option>
														<form:option value="Individual">
															<spring:message code="merchant.label.individual" />
														</form:option>
														<form:option value="Group">
															<spring:message code="merchant.label.group" />
														</form:option>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="merchantCategoryEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="merchant.label.feeprogram" /><span
														class="required-field">*</span></label>
													<form:select cssClass="form-control" path="feeProgram"
														id="feeProgram" onblur="validatefeeProgram()">
														<form:option value="">
															<spring:message code="reports.option.select" />
														</form:option>
														<c:forEach items="${feeprogramnames}" var="feename">
															<form:option value="${feename.label}">${feename.label}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="feeProgramEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="merchant.label.processor" /><span
														class="required-field">*</span></label>
													<form:select cssClass="form-control" path="processor"
														id="processor" onblur="validateProcessor()">
														<form:option value="">
															<spring:message code="reports.option.select" />
														</form:option>
														<c:forEach items="${processorNames}" var="processorName">
															<form:option value="${processorName.value}">${processorName.label}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="processorEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="merchant.label.merchantcallbackURL" /></label>
													<form:input cssClass="form-control"
														path="merchantCallBackURL" id="merchantCallBackURL"
														maxlength="50"
														onblur="this.value=this.value.trim();validateCallbackURL()" />
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="merchantCallBackURLEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="merchant.label.category" /><span
														class="required-field">*</span></label>
													<form:input path="category" id="category"
														cssClass="form-control" readonly="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="categoryEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="merchant.label.autotransferlimit" />
														<!-- <span class="required-field">*</span> --></label>
													<form:input cssClass="form-control" maxlength="10"
														path="autoTransferLimit" id="autoTransferLimit"
														onblur="this.value=this.value.trim();validateAutoTransferLimit()" />
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="autoTransferLimitEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="merchant.label.autopaymentmethod" /> <span
														class="required-field">*</span></label>
													<form:select cssClass="form-control"
														path="autoPaymentMethod" id="autoPaymentMethod"
														onblur="validateAutoPaymentMethod()">
														<form:option value="">
															<spring:message code="reports.option.select" />
														</form:option>
														<form:option value="EFT">
															<spring:message code="merchantaccount.label.EFT" />
														</form:option>
														<form:option value="Cheque">
															<spring:message code="merchantaccount.label.cheque" />
														</form:option>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="autoPaymentMethodEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="merchant.label.autotransferperiod" />
														<!-- <span class="required-field">*</span> --></label>
													<form:select cssClass="form-control"
														onchange="showAutoTransferDayFields()"
														path="autoTransferDay" id="autoTransferDay">
														<form:option value="">
															<spring:message code="reports.option.select" />
														</form:option>
														<form:option value="D">
															<spring:message code="merchantaccount.label.daily" />
														</form:option>
														<form:option value="W">
															<spring:message code="merchantaccount.label.weekly" />
														</form:option>
														<form:option value="M">
															<spring:message code="merchantaccount.label.monthly" />
														</form:option>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="autoTransferDayEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3" id="weeklySettlement"
													style="display: none;">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="merchant.label.selectdayoftheweek" /><span
														class="required-field">*</span></label>
													<form:select cssClass="form-control"
														onblur="return clientValidation('autoTransferWeeklyDay', 'state','autoTransferWeeklyDayEr');"
														path="autoTransferWeeklyDay" id="autoTransferWeeklyDay">
														<form:option value="">
															<spring:message code="reports.option.select" />
														</form:option>
														<form:option value="2">
															<spring:message code="merchantaccount.label.monday" />
														</form:option>
														<form:option value="3">
															<spring:message code="merchantaccount.label.tuesday" />
														</form:option>
														<form:option value="4">
															<spring:message code="merchantaccount.label.wednesday" />
														</form:option>
														<form:option value="5">
															<spring:message code="merchantaccount.label.thursday" />
														</form:option>
														<form:option value="6">
															<spring:message code="merchantaccount.label.friday" />
														</form:option>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="autoTransferWeeklyDayEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3" id="monthlySettlement"
													style="display: none;">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="merchant.label.selectdayofmonth" /><span
														class="required-field">*</span></label>
													<form:select cssClass="form-control"
														onblur="return clientValidation('autoTransferMonthlyDay', 'state','autoTransferMonthlyDayEr');"
														path="autoTransferMonthlyDay" id="autoTransferMonthlyDay">
														<form:option value="">
															<spring:message code="reports.option.select" />
														</form:option>
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
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="autoTransferMonthlyDayEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3" id="vantivMerchantId"
													style="display: none;">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="merchant.label.vantivmerchantId" /> <span
														class="required-field">*</span></label>
													<form:input cssClass="form-control" path="litleMID"
														id="litleMID" maxlength="50"
														onblur="this.value=this.value.trim();validatelitleMID()" />
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="litleMIDEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="merchant.label.bank" /> </label>
													<form:select cssClass="form-control" path="bankId"
														id="bankId" disabled="true">
														<form:option value="">
															<spring:message code="reports.option.select" />
														</form:option>
														<c:forEach items="${bankList}" var="bankData">
															<form:option value="${bankData.value}">${bankData.label}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span class="red-error" id="bankIdEr">&nbsp;</span>
													</div>
												</fieldset>
												<%-- <fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.reseller"/> </label>
													<form:select cssClass="form-control" path="resellerId"
														id="resellerId">
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
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="merchant.label.merchantcategorycode" /> <span
														class="required-field">*</span> </label>
													<form:select cssClass="form-control" path="mcc" id="mcc"
														onblur="validateMcc()">
														<form:option value="">
															<spring:message code="reports.option.select" />
														</form:option>
														<c:forEach items="${mccList}" var="mccList">
															<form:option value="${mccList}">${mccList}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="mccErr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>

												<fieldset class="col-sm-3 hide-localCurrency">
													<label><spring:message
															code="merchant.label.localcurrency" /></label>
													<%-- <form:select cssClass="form-control" path="localCurrency"
															id="localCurrency">
															<form:option  value ="${merchant.localCurrency}">${merchant.localCurrency}</form:option>
														</form:select> --%>
													<form:input path="localCurrency" id="localCurrency"
														readonly="true" cssClass="form-control" />
													<div class="discriptionErrorMsg">
														<span id="localCurrencyEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>


												<!-- Add Issuance Agent Configuration START -->

												<fieldset class="col-sm-12" id="issuanceAgentSettings">
													<fieldset class="col-sm-12 padding0 border-style-section">
														<fieldset class="col-sm-12">
															<div class="container-heading-separator">
																<span><spring:message
																		code="merchant.label.issuer.agent.configuration" /></span>
															</div>
															<div id="agentErrorId" class="red-error">&nbsp;</div>
															<div class="row">
																<div class="field-element-row">
																	<fieldset class="col-sm-3">
																		<label data-toggle="tooltip" data-placement="top"
																			title=""> <spring:message
																				code="common.label.agentName" />
																		</label>
																		<form:select cssClass="form-control" path="agentId"
																			id="agentId"
																			
																			onchange="fetchAgentData(this.value)">
																			<form:option value="">
																				<spring:message code="reports.option.select" />
																			</form:option>
																			<c:forEach items="${agentnamesList}" var="agentnames">
																				<form:option value="${agentnames.label}">${agentnames.value}</form:option>
																			</c:forEach>
																			<%-- <form:option value="${merchant.agentId}">${merchant.agentName}</form:option> --%>
																		</form:select>
																		<div class="discriptionErrorMsg" data-toggle="tooltip"
																			data-placement="top" title="">
																			<span id="agentIdErr" class="red-error">&nbsp;</span>
																		</div>
																	</fieldset>
																	<fieldset class="col-sm-4">
																		<label data-toggle="tooltip" data-placement="top"
																			title=""> <spring:message
																				code="merchant.label.issuer.agent.accnumber" /></label>
																		<form:input cssClass="form-control" maxlength="19"
																			onkeypress="return numbersonly(this,event)"
																			path="agentAccountNumber" id="agentAccountNumber"
																			readonly="true" />
																		<div class="discriptionErrorMsg" data-toggle="tooltip"
																			data-placement="top" title="">
																			<span id="agentAccountNumberErrorDiv"
																				class="red-error">&nbsp;</span>
																		</div>

																	</fieldset>
																	<fieldset class="col-sm-4">
																		<label data-toggle="tooltip" data-placement="top"
																			title=""> <spring:message
																				code="merchant.label.issuer.agent.clientid" /></label>
																		<form:input cssClass="form-control" maxlength="20"
																			onkeypress="return numbersonly(this,event)"
																			path="agentClientId" id="agentClientId"
																			readonly="true" />
																		<div class="discriptionErrorMsg" data-toggle="tooltip"
																			data-placement="top" title="">
																			<span id="agentClientIdErrorDiv" class="red-error">&nbsp;</span>
																		</div>

																	</fieldset>
																	<fieldset class="col-sm-4">
																		<label data-toggle="tooltip" data-placement="top"
																			title=""> <spring:message
																				code="merchant.label.issuer.agent.ani" /></label>
																		<form:input cssClass="form-control" maxlength="20"
																			onkeypress="return numbersonly(this,event)"
																			path="agentANI" id="agentANI" readonly="true" />
																		<div class="discriptionErrorMsg" data-toggle="tooltip"
																			data-placement="top" title="">
																			<span id="agentANIErrorDiv" class="red-error">&nbsp;</span>
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
																<span><spring:message
																		code="merchant.label.supportterminals" /></span>
															</div>
															<div class="row">
																<div class="field-element-row">
																	<fieldset class="col-sm-5">
																		<form:checkbox path="virtualTerminal"
																			id="virtualTerminal" value="0"
																			onclick="validateVirtualTerminal()" />
																		<spring:message
																			code="merchant.label.virtualterminaloptions" />

																		<fieldset id="virtualTerminalOptions">

																			<fieldset class="col-sm-12">
																				<form:checkbox path="refunds" id="refunds" value="0"
																					onclick="validateCheckBox()" />
																				<spring:message code="merchant.label.refunds" />
																			</fieldset>
																			<fieldset class="col-sm-12">
																				<form:checkbox path="tipAmount" id="tipAmount"
																					value="0" onclick="validateCheckBox()" />
																				<spring:message code="merchant.label.tipamount" />
																			</fieldset>
																			<fieldset class="col-sm-12">
																				<form:checkbox path="taxAmount" id="taxAmount"
																					value="0" onclick="validateCheckBox()" />
																				<spring:message code="merchant.label.taxamount" />
																			</fieldset>
																			<fieldset class="col-sm-12">
																				<form:checkbox path="shippingAmount"
																					id="shippingAmount" value="0"
																					onclick="validateCheckBox()" />
																				<spring:message code="merchant.label.shippingamount" />
																			</fieldset>
																			<%-- <fieldset class="col-sm-12">
																				<form:checkbox path="allowRepeatSale"
																					id="allowRepeatSale" value="0"
																					onclick="validateCheckBox()" />
																				<spring:message code="merchant.label.allowrepeatsale"/>
																			</fieldset>
																			<fieldset class="col-sm-12">
																				<form:checkbox path="showRecurringBilling"
																					id="showRecurringBilling" value="0"
																					onclick="validateCheckBox()" />
																				<spring:message code="merchant.label.showrecurringbilling"/>
																			</fieldset> --%>
																			<div class="discriptionErrorMsg"
																				data-toggle="tooltip" data-placement="top" title="">
																				<span id="refundsEr" class="red-error">&nbsp;</span>
																			</div>
																		</fieldset>
																	</fieldset>

																	<%-- <fieldset class="col-sm-4">
																		<form:checkbox path="posTerminal" id="posTerminal"
																			value="0" onclick="validatePos()" />
																		<spring:message code="merchant.label.pos"/>
																	</fieldset> --%>


																	<fieldset class="col-sm-4">
																		<form:checkbox path="online" id="online" value="0"
																			onclick="validateOnlineOptions()" />
																		<spring:message code="merchant.label.online" />

																		<fieldset id="onlineOptions">

																			<fieldset class="col-sm-12">
																				<label data-toggle="tooltip" data-placement="top"
																					title=""><spring:message
																						code="merchant.label.websiteaddress" /><span
																					class="required-field">*</span></label>
																				<form:input cssClass="form-control"
																					path="webSiteAddress" id="webSiteAddress"
																					onblur="this.value=this.value.trim();validateWebSiteAddressURL()" />
																				<div class="discriptionErrorMsg"
																					data-toggle="tooltip" data-placement="top" title="">
																					<span id="webSiteAddrErr" class="red-error">&nbsp;</span>
																				</div>
																			</fieldset>

																			<fieldset class="col-sm-12">
																				<label data-toggle="tooltip" data-placement="top"
																					title=""><spring:message
																						code="merchant.label.returnURL" /><span
																					class="required-field">*</span></label>
																				<form:input cssClass="form-control validate"
																					path="returnUrl" id="returnUrl"
																					onblur="this.value=this.value.trim();validateReturnURL()" />
																				<div class="discriptionErrorMsg"
																					data-toggle="tooltip" data-placement="top" title="">
																					<span id="returnURLErr" class="red-error">&nbsp;</span>
																				</div>
																			</fieldset>

																			<fieldset class="col-sm-12">
																				<label data-toggle="tooltip" data-placement="top"
																					title=""><spring:message
																						code="merchant.label.cancelURL" /><span
																					class="required-field">*</span></label>
																				<form:input cssClass="form-control validate"
																					path="cancelUrl" id="cancelUrl"
																					onblur="this.value=this.value.trim();validateCancelURL()" />
																				<div class="discriptionErrorMsg"
																					data-toggle="tooltip" data-placement="top" title="">
																					<span id="cancelURLErr" class="red-error">&nbsp;</span>
																				</div>
																			</fieldset>
																			<fieldset class="col-sm-12">
																				<form:checkbox path="payPageConfig"
																					id="payPageConfig" value="0" />
																				<label data-toggle="tooltip" data-placement="top"
																					title=""><spring:message
																						code="merchant.label.paypageconfiguration" /></label>
																				<div class="discriptionErrorMsg"
																					data-toggle="tooltip" data-placement="top" title="">
																					<span id="payPageConfigErr" class="red-error">&nbsp;</span>
																				</div>
																			</fieldset>
																		</fieldset>
																	</fieldset>
																	&nbsp;
																</div>
															</div>
														</fieldset>
													</fieldset>
												</fieldset>

												<!-- ADDED VIRTUAL, POS and ONLINE TERMINALS END-->

												<div class="col-sm-12 padding0 hide-block">
													<fieldset class="col-sm-12">
														<fieldset class="col-sm-3 multi-select-box"
															style="padding: 0 15px;">
															<label data-toggle="tooltip" data-placement="top"
																title=""><spring:message
																	code="merchant.label.availablecurrency" /><span
																class="required-field">*</span></label> <select
																class="features form-control left-select-box"
																multiple="true">
																<c:forEach items="${currencyListData}" var="currency">
																	<option value="${currency.label}">${currency.value}</option>
																</c:forEach>
															</select>
														</fieldset>
														<fieldset class="col-sm-1 multi-select-btn "
															style="margin-top: 55px;">
															<span class="left-right-btn form-control"
																onClick="SelectMoveRows(document.getElementsByClassName('features')[0],document.getElementsByClassName('features-codes')[0])">
																&gt; </span> <span class="right-left-btn form-control"
																onClick="SelectMoveRows(document.getElementsByClassName('features-codes')[0],document.getElementsByClassName('features')[0])">
																&lt; </span>
														</fieldset>
														<fieldset class="col-sm-5 multi-select-box"
															style="padding: 0 15px;">
															<label data-toggle="tooltip" data-placement="top"
																title=""><spring:message
																	code="merchant.label.DCCsupportedcurrency" /></label> <select
																id="currencyCodes"
																class="features-codes form-control right-select-box"
																multiple="true" style="float: left">
																<c:if test="${not empty selectedCurrencyCodes}">
																	<c:forEach items="${selectedCurrencyCodes}"
																		var="currency">
																		<option value="${currency.label}">${currency.value}</option>
																	</c:forEach>
																</c:if>
															</select>
															<div class="discriptionErrorMsg" data-toggle="tooltip"
																data-placement="top" title="">
																<span class="red-error"
																	style="width: 166px; float: left; margin-left: -234px; margin-top: 173px;"
																	id="mccCodeErrorMsg">&nbsp;</span>
															</div>
														</fieldset>
													</fieldset>
												</div>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="merchant.label.payOutat" /><span
														class="required-field">*</span></label>
													<form:select cssClass="form-control" path="payOutAt"
														id="payOutAt" onblur="doCheckPayoutAt()">
														<form:option value="">
															<spring:message code="reports.option.select" />
														</form:option>
														<form:option value="Merchant">
															<spring:message code="merchant.label.merchant" />
														</form:option>
														<form:option value="Sub-Merchant">
															<spring:message code="merchant.label.submerchant" />
														</form:option>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="payOutAtEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3" style="display: none;">
													<form:input cssClass="form-control" path="programManagerId"
														id="programManagerId" />
												</fieldset>
												<fieldset class="col-sm-3" style="display: none;">
													<form:input cssClass="form-control" path="programManagerId"
														id="programManagerId" />
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															class="form-control button pull-right atm-next"
															value='<spring:message code="common.label.continue"/>'
															onclick="validateRadio();"> <input type="button"
															value='<spring:message code="common.label.previous"/>'
															class="form-control button pull-right marginL10 atm-prev">
														<input type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.cancel"/>'
															onclick="openCancelConfirmationPopup()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<!-- ATM Transactions Content End -->
											<!-- POS Transactions Content Start -->
											<section class="field-element-row pos-transaction-content"
												style="display: none;">
												<fieldset class="col-sm-12 padding0">
													<fieldset class="col-sm-6">
														<fieldset class="fieldset merchant-content">
															<legend class="legend content-space">
																<spring:message code="merchant.label.basciinfo" />
															</legend>
															<table class="confirm-info-table">
																<tr>
																	<td><spring:message code="admin.PartnerName.message"/>:</td>
																	<td><div id="confirmPartnerId"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.companyname" />:</td>
																	<td><div id="confirmMbusinessName"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.merchantcode" />:</td>
																	<td><div id="confirmMmerchantCode"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.firstname" /></td>
																	<td><div id="confirmMfirstName"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.lastname" />:</td>
																	<td><div id="confirmMlastName"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.mobilephone" />:</td>
																	<td><div id="confirmMphone"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.fax" />:</td>
																	<td><div id="confirmMfax"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.emailID" />:</td>
																	<td><div id="confirmMemailId"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.address1" />:</td>
																	<td><div id="confirmMaddress1"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.address2" />:</td>
																	<td><div id="confirmMaddress2"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.city" />:</td>
																	<td><div id="confirmMcity"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.state" />:</td>
																	<td><div id="confirmMstate"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.country" />:</td>
																	<td><div id="confirmMcountry"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.zipcode" />:</td>
																	<td><div id="confirmMpin"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.applicationmode" />:</td>
																	<td><div id="confirmMappMode"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.businessURL" />:</td>
																	<td><div id="confirmMbusinessURL"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.lookingfor" /></td>
																	<td><div id="confirmLookingFor"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.businesstype" />:</td>
																	<td><div id="confirmBusinessType"></div></td>
																</tr>
																<tr>
																	<td><spring:message 
																			code="show-account-transfer.label.accountnumber" />:</td>
																	<td>${accountNumber}</td>
																</tr>
															</table>
														</fieldset>
													</fieldset>
													<fieldset class="col-sm-6">
														<fieldset class="fieldset contact-content">
															<legend class="legend content-space">
																<spring:message code="merchant.label.additonalinfo" />
															</legend>
															<table class="confirm-info-table">
																<tr>
																	<td><spring:message code="merchant.label.username" />:</td>
																	<td><div id="confirmMuserName"></div></td>
																</tr>
															</table>
														</fieldset>
													</fieldset>
													<fieldset class="col-sm-6">
														<fieldset class="fieldset merchant-content">
															<legend class="legend content-space">
																<spring:message code="merchant.label.bankinfo" />
															</legend>
															<table class="confirm-info-table">
																<tr>
																	<td><spring:message code="merchant.label.name" />:</td>
																	<td><div id="confirmbankAccountName"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.bankroutingnumber" />:</td>
																	<td><div id="confirmbankRoutingNumber"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.bankaccountnumber" />:</td>
																	<td><div id="confirmbankAccountNumber"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.type" />:</td>
																	<td><div id="confirmbankAccountType"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.address1" />:</td>
																	<td><div id="confirmbankAddress1"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.address2" />:</td>
																	<td><div id="confirmbankAddress2"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.city" />:</td>
																	<td><div id="confirmbankCity"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.country" />:</td>
																	<td><div id="confirmbankCountry"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.state" />:</td>
																	<td><div id="confirmbankState"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.zipcode" />:</td>
																	<td><div id="confirmbankPin"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.nameonaccount" />:</td>
																	<td><div id="confirmbankNameOnAccount"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.currency" />:</td>
																	<td><div id="confirmcurrencyId"></div></td>
																</tr>
															</table>
														</fieldset>
													</fieldset>
													<fieldset class="col-sm-6">
														<fieldset class="fieldset merchant-content">
															<legend class="legend content-space">
																<spring:message code="merchant.label.legalentityrep" />
															</legend>
															<table class="confirm-info-table">
																<tr>
																	<td><spring:message code="merchant.label.SSN" />:</td>
																	<td><div id="confirmlegalSSN"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.firstname" />:</td>
																	<td><div id="confirmlegalFirstName"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.lastname" />:</td>
																	<td><div id="confirmlegalLastName"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.mobilephone" />:</td>
																	<td><div id="confirmlegalMobilePhone"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.dateofbirth" />:</td>
																	<td><div id="confirmlegalDOB"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.passportnumber" />:</td>
																	<td><div id="confirmlegalPassport"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.countryresidence" />:</td>
																	<td><div id="confirmlegalCountryResidence"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.countrycitizenship" />:</td>
																	<td><div id="confirmlegalCitizen"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.homephone" />:</td>
																	<td><div id="confirmlegalHomePhone"></div></td>
																</tr>
															</table>
														</fieldset>
													</fieldset>
													<fieldset class="col-sm-6">
														<fieldset class="fieldset merchant-content">
															<legend class="legend content-space">
																<spring:message code="merchant.label.legalentity" />
															</legend>
															<table class="confirm-info-table">
																<tr>
																	<td><spring:message
																			code="merchant.label.entityLegalName" />:</td>
																	<td><div id="confirmlegalName"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.EIN/TaxID" />:</td>
																	<td><div id="confirmlegalTaxId"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.type" />:</td>
																	<td><div id="confirmlegalType"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.expectedannualcardsales" />:</td>
																	<td><div id="confirmlegalAnnualCard"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.address1" />:</td>
																	<td><div id="confirmlegalAddress1"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.address2" />:</td>
																	<td><div id="confirmlegalAddress2"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.city" />:</td>
																	<td><div id="confirmlegalCity"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.country" />:</td>
																	<td><div id="confirmlegalCountry"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.state" />:</td>
																	<td><div id="confirmlegalState"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.zipcode" />:</td>
																	<td><div id="confirmlegalPin"></div></td>
																</tr>
															</table>
														</fieldset>
													</fieldset>
													<fieldset class="col-sm-6">
														<fieldset class="fieldset bank-content">
															<legend class="legend content-space">
																<spring:message code="merchant.label.configuration" />
															</legend>
															<table class="confirm-info-table">
																<tr>
																	<td><spring:message
																			code="merchant.label.autosettlementoptions" />:</td>
																	<td><div id="confirmMautoSettlement"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.merchantcallbackURL" />:</td>
																	<td><div id="confirmMmerchantCallBackURL"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.category" />:</td>
																	<td><div id="confirmMcategory"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.autotransferlimit" />:</td>
																	<td><div id="confirmMautoTransferLimit"></div></td>
																</tr>
																<%-- <tr>
																	<td><spring:message code="merchant.label.vantivmerchantId"/>:</td>
																	<td><div id="confirmLitleMID"></div></td>
																</tr> --%>
																<tr>
																	<td><spring:message
																			code="merchant.label.autotransferperiod" />:</td>
																	<td><div id="confirmMautoTransferDay"></div></td>
																</tr>
																<tr id="hideDayTable" style="display: none;">
																	<td><spring:message
																			code="merchant.label.selecteddayoftheweek" />:</td>
																	<td><div id="confirmAutoTransferWeeklyDay"></div></td>
																</tr>
																<tr id="hideWeekyTable" style="display: none;">
																	<td><spring:message
																			code="merchant.label.selecteddayofmonth" />:</td>
																	<td><div id="confirmAutoTransferMonthlyDay"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.autopaymentmethod" />:</td>
																	<td><div id="confirmMautoPaymentMethod"></div></td>
																</tr>
																<%-- <tr>
																	<td><spring:message code="merchant.label.DCCenable"/>:</td>
																	<td><div id="confirmDccEnable"></div></td>
																</tr> --%>
																<%-- <tr>
																	<td><spring:message code="merchant.label.selectedcurrency"/>:</td>
																	<td><div id="confirmcurrencyCodes"></div></td>
																</tr> --%>
																<tr>
																	<td><spring:message
																			code="merchant.label.merchanttype" />:</td>
																	<td><div id="confirmMmerchantCategory"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.feeprogram" />:</td>
																	<td><div id="confirmMfeeProgram"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.processor" />:</td>
																	<td><div id="confirmMprocessor"></div></td>
																</tr>

																<tr>
																	<td><spring:message
																			code="merchant.label.merchantcategorycode" />:</td>
																	<td><div id="confirmMcc"></div></td>
																</tr>

																<tr>
																	<td><spring:message code="merchant.label.bank" />:</td>
																	<td><div id="confirmBankName"></div></td>
																</tr>

																<tr>
																	<td><spring:message
																			code="merchant.label.localcurrency" />:</td>
																	<td><div id="confirmlocalCurrency"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.agentName" />:</td>
																	<td><div id="confirmAgentName"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.issuer.agent.accnumber" />:</td>
																	<td><div id="confirmAgentAccountNumber"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.issuer.agent.clientid" />:</td>
																	<td><div id="confirmAgentClientid"></div></td>
																</tr>
																<tr>
																	<td><spring:message
																			code="merchant.label.issuer.agent.ani" />:</td>
																	<td><div id="confirmAgentANI"></div></td>
																</tr>

																<%-- <tr>
																	<td><spring:message code="merchant.label.reseller"/>:</td>
																	<td><div id="confirmResellerName"></div></td>
																</tr> --%>

																<tr>
																	<td><spring:message
																			code="merchant.label.virtualterminaloptions" />:</td>
																	<td><div id="confirmMvirtualTerminalList"></div></td>
																</tr>

																<%-- <tr>
																	<td><spring:message code="merchant.label.pos"/>:</td>
																	<td><div id="confirmMposTerminal"></div></td>
																</tr> --%>

																<tr>
																	<td><spring:message code="merchant.label.online" />:</td>
																	<td><div id="confirmMwebSiteAddress"></div>
																		<div id="confirmMreturnURL"></div>
																		<div id="confirmMcancelURL"></div></td>

																	<!-- <td><div id="payPageConfiguration"></div></td> -->

																</tr>
																<tr>
																	<td><spring:message code="merchant.label.payOutat" />:</td>
																	<td><div id="confirmMpayOutAt"></div></td>
																</tr>
															</table>
														</fieldset>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="submit"
															class="form-control button pull-right pos-next"
															value='<spring:message code="common.label.update"/>'
															onclick="return selectedCurrency();"> <input
															type="button"
															value='<spring:message code="common.label.previous"/>'
															class="form-control button pull-right marginL10 pos-prev">
														<input type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.cancel"/>'
															onclick="openCancelConfirmationPopup()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<!-- POS Transactions Content End -->
										</div>
									</div>
								</form:form>
								<input type="hidden" id="linkedPartnerId"
									value=${merchant.issuancePartnerId } />
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
							onclick="goToMerchantSearch()">
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
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/utils.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/validation.js"></script>
	<script type="text/javascript" src="../js/merchant.js"></script>
	<script type="text/javascript" src="../js/chatak-ajax.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		/* function highlightMainContent() {
			$("#navListId3").addClass("active-background");
		} */
		/* Common Navigation Include End */
		/* DatePicker Javascript Strat*/
		$(document).ready(
				function() {
					
					validateMcc();
					validateVirtualTerminal();
					validateOnlineOptions();
					$("#navListId6").addClass("active-background");
					/* populatePartnerAndAgentDetails($('#appMode').val(),
							'merchant', 'update', false); */
					$(window).keydown(function(event) {
						if (event.keyCode == 13) {
							event.preventDefault();
							return false;
						}

					});
					$('#my_popup1').popup({
						blur : false
					});

					if ("${merchant.dccEnable}" == "true") {
						$(".hide-block").show();
					} else {
						$(".hide-block").hide();
					}

					/* if ($('#status').val() == 1) {
					    $('#status').attr('disabled','disabled');
					} else {
					    $('#status').removeAttr('disabled');
					    $('#status').children('option[value="1"]').css('display','none');
					} */
					$('#status').attr('disabled', 'disabled');

					if ("${merchant.processor}" == "LITLE") {
						$('#vantivMerchantId').show();
					}

					if ("${merchant.autoTransferDay}" == "M") {
						$('#monthlySettlement').show();
					}
					if ("${merchant.autoTransferDay}" == "W") {
						$('#weeklySettlement').show();
					}

					loadRadio('${merchant.autoSettlement}');
					if ($('#status').val() != 1) {
						$('#status').children('option[value="1"]').css(
								'display', 'none');
					}
					if ($('#status').val() != 5) {
						$('#status').children('option[value="5"]').css(
								'display', 'none');

					}
					$(".focus-field").click(function() {
						$(this).children('.effectiveDate').focus();
					});
					$('.effectiveDate').datetimepicker({
						timepicker : false,
						format : 'm/d/Y',
						formatDate : 'Y/m/d',
					});
					showAddSubMerchant();
					var prevAppMode = "";
					$('#appMode').on(
							'change',
							function() {
								var currentAppMode = $('#appMode').val();
								if (currentAppMode.length > 0
										&& currentAppMode != prevAppMode) {
									prevAppMode = currentAppMode;
									isParentAndAgentDetailsAvailable = false;
									/* populatePartnerAndAgentDetails(
											currentAppMode, 'merchant',
											'create', true); */
								} else {
									prevAppMode = currentAppMode;
								}
							});
				});
		/* DatePicker Javascript End*/
		$(
				".bank-info-details-content, .legal-details-content, .legal-details-rep-content, .free-transactions-content, .atm-transaction-content, .pos-transaction-content")
				.hide();
		$(".account-details-content").show();
		$(".merchant-arrow").show();
		$(
				".contact-arrow, .bank-info-arrow, .legal-arrow, .legal-rep-arrow, .bank-legal-arrow, .bank-arrow, .configuration-arrow, .final-arrow")
				.hide();
		$(".account-details-list, .bank-prev")
				.click(
						function() {
							$(".merchant-circle-tab").addClass("active-circle");
							$(
									".bank-info-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .contact-circle-tab, .bank-circle-tab, .final-circle-tab")
									.removeClass("active-circle");
							$(".merchant-arrow").show();
							$(
									".bank-info-arrow, .legal-arrow, .legal-rep-arrow, .contact-arrow, .bank-arrow, .final-arrow")
									.hide();
							$(".account-details-content").show();
							$(
									".atm-transaction-content,.bank-info-details-content, .legal-details-content, .legal-details-rep-content, .pos-transaction-content, .free-transactions-content")
									.hide();
						});
		$(".bank-list, .acc-next, .legal-prev")
				.click(
						function() {
							if (!validateCreateMerchantStep1edit()) {
								return false;
							}
							$(".bank-info-circle-tab")
									.addClass("active-circle");
							$(
									".merchant-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .contact-circle-tab, .bank-circle-tab, .final-circle-tab")
									.removeClass("active-circle");
							$(".bank-info-arrow").show();
							$(
									".merchant-arrow, .legal-arrow, .legal-rep-arrow, .contact-arrow, .configuration-arrow, .bank-arrow, .configuration-arrow, .final-arrow")
									.hide();
							$(".bank-info-details-content").show();
							$(
									".account-details-content, .legal-details-content, .legal-details-rep-content, .atm-transaction-content, .pos-transaction-content, .free-transactions-content")
									.hide();
						});
		$(".legal-entiy-list, .bank-next, .legal-rep-prev")
				.click(
						function() {
							if (!validateCreateMerchantStep2edit()
									| !validateCreateMerchantStep1edit()) {
								return false;
							}
							$(".legal-circle-tab").addClass("active-circle");
							$(
									".merchant-circle-tab, .bank-info-circle-tab, .legal-circle-rep-tab, .contact-circle-tab, .bank-circle-tab, .final-circle-tab")
									.removeClass("active-circle");
							$(".legal-arrow").show();
							$(
									".merchant-arrow, .legal-rep-arrow, .bank-info-arrow, .contact-arrow, .configuration-arrow, .bank-arrow, .configuration-arrow, .final-arrow")
									.hide();
							$(".legal-details-content").show();
							$(
									".account-details-content, .legal-details-rep-content, .bank-info-details-content, .atm-transaction-content, .pos-transaction-content, .free-transactions-content")
									.hide();
						});
		$(".legal-entiy-rep-list, .legal-next, .free-prev")
				.click(
						function() {
							if (!validateLegalEntity() | !ssnValidation()
									| !validateCreateMerchantStep2edit()
									| !validateCreateMerchantStep1edit()
									| !validateLegalDOB()
									| resetLegalEntityInfoErrorMsg()) {
								return false;
							}
							$(".legal-circle-rep-tab")
									.addClass("active-circle");
							$(
									".merchant-circle-tab, .bank-info-circle-tab, .legal-circle-tab, .contact-circle-tab, .bank-circle-tab, .final-circle-tab")
									.removeClass("active-circle");
							$(".legal-rep-arrow").show();
							$(
									".merchant-arrow, .bank-info-arrow, .legal-arrow, .contact-arrow, .configuration-arrow, .bank-arrow, .configuration-arrow, .final-arrow")
									.hide();
							$(".legal-details-rep-content").show();
							$(
									".account-details-content, .bank-info-details-content, .legal-details-content, .atm-transaction-content, .pos-transaction-content, .free-transactions-content")
									.hide();
						});
		$(".free-transactions-list, .legal-rep-next, .atm-prev")
				.click(
						function() {
							if (!validateCreateMerchantStep3edit()
									| !validateCreateMerchantStep2edit()
									| !validateLegalEntity()
									| !validateCreateMerchantStep1edit()) {
								return false;
							}
							$(".contact-circle-tab").addClass("active-circle");
							$(
									".merchant-circle-tab,.bank-info-circle-tab, .bank-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .final-circle-tab")
									.removeClass("active-circle");
							$(".contact-arrow").show();
							$(
									".merchant-arrow, .legal-arrow, .legal-rep-arrow, .bank-info-arrow, .configuration-arrow, .bank-arrow, .final-arrow")
									.hide()
							$(".free-transactions-content").show();
							$(
									".atm-transaction-content, .legal-details-content, .legal-details-rep-content, .bank-info-details-content, .pos-transaction-content, .account-details-content")
									.hide();
						});
		$(".atm-transactions-list, .free-next, .pos-prev")
				.click(
						function() {
							if (!validateCreateMerchantStep4edit()
									| !validateCreateMerchantStep3edit()
									| !validateCreateMerchantStep2edit()
									| !validateLegalEntity()
									| !validateCreateMerchantStep1edit()) {
								return false;
							}
							$(".bank-circle-tab").addClass("active-circle");
							$(
									".merchant-circle-tab,.bank-info-circle-tab, .contact-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .final-circle-tab")
									.removeClass("active-circle");
							$(".configuration-arrow").show();
							$(
									".contact-arrow, .merchant-arrow, .legal-arrow, .legal-rep-arrow, .bank-info-arrow, .final-arrow")
									.hide()
							$(".atm-transaction-content").show();
							$(
									".free-transactions-content, .bank-info-details-content, .legal-details-content, .legal-details-rep-content, .pos-transaction-content, .account-details-content")
									.hide();
							//populatePartnerAndAgentDetails($('#appMode').val(),'merchant','update');
						});
		$(".pos-transactions-list, .atm-next")
				.click(
						function() {
							if (!validateCreateMerchantStep5()
									| !validateCreateMerchantStep4edit()
									| !validateCreateMerchantStep3edit()
									| !validateCreateMerchantStep2edit()
									| !validateLegalEntity()
									| !validateCreateMerchantStep1edit()) {
								return false;
							}
							$(".final-circle-tab").addClass("active-circle");
							$(
									".merchant-circle-tab, .bank-info-circle-tab, .contact-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .bank-circle-tab")
									.removeClass("active-circle");
							$(".final-arrow").show();
							$(
									".contact-arrow, .bank-arrow,.configuration-arrow, .bank-info-arrow, .legal-arrow, .legal-rep-arrow, .merchant-arrow")
									.hide()
							$(".pos-transaction-content").show();
							$(
									".free-transactions-content, .bank-info-details-content, .legal-details-content, .legal-details-rep-content, .atm-transaction-content, .account-details-content")
									.hide();
						});

		$(".pos-transactions-list, .atm-next")
				.click(
						function() {
							var selectcurrencytype = "";
							$("#currencyCodes option").each(
									function() {
										selectcurrencytype = selectcurrencytype
												+ " " + $(this).val();
									});

							$('#confirmcurrencyCodes').text(selectcurrencytype);
							var length = $('#currencyCodes').children('option').length;
							if (length == 0)
								document.getElementById("mccCodeErrorMsg").innerHTML = 'Please Select Currency';
							if (!validateCreateMerchantStep5()
									| !validateCreateMerchantStep1()
									| !validateCreateMerchantStep2()
									| !validateCreateMerchantStep3()
									| !validateLegalEntity()
									| !validateCreateMerchantStep4()) {
								return false
							}
							$(".final-circle-tab").addClass("active-circle");
							$(
									".merchant-circle-tab, .bank-info-circle-tab, .contact-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .bank-circle-tab")
									.removeClass("active-circle");
							$(".final-arrow").show();
							$(
									".contact-arrow, .bank-arrow,.configuration-arrow, .bank-info-arrow, .legal-arrow, .legal-rep-arrow, .merchant-arrow")
									.hide()
							$(".pos-transaction-content").show();
							$(
									".free-transactions-content, .bank-info-details-content, .legal-details-content, .legal-details-rep-content, .atm-transaction-content, .account-details-content")
									.hide();
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
		function SelectMoveRows(left, right) {
			var SelID = '';
			var SelText = '';
			// Move rows from left to right from bottom to top
			for (i = left.options.length - 1; i >= 0; i--) {
				if (left.options[i].selected == true) {
					SelID = left.options[i].value;
					SelText = left.options[i].text;
					var newRow = new Option(SelText, SelID);
					right.options[right.length] = newRow;
					left.options[i] = null;
				}
			}
			SelectSort(right);
			removeFetchedOperators(SelID);
		}

		$("#dcc_enable").click(function() {
			if ($(this).is(":checked")) {
				$(".hide-block").show();
				$(".hide-localCurrency").show();
			} else {
				$(".hide-block").hide();
				$(".hide-localCurrency").hide();
			}
		});

		$('#legalSSN').keyup(function() {
			var val = this.value.replace(/\D/g, '');
			var newVal = '';
			if (val.length > 4) {
				this.value = val;
			}
			if ((val.length > 3) && (val.length < 6)) {
				newVal += val.substr(0, 3) + '-';
				val = val.substr(3);
			}
			if (val.length > 5) {
				newVal += val.substr(0, 3) + '-';
				newVal += val.substr(3, 2) + '-';
				val = val.substr(5);
			}
			newVal += val;
			this.value = newVal;
		});
		$('#my_popup1').popup({
			blur : false
		});
	</script>
</body>
</html>
