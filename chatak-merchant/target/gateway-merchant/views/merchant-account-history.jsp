<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.merchant.constants.StatusConstants"%>
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
						<span class="breadcrumb-text"><spring:message code="merchant-account-history.label.merchantaccounts"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="merchant-account-history.label.history"/></span>
					</div>
					<!-- Breadcrumb End -->
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="merchant-account-history.label.accounthistory"/></a>
					</div>
					<!-- Content Block Start -->
					<div class="main-content-holder">

						<form action="getHistory" name="paginationForm" method="post">
							<input type="hidden" id="pageNumberId" name="pageNumber" /> <input
								type="hidden" id="totalRecordsId" name="totalRecords" />
								<input type="hidden" name="CSRFToken" value="${tokenval}">
						</form>
						<form action="editMerchant" name="editMercahntForm" method="post">
							<input type="hidden" id="getMerchantId" name="getMerchantId" />
							<input type="hidden" name="CSRFToken" value="${tokenval}">
						</form>
						<form action="get-history-report" name="downloadReport"	method="post">
							<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" />
							<input type="hidden" id="downloadTypeId" name="downloadType" />
							<input type="hidden" id="totalRecords" name="totalRecords">
							<input type="hidden" id="downloadAllRecords" name="downloadAllRecords">
							<input type="hidden" name="CSRFToken" value="${tokenval}">
						</form>
						<!-- Search Table Block Start -->
						<div class="search-results-table">
							<table class="table table-striped table-bordered table-condensed"
								id="data" style="margin: 1px;">
								<!-- Search Table Header Start -->
									<tr>

										<td colspan="10" class="search-table-header-column"
											style="text-align: left"><spring:message code="merchant-account-history.label.merchantaccountchangehistory"/><span
											class="glyphicon"></span></td>
									</tr>
									<!-- Search Table Header End -->
									<!-- Search Table Content Start -->
									</table>
									<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
										<thead>
									<tr>
										<th style="width: 100px;"><spring:message code="merchant-account-history.label.updateddate/time"/></th>
										<th style="width: 150px;"><spring:message code="merchant-account-history.label.accountnumber"/></th>
										<th style="width: 60px;"><spring:message code="merchant-account-history.label.currency"/></th>
										<th style="width: 120px;"><spring:message code="merchant-account-history.label.availablebalance"/></th>
										<th style="width: 110px;"><spring:message code="merchant-account-history.label.currentbalance"/></th>
										<th style="width: 80px;"><spring:message code="merchant-account-history.label.freebalance"/></th>
										<th style="width: 80px;"><spring:message code="merchant-account-history.label.paymenttype"/></th>
										<th style="width: 80px;"><spring:message code="merchant-account-history.label.status"/></th>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${!(fn:length(accountHistoryList) eq 0) }">
											<c:forEach items="${accountHistoryList}" var="historyModel">
												<tr>
													<td>${historyModel.updatedDateString }</td>
													<td class="tbl-text-align-right">${historyModel.accountNum }</td>
													<td class="tbl-text-align-right">${historyModel.currency }</td>
													<%-- <td>${transaction.currentBalance }</td>
											<td>${transaction.availableBalance }</td>  --%>
													<c:if test="${not empty historyModel.entityId }">

														<c:catch var="catchException">
															<fmt:setLocale value="en_US"  />
															<td class="tbl-text-align-right"><c:choose>
																	<c:when test="${historyModel.availableBalance lt 0}">
																		<span style="color: red;"><fmt:formatNumber
																				value="${historyModel.availableBalance/100}" minFractionDigits="2"/></span>
																		<br>
																	</c:when>
																	<c:otherwise>
																		<span><fmt:formatNumber
																				value="${historyModel.availableBalance/100}" minFractionDigits="2"/></span>
																		<br>
																	</c:otherwise>
																</c:choose></td>
															<td class="tbl-text-align-right"><c:choose>
																	<c:when test="${historyModel.currentBalance lt 0}">
																		<span style="color: red;"><fmt:formatNumber
																				value="${historyModel.currentBalance/100}" minFractionDigits="2"/></span>
																		<br>
																	</c:when>
																	<c:otherwise>
																		<span><fmt:formatNumber
																				value="${historyModel.currentBalance/100}" minFractionDigits="2"/></span>
																		<br>
																	</c:otherwise>
																</c:choose></td>
															<td class="tbl-text-align-right"><c:choose>
																	<c:when test="${historyModel.feeBalance lt 0}">
																		<span style="color: red;"><fmt:formatNumber
																				value="${historyModel.feeBalance/100}" minFractionDigits="2"/></span>
																		<br>
																	</c:when>
																	<c:otherwise>
																		<span><fmt:formatNumber
																				value="${historyModel.feeBalance/100}" minFractionDigits="2"/></span>
																		<br>
																	</c:otherwise>
																</c:choose></td>
														</c:catch>
														<c:if test="${catchException != null}">
															<p>
																The exception is : ${catchException} <br /> There is an
																exception: ${catchException.message}
															</p>
														</c:if>
													</c:if>
													<td class="tbl-text-align-left">${historyModel.paymentMethod }</td>
													<td class="tbl-text-align-left">${historyModel.status }</td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr>
												<td colspan="10" style="color: red;"><spring:message code="merchant-account-history.label.norecordsfound"/></td>
											</tr>
										</c:otherwise>
									</c:choose>
									</table>
									<table class="table table-striped table-bordered table-condensed">
									<c:if test="${ !(fn:length(accountHistoryList) eq 0)}">
									<tr class="table-footer-main">
										<td colspan="10" class="search-table-header-column">
											<div class="col-sm-12">
												<div class="col-sm-3">
													<div class="btn-toolbar" role="toolbar">
														<div class="btn-group custom-table-footer-button">
															<a
																href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.XLS_FILE_FORMAT%>',${totalRecords})">
																<button type="button" class="btn btn-default">
																	<img src="../images/excel.png">
																</button>
															</a> <a
																href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.PDF_FILE_FORMAT%>',${totalRecords})">
																<button type="button" class="btn btn-default">
																	<img src="../images/PDF.png">
																</button>
															</a>
														</div>
													</div>
												</div>
												<div class="col-sm-9">

													<ul
														class="pagination custom-table-footer-pagination my-navigation">

														<c:if test="${portalListPageNumber gt 1}">
															<li><a
																href="javascript:getPortalOnPageWithRecords('1','${totalRecords}')">
																	&laquo;</a></li>
															<li><a
																href="javascript:getPortalPrevPageWithRecords('${portalListPageNumber }','${totalRecords}')">
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
																	href="javascript:getPortalOnPageWithRecords('${pagePoint.index }','${totalRecords}')">${pagePoint.index}</a>
																</li>
															</c:if>
														</c:forEach>

														<c:if test="${portalListPageNumber lt portalPages}">
															<li><a
																href="javascript:getPortalNextPageWithRecords('${portalListPageNumber }','${totalRecords}')">
																	&rsaquo;</a></li>
															<li><a
																href="javascript:getPortalOnPageWithRecords('${portalPages }','${totalRecords}')">&raquo;
															</a></li>
														</c:if>


													</ul>
												</div>

											</div>
										</td>
									</tr>
								</c:if>
								</table>
									<!-- Search Table Content End -->
								</tbody>
							
						</div>
						<div>
							<input type="submit" class="form-control button pull-right" style="margin-top: 20px;"
								value="<spring:message code="merchant-account-history.label.back"/>" onclick="return backTodashBoard();">
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

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/jquery.min.js"></script>
	<script src="../js/common-lib.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script> <script src="../js/utils.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/sortable.js"></script>
	<script src="../js/changePassword.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	 <script src="../js/messages.js"></script>
	 <script src="../js/common-lib.js"></script>
	<script>
		$(document)
				.ready(
						function() {
							highlightMainContent('navListId1');
							var rowsShown = 20;
							var pageShown=10;
							var rowsTotal = $('#serviceResults tbody tr').length;
							var numPages = rowsTotal / rowsShown;
							var startPagePoint=1;
							var endPagePoint=startPagePoint+pageShown-1;
							/* $('#serviceResults')
							.after(
									'<table style="width: 100%; margin-top: -20px;"><tr class="table-footer-main"><td class="search-table-header-column" colspan="10"><div class="col-sm-12">	<div class="col-sm-3">&nbsp;</div><div class="col-sm-9"><ul class="pagination custom-table-footer-pagination"><li id="liIdLeft"><a href="#" rel="'+startPagePoint+'">&laquo;</a></li><li id="liId"></a></li><li id="liIdRight"><a href="#" rel="'+endPagePoint+'">&raquo;</a></li></ul></div></div></td></tr></table>'); */
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
							$('#liId a'); /* .bind(
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
									}); */
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
	<script type="text/javascript">
		function resetTxnSearch() {
			document.forms["resubmitForm"].submit();
		}
	</script>
	<script src="../js/backbutton.js"></script>

</body>
</html>