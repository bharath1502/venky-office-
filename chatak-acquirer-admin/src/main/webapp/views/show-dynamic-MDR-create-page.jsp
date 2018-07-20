<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Chatak Acquirer Admin</title>
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css" />
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->

			<!--Header Block End -->
			<!--Navigation Block Start -->
			<nav class="col-sm-12 nav-bar" id="main-navigation">
			<%@include file="navigation-panel.jsp"%>
			<%-- <jsp:include
					page="header.jsp" /> --%></nav>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="show-dynamic-MDR-create-page.label.setup"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span>
						<span class="breadcrumb-text"><spring:message code="show-dynamic-MDR-create-page.label.dynamicmdr"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="show-dynamic-MDR-create-page.label.create"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<c:if test="${fn:contains(existingFeatures,dynamicMDREdit)}">
					<div class="tab-header-container-first">
						<a href="show-dynamic-MDR-search"><spring:message code="show-dynamic-MDR-create-page.label.search"/>
						</a>
					</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,dynamicMDRCreate)}">
					<div class="tab-header-container active-background">
						<a href="#"><spring:message code="show-dynamic-MDR-create-page.label.create"/></a>
					</div>
					</c:if>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error">${error}</span> <span
											class="green-error">${sucess}</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<form:form action="process-dynamic-mdr-create" commandName="dynamicMDRDTO" name="dynamicMDRDTO">
								 <input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12 paddingT20">
										<div class="row">
											<!-- Account Details Content Start -->
											<section class="field-element-row account-details-content">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-dynamic-MDR-create-page.label.binnumber"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="binNumber"
															id="binNumber" maxlength="9"
															onblur="this.value=this.value.trim();validateMDRBinNumber()" onkeypress="return numbersonly(this,event)"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="binNumberEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													
													<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-dynamic-MDR-create-page.label.paymentscheme"/>
													<span class="required-field">*</span></label><br>
													<form:select path="paymentSchemeName" id="paymentSchemeName"
														cssClass="form-control" onblur="validatePaymentScheme()">
														<form:option value=""><spring:message code="show-dynamic-MDR-create-page.label.select"/></form:option>
															<c:forEach items="${searchPaymentRequestList}" var="paymentScheme">
															<form:option value="${paymentScheme.paymentSchemeName}">${paymentScheme.paymentSchemeName}</form:option>
														</c:forEach>
														</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="paymentSchemeNameErr"  class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-dynamic-MDR-create-page.label.bankname"/>
													<span class="required-field">*</span></label><br>
													<form:select path="bankName" id="bankName"
														cssClass="form-control" onblur="validateBank()">
														<form:option value=""><spring:message code="show-dynamic-MDR-create-page.label.select"/></form:option>
															<c:forEach items="${bankResponse}" var="bank">
															<form:option value="${bank.bankName}">${bank.bankName}</form:option>
														</c:forEach>
														</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="bankErr"  class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-dynamic-MDR-create-page.label.producttype"/>
													<span class="required-field">*</span></label><br>
													<form:select path="productType" id="productType"
														cssClass="form-control" onblur="validateProduct()">
														<form:option value=""><spring:message code="show-dynamic-MDR-create-page.label.select"/></form:option>
															<form:option value="classic"><spring:message code="show-dynamic-MDR-create-page.label.classic"/></form:option>
															<form:option value="gold"><spring:message  code="show-dynamic-MDR-create-page.label.gold"/></form:option>
															<form:option value="platinum"><spring:message code="show-dynamic-MDR-create-page.label.platinum"/></form:option>
															<form:option value="signature"><spring:message  code="show-dynamic-MDR-create-page.label.signature"/></form:option>
														</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="productTypeErr"  class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-dynamic-MDR-create-page.label.accounttype"/><span class="required-field">*</span></label><br>
													<input type="radio" id="accountType"
														   name="accountType" value="credit" onclick="validateRadio1()"><spring:message code="show-dynamic-MDR-create-page.label.credit"/>
														   
													<input type="radio" id="noAccountType"
														   name="accountType" value="debit" onclick="validateRadio1()"><spring:message code="show-dynamic-MDR-create-page.label.debit"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="accountTypeErr" class="red-error">&nbsp;</span>
													</div>
													</fieldset>
												
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-dynamic-MDR-create-page.label.transactiontype"/><span class="required-field">*</span></label><br>
													<input type="radio" id="transactionType"
														   name="transactionType" value="onus" onclick="validateRadio2()"><spring:message code="show-dynamic-MDR-create-page.label.onus"/>
														   
													<input type="radio" id="noTransactionType"
														   name="transactionType" value="offus" onclick="validateRadio2()"><spring:message code="show-dynamic-MDR-create-page.label.offus"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="transactionTypeErr" class="red-error">&nbsp;</span>
													</div>
													</fieldset>
													
													<fieldset class="col-sm-3">
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-dynamic-MDR-create-page.label.slabfee(%)"/><span class='required-field'>*</span></label> 
												<form:input path="slab" id="slab" cssClass="form-control slab" onblur="this.value=this.value.trim();validateSlab()"/>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="slabEr" class="red-error">&nbsp;</span>
														</div>
											</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="submit"
															class="form-control button pull-right acc-next"
															value='<spring:message code ="show-dynamic-MDR-create-page.label.createbutton"/>' onclick="return validateMDRBinCreate()"> 
															<input type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code ="show-dynamic-MDR-create-page.label.resetbutton"/>' onclick="resetMDRInfo()">
															<input 
															type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code ="show-dynamic-MDR-create-page.label.cancelbutton"/>' onclick="resetDynamicMDRBinCreate()"> 
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<!-- Account Details Content End -->
											</div>
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
	
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/chatak-ajax.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/mdrdynamicbin.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	</body>
	<script type="text/javascript">
	
	$(document).ready(function() {
		$("#navListId2").addClass("active-background");
	});
	
	function resetDynamicMDRBinCreate(){
		 window.location.href = 'show-dynamic-MDR-search';
	}
	$(document).ready(function() {
		loadRadio('${dynamicMDRDTO.accountType}');
		loadRadioforMDR('${dynamicMDRDTO.transactionType}');
		
	});
	</script>
	</html>