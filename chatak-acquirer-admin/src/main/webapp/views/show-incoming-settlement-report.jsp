<!DOCTYPE html>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.Calendar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.chatak.pg.util.Constants"%>
<%@ page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
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
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css" />
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
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message
								code="home.label.incomingsettlementreport" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message
								code="show-all-pending-merchants.label.details" /></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="marginL40">
						<%-- 	<c:if
							test="${fn:contains(existingFeatures,programmanagerView)||fn:contains(existingFeatures,programmanagerEdit)||fn:contains(existingFeatures,programmanagerSuspend)||fn:contains(existingFeatures,programmanagerActivate)}">
							<div class="tab-header-container">
								<a href="showProgramManager"><spring:message
										code="common.label.search" /> </a>
							</div>
						</c:if> --%>
						<c:if test="${fn:contains(existingFeatures,programmanagerCreate)}">
							<div class="tab-header-container active-background">
								<a><spring:message code="common.label.create" /></a>
							</div>
						</c:if>
					</div>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<form:form action="getAllMatchedTxnsByPgTxnId" name="getAllMatchedTxnsByPgTxnId"
								method="post">
								<input type="hidden" id="pgTxnIds" name="pgTxnIds" />
								<input type="hidden" id="getModelView" name="getModelView" />
								<input type="hidden" name="CSRFToken" value="${tokenval}">
							</form:form>
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<span class="green-error" id="sucessDiv">${sucess}</span> <span
									class="red-error" id="errorDiv">${error}</span>

								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
							
								<form:form action="processIncomingSettlementData"
									name="processIncomingSettlementData"
									modelAttribute="settlementDataRequest" method="post"
									enctype="multipart/form-data">
									<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="access-user-create.label.entityname" /><span
														class="required-field">*</span></label>
													<form:input path="programManagerName"
														cssClass="form-control" id="programManagerName"
														readonly="true" />
													<div class="discriptionErrorMsg">
														<span id="pgmmgrNameErrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>

												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="home.label.batch.date.time" /><span
														class="required-field">*</span></label>
													<form:input path="batchDate" cssClass="form-control"
														id="batchDate" readonly="true"
														onblur="clientValidation('programMangerName', 'program_manager_name','pgmmgrNameErrormsg')"
														onclick="clearErrorMsg('pgmmgrcompanynameerrormsg');" />

													<div class="discriptionErrorMsg">
														<span id="pgmmgrNameErrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="home.label.grossamount" /><span
														class="required-field">*</span></label>
													<fmt:formatNumber type="number"
															value="${settlementDataRequest.totalAmount}"
															pattern="<%=Constants.DOUBLE_AMOUNT_FORMAT %>"
															var="totalAmount" />
													<input name="totalAmount" Class="form-control"
													    value="${totalAmount}"
														id="totalAmount" maxlength="50" readonly="true"
														onblur="clientValidation('companyName','company_name','pgmmgrcompanynameerrormsg')"
														onclick="clearErrorMsg('pgmmgrcompanynameerrormsg');" />

													<div class="discriptionErrorMsg">
														<span id="pgmmgrcompanynameerrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6" id="browseFileHide">
													<label><spring:message code="admin-label.upload" /><span
														class="required-field">*</span></label>
													<div class="input-group">
														<span class="input-group-btn"> <span
															style="height: 24px;" class="btn btn-primary btn-file">
																<spring:message code="search.label.Browse" />&hellip; <input
																type="file" name="dataFile" id="browseFile"
																title="<spring:message code="admin-label.Nofilechosen"/>"
																onchange="return updateFileAndReadFileSettlementReport(this,'dataFile_errDiv');" />
														</span>
														</span> <input id="dataFile" class="form-control readonly"
															readonly />
													</div>
													<div class="discriptionErrorMsg">
														<span id="dataFile_errDiv" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>
										</div>
									</div>
									<!--Panel Action Button Start -->
									<div class="col-sm-12 form-action-buttons">
										<div class="col-sm-5"></div>
										<div class="col-sm-7">
											<input type="submit" class="form-control button pull-right"
												value="<spring:message code="home.label.process"/>"
												onclick="return validateIncomingSettlementReportData()">
											<input type="button" class="form-control button pull-right"
												value="<spring:message code="reports.label.cancelbutton"/>"
												onclick="goToDashBoard()">
										</div>
									</div>
									<!--Panel Action Button End -->
								</form:form>
								<div>
								<a href="show-incoming-settlement-report"><spring:message code="admin.label.clickhere"/></a>
								</div>
								<table id="serviceResults"
									class="table table-striped table-bordered table-responsive table-condensed tablesorter">
									<thead>
										<tr>
											<th style="width: 500px;"><spring:message
													code="admin.label.rowName" /></th>
											<th style="width: 500px;"><spring:message
													code="admin.label.errorMes" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${!(fn:length(hashMap) eq 0)}">
												<c:forEach items="${hashMap}" var="entry">
													<tr>
														<td>${entry.key}</td>
														<td>${entry.value}</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td colspan="7" style="color: red; text-align: center;"><spring:message
															code="home.label.noerrorsfound" /></td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
						<div class="search-results-table">
						 <table class="table table-striped table-bordered table-condensed" style="margin: 1px;">
							<tr>
								<td colspan="10" class="search-table-header-column"><span
									class="glyphicon glyphicon-search search-table-icon-text"></span>
									<span><spring:message code="header.label.matchTxnsummary"/></span>
								<td class="search-table-header-column"
										style="font-weight: bold;"><spring:message
											code="common.label.totalcount" /> : ${totalSettlementEntityList}</td>
								</td>
							</tr>
							</table>
					<table class="table table-striped table-bordered table-responsive table-condensed tablesorter">
						<thead>
							<tr>
								<th style="width: 150px;"><spring:message code="transaction-report-batchID"/></th>
								<th style="width: 150px;"><spring:message code="reports.label.transactions.merchantcode"/></th>
								<th style="width: 150px;"><spring:message code="home.label.acquirerAmunt"/></th>
								<th style="width: 150px;"><spring:message code="home.label.issunceAmunt"/></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${!(fn:length(settlementEntityList) eq 0)}">
										<c:forEach items="${settlementEntityList}" var="settlementEntity">
											<tr>
												<td>${settlementEntity.value.batchid}</td>
												<td>${settlementEntity.value.merchantId} </td>
												<td><fmt:formatNumber value="${settlementEntity.value.acqSaleAmount/100.0}"/></td>
												<td><fmt:formatNumber value="${settlementEntity.value.issSaleAmount/100.0}"/></td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr><td colspan="7" style="color: red; text-align: center;"><spring:message code="home.label.norecordsfound"/></td></tr>
									</c:otherwise>
							</c:choose>
						</tbody>
					</table>
							<table class="table table-striped table-bordered table-condensed" id="executeButton">
								<tr class="table-footer-main">
									<td colspan="10" class="search-table-header-column">
										<div class="col-sm-12">
											<div class="col-sm-12">
												<div class="btn-toolbar" role="toolbar">
															<a href="settlement-money-moment-report"><input type="button" class="form-control button pull-right dashboard-table-btn" value="<spring:message code="reports.label.executebutton"/>"></a>
												</div>
											</div>
										</div>
									</td>
								</tr>
						</table>
					</div>
				<div class="search-results-table">
						<table class="table table-striped table-bordered table-condensed" style="margin: 1px;">
							<tr>
								<td colspan="11" class="search-table-header-column"><span
									class="glyphicon glyphicon-search search-table-icon-text"></span>
									<span><spring:message code="home.label.acqunmachedtxn"/></span>
								<td class="search-table-header-column"
										style="font-weight: bold;"><spring:message
											code="common.label.totalcount" /> : ${totalIssuanceTransactionNotfoundAcquiringList}</td>
								</td>
							</tr>
							</table>
					<table class="table table-striped table-bordered table-responsive table-condensed tablesorter">
						<thead>
							<tr>
								<th style="width: 150px;"><spring:message code="transaction-report-batchID"/></th>
								<th style="width: 150px;"><spring:message code="reports.label.transactions.merchantcode"/></th>
								<th style="width: 150px;"><spring:message code="home.label.isstxnid"/></th>
								<th style="width: 150px;"><spring:message code="reports.label.transactions.TxnID"/></th>
								<th style="width: 150px;"><spring:message code="home.label.issunceAmunt"/></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${!(fn:length(issuanceTransactionNotfoundAcquiringList) eq 0)}">
										<c:forEach items="${issuanceTransactionNotfoundAcquiringList}" var="acquirerUnmatchTxn">
											<tr>
												<td>${acquirerUnmatchTxn.batchid}</td>
												<td>${acquirerUnmatchTxn.merchantId}</td>
												<td>${acquirerUnmatchTxn.issuerTxnID}</td>
												<td>${acquirerUnmatchTxn.pgTransactionId}</td>
												<td><fmt:formatNumber value="${acquirerUnmatchTxn.issSaleAmount/100.0}"/></td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr><td colspan="7" style="color: red; text-align: center;"><spring:message code="home.label.norecordsfound"/></td></tr>
									</c:otherwise>
							</c:choose>
						</tbody>
					</table>
					</div>
						<div class="search-results-table">
						<table class="table table-striped table-bordered table-condensed" style="margin: 1px;">
							<tr>
								<td colspan="11" class="search-table-header-column"><span
									class="glyphicon glyphicon-search search-table-icon-text"></span>
									<span><spring:message code="home.label.issunmachedtxn"/></span>
								<td class="search-table-header-column"
										style="font-weight: bold;"><spring:message
											code="common.label.totalcount" /> : ${totalAcquiringTransactionNotfoundIssuanceList}</td>
								</td>
							</tr>
							</table>
					<table class="table table-striped table-bordered table-responsive table-condensed tablesorter">
						<thead>
							<tr>
								<th style="width: 150px;"><spring:message code="transaction-report-batchID"/></th>
								<th style="width: 150px;"><spring:message code="reports.label.transactions.merchantcode"/></th>
								<th style="width: 150px;"><spring:message code="home.label.isstxnid"/></th>
								<th style="width: 150px;"><spring:message code="reports.label.transactions.TxnID"/></th>
								<th style="width: 150px;"><spring:message code="reports.label.pendingtransactions.dateortime"/></th>
								<th style="width: 150px;"><spring:message code="home.label.acqsaleamunt"/></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${!(fn:length(acquiringTransactionNotfoundIssuanceList) eq 0)}">
										<c:forEach items="${acquiringTransactionNotfoundIssuanceList}" var="issuanceUnmatchTxn">
											<tr>
												<td>${issuanceUnmatchTxn.batchId}</td>
												<td>${issuanceUnmatchTxn.merchantId}</td>
												<td>${issuanceUnmatchTxn.issuerTxnRefNum}</td>
												<td>${issuanceUnmatchTxn.transactionId}</td>
												<td>${issuanceUnmatchTxn.deviceLocalTxnTime}</td>
												<td><fmt:formatNumber value="${issuanceUnmatchTxn.txnAmount/100.0}"/></td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr><td colspan="7" style="color: red; text-align: center;"><spring:message code="home.label.norecordsfound"/></td></tr>
									</c:otherwise>
							</c:choose>
						</tbody>
					</table>
					</div> 
								<!-- Page Form End -->
							</div>
						</div>
					</div>
					<!-- Content Block End -->
				</div>
			</article>
			<!--Article Block End-->
			<jsp:include page="footer.jsp" />
		</div>
		<!--Container block End -->
	</div>
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/utils.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/sorting.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/bank.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/program-manager.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		/* Select li full area function Start */
		$("li").click(function() {
			window.location = $(this).find("a").attr("href");
			return false;
		});

		$(document).ready(function() {
			if('${hashMap}' == 0){
				$("#serviceResults").hide();
			}else{
				$("#serviceResults").show();
			}if('${settlementEntityList}' == 0 && '${acquiringTransactionNotfoundIssuanceList}' == 0 && '${issuanceTransactionNotfoundAcquiringList}' == 0){
				$("#browseFileHide").show();
			}else{
				$("#browseFileHide").hide();
			}if('${settlementEntityList}' == 0){
				$("#executeButton").hide();
			}else{
				$("#executeButton").show();
			}
			
			
			if ($('#autorepenish').prop('checked') == true) {
				$(".thresholdAmount").show();
			} else if ($('#autorepenish').prop('checked') == false) {
				$(".thresholdAmount").hide();
			}
		});

		$(".check").click(function() {
			if ($('#autorepenish').prop('checked') == true) {
				$(".thresholdAmount").show();
			} else if ($('#autorepenish').prop('checked') == false) {
				setValue('accountThresholdamount', '');
				setValue('sendFund', '');
				setValue('bankId', '');
				setValue('accountThresholdamount', '');
				$(".thresholdAmount").hide();
			}
		});

		/* Select li full area function End */
		/* Common Navigation Include Start */
		$(function() {
			$("#main-navigation").load("main-navigation.html");
		});
		function highlightMainContent() {
			$("#navListId2").addClass("active-background");
		}
		/* Common Navigation Include End */
	</script>
	<script>
		var MAX_PROGRAM_MANAGER_LOGO_FILE_SIZE = 1024 * 1024 * 1;

		function readImageURL(input) {
			if (!isValidImage(input.value)) {
				document.getElementById('image_div').innerHTML = webMessages.ALLOWED_IMAGES;
				return;
			}
			document.getElementById('image_div').innerHTML = '';
			if (input.files && input.files[0]) {
				if (parseInt(MAX_PROGRAM_MANAGER_LOGO_FILE_SIZE) < parseInt(input.files[0].size)) {
					document.getElementById('image_div').innerHTML = webMessages.IMAGE_SIZE;
					return;
				}
			}
		}

		function isValidImage(imageSrc) {
			var value = imageSrc.toUpperCase();
			if (value.indexOf('.PNG') != -1 || value.indexOf('.JPG') != -1
					|| value.indexOf('.JPEG') != -1
					|| value.indexOf('.GIF') != -1
					|| value.indexOf('.BMP') != -1) {
				return true;
			}
			return false;
		}

		$(document).ready(function() {
			$(".issuanceProgramManager").hide();
			$(".issuancePMlogo").hide();
			$(".acquirerCurrencyNames").hide();
			$(".acquirerBankNames").hide();
			$(".acquirerCardProgram").hide();
			
			
		});

		function closePopup() {
			$('#LogoDiv').popup("hide");
		}
		function openPopup() {
			$('#LogoDiv').popup("show");
		}
		document.getElementById("schedulerRunTime").value = "00:00:00";
		function changeProgramManager() {
			if ($('#checkDefaultProgramManager').prop('checked') == true) {
				$('#checkDefaultPMValue').val(true);
			} else {
				$('#checkDefaultPMValue').val(false);
				setDiv('sucessDiv', '');
			}
		}
		
		function showAllMatchedTxnsByMerchantId(pgTxnId, incomingSett) {
			get('pgTxnIds').value = pgTxnId;
			get('getModelView').value = incomingSett;
			document.forms["getAllMatchedTxnsByPgTxnId"].submit();
		}
	</script>
</body>
</html>
