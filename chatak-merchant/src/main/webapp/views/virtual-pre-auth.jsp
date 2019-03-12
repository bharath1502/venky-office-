<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->


<script type="text/javascript">
	/***********************************************
	 * Drop Down Date select script- by JavaScriptKit.com
	 * This notice MUST stay intact for use
	 * Visit JavaScript Kit at http://www.javascriptkit.com/ for this script and more
	 ***********************************************/

	var monthtext = [ '01', '02', '03', '04', '05', '06', '07', '08', '09',
			'10', '11', '12' ];
	//var monthtext=['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sept','Oct','Nov','Dec'];

	function populatedropdown(monthfield, yearfield) {
		var today = new Date()

		var monthfield = document.getElementById(monthfield);
		var yearfield = document.getElementById(yearfield);
		for (var m = 0; m < 12; m++)
			monthfield.options[m] = new Option(monthtext[m], monthtext[m])
		monthfield.options[today.getMonth()] = new Option(monthtext[today
				.getMonth()], monthtext[today.getMonth()], true, true) //select today's month
		var thisyear = today.getFullYear()
		for (var y = 0; y < 20; y++) {
			yearfield.options[y] = new Option(thisyear, thisyear)
			thisyear += 1
		}
		yearfield.options[0] = new Option(today.getFullYear(), today
				.getFullYear(), true, true) //select today's year
	}
