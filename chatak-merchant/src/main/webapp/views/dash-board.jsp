<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ page import="com.chatak.merchant.constants.StatusConstants"%>
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
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
<style type="text/css">
.txnDescDiv {
	width: 500px;
	max-width: 500px;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

.txnDescDiv:hover {
	white-space: pre-wrap;
	width: 500px;
	overflow: visible;
	word-break: break-all;
}

.txnDescDiv1 {
	width: 400px;
	max-width: 400px;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

.txnDescDiv1:hover {
	white-space: pre-wrap;
	width: 400px;
	overflow: visible;
	word-break: break-all;
}
</style>
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
<script src="../js/jquery.cookie.js"></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/jstimezonedetect/1.0.4/jstz.min.js'></script>
<script src="../js/common-lib.js"></script>
<script src="../js/messages.js"></script>
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<jsp:include page="header.jsp"></jsp:include>
			<!-- Header Block End -->
			<!--Navigation Block Start -->
			<nav class="col-sm-12 nav-bar" id="main-navigation">
				<%@include file="navigation-panel.jsp"%>
			</nav>
			<!--Navigation Block End -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">

					<form:form action="executed-transaction-details-report"
						name="downloadExecutedTxnReport" method="post">
						<input type="hidden" id="executedTxnDownloadPageNumberId" name="downLoadPageNumber" /> 
						<input type="hidden" id="executedTxnDownloadTypeId" name="downloadType" /> 
						<input type="hidden" id="requestFromId" name="requestFrom" />
						<input type="hidden" id="totalRecords" name="totalRecords" />
						<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>

					<form action="processing-transaction-details-report"
						name="downloadProcessingTxnReport" method="post">
						<input type="hidden" id="processingTxnDownloadPageNumberId"
							name="downLoadPageNumber" /> <input type="hidden"
							id="processingTxnDownloadTypeId" name="downloadType" /> <input
							type="hidden" id="requestFromId1" name="requestFrom" />
							<input type="hidden" id="totalRecords1" name="totalRecords" />
							<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form>

					<form action="manual-transaction-details-report"
						name="downloadDashboardReport" method="post">
						<input type="hidden" id="downloadManualTxnPageNumberId"
							name="downLoadPageNumber" /> <input type="hidden"
							id="downloadManualTxnTypeId" name="downloadType" /> <input
							type="hidden" id="requestManualTxnFromId" name="totalRecords" />
							<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form>

					<!--Success and Failure Message Start-->
					<div class="search-results-table">
					<input type="hidden" name="CSRFToken" value="${tokenval}">
						<form name="account" action="merchant-account-history"
							method="post">
							<input type="hidden" name="CSRFToken" value="${tokenval}">
							<div style="margin-top: 10px; margin-left: 240px;">
								<label data-toggle="tooltip" data-placement="top" title="" id="entityName"> &nbsp; </label><span
									class="fontweight">${merchantBusinessName}</span>
							</div>
							<input type="hidden" id="userType" value=${userType}>
							<div style="margin-top: 10px; margin-left: 240px;">
								<label data-toggle="tooltip" data-placement="top" title="" id="entityCode"> &nbsp; </label><span
									class="fontweight">${merchantCode}</span>
							</div>

							<table class="table table-bordered table-condensed"
								style="width: 50% !important; margin-left: 240px;">
								<!-- Search Table Header Start -->
								<!-- Search Table Header End -->
								<!-- Search Table Content Start -->
								<tr>
									<th><spring:message code="dash-board.label.accountnumber" /></th>
									<th><spring:message code="dash-board.label.summary" /></th>
								</tr>
								<tr>
									<c:if test="${not empty accountDetails.entityId }">

										<c:catch var="catchException">

											<fmt:setLocale value="en_US" />
											<td><a
												href="javascript:getDetailsOnAccountNumber(${accountDetails.accountNum })"
												style="text-decoration: underline;">${accountDetails.accountNum }
											</a></td>

											<td><span class="col-sm-6 tbl-text-align-right"><spring:message
														code="dash-board.label.availablebalance" /> : </span>
														${currencyAlpha} ${accountDetails.availableBalanceString}
														<br> <span class="col-sm-6 tbl-text-align-right"><spring:message
														code="dash-board.label.currentbalance" /> &nbsp;&nbsp;: </span>
														${currencyAlpha} ${accountDetails.currentBalanceString}
											</td>
										</c:catch>
										<c:if test="${catchException != null}">
											<p>
												The exception is : ${catchException} <br /> There is an
												exception: ${catchException.message}
											</p>
										</c:if>
									</c:if>
								<tr>
							</table>

							<input id="accountId" type="text" name="accountNumber"
								value="${accountDetails.accountNum }" hidden="true" />
						</form>

						<!-- Recent txns start -->
					</div>
					<!-- Completed txns Starts -->
					<div class="dashboard-tab active-background">
						<spring:message code="dash-board.label.completedtransactions" />
					</div>
					<div id="completedTnsDiv" class="dashboard-container">
						<table class="table table-striped table-bordered table-condensed"
							style="margin-bottom: 0px;">
							<!-- Search Table Header Start -->
							<tr>
								<td colspan="6" class="search-table-header-column "
									style="text-align: left"><spring:message
										code="dash-board.label.transactionsummary" /></td>
							</tr>
						</table>
						<!-- Search Table Header End -->
						<!-- Search Table Content Start -->
						<table id="serviceResults1"
							class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
								<tr>
									<th style="width: 150px;"><spring:message
											code="dash-board.label.transactiontime" /></th>
									<th style="width: 150px;"><spring:message
											code="merchant.common-deviceLocalTxnTime" /></th>
									<th style="width: 150px;"><spring:message
											code="dash-board.label.processedtime" /></th>
									<th style="width: 100px;"><spring:message
											code="dash-board.label.accounttransactionid" /> <br></th>
									<th style="width: 100px;"><spring:message
											code="dash-board.label.transactionid" /></th>
									<th style="width: 50px;"><spring:message
											code="search-sub-merchant.label.currencycode" /></th>
									<th style="width: 50px;"><spring:message
											code="dash-board.label.type" /></th>
									<th style="width: 400px;"><spring:message
											code="dash-board.label.description" /></th>
									<th style="width: 50px;"><spring:message
											code="dash-board.label.debit" /></th>
									<th style="width: 60px;"><spring:message
											code="dash-board.label.credit" /></th>
									<th style="width: 60px;"><spring:message
											code="dash-board.label.currentbalance" /></th>
									<th style="width: 50px;"><spring:message
											code="dash-board.label.status" /></th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${!(fn:length(executedTxnList) eq 0)}">
										<c:forEach items="${executedTxnList}" var="completedTxns">
											<tr data-txn-merchant-code="${completedTxns.merchantCode}">
												<td>${completedTxns.transactionTime}</td>
												<td>${completedTxns.deviceLocalTxnTime}<c:if
														test="${ not empty completedTxns.timeZoneOffset}">
														<br>(${completedTxns.timeZoneOffset})
													</c:if>
												</td>
												<td>${completedTxns.processedTime}</td>
												<td class="tbl-text-align-right"><span
													class="t-txn-id anchor-style">${completedTxns.transactionId}</span></td>
												<td class="tbl-text-align-right">${completedTxns.pgTransactionId}</td>
												<td class="tbl-text-align-right">${completedTxns.currency}</td>
												<c:set var="txnType"
													value="${fn:toUpperCase(completedTxns.type)}"></c:set>
												<c:choose>
													<c:when
														test="${txnType == 'S' && completedTxns.transactionCode == 'CC_AMOUNT_CREDIT' && (completedTxns.refundStatus == null || completedTxns.refundStatus == 0)}">
														<td class="login-page-content  txn-action"
															data-txn-status="${completedTxns.status}"><a
															class="anchor-style">${txnType}</a></td>
													</c:when>
													<c:otherwise>
														<td class="login-page-content">${txnType}</td>
													</c:otherwise>
												</c:choose>
												<td class="tbl-text-align-left">${completedTxns.description}</td>
												<td class="tbl-text-align-right">${completedTxns.debit}</td>
												<td class="credit tbl-text-align-right">${completedTxns.credit}</td>
												<td class="tbl-text-align-right">${completedTxns.currentBalance}</td>
												<td>${completedTxns.status}</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="10" style="color: red; text-align: center;"><spring:message
													code="dash-board.label.norecordsfound" /></td>
										</tr>
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
														<a
															href="javascript:downloadDashboardReport('${portalListPageNumber}', '<%=Constants.XLS_FILE_FORMAT%>', 'exetutedTxn', 'dashobard', ${totalRecords})">
															<button type="button" class="btn btn-default">
																<img src="../images/excel.png">
															</button>
														</a> <a
															href="javascript:downloadDashboardReport('${portalListPageNumber}', '<%=Constants.PDF_FILE_FORMAT%>', 'exetutedTxn', 'dashobard', ${totalRecords})">
															<button type="button" class="btn btn-default">
																<img src="../images/PDF.png">
															</button>
														</a>
													</div>
													<c:choose>
														<c:when test="${executedListSize gt 10}">
															<a href="executed-transaction-details"><input
																type="button" style="margin: 0px;"
																class="form-control button pull-right"
																value="<spring:message code="dash-board.label.viewall"/>"></a>
														</c:when>
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
					<!-- Manual Credit & Debit txns start -->
					<div class="dashboard-tab active-background">
						<spring:message
							code="reports.label.balancereports.manualtransactions" />
					</div>
					<div id="manualTransactionDiv" class="dashboard-container">
						<table class="table table-striped table-bordered table-condensed"
							style="margin-bottom: 0px;">
							<!-- Search Table Header Start -->
							<tr>
								<td colspan="6" class="search-table-header-column "
									style="text-align: left"><spring:message
										code="dash-board.label.transactionsummary" /></td>
							</tr>
						</table>
						<!-- Search Table Header End -->
						<!-- Search Table Content Start -->
						<table id="serviceResults2"
							class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
								<tr>
									<th style="width: 85px;"><spring:message
											code="dash-board.label.transactiontime" /></th>
									<th style="width: 85px;"><spring:message
											code="merchant.common-deviceLocalTxnTime" /></th>
									<th style="width: 100px;"><spring:message
											code="reports.label.balancereports.manualtransactions.description" /><br></th>
									<th style="width: 150px;"><spring:message
											code="reports.label.balancereports.manualtransactions.merchantorsubmerchantcode" /></th>
									<th style="width: 50px;"><spring:message
											code="reports.label.balancereports.manualtransactions.transactionID" /></th>
									<th style="width: 80px;"><spring:message
											code="search-sub-merchant.label.currencycode"/></th>
									<th style="width: 80px;"><spring:message
											code="reports.label.balancereports.manualtransactions.availableBalance" /></th>
									<th style="width: 80px;"><spring:message
											code="reports.label.balancereports.manualtransactions.credit" /></th>
									<th style="width: 80px;"><spring:message
											code="reports.label.balancereports.manualtransactions.debit" /></th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when
										test="${!(fn:length(manualTransactionsReportList) eq 0) }">
										<c:forEach items="${manualTransactionsReportList}"
											var="transaction">
											<tr>
												<td>${transaction.transactionTime }</td>
												<td>${transaction.deviceLocalTxnTime}<c:if
														test="${ not empty transaction.timeZoneOffset}">
														<br>(${transaction.timeZoneOffset})
													</c:if>
												</td>
												<td class="tbl-text-align-left">${transaction.description }</td>
												<td class="tbl-text-align-right">${transaction.merchantCode }</td>
												<td class="tbl-text-align-right">${transaction.transactionId }</td>
												<td class="tbl-text-align-right">${transaction.currency }</td>
												<td class="tbl-text-align-right">${transaction.currentBalance }</td>
												<c:choose>
													<c:when
														test="${transaction.transactionCode eq 'MANUAL_DEBIT' }">
														<td></td>
														<td class="tbl-text-align-right">${transaction.debit }</td>
													</c:when>
													<c:otherwise>
														<td class="tbl-text-align-right">${transaction.credit }</td>
														<td></td>
													</c:otherwise>
												</c:choose>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="8" style="color: red; text-align: center;"><spring:message
													code="dash-board.label.norecordsfound" /></td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
						<table class="table table-striped table-bordered table-condensed">
							<c:if test="${!(fn:length(manualTransactionsReportList) eq 0)}">
								<tr class="table-footer-main">
									<td colspan="10" class="search-table-header-column">
										<div class="col-sm-12">
											<div class="col-sm-12">
												<div class="btn-toolbar" role="toolbar">
													<div class="btn-group custom-table-footer-button">
														<a
															href="javascript:downloadDashboardReport('${portalListPageNumber}', '<%=Constants.XLS_FILE_FORMAT%>', 'manualTxn', 'dashobard')">
															<button type="button" class="btn btn-default">
																<img src="../images/excel.png">
															</button>
														</a> <a
															href="javascript:downloadDashboardReport('${portalListPageNumber}', '<%=Constants.PDF_FILE_FORMAT%>', 'manualTxn', 'dashobard')">
															<button type="button" class="btn btn-default">
																<img src="../images/PDF.png">
															</button>
														</a>
													</div>
													<c:choose>
														<c:when test="${manualTransactionsListSize gt 10}">
															<a href="manual-transaction-details"><input
																type="button" class="form-control button pull-right"
																value='<spring:message code="dash-board.label.viewall"/>'></a>
														</c:when>
													</c:choose>
												</div>
											</div>
										</div>
									</td>
								</tr>
							</c:if>
						</table>
					</div>
					<!-- Manual Credit & Debit txns Ends -->
				</div>
			</article>
			<!--Article Block End-->

			<jsp:include page="footer.jsp"></jsp:include>
		</div>
		<!--Container block End -->
	</div>

	<div id="txn-popup" class="txn-popup"></div>

	<div id="refundPopup" class="popup-void-refund refund">
		<div class="popup-hf-bc fw-b-fs15 txt-center">
			<span style="line-height: 2em;"><spring:message
					code="dash-board.label.refundpayment" /></span>
		</div>
		<div class="ele-container">
			<input type="radio" name="refundType"
				class="refundType fullRefund refund-radio" id="fullRefund"
				checked="checked" />
			<div class="margin-left20">
				<label data-toggle="tooltip" data-placement="top" title=""><spring:message
						code="dash-board.label.fullrefund" /></label><br> <span><spring:message
						code="dash-board.label.refundthefullamount" />(<span
					id="refundAmt"></span>)</span>
			</div>
		</div>
		<div class="ele-container">
			<input type="radio" name="refundType"
				class="refundType partialRefund refund-radio" id="partialRefund" />
			<div class="margin-left20">
				<label data-toggle="tooltip" data-placement="top" title=""><spring:message
						code="dash-board.label.partialrefund" /> </label><br> <span><spring:message
						code="dash-board.label.refundapartialamount" /></span>
			</div>
		</div>
		<div id="refundAmtDiv" style="display: none;">
			<div class="txt-center">
				<span class="glyphicon glyphicon-arrow-down"></span>
			</div>
			<div class="ele-container">
				<div class="margin-left20">
					<label data-toggle="tooltip" data-placement="top" title=""><spring:message
							code="dash-board.label.refundamount" />:</label> <br> <span
						class="refundAmtSymbol"></span><span><input type="text"
						id="partialRefundAmt" class="form-control" placeholder="0.00"
						onkeypress="return amountValidate(this,event)"
						onblur="validateRefundAmount(this.id)" /></span>
					<div class="discriptionErrorMsg" data-toggle="tooltip"
						data-placement="top" title="">
						<span class="red-error" id="partialRefundAmtEr">&nbsp;</span>
					</div>
				</div>
			</div>
		</div>
		<div class="fw-b-fs15 refund-note">
			<spring:message
				code="dash-board.label.refundtake5to10daystoappearonyourcustomer'sstatement"></spring:message>
			.
		</div>
		<div class="col-sm-12 popup-hf-bc">
			<input type="submit" class="form-control button pull-right margin5"
				value="<spring:message code="transactions-search.label.refundbutton"></spring:message>" onclick="refundTxn();" id="buttonCreate1"><input
				type="button" value="Processing" style="display: none;"
				id="processingButton2" class="form-control button pull-right" /> <input type="button"
				class="form-control button pull-right margin5 close-btn"
				value="<spring:message code="transactions-search.label.cancelbutton"></spring:message>">
		</div>
		<input type="hidden" id="refundMerchantId" name="merchantId" /> <input
			type="hidden" id="refundAccountTransactionId"
			name="accountTransactionId" /> <input type="hidden"
			id="refundBalanceAmount" /> <input type="hidden"
			id="refundTotalTxnAmount" />
	</div>

	<div id="voidPopup" class="popup-void-refund void">
		<div class="popup-hf-bc fw-b-fs15 txt-center">
			<span style="line-height: 2em;"><spring:message
					code="dash-board.label.voidpayment" /></span>
		</div>
		<div class="fw-b-fs15" style="padding: 20px;">
			<spring:message
				code="dash-board.label.areyousureyouwanttovoidthispayment" />
			?
		</div>
		<div class="fw-b-fs15"
			style="padding: 0px 0px 20px 20px; font-style: italic; color: #bfbfbf;">
			<spring:message code="dash-board.label.thiscanNOTbeundone" />
		</div>
		<div class="col-sm-12 popup-hf-bc">
			<input type="submit" class="form-control button pull-right margin5"
				value="Void" onclick="voidTxn();"> <input type="button"
				class="form-control button pull-right margin5 close-btn"
				value="Cancel">
		</div>
		<input type="hidden" id="voidMerchantId" name="merchantId" /> <input
			type="hidden" id="voidAccountTransactionId"
			name="accountTransactionId" />

	</div>

	<div id="voidResultPopup" class="popup-void-refund voidResult">
		<div class="popup-hf-bc fw-b-fs15 txt-center">
			<span style="line-height: 2em;"><spring:message
					code="dash-board.label.voidpayment" /></span>
		</div>
		<div class="col-xs-12">
			<div class="discriptionErrorMsg" data-toggle="tooltip"
				data-placement="top" title="">
				<span class="red-error" id="voidErrorData">&nbsp;</span> <span
					class="green-error" id="successData">&nbsp;</span>
			</div>
		</div>

		<div class="col-xs-12" id="voidResultInfo">
			<div class="fw-b-fs15">
				<spring:message
					code="dash-board.label.pleasenotedownthebelowinformation" />
				:
			</div>
			<br>
			<div>
				<label data-toggle="tooltip" data-placement="top" title=""><spring:message
						code="dash-board.label.transactionreferenceno" /> : </label><span
					id="txnRefNo">&nbsp;</span>
			</div>
			<br>
		</div>

		<div class="col-sm-12 popup-hf-bc">
			<input type="button"
				class="form-control button pull-right margin5 close-btn"
				value="Close">
		</div>
	</div>

	<div id="refundResultPopup" class="popup-void-refund voidResult">
		<div class="popup-hf-bc fw-b-fs15 txt-center">
			<span style="line-height: 2em;"><spring:message
					code="dash-board.label.refundpayment" /></span>
		</div>
		<div class="col-xs-12">
			<div class="discriptionErrorMsg" data-toggle="tooltip"
				data-placement="top" title="">
				<span class="red-error" id="refundErrorData">&nbsp;</span> <span
					class="green-error" id="successData">&nbsp;</span>
			</div>
		</div>

		<div class="col-xs-12" id="refundResultInfo">
			<div class="fw-b-fs15">
				<spring:message
					code="dash-board.label.pleasenotedownthebelowinformation" />
				:
			</div>
			<br>
			<div>
				<label data-toggle="tooltip" data-placement="top" title=""><spring:message
						code="reportFileExportUtil.total.amount" /> : </label><span
					id="TotalTxnAmount">&nbsp;</span> <label data-toggle="tooltip"
					data-placement="top" title=""><spring:message
						code="dash-board.label.transactionreferenceno" /> : </label><span
					id="refundTxnRefNo">&nbsp;</span> <label data-toggle="tooltip"
					data-placement="top" title=""><spring:message
						code="dash-board.label.CGreferenceno" /> : </label><span id="cgRefNo">&nbsp;</span>
			</div>
			<br>
		</div>

		<div class="col-sm-12 popup-hf-bc">
			<input type="button"
				class="form-control button pull-right margin5 close-btn"
				value="Close">
		</div>
	</div>

	<!--Body Wrapper block End -->

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->

	<script src="../js/backbutton.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/sortable.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/changePassword.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>

	<script>
		/* Select li full area function Start */
		$("li").click(function() {
			window.location = $(this).find("a").attr("href");
			return false;
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
			$(function() {
				
				  // call the tablesorter plugin
				  $('#serviceResults1').sortable({
					
					 divBeforeTable: '#divbeforeid',
					divAfterTable: '#divafterid',
					initialSort: false,
					locale: 'th',
					//negativeSort: [1, 2]
				});
		});
			$(function() {
				
				  // call the tablesorter plugin
				  $('#serviceResults2').sortable({
					
					 divBeforeTable: '#divbeforeid',
					divAfterTable: '#divafterid',
					initialSort: false,
					locale: 'th',
					//negativeSort: [1, 2]
				});
		});
			});
		
		/* Select li full area function End */
		$(document)
				.ready(
						function() {
							var usertype = $('#userType').val();
							if ($('#userType').val() == 'Merchant') {
								document.getElementById('entityName').innerHTML = "<spring:message code="search-sub-merchant.label.merchantcompanyname" /> : ";
								document.getElementById('entityCode').innerHTML = "<spring:message code="reports.label.merchantcode" /> : ";
							} else {
								document.getElementById('entityName').innerHTML = "<spring:message code="sub-merchant-account-search.label.sub-merchantname" /> : ";
								document.getElementById('entityCode').innerHTML = "<spring:message code="merchantFileExportUtil.merchant.code" /> : ";
							}
							highlightMainContent('navListId1');

							$('.txn-action')
									.on(
											'click',
											function() {
												if ($(this).attr(
														'data-txn-status') == 'Processing') {

													$('#refundMerchantId')
															.val(
																	$(this)
																			.parent()
																			.attr(
																					'data-txn-merchant-code'));
													$(
															'#refundAccountTransactionId')
															.val(
																	$(this)
																			.siblings()
																			.find(
																					'.t-txn-id')
																			.text());
													getRefundBalance();
													if ($('#partialRefund').is(
															':checked')) {
														$('#partialRefund')
																.removeAttr(
																		'checked');
														$('#fullRefund')
																.prop(
																		'checked',
																		true);
													}

													$('#refundAmtDiv').hide();
													$('#partialRefundAmt').val(
															'');
													$('#partialRefundAmtEr')
															.text('');
													$('#refundAmt')
															.text(
																	$(
																			'#refundBalanceAmount')
																			.val());
													$('#refundPopup').popup({
														autoopen : true,
														blur : false
													});

												} else if ($(this).attr(
														'data-txn-status') == 'Executed'
														|| $(this)
																.attr(
																		'data-txn-status') == 'Refunded') {
													$('#refundMerchantId')
															.val(
																	$(this)
																			.parent()
																			.attr(
																					'data-txn-merchant-code'));
													$(
															'#refundAccountTransactionId')
															.val(
																	$(this)
																			.siblings()
																			.find(
																					'.t-txn-id')
																			.text());
													getRefundBalance();
													if ($('#partialRefund').is(
															':checked')) {
														$('#partialRefund')
																.removeAttr(
																		'checked');
														$('#fullRefund')
																.prop(
																		'checked',
																		true);
													}
													$('#refundTotalTxnAmount')
															.val(
																	$(
																			'#refundBalanceAmount')
																			.val());
													$('#refundAmtDiv').hide();
													$('#partialRefundAmt').val(
															'');
													$('#partialRefundAmtEr')
															.text('');
													$('#refundAmt')
															.text(
																	$(
																			'#refundBalanceAmount')
																			.val());

													$('#refundPopup').popup({
														autoopen : true,
														blur : false
													});
												}

												$('#voidResultPopup').popup({
													onclose : function() {
														goToDashboard();
													}
												});

												$('#refundResultPopup').popup({
													onclose : function() {
														goToDashboard();
													}
												});
											});

							$('.refundType').on('click', function() {
								if ($(this).hasClass('fullRefund')) {
									$('#partialRefundAmt').val('');
									$('#partialRefundAmtEr').text('');
									$('#refundAmtDiv').hide();
								} else {
									$('#partialRefundAmt').text('');
									$('#refundAmtDiv').show();
								}
							});

							$('.close-btn').on(
									'click',
									function() {
										if ($(this).parent().parent().hasClass(
												'void')) {
											$('#voidPopup').popup("hide");
										} else if ($(this).parent().parent()
												.hasClass('refund')) {
											$('#refundPopup').popup("hide");
										} else if ($(this).parent().parent()
												.hasClass('voidResult')) {
											goToDashboard();
										}
									});

							$('#voidResultPopup').popup({
								onclose : function() {
									goToDashboard();
								}
							});

							$('#refundResultPopup').popup({
								onclose : function() {
									goToDashboard();
								}
							});
						});

		function getDetailsOnAccountNumber(accountId) {
			document.forms["account"].submit();
		}

		function voidTxn() {

			$
					.ajax({
						type : "POST",
						url : "process-popup-action",
						data : {
							merchantId : $('#voidMerchantId').val(),
							accountTransactionId : $(
									'#voidAccountTransactionId').val(),
							transactionType : 'VOID'
						},
						success : function(response) {
							$('#voidPopup').popup("hide");
							if (response.errorCode == '00') {

								$('#txnRefNo').text(response.txnRefNumber);

							} else {
								$('#voidResultInfo').hide();
								$('#voidErrorData').text(response.errorMessage);
							}
							$('#voidResultPopup').popup("show");

						},
						error : function(e) {
						}
					});
		}

		function refundTxn() {
			get('processingButton2').style.display = 'inline-block';
			get('buttonCreate1').style.display = 'none';
			var isPartialRefund = $('#fullRefund').is(':checked') ? 0 : 1;
			var refundAmount = 0;
			if (isPartialRefund == 1) {
				if (!validateRefundAmount('partialRefundAmt')) {
					return;
				} else {
					refundAmount = $('#partialRefundAmt').val() * 100;
				}
			} else if (isPartialRefund == 0) {
				if (parseFloat(parseFloat($('#refundTotalTxnAmount').val()) * 100) >= (parseFloat($(
						'#refundBalanceAmount').val()) * 100)) {
					isPartialRefund = 1;
					refundAmount = parseFloat($('#refundBalanceAmount').val()) * 100;

				} else if (parseFloat($('#refundAmt').text() * 100) > (parseFloat($(
						'#refundBalanceAmount').val()) * 100)) {
					$('#refundAmtEr').text(
							'Amount Exceeds Refund Balance Amount('
									+ $('#refundBalanceAmount').val() + ').');
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
				data : {
					merchantId : $('#refundMerchantId').val(),
					accountTransactionId : $('#refundAccountTransactionId')
							.val(),
					transactionType : 'REFUND',
					partialRefundFlag : isPartialRefund,
					totalTxnAmount : Math.floor(refundAmount),
					timeZoneOffset: new Date().toString().match(/([A-Z]+[\+-][0-9]+)/)[1],
					timeZoneRegion: jstz.determine().name(),CSRFToken: csrfToken
				},
				success : function(response) {
					$('#refundPopup').popup("hide");
					if (response.errorCode == '00') {
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
		function getRefundBalance() {
			var csrfToken = $("input[name=CSRFToken]").val();
			$.ajax({
				type : "POST",
				url : "chatak-partial-refund-balance",
				data : {
					merchantCode : $('#refundMerchantId').val(),
					accountTransactionId : $('#refundAccountTransactionId')
							.val(), CSRFToken: csrfToken
				},
				async : false,
				success : function(response) {

					if (null != response) {
						$('#refundBalanceAmount').val(response);
						$('#refundSubmit').show();
					} else {
						$('#refundErrorData').text('Refund Already Completed');
						$('#refundSubmit').hide();

					}

				},
				error : function(e) {
				}
			});
		}
	</script>
</body>
</html>