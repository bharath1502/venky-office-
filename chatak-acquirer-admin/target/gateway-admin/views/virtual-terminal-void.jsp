
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
					<span class="breadcrumb-text"><spring:message code="virtual-terminal-void.label.virtualterminal" /></span> 
					<span class="glyphicon glyphicon-play icon-font-size"></span> 
					<span class="breadcrumb-text"><spring:message code="virtual-terminal-void.label.void" /></span>
				</div>
				<!-- Breadcrumb End -->
				<!-- Tab Buttons Start -->
				<div class="tab-header-container-first">
					<a href="virtual-terminal-sale"><spring:message code="virtual-terminal-void.label.saletab" /></a>
				</div>
				<div class="tab-header-container">
					<a href="virtual-terminal-pre-auth-completion">Pre Auth
						Lookup</a>
				</div> -->
				<div class="tab-header-container">
					<a href="virtual-terminal-refund"><spring:message code="virtual-terminal-void.label.refundtab" /></a>
				</div>
				<div class="tab-header-container active-background">
					<a href="#"><spring:message code="virtual-terminal-void.label.voidtab" /></a>
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
							<form:form modelAttribute="virtualTeminalVoid"
								action="do-virtual-terminal-void" id="txnForm">
								<input type="hidden" id="timeZoneOffset" name="timeZoneOffset"/>
									<input type="hidden" id="timeZoneRegion" name="timeZoneRegion"/>
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								<div class="col-sm-12" id="hideAllFields">
									<div class="row">
										<div class="field-element-row">
										
											<fieldset class="col-sm-12">
											<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.merchantcode" /><span class="required-field">*</span></label>
													<%-- <form:input path="merchantId" cssClass="form-control"
														id="merchantIdDiv" maxlength="15" onblur="false"
														 /> --%>
														 <input type="text" class="form-control" id="merchantIdDiv"
														onblur="this.value=this.value.trim();validMerchantCode('merchantIdDiv','merchantIdErrorDiv')"  />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="merchantIdErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.transactionid" /><span class="required-field">*</span></label>
													<input type="text" class="form-control" id="refNumberDiv"
														onblur="this.value=this.value.trim();validRefNumber('refNumberDiv','refNumberErrorDiv')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="refNumberErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-6">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.description" /></label>
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
															value="<spring:message code="virtual-terminal-void.label.searchbutton" />" onClick="return validatePreAuthFetch()">
														<input type="reset"
															class="form-control button pull-right close-fetch-content"
															value="<spring:message code="virtual-terminal-void.label.resetbutton" />" onClick="return resetPreAuthFetch()">
													</div>
												</div>
												<div class="col-sm-12" style="color:#1f497d;"><spring:message code="virtual-terminal-void.label.note" /></div>
												<!--Panel Action Button End -->
											</fieldset>
											<fieldset class="col-sm-12 fetch-content">
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.invoicenumber" /><span class="required-field">*</span></label>
													<form:input path="invoiceNumber" cssClass="form-control"
														id="invoiceNumberDiv1" readonly="true"
														onblur="this.value=this.value.trim();validInvoiceNumber('invoiceNumberDiv1','invoiceNumberErrorDiv1')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="invoiceNumberErrorDiv1">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.cardnumber" /><span class="required-field">*</span></label>
													<form:input path="cardNumMasked" cssClass="form-control"
														id="cardNumberDiv1" readonly="true"
														onblur="this.value=this.value.trim();" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="cardNumberErrorDiv1">&nbsp;</span>
													</div>
												</fieldset>
													<form:hidden path="cardNum" id="cardNumPlain"/>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.authcode" /><span class="required-field">*</span></label>
													<form:input path="authId" cssClass="form-control"
														id="authNumberDiv1" readonly="true"
														onblur="this.value=this.value.trim();validAuthNumber('authNumberDiv1','authNumberErrorDiv1')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="authNumberErrorDiv1">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.expdate" /><span class="required-field">*</span></label>
													<form:input path="expDate" cssClass="form-control"
														id="expDateDiv"
														onblur="this.value=this.value.trim();validExpDate()"
														readonly="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="expDateErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.subtotal" /><span class="required-field">*</span></label>
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
												<fieldset class="">
													<form:hidden path="merchantId" id="merchantIdHDiv" />
												</fieldset>
												<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.fee" /></label>
															<fmt:formatNumber type="number"
														value="${virtualTeminalRefund.feeAmount}"
														pattern="<%=Constants.AMOUNT_FORMAT %>" var="feeAmount" />
															<input name="feeAmount" class="form-control" onkeypress="return amountValidate(this,event)" readonly="readonly"
																onblur="clientValidation('feeAmountDiv', 'fee_amount','feeAmountErrorDiv');" value="${feeAmount}" id="feeAmountDiv"  />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="feeAmountErrorDiv" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.totalamount" /><span class="required-field">*</span></label>
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
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.cardholdername" /><span class="required-field">*</span></label>
													<form:input path="cardHolderName" cssClass="form-control"
														id="cardHolderNameDiv" readonly="true"
														onblur="this.value=this.value.trim();validCardHolderName()" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="cardHolderNameErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.street" /><span class="required-field">*</span></label>
													<form:input path="street" cssClass="form-control"
														id="streetDiv"
														onblur="this.value=this.value.trim();validStreet()" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="streetErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.city" /><span class="required-field">*</span></label>
													<form:input path="city" cssClass="form-control"
														id="cityDiv"
														onblur="this.value=this.value.trim();validCity()" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="cityErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-void.label.zipcode" /><span class="required-field">*</span></label>
													<form:input path="zip" cssClass="form-control"
														id="zipcodeDiv"
														onblur="this.value=this.value.trim();validZipcode()" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="zipcodeErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													
													<form:hidden path="cgRefNumber" 
														id="cgRefNumberDiv"/>
													<form:hidden path="txnRefNumber" 
														id="txnRefNumberDiv"/>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 form-action-buttons">
													<div class="col-sm-5"></div>
													<div class="col-sm-7">
														<input type="button" class="form-control button pull-right" value="<spring:message code="virtual-terminal-void.label.continue" />" onClick="return openConfirmPageVoid()">
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
													<td><span class="black-text">&nbsp;<spring:message code="virtual-terminal-void.label.transactionstatus" /></span></td>
													<td><span class="green-error">&nbsp;: ${sucess}</span></td>
												</tr>
												<tr>
													<td><span class="green-error">&nbsp;<spring:message code="virtual-terminal-void.label.pleasenote" /></span></td>
												</tr>
											<tr>
												<td><span class="black-text">&nbsp;<spring:message code="virtual-terminal-void.label.authid" /></span></td>
												<td><span class="green-error">&nbsp;:
														${txnRefNum.authId}</span></td>
											</tr>
											<tr>
												<td><span class="black-text">&nbsp;<spring:message code="virtual-terminal-void.label.transaction" />
														ID</span></td>
												<td><span class="green-error">&nbsp;
														:${txnRefNum.txnRefNumber}</span></td>
											</tr>
											<tr>
													<td><span class="black-text">&nbsp;<spring:message code="virtual-terminal-void.label.merchantcode" /></span></td>
													<td><span class="green-error">&nbsp;
															:${mCode}</span></td>
												</tr>
										</table>
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<a class="form-control button pull-right" href="virtual-terminal-void"
													><spring:message code="virtual-terminal-void.label.backbutton" /></a>
											</div>
										</div>
									</c:if>
								</div>
								
								
								
								
								
								<section class="field-element-table pos-transaction-content" style="display:none;" id="confirmPage">
												<fieldset class="col-sm-12 padding0">
													<fieldset class="col-sm-10">
														<fieldset class="fieldset merchant-content">
															<legend class="confirmHeader"><spring:message code="virtual-terminal-void.label.confirmation" /></legend>
															<table class="confirm-info-table">
																<tr>
																	<td><spring:message code="virtual-terminal-void.label.merchantcodevalue" /></td>
																	<td><div id="confirmMerchantIdDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-void.label.referencenumbervalue" /></td>
																	<td><div id="confirmRefNumberDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-void.label.descriptionvalue" /></td>
																	<td><div id="confirmDescriptionDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-void.label.invoicenumbervalue" /></td>
																	<td><div id="confirmInvoiceNumberDiv1"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-void.label.cardnumbervalue" /></td>
																	<td><div id="confirmCardNumberDiv1"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-void.label.authcodevalue" /></td>
																	<td><div id="confirmAuthNumberDiv1"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-void.label.expdatevalue" /></td>
																	<td><div id="confirmExpDateDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-void.label.subtotalvalue" /></td>
																	<td><div id="confirmSubTotalDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-void.label.feevalue" /></td>
																	<td><div id="confirmFeeAmountDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-void.label.totalmountvalue" /></td>
																	<td><div id="confirmTotalAmtDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-void.label.cardholdernamevalue" /></td>
																	<td><div id="confirmCardHolderNameDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-void.label.streetvalue" /></td>
																	<td><div id="confirmStreetDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-void.label.cityvalue" /></td>
																	<td><div id="confirmCityDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-void.label.zipcodevalue" /></td>
																	<td><div id="confirmZipcodeDiv"></div></td>
																</tr>
															</table>
														</fieldset>
													</fieldset>
													
													
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="submit" class="form-control button pull-right pos-next" value="<spring:message code="virtual-terminal-void.label.processbutton" />"> 
														<input type="button" class="form-control button pull-right marginL10 pos-prev" onclick="showEditPageVoid()" value="<spring:message code="virtual-terminal-void.label.backbutton" />"> 
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
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	 <script src="../js/jquery.cookie.js"></script>
	 <script src='https://cdnjs.cloudflare.com/ajax/libs/jstimezonedetect/1.0.4/jstz.min.js'></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/virtual-terminal.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		$(document).ready(function() {
			 $("#navListId7").addClass("active-background");
			 $(window).keydown(function(event){
				    if(event.keyCode == 13) {
				      event.preventDefault();
				      return false;
				    }
				  });
			 
			alertrefNum('${refFlag}', '${txnRefNum.txnRefNumber}');

			if("${virtualTerminalVoidDTO.successDiv}" == "true"){
				$("#hideAllFields").hide();
				}
		});

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
				doAjaxFetchTransactionForVoid()
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
	</script>
	<script src="../js/backbutton.js"></script>
</body>
</html>

