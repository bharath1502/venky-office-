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
			<!-- 			<header class="col-sm-12 all-page-header"> -->
			<!-- 				Header Logo Start				 -->
			<!-- 				<div class="col-sm-4">  -->
			<!-- 					<img src="../images/chatak_logo.jpg" height="35px" alt="Logo"/> -->
			<!-- 				</div> -->
			<!-- 				Header Logo End	 -->
			<!-- 				Header Welcome Text and Logout button Start -->
			<!-- 				<div class="col-sm-5 col-xs-offset-3"> -->
			<!-- 					<div class="pull-right user-settings"> -->
			<!-- 						<table> -->
			<!-- 							<tr> -->
			<!-- 								<td><a href="#"><span class="glyphicon glyphicon-log-out"></span> Logout</a></td> -->
			<!-- 							</tr> -->
			<!-- 						</table> -->
			<!-- 					</div> -->
			<!-- 				</div> -->
			<!-- 				Header Welcome Text and Logout button End	 -->
			<!-- 			</header> -->
			<!--Header Block End -->
			<!--Navigation Block Start -->
			<%@include file="navigation-panel.jsp"%>
			<%-- <jsp:include page="header.jsp"></jsp:include> --%>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="setup.label.setup"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="roles.label.role"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="common.label.search"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<c:if test="${fn:contains(existingFeatures,rolesEdit)||fn:contains(existingFeatures,rolesDelete)||fn:contains(existingFeatures,rolesCreate)||fn:contains(existingFeatures,rolesView)}">
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="common.label.search"/></a>
					</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,rolesCreate)}">
					<div class="tab-header-container">
						<a href="access-role-create"><spring:message code="common.label.create"/></a>
					</div>
					</c:if>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="descriptionMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error">&nbsp;</span> 
										<span id="sucessDiv" class="green-error">${sucess}</span>
									</div>
								</div>

								<form:form action="role-pagination-action" name="paginationForm"
									method="post">
									<input type="hidden" id="pageNumberId" name="pageNumber" /> <input
										type="hidden" id="totalRecordsId" name="totalRecords" />
										<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								<form:form action="access-role-view" name="viewRoleForm" method="post">
									<input type="hidden" id="roleIdViewData" name="roleIdData" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>


								<form:form action="editRole" name="editRoleForm" method="post">
									<input type="hidden" id="roleIdData" name="roleIdData" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
									<!-- <input type="hidden" id="roleNameData" name="roleNameData" /> 
									<input type="hidden" id="statusData" name="statusData" /> -->

								</form:form>

								<form:form action="downloadRoleport" name="downloadReport"
									method="post">
									<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" /> 
									<input type="hidden" id="downloadTypeId" name="downloadType" />
									<input type="hidden" id="totalRecords" name="totalRecords" />
									<input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>


								<form:form action="deleteRole" name="deleteRoleForm" method="post">
									<input type="hidden" id="roleIdDeleteData"
										name="roleIdDeleteData" />
										<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>


								<form:form action="viewRole" name="viewRoleForm" method="post">
									<input type="hidden" id="roleIdViewData" name="roleIdViewData" />
									<input type="hidden" id="roleNameViewData"
										name="roleNameViewData" />
										<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

								<form:form name="viewUpdateRoleForm" action="viewUpdateRole"
									method="post">
									<input type="hidden" id="viewRolId" name="viewRoleId" /> <input
										type="hidden" id="viewRoleStatus" name="roleStatus" />
										<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>


								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="access-role-search"
									modelAttribute="userRoleDTO" method="post">
									<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
											<fieldset class="col-sm-3"> 
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="roles.label.roletype"/></label>
															<form:select cssClass="form-control"  path="roleType" id="roleType" >
															<c:forEach items="${roleLevelList}" var="roleLevel">
															         <form:option value="${roleLevel.value}">${roleLevel.value}</form:option>
														        </c:forEach>
															</form:select>
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span class="red-error" id="roleTypeError">&nbsp;</span>
															</div>
														</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="roles.label.rolename"/></label>
													<form:input path="roleName" id="roleName" cssClass="form-control" onblur="clientValidation('roleName','firstlast_name_notmend','roleNameError')"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="roleNameError" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.status"/></label>
													<form:select path="status" cssClass="form-control">
														<form:option value=""><spring:message code="reports.option.select"/></form:option>
														<form:option value="0"><spring:message code="common.label.active"/></form:option>
														<%-- <form:option value="1"><spring:message code="common.label.deleted"/></form:option> --%>
														<form:option value="2"><spring:message code="common.label.inactive"/></form:option>
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
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit" value='<spring:message code="common.label.search"/>' class="form-control button pull-right"
													onclick="return searchRoleName(); trimUserData()"> <input type="button"
													class="form-control button pull-right" value='<spring:message code="common.label.reset"/>'
													onclick="resetSearchRole()">
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
						<table class="table table-striped table-bordered table-condensed" style="margin: 1px;">
							<!-- Search Table Header Start -->
							<tr>
								<td colspan="7" class="search-table-header-column"><span
									class="glyphicon glyphicon-search search-table-icon-text"></span>
									<span><spring:message code="common.label.search"/></span>
									<span class="pull-right"><spring:message code="common.label.totalcount"/> : <label id="totalCount">${totalRecords}</label></span>
									</td>
							</tr>
							<!-- Search Table Header End -->
							<!-- Search Table Content Start -->
							</table>
							<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
							<tr>
							<th style="width: 250px;"><spring:message code="roles.label.roletype"/></th>
								<th style="width: 300px;"><spring:message code="roles.label.rolename"/></th>
								<th style="width: 192px;"><spring:message code="common.label.status"/></th>
								<th class="sorter-false tablesorter-header tablesorter-headerUnSorted"><spring:message code="common.label.action"/></th>
							</tr>
							</thead>


							<c:choose>
								<c:when test="${!(fn:length(roleList) eq 0) }">
									<c:forEach items="${roleList}" var="roleData">
										<tr>
										<td class="tbl-text-align-left">${roleData.roleType}</td>
											<td class="tbl-text-align-left">${roleData.roleName}</td>

											<td class="tbl-text-align-left"><c:choose>
													<c:when test="${roleData.status eq '0' }"> Active </c:when>
													<c:otherwise>
														<c:if test="${roleData.status eq '2' }">Suspended</c:if>
													</c:otherwise>
												</c:choose></td>
												<td data-title="Action">
															<c:if test="${roleData.status eq '0'}">
															<c:if test="${fn:contains(existingFeatures,rolesView)}">
															<a href="javascript:viewRole('${roleData.roleId}')">
																	<img src="../images/eyeimage.png" alt="<spring:message code="common.label.view"/>" 
																	title="<spring:message code="common.label.view"/>"></img>
																<span class="glyphicon glyphicon-eye"></span></a>
															</c:if>	
															<a href="javascript:changeStatus('${roleData.roleId}','Suspended','Suspended','rolePopupDiv')"
																	title="<spring:message code="common.label.suspend"/>"> <img src="../images/active.png"
																	alt="<spring:message code="common.label.suspend"/>" title="<spring:message code="common.label.suspend"/>"></img></a>
															<c:if test="${fn:contains(existingFeatures,rolesEdit)}">
															<a href="javascript:editRole('${roleData.roleId}')"
																	title="<spring:message code="common.label.edit"/>"><span
																	class="glyphicon glyphicon-pencil"></span></a>
															</c:if></c:if>
															<c:if test="${roleData.status eq '2'}">
																<a href="javascript:changeStatus('${roleData.roleId}','Active','Active','rolePopupDiv')"
																	title="<spring:message code="common.label.activate"/>"> <img alt="<spring:message code="common.label.activate"/>"
																	src="../images/deactive.png" title="<spring:message code="common.label.activate"/>"></img>
																</a>
															</c:if>
													<%-- <c:if
														test="${roleData.status eq '0' || roleData.status eq '2'}">

														<c:if test="${fn:contains(existingFeatures,rolesDelete)}">
															<c:choose>
																<c:when test="${roleData.isRoleCreatedUser == 'true'}">
																	<a href="#"
																		title="Role is already linked & hence not deletable"
																		style="opacity: 0.4;"> <span
																		class="glyphicon glyphicon-trash"></span>
																	</a>
																</c:when>
																<c:otherwise>
																	<a
																		href="javascript:confirmDeleteRole('${roleData.roleId}')"><span
																		class="glyphicon glyphicon-trash"></span></a>
																</c:otherwise>
															</c:choose>
														</c:if>
													</c:if> --%></td>
											</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="3" style="color: red;"><spring:message code="access-role-search.label.norolesfound"/></td>
									</tr>
								</c:otherwise>

							</c:choose>
							</table>
							<table class="table table-striped table-bordered table-condensed">

							<!-- 							<tr> -->
							<!-- 								<td>Paying Admin</td>																 -->
							<!-- 								<td>Paying Administrator</td>								 -->
							<!-- 								<td>Active</td> -->
							<!-- 								<td>									 -->
							<!-- 									<a href="access-role-edit.html"><span class="glyphicon glyphicon-pencil"></span></a>									 -->
							<!-- 								</td> -->
							<!-- 							</tr> -->
							<!-- 							<tr> -->
							<!-- 								<td>Super Admin</td>																 -->
							<!-- 								<td>Paying Administrator</td>								 -->
							<!-- 								<td>Active</td> -->
							<!-- 								<td>									 -->
							<!-- 									<a href="access-role-edit.html"><span class="glyphicon glyphicon-pencil"></span></a>									 -->
							<!-- 								</td> -->
							<!-- 							</tr> -->
							<tr class="table-footer-main">
								<td colspan="10" class="search-table-header-column">


									<div class="col-sm-12">
										<c:if test="${!(fn:length(roleList) eq 0) }">
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

													<c:forEach var="page" begin="${beginPortalPage}"
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

										</c:if>
									</div>
								</td>
							</tr>
							<!-- Search Table Content End -->
						</table>
					</div>
					</c:if>
					<!-- Search Table Block End -->
					<!-- Pop Up Box Information Start -->
					<div id="my_popup" class="locatioin-list-popup">
						<span class="glyphicon glyphicon-remove" onclick="closePopup()"></span>
						<h2><spring:message code="sub-merchant-account-search.label.reason"/></h2>
						<label data-toggle="tooltip" data-placement="top" title=""><span class="requiredFiled">*</span> <spring:message code="sub-merchant-account-search.label.reason"/> </label>
						<textarea></textarea>
						<!--Panel Action Button Start -->
						<div class="col-sm-12 form-action-buttons">
							<div class="col-sm-12">
								<input type="button" class="form-control button pull-right"
									value="Save">
							</div>
						</div>
						<!--Panel Action Button End -->
						<p><spring:message code="change-password.label.note"/> <spring:message code="access-role-search.label.onchangingthestatusallassociateduserswillbecomesamestatus"/></p>
					</div>
					<!-- Pop Up Box Information end -->
					
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
	
	<div id="rolePopupDiv" class="locatioin-list-popup">
		<span class="glyphicon glyphicon-remove"
			onclick="closePopup();clearPopupDesc();"></span>
		<h2><spring:message code="prepaid-admin-programmanager-search-label.ChangeStatus"/></h2>
		<form:form action="roleActivation" name="roleSuspendActiveForm" method="post">
			<input type="hidden" id="suspendActiveId" name="roleActivateId" /> 
			<input type="hidden" id="suspendActiveStatus" name="roleStatus" /> 
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
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
    <script src="../js/utils.js"></script>
	<script src="../js/messages.js" type="text/javascript"></script>
	
	<script src="../js/role.js" type="text/javascript"></script>
	<script src="../js/validation.js" type="text/javascript"></script>
	<script src="../js/sortable.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>		
		/* -- PopUp Show And Hide Functionality Start-- */
		$(document).ready(function() {
			$("#navListId2").addClass("active-background");
			$('#my_popup').popup({
				blur:false
			});
		});
		function closePopup(){
			$('#my_popup').popup("hide");
			$('#deletePopup').popup("hide");
		}
		function openPopup(){
			$('#my_popup').popup("show");
		}
		
		// active suspend popup
		$(document).ready(function() {
			$('#rolePopupDiv').popup({
				blur : false
			});
			 $('#roleName').focus();
		});
		function closePopup() {
			$('#rolePopupDiv').popup("hide");
		}
		function openPopup() {
			$('#rolePopupDiv').popup("show");
		}
		// active suspend popup end
		
		function editRole(roleId)
		{
			get('roleIdData').value = roleId;
			//get('roleNameData').value = roleName;
			//get('statusData').value = status;
			setValue("roleIdData",roleId);
			document.forms["editRoleForm"].submit();
		}
		
		function resetSearchRole() {
			window.location.href = "access-role-search";
		}
		
		function deleteRole(roleId)
		{
			get('roleIdDeleteData').value = roleId;
			document.forms["deleteRoleForm"].submit();

		}
		
		var rolesId;
		function confirmDeleteRole(roleId) {
			$('#deletePopup').popup("show");
			rolesId = roleId;
		}
		function deleteData() {
			get('roleIdDeleteData').value = rolesId;
			document.forms["deleteRoleForm"].submit();
			
		}
		
		function viewRole(roleId,roleName) {
			get('roleIdViewData').value = roleId;
			get('roleNameViewData').value = roleName;
			document.forms["viewRoleForm"].submit();

		}
		
		/* -- PopUp Show And Hide Functionality End-- */
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
	<!-- Include jQuery Popup Overlay Start -->
	<script type="text/javascript" src="../js/jquery.popupoverlay.js"></script>
	<!-- Include jQuery Popup Overlay End -->
</body>
</html>
