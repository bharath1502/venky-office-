<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.Calendar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
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
	<script type="text/javascript">
	 var testData = 'USPROG, COP, USD';
	</script>
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<!--Header Block End -->
			<!--Navigation Block Start -->
			<!-- <nav class="col-sm-12 nav-bar" id="main-navigation"> -->
			<%-- <jsp:include page="header.jsp"></jsp:include> --%>
			<%@include file="navigation-panel.jsp"%>
			<!-- </nav> -->
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="manage.label.manage"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="merchant.label.merchant"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="common.label.create"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<!-- <div class="tab-header-container-first">
						<a href="merchant-search">Search</a>
					</div> -->
					<c:if test="${fn:contains(existingFeatures,merchantView) || fn:contains(existingFeatures,merchantEdit) || fn:contains(existingFeatures,merchantDelete)||fn:contains(existingFeatures,merchantCreate)}">
					<div class="tab-header-container-first">
						<a href="merchant-search-page"><spring:message code="common.label.search"/></a>
					</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,merchantCreate)}">
					<div class="tab-header-container active-background">
						<a href="#"><spring:message code="common.label.create"/></a>
					</div>
					</c:if>
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
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.bankinfo"/></label>
										<div class="arrow-down bank-info-arrow"></div>
									</li>
									<li class="atm-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="bank-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.configuration"/></label>
										<div class="arrow-down configuration-arrow"></div>
									</li>
									<li class="pos-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="final-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.confirmation"/></label>
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
								<form:form action="createMerchant" modelAttribute="merchant" name="merchant">
								<input type="hidden" id="currencyCode" name="currencyCode">
								<input type="hidden" id="cardProgramIds" name="cardProgramIds">
								<input type="hidden" id="entitiesId" name="entitiesId">
								<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12 paddingT20">
										<div class="row">
											<!-- Account Details Content Start -->
											<jsp:include page="merchant-create-remaining.jsp"></jsp:include>
											<!-- Account Details Content End -->
											<!-- Bank Details Content Start -->
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
															path="bankRoutingNumber"
															onkeypress="return amountValidate(this,event)"
															id="bankRoutingNumber" maxlength="9"
															onblur="this.value=this.value.trim();return clientValidation('bankRoutingNumber', 'routing_number','bankRoutingNumberEr'),validateRoutingNumber();" />
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
															maxlength="50"
															onblur="return clientValidation('bankAccountNumber', 'account_numberBank','bankAccountNumberErrorDiv');" onkeypress="return numbersonly(this, event)" />
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
															onblur="this.value=this.value.trim();return clientValidation('bankAddress1', 'bank_address2','bankAddress1ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAddress1ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.address2"/></label>
														<form:input cssClass="form-control" path="bankAddress2"
															id="bankAddress2" maxlength="50"
															onblur="this.value=this.value.trim();return clientValidation('bankAddress2', 'bank_address2','bankAddress2ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankAddress2ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.city"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" path="bankCity"
															id="bankCity" maxlength="50"
															onblur="this.value=this.value.trim();return clientValidation('bankCity', 'bank_city_name','bankCityErrorDiv');" />
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
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankStateErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.zipcode"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="bankPin"
															onkeypress="generalZipCode()" id="bankPin" maxlength="7"
															onblur="this.value=this.value.trim();return zipCodeNotEmpty(id)" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankPinEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.nameonaccount"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control"
															path="bankNameOnAccount" id="bankNameOnAccount"
															onblur="this.value=this.value.trim();return clientValidation('bankNameOnAccount', 'general_name','bankNameOnAccountErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="bankNameOnAccountErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label><spring:message code="merchant.label.currency"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="currencyId"
															id="currencyId" 
															onchange="fetchCurrency(this.value,'bankId'),fetchEntityNameByPmIso(this.value, 'associatedTo')"
															onblur="clientValidation('currencyId', 'currencyValue','currencyEr')">
															<form:option value="">..:<spring:message code="reports.option.select"/>:..</form:option>
															<c:forEach items="${currencyCodeList}" var="currencyValue">
																<form:option value="${currencyValue.value}">${currencyValue.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg">
															<span id="currencyEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="merchant.label.iso" /><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control" path="associatedTo"
															id="associatedTo" onblur="clientValidation('associatedTo', 'associated_To','associatedToEr')">
															<form:option value="">
																<spring:message code="reports.option.select" />
															</form:option>
														</form:select>
														<div class="discriptionErrorMsg">
															<span id="associatedToEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															class="form-control button pull-right bank-next"
															value='<spring:message code="common.label.continue"/>' onclick="return zipCodeNotEmpty('bankPin')"
															> <input type="button"
															value='<spring:message code="common.label.previous"/>' class="form-control button pull-right marginL10 bank-prev"
															> <input
															type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.cancel"/>' onclick="openCancelConfirmationPopup()"><input type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.reset"/>' onclick="resetBankInfo()"> 
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<!-- Bank Details Content End -->
										
											<jsp:include page="merchant-create-confirmation.jsp"></jsp:include>
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
							onclick="cancelCreateMerchant()">
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
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/chatak-ajax.js"></script>
	<script src="../js/messages.js"></script>
	<script type="text/javascript" src="../js/merchant.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script src="../js/utils.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script src="../js/multiselect.js"></script>
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
		/* function highlightMainContent() {
			$("#navListId2").addClass("active-background");
		} */
		/* Common Navigation Include End */
		/* DatePicker Javascript Strat*/
		$(document).ready(
				function() {
					$("#navListId6").addClass("active-background");
					$(window).keydown(function(event) {
						if (event.keyCode == 13) {
							event.preventDefault();
							return false;
						}
					});

					$('#my_popup1').popup({
						blur : false
					});

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

		$(".focus-field").click(function() {
			$(this).children('.effectiveDate').focus();
		});

		/* DatePicker Javascript End*/
		$(
				".bank-info-details-content, .legal-details-content, .legal-details-rep-content, .free-transactions-content, .pm-iso-carprogram-content , .atm-transaction-content, .pos-transaction-content")
				.hide();
		$(".account-details-content").show();
		$(".merchant-arrow").show();
		$(
				".contact-arrow, .bank-info-arrow, .legal-arrow, .legal-rep-arrow, .bank-legal-arrow, .bank-arrow, .configuration-arrow, .final-arrow,.pic-arrow")
				.hide();
		$(".account-details-list, .bank-prev")
				.click(
						function() {
							$(".merchant-circle-tab").addClass("active-circle");
							$(
									".bank-info-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .contact-circle-tab, .bank-circle-tab, .final-circle-tab,.pic-circle-tab")
									.removeClass("active-circle");
							$(".merchant-arrow").show();
							$(
									".bank-info-arrow, .legal-arrow, .legal-rep-arrow, .contact-arrow, .bank-arrow, .final-arrow,.pic-arrow")
									.hide();
							$(".account-details-content").show();
							$(
									".atm-transaction-content,.bank-info-details-content, .legal-details-content, .legal-details-rep-content, .pos-transaction-content, .free-transactions-content,.pm-iso-carprogram-content")
									.hide();
						});

		$(".bank-list, .acc-next, .pic-prev")
				.click(
						function() {
							loadMsgTitleText();
							if (!validateCreateMerchantStep1()
									| resetBankInfoErrorMsg()) {
								return false;
							}
							$(".bank-info-circle-tab")
									.addClass("active-circle");
							$(
									".merchant-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .contact-circle-tab, .bank-circle-tab, .final-circle-tab,.pic-circle-tab")
									.removeClass("active-circle");
							$(".bank-info-arrow").show();
							$(
									".merchant-arrow, .legal-arrow, .legal-rep-arrow, .contact-arrow, .configuration-arrow, .bank-arrow, .configuration-arrow, .final-arrow,.pic-arrow")
									.hide();
							$(".bank-info-details-content").show();
							$(
									".account-details-content, .legal-details-content, .legal-details-rep-content, .atm-transaction-content, .pos-transaction-content, .free-transactions-content")
									.hide();
						});
		$(".atm-transactions-list, .bank-next, .pos-prev")
				.click(
						function() {
							if (!validateCreateMerchantStep2()
									| !validateCreateMerchantStep1()
									| resetConfigurationsInfoErrorMsg()) {
								return false;
							}
							$(".bank-circle-tab").addClass("active-circle");
							$(
									".merchant-circle-tab,.bank-info-circle-tab, .contact-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .final-circle-tab,.pic-circle-tab")
									.removeClass("active-circle");
							$(".configuration-arrow").show();
							$(
									".contact-arrow, .merchant-arrow, .legal-arrow, .legal-rep-arrow, .bank-info-arrow, .final-arrow,.pic-arrow")
									.hide()
							$(".atm-transaction-content").show();
							$(
									".free-transactions-content, .bank-info-details-content, .legal-details-content, .legal-details-rep-content, .pos-transaction-content, .account-details-content,.pm-iso-carprogram-content")
									.hide();
						});
		$(".pos-transactions-list, .atm-next")
				.click(
						function() {
							if (!validateCreateMerchantStep5()
									| !validateCreateMerchantStep1()
									| !validateCreateMerchantStep2()) {
								return false
							}
							$(".final-circle-tab").addClass("active-circle");
							$(
									".merchant-circle-tab, .bank-info-circle-tab, .contact-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .bank-circle-tab,.pic-circle-tab")
									.removeClass("active-circle");
							$(".final-arrow").show();
							$(
									".contact-arrow, .bank-arrow,.configuration-arrow, .bank-info-arrow, .legal-arrow, .legal-rep-arrow, .merchant-arrow,.pic-arrow")
									.hide()
							$(".pos-transaction-content").show();
							$(
									".free-transactions-content, .bank-info-details-content, .legal-details-content, .legal-details-rep-content, .atm-transaction-content, .account-details-content,.pm-iso-carprogram-content")
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
									| !validateMerchantcreates()) {
								return false
							}
							$(".final-circle-tab").addClass("active-circle");
							$(
									".merchant-circle-tab, .bank-info-circle-tab, .contact-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .bank-circle-tab")
									.removeClass("active-circle");
							$(".final-arrow").show();
							$(
									".contact-arrow, .bank-arrow,.configuration-arrow, .bank-info-arrow, .legal-arrow, .legal-rep-arrow, .merchant-arrow,.pic-arrow")
									.hide()
							$(".pos-transaction-content").show();
							$(
									".free-transactions-content, .bank-info-details-content, .legal-details-content, .legal-details-rep-content, .atm-transaction-content, .account-details-content,.pm-iso-carprogram-content")
									.hide();
						});

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
			if ($("#currencyCodes").children().length != 0) {
				document.getElementById("mccCodeErrorMsg").innerHTML = '';
			}
			SelectSort(right);
		}

		$(".hide-block").hide();
		//$(".hide-localCurrency").hide();
		$("#dcc_enable").click(function() {
			if ($(this).is(":checked")) {
				$(".hide-block").show();
				//$(".hide-localCurrency").show();
			} else {
				$(".hide-block").hide();
				//$(".hide-localCurrency").hide();
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
		/* Select Services moving form Left to Right and Right to Left functionality Start */
		var entitiesId = [];
		var entityNameArr = [];
        function SelectMoveRows(left, right, action) {
        	var tempProgramManagerIds = [];
        	var tempEntityName = [];
			var j=0;
            var SelID = '';
            var SelText = '';
            // Move rows from left to right from bottom to top
            if(action == 'ADD'){
				for (i = left.options.length - 1; i >= 0; i--) {
					if (left.options[i].selected == true) {
						SelID = left.options[i].value;
						SelText = left.options[i].text;
						var newRow = new Option(SelText, SelID);
						right.options[right.length] = newRow;
						left.options[i] = null;
						getCardProgramByPmId(SelID);
						entitiesId.push(SelID);
						entityNameArr.push(SelText);
						setDiv('programManagerNameIdEr', '');
					}
				}				
			}else if(action == 'REMOVE'){
				for (i = left.options.length - 1; i >= 0; i--) {
					if (left.options[i].selected == true) {
						SelID = left.options[i].value;
						SelText = left.options[i].text;
						var newRow = new Option(SelText, SelID);
						right.options[right.length] = newRow;
						left.options[i] = null;
						removeCardProgramFromList(SelID);
						for(var k=0; k < entitiesId.length; k++){
							if(entitiesId[k] != SelID){
								tempProgramManagerIds[j] = entitiesId[k];
								tempEntityName[j]=entityNameArr[i];
								j++;
							}
						}
						entitiesId = tempProgramManagerIds;
						entityNameArr = tempEntityName;
						j=0;
						tempProgramManagerIds = [];
						tempEntityName = [];
					}
				}
			}
			SelectSort(right);
			//set selected pm ids
			$('#entitiesId').val(entitiesId);
			$('#confirmEntityNames').text(entityNameArr.toString());
        }
        function SelectSort(SelList) {
            var ID = '';
            var Text = '';
            for (x = 0; x < SelList.length - 1; x++) {
                for (y = x + 1; y < SelList.length; y++) {
                    if (SelList[x].text > SelList[y].text) {
                        // Swap rows
                        ID = SelList[x].value;
                        Text = SelList[x].text;
                        SelList[x].value = SelList[y].value;
                        SelList[x].text = SelList[y].text;
                        SelList[y].value = ID;
                        SelList[y].text = Text;
                    }
                }
            }
        }
        /* Select Services moving form Left to Right and Right to Left functionality End */
		var cardProgramIdList = [];
        var cardProgramArr = [];
        var selectedCpId = [];
		function addCardProgram(cardProgramId,entityName,entityId){
			var tempCardProgramIds = [];
			var tempCardProgramArr = [];
			var j=0;
			var selectedId = 'cpId' + cardProgramId + entityId;
			
			if($('#' + selectedId).is(":checked")){
				$('#ambiguityFlag').text('');
				cardProgramIdList.push(cardProgramId+'@'+entityId);
				selectedCpId.push(parseInt(cardProgramId));
			}else if(!($('#' + selectedId).is(":checked"))){
				for(var i=0; i < cardProgramIdList.length; i++){
					if(cardProgramIdList[i] != cardProgramId+'@'+entityId){
						tempCardProgramIds[j] = cardProgramIdList[i];
						tempCardProgramArr[j] = cardProgramArr[i];
						j++;
					} 
				}
				cardProgramIdList = tempCardProgramIds;
				cardProgramArr = tempCardProgramArr;
				var index = selectedCpId.indexOf(parseInt(cardProgramId));
				if (index > -1) {
					selectedCpId.splice(index, 1);
				}
			}
			//set selected card pogram ids
		$('#cardProgramIds').val(cardProgramIdList);
		}
		function doUnCheckedToCardProgram(cardProgramId,cardProgramName,entityId) {
			var tempCardProgramIds = [];
			var tempCardProgramName = [];
			var j = 0;
			for (var i = 0; i < cardProgramIdList.length; i++) {
				if (cardProgramIdList[i] != cardProgramId+'@'+entityId && cardProgramArr[i] != cardProgramName) {
					tempCardProgramIds[j] = cardProgramIdList[i];
					tempCardProgramName[j] = cardProgramArr[i];
					j++;
				}
			}
			cardProgramIdList = tempCardProgramIds;
			cardProgramArr = tempCardProgramName;
			var index = selectedCpId.indexOf(parseInt(cardProgramId));
			if (index > -1) {
				selectedCpId.splice(index, 1);
			}
		}
		
		
		function validateAssocated() {
			document.getElementById("programManagerNameId").innerHTML = "";
			document.getElementById('programManagerNameId').options.length = 0;
			$("#serviceResults").find("tr:gt(0)").remove();
			cardProgramIdList = [];
			entityNameArr = [];
			entitiesId = [];
			cardProgramArr = [];
			selectedCpId = [];
		}

		function validateSelectedCardProgram() {
			var selectedCardProgramIdList = selectedCpId;
			if (selectedCardProgramIdList === undefined
					|| selectedCardProgramIdList.length == 0) {
				$('#ambiguityFlag').text(webMessages.SELECT_CARD_PROGRAM);
				return false;
			} else {
				$('#ambiguityFlag').text(' ');
				return true;
			}
		}
		document.getElementById('lookingFor').setAttribute('maxlength', '100');
	</script>
</body>
</html>
