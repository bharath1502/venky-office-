<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
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
			
				Header Logo Start				
				<div class="col-sm-4"> 
					<img src="images/chatak_logo.jpg" height="35px" alt="Logo"/>
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
						<span class="breadcrumb-text"><spring:message code="commission-program-search.label.programs" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="commission-program-search.label.commisisionprogram" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="commission-program-search.label.search" /></span>
					</div>
					<!-- Breadcrumb End -->
					
					
					<form:form action="commissionProgramPagination" name="paginationForm" method="post">
						<input type="hidden" id="pageNumberId" name="pageNumber" /> 
						<input type="hidden" id="totalRecordsId" name="totalRecords" />
						<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>
					
							<form:form action="commissionProgramEdit" name="commissionProgramEditForm" method="post">
									<input type="hidden" id="getCommissionId" name="getCommissionId" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								
								<form:form action="get-commission-program-report" name="downloadReport" method="post">
									<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" /> 
									<input type="hidden" id="downloadTypeId" name="downloadType" />
									<input type="hidden" id="totalRecords" name="totalRecords" />
									<input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
					
					<!-- Tab Buttons Start -->
					<c:if test="${fn:contains(existingFeatures,commissionProgramsEdit)}">
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="commission-program-search.label.searchtab" /> </a>
					</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,commissionProgramsCreate)}">
					<div class="tab-header-container">
						<a href="show-commission-program-create"><spring:message code="commission-program-search.label.createtab" /></a>
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
										<span class="red-error">${error}</span> <span
											class="green-error">${sucess}</span>
									</div>
								</div>

                                    <form:form action="commissionProgramSearch"
									modelAttribute="commissionDTO" method="post">
									<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="commission-program-search.label.commissionprogramname" /></label>
													<form:input path="commissionName" id="commissionName"
														cssClass="form-control" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
											
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="commission-program-search.label.status" /></label>
													
													<form:select cssClass="form-control" id="status"
														path="status">
														<form:option value=""><spring:message code="commission-program-search.label.all" /></form:option>
														<form:option value="Active"><spring:message code="commission-program-search.label.active" /></form:option>
														<form:option value="Suspend"><spring:message code="commission-program-search.label.suspend" /></form:option>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												
												
												 <fieldset class="col-sm-4">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="commission-program-search.label.recordsperpage" /></label>
													<form:select cssClass="form-control" path="pageSize" id="pageSize">
														<form:option value="10"><spring:message code="commission-program-search.label.10" /></form:option>
														<form:option value="20"><spring:message code="commission-program-search.label.20" /></form:option>
														<form:option value="50"><spring:message code="commission-program-search.label.50" /></form:option>
														<form:option value="100"><spring:message code="commission-program-search.label.100" /></form:option>
														<form:option value="250"><spring:message code="commission-program-search.label.250" /></form:option>
														<form:option value="500"><spring:message code="commission-program-search.label.500" /></form:option>
														<form:option value="1000"><spring:message code="commission-program-search.label.1000" /></form:option>
													</form:select>
												</fieldset>
											</div>
										</div>
										<!--Panel Action Button Start -->
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit" class="form-control button pull-right"
													value="<spring:message code="commission-program-search.label.searchbutton" />"> <input type="button"
													class="form-control button pull-right" value="<spring:message code="commission-program-search.label.resetbutton" />"
													onclick= "resetCommissionSearch()">
											</div>
										</div>
										<!--Panel Action Button End -->
									</div>
								</form:form>
							</div>
						</div>
					</div>
					<div class="search-results-table">
						<table class="table table-striped table-bordered table-condensed" style="margin: 1px;">
							<!-- Search Table Header Start -->
							<tr>
								<td colspan="10" class="search-table-header-column"><span
									class="glyphicon glyphicon-search search-table-icon-text"></span>
									<span><spring:message code="commission-program-search.label.searchtable" /></span>
									<span class="pull-right"><spring:message code="commission-program-search.label.totalcounttable" /><label id="totalCount">${totalRecords}</label></span>
									</td>
							</tr>
							</table>
							<!-- Search Table Header End -->
							<!-- Search Table Content Start -->
							<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
							<tr>
								<th><spring:message code="commission-program-search.label.commissionprogramnametable" /></th>
								
								<th><spring:message code="commission-program-search.label.statustable" /></th>
								<th><spring:message code="commission-program-search.label.actiontable" /></th>
							</tr>
							</thead>
							<c:choose>
								<c:when test="${!(fn:length(commissionDTOList) eq 0) }">
									<c:forEach items="${commissionDTOList}" var="searchDetails">
										<tr>
											<td>${searchDetails.commissionName}</td>
											<%-- <td>${searchDetails.processor} --%>
											<td>${searchDetails.status}</td>
											<td>
											<c:if test="${fn:contains(existingFeatures,commissionProgramsEdit)}">
											<a
												href="javascript:commissionProgramEdit('${searchDetails.commissionProgramId }')"
												title="Edit" class="table-actionicon-margin"><span
													class="glyphicon glyphicon-pencil"></span></a>
													</c:if> 
													
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="7" style="color: red;"><spring:message code="commission-program-search.label.norecordsfound" /></td>
									</tr>
								</c:otherwise>
							</c:choose>
							</table>
							<table class="table table-striped table-bordered table-condensed">
							<c:if test="${ !(fn:length(commissionDTOList) eq 0)}">
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
															href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.PDF_FILE_FORMAT%>',  ${totalRecords})">
															<button type="button" class="btn btn-default">
																<img src="../images/pdf.png">
															</button>
														</a>
														<a>
															<input type="checkbox" class="autoCheck check" id="totalRecordsDownload">
															<spring:message code="commission-program-search.label.downloadall" /> 
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
	<script src="../js/sortable.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/commission-program.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script>	
		$(document).ready(function() {
			$( "#navListId3" ).addClass( "active-background" );
		});
		
		/* Select li full area function Start */
		$("li").click(function(){
			window.location=$(this).find("a").attr("href"); 
			return false;
		});
		/* Select li full area function End */	
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