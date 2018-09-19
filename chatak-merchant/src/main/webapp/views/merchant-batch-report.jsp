<!DOCTYPE html>
<%@page import="com.chatak.merchant.constants.StatusConstants"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
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
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
		<header>
				<jsp:include page="header.jsp"></jsp:include>
				<!--Header Welcome Text and Logout button End -->
			</header>
			<!--Header Block Start -->
			<!--Navigation Block Start -->
				<nav class="col-sm-12 nav-bar" id="main-navigation">
				<%-- <jsp:include page="main-navigation.jsp"></jsp:include>  --%>
				<%@include file="navigation-panel.jsp"%>
			</nav>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="reports.label.reports" /></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="chatak-batch-report" /></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="chatak-batch-report" /></a>
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
										<span id="sucessDiv" class="green-error">&nbsp;${sucess }</span> 
									</div>
								</div>
								<form action="merchant-batch-report" name="resubmitForm" method="get"></form>
								<form action="get-batch-transaction-report" name="downloadReport" method="post">
									<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" /> 
									<input type="hidden" id="downloadTypeId" name="downloadType" />
									<input type="hidden" name="downloadReportObject" id="downloadReportId" />
									<input type="hidden" id="totalRecords" name="totalRecords" />
							        <input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
							        <input type="hidden" name="CSRFToken" value="${tokenval}">
								</form>
								<form action="getBatchTransactions" name="paginationForm" method="post" id="paginationForm">
									<input type="hidden" id="pageNumberId" name="pageNumber" /> 
									<input type="hidden" id="totalRecordsId" name="totalRecords" />
									<input type="hidden" id="selectedTxnsReqObj" name="requestObject" />
									<input type="hidden" id="removedTxns" name="removedTxns" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="getBatchReport" commandName="batchReport"
									name="batchReport">
									<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-3" id="mid">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="reports.label.merchantcode" /></label>
													<form:select cssClass="form-control" path="merchantCode" id="merchantCode"
														onchange="fetchSubMerchants(this.value, 'subMerchantCode')" >
															<form:option value="">..:Select:..</form:option>
															<c:forEach items="${merchantList}" var="merchant">
																<form:option value="${merchant.value}">${merchant.label}</form:option>
															</c:forEach>
													</form:select>
													<input type="hidden" id="userTypeVar" value=${userType}>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3" id="smid">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchantFileExportUtil.merchant.code" /></label>
													<form:select cssClass="form-control" path="subMerchantCode" id="subMerchantCode" >
															<form:option value="">..:Select:..</form:option>
															<c:forEach items="${subMerchantList}" var="subMerchant">
																<form:option value="${subMerchant.value}">${subMerchant.label}</form:option>
															</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="reports.label.fromdate" /><span class="required-field">*</span></label>
													<div class="input-group focus-field">
														<form:input path="fromDate" id="fromDate" onblur="return clientValidation('fromDate', 'startDate','transFromDateErrorDiv')"
															cssClass="form-control effectiveDate" />
														<span class="input-group-addon"><span
															class="glyphicon glyphicon-calendar"></span></span>
													</div>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="transFromDateErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="reports.label.todate" /><span class="required-field">*</span></label>
													<div class="input-group focus-field">
														<form:input path="toDate" onblur="return clientValidation('toDate', 'endDate','transToDateErrorDiv');"
															cssClass="form-control effectiveDate" id="toDate" />
														<span class="input-group-addon"><span
															class="glyphicon glyphicon-calendar"></span></span>
													</div>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="transToDateErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
											</div>
										</div>
										<!--Panel Action Button Start -->
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit" class="form-control button pull-right" onclick="return submitBatchFundingDateRange()"
													value="<spring:message code="virtual-terminal-sale.label.searchbutton" />"> <input type="button"
													class="form-control button pull-right" value="<spring:message code="virtual-terminal-sale.label.resetbutton" />"
													onclick="resetAll()">
											</div>
										</div>

										<!--Panel Action Button End -->
									</div>
								</form:form>
								<!-- Page Form End -->
							</div>
						</div>
					</div>
					<!-- Content Block End -->
					<!-- Search Table Block Start -->
                     <c:if test="${flag ne false }">
					<div class="search-results-table search-table-align" id="checkb">
						<table class="table table-striped table-bordered table-condensed" style="margin: 1px;">
							<!-- Search Table Header Start -->
							<tr>
								<td colspan="10" class="search-table-header-column">
									<span class="glyphicon glyphicon-search search-table-icon-text"></span>
									<span><spring:message code="common.label.search"/></span>
									<span class="pull-right"><spring:message code="search-sub-merchant.lable.totalcount"/> : <label id="totalCount">${totalRecords}</label></span>
								</td>
							</tr>
						</table>
						<table id="serviceResults"
								class="table table-striped table-bordered table-responsive table-condensed tablesorter">
								<thead>
									<tr>
										<th style="width: 50px;"><spring:message code="dash-board.label.transactiontime"/></th>
										<th style="width: 50px;"><spring:message code="merchant.common-deviceLocalTxnTime"/></th>
										<th style="width: 50px;"><spring:message code="merchantFileExportUtil.Account.transactionid"/></th>
										<th style="width: 50px;"><spring:message code="reports.label.balancereports.manualtransactions.merchantorsubmerchantcode"/></th>
										<th style="width: 40px;"><spring:message code="transaction-report-batchID"/></th>
										<th style="width: 40px;"><spring:message code="transactions-search.label.cardnumber"/></th>
										<th style="width: 50px;"><spring:message code="transactionFileExportUtil.description"/></th>
										<th style="width: 40px;"><spring:message code="search-sub-merchant.label.currencycode"/></th>
										<th style="width: 40px;"><spring:message code="recurring-search.label.amount"/></th>
										<th style="width: 50px;"><spring:message code="transactions-search.label.txntype"/></th>
										<th style="width: 50px;"><spring:message code="fileExportUtil.status" /></th>
									</tr>
								</thead>
								<c:choose>
									<c:when test="${!(fn:length(transactions) eq 0) }">
									<c:forEach items="${transactions}" var="transaction">
											<tr>
							                    		<tr data-txn-obj='${transaction.txnJsonString}' id='${transaction.transactionId }'>
							                      			<td class="alignleft" style="width: 100px;">${transaction.transactionDate}</td>
														<td>${transaction.deviceLocalTxnTime}<c:if
																test="${ not empty transaction.timeZoneOffset}">
																<br>(${transaction.timeZoneOffset})
															</c:if>
														</td>
													<td style="width: 10px;" class="alignright">${transaction.transactionId}</td>
							                      			<td class="alignright" style="width: 10px;">${transaction.merchant_code}</td>
															<td class="alignleft" style="width: 10px;">${transaction.batchId}</td>
															<td class="alignright" style="width: 10px;">${transaction.maskCardNumber}</td>
															<td class="left-justify"><div class="txn-desc feeDescDiv">${transaction.txnDescription}</div></td>
															<td class="alignright" style="width: 10px;">${transaction.localCurrency}</td>
							                      			<td class="alignright" style="width: 10px;">${transaction.txn_total_amount/100}</td>
							                      			<td class="alignleft" style="width: 10px;">${fn:toUpperCase(transaction.transaction_type) }</td>
												             <td class="alignleft">${transaction.merchantSettlementStatus }</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${transactionReqObject.toGetCurrentDayTxns == true}">
										<tr><td colspan="10" style="color: red;"><spring:message code="transactions-search.label.todaynotransactions" /></td></tr>
									</c:when>
									<c:otherwise>
										<tr><td colspan="10" style="color: red;"><spring:message code="transactions-search.label.notransactions" /></td></tr>
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
														</a>
														<a
															href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.PDF_FILE_FORMAT%>', ${totalRecords})">
															<button type="button" class="btn btn-default">
																<img src="../images/PDF.png">
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
					</c:if>
					<!-- Search Table Block End -->
				</div>
				<div id="my_popup" class="locatioin-list-popup" style="width: 880px;">
					<span class="glyphicon glyphicon-remove" onclick="closePopup()"></span>
					<fieldset class="col-sm-12 padding0">
						<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="transactions-search.label.comments" /><span class="required-field">*</span></label>
						<textarea id="comment" name="comment"
							class="form-control textareaResize"
							onblur="this.value=this.value.trim();return validateComment()"></textarea>
						<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
							<span id="commentErr" class="red-error">&nbsp;</span>
						</div>
					</fieldset>
					<!-- Form Button Information Start -->
					<div class="col-sm-12">
						<input type="button" class="form-control button pull-right"
							value="<spring:message code="transactions-search.label.submitbutton" />" onclick="return processSubmit()"> <input
							type="button" class="form-control button pull-right"
							value="<spring:message code="transactions-search.label.cancelbutton" />" onclick="closePopup()">
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

	<script src="../js/transactions.js"></script>
	<script src="../js/virtual-terminal.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/sortable.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/reports.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/validation.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
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
		/* DatePicker Javascript Strat*/
		var selectedTxns;
		var removedTxn = [];
		$(document).ready(function() {
			$("#navListId4").addClass("active-background");
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

			($( ".execute" ).length==0) ? $( ".executeAll").hide() : $( ".executeAll" ).show();
			($( ".process" ).length==0) ? $( ".processAll").hide() : $( ".processAll" ).show();
			($( ".cancel" ).length==0) ? $( ".cancelAll").hide() : $( ".cancelAll" ).show();
				
			$('.executeAll, .processAll, .cancelAll').on('click',function() {
				if ($(".transaction:checked").length == 0) {
					alert("Please select atleast one item")
					return;
				}
				
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
			
			var userTypeVar = $('#userTypeVar').val();
			if (userTypeVar == 'SubMerchant') {
				$('#mid').hide();
				$('#smid').hide();
			}
		});

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
			$('#my_popup').popup("show");
			//document.forms["processAction"].submit();

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
			if (selectedTxns != undefined && selectedTxns.trim().length > 0) {
				var selectedTxnxObj = JSON.parse(selectedTxns);
				var txnArray = selectedTxnxObj.actionDTOs;
				if (txnArray != undefined && txnArray.length > 0) {
					var txnId;
					for (var i = 0; i < txnArray.length; i++) {
						txnId = txnArray[i].txnId.trim();
						var txn;
						for (var j = 0; j < names.length; j++) {
							txn = (names[j] != undefined ? JSON.parse(names[j])
									: "");
							if (txnId == txn.txnId) {
								delete names[j];
								break;
							}
						}
					}
				}
				$('#paginationForm').find('#selectedTxnsReqObj').val(
						names.filter(Boolean));
			} else {
				$('#paginationForm').find('#selectedTxnsReqObj').val(names);
			}

			$('#removedTxns').val(removedTxn);
		}
	</script>
	
</body>
</html>
