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
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css" />
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
						<span class="breadcrumb-text"><spring:message code="fee-report.label.fee.report" /></span>
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
						<form:form action="getFeeReportPagination" name="paginationForm" method="post">
									<input type="hidden" id="pageNumberId" name="pageNumber" /> 
									<input type="hidden" id="totalRecordsId" name="totalRecords" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
						<form:form action="downloadFeeTxnReport" name="downloadReport" method="post">
							<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" /> 
							<input type="hidden" id="downloadTypeId" name="downloadType" />
							<input type="hidden" id="totalRecords" name="totalRecords" />
							<input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
							<input type="hidden" name="CSRFToken" value="${tokenval}">
						</form:form>

						<form:form action="showGlobalPendingTransactionReport" name="showGlobalPendingTransactionReport">
									<input type="hidden" id="fromDate" name="fromDate" />
									<input type="hidden" id="toDate" name="toDate" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

							<form:form action="processISOTxns" name="showISOFeeReport"
								method="post">
								<input type="hidden" id="getISOId" name="getISOId" />
								<input type="hidden" id="getFromDate" name="getFromDate" />
								<input type="hidden" id="getToDate" name="getToDate" />
								<input type="hidden" name="CSRFToken" value="${tokenval}">
							</form:form>
							<!-- Content Block End -->
						<!-- Search Table Block Start -->
						
						<form:form action="processFeeReport" commandName="feeReportRequest" method="post">
						<input type="hidden" name="CSRFToken" value="${tokenval}">
												
												<fieldset class="col-sm-3">
													<label><spring:message code="fee-report.label.pm.name"/><span
														class="required-field">*</span></label>
													<form:select id="programManagerId" path="programManagerId" onclick="validatePM()"
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
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="reports.label.balancereports.manualtransactions.selectdaterange.fromdate" /><span class="required-field">*</span></label>
													<div class="input-group focus-field">
														<form:input path="fromDate" id="transFromDate" onblur="return clientValidation('transFromDate', 'startDate','transFromDateErrorDiv')"
															cssClass="form-control effectiveDate" />
														<span class="input-group-addon"><span
															class="glyphicon glyphicon-calendar"></span></span>
													</div>
													<div class="discriptionMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="transFromDateErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="reports.label.balancereports.manualtransactions.selectdaterange.todate" /><span class="required-field">*</span></label>
													<div class="input-group focus-field">
														<form:input path="toDate" cssClass="form-control effectiveDate" id="transToDate"
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
													<a type="button" href="showFeeReport" class="form-control button pull-right"><spring:message code="accounts-manual-credit.label.resetbutton" /></a>
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
										<th><spring:message code="admin.iso.label.message"/></th>
										<th><spring:message code="fee-report.label.fee.report.totalamount"/></th>
									</tr>
								</thead>
								<c:choose>
									<c:when
										test="${!(fn:length(feeTransactionList) eq 0) }">
										<c:forEach items="${feeTransactionList}" var="transaction">
											<tr>
												<td><a href="javascript:showISOTxns('${transaction.isoId }')" style="text-decoration: underline;">${transaction.isoName }</a></td>
												<td>${transaction.isoEarnedAmount }</td>
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
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/reports.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/messages.js"></script>
	<script>

	$(document).ready(function() {
		$("#navListId4").addClass("active-background");
		$(".focus-field").click(function() {
			$(this).children('.effectiveDate').focus();
		});

		$('.effectiveDate').datetimepicker({
			timepicker : false,
			format : 'd/m/Y',
			formatDate : 'd/m/Y',
			maxDate:new Date()
		});
		
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
	
	function validate(){
		setDiv('errorDiv','');
		if(!clientValidation('transFromDate', 'startDate','transFromDateErrorDiv')
				| !clientValidation('transToDate', 'endDate','transToDateErrorDiv') 
				| !validatePM()){
			return false;
		}
		return true;
	}
	
	</script>
</body>
</html>
