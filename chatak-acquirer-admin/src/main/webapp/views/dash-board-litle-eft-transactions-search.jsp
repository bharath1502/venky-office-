<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ page import="com.chatak.pg.constants.PGConstants"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
 <link href="../css/rome.css" rel="stylesheet">
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
						<span class="breadcrumb-text"><spring:message code="dash-board-litle-eft-transactions-search.label.efttransactions"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="dash-board-litle-eft-transactions-search.label.litle"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Content Block Start -->
					<div class="">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionMsg" style="padding-bottom: 20px;">
										<span class="red-error">&nbsp;${error }</span>  <span
											class="green-error">&nbsp;${sucess }</span>
									</div>
								</div>
								<%-- <form action="dash-board-transactions-search"
									name="resubmitForm" method="get"></form> --%>
								<%-- <form action="get-transaction-report" name="downloadReport"
									method="post">
									<input type="hidden" id="downloadPageNumberId"
										name="downLoadPageNumber" /> <input type="hidden"
										id="downloadTypeId" name="downloadType" />
								</form> --%>

								<!--Success and Failure Message End-->
							</div>
						</div>
					</div>
					<!-- Content Block End -->
					<form:form action="getEFTPagination" name="paginationForm" method="post" id="paginationForm">
						<input type="hidden" id="pageNumberId" name="pageNumber" />
						<input type="hidden" id="totalRecordsId" name="totalRecords" />
						<input type="hidden" id="selectedTxnsReqObj" name="requestObject" />
						<input type="hidden" id="removedTxns" name="removedTxns" />
						<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>
					<form:form action="get-EFTtransaction-report-from-dashBoard" name="downloadReport" method="post">
						<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" />  
						<input type="hidden" id="downloadTypeId" name="downloadType" />
						<input type="hidden" id="totalRecords" name="totalRecords" />
						<!-- <input type="hidden" id="downloadAllRecords" name="downloadAllRecords" /> -->
						<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>
					<form:form action="process-bulk-litle-eft-action" method="post"name="litleBulkEFT">
						<input type="hidden" name="requestObject" id="requestObjectId" />
						<input type="hidden" id="removedTxns" name="removedTxns" />
						<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>
					<%-- <form action="editMerchant" name="editMercahntForm" method="post">
						<input type="hidden" id="getMerchantId" name="getMerchantId" />
					</form> --%>
					<form:form action="process-eft-dash-board-settlement-action" modelAttribute="litleEFTDTO" name="processAction" method="post">
						<form:hidden path="merchantCode" id="merchantId" />
						<form:hidden path="transactionId" id="transactionId" />
						<form:hidden path="amount" id="amount" />
						<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>
					<!-- Search Table Block Start -->
					<div class="search-results-table">
						<table class="table table-striped table-bordered table-condensed" style="margin: 1px;">
							<tr>
								<td colspan="11" class="search-table-header-column">
									<span class="glyphicon glyphicon-search search-table-icon-text"></span>
									<button class="executeAll" style="float: right;"><spring:message code="dash-board-litle-eft-transactions-search.label.processall"/></button>
								</td>
							</tr>
							</table>
							<!-- Search Table Header End -->
							<!-- Search Table Content Start -->
							<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
							<tr>
								<th style="width: 150px;"><spring:message code="dash-board-litle-eft-transactions-search.label.datetime"/></th>
								<th style="width: 150px;"><spring:message code="dash-board-litle-eft-transactions-search.label.txnid"/></th>
								<th style="width: 150px;"><spring:message code="dash-board-litle-eft-transactions-search.label.merchantcode"/></th>
								<th style="width: 200px;"><spring:message code="dash-board-litle-eft-transactions-search.label.amount"/></th>
								<th style="width: 150px;" class="sorter-false tablesorter-header tablesorter-headerUnSorted"><input type="checkbox" id="selectall"/><spring:message code="dash-board-litle-eft-transactions-search.label.selectall" /> &nbsp;</th>
								<!-- <th>Action</th> -->
							</tr>
							</thead>
							<c:choose>
								<c:when test="${!(fn:length(eftTransactionsList) eq 0) }">
									<c:forEach items="${eftTransactionsList}" var="eftTransactions">
										<tr data-txn-obj='${eftTransactions.txnJsonString}' id='${eftTransactions.transactionId }'>
											<td class="alignleft"><fmt:formatDate pattern="MM/dd/yyyy HH:mm:ss" value="${eftTransactions.dateTime }" /></td>
											<td class="alignleft"><span class="txn-id">${eftTransactions.transactionId}</span></td>
											<td class="alignleft">${eftTransactions.merchantCode }</td>
											<td class="alignright"><fmt:formatNumber value="${eftTransactions.amount/100}" type="currency" currencySymbol="$"/></td>
										    <td><input type="checkbox" id="checkBoxtn"	class="transaction" value="{ &quot;merchantCode&quot;:&quot;${eftTransactions.merchantCode}&quot;,&quot;transactionId&quot;:&quot;${eftTransactions.transactionId }&quot;,&quot;amount&quot;:&quot;${eftTransactions.amount }&quot;}" />
										    	<input type="button" name="Execute" class="execute" value='<spring:message code ="dash-board-litle-eft-transactions-search.label.processbutton"/>' onclick="openPopup('${eftTransactions.merchantCode}','${eftTransactions.transactionId}','${eftTransactions.amount}','<%=PGConstants.PG_SETTLEMENT_EXECUTED %>')" />
										    </td>
															 <%-- value="{ &quot;txnId&quot;:&quot;${transaction.transactionId}&quot;,&quot;merchantId&quot;:&quot;${transaction.merchant_code }&quot;,&quot;terminalId&quot;:&quot;${transaction.terminal_id }&quot;,&quot;txnType&quot;:&quot;${transaction.transaction_type }&quot;}" --%>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="11" style="color: red;"><spring:message code="dash-board-litle-eft-transactions-search.label.notransactionsfound"/></td>
									</tr>
								</c:otherwise>
							</c:choose>
							</table>
							<table class="table table-striped table-bordered table-condensed">
							<c:if test="${ !(fn:length(eftTransactionsList) eq 0)}">
								<tr class="table-footer-main">
									<td colspan="11" class="search-table-header-column">
										<div class="col-sm-12">
											<div class="col-sm-3">
												<div class="btn-toolbar" role="toolbar">
													<div class="btn-group custom-table-footer-button">
													<a href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.XLS_FILE_FORMAT%>', ${totalRecords})">
															<button type="button" class="btn btn-default">
																<img src="../images/excel.png">
															</button>
														</a> <a href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.PDF_FILE_FORMAT%>', ${totalRecords})">
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
														<li onclick="bindSelectedTxns();">
															<a href="javascript:getPortalOnPageWithRecords('1','${totalRecords}')"> &laquo;</a>
														</li>
														<li onclick="bindSelectedTxns();">
															<a href="javascript:getPortalPrevPageWithRecords('${portalListPageNumber }','${totalRecords}')"> &lsaquo; </a>
														</li>
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
								value='<spring:message code="dash-board-litle-eft-transactions-search.label.cancelbutton"/>' onclick="backToHome()">
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
	  <script src="../js/rome.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script src="../js/common-lib.js"></script>
	<script>
		/* Common Navigation Include Start */
		$(function() {
			//$( "#main-navigation" ).load( "main-navigation.html" );						
		});
		function highlightMainContent() {
			$("#navListId5").addClass("active-background");
		}
		/* Common Navigation Include End */
		/* DatePicker Javascript Strat*/
		var selectedTxns;
		var removedTxn = [];
		$(document).ready(function() {
			$(".focus-field").click(function() {
				$(this).children('.effectiveDate').focus();
			});
		/* 	rome(startDate, { time: false }); */
			/* $("#navListId5").addClass("active-background");
			$('.effectiveDate').datetimepicker({
				timepicker : false,
				format : 'm/d/Y',
				formatDate : 'Y/m/d',
			}); */
			
			$('#my_popup').popup({
				blur : false
			});
						
			// add multiple select / deselect functionality
			$("#selectall").click(function() {
				if($(this).prop("checked") == false){
					$('.transaction').each(function() {
						removedTxn.push(JSON.parse($(this).val()).txnId);
					});
					$('.transaction').attr('checked',false);
				} else {
					$('.transaction').prop('checked',this.checked);
					removedTxn = [];
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
			
			($( ".execute" ).length == 0) ? $( ".executeAll").hide() : $( ".executeAll" ).show();
			
			selectedTxns = JSON.stringify("${selected_bulk_settlement_list}"+"");
			
			if(selectedTxns != undefined && selectedTxns.trim().length > 0) {
				var selectedTxnxObj = JSON.parse(selectedTxns);
				var txnArray = selectedTxnxObj.litleEFTDTOs;
				
				if(txnArray != undefined && txnArray.length > 0) {
					var txnId;
					for (var i = 0; i < txnArray.length; i++) {
						txnId = txnArray[i].transactionId.trim();
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
		
		function openPopup(merchant_code, txn_id, amount, status) {
			 get('comment').value = '';
			 setDiv('commentErr', '')
		  	 get('merchantId').value = merchant_code;
			 get('transactionId').value = txn_id;
			 get('amount').value = amount; 

			 //$('#my_popup').popup("show");
			 document.forms["processAction"].submit();
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
		/* function process(transactionId, merchant_code, terminal_id,
				transaction_type, status) {

			get('merchantId').value = merchant_code;
			get('terminalId').value = terminal_id;
			get('txnId').value = transactionId;
			get('txnType').value = transaction_type;
			get('settlementStatus').value = status;
			document.forms["processAction"].submit();

		} */
		
		$('.executeAll').click(function() {
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
			document.forms["litleBulkEFT"].submit();

		});
		
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
				var txnArray = selectedTxnxObj.litleEFTDTOs;
				if(txnArray != undefined && txnArray.length > 0) {
					var txnId;
					for (var i = 0; i < txnArray.length; i++) {
						txnId = txnArray[i].transactionId.trim();
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
