<!DOCTYPE html>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.pg.util.Constants"%>
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
<link href="../css/jquery-datepicker.css" rel="stylesheet">
<link href="../css/rome.css" rel="stylesheet">
</head>
<body>
<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<!--Header Block End -->
			<!--Navigation Block Start -->
			<%-- <jsp:include page="header.jsp"></jsp:include> --%>
			<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="reports.label.reports" /></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="admin.label.isorevenue" /></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<!--Success and Failure Message Start-->
						<div class="row">
					<!-- <div class="col-sm-12"> -->
						<!--Success and Failure Message Start-->
						<div class="col-xs-12">
							<div class="discriptionMsg" data-toggle="tooltip" data-placement="top" title="">
								<span class="red-error" style="font-size: 13px;">&nbsp;${error }</span> 
								<span id="sucessDiv" class="green-error">&nbsp;${sucess }</span>
							</div>
						</div>
						<form:form action="getIsoRevenueReportPagination" name="paginationForm" method="post">
									<input type="hidden" id="pageNumberId" name="pageNumber" /> 
									<input type="hidden" id="totalRecordsId" name="totalRecords" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
						<form:form action="downloadIsoRevenueReport" name="downloadReport" method="post">
							<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" /> 
							<input type="hidden" id="downloadTypeId" name="downloadType" />
							<input type="hidden" id="totalRecords" name="totalRecords" />
							<input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
							<input type="hidden" name="CSRFToken" value="${tokenval}">
						</form:form>

							<form:form action="getAllMatchedTxnsByEntityId"
								name="getAllMatchedTxnsByEntityId" method="post">
								<input type="hidden" id="issuanceSettlementEntityId"
									name="issuanceSettlementEntityId" />
								<input type="hidden" name="CSRFToken" value="${tokenval}">
							</form:form>
							<!-- Content Block End -->
						<!-- Search Table Block Start -->
						
						<form:form action="processIsoRevenueReport" modelAttribute="feeReportRequest" method="post">
						<input type="hidden" name="CSRFToken" value="${tokenval}">
												<c:if test="${feeReportRequest.getEntityType() eq 'Program Manager'}">
												<fieldset class="col-md-3 col-sm-6">
												<%-- <td><fmt:formatNumber value="${transaction.pmAmount/100}"/></td> --%>
													<label><spring:message code="fee-report.label.pm.name" /></label>
													<form:select id="programManagerId" path="programManagerId" onclick="validatePM()" onchange="getIso(this.value)"
														cssClass="form-control" >
														<form:option value="${programManagersList.id}" >${programManagersList.programManagerName}</form:option>
													</form:select>
													<div class="discriptionErrorMsg">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												</c:if>
												<c:if test="${feeReportRequest.getEntityType() eq 'ISO'}">
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="admin.iso.label.message" /></label>
													<form:input id="isoId" cssClass="form-control" path="isoName" readonly="true" />
													<div class="discriptionErrorMsg">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												</c:if>
												<c:if test="${feeReportRequest.getEntityType() eq 'Admin'}">
												<fieldset class="col-sm-3">
													<label><spring:message code="fee-report.label.pm.name"/><span
														class="required-field">*</span></label>
													<form:select id="programManagerId" path="programManagerId" onclick="validatePM()" onchange="getIso(this.value)"
														cssClass="form-control" >
														<form:option value=""><spring:message code="fee-report.label.select"/></form:option>
														<c:if test="${not empty programManagersList}">
															<c:forEach items="${programManagersList}" var="programManager">
																			<form:option value="${programManager.id}" >${programManager.programManagerName}</form:option>
															</c:forEach>
														</c:if>
													</form:select>
													<div class="discriptionMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="pmError">&nbsp;</span>
													</div>
												</fieldset>
												</c:if>
												<c:if test="${feeReportRequest.getEntityType() eq 'Admin' || feeReportRequest.getEntityType() eq 'Program Manager'}">
												<fieldset class="col-sm-3">
													<label><spring:message code="admin.iso.label.message"/><span
														class="required-field">*</span></label>
													<form:select id="isoId" path="isoId" onclick="validateISO()"
														cssClass="form-control" >
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
												</c:if>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="reports.label.balancereports.manualtransactions.selectdaterange.fromdate" /><span class="required-field">*</span></label>
													<div class="input-group focus-field jquery-datepicker">
														<form:input path="fromDate" id="transFromDate" onblur="return clientValidation('transFromDate', 'startDate','transFromDateErrorDiv')"
															cssClass="form-control effectiveDate jquery-datepicker__input" />
														<span class="input-group-addon"><span
															class="glyphicon glyphicon-calendar"></span></span>
													</div>
													<div class="discriptionMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="transFromDateErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="reports.label.balancereports.manualtransactions.selectdaterange.todate" /><span class="required-field">*</span></label>
													<div class="input-group focus-field jquery-datepicker">
														<form:input path="toDate" cssClass="form-control effectiveDate jquery-datepicker__input" id="transToDate"
														onblur="return clientValidation('transToDate', 'endDate','transToDateErrorDiv');" />
														<span class="input-group-addon"><span
															class="glyphicon glyphicon-calendar"></span></span>
													</div>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="transToDateErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												
												<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
													<input type="submit" onclick="return validate()" value="<spring:message code="fee-report.label.fee.report.search" />" class="form-control button pull-right">
													<a type="button" href="showIsoRevenueReport" class="form-control button pull-right"><spring:message code="accounts-manual-credit.label.resetbutton" /></a>
											</div>
										</div>
								</form:form>
						</div>
						</div>
						
							<c:if test="${flag ne false }">
					<div class="search-results-table">
						<table class="table table-striped table-bordered table-condensed marginBM1">
							<!-- Search Table Header Start -->
							<tbody><tr>
								<td class="search-table-header-column widthP80">
									<span class="glyphicon glyphicon-search search-table-icon-text"></span>									
									<span><spring:message code="common.label.search"/></span>
								</td>
								<td class="search-table-header-column" style="font-weight:bold;"><spring:message code="common.label.totalcount"/> : <label id="totalCount">${totalRecords}</label></td>
							</tr>
							</tbody></table>
							<!-- Search Table Header End -->
							<!-- Search Table Content Start -->
							
						    <div class="search-results-table table-scroll" id="checkb" >

							<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
								<thead>
									<tr>
										<th><spring:message code="fee-report.label.fee.report.merchantid"/></th>
										<th><spring:message code="home.label.acqsaleamunt"/></th>
										<th><spring:message code="home.label.issunceAmunt"/></th>
										<th><spring:message code="admin.label.isoamount"/></th>
										<th><spring:message code="transaction-report-batchID"/></th>
									</tr>
								</thead>
								<c:choose>
									<c:when
										test="${!(fn:length(feeTransactionList) eq 0) }">
										<c:forEach items="${feeTransactionList}" var="transaction">
											<tr>
												<td><a href="javascript:showAllTxnsForIso('${transaction.issuanceSettlementEntityId }')" style="text-decoration: underline;">${transaction.merchantId }</a></td>
												<td><fmt:formatNumber value="${transaction.acquirerAmount/100.0}"/></td>
												<td><fmt:formatNumber value="${transaction.issAmount/100.0}"/></td>
												<td><fmt:formatNumber value="${transaction.isoAmount/100.0}"/></td>
												<td>${transaction.batchId }</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="12" style="color: red;"><spring:message code="reports.label.pendingtransactions.notransactionsfound" /></td>
										</tr>
									</c:otherwise>
								</c:choose>
								</table>
								<table class="table table-striped table-bordered table-condensed">
							<c:if test="${ !(fn:length(feeTransactionList) eq 0)}">
								<tr class="table-footer-main">
									<td colspan="10" class="search-table-header-column">
										<div class="col-sm-12">
											<div class="col-sm-3">
												<div class="btn-toolbar" role="toolbar">
													<div class="btn-group custom-table-footer-button">
														<a
															href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.XLS_FILE_FORMAT%>', ${totalRecords})">
															<button type="button" class="btn btn-default">
																<img src="../images/excel.png">
															</button>
														</a> <a
															href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.PDF_FILE_FORMAT%>', ${totalRecords})">
															<button type="button" class="btn btn-default">
																<img src="../images/pdf.png">
															</button>
														</a>
														<a>
															<input type="checkbox" class="autoCheck check" id="totalRecordsDownload">
															<spring:message code="fee-program-search.label.downloadall" />
														</a>
													</div>
												</div>
											</div>
											<div class="col-sm-9">
												<ul class="pagination custom-table-footer-pagination">
													<c:if test="${portalListPageNumber gt 1}">
														<li><a
															href="javascript:getPortalOnPageWithRecords('1','${totalRecords}')">
																&laquo;</a></li>
														<li><a
															href="javascript:getPortalPrevPageWithRecords('${portalListPageNumber }','${totalRecords}')">
																&lsaquo; </a></li>
													</c:if>

													<c:forEach var="page" begin="${beginPortalPage }"
														end="${endPortalPage}" step="1" varStatus="pagePoint">
														<c:if test="${portalListPageNumber == pagePoint.index}">
															<li
																class="${portalListPageNumber == pagePoint.index?'active':''}">
																<a href="javascript:">${pagePoint.index}</a>
															</li>
														</c:if>
														<c:if test="${portalListPageNumber ne pagePoint.index}">
															<li class=""><a
																href="javascript:getPortalOnPageWithRecords('${pagePoint.index }','${totalRecords}')">${pagePoint.index}</a>
															</li>
														</c:if>
													</c:forEach>

													<c:if test="${portalListPageNumber lt portalPages}">
														<li><a
															href="javascript:getPortalNextPageWithRecords('${portalListPageNumber }','${totalRecords}')">
																&rsaquo;</a></li>
														<li><a
															href="javascript:getPortalOnPageWithRecords('${portalPages }','${totalRecords}')">&raquo;
														</a></li>
													</c:if>
												</ul>
											</div>
										</div>
									</td>
								</tr>
							</c:if>
							<!-- Search Table Content End -->
						</table>
						</div>
					</div>
					</c:if>
					<!-- Search Table Block End -->
				</div>
				<div id="txn-popup" class="txn-popup"></div>
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
	<script src="../js/sortable.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/utils.js"></script>
	 <script src="../js/rome.js"></script>
	<script src="../js/reports.js"></script>
	<script src="../js/rome.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/iso.js"></script>
	<script>

	$(document).ready(function() {
		$("#navListId4").addClass("active-background");
		$(".focus-field").click(function() {
			 $(this).children('.effectiveDate').focus();
		});
		rome(transFromDate, { time: false,"inputFormat": "DD/MM/YYYY" });
		rome(transToDate, { time: false,"inputFormat": "DD/MM/YYYY" });
		/* $('.effectiveDate').datetimepicker({
			timepicker : false,
			format : 'd/m/Y',
			formatDate : 'd/m/Y',
			maxDate:new Date()
		}); */
		
		 if ("${transactionDiv}" == "true"){
			 $('#checkb').show();
			 $('#transinfo').show();
			 $('#showDates').show();
		   }  
	});
	
	$(document).ready(function() {
		/* Table Sorter includes Start*/
		$(function() {
			
				  // call the tablesorter plugin
				  $('#serviceResults').sortable({
					
					 divBeforeTable: '#divbeforeid',
					divAfterTable: '#divafterid',
					initialSort: false,
					locale: 'th',
					//negativeSort: [1, 2]
				});
		});
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
	
	function validateISO(){ 
		var iso = $('#isoId').val();
		if(iso == '' || iso == null){
			setDiv('isoError',webMessages.SELECT_ISO);
			return false;
		}
		setDiv('isoError','');
		return true;
	}
	
	function validate(){
		setDiv('errorDiv','');
		if(!validatePM()
				| !clientValidation('transFromDate', 'startDate','transFromDateErrorDiv')
				| !clientValidation('transToDate', 'endDate','transToDateErrorDiv') 
				| !validateISO()
				| !validatePM()
				| !validateBatchFundingReportsDates()){
			return false;
		}
		return true;
	}
	
	function showAllTxnsForIso(issuanceSettlementEntityId) {
		get('issuanceSettlementEntityId').value = issuanceSettlementEntityId;
		document.forms["getAllMatchedTxnsByEntityId"].submit();
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
	</script>
</body>
</html>
