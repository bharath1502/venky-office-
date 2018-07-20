<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
<script src="../js/jquery.min.js"></script>
<script src="../js/jquery.cookie.js"></script>
<script src="../js/common-lib.js"></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/jstimezonedetect/1.0.4/jstz.min.js'></script>
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Navigation Block Start -->
			<%-- <jsp:include page="header.jsp"></jsp:include> --%>
			<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<%-- <article>
				<div class="col-xs-12 content-wrapper">
					<!--Success and Failure Message Start-->
					<div class="col-xs-12">
						<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
							<span class="red-error">&nbsp;${error}</span> <span
								class="green-error">&nbsp;${sucess} </span>
						</div>
					<span>
					<form:form action="searchMerchant" commandName="merchant" name="searchMerchant" method="post">
							<form:hidden path="status" id="merchantStatus"/> 
					</form:form>
					<form:form action="sub-merchant-search" commandName="merchant" name="searchSubMerchant" method="post">
							<form:hidden path="status" id="subMerchantStatus"/> 
					</form:form>
					<form:form action="searchTransactionBySettlementStatus"
						commandName="transaction" name="transaction">
						<form:hidden path="settlementStatus" id="settlementStatus" />
					</form:form>
					
					<form:form action="getLitleEFTTransactionListToDashBoard" name="litleEFTTransaction" method="get">
					</form:form>
					
						<table class="table table-striped table-bordered table-condensed">
						<!-- Search Table Header Start -->
						<tr>
							<td colspan="10" class="col-xs-8 search-table-header-column"><span
								class="glyphicon glyphicon-search search-table-icon-text"></span>
								<span>User Registration Requests</span></td>
						</tr>
						<tr>
							<th colspan="2"><center>Pending Registration Requests</center></th>
						</tr>
						<tr align="center">
							<c:if test="${not empty dashBoardRecords }">
								<td style="width: 50%;"><a style="text-decoration: underline;" href="javascript:;"
									onclick="return getSignUpMerchantRegistrations('5')">Merchant : ${pedningMerchantSignupCount}</a>
								</td>
								<td style="width: 50%;"><a style="text-decoration: underline;" href="javascript:;"
									onclick="return getSignUpSubMerchantRegistrations('5')">Sub Merchant : ${pedningSubMerchantSignupCount}</a>
								</td>
							</c:if>
						</table>
					<table class="table table-striped table-bordered table-condensed">
						<!-- Search Table Header Start -->
						<tr>
							<td colspan="10" class="col-xs-8 search-table-header-column"><span
								class="glyphicon glyphicon-search search-table-icon-text"></span>
								<span>Settlement Status Summary</span></td>
						</tr>
						<tr>
							<th><center>Pending Transactions</center></th>
							<th><center>Processing Transactions</center></th>
							<th><center>Executed Transactions</center></th>
							<th><center>Rejected Transactions</center></th>
						</tr>
						<tr align="center">
							<c:if test="${not empty dashBoardRecords }">
								<td><a style="text-decoration: underline;"
									href="javascript:;"
									onclick="return getTransactionListToDashBoard('Pending')">${dashBoardRecords.pendingCount}</a>
								</td>
								<td><a href="javascript:;"
									style="text-decoration: underline;"
									onclick="return getTransactionListToDashBoard('Processing')">${dashBoardRecords.processingCount}</a>
								</td>
								<td><a href="javascript:;"
									style="text-decoration: underline;"
									onclick="return getTransactionListToDashBoard('Executed')">${dashBoardRecords.executedCount}</a>
								</td>
								<td><a href="javascript:;"
									style="text-decoration: underline;"
									onclick="return getTransactionListToDashBoard('Rejected')">${dashBoardRecords.rejectedCount}</a>
								</td>
							</c:if>
						</tr>
					</table>
					<table class="table table-striped table-bordered table-condensed">
						<!-- Search Table Header Start -->
						<tr>
							<td colspan="10" class="col-xs-8 search-table-header-column"><span
								class="glyphicon glyphicon-search search-table-icon-text"></span>
								<span>EFT Requests</span></td>
						</tr>
						<tr>
									<th><center>Executed Litle Transactions</center></th>
						</tr>
						<tr align="center">
								<td><a style="text-decoration: underline;cursor: pointer;" onclick="return getLitleEFTTransactionListToDashBoard()">${litleEFTCount}</a></td>
							
						</tr>
					</table>
				
					</span>
					<img id="imgId" src="../images/dashboard.png" width="100%" height="400px"
						class="col-xs-12 content-wrapper" style="margin-bottom:10px;">
					<span class="col-xs-6 dashboard-image-description"> <label data-toggle="tooltip" data-placement="top" title="">Transaction
							by Card Program</label>
					</span> <span class="col-xs-6 dashboard-image-description"> <label data-toggle="tooltip" data-placement="top" title="">Transaction
							Percentage by Card Program</label>
					</span>
					</div>
					</div>
					
			</article> --%>
			
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<form action="executed-transaction-details-report" name="downloadExecutedTxnReport" method="post">
						<input type="hidden" id="executedTxnDownloadPageNumberId" name="downLoadPageNumber" />
						<input type="hidden" id="executedTxnDownloadTypeId" name="downloadType" />
						<input type="hidden" id="requestFromId" name="requestFrom" />
						<input type="hidden" id="executedTotalRecords" name="downloadAllRecords" />
						<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form>
					
					<form action="processing-transaction-details-report" name="downloadProcessingTxnReport" method="post">
						<input type="hidden" id="processingTxnDownloadPageNumberId" name="downLoadPageNumber" />
						<input type="hidden" id="processingTxnDownloadTypeId" name="downloadType" />
						<input type="hidden" id="requestFromId1" name="requestFrom" />
						<input type="hidden" id="pendingTotalRecords" name="downloadAllRecords" />
						<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form>
					
					<form:form action="pending-merchant-show" name="viewPendingMerchant" method="post">
									<input type="hidden" id="merchantViewId" name="merchantViewId" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>
					<form:form action="show-incoming-settlement-report" name="getSettlementDetails" method="post">
									<input type="hidden" id="programViewId" name="programViewId" />
									<input type="hidden" id="batchDate" name="batchDate" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>
						<!--Success and Failure Message Start-->
						<div class="col-xs-12" style="margin-top: 10px;">
						<div class="discriptionMsg" data-toggle="tooltip" data-placement="top" title="">
							<span class="red-error">&nbsp;${pendingMerchantError}</span> <span
								class="green-error">&nbsp;${pendingMerchantSucess} </span>
						</div>
						</div>			
					<div style="margin-top: 30px;">
					<!-- InComing Settelment Report start -->
					<div id="incomingsettlementreport" class="dashboard-tab active-background"><spring:message code="home.label.incomingsettlementreport"/></div>
					<div id="pendingPM" class="dashboard-container">
					<table class="table table-striped table-bordered table-condensed" style="margin-bottom: 0px;">
						<tr>
								<td colspan="6" class="search-table-header-column " style="text-align: left">
									<spring:message code="home.label.incomingsettlementreport"/>
								</td>
							</tr>
					</table>
					<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
						<thead>
							<tr>
								<th style="width: 150px;"><spring:message code="access-user-create.label.entityname"/></th>
								<th style="width: 150px;"><spring:message code="home.label.batch.date.time"/></th>
								<th style="width: 150px;"><spring:message code="home.label.grossamount"/></th>
								<th style="width: 150px;"><spring:message code="home.label.txncount"/></th>
								<%-- <th style="width: 150px;"><spring:message code="home.label.status"/></th> --%>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${!(fn:length(settlementDataList) eq 0)}">
										<c:forEach items="${settlementDataList}" var="settlementData">
											<tr>
												<td class="tbl-text-align-center"><a 
												href="javascript:getDataByPMId('${settlementData.programManagerId}','${settlementData.batchDate}')"
												style="text-decoration: underline;">${settlementData.programManagerName}
											    </a></td>
												<td>${settlementData.batchDate}</td>
												<td>${settlementData.totalAmount}</td>
												<td>${settlementData.totalTxnCount}</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr><td colspan="6" style="color: red; text-align: center;"><spring:message code="home.label.norecordsfound"/></td></tr>
									</c:otherwise>
							</c:choose>
						</tbody>
					</table>
						<table class="table table-striped table-bordered table-condensed">
								<tr class="table-footer-main">
									<td colspan="10" class="search-table-header-column">
										<div class="col-sm-12">
											<div class="col-sm-12">
												<div class="btn-toolbar" role="toolbar">
															<a href="show-All-incoming-settlement-report"><input type="button" class="form-control button pull-right dashboard-table-btn" value="<spring:message code="home.label.viewall"/>"></a>
												</div>
											</div>
										</div>
									</td>
								</tr>
						</table>
					</div>
					
					<!-- InComing Settelment Report End -->
					<!-- Pending Merchants starts -->
					<div class="dashboard-tab active-background"><spring:message code="home.label.pendingmerchants"/></div>
					<div id="pendingMerchants" class="dashboard-container">
					<table class="table table-striped table-bordered table-condensed" style="margin-bottom: 0px;">
						<tr>
								<td colspan="9" class="search-table-header-column " style="text-align: left">
									<spring:message code="home.label.pendingmerchantstab"/>
								</td>
							</tr>
					</table>
					<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
						<thead>
							<tr>
								<th style="display: none;"><spring:message code="home.label.id"/></th>
								<th style="width: 150px;"><spring:message code="home.label.companyname"/></th>
								<th style="width: 150px;"><spring:message code="home.label.address1"/></th>
								<th style="width: 150px;"><spring:message code="home.label.address2"/></th>
								<th style="width: 150px;"><spring:message code="home.label.status"/></th>
								<th style="width: 150px;"><spring:message code="home.label.action"/></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${!(fn:length(merchantSubList) eq 0)}">
										<c:forEach items="${merchantSubList}" var="pendingMerchnats">
											<tr>
												<td id = "merchantViId" style="display: none;">${pendingMerchnats.id}</td>
												<td>${pendingMerchnats.businessName}</td>
												<td>${pendingMerchnats.address1}</td>
												<td>${pendingMerchnats.address2}</td>
												<td>
												<c:if test="${pendingMerchnats.status eq 1}">
												<span><spring:message code="home.label.pending"/></span>
												</c:if>	
												<c:if test="${pendingMerchnats.status eq 4}">
												<span><spring:message code="home.label.decline"/></span>
												</c:if>												
												<c:if test="${pendingMerchnats.status eq 0}">
												<span><spring:message code="home.label.active"/></span>	
												</c:if>
												</td>
												<c:choose>
												<c:when test="${pendingMerchnats.createdBy eq adminId}">	
												<td>	</td>
												</c:when>
												<c:otherwise>
												<c:if test="${pendingMerchnats.status eq 1}">
												<td style="vertical-align:middle;text-align:center;"><input type="Button" value="<spring:message code="home.label.approve"/>" onclick="merchantView('${pendingMerchnats.id}')"></td>
												</c:if>
												<c:if test="${pendingMerchnats.status ne 1}">
												<td>     </td>
												</c:if>
												</c:otherwise>
												</c:choose>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr><td colspan="9" style="color: red; text-align: center;"><spring:message code="home.label.norecordsfound"/></td></tr>
									</c:otherwise>
							</c:choose>
						</tbody>
					</table>
						<table class="table table-striped table-bordered table-condensed">
								<tr class="table-footer-main">
									<td colspan="10" class="search-table-header-column">
										<div class="col-sm-12">
											<div class="col-sm-12">
												<div class="btn-toolbar" role="toolbar">
															<a href="show-all-pending-merchants"><input type="button" class="form-control button pull-right dashboard-table-btn" value="<spring:message code="home.label.viewall"/>"></a>
												</div>
											</div>
										</div>
									</td>
								</tr>
						</table>
					</div>
					<!-- Pending Merchants Ends -->
					<!-- Processing txns Starts -->
					<div class="dashboard-tab active-background"><spring:message code="home.label.processingtransactions"/></div>
					<div id="processingTnsDiv" class="dashboard-container" style="overflow-x:auto;">
						<table class="table table-striped table-bordered table-condensed" style="margin-bottom: 0px;">
							<!-- Search Table Header Start -->
							<tr>
								<td colspan="12" class="search-table-header-column " style="text-align: left">
									<spring:message code="home.label.transactionsummary"/>
								</td>
							</tr>
						</table>
						<!-- Search Table Header End -->
						<!-- Search Table Content Start -->
						<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
								<tr>
									<th style="width: 120px;"><spring:message code="reports.label.transactions.dateortime"/></th>
									<th style="width: 120px;"><spring:message code="admin.common-deviceLocalTxnTime"/></th>
									<th style="width: 100px;"><spring:message code="home.label.accounttransactionid"/> <br></th>
									<th style="width: 120px;"><spring:message code="home.label.transactionid"/></th>
									<th style="width: 50px;"><spring:message code="currency-search-page.label.currencycode"/></th>
									<th style="width: 50px;"><spring:message code="home.label.type"/></th>
									<th style="width: 500px;"><spring:message code="home.label.description"/></th>
									<th style="width: 80px;"><spring:message code="home.label.debit"/></th>
									<th style="width: 80px;"><spring:message code="home.label.credit"/></th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${!(fn:length(processingTxnList) eq 0)}">
										<c:forEach items="${processingTxnList}" var="processingTxns">
											<tr data-txn-merchant-code="${processingTxns.merchantCode}">
												<td>${processingTxns.transactionTime}</td>
													<td>${processingTxns.deviceLocalTxnTime}
													<c:if test="${ not empty processingTxns.timeZoneOffset}" >
														<br>(${processingTxns.timeZoneOffset})
													</c:if>
												</td>
												<td class="tbl-text-align-right"><span class="t-txn-id anchor-style">${processingTxns.transactionId}</span></td>
												<td class="tbl-text-align-right">${processingTxns.pgTransactionId}</td>
												<td class="tbl-text-align-right">${processingTxns.currency}</td>
												<c:set var="txnType" value="${fn:toUpperCase(processingTxns.type)}"></c:set>
												<%-- <c:choose>
													<c:when test="${txnType == 'S' && processingTxns.transactionCode == 'CC_AMOUNT_CREDIT'}">
														<td class="login-page-content txn-action" data-txn-status="${processingTxns.status}"><a class="anchor-style">${txnType}</a></td>
													</c:when>
													<c:otherwise>
														<td class="login-page-content">${txnType}</td>
													</c:otherwise>
												</c:choose> --%>
												<td class="login-page-content">${txnType}</td>
												<td class="tbl-text-align-left">${processingTxns.description}</td>
												<td class="tbl-text-align-right">${processingTxns.debit}</td>
												<td class="credit tbl-text-align-right">${processingTxns.credit}</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr><td colspan="9" style="color: red; text-align: center;"><spring:message code="home.label.norecordsfound"/></td></tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
						<table class="table table-striped table-bordered table-condensed">
							<c:if test="${!(fn:length(processingTxnList) eq 0)}">
								<tr class="table-footer-main">
									<td colspan="10" class="search-table-header-column">
										<div class="col-sm-12">
											<div class="col-sm-12">
												<div class="btn-toolbar" role="toolbar">
													<div class="btn-group custom-table-footer-button">
														<a href="javascript:downloadDashboardReport('${portalListPageNumber}', '<%=Constants.XLS_FILE_FORMAT%>', 'processingTxn', 'dashobard')">
															<button type="button" class="btn btn-default"><img src="../images/excel.png"> </button>
														</a> 
														<a href="javascript:downloadDashboardReport('${portalListPageNumber}', '<%=Constants.PDF_FILE_FORMAT%>', 'processingTxn', 'dashobard')">
															<button type="button" class="btn btn-default"><img src="../images/pdf.png"></button>
														</a>
													</div>
													<c:choose>
														<c:when test="${processingListSize gt 10}">
															<a href="processing-transaction-details"><input type="button" class="form-control button dashboard-table-btn pull-right" value="<spring:message code="home.label.viewall"/>"></a>
														</c:when>
														<c:otherwise>
                                                            <input type="button" class="form-control button dashboard-table-btn pull-right" value="<spring:message code="home.label.viewall"/> " disabled="disabled">														
                                                            </c:otherwise>
													</c:choose>
												</div>
											</div>
										</div>
									</td>
								</tr>
							</c:if>
						</table>
					</div>
					<!-- Processing txns Ends -->
					<!-- Completed txns Starts -->
					<div class="dashboard-tab active-background"><spring:message code="home.label.completedtransactions"/></div>
					<div id="completedTnsDiv" class="dashboard-container" style="overflow-x:auto;">
						<table class="table table-striped table-bordered table-condensed" style="margin-bottom: 0px;">
							<!-- Search Table Header Start -->
							<tr>
								<td colspan="6" class="search-table-header-column " style="text-align: left">
									<spring:message code="home.label.transactopmsummary"/>
								</td>
							</tr>
						</table>
						<!-- Search Table Header End -->
						<!-- Search Table Content Start -->
						<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
								<tr>
									<th style="width: 100px;"><spring:message code="reports.label.transactions.dateortime"/> <br></th>
									<th style="width: 100px;"><spring:message code="chatak.admin.ProcessedTime"/><br></th>
									<th style="width: 120px;"><spring:message code="admin.common-deviceLocalTxnTime"/></th>
									<th style="width: 100px;"><spring:message code="home.label.accounttransactionid"/> <br></th>
									<th style="width: 100px;"><spring:message code="home.label.transactionid"/> <br></th>
									<th style="width: 50px;"><spring:message code="currency-search-page.label.currencycode"/></th>
									<th style="width: 50px;"><spring:message code="home.label.type"/></th>
									<th style="width: 400px;"><spring:message code="home.label.description"/></th>
									<th style="width: 50px;"><spring:message code="home.label.debit"/></th>
									<th style="width: 60px;"><spring:message code="home.label.credit"/></th>
									<th style="width: 60px;"><spring:message code="fundtransfer-file-currentbalance"/><br></th>
									<th style="width: 50px;"><spring:message code="home.label.status"/></th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${!(fn:length(executedTxnList) eq 0)}">
										<c:forEach items="${executedTxnList}" var="completedTxns">
											<tr data-txn-merchant-code="${completedTxns.merchantCode}">
												<td>${completedTxns.transactionTime}</td>
												<td>${completedTxns.processedTime}</td>
												<td>${completedTxns.deviceLocalTxnTime}
													<c:if test="${ not empty completedTxns.timeZoneOffset}" >
														<br>(${completedTxns.timeZoneOffset})
													</c:if>
												</td>
												<td class="tbl-text-align-right"><span class="t-txn-id anchor-style">${completedTxns.transactionId}</span></td>
												<td class="tbl-text-align-right">${completedTxns.pgTransactionId}</td>
												<td class="tbl-text-align-right">${completedTxns.currency}</td>
												<c:set var="txnType" value="${fn:toUpperCase(completedTxns.type)}"></c:set>
												<c:choose>
													<c:when test="${txnType == 'S' && completedTxns.transactionCode == 'CC_AMOUNT_CREDIT' && (completedTxns.refundStatus == null || completedTxns.refundStatus == 0)}">
														<td class="login-page-content  txn-action" data-txn-status="${completedTxns.status}"><a class="anchor-style">${txnType}</a></td>
												</c:when>
													<c:otherwise>
														<td class="login-page-content">${txnType}</td>
													</c:otherwise>
												</c:choose>
												<td class="tbl-text-align-left">${completedTxns.description}</td>
												<td class="tbl-text-align-right">${completedTxns.debit}</td>
												<td class="credit tbl-text-align-right">${completedTxns.credit}</td>
												<td class="tbl-text-align-right">${completedTxns.currentBalance}</td>
												<td class="tbl-text-align-left">${completedTxns.status}</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr><td colspan="9" style="color: red; text-align: center;"><spring:message code="home.label.norecordsfound"/></td></tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
						<table class="table table-striped table-bordered table-condensed">
							<c:if test="${!(fn:length(executedTxnList) eq 0)}">
								<tr class="table-footer-main">
									<td colspan="10" class="search-table-header-column">
										<div class="col-sm-12">
											<div class="col-sm-12">
												<div class="btn-toolbar" role="toolbar">
													<div class="btn-group custom-table-footer-button">
														<a href="javascript:downloadDashboardReport('${portalListPageNumber}', '<%=Constants.XLS_FILE_FORMAT%>', 'exetutedTxn', 'dashobard')">
															<button type="button" class="btn btn-default"><img src="../images/excel.png"> </button>
														</a> 
														<a href="javascript:downloadDashboardReport('${portalListPageNumber}', '<%=Constants.PDF_FILE_FORMAT%>', 'exetutedTxn', 'dashobard')">
															<button type="button" class="btn btn-default"><img src="../images/pdf.png"></button>
														</a>
														<a>
															<input class="autoCheck check" id="totalRecordsDownload" type="checkbox">
															<spring:message code="manage.label.sub-merchant.downloadall"/>
														</a>
													</div>
													<c:choose>
														<c:when test="${executedListSize gt 10}">
															<a href="executed-transaction-details"><input type="button" class="form-control button pull-right" value="<spring:message code="home.label.viewall"/> "></a>
														</c:when>
														<c:otherwise>
															<input type="button" class="form-control button dashboard-table-btn pull-right" value="<spring:message code="home.label.viewall"/> " disabled="disabled">
														</c:otherwise>
													</c:choose>
												</div>
											</div>
										</div>
									</td>
								</tr>
							</c:if>
						</table>
					</div>
					<!-- Completed txns Ends -->
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
	
	<div id="txn-popup" class="txn-popup"></div>
	
	<div id="refundPopup" class="popup-void-refund refund">
		<div class="popup-hf-bc fw-b-fs15 txt-center"><span style="line-height: 2em;"><spring:message code="home.label.refundpayment"/></span></div>
		<div class="ele-container">
			<input type="radio" name="refundType" class="refundType fullRefund refund-radio" id="fullRefund" checked="checked" />
			<div class="margin-left20">
				<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="home.label.fullrefund"/></label><br> 
				<span><spring:message code="home.label.fullamount"/>(<span id="refundAmt"></span>)</span>
			</div>
		</div>
		<div class="ele-container">
			<input type="radio" name="refundType" class="refundType partialRefund refund-radio" id="partialRefund"/>
			<div class="margin-left20">
				<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="home.label.partialrefund"/></label><br> 
				<span><spring:message code="home.label.partialamount"/></span>
			</div>
		</div>
		<div id="refundAmtDiv" style="display: none;">
			<div class="txt-center">
				<span class="glyphicon glyphicon-arrow-down"></span>
			</div>
			<div class="ele-container">
				<div class="margin-left20">
					<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="home.label.refundamount"/></label> <br>
					<span class="refundAmtSymbol"><spring:message code="home.label.$"/></span><span><input type="text" id="partialRefundAmt" class="form-control" placeholder="0.00" onkeypress="return amountValidate(this,event)" onblur="validateRefundAmount(this.id)"/></span>
					<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
						<span class="red-error" id="partialRefundAmtEr">&nbsp;</span>
					</div>
				</div>
			</div>
		</div>
		<div class="fw-b-fs15 refund-note"><spring:message code="home.label.refundtaketime"/></div>
		<div class="col-sm-12 popup-hf-bc">
			<input type="submit" class="form-control button pull-right margin5" value="<spring:message code="home.label.refundbutton"/>" onclick="refundTxn();" id="buttonCreate1">
				<input type="button" value="Processing" style="display: none;" id="processingButton2" class="form-control button pull-right" /> 
			<input type="button" class="form-control button pull-right margin5 close-btn" value="<spring:message code="home.label.cancelbutton"/>">
		</div>
		<input type="hidden" id="refundMerchantId" name="merchantId" />
		<input type="hidden" id="refundAccountTransactionId" name="accountTransactionId" />
		<input type="hidden" id="refundBalanceAmount" />
		<input type="hidden" id="refundTotalTxnAmount" />
	</div>
	
	<div id="voidPopup" class="popup-void-refund void">
		<div class="popup-hf-bc fw-b-fs15 txt-center"><span style="line-height: 2em;"><spring:message code="home.label.voidpayment"/></span></div>
		<div class="fw-b-fs15" style="padding: 20px;"><spring:message code="home.label.sure"/></div>
		<div class="fw-b-fs15" style="padding: 0px 0px 20px 20px; font-style: italic; color: #bfbfbf;"></div>
		<div class="col-sm-12 popup-hf-bc">
			<input type="submit" class="form-control button pull-right margin5" value="<spring:message code="home.label.voidbutton"/>" onclick="voidTxn();"> 
			<input type="button" class="form-control button pull-right margin5 close-btn" value="<spring:message code="home.label.cancelbutton"/>" >
		</div>
		<input type="hidden" id="voidMerchantId" name="merchantId" />
		<input type="hidden" id="voidAccountTransactionId" name="accountTransactionId" />
		
	</div>
	
	<div id="voidResultPopup" class="popup-void-refund voidResult">
		<div class="popup-hf-bc fw-b-fs15 txt-center"><span style="line-height: 2em;"><spring:message code="home.label.voidpayment"/></span></div>
		<div class="col-xs-12">
			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
				<span class="red-error" id="voidErrorData">&nbsp;</span> 
				<span class="green-error" id="successData">&nbsp;</span>
			</div>
		</div>
		
		<div class="col-xs-12" id="voidResultInfo">
			<div class="fw-b-fs15"><spring:message code="home.label.pleasenote"/></div>
			<br>
			<div>
				<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="home.label.referenceno"/></label><span id="txnRefNo">&nbsp;</span> 
				<!-- <label data-toggle="tooltip" data-placement="top" title="">CG Reference No. : </label><span id="cgRefNo">&nbsp;</span>  -->
			</div>
			<br>
		</div>
		
		<div class="col-sm-12 popup-hf-bc">
			<input type="button" class="form-control button pull-right margin5 close-btn" value="<spring:message code="home.label.closebutton"/>" >
		</div>
	</div>
	
	<div id="refundResultPopup" class="popup-void-refund voidResult">
		<div class="popup-hf-bc fw-b-fs15 txt-center"><span style="line-height: 2em;"><spring:message code="home.label.refundpayment"/></span></div>
		<div class="col-xs-12">
			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
				<span class="red-error" id="refundErrorData">&nbsp;</span> 
				<span class="green-error" id="successData">&nbsp;</span>
			</div>
		</div>
		
		<div class="col-xs-12" id="refundResultInfo">
			<div class="fw-b-fs15"><spring:message code="home.label.pleasenote"/></div>
			<br>
			<div>
			    <label data-toggle="tooltip" data-placement="top" title=""><spring:message code="reports-file-exportutil-totalTransactionAmount"/> : </label><span id="TotalTxnAmount">&nbsp;</span>
				<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="home.label.referenceno"/> : </label><span id="refundTxnRefNo">&nbsp;</span> 
				<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="home.label.cgreferenceno"/> : </label><span id="cgRefNo">&nbsp;</span>
			</div>
			<br>
		</div>
		
		<div class="col-sm-12 popup-hf-bc">
			<input type="button" class="form-control button pull-right margin5 close-btn" value="<spring:message code="home.label.closebutton"/>" >
		</div>
	</div>
			
	<!--Body Wrapper block End -->
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	 <script src="../js/messages.js"></script>
	<script type="text/javascript" src="../js/transactions.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/tablesorter.js"></script>
	<script src="../js/tablesorter.widgets.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script>
		/* Select li full area function Start */
		$("li").click(function() {
			window.location = $(this).find("a").attr("href");
			return false;
		});
		/* Select li full area function End */
		$(document).ready(function() {
			highlightMainContent('navListId1');
			if('${loginType}' == "ISO"){
				$('#pendingPM').hide();
				$('#incomingsettlementreport').hide();
			}else{
				$('#pendingPM').show();
				$('#incomingsettlementreport').show();
			}
			$('#imgId').mousedown(function(){return false});
			
			$('.txn-action').on('click', function() {
				if($(this).attr('data-txn-status') == 'Processing') {
					
					$('#refundMerchantId').val($(this).parent().attr('data-txn-merchant-code'));
					$('#refundAccountTransactionId').val($(this).siblings().find('.t-txn-id').text());
					getRefundBalance();
					if($('#partialRefund').is(':checked')) {
						$('#partialRefund').removeAttr('checked');
						$('#fullRefund').prop('checked',true);
					}
					
					$('#refundAmtDiv').hide();
					$('#partialRefundAmt').val('');
					$('#partialRefundAmtEr').text('');
					$('#refundAmt').text($('#refundBalanceAmount').val());
					$('#refundPopup').popup({
						autoopen: true,
						blur : false
					});
					
				} else if($(this).attr('data-txn-status') == 'Executed' || $(this).attr('data-txn-status') == 'Refunded') {
					$('#refundMerchantId').val($(this).parent().attr('data-txn-merchant-code'));
					$('#refundAccountTransactionId').val($(this).siblings().find('.t-txn-id').text());
					getRefundBalance();
					if($('#partialRefund').is(':checked')) {
						$('#partialRefund').removeAttr('checked');
						$('#fullRefund').prop('checked',true);
					}
					$('#refundTotalTxnAmount').val($('#refundBalanceAmount').val()	);
					$('#refundAmtDiv').hide();
					$('#partialRefundAmt').val('');
					$('#partialRefundAmtEr').text('');
					$('#refundAmt').text($('#refundBalanceAmount').val());
					//$('#refundPopup').popup("show");
					
					$('#refundPopup').popup({
						autoopen: true,
						blur : false
					});
				}
			});
			
			$('.refundType').on('click', function() {
				if($(this).hasClass('fullRefund')) {
					$('#partialRefundAmt').val('');
					$('#partialRefundAmtEr').text('');
					$('#refundAmtDiv').hide();
				} else {
					$('#partialRefundAmt').text('');
					$('#refundAmtDiv').show();
				}
			}); 
			
			$('.close-btn').on('click', function() {
				if($(this).parent().parent().hasClass('void')) {
					$('#voidPopup').popup("hide");
				} else if($(this).parent().parent().hasClass('refund')) {
					$('#refundPopup').popup("hide");
				} else if($(this).parent().parent().hasClass('voidResult')) {
					goToDashboard();
				} 
			});
			
			$('#voidResultPopup').popup({
				onclose: function() {
					goToDashboard();
				  }
			});
			
			$('#refundResultPopup').popup({
				onclose: function() {
					goToDashboard();
				  }
			});
		});
		
		function voidTxn() {
			var csrfToken = $("input[name=CSRFToken]").val();
			$.ajax({
				type : "POST",
				url : "process-popup-action",
				data: { merchantId: $('#voidMerchantId').val(), accountTransactionId: $('#voidAccountTransactionId').val(), transactionType: 'VOID', CSRFToken: csrfToken},
				success : function(response) {
					$('#voidPopup').popup("hide");
					if(response.errorCode == '00') {
						
						$('#txnRefNo').text(response.txnRefNumber);
						//$('#cgRefNo').text(errorMessage);
						
					} else {
						$('#voidResultInfo').hide();
						$('#voidErrorData').text(response.errorMessage);
					}
					$('#voidResultPopup').popup("show");
					
				},
				error : function(e) {
				}
			});
			//document.forms["popup-action-process"].submit();
		}
		
		function refundTxn() {
			get('processingButton2').style.display = 'inline-block';
			get('buttonCreate1').style.display = 'none';
			var isPartialRefund = $('#fullRefund').is(':checked') ? 0 : 1;	
			var refundAmount = 0;
			if(isPartialRefund == 1) {
				if(!validateRefundAmount('partialRefundAmt')) {
					return;
				} else {
					refundAmount = $('#partialRefundAmt').val() * 100;
				}
			}  else if(isPartialRefund == 0 ) {
				if(parseFloat( parseFloat($('#refundTotalTxnAmount').val())* 100) >= (parseFloat($('#refundBalanceAmount').val()) * 100)){
					isPartialRefund=1;
					refundAmount=parseFloat($('#refundBalanceAmount').val()) * 100;
					
				} else if(parseFloat($('#refundAmt').text() * 100) > (parseFloat($('#refundBalanceAmount').val()) * 100)){
					$('#refundAmtEr').text('Amount Exceeds Refund Balance Amount('+$('#refundBalanceAmount').val()+').');
					return false;
				}
				
				else {
					refundAmount = $('#refundAmt').text().substring(4, 11) * 100;
				}
			}
			var csrfToken = $("input[name=CSRFToken]").val();
			$.ajax({
				type : "POST",
				url : "process-popup-action",
				data: { merchantId: $('#refundMerchantId').val(), 
						accountTransactionId: $('#refundAccountTransactionId').val(), 
						transactionType: 'REFUND',
						partialRefundFlag: isPartialRefund,
						timeZoneOffset: new Date().toString().match(/([A-Z]+[\+-][0-9]+)/)[1],
						timeZoneRegion: jstz.determine().name(),
						totalTxnAmount: Math.floor(refundAmount), CSRFToken: csrfToken},
				success : function(response) {
					$('#refundPopup').popup("hide");
					if(response.errorCode == '00') {
						$("#TotalTxnAmount").text(response.totalTxnAmount);
						$('#refundTxnRefNo').text(response.txnRefNumber);
						$('#cgRefNo').text(response.cgRefNumber);
						
					} else {
						get('buttonCreate1').style.display = 'inline-block';
						get('processingButton2').style.display = 'none';
						$('#refundResultInfo').hide();
						$('#refundErrorData').text(response.errorMessage);
					}
					$('#refundResultPopup').popup("show");
					
				},
				error : function(e) {
				}
			});
		}
		
		function merchantView(id){
			get('merchantViewId').value = id;
			document.forms["viewPendingMerchant"].submit();
			
		}
		function getRefundBalance() {
			var csrfToken = $("input[name=CSRFToken]").val();
			$.ajax({
				type : "POST",
				url : "chatak-partial-refund-balance",
				data: { merchantCode: $('#refundMerchantId').val(), accountTransactionId: $('#refundAccountTransactionId').val(), CSRFToken: csrfToken},
				async:false,
				success : function(response) {
					
					if(null!=response) {
						$('#refundBalanceAmount').val(response);
						$('#refundSubmit').show();
					} else {
						$('#refundErrorData').text('Refund Already Completed');
						$('#refundSubmit').hide();
						
					}
					//$('#voidResultPopup').popup("show");
					
				},
				error : function(e) {
				}
			});
		}
		function getDataByPMId(programManagerId, batchDate) {
			get('programViewId').value = programManagerId;
			get('batchDate').value = batchDate;
			document.forms["getSettlementDetails"].submit();
		}
	</script>
</body>
</html>