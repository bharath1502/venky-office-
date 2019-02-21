<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
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
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
<style>
.pan-btn {
	padding: 10px
}

.head-border {
	border-bottom: 2px solid #bfbfbf;
}

.radio-btn-align {
	float: left;
	width: 17px;
}

.radio-btn-input-align {
	width: 160px;
	margin-left: 5px;
	font-size: 11px !important;
	margin-top: 5px;
}

.last-input-field {
	margin-left: -100px;
	width: 15%;
}

.add-btn-style {
	margin: 10px 30px
}

.other-fee-label {
	border-bottom: 2px solid #bfbfbf
}

.final-create-btn {
	margin-right: 20px
}

.bck-color {
	background: #dddddd;
}

.filter-content {
	margin-bottom: 20px;
}

.dollar {
	color: #373737;
	font-size: 13px;
	left: 20px;
	position: absolute;
	top: -14px;
}

.flat-dollar {
	color: #373737;
	font-size: 13px;
	left: 20px;
	position: absolute;
	top: 8px;
}
.marginML-25{
	margin-left:-25px;
	width:18.66%
}
.dynamicData
{
   margin-left: -15px;margin-right: 2px;
}
.dynamicradio
{
	padding: 0px; margin-left: -12px;
}
.padding-left_25px
{

	padding-left:25px;
	
}
</style>
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<header class="col-sm-12 all-page-header">
				<!--Header Logo Start -->
				<!-- <div class="col-sm-4">
					<img src="images/chatak_logo.jpg" height="35px" alt="Logo"/>
				</div> -->
				<!--Navigation Block Start -->
				<%-- <jsp:include page="header.jsp"></jsp:include> --%>
				<%@include file="navigation-panel.jsp"%>
				<!--Navigation Block Start -->
				<!--Header Logo End -->
			</header>
			<!--Header Block End -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="fee-program-edit.label.programs" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="fee-program-edit.label.feeprogram" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="fee-program-edit.label.edit" /></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first">
						<a href="show-fee-program-search"><spring:message code="fee-program-edit.label.searchtab" /></a>
					</div>
					<div class="tab-header-container active-background">
						<a href="#"><spring:message code="fee-program-edit.label.edittab" /></a>
					</div>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<span class="green-error" id="sucessDiv">${sucess}</span> <span
									class="red-error" id="errorDiv">${error}</span>

								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<c:set var="title">
									<spring:message code="admin.label.wildcard" />
								</c:set>
								<form:form action="updateFeeProgram" modelAttribute="feeProgramDTO" name="feeProgramDTO">
								 <input type="hidden" name="CSRFToken" value="${tokenval}">
								 <input type="hidden" name="feeProgramId" value="${feeProgramDTO.feeProgramId}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
												code="fee-program-create.label.feeprogramname" /><span
												class='required-field'>*</span></label>
												<form:input path="feeProgramName" id="feeProgramName" readonly="true"
												cssClass="form-control"
												onblur="this.value=this.value.trim();clientValidation('feeProgramName', 'company_name','feeProgramNameErr')"/>
													<div class="discriptionErrorMsg">
														<span id="feeProgramNameErr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
										<fieldset class="col-sm-3">
													<label><spring:message code="admin.panLow-panHigh.label.message"/><span
														class="required-field">*</span></label>
														<form:input path="panRange" id="panLow" readonly="true" cssClass="form-control"/>
													<div class="discriptionMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="isoError">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label data-toggle='tooltip' data-placement='top' title=''><spring:message
													code="fee-program-create.label.%value" /><span
												class='required-field'>*</span></label>
												<form:input path="feeValueList[0].feePercentage" id="feePercentage"
												onblur="this.value=this.value.trim();clientValidation('feePercentage', 'fee','feePercentageErr');validateFeePercentValue();"
												cssClass="form-control" onkeypress="return amountValidate(this, event);"/>
													<div class="discriptionErrorMsg">
														<span id="feePercentageErr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label data-toggle='tooltip' data-placement='top' title=''><spring:message
													code="fee-program-create.label.flatfee" /><span
												class='required-field'>*</span></label>
												<form:input path="feeValueList[0].flatFee" id="flatFee"
												onblur="this.value=this.value.trim();clientValidation('flatFee', 'fee','flatFeeErr')"
												onkeypress="return amountValidate(this, event);"
												cssClass="form-control"/>
													<div class="discriptionErrorMsg">
														<span id="flatFeeErr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label data-toggle='tooltip' data-placement='top' title=''><spring:message code="admin.pm.fee.share"/><span
												class='required-field'>*</span></label>
												<form:input path="pmShare" id="pmShare" onkeypress="return amountValidate(this, event);"
												maxlength="6"
												onblur="this.value=this.value.trim();clientValidation('pmShare', 'fee','pmShareErr')"
												cssClass="form-control"/>
													<div class="discriptionErrorMsg">
														<span id="pmShareErr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label data-toggle='tooltip' data-placement='top' title=''><spring:message code="admin.iso.fee.share"/><span
												class='required-field'>*</span></label>
												<form:input path="isoShare" id="isoShare" onkeypress="return amountValidate(this, event);"
												maxlength="6"
												onblur="this.value=this.value.trim();clientValidation('isoShare', 'fee','isoShareErr')"
												cssClass="form-control"/>
													<div class="discriptionErrorMsg">
														<span id="isoShareErr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>
										</div>
										<!--Panel Action Button Start -->
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit" class="form-control button pull-right"
													value="<spring:message code="fee-program-edit.label.updatebutton" />" onclick="return validateEditFeeProgram()">
												<input type="button" class="form-control button pull-right marginL10" value="<spring:message code="fee-program-edit.label.cancelbutton" />" onclick="openCancelConfirmationPopup()"/>
											</div>
										</div>

										<!--Panel Action Button End -->
									</div>
								</form:form>
								<!-- Page Form End -->
							</div>
						</div>
					</div>
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
							onclick="resetFeeSearch()">
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
	<!--Body Wrapper block End -->

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/jquery.min.js"></script>
	
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/feeprogram.js"></script>
	 <script src="../js/messages.js"></script>
	 <script src="../js/validation.js"></script>
	 <script src="../js/jquery.popupoverlay.js"></script>
	 <script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		$(document).ready(function() {
			$( "#navListId3" ).addClass( "active-background" );
		});
		$('#my_popup1').popup({
			blur : false
		});
	</script>
</body>
</html>