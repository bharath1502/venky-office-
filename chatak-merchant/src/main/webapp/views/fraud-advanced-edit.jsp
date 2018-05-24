<!DOCTYPE html>
<%@page import="com.chatak.merchant.constants.JSPConstants"%>
<%@page import="com.chatak.pg.util.Constants"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
</head>
<body>
	<!--Body Wrapper block Start -->	
    <div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start --> 
			<jsp:include page="header.jsp"></jsp:include>
			<!-- Header Block End -->
			<!--Navigation Block Start -->
			<nav class="col-sm-12 nav-bar" id="main-navigation">
				<%-- <jsp:include page="main-navigation.jsp"></jsp:include> --%>
				<%@include file="navigation-panel.jsp"%>
			</nav>
			<!--Navigation Block Start -->  			
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">					
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="fraud-basic.label.fraud"/></span>
						<span class="glyphicon glyphicon-play icon-font-size"></span>
						<span class="breadcrumb-text"><spring:message code="fraud-basic.label.advanced"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionErrorMsg" style="text-align:center;font-size:12pt" id="errorDescDiv">
										<span class="red-error">&nbsp;${error }</span>
										<span class="green-error">&nbsp;${success}</span>
									</div>
								</div>
					<!--Success and Failure Message End-->
					<div class="tab-header-container active-background">
						<a href="#"><spring:message code="sub-merchant-update.label.edit"/></a>
					</div>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder padding0">
						<form:form action="updateAdvancedFraud" commandName="advancedFraudDTO" name="advancedFraudDTO" method="post">
						<form:hidden path="id"/>
						<form:hidden path="merchantCode"/>
									<div class="col-sm-12 paddingT20">
										<div class="row">
										<!-- Fraud Information Content Start -->
											<section class="field-element-row payment-info-contnent">
													<fieldset class="col-sm-12 padding0">
													
														<fieldset class="col-sm-3"> 
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fraud-basic.label.filterype"/><span class="required-field">*</span></label>
															
															<form:input path="filterType" 
																		id="filterType" cssClass="form-control"
																		readonly="true" />
															
															<%-- <form:select id="filterType" path="filterType" cssClass="form-control"
																onblur="return clientValidation('filterType', 'acceptance','filterType_ErrorDiv');"
																onchange="showValidateParameters()" readonly="true">
																<form:option value="">..:Select:..</form:option>
																<form:option value="Velocity">Velocity</form:option>
																<form:option value="Threshold">Threshold</form:option>
															</form:select> --%>
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="filterType_ErrorDiv" class="red-error">&nbsp;</span>
															</div> 
														</fieldset>
														
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fraud-basic.label.filterOn"/><span class="required-field">*</span></label>
															<form:select id="filterOn" path="filterOn" cssClass="form-control"
																onblur="return clientValidation('filterOn', 'acceptance','filterOn_ErrorDiv');">
																<form:option value="">..:<spring:message code="sub-merchant-create.label.select"/>:..</form:option>
																<form:option value="CreditCardNum"><spring:message code="fraud-basic.label.creditcardnumber"/></form:option>
																<form:option value="Email"><spring:message code="fraud-basic.label.Email"/></form:option>
																<form:option value="Address"><spring:message code="fraud-basic.label.Address"/></form:option>
															</form:select>
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="filterOn_ErrorDiv" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
											
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fraud-basic.label.Duration"/><span class="required-field">*</span></label>
															<form:select id="duration" path="duration"
																cssClass="form-control"
																onblur="return clientValidation('duration', 'acceptance','duration_ErrorDiv');">
																<form:option value="">..:<spring:message code="sub-merchant-create.label.select"/>:..</form:option>
																<form:option value="6hour">6hour</form:option>
																<form:option value="12hour">12hour</form:option>
																<form:option value="1Day">1Day</form:option>
																<form:option value="2Day">2Day</form:option>
																<form:option value="1Month">1Month</form:option>
															</form:select>
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="duration_ErrorDiv" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
														
														<fieldset class="col-sm-3" id="transactionLimit1"> 
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fraud-basic.label.transactionlimit"/><span class="required-field">*</span></label>
															<form:input id="transactionLimit" path="transactionLimit" cssClass="form-control" 
															onblur="return clientValidation('transactionLimit', 'credit_amount','transactionLimit_ErrorDiv');"/>
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="transactionLimit_ErrorDiv" class="red-error">&nbsp;</span>
															</div> 
														</fieldset>
														
														<fieldset class="col-sm-3" id="maxLimit1"> 
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fraud-basic.label.maxlimit"/><span class="required-field">*</span></label>
															<form:input id="maxLimit" path="maxLimit" cssClass="form-control" 
															onblur="return clientValidation('maxLimit', 'credit_amount','maxLimit_ErrorDiv');"/>
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="maxLimit_ErrorDiv" class="red-error">&nbsp;</span>
															</div> 
														</fieldset>
														
														<fieldset class="col-sm-3">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fraud-basic.label.action"/><span class="required-field">*</span></label>
															<form:select id="action" path="action"
																cssClass="form-control"
																onblur="return clientValidation('action', 'acceptance','action_ErrorDiv');">
																<form:option value="">..:<spring:message code="sub-merchant-create.label.select"/>:..</form:option>
																<form:option value="Auth+Cap"><spring:message code="fraud-basic.label.authcap"/></form:option>
																<form:option value="Auth+Hold"><spring:message code="fraud-basic.label.authhold"/></form:option>
																<form:option value="No_Auth+Hold"><spring:message code="fraud-basic.label.noauthhold"/></form:option>
																<form:option value="Deny"><spring:message code="fraud-basic.label.deny"/></form:option>
															</form:select>
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="action_ErrorDiv" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
													
												</fieldset>
												
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<!-- <input type="button" class="form-control button pull-right atm-next" value="Continue"> -->
														<input type="submit" class="form-control button pull-right marginL10 widthP30" value="Update" onclick="return validateAdvancedFraud()">
														<input type="button" class="form-control button pull-right" value="Cancel" onclick="return cancelAdvancedFraud()">
														<!-- <input type="button" class="form-control button pull-right marginL10 atm-prev" value="Previous"> -->
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
    <script src="../js/fraud.js"></script>
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

			showValidateParameters();
			
			$(".focus-field").click(function(){
				$(this).children('.effectiveDate').focus();
			});
			highlightMainContent('navListId4');
			$('.effectiveDate').datetimepicker({
				timepicker:false,
				format:'d/m/Y',
				formatDate:'Y/m/d',
			});
		});
		/* DatePicker Javascript End*/
		/* Fraud Information Table Functionality Start */
		$(".payment-info-contnent").show();
		$(".payment-info-table-btn").click(function(){
			$(".payment-info-contnent").show();
		});
		/* Payment Information Table Functionality End */
	</script>
	<script src="../js/backbutton.js"></script>	
  </body>  
</html>