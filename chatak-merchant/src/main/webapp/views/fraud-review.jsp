<!DOCTYPE html>
<%@ page import="com.chatak.merchant.constants.JSPConstants"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Chatak Acquirer Merchant</title>
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
<body>
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Navigation Block Start -->
			<jsp:include page="header.jsp"></jsp:include> 
			<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text">Fraud</span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span>
						<span class="breadcrumb-text">Fraud Review</span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first active-background">
						<a href="#">Search</a>
					</div>
					<!-- <div class="tab-header-container">
						<a href="merchant-details-account-create?entityType=Merchant">Create</a>
					</div> -->
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

								<%-- <form action="merchant-account-search-pagination" name="paginationForm" method="post">
									<input type="hidden" id="pageNumberId" name="pageNumber" /> 
									<input type="hidden" id="totalRecordsId" name="totalRecords" />
									<input type="hidden" id="sortProperty" name="sortProperty" />
								</form>
								
								<form action="merchant-account-search-report" name="downloadReport" method="post">
									<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" /> 
									<input type="hidden" id="downloadTypeId" name="downloadType" />
									<input type="hidden" id="totalRecords" name="totalRecords" />
									<input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
								</form>
								
								<form action="merchant-account-edit-page" name="editMercahntAccountForm" method="post">
									<input type="hidden" id="editAccountId" name="accountId" />
									<input type="hidden" id="editmerchantCode" name="merchantCode" />
									<input type="hidden" id="editMerchantType" name="merchantType" />
								</form>
								 --%>
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
								<form:form action="fraud-review" commandName="merchantAccountSearchDto" name="merchantAccountSearchDto">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title="">Action</label>
													<form:select id="action" path="action"
														cssClass="form-control"">
														<form:option value="">..:Select:..</form:option>
														<form:option value="Auth+Cap">Auth + Cap</form:option>
														<form:option value="Auth+Hold">Auth + Hold</form:option>
														<form:option value="No_Auth+Hold">No Auth + Hold</form:option>
														<form:option value="Deny">Deny</form:option>
													</form:select>
												</fieldset>
												
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title="">Payment Type</label>
													<form:select cssClass="form-control" path="accountStatus" id="accountStatus">
														<form:option value="">..:Select:..</form:option>
														<form:option value="Visa">Visa</form:option>
														<form:option value="MasterCard">MasterCard</form:option>
														<form:option value="AMEX">AMEX</form:option>
													</form:select>
												</fieldset>
												
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title="">Card Holder Name</label>
													<form:input cssClass="form-control" path="businessName" id="businessName" />
												</fieldset>
											
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title="">Transaction ID</label>
													<form:input cssClass="form-control" path="merchantAccountNumber" id="merchantAccountNumber" />
												</fieldset>
												
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title="">Transaction amount range</label>
													<form:input cssClass="form-control" path="businessName" id="businessName" />
												</fieldset>
												
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title="">From Date</label>
													<div class="input-group focus-field">
														<form:input path="from_date" id="fromDate"
															cssClass="form-control effectiveDate" />
														<span class="input-group-addon"><span
															class="glyphicon glyphicon-calendar"></span></span>
													</div>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="tranFromDateErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title="">To Date</label>
													<div class="input-group focus-field">
														<form:input path="to_date"
															cssClass="form-control effectiveDate" id="toDate" />
														<span class="input-group-addon"><span
															class="glyphicon glyphicon-calendar"></span></span>
													</div>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title="">Records Per Page</label>
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
												<input type="submit" class="form-control button pull-right" value="Search"> 
												<input type="button" class="form-control button pull-right" value="Reset" onclick="resetMerchantAccountSearch()">
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
									<span>Search Summary</span>
									<span class="pull-right">Total Count : <label id="totalCount">${searchResponse.totalNoOfRows}</label></span>
								</td>
							</tr>
						</table>
						<!-- Search Table Header End -->
						<!-- Search Table Content Start -->
						<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
							<tr>
								<th style="width: 100px;">Transaction ID</th>
								<th style="width: 150px;">Card Number</th>
								<th style="width: 150px;">Card Holder Name</th>
								<th style="width: 150px;">Payment Type</th>
								<th style="width: 100px;">Filter Type</th>
								<th style="width: 100px;">Status</th>
								<th style="width: 100px;">Amount</th>
								<th style="width: 100px;">Date/Time stamp</th>
								<th style="width: 100px;">Action</th>
								<th style="width: 100px;" class="sorter-false tablesorter-header tablesorter-headerUnSorted">Action</th>
							</tr>
							</thead>
							<tbody>
								<c:choose>
								<c:when test="${!(fn:length(searchResponse.merchantAccountSearchDtoList) eq 0) }">
									<c:forEach items="${searchResponse.merchantAccountSearchDtoList}" var="accountData">
										<tr>
											<td style="text-align: left;">${accountData.businessName}</td>
											<td>${accountData.bankState}</td>
											<td><a class="anchor-style">${accountData.merchantAccountNumber}</a></td>
											<td style="text-align: left;">${accountData.bankAccountName}</td>
											<td style="text-align: right;">${accountData.currentBalance}</td>
											<td>${accountData.accountStatus}</td>
											<td style="white-space:nowrap;">
												<c:if test="${accountData.accountStatus == 'Active'}">
													<a href="javascript:editMerchantAccount('${accountData.accountId}','${accountData.merchantCode}','${accountData.merchantType}')" title="Edit" class="table-actionicon-margin">
													<span class="glyphicon glyphicon-pencil"></span></a>
												</c:if> 
												<c:choose>
													<c:when test="${accountData.accountStatus == 'Active'}">
														<a href="javascript:changeAccountStatus('${accountData.accountId}','Suspended')" title="Suspend">
															<img src="../images/deactive.png" alt="Suspend" title="Suspend"></img>
	                 									</a>
													</c:when>
													<c:when test="${accountData.accountStatus == 'Suspended'}">
														<a href="javascript:changeAccountStatus('${accountData.accountId}','Active')" title="Activate">
															<img src="../images/active.png" alt="Active" title="Activate"></img>
	                 									</a>
													</c:when>
													<c:otherwise>
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr><td colspan="10" style="color: red;">No Merchants found</td></tr>
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
															Download All 
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
			<jsp:include page="footer.jsp"></jsp:include>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->

	<div id="my_popup" class="locatioin-list-popup">
		<span class="glyphicon glyphicon-remove" onclick="closePopup()"></span>
		<h2>Change Status</h2>
		<form action="change-Account-Status" name="changeStatusForm" method="post">
			<input type="hidden" id="csAccountId" name="accountId" />
			<input type="hidden" id="csAccountStatus" name="accountStatus" />
			<input type="hidden" id="csMerchantType" name="merchantType" />
			
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
		</form>
		<!--Panel Action Button End -->
	</div>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script> <script src="../js/utils.js"></script>
	<script src="../js/sorting.js"></script>
	<script src="../js/tablesorter.js"></script>
	<script src="../js/tablesorter.widgets.js"></script>
	<script src="../js/common-lib.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/merchant.js"></script>
	
	<script>
	
		var sortProperty = "${sortProperty}" + "";
		
		if($.trim(sortProperty).length > 0) {
			var sortPropArr = sortProperty.split(",");
			$('#serviceResults').tablesorter({
				sortList: [[parseInt(sortPropArr[0]),parseInt(sortPropArr[1])]]
			});
		}	
	
		$(document).ready(function() {
			
			$("#navListId4").addClass("active-background");
			
		});
	</script>
</body>
</html>