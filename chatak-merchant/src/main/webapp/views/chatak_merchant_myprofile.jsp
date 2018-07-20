<!DOCTYPE html>


<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">

	
	<!-- <script src="../js/common-lib.min.js"></script> -->

	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<jsp:include page="header.jsp"></jsp:include>
			<!--Header Block End -->
			<!--Navigation Block Start -->
			<nav class="col-sm-12 nav-bar" id="main-navigation">
				<%@include file="navigation-panel.jsp"%>
			</nav>
			<!--Navigation Block End -->
			<!--Article Block Start-->
			<article>
				<div class="">
					<div class="col-xs-12 content-wrapper">


						<form:form commandName="merchantProfile"
							action="chatak_merchant_myprofile_edit" method="post">
							<!--  -->
							<!-- modelAttribute="userProfileRequest" -->

							<!-- Breadcrumb start -->
							<div class="breadCrumb">
								<span class="breadcrumb-text"><spring:message code="myprofile.label.myprofile"/></span>
							</div>
							<!-- Tab Buttons End -->
							<!-- Content Block Start -->

							<div class="main-content-holder">
								<div class="row">

									<!--Success and Failure Message End-->
									<!-- Page Form Start -->
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<div class="col-sm-12">
													<!--Success and Failure Message Start-->
													<div class="col-xs-12">
														<div class="discriptionMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error">${error}</span> <span
																class="green-error">${sucess}</span>
														</div>
													</div>

												</div>
												<div class="breadCrumb">
													<span class="breadcrumb-text"><spring:message code="myprofile.label.companydetails"/></span>
												</div>

												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="myprofile.label.companyname"/><span class="required-field">*</span></label>
													<form:input cssClass="form-control" path="businessName"
														id="businessName" maxlength="50"
														onblur="this.value=this.value.trim();validateBusinessName()" disabled="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="businessNameEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="myprofile.label.merchantcode"/><span class="required-field">*</span></label>
													<form:input cssClass="form-control" path="merchantCode"
														id="merchantCode" maxlength="50"
														onblur="this.value=this.value.trim();validateMerchantCode()" readonly="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="merchantCodeEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="myprofile.label.firstname"/><span class="required-field">*</span></label>
													<form:input cssClass="form-control" path="firstName"
														id="firstName" maxlength="50" onblur="this.value=this.value.trim();validateFirstName()"
														disabled="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="firstNameEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="myprofile.label.lastname"/><span class="required-field">*</span></label>
													<form:input cssClass="form-control" path="lastName"
														id="lastName" maxlength="50" onblur="this.value=this.value.trim();validateLastName()"
														disabled="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="lastNameEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="myprofile.label.phone"/><span class="required-field">*</span></label>
													<form:input cssClass="form-control" path="phone" id="phone"
														maxlength="13" onblur="this.value=this.value.trim();validatePhone()" disabled="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="phoneEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
											<%-- 	<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title="">Fax<span class="required-field">*</span></label>
													<form:input cssClass="form-control" path="fax" id="fax"
														maxlength="13" onblur="validateFax()" disabled="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="faxEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset> --%>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="myprofile.label.emailID"/><span class="required-field">*</span></label>
													<form:input cssClass="form-control" path="emailId"
														id="emailId" readonly="true" disabled="true" onblur="this.value=this.value.trim();"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="emailIdEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<div class="breadCrumb">
													<span class="breadcrumb-text"><spring:message code="myprofile.label.physicaladdress"/></span>
												</div>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="myprofile.label.address1"/><span class="required-field">*</span></label>
													<form:input cssClass="form-control" path="address1"
														id="address1" maxlength="50" onblur="this.value=this.value.trim();validateAddress1()"
														disabled="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="address1Er" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="myprofile.label.address2"/></label>
													<form:input cssClass="form-control" path="address2"
														id="address2" maxlength="50" onblur="this.value=this.value.trim();validateAddress2()"
														disabled="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="address2Er" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="myprofile.label.city"/><span class="required-field">*</span></label>
													<form:input cssClass="form-control" path="city" id="city"
														maxlength="50" onblur="this.value=this.value.trim();validateCity()" disabled="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="cityEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="myprofile.label.country"/><span class="required-field">*</span></label>
													<form:select cssClass="form-control" path="country"
														id="country" onblur="validateCountry()"
														onchange="fetchMerchantState(this.value, 'state')" disabled="true">
														<form:option value=""><spring:message code="common.label.search"/></form:option>
														<c:forEach items="${countryList}" var="country">
															<form:option value="${country.label}">${country.label}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="countryEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="myprofile.label.state"/><span class="required-field">*</span></label>
													<form:select cssClass="form-control" path="state"
														id="state" onblur="validateState()" disabled="true">
														<form:option value=""><spring:message code="common.label.search"/></form:option>
														<c:forEach items="${stateList}" var="item">
															<form:option value="${item.label}">${item.label}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="stateEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>

												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="myprofile.label.zipcode"/><span class="required-field">*</span></label>
													<form:input cssClass="form-control" path="pin" id="pin"
														maxlength="10" onblur="this.value=this.value.trim();validatePin()" disabled="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="pinEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>


											</div>
											<div class="col-sm-12">
												<div class="row">
													<div class="field-element-row">
														<div class="breadCrumb">
															<span class="breadcrumb-text"><spring:message code="myprofile.label.mailingaddress"/></span>
														</div>
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="myprofile.label.address1"/><!-- <span class="required-field">*</span> --></label>
															<form:input cssClass="form-control"
																path="mailingAddress1" id="mailingAddress1"
																maxlength="50" onblur="this.value=this.value.trim();<%-- validateMailingAddress1() --%>"
																disabled="true" />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="mailingAddress1Er" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="myprofile.label.address2"/></label>
															<form:input cssClass="form-control"
																path="mailingAddress2" id="mailingAddress2"
																maxlength="50" onblur="this.value=this.value.trim();<%-- validateMailingAddress2() --%>"
																disabled="true" />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="mailingAddress2Er" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="myprofile.label.city"/><!-- <span class="required-field">*</span> --></label>
															<form:input cssClass="form-control" path="mailingCity"
																id="mailingCity" maxlength="50"
																onblur="this.value=this.value.trim();<%-- validateMailingCity() --%>" disabled="true" />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="mailingCityEr" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="myprofile.label.country"/><!-- <span class="required-field">*</span> --></label>
															<form:select cssClass="form-control"
																path="mailingCountry" id="mailingCountry"
																onblur="<%-- validateMailingCountry() --%>"
																onchange="fetchMerchantState(this.value, 'mailingState')"
																disabled="true">
																<form:option value=""><spring:message code="common.label.search"/></form:option>
																<c:forEach items="${countryList}" var="country">
																	<form:option value="${country.label}">${country.label}</form:option>
																</c:forEach>
															</form:select>
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="mailingCountryEr" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="myprofile.label.state"/><!--< span class="required-field">*</span> --></label>
															<form:select cssClass="form-control" path="mailingState"
																id="mailingState" onblur="<%-- validateMailingState() --%>"
																disabled="true">
																<form:option value=""><spring:message code="common.label.search"/></form:option>
																<c:forEach items="${stateList}" var="item">
																	<form:option value="${item.label}">${item.label}</form:option>
																</c:forEach>
															</form:select>
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="mailingStateEr" class="red-error">&nbsp;</span>
															</div>
														</fieldset>

														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="myprofile.label.zipcode"/><!-- <span class="required-field">*</span> --></label>
															<form:input cssClass="form-control" path="mailingPin"
																id="mailingPin" maxlength="10"
																onblur="this.value=this.value.trim();<%-- validateMailingPin() --%>" disabled="true" />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="mailingPinEr" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
													</div>
                                                <div class="col-sm-12">
												<div class="row">
													<div class="field-element-row">
														<div class="breadCrumb">
															<span class="breadcrumb-text"><spring:message code="myprofile.label.userdetails"/></span>
														</div>
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="myprofile.label.username"/><span class="required-field">*</span></label>
															<form:input cssClass="form-control" path="userName"
																id="userName" maxlength="50" readonly="true"
																disabled="true" onblur="this.value=this.value.trim();"/>
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="userNameEr" class="red-error">&nbsp;</span> <span
																	id="userNamegreenEr" class="green-error">&nbsp;</span>
															</div>
														</fieldset>
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="myprofile.label.businessURL"/><span class="required-field">*</span></label>
															<form:input cssClass="form-control" path="businessURL"
																maxlength="100" id="businessURL" onclick="validateURL()"
																disabled="true" onblur="this.value=this.value.trim();"/>
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="businessURLEr" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
													<%-- 	<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title="">Federal Tax ID<span class="required-field">*</span></label>
															<form:input cssClass="form-control" path="federalTaxId"
																id="federalTaxId" maxlength="50"
																onblur="validatefederalTaxId()" disabled="true" />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="federalTaxIdEr" class="red-error">&nbsp;</span>
															</div>
														</fieldset> --%>
														<%-- <fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title="">State Tax ID<span class="required-field">*</span></label>
															<form:input cssClass="form-control" path="stateTaxId"
																id="stateTaxId" maxlength="50"
																onblur="validatestateTaxId()" disabled="true" />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="stateTaxIdEr" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title="">Sales Tax ID<span class="required-field">*</span></label>
															<form:input cssClass="form-control" path="salesTaxId"
																id="salesTaxId" maxlength="50"
																onblur="validatesalesTaxId()" disabled="true" />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="salesTaxIdEr" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""> Business Start Date<span
																class="required-field">*</span></label>
															<div class="input-group focus-field">
																<form:input cssClass="form-control effectiveDate"
																	path="businessStartDate" id="businessStartDate"
																	onblur="validateBusinessStartDate()" disabled="true" />
																<span class="input-group-addon"><span
																	class="glyphicon glyphicon-calendar"></span></span>
															</div>
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="businessStartDateEr" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title="">Estimated Yearly Sales<span
																class="required-field">*</span></label>
															<form:input cssClass="form-control"
																path="estimatedYearlySale" id="estimatedYearlySale"
																onblur="validateEstimatedYearlySale()" disabled="true" />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="estimatedYearlySaleEr" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title="">No. OF Employees<span
																class="required-field">*</span></label>
															<form:input cssClass="form-control" path="noOfEmployee"
																id="noOfEmployee" maxlength="50"
																onblur="validateNoOfEmployees()" disabled="true" />

															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="noOfEmployeeEr" class="red-error">&nbsp;</span>
															</div>
														</fieldset> --%>
                                                   </div>
                                                   </div>
													</div>
												</div>
												<div class="field-element-row">

													<fieldset class="col-sm-3">
														<form:hidden path="feeProgram" />
													</fieldset>
													<fieldset class="col-sm-3">
														<form:hidden path="processor" />
													</fieldset>
													<fieldset class="col-sm-3">
														<form:hidden path="merchantCallBackURL" />
													</fieldset>
													<fieldset class="col-sm-3">
														<form:hidden path="category" />
													</fieldset>
													<fieldset class="col-sm-3">
														<form:hidden path="autoTransferLimit" />
													</fieldset>

													<fieldset class="col-sm-3">
														<form:hidden path="autoPaymentMethod" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoPaymentMethodEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<form:hidden path="autoTransferDay" />
													</fieldset>

													<fieldset class="col-sm-12">
														<form:hidden path="refunds" />
														<form:hidden path="tipAmount" />
														<form:hidden path="taxAmount" />
														<form:hidden path="shippingAmount" />
														<%-- <form:hidden path="allowRepeatSale" />
														<form:hidden path="showRecurringBilling" /> --%>
														<form:hidden path="autoSettlement" />
														<form:hidden path="ownership" />
														<form:hidden path="status" />
														<form:hidden path="appMode" />
														<form:hidden path="merchantUserId" />
													</fieldset>
												</div>
											</div>
										</div>

										<!-- Page Form End -->

									</div>
									<div class="col-sm-12 button-content" id="submitUpdate">
										<fieldset class="col-sm-7 pull-right">
											<input type="submit"
												class="form-control button pull-right pos-next"
												value='<spring:message code="common.label.confirm"/>' onclick="return validateProfileSubmit();">
											<div>
												<input type="button" class="form-control button pull-right"
													value='<spring:message code="common.label.cancel"/>' onclick="return backTodashBoard();">
											</div>
										</fieldset>
									</div>
									<!--Panel Action Button End -->
									<div class="col-sm-12 button-content" id="myEdit">
										<fieldset class="col-sm-7 pull-right">
											<input type="button" value='<spring:message code="common.label.edit"/>'
												class="form-control button pull-right pos-next">
											<input type="button" class="form-control button pull-right"
												value='<spring:message code="common.label.back"/>' onclick="return backTodashBoard();">
										</fieldset>
										<fieldset>
											<div class="col-sm-7 pull-right"></div>
										</fieldset>
									</div>
								</div>
							</div>
							<input type="hidden" name="CSRFToken" value="${tokenval}">
						</form:form>
					</div>
				</div>
			</article>
			<!--Article Block End-->
			<jsp:include page="footer.jsp"></jsp:include>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<!-- 	<script src="../js/common-lib.min.js"></script> -->
	<script src="../js/backbutton.js" type="text/javascript"></script>
	<script src="../js/changePassword.js" type="text/javascript"></script>
	<script src="../js/jquery.min.js" type="text/javascript"></script>
	<script src="../js/bootstrap.min.js" type="text/javascript"></script> <script src="../js/utils.js"></script>
	<script src="../js/common-lib.js" type="text/javascript"></script>
	<script src="../js/admin-user.js" type="text/javascript"></script>
	<script src="../js/merchant.js" type="text/javascript"></script>
	<script src="../js/merchant-profile.js" type="text/javascript"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/messages.js"></script>
	<!-- <script src="../js/merchant-profile.js"></script> -->

	<script>
		/* Common Navigation Include Start */

		$(document).ready(function() {
			/* $("#main-navigation").load("main-navigation.html");
			$('#submitUpdate').hide(); */
		}); 
		$('#submitUpdate').hide();
		$("#navListId8").addClass("active-background");
		/* function highlightMainContent() {
			$("#navListId8").addClass("active-background");
		} */
		/* Common Navigation Include End */
		/* Approval drop down functionality start */
		$("#approvalid").change(function() {
			if (this.value == 2) {
				$("#approverDropDown").show();
			} else {
				$("#approverDropDown").hide();
			}
		});
		$("#approverDropDown").hide();
		$("#myEdit").click(function() {
			$("input").removeAttr('disabled');
			$('select').removeAttr('disabled');
			$('#myEdit').hide();
			$('#submitUpdate').show();
		});
		/* Approval drop down functionality End */
	</script>
</body>
</html>