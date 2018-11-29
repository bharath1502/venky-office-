<!DOCTYPE html>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
						<span class="breadcrumb-text"><spring:message
								code="setup.label.setup" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text" id="userType"><spring:message
								code="users.label.user" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message
								code="common.label.search" /></span>
					</div>

					<form:form action="userPaginationAction" name="paginationForm"
						id="paginationForm" method="post">
						<input type="hidden" id="pageNumberId" name="pageNumber" /> <input
							type="hidden" id="totalRecordsId" name="totalRecords" /> <input
							type="hidden" id="paginationRequestType" name="requestType" />
							<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>

					<form:form action="access-user-view" name="userViewForm" method="post">
						<input type="hidden" id="userViewIdData" name="userIdData" /> <input
							type="hidden" id="usersViewGroupType" name="usersGroupType" /> <input
							type="hidden" id="viewRequestType" name="requestType" />
							<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>

					<form:form action="access-user-edit" name="userEditForm" method="post">
						<input type="hidden" id="userIdData" name="userIdData" /> <input
							type="hidden" id="usersGroupType" name="usersGroupType" /> <input
							type="hidden" id="editRequestType" name="requestType" />
							<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>

					<form:form action="downloadUserport" name="downloadReport"
						id="downloadReport" method="post">
						<input type="hidden" id="downloadPageNumberId"
							name="downLoadPageNumber" /> <input type="hidden"
							id="downloadTypeId" name="downloadType" /> <input type="hidden"
							id="downloadRequestType" name="requestType" /> <input
							type="hidden" id="totalRecords" name="totalRecords" /> <input
							type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
							<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>

					<form:form action="deleteMerchantUser" name="deleteMercahntUserForm"
						method="post">
						<input type="hidden" id="getMerchantUserId"
							name="getMerchantUserId" /> <input type="hidden"
							id="usersGroupTypes" name="usersGroupTypes" /> <input
							type="hidden" id="deleteRequestType" name="requestType" />
							<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>

					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<c:if
						test="${fn:contains(existingFeatures,usersDelete)||fn:contains(existingFeatures,usersEdit)||fn:contains(existingFeatures,usersCreate)||fn:contains(existingFeatures,usersView)}">
						<div class="tab-header-container-first active-background">
							<a href="#"><spring:message code="common.label.search" /></a>
						</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,usersCreate)}">
						<div class="tab-header-container">
							<a href="access-user-create"><spring:message
									code="common.label.create" /></a>
						</div>
					</c:if>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="descriptionMsg" data-toggle="tooltip"
										data-placement="top" title="">
										<span class="red-error">${error}</span> 
										<span id="sucessDiv" class="green-error">${sucess}</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="access-user-search"
									modelAttribute="userDataDto" method="post">
									<input type="hidden" name="CSRFToken" value="${tokenval}">
									<form:hidden path="requestType" id="requestType" />
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="users.label.usertype" /></label>
													<form:select path="userType" id="roleType"
														cssClass="form-control" onchange="populatemerchantcode(this.value)">
														<c:forEach items="${roleLevelList}" var="roleLevelList">
															         <form:option value="${roleLevelList.value}">${roleLevelList.value}</form:option>
														        </c:forEach>

													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span class="red-error" id="roleTypeError">&nbsp;</span>
													</div>
												</fieldset>

												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="users.label.firstname" /></label>

													<form:input path="firstName" id="firstName"
														cssClass="form-control" onblur="clientValidation('firstName','middle_name','firstnameerror')" />

													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="firstnameerror" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="users.label.lastname" /></label>
													<form:input path="lastName" id="lastName"
														cssClass="form-control" onblur="clientValidation('lastName','middle_name','lastnameerror')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="lastnameerror" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="common.label.emailid" /></label>
													<form:input path="email" id="emailId"
														cssClass="form-control" onblur="clientValidation('emailId','email_search','emailerror')" />
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="emailerror" class="red-error">&nbsp;</span>
													</div>
												</fieldset>

												<%-- <fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title="">User Name</label>
													<form:input path="userName" id="userName"
														cssClass="form-control" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset> --%>


												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="users.label.phonenumber" /></label>
													<form:input path="phone" id="phone" cssClass="form-control" onblur="clientValidation('phone','contact_phone_search','phonenumerror')"
														onkeypress="return numbersonly(this,event)" maxlength="10" />
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="phonenumerror" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="common.label.status" /></label>
													<form:select cssClass="form-control" path="status"
														id="status">
														<form:option value="">
															<spring:message code="reports.option.select" />
														</form:option>
														<form:option value="0">
															<spring:message code="common.label.active" />
														</form:option>
														<form:option value="1">
															<spring:message code="common.label.pending" />
														</form:option>
														<form:option value="2">
															<spring:message code="common.label.inactive" />
														</form:option>
														<form:option value="4">
															<spring:message code="reports.option.declined" />
														</form:option>
													</form:select>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="common.label.recordsperpage" /></label>
													<form:select cssClass="form-control" path="pageSize"
														id="pageSize">
														<form:option value="10">10</form:option>
														<form:option value="20">20</form:option>
														<form:option value="50">50</form:option>
														<form:option value="100">100</form:option>
														<form:option value="250">250</form:option>
														<form:option value="500">500</form:option>
														<form:option value="1000">1000</form:option>
													</form:select>
												</fieldset>
												<c:if test="${userData.userType == 'Merchant'}">
												<fieldset class="col-sm-3" id="merchantDivId">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="accounts-manual-credit.label.merchantorsubmerchantcode" /></label>
													<form:input path="merchantCode" id="merchantCode"
														cssClass="form-control" />
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
                                               </c:if>	
											</div>
										</div>
										<!--Panel Action Button Start -->
										<div
											class="col-sm-12 form-action-buttons pull-right zero-padding">
											<div class="col-sm-6 zero-padding"></div>
											<div class="col-sm-6 zero-padding">
												<input type="submit"
													value='<spring:message code="common.label.search"/>'
													class="form-control button pull-right"
													onclick="return searchUserDetails(); trimUserData()"> <input
													type="button" class="form-control button pull-right"
													value='<spring:message code="common.label.reset"/>'
													onclick="resetSearchUser()">
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
									<td colspan="8" class="search-table-header-column"><span
										class="glyphicon glyphicon-search search-table-icon-text"></span>
										<span><spring:message code="common.label.search" /></span> <span
										class="pull-right"><spring:message
												code="common.label.totalcount" /> : <label id="totalCount">${totalRecords}</label></span>
									</td>
								</tr>
							</table>
							<!-- Search Table Header End -->
							<!-- Search Table Content Start -->
							<table id="serviceResults"
								class="table table-striped table-bordered table-responsive table-condensed tablesorter">
								<thead>
									<tr>
										<th style="width: 100px;"><spring:message
												code="users.label.usertype" /></th>
										<th style="width: 120px;"><spring:message
												code="roles.label.rolename" /></th>
										<th style="width: 133px;"><spring:message
												code="merchant.label.username" /></th>
										<th style="width: 96px;"><spring:message
												code="users.label.firstname" /></th>
										<th style="width: 95px;"><spring:message
												code="users.label.lastname" /></th>
										<th style="width: 225px;"><spring:message
												code="common.label.emailid" /></th>
										<th style="width: 75px;"><spring:message
												code="common.label.status" /></th>
										<th
											class="sorter-false tablesorter-header tablesorter-headerUnSorted"><spring:message
												code="common.label.action" /></th>
									</tr>
								</thead>
								<c:choose>
									<c:when test="${!(fn:length(userList) eq 0) }">
										<c:forEach items="${userList}" var="userData">
											<tr>
												<td class="tbl-text-align-left">${userData.userType}</td>
												<td class="tbl-text-align-left">${userData.userRoleName}</td>
												<td style="text-align: left;"><!-- <div class="feeDescDiv"> -->${userData.userName}<!-- </div> --></td>
												<td style="text-align: left;"><div class="feeDescDiv">${userData.firstName}</div></td>
												<td style="text-align: left;"><div class="feeDescDiv">${userData.lastName}</div></td>
												<td style="text-align: left;"><div><!-- class="feeDescDiv" -->${userData.email}</div></td>
												<td class="tbl-text-align-left">
													<c:if test="${userData.status == '1'}">
														<span><spring:message code="common.label.pending" /></span>
													</c:if> <c:if test="${userData.status == '0'}">
														<span><spring:message code="home.label.active" /></span>
													</c:if> <c:if test="${userData.status == '2'}">
														<span><spring:message code="common.label.suspended" /></span>
													</c:if> <c:if test="${userData.status == '3'}">
														<span><spring:message code="manage.option.sub-merchant.deleted" /></span>
													</c:if> <c:if test="${userData.status == '4'}">
														<span><spring:message code="reports.option.declined" /></span>
													</c:if></td>
												<td>
												<c:if test="${userData.status !='3'}">
													<c:if test="${fn:contains(existingFeatures,usersView)}">
														<a href="javascript:viewUser('${userData.adminUserId}','${userData.userType}')">
															<img src="../images/eyeimage.png" alt="<spring:message code="common.label.view"/>" title="<spring:message code="common.label.view"/>"></img>
															<span class="glyphicon glyphicon-eye"></span></a>
													</c:if>		
												</c:if>
												<c:if test="${userData.status == '1'}">
													 <a href="javascript:changeStatus('${userData.adminUserId}','Active','Active','userPopupDiv')" title="Active">
													 <img alt="Active" src="../images/deactive.png" title="Activate"></img></a>
												</c:if>
												<c:if test="${userData.status == '2'}">
												     <a href="javascript:changeStatus('${userData.adminUserId}','Active','Active','userPopupDiv')" title="Active">
													 <img alt="Active" src="../images/deactive.png" title="Activate"></img></a>			
												</c:if>
												<c:if test="${userData.status == '0'}">
													<c:if test="${fn:contains(existingFeatures,usersEdit)}">
													 	<a href="javascript:editUser('${userData.adminUserId}','${userData.userType}')">
														<span class="glyphicon glyphicon-pencil" title="Edit"></span></a>
													 </c:if>
													 <a href="javascript:changeStatus('${userData.adminUserId}','Suspended','Suspended','userPopupDiv')" title="Suspend">
													 <img src="../images/active.png" alt="Suspend" title="Suspend"></img></a>
													 <c:if test="${fn:contains(existingFeatures,usersDelete)}">
													 	<a href="javascript:confirmDeleteMerchantUser('${userData.adminUserId }','${userData.userType}')">
													 	<span class="glyphicon glyphicon-trash" title="Delete"></span></a>
													 </c:if>
												</c:if>
												<c:if test="${userData.status == '3'}"></c:if>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="8" style="color: red;"><spring:message
													code="access-user-search.label.usernotfound" /></td>
										</tr>
									</c:otherwise>
								</c:choose>
							</table>
							<table class="table table-striped table-bordered table-condensed">
								<tr class="table-footer-main">
									<td colspan="10" class="search-table-header-column">
										<div class="col-sm-12">

											<c:if test="${!(fn:length(userList) eq 0) }">


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
															</a> <a> <input type="checkbox" class="autoCheck check"
																id="totalRecordsDownload"> <spring:message
																	code="common.label.downloadall" />
															</a>
														</div>
													</div>
												</div>

												<div class="col-sm-9">
													<ul class="pagination custom-table-footer-pagination">
														<c:if test="${portalListPageNumber gt 1}">
															<li onclick="bindReqType();"><a
																href="javascript:getPortalOnPageWithRecords('1','${totalRecords}')">
																	&laquo;</a></li>
															<li onclick="bindReqType();"><a
																href="javascript:getPortalPrevPageWithRecords('${portalListPageNumber }','${totalRecords}')">
																	&lsaquo; </a></li>
														</c:if>

														<c:forEach var="page" begin="${beginPortalPage }"
															end="${endPortalPage}" step="1" varStatus="pagePoint">
															<c:if test="${portalListPageNumber == pagePoint.index}">
																<li
																	class="${portalListPageNumber == pagePoint.index?'active':''}"
																	onclick="bindReqType();"><a href="javascript:">${pagePoint.index}</a>
																</li>
															</c:if>
															<c:if test="${portalListPageNumber ne pagePoint.index}">
																<li class="" onclick="bindReqType();"><a
																	href="javascript:getPortalOnPageWithRecords('${pagePoint.index }','${totalRecords}')">${pagePoint.index}</a>
																</li>
															</c:if>
														</c:forEach>

														<c:if test="${portalListPageNumber lt portalPages}">
															<li onclick="bindReqType();"><a
																href="javascript:getPortalNextPageWithRecords('${portalListPageNumber }','${totalRecords}')">
																	&rsaquo;</a></li>
															<li onclick="bindReqType();"><a
																href="javascript:getPortalOnPageWithRecords('${portalPages }','${totalRecords}')">&raquo;
															</a></li>
														</c:if>
													</ul>
												</div>
											</c:if>

										</div>
									</td>
								</tr>
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
	
	<div id="userPopupDiv" class="locatioin-list-popup">
		<span class="glyphicon glyphicon-remove"
			onclick="closePopup();clearPopupDesc();"></span>
		<h2><spring:message code="prepaid-admin-programmanager-search-label.ChangeStatus"/></h2>
		<form:form action="userActivationSuspention" name="userActivationSuspentionForm" method="post">
			<input type="hidden" id="suspendActiveId" name="userId" /> 
			<input type="hidden" id="suspendActiveStatus" name="userStatus" /> 
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
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/utils.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/validation.js" type="text/javascript"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/messages.js" type="text/javascript"></script>
	<script src="../js/sortable.js"></script>
	<script src="../js/admin-user.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		function resetSearchUser() {
			window.location.href = "access-user-search?requestType="
					+ $('#requestType').val();
		}

		function updateHref($this) {
			var href = $($this).attr('href');
			$($this).attr('href',
					href + "?requestType=" + $('#requestType').val());
		}

		$(document).ready(function() {
			$('#userPopupDiv').popup({
				blur : false
			});
			$("#navListId2").addClass("active-background");
			if($('#roleType').val() == 'Admin'){
				$('#merchantDivId').hide();
			}
			if($('#roleType').val() == 'Tms'){
				$('#merchantDivId').hide();
			}
			if($('#roleType').val() == 'Reseller'){
				$('#merchantDivId').hide();
			}
		});
		
		function closePopup() {
			$('#userPopupDiv').popup("hide");
			$('#deletePopup').popup("hide");
		}
		function openPopup() {
			$('#userPopupDiv').popup("show");
		}

		function bindReqType() {
			$('#paginationRequestType').val($('#requestType').val());
		}
		function populatemerchantcode(usertype) {
			if (usertype == 'Merchant') {
				$('#merchantDivId').show();
			} else {
				$('#merchantDivId').hide();
			}
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
