<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><spring:message code="common.lable.title"/></title>
<!-- Bootstrap -->
<link rel="icon" href="../images/favicons.png" type="image/png">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<jsp:include page="header.jsp"></jsp:include>
			<!--Navigation Block Start -->
			<nav class="col-sm-12 nav-bar" id="main-navigation">
				<%-- <jsp:include page="main-navigation.jsp"></jsp:include> --%>
				<%@include file="navigation-panel.jsp"%>
			</nav>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="processing-transaction-details.label.transaction"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="show-all-pending-merchants.label.details"/></span>
					</div>
					<!-- Breadcrumb End -->
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="reports.label.balancereports.manualtransactions"/></a>
					</div>
					<!-- Content Block Start -->
					<div class="main-content-holder">

						<form action="manual-transaction-details-pagination" name="paginationForm" method="post">
							<input type="hidden" id="pageNumberId" name="pageNumber" /> 
							<input type="hidden" id="totalRecordsId" name="totalRecords" />
						</form>

						<form action="manual-transaction-details-report" name="downloadDashboardReport" method="post">
							<input type="hidden" id="downloadManualTxnPageNumberId" name="downLoadPageNumber" /> 
							<input type="hidden"id="downloadManualTxnTypeId" name="downloadType" /> 
							<input type="hidden" id="requestManualTxnFromId" name="totalRecords" />
							<input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
						</form>
						<!-- Search Table Block Start -->
						<div class="search-results-table">
							<table class="table table-striped table-bordered table-condensed" style="margin-bottom: 0px;">
							<!-- Search Table Header Start -->
							<tr>
								<td colspan="6" class="search-table-header-column " style="text-align: left">
									<spring:message code="processing-transaction-details.label.transactionsummary"/>
									<span class="pull-right"><spring:message code="search-sub-merchant.lable.totalcount"/> : <label id="totalCount">${totalRecords}</label></span>
									
								</td>
							</tr>
						</table>
						<!-- Search Table Header End -->
						<!-- Search Table Content Start -->
						<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
								<tr>
									<th style="width: 100px;"><spring:message code="dash-board.label.transactiontime"/></th>
									<th style="width: 100px;"><spring:message code="merchant.common-deviceLocalTxnTime"/></th>
									<th style="width: 100px;"><spring:message code="reports.label.balancereports.manualtransactions.description"/><br></th>
									<th style="width: 150px;"><spring:message code="reports.label.balancereports.manualtransactions.merchantorsubmerchantcode"/></th>
									<th style="width: 50px;"><spring:message code="reports.label.balancereports.manualtransactions.transactionID"/></th>
									<th style="width: 80px;"><spring:message code="reports.label.balancereports.manualtransactions.currency"/></th>
									<th style="width: 80px;"><spring:message code="reports.label.balancereports.manualtransactions.availableBalance"/></th>
									<th style="width: 80px;"><spring:message code="reports.label.balancereports.manualtransactions.credit"/></th>
									<th style="width: 80px;"><spring:message code="reports.label.balancereports.manualtransactions.debit"/></th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when
										test="${!(fn:length(manualTransactionsReportList) eq 0) }">
										<c:forEach items="${manualTransactionsReportList}" var="transaction">
											<tr >
												<td>${transaction.transactionTime }</td>
													<td class="tbl-text-align-left">${transaction.deviceLocalTxnTime}<c:if
															test="${ not empty transaction.timeZoneOffset}">
															<br>(${transaction.timeZoneOffset})
													</c:if>
													</td>
													<td class="tbl-text-align-left">${transaction.description }</td>
												<td class="tbl-text-align-left">${transaction.merchantCode }</td>
												<td class="tbl-text-align-left">${transaction.transactionId }</td>
												<td class="tbl-text-align-left">${transaction.currency }</td>
												<td class="tbl-text-align-right">${transaction.currentBalance }</td>
												<c:choose>
												<c:when test="${transaction.transactionCode eq 'MANUAL_DEBIT' }">
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
										<tr><td colspan="6" style="color: red; text-align: center;"><spring:message code="execute-transaction-details.label.norecordsfound"/></td></tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
						<table class="table table-striped table-bordered table-condensed">
							<c:if test="${!(fn:length(manualTransactionsReportList) eq 0)}">
								<tr class="table-footer-main">
									<td colspan="10" class="search-table-header-column">
										<div class="col-sm-12">
											<div class="col-sm-6">
												<div class="btn-toolbar" role="toolbar">
													<div class="btn-group custom-table-footer-button">
														<a href="javascript:downloadDashboardReport('${portalListPageNumber}', '<%=Constants.XLS_FILE_FORMAT%>', 'manualTxn', 'dashobard')">
															<button type="button" class="btn btn-default"><img src="../images/excel.png"> </button>
														</a> 
														<a href="javascript:downloadDashboardReport('${portalListPageNumber}', '<%=Constants.PDF_FILE_FORMAT%>', 'manualTxn', 'dashobard')">
															<button type="button" class="btn btn-default"><img src="../images/PDF.png"></button>
														</a>
														<a>
															<input type="checkbox" class="autoCheck check" id="totalRecordsDownload">
															<spring:message code="processing-transaction-details.label.downloadall"/> 
														</a>
													</div>
												</div>
											</div>
											<div class="col-sm-6">
												<ul class="pagination custom-table-footer-pagination">
													<c:if test="${portalListPageNumber gt 1}">
														<li><a href="javascript:getPortalOnPageWithRecords('1','${totalRecords}')">&laquo;</a></li>
														<li><a href="javascript:getPortalPrevPageWithRecords('${portalListPageNumber }','${totalRecords}')">&lsaquo; </a></li>
													</c:if>

													<c:forEach var="page" begin="${beginPortalPage }" end="${endPortalPage}" step="1" varStatus="pagePoint">
														<c:if test="${portalListPageNumber == pagePoint.index}">
															<li class="${portalListPageNumber == pagePoint.index ? 'active' : ''}">
																<a href="javascript:">${pagePoint.index}</a>
															</li>
														</c:if>
														<c:if test="${portalListPageNumber ne pagePoint.index}">
															<li class="">
																<a href="javascript:getPortalOnPageWithRecords('${pagePoint.index }','${totalRecords}')">${pagePoint.index}</a>
															</li>
														</c:if>
													</c:forEach>

													<c:if test="${portalListPageNumber lt portalPages}">
														<li><a href="javascript:getPortalNextPageWithRecords('${portalListPageNumber }','${totalRecords}')">&rsaquo;</a></li>
														<li><a href="javascript:getPortalOnPageWithRecords('${portalPages }','${totalRecords}')">&raquo;</a></li>
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
						<div>
							<input type="submit" class="form-control button pull-right" style="margin-top: 20px;" value="Back" onclick="return goToDashboard();">
						</div>
					</div>
					<!-- Search Table Block End -->
				</div>
			</article>
			<!--Article Block End-->
			<jsp:include page="footer.jsp"></jsp:include>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->
	
	<div id="txn-popup" class="txn-popup"></div>

	<div id="refundPopup" class="popup-void-refund refund">
		<div class="popup-hf-bc fw-b-fs15 txt-center"><span style="line-height: 2em;"><spring:message code="dash-board.label.refundpayment"/></span></div>
		<div class="ele-container">
			<input type="radio" name="refundType" class="refundType fullRefund refund-radio" id="fullRefund"  checked="checked" />
			<div class="margin-left20">
				<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="dash-board.label.fullrefund"/></label><br> 
				<span><spring:message code="dash-board.label.refundthefullamount"/>(<span id="refundAmt"></span>)</span>
			</div>
		</div>
		<div class="ele-container">
			<input type="radio" name="refundType" class="refundType partialRefund refund-radio" id="partialRefund"/>
			<div class="margin-left20">
				<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="dash-board.label.partialrefund"/> </label><br> 
				<span><spring:message code="dash-board.label.refundapartialamount"/></span>
			</div>
		</div>
		<div id="refundAmtDiv" style="display: none;">
			<div class="txt-center">
				<span class="glyphicon glyphicon-arrow-down"></span>
			</div>
			<div class="ele-container">
				<div class="margin-left20">
					<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="dash-board.label.refundamount"/>:</label> <br>
					<span class="refundAmtSymbol"></span><span><input type="text" id="partialRefundAmt" class="form-control" placeholder="0.00" onkeypress="return amountValidate(this,event)" onblur="validateRefundAmount(this.id)"/></span>
					<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
						<span class="red-error" id="partialRefundAmtEr">&nbsp;</span>
					</div>
				</div>
			</div>
		</div>
		<div class="fw-b-fs15 refund-note"><spring:message code="dash-board.label.refundtake5to10daystoappearonyourcustomer'sstatement"></spring:message>.</div>
		<div class="col-sm-12 popup-hf-bc">
			<input type="submit" class="form-control button pull-right margin5" value="Refund" onclick="refundTxn();"> 
			<input type="button" class="form-control button pull-right margin5 close-btn" value="Cancel">
		</div>
		<input type="hidden" id="refundMerchantId" name="merchantId" />
		<input type="hidden" id="refundAccountTransactionId" name="accountTransactionId" />
		<input type="hidden" id="refundBalanceAmount" />
		<input type="hidden" id="refundTotalTxnAmount" />
	</div>
	
	<div id="voidResultPopup" class="popup-void-refund voidResult">
		<div class="popup-hf-bc fw-b-fs15 txt-center"><span style="line-height: 2em;"><spring:message code="processing-transaction-details.label.voidpayment"/></span></div>
		<div class="col-xs-12">
			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
				<span class="red-error" id="voidErrorData">&nbsp;</span> 
				<span class="green-error" id="successData">&nbsp;</span>
			</div>
		</div>
		
		<div class="col-xs-12" id="voidResultInfo">
		
			<div class="fw-b-fs15"><spring:message code="processing-transaction-details.label.pleasenote"/></div>
			<br>
			<div>
				<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="processing-transaction-details.label.referenceno"/> </label><span id="txnRefNo">&nbsp;</span> 
				<!-- <label data-toggle="tooltip" data-placement="top" title="">CG Reference No. : </label><span id="cgRefNo">&nbsp;</span>  -->
			</div>
			<br>
		</div>
		
		<div class="col-sm-12 popup-hf-bc">
			<input type="button" class="form-control button pull-right margin5 close-btn" value="Close" >
		</div>
	</div>
	
	<div id="refundResultPopup" class="popup-void-refund voidResult">
		<div class="popup-hf-bc fw-b-fs15 txt-center"><span style="line-height: 2em;"><spring:message code="processing-transaction-details.label.fefundpayment"/></span></div>
		<div class="col-xs-12">
			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
				<span class="red-error" id="refundErrorData">&nbsp;</span> 
				<span class="green-error" id="successData">&nbsp;</span>
			</div>
		</div>
		
		<div class="col-xs-12" id="refundResultInfo">
			<div class="fw-b-fs15"><spring:message code="processing-transaction-details.label.pleasenote"/></div>
			<br>
			<div>
			    <label data-toggle="tooltip" data-placement="top" title=""><spring:message code="reportFileExportUtil.total.amount"/> : </label><span id="TotalTxnAmount">&nbsp;</span>
				<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="processing-transaction-details.label.referenceno"/> : </label><span id="refundTxnRefNo">&nbsp;</span> 
				<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="processing-transaction-details.label.cgreferenceno"/> : </label><span id="cgRefNo">&nbsp;</span>
			</div>
			<br>
		</div>
		
		<div class="col-sm-12 popup-hf-bc">
			<input type="button" class="form-control button pull-right margin5 close-btn" value="Close" >
		</div>
	</div>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/jquery.min.js"></script>
	<!-- <script src="../js/transactions.js"></script> -->
	<script src="../js/common-lib.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script> <script src="../js/utils.js"></script>
	<script src="../js/sorting.js"></script>
	<script src="../js/tablesorter.js"></script>
	<script src="../js/tablesorter.widgets.js"></script>
	<script src="../js/backbutton.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	
	<script type="text/javascript">
	$(document).ready(function() {
		
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
				
			} else if($(this).attr('data-txn-status') == 'Executed') {
				$('#refundMerchantId').val($(this).parent().attr('data-txn-merchant-code'));
				$('#refundAccountTransactionId').val($(this).siblings().find('.t-txn-id').text());
				
				if($('#partialRefund').is(':checked')) {
					$('#partialRefund').removeAttr('checked');
					$('#fullRefund').prop('checked',true);
				}
				
				$('#refundAmtDiv').hide();
				$('#partialRefundAmt').val('');
				$('#partialRefundAmtEr').text('');
				$('#refundAmt').text($(this).siblings('.credit').text());
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
				window.location.href = 'processing-transaction-details';
			} 
		});
		
		$('#voidResultPopup').popup({
			onclose: function() {
				window.location.href = 'processing-transaction-details';
			  }
		});
		
		$('#refundResultPopup').popup({
			onclose: function() {
				window.location.href = 'processing-transaction-details';
			  }
		});
	});
	
	function voidTxn() {

		$.ajax({
			type : "POST",
			url : "process-popup-action",
			data: { merchantId: $('#voidMerchantId').val(), accountTransactionId: $('#voidAccountTransactionId').val(), transactionType: 'VOID'},
			success : function(response) {
				$('#voidPopup').popup("hide");
				if(response.errorCode == '00') {
					
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
		}
		
		$.ajax({
			type : "POST",
			url : "process-popup-action",
			data: { merchantId: $('#refundMerchantId').val(), 
					accountTransactionId: $('#refundAccountTransactionId').val(), 
					transactionType: 'REFUND',
					partialRefundFlag: isPartialRefund,
					totalTxnAmount: Math.floor(refundAmount)},
			success : function(response) {
				$('#refundPopup').popup("hide");
				if(response.errorCode == '00') {
					$("#TotalTxnAmount").text(response.totalTxnAmount);
					$('#refundTxnRefNo').text(response.txnRefNumber);
					$('#cgRefNo').text(response.cgRefNumber);
					
				} else {
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
		
		$.ajax({
			type : "POST",
			url : "chatak-partial-refund-balance",
			data: { merchantCode: $('#refundMerchantId').val(), accountTransactionId: $('#refundAccountTransactionId').val()},
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
	</script>
</body>
</html>