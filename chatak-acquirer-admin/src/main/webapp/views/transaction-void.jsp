
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
			<!--Header Block Start -->
			<%-- <jsp:include page="header.jsp"></jsp:include> --%>
			<!--Header Welcome Text and Logout button End -->
			</header>
			<!--Header Block End -->
			<!--Navigation Block Start -->
			<%-- <jsp:include page="header.jsp"></jsp:include> --%>
			<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block End -->
			<!--Article Block Start-->
			<article>
			<div class="col-xs-12 content-wrapper">
				<!-- Breadcrumb start -->
				<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="execute-transaction-details.label.transaction"/>s</span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="execute-transaction-details.label.voidbutton"/></span>
					</div>
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="execute-transaction-details.label.voidbutton"/></a>
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
							<form:form modelAttribute="virtualTeminalVoid" action="processTransactionVoid" id="txnForm" method="post">
							 <input type="hidden" name="CSRFToken" value="${tokenval}">
								<div class="col-sm-12" id="hideAllFields">
									<div class="row">
										<div class="field-element-row">
										
											<fieldset class="col-sm-12">
											<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.merchantcode"/></label>
														 <form:input path="merchantId" cssClass="form-control" id="merchantIdDiv" readonly="readonly"
														onblur="this.value=this.value.trim();validMerchantCode('merchantIdDiv','merchantIdErrorDiv')"  />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="merchantIdErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.transactionid"/></label>
													<form:input path="txnRefNumber" cssClass="form-control" id="refNumberDiv" readonly="readonly"
														onblur="this.value=this.value.trim();validRefNumber('refNumberDiv','refNumberErrorDiv')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="refNumberErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.invoicenumber"/></label>
													<form:input path="invoiceNumber" cssClass="form-control"
														id="invoiceNumberDiv1" readonly="true"
														onblur="this.value=this.value.trim();validInvoiceNumber('invoiceNumberDiv1','invoiceNumberErrorDiv1')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="invoiceNumberErrorDiv1">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.cardnumber"/></label>
													<form:input path="cardNum" cssClass="form-control"
														id="cardNumberDiv1" readonly="true"
														onblur="this.value=this.value.trim();validCardNumber('cardNumberDiv1','cardNumberErrorDiv1')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="cardNumberErrorDiv1">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.authcode"/></label>
													<form:input path="authId" cssClass="form-control"
														id="authNumberDiv1" readonly="true"
														onblur="this.value=this.value.trim();validAuthNumber('authNumberDiv1','authNumberErrorDiv1')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="authNumberErrorDiv1">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.expdate"/></label>
													<form:input path="expDate" cssClass="form-control"
														id="expDateDiv"
														onblur="this.value=this.value.trim();validExpDate()"
														readonly="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="expDateErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.subtotal"/></label>
													<fmt:formatNumber type="number"
														value="${virtualTeminalVoid.subTotal}"
														pattern="<%=Constants.AMOUNT_FORMAT %>" var="subTotal" />
													<input name="subTotal" value="${subTotal}"
														class="form-control" id="subTotalDiv" readonly="readonly"
														onblur="this.value=this.value.trim();validAmount('subTotalDiv','subTotalErrorDiv');" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="subTotalErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="">
													<form:hidden path="taxAmt" id="taxAmtDiv" />
												</fieldset>
												<fieldset class="">
													<form:hidden path="tipAmount" id="tipAmountDiv" />
												</fieldset>
												<fieldset class="">
													<form:hidden path="shippingAmt" id="shippingAmtDiv" />
												</fieldset>
												<%-- <fieldset class="">
													<form:hidden path="merchantId" id="merchantIdHDiv" />
												</fieldset> --%>
												<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.fee"/></label>
															<fmt:formatNumber type="number" value="${virtualTeminalVoid.feeAmount}" pattern="<%=Constants.AMOUNT_FORMAT %>" var="feeAmount" />
															<input name="feeAmount" class="form-control" onkeypress="return amountValidate(this,event)" readonly="readonly"
																onblur="clientValidation('feeAmountDiv', 'fee_amount','feeAmountErrorDiv');" value="${feeAmount}" id="feeAmountDiv"  />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="feeAmountErrorDiv" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.totalamount"/></label>
													<fmt:formatNumber type="number"
														value="${virtualTeminalVoid.txnAmount}"
														pattern="<%=Constants.AMOUNT_FORMAT %>" var="txnAmount" />
													<input name="txnAmount" value="${txnAmount}"
														class="form-control" id="totalAmtDiv" readonly="readonly"
														onblur="this.value=this.value.trim();validAmount('totalAmtDiv','totalAmtErrorDiv')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="totalAmtErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.cardholdername"/></label>
													<form:input path="cardHolderName" cssClass="form-control"
														id="cardHolderNameDiv" readonly="true"
														onblur="this.value=this.value.trim();" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="cardHolderNameErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-6">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.descriptionvalue"/><span class="required-field">*</span></label>
													<form:textarea path="description" cssClass="form-control" id="descriptionDiv" onblur="this.value=this.value.trim();validateDesc()" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="descriptionErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													
													<form:hidden path="cgRefNumber" 
														id="cgRefNumberDiv"/>
													<%-- <form:hidden path="txnRefNumber" 
														id="txnRefNumberDiv"/> --%>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 form-action-buttons">
													<div class="col-sm-5"></div>
													<div class="col-sm-7">
													<a href="transactions-search" class="form-control button pull-right"><spring:message code="virtual-terminal-refund.label.backbutton"
														/></a>
														<input type="submit" class="form-control button pull-right" value="<spring:message code="virtual-terminal-refund.label.processbutton"/>" onClick="return validateVoidTrans()">
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
													<td><span class="black-text">&nbsp;<spring:message code="virtual-terminal-sale.label.transactionstatus"/></span></td>
													<td><span class="green-error">&nbsp;: ${sucess}</span></td>
												</tr>
												<tr>
													<td><span class="green-error">&nbsp;<spring:message code="virtual-terminal-sale.label.pleasenote"/></span></td>
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
											<tr>
													<td><span class="black-text">&nbsp;<spring:message code="virtual-terminal-sale.label.merchantcode"/></span></td>
													<td><span class="green-error">&nbsp;
															:${mCode}</span></td>
												</tr>
										</table>
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<a class="form-control button pull-right" href="transactions-search"
													><spring:message code="virtual-terminal-refund.label.backbutton"/></a>
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

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/virtual-terminal.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		/* Common Navigation Include Start */
		$(document).ready(function() {
			alertrefNum('${refFlag}', '${txnRefNum.txnRefNumber}');

			 if("${virtualTerminalVoidDTO.successDiv}" == "true"){
				$("#hideAllFields").hide();
				}
		});

		function highlightMainContent() {
			$("#navListId6").addClass("active-background");
		}
		/* Common Navigation Include End */
		/* Approval drop down functionality start */
		/* Approval drop down functionality End */
		/* Fetch Content Functionality Start */
		function alertrefNum(refFlag, txnRefNum) {
			if (refFlag) {
				/* 	alert("Please keep this reference num:" + txnRefNum +"auth code:"+'${txnRefNum.authId}'); */
			}
		}
		/* Fetch Content Functionality End */
		$(document).ready( function() {
			highlightMainContent('navListId6');
			
			/*  $("#search-result-table").hide(); */
		});	
	</script>
	<script src="../js/backbutton.js"></script>
</body>
</html>
