<!DOCTYPE html>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page  import="java.util.Calendar"%>
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
						<span class="breadcrumb-text"><spring:message code="setup.label.setup"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="payment.label.paymentscheme"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="common.label.search"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<c:if test="${fn:contains(existingFeatures,paymentSchemeEdit)||fn:contains(existingFeatures,paymentSchemeCreate)||fn:contains(existingFeatures,paymentSchemeView)}">
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="common.label.search"/></a>
					</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,paymentSchemeCreate)}">
					<div class="tab-header-container">
						<a href="payment-scheme-create"><spring:message code="common.label.create"/></a>
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
								<form:form action="getPaymentSchemeInfo" name="paginationForm" method="post">
									<input type="hidden" id="pageNumberId" name="pageNumber" /> 
									<input type="hidden" id="totalRecordsId" name="totalRecords" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								<form:form action="getPaymentShemeReport" name="downloadReport" method="post">
									<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" /> 
									<input type="hidden" id="downloadTypeId" name="downloadType" />
									<input type="hidden" id="totalRecords" name="totalRecords" />
									<input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

								<form:form action="edit-Payment-Scheme" name="editPaymentSchemeForm"
									method="post">
									<input type="hidden" id="getpaymentschemeId"
										name="getpaymentschemeId" />
								 <input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								
								<form:form action="view-payment-scheme" name="viewPaymentSchemeForm"
									method="post">
									<input type="hidden" id="getViewPaymentSchemeId"
										name="getpaymentschemeId" />
									 <input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

								<!-- Page Form Start -->
								<form:form action="payment-scheme-search-action"
									modelAttribute="paymentScheme" method="post">
								 <input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="payment.label.paymentname"/></label>
													<form:input cssClass="form-control"
														path="paymentSchemeName" id="paymentSchemeName" />
												</fieldset>
												<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="payment.label.typeofcard"/></label>
														<form:select cssClass="form-control" path="typeOfCard"
															id="typeOfCard">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<form:option value="Contact"><spring:message code="payment.label.contact"/></form:option>
															<form:option value="Contactless"><spring:message code="payment.label.contactless"/></form:option>
															<form:option value="Magnet Stripe"><spring:message code="payment.label.magnetstripe"/></form:option>
														</form:select>
														<!-- <div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="typeOfCardEr" class="red-error">&nbsp;</span>
														</div> -->
													</fieldset>											
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="payment.label.rid"/></label>
													<form:input cssClass="form-control" path="rid" id="rid" />
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.status"/></label>
													<form:select cssClass="form-control" path="status"
														id="status">
														<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<form:option value="0"><spring:message code="common.label.active"/></form:option>
															<form:option value="2"><spring:message code="common.label.inactive"/></form:option>
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
													onclick="return trimUserData()"> <input type="reset"
													class="form-control button pull-right" value='<spring:message code="common.label.reset"/>'
													onclick="goToPaymentSchemeSearch()">
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
									<span><spring:message code="common.label.search"/></span> <span class="pull-right">Total
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
									<th style="width: 140px;"><spring:message code="payment.label.paymentname"/></th>
									<th style="width: 140px;"><spring:message code="payment.label.contactname"/></th>
									<th style="width: 120px;"><spring:message code="payment.label.contactemail"/></th>
									<th style="width: 140px;"><spring:message code="payment.label.contactphone"/></th>
									<th style="width: 140px;"><spring:message code="payment.label.rid"/></th>
									<th style="width: 100px;"><spring:message code="payment.label.typeofcard"/></th>
									<th style="width: 80px;"><spring:message code="common.label.status"/></th>
									<th style="width: 90px;"
										class="sorter-false tablesorter-header tablesorter-headerUnSorted"><spring:message code="common.label.action"/></th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${!(fn:length(paymentSchemeSearchData) eq 0) }">
										<c:forEach items="${paymentSchemeSearchData}"
											var="paymentData">
											<tr>
												<td><div class="feeDescDiv tbl-text-align-left">${paymentData.paymentSchemeName}</div></td>
												<td><div class="feeDescDiv tbl-text-align-left">${paymentData.contactName}</div></td>
												<td><div class="feeDescDiv">${paymentData.contactEmail}</div></td>
												<td class="tbl-text-align-left">${paymentData.contactPhone}</td>
												<td class="tbl-text-align-right">${paymentData.rid}</td>
												<td class="tbl-text-align-left">${paymentData.typeOfCard}</td>
												<td class="tbl-text-align-left">
												<c:if test="${paymentData.status == '0'}"> Active </c:if>
												<c:if test="${paymentData.status == '2'}"> Suspended </c:if>
												</td>
												<td style="white-space: nowrap;">
													<c:if test="${fn:contains(existingFeatures,paymentSchemeView)}">
													<a href="javascript:viewPaymentScheme('${paymentData.id }')">
														<img src="../images/eyeimage.png" alt="<spring:message code="common.label.view"/>" title="<spring:message code="common.label.view"/>"></img>
															<span class="glyphicon glyphicon-eye"></span></a>
													</c:if>
													<c:if test="${paymentData.status == '0'}">
											        <a href="javascript:changeStatus('${paymentData.id}','Suspended','Suspended','paymentSchemePopupDiv')" title="Suspend">
													<img src="../images/active.png" alt="Suspend" title="Suspend"></img></a>
													<c:if test="${fn:contains(existingFeatures,paymentSchemeEdit)}">
														<a href="javascript:editPaymentScheme('${paymentData.id }')" title="Edit" class="table-actionicon-margin">
														<span class="glyphicon glyphicon-pencil"></span></a>
											        </c:if>
											        </c:if>
													<c:if test="${paymentData.status == '2'}">
											        <a href="javascript:changeStatus('${paymentData.id}','Active','Active','paymentSchemePopupDiv')" title="Active">
												    <img alt="Active" src="../images/deactive.png" title="Activate"></img></a>
											        </c:if>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="10" style="color: red;"><spring:message code="payment.label.nopaymentschemefound"/></td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
						<table class="table table-striped table-bordered table-condensed">
							<c:if test="${ !(fn:length(paymentSchemeSearchData) eq 0)}">
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
			</article>
			<!--Article Block End-->
			<div class="footer-container">
				<jsp:include page="footer.jsp"></jsp:include>
			</div>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->

	<div id="my_popup" class="locatioin-list-popup">
		<span class="glyphicon glyphicon-remove" onclick="closePopup()"></span>
		<h2>Change Status</h2>
		<form:form action="change-Account-Status" name="changeStatusForm"
			method="post">
			<input type="hidden" id="editAccountId" name="accountId" /> <input
				type="hidden" id="editAccountStatus" name="accountStatus" /> <input
				type="hidden" id="editMerchantType" name="merchantType" /> 
			<input type="hidden" name="CSRFToken" value="${tokenval}">
				<label data-toggle="tooltip" data-placement="top" title=""><span
				class="requiredFiled">*</span> Reason </label>
			<textarea id="reason" name="reason" maxlength="500"
				onblur="validatePopupDesc();clientValidation('reason', 'reason','popDescError_div')"></textarea>
			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
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
	</div>
	
	<div id="paymentSchemePopupDiv" class="locatioin-list-popup">
		<span class="glyphicon glyphicon-remove"
			onclick="closePopup();clearPopupDesc();"></span>
		<h2><spring:message code="prepaid-admin-programmanager-search-label.ChangeStatus"/></h2>
		<form:form action="paymentSchemeActivationSuspention" name="paymentSchemeActivationSuspentionForm" method="post">
			<input type="hidden" id="suspendActiveId" name="paymentSchemeId" /> <input
				type="hidden" id="suspendActiveStatus" name="paymentSchemeStatus" /> 
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
	<script type="text/javascript" src="../js/payment-scheme.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/validation.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			        $('#paymentSchemePopupDiv').popup({
				        blur : false
			        });
					$("#navListId2").addClass("active-background");

					$("#parentMerchantCode").val("${merchantAccountSearchDto.parentMerchantCode}");
	    });

		var sortProperty = "${sortProperty}" + "";

		if ($.trim(sortProperty).length > 0) {
			var sortPropArr = sortProperty.split(",");
			$('#serviceResults').tablesorter(
					{
						sortList : [ [ parseInt(sortPropArr[0]),
								parseInt(sortPropArr[1]) ] ]
					});
		}
		
		function closePopup() {
			$('#paymentSchemePopupDiv').popup("hide");
		}
		function openPopup() {
			$('#paymentSchemePopupDiv').popup("show");
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
