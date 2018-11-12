<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Chatak Acquirer - Terminal Create</title>
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css">
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
			<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text">Terminal</span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text">Create</span>
					</div>
					<!-- Breadcrumb End -->
					<!--Success and Failure Message Start-->
					<div class="col-xs-12">
						<div class="mainDiscriptionErrorMsg col-xs-12">
							<span class="red-error">&nbsp;${error }</span> <span
								class="green-error">&nbsp;${sucess }</span>
						</div>
					</div>
					<!--Success and Failure Message End -->
					<!-- Content Block Start -->
					<div class="main-content-holder col-sm-12 padding0">

						<!-- Page Menu Start -->
						<div class="col-sm-12 padding0">
							<div class="sub-nav">
								<ul>
									<li class="account-details-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="merchant-circle-tab active-circle"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title="">Terminal Details</label>
										<div class="arrow-down merchant-arrow"></div>
									</li>
									<li class="free-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="contact-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title="">Contact Details</label>
										<div class="arrow-down contact-arrow"></div>
									</li>
									<li class="pos-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="final-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.confirmation"></spring:message></label>
										<div class="arrow-down final-arrow"></div>
									</li>
								</ul>
							</div>
						</div>
						<!-- Page Menu End -->
						<div class="row margin0">
							<div class="col-sm-12">
								<!-- Page Form Start -->
								<form:form action="createTerminal" modelAttribute="terminal"
									name="terminal">
								 <input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12 paddingT20">
										<div class="row">
											<!-- Account Details Content Start -->
											<section class="field-element-row account-details-content">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-4">

														<label data-toggle="tooltip" data-placement="top" title="">Merchant Code<span class="red-error">*</span></label>
														<form:select cssClass="form-control" path="merchantId"
															id="merchantId" onblur="validateMerchantCode()">
															<form:option value="-1">..:Select:..</form:option>
															<c:forEach items="${merchantOptions}" var="merchantOpt">
																<form:option value="${merchantOpt.value }">${merchantOpt.label }</form:option>
															</c:forEach>
														</form:select>

														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="merchantIdEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-4">
														<label data-toggle="tooltip" data-placement="top" title="">Terminal Code<span class="red-error">*</span></label>
														<form:input cssClass="form-control" path="terminalId"
															id="terminalId" maxlength="16"
															onblur="validateTerminalCode()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="terminalIdEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-4">
														<label data-toggle="tooltip" data-placement="top" title="">Make<span class="red-error">*</span></label>
														<form:input cssClass="form-control" path="make" id="make"
															maxlength="100" onblur="validateMake()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="makeEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-4">
														<label data-toggle="tooltip" data-placement="top" title="">Model<span class="red-error">*</span></label>
														<form:input cssClass="form-control" path="model"
															id="model" maxlength="100" onblur="validateModel()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="modelEr" class="red-error">&nbsp;</span>
														</div>

													</fieldset>
													<fieldset class="col-sm-4">
														<label data-toggle="tooltip" data-placement="top" title="">Terminal Type<span class="red-error">*</span></label>
														<div class="input-group">
															<form:input cssClass="form-control" path="terminalType"
																id="terminalType" maxlength="50"
																onblur="validateTerminalType()" />
														</div>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="terminalTypeEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-4">
														<label data-toggle="tooltip" data-placement="top" title="">Category<span class="red-error">*</span></label>
														<div class="input-group">
															<form:input cssClass="form-control" path="category"
																id="category" maxlength="50" onblur="validateCategory()" />
														</div>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="categoryEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-4">
														<label data-toggle="tooltip" data-placement="top" title="">Currency<span class="red-error">*</span></label>
														<form:select cssClass="form-control" path="currency"
															id="currency" onblur="validateCurrency()">
															<form:option value="0">..:Select:..</form:option>
															<form:option value="USD">USD</form:option>
															<form:option value="EUR">EUR</form:option>
															<form:option value="INR">INR</form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="currencyEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-4">
														<label data-toggle="tooltip" data-placement="top" title="">Valid from<span class="red-error">*</span></label>
														<div class="input-group">
															<form:input cssClass="form-control effectiveDate"
																path="validFrom" id="validFrom" onblur="checkFromDate()" />
															<span class="input-group-addon"><span
																class="glyphicon glyphicon-calendar"></span></span>
														</div>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="validFromEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-4">
														<label data-toggle="tooltip" data-placement="top" title="">Valid till<span class="red-error">*</span></label>
														<div class="input-group">
															<form:input cssClass="form-control effectiveDate"
																path="validTo" id="validTo"
																onblur="checkToDate();compareDate()" />
															<span class="input-group-addon"><span
																class="glyphicon glyphicon-calendar"></span></span>
														</div>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="validToEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															class="form-control button pull-right acc-next"
															value="Continue"> <input type="button"
															class="form-control button pull-right marginL10"
															value="Cancel"
															onclick="window.location.href='terminal-search'">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<!-- Account Details Content End -->
											<!-- Free Transactions Content Start -->
											<section class="field-element-row free-transactions-content">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-4">
														<label data-toggle="tooltip" data-placement="top" title="">City</label>
														<form:input cssClass="form-control" path="city" id="city"
															maxlength="50" onblur="validateCity()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="cityEr" class="red-error">&nbsp;</span>
														</div>
														<br>
													</fieldset>
													<fieldset class="col-sm-4">
														<label data-toggle="tooltip" data-placement="top" title="">State</label>
														<form:input cssClass="form-control" path="state"
															id="state" maxlength="50" onblur="validateState()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="stateEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-6">
														<label data-toggle="tooltip" data-placement="top" title="">&nbsp;</label>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-4">
														<label data-toggle="tooltip" data-placement="top" title="">Country</label>
														<form:input cssClass="form-control" path="country"
															id="country" maxlength="50" onblur="validateCountry()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="countryEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-4">
														<label data-toggle="tooltip" data-placement="top" title="">ZIP Code</label>
														<form:input cssClass="form-control" path="zip" id="zip"
															maxlength="10" onblur="validateZip()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="zipEr" class="red-error">&nbsp;</span>
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
															value="Previous">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<!-- Free Transactions Content End -->

											<!-- POS Transactions Content Start -->
											<section class="field-element-row pos-transaction-content">
												<fieldset class="col-sm-12 padding0">
													<fieldset class="col-sm-6">
														<fieldset class="fieldset merchant-content">
															<legend class="legend">Terminal Details</legend>
															<!--label>Label1: </label><input type="text"><br>
															<label data-toggle="tooltip" data-placement="top" title="">Label2: </label><input type="text"><br>
															<label data-toggle="tooltip" data-placement="top" title="">Label3: </label><input type="text"-->
															<table class="confirm-info-table">
																<tr>
																	<td>Merchant Code:</td>
																	<td><div id="confirmMmerchantId"></div></td>
																</tr>
																<tr>
																	<td>Terminal Code:</td>
																	<td><div id="confirmMterminalId"></div></td>
																</tr>
																<tr>
																	<td>Make:</td>
																	<td><div id="confirmMmake"></div></td>
																</tr>
																<tr>
																	<td>Model:</td>
																	<td><div id="confirmMmodel"></div></td>
																</tr>
																<tr>
																	<td>Terminal Type:</td>
																	<td><div id="confirmMterminalType"></div></td>
																</tr>
																<tr>
																	<td>Category:</td>
																	<td><div id="confirmMcategory"></div></td>
																</tr>
																<tr>
																	<td>Currency:</td>
																	<td><div id="confirmMcurrency"></div></td>
																</tr>
																<tr>
																	<td>Valid From:</td>
																	<td><div id="confirmMvalidFromDate"></div></td>
																</tr>
																<tr>
																	<td>Valid Till:</td>
																	<td><div id="confirmMvalidToDate"></div></td>
																</tr>
															</table>
														</fieldset>
													</fieldset>
													<fieldset class="col-sm-6">
														<fieldset class="fieldset contact-content">
															<legend class="legend">Contact Details</legend>
															<table class="confirm-info-table">
																<tr>
																	<td>City:</td>
																	<td><div id="confirmMcity"></div></td>
																</tr>
																<tr>
																	<td>State:</td>
																	<td><div id="confirmMstate"></div></td>
																</tr>
																<tr>
																	<td>Country:</td>
																	<td><div id="confirmMcountry"></div></td>
																</tr>
																<tr>
																	<td>Zip:</td>
																	<td><div id="confirmMzip"></div></td>
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
															value="Confirm"> <input type="button"
															class="form-control button pull-right marginL10 pos-prev"
															value="Previous">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
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
	<script type="text/javascript" src="../js/terminal.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script src="../js/common-lib.js"></script>
	<script>
		$(document).ready(function() {
			highlightMainContent('navListId3');
			/* DatePicker Javascript Strat*/
			$('.effectiveDate').datetimepicker({
				timepicker : false,
				format : 'm/d/Y',
				formatDate : 'Y/m/d',
			});
			/* DatePicker Javascript End*/
		});
		$(
				".free-transactions-content, .atm-transaction-content, .pos-transaction-content")
				.hide();
		$(".account-details-content").show();
		$(".merchant-arrow").show();
		$(".contact-arrow, .final-arrow").hide();
		$(".free-prev")
				.click(
						function() {
							$(".merchant-circle-tab").addClass("active-circle");
							$(".contact-circle-tab, .final-circle-tab")
									.removeClass("active-circle");

							$(".merchant-arrow").show();

							$(".account-details-content").show();
							$(
									".atm-transaction-content, .pos-transaction-content, .free-transactions-content")
									.hide();
						});
		$(".acc-next, .atm-prev")
				.click(
						function() {
							if (!validateCreateTerminalStep1()) {
								return false;
							}
							$(".contact-circle-tab").addClass("active-circle");
							$(".merchant-circle-tab, .final-circle-tab")
									.removeClass("active-circle");

							$(".contact-arrow").show();
							$(".merchant-arrow, .final-arrow").hide()

							$(".free-transactions-content").show();
							$(
									".atm-transaction-content, .pos-transaction-content, .account-details-content")
									.hide();
						});
		$(".free-next, .pos-prev")
				.click(
						function() {
							if (!validateCreateTerminalStep2()) {
								return false;
							}
							$(".final-circle-tab").addClass("active-circle");
							$(
									"merchant-circle-tab, .contact-circle-tab, .bank-circle-tab")
									.removeClass("active-circle");

							$(".final-arrow").show();
							$(".contact-arrow, .bank-arrow, .merchant-arrow")
									.hide()

							$(".pos-transaction-content").show();
							$(
									".free-transactions-content, .atm-transaction-content, .account-details-content")
									.hide();
						});
		$(".atm-next")
				.click(
						function() {

							$(".final-circle-tab").addClass("active-circle");
							$(
									"merchant-circle-tab, .contact-circle-tab, .bank-circle-tab")
									.removeClass("active-circle");

							$(".final-arrow").show();
							$(".contact-arrow, .bank-arrow, .merchant-arrow")
									.hide()

							$(".pos-transaction-content").show();
							$(
									".free-transactions-content, .atm-transaction-content, .account-details-content")
									.hide();
						});
	</script>
</body>
</html>