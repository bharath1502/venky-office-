<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.Calendar"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
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
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css" />
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<!--Header Block End -->
			<!--Navigation Block Start -->
			<!-- <nav class="col-sm-12 nav-bar" id="main-navigation"> -->
				<%-- <jsp:include page="header.jsp"></jsp:include> --%>
				<%@include file="navigation-panel.jsp"%>
			<!-- </nav> -->
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="manage.label.manage"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span>
						<span class="breadcrumb-text"><spring:message code="dcc.label.markup"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="common.label.search"/></span>
						
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<c:if test="${fn:contains(existingFeatures,dccMarkupEdit)||fn:contains(existingFeatures,dccMarkupDelete)}">
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="common.label.search"/> </a>
					</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,dccMarkupCreate)}">
					<div class="tab-header-container">
						<a href="chatak-admin-dcc-markup-create-page"><spring:message code="common.label.create"/></a>
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
										<span class="red-error" id="errorData">${error}</span> <span
											class="green-error">${sucess}</span>
									</div>
								</div>

								<form:form action="getFeeProgramDetails" name="paginationForm" method="post">
									<input type="hidden" id="pageNumberId" name="pageNumber" /> 
									<input type="hidden" id="totalRecordsId" name="totalRecords" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

								<form:form action="editDccMarkup" name="editDccMarkupForm" method="post">
									<input type="hidden" id="getMerchantCode" name="getMerchantCode" />
									<input type="hidden" id="getMerchantName" name="getMerchantName" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

								<form:form action="downloadFeeProgramreport" name="downloadReport" method="post">
									<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" /> 
									<input type="hidden" id="downloadTypeId" name="downloadType" />
									<input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
				      				<input type="hidden" id="totalRecordsflag" name="totalRecords" />
				      				<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								
								<form:form action="deleteDccMarkup" name="deleteDccMarkupForm" method="post">
									<input type="hidden" id="merchantCodeId" name="merchantCodeId" /> 
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="chatak-admin-search-markup-fee" modelAttribute="dccMarkup" method="post">
								<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="dcc.label.merchantname"/></label>
													<form:select cssClass="form-control" path="merchantName" id="dccName">
														<form:option value=""><spring:message code="fee-program-create.label.createtab"/></form:option>
														<c:forEach items="${merchantNamesList}" var="merchant">
															<form:option value="${merchant.merchantCode}">${merchant.merchantName}</form:option>
														</c:forEach>
													</form:select>
												</fieldset>
												
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.status"/></label>
													<form:select cssClass="form-control" id="status" path="status">
														<form:option value=""><spring:message code="common.label.all"/></form:option>
														<form:option value="0"><spring:message code="common.label.active"/></form:option>
														<form:option value="3"><spring:message code="common.label.deleted"/></form:option>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>
										</div>
										<!--Panel Action Button Start -->
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit" value='<spring:message code="common.label.search"/>' class="form-control button pull-right" > 
												<input type="button" class="form-control button pull-right" value='<spring:message code="common.label.reset"/>' onclick="resetMarkupFeeSearch()">
											</div>
										</div>
										<!--Panel Action Button End -->
									</div>
								</form:form>
								<!-- Page Form End -->
							</div>
						</div>
					</div>
					<div class="search-results-table">
						<table class="table table-striped table-bordered table-condensed" style="margin: 1px;">
							<!-- Search Table Header Start -->
							<tr>
								<td colspan="10" class="search-table-header-column"><span
									class="glyphicon glyphicon-search search-table-icon-text"></span>
									<span><spring:message code="common.label.search"/></span></td>
									<td class="search-table-header-column" style ="font-weight:bold;text-align: right;"><spring:message code="common.label.totalcount"/> : ${totalRecords}</td>
							</tr>
							</table>
							<!-- Search Table Header End -->
							<!-- Search Table Content Start -->
							<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
							<tr>
								<th><spring:message code="dcc.label.merchantname"/></th>
								<th><spring:message code="dcc.label.merchantcode"/></th>
								<th><spring:message code="common.label.status"/></th>
								<th><spring:message code="common.label.action"/></th>
							</tr>
							</thead>
							<c:choose>
								<c:when test="${!(fn:length(markupFee) eq 0) }">
									<c:forEach items="${markupFee}" var="markupFee">
										<tr>
											<td>${markupFee.merchantName}</td>
											<td>${markupFee.merchantCode}</td>
											<td><c:choose>
												 <c:when test="${markupFee.status eq '0' }">Active</c:when>
												<c:otherwise>
													<c:if test="${markupFee.status eq '3' }">Deleted</c:if>
												</c:otherwise>
											</c:choose></td>
											<td>
											<c:if test="${fn:contains(existingFeatures,dccMarkupEdit)}">
											<a href="javascript:editdccMarkup('${markupFee.merchantCode}','${markupFee.merchantName }')" title="Edit" class="table-actionicon-margin"><span
													class="glyphicon glyphicon-pencil"></span></a> 
													</c:if>
													<c:if test="${fn:contains(existingFeatures,dccMarkupDelete)}">
													<c:if test="${markupFee.status eq '0'}">
													<a href="javascript:confirmDeleteDccMarkup('${markupFee.merchantCode}')">
													<span class="glyphicon glyphicon-trash"></span></a>
													</c:if>
													</c:if>
											 </td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="7" style="color: red;"><spring:message code="common.label.norecords"/></td>
									</tr>
								</c:otherwise>
							</c:choose>
							</table>
							<table class="table table-striped table-bordered table-condensed">
							<c:if test="${ !(fn:length(markupFee) eq 0)}">
								<tr class="table-footer-main">
									<td colspan="10" class="search-table-header-column">
										<div class="col-sm-12">
											<div class="col-sm-3">
												<div class="btn-toolbar" role="toolbar">
													<div class="btn-group custom-table-footer-button">
														<a href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.XLS_FILE_FORMAT%>','${totalRecords}')">
															<button type="button" class="btn btn-default">
																<img src="../images/excel.png">
															</button>
														</a> 
														<a href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.PDF_FILE_FORMAT%>','${totalRecords}')">
															<button type="button" class="btn btn-default">
																<img src="../images/pdf.png">
															</button>
														</a>
													</div>
													<div>
														<input type="checkbox" class="autoCheck check" id="totalRecordsDownload"><%=Constants.DOWNLOAD_ALL_LABEL %> 
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
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/chatak-ajax.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/sortable.js"></script>
	<script type="text/javascript" src="../js/dcc-markup.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script>
	$(document).ready(function() {
		$( "#navListId6" ).addClass( "active-background" );
	});
	
	/* Select li full area function Start */
	$("li").click(function(){
		window.location=$(this).find("a").attr("href"); 
		return false;
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