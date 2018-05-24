<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><spring:message code="common.lable.title"/></title>
<!-- Bootstrap -->
<link rel="icon" href="../images/favicons.png" type="image/png">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script> 
	<script src="../js/jquery.cookie.js"></script>
	<script src='https://cdnjs.cloudflare.com/ajax/libs/jstimezonedetect/1.0.4/jstz.min.js'></script>
	<script src="../js/common-lib.js"></script>
</head>
<body>
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
		<jsp:include page="header.jsp"></jsp:include>
			<!--Navigation Block Start --> 
			<nav class="col-sm-12 nav-bar" id="main-navigation">
			<%@include file="navigation-panel.jsp"%>
				<%-- <jsp:include page="main-navigation.jsp"></jsp:include> --%>
			</nav>
			<!--Navigation Block End -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="transactions-search.label.transactions"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="transactions-search.label.refundbutton"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					
					<!-- 	<div class="tab-header-container">
					<a href="virtual-pre-auth">Pre Auth</a>
				</div>
				<div class="tab-header-container">
					<a href="virtual-terminal-pre-auth-completion">Pre Auth Lookup</a>
				</div> -->
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="transactions-search.label.refundbutton"/></a>
					</div>
					
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionMsg" id="errorDescDiv">
										<span class="red-error">&nbsp;${error }</span> 
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="process-transaction-refund" commandName="virtualTeminalRefund" id="txnForm">
								<input type="hidden" id="timeZoneOffset" name="timeZoneOffset"/>
								<input type="hidden" id="timeZoneRegion" name="timeZoneRegion"/>
									<div class="col-sm-12" id="hideAllFields">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-12">
													<%-- <fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Merchant Code</label>
														<form:input path="merchantId" cssClass="form-control"
														id="merchantIdDiv" maxlength="15" readonly="true"
														 />
													</fieldset> --%>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.transactionid"/></label> 
														<form:input path="txnRefNumber" cssClass="form-control"
														id="transactionIdDiv" maxlength="15" readonly="true"
														 />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error" id="refNumberErrorDiv">&nbsp;</span>
														</div>
													</fieldset>
													
													<!--Panel Action Button End -->
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.invoicenumber"/></label>
														<form:input path="invoiceNumber" cssClass="form-control"
															id="invoiceNumberDiv1" readonly="true" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error" id="invoiceNumberErrorDiv1">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.cardnumber"/></label>
														<form:input path="cardNum" cssClass="form-control"
															readonly="true" id="cardNumberDiv1"	/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error" id="cardNumberErrorDiv1">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="transaction-refund.label.authcode"/></label>
														<form:input path="authId" cssClass="form-control"
															id="authNumberDiv1" readonly="true"	/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error" id="authNumberErrorDiv1">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.expirationdate"/></label>
														<form:input path="expDate" cssClass="form-control"
															readonly="true" id="expDateDiv"	/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error" id="expDateErrorDiv">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.Subtotal"/><span class="required-field">*</span></label>
														<fmt:formatNumber type="number"
															value="${virtualTeminalRefund.subTotal}"
															pattern="<%=Constants.AMOUNT_FORMAT %>" var="subTotal" />
														<input name="subTotal" class="form-control"
															id="subTotalDiv" value="${subTotal}"
															onblur="this.value=this.value.trim();validAmount('subTotalDiv','subTotalErrorDiv');doRefundAdd()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error" id="subTotalErrorDiv">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.fee"/><span class="required-field">*</span></label>
														<fmt:formatNumber type="number" value="${virtualTeminalRefund.feeAmount}" pattern="<%=Constants.AMOUNT_FORMAT %>" var="feeAmount" />
														<input name="feeAmount" class="form-control" onkeypress="return amountValidate(this,event)"
															onblur="clientValidation('feeAmountDiv', 'fee_amount','feeAmountErrorDiv');doRefundAdd();"
															value="${feeAmount}" id="feeAmountDiv" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="feeAmountErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.totalamount"/></label>
														<fmt:formatNumber type="number" value="${virtualTeminalRefund.txnAmount}" pattern="<%=Constants.AMOUNT_FORMAT %>" var="txnAmount" />
														<input name="txnAmount" class="form-control"
															value="${txnAmount}" readonly="readonly" id="totalAmtDiv"
															onblur="this.value=this.value.trim();validAmount('totalAmtDiv','totalAmtErrorDiv')" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error" id="totalAmtErrorDiv">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.cardholdername"/></label>
														<form:input path="cardHolderName" cssClass="form-control" readonly="true" id="cardHolderNameDiv" onkeydown="validateSpace(this)"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error" id="cardHolderNameErrorDiv">&nbsp;</span>
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
													
													<fieldset class="col-sm-3">
														<form:hidden path="taxAmt" />
													</fieldset>
													<fieldset class="col-sm-3">
														<!-- <!-- <label data-toggle="tooltip" data-placement="top" title="">Tip Amount</label> -->
														<form:hidden path="tipAmount" id="tipAmountDiv" />
														<form:hidden path="merchantId" id="merchantIdHDiv" />
														<form:hidden path="terminalId" id="terminalIdHDiv" />
													</fieldset>
													<fieldset class="col-sm-3">
														<!-- <label data-toggle="tooltip" data-placement="top" title="">Shipping Charges</label> -->
														<form:hidden path="shippingAmt" id="shippingAmtDiv" />
													</fieldset>
													<fieldset class="col-sm-3">

														<form:hidden path="cgRefNumber" id="cgRefNumberDiv" />
														<%-- <form:hidden path="txnRefNumber" id="txnRefNumberDiv" /> --%>
													</fieldset>
													<!--Panel Action Button Start -->
													<div class="col-sm-12 form-action-buttons">
														<div class="col-sm-5"></div>
														<div class="col-sm-7">
														<input type="submit" class="form-control button pull-right" value="Process" onclick="return validateDoRefund()">
														<a href="transactions-search" class="form-control button pull-right"><spring:message code="common.label.back"/></a>
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
													<td><b><span class="black-text">&nbsp;<spring:message code="virtual-terminal-sale.label.pleasenotebelowdetails"/></span></b></td>
											</tr>
										     <tr>
													<td><span class="black-text">&nbsp;<spring:message code="virtual-terminal-sale.label.transactionstatus"/></span></td>
													<td><span class="green-error">&nbsp;: ${sucess}</span></td>
											</tr>
												
											<tr>
												<td><span class="black-text">&nbsp;<spring:message code="reportFileExportUtil.total.amount"/>
															</span></td>
												<td><span class="green-error">&nbsp;: ${txnRefNum.totalTxnAmount}</span></td>
											</tr>
												
											  <tr>
												<td><span class="black-text">&nbsp;<spring:message code="virtual-terminal-sale.label.authid"/></span></td>
												<td><span class="green-error">&nbsp;: ${txnRefNum.authId}</span></td>
											  </tr>
											<tr>
												<td><span class="black-text">&nbsp;<spring:message code="virtual-terminal-sale.label.transactionid"/>
														</span></td>
												<td><span class="green-error">&nbsp;: ${txnRefNum.txnRefNumber}</span></td>
											</tr>
											<tr>
													<td><span class="black-text">&nbsp; <spring:message code="virtual-terminal-sale.label.cgtransactionid"/>
														</span></td>
												<td><span class="green-error">&nbsp;: ${txnRefNum.cgRefNumber}</span></td>
												</tr>
										</table>
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<a class="form-control button pull-right" href="transactions-search"
													><spring:message code="common.label.back"/></a>
											</div>
										</div>
									</c:if>
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

	<script src="../js/utils.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/virtual-terminal.js"></script>
	<script src="../js/validation.js"></script>
	<script>
		/* Common Navigation Include Start */
		$(document).ready(function() {
			 alertrefNum('${refFlag}', '${txnRefNum.txnRefNumber}');
			if ("${virtualTeminalRefund.successDiv}" == "true") {
				$("#hideAllFields").hide();
			} 
		});

		function highlightMainContent() {
			$("#navListId6").addClass("active-background");
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
	/* 	$(".fetch-content").hide();
		$(".fetch-button").click(function() {
			if (slideDownFlag == 1) {
				doAjaxFetchTransactionForRefund();
			} else {
				$(".fetch-content").slideUp();
			}
		});
		$(".close-fetch-content").click(function() {
			$(".fetch-content").slideUp();
		}); */

		function alertrefNum(refFlag, txnRefNum) {
			if (refFlag) {
				/* 	alert("Please keep this reference num:" + txnRefNum +"auth code:"+'${txnRefNum.authId}'); */
			}
		}
		/* Fetch Content Functionality End */
		$(document).ready(function() {
			highlightMainContent('navListId6');

			/*  $("#search-result-table").hide(); */
		});
	</script>
	<script src="../js/backbutton.js"></script>
</body>
</html>