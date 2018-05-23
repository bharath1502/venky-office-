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

			<!--Navigation Block Start -->

			<!--Article Block Start-->
			<article>

				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->

					<div class="breadCrumb" id="subMerchantDiv">
						<span class="breadcrumb-text"><spring:message
								code="manage.label.manage" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message
								code="manage.label.sub-merchant" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message
								code="manage.label.sub-merchant.view" /></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first">
						<a href="sub-merchant-search-page"><spring:message
								code="manage.label.sub-merchant.search" /></a>
					</div>
					<div class="tab-header-container active-background">
						<a href="#"><spring:message
								code="manage.label.sub-merchant.view" /></a>
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
												code="manage.label.sub-merchant.basicinfo" /></label>
										<div class="arrow-down merchant-arrow"></div>
									</li>
									<li class="bank-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="bank-info-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message
												code="manage.label.sub-merchant.bankinfo" /> </label>
										<div class="arrow-down bank-info-arrow"></div>
									</li>
									<li class="legal-entiy-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="legal-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message
												code="manage.label.sub-merchant.legalentityrepresentative" /></label>
										<div class="arrow-down legal-arrow"></div>
									</li>
									<li class="legal-entiy-rep-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="legal-circle-rep-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message
												code="manage.label.sub-merchant.legalentity" /></label>
										<div class="arrow-down legal-rep-arrow"></div>
									</li>
									<li class="free-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="contact-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message
												code="manage.label.sub-merchant.additionalinfo" /></label>
										<div class="arrow-down contact-arrow"></div>
									</li>
									<li class="atm-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="bank-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message
												code="manage.label.sub-merchant.configurations" /></label>
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

								<form:form action="showSubMerchantList" name="showSubMerchantList"
									method="post">
									<input type="hidden" id="getParentMerchantId"
										name="getParentMerchantId" />
								</form:form>

								<!-- Page Form Start -->
								<form:form action="updateMerchant" commandName="merchant"
									name="merchant">
									<div class="col-sm-12 paddingT20">
										<div class="row">
											<!-- Account Details Content Start -->
											<section class="field-element-row account-details-content"
												style="display: none;">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.companyname" /><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="businessName"
															id="businessName" maxlength="50"
															onblur="validateBusinessName()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="businessNameEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.submerchantcode" /><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="merchantCode"
															id="merchantCode" maxlength="50"
															onblur="validateMerchantCode()" readonly="true" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="merchantCodeEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.firstname" /><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="firstName"
															id="firstName" maxlength="50"
															onblur="validateFirstName()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="firstNameEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.lastname" /><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="lastName"
															id="lastName" maxlength="50" onblur="validateLastName()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="lastNameEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.phone" /><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="phone"
															id="phone" maxlength="10" onblur="validatePhone()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="phoneEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.fax" /> <!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="fax" id="fax"
															onkeypress="return amountValidate(this,event)"
															maxlength="13" />
														<%--  onblur="validateFax()" --%>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="faxEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.E-mailID" /><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="emailId"
															id="emailId" onblur="validateEmailIdEdit()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="emailIdEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.address1" /><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="address1"
															id="address1" maxlength="50" onblur="validateAddress1()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="address1Er" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.address2" /></label>
														<form:input cssClass="form-control" path="address2"
															id="address2" maxlength="50" onblur="validateAddress2()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="address2Er" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.city" /><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="city" id="city"
															maxlength="50" onblur="validateCity()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="cityEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.country" /><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control" path="country"
															id="country" onblur="validateCountry()"
															onchange="fetchMerchantState(this.value, 'state')">
															<form:option value="">..:<spring:message
																	code="manage.option.sub-merchant.select" />:..</form:option>
															<c:forEach items="${countryList}" var="country">
																<form:option value="${country.label}">${country.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="countryEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.state" /><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control" path="state"
															id="state" onblur="validateState()">
															<form:option value="">..:<spring:message
																	code="manage.option.sub-merchant.select" />:..</form:option>
															<c:forEach items="${stateList}" var="item">
																<form:option value="${item.label}">${item.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="stateEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.zipcode" /><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="pin" id="pin"
															maxlength="10" onblur="validatePin()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="pinEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<%-- <fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.status" /><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control" path="status"
															id="status" onblur="validateStatus()">
															<form:option value="">..:<spring:message
																	code="manage.option.sub-merchant.select" />:..</form:option>
															<form:option value="0">
																<spring:message code="manage.option.sub-merchant.active" />
															</form:option>
															<form:option value="1">
																<spring:message
																	code="manage.option.sub-merchant.pending" />
															</form:option>
															<form:option value="2">
																<spring:message
																	code="manage.option.sub-merchant.in-active" />
															</form:option>
															<form:option value="5">
																<spring:message
																	code="manage.option.sub-merchant.self-registered-pending" />
															</form:option>
														</form:select>
														<form:hidden path="merchantType" id="merchantType" />
														<form:hidden path="parentMerchantId" id="getParentMercId" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="statusEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset> --%>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.applicationmode" /><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control" path="appMode"
															id="appMode" onblur="validateAppMode()">
															<form:option value="">..:<spring:message
																	code="manage.option.sub-merchant.select" />:..</form:option>
															<form:option value="DEMO">
																<spring:message code="manage.option.sub-merchant.demo" />
															</form:option>
															<form:option value="PRELIVE">
																<spring:message
																	code="manage.option.sub-merchant.pre-live" />
															</form:option>
															<form:option value="LIVE">
																<spring:message code="manage.option.sub-merchant.live" />
															</form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="appModeEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.businessurl" /><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="businessURL"
															maxlength="50" id="businessURL" onclick="validateURL()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="businessURLEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.lookingfor" /></label>
														<form:textarea cssClass="form-control" path="lookingFor"
															readonly="true" id="lookingFor" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="lookingForEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.businesstype" /></label>
														<form:select cssClass="form-control" path="businessType"
															disabled="disabled" id="businessType">
															<form:option value="">.:<spring:message
																	code="manage.option.sub-merchant.chooseatype" />:.</form:option>
															<form:option value="Airline">
																<spring:message
																	code="manage.option.sub-merchant.airline" />
															</form:option>
															<form:option value="Auto Rental">
																<spring:message
																	code="manage.option.sub-merchant.autorental" />
															</form:option>
															<form:option value="Clothing Stores">
																<spring:message
																	code="manage.option.sub-merchant.clothingstores" />
															</form:option>
															<form:option value="Department Stores">
																<spring:message
																	code="manage.option.sub-merchant.departmentstores" />
															</form:option>
															<form:option value="Deposit Transactions">
																<spring:message
																	code="manage.option.sub-merchant.deposittransactions" />
															</form:option>
															<form:option value="Discount Stores">
																<spring:message
																	code="manage.option.sub-merchant.discountstores" />
															</form:option>
															<form:option value="Drug Stores">
																<spring:message
																	code="manage.option.sub-merchant.drugstores" />
															</form:option>
															<form:option value="Education">
																<spring:message
																	code="manage.option.sub-merchant.education" />
															</form:option>
															<form:option value="Electric-Appliance">
																<spring:message
																	code="manage.option.sub-merchant.electric-appliance" />
															</form:option>
															<form:option value="Food Stores-Warehouse">
																<spring:message
																	code="manage.option.sub-merchant.foodstores-warehouse" />
															</form:option>
															<form:option value="Gas Stations">
																<spring:message
																	code="manage.option.sub-merchant.gasstations" />
															</form:option>
															<form:option value="Hardware">
																<spring:message
																	code="manage.option.sub-merchant.hardware" />
															</form:option>
															<form:option value="Health Care">
																<spring:message
																	code="manage.option.sub-merchant.healthcare" />
															</form:option>
															<form:option value="Hotel-Motel">
																<spring:message
																	code="manage.option.sub-merchant.hotel-motel" />
															</form:option>
															<form:option value="Interior Furnishings">
																<spring:message
																	code="manage.option.sub-merchant.interiorfurnishings" />
															</form:option>
															<form:option value="Other Retail">
																<spring:message
																	code="manage.option.sub-merchant.otherretail" />
															</form:option>
															<form:option value="Other Services">
																<spring:message
																	code="manage.option.sub-merchant.otherservices" />
															</form:option>
															<form:option value="Other Transport">
																<spring:message
																	code="manage.option.sub-merchant.othertransport" />
															</form:option>
															<form:option value="Professional Services">
																<spring:message
																	code="manage.option.sub-merchant.professionalservices" />
															</form:option>
															<form:option value="Recreation">
																<spring:message
																	code="manage.option.sub-merchant.recreation" />
															</form:option>
															<form:option value="Repair Shops">
																<spring:message
																	code="manage.option.sub-merchant.repairshops" />
															</form:option>
															<form:option value="Restaurants-Bars">
																<spring:message
																	code="manage.option.sub-merchant.restaurants-bars" />
															</form:option>
															<form:option value="Sporting-Toy Stores">
																<spring:message
																	code="manage.option.sub-merchant.sporting-toystores" />
															</form:option>
															<form:option value="Vehicles">
																<spring:message
																	code="manage.option.sub-merchant.vehicles" />
															</form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="businessTypeEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.merchantcode" /><span
															class="required-field">*</span></label>
														<form:input path="parentMerchantId" id="parentMerchantId" readonly="true" cssClass="form-control" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="parentMerchantIdEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="admin.pm.Name.message" /></label>
														<form:input cssClass="form-control"
															path="programManagerName" id="programManagerName"
															readonly="true" />
														<div class="discriptionErrorMsg" data-toggle="tooltip"
															data-placement="top" title="">
															<span id="programManagerNameEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="admin.PartnerName.message" /></label>
														<form:input cssClass="form-control" path="partnerName"
															id="partnerName" readonly="true" />
														<div class="discriptionErrorMsg" data-toggle="tooltip"
															data-placement="top" title="">
															<span id="partnerNameEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content merchantDiv1">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															class="form-control button pull-right acc-next"
															value="<spring:message code="manage.buttton.sub-merchant.continue" />">
														<input type="button"
															class="form-control button pull-right marginL10"
															value="<spring:message code="manage.buttton.sub-merchant.cancel" />"
															onclick="goToMerchantSearch()">
													</fieldset>
												</div>
												<div class="col-sm-12 button-content subMerchantDiv1"
													style="display: none;">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															class="form-control button pull-right acc-next"
															value="<spring:message code="manage.buttton.sub-merchant.continue" />">
														<input type="button"
															class="form-control button pull-right marginL10"
															value="<spring:message code="manage.buttton.sub-merchant.cancel" />"
															onclick="goToSubMerchantSearch()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<!-- Account Details Content End -->
											<section class="field-element-row bank-info-details-content"
												style="display: none;">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.name" /><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="bankAccountName"
															id="bankAccountName" maxlength="50"
															onblur="return clientValidation('bankAccountName', 'first_name_SplChar','bankAccountNameErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAccountNameErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.bankroutingnumber" /><span
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
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.bankaccountnumber" /><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control"
															path="bankAccountNumber" id="bankAccountNumber"
															onblur="return clientValidation('bankAccountNumber', 'account_numberBank','bankAccountNumberErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAccountNumberErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.type" /><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control"
															path="bankAccountType" id="bankAccountType"
															onblur="return clientValidation('bankAccountType', 'account_type','bankAccountTypeErrorDiv');">
															<form:option value="">..:<spring:message
																	code="manage.option.sub-merchant.select" />:..</form:option>
															<form:option value="S">
																<spring:message
																	code="manage.option.sub-merchant.savings" />
															</form:option>
															<form:option value="C">
																<spring:message
																	code="manage.option.sub-merchant.checking" />
															</form:option>
														</form:select>

														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAccountTypeErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.address1" /> <!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="bankAddress1"
															id="bankAddress1" maxlength="50"
															onblur="return clientValidation('bankAddress1', 'bank_address2','bankAddress1ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAddress1ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.address2" /></label>
														<form:input cssClass="form-control" path="bankAddress2"
															id="bankAddress2" maxlength="50"
															onblur="return clientValidation('bankAddress2', 'bank_address2','bankAddress2ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAddress2ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.city" /> <!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="bankCity"
															id="bankCity" maxlength="50"
															onblur="return clientValidation('bankCity', 'bank_address2','bankCityErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankCityErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.country" /><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control" path="bankCountry"
															id="bankCountry"
															onblur="return clientValidation('bankCountry', 'country','bankCountryErrorDiv');"
															onchange="fetchMerchantState(this.value, 'bankState')">
															<form:option value="">..:<spring:message
																	code="manage.option.sub-merchant.select" />:..</form:option>
															<c:forEach items="${countryList}" var="country">
																<form:option value="${country.label}">${country.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankCountryErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.state" /><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control" path="bankState"
															id="bankState"
															onblur="return clientValidation('bankState', 'state','bankStateErrorDiv');">
															<form:option value="">..:<spring:message
																	code="manage.option.sub-merchant.select" />:..</form:option>
															<c:forEach items="${bankStateList}" var="item">
																<form:option value="${item.label}">${item.label}</form:option>
															</c:forEach>

														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankStateErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.zipcode" /><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="bankPin"
															id="bankPin" maxlength="10"
															onblur="return clientValidation('bankPin', 'zip_code','bankPinErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankPinErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.nameonaccount" /><span
															class="required-field">*</span></label>

														<form:input cssClass="form-control"
															path="bankNameOnAccount" id="bankNameOnAccount"
															onblur="return clientValidation('bankNameOnAccount', 'first_name_SplChar','bankNameOnAccountErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankNameOnAccountErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label><spring:message code="merchant.label.currency"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="localCurrency" id="localCurrency" readonly="true"/>
														<div class="discriptionErrorMsg">
															<span id="countryEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content merchantDiv1">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															class="form-control button pull-right bank-next"
															value="<spring:message code="manage.buttton.sub-merchant.continue" />">
														<input type="button"
															class="form-control button pull-right marginL10 bank-prev"
															value="<spring:message code="manage.buttton.sub-merchant.previous" />">
														<input type="button"
															class="form-control button pull-right marginL10"
															value="<spring:message code="manage.buttton.sub-merchant.cancel" />"
															onclick="goToMerchantSearch()">
													</fieldset>
												</div>
												<div class="col-sm-12 button-content subMerchantDiv1"
													style="display: none;">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															class="form-control button pull-right bank-next"
															value="<spring:message code="manage.buttton.sub-merchant.continue" />">
														<input type="button"
															class="form-control button pull-right marginL10 bank-prev"
															value="<spring:message code="manage.buttton.sub-merchant.previous" />">
														<input type="button"
															class="form-control button pull-right marginL10"
															value="<spring:message code="manage.buttton.sub-merchant.cancel" />"
															onclick="goToSubMerchantSearch()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<section class="field-element-row legal-details-content"
												style="display: none;">
												<fieldset class="col-sm-12">

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.ssn" /> <!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalSSN"
															onkeypress="return amountValidate(this,event)"
															id="legalSSN" maxlength="20" />
														<%-- onblur="return clientValidation('legalSSN', 'ssn','legalSSNErrorDiv');" --%>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalSSNErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.firstname" /> <!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalFirstName"
															id="legalFirstName" maxlength="50" />
														<!-- onblur="return clientValidation('legalFirstName', 'first_name_NotMand','legalFirstNameErrorDiv');" -->
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalFirstNameErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.lastname" /> <!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalLastName"
															id="legalLastName" maxlength="50" />
														<!-- onblur="return clientValidation('legalLastName', 'first_name_NotMand','legalLastNameErrorDiv');" -->
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalLastNameErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.mobilephone" /> <!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control"
															path="legalMobilePhone" maxlength="10"
															id="legalMobilePhone" />
														<!-- onblur="return clientValidation('legalMobilePhone', 'mobile_phone','legalMobilePhoneErrorDiv');" -->
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalMobilePhoneErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.dateofbirth" /> <!-- <span class="required-field">*</span> --></label>
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
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.passportnumber" /> <!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalPassport"
															id="legalPassport" maxlength="20" />
														<%-- onblur="return clientValidation('legalPassport', 'bank_code','legalPassportErrorDiv');" --%>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalPassportErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.countryofresidence" />
															<!-- <span class="required-field">*</span> --></label>
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
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.countryofcitizenship" />
															<!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalCitizen"
															id="legalCitizen" />
														<%-- onblur="return clientValidation('legalCitizen', 'bank_country','legalCitizenErrorDiv');" --%>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalCitizenErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.homephone" /> <!-- <span class="required-field">*</span> --></label>
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
												<div class="col-sm-12 button-content merchantDiv1">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															class="form-control button pull-right legal-next"
															value="<spring:message code="manage.buttton.sub-merchant.continue" />">
														<input type="button"
															class="form-control button pull-right marginL10 legal-prev"
															value="<spring:message code="manage.buttton.sub-merchant.previous" />">
														<input type="button"
															class="form-control button pull-right marginL10"
															value="<spring:message code="manage.buttton.sub-merchant.cancel" />"
															onclick="goToMerchantSearch()">
													</fieldset>
												</div>
												<div class="col-sm-12 button-content subMerchantDiv1"
													style="display: none;">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															class="form-control button pull-right legal-next"
															value="<spring:message code="manage.buttton.sub-merchant.continue" />">
														<input type="button"
															class="form-control button pull-right marginL10 legal-prev"
															value="<spring:message code="manage.buttton.sub-merchant.previous" />">
														<input type="button"
															class="form-control button pull-right marginL10"
															value="<spring:message code="manage.buttton.sub-merchant.cancel" />"
															onclick="goToSubMerchantSearch()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<!--Legal Entity Content Ends  -->
											<!-- Legal Details Content Start -->
											<section class="field-element-row legal-details-rep-content"
												style="display: none;">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.entitylegalname" /><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalName"
															id="legalName" maxlength="101"
															onblur="return clientValidation('legalName', 'first_name_SplChar','legalNameErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalNameErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.EINorTaxID" />:<span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalTaxId"
															id="legalTaxId" maxlength="50"
															onblur="return clientValidation('legalTaxId', 'eIN_taxId','legalTaxIdErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalTaxIdErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.type" /><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control" path="legalType"
															id="legalType"
															onblur="return clientValidation('legalType', 'state','legalTypeErrorDiv');">
															<form:option value="">..:<spring:message
																	code="manage.option.sub-merchant.select" />:..</form:option>
															<form:option value="1">
																<spring:message
																	code="manage.option.sub-merchant.associationestatetrust" />
															</form:option>
															<form:option value="2">
																<spring:message
																	code="manage.option.sub-merchant.corporation" />
															</form:option>
															<form:option value="3">
																<spring:message
																	code="manage.option.sub-merchant.governmentagency" />
															</form:option>
															<form:option value="4">
																<spring:message
																	code="manage.option.sub-merchant.individualorsoleproprietorship" />
															</form:option>
															<form:option value="5">
																<spring:message
																	code="manage.option.sub-merchant.internationalorginization" />
															</form:option>
															<form:option value="6">
																<spring:message
																	code="manage.option.sub-merchant.limitedliabilitycompany" />
															</form:option>
															<form:option value="7">
																<spring:message
																	code="manage.option.sub-merchant.partnership" />
															</form:option>
															<form:option value="8">
																<spring:message
																	code="manage.option.sub-merchant.taxexemptorganization501C" />
															</form:option>
															<form:option value="11">
																<spring:message
																	code="manage.option.sub-merchant.testing" />
															</form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalTypeErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.expectedsnnualcardsales" /><span
															class="required-field">*</span></label>
															<fmt:formatNumber type="currency" value="${merchant.legalAnnualCard}" pattern="<%=Constants.AMOUNT_FORMAT%>" var="legalAnnualCard" />
															<input name="legalAnnualCard" id="legalAnnualCard" value="${ legalAnnualCard}" cssClass="form-control" onblur="this.value=this.value.trim();appendDollarSymbol();return clientValidation('legalAnnualCard', 'dollar_amount','legalAnnualCardErrorDiv');"disabled="disabled"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalAnnualCardErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.address1" /><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalAddress1"
															id="legalAddress1"
															onblur="return clientValidation('legalAddress1', 'bank_address1','legalAddress1ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalAddress1ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.address2" /></label>
														<form:input cssClass="form-control" path="legalAddress2"
															id="legalAddress2"
															onblur="return clientValidation('legalAddress2', 'bank_address2','legalAddress2ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalAddress2ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.city" /><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalCity"
															id="legalCity"
															onblur="return clientValidation('legalCity', 'bank_city','legalCityErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalCityErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.country" /><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control" path="legalCountry"
															id="legalCountry"
															onblur="return clientValidation('legalCountry', 'country','legalCountryErrorDiv');"
															onchange="fetchMerchantState(this.value, 'legalState')">
															<form:option value="">..:<spring:message
																	code="manage.option.sub-merchant.select" />:..</form:option>
															<c:forEach items="${countryList}" var="country">
																<form:option value="${country.label}">${country.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalCountryErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.state" /><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control" path="legalState"
															id="legalState"
															onblur="return clientValidation('legalState', 'state','legalStateErrorDiv');">
															<form:option value="">..:<spring:message
																	code="manage.option.sub-merchant.select" />:..</form:option>
															<c:forEach items="${legalStateList}" var="state">
																<form:option value="${state.label}">${state.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalStateErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="manage.label.sub-merchant.zipcode" /><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalPin"
															id="legalPin"
															onblur="return clientValidation('legalPin', 'zip_code','legalPinErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalPinErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content merchantDiv1">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															class="form-control button pull-right legal-rep-next"
															value="<spring:message code="manage.buttton.sub-merchant.continue" />">
														<input type="button"
															class="form-control button pull-right marginL10 legal-rep-prev"
															value="<spring:message code="manage.buttton.sub-merchant.previous" />">
														<input type="button"
															class="form-control button pull-right marginL10"
															value="<spring:message code="manage.buttton.sub-merchant.cancel" />"
															onclick="goToMerchantSearch()">
													</fieldset>
												</div>
												<div class="col-sm-12 button-content subMerchantDiv1"
													style="display: none;">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															class="form-control button pull-right legal-rep-next"
															value="<spring:message code="manage.buttton.sub-merchant.continue" />">
														<input type="button"
															class="form-control button pull-right marginL10 legal-rep-prev"
															value="<spring:message code="manage.buttton.sub-merchant.previous" />">
														<input type="button"
															class="form-control button pull-right marginL10"
															value="<spring:message code="manage.buttton.sub-merchant.cancel" />"
															onclick="goToSubMerchantSearch()">
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
																code="manage.label.sub-merchant.username" /><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="userName"
															id="userName" maxlength="50"
															onblur="vlalidateSubMercUserName()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="userNameEr" class="red-error">&nbsp;</span> <span
																id="userNamegreenEr" class="green-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content merchantDiv1">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															class="form-control button pull-right free-next"
															value="<spring:message code="manage.buttton.sub-merchant.continue" />">
														<input type="button"
															class="form-control button pull-right marginL10 free-prev"
															value="<spring:message code="manage.buttton.sub-merchant.previous" />">
														<input type="button"
															class="form-control button pull-right marginL10"
															value="<spring:message code="manage.buttton.sub-merchant.cancel" />"
															onclick="goToMerchantSearch()">
													</fieldset>
												</div>
												<div class="col-sm-12 button-content subMerchantDiv1"
													style="display: none;">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															class="form-control button pull-right free-next"
															value="<spring:message code="manage.buttton.sub-merchant.continue" />">
														<input type="button"
															class="form-control button pull-right marginL10 free-prev"
															value="<spring:message code="manage.buttton.sub-merchant.previous" />">
														<input type="button"
															class="form-control button pull-right marginL10"
															value="<spring:message code="manage.buttton.sub-merchant.cancel" />"
															onclick="goToSubMerchantSearch()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<!-- Free Transactions Content End -->
											<!-- ATM Transactions Content Start -->
											<jsp:include page="sub-merchant-view-continue.jsp"></jsp:include>
											<!-- ATM Transactions Content End -->
											<!-- POS Transactions Content End -->
										</div>
									</div>
								</form:form>
								<input type="hidden" id="linkedPartnerId"
									value=${merchant.issuancePartnerId } /> <input type="hidden"
									id="linkedAgentId" value=${merchant.agentId } />
								<!-- Page Form End -->
							</div>
						</div>
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
	<script src="../js/common-lib.js"></script>
	<script src="../js/validation.js"></script>
	<script type="text/javascript" src="../js/merchant.js"></script>
	<script type="text/javascript" src="../js/chatak-ajax.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script src="../js/messages.js"></script>
	<script>
		$(document)
				.ready(
						function() {
							$("#navListId6").addClass("active-background");
							validateVirtualTerminal();
							validateOnlineOptions();
							/* populatePartnerAndAgentDetails($('#appMode').val(),
									'sub-merchant', 'view', false); */
							$(
									".form-control, input[type=radio], input[type=checkbox], input[type=file]")
									.attr("disabled", "disabled");
							$(".button").removeAttr("disabled");

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
							//	populatedropdown("autoTransferDay");
							if ($('#status').val() != 1) {
								$('#status').children('option[value="1"]').css(
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

							if ("${merchant.merchantFlag}" == "true") {
								$('#subMerchantDiv').show();
								$('#merchantDiv').hide();
								$('.subMerchantDiv1').show();
								$('.merchantDiv1').hide();
								$('#subMerchSearch').show();
								$('#merchSearch').hide();
							}
						});

		/* DatePicker Javascript Strat*/
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
							//populatePartnerAndAgentDetails($('#appMode').val(),'sub-merchant','view');
						});
		$(".pos-transactions-list, .atm-next")
				.click(
						function() {
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
	</script>
</body>
</html>
