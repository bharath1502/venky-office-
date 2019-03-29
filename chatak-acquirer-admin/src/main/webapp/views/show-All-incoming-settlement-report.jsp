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
						<span class="breadcrumb-text"><spring:message code="home.label.incomingsettlementreport"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="show-all-pending-merchants.label.details"/></span>
					</div>
					<!-- Breadcrumb End -->
					<div class="tab-header-container-first active-background">
						<a><spring:message code="home.label.incomingsettlementreport"/></a>
					</div>
					<!-- Content Block Start -->
					<div class="main-content-holder">

						<form:form action="processing-transaction-details-pagination" name="paginationForm" method="post">
							<input type="hidden" id="pageNumberId" name="pageNumber" /> 
							<input type="hidden" id="totalRecordsId" name="totalRecords" />
						    <input type="hidden" name="CSRFToken" value="${tokenval}">
						</form:form>

						<form:form action="processing-transaction-details-report" name="downloadReport" method="post">
							<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" />
							<input type="hidden" id="downloadTypeId" name="downloadType" />
						    <input type="hidden" name="CSRFToken" value="${tokenval}">
						</form:form>
						
						<form:form action="pending-merchant-show" name="viewPendingMerchant" method="post">
									<input type="hidden" id="merchantViewId" name="merchantViewId" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
						</form:form>
								<form:form action="show-incoming-settlement-report" name="getSettlementDetails" method="post">
									<input type="hidden" id="programViewId" name="programViewId" />
									<input type="hidden" id="batchDate" name="batchDate" />
								<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>		
			
						<!-- Search Table Block Start -->
						<div class="search-results-table">
							<table class="table table-striped table-bordered table-condensed" style="margin-bottom: 0px;">
							<!-- Search Table Header Start -->
							<tr>
								<td colspan="6" class="search-table-header-column " style="text-align: left">
									<spring:message code="home.label.incomingsettlementreport"/>
									<span class="pull-right"><spring:message code="common.label.totalcount"/> : <label id="totalCount">${totalRecords}</label></span>
								</td>
							</tr>
						</table>
						<!-- Search Table Header End -->
						<!-- Search Table Content Start -->
						<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
						<thead>
							<tr>
								<th style="width: 150px;"><spring:message code="access-user-create.label.entityname"/></th>
								<th style="width: 150px;"><spring:message code="home.label.batch.date.time"/></th>
								<th style="width: 150px;"><spring:message code="home.label.grossamount"/></th>
								<th style="width: 150px;"><spring:message code="home.label.txncount"/></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
							<c:when test="${!(fn:length(settlementDataList) eq 0)}">
										<c:forEach items="${settlementDataList}" var="settlementData">
											<tr>
												<td class="tbl-text-align-center"><a 
												href="javascript:getDetailsOnPMId('${settlementData.programManagerId}','${settlementData.batchDate}')"
												style="text-decoration: underline;">${settlementData.programManagerName}
											    </a></td>
												<td>${settlementData.batchDate}</td>
												<td>${settlementData.totalAmount}</td>
												<td>${settlementData.totalTxnCount}</td>
												<%-- <td>
												<c:if test="${pendingMerchnats.status eq 1}">
												<span><spring:message code="home.label.pending"/></span>
												</c:if>	
												<c:if test="${pendingMerchnats.status eq 4}">
												<span><spring:message code="home.label.decline"/></span>
												</c:if>												
												<c:if test="${pendingMerchnats.status eq 0}">
												<span><spring:message code="home.label.active"/></span>	
												</c:if>
												</td> --%>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr><td colspan="6" style="color: red; text-align: center;"><spring:message code="home.label.norecordsfound"/></td></tr>
									</c:otherwise>
							</c:choose>
						</tbody>
					</table>
						</div>
						<div>
							<input type="submit" class="form-control button pull-right" style="margin-top: 20px;" value="<spring:message code="show-all-pending-merchants.label.backbutton"/>" onclick="return goToDashboard();">
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
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/backbutton.js"></script>
	 <script src="../js/messages.js"></script>
	<script src="../js/common-lib.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
		<script>
		function merchantView(id){
			get('merchantViewId').value = id;
			document.forms["viewPendingMerchant"].submit();
			
		}
		
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
		function getDetailsOnPMId(programManagerId, batchDate) {
			get('programViewId').value = programManagerId;
			get('batchDate').value = batchDate;
			document.forms["getSettlementDetails"].submit();
		}
		</script>
</body>
</html>
