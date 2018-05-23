<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page  import="java.util.Calendar"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<body>

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
						<span class="breadcrumb-text"><spring:message code="switch.label.switchinfo"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="common.label.view"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first">
						<a href="switch-search"><spring:message code="common.label.search"/></a>
					</div>
					<div class="tab-header-container active-background">
						<a href="#"><spring:message code="common.label.view"/></a>
					</div>
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
								<!-- Page Form Start -->
								<form:form action="updateSwitch" commandName="switch" name="switch">
									<div class="col-sm-12 paddingT20">
										<div class="row">
											<!-- Account Details Content Start -->
											<section class="field-element-row account-details-content">
											<form:hidden path="id"/>
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="switch.label.switchname"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="switchName"
															id="switchName" maxlength="50" disabled="true"
															onblur="validateSwitchName()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="switchNameEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="switch.label.switchtype"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="switchType"
															id="switchType" disabled="true" onblur="validateSwitchType()">
															<form:option value="SOCKET"><spring:message code="switch.label.socket"/></form:option>
															<form:option value="WEB SERVICES"><spring:message code="switch.label.webservice"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="switchTypeEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="switch.label.primaryswitchURL"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="primarySwitchURL"
															maxlength="75" id="primarySwitchURL" disabled="true" onblur="validatePrimarySwitchURL()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="primarySwitchURLEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="switch.label.primaryswitchport"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="primarySwitchPort"
															id="primarySwitchPort" maxlength="4" disabled="true" onblur="validatePrimarySwitchPort()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="primarySwitchPortEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="switch.label.secondaryswitchURL"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="secondarySwitchURL"
															maxlength="75" id="secondarySwitchURL" disabled="true" onblur="validateSecondarySwitchURL()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="secondarySwitchURLEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="switch.label.secondaryswitchport"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="secondarySwitchPort"
															id="secondarySwitchPort" maxlength="4" disabled="true" onblur="validateSecondarySwitchPort()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="secondarySwitchPortEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="switch.label.priority"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="priority"
															id="priority" maxlength="1" disabled="true" onblur="validatePriority()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="priorityEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												
													<%-- <fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.status"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="status" disabled="true"
															id="status"  onblur="validateStatus()">
															<form:option value="1"><spring:message code="common.label.pending"/></form:option>
															<form:option value="0"><spring:message code="common.label.active"/></form:option>
															<form:option value="2"><spring:message code="common.label.inactive"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="statusEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset> --%>
													
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														 <input type="button" class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.cancel"/>' onclick="goToSwitchSearch()">
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
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/chatak-ajax.js"></script>
	<script src="../js/messages.js"></script>
	<script type="text/javascript" src="../js/switch.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script>
		$(document).ready(function() {
			$("#navListId2").addClass("active-background");
		});
	</script>
</body>
</html>