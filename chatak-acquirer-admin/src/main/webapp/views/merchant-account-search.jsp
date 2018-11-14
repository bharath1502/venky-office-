<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.Calendar"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<%
  int year = Calendar.getInstance().get(Calendar.YEAR);
%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Chatak Acquirer Admin</title>
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
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
			<!--Navigation Block Start -->
			<%-- <jsp:include page="header.jsp"></jsp:include> --%>
			<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="manage.label.manage"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span>
						<span class="breadcrumb-text"><spring:message code="merchant.label.merchantaccount"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="common.label.search"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<c:if test="${fn:contains(existingFeatures,merchantAccountEdit) || fn:contains(existingFeatures,merchantAccountSuspend) || fn:contains(existingFeatures,merchantAccountActivate)}">
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="common.label.search"/></a>
					</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,merchantAccountCreate)}">
					<div class="tab-header-container">
						<a href="merchant-details-account-create?entityType=Merchant"><spring:message code="common.label.create"/></a>
					</div>
					</c:if>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error">&nbsp;${error}</span> <span
											class="green-error">&nbsp;${sucess}</span>
									</div>
								</div>

								<form:form action="merchant-account-search-pagination" name="paginationForm" method="post">
									<input type="hidden" id="pageNumberId" name="pageNumber" /> 
									<input type="hidden" id="totalRecordsId" name="totalRecords" />
									<input type="hidden" id="sortProperty" name="sortProperty" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								
								<form:form action="merchant-account-search-report" name="downloadReport" method="post">
									<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" /> 
									<input type="hidden" id="downloadTypeId" name="downloadType" />
									<input type="hidden" id="totalRecords" name="totalRecords" />
									<input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								
								<form:form action="merchant-account-edit-page" name="editMercahntAccountForm" method="post">
									<input type="hidden" id="editAccountId" name="accountId" />
									<input type="hidden" id="editmerchantCode" name="merchantCode" />
									<input type="hidden" id="editMerchantType" name="merchantType" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								
								<%-- <form action="#" name="viewMercahntaccountForm" method="post">
									<input type="hidden" id="merchantViewId" name="merchantViewId" />
									<input type="hidden" id="merchantType" name="merchantType" />
								</form>

								<form action="#" name="deleteMercahntAccountForm" method="post">
									<input type="hidden" id="getMerchantsId" name="getMerchantsId" />
									<input type="hidden" id="merchantType" name="merchantType" />
								</form> --%>

								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="merchant-account-search" modelAttribute="merchantAccountSearchDto" name="merchantAccountSearchDto">
								<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.merchantaccountnumber"/></label>
													<form:input cssClass="form-control" path="merchantAccountNumber" id="merchantAccountNumber" />
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.merchantname"/></label>
													<form:input cssClass="form-control" path="businessName" id="businessName" />
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.status"/></label>
													<form:select cssClass="form-control" path="accountStatus" id="accountStatus">
														<form:option value=""><spring:message code="reports.option.select"/></form:option>
														<form:option value="Active"><spring:message code="common.label.active"/></form:option>
														<form:option value="Suspended"><spring:message code="common.label.suspended"/></form:option>
														<form:option value="Terminated"><spring:message code="common.label.terminated"/></form:option>
													</form:select>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.recordsperpage"/></label>
													<form:select cssClass="form-control" path="pageSize" id="pageSize">
														<form:option value="10">10</form:option>
														<form:option value="20">20</form:option>
														<form:option value="50">50</form:option>
														<form:option value="100">100</form:option>
														<form:option value="250">250</form:option>
														<form:option value="500">500</form:option>
														<form:option value="1000">1000</form:option>
													</form:select>
												</fieldset>
												<form:hidden path="merchantType" id="merchantType" />
											</div>
										</div>
										<!--Panel Action Button Start -->
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit" value='<spring:message code="common.label.search"/>' class="form-control button pull-right"> 
												
												<input type="button" class="form-control button pull-right" value='<spring:message code="common.label.reset"/>'onclick="resetMerchantAccountSearch()">
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
					<div class="search-results-table">
						<table class="table table-striped table-bordered table-condensed" style="margin: 1px;">
							<!-- Search Table Header Start -->
							<tr>
								<td colspan="10" class="search-table-header-column">
									<span class="glyphicon glyphicon-search search-table-icon-text"></span>
									<span><spring:message code="common.label.search"/></span>
									<span class="pull-right"><spring:message code="common.label.totalcount"/> : <label id="totalCount">${searchResponse.totalNoOfRows}</label></span>
								</td>
							</tr>
						</table>
						<!-- Search Table Header End -->
						<!-- Search Table Content Start -->
						<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
							<tr>
								<th style="width: 150px;"><spring:message code="merchant.label.merchantname"/></th>
								<th style="width: 150px;"><spring:message code="common.label.state"/></th>
								<th style="width: 150px;"><spring:message code="merchant.label.accountnumber"/></th>
								<th style="width: 150px;"><spring:message code="merchant.label.contactperson"/></th>
								<th style="width: 100px;"><spring:message code="merchant.label.currentbalance"/></th>
								<th style="width: 100px;"><spring:message code="common.label.status"/></th>
								<th style="width: 100px;" class="sorter-false tablesorter-header tablesorter-headerUnSorted"><spring:message code="common.label.action"/></th>
							</tr>
							</thead>
							<tbody>
								<!-- <tr>
									<td class="sale-op-r">R</td>
									<td class="sale-op-v">V</td>
								</tr> -->
								<c:choose>
								<c:when test="${!(fn:length(searchResponse.merchantAccountSearchDtoList) eq 0) }">
									<c:forEach items="${searchResponse.merchantAccountSearchDtoList}" var="accountData">
										<tr>
											<td class="tbl-text-align-left">${accountData.businessName}</td>
											<td class="tbl-text-align-left">${accountData.bankState}</td>
											<td class="tbl-text-align-left">${accountData.merchantAccountNumber}</td>
											<td class="tbl-text-align-left"">${accountData.bankAccountName}</td>
											<td style="text-align: right;">${accountData.currentBalance}</td>
											<td class="tbl-text-align-left">${accountData.accountStatus}</td>
											<td style="white-space:nowrap;">
												<c:if test="${accountData.accountStatus == 'Active'}">
												<c:if test="${fn:contains(existingFeatures,merchantAccountEdit)}">
													<a href="javascript:editMerchantAccount('${accountData.accountId}','${accountData.merchantCode}','${accountData.merchantType}')" title="Edit" class="table-actionicon-margin">
													<span class="glyphicon glyphicon-pencil"></span></a>
													</c:if>
												</c:if> 
												<c:choose>
													<c:when test="${accountData.accountStatus == 'Active'}">
													<c:if test="${fn:contains(existingFeatures,merchantAccountSuspend)}">
														<a href="javascript:changeAccountStatus('${accountData.accountId}','Suspended')" title="Suspend">
															<img src="../images/deactive.png" alt="Suspend" title="Suspend"></img>
	                 									</a>
	                 									</c:if>
													</c:when>
													<c:when test="${accountData.accountStatus == 'Suspended'}">
													<c:if test="${fn:contains(existingFeatures,merchantAccountActivate)}">
														<a href="javascript:changeAccountStatus('${accountData.accountId}','Active')" title="Activate">
															<img src="../images/active.png" alt="Active" title="Activate"></img>
	                 									</a>
	                 									</c:if>
													</c:when>
													<c:otherwise>
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr><td colspan="10" style="color: red;"><spring:message code="merchant.label.nomerchantsfound"/></td></tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						<table class="table table-striped table-bordered table-condensed">
							<c:if test="${ !(fn:length(searchResponse.merchantAccountSearchDtoList) eq 0)}">
								<tr class="table-footer-main">
									<td colspan="10" class="search-table-header-column">
										<div class="col-sm-12">
											<div class="col-sm-3">
												<div class="btn-toolbar" role="toolbar">
													<div class="btn-group custom-table-footer-button">
														<a href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.XLS_FILE_FORMAT%>', ${totalRecords})">
															<button type="button" class="btn btn-default"><img src="../images/excel.png"> </button>
														</a> 
														<a href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.PDF_FILE_FORMAT%>', ${totalRecords})">
															<button type="button" class="btn btn-default"><img src="../images/pdf.png"></button>
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
					<!-- Search Table Block End -->
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

	<div id="my_popup" class="locatioin-list-popup">
		<span class="glyphicon glyphicon-remove" onclick="closePopup()"></span>
		<h2><spring:message code="sub-merchant-account-search.label.changestatus"/></h2>
		<form:form action="change-Account-Status" name="changeStatusForm" method="post">
			<input type="hidden" id="csAccountId" name="accountId" />
			<input type="hidden" id="csAccountStatus" name="accountStatus" />
			<input type="hidden" id="csMerchantType" name="merchantType" />
			<input type="hidden" name="CSRFToken" value="${tokenval}">
			<label data-toggle="tooltip" data-placement="top" title=""><span class="required-field">*</span> Reason </label>
			<textarea id="reason" name="reason" maxlength="500" onblur="validatePopupDesc();clientValidation('reason', 'reason','popDescError_div')"></textarea>
			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
				<span class="red-error" id="popDescError_div">&nbsp;</span>
			</div>
			<!--Panel Action Button Start -->
			<div class="col-sm-12 form-action-buttons">
				<div class="col-sm-12">
					<input type="submit" class="form-control button pull-right" value="Submit" onclick="return validatePopupDesc();">
				</div>
			</div>
		</form:form>
		<!--Panel Action Button End -->
	</div>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/sortable.js"></script>
	<script src="../js/common-lib.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/merchant.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	
	<script>
	
		var sortProperty = "${sortProperty}" + "";
		
		if($.trim(sortProperty).length > 0) {
			var sortPropArr = sortProperty.split(",");
			$('#serviceResults').tablesorter({
				sortList: [[parseInt(sortPropArr[0]),parseInt(sortPropArr[1])]]
			});
		}	
	
		$(document).ready(function() {
			
			$("#navListId6").addClass("active-background");
			
			/* $('.sale-op-v').on('click',function() {
				$('#voidPopup').popup("show");
			});
			
			$('.sale-op-r').on('click',function() {
				$('#refundPopup').popup("show");
			});
			
			$('.refundType').on('click', function() {
				if($(this).hasClass('fullRefund')) {
					$('#refundAmtDiv').hide();
				} else {
					$('#refundAmtDiv').show();
				}
			}); */
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