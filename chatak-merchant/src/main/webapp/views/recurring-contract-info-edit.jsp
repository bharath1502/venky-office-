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
<link href="../css/rome.css" rel="stylesheet">
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
						<span class="breadcrumb-text"><spring:message code="sub-merchant-update.label.edit"/></span>
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
										</div>
										<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.primarycontactinfo"/></label>
										<div class="merchant-arrow"></div>
									</li>
									<li class="atm-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="bank-circle-tab"></span>
										</div>
										<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.paymentinformation"/></label>
										<div class="bank-arrow"></div>
									</li>
									<li class="pos-transactions-list">
										<div class="circle-div">
											<div class="hr"></div>
											<span class="final-circle-tab active-circle"></span>
										</div>
										<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.contracts"/></label>
										<div class="arrow-down final-arrow"></div>
									</li>
								</ul>
							</div>
						</div>
						<!-- Page Menu End -->
						<form:form action="updateRecurringContractInfo" modelAttribute="recurringContractInfoDTO" name="recurringContractInfoDTO" method="post" >
						<input type="hidden" name="CSRFToken" value="${tokenval}">
						<form:hidden path="recurringpaymentInfoId"/>
									<div class="col-sm-12 paddingT20">
										<div class="row">
									<!-- Contract Information Content Start -->
									<form:hidden path="recurringContractInfoId"/>
									<form:hidden path="lastBillDate"/>
									<form:hidden path="recurringpaymentInfoId"/>
											<section class="field-element-row contract-info-contnent">
												<fieldset class="col-sm-12 padding0">
													<fieldset class="col-sm-3"> 
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-contract-add-new.label.contractid"/><span class="required-field">*</span></label>
															<form:input path="contractId" cssClass="form-control" disabled="true" />														
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="contractId_ErrorDiv" class="red-error">&nbsp;</span>
														</div> 
													</fieldset> 
													<fieldset class="col-sm-3"> 
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-contract-create.label.contractname"/><span class="required-field">*</span></label>
														<form:input id="contractName" path="contractName" maxlength="50" cssClass="form-control" onblur="this.value=this.value.trim();return clientValidation('contractName', 'contractName','contractName_ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="contractName_ErrorDiv" class="red-error">&nbsp;</span>
														</div> 
													</fieldset>
													<fieldset class="col-sm-3"> 
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-contract-create.label.startdate"/><span class="required-field">*</span></label>
														<div class="input-group focus-field">
															<form:input id="startDate" path="startDate" cssClass="form-control effectiveDate" onblur="clientValidation('startDate', 'startDate','startDate_ErrorDiv');validateFromDate('startDate','startDate_ErrorDiv');"/>
															<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
														</div>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="startDate_ErrorDiv" class="red-error">&nbsp;</span>
														</div> 
													</fieldset>
													<fieldset class="col-sm-3"> 
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-contract-create.label.enddate"/><span class="required-field">*</span></label>
														<div class="input-group focus-field">
															<form:input id="endDate" path="endDate" cssClass="form-control effectiveDate"  onblur="clientValidation('endDate', 'endDate','endDate_ErrorDiv');validateToDate('endDate','endDate_ErrorDiv')"/>
															<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
														</div>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="endDate_ErrorDiv"  class="red-error 12">&nbsp;</span>
														</div> 
													</fieldset>
													<fieldset class="col-sm-3"> 
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-contract-create.label.contractamount"/><span class="required-field">*</span></label>
														<form:input path="contractAmount" cssClass="form-control" onkeypress="return amountValidate(this,event)" id="amount" onblur="this.value=this.value.trim();return clientValidation('amount', 'amount','contractAmount_ErrorDiv');"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="contractAmount_ErrorDiv" class="red-error">&nbsp;</span>
														</div> 
													</fieldset>
													<fieldset class="col-sm-3"> 
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-contract-create.label.tax"/><span class="required-field">*</span></label>
														<form:input path="tax" id="tax" cssClass="form-control" onkeypress="return amountValidate(this,event)"  maxlength="<%= JSPConstants.AMOUNT.toString() %>" onblur="this.value=this.value.trim();setTotal();return clientValidation('tax', 'tax','tax_ErrorDiv');"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="tax_ErrorDiv" class="red-error">&nbsp;</span>
														</div> 
													</fieldset>
													<fieldset class="col-sm-3"> 
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-contract-create.label.subtotal"/></label>
														<input type="text" name="total" class="form-control" id="total" readonly="readonly">
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error">&nbsp;</span>
														</div> 
													</fieldset>
													<fieldset class="col-sm-3"> 
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="search-sub-merchant.label.status"/><span class="required-field">*</span></label>
														<form:select id="status" path="status" cssClass="form-control" onblur="return clientValidation('status', 'status','status_ErrorDiv');" >
															<<form:option value="">..:<spring:message code="sub-merchant-create.label.select"/>:..</form:option>
															<form:option value="Active"><spring:message code="search-sub-merchant.lable.active"/></form:option>
															<form:option value="Inactive"><spring:message code="recurring-search.label.inactive"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="status_ErrorDiv" class="red-error">&nbsp;</span>
														</div> 
													</fieldset>
													<fieldset class="col-sm-3"> 
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-contract-create.label.executethiscontract"/><span class="required-field">*</span></label>
														<form:select path="contractExecution" id="contractExecution" onblur="return validateContractExecution();" >
															<form:option value="">..:<spring:message code="sub-merchant-create.label.select"/>:..</form:option>
															<form:option value="Daily"><spring:message code="recurring-contract-create.label.daily"/></form:option>
															<form:option value="Weekly"><spring:message code="recurring-contract-create.label.weekly"/></form:option>
															<form:option value="Bi-Weekly"><spring:message code="recurring-contract-create.label.bi-weekly"/></form:option>
															<form:option value="Monthly"><spring:message code="recurring-contract-create.label.monthly"/></form:option>
															<form:option value="Quarterly"><spring:message code="recurring-contract-create.label.quarterly"/></form:option>
															<form:option value="Annually"><spring:message code="recurring-contract-create.label.annually"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="contractExecutionEr" class="red-error">&nbsp;</span>
														</div> 
													</fieldset>
												<fieldset class="col-sm-3"> 
														<label data-toggle="tooltip" data-placement="top" title=""> <spring:message code="recurring-contract-create.label.reprocessthiscontract"/> <span class="required-field">*</span></label>
														<form:input path="contractExecutionReprocess"  id="contractExecutionReprocess"  cssClass="form-control" onblur="this.value=this.value.trim();return validateContractExecutionReprocess()" maxlength="4" onkeypress="return numbersonly(this,event)"/>
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="contractExecutionReprocessEr" class="red-error">&nbsp;</span>
														</div> 
													</fieldset>
													<fieldset class="col-sm-6"> 
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-contract-create.label.Whichaccountshouldbebilledforthiscontract"/>?<span class="required-field">*</span></label>
															<form:select path="recurringpaymentInfoId" cssClass="form-control" id="recurringPaymentId" onblur="this.value=this.value.trim();return clientValidation('recurringPaymentId', 'status','recurringPaymentId_ErrorDiv');" >
																<form:option value="">..:<spring:message code="sub-merchant-create.label.select"/>..:</form:option>
																<c:choose>
																	<c:when test="${paymentList ne null }">
																		<c:forEach items="${paymentList}" var="payment">
																			<form:option value="${payment.recurringPaymentInfoId }">${payment.nameOnCard }</form:option>
																		</c:forEach>
																	</c:when>
																	<c:otherwise>
																	</c:otherwise>
																</c:choose>
															</form:select>														
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="recurringPaymentId_ErrorDiv" class="red-error">&nbsp;</span>
														</div> 
													</fieldset>
													<fieldset class="col-sm-12"> 
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-contract-create.label.emailconformationtractionapproved"/>?</label><br>
														<input type="radio" name="transactionApprovedEmail" value="Yes"  checked="checked" /><spring:message code="configurations.label.yes"/>
														<input type="radio" name="transactionApprovedEmail" value="No"/>No
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error">&nbsp;</span>
														</div> 
													</fieldset>
													<fieldset class="col-sm-12"> 
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-contract-create.label.emailconformationtractiondeclined"/>?</label><br>
														<input type="radio" name="transactionDeclinedEmail" value="Yes" checked="checked" /> <spring:message code="configurations.label.yes"/>
														<input type="radio" name="transactionDeclinedEmail" value="No" > No
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error">&nbsp;</span>
														</div> 
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="submit" class="form-control button pull-right pos-next" value=<spring:message code="recurring-contract-info-edit.label.Update"/> onclick="return validateRecurringContract()">
														<input type="button" class="form-control button pull-right" value="<spring:message code="merchant-forgot-password.label.cancel"/>" onclick="return cancel()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<!-- Contract Information Content End -->
										</div>
										</div>
									</form:form>
								<!-- Page Form End -->	
								<!-- Page Form End -->	
							</div>
						</div>
						</article>
					</div>
					<!-- Content Block End -->
			<jsp:include page="footer.jsp"></jsp:include>
		</div>
		<!--Container block End -->
	   <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="../js/jquery.min.js"></script>
    <script src="../js/common-lib.js"></script>
    <script src="../js/validation.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../js/bootstrap.min.js"></script> <script src="../js/utils.js"></script>	
	 <script src="../js/rome.js"></script>
    <script src="../js/jquery.popupoverlay.js"></script>
    <script src="../js/recurring.js"></script>
    <script src="../js/jquery.maskedinput.js"></script>
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
		$( document ).ready(function() {
			$(".focus-field").click(function(){
				$(this).children('.effectiveDate').focus();
			});
			
			highlightMainContent('navListId5');
			rome(startDate, { time: false });
			rome(endDate, { time: false });
			/* $('.effectiveDate').datetimepicker({
				timepicker:false,
				format:'m/d/Y',
				formatDate:'Y/m/d',
			}); */
			
			setTotal();
		});
		/* DatePicker Javascript End*/
		/* Sub Tab Functionality Start */
		$(".free-transactions-content, .atm-transaction-content, .pos-transaction-content").hide();
		$(".account-details-content").show();
		$(".final-arrow").show();
		$(".merchant-arrow, .bank-arrow").hide()
		$(".account-details-list, .free-prev").click(function(){
	   	$(".merchant-circle-tab").addClass("active-circle");
			$(".bank-circle-tab, .final-circle-tab").removeClass("active-circle");
			
			$(".merchant-arrow").show();
			$(".bank-arrow, .final-arrow").hide()
			
			$(".account-details-content").show();
			$(".atm-transaction-content, .pos-transaction-content, .free-transactions-content").hide();
		});
		
		/* Sub Tab Functionality End */
		/* Account Charge Radio Functionality Start */
		$(".acc-charge-content").hide();
		$('.acc-charge-radio').on('change', function() {
		   var id=$(this).val();
			if(id=="Charge Now"){
				$(".acc-charge-content").show();
			}
			else {
				$(".acc-charge-content").hide();
			}
		});
		/* Account Charge Radio Functionality End */
		/* Payment Information Table Functionality Start */
		$(".payment-info-contnent").show();
		$(".payment-info-table-btn").click(function(){
			$(".payment-info-contnent").show();
		});
		/* Payment Information Table Functionality End */
		/* Contract Information Table Functionality Start */
		$(".contract-table").hide();
		$(".contract-table-btn").click(function(){
			$(".contract-table").show();
		});
		/* Contract Information Table Functionality End */
	</script>
	<script src="../js/backbutton.js"></script>	
  </body>  
</html>
