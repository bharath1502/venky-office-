<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript" src="../js/browser-close.js"></script>
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
<section class="field-element-row account-details-content"
												style="display: none;">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show-remaining.label.companyname"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="businessName"
															id="businessName" maxlength="50"
															onblur="validateBusinessName()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="businessNameEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show-remaining.label.merchantcode"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="merchantCode"
															id="merchantCode" maxlength="50"
															onblur="validateMerchantCode()" readonly="true" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="merchantCodeEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show-remaining.label.firstname"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="firstName"
															id="firstName" maxlength="50"
															onblur="validateFirstName()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="firstNameEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show-remaining.label.lastname"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="lastName"
															id="lastName" maxlength="50" onblur="validateLastName()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="lastNameEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show-remaining.label.phone"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="phone"
															id="phone" maxlength="10" onblur="validatePhone()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="phoneEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show-remaining.label.fax"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="fax" id="fax"
															onkeypress="return amountValidate(this,event)"
															maxlength="13" />
														<%--  onblur="validateFax()" --%>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="faxEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show-remaining.label.emailid"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="emailId"
															id="emailId" readonly="true" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="emailIdEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show-remaining.label.address1"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="address1"
															id="address1" maxlength="50" onblur="validateAddress1()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="address1Er" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show-remaining.label.address2"/></label>
														<form:input cssClass="form-control" path="address2"
															id="address2" maxlength="50" onblur="validateAddress2()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="address2Er" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show-remaining.label.city"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="city" id="city"
															maxlength="50" onblur="validateCity()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="cityEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show-remaining.label.country"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="country"
															id="country" onblur="validateCountry()"
															onchange="fetchMerchantState(this.value, 'state')">
															<form:option value="">..:<spring:message code="pending-merchant-show-remaining.label.select"/>:..</form:option>
															<c:forEach items="${countryList}" var="country">
																<form:option value="${country.label}">${country.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="countryEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show-remaining.label.state"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="state"
															id="state" onblur="validateState()">
															<form:option value="">..:<spring:message code="pending-merchant-show-remaining.label.select"/>:..</form:option>
															<c:forEach items="${stateList}" var="item">
																<form:option value="${item.label}">${item.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="stateEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show-remaining.label.zipcode"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="pin" id="pin"
															maxlength="10" onblur="validatePin()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="pinEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show-remaining.label.status"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="status"
															id="status" onblur="validateStatus()">
															<form:option value="">..:<spring:message code="pending-merchant-show-remaining.label.select"/>:..</form:option>
															<form:option value="0"><spring:message code="pending-merchant-show-remaining.label.active"/></form:option>
															<form:option value="1"><spring:message code="pending-merchant-show-remaining.label.pending"/></form:option>
															<form:option value="2"><spring:message code="pending-merchant-show-remaining.label.inactive"/></form:option>
															<form:option value="5"><spring:message code="pending-merchant-show-remaining.label.selfregistrationpending"/></form:option>
														</form:select>
														<form:hidden path="merchantType" id="merchantType" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="statusEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show-remaining.label.applicationmode"/><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control" path="appMode"
															id="appMode" onblur="validateAppMode()">
															<form:option value="">..:<spring:message code="pending-merchant-show-remaining.label.select"/>:..</form:option>
															<form:option value="DEMO"><spring:message code="pending-merchant-show-remaining.label.demo"/></form:option>
															<form:option value="PRELIVE"><spring:message code="pending-merchant-show-remaining.label.prelive"/></form:option>
															<form:option value="LIVE"><spring:message code="pending-merchant-show-remaining.label.live"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="appModeEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show-remaining.label.businessurl"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="businessURL"
															maxlength="50" id="businessURL" onclick="validateURL()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="businessURLEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show-remaining.label.lookingfor"/>?</label>
														<form:textarea cssClass="form-control" path="lookingFor"
															readonly="true" id="lookingFor" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="lookingForEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show-remaining.label.businesstype"/></label>
														<form:select cssClass="form-control" path="businessType"
															disabled="true" id="businessType">
															<form:option value="">.:<spring:message code="pending-merchant-show-remaining.label.chooseatype"/>:.</form:option>
															<form:option value="Airline"><spring:message code="pending-merchant-show-remaining.label.airline"/></form:option>
															<form:option value="Auto Rental"><spring:message code="pending-merchant-show-remaining.label.autorental"/></form:option>
															<form:option value="Clothing Stores"><spring:message code="pending-merchant-show-remaining.label.clothingstores"/></form:option>
															<form:option value="Department Stores"><spring:message code="pending-merchant-show-remaining.label.departmentstores"/></form:option>
															<form:option value="Deposit Transactions"><spring:message code="pending-merchant-show-remaining.label.deposittransactions"/></form:option>
															<form:option value="Discount Stores"><spring:message code="pending-merchant-show-remaining.label.discountstores"/></form:option>
															<form:option value="Drug Stores"><spring:message code="pending-merchant-show-remaining.label.drugstores"/></form:option>
															<form:option value="Education"><spring:message code="pending-merchant-show-remaining.label.education"/></form:option>
															<form:option value="Electric-Appliance"><spring:message code="pending-merchant-show-remaining.label.electricAppliance"/></form:option>
															<form:option value="Food Stores-Warehouse"><spring:message code="pending-merchant-show-remaining.label.foodstoreswarehouse"/></form:option>
															<form:option value="Gas Stations"><spring:message code="pending-merchant-show-remaining.label.gasstations"/></form:option>
															<form:option value="Hardware"><spring:message code="pending-merchant-show-remaining.label.hardware"/></form:option>
															<form:option value="Health Care"><spring:message code="pending-merchant-show-remaining.label.healthcare"/></form:option>
															<form:option value="Hotel-Motel"><spring:message code="pending-merchant-show-remaining.label.hotelmotel"/></form:option>
															<form:option value="Interior Furnishings"><spring:message code="pending-merchant-show-remaining.label.interiorfurnishings"/></form:option>
															<form:option value="Other Retail"><spring:message code="pending-merchant-show-remaining.label.otherretail"/></form:option>
															<form:option value="Other Services"><spring:message code="pending-merchant-show-remaining.label.otherservices"/></form:option>
															<form:option value="Other Transport"><spring:message code="pending-merchant-show-remaining.label.othertransport"/></form:option>
															<form:option value="Professional Services"><spring:message code="pending-merchant-show-remaining.label.professionalservices"/></form:option>
															<form:option value="Recreation"><spring:message code="pending-merchant-show-remaining.label.recreation"/></form:option>
															<form:option value="Repair Shops"><spring:message code="pending-merchant-show-remaining.label.repairshops"/></form:option>
															<form:option value="Restaurants-Bars"><spring:message code="pending-merchant-show-remaining.label.restaurantsbars"/></form:option>
															<form:option value="Sporting-Toy Stores"><spring:message code="pending-merchant-show-remaining.label.supportingtoystores"/></form:option>
															<form:option value="Vehicles"><spring:message code="pending-merchant-show-remaining.label.vahicles"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="businessTypeEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
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
															class="form-control button pull-right acc-next"
															value="<spring:message code="pending-merchant-show-remaining.label.continuebutton"/>"> <input type="button"
															class="form-control button pull-right marginL10"
															value="<spring:message code="pending-merchant-show-remaining.label.cancelbutton"/>" onclick="goToDashBoard()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<script src="../js/common-lib.js"></script>
</body>