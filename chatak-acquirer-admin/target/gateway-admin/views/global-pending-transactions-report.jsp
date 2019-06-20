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
     <link href="../css/rome.css" rel="stylesheet">
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
						<span class="breadcrumb-text"><spring:message code="reports.label.pendingtransactions" /></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<!--Success and Failure Message Start-->
						<div class="row">
						<!--Success and Failure Message Start-->
						<div class="col-xs-12">
							<div class="discriptionMsg" data-toggle="tooltip" data-placement="top" title="">
								<span class="red-error" style="font-size: 13px;">&nbsp;${error }</span> 
								<span id="sucessDiv" class="green-error">&nbsp;${sucess }</span>
							</div>
						</div>
						<form:form action="getPendingTranscationInfo" name="paginationForm" method="post">
									<input type="hidden" id="pageNumberId" name="pageNumber" /> 
									<input type="hidden" id="totalRecordsId" name="totalRecords" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
						<form:form action="downloadPendingTransReport" name="downloadReport" method="post">
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


						<!-- Content Block End -->
						<!-- Search Table Block Start -->
						
						<form:form action="showGlobalPendingTransactionReport" modelAttribute="getTransactionsListRequest" method="post">
						<input type="hidden" name="CSRFToken" value="${tokenval}">
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="reports.label.balancereports.manualtransactions.selectdaterange.fromdate" /><span class="required-field">*</span></label>
													<div class="input-group focus-field">
														<form:input path="from_date" id="transFromDate" onblur="return clientValidation('transFromDate', 'startDate','transFromDateErrorDiv')"
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
														<form:input path="to_date" cssClass="form-control effectiveDate" id="transToDate"
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
													<button type="submit" onclick="return submitManualTransferDateRange()" class="form-control button pull-right"><spring:message code="reports.label.generatebutton" /></button>
													<a type="button" href="showGlobalSysPendingTransReports" class="form-control button pull-right"><spring:message code="accounts-manual-credit.label.resetbutton" /></a>
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
										<th style="width: 117px;"><spring:message code="reports.label.transactions.dateortime" /></th>
										<th style="width: 117px;"><spring:message code="admin.common-deviceLocalTxnTime" /></th>
										<th style="width: 77px;"><spring:message code="reports.label.pendingtransactions.username" /></th>
										<th style="width: 134px;"><spring:message code="reports.label.pendingtransactions.companyorfullname" /></th>
										<th style="width: 105px;"><spring:message code="reports.label.pendingtransactions.accountnumber" /></th>
										<th style="width: 86px;"><spring:message code="reports.label.pendingtransactions.accounttype" /></th>
										<th style="width: 88px;"><spring:message code="reports.label.pendingtransactions.transactionID" /></th>
										<th style="width: 139px;"><spring:message code="reports.label.pendingtransactions.transactiondescription" /></th>
										<th style="width: 60px;"><spring:message code="currency-search-page.label.currencycode" /></th>
										<th style="width: 43px;"><spring:message code="reports.label.pendingtransactions.credit" /></th>
										<th style="width: 53px;"><spring:message code="reports.label.pendingtransactions.debit" /></th>
									</tr>
								</thead>
								<c:choose>
									<c:when
										test="${!(fn:length(pendingTransactionsReportList) eq 0) }">
										<c:forEach items="${pendingTransactionsReportList}" var="transaction">
											<tr data-txn-obj='${transaction.txnJsonString}'>
												<td>${transaction.dateTime }</td>
												<td style="width: 100px;">${transaction.deviceLocalTxnTime}
													<c:if test="${ not empty transaction.timeZoneOffset}" >
														<br>(${transaction.timeZoneOffset})
													</c:if>
												</td>
												<td>${transaction.userName }</td>
												<td>${transaction.companyName }</td>
												<td class="alignright">${transaction.accountNumber }</td>
												<td>${transaction.accountType }</td>
												<td class="alignright"><span class="txn-id">${transaction.transactionId }</span></td>
												<td><div class="feeDescDiv">${transaction.description }</div></td>
												<td class="alignright">${transaction.currency }</td>
												<%-- <td>${executedTransactionsReportList.businessName}</td> --%>
												<%-- 	<td>${transaction.dateTime }</td> --%>
												<c:choose>
												<c:when test="${transaction.paymentMethod eq 'debit' }">
												<td></td>
												<td>${transaction.amount }</td>
												</c:when>
												<c:otherwise>
												<td>${transaction.amount }</td>
												<td></td>
												</c:otherwise>
												</c:choose>
												
												<%-- <td>${transaction.userName }</td> --%>
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
								<c:if test="${ !(fn:length(pendingTransactionsReportList) eq 0)}">
									<tr class="table-footer-main">
										<td colspan="8" class="search-table-header-column">
											<div class="col-sm-12">
												<div class="col-sm-4">
													<div class="btn-toolbar" role="toolbar">
														<div class="btn-group custom-table-footer-button">
															<a
																href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.XLS_FILE_FORMAT%>','${totalRecords}')">
																<button type="button" class="btn btn-default">
																	<img src="../images/excel.png">
																</button>
															</a> <a
																href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.PDF_FILE_FORMAT%>','${totalRecords}')">
																<button type="button" class="btn btn-default">
																	<img src="../images/pdf.png">
																</button>
															</a>
															<a>
															<input type="checkbox" class="autoCheck check" id="totalRecordsDownload">
															<spring:message code="common.label.downloadall"/> 
														</a>
													</div>
												</div>
											</div>
											<div class="col-sm-8">
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
							<%-- <div class="col-sm-8 form-action-buttons" style="width: 96%;">
								<div class="col-sm-5"></div>
								<div class="col-sm-7">
									<!-- <input type="button" class="form-control button pull-right"
													value="Search">  -->
									<a type="button" href="showGlobalSysPendingTransReports"
										class="form-control button pull-right"><spring:message code="reports.label.backbutton" /></a>
								</div>
							</div> --%>
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
		rome(transFromDate, { time: false });
		rome(transToDate, { time: false });
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
	</script>
</body>
</html>
