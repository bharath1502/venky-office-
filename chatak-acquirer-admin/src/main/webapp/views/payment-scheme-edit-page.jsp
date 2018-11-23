<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page  import="java.util.Calendar"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<%
  int year = Calendar.getInstance().get(Calendar.YEAR);
%>
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
					   <span class="breadcrumb-text"><spring:message code="setup.label.setup"/></span> 
					   <span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="payment.label.paymentscheme"/></span>
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="common.label.edit"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first">
						<a href="payment-scheme-search-urlpage"><spring:message code="common.label.search"/></a>
					</div>
					<div class="tab-header-container active-background">
						<a href="#"><spring:message code="common.label.edit"/></a>
					</div>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="descriptionMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error">${error}</span> <span
											class="green-error">${sucess}</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="updatePaymentScheme" modelAttribute="paymentScheme" name="paymentScheme" method="post">
								 <input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12 paddingT20">
										<div class="row">
											<!-- Account Details Content Start -->
											<section class="field-element-row account-details-content">
											<form:hidden path="id"/>
											<form:hidden path="paymentSchemeName"/>
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="payment.label.paymentname"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="paymentSchemeName" disabled="true"
															id="paymentSchemeName" maxlength="50" 
															onblur="validatePaymentSchemeName()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="paymentSchemeNameEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="payment.label.contactname"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="contactName"
															maxlength="50" id="contactName" onblur="this.value=this.value.trim();validateContactName()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="contactNameEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="payment.label.contactemail"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="contactEmail"
															maxlength="100" id="contactEmail" onblur="this.value=this.value.trim();validateUpdateContactEmail()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="contactEmailEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>													
													
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="payment.label.contactphone"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="contactPhone"
															id="contactPhone" maxlength="10" onblur="this.value=this.value.trim();validateContactPhone()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="contactPhoneEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="payment.label.rid"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="rid"
															maxlength="10" id="rid" onblur="this.value=this.value.trim();validateRid()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="ridEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="payment.label.typeofcard"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="typeOfCard"
															id="typeOfCard" onblur="validateTypeOfCard()">
															<form:option value="Contact"><spring:message code="payment.label.contact"/></form:option>
															<form:option value="Contactless"><spring:message code="payment.label.contactless"/></form:option>
															<form:option value="Magnet Stripe"><spring:message code="payment.label.magnetstripe"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="statusEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<!-- <input type="submit"
															class="form-control button pull-right acc-next"
															value="Update" onclick="return validateUpdatePaymentScheme()"> -->
															<input type="submit"
															class="form-control button pull-right acc-next"
															value='<spring:message code="common.label.update"/>' onclick="return validateUpdatePaymentScheme()"> 
															<input type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.cancel"/>' onclick="openCancelConfirmationPopup()">
															
															<!-- <input type="button" class="form-control button pull-right" 
															value="Reset" onclick="resetPaymentSchemeInfo()"> -->
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
				<div id="my_popup1" class="popup-void-refund voidResult">
					<span class="glyphicon glyphicon-remove closePopupMes"
						onclick="closeCancelConfirmationPopup()"></span>
					<div class="fw-b-fs15" style="padding: 20px;">
						<spring:message code="cancle.conformation.lable.currency" />
					</div>
					<div class="col-sm-12">

						<input type="button"
							class="form-control button pull-right margin5 close-btn"
							value="<spring:message code="bin.label.no"/>"
							onclick="closeCancelConfirmationPopup()"> <input
							type="submit" class="form-control button pull-right margin5"
							value="<spring:message code="bin.label.yes"/>"
							onclick="goToPaymentSchemeSearch()">
					</div>
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
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/chatak-ajax.js"></script>
	<script src="../js/messages.js"></script>
	<script type="text/javascript" src="../js/payment-scheme.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>	
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script>
		$(document).ready(function() {
			$("#navListId2").addClass("active-background");
			$(window).keydown(function(event) {
				if (event.keyCode == 13) {
					event.preventDefault();
					return false;
				}
			});

		});
		$('#my_popup1').popup({
			blur : false
		});
		
		/* $('.update-payement').click(function() {
			if(!validateUpdatePaymentScheme())
			{
				return false;
			}
			
		
		}); */
		
		
	</script>
</body>
</html>
