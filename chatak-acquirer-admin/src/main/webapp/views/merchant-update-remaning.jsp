<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<html lang="en">
<head>
<title>Merchant Update Renaming</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
<section class="field-element-row account-details-content" style="display:none;">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.companyname"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="businessName"
															id="businessName" maxlength="50"
															onblur="this.value=this.value.trim();validateBusinessName()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="businessNameEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.merchantcode"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="merchantCode"
															id="merchantCode" maxlength="50"
															onblur="this.value=this.value.trim();validateMerchantCode()" readonly="true" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="merchantCodeEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.firstname"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="firstName"
															id="firstName" maxlength="50"
															onblur="this.value=this.value.trim();validateFirstName()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="firstNameEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.lastname"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="lastName"
															id="lastName" maxlength="50" onblur="this.value=this.value.trim();validateLastName()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="lastNameEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.mobilephone"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="phone"
															id="phone" maxlength="10" onblur="this.value=this.value.trim();validatePhone()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="phoneEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.fax"/></label>
														<form:input cssClass="form-control" path="fax" id="fax" onkeypress="return amountValidate(this,event)"
															maxlength="13" onblur="this.value=this.value.trim();clientValidation('fax','fax','faxEr')"/><%--  onblur="validateFax()" --%>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="faxEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.emailID"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="emailId"
															id="emailId" maxlength="50" onblur="this.value=this.value.trim();validateEmailId()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="emailIdEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.address1"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="address1"
															id="address1" maxlength="50" onblur="this.value=this.value.trim();validateAddress1()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="address1Er" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.address2"/></label>
														<form:input cssClass="form-control" path="address2"
															id="address2" maxlength="50" onblur="this.value=this.value.trim();validateAddress2();clientValidation('address2','address2','address2Er')" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="address2Er" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.city"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="city" id="city"
															maxlength="50" onblur="this.value=this.value.trim();validateCity()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="cityEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.country"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="country"
															id="country" onblur="validateCountry()"
															onchange="fetchMerchantState(this.value, 'state')">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<c:forEach items="${countryList}" var="country">
																<form:option value="${country.label}">${country.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="countryEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.state"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="state"
															id="state" onblur="validateState()">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<c:forEach items="${stateList}" var="item">
																<form:option value="${item.label}">${item.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="stateEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.zipcode"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="pin" id="pin"
															onkeypress ="generalZipCode()"
															maxlength="7" onblur="this.value=this.value.trim();return zipCodeNotEmpty(id)" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="pinEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<%-- <fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Status<span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="status"
															id="status" onblur="validateStatus()">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<form:option value="0"><spring:message code="common.label.active"/></form:option>
															<form:option value="1"><spring:message code="common.label.pending" /></form:option>
															<form:option value="2"><spring:message code="common.label.inactive"/></form:option>
														</form:select>
														<form:hidden path="merchantType" id="merchantType" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="statusEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset> --%>
														<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.businesstype"/></label>
														<form:select cssClass="form-control" path="businessType"
															id="businessType">
															<form:option value=""><spring:message code="merchant.label.choosetype"/></form:option>
															<form:option value="Airline"><spring:message code="merchant.label.airline"/></form:option>
															<form:option value="Auto Rental"><spring:message code="merchant.label.autorental"/></form:option>
															<form:option value="Clothing Stores"><spring:message code="merchant.label.clothingstores"/></form:option>
															<form:option value="Department Stores"><spring:message code="merchant.label.departmentstores"/></form:option>
															<form:option value="Deposit Transactions"><spring:message code="merchant.label.deposittransactions"/></form:option>
															<form:option value="Discount Stores"><spring:message code="merchant.label.discountstores"/></form:option>
															<form:option value="Drug Stores"><spring:message code="merchant.label.drugstores"/></form:option>
															<form:option value="Education"><spring:message code="merchant.label.education"/></form:option>
															<form:option value="Electric-Appliance"><spring:message code="merchant.label.electricappliance"/></form:option>
															<form:option value="Food Stores-Warehouse"><spring:message code="merchant.label.foodstoreswarehouse"/></form:option>
															<form:option value="Gas Stations"><spring:message code="merchant.label.gasstations"/></form:option>
															<form:option value="Hardware"><spring:message code="merchant.label.hardware"/></form:option>
															<form:option value="Health Care"><spring:message code="merchant.label.healthcare"/></form:option>
															<form:option value="Hotel-Motel"><spring:message code="merchant.label.hotelmotel"/></form:option>
															<form:option value="Interior Furnishings"><spring:message code="merchant.label.interiorfurnishings"/></form:option>
															<form:option value="Other Retail"><spring:message code="merchant.label.otherretail"/></form:option>
															<form:option value="Other Services"><spring:message code="merchant.label.otherservices"/></form:option>
															<form:option value="Other Transport"><spring:message code="merchant.label.othertransport"/></form:option>
															<form:option value="Professional Services"><spring:message code="merchant.label.professionalservices"/></form:option>
															<form:option value="Recreation"><spring:message code="merchant.label.recreation"/></form:option>
															<form:option value="Repair Shops"><spring:message code="merchant.label.repairshops"/></form:option>
															<form:option value="Restaurants-Bars"><spring:message code="merchant.label.restaurantsbars"/></form:option>
															<form:option value="Sporting-Toy Stores"><spring:message code="merchant.label.sportingtoystores"/></form:option>
															<form:option value="Vehicles"><spring:message code="merchant.label.vehicles"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="businessTypeEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
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
												<!--Panel Action Button Start1 -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															value='<spring:message code="common.label.continue"/>' class="form-control button pull-right acc-next"
															> <input type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.cancel"/>' onclick="openCancelConfirmationPopup()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											
											<section class="field-element-row bank-info-details-content"
												style="display: none;">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.name"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="bankAccountName"
															id="bankAccountName" maxlength="50"
															onblur="this.value=this.value.trim();return clientValidation('bankAccountName', 'first_name_SplChar','bankAccountNameErrorDiv');" />
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
															onblur="this.value=this.value.trim();return clientValidation('bankRoutingNumber', 'routing_number','bankRoutingNumberEr');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankRoutingNumberEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.bankaccountnumber"/><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control"
															path="bankAccountNumber" id="bankAccountNumber"
															maxlength="50"
															onblur="this.value=this.value.trim();return clientValidation('bankAccountNumber', 'account_numberBank','bankAccountNumberErrorDiv');" />
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
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.address1"/></label>
														<form:input cssClass="form-control" path="bankAddress1"
															id="bankAddress1" maxlength="50"
															onblur="this.value=this.value.trim();return clientValidation('bankAddress1', 'bank_address2','bankAddress1ErrorDiv');"
															onkeydown="validateSpace(this)" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAddress1ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.address2"/></label>
														<form:input cssClass="form-control" path="bankAddress2"
															id="bankAddress2" maxlength="50"
															onblur="this.value=this.value.trim();return clientValidation('bankAddress2', 'bank_address2','bankAddress2ErrorDiv');"
															onkeydown="validateSpace(this)" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAddress2ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.city"/></label>
														<form:input cssClass="form-control" path="bankCity"
															id="bankCity" maxlength="50"
															onblur="this.value=this.value.trim();return clientValidation('bankCity', 'bank_address2','bankCityErrorDiv');"
															onkeydown="validateSpace(this)" />
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
															onkeypress ="generalZipCode()"
															id="bankPin" maxlength="7"
															onblur="this.value=this.value.trim();return zipCodeNotEmpty(id)" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankPinEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.nameonaccount"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control"
															path="bankNameOnAccount" id="bankNameOnAccount"
															onblur="this.value=this.value.trim();return clientValidation('bankNameOnAccount', 'first_name_SplChar','bankNameOnAccountErrorDiv');" />
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
													<fieldset class="col-sm-3">
														<label><spring:message code="merchant.label.iso"/><span class="required-field">*</span></label>
														<form:input path="associatedTo" id="associatedTo" readonly="true" cssClass="form-control"/>
														<div class="discriptionErrorMsg">
															<span id="associatedToEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>													
												</fieldset>
												<!--Panel Action Button Start2 -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															value='<spring:message code="common.label.continue"/>' class="form-control button pull-right bank-next" onclick="return zipCodeNotEmpty('bankPin')"
															> <input type="button"
															value='<spring:message code="common.label.previous"/>' class="form-control button pull-right marginL10 bank-prev"
															> <input type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.cancel"/>' onclick="openCancelConfirmationPopup()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<%-- <section class="field-element-row legal-details-content"
												style="display: none;">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.SSN"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalSSN"
															id="legalSSN" maxlength="11" onblur="return ssnValidation()"/>onblur="return clientValidation('legalSSN', 'ssn','legalSSNErrorDiv');"
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalSSNErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.firstname"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalFirstName"
															id="legalFirstName" maxlength="50"
															onblur="this.value=this.value.trim();return clientValidation('legalFirstName', 'first_name_NotMand','legalFirstNameErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalFirstNameErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.lastname"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalLastName"
															id="legalLastName" maxlength="50"
															onblur="this.value=this.value.trim();return clientValidation('legalLastName', 'first_name_NotMand','legalLastNameErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalLastNameErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.mobilephone"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control"
															path="legalMobilePhone" maxlength="10"
															id="legalMobilePhone"
															onblur="this.value=this.value.trim();return clientValidation('legalMobilePhone', 'mobile_optional','legalMobilePhoneErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalMobilePhoneErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.dateofbirth"/><!-- <span class="required-field">*</span> --></label>
														<div class="input-group focus-field">
															<form:input cssClass="form-control effectiveDate"
																path="legalDOB" id="legalDOB"
																onblur="validateLegalDOB()" />
															onblur="return clientValidation('legalDOB', 'startDate','legalDOBErrorDiv');"
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
															id="legalPassport" maxlength="20"
															onblur="this.value=this.value.trim();return clientValidation('legalPassport', 'passport_number','legalPassportErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalPassportErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.countryresidence"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control"
															path="legalCountryResidence" id="legalCountryResidence"
															maxlength="50" onblur="this.value=this.value.trim();" />
														 onblur="return clientValidation('legalCountryResidence', 'bank_country','legalCountryResidenceErrorDiv');"
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalCountryResidenceErrorDiv"
																class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.countrycitizenship"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalCitizen"
															maxlength="50" id="legalCitizen"
														onblur="this.value=this.value.trim();" />
														onblur="return clientValidation('legalCitizen', 'bank_country','legalCitizenErrorDiv');"
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalCitizenErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.homephone"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="legalHomePhone"
															onkeypress="return amountValidate(this,event)"
															maxlength="12" id="legalHomePhone" onblur="this.value=this.value.trim();"/>
														onblur="return clientValidation('legalHomePhone', 'contact_phone','legalHomePhoneErrorDiv');"
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalHomePhoneErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start3 -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															value='<spring:message code="common.label.continue"/>' class="form-control button pull-right legal-next"
															> <input
															type="button"
															value='<spring:message code="common.label.previous"/>' class="form-control button pull-right marginL10 legal-prev"
															> <input type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.cancel"/>' onclick="openCancelConfirmationPopup()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section> --%>
										<!--Legal Entity Content Ends  -->
										<script src="../js/common-lib.js"></script>
</body>
