<!DOCTYPE html>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="java.util.Calendar"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
			<!--Navigation Block Start -->
			<%-- <jsp:include page="header.jsp"></jsp:include> --%>
			<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="setup.label.setup"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="merchant.label.code"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="common.label.search"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<c:if test="${fn:contains(existingFeatures,merchantCategoryCodeView)||fn:contains(existingFeatures,merchantCategoryCodeEdit)||fn:contains(existingFeatures,merchantCategoryCodeCreate)||fn:contains(existingFeatures,merchantCategoryCodeDelete)}">
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="common.label.search"/></a>
					</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,merchantCategoryCodeCreate)}">
					<div class="tab-header-container">
						<a href="merchant-category-code-create"><spring:message code="common.label.create"/></a>
					</div>
					</c:if>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error">&nbsp;${error}</span> <span
											class="green-error">&nbsp;${sucess}</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<form:form action="merchant-category-code-view" name="viewMCCForm"
									method="post">
									<input type="hidden" id="viewId" name="viewId" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

								<form:form action="merchant-category-code-edit" name="editMCCForm"
									method="post">
									<input type="hidden" id="editId" name="editId" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

								<form:form action="merchant-category-code-pagination"
									name="paginationForm" method="post">
									<input type="hidden" id="pageNumberId" name="pageNumber" /> <input
										type="hidden" id="totalRecordsId" name="totalRecords" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

								<form:form action="get-mcc-report" name="downloadReport"method="post">
									<input type="hidden" id="downloadPageNumberId"name="downLoadPageNumber" /> 
									<input type="hidden"id="downloadTypeId" name="downloadType" />
									<input type="hidden" id="totalRecords" name="totalRecords" />
									<input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								
								<form:form action="deleteMCC" name="deleteMCCForm" method="post">
						            <input type="hidden" id="getDeleteMCCId" name="getDeleteMCCId" /> 
						            <input type="hidden" name="CSRFToken" value="${tokenval}">
					            </form:form>

								<!-- Page Form Start -->
								<form:form action="merchant-category-code-search"
									modelAttribute="mcc" name="mcc" method="post">
								<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.code"/></label>
													<form:input cssClass="form-control"
														path="merchantCategoryCode" id="merchantCategoryCode" maxlength="4" onblur="this.value=this.value.trim()"/>
												</fieldset>

												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.status"/></label>
													<form:select cssClass="form-control" path="status"
														id="status">
														<form:option value=""><spring:message code="reports.option.select"/></form:option>
														<form:option value="Active"><spring:message code="common.label.active"/></form:option>
														<form:option value="Suspended"><spring:message code="common.label.inactive"/></form:option>
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
											</div>
										</div>
										<!--Panel Action Button Start -->
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit" value='<spring:message code="common.label.search"/>' class="form-control button pull-right"
													onclick="return trimUserData()"> <input type="button"
													class="form-control button pull-right" value='<spring:message code="common.label.reset"/>'
													onclick="cancelCreateOrUpdateMcc()">
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
					<div class="search-results-table">
						<table class="table table-striped table-bordered table-condensed"
							style="margin: 1px;">
							<!-- Search Table Header Start -->
							<tr>
								<td colspan="10" class="search-table-header-column"><span
									class="glyphicon glyphicon-search search-table-icon-text"></span>
									<span><spring:message code="common.label.search"/></span> <span class="pull-right"><spring:message code="common.label.totalcount"/> : <label id="totalRecords">${totalRecords}</label>
								</span></td>
							</tr>
						</table>
						<!-- Search Table Header End -->
						<!-- Search Table Content Start -->
						<table id="serviceResults"
							class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
								<tr>
									<th style="width: 150px;"><spring:message code="merchant.label.code"/></th>
									<th style="width: 150px;"><spring:message code="transaction.label.code"/></th>
									<th style="width: 150px;"><spring:message code="merchant.label.description"/></th>
									<th style="width: 100px;"><spring:message code="common.label.status"/></th>
									<th style="width: 100px;"class="sorter-false tablesorter-header tablesorter-headerUnSorted"><spring:message code="common.label.action"/></th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${!(fn:length(mccs) eq 0) }">
										<c:forEach items="${mccs}" var="mccs">
											<tr>
												<td class="tbl-text-align-left">${mccs.merchantCategoryCode}</td>
												<td class="tbl-text-align-left">${mccs.selectedTcc}</td>
												<td class="tbl-text-align-left"><div class="feeDescDiv tbl-text-align-left">${mccs.description}</div></td>
												<td class="tbl-text-align-left">${mccs.status}</td>
												<td style="white-space: nowrap;">
												<c:if test="${mccs.status == 'Active'}">
												<c:if test="${fn:contains(existingFeatures,merchantCategoryCodeView)}">
													<a href="javascript:viewMCCInfo('${mccs.id }')"><img src="../images/eyeimage.png" alt="View" title="View"></img> 
													<span class="glyphicon glyphicon-eye"></span></a>
												</c:if>
													<a href="javascript:changeStatus('${mccs.id}','Suspended','Suspended','merchantCategoryCodePopupDiv')" title="Suspend">
													<img src="../images/active.png" alt="Suspend" title="Suspend"></img></a>
													<c:if test="${fn:contains(existingFeatures,merchantCategoryCodeEdit)}">
														<a href="javascript:editMCC('${mccs.id }')" title="Edit" class="table-actionicon-margin">
														<span class="glyphicon glyphicon-pencil"></span></a>
													</c:if>	
													<c:if test="${fn:contains(existingFeatures,merchantCategoryCodeDelete)}">
														<a href="javascript:confirmDeleteMCC('${mccs.id }')">
													 		<span class="glyphicon glyphicon-trash" title="Delete"></span></a>
													</c:if>
												</c:if> 
												<c:if test="${mccs.status == 'Suspended'}">
													<a href="javascript:changeStatus('${mccs.id}','Active','Active','merchantCategoryCodePopupDiv')" title="Active">
												    <img alt="Active" src="../images/deactive.png" title="Activate"></img></a>
											   </c:if>
												<c:if test="${mccs.status == 'Deleted'}"></c:if>
											  </td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="10" style="color: red;"><spring:message code="merchant.label.notfound"/></td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
						<table class="table table-striped table-bordered table-condensed">
							<c:if test="${ !(fn:length(mccs) eq 0)}">
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
							</c:if>
							<!-- Search Table Content End -->
						</table>


					</div>
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
	 <div id="merchantCategoryCodePopupDiv" class="locatioin-list-popup">
		<span class="glyphicon glyphicon-remove"
			onclick="closePopup();clearPopupDesc();"></span>
		<h2><spring:message code="prepaid-admin-programmanager-search-label.ChangeStatus"/></h2>
		<form:form action="merchantCategoryCodeActivationSuspention" name="merchantCategoryCodeActivationSuspentionForm" method="post">
			<input type="hidden" id="suspendActiveId" name="merchantCategoryCodeId" /> <input
				type="hidden" id="suspendActiveStatus" name="merchantCategoryCodeStatus" /> 
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
	<script src="../js/sortable.js"></script>
	<script src="../js/messages.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/merchant-category-code.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>

	<script>
		var sortProperty = "${sortProperty}" + "";

		if ($.trim(sortProperty).length > 0) {
			var sortPropArr = sortProperty.split(",");
			$('#serviceResults').tablesorter(
					{
						sortList : [ [ parseInt(sortPropArr[0]),
								parseInt(sortPropArr[1]) ] ]
					});
		}

		$(document).ready(function() {
			$('#merchantCategoryCodePopupDiv').popup({
				blur : false
			});
			$("#navListId2").addClass("active-background");

		});
		
		function closePopup() {
			$('#merchantCategoryCodePopupDiv').popup("hide");
			$('#deletePopup').popup("hide");
		}
		function openPopup() {
			$('#merchantCategoryCodePopupDiv').popup("show");
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
