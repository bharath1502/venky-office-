<!DOCTYPE html>
<%@page import="com.chatak.merchant.constants.JSPConstants"%>
<%@page import="com.chatak.pg.util.Constants"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	<link href="../css/jquery.datetimepicker.css" rel="stylesheet" type="text/css"/>
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
			<!-- Header Block End -->
			<!--Navigation Block Start -->
			<nav class="col-sm-12 nav-bar" id="main-navigation">
				<jsp:include page="navigation-panel.jsp"></jsp:include> 
			</nav>
			<!--Navigation Block Start -->  			
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">					
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="recurring-search.label.recurring"/></span>
						<span class="glyphicon glyphicon-play icon-font-size"></span>
						<span class="breadcrumb-text"><spring:message code="recurring-search.label.create"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionErrorMsg" style="text-align:center;font-size:10pt" id="errorDescDiv">
										<span class="red-error">&nbsp;${error }</span>
										<span class="green-error">&nbsp;${success}</span>
									</div>
								</div>
					<!--Success and Failure Message End-->
					<div class="tab-header-container-first">
						<a href="recurring-search"><spring:message code="recurring-search.label.search"/></a>
					</div>
					<div class="tab-header-container active-background">
						<a href="#"><spring:message code="recurring-search.label.create"/></a>
					</div>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder padding0">
						<!-- Page Menu Start -->
						<div class="col-sm-12 padding0">
							<div class="sub-nav">
								<ul>
									<li class="account-details-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="merchant-circle-tab"></span>
										</div>
										<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.primarycontactinfo"/></label>
										<div class="merchant-arrow"></div>
									</li>
									<li class="atm-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="bank-circle-tab active-circle"></span>
										</div>
										<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.paymentinformation"/></label>
										<div class="arrow-down bank-arrow"></div>
									</li>
									<li class="pos-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="final-circle-tab"></span>
										</div>
									<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.contracts"/></label>
										<div class="final-arrow"></div>
									</li>
								</ul>
							</div>
						</div>
						
						<form action="recurring-customer-previous" name="editRecurringCreateForm" method="post">
							<input type="hidden" id="getRecurringCustInfoId" name="getRecurringCustInfoId" />
							<input type="hidden" name="CSRFToken" value="${tokenval}">
						</form>
						
						<!-- Page Menu End -->
						<form:form action="recurring-payment-contact" commandName="recurringPaymentInfoDTO" name="recurringPaymentInfoDTO" method="post">
						<input type="hidden" name="CSRFToken" value="${tokenval}">
						<form:hidden path="expDt" id ="expDt"/>
						<form:hidden path="recurringCustomerInfoId"/>
						<form:hidden path="recurringPaymentInfoId"/>
									<div class="col-sm-12 paddingT20">
										<div class="row">
										<!-- Payment Information Content Start -->
											<section class="field-element-row payment-info-contnent">
												<fieldset class="col-sm-12 padding0">
													<fieldset class="col-sm-3"> 
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.creditCardType"/><span class="required-field">*</span></label>
														<form:select id="cardType" path="creditCardType" cssClass="form-control" onblur="return clientValidation('cardType', 'cardType','cardType_ErrorDiv');">
															<form:option value="">..:<spring:message code="sub-merchant-create.label.select"/>:..</form:option>
															<option value="IC">Chatak Prepaid Card</option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="cardType_ErrorDiv" class="red-error">&nbsp;</span>
														</div> 
														</fieldset>
													<fieldset class="col-sm-3"> 
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.cardnumber"/><span class="required-field">*</span></label>
														<form:input path="cardNumber" id="cardNumber" cssClass="form-control" maxlength="250" readonly="true"/>
													<!-- 	<input ype="text" id="cardNumber" name="cardNumber" class="form-control" onkeypress="return numbersonly(this,event)" onblur="return clientValidation('cardNumber', 'card_Number','cardNumber_ErrorDiv');"/> -->
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="cardNumber_ErrorDiv" class="red-error">&nbsp;</span>
														</div> 
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.expiredate"/><span class="required-field">*</span></label><br>
														<form:select path="month" cssClass="form-control widthP30 displaiIB" id="month" onblur="return validateExpDate()">
															<form:option value="">MM</form:option>
															<form:option value="01">Jan</form:option>	
															<form:option value="02">Feb</form:option>	
															<form:option value="03">Mar</form:option>	
															<form:option value="04">Apr</form:option>	
															<form:option value="05">May</form:option>	
															<form:option value="06">Jun</form:option>	
															<form:option value="07">Jul</form:option>	
															<form:option value="08">Aug</form:option>	
															<form:option value="09">Sep</form:option>	
															<form:option value="10">Oct</form:option>	
															<form:option value="11">Nov</form:option>	
															<form:option value="12">Dec</form:option>	
														</form:select>
														<form:select path="year" cssClass="form-control widthP30 displaiIB" id="year" onblur="return validateExpDate()">
															<form:option value="">YYYY</form:option>
															<form:option value="2015">2015</form:option>
															<form:option value="2016">2016</form:option>
															<form:option value="2017">2017</form:option>
															<form:option value="2018">2018</form:option>
															<form:option value="2019">2019</form:option>
															<form:option value="2020">2020</form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="cardExp_ErrorDiv" class="red-error">&nbsp;</span>
														</div> 
													</fieldset>
													<fieldset class="col-sm-3"> 
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.nameoncard"/><span class="required-field">*</span></label>
														<form:input id="nameOnCard" path="nameOnCard" maxlength="50" cssClass="form-control" onblur="return clientValidation('nameOnCard', 'card_Prog_Desc','nameOnCard_ErrorDiv');" onkeydown="validateSpace(this)"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="nameOnCard_ErrorDiv" class="red-error">&nbsp;</span>
														</div> 
													</fieldset>
													<fieldset class="col-sm-3"> 
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.streetaddress"/></label>
														<form:input path="streetAddress" cssClass="form-control" maxlength="250" onkeydown="validateSpace(this)"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error">&nbsp;</span>
														</div> 
													</fieldset>
													<fieldset class="col-sm-3"> 
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="sub-merchant-create.label.zipcode"/><span class="required-field">*</span></label>
														<form:input id="zipCode" path="zipCode" cssClass="form-control" maxlength="7"  onkeypress ="generalZipCode()" onblur="return zipCodeNotEmpty(id)"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="zipCodeEr" class="red-error">&nbsp;</span>
														</div> 
													</fieldset>
													<fieldset class="col-sm-12"> 
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.streetaddress.Wouldyouliketochargetheaccountrightawayorstoreitforlateruse"/>?</label><br> 
														<form:radiobutton path="immidiateCharge" id="No" cssClass="acc-charge-radio"  value="No"/><spring:message code="recurring-search.label.lateruseonly"/>
														<form:radiobutton path="immidiateCharge" id="Yes" cssClass="acc-charge-radio" value="Yes"/><spring:message code="recurring-search.label.chargenow"/>	
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error">&nbsp;</span>
														</div> 
													</fieldset>
													<fieldset class="col-sm-3 acc-charge-content" id="acc-charge-content"> 
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.amount"/><span class="required-field">*</span></label>
														<form:input path="immidateChargeAmount" cssClass="form-control" maxlength="<%= JSPConstants.AMOUNT.toString() %>" onkeypress="return amountValidate(this,event)" id="amount" onblur="return clientValidation('amount','amount','amount_Error_Div')"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="amount_Error_Div" class="red-error">&nbsp;</span>
														</div> 
													</fieldset>
												</fieldset>
												<input type="hidden" id="immediate" value="${immdeate}"/>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="submit" class="form-control button pull-right marginL10" value="<spring:message code="recurring-search.label.savecontinue"/>" onclick="return validateRecurringPayment()">
														<input type="button" class="form-control button pull-right marginL10" value="<spring:message code="sub-merchant-create.label.previous"/>" onclick="recurringCreatePreviousPage('${recurringPaymentInfoDTO.recurringCustomerInfoId}')">
														<input type="button" class="form-control button pull-right" value="<spring:message code="merchant-forgot-password.label.cancel"/>" onclick="return cancel()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<!-- Payment Information Content End -->
										</div>
									</div>
								</form:form>
								<!-- Page Form End -->	
							</div>
						</div>
					<!-- Content Block End -->
			</article>
			<!--Article Block End-->
		<jsp:include page="footer.jsp"></jsp:include>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->	

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="../js/recurring.js"></script>
    <script src="../js/common-lib.js"></script>
    <script src="../js/jquery.min.js"></script>
    <script src="../js/validation.js"></script>
    <script src="../js/jquery.maskedinput.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../js/bootstrap.min.js"></script> <script src="../js/utils.js"></script>	
	<script src="../js/jquery.datetimepicker.js"></script>
    <script src="../js/jquery.popupoverlay.js"></script>
	<script>	

		/* DatePicker Javascript Strat*/
		$( document ).ready(function() {
			$(".focus-field").click(function(){
				$(this).children('.effectiveDate').focus();
			});
			highlightMainContent('navListId5');
			$('.effectiveDate').datetimepicker({
				timepicker:false,
				format:'d/m/Y',
				formatDate:'Y/m/d',
			});
			showBox();
		});
		/* DatePicker Javascript End*/
		/* Sub Tab Functionality Start */
		$(".free-transactions-content, .atm-transaction-content, .pos-transaction-content").hide();
		$(".account-details-content").show();
		$(".bank-arrow").show();
		$(".merchant-arrow, .final-arrow").hide();
		
		<%-- $("#cardNumber").mask("<%=Constants.CARD_NUMBER_FORMAT%>");  --%>
	
		$('#No').on('change', function() {
			$("#acc-charge-content").hide();
		});
		$('#Yes').on('change', function() {
			$("#acc-charge-content").show();
		});
		
		/* Account Charge Radio Functionality End */
		/* Payment Information Table Functionality Start */
		$(".payment-info-contnent").show();
		$(".payment-info-table-btn").click(function(){
			$(".payment-info-contnent").show();
		});
		/* Payment Information Table Functionality End */
		/* Contract Information Table Functionality Start */
		$(".contract-info-contnent").show();
		$(".contract-table-btn").click(function(){
			$(".contract-info-contnent").show();
		});
		/* Contract Information Table Functionality End */
	</script>
	<script src="../js/backbutton.js"></script>	
  </body>  
</html>
