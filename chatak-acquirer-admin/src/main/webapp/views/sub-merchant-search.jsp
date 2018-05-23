<!DOCTYPE html>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.pg.util.Constants"%>
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
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="../js/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
<link href="../css/jquery.datetimepicker.css" rel="stylesheet" type="text/css">
<script src="../js/jquery.cookie.js"></script>
<script src="../js/common-lib.js"></script>
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
</head>
<body>

	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<!--Header Block End -->
			<!--Navigation Block Start -->
			<%-- <jsp:include page="header.jsp"></jsp:include> --%>
			<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block Start  -->

			<!--Article Block Start-->
			<article>
				<form:form action="get-sub-merchant-report" name="downloadReport" method="post">
					<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" />
					<input type="hidden" id="downloadTypeId" name="downloadType" />
					<input type="hidden" id="totalRecords" name="totalRecords" />
					<input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
				</form:form>
				<form:form action="getSubMerchants" name="paginationForm" method="post">
					<input type="hidden" id="pageNumberId" name="pageNumber" />
					<input type="hidden" id="totalRecordsId" name="totalRecords" />
				</form:form>
				<form:form action="editSubMerchant" name="editSubMercahntForm" method="post">
					<input type="hidden" id="merchantEditId" name="merchantEditId" />
				</form:form>
				
				<form:form action="merchant-view" name="viewMercahntForm" method="post">
					<input type="hidden" id="merchantViewId" name="merchantViewId" />
					<input type="hidden" id="merchantType" name="merchantType" />
				</form:form>
				
				<form:form action="deleteMerchant" name="deleteMercahntForm" method="post">
					<input type="hidden" id="getMerchantsId" name="getMerchantsId" />
					<input type="hidden" id="merchantsType" name="merchantsType" />
				</form:form>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="manage.label.manage" /></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span>
						<span class="breadcrumb-text"><spring:message code="manage.label.sub-merchant" /></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="manage.label.sub-merchant.search" /></span>
					</div>
					<!-- <div class=" pull-right" style="margin-right: 40px"
						id="subMerchant">
						<img alt="Create sub merchant" src="../images/user_icon.png"><a
							href="sub-merchant-create">Add Sub Merchant</a>
					</div> -->
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<c:if test="${fn:contains(existingFeatures,subMerchantView) || fn:contains(existingFeatures,subMerchantEdit) || fn:contains(existingFeatures,subMerchantDelete)||fn:contains(existingFeatures,subMerchantCreate)}">
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="manage.label.sub-merchant.search" /></a>
					</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,subMerchantCreate)}">
					<div class="tab-header-container">
						<a href="sub-merchant-create?entityType=SubMerchant"><spring:message code="manage.label.sub-merchant.create" /></a>
					</div>
					</c:if>
					
					<!-- <div class="tab-header-container active-background">
						<a href="#">View</a>
					</div> -->
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder padding0">

						<div class="row margin0">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionMsg col-xs-12">
										<span class="red-error">&nbsp;${error }</span> <span
											class="green-error">&nbsp;${sucess }</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="sub-merchant-search" commandName="merchant"
									name="merchant">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="manage.label.sub-merchant.submerchantcode" /></label>
													<form:input cssClass="form-control" path="subMerchantCode"
														id="subMerchantCode" maxlength="15"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.merchantname" /></label>
													<form:input cssClass="form-control" path="businessName"
														id="businessName" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="manage.label.sub-merchant.firstname" /></label>
													<form:input cssClass="form-control" path="firstName"
														id="firstName" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="manage.label.sub-merchant.lastname" /></label>
													<form:input cssClass="form-control" path="lastName"
														id="lastName" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="manage.label.sub-merchant.E-mailID" /></label>
													<form:input cssClass="form-control" path="emailId"
														id="emailId" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="manage.label.sub-merchant.phone" /></label>
													<form:input cssClass="form-control" path="phone" id="phone" onkeypress="return numbersonly(this,event)" maxlength="10" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="manage.label.sub-merchant.city" /></label>
													<form:input cssClass="form-control" path="city" id="city" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="manage.label.sub-merchant.country" /></label>
													<form:select cssClass="form-control" path="country"
														id="country">
														<form:option value="">..:<spring:message code="manage.option.sub-merchant.select" />:..</form:option>
														<c:forEach items="${countryList}" var="country">
															<form:option value="${country.label}">${country.label}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="manage.label.sub-merchant.status" /></label>
													<form:select cssClass="form-control" path="status"
														id="status">
														<form:option value=""><spring:message code="manage.option.sub-merchant.all" /></form:option>
														<form:option value="0"><spring:message code="manage.option.sub-merchant.active" /></form:option>
														<%-- <form:option value="1"><spring:message code="manage.option.sub-merchant.pending" /></form:option> --%>
														<form:option value="2"><spring:message code="manage.option.sub-merchant.in-active" /></form:option>
														<%-- <form:option value="3"><spring:message code="manage.option.sub-merchant.deleted" /></form:option> --%>
														<%-- <form:option value="5"><spring:message code="manage.option.sub-merchant.self-registered" /></form:option> --%>

													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="manage.label.sub-merchant.merchantcode" /></label>
													<form:input cssClass="form-control" path="merchantCode"
														id="merchantCode" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="manage.label.sub-merchant.recordsperpage" /></label>
													<form:select cssClass="form-control" path="pageSize" id="pageSize">
														<form:option value="10"><spring:message code="manage.option.sub-merchant.10" /></form:option>
														<form:option value="20"><spring:message code="manage.option.sub-merchant.20" /></form:option>
														<form:option value="50"><spring:message code="manage.option.sub-merchant.50" /></form:option>
														<form:option value="100"><spring:message code="manage.option.sub-merchant.100" /></form:option>
														<form:option value="250"><spring:message code="manage.option.sub-merchant.250" /></form:option>
														<form:option value="500"><spring:message code="manage.option.sub-merchant.500" /></form:option>
														<form:option value="1000"><spring:message code="manage.option.sub-merchant.1000" /></form:option>
													</form:select>
												</fieldset>
											</div>
										</div>
										<!--Panel Action Button Start -->
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit" class="form-control button pull-right" onclick="return trimUserData()"
													value="<spring:message code="manage.buttton.sub-merchant.search" />"> <input type="button"
													class="form-control button pull-right" value="<spring:message code="manage.buttton.sub-merchant.reset" />"
													onclick="goToSubMerchantSearch()">
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
									<span><spring:message code="manage.label.sub-merchant.search" /></span>
									<span class="pull-right"><spring:message code="manage.label.sub-merchant.totalcount" /> : <label id="totalCount">${totalRecords}</label></span>
									</td>
							</tr>
							</table>
							<!-- Search Table Header End -->
							<!-- Search Table Content Start -->
							<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
							<tr>
								<th style="width: 102px;"><spring:message code="manage.label.sub-merchant.submerchantcode" /></th>
								<th style="width: 151px;"><spring:message code="merchant.label.merchantname" /></th>
								<th style="width: 151px;"><spring:message code="currency-search-page.label.currencycode"/></th>
								<th style="width: 110px;"><spring:message code="manage.label.sub-merchant.firstname" /></th>
								<th style="width: 92px;"><spring:message code="manage.label.sub-merchant.lastname" /></th>
								<th style="width: 93px;"><spring:message code="manage.label.sub-merchant.E-mailID" /></th>
								<th style="width: 72px;"><spring:message code="manage.label.sub-merchant.phone" /></th>
								<th style="width: 103px;"><spring:message code="manage.label.sub-merchant.city" /></th>
								<th style="width: 54px;"><spring:message code="manage.label.sub-merchant.country" /></th>
								<th style="width: 93px;"><spring:message code="manage.label.sub-merchant.status" /></th>
								<th class="sorter-false tablesorter-header tablesorter-headerUnSorted"><spring:message code="manage.label.sub-merchant.action" /></th>
							</tr>
							</thead>
							<c:choose>
								<c:when test="${!(fn:length(merchants) eq 0) }">
									<c:forEach items="${merchants}" var="merchantData">
										<tr>
											<td><div class="feeDescDiv tbl-text-align-right">${merchantData.merchantCode }</div></td>
											<td><div class="feeDescDiv tbl-text-align-left">${merchantData.businessName }</div></td>
											<td><div class="feeDescDiv tbl-text-align-left">${merchantData.localCurrency }</div></td>
											<td><div class="feeDescDiv tbl-text-align-left">${merchantData.firstName }</div></td>
											<td><div class="feeDescDiv tbl-text-align-left">${merchantData.lastName }</div></td>
											<td><div class="feeDescDiv">${merchantData.emailId }</div></td>
											<td><div class="feeDescDiv tbl-text-align-left">${merchantData.phone }</div></td>
											<td><div class="feeDescDiv tbl-text-align-left">${merchantData.city }</div></td>
											<td><div class="feeDescDiv tbl-text-align-left">${merchantData.country }</div></td>
											<td><div class="feeDescDiv tbl-text-align-left">${merchantData.status }</div></td>
											<td style="white-space:nowrap;">
											<c:if test="${merchantData.status == 'Active' || merchantData.status == 'Suspended'}">
											    <c:if test="${fn:contains(existingFeatures,subMerchantView)}">
													<a href="javascript:viewMerchantInfo('${merchantData.id }','SubMerchant')"><img src="../images/eyeimage.png" alt="<spring:message code="common.label.view"/>" title="<spring:message code="manage.buttton.sub-merchant.view" />"></img>
													<span class="glyphicon glyphicon-eye"></span></a>
											    </c:if>
											</c:if> 	
											<c:if test="${merchantData.status == 'Active'}">
											    <c:if test="${fn:contains(existingFeatures,subMerchantEdit)}">
													<a href="javascript:editSubMerchant('${merchantData.id }')" title="<spring:message code="manage.buttton.sub-merchant.edit" />" class="table-actionicon-margin">
													<span class="glyphicon glyphicon-pencil"></span></a>
											    </c:if>
											</c:if> 
											<c:if test="${fn:contains(existingFeatures,merchantDelete)}">
											   <c:if test="${merchantData.status == 'Active'}">
											       <a href="javascript:changeStatus('${merchantData.id}','Suspended','Suspended','subMerchantPopupDiv')" title="Suspend">
												   <img src="../images/active.png" alt="Suspend" title="Suspend"></img></a>
											   </c:if>
											</c:if>
											<c:if test="${fn:contains(existingFeatures,merchantDelete)}">
											    <c:if test="${merchantData.status == 'Suspended'}">
											        <a href="javascript:changeStatus('${merchantData.id}','Active','Active','subMerchantPopupDiv')" title="Active">
												    <img alt="Active" src="../images/deactive.png" title="Activate"></img></a>
											    </c:if>
											</c:if>
											<c:if test="${merchantData.status == 'Active'}">
											    <c:if test="${fn:contains(existingFeatures,subMerchantDelete)}">
												    <a href="javascript:confirmDeleteMerchant('${merchantData.id }','SubMerchant')">
												    <span class="glyphicon glyphicon-trash"></span></a>
											    </c:if>
											</c:if>
											</td>
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
										<td colspan="10" style="color: red;"><spring:message code="manage.label.sub-merchant.nomerchantsfound" /></td>
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
														<a href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.XLS_FILE_FORMAT%>', ${totalRecords})">
															<button type="button" class="btn btn-default">
																<img src="../images/excel.png">
															</button>
														</a> 
														<a href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.PDF_FILE_FORMAT%>', ${totalRecords})">
															<button type="button" class="btn btn-default">
																<img src="../images/pdf.png">
															</button>
														</a>
														<a>
															<input type="checkbox" class="autoCheck check" id="totalRecordsDownload">
															<spring:message code="manage.label.sub-merchant.downloadall" /> 
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
					<!-- Content Block End -->
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
	<div id="subMerchantPopupDiv" class="locatioin-list-popup">
		<span class="glyphicon glyphicon-remove"
			onclick="closePopup();clearPopupDesc();"></span>
		<h2><spring:message code="prepaid-admin-programmanager-search-label.ChangeStatus"/></h2>
		<form:form action="subMerchantActivationSuspention" name="merchantActivationSuspentionForm" method="post">
			<input type="hidden" id="suspendActiveId" name="subMerchantId" /> <input
				type="hidden" id="suspendActiveStatus" name="subMerchantStatus" /> 
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
	
	
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/sorting.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/tablesorter.js"></script>
	<script src="../js/tablesorter.widgets.js"></script>
	<script type="text/javascript" src="../js/merchant.js"></script>
	<script type="text/javascript" src="../js/chatak-ajax.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script src="../js/messages.js"></script>
	<script>
		$(document).ready(function() {
			$('#subMerchantPopupDiv').popup({
				blur : false
			});
			$("#navListId6").addClass("active-background");			
			
		});
		function closePopup() {
			$('#subMerchantPopupDiv').popup("hide");
			$('#deletePopup').popup("hide");
		}
		function openPopup() {
			$('#subMerchantPopupDiv').popup("show");
		}
		
		/* $(".bank-info-details-content, .legal-details-content, .legal-details-rep-content, .free-transactions-content, .atm-transaction-content, .pos-transaction-content").hide();
		$(".account-details-content").show();
		$(".merchant-arrow").show();
		$(".contact-arrow, .bank-info-arrow, .legal-arrow, .legal-rep-arrow, .bank-legal-arrow, .bank-arrow, .configuration-arrow, .final-arrow").hide();
		$(".account-details-list, .bank-prev").click(function(){
            $(".merchant-circle-tab").addClass("active-circle");
			$(".bank-info-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .contact-circle-tab, .bank-circle-tab, .final-circle-tab").removeClass("active-circle");
			$(".merchant-arrow").show();
			$(".bank-info-arrow, .legal-arrow, .legal-rep-arrow, .contact-arrow, .bank-arrow, .final-arrow").hide();
			$(".account-details-content").show();
			$(".atm-transaction-content,.bank-info-details-content, .legal-details-content, .legal-details-rep-content, .pos-transaction-content, .free-transactions-content").hide();
		});
		
		
		 $(".bank-list, .acc-next, .legal-prev").click(function(){
			$(".bank-info-circle-tab").addClass("active-circle");
			$(".merchant-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .contact-circle-tab, .bank-circle-tab, .final-circle-tab").removeClass("active-circle");
			$(".bank-info-arrow").show();
			$(".merchant-arrow, .legal-arrow, .legal-rep-arrow, .contact-arrow, .configuration-arrow, .bank-arrow, .configuration-arrow, .final-arrow").hide();
			$(".bank-info-details-content").show();
			$(".account-details-content, .legal-details-content, .legal-details-rep-content, .atm-transaction-content, .pos-transaction-content, .free-transactions-content").hide();
		}); 
		
		 $(".legal-entiy-list, .bank-next, .legal-rep-prev").click(function(){
		 	$(".legal-circle-tab").addClass("active-circle");
			$(".merchant-circle-tab, .bank-info-circle-tab, .legal-circle-rep-tab, .contact-circle-tab, .bank-circle-tab, .final-circle-tab").removeClass("active-circle");
			$(".legal-arrow").show();
			$(".merchant-arrow, .legal-rep-arrow, .bank-info-arrow, .contact-arrow, .configuration-arrow, .bank-arrow, .configuration-arrow, .final-arrow").hide();
			$(".legal-details-content").show();
			$(".account-details-content, .legal-details-rep-content, .bank-info-details-content, .atm-transaction-content, .pos-transaction-content, .free-transactions-content").hide();
		});
		$(".legal-entiy-rep-list, .legal-next, .free-prev").click(function(){
		 	$(".legal-circle-rep-tab").addClass("active-circle");
			$(".merchant-circle-tab, .bank-info-circle-tab, .legal-circle-tab, .contact-circle-tab, .bank-circle-tab, .final-circle-tab").removeClass("active-circle");
			$(".legal-rep-arrow").show();
			$(".merchant-arrow, .bank-info-arrow, .legal-arrow, .contact-arrow, .configuration-arrow, .bank-arrow, .configuration-arrow, .final-arrow").hide();
			$(".legal-details-rep-content").show();
			$(".account-details-content, .bank-info-details-content, .legal-details-content, .atm-transaction-content, .pos-transaction-content, .free-transactions-content").hide();
		});
		$(".free-transactions-list, .legal-rep-next, .atm-prev").click(function() {
		 	$(".contact-circle-tab").addClass("active-circle");
			$(".merchant-circle-tab,.bank-info-circle-tab, .bank-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .final-circle-tab").removeClass("active-circle");
			$(".contact-arrow").show();
			$(".merchant-arrow, .legal-arrow, .legal-rep-arrow, .bank-info-arrow, .configuration-arrow, .bank-arrow, .final-arrow").hide()
			$(".free-transactions-content").show();
			$(".atm-transaction-content, .legal-details-content, .legal-details-rep-content, .bank-info-details-content, .pos-transaction-content, .account-details-content").hide();
		});
		$(".atm-transactions-list, .free-next, .pos-prev").click(function() {
		 	$(".bank-circle-tab").addClass("active-circle");
			$(".merchant-circle-tab,.bank-info-circle-tab, .contact-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .final-circle-tab").removeClass("active-circle");
			$(".configuration-arrow").show();
			$(".contact-arrow, .merchant-arrow, .legal-arrow, .legal-rep-arrow, .bank-info-arrow, .final-arrow").hide()
			$(".atm-transaction-content").show();
			$(".free-transactions-content, .bank-info-details-content, .legal-details-content, .legal-details-rep-content, .pos-transaction-content, .account-details-content").hide();
			//populatePartnerAndAgentDetails($('#appMode').val(),'merchant','view');
		});
		$(".pos-transactions-list, .atm-next").click(function() {
			$(".final-circle-tab").addClass("active-circle");
			$(".merchant-circle-tab, .bank-info-circle-tab, .contact-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .bank-circle-tab").removeClass("active-circle");
			$(".final-arrow").show();
			$(".contact-arrow, .bank-arrow,.configuration-arrow, .bank-info-arrow, .legal-arrow, .legal-rep-arrow, .merchant-arrow").hide()
			$(".pos-transaction-content").show();
			$(".free-transactions-content, .bank-info-details-content, .legal-details-content, .legal-details-rep-content, .atm-transaction-content, .account-details-content").hide();
		});

		function loadRadio(data) {
			if (data == '0') {
				$("#noAutoSettlement").prop("checked", true);
				$("#allowAutoSettlement").prop("checked", false);
			} else if (data == '1') {
				$("#noAutoSettlement").prop("checked", false);
				$("#allowAutoSettlement").prop("checked", true);
			}
		}
		function showAddSubMerchant() {
			if (checkStatusAndMerchantType()) {
				$('#subMerchant').show();
			} else {
				$('#subMerchant').hide();
			}
		} */
	</script>
</body>
</html>
