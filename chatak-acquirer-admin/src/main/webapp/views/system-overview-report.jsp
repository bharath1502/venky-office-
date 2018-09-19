<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
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
						<span class="breadcrumb-text"><spring:message
								code="reports.label.reports" /></span>
						<!-- <span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text">Global System Reports</span> -->
						<span class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message
								code="reports.label.overviewandbalancesheet" /></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<!-- <div class="tab-header-container-first active-background">
						<a href="merchant-search">Specific User</a>
					</div> -->
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="col-sm-12">
						<!--Success and Failure Message Start-->
						<div class="col-xs-12">
							<div class="discriptionMsg" data-toggle="tooltip"
								data-placement="top" title="">
								<span class="red-error">&nbsp;${error }</span> 
								<span id="sucessDiv" class="green-error">&nbsp;${sucess }</span>
							</div>
						</div>
						<form action="downloadOverviewReport" name="downloadReport"
							method="post">
							<input type="hidden" id="downloadPageNumberId"
								name="downLoadPageNumber" /> 
								<input type="hidden"
									id="downloadTypeId" name="downloadType" /> 
								<input type="hidden"
									id="totalRecords" name="totalRecords" /> 
								<input type="hidden" 
									id="downloadAllRecords" name="downloadAllRecords" />
						    <input type="hidden" name="CSRFToken" value="${tokenval}">
						</form>
					</div>
					<!-- Content Block End -->
					<!-- Search Table Block Start -->
					<div class="search-results-table table-scroll">
						<!-- Search Table Header End -->
						<!-- Search Table Content Start -->
						<!-- <table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
								<tr>
									<th>Pending Registrations</th>
									<th>Pending Profiles</th>
									<th>Active Profiles</th>
									<th>Frozen Profiles</th>
								</tr>
							</thead>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
							</table> -->

						<table id="serviceResults"
							class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
								<tr>
									<th style="width: 208px;"><spring:message
											code="reports.label.overviewandbalancesheet.accounttypes" /></th>
									<th style="width: 386px;"><spring:message
											code="reports.label.overviewandbalancesheet.activeandblockedaccounts" /></th>
									<th style="width: 136px;"><spring:message
											code="currency-search-page.label.currencycode"/></th>
									<th style="width: 202px;"><spring:message
											code="reports.label.overviewandbalancesheet.totalbalances" /></th>
								</tr>
							</thead>
							<%-- <c:choose> --%>
							<c:forEach items="${accessLogReportList}" var="accessLogReportList">
								<tr>
									<td class="alignleft"><spring:message code="reports.label.overviewandbalancesheet.merchant" /></td>
									<td>${accessLogReportList.merchantAccountCount}</td>
									<td class="alignright">${accessLogReportList.currency}</td>
									<td class="alignright">${accessLogReportList.merchantAccountBalance}</td>
								</tr>
							</c:forEach>
							<c:forEach items="${accessLogReportList}" var="accessLogReportList">
							 <tr>
								<td class="alignleft"><spring:message
										code="reports.label.overviewandbalancesheet.submerchant" /></td>
								<td>${accessLogReportList.subMerchantAccountCount}</td>
								<td class="alignright">${accessLogReportList.currency }</td>
								<td class="alignright">${accessLogReportList.subMerchantAccountBalance }</td>
							</tr>
							</c:forEach>
							<c:forEach items="${accessLogReportList}" var="accessLogReportList">
							<tr>
								<td class="alignleft"><spring:message
										code="reports.label.overviewandbalancesheet.revenueaccount" /></td>
								<td>${accessLogReportList.chatakAccountCount}</td>
								<td class="alignright">${accessLogReportList.currency }</td>
								<td class="alignright">${accessLogReportList.chatakAccountBalance}</td>
							</tr> 
							</c:forEach>
							<%-- <c:otherwise>
									<tr>
										<td colspan="14" style="color: red;">No Logs Found</td>
									</tr>
								</c:otherwise> --%>
							<%-- 	</c:choose> --%>
						</table>

						<!-- <table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
								<tr>
									<th>Manual Revenues</th>
									<th>Count</th>
									<th>Total</th>
								</tr>
							</thead>
										<tr>
											<td></td>
											<td></td>
											<td></td>
										</tr>
							</table>
							<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
								<tr>
									<th>System Revenues</th>
									<th>Count</th>
									<th>Total</th>
								</tr>
							</thead>
										<tr>
											<td></td>
											<td></td>
											<td></td>
										</tr>
							</table>
							<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
								<tr>
									<th>All Generated Revenues</th>
									<th>Count</th>
									<th>Total</th>
								</tr>
							</thead>
										<tr>
											<td></td>
											<td></td>
											<td></td>
										</tr>
							</table> -->


						<table class="table table-striped table-bordered table-condensed"
							style="margin-bottom: 0px;">
							<%-- <c:if test="${ !(fn:length(accessLogReportList) eq 0)}"> --%>
							<tr class="table-footer-main">
								<td colspan="14" class="search-table-header-column">
									<div class="col-sm-12">
										<div class="col-sm-3">
											<div class="btn-toolbar" role="toolbar">
												<div class="btn-group custom-table-footer-button">
													<a
														href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.XLS_FILE_FORMAT%>', '${totalRecords}')">
														<button type="button" class="btn btn-default">
															<img src="../images/excel.png">
														</button>
													</a> <a
														href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.PDF_FILE_FORMAT%>', '${totalRecords}')">
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
									</div>
								</td>
							</tr>
							<%-- </c:if> --%>
						</table>
						<div class="col-sm-8 form-action-buttons" style="width: 96%;">
							<div class="col-sm-5"></div>
							<div class="col-sm-7">
								<!-- <input type="button" class="form-control button pull-right"
													value="Search">  -->
								<a type="button" href="home"
									class="form-control button pull-right"><spring:message
										code="reports.label.backbutton" /></a>
							</div>
						</div>
					</div>
					<!-- Search Table Block End -->
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
	<script src="../js/sortable.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/messages.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		$(document).ready(function() {
			$("#navListId4").addClass("active-background");

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
