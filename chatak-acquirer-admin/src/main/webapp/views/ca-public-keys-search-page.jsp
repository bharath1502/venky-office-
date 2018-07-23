<!DOCTYPE html>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.Calendar"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css" />
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
							class="breadcrumb-text"><spring:message code="ca.public.label.keys"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="common.label.search"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<c:if test="${fn:contains(existingFeatures,caPublicKeysEdit)||fn:contains(existingFeatures,caPublicKeysCreate)||fn:contains(existingFeatures,caPublicKeysView)||fn:contains(existingFeatures,caPublicKeysDelete)}">
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="common.label.search"/></a>
					</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,caPublicKeysCreate)}">
					<div class="tab-header-container">
						<a href="ca-public-keys-create"><spring:message code="common.label.create"/></a>
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
								<form:form action="editCAPublicKeys" name="editCAPublicKeysForm"
									method="post">
									<input type="hidden" id="getCAPublicKeysId"
										name="getCAPublicKeysId" />
										<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

								<form:form action="ca-public-key-view" name="viewCAPublicKeysForm"
									method="post">
									<input type="hidden" id="getViewCAPublicKeysId"
										name="getCAPublicKeysId" />
										<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

								<%-- <form action="bank-view" name="viewBankForm" method="post">
									<input type="hidden" id="bankViewName" name="bankViewName" />
								</form> --%>

								<form:form action="ca-public-keys-search-page" name="caPublicKeysDTO"
									method="get">
									<input type="hidden" id="caPublicKeysDTO"
										name="caPublicKeysDTO" />
										<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>


								<form:form action="getCaPublicKeysInfo" name="paginationForm"
									method="post">
									<input type="hidden" id="pageNumberId" name="pageNumber" /> <input
										type="hidden" id="totalRecordsId" name="totalRecords" />
										<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

								<form:form action="deleteCAPublicKey" name="deleteCAPublicKey" method="post">
									<input type="hidden" id="getCAPublicKeyID" name="getCAPublicKeyID" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

								<!-- Page Form Start -->
								<form:form action="caPublicKeysSearch"
									commandName="caPublicKeysDTO" name="caPublicKeysDTO">
									<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="ca.public.label.name"/></label>
													<form:input cssClass="form-control" path="publicKeyName"
														id="publicKeyName" maxlength="50" />
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="ca.public.label.rid"/></label>
													<form:input path="rid" id="rID" cssClass="form-control"
														onblur="validateCAPublicKeys()" maxlength="10" />

													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="rIDEr">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="ca.public.label.modulus"/></label>
													<form:input path="publicKeyModulus" id="publicKeyModulus" maxlength="200"
														cssClass="form-control" onblur="validateCAPublicKeys()" />

													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="publicKeyModulusEr">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="ca.public.label.exponent"/></label>
													<form:input path="publicKeyExponent" id="publicKeyExponent" onkeypress="return numbersonly(this,event)"
														cssClass="form-control" onblur="validateCAPublicKeys()"
														maxlength="6" />

													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="publicKeyExponentEr">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="ca.public.label.expiredate"/></label>
													<div class="input-group focus-field">
														<form:input path="expiryDate" id="expiryDate"
															cssClass="form-control effectiveDate" />
														<span class="input-group-addon"><span
															class="glyphicon glyphicon-calendar"></span></span>
													</div>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="expiryDateErrorDiv">&nbsp;</span>
													</div>
												</fieldset>

												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="ca.public.label.index"/></label>
													<form:input path="publicKeyIndex" id="index" onkeypress="return numbersonly(this,event)"
														cssClass="form-control" onblur="validateCAPublicKeys()"
														maxlength="2" />

													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="indexEr">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.status"/></label>
													<form:select cssClass="form-control" path="status"
														id="status">
														<form:option value=""><spring:message code="reports.option.select"/></form:option>
														<form:option value="Active"><spring:message code="common.label.active"/></form:option>
														<form:option value="Suspended"><spring:message code="common.label.inactive"/></form:option>
														<%-- <form:option value="Deleted"><spring:message code="common.label.deleted"/></form:option> --%>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.recordsperpage"/></label>
													<form:select cssClass="form-control" path="pageSize"
														id="pageSize">
														<form:option value="10">10</form:option>
														<form:option value="20">20</form:option>
														<form:option value="50">50</form:option>
														<form:option value="100">100</form:option>
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
													onclick="resetCAPublicKeysSearch()">
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
									<th style="width: 150px;"><spring:message code="ca.public.label.name"/></th>
									<th style="width: 100px;"><spring:message code="ca.public.label.rid"/></th>
									<th style="width: 100px;"><spring:message code="ca.public.label.modulus"/></th>
									<th style="width: 100px;"><spring:message code="ca.public.label.exponent"/></th>
									<th style="width: 100px;"><spring:message code="ca.public.label.expiredate"/></th>
									<th style="width: 100px;"><spring:message code="ca.public.label.index"/></th>
									<th style="width: 100px;"><spring:message code="common.label.status"/></th>
									<th style="width: 100px;"
										class="sorter-false tablesorter-header tablesorter-headerUnSorted"><spring:message code="common.label.action"/></th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${!(fn:length(searchList) eq 0) }">
										<c:forEach items="${searchList}" var="searchList">
											<tr>
												<%-- <td class="tbl-text-align-left">${searchList.publicKeyName}</td> --%>
												<td style="text-align: center;"><div class="feeDescDiv">${searchList.publicKeyName}</div></td>
												<td class="tbl-text-align-right">${searchList.rid}</td>
												<td style="text-align: center;"><div class="feeDescDiv">${searchList.publicKeyModulus}</div></td>
												<%-- <td style="text-align: left;">${searchList.publicKeyModulus}</td> --%>
												<td class="tbl-text-align-right">${searchList.publicKeyExponent}</td>
												<td class="tbl-text-align-left">${searchList.expiryDate}</td>
												<td class="tbl-text-align-right">${searchList.publicKeyIndex}</td>
												<td class="tbl-text-align-left">${searchList.status}</td>
												<td style="white-space: nowrap;">
												<c:if test="${fn:contains(existingFeatures,caPublicKeysView)}">
													<a href="javascript:viewCAPublicKeys('${searchList.publicKeyId}')">
														<img src="../images/eyeimage.png" alt="<spring:message code="common.label.view"/>"
														title="<spring:message code="common.label.view"/>"></img> <span
														class="glyphicon glyphicon-eye"></span>
													</a>
												</c:if>
												<c:if test="${searchList.status == 'Active'}"> 
													 <a href="javascript:changeStatus('${searchList.publicKeyId}','Suspended','Suspended','caPublicKeyPopupDiv')" title="Suspend">
													 <img src="../images/active.png" alt="Suspend" title="Suspend"></img></a>
													 <c:if test="${fn:contains(existingFeatures,caPublicKeysEdit)}">
														 <a href="javascript:editCAPublicKeys('${searchList.publicKeyId}')" title="Edit" class="table-actionicon-margin">
														 <span class="glyphicon glyphicon-pencil"></span></a>
													</c:if>
													<c:if test="${fn:contains(existingFeatures,caPublicKeysDelete)}">
												  	<a href="javascript:confirmDeleteCAPublicKey('${searchList.publicKeyId }')">
												  	<span class="glyphicon glyphicon-trash"></span></a>
												  </c:if>	 
											   </c:if>
											   <c:if test="${searchList.status == 'Suspended'}">
													<a href="javascript:changeStatus('${searchList.publicKeyId}','Active','Active','caPublicKeyPopupDiv')" title="Active">
												    <img alt="Active" src="../images/deactive.png" title="Activate"></img></a>	
											   </c:if>
											   </td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="10" style="color: red;"><spring:message code="ca.public.label.nopublickeys"/></td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
						<table class="table table-striped table-bordered table-condensed">
							<c:if test="${ !(fn:length(searchList) eq 0)}">
								<tr class="table-footer-main">
									<td colspan="10" class="search-table-header-column">
										<div class="col-sm-12">
											<div class="col-sm-3">
												<%-- <div class="btn-toolbar" role="toolbar">
													<div class="btn-group custom-table-footer-button">
														<a
															href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.XLS_FILE_FORMAT%>')">
															<button type="button" class="btn btn-default">
																<img src="../images/excel.png">
															</button>
														</a> <a
															href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.PDF_FILE_FORMAT%>')">
															<button type="button" class="btn btn-default">
																<img src="../images/pdf.png">
															</button>
														</a>
													</div>
												</div> --%>
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
	
	<div id="caPublicKeyPopupDiv" class="locatioin-list-popup">
		<span class="glyphicon glyphicon-remove"
			onclick="closePopup();clearPopupDesc();"></span>
		<h2><spring:message code="prepaid-admin-programmanager-search-label.ChangeStatus"/></h2>
		<form:form action="caPublicKeyActivationSuspention" name="caPublicKeyActivationSuspentionForm" method="post">
			<input type="hidden" id="suspendActiveId" name="caPublicKeyId" /> <input
				type="hidden" id="suspendActiveStatus" name="caPublicKeyStatus" /> 
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
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/sortable.js"></script>
	<script src="../js/messages.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/capublickeys.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script type="text/javascript">
	
	$(document).ready(function() {
		$('#caPublicKeyPopupDiv').popup({
			blur : false
		});
		$("#navListId2").addClass("active-background");
	});
	
	function closePopup() {
		$('#caPublicKeyPopupDiv').popup("hide");
		$('#deletePopup').popup("hide");
	}
	function openPopup() {
		$('#caPublicKeyPopupDiv').popup("show");
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