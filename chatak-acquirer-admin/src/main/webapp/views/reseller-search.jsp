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
			<!--Header Block Start -->
			<!-- 		<header class="col-sm-12 all-page-header">
				Header Logo Start				
				<div class="col-sm-4"> 
					<img src="../images/chatak_logo.jpg" height="35px" alt="Logo"/>
				</div>
				Header Logo End	
				Header Welcome Text and Logout button Start
				<div class="col-sm-5 col-xs-offset-3">
					<div class="pull-right user-settings">
						<table>
							<tr>
								<td><a href="#"><span class="glyphicon glyphicon-log-out"></span> Logout</a></td>
							</tr>
						</table>
					</div>
				</div>
				Header Welcome Text and Logout button End	
			</header> -->
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
						<span class="breadcrumb-text"><spring:message code="manage.label.manage"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span>
						<span class="breadcrumb-text"><spring:message code="reseller-create.label.reseller"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="common.label.search"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<c:if test="${fn:contains(existingFeatures,resellerView) || fn:contains(existingFeatures,resellerEdit) || fn:contains(existingFeatures,resellerDelete)}">
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="common.label.search"/></a>
					</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,resellerCreate)}">
					<div class="tab-header-container">
						<a href="reseller-create-page"><spring:message code="common.label.create"/></a>
					</div>
					</c:if>
					<!-- <div class="tab-header-container">
						<a href="merchant-create">Create</a>
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

								<form:form action="getResellers" name="paginationForm" method="post">
									<input type="hidden" id="pageNumberId" name="pageNumber" /> 
									<input type="hidden" id="totalRecordsId" name="totalRecords" />
								    <input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								<form:form action="editReseller" name="editResellerForm" method="post">
									<input type="hidden" id="getResellerId" name="getResellerId" />
								    <input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								
								
								<form:form action="reseller-viewdata" name="viewResellerForm" method="post">
									<input type="hidden" id="resellerViewresellerId" name="resellerViewresellerId" />
								    <input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

								<form:form action="deleteReseller" name="deleteResellerForm" method="post">
									<input type="hidden" id="getResellerId1" name="getResellerId" />
								    <input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

								<form:form action="get-reseller-report" name="downloadReport" method="post">
									<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" /> 
									<input type="hidden" id="downloadTypeId" name="downloadType" />
									<input type="hidden" id="totalRecords" name="totalRecords" />
									<input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>								

								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
							<form:form action="searchReseller" modelAttribute="resellerData" name="resellerData">
								 <input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12 paddingT20">
										<div class="row">
											<!-- Account Details Content Start -->
											<section class="field-element-row account-details-content" >
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="reseller-create.label.resellername"/></label>
														<form:input cssClass="form-control" path="resellerName"
															id="resellerName" maxlength="50"
															/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="businessNameEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													
														<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="reseller-create.label.accountnumber"/></label>
														<form:input cssClass="form-control" path="accountNumber"
															id="accountNumber" maxlength="50"
															/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="businessNameEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""> <spring:message code="common.label.emailid"/></label>
														<form:input cssClass="form-control" path="emailId"
															id="emailId" maxlength="50"  />
														<!-- <div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="lastNameEr" class="red-error">&nbsp;</span>
														</div> -->
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""> <spring:message code="reseller-create.label.phone"/></label>
														<form:input cssClass="form-control" path="phone"
															id="phone" maxlength="10"  />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="phoneEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.city"/></label>
														<form:input cssClass="form-control" path="city" id="city"
															maxlength="50"  />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="cityEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.country"/></label>
														<form:select cssClass="form-control" path="country"
															id="country" 
															onchange="fetchMerchantState(this.value, 'state')">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<c:forEach items="${countryList}" var="country">
																<form:option value="${country.label}">${country.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="countryEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.status"/></label>
														<form:select cssClass="form-control" path="status"
															id="status">
															<%-- <form:option value="1">Pending</form:option> --%>
															<form:option value="">All</form:option>
															<form:option value="0"><spring:message code="common.label.active"/></form:option>
															<form:option value="1"><spring:message code="common.label.pending"/></form:option>
															<form:option value="2"><spring:message code="common.label.inactive"/></form:option>
															<form:option value="3"><spring:message code="common.label.deleted"/></form:option>
														</form:select>
														<%-- <form:hidden path="merchantType" id="merchantType" /> --%>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="statusEr" class="red-error">&nbsp;</span>
														</div>
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
												</fieldset>
											<!--Panel Action Button Start -->
										    <div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit" value='<spring:message code="common.label.search"/>' class="form-control button pull-right"
													> <input type="button"
													class="form-control button pull-right" value='<spring:message code="common.label.reset"/>' onclick="goToRsellerSearch()">
											</div>
										</div>
										<!--Panel Action Button End -->
									</section>		
									</div>	
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
								<td colspan="10" class="search-table-header-column"><span
									class="glyphicon glyphicon-search search-table-icon-text"></span>
									<span><spring:message code="common.label.search"/></span>
									<span class="pull-right"><spring:message code="common.label.totalcount"/> : <label id="totalCount">${totalRecords}</label></span></td>
							</tr>
							</table>
							<!-- Search Table Header End -->
							<!-- Search Table Content Start -->
							<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
							<tr>
							
								<th style="width: 151px;"><spring:message code="reseller-create.label.resellername"/></th>
								<th style="width: 151px;"><spring:message code="reseller-create.label.accountnumber"/></th>
								<th style="width: 92px;"><spring:message code="reseller-create.label.accountbalance"/></th>
								<th style="width: 93px;"><spring:message code="common.label.emailid"/></th>
								<th style="width: 72px;"><spring:message code="reseller-create.label.phone"/></th>
								<th style="width: 72px;"><spring:message code="common.label.city"/></th>
								<th style="width: 72px;"><spring:message code="common.label.country"/></th>
								<th style="width: 93px;"><spring:message code="common.label.status"/></th>
								<th class="sorter-false tablesorter-header tablesorter-headerUnSorted" style="width: 93px;" ><spring:message code="common.label.action"/></th>
							</tr>
							</thead>
							<c:choose>
								<c:when test="${!(fn:length(resellerValues) eq 0) }">
									<c:forEach items="${resellerValues}" var="resellerData">
										<tr>
											<td><div class="feeDescDiv">${resellerData.resellerName }</div></td>
											<td>${resellerData.accountNumber }</td>
											<td><div class="feeDescDiv" align  = 'right'>${resellerData.accountBalance }</div></td>
											<td><div class="feeDescDiv">${resellerData.emailId }</div></td>
											<td><div class="feeDescDiv">${resellerData.phone }</div></td>
											<td><div class="feeDescDiv">${resellerData.city }</div></td>
											<td><div class="feeDescDiv">${resellerData.country}</div></td>
											<td>${resellerData.status }</td>
											<td style="white-space:nowrap;"><c:if test="${resellerData.status == 'Active'|| resellerData.status == 'Self-Registered' }">
											<c:if test="${fn:contains(existingFeatures,resellerView)}">
													<a href="javascript:viewResellerInfo('${resellerData.resellerId}','Resellerdata')"><img src="../images/eyeimage.png" alt="View" title="View"></img>
													<span class="glyphicon glyphicon-eye"></span></a>
													</c:if>
												</c:if> <c:if test="${resellerData.status == 'Active' || resellerData.status == 'In-Active'|| resellerData.status == 'Pending'|| merchantData.status == 'Self-Registered'  }">
													<c:if test="${fn:contains(existingFeatures,resellerEdit)}">
													<a href="javascript:editReseller('${resellerData.resellerId}')" title="Edit" class="table-actionicon-margin">
													<span class="glyphicon glyphicon-pencil"></span></a>
													</c:if>
												</c:if> <c:if test="${resellerData.status == 'Active'}">
												<c:if test="${fn:contains(existingFeatures,resellerDelete)}">
													<a href="javascript:confirmDeleteReseller('${resellerData.resellerId}','Resellerdata')">
													<span class="glyphicon glyphicon-trash" title="Delete"></span></a>
													</c:if>
												</c:if></td>
										</tr>
									</c:forEach>
									<%-- <tr>
										<c:choose>
											<c:when test="${!(fn:length(subMerchants) eq 0) }">
												<c:forEach items="${subMerchants}" var="subMerchantData">
													<c:if
														test="${subMerchantData.status == 'Active' || subMerchantData.status == 'In-Active'|| subMerchantData.status == 'Pending'|| merchantData.status == 'Self-Registered' }">
														<button onclick="javascript:viewMerchants('${subMerchantData.id }')" title="View Sub Merchant" class="table-actionicon-margin pull-right">View
															Sub merchant</button>
													</c:if>

												</c:forEach>
											</c:when>
											<c:otherwise></c:otherwise>
										</c:choose>
									</tr> --%>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="10" style="color: red;">No Reseller found</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</table>
							<table class="table table-striped table-bordered table-condensed">
							<c:if test="${ !(fn:length(resellerValues) eq 0)}">
								<tr class="table-footer-main">
									<td colspan="10" class="search-table-header-column">
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

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/sortable.js"></script>

	<script src="../js/merchant.js"></script>
	<script src="../js/reseller.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		$(document).ready(function() {
			$("#navListId6").addClass("active-background");
			
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


