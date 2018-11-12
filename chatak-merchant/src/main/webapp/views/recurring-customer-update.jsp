<!DOCTYPE html>
<%@page import="com.chatak.merchant.constants.JSPConstants"%>
<%@page import="com.chatak.pg.util.Constants"%>
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
<title>Chatak Acquirer Merchant</title>
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css" />
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<jsp:include page="header.jsp"></jsp:include>
			<!-- Header Block End -->
			<!--Navigation Block Start -->
			<nav class="col-sm-12 nav-bar" id="main-navigation">
				<jsp:include page="navigation-panel.jsp"></jsp:include> 
			</nav>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="recurring-search.label.recurring"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="sub-merchant-update.label.edit"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first">
						<a href="recurring-search"><spring:message code="recurring-search.label.search"/></a>
					</div>
					<div class="tab-header-container active-background">
						<a href="#"><spring:message code="sub-merchant-update.label.edit"/></a>
					</div>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder padding0">
						<!-- Page Menu Start -->
						<!--Success and Failure Message Start-->
						<div class="col-xs-12">
							<div class="discriptionErrorMsg"
								style="text-align: center; font-size: 10pt" id="errorDescDiv">
								<span class="red-error">&nbsp;${error }</span> <span
									class="green-error">&nbsp;${success}</span>
							</div>
						</div>
						<!--Success and Failure Message End-->
						<div class="col-sm-12 padding0">
							<div class="sub-nav">
								<ul>
									<li class="account-details-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="merchant-circle-tab active-circle"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.primarycontactinfo"/></label>
										<div class="arrow-down merchant-arrow"></div>
									</li>
									<li class="atm-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="bank-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.paymentinformation"/></label>
										<div class="arrow-down bank-arrow"></div>
									</li>
									<li class="pos-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="final-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.contracts"/></label>
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
									<div class="discriptionErrorMsg col-xs-12">
										<span class="red-error">&nbsp; ${error }</span> <span
											class="green-error">&nbsp;${success }</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="updateRecurringCustomer"
									modelAttribute="recurringCustomer" name="recurringCustomer"
									method="post">
									<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12 paddingT20">
										<div class="row">
											<!-- Primary contact Info Content Start -->
											<section class="field-element-row account-details-content">
												<fieldset class="col-sm-12">
												<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.customerid"/><span class="required-field">*</span></label>
														<form:input path="customerId" cssClass="form-control"
															id="customerId" maxlength="250" disabled="true"
															onblur="this.value=this.value.trim();return clientValidation('customerId', 'customer_Id','customerId_ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error" id="customerId_ErrorDiv">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.firstname"/><span class="required-field">*</span></label>
														<form:input path="firstName" cssClass="form-control"
															id="firstName" maxlength="250"
															onblur="this.value=this.value.trim();return clientValidation('firstName', 'first_name','firstName_ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error" id="firstName_ErrorDiv">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.lastname"/><span class="required-field">*</span></label>
														<form:input id="lastName" path="lastName"
															cssClass="form-control" maxlength="250"
															onblur="this.value=this.value.trim();return clientValidation('lastName', 'last_name','lastName_ErrorDiv');"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="lastName_ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.company"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="businessName"
															id="businessName" maxlength="50"
															onblur="this.value=this.value.trim();validateBusinessName()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="businessNameEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.title"/></label>
														<form:input id="title" path="title" maxlength="50"
															cssClass="form-control" onblur="this.value=this.value.trim();"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="title_ErrorDiv" >&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.department"/></label>
														<form:input id="department" path="department" maxlength="50"
															cssClass="form-control"
															onblur="this.value=this.value.trim();return clientValidation('department', 'department','department_ErrorDiv');" />
													    <div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.emailid"/><span class="required-field">*</span></label>
														<form:input id="emailId" path="emailId" maxlength="100"
															cssClass="form-control"
															onblur="this.value=this.value.trim();return validateUpdateEmailId();" onkeydown="validateSpace(this);" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error" id="emailIdEr">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.mobilephone"/><span class="required-field">*</span></label>
														<form:input id="mobileNumber" path="mobileNumber" maxlength="10"
															cssClass="form-control"
															onblur="this.value=this.value.trim();return validateMobilePhone(mobileNumber);" onkeydown="validateSpace(this);" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="mobileNumberEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.daytimephone"/></label>
														<form:input id="daytimePhone" path="daytimePhone"
															maxlength="10" cssClass="form-control"
															onblur="this.value=this.value.trim();return validateDayTimePhone('daytimePhone');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="daytimePhoneEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.eveningphone"/></label>
														<form:input id="eveningPhone" path="eveningPhone"
															maxlength="10" cssClass="form-control"
															onblur="this.value=this.value.trim();return validateEveningTimePhone('eveningPhone');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="eveningPhoneEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="sub-merchant-create.label.fax"/></label>
														<form:input cssClass="form-control" path="fax" id="fax"
															onkeypress="return amountValidate(this,event)"
															maxlength="13" onblur="this.value=this.value.trim();"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="faxEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3"> 
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="dash-board.label.status"/><span class="required-field">*</span></label>
														<form:select id="status" path="status" cssClass="form-control" onblur="return clientValidation('status', 'status','status_ErrorDiv');">
															<form:option value="">..:<spring:message code="sub-merchant-create.label.select"/>:..</form:option>
															<form:option value="Active"><spring:message code="search-sub-merchant.lable.active"/></form:option>
															<form:option value="Inactive"><spring:message code="recurring-search.label.inactive"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="status_ErrorDiv" class="red-error">&nbsp;</span>
														</div> 
													</fieldset>
													<br/>
													<fieldset class="col-sm-12">
                                                        <span><strong><spring:message code="recurring-search.label.mailingaddress"/></strong></span>
                                                      </fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="sub-merchant-create.label.address1"/><span class="required-field">*</span></label>
														<form:input id="address1" path="address1" maxlength="250"
															cssClass="form-control"
															onblur="this.value=this.value.trim();return clientValidation('address1', 'address','address1_ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="address1_ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="sub-merchant-create.label.address2"/><span class="required-field">*</span></label>
														<form:input id="address2" path="address2" cssClass="form-control" maxlength="250"
														onblur="this.value=this.value.trim();return clientValidation('address2', 'address','address2_ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="address2_ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.address3"/></label>
														<form:input path="address3" cssClass="form-control" maxlength="250" onblur="this.value=this.value.trim();"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.area"/><span class="required-field">*</span></label>
														<form:input id="area" path="area" cssClass="form-control" maxlength="50"
															onblur="this.value=this.value.trim();return clientValidation('area', 'area','area_ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="area_ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.city"/><span class="required-field">*</span></label>
														<form:input id="city" path="city" cssClass="form-control" maxlength="20"
															onblur="this.value=this.value.trim();return clientValidation('city', 'pcity','city_ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="city_ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="search-sub-merchant.label.country"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="country"
															id="country"
															onblur="return clientValidation('country', 'country','country_ErrorDiv');">
															<form:option value="">..:<spring:message code="sub-merchant-create.label.select"/>:..</form:option>
															<c:forEach items="${countryList}" var="country">
																<form:option value="${country.label}">${country.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="countryEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="sub-merchant-create.label.state"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="state"
															id="state"
															onblur="return clientValidation('state', 'state','state_ErrorDiv');" onkeydown="validateSpace(this)">
															<form:option value="">..:<spring:message code="sub-merchant-create.label.select"/>:..</form:option>
															<c:forEach items="${stateList}" var="item">
																<form:option value="${item.label}">${item.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="stateEr" class="red-error">&nbsp;</span>
														</div>																															
												</fieldset>		
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="sub-merchant-create.label.zipcode"/><span class="required-field">*</span></label>
														<form:input id="zipCode" path="zipCode" cssClass="form-control"
								                        	maxlength="7" onkeypress ="generalZipCode()"
															onblur="this.value=this.value.trim();return zipCodeNotEmpty(id)" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="zipCodeEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												<form:hidden path="recurringCustInfoId" />
												<form:hidden path="customerId" />
												<fieldset class="col-sm-12"></fieldset>
												
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="submit"
															class="form-control button pull-right"
															value="<spring:message code="recurring-search.label.updatecontinue"/>"
															onclick="return validateRecurringCustmerUpdate()"> <input
															type="button" class="form-control button pull-right"
															value="<spring:message code="merchant-forgot-password.label.cancel"/>" onclick="return cancel()">
													</fieldset>
												</div>
												</fieldset>
												<!--Panel Action Button End -->
											</section>
											<!-- Primary contact Info Content End -->
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

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/jquery.min.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/recurring.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/jquery.maskedinput.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script> <script src="../js/utils.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	 <script src="../js/messages.js"></script>
	<script>		
		/* Common Navigation Include Start */
		/* Common Navigation Include End */
		/* DatePicker Javascript Strat*/
		$( document ).ready(function() {
			$(".focus-field").click(function(){
				$(this).children('.effectiveDate').focus();
			});
			highlightMainContent('navListId5');
			$('.effectiveDate').datetimepicker({
				timepicker:false,
				format:'d/m/Y',
				formatDate:'Y/m/d',
			});
			$('input:checkbox').removeAttr('checked');
		});
		/* DatePicker Javascript End*/
		/* Sub Tab Functionality Start */
		$("#mobileNumber").mask("<%=Constants.PHONE_NUMBER_FORMAT%>");
		$("#daytimePhone").mask("<%=Constants.PHONE_NUMBER_FORMAT%>");
		$("#eveningPhone").mask("<%=Constants.PHONE_NUMBER_FORMAT%>");
		$(".free-transactions-content, .atm-transaction-content, .pos-transaction-content").hide();
		$(".account-details-content").show();
		$(".merchant-arrow").show();
		$(".bank-arrow, .final-arrow").hide()
		/* Account Charge Radio Functionality End */
		/* Payment Information Table Functionality Start */
		$(".account-details-content").show();
		$(".payment-info-table-btn").click(function(){
			$(".account-details-content").show();
		});
		/* Payment Information Table Functionality End */
		/* Contract Information Table Functionality Start */
		/* Contract Information Table Functionality End */
	</script>
	<script src="../js/backbutton.js"></script>
</body>
</html>