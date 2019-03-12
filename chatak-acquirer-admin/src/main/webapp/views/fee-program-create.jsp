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
<title><spring:message code="common.lable.title" /></title>
<!-- Bootstrap -->
<link rel="icon" href="../images/favicon.png" type="image/png">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<script src="../js/feeprogram.js"></script>
<script src="../js/validation.js"></script>
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

.marginML-25 {
	margin-left: -25px;
	width: 18.66%
}

.dynamicData {
	margin-left: -15px;
	margin-right: 2px;
}

.dynamicradio {
	padding: 0px;
	margin-left: -12px;
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
						<span class="breadcrumb-text"><spring:message
								code="fee-program-create.label.programs" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message
								code="fee-program-create.label.feeprogram" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message
								code="fee-program-create.label.create" /></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<c:if test="${fn:contains(existingFeatures,feeProgramsEdit)||fn:contains(existingFeatures,feeProgramsCreate)||fn:contains(existingFeatures,feeProgramsView)||fn:contains(existingFeatures,feeProgramsDelete)}">
						<div class="tab-header-container-first">
							<a href="show-fee-program-search"><spring:message
									code="fee-program-create.label.searchtab" /></a>
						</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,feeProgramsCreate)}">
						<div class="tab-header-container active-background">
							<a href="#"><spring:message
									code="fee-program-create.label.create" /></a>
						</div>
					</c:if>
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
								<form:form action="feeProgramCreate" modelAttribute="feeProgramDTO" name="feeProgramDTO">
								 <input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
												code="fee-program-create.label.feeprogramname" /><span
												class='required-field'>*</span></label>
													<input type="text" name="feeProgramName" id="feeProgramName"
												class="form-control"
												onblur="this.value=this.value.trim(); validateFeePrgmName()">
													<div class="discriptionErrorMsg">
														<span id="feeProgramNameErr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
										<fieldset class="col-sm-3">
													<label><spring:message code="fee-report.label.pm.name"/><span
														class="required-field">*</span></label>
													<form:select id="programManagerId" path="programManagerId" onclick="validatePM()" onchange="getIso(this.value)"
														cssClass="form-control" onblur="clientValidation('programManagerId','partner_name_dropdown','pmError')" >
														<form:option value=""><spring:message code="fee-report.label.select"/></form:option>
														<c:if test="${not empty cardProgramList}">
															<c:forEach items="${cardProgramList}" var="programManager">
																			<form:option value="${programManager.id}" >${programManager.programManagerName}</form:option>
															</c:forEach>
														</c:if>
													</form:select>
													<div class="discriptionMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="pmError">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label><spring:message code="admin.iso.label.message"/><span
														class="required-field">*</span></label>
													<form:select id="isoId" path="isoId" onclick="validateISO()" onchange="getPan(this.value)"
														cssClass="form-control" onblur="clientValidation('isoId','partner_name_dropdown','isoError')" >
														<form:option value=""><spring:message code="fee-report.label.select"/></form:option>
														<c:if test="${not empty isoRequestsList}">
															<c:forEach items="${isoRequestsList}" var="iso">
																			<form:option value="${iso.id}" >${iso.isoName}</form:option>
															</c:forEach>
														</c:if>
													</form:select>
													<div class="discriptionMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="isoError">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label><spring:message code="admin.panLow-panHigh.label.message"/><span
														class="required-field">*</span></label>
													<form:select id="panLow" path="panId"
														cssClass="form-control" onblur="clientValidation('panLow','partner_name_dropdown','panError')">
														<form:option value=""><spring:message code="fee-report.label.select"/></form:option>
														<c:if test="${not empty panRequestsList}">
															<c:forEach items="${panRequestsList}" var="pan">
																			<form:option value="${pan.isoId}" >${pan.panHigh}</form:option>
															</c:forEach>
														</c:if>
													</form:select>
													<div class="discriptionMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="panError">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6 clear-Both">
													<label data-toggle='tooltip' data-placement='top' title=''><spring:message
													code="fee-program-create.label.%value" /><span
												class='required-field'>*</span></label>
													<input type="text" name="feeValueList[0].feePercentage"
												id="feePercentage" onkeypress="return amountValidate(this, event);"
												class="form-control feePercentage"
												onblur="this.value=this.value.trim();clientValidation('feePercentage', 'fee','feePercentageErr');validateFeePercentValue();"
												class="form-control">
													<div class="discriptionErrorMsg">
														<span id="feePercentageErr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label data-toggle='tooltip' data-placement='top' title=''><spring:message
													code="fee-program-create.label.flatfee" /><span
												class='required-field'>*</span></label>
													<input type="text" name="feeValueList[0].flatFee"
												id="flatFee" onkeypress="return amountValidate(this, event);"
												class="form-control"
												onblur="this.value=this.value.trim();clientValidation('flatFee', 'fee','flatFeeErr')"
												class="form-control">
													<div class="discriptionErrorMsg">
														<span id="flatFeeErr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label data-toggle='tooltip' data-placement='top' title=''><spring:message code="admin.pm.fee.share"/><span
												class='required-field'>*</span></label>
													<input type="text" name="pmShare" maxlength="6"
												id="pmShare" onkeypress="return amountValidate(this, event);"
												class="form-control"
												onblur="this.value=this.value.trim();clientValidation('pmShare', 'fee','pmShareErr')"
												class="form-control">
													<div class="discriptionErrorMsg">
														<span id="pmShareErr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label data-toggle='tooltip' data-placement='top' title=''><spring:message code="admin.iso.fee.share"/><span
												class='required-field'>*</span></label>
													<input type="text" name="isoShare" maxlength="6"
												id="isoShare" onkeypress="return amountValidate(this, event);"
												class="form-control"
												onblur="this.value=this.value.trim();clientValidation('isoShare', 'fee','isoShareErr')"
												class="form-control">
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
													value="<spring:message code="fee-program-create.label.createbutton" />" id="createFeePgm" onclick="return validateFeeProgram()">
												<a href="show-fee-program-create"
													class="form-control button pull-right"><spring:message
														code="common.label.reset" /></a>
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
	<script src="../js/messages.js"></script>
	<script src="../js/feeprogram.js"></script>
	<script src="../js/validation.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		$(document).ready(function() {
			$( "#navListId3" ).addClass( "active-background" );
		});
		
		function validatePM(){ 
			var pm = $('#programManagerId').val();
			if(pm == '' || pm == null){
				setDiv('pmError',webMessages.SELECT_PROGRAM_MANAGER);
				return false;
			}
			setDiv('pmError','');
			return true;
		}
		
		function getIso(programManagerId) {
			 
			// Remove previous options from the dropdown
			document.getElementById("isoId").options.length = 0;

			// Create 'Select' option
			var selectOption = document.createElement("option");
			selectOption.innerHTML = webMessages.Select;
			selectOption.value = "";
			$("#isoId").append(selectOption);
			doAjaxToGetIso(programManagerId);
		}

		function doAjaxToGetIso(programManagerId) {
			$
					.ajax({
						type : "GET",
						url : "getPartnersEntites?programManagerId=" + programManagerId,
						success : function(response) {
							// we have the response

							var obj = JSON.parse(response);

							// Remove previous options from the dropdown
							document.getElementById("isoId").options.length = 0;

							// Create 'Select' option
							var selectOption = document.createElement("option");
							selectOption.innerHTML = webMessages.Select;
							selectOption.value = "";
							$("#isoId").append(selectOption);

							if (obj.errorMessage == "sucess") {

								var data = obj.isoRequest;
								for (var i = 0; i < data.length; i++) {
									var newOption = document
											.createElement("option");
									newOption.value = data[i].id;
									newOption.innerHTML = data[i].isoName;

									$("#isoId").append(newOption);
								}
							}
						},
						failure : function(e) {
						}
					});
		}
		
		function validateISO(){ 
			var iso = $('#isoId').val();
			if(iso == '' || iso == null){
				setDiv('isoError',webMessages.SELECT_ISO);
				return false;
			}
			setDiv('isoError','');
			return true;
		}
		
		function getPan(isoId) {
			 
			// Remove previous options from the dropdown
			document.getElementById("panLow").options.length = 0;
			//document.getElementById("panHigh").options.length = 0;

			// Create 'Select' option
			var selectOption = document.createElement("option");
			selectOption.innerHTML = webMessages.Select;
			selectOption.value = "";
			//$("#panHigh").append(selectOption);
			$("#panLow").append(selectOption);
			doAjaxToGetPan(isoId);
		}
		
		function doAjaxToGetPan(isoId) {
			$
					.ajax({
						type : "GET",
						url : "getPanEntites?isoId=" + isoId,
						success : function(response) {
							// we have the response

							var obj = JSON.parse(response);

							// Remove previous options from the dropdown
							document.getElementById("panLow").options.length = 0;

							// Create 'Select' option
							var selectOption = document.createElement("option");
							var panLowOption = document.createElement("option");
							selectOption.innerHTML = webMessages.Select;
							panLowOption.innerHTML = webMessages.Select;
							selectOption.value = "";
							$("#panLow").append(selectOption);

							if (obj.errorMessage == "sucess") {

								var data = obj.panRangeRequests;
								for (var i = 0; i < data.length; i++) {
									var newOption = document
											.createElement("option");
									newOption.value = data[i].isoId;
									newOption.innerHTML = data[i].panRange;

								 $("#panLow").append(newOption);
								}
							}
						},
						failure : function(e) {
						}
					});
		}
		
	</script>
</body>
</html>
