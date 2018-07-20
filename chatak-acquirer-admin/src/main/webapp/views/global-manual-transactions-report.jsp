<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page  import="java.util.Calendar"%>
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
						<!-- <span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text">Global System Reports</span> -->
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="reports.label.balancereports.manualtransactions" /></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<!-- <div class="tab-header-container-first active-background">
						<a href="merchant-search">Specific User</a>
					</div> -->
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<!--Success and Failure Message Start-->
						<div class="row">
						<div class="col-xs-12">
							<div class="descriptionMsg" data-toggle="tooltip" data-placement="top" title="">
								<span class="red-error" style="font-size: 13px;">&nbsp;${error }</span> <span
									class="green-error">&nbsp;${sucess }</span>
							</div>
						</div>
						<form:form action="downloadGlobalManualReport" name="downloadReport" method="post">
							<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" /> 
							<input type="hidden" id="downloadTypeId" name="downloadType" /> 
							<input type="hidden" id="totalRecords" name="totalRecords" />
							<input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
							<input type="hidden" name="CSRFToken" value="${tokenval}">
						</form:form>

						<form:form action="mannual-txn-pagination" name="paginationForm" method="post">
							<input type="hidden" id="pageNumberId" name="pageNumber" /> 
							<input type="hidden" id="totalRecordsId" name="totalRecords" />
							<input type="hidden" name="CSRFToken" value="${tokenval}">
						</form:form>
						<form:form action="showGlobalManualTransferReport" commandName="getTransactionsListRequest" method="post">
						<input type="hidden" name="CSRFToken" value="${tokenval}">
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="reports.label.balancereports.manualtransactions.selectdaterange.fromdate" /><span class="required-field">*</span></label>
													<div class="input-group focus-field">
														<form:input path="from_date" id="transFromDate" onblur="return clientValidation('transFromDate', 'startDate','transFromDateErrorDiv')"
															cssClass="form-control effectiveDate" />
														<span class="input-group-addon"><span
															class="glyphicon glyphicon-calendar"></span></span>
													</div>
													<div class="discriptionMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="transFromDateErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="reports.label.balancereports.manualtransactions.selectdaterange.todate" /><span class="required-field">*</span></label>
													<div class="input-group focus-field">
														<form:input path="to_date" cssClass="form-control effectiveDate" id="transToDate"
														onblur="return clientValidation('transToDate', 'endDate','transToDateErrorDiv');" />
														<span class="input-group-addon"><span
															class="glyphicon glyphicon-calendar"></span></span>
													</div>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="transToDateErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												
												<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<!-- <input type="button" class="form-control button pull-right"
													value="Search">  -->
													<button type="submit" onclick="return submitManualTransferDateRange()" class="form-control button pull-right"><spring:message code="reports.label.generatebutton" /></button>
													<a type="button" href="showGlobalSysManualTransReports" class="form-control button pull-right"><spring:message code="accounts-manual-credit.label.resetbutton" /></a>
											</div>
										</div>
								</form:form>
						</div>
						</div>
						<div class="search-results-table" id="checkb" style="display: none;">
						<table class="table table-striped table-bordered table-condensed marginBM1">
							<!-- Search Table Header Start -->
							<tbody><tr>
								<td class="search-table-header-column widthP80">
									<span class="glyphicon glyphicon-search search-table-icon-text"></span>									
									<span><spring:message code="common.label.search"/></span>
								</td>
								<td class="search-table-header-column" style="font-weight:bold;"><spring:message code="common.label.totalcount"/> : <label id="totalCount">${totalRecords}</label></td>
							</tr>
							</tbody></table>

							<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
								<thead>
									<tr>
										<th style="width: 104px;"><spring:message code="reports.label.transactions.dateortime"/></th>
										<th style="width: 104px;"><spring:message code="admin.common-deviceLocalTxnTime"/></th>
										<th style="width: 121px;"><spring:message code="show-account-transfer.label.description" /></th>
										<th style="width: 82px;"><spring:message code="accounts-manual-debit.label.merchantorsubmerchantcode" /></th>
										<th style="width: 83px;"><spring:message code="reports.label.balancereports.manualtransactions.transactionID" /></th>
										<th style="width: 42px;"><spring:message code="currency-search-page.label.currencycode" /></th>
										<th style="width: 58px;"><spring:message code="show-account-transfer.label.availablebalance" /></th>
										<th style="width: 42px;"><spring:message code="reports.label.balancereports.manualtransactions.credit" /></th>
										<th style="width: 42px;"><spring:message code="reports.label.balancereports.manualtransactions.debit" /></th>
									</tr>
								</thead>
								<c:choose>
									<c:when
										test="${!(fn:length(manualTransactionsReportList) eq 0) }">
										<c:forEach items="${manualTransactionsReportList}" var="transaction">
											<%-- <tr data-txn-obj='${transaction.txnJsonString}'> --%>
											<tr >
												<td>${transaction.transactionTime }</td>
											<td style="width: 100px;">${transaction.deviceLocalTxnTime}
												<c:if test="${ not empty transaction.timeZoneOffset}">
													<br>(${transaction.timeZoneOffset})
													</c:if>
											</td>
											<td class="tbl-text-align-left">${transaction.description }</td>
												<%-- <td class="tbl-text-align-left">${transaction.pgTransactionId }</td> --%>
												<td class="tbl-text-align-left">${transaction.merchantCode }</td>
												<td class="tbl-text-align-left">${transaction.transactionId }</td>
												<td class="tbl-text-align-center">${transaction.currency }</td>
												<%-- <td class="tbl-text-align-left"><div class="feeDescDiv">${transaction.transactionCode }</div></td> --%>
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
<!-- 												<td></td> -->
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="12" style="color: red;"><spring:message code="reports.label.balancereports.manualtransactions.notransactionsfound" /></td>
										</tr>
									</c:otherwise>
								</c:choose>
								</table>
								
							<table class="table table-striped table-bordered table-condensed">
							<tbody><tr class="table-footer-main">
								<td colspan="8" class="search-table-header-column">
									<div class="col-sm-12">
										<div class="col-sm-4">
												<div class="btn-toolbar" role="toolbar">
													<div class="btn-group custom-table-footer-button">
														<a
															href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.XLS_FILE_FORMAT%>','${totalRecords}')">
															<button type="button" class="btn btn-default">
																<img src="../images/excel.png">
															</button>
														</a> <a
															href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.PDF_FILE_FORMAT%>','${totalRecords}')">
															<button type="button" class="btn btn-default">
																<img src="../images/pdf.png">
															</button>
														</a> <a> <input type="checkbox" class="autoCheck check"
															id="totalRecordsDownload"> <spring:message
																code="common.label.downloadall" />
														</a>
													</div>
												</div>
											</div>
											<div class="col-sm-8">
												<c:if test="${ !(fn:length(manualTransactionsReportList) eq 0)}">
													<ul class="pagination custom-table-footer-pagination">
														<c:if test="${portalListPageNumber gt 1}">
															<li><a href="javascript:getPortalOnPageWithRecords('1','${totalRecords}')">&laquo;</a></li>
															<li><a href="javascript:getPortalPrevPageWithRecords('${portalListPageNumber }', '${totalRecords}')">&lsaquo; </a></li>
														</c:if>

														<c:forEach var="page" begin="${beginPortalPage }"
															end="${endPortalPage}" step="1" varStatus="pagePoint">
															<c:if test="${portalListPageNumber == pagePoint.index}">
																<li class="${portalListPageNumber == pagePoint.index?'active':''}">
																	<a href="javascript:">${pagePoint.index}</a>
																</li>
															</c:if>
															<c:if test="${portalListPageNumber ne pagePoint.index}">
																<li class=""><a href="javascript:getPortalOnPageWithRecords('${pagePoint.index }','${totalRecords}')">${pagePoint.index}</a>
																</li>
															</c:if>
														</c:forEach>


														<c:if test="${portalListPageNumber lt portalPages}">
															<li><a href="javascript:getPortalNextPageWithRecords('${portalListPageNumber }', '${totalRecords}')">&rsaquo;</a></li>
															<li><a href="javascript:getPortalOnPageWithRecords('${portalPages }', '${totalRecords}')">&raquo;</a></li>
														</c:if>

													</ul>

												</c:if>
											</div>
										</div>								
								</td>
							</tr>
							
							<!-- Search Table Content End -->	
						</tbody>
						</table>
						</div>
					</div>
					<!-- Search Table Block End -->
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
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/sortable.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/reports.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/validation.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script src="../js/common-lib.js"></script>
	<script>

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
		
		  if ("${transactionDiv}" == "true"){
			 $('#checkb').show();
			 $('#transinfo').show();
			 $('#showDates').show();
		   }  
	});

		/* Common Pagination Include Start */
		 
						$(function() {
							/*highlightMainContent('navListId1'); */
							var rowsShown = 20;
							var pageShown=10;
							var rowsTotal = $('#serviceResults tbody tr').length;
							var numPages = rowsTotal / rowsShown;
							var startPagePoint=1;
							var endPagePoint=startPagePoint+pageShown-1;
							for (i = 0; i < numPages; i++) {
								var pageNum = i + 1;
								$('#liId').append(
										'<a href="#" rel="'+i+'" id="rel'+i+'">' + pageNum
												+ '</a> ');
							}
							if(numPages<=pageShown){
								$('#liIdRight a').hide();
								$('#liIdLeft a').hide();
								}
							$('#liIdLeft a').hide();
							$('#serviceResults tbody tr').hide();
							$('#serviceResults tbody tr').slice(0, rowsShown).show();
							$('#liId a:first').addClass('active');
							$('#liId a').hide(); 
							$('#liId a').slice(0, pageShown).show(); 
							$('#liId a').bind(
									'click',
									function() {
										$('#liId a').removeClass('active');
										$(this).addClass('active');
										var currPage = parseInt($(this).attr('rel'));
										var startItem = currPage * rowsShown;
										var endItem = startItem + rowsShown;
										$('#serviceResults tbody tr').css('opacity',
												'0.0').hide().slice(startItem,
												endItem).css('display',
												'table-row').animate({
											opacity : 1
										}, 300);
									});
							$('#liIdLeft a').bind(
									'click',
									function() {
										var currPage1 = parseInt($(this).attr('rel'));
										$('#liId a').hide();
										$('#liId a').slice(currPage1-pageShown,currPage1).show();
										if((currPage1-pageShown)<=0){
											startPagePoint=0;
											$('#liIdLeft a').hide();
											$(this).attr('rel',1);
											}
										else{
											startPagePoint=(currPage1-pageShown);
											$('#liIdLeft a').show();
											$(this).attr('rel',startPagePoint);
									     	}
										endPagePoint=startPagePoint+pageShown;
										$('#liIdRight a').attr('rel',endPagePoint);
										if(endPagePoint>=numPages){
											$('#liIdRight a').hide();
											}else{
												$('#liIdRight a').show();
												}
										if(startPagePoint>0&&startPagePoint<=(numPages)){
											$('#liIdLeft a').show();
											}else{
												$('#liIdLeft a').hide();
												}
										var t='rel'+(startPagePoint);
										$('#'+t).click();
									});
							$('#liIdRight a').bind(
									'click',
									function() {
										var currPage2 = parseInt($(this).attr('rel'));
										$('#liId a').slice(0,currPage2).hide();
										$('#liId a').slice(currPage2,currPage2+pageShown).show();
										startPagePoint=parseInt(currPage2);
										endPagePoint=parseInt(startPagePoint)+pageShown;
										$(this).attr('rel',endPagePoint);
										$('#liIdLeft a').attr('rel',startPagePoint);
										if(endPagePoint>=numPages){
											$('#liIdRight a').hide();
											}
										if(startPagePoint>0&&startPagePoint<=numPages){
											$('#liIdLeft a').show();
											}
										var t='rel'+(startPagePoint);
										$('#'+t).click();
									});
						});
		
				  /* Common Pagination Include End */
				  
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