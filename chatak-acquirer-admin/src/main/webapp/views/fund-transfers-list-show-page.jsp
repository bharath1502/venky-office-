<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ page import="com.chatak.pg.constants.PGConstants"%>
<%@ page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Chatak Acquirer Admin</title>
<!-- Bootstrap -->
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
						<span class="breadcrumb-text">Requests</span> <span
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
									<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error">&nbsp;${error }</span> <span
											class="green-error">&nbsp;${success }</span> <span
											class="green-error">&nbsp;${sucess }</span>
									</div>
								</div>
								<form:form action="dash-board-transactions-search" name="resubmitForm" method="get">
								</form:form>

								<form:form action="fund-request-export" name="downloadReport" method="post">
									<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" /> 
									<input type="hidden" id="downloadTypeId" name="downloadType" /> 
									<input type="hidden" id="transferModeId" name="transferMode" /> 
									<input type="hidden" id="statusId" name="status" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								
								<form:form action="eft-batch-export" name="downloadBatchReport" method="post">
									<input type="hidden" id="transfersIds" name="transfersIds" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

								<form:form action="getFundTransferPagination" name="paginationForm" method="post">
									<input type="hidden" id="pageNumberId" name="pageNumber" /> 
									<input type="hidden" id="totalRecordsId" name="totalRecords" />
									<input type="hidden" id="statusPagination" name="status" /> 
									<input type="hidden" id="transferModePagination" name="transferMode" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

								<form:form action="process-bulk-fund-transfer-action" method="post" name="bulkSettlement">
									<input type="hidden" name="requestObject" id="requestObjectId" />
									<input type="hidden" name="action" id="actionId" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

								<!--Success and Failure Message End-->
							</div>
						</div>
					</div>
					<form:form action="process-transfer-action" modelAttribute="fundTransferActionModel" name="fundTransferActionModel" method="post">
						<form:hidden path="pgTransfersId" id="pgTransfersId" />
						<form:hidden path="action" id="action" />
						<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>
					<!-- Content Block End -->
					<%-- <form action="getTransactionsOnStatus" name="paginationForm"
						method="post">
						<input type="hidden" id="pageNumberId" name="pageNumber" /> <input
							type="hidden" id="totalRecordsId" name="totalRecords" />
					</form>
					<form action="editMerchant" name="editMercahntForm" method="post">
						<input type="hidden" id="getMerchantId" name="getMerchantId" />
					</form>
					
					<form:form action="process-dash-board-settlement-action"
						modelAttribute="settlementDto" name="processAction" method="post">
						<form:hidden path="merchantId" id="merchantId" />
						<form:hidden path="terminalId" id="terminalId" />
						<form:hidden path="txnId" id="txnId" />
						<form:hidden path="txnType" id="txnType" />
						<form:hidden path="settlementStatus" id="settlementStatus" />
						<form:hidden path="comments" id="comments" />
					</form:form> --%>
					<!-- Search Table Block Start -->
					<div class="search-results-table">
						<!-- <table class="table table-striped tablesorter table-bordered table-condensed"> -->
						<table class="table table-striped table-bordered table-condensed"
							style="margin: 1px;">
							<tr>
								<td colspan="8" class="search-table-header-column"
									style="padding-right: 700px;"><span
									class="glyphicon glyphicon-search search-table-icon-text"></span>
								</td>
								<td colspan="4" class="search-table-header-column">
									<span>
										<button class="executeAll"><spring:message code="reports.label.transactions.executeall"/></button>
										<button class="processAll"><spring:message code="reports.label.transactions.processall"/></button>
										<c:if test="${isEFT_Pending == true}">
											<button class="batchAll"><spring:message code="fund-transfers-list-show-page.label.batchall"/></button>
										</c:if>
									</span>
								</td>
							</tr>
						</table>
						<!-- Search Table Header End -->
						<!-- Search Table Content Start -->
						<table id="serviceResults"
							class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
								<tr>
									<th style="width: 110px;"><spring:message code="dash-board-litle-eft-transactions-search.label.datetime"/></th>
									<th style="width: 101px;"><spring:message code="dash-board-litle-eft-transactions-search.label.merchantcode"/></th>
									<th style="width: 80px;"><spring:message code="dash-board-litle-eft-transactions-search.label.amount"/></th>
									<th style="width: 113px;"><spring:message code="fund-transfer-list-show.label.fundtransfermode"/></th>
									<th style="width: 84px;"><spring:message code="fund-transfer-list-show.label.fromaccount"/></th>
									<th style="width: 119px;"><spring:message code="fund-transfer-list-show.label.toaccount"/></th>
									<th style="width: 55px;"><spring:message code="reports.label.transactions.status"/></th>
									<th class="sorter-false tablesorter-header tablesorter-headerUnSorted"><spring:message code="commission-program-search.label.actiontable"/></th>
									<th style="width: 100px;" class="sorter-false tablesorter-header tablesorter-headerUnSorted"><input type="checkbox" id="selectall" />Bulk Transfer</th>
									<!-- <th>Action</th> -->
								</tr>
							</thead>
							<c:choose>
								<c:when test="${!(fn:length(transferRequestsList) eq 0) }">
									<c:forEach items="${transferRequestsList}" var="pgTransfers">
										<tr>
											<td><fmt:formatDate pattern="MM/dd/yyyy HH:mm:ss"
													value="${pgTransfers.createdDate}" /></td>
											<td>${pgTransfers.merchantId }</td>
											<td><fmt:formatNumber value="${pgTransfers.amount/100 }"
													type="currency" /></td>
											<td>${pgTransfers.transferMode }</td>
											<td>${pgTransfers.fromAccount }</td>
											<c:choose>
												<c:when test="${empty pgTransfers.toAccount}">
													<td>NA</td>
												</c:when>
												<c:otherwise>
													<td>${pgTransfers.toAccount }</td>
												</c:otherwise>
											</c:choose>
											<td>${pgTransfers.status }</td>
											<c:if test="${!(fn:length(transferRequestsList) eq 0) }">
												<c:choose>
													<c:when test="${pgTransfers.status == 'Processing'}">
														<td>
															<input type="button" name="Execute" class="execute" value="Execute"
																onclick="processAction('${pgTransfers.pgTransfersId }','<%=PGConstants.PG_SETTLEMENT_EXECUTED %>')" />
															<input type="button" name="Cancel" value="Cancel" class="cancel"
																onclick="processAction('${pgTransfers.pgTransfersId}','<%=PGConstants.PG_TXN_VOIDED %>')" />
														</td>
														<td><input type="checkbox" id="checkBoxtn"
															class="transaction"
															value="{ &quot;pgTransfersId&quot;:&quot;${pgTransfers.pgTransfersId}&quot;}" /></td>
													</c:when>

													<c:when test="${pgTransfers.status == 'Pending'}">
														<td style="white-space: nowrap;">
															<input type="button" name="Process" class="process" value="Process"
															onclick="processAction('${pgTransfers.pgTransfersId}','<%=PGConstants.PG_SETTLEMENT_PROCESSING %>')" />
															<input type="button" name="Cancel" value="Cancel" class="cancel"
															onclick="processAction('${pgTransfers.pgTransfersId}','<%=PGConstants.PG_TXN_VOIDED %>')" />
															<c:if test="${isEFT_Pending == true}">
																<input type="button" name="Batch" value="Batch" class="batch"
																	onclick="processBatch(this,'${pgTransfers.pgTransfersId}')" />
															</c:if>
														</td>
														<td><input type="checkbox" id="checkBoxtn"
															class="transaction"
															value="{ &quot;pgTransfersId&quot;:&quot;${pgTransfers.pgTransfersId}&quot;}" /></td>

														<!-- value="{txnId:'${transaction.transactionId}',merchantCode:'${transaction.merchant_code }',terminalId:'${transaction.terminal_id }',txnType:'${transaction.transaction_type }'}"  -->
													</c:when>

													<c:otherwise>
														<td>N/A</td>
														<td>N/A</td>
													</c:otherwise>
												</c:choose>

											</c:if>
											<c:if
												test="${pgTransfers.status == 'Failed' ||pgTransfers.status == 'Declined'}">
												<td>N/A</td>
												<td>N/A</td>
											</c:if>


											<%-- 	<c:if
												test="${transaction.statusMessage == 'Approved' && (transaction.transaction_type=='sale'||transaction.transaction_type=='void') }">
												<c:choose>
													<c:when
														test="${transaction.merchantSettlementStatus == 'Processing'}">
														<td><input type="button" name="Execute"
															value="Execute"
															onclick="openPopup('${transaction.transactionId}','${transaction.merchant_code }','${transaction.terminal_id }','${transaction.transaction_type }','<%=PGConstants.PG_SETTLEMENT_EXECUTED %>')" />

														</td>
													</c:when>

													<c:when
														test="${transaction.merchantSettlementStatus == 'Pending'}">
														<td><input type="button" name="Process"
															value="Process"
															onclick="process('${transaction.transactionId}','${transaction.merchant_code }','${transaction.terminal_id }','${transaction.transaction_type }','<%=PGConstants.PG_SETTLEMENT_PROCESSING %>')" /><input
															type="button" name="Execute" value="Execute"
															onclick="openPopup('${transaction.transactionId}','${transaction.merchant_code }','${transaction.terminal_id }','${transaction.transaction_type }','<%=PGConstants.PG_SETTLEMENT_EXECUTED %>')" />

														</td>
													</c:when>

													<c:otherwise>
														<td>N/A</td>
													</c:otherwise>
												</c:choose>

											</c:if>
											<c:if
												test="${transaction.statusMessage == 'Failed' || transaction.statusMessage == 'Declined'||transaction.transaction_type=='auth'||transaction.transaction_type=='refund'}">
												<td>N/A</td>
											</c:if> --%>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="9" style="color: red;"><spring:message code="fund-transfer-list-show.label.noRequestsfound"/></td>
									</tr>
								</c:otherwise>
							</c:choose>
						</table>
						<table class="table table-striped table-bordered table-condensed">
							<c:if test="${ !(fn:length(transferRequestsList) eq 0)}">
								<tr class="table-footer-main">
									<td colspan="10" class="search-table-header-column">
										<div class="col-sm-12">
											<div class="col-sm-3">
												<div class="btn-toolbar" role="toolbar">
													<div class="btn-group custom-table-footer-button">
														<a
															href="javascript:downloadRequestReport('${transferMode}','${status}','${portalListPageNumber}', '<%=Constants.XLS_FILE_FORMAT%>')">
															<button type="button" class="btn btn-default">
																<img src="../images/excel.png">
															</button>
														</a> <a
															href="javascript:downloadRequestReport('${transferMode}','${status}','${portalListPageNumber}', '<%=Constants.PDF_FILE_FORMAT%>')">
															<button type="button" class="btn btn-default">
																<img src="../images/pdf.png">
															</button>
														</a>
													</div>
												</div>
											</div>
											<div class="col-sm-9">

												<ul class="pagination custom-table-footer-pagination">
													<c:if test="${portalListPageNumber gt 1}">
														<li><a
															href="javascript:getPortalOnPageWithRecordsForRequest('1','${totalRecords}','${status}','${transferMode}')">
																&laquo;</a></li>
														<li><a
															href="javascript:getPortalPrevPageWithRecordsForRequest('${portalListPageNumber }','${totalRecords}','${status}','${transferMode}')">
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
																href="javascript:getPortalOnPageWithRecordsForRequest('${pagePoint.index }','${totalRecords}','${status}','${transferMode}')">${pagePoint.index}</a>
															</li>
														</c:if>
													</c:forEach>

													<c:if test="${portalListPageNumber lt portalPages}">
														<li><a
															href="javascript:getNextPageWithRecordsForRequest('${portalListPageNumber }','${totalRecords}','${status}','${transferMode}')">
																&rsaquo;</a></li>
														<li><a
															href="javascript:getLastPageWithRecordsForRequest('${portalPages }','${totalRecords}','${status}','${transferMode}')">&raquo;
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
						<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="reports.label.comments"/><span class="required-field">*</span></label>
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
	<script src="../js/transactions.js"></script>
	<script src="../js/common-lib.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/sortable.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script src="../js/transfers.js"></script>
	<script>
		/* Common Navigation Include Start */
		$(function() {
		//	$( "#main-navigation" ).load( "main-navigation.html" );
			highlightMainContent();						
		});
		function highlightMainContent() {
			$("#navListId9").addClass("active-background");
		}
		/* Common Navigation Include End */
		/* DatePicker Javascript Strat*/
		$(document).ready(function() {
			$(".focus-field").click(function() {
				$(this).children('.effectiveDate').focus();
			});

			/* $('.effectiveDate').datetimepicker({
				timepicker : false,
				format : 'd/m/Y',
				formatDate : 'Y/m/d',
			}); */
			$('#my_popup').popup({
				blur : false
			});
		}); 




		$(document).ready(function() {
			$(".focus-field").click(function() {
				$(this).children('.effectiveDate').focus();
			});
			/* rome(transFromDate, { time: false });
			rome(transToDate, { time: false }); */
			/* $('.effectiveDate').datetimepicker({
				timepicker : false,
				format : 'm/d/Y',
				formatDate : 'Y/m/d',
			}); */
			$('#my_popup').popup({
				blur : false
			});
			
			($( ".execute" ).length==0) ? $( ".executeAll").hide() : $( ".executeAll" ).show();
			($( ".process" ).length==0) ? $( ".processAll").hide() : $( ".processAll .batchAll" ).show();
			
			/* $(function() {
				if($( ".execute" ).length==0){
					$( ".executeAll").hide();
				}else{
					$( ".executeAll" ).show();
				}
				});
			$(function() {
				if($( ".process" ).length==0){
					$( ".processAll" ).hide();
				}else{
					$( ".processAll" ).show();
				}}); */
			$(function() {
				// add multiple select / deselect functionality
				$("#selectall").click(function() {
					if($(this).prop("checked") == false){
						$('.transaction').attr('checked',
								false);
					}else {
						$('.transaction').prop('checked',this.checked);
					}
				});
				// if all checkbox are selected, check the selectall checkbox
				// and viceversa
				$(".transaction").click(function() {
					if ($(".transaction").length == $(".transaction:checked").length) {
						$("#selectall").attr(
								"checked",
								"checked");
					} else {
						$("#selectall").attr('checked',false);
					}
				});
			});
				
			$('.executeAll, .processAll').on('click',function() {
				var names = [];
				if ($(".transaction:checked").length == 0) {
					alert("Please select atleast one item")
					return;
				}
				$('.transaction:checked').each(function() {
					names.push(this.value);
				});
				var myJsonString = JSON.stringify(names);
				get("requestObjectId").value = names;
				if($(this).hasClass('executeAll')) {
					get("statusId").value = "Executed";
				} else if($(this).hasClass('processAll')) {
					get("statusId").value = "Processing";
				} 
				document.forms["bulkSettlement"].submit();

			});
		});

		function closePopup() {
			$('#my_popup').popup("hide");
		}
		function openPopup(transactionId, merchant_code, terminal_id,
				transaction_type, status) {
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
		function process(transactionId, merchant_code, terminal_id,
				transaction_type, status) {

			get('merchantId').value = merchant_code;
			get('terminalId').value = terminal_id;
			get('txnId').value = transactionId;
			get('txnType').value = transaction_type;
			get('settlementStatus').value = status;
			document.forms["processAction"].submit();

		}

		/* $('.executeAll').click(function() {
			var names = [];
			if ($(".transaction:checked").length == 0) {
				alert("Please select atleast one item")
				return;
			}
			$('.transaction:checked').each(function() {
				names.push(this.value);
			});
			var myJsonString = JSON.stringify(names);
			get("requestObjectId").value = names;
			get("actionId").value = "Executed";
			document.forms["bulkSettlement"].submit();

		});
		$('.processAll').click(function() {
			var names = [];
			if ($(".transaction:checked").length == 0) {
				alert("Please select atleast one item")
				return;
			}
			$('.transaction:checked').each(function() {
				names.push(this.value);
			});
			var myJsonString = JSON.stringify(names);
			get("requestObjectId").value = names;
			get("actionId").value = "Processing";
			document.forms["bulkSettlement"].submit();

		}); */
		
		function processBatch($this,transferId) {

			$($this).parent().next().find('.transaction').attr('checked', false);
			var transfersIds = [transferId];
			$('#transfersIds').val(transfersIds);
			document.forms["downloadBatchReport"].submit();
		}
		
		$('.batchAll').click(function() {
			var transfersIds = [];
			if ($(".transaction:checked").length == 0) {
				alert("Please select atleast one item")
				return;
			}
			$('.transaction:checked').each(function() {
				$(this).attr('checked', false);
				var transferId = JSON.parse(this.value);
				transfersIds.push(transferId.pgTransfersId);
			});
			
			$('#transfersIds').val(transfersIds);
			document.forms["downloadBatchReport"].submit();

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
