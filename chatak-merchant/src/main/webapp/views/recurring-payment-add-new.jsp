<!DOCTYPE html>
<%@page import="com.chatak.merchant.constants.JSPConstants"%>
<%@page import="com.chatak.pg.util.Constants"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Ipsidy Acquirer Merchant</title>
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css" />
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
			<jsp:include page="header.jsp"></jsp:include>
			<!-- Header Block End -->
			<!--Navigation Block Start -->
			<nav class="col-sm-12 nav-bar" id="main-navigation">
				<jsp:include page="navigation-panel.jsp"></jsp:include> 
			</nav>
			<!--Navigation Block End -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="recurring-search.label.recurring"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="sub-merchant-update.label.edit"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first">
						<a href="recurring-search"><spring:message code="recurring-search.label.search"/></a>
					</div>
					<div class="tab-header-container active-background">
						<a href="#"><spring:message code="sub-merchant-update.label.edit"/></a>
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
										</div>	<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.primarycontactinfo"/></label>
										<div class="merchant-arrow"></div>
									</li>
									<li class="atm-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="bank-circle-tab active-circle"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.paymentinformation"/></label>
										<div class="arrow-down bank-arrow"></div>
									</li>
									<li class="pos-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="final-circle-tab"></span>
										</div> <label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.contracts"/></label>
										<div class="final-arrow"></div>
									</li>
								</ul>
							</div>
						</div>
						<!-- Page Menu End -->
						<div class="row margin0">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionErrorMsg col-xs-12" style="text-align:center;font-size:10pt" id="errorDescDiv">
										<span class="red-error">&nbsp; ${error }</span> <span
											class="green-error">&nbsp;${success}</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<div id="my_popup" class="locatioin-list-popup">
								<form:form action="addNewPaymentInfo" commandName="recurringPaymentInfoDTO" method="post" name="recurringPayment" id="recurringPayment">
									<form:hidden path="expDt" id ="expDt"/>
									<form:hidden path="recurringCustomerInfoId"/>
									<!-- Payment Information Pop Up Box Information Start -->
										<span class="glyphicon glyphicon-remove"
											onclick="closePopup()"></span>
										<fieldset class="col-sm-12 padding0">
											<fieldset class="col-sm-3">
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.creditCardType"/><span class="required-field">*</span></label>
												<form:select id="cardType" path="creditCardType"
													cssClass="form-control"
													onblur="return clientValidation('cardType', 'cardType','cardType_ErrorDiv');">
													<form:option value="">..:<spring:message code="sub-merchant-create.label.select"/>:..</form:option>
													<form:option value="Visa">Visa</form:option>
													<form:option value="MC">MC</form:option>
													<form:option value="JCB">JCB</form:option>
												</form:select>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span id="cardType_ErrorDiv" class="red-error">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.cardnumber"/><span class="required-field">*</span></label>
												<input type="text" id="cardNumber" name="cardNumber" maxlength="250" class="form-control" onkeypress="return cardnumbersonly(this,event)" onblur="return clientValidation('cardNumber', 'card_Number','cardNumber_ErrorDiv');"/>
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
												<form:input id="nameOnCard" path="nameOnCard"
													cssClass="form-control" maxlength="50"
													onblur="return clientValidation('nameOnCard', 'card_Prog_Desc','nameOnCard_ErrorDiv');" onkeydown="validateSpace(this)"/>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span id="nameOnCard_ErrorDiv" class="red-error">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.streetaddress"/></label>
												<form:input path="streetAddress" maxlength="250" cssClass="form-control" onkeydown="validateSpace(this)"/>
												<div class="discriptionErrorMsg" id="streedDivId">
													<span class="red-error">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3">
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="sub-merchant-create.label.zipcode"/><span class="required-field">*</span></label>
												<form:input id="zipCode" path="zipCode"
													cssClass="form-control" onkeypress ="generalZipCode()"
												maxlength="7" onblur="return zipCodeNotEmpty(id)" />
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span id="zipCodeEr" class="red-error">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-12"> 
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.streetaddress.Wouldyouliketochargetheaccountrightawayorstoreitforlateruse"/>?<span class="required-field">*</span></label><br>
														<form:radiobutton path="immidiateCharge" id="No"  cssClass="acc-charge-radio" value="No" onblur="validateRadio('radio_err')"/><spring:message code="recurring-search.label.lateruseonly"/>
														<form:radiobutton path="immidiateCharge" id="Yes"  cssClass="acc-charge-radio" value="Yes" onblur="validateRadio('radio_err')"/><spring:message code="recurring-search.label.chargenow"/>														
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="radio_err" class="red-error">&nbsp;</span>
														</div> 
													</fieldset>
											<fieldset class="col-sm-3 acc-charge-content" id="acc-charge-content">
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.amount"/><span class="required-field">*</span></label> 
													<form:input path="immidateChargeAmount" cssClass="form-control" maxlength="<%= JSPConstants.AMOUNT.toString() %>" onkeypress="return amountValidate(this,event)" id="amount" onblur="return clientValidation('amount','amount','amount_Error_Div')" />
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
													<span id="amount_Error_Div" class="red-error">&nbsp;</span>
												</div>
											</fieldset>
										</fieldset>
										<!-- Form Button Information Start -->
										<div class="col-sm-12">
											<input type="button" class="form-control button pull-right"
												value="<spring:message code="merchant-forgot-password.label.cancel"/>" onclick="closePopup()"> <input
												type="submit"
												class="form-control button pull-right payment-info-table-btn"
												value="<spring:message code="recurring-payment-add-new.label.Save"/>" onclick="return validateRecurringPayment()">
										</div>
										<!-- Form Button Information End -->
									
									<!-- Payment Information Pop Up Box Information Start -->
								</form:form>
								</div>
								<!-- Page Form Start -->
								<div>
								<form name="contractSubmt" action="showContractAddPage" method="post">
									<input type="hidden" name="recurringCustInfoId" value="${ recurringPaymentInfoDTO.recurringCustomerInfoId}">
								 </form>
								 
								 <form action="deleteRecurringPayment" name="deleteRecurringPaymentForm" method="post">
								 	<input type="hidden" name="getPaymentInfoId" id="getPaymentInfoId" />
								 </form>
								 
								 <form action="showRecurringPaymentInfoEditPage" name="editRecurringPaymentForm" method="post">
								 	<input type="hidden" id="getRecurringPaymentInfoId" name="getRecurringPaymentInfoId">
								 	<input type="hidden" id="getRecurringCutomerInfoId" name="getRecurringCutomerInfoId">
								 </form>
								<form:form name="contract" >
									<div class="col-sm-12 paddingT20">
										<div class="row">
											<!-- Payment Information Content Start -->
											<section class="field-element-row atm-transaction-content">
												<fieldset class="col-sm-12 padding0">
													<fieldset>
														<input type="button" class="form-control button"
															value="<spring:message code="recurring-contract-info-edit.label.addnew"/>" onclick="openPopup()">
													</fieldset>
													<!-- Payment information Table Block Start -->
													<div
														class="search-results-table payment-info-table paddingLR10">
														<table
															class="table table-striped table-bordered table-condensed">
															<!-- Search Table Header Start -->
															<tr>
																<td colspan="8" class="search-table-header-column">
																	<span><spring:message code="recurring-search.label.paymentinformation"/></span>
																</td>
															</tr>
															<!-- Search Table Header End -->
															<!-- Search Table Content Start -->
															<tr>
															    <th><spring:message code="recurring-payment-add-new.label.paymenttype"/></th>
																<th><spring:message code="recurring-payment-add-new.label.accountnumber"/></th>
																<th><spring:message code="recurring-search.label.expiredate"/></th>
																<th><spring:message code="search-sub-merchant.lable.action"/></th>
															</tr>
															<c:choose>
																<c:when test="${!(fn:length(recurringPaymentList) eq 0) }">
																	<c:forEach items="${recurringPaymentList }" var="recurring">
																		<tr>
																			<td>${recurring.nameOnCard}</td>
																			<td>${recurring.cardNumber }</td>
																			<td>${recurring.expDt }</td>
																			<c:choose>
																				<c:when test="${recurring.status eq 'Active' }">
																					<td>
																						<a href="javascript:editRecurringPayment('${recurring.recurringPaymentInfoId }',${recurring.recurringCustomerInfoId })" title="Edit" class="table-actionicon-margin"><span class="glyphicon glyphicon-pencil"></span></a> 
																						<a href="javascript:confirmDeleteRecurringPayment('${recurring.recurringPaymentInfoId }')"><span class="glyphicon glyphicon-trash"></span></a>
																					</td>
																				</c:when>
																				<c:otherwise>
																					<td>
																						<spring:message code="recurring-payment-add-new.label.deleted"/>
																					</td>
																				</c:otherwise>
																			</c:choose>
																		</tr>
																	</c:forEach>
																</c:when>
																<c:otherwise>
																	<tr>
																		<td colspan="8" style="color: red;"><spring:message code="recurring-search.label.norecordfound"/></td>
																</c:otherwise>
															</c:choose>
															<!-- Search Table Content End -->
														</table>
													</div>
													<!-- Payment information Table Block End -->
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="button" class="form-control button pull-right atm-next" value="<spring:message code="sub-merchant-create.label.continue"/>" onclick="formSubmit()">
														<input type="button" class="form-control button pull-right" value="<spring:message code="merchant-forgot-password.label.cancel"/>" onclick="return cancel()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<!-- Payment Information Content End -->
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
	<script src="../js/recurring.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/jquery.min.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/jquery.maskedinput.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script> <script src="../js/utils.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	 <script src="../js/messages.js"></script>
	<script>
		/* Common Navigation Include Start */
		/* 	$(function(){
				$( "#main-navigation" ).load( "main-navigation.html" );
			});
			function highlightMainContent(){
				$( "#navListId5" ).addClass( "active-background" );
			}	 */
		/* Common Navigation Include End */
		/* DatePicker Javascript Strat*/
		$(document).ready(function() {
			$(".focus-field").click(function() {
				$(this).children('.effectiveDate').focus();
			});
			highlightMainContent('navListId5');
			$('.effectiveDate').datetimepicker({
				timepicker : false,
				format : 'd/m/Y',
				formatDate : 'Y/m/d',
			});
		});
		/* DatePicker Javascript End*/
		/* Payment Information Popup Overlay Functionality Start */
		$(document).ready(function() {
			$('#my_popup').popup({
				blur : false
			});
		});
		function closePopup() {
			 /* document.getElementById("cardType_ErrorDiv").innerHTML = "";
			 document.getElementById("cardNumber_ErrorDiv").innerHTML = "";
			 document.getElementById("cardExp_ErrorDiv").innerHTML = "";
			 document.getElementById("nameOnCard_ErrorDiv").innerHTML = "";
			 document.getElementById("streedDivId").innerHTML = "";
			 document.getElementById("zipCodeEr").innerHTML = "";
			 document.getElementById("immdediateDiv").innerHTML = "";
			 document.getElementById("amount_Error_Div").innerHTML = ""; */
			 
				$('#my_popup').find("[id$='_ErrorDiv']").each(function() {
					$(this).text('');
				});
			 
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
		/* Payment Information Popup Overlay Functionality End */
		/* Sub Tab Functionality Start */
		$(".atm-transaction-content").show();
		$(".bank-arrow").show();
		/* Sub Tab Functionality End */
		/* Account Charge Radio Functionality Start */
		/* $(".acc-charge-content").hide();
		$('.acc-charge-radio').on('change', function() {
			var id = $(this).val();
			if (id == "true") {
				$(".acc-charge-content").show();
			} else {
				$(".acc-charge-content").hide();
			}
		}); */
		$("#acc-charge-content").hide();
		console.log($('#No').val()+"Radio1 Value");
		console.log($('#Yes').val()+"Radio2 Value");
		$('#No').on('change', function() {
			$("#acc-charge-content").hide();
		});
		$('#Yes').on('change', function() {
				$("#acc-charge-content").show();
		});
		/* Account Charge Radio Functionality End */
		/* Payment Information Table Functionality Start */
		$(".payment-info-table").show();
		$(".payment-info-table-btn").click(function() {
			$(".payment-info-table").show();
		});
	 	<%--  $("#cardNumber").mask("<%=Constants.CARD_NUMBER_FORMAT%>"); --%> 
		/* Payment Information Table Functionality End */
	</script>
	<script src="../js/backbutton.js"></script>	
</body>
</html>