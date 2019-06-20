<!DOCTYPE html>


<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
			<!--Header Block End -->
			<!--Navigation Block Start -->
			<nav class="col-sm-12 nav-bar" id="main-navigation">
				<%-- <jsp:include page="main-navigation.jsp"></jsp:include> --%>
				<%@include file="navigation-panel.jsp"%>
			</nav>
			<!--Navigation Block End -->
			<!--Article Block Start-->
			<article>
				<div class="">
					<div class="col-xs-12 content-wrapper">
						<div class="breadCrumb">
							<span class="breadcrumb-text"><spring:message code="fund-transfer-eft.label.transfers"/></span> <span
								class="glyphicon glyphicon-play icon-font-size"></span> <span
								class="breadcrumb-text"><spring:message code="fund-transfer-eft.label.sendsfundsaccount"/></span>
						</div>
						<form:form modelAttribute="fundTransferDTO"
							action="eft-process-fund-transfer" method="post">
							<input type="hidden" name="CSRFToken" value="${tokenval}">

							<%-- <form:form modelAttribute="" action="chatak_merchant_myprofile"
							method="post"> --%>
							<!--  -->

							<!-- Breadcrumb start -->
							<!-- Tab Buttons End -->
							<!-- Content Block Start -->

							<div class="main-content-holder">
								<div class="row">
									<!--Success and Failure Message End-->
									<!-- Page Form Start -->
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<div class="col-sm-12">
													<!--Success and Failure Message Start-->
													<div class="col-xs-12">
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error">${error}</span> <span
																class="green-error">${success}</span>
														</div>
													</div>
												</div>
												<div class="breadCrumb">
													<span class="breadcrumb-text"><spring:message code="fund-transfer-eft.label.debitfromaccount"/></span>
												</div>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fund-transfer-eft.label.account"/>:</label>
													<form:input path="debitAccount.accountNumber"
														readonly="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="address1Er" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fund-transfer-eft.label.availablebalance"/>:</label>
													<%-- <form:input path="debitAccount.avaliableBalance" /> --%>
													<form:input cssClass="tbl-text-align-right" path="debitAccount.avaliableBalance"
														readonly="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="address1Er" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<form:hidden path="debitAccount.accountType"/>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fund-transfer-eft.label.accounttype"/>:</label>
													<form:input path="debitAccount.accountTypeValue" readonly="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="address1Er" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<div class="breadCrumb">
													<span class="breadcrumb-text"><spring:message code="fund-transfer-eft.label.credittobankaccount"/></span>
												</div>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fund-transfer-eft.label.account"/>:</label>
													<form:input path="creditAccount.accountNumber"
														readonly="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="address1Er" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fund-transfer-eft.label.bankname"/>:</label>
													<form:input path="creditAccount.bankName" readonly="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="address1Er" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fund-transfer-eft.label.address"/>:</label>
													<form:input path="creditAccount.address" readonly="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="address1Er" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fund-transfer-eft.label.city"/>:</label>
													<form:input path="creditAccount.city" readonly="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="address1Er" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fund-transfer-eft.label.state"/>:</label>
													<form:input path="creditAccount.state" readonly="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="address1Er" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<form:hidden path="creditAccount.accountType"/>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fund-transfer-eft.label.accounttype"/>:</label>
													<form:input path="creditAccount.accountTypeValue"
														readonly="true" />
													
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="address1Er" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fund-transfer-eft.label.bankroutingnumber"/>:</label>
													<form:input path="creditAccount.bankRoutingNumber"
														readonly="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="address1Er" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fund-transfer-eft.label.bankroutingnumber"/>:</label>
													<%-- <form:input path="creditAccount.accountNumber" readonly="true"/> --%>
													<input type="text"
														value='${fundTransferDTO.creditAccount.accountNumber }'
														readonly="readonly" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="address1Er" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fund-transfer-eft.label.nameonaccount"/>:</label>
													<form:input path="creditAccount.nameOnAcccount"
														readonly="true" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="address1Er" class="red-error">&nbsp;</span>
													</div>
												</fieldset>

											</div>
											<div class="col-sm-12">
												<div class="row">
													<div class="field-element-row">
														<div class="breadCrumb">
															<span class="breadcrumb-text"><spring:message code="fund-transfer-eft.label.transactiondetails"/></span>
														</div>
														<fieldset class="col-sm-3">
														<legend></legend>
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fund-transfer-eft.label.enteramttransfer"/>:<span class="required-field">*</span></label>
															<form:input  path="amountToTransfer" cssClass="tbl-text-align-right"
																onkeypress='return amountValidate(this,event)'
																onblur="return clientValidation('amountToTransfer', 'amount_new','amountToTransferEr');" />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="amountToTransferEr" class="red-error">&nbsp;</span>
															</div>
															<form:hidden path="fundTransferMode" />
															<form:hidden path="merchantCode" />
														</fieldset>

													</div>

												</div>
											</div>
										</div>
									</div>

									<!-- Page Form End -->
									<div class="col-sm-12 button-content" id="submitUpdate">
										<fieldset class="col-sm-7 pull-right">
										<legend></legend>
											<input type="submit"
												onclick="return clientValidation('amountToTransfer', 'amount_new','amountToTransferEr');"
												class="form-control button pull-right pos-next"
												value="<spring:message code="fund-transfer-eft.label.processbutton"/>">
											<div>
												<input type="button" class="form-control button pull-right"
													value="<spring:message code="fund-transfer-eft.label.cancelbutton"/>" onclick="return backTodashBoard();">
											</div>
										<a type="button"
								class="form-control button pull-right"
								href="fund-transfer-eft"><spring:message code="fund-transfer-eft.label.resetbutton"/></a></div>
										</fieldset>
									</div>
								</div>

								<!--Panel Action Button End -->
								<div class="col-sm-12 button-content" id="myEdit">
									<fieldset class="col-sm-7 pull-right">
									<legend></legend>
									</fieldset>
									<fieldset>
									<legend></legend>
										<div class="col-sm-7 pull-right"></div>
									</fieldset>
								</div>
								</form:form>
							</div>
						<%-- </form:form> --%>
					</div>
					
					</article>
				</div>
		<!--Article Block End-->
		<jsp:include page="footer.jsp"></jsp:include>
			
		</div>
		
	<!--Container block End -->
	<!--Body Wrapper block End -->

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/backbutton.js" type="text/javascript"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/changePassword.js" type="text/javascript"></script>
	<script src="../js/merchant-profile.js" type="text/javascript"></script>
	<script src="../js/jquery.min.js" type="text/javascript"></script>
	<script src="../js/bootstrap.min.js" type="text/javascript"></script> <script src="../js/utils.js"></script>
	<script src="../js/common-lib.js" type="text/javascript"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/admin-user.js" type="text/javascript"></script>
	<script src="../js/validation.js" type="text/javascript"></script>
	
	<script>
		/* Common Navigation Include Start */

		$(document).ready(function() {
			$("#navListId10").addClass("active-background");
			/* $("#main-navigation").load("main-navigation.html");
			$('#submitUpdate').hide(); */
		});
		/* $('#submitUpdate').hide(); */
		/* 	$("#navListId9").addClass("active-background"); */
		/* function highlightMainContent() {
			$("#navListId8").addClass("active-background");
		} */
		/* Common Navigation Include End */
		/* Approval drop down functionality start */
		/* 	$("#approvalid").change(function() {
				if (this.value == 2) {
					$("#approverDropDown").show();
				} else {
					$("#approverDropDown").hide();
				}
			});
			$("#approverDropDown").hide(); */
		/* $("#myEdit").click(function() {
			$("input").removeAttr('disabled');
			$('select').removeAttr('disabled');
			$('#myEdit').hide();
			$('#submitUpdate').show();
		}); */
		/* Approval drop down functionality End */
	</script>
</body>
</html>