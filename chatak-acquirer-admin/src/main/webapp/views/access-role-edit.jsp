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
<link rel="icon" href="../images/favicon.png" type="image/png">
<!-- Bootstrap -->
<link rel="icon" href="../images/favicon.png" type="image/png">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<link href="../css/font-awesome.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery.min.js"></script>
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
			<!--Navigation Block Start -->
			<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<form:form name="inActiveRoleForm" action="inActivePage" method="post">
				<input type="hidden" name="CSRFToken" value="${tokenval}">
				</form:form>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="setup.label.setup"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="roles.label.role"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="common.label.edit"/></span>
					</div>
					<form:form action="getRoleCategory" name="roleTypeForm" method="post">
				      <input type="hidden" id="rolesType" name="rolesType" />
				      <input type="hidden" name="CSRFToken" value="${tokenval}">
				      <!-- <input type="hidden" id="rolesName" name="roleName" />
				      <input type="hidden" id="roleDiscription" name="description" /> -->
			        </form:form>
			        
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first">
						<a href="access-role-search"><spring:message code="common.label.search"/> </a>
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
								<div class="col-sm-12">
									<div class="discriptionMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error" id="errorDiv">${error }</span> <span
											class="green-error" id="sucessDiv">${sucess }</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="processChatakAdminEdit"  modelAttribute="userRolesDTO" method="post" onsubmit="buttonDisabled()" >
								<input type="hidden" id="permissions" name="permissions">
								<input type="hidden" name="CSRFToken" value="${tokenval}">
								  <form:hidden path="userRoleId"/>
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
											<fieldset class="col-md-3 col-sm-6"> 
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="roles.label.roletype"/></label>
													<form:input id="roleType" path="roleType" cssClass="form-control" readonly="true"/>													
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="roleTypeError">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="roles.label.rolename"/></label>
													<form:input id="roleName"  path="roleName" cssClass="form-control" readonly ="true"/> 
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="roleDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="roles.label.roledescription"/><span class="required-field">*</span></label>
													<form:input  cssClass="form-control" id="description" maxlength="40"
														path="description" onblur="this.value=this.value.trim();validRoleDescription('description','roleDescription','Please enter the description','Please enter the valid description')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="roleDescription">&nbsp;</span>
													</div>
												</fieldset>
												<div class="col-sm-12 role_assign_message">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="roles.label.rolepermission"/></label>
												</div>
												<div class="col-sm-12 roleListDiv">
												<!-- Collapseable panel Main Start -->
												<div class="panel-group" id="accordion">
													<ul id="check-tree" class="marginLM60">
														<c:forEach items="${featureList}" var="feature">
															<c:if test="${feature.getRefFeatureId() == 0}">
																	<div class="panel panel-default clearfix">
																		<!-- Panel Header Start -->
																		<div class="panel-heading permission_color">
																			<h4 class="panel-title">
																				<a id="anchorClick${feature.getFeatureId()}"
																					data-toggle="collapse" data-parent="#accordion"
																					href="#collapse${feature.getFeatureId()}"  class="fa fa-angle-double-left" > <label class="permission_cursor_type" style="font-size: 15px;font-family: Verdana, Geneva, sans-serif;font-style: normal;">
																					<c:if test="${feature.getName() eq 'Dashboard'}"><spring:message code="header.label.dashboard"/></c:if>
																					<c:if test="${feature.getName() eq 'SETUP'}"><spring:message code="header.label.setup"/></c:if>
																					<c:if test="${feature.getName() eq 'Programs'}"><spring:message code="commission-program-search.label.programs"/></c:if>
																					<c:if test="${feature.getName() eq 'Manage'}"><spring:message code="manage.label.manage1"/></c:if>
																					<c:if test="${feature.getName() eq 'Adjustment'}"><spring:message code="accounts-manual-credit.label.adjustments"/></c:if>
																					<c:if test="${feature.getName() eq 'Reports'}"><spring:message code="reports.label.reports"/></c:if>
																					<c:if test="${feature.getName() eq 'VIRTUAL TERMINAL'}"><spring:message code="virtual-terminal-void.label.virtualterminal"/></c:if>
																					<c:if test="${feature.getName() eq 'SubMerchant'}"><spring:message code="reports.label.overviewandbalancesheet.submerchant"/></c:if>
																					<c:if test="${feature.getName() eq 'Transaction'}"><spring:message code="reports.label.transactions.txnstatus"/></c:if>
																					<c:if test="${feature.getName() eq 'Schedule report'}"><spring:message code="chatak-report-lable-schedule-report" /></c:if>
																					</label>
																				</a>
																			</h4>
																		</div>
																		<!-- Panel Header End -->
																		<!-- Panel Content Start -->
																		<div id="collapse${feature.getFeatureId()}"
																			class="panel-collapse collapse parent-child-list">

																			<li><label data-toggle="tooltip" data-placement="top" title=""><input type="checkbox" name="featureTO" 
																					value="${feature.getFeatureId()}"
																					id="${feature.getFeatureId()}"
																					<c:if test="${fn:contains(roleExistingFeatures,feature.getRoleFeatureId())}">checked</c:if> <c:if test="${feature.getName() eq 'Dashboard'}">disabled checked</c:if>  />
																					<c:if test="${feature.getName() eq 'Dashboard'}"><spring:message code="header.label.dashboard"/></c:if>
																					<c:if test="${feature.getName() eq 'SETUP'}"><spring:message code="header.label.setup"/></c:if>
																					<c:if test="${feature.getName() eq 'Programs'}"><spring:message code="commission-program-search.label.programs"/></c:if>
																					<c:if test="${feature.getName() eq 'Manage'}"><spring:message code="manage.label.manage1"/></c:if>
																					<c:if test="${feature.getName() eq 'Adjustment'}"><spring:message code="accounts-manual-credit.label.adjustments"/></c:if>
																					<c:if test="${feature.getName() eq 'Reports'}"><spring:message code="reports.label.reports"/></c:if>
																					<c:if test="${feature.getName() eq 'VIRTUAL TERMINAL'}"><spring:message code="virtual-terminal-void.label.virtualterminal"/></c:if>
																					<c:if test="${feature.getName() eq 'SubMerchant'}"><spring:message code="reports.label.overviewandbalancesheet.submerchant"/></c:if>
																					<c:if test="${feature.getName() eq 'Transaction'}"><spring:message code="reports.label.transactions.txnstatus"/></c:if>
																					<c:if test="${feature.getName() eq 'Schedule report'}"><spring:message code="chatak-report-lable-schedule-report" /></c:if>
																					</label>
																				<ul>
																					<c:forEach items="${featureList}" var="featureSub" 
																						varStatus="counter">
																						<c:if
																							test="${featureSub.getRefFeatureId() eq feature.getFeatureId()}">

																							<!-- DisplayChild(Level2) -->
																							<fieldset class="col-md-3 col-sm-6">
																							<li><label data-toggle="tooltip" data-placement="top" title=""><input type="checkbox"
																									name="featureTO"
																									id="${featureSub.getFeatureId()}"
																									<c:if test="${fn:contains(roleExistingFeatures,featureSub.getRoleFeatureId())}">checked</c:if>  />
																									<c:if test="${featureSub.getName() eq 'Roles'}"><spring:message code="roles.label.role"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Users'}"><spring:message code="users.label.user"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Switch'}"><spring:message code="Switch.lable.Switch"/></c:if>
																									<c:if test="${featureSub.getName() eq 'ONUS Bin'}"><spring:message code="bin.label.onus"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Black Listed Card'}"><spring:message code="blacklist.label.black"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Payment Scheme'}"><spring:message code="payment.label.paymentscheme"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Merchant Category Code'}"><spring:message code="merchant.label.code"/></c:if>
																									<c:if test="${featureSub.getName() eq 'CA Public Keys'}"><spring:message code="header.label.capublickeys"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Fee Programs'}"><spring:message code="fee-program-search.label.feeprogram"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Program Manager'}"><spring:message code="admin.pm.message"/></c:if>
																									<c:if test="${featureSub.getName() eq 'ISO'}"><spring:message code="admin.partner.message"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Merchant'}"><spring:message code="reports.label.overviewandbalancesheet.merchant"/></c:if>
																									<c:if test="${featureSub.getName() eq 'SubMerchant'}"><spring:message code="reports.label.overviewandbalancesheet.submerchant"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Bank'}"><spring:message code="bank.label.bank"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Currency'}"><spring:message code="reports.label.overviewandbalancesheet.currency"/></c:if>
																					                <c:if test="${featureSub.getName() eq 'VIRTUAL TERMINAL'}"><spring:message code="virtual-terminal-void.label.virtualterminal"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Sale'}"><spring:message code="reports.option.sale"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Refund'}"><spring:message code="processing-transaction-details.label.refundbutton"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Create'}"><spring:message code="show-dynamic-MDR-create-page.label.create"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Edit'}"><spring:message code="commission-program-update.label.edit"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Delete'}"><spring:message code="common.label.delete"/></c:if>
																									<c:if test="${featureSub.getName() eq 'View'}"><spring:message code="pending-merchant-show.label.view"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Merchant Transaction Revenue'}"><spring:message code="chatak.admin.revenue.generated.header.message"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Daily Funding Report'}"><spring:message code="chatak-report-lable-daily-unding-report"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Overview & Balance Sheet'}"><spring:message code="reports.label.overviewandbalancesheet"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Pending transactions'}"><spring:message code="reports.label.pendingtransactions"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Transactions Report'}"><spring:message code="reports.label.transactionsreports"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Balance Reports'}"><spring:message code="reports.label.balancereports"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Manual Transactions'}"><spring:message code="reports.label.balancereports.manualtransactions"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Settlement Report'}"><spring:message code="reports.label.settlementReport"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Batch Report'}"><spring:message code="chatak-batch-report"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Manual Credit'}"><spring:message code="accounts-manual-credit.label.manualcredit"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Manual Debit'}"><spring:message code="accounts-manual-debit.label.manualdebit"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Run Manual'}"><spring:message code="chatak.manual.funding.header"/></c:if>
																									<c:if test="${featureSub.getName() eq 'Unblock Users'}"><spring:message code="header.label.unblockusers"/></c:if>
																									</label>
																								<ul>

																									<c:forEach items="${featureList}"
																										var="featureChild">
																										<c:if
																											test="${featureChild.getRefFeatureId() eq featureSub.getFeatureId()}">
																											<!-- DisplayChild(Level3) -->
																											<li><label data-toggle="tooltip" data-placement="top" title=""><input
																													type="checkbox" name="featureTO"
																													id="${featureChild.getFeatureId()}"
																													<c:if test="${fn:contains(roleExistingFeatures,featureChild.getRoleFeatureId())}">checked</c:if>  />
																													<c:if test="${featureChild.getName() eq 'Create'}"><spring:message code="show-dynamic-MDR-create-page.label.create"/></c:if>
																					                                <c:if test="${featureChild.getName() eq 'Edit'}"><spring:message code="commission-program-update.label.edit"/></c:if>
																					                      			<c:if test="${featureChild.getName() eq 'Delete'}"><spring:message code="common.label.delete"/></c:if>
																					                                <c:if test="${featureChild.getName() eq 'View'}"><spring:message code="pending-merchant-show.label.view"/></c:if></label>
																											</li>
																										</c:if>
																									</c:forEach>
																								</ul></fieldset></li>
																						</c:if>
																					</c:forEach>
																				</ul></li>
																		</div>

																		<!-- Panel Content End -->
																	</div>
																	<!-- User Information Collapseable panel End-->
															</c:if>
														</c:forEach>
													</ul>
												</div>

											</div>

										</div>
										</form:form>
									</div>
									<!--Panel Action Button Start -->
									<div class="col-sm-12 form-action-buttons">
										<div class="col-md-3 col-sm-6">&nbsp;</div>
										<div class="col-sm-9">
										<c:if test="${SAME_ROLE_FLAG eq true}">
										<input type="submit" class="form-control button pull-right" value='<spring:message code="common.label.update"/>' disabled="disabled" id="buttonCreate" onclick="return continueEditRole()">
										<a href="#" id="processingButton1" style="display: none;"
												class="form-control button pull-right">Processing... </a> 
												<input type="button" onclick="openCancelConfirmationPopup()"
												class="form-control button pull-right" value='<spring:message code="common.label.cancel"/>' >
										</c:if>
										<c:if test="${SAME_ROLE_FLAG eq false}">
												<input type="submit" class="form-control button pull-right" value='<spring:message code="common.label.update"/>' id="buttonCreate" onclick="return continueEditRole()"><a href="#" id="processingButton1"
												style="display: none;"
												class="form-control button pull-right">Processing... </a> <input type="button" onclick="openCancelConfirmationPopup()"
												class="form-control button pull-right" value='<spring:message code="common.label.cancel"/>' >
										</c:if>
										</div>
									</div>

									<!--Panel Action Button End -->
							</div>
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
							onclick="cancelCreateUser()">
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
	
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/jquery-checktree.js"></script>
	 <!-- <script src="../js/prepaid-lib.js" type="text/javascript"></script> -->
    <script src="../js/messages.js"></script>
	<script src="../js/role.js" type="text/javascript"></script>
	<script src="../js/validation.js" type="text/javascript"></script>
	<script src="../js/backbutton.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	
	<!-- <script src="../js/bootstrap.js" type="text/javascript"></script> -->
	<script>
		var APP_SERVICE_BASE_URL="<%=request.getContextPath()%>";
		/* Common Navigation Include Start */
	
		$(document).ready(function() {
			$("#navListId2").addClass("active-background");
			$('#my_popup').popup({
				blur:false
			});
		});
		$('#my_popup1').popup({
			blur:false
		});
		
		$(function() {
			$("#main-navigation").load("main-navigation.html");
	    });
		function highlightMainContent() {
			$("#navListId2").addClass("active-background");
		}
		/* Common Navigation Include End */
		/*Collapse Panel functionality Start */
		function nextPanelOpen(collapsID) {
			$('#anchorClick' + (collapsID + 1)).click();
		}
		/*Collapse Panel functionality End */
		/* Custom Checkbox function Start*/
		$('#check-tree').checktree();
		var _gaq = _gaq || [];
		_gaq.push([ '_setAccount', 'UA-36251023-1' ]);
		_gaq.push([ '_setDomainName', 'jqueryscript.net' ]);
		_gaq.push([ '_trackPageview' ]);

		(function() {
			var ga = document.createElement('script');
			ga.type = 'text/javascript';
			ga.async = true;
			ga.src = ('https:' == document.location.protocol ? 'https://ssl'
					: 'http://www')
					+ '.google-analytics.com/ga.js';
			var s = document.getElementsByTagName('script')[0];
			s.parentNode.insertBefore(ga, s);
		})();
		/* Custom Checkbox function End*/
	</script>
</body>
</html>