</script>
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<jsp:include page="header.jsp"></jsp:include>
			<!--Header Block End -->
			<!--Navigation Block Start -->
			<nav class="col-sm-12 nav-bar" id="main-navigation"> 
			<%@include file="navigation-panel.jsp"%> </nav>
			<!--Navigation Block End -->
			<!--Article Block Start-->
			<article>
			<div class="col-xs-12 content-wrapper">
				<!-- Breadcrumb start -->
				<div class="breadCrumb">
					<span class="breadcrumb-text"><spring:message code="virtual-terminal-sale.label.virtualterminal"/></span> <span
						class="glyphicon glyphicon-play icon-font-size"></span> <span
						class="breadcrumb-text"><spring:message code="virtual-terminal-sale.label.preauth"/></span>
				</div>
				<!-- Breadcrumb End -->
				<!-- Tab Buttons Start -->
				<div class="tab-header-container-first">
					<a href="virtual-terminal-sale"><spring:message code="virtual-terminal-sale.label.sale"/></a>
				</div>
				<div class="tab-header-container active-background">
					<a href="#"><spring:message code="virtual-terminal-sale.label.preauth"/></a>
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
				<div class="tab-header-container">
					<!-- <a href="virtual-terminal-adjust">Adjust</a> -->
					<a href="virtual-terminal-adjust"><spring:message code="virtual-terminal-sale.label.adjust"/></a>
				</div>
				<!-- Tab Buttons End -->
				<!-- Content Block Start -->
				<div class="main-content-holder">
					<div class="row">
						<div class="col-sm-12">
							<!--Success and Failure Message Start-->
							<div class="col-xs-12">
								<div class="discriptionErrorMsg" id="errorDescDiv">
									<span class="red-error">&nbsp;${error}</span> 
								</div>
							</div>
							<!--Success and Failure Message End-->
							<!-- Page Form Start -->
							<form:form action="do-virtual-terminal-preAuth"
								modelAttribute="virtualTerminalPreAuth" id="txnForm">
								<input type="hidden" name="CSRFToken" value="${tokenval}">
								<div class="col-sm-12" id="hideAllFields">
									<div class="row">
										<div class="field-element-row">
											<fieldset class="col-sm-3">
											<legend></legend>
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.cardnumber"/><span class="required-field">*</span></label>
												<form:input path="cardNum" cssClass="form-control"
													id="cardNumberDiv"
													onblur="this.value=this.value.trim();validCardNumber('cardNumberDiv','cardNumberErrorDiv')" maxlength="<%=Constants.CARD_NUM_MAXLEN%>"/>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="cardNumberErrorDiv">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
											<legend></legend>
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.expirationdate"/><span class="required-field">*</span></label><br>
												<form:select path="year"
													cssClass="form-control widthP50 displaiIB"
													id="yeardropdown" onblur="validateExpiryDate()">
												</form:select>
												<form:select path="month"
													cssClass="form-control widthP30 displaiIB"
													id="monthdropdown" onblur="validateExpiryDate()">
												</form:select>

												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="expiryDateErr">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
											<legend></legend>
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.cv2"/><span class="required-field">*</span></label>
												<form:input path="cvv" cssClass="form-control" id="cv2Div"
													onblur="this.value=this.value.trim();validCv2()"  maxlength="<%=Constants.FOUR.toString()%>"/>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="cv2ErrorDiv">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
											<legend></legend>
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.cardholdername"/><span class="required-field">*</span></label>
												<form:input path="cardHolderName" cssClass="form-control"
													id="cardHolderNameDiv"
													onblur="this.value=this.value.trim();validCardHolderName()" onkeydown="validateSpace(this)"/>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="cardHolderNameErrorDiv">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
											<legend></legend>
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.Subtotal"/><span class="required-field">*</span></label>
												<fmt:formatNumber type="number"
													value="${virtualTerminalPreAuth.subTotal}"
													pattern="<%=Constants.AMOUNT_FORMAT %>" var="subTotal" />
												<input name="subTotal" value="${subTotal}"
													class="form-control" id="subTotalDiv" onkeypress="return amountValidate(this,event)"
													onblur="this.value=this.value.trim();validAmount('subTotalDiv','subTotalErrorDiv');doAdd()" />
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="subTotalErrorDiv">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
											<legend></legend>
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.taxamount"/></label>
												<fmt:formatNumber type="number"
													value="${virtualTerminalPreAuth.taxAmt}"
													pattern="<%=Constants.AMOUNT_FORMAT %>" var="taxAmt" />
												<input name="taxAmt" class="form-control" value="${taxAmt}" onkeypress="return amountValidate(this,event)"
													id="taxAmtDiv" onblur="clientValidation('taxAmtDiv', 'fee_amount','taxAmtErrorDiv');doAdd();" />
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="taxAmtErrorDiv">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
											<legend></legend>
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.tipamount"/></label>
												<fmt:formatNumber type="number"
													value="${virtualTerminalPreAuth.tipAmount}"
													pattern="<%=Constants.AMOUNT_FORMAT %>" var="tipAmount" />
												<input name="tipAmount" value="${tipAmount}" onkeypress="return amountValidate(this,event)"
													class="form-control" id="tipAmountDiv" onblur="clientValidation('tipAmountDiv', 'fee_amount','tipAmtErrorDiv');doAdd();" />
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="tipAmtErrorDiv">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
											<legend></legend>
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.shippingcharge"/></label>
												<fmt:formatNumber type="number"
													value="${virtualTerminalPreAuth.shippingAmt}"
													pattern="<%=Constants.AMOUNT_FORMAT %>" var="shippingAmt" />
												<input name="shippingAmt" class="form-control" onkeypress="return amountValidate(this,event)"
													value="${shippingAmt}" id="shippingAmtDiv" onblur="clientValidation('shippingAmtDiv', 'fee_amount','shippingAmtErrorDiv');doAdd();" />
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="shippingAmtErrorDiv">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
											<legend></legend>
											<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.fee"/></label>
													<fmt:formatNumber type="number"
												value="${virtualTerminalPreAuth.feeAmount}"
												pattern="<%=Constants.AMOUNT_FORMAT %>" var="feeAmount" />
													<input name="feeAmount" class="form-control" onkeypress="return amountValidate(this,event)" 
														onblur="clientValidation('feeAmountDiv', 'fee_amount','feeAmountErrorDiv');doAdd();" value="${feeAmount}" id="feeAmountDiv"  />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="feeAmountErrorDiv" class="red-error">&nbsp;</span>
											</div>
										</fieldset>
											<fieldset class="col-sm-3">
											<legend></legend>
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.totalamount"/><span class="required-field">*</span></label>
												<fmt:formatNumber type="number"
													value="${virtualTerminalPreAuth.txnAmount}"
													pattern="<%=Constants.AMOUNT_FORMAT %>" var="txnAmount" />
												<input name="txnAmount" class="form-control"
													value="${txnAmount}" id="totalAmtDiv" onkeypress="return amountValidate(this,event)" 
													onblur="this.value=this.value.trim();validAmount('totalAmtDiv','totalAmtErrorDiv')" />
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="totalAmtErrorDiv">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
											<legend></legend>
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.street"/><span class="required-field">*</span></label>
												<form:input path="street" cssClass="form-control"
													id="streetDiv"
													onblur="this.value=this.value.trim();validStreet()" onkeydown="validateSpace(this)"/>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="streetErrorDiv">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
											<legend></legend>
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.city"/><span class="required-field">*</span></label>
												<form:input path="city" cssClass="form-control" id="cityDiv"
													onblur="this.value=this.value.trim();validCity()" onkeydown="validateSpace(this)"/>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="cityErrorDiv">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
											<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.state"/><span class="required-field">*</span></label>
													<form:input path="state" cssClass="form-control"
														id="stateDiv"
														onblur="this.value=this.value.trim();validState()" onkeydown="validateSpace(this)"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="stateErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
											<fieldset class="col-sm-3">
											<legend></legend>
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.zipcode"/><span class="required-field">*</span></label>
												<form:input path="zip" cssClass="form-control"
													id="zipcodeDiv"
													onkeypress ="generalZipCode()"
															maxlength="7" onblur="return zipCodeNotEmpty(id)"  />
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="zipcodeDivEr">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
											<legend></legend>
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.invoicenumber"/><span class="required-field">*</span></label>
												<form:input path="invoiceNumber" cssClass="form-control"
													id="invoiceNumberDiv"
													onblur="this.value=this.value.trim();validInvoiceNumber('invoiceNumberDiv','invoiceNumberErrorDiv')" />
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="invoiceNumberErrorDiv">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
											<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.description"/><span class="required-field">*</span></label>
													<form:textarea path="description" cssClass="form-control"
														id="descriptionDiv" 
														onblur="this.value=this.value.trim();validateDesc()" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="descriptionErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
										</div>
									</div>
									<!--Panel Action Button Start -->
									<div class="col-sm-12 form-action-buttons">
										<div class="col-sm-5"></div>
										<div class="col-sm-7">
											<input type="button" class="form-control button pull-right" value='<spring:message code="virtual-terminal-sale.label.continuebutton"/>' onclick="return openConfirmPageSaleAndPreAuth()">
											<input type="button" class="form-control button pull-right" value='<spring:message code = "virtual-terminal-sale.label.resetbutton"/>' onclick="return resetAuth()">
										</div>
									</div>
									<!--Panel Action Button End -->
								</div>
								<div id="responseDiv">
									<c:if test="${not empty txnRefNum.authId }">
										
										<br>
										<table>
										<tr>
													<td><span class="black-text">&nbsp;<spring:message code="virtual-terminal-sale.label.transactionstatus"/>:</span></td>
													<td><span class="green-error">&nbsp;: ${success}</span></td>
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
											<tr>
												<td><span class="black-text">&nbsp;<spring:message code="virtual-terminal-sale.label.cgtransactionid"/></span></td>
												<td><span class="green-error">&nbsp;
														:${txnRefNum.cgRefNumber}</span></td>
											</tr>
										</table>
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<a class="form-control button pull-right" href="virtual-pre-auth"
													><spring:message code="virtual-terminal-sale.label.backbutton"/></a>
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
																	<td><spring:message code="virtual-terminal-sale.label.cardnumber"/>:</td>
																	<td><div id="confirmCardNumberDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-sale.label.expirationdate"/>:</td>
																	<td><div id="confirmYeardropdown"></div><div id="confirmMonthdropdown"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-sale.label.cv2"/>:</td>
																	<td><div id="confirmCv2Div"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-sale.label.cardholdername"/>:</td>
																	<td><div id="confirmCardHolderNameDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-sale.label.Subtotal"/>($):</td>
																	<td><div id="confirmSubTotalDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-sale.label.taxamount"/>($):</td>
																	<td><div id="confirmTaxAmtDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-sale.label.tipamount"/>($):</td>
																	<td><div id="confirmTipAmountDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-sale.label.shippingcharge"/>($):</td>
																	<td><div id="confirmShippingAmtDiv"></div></td>
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
																	<td><spring:message code="virtual-terminal-sale.label.street"/>:</td>
																	<td><div id="confirmStreetDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-sale.label.city"/>:</td>
																	<td><div id="confirmCityDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-sale.label.state"/>:</td>
																	<td><div id="confirmStateDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-sale.label.zipcode"/>:</td>
																	<td><div id="confirmZipcodeDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-sale.label.invoicenumber"/>:</td>
																	<td><div id="confirmInvoiceNumberDiv"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="virtual-terminal-sale.label.description"/>:</td>
																	<td><div id="confirmDescriptionDiv"></div></td>
																</tr>
															</table>
														</fieldset>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
													<legend></legend>
														<input type="submit" class="form-control button pull-right pos-next" value="Process"> 
														<input type="button" class="form-control button pull-right marginL10 pos-prev" onclick="showEditPageSale()" value="Back"> 
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
	<script src="../js/validation.js"></script>
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
			
			if ("${virtualTerminalPreAuth.successDiv}" == "true") {
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

		function alertrefNum(refFlag, txnRefNum) {
			if (refFlag) {
				/* alert("Please keep this reference num:" + txnRefNum +"auth code:"+'${txnRefNum.authId}'); */
			}
		}
	</script>

	<script type="text/javascript">
		//populatedropdown(id_of_day_select, id_of_month_select, id_of_year_select)
		window.onload = function() {
			populatedropdown("monthdropdown", "yeardropdown")
		}
		$(document).ready(function() {
			highlightMainContent('navListId1');

			/*  $("#search-result-table").hide(); */
		});
	</script>
	<script src="../js/backbutton.js"></script>

</body>
</html>