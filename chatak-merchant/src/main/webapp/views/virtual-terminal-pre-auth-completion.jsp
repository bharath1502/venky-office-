<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Ipsidy Acquirer Merchant</title>
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
<body>
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<jsp:include page="header.jsp"></jsp:include>
			<!--Header Welcome Text and Logout button End -->
			<!--Header Block End -->
			<!--Navigation Block Start -->
			<nav class="col-sm-12 nav-bar" id="main-navigation">
			<%@include file="navigation-panel.jsp"%>
			 <%-- <jsp:include
				page="main-navigation.jsp"></jsp:include> --%> </nav>
			<!--Navigation Block End -->
			<!--Article Block Start-->
			<article>
			<div class="col-xs-12 content-wrapper">
				<!-- Breadcrumb start -->
				<div class="breadCrumb">
					<span class="breadcrumb-text"><spring:message code="virtual-terminal-sale.label.virtualterminal"/></span> <span
						class="glyphicon glyphicon-play icon-font-size"></span> <span
						class="breadcrumb-text"><spring:message code="virtual-terminal-sale.label.preauthlookup"/></span>
				</div>
				<!-- Breadcrumb End -->
				<!-- Tab Buttons Start -->
				<div class="tab-header-container-first">
					<a href="virtual-terminal-sale"><spring:message code="virtual-terminal-sale.label.sale"/></a>
				</div>
				<div class="tab-header-container">
					<a href="virtual-pre-auth"><spring:message code="virtual-terminal-sale.label.preauth"/></a>
				</div>
				<div class="tab-header-container active-background">
					<a href="#"><spring:message code="virtual-terminal-sale.label.preauthlookup"/></a>
				</div>
				<div class="tab-header-container">
					<a href="virtual-terminal-refund"><spring:message code="virtual-terminal-sale.label.refund"/></a>
				</div>
				<div class="tab-header-container">
					<a href="virtual-terminal-void"><spring:message code="virtual-terminal-sale.label.void"/></a>
				</div>
				<div class="tab-header-container">
					<a href="virtual-terminal-adjust"><spring:message code="virtual-terminal-sale.label.adjust"/></a>
				</div>
				<!-- Tab Buttons End -->
				<!-- Content Block Start -->
				<div class="main-content-holder">
					<div class="row">
						<div class="col-sm-12">
							<!--Success and Failure Message Start-->
							<div class="col-xs-12">
								<div class="discriptionErrorMsg red-error" id="errorDescDiv">
									<span class="red-error">&nbsp;${error }</span>
								</div>
							</div>
							<!--Success and Failure Message End-->
							<!-- Page Form Start -->
							<form:form action="do-virtual-terminal-pre-auth-completion"
								commandName="virtualTeminalPreAuthCompleation" id="txnForm">
								<div class="col-sm-12" id="hideAllFields">
									<div class="row">
										<div class="field-element-row">
											<fieldset class="col-sm-12">
												<!-- <fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title="">Invoice Number<span class="required-field">*</span></label>
													<input type="text" class="form-control"
														id="invoiceNumberDiv"
														onblur="this.value=this.value.trim();validInvoiceNumber('invoiceNumberDiv','invoiceNumberErrorDiv')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="invoiceNumberErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title="">Card Number<span class="required-field">*</span></label>
													<input type="text" class="form-control" id="cardNumberDiv"
														onblur="this.value=this.value.trim();validCardNumber('cardNumberDiv','cardNumberErrorDiv')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="cardNumberErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title="">Auth Code<span class="required-field">*</span></label>
													<input type="text" class="form-control" id="authNumberDiv"
														onblur="this.value=this.value.trim();validAuthNumber('authNumberDiv','authNumberErrorDiv')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="authNumberErrorDiv">&nbsp;</span>
													</div>
												</fieldset> -->
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.transactionid"/><span class="required-field">*</span></label>
													<input type="text" class="form-control" id="refNumberDiv"
														onblur="this.value=this.value.trim();validRefNumber('refNumberDiv','refNumberErrorDiv')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="refNumberErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-6">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.description"/><span class="required-field">*</span></label>
													<form:textarea path="description" cssClass="form-control"
														id="descriptionDiv" 
														onblur="this.value=this.value.trim();validateDesc()" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="descriptionErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 form-action-buttons">
													<div class="col-sm-5"></div>
													<div class="col-sm-7">
														<input type="button"
															class="form-control button pull-right fetch-button"
															value='<spring:message code="virtual-terminal-sale.label.searchbutton"/>' onClick="return validatePreAuthFetch()">
														<input type="reset"
															class="form-control button pull-right close-fetch-content"
															value='<spring:message code = "virtual-terminal-sale.label.resetbutton"/>' onClick="resetPreAuthFetch()">
													</div>
												</div>
												<!--Panel Action Button End -->
											</fieldset>
											<fieldset class="col-sm-12 fetch-content">
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.invoicenumber"/><span class="required-field">*</span></label>
													<form:input path="invoiceNumber" cssClass="form-control"
														id="invoiceNumberDiv1" readonly="true"
														onblur="this.value=this.value.trim();validInvoiceNumber('invoiceNumberDiv1','invoiceNumberErrorDiv1')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="invoiceNumberErrorDiv1">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.cardnumber"/><span class="required-field">*</span></label>
													<form:input path="cardNumMasked" cssClass="form-control"
														id="cardNumberDiv1" readonly="true"
														onblur="this.value=this.value.trim();" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="cardNumberErrorDiv1">&nbsp;</span>
													</div>
												</fieldset>
												<form:hidden path="cardNum" id="cardNumPlain"/>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="transaction-refund.label.authcode"/><span class="required-field">*</span></label>
													<form:input path="authId" cssClass="form-control"
														readonly="true" id="authNumberDiv1"
														onblur="this.value=this.value.trim();validAuthNumber('authNumberDiv1','authNumberErrorDiv1')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="authNumberErrorDiv1">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.expiredate"/><span class="required-field">*</span></label>
													<form:input path="expDate" cssClass="form-control"
														id="expDateDiv" readonly="true"
														onblur="this.value=this.value.trim();validExpDate()" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="expDateErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-contract-create.label.subtotal"/><span class="required-field">*</span></label>
													<fmt:formatNumber type="number"
														value="${virtualTeminalPreAuthCompleation.subTotal}"
														pattern="<%=Constants.AMOUNT_FORMAT %>" var="subTotal" />
													<input name="subTotal" value="${subTotal}"
														class="form-control" readonly="readonly" id="subTotalDiv"
														onblur="this.value=this.value.trim();validAmount('subTotalDiv','subTotalErrorDiv');doAdd()" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="subTotalErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<!-- 	<label data-toggle="tooltip" data-placement="top" title="">Tax Amount</label> -->
													<form:hidden path="taxAmt" id="taxAmtDiv" />
													<!-- <div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div> -->
												</fieldset>
												<fieldset class="col-sm-3">
													<!-- <label data-toggle="tooltip" data-placement="top" title="">Tip Amount</label> -->
													<form:hidden path="tipAmount" id="tipAmountDiv" />
													<!-- <div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div> -->
												</fieldset>
												<fieldset class="col-sm-3">
													<!-- <label data-toggle="tooltip" data-placement="top" title="">Shipping Charges</label> -->
													<form:hidden path="shippingAmt" id="shippingAmtDiv" />
													<!-- <div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div> -->
												</fieldset>
												<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.fee"/></label>
															<fmt:formatNumber type="number"
														value="${virtualTeminalRefund.feeAmount}"
														pattern="<%=Constants.AMOUNT_FORMAT %>" var="feeAmount" />
															<input name="feeAmount" class="form-control" onkeypress="return amountValidate(this,event)" readonly="readonly"
																onblur="clientValidation('feeAmountDiv', 'fee_amount','feeAmountErrorDiv');doAdd();" value="${feeAmount}" id="feeAmountDiv"  />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="feeAmountErrorDiv" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.totalamount"/><span class="required-field">*</span></label>
													<fmt:formatNumber type="number"
														value="${virtualTeminalPreAuthCompleation.txnAmount}"
														pattern="<%=Constants.AMOUNT_FORMAT %>" var="txnAmount" />
													<input name="txnAmount" value="${txnAmount}"
														class="form-control" id="totalAmtDiv" readonly="readonly"
														onblur="this.value=this.value.trim();validAmount('totalAmtDiv','totalAmtErrorDiv')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="totalAmtErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.cardholdername"/><span class="required-field">*</span></label>
													<form:input path="cardHolderName" cssClass="form-control"
														id="cardHolderNameDiv" readonly="true"
														onblur="this.value=this.value.trim();validCardHolderName()" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="cardHolderNameErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.street"/><span class="required-field">*</span></label>
													<form:input path="street" cssClass="form-control"
														id="streetDiv"
														onblur="this.value=this.value.trim();validStreet()" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="streetErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.city"/><span class="required-field">*</span></label>
													<form:input path="city" cssClass="form-control"
														id="cityDiv"
														onblur="this.value=this.value.trim();validCity()" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="cityErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.zipcode"/><span class="required-field">*</span></label>
													<form:input path="zip" cssClass="form-control"
														id="zipcodeDiv"
														onblur="this.value=this.value.trim();validZipcode()" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="zipcodeErrorDiv">&nbsp;</span>
													</div>
													<!-- <input type="hidden" id="txnRefNum" name="txnRefNum" /> -->
												</fieldset>
												<fieldset class="col-sm-3">
													<form:hidden path="cgRefNumber" id="cgRefNumberDiv" />
													<form:hidden path="txnRefNumber" id="txnRefNumberDiv" />
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 form-action-buttons">
													<div class="col-sm-5"></div>
													<div class="col-sm-7">
														<input type="button" class="form-control button pull-right" value='<spring:message coode="virtual-terminal-sale.label.processbutton"/>" onClick="return openConfirmPagePreLook()">
														<!--  <input
															type="button"
															class="form-control button pull-right close-fetch-content"
															value="Cancel" onClick="return resetPreAuth()"> -->
													</div>
												</div>
												<!--Panel Action Button End -->
											</fieldset>
										</div>
									</div>
								</div>
								<div id="responseDiv">
									<c:if test="${not empty txnRefNum.authId }">
										<br>
										<table>
										<tr>
													<td><span class="black-text">&nbsp;<spring:message code="virtual-terminal-sale.label.transactionstatus"/>:</span></td>
													<td><span class="green-error">&nbsp;: ${sucess}</span></td>
												</tr>	
												<tr>
													<td><span class="green-error">&nbsp;<spring:message code="virtual-terminal-sale.label.pleasenotebelowdetails"/></span></td>
												</tr>
											<tr>
												<td><span class="black-text">&nbsp;<spring:message code="virtual-terminal-sale.label.authid"/></span></td>
												<td><span class="green-error">&nbsp;:
														${txnRefNum.authId}</span></td>
											</tr>
											<tr>
												<td><span class="black-text">&nbsp;<spring:message code="virtual-terminal-sale.label.transactionid"/></span></td>
												<td><span class="green-error">&nbsp;
														:${txnRefNum.txnRefNumber}</span></td>
											</tr>
											
										</table>
										<div class="col-sm-12 form-action-buttons">
												<div class="col-sm-5"></div>
												<div class="col-sm-7">
													<a class="form-control button pull-right"
														href="virtual-terminal-pre-auth-completion"><spring:message code="virtual-terminal-sale.label.backbutton"/></a>
												</div>
											</div>
									</c:if>

								</div>
								
								<section class="field-element-table pos-transaction-content" style="display:none;" id="confirmPage">
												<fieldset class="col-sm-12 padding0">
													<fieldset class="col-sm-10">
														<fieldset class="fieldset merchant-content">
															<legend class="confirmHeader"><spring:message code="virtual-terminal-sale.label.confirmation"/></legend>
															<table class="confirm-info-table">
																<tr>
																	<td><spring:message code="virtual-terminal-adjust.label.referencenumber"/>:</td>
																	<td><div id="confirmRefNumberDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="processing-transaction-details.label.description"/>:</td>
																	<td><div id="confirmDescriptionDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-sale.label.invoicenumber"/>:</td>
																	<td><div id="confirmInvoiceNumberDiv1"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="recurring-search.label.cardnumber"/>:</td>
																	<td><div id="confirmCardNumberDiv1"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="transaction-refund.label.authcode"/>:</td>
																	<td><div id="confirmAuthNumberDiv1"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="recurring-search.label.expiredate"/>:</td>
																	<td><div id="confirmExpDateDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="recurring-contract-create.label.subtotal"/>($):</td>
																	<td><div id="confirmSubTotalDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-sale.label.fee"/>($):</td>
																	<td><div id="confirmFeeAmountDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-sale.label.totalamount"/>($):</td>
																	<td><div id="confirmTotalAmtDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-sale.label.cardholdername"/>:</td>
																	<td><div id="confirmCardHolderNameDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-sale.label.street"/>:</td>
																	<td><div id="confirmStreetDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-sale.label.city"/>:</td>
																	<td><div id="confirmCityDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-sale.label.zipcode"/>:</td>
																	<td><div id="confirmZipcodeDiv"></div></td>
																</tr>
															</table>
														</fieldset>
													</fieldset>
													
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="submit" class="form-control button pull-right pos-next" value="<spring:message code="virtual-terminal-sale.label.processbutton"/>"> 
														<input type="button" class="form-control button pull-right marginL10 pos-prev" onclick="showEditPageSale()" value="<spring:message code="virtual-terminal-sale.label.backbutton"/>"> 
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											
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
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script> <script src="../js/utils.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/virtual-terminal.js"></script>
	<script>
		/* Common Navigation Include Start */
		$(document).ready(function() {
			
			 $(window).keydown(function(event){
				    if(event.keyCode == 13) {
				      event.preventDefault();
				      return false;
				    }
				  });
			 
			alertrefNum('${refFlag}', '${txnRefNum.txnRefNumber}');
			
			if ("${virtualTeminalPreAuthCompleation.successDiv}" == "true") {
				$("#hideAllFields").hide();
			}
		});
		function highlightMainContent() {
			$("#navListId2").addClass("active-background");
		}
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
		/* Approval drop down functionality End */
		/* Fetch Content Functionality Start */
		$(".fetch-content").hide();
		$(".fetch-button").click(function() {
			if (slideDownFlag == 1) {
				doAjaxFetchTransactionForCapture();
			} else {
				$(".fetch-content").slideUp();
			}
		});
		$(".close-fetch-content").click(function() {
			$(".fetch-content").slideUp();
		});
		function alertrefNum(refFlag, txnRefNum) {
			if (refFlag) {
				/* alert("Please keep this reference num:" + txnRefNum +"auth code:"+'${txnRefNum.authId}'); */
			}
		}
		/* Fetch Content Functionality End */
		$(document).ready(function() {
			highlightMainContent('navListId1');

			/*  $("#search-result-table").hide(); */
		});
	</script>
	<script src="../js/backbutton.js"></script>
</body>
</html>