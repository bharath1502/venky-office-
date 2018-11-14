<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css" />

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<div >
			<header > 
				<!--Header Block Start -->
				<!--Header Welcome Text and Logout button End -->
					<div class="col-sm-12">
				<img style="margin-left:400px" src="../images/chatak_logo.jpg" height="50px" alt="Logo" />
			</div>
			</header>
			<!--Header Block End -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text">Merchant User</span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text">Sign Up</span>
					</div>
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
										</div> <label data-toggle="tooltip" data-placement="top" title="">Basic Info</label>
										<div class="arrow-down merchant-arrow"></div>
									</li>
									<li class="bank-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="bank-info-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title="">Bank Info </label>
										<div class="arrow-down bank-info-arrow"></div>
									</li>
									<li class="legal-entiy-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="legal-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title="">Legal Entity Representative</label>
										<div class="arrow-down legal-arrow"></div>
									</li>
									<li class="legal-entiy-rep-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="legal-circle-rep-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title="">Legal Entity</label>
										<div class="arrow-down legal-rep-arrow"></div>
									</li>
									<li class="free-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="contact-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title="">Additional Info</label>
										<div class="arrow-down contact-arrow"></div>
									</li>
									<li class="atm-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="bank-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title="">Configurations</label>
										<div class="arrow-down configuration-arrow"></div>
									</li>
									<li class="pos-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="final-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title="">Confirmation</label>
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
									<div class="discriptionMsg col-xs-12">
										<span class="red-error">&nbsp;${error }</span> <span
											class="green-error">&nbsp;${sucess }</span> <span
											class="green-error">&nbsp;${success }</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="processMerchantSignUP"
									modelAttribute="merchantSignUpRequest" method="post">
									<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12 paddingT20">
										<div class="row">
											<!-- Account Details Content Start -->
											<section class="field-element-row account-details-content"
												style="display: none;">
												<fieldset class="col-sm-12">
												<fieldset class="col-sm-12">
														<label data-toggle="tooltip" data-placement="top" title="">Merchant Type
														<span class="required-field">*</span></label><br> 
														<input type="radio" id="merchant" name="merchant" value="1" onclick="validateRadioMerchantButton()">Merchant
														<input type="radio" id="subMerchant" name="merchant" value="0" onclick="validateRadioMerchantButton()">SubMerchant
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="merchantTypeEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3" id="parentMerchantCodeField" style="display: none;">
														<label data-toggle="tooltip" data-placement="top" title="">Primary Merchant Code<span class="required-field">*</span></label>
														<input type="text" class="form-control" id="parentMerchantCode" name="" maxlength="50"
															onblur="validParentMerchantCode('parentMerchantCode','parentMerchantCodeEr');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="parentMerchantCodeEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Company Name<span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="businessName"
															id="businessName" maxlength="50"
															onblur="validateBusinessName()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="businessNameEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">First Name<span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="firstName"
															id="firstName" maxlength="50"
															onblur="validateFirstName()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="firstNameEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Last Name<span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="lastName"
															id="lastName" maxlength="50" onblur="validateLastName()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="lastNameEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Phone<span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="phone"
															id="phone" maxlength="10" onblur="validatePhone()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="phoneEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Fax<!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="fax" id="fax"
															onkeypress="return amountValidate(this,event)"
															maxlength="13" />
														<%-- onblur="validateFax()" --%>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="faxEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Email ID<span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="emailId"
															id="emailId" maxlength="50" onblur="validateEmailId()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="emailIdEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Address 1<span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="address1"
															id="address1" maxlength="50" onblur="validateAddress1()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="address1Er" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Address 2</label>
														<form:input cssClass="form-control" path="address2"
															id="address2" maxlength="50" onblur="validateAddress2()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="address2Er" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">City<span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="city" id="city"
															maxlength="50" onblur="validateCity()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="cityEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Country<span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="country"
															id="country" onblur="validateCountry()"
															onchange="fetchMerchantState(this.value, 'state')">
															<form:option value="">..:Select:..</form:option>
															<c:forEach items="${countryList}" var="country">
																<form:option value="${country.label}">${country.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="countryEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">State<span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="state"
															id="state" onblur="validateState()">
															<form:option value="">..:Select:..</form:option>

														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="stateEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Zip Code<span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="pin" id="pin"
															maxlength="6" onblur="validatePin()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="pinEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3" id="hideStatusfield">
														<label data-toggle="tooltip" data-placement="top" title="">Status<span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="status"
															id="status" onblur="validateStatus()">
															<form:option value="5">Pending</form:option>
															<%-- <form:option value="0">Active</form:option>
															<form:option value="2">In-Active</form:option> --%>
														</form:select>
														<%-- <form: path="status" id="status" value="5"/> --%>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="statusEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Application Mode<span
															class="required-field">*</span></label>
														<form:select cssClass="form-control" path="appMode"
															id="appMode" onblur="validateAppMode()">
															<form:option value="">..:Select:..</form:option>
															<form:option value="DEMO">Demo</form:option>
															<form:option value="PRELIVE">Pre-Live</form:option>
															<form:option value="LIVE">Live</form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="appModeEr" class="red-error">&nbsp;</span>
														</div>
														<form:hidden path="parentMerchantId" id="parentMerchantId"/>
														<form:hidden path="merchantType" id="merchantTypeId"/>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Business URL<span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="businessURL"
															maxlength="50" id="businessURL" onblur="validateURL()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="businessURLEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Looking For?</label>
														<form:textarea cssClass="form-control" path="lookingFor"
															id="lookingFor" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="lookingForEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Business Type</label>
														<form:select cssClass="form-control" path="businessType"
															id="businessType">
															<form:option value=""><spring:message code="sub-merchant-create.label.chooseatype"/></form:option>
															<form:option value="Airline">Airline</form:option>
															<form:option value="Auto Rental">Auto Rental</form:option>
															<form:option value="Clothing Stores">Clothing Stores</form:option>
															<form:option value="Department Stores">Department Stores</form:option>
															<form:option value="Deposit Transactions">Deposit Transactions</form:option>
															<form:option value="Discount Stores">Discount Stores</form:option>
															<form:option value="Drug Stores">Drug Stores</form:option>
															<form:option value="Education">Education</form:option>
															<form:option value="Electric-Appliance">Electric-Appliance</form:option>
															<form:option value="Food Stores-Warehouse">Food Stores-Warehouse</form:option>
															<form:option value="Gas Stations">Gas Stations</form:option>
															<form:option value="Hardware">Hardware</form:option>
															<form:option value="Health Care">Health Care</form:option>
															<form:option value="Hotel-Motel">Hotel-Motel</form:option>
															<form:option value="Interior Furnishings">Interior Furnishings</form:option>
															<form:option value="Other Retail">Other Retail</form:option>
															<form:option value="Other Services">Other Services</form:option>
															<form:option value="Other Transport">Other Transport</form:option>
															<form:option value="Professional Services">Professional Services</form:option>
															<form:option value="Recreation">Recreation</form:option>
															<form:option value="Repair Shops">Repair Shops</form:option>
															<form:option value="Restaurants-Bars">Restaurants-Bars</form:option>
															<form:option value="Sporting-Toy Stores">Sporting-Toy Stores</form:option>
															<form:option value="Vehicles">Vehicles</form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="businessTypeEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="button" class="form-control button pull-right acc-next" value="Continue">
														<input type="button" class="form-control button pull-right marginL10" value="Reset" onclick="resetBasicInfoSignUp()">
														<input type="button" class="form-control button pull-right marginL10" value="Cancel" onclick="cancelRegisterMerchant()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<!-- Account Details Content End -->

											<!-- Bank Details Content Start -->
											<section class="field-element-row bank-info-details-content"
												style="display: none;">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Name<span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="bankAccountName"
															id="bankAccountName" maxlength="50"
															onblur="return clientValidation('bankAccountName', 'first_name_SplChar','bankAccountNameErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAccountNameErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Bank Routing Number<span
															class="required-field">*</span></label>
														<form:input cssClass="form-control"
															path="bankRoutingNumber"
															onkeypress="return amountValidate(this,event)"
															id="bankRoutingNumber" maxlength="9"
															onblur="return clientValidation('bankRoutingNumber', 'routing_number','bankRoutingNumberEr');" />
														<!-- onblur="return validRoutingNumber()"  -->
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankRoutingNumberEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Bank Account Number<span
															class="required-field">*</span></label>
														<form:input cssClass="form-control"
															path="bankAccountNumber" id="bankAccountNumber"
															maxlength="50"
															onblur="return clientValidation('bankAccountNumber', 'account_numberBank','bankAccountNumberErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAccountNumberErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Type<span class="required-field">*</span></label>
														<form:select cssClass="form-control"
															path="bankAccountType" id="bankAccountType"
															onblur="return clientValidation('bankAccountType', 'account_type','bankAccountTypeErrorDiv');">
															<form:option value="">..:Select:..</form:option>
															<form:option value="S">Savings</form:option>
															<form:option value="C">Checking</form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAccountTypeErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Address 1<!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="bankAddress1"
															id="bankAddress1" maxlength="50"
															onblur="return clientValidation('bankAddress1', 'bank_address2','bankAddress1ErrorDiv');" onkeydown="validateSpace(this)"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAddress1ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Address 2</label>
														<form:input cssClass="form-control" path="bankAddress2"
															id="bankAddress2" maxlength="50"
															onblur="return clientValidation('bankAddress2', 'bank_address2','bankAddress2ErrorDiv');" onkeydown="validateSpace(this)"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAddress2ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">City<!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="bankCity"
															id="bankCity" maxlength="50"
															onblur="return clientValidation('bankCity', 'bank_address2','bankCityErrorDiv');" onkeydown="validateSpace(this)"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankCityErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Country<span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="bankCountry"
															id="bankCountry"
															onblur="return clientValidation('bankCountry', 'country','bankCountryErrorDiv');"
															onchange="fetchMerchantState(this.value, 'bankState')">
															<form:option value="">..:Select:..</form:option>
															<c:forEach items="${countryList}" var="country">
																<form:option value="${country.label}">${country.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankCountryErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">State<span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="bankState"
															id="bankState"
															onblur="return clientValidation('bankState', 'state','bankStateErrorDiv');">
															<form:option value="">..:Select:..</form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankStateErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Zip Code<span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="bankPin" onkeypress ="generalZipCode()"
															id="bankPin" maxlength="7"
															onblur="return zipCodeNotEmpty(id)" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankPinEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Name on Account<span class="required-field">*</span></label>
														<form:input cssClass="form-control"
															path="bankNameOnAccount" id="bankNameOnAccount"
															onblur="return clientValidation('bankNameOnAccount', 'first_name_SplChar','bankNameOnAccountErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankNameOnAccountErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															class="form-control button pull-right bank-next" onclick="return zipCodeNotEmpty('bankPin')"
															value="Continue"> <input type="button"
															class="form-control button pull-right marginL10 bank-prev"
															value="Previous"> <input type="button"
															class="form-control button pull-right marginL10"
															value="Reset" onclick="resetBankInfo()"> <input
															type="button"
															class="form-control button pull-right marginL10"
															value="Cancel" onclick="cancelRegisterMerchant()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<!-- Bank Details Content End -->
											<!-- Legal Entity Content Starts  -->
											<section class="field-element-row legal-details-content"
												style="display: none;">
												<fieldset class="col-sm-12">

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">SSN<!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalSSN"
															onkeypress="return amountValidate(this,event)"
															id="legalSSN" maxlength="20" />
														<%-- onblur="return clientValidation('legalSSN', 'ssn','legalSSNErrorDiv');" --%>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalSSNErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">First Name<!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalFirstName"
															id="legalFirstName" maxlength="50" onblur="return clientValidation('legalFirstName', 'first_name_NotMand','legalFirstNameErrorDiv');"/><!--  -->
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalFirstNameErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Last Name<!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalLastName"
															id="legalLastName" maxlength="50" onblur="return clientValidation('legalLastName', 'first_name_NotMand','legalLastNameErrorDiv');"/><!--   -->
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalLastNameErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Mobile Phone<!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control"
															path="legalMobilePhone" maxlength="10"
															id="legalMobilePhone" onblur="return clientValidation('legalMobilePhone', 'mobile_optional','legalMobilePhoneErrorDiv');"/><!--  -->
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalMobilePhoneErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""> Date of Birth<!-- <span class="required-field">*</span> --></label>
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
														<label data-toggle="tooltip" data-placement="top" title="">Passport Number<!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalPassport"
															id="legalPassport" maxlength="20" onblur="return clientValidation('legalPassport', 'passport_number','legalPassportErrorDiv');"/>
														<%--   --%>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalPassportErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Country of Residence<!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control"
															path="legalCountryResidence" id="legalCountryResidence"
															maxlength="50" />
														<%-- onblur="return clientValidation('legalCountryResidence', 'bank_country','legalCountryResidenceErrorDiv');" --%>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalCountryResidenceErrorDiv"
																class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Country of Citizenship<!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalCitizen" maxlength="50"
															id="legalCitizen" />
														<%-- onblur="return clientValidation('legalCitizen', 'bank_country','legalCitizenErrorDiv');" --%>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalCitizenErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Home Phone<!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalHomePhone"
															onkeypress="return amountValidate(this,event)"  maxlength="12"
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
														<input type="button" class="form-control button pull-right legal-next" value="Continue">
														<input type="button" class="form-control button pull-right marginL10 legal-prev" value="Previous">
														<input type="button" class="form-control button pull-right marginL10" value="Reset" onclick="resetLegalEntityInfo()">
														<input type="button" class="form-control button pull-right marginL10" value="Cancel" onclick="cancelRegisterMerchant()">
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
														<label data-toggle="tooltip" data-placement="top" title="">Entity Legal Name<span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalName"
															id="legalName" maxlength="101"
															onblur="return clientValidation('legalName', 'first_name_SplChar','legalNameErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalNameErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">EIN/Tax ID:<span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalTaxId"
															id="legalTaxId" maxlength="50"
															onblur="return clientValidation('legalTaxId', 'eIN_taxId','legalTaxIdErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalTaxIdErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Type<span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="legalType"
															id="legalType"
															onblur="return clientValidation('legalType', 'state','legalTypeErrorDiv');">
															<form:option value="">..:Select:..</form:option>
															<form:option value="1">Association Estate Trust</form:option>
															<form:option value="2">Corporation</form:option>
															<form:option value="3">Government Agency</form:option>
															<form:option value="4">Individual/Sole Proprietorship</form:option>
															<form:option value="5">International Organization</form:option>
															<form:option value="6">Limited Liability Company(LLC)</form:option>
															<form:option value="7">Partnership</form:option>
															<form:option value="8">Tax Exempt Organization(501C)</form:option>
															<form:option value="11">Testing</form:option>
														</form:select>

														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalTypeErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Expected Annual Card Sales<span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalAnnualCard"
															id="legalAnnualCard" maxlength="18"
															onblur="return clientValidation('legalAnnualCard', 'dollar_amount','legalAnnualCardErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalAnnualCardErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Address 1<span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalAddress1"
															id="legalAddress1"
															onblur="return clientValidation('legalAddress1', 'bank_address1','legalAddress1ErrorDiv');" onkeydown="validateSpace(this)"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalAddress1ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Address 2</label>
														<form:input cssClass="form-control" path="legalAddress2"
															id="legalAddress2"
															onblur="return clientValidation('legalAddress2', 'bank_address2','legalAddress2ErrorDiv');" onkeydown="validateSpace(this)"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalAddress2ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">City<span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalCity"
															id="legalCity"
															onblur="return clientValidation('legalCity', 'bank_city','legalCityErrorDiv');" onkeydown="validateSpace(this)"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalCityErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Country<span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="legalCountry"
															id="legalCountry"
															onblur="return clientValidation('legalCountry', 'country','legalCountryErrorDiv');"
															onchange="fetchMerchantState(this.value, 'legalState')">
															<form:option value="">..:Select:..</form:option>
															<c:forEach items="${countryList}" var="country">
																<form:option value="${country.label}">${country.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalCountryErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">State<span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="legalState"
															id="legalState"
															onblur="return clientValidation('legalState', 'state','legalStateErrorDiv');">
															<form:option value="">..:Select:..</form:option>

														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalStateErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Zip Code<span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalPin" onkeypress ="generalZipCode()"
															id="legalPin"  maxlength="7"
															onblur="return zipCodeNotEmpty(id)" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalPinEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															class="form-control button pull-right legal-rep-next" onclick="return zipCodeNotEmpty('legalPin')"
															value="Continue"> <input type="button"
															class="form-control button pull-right marginL10 legal-rep-prev"
															value="Previous"> <input type="button"
															class="form-control button pull-right marginL10"
															value="Reset" onclick="resetLegalEntityRepInfo()">
														<input type="button"
															class="form-control button pull-right marginL10"
															value="Cancel" onclick="cancelRegisterMerchant()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<!-- Legal Details Content End -->
											<!-- Free Transactions Content Start -->
											<section class="field-element-row free-transactions-content"
												style="display: none;">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">User Name<span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="userName"
															id="userName" maxlength="50" onblur="vlalidateUserName()" onkeydown="validateSpace(this)"/>
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
															value="Continue"> <input type="button"
															class="form-control button pull-right marginL10 free-prev"
															value="Previous"> <input type="button"
															class="form-control button pull-right marginL10"
															value="Reset" onclick="resetAdditionalInfo()"> <input
															type="button"
															class="form-control button pull-right marginL10"
															value="Cancel" onclick="cancelRegisterMerchant()">
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
														<label data-toggle="tooltip" data-placement="top" title="">Auto settlement options<span class="required-field">*</span></label><br> 
														<input type="radio" id="allowAutoSettlement" name="autoSettlement" value="1" onclick="validateRadio()">Yes
														<input type="radio" id="noAutoSettlement" name="autoSettlement" value="0" onclick="validateRadio()">No
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="noAutoSettlementEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3 auto_settlement_opts" style="display: none;">
														<label data-toggle="tooltip" data-placement="top" title="">Fee Program<span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="feeProgram"
															id="feeProgram" onblur="validatefeeProgram()">
															<form:option value="">..:Select:..</form:option>
															<c:forEach items="${feeprogramnames}" var="feename">
																<form:option value="${feename.label}">${feename.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="feeProgramEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3 auto_settlement_opts" style="display: none;">
														<label data-toggle="tooltip" data-placement="top" title="">Auto Transfer Limit</label>
														<form:input cssClass="form-control" maxlength="10"
															path="autoTransferLimit" id="autoTransferLimit"
															onblur="validateAutoTransferLimit()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoTransferLimitEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3 auto_settlement_opts" style="display: none;">
														<label data-toggle="tooltip" data-placement="top" title="">Auto Payment Method <span
															class="required-field">*</span></label>
														<form:select cssClass="form-control"
															path="autoPaymentMethod" id="autoPaymentMethod"
															onblur="validateAutoPaymentMethod()">
															<form:option value="">..:Select:..</form:option>
															<form:option value="EFT">EFT</form:option>
															<form:option value="Cheque">Cheque</form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoPaymentMethodEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3 auto_settlement_opts" style="display: none;">
														<label data-toggle="tooltip" data-placement="top" title="">Auto Transfer<span class="required-field">*</span></label>
														<form:select cssClass="form-control"
															onchange="showAutoTransferDayFields()"
															path="autoTransferDay" id="autoTransferDay"
															onblur="validateAutoTransferDayFields()">
															<form:option value="">..:Select:..</form:option>
															<form:option value="D">Daily</form:option>
															<form:option value="W">Weekly</form:option>
															<form:option value="M">Monthly</form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoTransferDayEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3" id="weeklySettlement" style="display: none;">
														<label data-toggle="tooltip" data-placement="top" title="">Select Day Of The Week<span class="required-field">*</span></label>
														<form:select cssClass="form-control"
															onblur="return clientValidation('autoTransferWeeklyDay', 'state','autoTransferWeeklyDayEr');"
															path="autoTransferWeeklyDay" id="autoTransferWeeklyDay">
															<form:option value="">.:Select:.</form:option>
															<form:option value="1">Monday</form:option>
															<form:option value="2">Tuesday</form:option>
															<form:option value="3">Wednesday</form:option>
															<form:option value="4">Thursday</form:option>
															<form:option value="5">Friday</form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoTransferWeeklyDayEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3" id="monthlySettlement" style="display: none;">
														<label data-toggle="tooltip" data-placement="top" title="">Select Day Of Month<span class="required-field">*</span></label>
														<form:select cssClass="form-control"
															onblur="return clientValidation('autoTransferMonthlyDay', 'state','autoTransferMonthlyDayEr');"
															path="autoTransferMonthlyDay" id="autoTransferMonthlyDay">
															<form:option value="">.:Select:.</form:option>
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
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															class="form-control button pull-right atm-next"
															value="Continue"> <input
															type="button"
															class="form-control button pull-right marginL10 atm-prev"
															value="Previous"> <input type="button"
															class="form-control button pull-right marginL10"
															value="Reset" onclick="resetConfigurationsInfo()">
														<input type="button"
															class="form-control button pull-right marginL10"
															value="Cancel" onclick="cancelRegisterMerchant()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<!-- ATM Transactions Content End -->
											<!-- POS Transactions Content Start -->
											<jsp:include page="merchant-signup-confirmation.jsp"></jsp:include>
											<!-- POS Transactions Content End -->
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
			<jsp:include page="footer.jsp"></jsp:include>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script> <script src="../js/utils.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/validation.js"></script>
	<!-- <script src="../js/chatak-ajax.js"></script> -->
	<script src="../js/messages.js"></script>
	<script type="text/javascript" src="../js/merchant.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script>
		window.onload = function() {
			//	populatedropdown("autoTransferDay");
		}
		function getState(countryId) {
			var cid = 2;
			var strURL = "findState?country=" + cid;
			var req = getXMLHTTP();
			if (req) {
				req.onreadystatechange = function() {
					if (req.readyState == 4) {
						// only if "OK"
						if (req.status == 200) {
							document.getElementById('statediv').innerHTML = req.responseText;
						} else {
							alert("There was a problem while using XMLHTTP:\n"
									+ req.statusText);
						}
					}
				}
				req.open("GET", strURL, true);
				req.send(null);
			}
		}
	</script>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->

	<script>
		/* Common Navigation Include End */
		/* DatePicker Javascript Strat*/
		$(document).ready(function() {
			$(window).keydown(function(event){
			    if(event.keyCode == 13) {
			      event.preventDefault();
			      return false;
			    }
			  });
			$('#hideStatusfield').hide();
			$('#hideStatusfieldCon').hide();
			$("#merchant").prop("checked", true)
		});

		$(".focus-field").click(function() {
			$(this).children('.effectiveDate').focus();
		});
		$('.effectiveDate').datetimepicker({
			timepicker : false,
			format : 'd/m/Y',
			formatDate : 'Y/m/d',
		});

		function highlightMainContent() {
			$("#navListId2").addClass("active-background");
		}
		/* DatePicker Javascript End*/
		$(".bank-info-details-content, .legal-details-content, .legal-details-rep-content, .free-transactions-content, .atm-transaction-content, .pos-transaction-content").hide();
		$(".account-details-content").show();
		$(".merchant-arrow").show();
		$(".contact-arrow, .bank-info-arrow, .legal-arrow, .legal-rep-arrow, .bank-legal-arrow, .bank-arrow, .configuration-arrow, .final-arrow").hide();
		$(".account-details-list, .bank-prev").click(function(){
							$(".merchant-circle-tab").addClass("active-circle");
							$(".bank-info-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .contact-circle-tab, .bank-circle-tab, .final-circle-tab").removeClass("active-circle");
							$(".merchant-arrow").show();
							$(".bank-info-arrow, .legal-arrow, .legal-rep-arrow, .contact-arrow, .bank-arrow, .final-arrow").hide();
							$(".account-details-content").show();
							$(".atm-transaction-content,.bank-info-details-content, .legal-details-content, .legal-details-rep-content, .pos-transaction-content, .free-transactions-content")
									.hide();
						});

		$(".bank-list, .acc-next, .legal-prev").click(function(){
							if (!validateCreateMerchantStep1SignUp()
									| resetBankInfoErrorMsg()) {
								return false;
							}
							$(".bank-info-circle-tab").addClass("active-circle");
							$(".merchant-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .contact-circle-tab, .bank-circle-tab, .final-circle-tab").removeClass("active-circle");
							$(".bank-info-arrow").show();
							$(".merchant-arrow, .legal-arrow, .legal-rep-arrow, .contact-arrow, .configuration-arrow, .bank-arrow, .configuration-arrow, .final-arrow").hide();
							$(".bank-info-details-content").show();
							$(".account-details-content, .legal-details-content, .legal-details-rep-content, .atm-transaction-content, .pos-transaction-content, .free-transactions-content")
									.hide();
						});

		$(".legal-entiy-list, .bank-next, .legal-rep-prev").click(function() {
							if (!validateCreateMerchantStep2()
									| !validateCreateMerchantStep1SignUp()
									| resetLegalEntityErrorMsg()) {
								return false;
							}
							$(".legal-circle-tab").addClass("active-circle");
							$(".merchant-circle-tab, .bank-info-circle-tab, .legal-circle-rep-tab, .contact-circle-tab, .bank-circle-tab, .final-circle-tab").removeClass("active-circle");
							$(".legal-arrow").show();
							$(".merchant-arrow, .legal-rep-arrow, .bank-info-arrow, .contact-arrow, .configuration-arrow, .bank-arrow, .configuration-arrow, .final-arrow").hide();
							$(".legal-details-content").show();
							$(".account-details-content, .legal-details-rep-content, .bank-info-details-content, .atm-transaction-content, .pos-transaction-content, .free-transactions-content")
									.hide();
						});

		$(".legal-entiy-rep-list, .legal-next, .free-prev").click(function() {
							if (!validateLegalEntity()
									| !validateCreateMerchantStep2()
									| !validateCreateMerchantStep1SignUp()
									| resetLegalEntityInfoErrorMsg()) {
								return false;
							}
							$(".legal-circle-rep-tab").addClass("active-circle");
							$(".merchant-circle-tab, .bank-info-circle-tab, .legal-circle-tab, .contact-circle-tab, .bank-circle-tab, .final-circle-tab").removeClass("active-circle");
							$(".legal-rep-arrow").show();
							$(".merchant-arrow, .bank-info-arrow, .legal-arrow, .contact-arrow, .configuration-arrow, .bank-arrow, .configuration-arrow, .final-arrow").hide();
							$(".legal-details-rep-content").show();
							$(".account-details-content, .bank-info-details-content, .legal-details-content, .atm-transaction-content, .pos-transaction-content, .free-transactions-content")
									.hide();
						});

		$(".free-transactions-list, .legal-rep-next, .atm-prev").click(function() {
							if (!validateCreateMerchantStep3()
									| !validateCreateMerchantStep1SignUp()
									| !validateCreateMerchantStep2()
									| !validateLegalEntity()
									| resetAdditionalInfoErrorMsg()) {
								return false;
							}
							$(".contact-circle-tab").addClass("active-circle");
							$(".merchant-circle-tab,.bank-info-circle-tab, .bank-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .final-circle-tab").removeClass("active-circle");
							$(".contact-arrow").show();
							$(".merchant-arrow, .legal-arrow, .legal-rep-arrow, .bank-info-arrow, .configuration-arrow, .bank-arrow, .final-arrow").hide()
							$(".free-transactions-content").show();
							$(".atm-transaction-content, .legal-details-content, .legal-details-rep-content, .bank-info-details-content, .pos-transaction-content, .account-details-content")
									.hide();
						});
		$(".atm-transactions-list, .free-next, .pos-prev").click(function() {
			  if (!validateCreateMerchantStep4()
					 |!validateCreateMerchantStep2()
					 |!validateCreateMerchantStep3()
					 |!validateCreateMerchantStep1SignUp()
					 |!validateLegalEntity()
					 |resetConfigurationsInfoErrorMsg()) { 
				return false;
			}
			$(".bank-circle-tab").addClass("active-circle");
			$(".merchant-circle-tab,.bank-info-circle-tab, .contact-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .final-circle-tab").removeClass("active-circle");
			$(".configuration-arrow").show();
			$(".contact-arrow, .merchant-arrow, .legal-arrow, .legal-rep-arrow, .bank-info-arrow, .final-arrow").hide()
			$(".atm-transaction-content").show();
			$(".free-transactions-content, .bank-info-details-content, .legal-details-content, .legal-details-rep-content, .pos-transaction-content, .account-details-content").hide();
			if($('#allowAutoSettlement').is(':checked')) {
				$('.auto_settlement_opts').show();
			} else {
				$('.auto_settlement_opts').hide();
			}
		});
		$(".pos-transactions-list, .atm-next").click(function() {
			  if (!validateCreateMerchantStep5()
					 |!validateCreateMerchantStep1SignUp()
					 |!validateCreateMerchantStep2()
					 |!validateCreateMerchantStep3()
					 |!validateLegalEntity()
					 |!validateCreateMerchantStep4()) {
				return false;
			}
			$(".final-circle-tab").addClass("active-circle");
			$(".merchant-circle-tab, .bank-info-circle-tab, .contact-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .bank-circle-tab").removeClass("active-circle");
			$(".final-arrow").show();
			$(".contact-arrow, .bank-arrow,.configuration-arrow, .bank-info-arrow, .legal-arrow, .legal-rep-arrow, .merchant-arrow").hide()
			$(".pos-transaction-content").show();
			$(".free-transactions-content, .bank-info-details-content, .legal-details-content, .legal-details-rep-content, .atm-transaction-content, .account-details-content").hide();
			if(!$('#allowAutoSettlement').is(':checked')) {
				setLable('confirmMautoTransferLimit', '');
				setLable('confirmMautoTransferDay', '');
				setLable('confirmAutoTransferWeeklyDay', '');
				setLable('confirmAutoTransferMonthlyDay', '');
				setLable('confirmMautoPaymentMethod', '');
				setLable('confirmMfeeProgram', '');
			}
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
		$('.atm-transaction-content').on('click','#allowAutoSettlement,#noAutoSettlement',function(){
			if($('#allowAutoSettlement').is(':checked')) {
				$('.auto_settlement_opts').show();
				$('#feeProgram').val('');
				$('#autoTransferLimit').val('');
				$('#autoPaymentMethod').val('');
				$('#autoTransferDay').val('');
				$('#autoTransferWeeklyDay').val('');
				$('#autoTransferMonthlyDay').val('');
			} else {
				$('.auto_settlement_opts').hide();
				$('#weeklySettlement').hide();
				$('#monthlySettlement').hide();
				setLable('autoTransferLimitEr','');
				setLable('autoPaymentMethodEr','');
				setLable('autoTransferDayEr','');
				setLable('feeProgramEr','');
			}
		});
	</script>
</body>
</html>