<!DOCTYPE html>
<%@page import="com.chatak.merchant.constants.StatusConstants"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
			<header>
				<jsp:include page="header.jsp"></jsp:include>
				<!--Header Welcome Text and Logout button End -->
			</header>
			<!--Header Block End -->
			<!--Navigation Block Start -->
			<nav class="col-sm-12 nav-bar" id="main-navigation">
				<%-- <jsp:include page="main-navigation.jsp"></jsp:include>  --%>
				<%@include file="navigation-panel.jsp"%>
			</nav>
			<!--Navigation Block End -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message
								code="search-sub-merchant.label.submerchant" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message
								code="search-sub-merchant.label.search" /></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<c:if
						test="${fn:contains(existingFeatures,subMerchantView) || fn:contains(existingFeatures,subMerchantEdit) || fn:contains(existingFeatures,subMerchantDelete)||fn:contains(existingFeatures,subMerchantCreate)}">
						<div class="tab-header-container-first active-background">
							<a href="search-sub-merchant"><spring:message
									code="search-sub-merchant.lable.search" /></a>
						</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,subMerchantCreate)}">
						<div class="tab-header-container">
							<a href="sub-merchant-create"><spring:message
									code="search-sub-merchant.label.create" /></a>
						</div>
					</c:if>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionMsg" data-toggle="tooltip"
										data-placement="top" title="">
										<span class="red-error">&nbsp;${error }</span> <span id="sucessDiv"
											class="green-error">&nbsp;${sucess }</span>
									</div>
								</div>

								<form action="getMerchants" name="paginationForm" method="post">
									<input type="hidden" id="pageNumberId" name="pageNumber" /> <input
										type="hidden" id="totalRecordsId" name="totalRecords" />
										<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form>
								<form action="editMercahnt" name="editMercahntForm"
									method="post">
									<input type="hidden" id="getMerchantId" name="getMerchantId" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form>
								<form action="editSubMerchant" name="editSubMercahntForm"
									method="post">
									<input type="hidden" id="getSubMerchantId"
										name="getSubMerchantId" />
										<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form>

								<form action="deleteMerchant" name="deleteMercahntForm"
									method="post">
									<input type="hidden" id="getMerchantsId" name="getMerchantsId" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form>

								<form action="get-merchant-report" name="downloadReport"
									method="post">
									<input type="hidden" id="downloadPageNumberId"
										name="downLoadPageNumber" /> <input type="hidden"
										id="downloadTypeId" name="downloadType" /> <input
										type="hidden" id="totalRecords" name="totalRecords" /> <input
										type="hidden" id="downloadAllRecords"
										name="downloadAllRecords" />
										<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form>
								<!--getSubMerchantListForm  -->
								<form action="viewSubMerchant" name="getSubMerchantListForm"
									method="post">
									<input type="hidden" id="getSubMerchantIdForView"
										name="getSubMerchantIdForView" />
										<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form>

								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="search-sub-merchant" modelAttribute="merchant"
									name="merchant">
									<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="search-sub-merchant.label.submerchantcode" /></label>
													<form:input cssClass="form-control" path="subMerchantCode"
														id="subMerchantCode" maxlength="15" onblur="clientValidation('subMerchantCode','merchant_codes','subMerchantCodeError')"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="subMerchantCodeError" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="search-sub-merchant.label.merchantcompanyname" /></label>
													<form:input cssClass="form-control" path="businessName"
														id="businessName" onblur="clientValidation('businessName','company_name_aphhanumeric_space_dot_NM','businessNameError')"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="businessNameError" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="search-sub-merchant.label.firstname" /></label>
													<form:input cssClass="form-control" path="firstName"
														id="firstName" onblur="clientValidation('firstName','passport_number','firstNameError')"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="firstNameError" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="search-sub-merchant.label.lastname" /></label>
													<form:input cssClass="form-control" path="lastName"
														id="lastName" onblur="clientValidation('lastName','passport_number','lastNameError')"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="lastNameError" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="search-sub-merchant.label.e-mailid" /> </label>
													<form:input cssClass="form-control" path="emailId"
														id="emailId" onblur="clientValidation('emailId','email_id','emailIdError')"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="emailIdError" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="search-sub-merchant.label.phone" /></label>
													<form:input cssClass="form-control" path="phone" id="phone"
														onkeypress="return numbersonly(this,event)" maxlength="10" onblur="clientValidation('phone','mobile_optional','phoneError')"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="phoneError" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="search-sub-merchant.label.city" /></label>
													<form:input cssClass="form-control" path="city" id="city" onblur="clientValidation('city','name_account','cityError')"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="cityError" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="search-sub-merchant.label.country" /></label>
													<form:select cssClass="form-control" path="country"
														id="country">
														<form:option value="">..:<spring:message
																code="sub-merchant-create.label.select" />:..</form:option>
														<c:forEach items="${countryList}" var="country">
															<form:option value="${country.label}">${country.label}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="search-sub-merchant.label.status" /></label>
													<form:select cssClass="form-control" path="status"
														id="status">
														<form:option value=""><spring:message code="search-sub-merchant.lable.all"/></form:option>
														<form:option value="0"><spring:message code="search-sub-merchant.lable.active"/></form:option>
														<form:option value="2"><spring:message code="search-sub-merchant.lable.inactive"/></form:option>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="search-sub-merchant.lable.recordsperpage" /></label>
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

											</div>
										</div>
										<!--Panel Action Button Start -->
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit" class="form-control button pull-right"
													onclick="return searchValidationForMerchant();trimUserData()"
													value="<spring:message code="search-sub-merchant.lable.search"/>">
												<input type="button" class="form-control button pull-right"
													value="<spring:message code="search-sub-merchant.lable.reset"/>"
													onclick="cancelCreateMerchant()">
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
						<div class="search-results-table" id="search-results-tableID">
							<table class="table table-striped table-bordered table-condensed"
								style="margin: 1px;">
								<!-- Search Table Header Start -->
								<tr>
									<td colspan="10" class="search-table-header-column"><span
										class="glyphicon glyphicon-search search-table-icon-text"></span>
										<span><spring:message
												code="search-sub-merchant.lable.search" /></span> <span
										class="pull-right"><spring:message
												code="search-sub-merchant.lable.totalcount" /> : <label
											id="totalRecords">${totalRecords}</label></span></td>
								</tr>
							</table>
							<!-- Search Table Header End -->
							<!-- Search Table Content Start -->
							<table id="serviceResults"
								class="table table-striped table-bordered table-responsive table-condensed tablesorter">
								<thead>
									<tr>
										<th style="width: 90px;"><spring:message
												code="search-sub-merchant.label.submerchantcode" /></th>
										<th style="width: 130px;"><spring:message
												code="search-sub-merchant.label.merchantcompanyname" /></th>
												<th style="width: 80px;"><spring:message
												code="search-sub-merchant.label.currencycode" /></th>
										<th style="width: 80px;"><spring:message
												code="search-sub-merchant.label.firstname" /></th>
										<th style="width: 80px;"><spring:message
												code="search-sub-merchant.label.lastname" /></th>
										<th style="width: 80px;"><spring:message
												code="search-sub-merchant.label.e-mailid" /></th>
										<th style="width: 80px;"><spring:message
												code="search-sub-merchant.label.phone" /></th>
										<th style="width: 45px;"><spring:message
												code="search-sub-merchant.label.city" /></th>
										<th style="width: 80px;"><spring:message
												code="search-sub-merchant.label.country" /></th>
										<th style="width: 80px;"><spring:message
												code="search-sub-merchant.label.status" /></th>
										<th style="width: 80px;"
											class="sorter-false tablesorter-header tablesorter-headerUnSorted"><spring:message
												code="search-sub-merchant.lable.action" /></th>
									</tr>
								</thead>
								<c:choose>
									<c:when test="${!(fn:length(merchants) eq 0) }">
										<c:forEach items="${merchants}" var="merchantData">
											<tr>
												<td><div class="feeDescDiv tbl-text-align-left">${merchantData.merchantCode }</div></td>
												<td><div class="feeDescDiv tbl-text-align-left">${merchantData.businessName }</div></td>
												<td><div class="feeDescDiv tbl-text-align-left">${merchantData.localCurrency }</div></td>
												<td><div class="feeDescDiv tbl-text-align-left">${merchantData.firstName }</div></td>
												<td><div class="feeDescDiv tbl-text-align-left">${merchantData.lastName }</div></td>
												<td><div class="feeDescDiv">${merchantData.emailId }</div></td>
												<td><div class="feeDescDiv tbl-text-align-left">${merchantData.phone }</div></td>
												<td><div class="feeDescDiv tbl-text-align-left">${merchantData.city }</div></td>
												<td><div class="feeDescDiv tbl-text-align-left">${merchantData.country }</div></td>
												<td><div class="tbl-text-align-left ">${merchantData.status }</div></td>
												<td><c:if
														test="${merchantData.status == 'Active' || merchantData.status == 'Suspended'}">
													<c:if test="${fn:contains(existingFeatures,subMerchantView)}">	
														<a href="javascript:getSubMerchantList('${merchantData.id }')"><img
															src="../images/eyeimage.png" alt="<spring:message code="common.label.view"/>" title="<spring:message code="common.label.view"/>"></img><span
															class="glyphicon glyphicon-eye"></span></a>
													</c:if>		
													</c:if> <c:if
														test="${merchantData.status == 'Active' }">
														<c:if test="${fn:contains(existingFeatures,subMerchantEdit)}">
															<a href="javascript:editSubMerchant('${merchantData.id }')"
																title="<spring:message code="common.label.edit"/>" class="table-actionicon-margin"><span
																class="glyphicon glyphicon-pencil"></span></a>
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
													<c:if test="${merchantData.status == 'Active'}">
														<c:if test="${fn:contains(existingFeatures,subMerchantDelete)}">
															<a href="javascript:confirmDeleteMerchant('${merchantData.id }')"><span
																class="glyphicon glyphicon-trash"></span></a>
														</c:if>
													</c:if></td>
											</tr>
										</c:forEach>
										<tr>
											<c:choose>
												<c:when test="${!(fn:length(subMerchants) eq 0) }">
													<c:forEach items="${subMerchants}" var="subMerchantData">
														<c:if
															test="${subMerchantData.status == 'Active' || subMerchantData.status == 'In-Active'|| subMerchantData.status == 'Self-Registered' }">
															<button
																onclick="javascript:editSubMerchant('${subMerchantData.id }')"
																title="View Sub Merchant"
																class="table-actionicon-margin pull-right">
																<spring:message
																	code="search-sub-merchant.lable.viewsubmerchant" />
															</button>
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise></c:otherwise>
											</c:choose>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="12" style="color: red;"><spring:message
													code="search-sub-merchant.lable.nomerchantsfound" /></td>
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
																href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.XLS_FILE_FORMAT%>',${totalRecords})">
																<button type="button" class="btn btn-default">
																	<img src="../images/excel.png">
																</button>
															</a> <a
																href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.PDF_FILE_FORMAT%>',${totalRecords})">
																<button type="button" class="btn btn-default">
																	<img src="../images/PDF.png">
																</button>
															</a> <input type="checkbox" class="autoCheck check"
																id="totalRecordsDownload">
															<spring:message
																code="search-sub-merchant.lable.downloadall" />
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
			<jsp:include page="footer.jsp"></jsp:include>
		</div>
		<!--Container block End -->
	</div>
	<div id="merchantPopupDiv" class="locatioin-list-popup">
		<span class="glyphicon glyphicon-remove"
			onclick="closePopup();clearPopupDesc();"></span>
		<h2><spring:message code="merchant.label.ChangeStatus"/></h2>
		<form:form action="subMerchantActivationSuspention" name="merchantActivationSuspentionForm" method="post">
			<input type="hidden" id="suspendActiveId" name="subMerchantId" /> <input
				type="hidden" id="suspendActiveStatus" name="subMerchantStatus" /> 
				<input type="hidden" name="CSRFToken" value="${tokenval}">
				<label><span class="requiredFiled">*</span> <spring:message code="merchant.label.Reason"/> </label>
			<textarea id="reason" name="reason" maxlength="<%= StatusConstants.REASON %>"
				onblur="validatePopupDesc();clientValidation('reason', 'reason','popDescError_div')"></textarea>
			<div class="discriptionErrorMsg">
				<span class="red-error" id="popDescError_div">&nbsp;</span>
			</div>
			<!--Panel Action Button Start -->
			<div class="col-sm-12 form-action-buttons">
				<div class="col-sm-12">
					<input type="submit" class="form-control button pull-right"
						value="<spring:message code="common.label.submit"/>" onclick="return validatePopupDesc();">
				</div>
			</div>
		</form:form>
		<!--Panel Action Button End -->
	</div>
	<!--Body Wrapper block End -->

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/utils.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/sortable.js"></script>
	<script src="../js/merchant.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/messages.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
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
		/* Common Navigation Include Start */
		$(function() {
			//$("#main-navigation").load("main-navigation.html");
		});
		/* function highlightMainContent() {
			$("#navListId2").addClass("active-background");
		} */

		$(document).ready(function() {
			$('#merchantPopupDiv').popup({
				blur : false
			});
			/* var flag = $
			{
				flag
			}
			;
			if (flag == true) {
				$('#search-results-tableID').show();
			} else {
				$('#search-results-tableID').hide();
			} */

			highlightMainContent('navListId9');

			/*  $("#search-result-table").hide(); */
		});
		function closePopup() {
			$('#merchantPopupDiv').popup("hide");
			$('#deletePopup').popup("hide");
		}
		function openPopup() {
			$('#merchantPopupDiv').popup("show");
		}
		/* Common Navigation Include End */
	</script>
</body>
</html>