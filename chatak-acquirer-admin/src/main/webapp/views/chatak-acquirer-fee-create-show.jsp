<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.pg.util.Constants"%>
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
</head>
<body>
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/utils.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/feeprogram.js"></script>
	<script src="../js/validation.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<!--Navigation Block Start -->
			<%-- <jsp:include page="header.jsp"></jsp:include> --%>
			<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>

				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text">Fee Code</span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text">Add Aquirer Fee</span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first active-background">
						<a href="#">Add New Aquirer Fee Code</a>
					</div>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error">&nbsp;${error }</span> <span
											class="green-error">&nbsp;${sucess }</span>
									</div>
								</div>
								<!-- Page Form Start -->
								<form:form action="chatak-acquirer-fee-create-process" modelAttribute="pgAcquirerFeeCode" method="post" >
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">

												<fieldset class="col-sm-3">
							<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fee-program-create.label.cardtype"/><span
								class="required-field">*</span></label>
							<form:select cssClass="form-control" path="acquirerName" id="acquirerFeeName" onblur="return clientValidation('acquirerFeeName', 'country','acquirerFeeNameErrorDiv');">
								<form:option value="">..:Select:..</form:option>
								<form:option value="MC"><spring:message code="fee-program-create.label.mastercard"/></form:option>
								<form:option value="VI"><spring:message code="fee-program-edit.label.visa"/></form:option>
								<form:option value="AX">American Express</form:option>
								<form:option value="DC"><spring:message code="fee-program-edit.label.discover"/></form:option>
								<%-- <form:option value="DC">Dinner's Club</form:option>
								<form:option value="PP">Paypal</form:option>
								<form:option value="JC">Japanese Credit Bureau(JCB) </form:option>
								<form:option value="BL">Bill Me Later</form:option>
								<form:option value="EC">Echeck</form:option> --%>
							</form:select>
							<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
								<span class="red-error" id="acquirerFeeNameErrorDiv">&nbsp;</span>
							</div>
						</fieldset>
												<fieldset class="col-sm-3">
								<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fee-program-edit.label.%value"/><span class="required-field">*</span></label>
								<form:input cssClass="form-control" path="percentageOfTxn" id="feePercentageOnlyAcquirer" onkeypress="return amountValidate(this,event)" onblur="return clientValidation('feePercentageOnlyAcquirer', 'amount','feePercentageOnlyAcquirerErrorDiv');"/>
								<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
									<span class="red-error" id="feePercentageOnlyAcquirerErrorDiv">&nbsp;</span>
								</div>
							</fieldset>
												<fieldset class="col-sm-3">
								<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fee-program-edit.label.flatfee"/><span class="required-field">*</span></label>
								<form:input cssClass="form-control" path="flatFee" id="flatFeeAcquirer" onkeypress="return amountValidate(this,event)" onblur="return clientValidation('flatFeeAcquirer', 'amount','flatFeeAcquirerErrorDiv');" />
								<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
									<span class="red-error" id="flatFeeAcquirerErrorDiv">&nbsp;</span>
								</div>
							</fieldset>
												<div class="col-sm-12 form-action-buttons">
								<div class="col-sm-5"></div>
								<div class="col-sm-7">
									<input type="submit" class="form-control button pull-right" value="Add" onclick="return validateAcquirerFeeCode()"> 
									<a type="button" class="form-control button pull-right" href="chatak-partner-fee-show"><spring:message code="fee-program-edit.label.cancelbutton"/></a>
								</div>
								<!-- onclick="return validateFeeAcquirerEdit()" -->
							</div>
										<!--Panel Action Button End -->
									</div>
								</form:form>
								<!-- Page Form End -->
							</div>
						</div>
					</div>
					
				</div>
			</article>
			<footer class="footer">
				<jsp:include page="footer.jsp"></jsp:include>
			</footer>
		</div>
	</div>
</body>
</html>