<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.merchant.constants.StatusConstants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Chatak Acquirer Merchant</title>
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
			<jsp:include page="header.jsp"></jsp:include>
			<!--Header Welcome Text and Logout button End -->
			</header>
			<!--Header Block End -->
			<!--Navigation Block Start -->
			<nav class="col-sm-12 nav-bar" id="main-navigation">
			<%@include file="navigation-panel.jsp"%>
			 <%-- <jsp:include
				page="main-navigation.jsp"></jsp:include>  --%></nav>
			<!--Navigation Block End -->
			<!--Article Block Start-->
			<article>
			<div class="col-xs-12 content-wrapper">
				<!-- Breadcrumb start -->
				<div class="breadCrumb">
					<span class="breadcrumb-text"><spring:message code="virtual-terminal-sale.label.virtualterminal"/></span> <span
						class="glyphicon glyphicon-play icon-font-size"></span> <span
						class="breadcrumb-text"><spring:message code="virtual-terminal-sale.label.void"/></span>
				</div>
				<!-- Breadcrumb End -->
				<!-- Tab Buttons Start -->
				<div class="tab-header-container-first">
					<a href="virtual-terminal-sale"><spring:message code="transactions-search.label.sale"/></a>
				</div>
				<div class="tab-header-container">
					<a href="virtual-pre-auth"><spring:message code="virtual-terminal-sale.label.preauth"/></a>
				</div>
				<div class="tab-header-container">
					<a href="virtual-terminal-pre-auth-completion"><spring:message code="virtual-terminal-sale.label.preauthlookup"/></a>
				</div>
				<div class="tab-header-container">
					<a href="virtual-terminal-refund"><spring:message code="virtual-terminal-sale.label.refund"/></a>
				</div>
				<div class="tab-header-container">
					<a href="virtual-terminal-void"><spring:message code="virtual-terminal-sale.label.void"/></a>
				</div>
				<div class="tab-header-container active-background">
					<a href="#"><spring:message code="virtual-terminal-sale.label.adjust"/></a>
				</div>
				<!-- Tab Buttons End -->
				<!-- Content Block Start -->
				<div class="main-content-holder">
					<div class="row">
						<div class="col-sm-12">
							<!--Success and Failure Message Start-->
							<div class="col-xs-12">
								<div class="discriptionErrorMsg" id="errorDescDiv">
									<span class="red-error">&nbsp;${error }</span> <span
										class="green-error">&nbsp;${success }</span>
								</div>
							</div>
							<!--Success and Failure Message End-->
							<!-- Page Form Start -->
							<form:form action="do-virtual-terminal-adjust"
								modelAttribute="virtualTeminalAdjust" id="txnForm">
								<input type="hidden" name="CSRFToken" value="${tokenval}">
								<div class="col-sm-12">
									<div class="row">
										<div class="field-element-row">
											<fieldset class="col-sm-12">
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-adjust.label.referencenumber"/><span class="required-field">*</span></label>
													<input type="text" class="form-control" id="refNumberDiv"
														onblur="this.value=this.value.trim();validRefNumber('refNumberDiv','refNumberErrorDiv')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="refNumberErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 form-action-buttons">
													<div class="col-sm-5"></div>
													<div class="col-sm-7">
														<input type="button"
															class="form-control button pull-right fetch-button"
															value="Fetch" onClick="return validatePreAuthFetch()" />
														<input type="reset" class="form-control button pull-right"
															value="Reset" onClick="resetPreAuthFetch()">
													</div>
												</div>
												<!--Panel Action Button End -->
											</fieldset>
											<fieldset class="col-sm-12 fetch-content">
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.invoicenumber"/><span class="required-field">*</span></label>
													<form:input path="invoiceNumber" cssClass="form-control"
														id="invoiceNumberDiv1"
														onblur="this.value=this.value.trim();validInvoiceNumber('invoiceNumberDiv1','invoiceNumberErrorDiv1')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="invoiceNumberErrorDiv1">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.cardnumber"/><span class="required-field">*</span></label>
													<form:input path="cardNum" cssClass="form-control"
														id="cardNumberDiv1"
														onblur="this.value=this.value.trim();validCardNumber('cardNumberDiv1','cardNumberErrorDiv1')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="cardNumberErrorDiv1">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="transaction-refund.label.authcode"/><span class="required-field">*</span></label>
													<form:input path="authId" cssClass="form-control"
														id="authNumberDiv1"
														onblur="this.value=this.value.trim();validAuthNumber('authNumberDiv1','authNumberErrorDiv1')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="authNumberErrorDiv1">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-contract-create.label.subtotal"/><span class="required-field">*</span></label>
													<fmt:formatNumber type="number"
													value="${virtualTeminalAdjust.subTotal}"
													pattern="<%=Constants.AMOUNT_FORMAT %>" var="subTotal" />
													<input name="subTotal" value="${subTotal}" class="form-control"
														id="subTotalDiv"
														onblur="this.value=this.value.trim();validAmount('subTotalDiv','subTotalErrorDiv')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="subTotalErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="configurations.label.tipamount"/></label>
													<form:input path="tipAmount" cssClass="form-control"
														id="tipAmountDiv" onblur="doAddAdjust()" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title="">Total<span class="required-field">*</span></label>
													<form:input path="txnAmount" cssClass="form-control"
														id="totalAmtDiv"
														onblur="this.value=this.value.trim();validAmount('totalDiv','totalErrorDiv')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="totalErrorDiv">&nbsp;</span>
													</div>
													<input type="hidden" id="txnRefNum" name="txnRefNum" />
													<input type="hidden" id="expDate" name="expDate" />
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 form-action-buttons">
													<div class="col-sm-5"></div>
													<div class="col-sm-7">
														<input type="submit"
															class="form-control button pull-right" value="Process"
															onClick="return validateAdjust()"> <input
															type="button"
															class="form-control button pull-right close-fetch-content"
															value="Cancel" onClick="return resetAdjust()">
													</div>
												</div>
												<!--Panel Action Button End -->
											</fieldset>
										</div>
									</div>
								</div>
								<div id="responseDiv">
									<c:if test="${not empty txnRefNum.authId }">
									<span class="green-error">&nbsp;<spring:message code="virtual-terminal-sale.label.pleasenotebelowdetails"/> </span><br>
									<table>
									<tr><td>

											<span class="red-error">&nbsp;<spring:message code="virtual-terminal-sale.label.authid"/></span></td><td>
											<span class="green-error">&nbsp;: ${txnRefNum.authId}</span></td></tr>
											<tr><td><span class="red-error">&nbsp;<spring:message code="processing-transaction-details.label.referenceno"/></span></td><td>
											<span class="green-error">&nbsp;
												:${txnRefNum.txnRefNumber}</span></td></tr>
									</table>
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
	<script src="../js/bootstrap.min.js"></script> <script src="../js/utils.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/virtual-terminal.js"></script>
	<script>
		/* Common Navigation Include Start */
		$(document).ready(function() {
			alertrefNum('${refFlag}', '${txnRefNum.txnRefNumber}');
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
				doAjaxFetchTransactionForAdjust();

			} else {
				$(".fetch-content").slideUp();
			}
		});
		$(".close-fetch-content").click(function() {
			$(".fetch-content").slideUp();
		});
		
		function alertrefNum(refFlag, txnRefNum) {
			if (refFlag) {
			/* 	alert("Please keep this reference num:" + txnRefNum +"auth code:"+'${txnRefNum.authId}'); */
			}
		}
		/* Fetch Content Functionality End */
		$(document).ready( function() {
			highlightMainContent('navListId1');
			
			/*  $("#search-result-table").hide(); */
		});	
	</script>
	<script src="../js/backbutton.js"></script>	
</body>
</html>
