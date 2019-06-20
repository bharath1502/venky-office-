<!DOCTYPE html>
<%@page import="com.chatak.merchant.constants.JSPConstants"%>
<%@page import="com.chatak.pg.util.Constants"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
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
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css" />
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
				<%-- <jsp:include page="main-navigation.jsp"></jsp:include> --%>
				<%@include file="navigation-panel.jsp"%>
			</nav>
			<!--Navigation Block End -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="fraud-basic.label.fraud"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="fraud-basic.label.advanced"/></span>
					</div>
					
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="fraud-basic.label.advanced"/></a>
					</div>
					<!-- Breadcrumb End -->
					
					<!-- Content Block Start -->
					<div class="main-content-holder padding0">
						<!-- Page Menu Start -->
						
						<!-- Page Menu End -->
						<div class="row margin0">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionErrorMsg col-xs-12" style="text-align:center;font-size:12pt" id="errorDescDiv">
										<span class="red-error">&nbsp; ${error }</span> <span
											class="green-error">&nbsp;${success}</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<div id="my_popup" class="locatioin-list-popup">
								<form:form action="addNewAdvancedFraud" modelAttribute="advancedFraudDTO" method="post" name="advancedFraud" id="advancedFraud">
								<input type="hidden" name="CSRFToken" value="${tokenval}">
									<!-- Fraud Information Pop Up Box Information Start -->
										<span class="glyphicon glyphicon-remove"
											onclick="closePopup()"></span>
											
											
										<fieldset class="col-sm-12 padding0">
											<fieldset class="col-sm-3">
											<legend></legend>
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fraud-basic.label.filterype"/><span class="required-field">*</span></label>
												<form:select id="filterType" path="filterType"
													cssClass="form-control"
													onblur="return clientValidation('filterType', 'acceptance','filterType_ErrorDiv');"
													onchange="showValidateParameters()">
													<form:option value=""><spring:message code="fraud-basic.label.select"/></form:option>
													<form:option value="Velocity"><spring:message code="fraud-basic.label.velocity"/></form:option>
													<form:option value="Threshold"><spring:message code="fraud-basic.label.threshold"/></form:option>
												</form:select>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span id="filterType_ErrorDiv" class="red-error">&nbsp;</span>
												</div>
											</fieldset>
											
											<fieldset class="col-sm-3">
											<legend></legend>
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fraud-basic.label.filterOn"/><span class="required-field">*</span></label>
												<form:select id="filterOn" path="filterOn"
													cssClass="form-control"
													onblur="return clientValidation('filterOn', 'acceptance','filterOn_ErrorDiv');">
													<form:option value=""><spring:message code="fraud-basic.label.select"/></form:option>
													<form:option value="CreditCardNum"><spring:message code="fraud-basic.label.creditcardnumber"/></form:option>
													<form:option value="Email"><spring:message code="fraud-basic.label.Email"/></form:option>
													<form:option value="Address"><spring:message code="fraud-basic.label.Address"/></form:option>
												</form:select>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span id="filterOn_ErrorDiv" class="red-error">&nbsp;</span>
												</div>
											</fieldset>
											
											<fieldset class="col-sm-3">
											<legend></legend>
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fraud-basic.label.Duration"/><span class="required-field">*</span></label>
												<form:select id="duration" path="duration"
													cssClass="form-control"
													onblur="return clientValidation('duration', 'acceptance','duration_ErrorDiv');">
													<form:option value=""><spring:message code="fraud-basic.label.select"/></form:option>
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
											<legend></legend>
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fraud-basic.label.transactionlimit"/><span class="required-field">*</span></label>
												<input type="text" id="transactionLimit" name="transactionLimit" class="form-control" onkeypress="return cardnumbersonly(this,event)" onblur="return clientValidation('transactionLimit', 'credit_amount','transactionLimit_ErrorDiv');"/>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span id="transactionLimit_ErrorDiv" class="red-error">&nbsp;</span>
												</div>
											</fieldset>
											
											<fieldset class="col-sm-3" id="maxLimit1">
											<legend></legend>
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fraud-basic.label.maxlimit"/><span class="required-field">*</span></label>
												<input type="text" id="maxLimit" name="maxLimit" class="form-control" onkeypress="return cardnumbersonly(this,event)" onblur="return clientValidation('maxLimit', 'credit_amount','maxLimit_ErrorDiv');"/>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span id="maxLimit_ErrorDiv" class="red-error">&nbsp;</span>
												</div>
											</fieldset>
											
											<fieldset class="col-sm-3">
											<legend></legend>
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fraud-basic.label.action"/><span class="required-field">*</span></label>
												<form:select id="action" path="action"
													cssClass="form-control"
													onblur="return clientValidation('action', 'acceptance','action_ErrorDiv');">
													<form:option value=""><spring:message code="fraud-basic.label.select"/></form:option> /  /  / Deny
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
										
										<!-- Form Button Information Start -->
										<div class="col-sm-12">
											<input type="button" class="form-control button pull-right"
												value='<spring:message code="fraud-basic.label.cancelbutton"/>' onclick="closePopup()"> <input
												type="submit"
												class="form-control button pull-right payment-info-table-btn"
												value='<spring:message code="fraud-basic.label.savebutton"/>' onclick="return validateAdvancedFraud()">
										</div>
										<!-- Form Button Information End -->
									
									<!-- Fraud Information Pop Up Box Information Start -->
								</form:form>
								</div>
								<!-- Page Form Start -->
								<div>
								 <form action="deleteAdvancedFraud" name="deleteAdvancedFraudForm" method="post">
								 	<input type="hidden" id="getId1" name="getId">
								 	<input type="hidden" id="getMerchantCode1" name="getMerchantCode">
								 	<input type="hidden" name="CSRFToken" value="${tokenval}">
								 </form> 
								 
								 <form action="advancedFraudEditPage" name="editAdvancedFraudForm" method="post">
								 	<input type="hidden" id="getId" name="getId">
								 	<input type="hidden" id="getMerchantCode" name="getMerchantCode">
								 	<input type="hidden" name="CSRFToken" value="${tokenval}">
								 </form> 
								 
								<form:form name="contract" >
									<div class="col-sm-12 paddingT20">
										<div class="row">
											<!-- Fraud Information Content Start -->
											<section class="field-element-row atm-transaction-content">
												<fieldset class="col-sm-12 padding0">
													<fieldset>
													<legend></legend>
														<input type="button" class="form-control button"
															value='<spring:message code="fraud-basic.label.addnewadvancedfraudbutton"/>' onclick="openPopup()">
													</fieldset>
													<!-- Fraud information Table Block Start -->
													<div
														class="search-results-table payment-info-table paddingLR10">
														<table
															class="table table-striped table-bordered table-condensed">
															<!-- Search Table Header Start -->
															<tr>
																<td colspan="8" class="search-table-header-column">
																	<span><spring:message code="fraud-basic.label.allowadvancedaraud"/></span>
																</td>
															</tr>
															<!-- Search Table Header End -->
															<!-- Search Table Content Start -->
															<tr>
																<th><spring:message code="fraud-basic.label.filterype"/></th>
																<th><spring:message code="fraud-basic.label.filterOn"/></th>
																<th><spring:message code="fraud-basic.label.Duration"/></th>
																<th><spring:message code="fraud-basic.label.transactionlimit"/></th>
																<th><spring:message code="fraud-basic.label.maxlimit"/></th>
																<th><spring:message code="fraud-basic.label.action"/></th>
																<th><spring:message code="fraud-basic.label.editdelete"/></th>
															</tr>
															<c:choose>
																<c:when test="${!(fn:length(advancedFraudList) eq 0) }">
																	<c:forEach items="${advancedFraudList }" var="advanceFraud">
																		<tr>
																			<td>${advanceFraud.filterType}</td>
																			<td>${advanceFraud.filterOn }</td>
																			<td>${advanceFraud.duration }</td>
																			<td>${advanceFraud.transactionLimit }</td>
																			<td>${advanceFraud.maxLimit }</td>
																			<td>${advanceFraud.action }</td>
																			<td>
																				<a href="javascript:editAdvancedFraud('${advanceFraud.id }',${advanceFraud.merchantCode })" title="Edit" class="table-actionicon-margin"><span class="glyphicon glyphicon-pencil"></span></a> 
																				<a href="javascript:confirmDeleteAdvancedFraud('${advanceFraud.id }',${advanceFraud.merchantCode })" ><span class="glyphicon glyphicon-trash"></span></a>
																			</td>
																		</tr>
																	</c:forEach>
																</c:when>
																<c:otherwise>
																	<tr>
																		<td colspan="8" style="color: red;"><spring:message code="fraud-basic.label.advancedfrauddataisnotavailable"/></td>
																</c:otherwise>
															</c:choose>
															<!-- Search Table Content End -->
														</table>
													</div>
													<!-- Fraud information Table Block End -->
												</fieldset>
											</section>
											<!-- Fraud Information Content End -->
										</div>
									</div>
								</form:form>
								</div>
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
		$(document).ready(function() {
			$("#transactionLimit1").hide();
			$("#maxLimit1").hide();
			
			$(".focus-field").click(function() {
				$(this).children('.effectiveDate').focus();
			});
			highlightMainContent('navListId4');
			$('.effectiveDate').datetimepicker({
				timepicker : false,
				format : 'd/m/Y',
				formatDate : 'Y/m/d',
			});
		});
		/* DatePicker Javascript End*/
		/* Fraud Information Popup Overlay Functionality Start */
		$(document).ready(function() {
			$('#my_popup').popup({
				blur : false
			});
		});
		
		function closePopup() {
			 document.getElementById("filterType_ErrorDiv").innerHTML = "";
			 document.getElementById("filterOn_ErrorDiv").innerHTML = "";
			 document.getElementById("duration_ErrorDiv").innerHTML = "";
			 document.getElementById("transactionLimit_ErrorDiv").innerHTML = "";
			 document.getElementById("maxLimit_ErrorDiv").innerHTML = "";
			 document.getElementById("action_ErrorDiv").innerHTML = "";
			$("#transactionLimit1").hide();
			$("#maxLimit1").hide();
			$('#my_popup').popup("hide");
		}
		
		function openPopup() {
			 document.getElementById("errorDescDiv").innerHTML = "";
			 jQuery("#my_popup").find(':input').each(function() {
				    switch(this.type) {
				        case 'password':
				        case 'text':
				        case 'textarea':
				        case 'file':
				        case 'select-one':
				        case 'select-multiple':
				            jQuery(this).val('');
				            break;
				        case 'checkbox':
				        case 'radio':
				            this.checked = false;
				    }
				  });
			
			$('#my_popup').popup("show");
		}
		$(".atm-transaction-content").show();
		$(".payment-info-table").show();
		$(".payment-info-table-btn").click(function() {
			$(".payment-info-table").show();
		});
		/* Fraud Information Table Functionality End */
	</script>
	<script src="../js/backbutton.js"></script>	
</body>
</html>