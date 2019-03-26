<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Chatak Acquirer Admin</title>
<!-- Bootstrap -->
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
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Navigation Block Start -->
		<%-- <jsp:include page="header.jsp"></jsp:include> --%>
		<jsp:include page="navigation-panel.jsp"></jsp:include>
		<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><a
							href="javascript:showDropDownMenu('navListId4');"
							class="breadcrumb-text"><spring:message code="manage.label.manage"/></a></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><a href="#"
							class="breadcrumb-text" id="bc-entityType"
							onclick="updateHref();"><spring:message code="merchant.label.merchantaccount"/></a></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="common.label.edit"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first">
						<a href="merchant-account-search" id="entitySearch"><spring:message code="common.label.search"/></a>
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
								<div class="col-xs-12">
									<div>
										<span class="red-error">&nbsp;${error}</span> <span
											class="green-error">&nbsp;${sucess}</span>
									</div>
								</div>

								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="merchant-account-edit" modelAttribute="merchant"
									name="merchant" id="merchantAccountCreate">
								<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<!-- Bank Details Content Start -->
												<label
													style="font-weight: bold; font-size: 12px; margin-left: 25px;"><spring:message code="merchant.label.bankdetails"/></label>
												<section class="field-element-row bank-info-details-content"
													style="border: 1px solid;">
													<fieldset class="col-sm-12">
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-dynamic-MDR-create-page.label.bankname"/><span class="required-field">*</span></label>
															<form:input cssClass="form-control validate"
																path="bankAccountName" id="bankAccountName"
																maxlength="50"
																onblur="return clientValidation('bankAccountName', 'first_name_SplChar','bankAccountNameErrorDiv');" />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="bankAccountNameErrorDiv" class="red-error">&nbsp;</span>
															</div>
														</fieldset>

														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.bankrountingnumber"/><span
																class="required-field">*</span></label>
															<form:input cssClass="form-control validate"
																path="bankRoutingNumber"
																onkeypress="return amountValidate(this,event)"
																id="bankRoutingNumber" maxlength="9"
																onblur="return clientValidation('bankRoutingNumber', 'routing_number','bankRoutingNumberEr');" />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="bankRoutingNumberEr" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.bankaccountnumber"/><span
																class="required-field">*</span></label>
															<form:input cssClass="form-control validate"
																path="bankAccountNumber" id="bankAccountNumber"
																maxlength="50"
																onblur="return clientValidation('bankAccountNumber', 'account_numberBank','bankAccountNumberErrorDiv');" />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="bankAccountNumberErrorDiv" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.type"/><span class="required-field">*</span></label>
															<form:select cssClass="form-control validate"
																path="bankAccountType" id="bankAccountType"
																onblur="return clientValidation('bankAccountType', 'account_type','bankAccountTypeErrorDiv');">
																<form:option value=""><spring:message code="commission-program-create.label.select"/></form:option>
																<form:option value="S"><spring:message code="pending-merchant-show.label.savings"/></form:option>
																<form:option value="C"><spring:message code="pending-merchant-show.label.checking"/></form:option>
															</form:select>
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="bankAccountTypeErrorDiv" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.address1"/></label>
															<form:input cssClass="form-control validate"
																path="bankAddress1" id="bankAddress1" maxlength="50"
																onblur="return clientValidation('bankAddress1', 'bank_address2','bankAddress1ErrorDiv');" />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="bankAddress1ErrorDiv" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.address2"/></label>
															<form:input cssClass="form-control validate"
																path="bankAddress2" id="bankAddress2" maxlength="50"
																onblur="return clientValidation('bankAddress2', 'bank_address2','bankAddress2ErrorDiv');" />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="bankAddress2ErrorDiv" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.city"/></label>
															<form:input cssClass="form-control validate"
																path="bankCity" id="bankCity" maxlength="50"
																onblur="return clientValidation('bankCity', 'bank_address2','bankCityErrorDiv');" />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="bankCityErrorDiv" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.country"/><span class="required-field">*</span></label>
															<form:select cssClass="form-control validate"
																path="bankCountry" id="bankCountry"
																onblur="return clientValidation('bankCountry', 'country','bankCountryErrorDiv');"
																onchange="fetchMerchantState(this.value, 'bankState')">
																<form:option value=""><spring:message code="commission-program-create.label.select"/></form:option>
																<c:forEach items="${countryList}" var="country">
																	<form:option value="${country.label}">${country.label}</form:option>
																</c:forEach>
															</form:select>
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="bankCountryErrorDiv" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.state"/><span class="required-field">*</span></label>
															<form:select cssClass="form-control validate"
																path="bankState" id="bankState"
																onblur="return clientValidation('bankState', 'state','bankStateErrorDiv');">
																<form:option value=""><spring:message code="commission-program-create.label.select"/></form:option>
																<c:forEach items="${bankStateList}" var="item">
																	<form:option value="${item.value}">${item.label}</form:option>
																</c:forEach>
															</form:select>
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="bankStateErrorDiv" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																	code="common.label.zipcode" /><span
																class="required-field">*</span></label>
															<form:input cssClass="form-control validate"
																path="bankPin" id="bankPin" maxlength="10"
																onblur="return clientValidation('bankPin', 'zip_code','bankPinErrorDiv');" />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="bankPinErrorDiv" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																	code="merchant.label.nameonaccount" /><span
																class="required-field">*</span></label>
															<form:input cssClass="form-control validate"
																path="bankNameOnAccount" id="bankNameOnAccount"
																onblur="return clientValidation('bankNameOnAccount', 'first_name_SplChar','bankNameOnAccountErrorDiv');" />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="bankNameOnAccountErrorDiv" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
														<%-- <fieldset class="col-sm-3">
															<form:checkbox path="emailNotificationFlag"
																id="emailNotificationFlag" value="1"
																onclick="copyEMailId();" />
															<label data-toggle="tooltip" data-placement="top" title="">Email the Batch Report</label>
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="" class="red-error">&nbsp;</span>
															</div>
														</fieldset> --%>

														<fieldset class="col-sm-3" id="batchMailField">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="chatak-admin-myprofile.label.emailid"/><span class="required-field">*</span></label>
															<form:input cssClass="form-control" path="batchMailId"
																id="batchMailId" maxlength="50"
																onblur="return clientValidation('batchMailId', 'email','batchMailIdErrorDiv');" />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="batchMailIdErrorDiv" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
													</fieldset>
												</section>
												<!-- Bank Details Content End -->
												<!-- ATM Transactions Content Start -->
												<label
													style="font-weight: bold; font-size: 12px; margin: 20px 0px 0px 25px;"><spring:message
														code="merchant.label.configuration" /></label>
												<section class="field-element-row atm-transaction-content"
													style="border: 1px solid;">
													<fieldset class="col-sm-12">
														<fieldset class="col-sm-12">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																	code="merchant.label.autosettlementoptions" /><span
																class="required-field">*</span></label><br> <input
																type="radio" id="allowAutoSettlement"
																name="autoSettlement" value="1"
																onchange="return validateRadio()"><spring:message code="manage.option.radio.sub-merchant.yes"/> <input
																type="radio" id="noAutoSettlement" name="autoSettlement"
																value="0" onchange="return validateRadio()"><spring:message code="manage.option.radio.sub-merchant.no"/>
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="noAutoSettlementEr" class="red-error">&nbsp;</span>
															</div>

															<input type="hidden" name="autoSettlementStatus"
																id="autoSettlementStatus" />

														</fieldset>													
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																	code="merchant.label.feeprogram" /><span
																class="required-field">*</span></label>
															<form:select cssClass="form-control" path="feeProgram"
																id="feeProgram">
																<form:option value="">..:<spring:message
																		code="reports.option.select" />:..</form:option>
																<c:forEach items="${feeprogramnames}" var="feename">
																	<form:option value="${feename.label}">${feename.label}</form:option>
																</c:forEach>
															</form:select>
														</fieldset>
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="pending-merchant-show.label.processor"/><span class="required-field">*</span></label>
															<form:select cssClass="form-control" path="processor"
																id="processor">
																<form:option value="">..:<spring:message
																		code="reports.option.select" />:..</form:option>
																<c:forEach items="${processorNames}" var="processorName">
																	<form:option value="${processorName.value}">${processorName.label}</form:option>
																</c:forEach>
															</form:select>
														</fieldset>
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																	code="merchant.label.merchantcallbackURL" /></label>
															<form:input cssClass="form-control"
																path="merchantCallBackURL" id="merchantCallBackURL"
																maxlength="50" />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="merchantCallBackURLEr" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																	code="merchant.label.category" /><span
																class="required-field">*</span></label> <select
																class="form-control" id="tempCategory"
																disabled="disabled">
																<option value="">.:
																	<spring:message code="reports.option.select" />:.
																</option>
																<option value="primary"><spring:message
																		code="merchantaccount.label.primary" /></option>
																<option value="secondary"><spring:message
																		code="merchantaccount.label.secondary" /></option>
															</select>
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="categoryEr" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
														<%-- <div class="hideAutoSettlementOptionsField">
															<fieldset class="col-sm-3">
																<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.autotransferlimit"/></label>
																<form:input cssClass="form-control validate"
																	maxlength="10" path="autoTransferLimit"
																	id="autoTransferLimit"
																	onblur="validateAutoTransferLimit()" />
																<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																	<span id="autoTransferLimitEr" class="red-error">&nbsp;</span>
																</div>
															</fieldset>
															<fieldset class="col-sm-3">
																<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																		code="merchant.label.autopaymentmethod" /><span
																	class="required-field">*</span></label>
																<form:select cssClass="form-control validate"
																	path="autoPaymentMethod" id="autoPaymentMethod"
																	onblur="validateAutoPaymentMethod()">
																	<form:option value="">..:<spring:message
																			code="reports.option.select" />:..</form:option>
																	<form:option value="EFT">
																		<spring:message code="merchantaccount.label.EFT" />
																	</form:option>
																	<form:option value="Cheque">
																		<spring:message code="merchantaccount.label.cheque" />
																	</form:option>
																</form:select>
																<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																	<span id="autoPaymentMethodEr" class="red-error">&nbsp;</span>
																</div>
															</fieldset>
															<fieldset class="col-sm-3">
																<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.autotransfer"/></label>
																<form:select cssClass="form-control validate"
																	onchange="showAutoTransferDayFields();clientValidation('autoTransferDay', 'state','autoTransferDayEr');"
																	path="autoTransferDay" id="autoTransferDay">
																	<form:option value="">
																		<spring:message code="merchant.label.autotransfer" />
																	</form:option>
																	<form:option value="D">
																		<spring:message code="merchant.label.autotransfer" />
																	</form:option>
																	<form:option value="W">
																		<spring:message code="merchantaccount.label.weekly" />
																	</form:option>
																	<form:option value="M">
																		<spring:message code="merchantaccount.label.monthly" />
																	</form:option>
																</form:select>
																<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																	<span id="autoTransferDayEr" class="red-error">&nbsp;</span>
																</div>
															</fieldset>
															<fieldset class="col-sm-3" id="weeklySettlement"
																style="display: none;">
																<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																		code="merchant.label.selectdayoftheweek" /><span
																	class="required-field">*</span></label>
																<form:select cssClass="form-control validate"
																	onblur="return clientValidation('autoTransferWeeklyDay', 'state','autoTransferWeeklyDayEr');"
																	path="autoTransferWeeklyDay" id="autoTransferWeeklyDay">
																	<form:option value="">.:<spring:message
																			code="reports.option.select" />:.</form:option>
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
																<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																	<span id="autoTransferWeeklyDayEr" class="red-error">&nbsp;</span>
																</div>
															</fieldset>
															<fieldset class="col-sm-3" id="monthlySettlement"
																style="display: none;">
																<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																		code="merchant.label.selectdayofmonth" /><span
																	class="required-field">*</span></label>
																<form:select cssClass="form-control validate"
																	onblur="return clientValidation('autoTransferMonthlyDay', 'state','autoTransferMonthlyDayEr');"
																	path="autoTransferMonthlyDay"
																	id="autoTransferMonthlyDay">
																	<form:option value="">.:<spring:message
																			code="reports.option.select" />:.</form:option>
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
																</form:select>
																<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																	<span id="autoTransferMonthlyDayEr" class="red-error">&nbsp;</span>
																</div>
															</fieldset>
														</div> --%>
														<fieldset class="col-sm-3" id="vantivMerchantId">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																	code="merchant.label.vantivmerchantId" /><span
																class="required-field">*</span></label>
															<form:input cssClass="form-control" path="litleMID"
																id="litleMID" maxlength="50" />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="litleMIDEr" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
														<form:hidden path="merchantCode" id="merchantCode" />
														<form:hidden path="merchantType" id="merchantType" />
														<form:hidden path="accountId" id="accountId" />
														<form:hidden path="category" id="category" />
														<form:hidden path="accountStatus" id="accountStatus" />
													</fieldset>
												</section>
												<!-- ATM Transactions Content End -->
											</div>
										</div>
										<!--Panel Action Button Start -->
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit" class="form-control button pull-right"
													value="Update" onclick="return validateAccountDetails();">
												<input type="button" class="form-control button pull-right"
													value="Cancel" onclick="resetMerchantAccountSearch()">
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
			<footer class="footer">
				<jsp:include page="footer.jsp"></jsp:include>
			</footer>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/sortable.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/merchant.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/messages.js" type="text/javascript"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		$(document).ready(
				function() {

					$("#navListId6").addClass("active-background");

					loadRadio('${merchant.autoSettlementStatus}');
					emailField('${merchant.emailNotificationFlag}');

					var href;
					if ($('#merchantType').val() == 'Merchant') {
						$('#bc-entityType').text('Merchant Account');
						href = "merchant-account-search";
					} else {
						$('#bc-entityType').text('Sub-Merchant Account');
						href = "sub-merchant-account-search";
					}
					$('#entitySearch').attr('href', href);

					if ($('#litleMID').val().trim().length <= 0) {
						$('#vantivMerchantId').hide();
					}

					if ("${merchant.autoTransferDay}" == "M") {
						$('#monthlySettlement').show();
					}

					if ("${merchant.autoTransferDay}" == "W") {
						$('#weeklySettlement').show();
					}

					/* if($('#autoSettlementStatus').val() == '1') {
						$('#allowAutoSettlement').attr('checked',true);
					} else {
						$('#noAutoSettlement').attr('checked',true);
					} */

					 if($('#allowAutoSettlement').val() == '1') {
						$('#allowAutoSettlement').attr('checked',true);
					} else {
						$('#noAutoSettlement').attr('checked',true);
					} 

					$('#tempCategory').val($('#category').val());

					$('#feeProgram,#processor,#litleMID,#merchantCallBackURL')
							.attr('disabled', true);

				});

		function loadRadio(data) {
			if (data == '0') {
				$("#noAutoSettlement").prop("checked", true);
				$("#allowAutoSettlement").prop("checked", false);
				$(".hideAutoSettlementOptionsField").hide();
			} else if (data == '1') {
				$("#noAutoSettlement").prop("checked", false);
				$("#allowAutoSettlement").prop("checked", true);
				$(".hideAutoSettlementOptionsField").show();
			}
		}
		function emailField(data) {
			if (data == '1') {
				$('#batchMailField').show();
			} else {
				$('#batchMailField').hide();
			}
		}

		/* function validateAccountDetails() {
			var isValid = true;
			$('.validate').each(function() {
				if($(this).is(':visible')) {
					$(this).trigger('blur');
				}
			});
			
		// 			if(!$('#allowAutoSettlement').is(':checked') && !$('#noAutoSettlement').is(':checked')) {
		// 				setError(get('noAutoSettlement'), 'Please select one');
		// 			} else {
		// 				setError(get('noAutoSettlement'), '');
		// 			}
			
			$('.discriptionErrorMsg').each(function() {
				if($(this).find('span').text().trim().length > 0) {
					isValid = false;
					return false;
				}
			});
			return isValid;
		} */

		function updateHref() {
			if ($('#merchantType').val() == 'Merchant') {
				resetMerchantAccountSearch();
			} else {
				resetSubMerchantAccountSearch();
			}
		}
		
		$(document).ready(function() {
			/* Table Sorter includes Start*/
			$(function() {
				
					  // call the tablesorter plugin
					  $('#serviceResults').sortable({
						
						 divBeforeTable: '#divbeforeid',
						divAfterTable: '#divafterid',
						initialSort: false,
						locale: 'th',
						//negativeSort: [1, 2]
					});
			});
			});
	</script>
</body>
</html>
