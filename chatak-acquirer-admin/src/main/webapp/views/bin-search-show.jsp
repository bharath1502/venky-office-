<!DOCTYPE html>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="java.util.Calendar"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%
  int year = Calendar.getInstance().get(Calendar.YEAR);
%>

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
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->

			<!--Header Block End -->
			<!--Navigation Block Start -->
			<nav class="col-sm-12 nav-bar" id="main-navigation">
				<%-- <jsp:include page="header.jsp" /> --%>
				<%@include file="navigation-panel.jsp"%>
			</nav>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="setup.label.setup"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text" id="userType"><spring:message code="bin.label.onus"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="common.label.search"/></span>
					</div>
					<!-- Breadcrumb end -->
					<!-- Tab Buttons Start -->
					<c:if test="${fn:contains(existingFeatures,ONUSBINEdit)||fn:contains(existingFeatures,ONUSBINDelete)||fn:contains(existingFeatures,ONUSBINCreate)||fn:contains(existingFeatures,ONUSBINView)}">
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="common.label.search"/></a>
					</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,ONUSBINCreate)}">
					<div class="tab-header-container">
						<a href="show-bin-create"><spring:message code="common.label.create"/></a>
					</div>
					</c:if>
					<!-- Tab Buttons End -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="descriptionMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error">${error}</span> <span
											class="green-error">${sucess}</span>
									</div>
								</div>
								<form:form action="viewBin" name="viewBin" method="post">
									<input type="hidden" id="getViewBinId" name="getBinId" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								<form:form action="editBin" name="editBin" method="post">
									<input type="hidden" id="getBinId" name="getBinId" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								<form:form action="deleteBin" name="deleteBin" method="post">
									<input type="hidden" id="getBinID" name="getBinID" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								<form:form action="getBinForPagination" name="paginationForm" method="post">
									<input type="hidden" id="pageNumberId" name="pageNumber" /> 
									<input type="hidden" id="totalRecordsId" name="totalRecords" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								<form:form action="get-bin-report" name="downloadReport" method="post">
									<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" /> 
									<input type="hidden" id="downloadTypeId" name="downloadType" />
									<input type="hidden" id="totalRecords" name="totalRecords"/>
									<input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								<!--Success and Failure Message End-->
									<!-- Page Form Start -->
								<form:form action="bin-search" modelAttribute="binDTO" method="post">
									<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="bin.label.onus"/></label>
													<form:input path="binNumber"  id="binNumber" onkeypress="return numbersonly(this,event)" maxlength="6"
														cssClass="form-control" onblur="this.value=this.value.trim()"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="switch.label.switchname"/></label>
													<form:select path="switchId" id="switchId"
														cssClass="form-control" >
														<form:option value=""><spring:message code="reports.option.select"/></form:option>
														<c:forEach items="${switchName}" var="switchName">
															<form:option value="${switchName.id}">${switchName.switchName}</form:option>
														</c:forEach>
														</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
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
											</div>
										</div>
										<!--Panel Action Button Start -->
										<div
											class="col-sm-12 form-action-buttons pull-right zero-padding">
											<div class="col-sm-6 zero-padding"></div>
											<div class="col-sm-6 zero-padding">
												<input type="submit" value='<spring:message code="common.label.search"/>' class="form-control button pull-right"
													> <input type="button"
													class="form-control button pull-right" value='<spring:message code="common.label.reset"/>'
													onclick="resetBinSearch()">
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
					<!-- Search Table Content Start -->
					<c:if test="${flag ne false }">
					<div class="search-results-table">
						<table class="table table-striped table-bordered table-condensed" style="margin: 1px;">
							<!-- Search Table Header Start -->
							<tr>
								<td colspan="10" class="search-table-header-column"><span
									class="glyphicon glyphicon-search search-table-icon-text"></span>
									<span><spring:message code="common.label.search"/></span><span class="pull-right"><spring:message code="common.label.totalcount"/> : <label id="totalRecords">${totalRecords}</label></span></td>
							</tr>
							</table>
							<!-- Search Table Header End -->
							<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
							<tr>
								<th style="width: 200px;"><spring:message code="bin.label.binnumber"/></th>
								<th style="width: 200px;"><spring:message code="bin.label.emvsupport"/></th>
								<th style="width: 200px;"><spring:message code="bin.label.dccsupport"/></th>
								<th style="width: 200px;"><spring:message code="switch.label.switchname"/></th>
								<th style="width: 150px;"><spring:message code="common.label.status"/></th>
								<th style="width: 120px;" class="sorter-false tablesorter-header tablesorter-headerUnSorted"><spring:message code="common.label.action"/></th>
							</tr>
							</thead>
								<c:choose>
								<c:when test="${!(fn:length(binList) eq 0) }">
									<c:forEach items="${binList}" var="binData">
										<tr>
											<td class="tbl-text-align-right">${binData.binNumber }</td>
											<td>
											<c:if test="${binData.emvSupported == '1'}">
											<span><spring:message code="bin.label.yes"/></span>
											</c:if>
											<c:if test="${binData.emvSupported == '0'}">
											<span><spring:message code="bin.label.no"/></span>
											</c:if>
											</td>
											<td>
											<c:if test="${binData.dccSupported == '1'}">
											<span><spring:message code="bin.label.yes"/></span>
											</c:if>
											<c:if test="${binData.dccSupported == '0'}">
											<span><spring:message code="bin.label.no"/></span>
											</c:if>
											</td>
											<td class="tbl-text-align-left">${binData.switchName }</td>
											<td class="tbl-text-align-left">${binData.status }</td>
											<td  style="white-space:nowrap;">
												<c:if test="${fn:contains(existingFeatures,ONUSBINView)}">
													<a href="javascript:viewBin('${binData.id }')">
														<img src="../images/eyeimage.png" alt="<spring:message code="common.label.view"/>" title="<spring:message code="common.label.view"/>"></img>
														<span class="glyphicon glyphicon-eye"></span></a>
												</c:if>
											
											<c:if test="${binData.status == 'Active'}">
											      <a href="javascript:changeStatus('${binData.id}','Suspended','Suspended','binPopupDiv')" title="Suspend">
												  <img src="../images/active.png" alt="Suspend" title="Suspend"></img></a>
												  <c:if test="${fn:contains(existingFeatures,ONUSBINEdit)}">
												  	<a href="javascript:editBin('${binData.id }')" title="Edit" class="table-actionicon-margin">
												  	<span class="glyphicon glyphicon-pencil"></span></a>
												  </c:if>
												  <c:if test="${fn:contains(existingFeatures,ONUSBINDelete)}">
												  	<a href="javascript:confirmDeleteBIN('${binData.id }')">
												  	<span class="glyphicon glyphicon-trash"></span></a>
												  </c:if>
											</c:if> 
											<c:if test="${binData.status == 'Suspended'}">
												  <a href="javascript:changeStatus('${binData.id}','Active','Active','binPopupDiv')" title="Active">
												  <img alt="Active" src="../images/deactive.png" title="Activate"></img></a>
											</c:if>
											</td>
										</tr>
									</c:forEach>
									<tr>
									</tr>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="10" style="color: red;"><spring:message code="bin-search-show.label.nobinsfound"/></td>
									</tr>
								</c:otherwise>
							</c:choose>
							</table>
							<table class="table table-striped table-bordered table-condensed">
							<c:if test="${ !(fn:length(binList) eq 0)}">
								<tr class="table-footer-main">
									<td colspan="10" class="search-table-header-column">
										<div class="col-sm-12">
											<div class="col-sm-3">
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
					</c:if>
					<!-- Search Table Block End -->
							</div>
							<jsp:include page="common-delete.jsp"></jsp:include>
			</article>
			<!--Article Block End-->
			<div class="footer-container">
				<jsp:include page="footer.jsp"></jsp:include>
			</div>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->
	<div id="binPopupDiv" class="locatioin-list-popup">
		<span class="glyphicon glyphicon-remove"
			onclick="closePopup();clearPopupDesc();"></span>
		<h2><spring:message code="prepaid-admin-programmanager-search-label.ChangeStatus"/></h2>
		<form:form action="binActivationSuspention" name="binActivationSuspentionForm" method="post">
			<input type="hidden" id="suspendActiveId" name="binId" /> <input
				type="hidden" id="suspendActiveStatus" name="binStatus" /> 
				<input type="hidden" name="CSRFToken" value="${tokenval}">
				<label><span class="requiredFiled">*</span> <spring:message code="prepaid-admin-label.Reason"/> </label>
			<textarea id="reason" name="reason" maxlength="<%= StatusConstants.REASON %>"
				onblur="validatePopupDesc();clientValidation('reason', 'reason','popDescError_div')"></textarea>
			<div class="discriptionErrorMsg">
				<span class="red-error" id="popDescError_div">&nbsp;</span>
			</div>
			<!--Panel Action Button Start -->
			<div class="col-sm-12 form-action-buttons">
				<div class="col-sm-12">
					<input type="submit" class="form-control button pull-right"
						value="<spring:message code="prepaid-admin-button.submit"/>" onclick="return validatePopupDesc();">
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
	<script src="../js/common-lib.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/sortable.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/bin.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
</body>
<script>
	$(document).ready(function() {
		$("#navListId2").addClass("active-background");
	});

	function resetBinSearch() {
		window.location.href = 'bin-search-show';
	}

	$(document).ready(function() {
		$('#binPopupDiv').popup({
			blur : false
		});
		var error = $
		{
			'binData.binNumber'
		}
		;
		if (error != null) {
			setDiv("binNumber", "");
		}
	});
	
	function closePopup() {
		$('#binPopupDiv').popup("hide");
		$('#deletePopup').popup("hide");
	}
	function openPopup() {
		$('#binPopupDiv').popup("show");
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
</html>