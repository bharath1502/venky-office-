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
<title><spring:message code="common.lable.title"/></title>
<!-- Bootstrap -->
<link rel="icon" href="../images/favicon.png" type="image/png">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script> <script src="../js/utils.js"></script>
<script src="../js/common-lib.js"></script>
<script src="../js/jquery.cookie.js"></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/jstimezonedetect/1.0.4/jstz.min.js'></script>
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

	// var monthtext=['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sept','Oct','Nov','Dec'];
	var monthtext = [ '01', '02', '03', '04', '05', '06', '07', '08', '09',
			'10', '11', '12' ];

	function populatedropdown(monthfield, yearfield) {
		var monthfield = document.getElementById(monthfield);
		var yearfield = document.getElementById(yearfield);	
		var selectedYear = "${virtualTeminalSale.year}";
		var selectedMonth = "${virtualTeminalSale.month}";
		var today = new Date()
		var monthIndex = 0;
		for (var m = 0; m < 12; m++) {
			monthfield.options[m] = new Option(monthtext[m], monthtext[m])
			if (selectedMonth == monthtext[m]) {
				monthIndex = m;
			}
		}
		
		if (selectedMonth != "") {
			monthfield.options[monthIndex] = new Option(monthtext[monthIndex], monthtext[monthIndex], false, true)
		} else {
			monthfield.options[today.getMonth()] = new Option(monthtext[today
			                                            				.getMonth()], monthtext[today.getMonth()], true, true) //select today's month	
		}
		
		var thisyear = today.getFullYear()
		var yearIndex = 0;
		for (var y = 0; y < 20; y++) {
			yearfield.options[y] = new Option(thisyear, thisyear)
			if (selectedYear == thisyear) {
				yearIndex = y;
			}
			thisyear++;
		}
		if (selectedYear != "") {
			yearfield.options[yearIndex] = new Option(selectedYear, selectedYear, false, true)
		} else {
			yearfield.options[0] = new Option(today.getFullYear(), today.getFullYear(), true, true) //select today's year	
		}
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
			<!--Header Welcome Text and Logout button End -->
			<!--Header Block End -->
			<!--Navigation Block Start -->
			<nav class="col-sm-12 nav-bar" id="main-navigation">
				<%@include file="navigation-panel.jsp"%>
			</nav>
			<!--Navigation Block End -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="virtual-terminal-sale.label.virtualterminal"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="virtual-terminal-sale.label.sale"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<c:if test="${fn:contains(existingFeatures,virtualTerminalSale)}">
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="virtual-terminal-sale.label.sale"/></a>
					</div>
					</c:if>
					<%-- <div class="tab-header-container">
						<a href="virtual-pre-auth"><spring:message code="virtual-terminal-sale.label.preauth"/></a>
					</div>
					<div class="tab-header-container">
						<a href="virtual-terminal-pre-auth-completion"><spring:message code="virtual-terminal-sale.label.preauthlookup"/></a>
					</div> --%>
					<c:if test="${fn:contains(existingFeatures,virtualTerminalRefund)}">
					<div class="tab-header-container" id="refundOption">
						<a href="virtual-terminal-refund"><spring:message code="virtual-terminal-sale.label.refund"/></a>
					</div>
				   </c:if>
					<%-- <div class="tab-header-container">
						<a href="virtual-terminal-void"><spring:message code="virtual-terminal-sale.label.void"/></a>
					</div> --%>
					<%-- <div class="tab-header-container">
						<a href="virtual-terminal-adjust"><spring:message code="virtual-terminal-sale.label.adjust"/></a>
					</div> --%>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="descriptionMsg" id="errorDescDiv">
										<span class="red-error">&nbsp;${error }</span>
										<%-- <span class="green-error">${success}</span> --%>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="do-virtual-terminal-sale"
									commandName="virtualTeminalSale" id="txnForm">
									<input type="hidden" id="timeZoneOffset" name="timeZoneOffset"/>
									<input type="hidden" id="timeZoneRegion" name="timeZoneRegion"/>
									<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12" id="hideAllFields">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.cardnumber"/><span class="required-field">*</span></label>
													<form:input path="cardNum" cssClass="form-control"
														id="cardNumberDiv" maxlength="<%=Constants.CARD_NUM_MAXLEN%>"
														onblur="this.value=this.value.trim();validCardNumber('cardNumberDiv','cardNumberErrorDiv')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="cardNumberErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.expirationdate"/><span class="required-field">*</span></label>
													<form:select path="year"
														cssClass="form-control widthP50 displaiIB"
														id="yeardropdown" onblur="validateExpiryDate()">
													</form:select>
													<form:select path="month"
														cssClass="form-control widthP30 displaiIB"
														id="monthdropdown" onblur="validateExpiryDate()" />

													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="expiryDateErr">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.cv2"/><span class="required-field">*</span></label>
													<form:input path="cvv" cssClass="form-control" id="cv2Div"
														autocomplete="off"
														onblur="this.value=this.value.trim();validCv2()"
														maxlength="<%=Constants.THREE.toString()%>" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="cv2ErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.cardholdername"/><span class="required-field">*</span></label>
													<form:input path="cardHolderName" cssClass="form-control"
														id="cardHolderNameDiv"
														onblur="this.value=this.value.trim();validCardHolderName()" onkeydown="validateSpace(this)"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="cardHolderNameErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.Subtotal"/><span class="required-field">*</span></label>
													<fmt:formatNumber type="number"
														value="${virtualTeminalSale.subTotal}"
														pattern="<%=Constants.AMOUNT_FORMAT %>" var="subTotal" />
													<input name="subTotal" value="${subTotal}" maxlength="10"
														class="form-control" id="subTotalDiv" onkeypress="return amountValidate(this,event)"
														onblur="this.value=this.value.trim();clientValidation('subTotalDiv', 'fee_amount','subTotalErrorDiv');doAdd();" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="subTotalErrorDiv">&nbsp;</span>
													</div>
												</fieldset>	
												<fieldset class="col-sm-3 taxAmount">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.taxamount"/></label>
													<fmt:formatNumber type="number"
														value="${virtualTeminalSale.taxAmt}"
														pattern="<%=Constants.AMOUNT_FORMAT %>" var="taxAmt" />
													<input name="taxAmt" class="form-control" value="${taxAmt}" onkeypress="return amountValidate(this,event)" maxlength="10"
														id="taxAmtDiv" onblur="clientValidation('taxAmtDiv', 'fee_amount','taxAmtErrorDiv');doAdd();" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="taxAmtErrorDiv">&nbsp;</span>
													</div>
												</fieldset>	
												<fieldset class="col-sm-3 tipsAmount">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.tipamount"/></label>
													<fmt:formatNumber type="number"
														value="${virtualTeminalSale.tipAmount}"
														pattern="<%=Constants.AMOUNT_FORMAT %>" var="tipAmount" />
													<input name="tipAmount" value="${tipAmount}" onkeypress="return amountValidate(this,event)" maxlength="10"
														class="form-control" id="tipAmountDiv" onblur="clientValidation('tipAmountDiv', 'fee_amount','tipAmtErrorDiv');doAdd();" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="tipAmtErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3 shipAmount">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.shippingcharge"/></label>
													<fmt:formatNumber type="number"
														value="${virtualTeminalSale.shippingAmt}"
														pattern="<%=Constants.AMOUNT_FORMAT %>" var="shippingAmt" />
													<input name="shippingAmt" class="form-control" maxlength="10"
														value="${shippingAmt}" id="shippingAmtDiv" onkeypress="return amountValidate(this,event)"
														onblur="clientValidation('shippingAmtDiv', 'fee_amount','shippingAmtErrorDiv');doAdd();" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="shippingAmtErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.totalamount"/><span class="required-field">*</span></label>
													<fmt:formatNumber type="number"
														value="${virtualTeminalSale.txnAmount}"
														pattern="<%=Constants.AMOUNT_FORMAT %>" var="txnAmount" />
													<input name="txnAmount" class="form-control alignright"
														value="${txnAmount}" id="totalAmtDiv" onkeypress="return amountValidate(this,event)" 
														onblur="this.value=this.value.trim();" readonly />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="totalAmtErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.street"/><span class="required-field">*</span></label>
													<form:input path="street" cssClass="form-control"
														id="streetDiv"
														onblur="this.value=this.value.trim();validStreet()" onkeydown="validateSpace(this)"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="streetErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.city"/><span class="required-field">*</span></label>
													<form:input path="city" cssClass="form-control"
														id="cityDiv"
														onblur="this.value=this.value.trim();validCity()" onkeydown="validateSpace(this)"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="cityErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.state"/><span class="required-field">*</span></label>
													<form:input path="state" cssClass="form-control"
														id="stateDiv"
														onblur="this.value=this.value.trim();validState()" onkeydown="validateSpace(this)"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="stateErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.zipcode"/><span class="required-field">*</span></label>
													<form:input path="zip" cssClass="form-control"
														id="zipcodeDiv"
															maxlength="6" onblur="this.value=this.value.trim(),validZipcode();" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="zipcodeErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.invoicenumber"/><span class="required-field">*</span></label>
													<form:input path="invoiceNumber" cssClass="form-control"
														id="invoiceNumberDiv" maxlength="16"
														onblur="this.value=this.value.trim();validInvoiceNumber('invoiceNumberDiv','invoiceNumberErrorDiv')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="invoiceNumberErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="virtual-terminal-sale.label.description"/></label>
													<form:textarea path="description" cssClass="form-control"
														id="descriptionDiv" />
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
												<input type="button" class="form-control button pull-right" value='<spring:message code ="virtual-terminal-sale.label.continuebutton"/>' onclick="return openConfirmPageSaleAndPreAuth()"> 
												<input type="button" class="form-control button pull-right" value='<spring:message code = "virtual-terminal-sale.label.resetbutton"/>' onclick="return resetAuth('monthdropdown', 'yeardropdown');">
											</div>
										</div>
										<!--Panel Action Button End -->
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
													<td><span class="green-error">&nbsp;
													: ${sucess}</span></td>
												</tr>
												
												<tr>
												<td><span class="black-text">&nbsp;<spring:message code="reportFileExportUtil.total.amount"/>
															</span></td>
													<td><span class="green-error">&nbsp;
															: ${txnRefNum.totalTxnAmount}</span></td>
												</tr>
												<tr>
													<td><span class="black-text">&nbsp;<spring:message code="virtual-terminal-sale.label.authid"/></span></td>
													<td><span class="green-error">&nbsp; 
															 : ${txnRefNum.authId}</span></td>
												</tr>
												<tr>
													<td><span class="black-text">&nbsp;<spring:message code="virtual-terminal-sale.label.transactionid"/></span></td>
													<td><span class="green-error">&nbsp;
															: ${txnRefNum.txnRefNumber}</span></td>
												</tr>
												<tr>
													<td><span class="black-text">&nbsp;<spring:message code="virtual-terminal-sale.label.cgtransactionid"/></span></td>
													<td><span class="green-error">&nbsp;
															: ${txnRefNum.cgRefNumber}</span></td>
												</tr>
											</table>
											<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<a class="form-control button pull-right" href="virtual-terminal-sale"
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
																	<td><spring:message code="virtual-terminal-sale.label.Subtotal"/></td>
																	<td><div id="confirmSubTotalDiv"></div></td>
																</tr>
															
																<tr>
																	<td><spring:message code="virtual-terminal-sale.label.totalamount"/></td>
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
														<input type="submit" class="form-control button pull-right pos-next" value='<spring:message code ="virtual-terminal-sale.label.processbutton"/>'> 
														<input type="button" class="form-control button pull-right marginL10 pos-prev" onclick="showEditPageSale()" value='<spring:message code ="virtual-terminal-sale.label.backbutton"/>'> 
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
	<script src="../js/messages.js"></script>
	<script src="../js/virtual-terminal.js"></script>  
	<script src="../js/validation.js"></script>
	<script src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
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
			if ("${virtualTeminalSale.successDiv}" == "true") {
				$("#hideAllFields").hide();
			}

		    if ("${pgmerchantconfig.getTaxAmount()}" != 1) {
				$('.taxAmount').hide();
			} else
				$('.taxAmount').show();
		    
			if ("${pgmerchantconfig.getTipAmount()}" != 1) {
				$('.tipsAmount').hide();
			} else
				$('.tipsAmount').show();

			if ("${pgmerchantconfig.getShippingAmount()}" != 1) {
				$('.shipAmount').hide();
			} else
				$('.shipAmount').show();
		
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

		function alertrefNum(refFlag, txnRefNum) {
			if (refFlag) {
				/* alert("Please keep this reference num:" + txnRefNum +"\n auth code:"+'${txnRefNum.authId}'); */
			}
		}

		/* Approval drop down functionality End */
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
