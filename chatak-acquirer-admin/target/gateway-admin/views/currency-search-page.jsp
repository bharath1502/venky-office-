<!DOCTYPE html>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.Calendar"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
						<span class="breadcrumb-text"><spring:message
								code="setup.label.setup" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message
								code="currency-search-page.label.currency" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message
								code="common.label.search" /></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<c:if
						test="${fn:contains(existingFeatures,currencyEdit)||fn:contains(existingFeatures,currencyDelete)||fn:contains(existingFeatures,currencyCreate)||fn:contains(existingFeatures,currencyView)}">
						<div class="tab-header-container-first active-background">
							<a href="#"><spring:message code="common.label.search" /></a>
						</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,currencyCreate)}">
						<div class="tab-header-container">
							<a href="currency-create-page"><spring:message
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
									<div class="descriptionMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error">&nbsp;${error }</span> 
										<span id="sucessDiv" class="green-error">&nbsp;${sucess }</span>
									</div>
								</div>
								<form:form action="edit-currency" name="editCurrencyForm"
									method="post">
									<input type="hidden" id="getId" name="getId" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								
								<form:form action="currency-view" name="viewCurrencyForm"
									method="post">
									<input type="hidden" id="getViewId" name="getId" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								
								<form:form action="deleteCurrency" name="deleteCurrencyForm"
									method="post">
									<input type="hidden" id="getDeleteId" name="getDeleteId" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								
								<form:form action="getCurrencyInfo" name="paginationForm" method="post">
									<input type="hidden" id="pageNumberId" name="pageNumber" /> 
									<input type="hidden" id="totalRecordsId" name="totalRecords" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								
								<form:form action="get-currency-report" name="downloadReport" method="post">
									<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" /> 
									<input type="hidden" id="downloadTypeId" name="downloadType" />
									<input type="hidden" id="totalRecords" name="totalRecords" />
									<input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								
								<!-- Page Form Start -->
								<form:form action="currency-search-action"
									modelAttribute="currencyDTO" method="post">
									<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="currency-search-page.label.currencyname" /></label>
													<form:input cssClass="form-control" path="currencyName"
														id="currencyName" onblur="clientValidation('currencyName','firstlast_name_not_mandatory','currencyName_Error');" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="currencyName_Error" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="currency-search-page.label.currencyCodeAlpha" /></label>
													<form:input cssClass="form-control" onblur="return validateCurrencyAlpha()"
														maxlength="3" path="currencyCodeAlpha" id="currencyCodeAlpha" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="currencyCodeAlphaEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="currency-search-page.label.currencycodenumeric" /></label>
													<form:input cssClass="form-control" onblur="return validateCurrencyNumeric()"
														maxlength="3" path="currencyCodeNumeric" id="currencyCodeNumeric" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="currencyCodeNumericEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="currency-create-page.label.currencyexponent" /></label>
													<form:input cssClass="form-control" onkeypress="return isNumberKey(event);" maxlength="1" path="currencyExponent"
														id="currencyExponent" onblur="clientValidation('currencyExponent','amount_False','currencyExponentEr');"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="currencyExponentEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<%-- <fieldset class="col-sm-3">
													<label><spring:message code="currency-search-page.label.currencysymbol"/></label>
													<form:input cssClass="form-control" path="currencySymbol" id="currencySymbol" />
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset> --%>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.status" /></label>
													<form:select cssClass="form-control" path="status"
														id="status">
														<form:option value="">..:<spring:message
																code="reports.option.select" />:..</form:option>
														<form:option value="0">
															<spring:message code="common.label.active" />
														</form:option>
														<form:option value="2">
															<spring:message code="common.label.inactive" />
														</form:option>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="statusEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>

												<%-- <fieldset class="col-sm-3" style="margin-top: 20px;">
													<label>Records Per Page</label>
													<form:select cssClass="form-control" path="recordsPerPage"
														id="recordsPerPage">
														<form:option value="10">10</form:option>
														<form:option value="20">20</form:option>
														<form:option value="50">50</form:option>
														<form:option value="100">100</form:option>
														<form:option value="250">250</form:option>
														<form:option value="500">500</form:option>
														<form:option value="1000">1000</form:option>
													</form:select>
												</fieldset> --%>
											</div>
										</div>
										<!--Panel Action Button Start -->
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit"
													value='<spring:message code="common.label.search"/>'
														onclick="return validateSearchData()"
													class="form-control button pull-right"> <input
													type="reset" class="form-control button pull-right"
													value='<spring:message code="common.label.reset"/>'
													onclick="goToCurrencySearch()">
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
									<span><spring:message code="common.label.search" /></span> <span class="pull-right">Total
										Count : <label id="totalRecords">${totalRecords}</label>
								</span></td>
							</tr>
						</table>
						<!-- Search Table Header End -->
						<!-- Search Table Content Start -->
						<table id="serviceResults"
							class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
								<tr>
									<th style="width: 140px;"><spring:message
											code="currency-search-page.label.currencyname" /></th>
									<th style="width: 140px;"><spring:message
											code="currency-search-page.label.currencyCodeAlpha" /></th>
									<th style="width: 120px;"><spring:message
											code="currency-search-page.label.currencycodenumeric" /></th>
											<th style="width: 120px;"><spring:message
											code="currency-create-page.label.currencyexponent" /></th>
									<th style="width: 100px;"><spring:message
											code="common.label.status" /></th>
									<th style="width: 70px;"
										class="sorter-false tablesorter-header tablesorter-headerUnSorted"><spring:message
											code="common.label.action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${!(fn:length(currencyDTOlist) eq 0) }">
										<c:forEach items="${currencyDTOlist}" var="currencyData">
											<tr>
												<td><div class="feeDescDiv tbl-text-align-left">${currencyData.currencyName}</div></td>
												<td><div class="feeDescDiv tbl-text-align-right">${currencyData.currencyCodeAlpha}</div></td>
												<td><div class="tbl-text-align-right">${currencyData.currencyCodeNumeric}</div></td>
												<td><div class="tbl-text-align-right">${currencyData.currencyExponent}</div></td>
												<td class="tbl-text-align-left">${currencyData.status}</td>
												<td style="white-space: nowrap;">
												<c:if test="${fn:contains(existingFeatures,currencyView)}">
													<a href="javascript:viewCurrency('${currencyData.id}')">
														<img src="../images/eyeimage.png" alt="<spring:message code="common.label.view"/>"
															title="<spring:message code="common.label.view"/>"></img> <span
															class="glyphicon glyphicon-eye"></span>
													</a>
												</c:if>
												<c:if test="${currencyData.status == 'Active'}">
												      <a href="javascript:changeStatus('${currencyData.id}','Suspended','Suspended','currencyPopupDiv')" title="Suspend">
													  <img src="../images/active.png" alt="Suspend" title="Suspend"></img></a>
													  <c:if test="${fn:contains(existingFeatures,currencyEdit)}">
													  	<a href="javascript:editCurrency('${currencyData.id}')" title="Edit" class="table-actionicon-margin">
													  	<span class="glyphicon glyphicon-pencil"></span></a>
													  </c:if>	
													  <c:if test="${fn:contains(existingFeatures,currencyDelete)}">
													  	<a href="javascript:confirmDeleteCurrency('${currencyData.id}','Currency')">
													  	<span class="glyphicon glyphicon-trash" title="Delete"></span></a>
													  </c:if>
												</c:if>
												<c:if test="${currencyData.status == 'Suspended'}">
													  <a href="javascript:changeStatus('${currencyData.id}','Active','Active','currencyPopupDiv')" title="Active">
												      <img alt="Active" src="../images/deactive.png" title="Activate"></img></a>	
												</c:if>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="10" style="color: red;"><spring:message
													code="commission-program-search.label.norecordsfound" /></td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
							<table class="table table-striped table-bordered table-condensed">
							<c:if test="${ !(fn:length(currencyDTOlist) eq 0)}">
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
	<div id="currencyPopupDiv" class="locatioin-list-popup">
		<span class="glyphicon glyphicon-remove"
			onclick="closePopup();clearPopupDesc();"></span>
		<h2><spring:message code="prepaid-admin-programmanager-search-label.ChangeStatus"/></h2>
		<form:form action="currencyActivationSuspention" name="currencyActivationSuspentionForm" method="post">
			<input type="hidden" id="suspendActiveId" name="currencyId" /> <input
				type="hidden" id="suspendActiveStatus" name="currencyStatus" /> 
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
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/sortable.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/messages.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/currency.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/validation.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
					$('#currencyPopupDiv').popup({
						blur : false
					});
					$("#navListId2").addClass("active-background");

					$("#parentMerchantCode").val(
							"${merchantAccountSearchDto.parentMerchantCode}");
				});
		
		function closePopup() {
			$('#currencyPopupDiv').popup("hide");
			$('#deletePopup').popup("hide");
		}
		function openPopup() {
			$('#currencyPopupDiv').popup("show");
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
