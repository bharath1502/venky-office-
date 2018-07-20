<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
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
			<%-- <jsp:include page="header.jsp"></jsp:include> --%>
			<!--Header Block End -->
			<!--Navigation Block Start -->
			<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block End -->
			<!--Article Block Start-->
			<article>
			<div class="col-xs-12 content-wrapper">
				<!-- Breadcrumb start -->
				<div class="breadCrumb">
					<span class="breadcrumb-text">VIRTUAL TERMINAL</span> 
					<span class="glyphicon glyphicon-play icon-font-size"></span> 
					<span class="breadcrumb-text">Pre Auth</span>
				</div>
				<!-- Breadcrumb End -->
				<!-- Tab Buttons Start -->
				<div class="tab-header-container-first">
					<a href="virtual-terminal-sale">Sale</a>
				</div>
				<div class="tab-header-container active-background">
					<a href="#">Pre Auth</a>
				</div>
				<div class="tab-header-container">
					<a href="virtual-terminal-pre-auth-completion">Pre Auth
						Lookup</a>
				</div>
				<div class="tab-header-container">
					<a href="virtual-terminal-refund">Refund</a>
				</div>
				<div class="tab-header-container">
					<a href="virtual-terminal-void">Void</a>
				</div>
				<div class="tab-header-container">
					<!-- <a href="virtual-terminal-adjust">Adjust</a> -->
					<a href="#">Adjust</a>
				</div>
				<!-- Tab Buttons End -->
				<!-- Content Block Start -->
				<div class="main-content-holder">
					<div class="row">
						<div class="col-sm-12">
							<!--Success and Failure Message Start-->
							<div class="col-xs-12">
								<div class="discriptionErrorMsg" id="errorDescDiv">
									<span class="red-error">&nbsp;${error}</span> <span
										class="green-error">&nbsp;${sucess}</span>
								</div>
							</div>
							<!--Success and Failure Message End-->
							<!-- Page Form Start -->
							<form:form action="do-virtual-terminal-preAuth"
								commandName="virtualTerminalPreAuth" id="txnForm">
								<input type="hidden" name="CSRFToken" value="${tokenval}">
								<div class="col-sm-12">
									<div class="row">
										<div class="field-element-row">
											<fieldset class="col-sm-3">
												<label data-toggle="tooltip" data-placement="top" title="">Card Number<span class="required-field">*</span></label>
												<form:input path="cardNum" cssClass="form-control"
													id="cardNumberDiv"
													onblur="this.value=this.value.trim();validCardNumber('cardNumberDiv','cardNumberErrorDiv')" maxlength="<%=Constants.CARD_NUM_MAXLEN%>"/>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="cardNumberErrorDiv">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
												<label data-toggle="tooltip" data-placement="top" title="">Expiration Date<span class="required-field">*</span></label><br>
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
												<label data-toggle="tooltip" data-placement="top" title="">CV2<span class="required-field">*</span></label>
												<form:input path="cvv" cssClass="form-control" id="cv2Div"
													onblur="this.value=this.value.trim();validCv2()"  maxlength="<%=Constants.FOUR.toString()%>"/>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="cv2ErrorDiv">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
												<label data-toggle="tooltip" data-placement="top" title="">Card Holder Name<span class="required-field">*</span></label>
												<form:input path="cardHolderName" cssClass="form-control"
													id="cardHolderNameDiv"
													onblur="this.value=this.value.trim();validCardHolderName()" />
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="cardHolderNameErrorDiv">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
												<label data-toggle="tooltip" data-placement="top" title="">Sub Total<span class="required-field">*</span></label>
												<fmt:formatNumber type="number"
													value="${virtualTerminalPreAuth.subTotal}"
													pattern="<%=Constants.AMOUNT_FORMAT %>" var="subTotal" />
												<input name="subTotal" value="${subTotal}"
													class="form-control" id="subTotalDiv"
													onblur="this.value=this.value.trim();validAmount('subTotalDiv','subTotalErrorDiv');doAdd()" />
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="subTotalErrorDiv">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
												<label data-toggle="tooltip" data-placement="top" title="">Tax Amount</label>
												<fmt:formatNumber type="number"
													value="${virtualTerminalPreAuth.taxAmt}"
													pattern="<%=Constants.AMOUNT_FORMAT %>" var="taxAmt" />
												<input name="taxAmt" class="form-control" value="${taxAmt}"
													id="taxAmtDiv" onblur="clientValidation('taxAmtDiv', 'fee_amount','taxAmtErrorDiv');doAdd();" />
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="taxAmtErrorDiv">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
												<label data-toggle="tooltip" data-placement="top" title="">Tip Amount</label>
												<fmt:formatNumber type="number"
													value="${virtualTerminalPreAuth.tipAmount}"
													pattern="<%=Constants.AMOUNT_FORMAT %>" var="tipAmount" />
												<input name="tipAmount" value="${tipAmount}"
													class="form-control" id="tipAmountDiv" onblur="clientValidation('tipAmountDiv', 'fee_amount','tipAmtErrorDiv');doAdd();" />
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="tipAmtErrorDiv">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
												<label data-toggle="tooltip" data-placement="top" title="">Shipping Charge</label>
												<fmt:formatNumber type="number"
													value="${virtualTerminalPreAuth.shippingAmt}"
													pattern="<%=Constants.AMOUNT_FORMAT %>" var="shippingAmt" />
												<input name="shippingAmt" class="form-control"
													value="${shippingAmt}" id="shippingAmtDiv" onblur="clientValidation('shippingAmtDiv', 'fee_amount','shippingAmtErrorDiv');doAdd();" />
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="shippingAmtErrorDiv">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title="">Fee</label>
															<fmt:formatNumber type="number"
														value="${virtualTeminalSale.feeAmount}"
														pattern="<%=Constants.AMOUNT_FORMAT %>" var="feeAmount" />
															<input name="feeAmount" class="form-control" onkeypress="return amountValidate(this,event)" 
																onblur="clientValidation('feeAmountDiv', 'fee_amount','feeAmountErrorDiv');doAdd();" value="${feeAmount}" id="feeAmountDiv"  />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="feeAmountErrorDiv" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
											<fieldset class="col-sm-3">
												<label data-toggle="tooltip" data-placement="top" title="">Total Amount<span class="required-field">*</span></label>
												<fmt:formatNumber type="number"
													value="${virtualTerminalPreAuth.txnAmount}"
													pattern="<%=Constants.AMOUNT_FORMAT %>" var="txnAmount" />
												<input name="txnAmount" class="form-control"
													value="${txnAmount}" id="totalAmtDiv"
													onblur="this.value=this.value.trim();validAmount('totalAmtDiv','totalAmtErrorDiv')" />
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="totalAmtErrorDiv">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
												<label data-toggle="tooltip" data-placement="top" title="">Street<span class="required-field">*</span></label>
												<form:input path="street" cssClass="form-control"
													id="streetDiv"
													onblur="this.value=this.value.trim();validStreet()" />
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="streetErrorDiv">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
												<label data-toggle="tooltip" data-placement="top" title="">City<span class="required-field">*</span></label>
												<form:input path="city" cssClass="form-control" id="cityDiv"
													onblur="this.value=this.value.trim();validCity()" />
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="cityErrorDiv">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title="">State<span class="required-field">*</span></label>
													<form:input path="state" cssClass="form-control"
														id="stateDiv"
														onblur="this.value=this.value.trim();validState()" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="stateErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
											<fieldset class="col-sm-3">
												<label data-toggle="tooltip" data-placement="top" title="">Zip Code<span class="required-field">*</span></label>
												<form:input path="zip" cssClass="form-control"
													id="zipcodeDiv"
													onblur="this.value=this.value.trim();validZipcode()" />
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="zipcodeErrorDiv">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
												<label data-toggle="tooltip" data-placement="top" title="">Invoice Number<span class="required-field">*</span></label>
												<form:input path="invoiceNumber" cssClass="form-control"
													id="invoiceNumberDiv"
													onblur="this.value=this.value.trim();validInvoiceNumber('invoiceNumberDiv','invoiceNumberErrorDiv')" />
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span class="red-error" id="invoiceNumberErrorDiv">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title="">Description<span class="required-field">*</span></label>
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
											<input type="submit" class="form-control button pull-right"
												value="Process" onclick="return validateAuth()"> <input
												type="reset" class="form-control button pull-right"
												value="Reset" onclick="return resetAuth()">
										</div>
									</div>
									<!--Panel Action Button End -->
								</div>
								<div id="responseDiv">
									<c:if test="${not empty txnRefNum.authId }">
										<span class="green-error">&nbsp;Please note below
											details </span>
										<br>
										<table>
											<tr>
												<td><span class="red-error">&nbsp;Auth Id</span></td>
												<td><span class="green-error">&nbsp;:
														${txnRefNum.authId}</span></td>
											</tr>
											<tr>
												<td><span class="red-error">&nbsp;Transaction
														reference number</span></td>
												<td><span class="green-error">&nbsp;
														:${txnRefNum.txnRefNumber}</span></td>
											</tr>
											<tr>
												<td><span class="red-error">&nbsp;PG Transaction
														reference number</span></td>
												<td><span class="green-error">&nbsp;
														:${txnRefNum.cgRefNumber}</span></td>
											</tr>
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
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/virtual-terminal.js"></script>
	<script src="../js/validation.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		$(document).ready(function() {
			$("#navListId7").addClass("active-background");
			alertrefNum('${refFlag}', '${txnRefNum.txnRefNumber}');
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
	</script>
	<script src="../js/backbutton.js"></script>

</body>
</html>