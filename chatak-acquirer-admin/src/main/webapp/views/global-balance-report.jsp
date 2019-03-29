<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
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
						<span class="breadcrumb-text"><spring:message code="reports.label.balancereports" /></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="descriptionMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error">&nbsp;${error }</span> 
										<span id="sucessDiv" class="green-error">&nbsp;${sucess }</span>
									</div>
								</div>
								<form:form action="getGlobalBalanceInfo" name="paginationForm" method="post">
									<input type="hidden" id="pageNumberId" name="pageNumber" /> 
									<input type="hidden" id="totalRecordsId" name="totalRecords" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								
								<form:form action="downloadGlobalBalanceReport" name="downloadReport" method="post">
									<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" /> 
									<input type="hidden" id="downloadTypeId" name="downloadType" />
									<input type="hidden" id="totalRecords" name="totalRecords" />
									<input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
							</div>
					<!-- Content Block End -->
					<!-- Search Table Block Start -->
					<div class="search-results-table">
						<table class="table table-striped table-bordered table-condensed" style="margin-bottom: 0px;">
							<!-- Search Table Header Start -->
							<tr>
								<td colspan="10" class="search-table-header-column"><span
									class="glyphicon glyphicon-search search-table-icon-text"></span>
									<span><spring:message code="common.label.search"/></span>
									<span class="pull-right"><spring:message code="common.label.totalcount"/> : <label id="totalCount">${totalRecords}</label></span></td>
							</tr>
							</table>
							<!-- Search Table Header End -->
							<!-- Search Table Content Start -->
							<div class="search-results-table table-scroll" id="checkb">

							<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
							<tr>
								<th style="width: 117px;"><spring:message code="reports.label.balancereports.username" /></th>
								<th style="width: 150px;"><spring:message code="reports.label.balancereports.companyorfullname" /></th>
								<th style="width: 132px;"><spring:message code="reports.label.balancereports.accountcreationdate" /></th>
								<th style="width: 104px;"><spring:message code="reports.label.balancereports.accountnumber" /></th>
								<th style="width: 85px;"><spring:message code="reports.label.balancereports.accounttype" /></th>
								<th style="width: 59px;"><spring:message code="currency-search-page.label.currencycode" /></th>
								<th style="width: 105px;"><spring:message code="reports.label.balancereports.availablebalance" /></th>
								<th style="width: 96px;"><spring:message code="reports.label.balancereports.currentbalance" /></th>
								<th style="width: 46px;"><spring:message code="reports.label.balancereports.status" /></th>
							</tr>
							</thead>
							<c:choose>
								<c:when test="${!(fn:length(balanceList) eq 0) }">
									<c:forEach items="${balanceList}" var="balanceList">
										<tr>
											<td class="alignleft"><div>${balanceList.userName }</div></td>
											<td class="alignleft"><div>${balanceList.businessName }</div></td>
											<td class="alignleft"><div>${balanceList.accCreationDate}</div></td>
											<td class="alignright">${balanceList.accountNumber }</td>
											<td class="alignleft">${balanceList.accountType }</td>
											<td class="alignright">${balanceList.currency }</td>
											<td class="alignright"><div>${balanceList.availableBalance }</div></td>
											<td class="alignright"><div>${balanceList.currentBalance }</div></td>
											<td>${balanceList.status }</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="7" style="color: red;"><spring:message code="reports.label.balancereports.nobalancesfound" /></td>
									</tr>
								</c:otherwise>
							</c:choose>
							</table>
							<table class="table table-striped table-bordered table-condensed" style="margin-bottom: 0px;">
							<c:if test="${ !(fn:length(balanceList) eq 0)}">
								<tr class="table-footer-main">
									<td colspan="10" class="search-table-header-column">
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
						<div class="col-sm-8 form-action-buttons" style="width: 96%;">
								<div class="col-sm-5"></div>
								<div class="col-sm-7">
									<a type="button" href="home"
										class="form-control button pull-right"><spring:message code="reports.label.backbutton" /></a>
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
	<script src="../js/reports.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/messages.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		/* Common Navigation Include Start */
		/* $(function() {
			//$("#main-navigation").load("main-navigation.html");
			highlightMainContent();
		}); */
		/* function highlightMainContent() {
			$("#navListId7").addClass("active-background");
		} */
		/* Common Navigation Include End */
		/* Common Pagination Include Start */
		
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
