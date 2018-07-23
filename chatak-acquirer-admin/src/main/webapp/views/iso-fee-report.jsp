<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
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
			<!--Navigation Block Start -->
			<nav class="col-sm-12 nav-bar" id="main-navigation">
				<%-- <jsp:include page="header.jsp"></jsp:include> --%>
				<%@include file="navigation-panel.jsp"%>
			</nav>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message
								code="reports.label.reports" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message
								code="fee-report.label.fee.report" /></span>
					</div>
					<!-- Breadcrumb End -->
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message
								code="fee-report.label.fee.report.iso.feetxns" /></a>
					</div>
					<!-- Content Block Start -->
					<div class="main-content-holder">

						<form action="executed-transaction-details-pagination"
							name="paginationForm" method="post">
							<input type="hidden" id="pageNumberId" name="pageNumber" /> <input
								type="hidden" id="totalRecordsId" name="totalRecords" />
								<input type="hidden" name="CSRFToken" value="${tokenval}">
						</form>

						<form action="executed-transaction-details-report"
							name="downloadReport" method="post">
							<input type="hidden" id="downloadPageNumberId"
								name="downLoadPageNumber" /> <input type="hidden"
								id="downloadTypeId" name="downloadType" /> <input type="hidden"
								id="totalRecords" name="totalRecords" /> <input type="hidden"
								id="downloadAllRecords" name="downloadAllRecords" />
								<input type="hidden" id="requestFromId" name="requestFrom" />
								<input type="hidden" name="CSRFToken" value="${tokenval}">
						</form>
						<!-- Search Table Block Start -->
						<div class="search-results-table">
							<table class="table table-striped table-bordered table-condensed"
								style="margin-bottom: 0px;">
								<!-- Search Table Header Start -->
								<tr>
									<td colspan="6" class="search-table-header-column "
										style="text-align: left"><spring:message code="execute-transaction-details.label.transactionsummary" />
										<span class="pull-right"><spring:message code="common.label.totalcount"/> : <label id="totalCount">${totalRecords}</label></span>		
									</td>
								</tr>
							</table>
							<!-- Search Table Header End -->
							<!-- Search Table Content Start -->
							<table id="serviceResults"
								class="table table-striped table-bordered table-responsive table-condensed tablesorter">
								<thead>
									<tr>
									<th style="width: 100px;"><spring:message
												code="fee-report.label.fee.report.merchantid" /> <br></th>
										<th style="width: 90px;"><spring:message
												code="reports.label.transactions.dateortime" /> <br></th>
										<th style="width: 90px;"><spring:message
												code="admin.common-deviceLocalTxnTime" /><br></th>
										<th style="width: 100px;"><spring:message
												code="fee-report.label.fee.report.amount" /> <br></th>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${!(fn:length(isoFeeList) eq 0)}">
											<c:forEach items="${isoFeeList}" var="isoTxns">
												<tr data-txn-merchant-code="${isoTxns.merchantId}">
												<td class="tbl-text-align-left">${isoTxns.merchantId}</td>
													<td class="tbl-text-align-left">${isoTxns.txnDate}</td>
													<td>${isoTxns.deviceLocalTxnTime}
													</td>
													<td class="tbl-text-align-right">${isoTxns.isoAmount}</td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr>
												<td colspan="9" style="color: red; text-align: center;"><spring:message
														code="execute-transaction-details.label.norecordsfound" /></td>
											</tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
						<div>
							<input type="submit" class="form-control button pull-right"
								style="margin-top: 20px;"
								value="<spring:message code="execute-transaction-details.label.backbutton"/>"
								onclick="return goToFeeReport();">
						</div>
					</div>
					<!-- Search Table Block End -->
				</div>
			</article>
			<!--Article Block End-->
			<footer class="footer">
				<jsp:include page="footer.jsp"></jsp:include>
			</footer>
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
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/backbutton.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/common-lib.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>

	<script type="text/javascript">
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
		
		function goToFeeReport() {
			window.location.href = 'showFeeReport';
		}
	</script>
</body>
</html>