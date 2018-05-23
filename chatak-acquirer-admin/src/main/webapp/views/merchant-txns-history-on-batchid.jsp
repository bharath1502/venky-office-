<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ page import="com.chatak.pg.constants.PGConstants"%>
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
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
<script src="../js/jquery.cookie.js"></script>
<script src="../js/common-lib.js"></script>
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<!--Navigation Block Start -->
				<%-- <jsp:include page="header.jsp"></jsp:include> --%>
				<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="chatak-report-lable-schedule-report"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="chatak-report-lable-daily-unding-report"/></span>
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="report-transactions-history"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="report-transactions-history"/></a>
					</div>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error">&nbsp;${error }</span> 
										<span class="green-error">&nbsp;${sucess }</span> 
									</div>
								</div>

								<form:form action="get-transaction-history-report" name="downloadReport" method="post">
									<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" /> 
									<input type="hidden" id="downloadTypeId" name="downloadType" />
									<input type="hidden" id="totalRecords" name="totalRecords" />
									<input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
								</form:form>
								<form:form action="get-transaction-history-pagination" name="paginationForm" method="post">
									<input type="hidden" id="pageNumberId" name="pageNumber" /> 
									<input type="hidden" id="totalRecordsId" name="totalRecords" />
								</form:form>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<!-- Page Form End -->
							</div>
						</div>
					<!-- </div> -->
					<!-- Content Block End -->

					<!-- Search Table Block Start -->
                     <c:if test="${flag ne false }">
					<div class="search-results-table search-table-align" id="checkb">

						<!-- <table class="table table-striped table-bordered table-condensed" style="margin: 1px;">

						</table> -->
						<table id="serviceResults"
								class="table table-striped table-bordered table-responsive table-condensed tablesorter">
								<thead>
									<tr>
										<th style="width: 30px;"><spring:message code="reports.label.transactions.dateortime"/></th>
										<th style="width: 50px;"><spring:message code="admin.common-deviceLocalTxnTime"/></th>
										<th style="width: 50px;"><spring:message code="reports.label.transactions.transactionID"/></th>
										<th style="width: 50px;"><spring:message code="reports.label.balancereports.merchantorsubmerchantName"/></th>
										<th style="width: 50px;"><spring:message code="reports.label.balancereports.merchantorsubmerchantcode"/></th>
										<th style="width: 50px;"><spring:message code="reports.label.transactions.processtransactionID"/></th>
										<th style="width: 40px;"><spring:message code="transaction-report-batchID"/></th>
										<th style="width: 60px;"><spring:message code="reports.label.transactions.cardnumberField"/></th>
										<th style="width: 40px;"><spring:message code="currency-search-page.label.currency"/></th>
										<th style="width: 40px;"><spring:message code="transaction-file-exportutil-totaltxnamt"/></th>
										<th style="width: 50px;"><spring:message code="reports.label.transactions.txntype"/></th>
										<th style="width: 50px;"><spring:message code="reports.label.transactions.status" /></th>
									</tr>
								</thead>
								<c:choose>
									<c:when test="${!(fn:length(transactions) eq 0) }">
									<c:forEach items="${transactions}" var="transaction">
											<tr>
							                    		<tr data-txn-obj='${transaction.txnJsonString}' id='${transaction.transactionId }'>
							                      			<td class="alignleft" style="width: 100px;">${transaction.transactionDate}</td>
													<td style="width: 10px;">${transaction.deviceLocalTxnTime}
														<c:if test="${ not empty transaction.timeZoneOffset}">
															<br>(${transaction.timeZoneOffset})
													</c:if>
													</td>
													<td style="width: 10px;" class="alignright">${transaction.transactionId}</td>
							                      			<td class="alignleft" style="width: 10px;">${transaction.merchantBusinessName}</td>
							                      			<td class="alignright" style="width: 10px;">${transaction.merchant_code}</td>
							                      			<td class="alignright" style="width: 10px;">
																<c:choose>
																	<c:when test="${(transaction.ref_transaction_id) ne '0'}">
																			${transaction.ref_transaction_id }
																	</c:when>
																	<c:otherwise>
																		N/A
																	</c:otherwise>
																</c:choose>
															</td>
															<td class="alignleft" style="width: 10px;">${transaction.batchId}</td>
															<td class="alignright" style="width: 10px;">${transaction.maskCardNumber}</td>
															<td class="alignright" style="width: 10px;">${transaction.localCurrency}</td>
							                      			<td class="alignright" style="width: 10px;">${transaction.txn_total_amount}</td>
							                      			<td class="alignleft" style="width: 10px;">${fn:toUpperCase(transaction.transaction_type) }</td>
												            <td class="alignleft">${transaction.merchantSettlementStatus }</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${transactionReqObject.toGetCurrentDayTxns == true}">
										<tr><td colspan="11" style="color: red;"><spring:message code="reports.label.transactions.notransactionsfoundfortheday" /></td></tr>
									</c:when>
									<c:otherwise>
										<tr><td colspan="11" style="color: red;"><spring:message code="reports.label.transactions.notransactionsfound" /></td></tr>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
							</table>
					</div>
					
					<div class="search-results-table" id="checkb">

					<table class="table table-condensed table-striped table-bordered">
					
					<c:if test="${ !(fn:length(transactions) eq 0)}">
								<tr class="table-footer-main">
									<td colspan="14" class="search-table-header-column">
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
															<spring:message code="common.label.downloadall"/> 
														</a>
													</div>
												</div>
											</div>
											<div class="col-sm-9">

												<ul class="pagination custom-table-footer-pagination">
													<c:if test="${portalListPageNumber gt 1}">
														<li onclick="bindSelectedTxns();"><a href="javascript:getPortalOnPageWithRecords('1','${totalRecords}')"> &laquo;</a></li>
														<li onclick="bindSelectedTxns();"><a href="javascript:getPortalPrevPageWithRecords('${portalListPageNumber }','${totalRecords}')"> &lsaquo; </a></li>
													</c:if>

													<c:forEach var="page" begin="${beginPortalPage }" end="${endPortalPage}" step="1" varStatus="pagePoint">
														<c:if test="${portalListPageNumber == pagePoint.index}">
															<li class="${portalListPageNumber == pagePoint.index?'active':''}">
																<a href="javascript:">${pagePoint.index}</a>
															</li>
														</c:if>
														<c:if test="${portalListPageNumber ne pagePoint.index}">
															<li class="" onclick="bindSelectedTxns();">
																<a href="javascript:getPortalOnPageWithRecords('${pagePoint.index }','${totalRecords}')">${pagePoint.index}</a>
															</li>
														</c:if>
													</c:forEach>

													<c:if test="${portalListPageNumber lt portalPages}">
														<li onclick="bindSelectedTxns();">
															<a href="javascript:getPortalNextPageWithRecords('${portalListPageNumber }','${totalRecords}')"> &rsaquo;</a>
														</li>
														<li onclick="bindSelectedTxns();">
															<a href="javascript:getPortalOnPageWithRecords('${portalPages }','${totalRecords}')">&raquo; </a>
														</li>
													</c:if>

												</ul>

											</div>
										</div>
									</td>
								</tr>
							</c:if>
							</table>
							</div>
							<div>
							<input type="submit" class="form-control button pull-right" style="margin-top: 20px;"
								value="Back" onclick="return backToDailyFundingReport();">
						</div>
					</div>
					</c:if>
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

	<script src="../js/transactions.js"></script>
	<script src="../js/virtual-terminal.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/sorting.js"></script>
	<script src="../js/tablesorter.js"></script>
	<script src="../js/tablesorter.widgets.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script src="../js/messages.js"></script>
	<script>
		/* DatePicker Javascript Strat*/
		var selectedTxns;
		var removedTxn = [];
		$(document).ready(function() {
			$("#navListId8").addClass("active-background");
			$(".focus-field").click(function() {
				$(this).children('.effectiveDate').focus();
			});

			$('.effectiveDate').datetimepicker({
				timepicker : false,
				format : 'd/m/Y',
				formatDate : 'd/m/Y',
				maxDate:new Date()
			});
			
			$('#my_popup').popup({
				blur : false
			});
			
			// if all checkbox are selected, check the selectall checkbox
			// and viceversa
			$(".transaction").click(function() {
				if ($(".transaction").length == $(".transaction:checked").length) {
					$("#selectall").attr("checked","checked");
				} else {
					$("#selectall").attr('checked',false);
				}
				
				if(!$(this).is(':checked')) {
					removedTxn.push(JSON.parse($(this).val()).txnId);
				}

			});
			
			var currentTxns = ${selected_bulk_settlement_list} + "";
			selectedTxns = JSON.stringify(currentTxns);
			
			if(selectedTxns != undefined && selectedTxns.trim().length > 0) {
				var selectedTxnxObj = JSON.parse(selectedTxns);
				var txnArray = selectedTxnxObj.actionDTOs;
				if(txnArray != null) {
					var txnId;
					for (var i = 0; i < txnArray.length; i++) {
						txnId = txnArray[i].txnId.trim();
						$('#'+txnId).find('.transaction').attr('checked',true);
					}
				}
				
				if ($(".transaction").length > 0 && $(".transaction").length == $(".transaction:checked").length) {
					$("#selectall").attr("checked","checked");
				}
			}

			$('#serviceResults tbody .odd').css('background-color','#f0f0f0');
			$('#serviceResults tbody tr:nth-child(even)').css('background-color','#e0f0f0');
			
		});

		function openPopup(transactionId, merchant_code, terminal_id,transaction_type, status) {
			get('comment').value = '';
			setDiv('commentErr', '')
			get('merchantId').value = merchant_code;
			get('terminalId').value = terminal_id;
			get('txnId').value = transactionId;
			get('txnType').value = transaction_type;
			get('settlementStatus').value = status;

			$('#my_popup').popup("show");
		}
		/* DatePicker Javascript End*/

		function processSubmit() {

			get('comments').value = get('comment').value;
			if (!validateComment()) {
				return false;
			} else {
				document.forms["processAction"].submit();
				return true;
			}
		}
		
		function process(transactionId, merchant_code, terminal_id,transaction_type, status) {

			get('merchantId').value = merchant_code;
			get('terminalId').value = terminal_id;
			get('txnId').value = transactionId;
			get('txnType').value = transaction_type;
			get('settlementStatus').value = status;
			$('#my_popup').popup("show");

		}
		
		function getSelectedTxns() {
			var names = [];
			$('.transaction:checked').each(function() {
				names.push(this.value);
			});
			return names;
		}
		
		function bindSelectedTxns() {
			var names = getSelectedTxns();
			if(selectedTxns != undefined && selectedTxns.trim().length > 0) {
				var selectedTxnxObj = JSON.parse(selectedTxns);
				var txnArray = selectedTxnxObj.actionDTOs;
				if(txnArray != undefined && txnArray.length > 0) {
					var txnId;
					for (var i = 0; i < txnArray.length; i++) {
						txnId = txnArray[i].txnId.trim();
						var txn;
						for(var j = 0; j < names.length; j++) {
							txn = (names[j] != undefined ? JSON.parse(names[j]) : "");
							if(txnId == txn.txnId) {
								delete names[j];
								break;
							}
						}
					}
				}
				$('#paginationForm').find('#selectedTxnsReqObj').val(names.filter(Boolean));
			} else {
				$('#paginationForm').find('#selectedTxnsReqObj').val(names);
			}
			
			$('#removedTxns').val(removedTxn);
		}
		function backToDailyFundingReport() {
			window.location.href = 'getDailyFundingReport';
		}
	</script>
	
</body>
</html>