<!DOCTYPE html>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.Calendar"%>
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
<title><spring:message code="common.lable.title" /></title>
<!-- Bootstrap -->
<link rel="icon" href="../images/favicon.png" type="image/png">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">

			<!--Navigation Block Start -->
			<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message
								code="manage.label.manage" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><a href="showProgramManager"><spring:message
									code="admin.pm.message" /></a></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message
								code="common.label.search" /></span>
					</div>
					<!-- Breadcrumb End -->
					<form:form action="showEditProgramManager" name="editProgramManagerForm"
						method="post">
						<input type="hidden" id="programManagerId" name="programManagerId" />
					</form:form>
					<form:form action="programManagerPaginationAction" name="paginationForm"
						method="post">
						<input type="hidden" id="pageNumberId"
							name="programManagerPageData" /> <input type="hidden"
							id="totalRecordsId" name="totalRecords" />
					</form:form>

					<form:form action="downloadProgramManagerReport" name="downloadReport"
						method="post">
						<input type="hidden" id="downloadPageNumberId"
							name="downLoadPageNumber" /> <input type="hidden"
							id="downloadTypeId" name="downloadType" /> <input type="hidden"
							id="totalRecords" name="totalRecords" /> <input type="hidden"
							id="downloadAllRecords" name="downloadAllRecords" />
					</form:form>
					<!-- Tab Buttons Start -->
					<div class="marginL40">
						<c:if
							test="${fn:contains(existingFeatures,programmanagerView)||fn:contains(existingFeatures,programmanagerEdit)||fn:contains(existingFeatures,programmanagerSuspend)||fn:contains(existingFeatures,programmanagerActivate)}">
							<div class="tab-header-container active-background">
								<a href="#"><spring:message code="common.label.search" /> </a>
							</div>
						</c:if>
						<c:if test="${fn:contains(existingFeatures,programmanagerCreate)}">
							<div class="tab-header-container">
								<a href="showCreateProgramManager"><spring:message
										code="common.label.create" /></a>
							</div>
						</c:if>
					</div>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<span class="green-error" id="sucessDiv">${sucess}</span> <span
									class="red-error" id="errorDiv">${error}</span>

								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<c:set var="title">
									<spring:message code="admin.label.wildcard" />
								</c:set>
								<form:form action="searchProgramManager"
									modelAttribute="programManagerRequest" method="post">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="admin.pm.Name.message" /></label>
													<form:input path="programManagerName" maxlength="50"
														cssClass="form-control" />

													<div class="discriptionErrorMsg">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="home.label.companyname" /></label>
													<form:input path="companyName" maxlength="50"
														cssClass="form-control" />
													<div class="discriptionErrorMsg">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="bank.label.bankname" /></label>
													<form:select id="bankName" path="bankNames" cssClass="form-control">
														<form:option value="">
															<spring:message code="fee-program-create.label.select" />
														</form:option>
														<c:if test="${not empty bankList}">
															<c:forEach items="${bankList}" var="bank">
																<form:option value="${bank.label}">${bank.label}</form:option>
															</c:forEach>
														</c:if>
													</form:select>
													<div class="discriptionErrorMsg">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="fee-program-search.label.status" /></label>
													<form:select cssClass="form-control" id="STATUS"
														path="status">
														<form:option value="">
															<spring:message code="common.label.all" />
														</form:option>
														<c:forEach items="${pmstatusList}" var="pmstatus">
															<form:option value="${pmstatus}">
																${pmstatus}
															</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="common.label.recordsperpage" /></label>
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
												<input type="submit" class="form-control button pull-right"
													value="<spring:message code="common.label.search"/>">
												<a href="showProgramManager"
													class="form-control button pull-right"><spring:message
														code="common.label.reset" /></a>
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
					<c:set var="flageCheck" scope="session" value="yes" />
					<c:if test="${searchList ne flageCheck }">
						<div class="search-results-table">
							<table
								class="table table-striped table-bordered table-condensed marginBM1">
								<!-- Search Table Header Start -->
								<tr>
									<td class="search-table-header-column widthP80"><span
										class="glyphicon glyphicon-search search-table-icon-text"></span>
										<span><spring:message code="common.label.search" /></span>
									<td class="search-table-header-column"
										style="font-weight: bold;"><spring:message
											code="common.label.totalcount" /> : ${totalRecords}</td>
								</tr>
							</table>
							<!-- Search Table Header End -->
							<!-- Search Table Content Start -->
							<table id="serviceResults"
								class="table table-striped table-bordered table-responsive table-condensed tablesorter marginBM1 common-table">
								<thead>
									<tr>
										<th><spring:message code="admin.pm.Name.message" /></th>
										<th><spring:message code="home.label.companyname" /></th>
										<th><spring:message
												code="admin.BusinessEntityName.message" /></th>
										<th style="width: 15%;"><spring:message
												code="home.label.status" /></th>
										<th
											class="sorter-false tablesorter-header tablesorter-headerUnSorted"
											style="width: 10%;"><spring:message
												code="common.label.action" /></th>
									</tr>
								</thead>
								<c:choose>
									<c:when test="${!(fn:length(searchList) eq 0) }">
										<c:forEach items="${searchList}" var="programManagerDetails">
											<tr>
												<td data-title="Program Manager Name" class="ellipsis"
													id="15" title="${programManagerDetails.programManagerName}">${programManagerDetails.programManagerName}&nbsp;</td>
												<td data-title="Company Name" class="ellipsis" id="15"
													title="${programManagerDetails.companyName}">${programManagerDetails.companyName}&nbsp;</td>
												<td data-title="Business Entity Name" class="ellipsis"
													id="15" title="${programManagerDetails.businessName}">${programManagerDetails.businessName}&nbsp;</td>
												<td data-title="Status">
													${programManagerDetails.status}&nbsp;</td>
												<td data-title="Action"><c:choose>
														<c:when
															test="${fn:containsIgnoreCase(programManagerDetails.status,'Active') }">
															<c:if
																test="${fn:contains(existingFeatures,programmanagerEdit)}">
																<a
																	href="javascript:editProgramManager('${programManagerDetails.id}')"
																	title=Edit><span class="glyphicon glyphicon-pencil"></span></a>
															</c:if>
															<c:if
																test="${fn:contains(existingFeatures,programmanagerSuspend)}">
																<a
																	href="javascript:changeProgramManagerStatus('${programManagerDetails.id}','Suspended','Suspended')"
																	title="Suspend"> <img src="../images/active.png"
																	alt="<spring:message code='common.label.suspend'/>"
																	title='<spring:message code='common.label.suspend'/>'></img></a>
															</c:if>
														</c:when>
														<c:otherwise>
															<c:if
																test="${fn:containsIgnoreCase(programManagerDetails.status,'Suspended') && fn:contains(existingFeatures,programmanagerActivate)}">
																<a
																	href="javascript:changeProgramManagerStatus('${programManagerDetails.id}','Active','Active')"
																	title="Active"> <img alt="Active"
																	src="../images/deactive.png"
																	title='<spring:message code='common.label.active'/>'></img>
																</a>
															</c:if>
														</c:otherwise>
													</c:choose></td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="8" style="color: red;"><spring:message code="no.pms.founs" /></td>
										</tr>
									</c:otherwise>
								</c:choose>
							</table>
							<!-- Search Table Content End -->
							<table class="table table-striped table-bordered table-condensed">
								<c:if test="${!(fn:length(searchList) eq 0) }">
									<tr class="table-footer-main">
										<td colspan="10" class="search-table-header-column">
											<div class="col-sm-12">
												<div class="col-md-3 col-sm-6">
													<div class="btn-toolbar" role="toolbar">
														<div class="btn-group custom-table-footer-button">
															<a
																href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.XLS_FILE_FORMAT%>','${totalRecords}')">
																<button type="button" class="btn btn-default">
																	<img src="../images/excel.png">
																</button>
															</a> <a
																href="javascript:downloadReport('${portalListPageNumber}','<%=Constants.PDF_FILE_FORMAT%>','${totalRecords}')">
																<button type="button" class="btn btn-default">
																	<img src="../images/pdf.png" />
																</button>
															</a>
														</div>
														<div>
															<input type="checkbox" class="autoCheck check"
																id="totalRecordsDownload">
															<spring:message code="common.label.downloadall" />
														</div>
													</div>

												</div>
												<div class="col-md-9 col-sm-6">
													<c:if test="${ !(fn:length(searchList) eq 0)}">
														<ul class="pagination custom-table-footer-pagination">
															<c:if test="${portalListPageNumber gt 1}">
																<li><a
																	href="javascript:getPortalOnPageWithRecords('1','${totalRecords}')">
																		&laquo;</a></li>
																<li><a
																	href="javascript:getPortalPrevPageWithRecords('${portalListPageNumber }', '${totalRecords}')">
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
																	href="javascript:getPortalNextPageWithRecords('${portalListPageNumber }', '${totalRecords}')">
																		&rsaquo;</a></li>
																<li><a
																	href="javascript:getPortalOnPageWithRecords('${portalPages }', '${totalRecords}')">&raquo;
																</a></li>
															</c:if>
														</ul>
													</c:if>
												</div>
											</div>
										</td>

									</tr>
								</c:if>
								<!-- Search Table Content End -->
							</table>
							<!--Panel Action Button Start -->
							<div class="col-sm-12 form-action-buttons">
								<fieldset class="col-sm-3 pull-right">
									<input type="button"
										class="form-control button pull-right table-hide-btn"
										value="Back">
								</fieldset>
							</div>
							<!--Panel Action Button End -->
						</div>
					</c:if>
					<!-- Search Table Block End -->
				</div>
			</article>
			<!--Article Block End-->
			<jsp:include page="footer.jsp" />
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->

	<!-- Pop Up box information starts here -->
	<div id="programManagerDiv" class="locatioin-list-popup">
		<span class="glyphicon glyphicon-remove" onclick="closePopup()"></span>
		<h2>
			<spring:message
				code="prepaid-admin-programmanager-search-label.ChangeStatus" />
		</h2>
		<form:form action="changeProgramManagerStatus" name="spActivationForm"
			method="post">
			<input type="hidden" id="manageProgramManagerId"
				name="manageProgramManagerId" /> <input type="hidden"
				id="manageProgramManagerStatus" name="manageProgramManagerStatus" />
			<!-- <input type="hidden" id="partnerName" name="partnerName" />   -->
			<!--  <input type="hidden" id="totalRecordsId" name="totalRecords" /> -->
			<label><span class="requiredFiled">*</span> <spring:message code="prepaid-admin-label.Reason"/></label>
			<textarea id="reason" name="reason" maxlength="250"
				onblur="validatePopupDesc();clientValidation('reason', 'reason','popDescError_div')"></textarea>
			<div class="discriptionErrorMsg">
				<span class="red-error" id="popDescError_div">&nbsp;</span>
			</div>
			<!--Panel Action Button Start -->
			<div class="col-sm-12 form-action-buttons">
				<div class="col-sm-12">
					<input type="submit" class="form-control button pull-right"
						value="Submit" onclick="return validatePopupDesc();">
				</div>
			</div>
		</form:form>
		<!--Panel Action Button End -->
		<!-- <p>
			<b>Note</b>
		</p> -->
		<br>
	</div>
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/utils.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/sorting.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/tablesorter.js"></script>
	<script src="../js/tablesorter.widgets.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/program-manager.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		/* Select li full area function Start */
		$("li").click(function() {
			window.location = $(this).find("a").attr("href");
			return false;
		});
		/* Select li full area function End */
		/* Common Navigation Include Start */
		$(function() {
			$("#main-navigation").load("main-navigation.html");
		});
		function highlightMainContent() {
			$("#navListId2").addClass("active-background");
		}
		/* Common Navigation Include End */

		$(document).ready(function() {
			$('#programManagerDiv').popup({
				blur : false
			});
			$('input:visible:enabled:first').focus();
		});
		function closePopup() {
			$('#programManagerDiv').popup("hide");
		}
		function openPopup() {
			$('#programManagerDiv').popup("show");
		}

		$(".table-hide-btn").click(function() {
			$(".search-results-table").slideUp();
		});
	</script>
</body>
</html>