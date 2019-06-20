<!DOCTYPE html>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page  import="java.util.Calendar"%>
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
						<span class="breadcrumb-text"><spring:message code="merchant.label.merchant"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="common.label.search"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<c:if test="${fn:contains(existingFeatures,merchantView) || fn:contains(existingFeatures,merchantEdit) || fn:contains(existingFeatures,merchantDelete) || fn:contains(existingFeatures,resellerMerchantView)||fn:contains(existingFeatures,merchantCreate)}">
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="common.label.search"/></a>
					</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,merchantCreate)}"> 
					<div class="tab-header-container">
						<a href="merchant-create"><spring:message code="common.label.create"/></a>
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
										<span class="red-error">&nbsp;${error }</span> 
										<span id="sucessDiv" class="green-error">&nbsp;${sucess }</span>
									</div>
								</div>

								<form:form action="getMerchants" name="paginationForm" method="post">
									<input type="hidden" id="pageNumberId" name="pageNumber" /> 
									<input type="hidden" id="totalRecordsId" name="totalRecords" />
									 <input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								<form:form action="editMerchant" name="editMercahntForm" method="post">
									<input type="hidden" id="getMerchantId" name="getMerchantId" />
									 <input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								<form:form action="editSubMerchant" name="editSubMercahntForm" method="post">
									<input type="hidden" id="getSubMerchantId" name="getSubMerchantId" />
									 <input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								
								<form:form action="merchant-view" name="viewMercahntForm" method="post">
									<input type="hidden" id="merchantViewId" name="merchantViewId" />
									<input type="hidden" id="merchantType" name="merchantType" />
									 <input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

								<form:form action="deleteMerchant" name="deleteMercahntForm" method="post">
									<input type="hidden" id="getMerchantsId" name="getMerchantsId" />
									<input type="hidden" id="merchantsType" name="merchantsType" />
									 <input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

								<form:form action="get-merchant-report" name="downloadReport" method="post">
									<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" /> 
									<input type="hidden" id="downloadTypeId" name="downloadType" />
									<input type="hidden" id="totalRecords" name="totalRecords" />
									<input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
									 <input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								<!--getSubMerchantListForm  -->
								<form:form action="showSubMerchantList" name="getSubMerchantListForm" method="post">
									<input type="hidden" id="getParentMerchantId" name="getParentMerchantId" />
									 <input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="merchant-search" modelAttribute="merchant" name="merchant">
								 <input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.merchantcode"/></label>
													<form:input cssClass="form-control" path="merchantCode"
														id="merchantCode" maxlength="15" onblur="clientValidation('merchantCode','merchant_ID','merchantCodeEr')"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="merchantCodeEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.merchantname"/></label>
													<form:input cssClass="form-control" path="businessName"
														id="businessName" onblur="clientValidation('businessName','companyname_not_mandatory','businessNameEr')"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="businessNameEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
										<%-- 		<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.entitytype"/></label>
													<form:input cssClass="form-control" path="entityType"
														id="entityType" onblur="clientValidation('entityType','middle_name','entityTypeEr')"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="entityTypeEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="admin.iso.label.message"/></label>
													<form:input cssClass="form-control" path="isoName"
														id="isoName" onblur="clientValidation('isoName','companyname_not_mandatory','isoNameEr')"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="isoNameEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset> --%>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.emailID"/> </label>
													<form:input cssClass="form-control" path="emailId"
														id="emailId" onblur="clientValidation('emailId','email_Id','emailIdEr')"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="emailIdEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
											<%-- 	<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="admin.cardprogramname"/></label>
													<form:input cssClass="form-control" path="cardProgramName" id="cardProgramName" onblur="clientValidation('cardProgramName','middle_name','cardProgramNameEr')"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="cardProgramNameEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset> --%>
											<%-- 	<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="access-user-create.label.entityname"/></label>
													<form:input cssClass="form-control" path="programManagerName" id="programManagerName" onblur="clientValidation('programManagerName','companyname_not_mandatory','programManagerNameEr')"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="programManagerNameEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset> --%>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.country"/></label>
													<form:select cssClass="form-control" path="country"
														id="country">
														<form:option value=""><spring:message code="reports.option.select"/></form:option>
														<c:forEach items="${countryList}" var="country">
															<form:option value="${country.label}">${country.label}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.status"/></label>
													<form:select cssClass="form-control" path="status"
														id="status">
														<form:option value=""><spring:message code="common.label.all"/></form:option>
														<form:option value="0"><spring:message code="common.label.active"/></form:option>
														<form:option value="1"><spring:message code="common.label.pending"/></form:option>
														<form:option value="2"><spring:message code="common.label.inactive"/></form:option>
														<%-- <form:option value="3"><spring:message code="common.label.deleted"/></form:option> --%>
														<%-- <form:option value="5"><spring:message code="common.label.selfregistered"/></form:option> --%>

													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<%-- <fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.submerchantcode"/></label>
													<form:input cssClass="form-control" path="subMerchantCode"
														id="subMerchantCode" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset> --%>
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
													onclick="return validateSpecialCharactersMerchant();trimUserData()"> <input type="button"
													class="form-control button pull-right" value='<spring:message code="common.label.reset"/>'
													onclick="goToMerchantSearch()">
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
								<td colspan="10" class="search-table-header-column"><span
									class="glyphicon glyphicon-search search-table-icon-text"></span>
									<span><spring:message code="common.label.search"/></span>
									<span class="pull-right"><spring:message code="common.label.totalcount"/> : <label id="totalCount">${totalRecords}</label></span>
									</td>
							</tr>
							</table>
							<!-- Search Table Header End -->
							<!-- Search Table Content Start -->
							<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
							<tr>
								<th style="width: 150px;"><spring:message code="merchant.label.merchantcode"/></th>
								<th style="width: 151px;"><spring:message code="merchant.label.merchantname"/></th>
								<th style="width: 151px;"><spring:message code="currency-search-page.label.currencycode"/></th>
								<%-- <th style="width: 110px;"><spring:message code="merchant.label.entitytype"/></th>
								<th style="width: 110px;"><spring:message code="merchant.label.entityname"/></th>
								<th style="width: 103px;"><spring:message code="admin.cardprogramname"/></th> --%>
								<th style="width: 150px;"><spring:message code="merchant.label.emailID"/></th>
								<th style="width: 100px;"><spring:message code="common.label.country"/></th>
								<th style="width: 100px;"><spring:message code="common.label.status"/></th>
								<th class="sorter-false tablesorter-header tablesorter-headerUnSorted"><spring:message code="common.label.action"/></th>
							</tr>
							</thead>
							<c:choose>
								<c:when test="${!(fn:length(merchants) eq 0) }">
									<c:forEach items="${merchants}" var="merchantData">
										<tr>
											<td><div class="feeDescDiv tbl-text-align-right">${merchantData.merchantCode }</div></td>
											<td><div class="feeDescDiv tbl-text-align-left">${merchantData.businessName }</div></td>
											<td><div class="feeDescDiv tbl-text-align-left">${merchantData.localCurrency }</div></td>
											<%-- <td><div class="feeDescDiv tbl-text-align-left">${merchantData.entityType }</div></td>
											<td><div class="feeDescDiv tbl-text-align-left">${merchantData.entityName }</div></td>
											<td><div class="feeDescDiv">${merchantData.cardProgramName }</div></td> --%>
											<td><div class="feeDescDiv">${merchantData.emailId }</div></td>
											<%-- <td><div class="feeDescDiv tbl-text-align-left">${merchantData.city }</div></td> --%>
											<td class="tbl-text-align-left">${merchantData.country }</td>
											<td class="tbl-text-align-left">${merchantData.status }</td>
											<td style="white-space:nowrap;">
											<c:if test="${fn:contains(existingFeatures,merchantView) || fn:contains(existingFeatures,resellerMerchantView)}">
											<c:if test="${merchantData.status == 'Active' || merchantData.status == 'Suspended' || merchantData.status == 'Pending'}">											
												<a href="javascript:viewMerchantInfo('${merchantData.id }','Merchant')"><img src="../images/eyeimage.png" alt="<spring:message code="common.label.view"/>" title="<spring:message code="common.label.view"/>"></img>
												<span class="glyphicon glyphicon-eye"></span></a>
											</c:if>													
											</c:if> 
											<c:if test="${fn:contains(existingFeatures,merchantEdit)}">
										    <c:if test="${merchantData.status == 'Active' || merchantData.status == 'Pending'}">
												<a href="javascript:editMerchant('${merchantData.id }')" title="<spring:message code="common.label.edit"/>" class="table-actionicon-margin">
												<span class="glyphicon glyphicon-pencil"></span></a>
											</c:if>
											</c:if> 
											<c:if test="${fn:contains(existingFeatures,merchantDelete)}">
											<c:if test="${merchantData.status == 'Active'}">
											    <a href="javascript:changeStatus('${merchantData.id}','Suspended','Suspended','merchantPopupDiv')" title="Suspend">
												<img src="../images/active.png" alt="Suspend" title="Suspend"></img></a>
											</c:if>
											</c:if>
											<c:if test="${fn:contains(existingFeatures,merchantDelete)}">
											<c:if test="${merchantData.status == 'Suspended'}">
											    <a href="javascript:changeStatus('${merchantData.id}','Active','Active','merchantPopupDiv')" title="Active">
												<img alt="Active" src="../images/deactive.png" title="Activate"></img></a>
											</c:if>
											</c:if>
											<c:if test="${fn:contains(existingFeatures,merchantDelete)}">
											<c:if test="${merchantData.status == 'Active'}">
											    <a href="javascript:confirmDeleteMerchant('${merchantData.id }','Merchant')">
												<span class="glyphicon glyphicon-trash"></span></a>
											</c:if>
											</c:if>
										</td>
									</tr>
									</c:forEach>
									<tr>
										<c:choose>
											<c:when test="${!(fn:length(subMerchants) eq 0) }">
												<c:forEach items="${subMerchants}" var="subMerchantData">
												<c:if test="${fn:contains(existingFeatures,subMerchantView) || fn:contains(existingFeatures,resellerSubMerchantView)}">
													<c:if
														test="${subMerchantData.status == 'Active' || subMerchantData.status == 'Suspended'|| subMerchantData.status == 'Pending'}">
														<button onclick="javascript:viewMerchants('${subMerchantData.id }')" title="View Sub Merchant" class="table-actionicon-margin pull-right">View
															Sub merchant</button>
													</c:if>
													</c:if>

												</c:forEach>
											</c:when>
										</c:choose>
									</tr>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="10" style="color: red;"><spring:message code="merchant.label.nomerchantsfound"/></td>
									</tr>
								</c:otherwise>
							</c:choose>
							</table>
							<table class="table table-striped table-bordered table-condensed">
							<c:if test="${ !(fn:length(merchants) eq 0)}">
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
	
	<div id="merchantPopupDiv" class="locatioin-list-popup">
		<span class="glyphicon glyphicon-remove"
			onclick="closePopup();clearPopupDesc();"></span>
		<h2><spring:message code="prepaid-admin-programmanager-search-label.ChangeStatus"/></h2>
		<form:form action="merchantActivationSuspention" name="merchantActivationSuspentionForm" method="post">
			<input type="hidden" id="suspendActiveId" name="merchantId" /> <input
				type="hidden" id="suspendActiveStatus" name="merchantStatus" />
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
	<script src="../js/jquery.cookie.js"></script>
	
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/sortable.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/merchant.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script src="../js/messages.js"></script>
	<script>
		$(document).ready(function() {
			$('#merchantPopupDiv').popup({
				blur : false
			});
			$("#navListId6").addClass("active-background");
			
		});
		function closePopup() {
			$('#merchantPopupDiv').popup("hide");
			$('#deletePopup').popup("hide");
		}
		function openPopup() {
			$('#merchantPopupDiv').popup("show");
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
