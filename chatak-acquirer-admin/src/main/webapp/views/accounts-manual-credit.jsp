<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ page import="com.chatak.pg.constants.PGConstants"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
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
<body>
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<%-- 	<jsp:include page="header.jsp"></jsp:include> --%>
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
						<span class="breadcrumb-text"><spring:message code="accounts-manual-credit.label.adjustments"/></span>
						<span class="glyphicon glyphicon-play icon-font-size"></span>
						<span class="breadcrumb-text"><spring:message code="accounts-manual-credit.label.manualcredit"/></span>
					</div>
					<div class="tab-header-container active-background" style="margin-left: 44px;">
						<a href="#"><spring:message code="accounts-manual-credit.label.manualcredit"/></a>
					</div>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="descriptionMsg red-error" id="errorMsgDiv">
										<span class="red-error">&nbsp;${error }</span> 
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="process-manual-credit" commandName="accountBalance" method="post">
								<form:hidden id="availableBal" path="availableBalance"/>
								<form:hidden id="currentBal" path="currentBalance"/>
								<form:hidden id="inputAmt" path="inputAmount"/>
								<form:hidden id="timeZoneOffset" path="timeZoneOffset"/>
								<form:hidden id="timeZoneRegion" path="timeZoneRegion"/>
									<div class="col-sm-12" id="hideAllFields">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-12" id="hideMerchantCode">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="accounts-manual-credit.label.merchantorsubmerchantcode"/><span class="required-field">*</span></label>
														<%-- <form:input path="merchantId" cssClass="form-control"
														id="merchantIdDiv" maxlength="15" onblur="false"
														 /> --%>
														<form:input cssClass="form-control" path="merchantCode" id="merchantIdDiv" 
															onblur="this.value=this.value.trim();clientValidation('merchantIdDiv', 'merchant_Code','merchantIdErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error" id="merchantIdErrorDiv">&nbsp;</span>
														</div>
													</fieldset>
													<!-- <fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title="">Transaction ID<span
															class="required-field">*</span></label> <input type="text"
															class="form-control" id="refNumberDiv"
															onblur="this.value=this.value.trim();validRefNumber('refNumberDiv','refNumberErrorDiv')" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error" id="refNumberErrorDiv">&nbsp;</span>
														</div>
													</fieldset> -->
													
													<!--Panel Action Button Start -->
													<div class="col-sm-12 form-action-buttons" id="hideSearchButton">
														<div class="col-sm-5"></div>
														<div class="col-sm-7">
															<input type="button" class="form-control button pull-right fetch-button" value='<spring:message code="accounts-manual-credit.label.searchbutton"/>' onClick="return validateAccMerchantCode()">
															<input type="button" class="form-control button pull-right close-fetch-details" onclick="resetValues()" value='<spring:message code="accounts-manual-credit.label.resetbutton"/>'>
														</div>
													</div>
													<!--Panel Action Button End -->
												</fieldset>
												<fieldset class="col-sm-12 fetch-details" style="display: none;">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="reports.label.transactions.name"/><span class="required-field">*</span></label>
														<form:input path="merchantName" cssClass="form-control"
															id="merchantName" readonly="true"
															onblur="this.value=this.value.trim();validInvoiceNumber('invoiceNumberDiv1','invoiceNumberErrorDiv1')" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error" id="invoiceNumberErrorDiv1">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="reports.label.balancereports.accountnumber"/><span class="required-field">*</span></label>
														<form:input path="accountNumber" cssClass="form-control"
															readonly="true" id="accountNumber"
															onblur="this.value=this.value.trim();" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error" id="cardNumberErrorDiv1">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-account-transfer.label.availablebalance"/><span class="required-field">*</span></label>
														<form:input path="availableBalance" cssClass="form-control alignright"
															id="availableBalance" readonly="true"
															onblur="this.value=this.value.trim();validAuthNumber('authNumberDiv1','authNumberErrorDiv1')" />
															<h3 class="currencySymbol" id="availableBalCurrencyAlpha"></h3>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error" id="authNumberErrorDiv1">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="reports.label.balancereports.currentbalance"/><span class="required-field">*</span></label>
														<form:input path="currentBalance" cssClass="form-control alignright"
															readonly="true" id="currentBalance"
															onblur="this.value=this.value.trim();validExpDate()" />
															<h3 class="currencySymbol" id="currentBalCurrencyAlpha"></h3>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error" id="expDateErrorDiv">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="accounts-manual-credit.label.creditamount"/><span class="required-field">*</span></label>
														<fmt:formatNumber type="number"
															value="${accountBalance.inputAmount}"
															pattern="<%=Constants.AMOUNT_FORMAT %>" var="amount" />
														<input name="inputAmount" class="form-control alignright" maxlength="7"
															id="inputAmount" value="${amount}" onkeypress="return amountValidate(this,event);" 
															onblur="formatNum(id);this.value=this.value.trim();validInputAmount('inputAmount','inputAmountErrorDiv')" />
															<h3 class="currencySymbol" id="inputAmtCurrencyAlpha"></h3>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error" id="inputAmountErrorDiv">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-6">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-account-transfer.label.description"/><span class="required-field">*</span></label>
														<form:textarea path="description" cssClass="form-control"
															id="descriptionDiv"
															onblur="this.value=this.value.trim();clientValidation('descriptionDiv', 'fee_Description','descriptionErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error" id="descriptionErrorDiv">&nbsp;</span>
														</div>
													</fieldset>
													<!--Panel Action Button Start -->
													<div class="col-sm-12 button-content">
														<fieldset class="col-sm-7 pull-right">
															<input type="submit" class="form-control button pull-right pos-next" id="processBtn" onclick="return validateCredit();amountFmt()" value="Process"> 
															<input type="button" class="form-control button pull-right marginL10 pos-prev close-fetch-details" value="Back"> <!-- onclick="showEditPage()"  -->
														</fieldset>
													</div>
													<!--Panel Action Button End -->
												</fieldset>
											</div>
										</div>
									</div>
									
									<div id="responseDiv">
										<c:if test="${creditResponse.errorCode eq 00}">
											<br>
											<table>
											<tr>
													<td><span class="black-text" style="font-size: initial;">&nbsp;<spring:message code="accounts-manual-credit.label.creditedsuccessfully"/></span></td>
													<td><span class="green-error">&nbsp; ${sucess}</span></td>
												</tr>
												<c:if test="${accountBalance.entityType eq 'Merchant'}">
												<tr>
													<td><span class="black-text">&nbsp;<spring:message code="virtual-terminal-void.label.merchantcode"/></span></td>
													<td><span class="green-error">&nbsp;:${accountBalance.merchantCode}</span></td>
												</tr>
												</c:if>
												<c:if test="${accountBalance.entityType eq 'SubMerchant'}">
												<tr>
													<td><span class="black-text">&nbsp;<spring:message code="merchant.label.submerchantcode"/></span></td>
													<td><span class="green-error">&nbsp;:${accountBalance.merchantCode}</span></td>
												</tr>
												</c:if>
												<tr>
													<td><span class="black-text">&nbsp;<spring:message code="reports.label.balancereports.availablebalance"/>(${accountBalance.merchantCurrencyAlpha})</span></td>
													<td><span id="avlBal" class="green-error">&nbsp;:${accountBalance.availableBalance}</span></td>
													<input type="hidden" id="avlamt" value="${accountBalance.availableBalance/100}" />
												</tr>
												<tr>
													<td><span class="black-text">&nbsp;<spring:message code="reports.label.balancereports.currentbalance"/>(${accountBalance.merchantCurrencyAlpha})</span></td>
													<td ><span id="curBal" class="green-error">&nbsp;:${accountBalance.currentBalance}</span></td>
													<input type="hidden" id="curamt" value="${accountBalance.currentBalance/100}" />
												</tr>
												<tr>
													<td><span class="black-text">&nbsp;<spring:message code="reports.label.transactions.companyname"/></span></td>
													<td><span class="green-error">&nbsp;:${accountBalance.merchantName}</span></td>
												</tr>
											</table>
											<div class="col-sm-12 form-action-buttons">
												<div class="col-sm-5"></div>
												<div class="col-sm-7">
													<a class="form-control button pull-right" href="accounts-manual-credit"><spring:message code="reports.label.backbutton"/></a>
												</div>
											</div>
											<script type="text/javascript">
											$("#hideMerchantCode").hide();
											</script>
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
			<div class="footer-container">
				<jsp:include page="footer.jsp"></jsp:include>
			</div>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/jquery.min.js"></script>
	<script src="../js/backbutton.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
	<script src='https://cdnjs.cloudflare.com/ajax/libs/jstimezonedetect/1.0.4/jstz.min.js'></script>
	<script src="../js/utils.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/accounts.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		/* Common Navigation Include Start */
		$(document).ready(function() {
			highlightMainContent();
			 $(window).keydown(function(event){
				    if(event.keyCode == 13) {
				      event.preventDefault();
				      return false;
				    }
				  });
			 
			/* alertrefNum('${refFlag}', '${txnRefNum.txnRefNumber}');
			if ("${virtualTerminalRefundDTO.successDiv}" == "true") {
				$("#hideAllFields").hide();
			} */
			doAjaxFetchAccountDetails();
			amountFmt();
		});

		function highlightMainContent() {
			$("#navListId5").addClass("active-background");
		}
		/* Fetch Content Functionality Start */
		$(".fetch-details").hide();
		$(".fetch-button").click(function() {
			if (slideDownFlag1 == 1) {
				doAjaxFetchAccountDetails();
			} else {
				$(".fetch-details").slideUp();
				$("#hideSearchButton").fadeIn();
			}
		});
		$(".close-fetch-details").click(function() {
			$(".fetch-details").slideUp();
			$("#hideSearchButton").fadeIn();
			$('#processBtn').show();
			document.getElementById("merchantIdDiv").readOnly = false;
		});
	</script>
	
</body>
</html>