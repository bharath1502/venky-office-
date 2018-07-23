<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ page import="com.chatak.pg.constants.PGConstants"%>
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
			<%-- <jsp:include page="header.jsp"></jsp:include> --%>
			<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text">Transactions</span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text">Details</span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Content Block Start -->
					<div class="">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error">&nbsp;${error }</span> <span
											class="green-error">&nbsp;${success }</span> <span
											class="green-error">&nbsp;${sucess }</span>
									</div>
								</div>
								<form:form action="dash-board-transactions-search" name="resubmitForm" method="get"></form:form>
								<form:form action="get-transaction-report-from-dashBoard" name="downloadReport" method="post">
									<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" />
									<input type="hidden" id="downloadTypeId" name="downloadType" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
									<!-- <input type="hidden" id="downloadAllRecords" name="downloadAllRecords" /> -->
								</form:form>
								<!--Success and Failure Message End-->
							</div>
						</div>
					</div>
					<!-- Content Block End -->
					<form:form action="getTransactionsOnStatus" name="paginationForm" id="paginationForm" method="post">
						<input type="hidden" id="pageNumberId" name="pageNumber" />
						<input type="hidden" id="totalRecordsId" name="totalRecords" />
						<input type="hidden" id="selectedTxnsReqObj" name="requestObject" />
						<input type="hidden" id="removedTxns" name="removedTxns" />
						<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>
					<form:form action="editMerchant" name="editMercahntForm" method="post">
						<input type="hidden" id="getMerchantId" name="getMerchantId" />
						<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>
					<form:form action="process-dash-board-settlement-action" commandName="settlementDto" name="processAction" method="post">
						<form:hidden path="merchantId" id="merchantId" />
						<form:hidden path="terminalId" id="terminalId" />
						<form:hidden path="txnId" id="txnId" />
						<form:hidden path="txnType" id="txnType" />
						<form:hidden path="settlementStatus" id="settlementStatus" />
						<form:hidden path="comments" id="comments" />
						<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>
					<form:form action="process-bulk-settlement-action" method="post" name="bulkSettlement">
						<input type="hidden" name="requestObject" id="requestObjectId" />
						<input type="hidden" name="status" id="statusId" />
						<input type="hidden" id="removedTxns" name="removedTxns" />
						<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>
					<!-- Search Table Block Start -->
					<div class="search-results-table">
						<table class="table table-striped table-bordered table-condensed" style="margin: 1px;">
							<tr>
								<td colspan="7" class="search-table-header-column"><span
									class="glyphicon glyphicon-search search-table-icon-text"></span>
									<td colspan="4" class="search-table-header-column">
										<span style="margin-right: -156px;">
											<button class="executeAll">Execute all</button>
											<button class="processAll">Process all</button>
											<button class="cancelAll">Cancel all</button>
										</span>
									</td>
							</tr>
							</table>
							<!-- Search Table Header End -->
							<!-- Search Table Content Start -->
							<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
							<tr>
								<th>Date/Time</th>
								<th>Txn ID</th>
								<th>Proc Txn ID</th>
								<th>Card No</th>
								<th>Merchant Code</th>
								<th>Txn Type</th>
								<th>Merchant Txn Amount</th>
								<!-- <th>Acquiring Channel</th>
								<th>Entry Mode</th> -->
								<th>Description</th>
								<th>Status</th>
								<th>Action</th>
								<th><input type="checkbox" id="selectall" />Bulk settlement</th>
							</tr>
							</thead>
							<c:choose>
								<c:when test="${!(fn:length(transactions) eq 0) }">
									<c:forEach items="${transactions}" var="transaction">
										<tr data-txn-obj='${transaction.txnJsonString}' id='${transaction.transactionId }'>
											<td>${transaction.transactionDate }</td>
											<td><span class="txn-id">${transaction.transactionId }</span></td>
											<c:choose>
												<c:when test="${(transaction.ref_transaction_id) ne null }">
													<td><div class="feeDescDiv">${transaction.ref_transaction_id }</div></td>
												</c:when>
												<c:otherwise>
													<td>NA</td>
												</c:otherwise>
											</c:choose>
											<td><div class="feeDescDiv">${transaction.maskCardNumber }</div></td>
											<td><div class="feeDescDiv">${transaction.merchant_code }</div></td>
											<td>${fn:toUpperCase(transaction.transaction_type) }</td>
											<td>${transaction.transactionAmount }</td>
											<%-- <td>${transaction.acqChannel }</td>
											<td>${transaction.entryMode}</td> --%>
											<td class="left-justify"><div class="feeDescDiv">${transaction.txnDescription }</div></td>
											<td>${transaction.merchantSettlementStatus }</td>
											<c:choose>
											<c:when test="${transaction.merchantSettlementStatus == 'Executed' ||transaction.merchantSettlementStatus == 'Declined'||transaction.merchantSettlementStatus == 'Refunded'||transaction.merchantSettlementStatus == 'Cancelled'||transaction.merchantSettlementStatus == 'Rejected'}">
													<td>${transaction.updated_date }</td>
													<td>N/A</td>
											</c:when>
											<c:otherwise>
											<c:if test="${transaction.statusMessage == 'Approved' && (transaction.transaction_type=='sale'||transaction.transaction_type=='void') }">
												<c:choose>
													<c:when
														test="${transaction.merchantSettlementStatus == 'Processing'}">
														<td>
															<input type="button" name="Execute" class="execute" value="Execute"
															onclick="openPopup('${transaction.transactionId}','${transaction.merchant_code }','${transaction.terminal_id }','${transaction.transaction_type }','<%=PGConstants.PG_SETTLEMENT_EXECUTED %>')" />
															<input type="button" name="Cancel" class="cancel" value="Cancel" style="margin-top: 2px;"
															onclick="process('${transaction.transactionId}','${transaction.merchant_code }','${transaction.terminal_id }','${transaction.transaction_type }','<%=PGConstants.PG_SETTLEMENT_REJECTED %>')" />
														</td>
														<td><input type="checkbox" id="checkBoxtn" 
															class="transaction"
															value="{ &quot;txnId&quot;:&quot;${transaction.transactionId}&quot;,&quot;merchantId&quot;:&quot;${transaction.merchant_code }&quot;,&quot;terminalId&quot;:&quot;${transaction.terminal_id }&quot;,&quot;txnType&quot;:&quot;${transaction.transaction_type }&quot;}" /></td>
													</c:when>

													<c:when
														test="${transaction.merchantSettlementStatus == 'Pending'}">
														<td style="white-space: nowrap;">
															<input type="button" name="Process" class="process" value="Process" 
															onclick="process('${transaction.transactionId}','${transaction.merchant_code }','${transaction.terminal_id }','${transaction.transaction_type }','<%=PGConstants.PG_SETTLEMENT_PROCESSING %>')" />
															<input type="button" name="Cancel" class="cancel" value="Cancel" 
															onclick="process('${transaction.transactionId}','${transaction.merchant_code }','${transaction.terminal_id }','${transaction.transaction_type }','<%=PGConstants.PG_SETTLEMENT_REJECTED %>')" />
															<%-- <input
															type="button" name="Execute" value="Execute"
															onclick="openPopup('${transaction.transactionId}','${transaction.merchant_code }','${transaction.terminal_id }','${transaction.transaction_type }','<%=PGConstants.PG_SETTLEMENT_EXECUTED %>')" /> --%>

														</td>
														<td><input type="checkbox" id="checkBoxtn" 
															class="transaction"
															value="{ &quot;txnId&quot;:&quot;${transaction.transactionId}&quot;,&quot;merchantId&quot;:&quot;${transaction.merchant_code }&quot;,&quot;terminalId&quot;:&quot;${transaction.terminal_id }&quot;,&quot;txnType&quot;:&quot;${transaction.transaction_type }&quot;}" /></td>
													</c:when>

													<c:otherwise>
														<td>N/A</td>
														<td>N/A</td>
													</c:otherwise>
												</c:choose>

											</c:if>
											<c:if test="${transaction.statusMessage == 'Failed' || transaction.statusMessage == 'Declined'||transaction.transaction_type=='auth'||transaction.transaction_type=='refund'}">
												<td>N/A</td>
												<td>N/A</td>
											</c:if>
											</c:otherwise>
											</c:choose>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr><td colspan="11" style="color: red;">No TransactionsFound</td></tr>
								</c:otherwise>
							</c:choose>
							</table>
							<table class="table table-striped table-bordered table-condensed">
							<c:if test="${ !(fn:length(transactions) eq 0)}">
								<tr class="table-footer-main">
									<td colspan="11" class="search-table-header-column">
										<div class="col-sm-12">
											<div class="col-sm-3">
												<div class="btn-toolbar" role="toolbar">
													<div class="btn-group custom-table-footer-button">
														<a href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.XLS_FILE_FORMAT%>')">
															<button type="button" class="btn btn-default">
																<img src="../images/excel.png">
															</button>
														</a> <a href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.PDF_FILE_FORMAT%>')">
															<button type="button" class="btn btn-default">
																<img src="../images/pdf.png">
															</button>
														</a>
														<!-- <a>
															<input type="checkbox" class="autoCheck check" id="totalRecordsDownload">
															Download All 
														</a> -->
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
															<a href="javascript:getPortalOnPageWithRecords('${portalPages }','${totalRecords}')">&raquo;</a>
														</li>
													</c:if>
												</ul>
											</div>
										</div>
									</td>
								</tr>
							</c:if>
							<!-- Search Table Content End -->
						</table>
						<!--Panel Action Button Start -->
					<div class="col-sm-12 form-action-buttons">
						<div class="col-sm-5"></div>
						<div class="col-sm-7">
							<input type="button" class="form-control button pull-right"
								value="Cancel" onclick="backToHome()">
						</div>
					</div>
					<!--Panel Action Button End -->
					</div>
					<!-- Search Table Block End -->
					
				</div>
				<div id="my_popup" class="locatioin-list-popup">
					<span class="glyphicon glyphicon-remove" onclick="closePopup()"></span>
					<fieldset class="col-sm-12 padding0">
						<label data-toggle="tooltip" data-placement="top" title="">Comments<span class="required-field">*</span></label>
						<textarea id="comment" name="comment"
							class="form-control textareaResize"
							onblur="return validateComment()"></textarea>
						<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
							<span id="commentErr" class="red-error">&nbsp;</span>
						</div>
					</fieldset>
					<!-- Form Button Information Start -->
					<div class="col-sm-12">
						<input type="button" class="form-control button pull-right"
							value="Submit" onclick="return processSubmit()"> <input
							type="button" class="form-control button pull-right"
							value="Cancel" onclick="closePopup()">
					</div>
					<!-- Form Button Information End -->
					
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
	<script src="../js/transactions.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/sortable.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script src="../js/common-lib.js"></script>
	<script>
		/* Common Navigation Include Start */
		$(function() {
			//$( "#main-navigation" ).load( "main-navigation.html" );						
		});
		function highlightMainContent() {
			$("#navListId6").addClass("active-background");
		}
		/* Common Navigation Include End */
		/* DatePicker Javascript Strat*/
		var selectedTxns;
		var removedTxn = [];
		$(document).ready(function() {
			
			$(".focus-field").click(function() {
				$(this).children('.effectiveDate').focus();
			});

			$('.effectiveDate').datetimepicker({
				timepicker : false,
				format : 'm/d/Y',
				formatDate : 'Y/m/d',
			});
			
			$('#my_popup').popup({
				blur : false
			});

			($( ".execute" ).length==0) ? $( ".executeAll").hide() : $( ".executeAll" ).show();
			($( ".process" ).length==0) ? $( ".processAll").hide() : $( ".processAll" ).show();
			($( ".cancel" ).length==0) ? $( ".cancelAll").hide() : $( ".cancelAll" ).show();
				
			$('.executeAll, .processAll, .cancelAll').on('click',function() {
				//var names = [];
				if ($(".transaction:checked").length == 0) {
					alert("Please select atleast one item")
					return;
				}
				/* $('.transaction:checked').each(function() {
					names.push(this.value);
				});
				var myJsonString = JSON.stringify(names);
				get("requestObjectId").value = names; */
				
				get("requestObjectId").value = getSelectedTxns();
				if($(this).hasClass('executeAll')) {
					get("statusId").value = "Executed";
				} else if($(this).hasClass('processAll')) {
					get("statusId").value = "Processing";
				} else if($(this).hasClass('cancelAll')) {
					get("statusId").value = "Rejected";
				} 
				document.forms["bulkSettlement"].submit();

			});
			
			// add multiple select / deselect functionality
			$("#selectall").click(function() {
				if($(this).prop("checked") == false) {
					$('.transaction').each(function() {
						removedTxn.push(JSON.parse($(this).val()).txnId);
					});
					$('.transaction').attr('checked',false);
				} else {
					$('.transaction').prop('checked',this.checked);
				}
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
			
			selectedTxns = JSON.stringify(${selected_bulk_settlement_list});
			
			if(selectedTxns != undefined && selectedTxns.trim().length > 0) {
				var selectedTxnxObj = JSON.parse(selectedTxns);
				var txnArray = selectedTxnxObj.actionDTOs;
				
				if(txnArray != undefined && txnArray.length > 0) {
					var txnId;
					for (var i = 0; i < txnArray.length; i++) {
						txnId = txnArray[i].txnId.trim();
						$('#'+txnId).find('.transaction').attr('checked',true);
					}
					
					if ($(".transaction").length > 0 && $(".transaction").length == $(".transaction:checked").length) {
						$("#selectall").attr("checked","checked");
					}
				}
			}
		});

		function closePopup() {
			$('#my_popup').popup("hide");
		}

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
			document.forms["processAction"].submit();

		}
		
		function process(transactionId, merchant_code, terminal_id,transaction_type, status) {

			get('merchantId').value = merchant_code;
			get('terminalId').value = terminal_id;
			get('txnId').value = transactionId;
			get('txnType').value = transaction_type;
			get('settlementStatus').value = status;
			document.forms["processAction"].submit();

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
				//$('#paginationForm').find('#selectedTxnsReqObj').val(names);
				$('#paginationForm').find('#selectedTxnsReqObj').val(names.filter(Boolean));
			} else {
				$('#paginationForm').find('#selectedTxnsReqObj').val(names);
			}
			
			$('#removedTxns').val(removedTxn);
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
	</script>
</body>
</html>