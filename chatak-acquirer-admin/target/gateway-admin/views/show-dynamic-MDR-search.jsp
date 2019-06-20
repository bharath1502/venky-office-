<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ page import="com.chatak.acquirer.admin.constants.StatusConstants"%>

<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Chatak Acquirer Admin</title>
<!-- Bootstrap -->
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
						<span class="breadcrumb-text"><spring:message code="show-dynamic-MDR-search.label.setup"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="show-dynamic-MDR-search.label.dynamicmdr"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="show-dynamic-MDR-search.label.search"/></span>
					</div>

					<form:form action="show-dynamic-MDR-edit" name="editMDR" method="post">
						<input type="hidden" id="getMDRId" name="getMDRId" />
						<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>
					<form:form action="dynamic-MDR-Pagination" name="paginationForm" method="post">
						<input type="hidden" id="pageNumberId" name="pageNumber" /> 
						<input type="hidden" id="totalRecordsId" name="totalRecords" />
						<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>
					
					<form:form action="download-dynamic-mdr-report" name="downloadReport" method="post">
						<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" /> 
						<input type="hidden" id="downloadTypeId" name="downloadType" />
						<input type="hidden" id="totalRecords" name="totalRecords" />
						<input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
					    <input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>
					
					<%-- <form action="show-view-MDR-Dynamic-Bin" name="viewResellerForm" method="post">
						<input type="hidden" id="resellerViewresellerId" name="resellerViewresellerId" />
					</form> --%>

					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<c:if test="${fn:contains(existingFeatures,dynamicMDREdit)}">
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="show-dynamic-MDR-search.label.search"/></a>
					</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,dynamicMDRCreate)}">
					<div class="tab-header-container">
						<a href="show-dynamic-MDR-create"><spring:message code="show-dynamic-MDR-search.label.create"/></a>
					</div>
					</c:if>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error">${error}</span> <span
											class="green-error">${sucess}</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="process-dynamic-mdr-search" modelAttribute="dynamicMDRDTO" method="post">
								 <input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-dynamic-MDR-search.label.binnumber"/></label>
													<form:input path="binNumber" id="binNumber"
														cssClass="form-control" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-dynamic-MDR-search.label.paymentscheme"/></label>
													<form:select path="paymentSchemeName" id="paymentSchemeName"
														cssClass="form-control">
														<form:option value=""><spring:message code="show-dynamic-MDR-search.label.select"/></form:option>
															<c:forEach items="${searchPaymentRequestList}" var="paymentSchemes">
															<form:option value="${paymentSchemes.paymentSchemeName}">${paymentSchemes.paymentSchemeName}</form:option>
														</c:forEach>
														</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-dynamic-MDR-search.label.bankname"/></label>
													<form:select path="bankName" id="bankName"
														cssClass="form-control">
														<form:option value=""><spring:message code="show-dynamic-MDR-search.label.select"/></form:option>
															<c:forEach items="${bankResponse}" var="bank">
															<form:option value="${bank.bankName}">${bank.bankName}</form:option>
														</c:forEach>
														</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-dynamic-MDR-search.label.accounttype"/></label>
													<form:select path="accountType" id="accountType"
														cssClass="form-control">
														<form:option value=""><spring:message code="show-dynamic-MDR-search.label.select"/></form:option>
															<form:option value="credit"><spring:message code="show-dynamic-MDR-search.label.credit"/></form:option>
															<form:option value="debit"><spring:message code="show-dynamic-MDR-search.label.debit"/></form:option>
														</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>

												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-dynamic-MDR-search.label.producttype"/></label>
													<form:select path="productType" id="productType"
														cssClass="form-control" onblur="validateProduct()">
														<form:option value=""><spring:message code="show-dynamic-MDR-search.label.select"/></form:option>
															<form:option value="classic"><spring:message code="show-dynamic-MDR-search.label.classic"/></form:option>
															<form:option value="gold"><spring:message code="show-dynamic-MDR-search.label.gold"/></form:option>
															<form:option value="platinum"><spring:message code="show-dynamic-MDR-search.label.platinum"/></form:option>
															<form:option value="signature"><spring:message code="show-dynamic-MDR-search.label.signature"/></form:option>
														</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-dynamic-MDR-search.label.transactiontype"/></label>
													<form:select path="transactionType" id="transactionType"
														cssClass="form-control">
														<form:option value=""><spring:message code="show-dynamic-MDR-search.label.select"/></form:option>
															<form:option value="onus"><spring:message code="show-dynamic-MDR-search.label.onus"/></form:option>
															<form:option value="offus"><spring:message code="show-dynamic-MDR-search.label.offus"/></form:option>
														</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3 marginML-25">
												<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-dynamic-MDR-search.label.slabfee"/></label> <input type="text"
													name="slab" id="slab" class="form-control "  onblur="this.value=this.value.trim();validateSlab()"/>
												<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="slabEr" class="red-error">&nbsp;</span>
														</div>
											</fieldset>
											<fieldset class="col-sm-4">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-dynamic-MDR-search.label.recordsperpage"/></label>
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
										<div
											class="col-sm-12 form-action-buttons pull-right zero-padding">
											<div class="col-sm-6 zero-padding"></div>
											<div class="col-sm-6 zero-padding">
												<input type="submit" class="form-control button pull-right"
													value='<spring:message code ="show-dynamic-MDR-search.label.searchbutton"/>'> <input type="button"
													class="form-control button pull-right" value='<spring:message code="show-dynamic-MDR-search.label.resetbutton"/>'
													onclick="resetDynamicMDR()">
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
					<div class="search-results-table">
						<table class="table table-striped table-bordered table-condensed" style="margin: 1px;">
							<!-- Search Table Header Start -->
							<tr>
								<td colspan="8" class="search-table-header-column"><span
									class="glyphicon glyphicon-search search-table-icon-text"></span>
									<span><spring:message code="show-dynamic-MDR-search.label.search"/></span></td>
							</tr>
							</table>
							<!-- Search Table Header End -->
							<!-- Search Table Content Start -->
							<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
							<tr>
								<th style="width: 120px;"><spring:message code="show-dynamic-MDR-search.label.binnumber"/></th>
								<th style="width: 180px;"><spring:message code="show-dynamic-MDR-search.label.paymentscheme"/></th>
								<th style="width: 180px;"><spring:message code="show-dynamic-MDR-search.label.bankname"/></th>
								<th style="width: 150px;"><spring:message code="show-dynamic-MDR-search.label.accounttype"/></th>
								<th style="width: 150px;"><spring:message code="show-dynamic-MDR-search.label.producttype"/></th>
								<th style="width: 150px;"><spring:message code="show-dynamic-MDR-search.label.transactiontype"/></th>
								<th style="width: 150px;"><spring:message code="show-dynamic-MDR-search.label.slab"/></th>
								<th style="width: 150px;"><spring:message code="show-dynamic-MDR-search.label.action"/></th>
							</tr>
							</thead>
							<c:choose>
								<c:when test="${!(fn:length(dynamicMDRDTOlist) eq 0) }"> 
									<c:forEach items="${dynamicMDRDTOlist}" var="dynamicMDRDTOlist">
										<tr>
											<td class="tbl-text-align-left">${dynamicMDRDTOlist.binNumber}</td>
											<td class="tbl-text-align-left">${dynamicMDRDTOlist.paymentSchemeName}</td>
											<td><div class="feeDescDiv tbl-text-align-left">${dynamicMDRDTOlist.bankName}</div></td>
											<td class="tbl-text-align-left">${dynamicMDRDTOlist.accountType}</td>
											<td class="tbl-text-align-left">${dynamicMDRDTOlist.productType}</td>
											<td class="tbl-text-align-left">${dynamicMDRDTOlist.transactionType}</td>
											<td class="tbl-text-align-left">${dynamicMDRDTOlist.slab}</td>
											
											<td style="white-space:nowrap;">
													<%-- <a href="javascript:viewMerchantInfo('${dynamicMDRDTOlist.id }')"><img src="../images/eyeimage.png" alt="View" title="View"></img>
													<span class="glyphicon glyphicon-eye"></span></a> --%>
												<c:if test="${fn:contains(existingFeatures,dynamicMDREdit)}">
													<a href="javascript:editMDR('${dynamicMDRDTOlist.id }')" title="Edit" class="table-actionicon-margin">
													<span class="glyphicon glyphicon-pencil"></span></a>
													</c:if>
												
													<%-- <a href="javascript:confirmDeleteMDRBin('${dynamicMDRDTOlist.id }')">
													<span class="glyphicon glyphicon-trash"></span></a> --%>
												</td>
										</tr>
									</c:forEach>
								</c:when> 
								<c:otherwise>
									<tr>
										<td colspan="8" style="color: red;"><spring:message code="show-dynamic-MDR-search.label.norecordsfound"/></td>
									</tr>
								</c:otherwise>
							</c:choose>
							</table>
							<table class="table table-striped table-bordered table-condensed">
							<tr class="table-footer-main">
								<td colspan="10" class="search-table-header-column">
									<div class="col-sm-12">

										<c:if test="${!(fn:length(dynamicMDRDTOlist) eq 0) }">


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
													</div>
													<div>
														  <input type="checkbox" class="autoCheck check" id="totalRecordsDownload">
																<spring:message code="show-dynamic-MDR-search.label.downloadall"/> 
													</div>
												</div>
											</div>

											<div class="col-sm-9">
												<ul class="pagination custom-table-footer-pagination">
													<c:if test="${portalListPageNumber gt 1}">
														<li onclick="bindReqType();"><a href="javascript:getPortalOnPageWithRecords('1','${totalRecords}')"> &laquo;</a></li>
														<li onclick="bindReqType();"><a href="javascript:getPortalPrevPageWithRecords('${portalListPageNumber }','${totalRecords}')"> &lsaquo; </a></li>
													</c:if>

													<c:forEach var="page" begin="${beginPortalPage }" end="${endPortalPage}" step="1" varStatus="pagePoint">
														<c:if test="${portalListPageNumber == pagePoint.index}">
															<li class="${portalListPageNumber == pagePoint.index?'active':''}" onclick="bindReqType();">
																<a href="javascript:">${pagePoint.index}</a>
															</li>
														</c:if>
														<c:if test="${portalListPageNumber ne pagePoint.index}">
															<li class="" onclick="bindReqType();">
																<a href="javascript:getPortalOnPageWithRecords('${pagePoint.index }','${totalRecords}')">${pagePoint.index}</a>
															</li>
														</c:if>
													</c:forEach>

													<c:if test="${portalListPageNumber lt portalPages}">
														<li onclick="bindReqType();"><a href="javascript:getPortalNextPageWithRecords('${portalListPageNumber }','${totalRecords}')"> &rsaquo;</a></li>
														<li onclick="bindReqType();"><a href="javascript:getPortalOnPageWithRecords('${portalPages }','${totalRecords}')">&raquo; </a></li>
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

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/sortable.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/mdrdynamicbin.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		
		function resetDynamicMDR() {
			window.location.href = "show-dynamic-MDR-search?requestType="+$('#requestType').val();
		}

		$(document).ready(function() {
			$("#navListId2").addClass("active-background");
		});
		
		function bindReqType() {
			$('#paginationRequestType').val($('#requestType').val());
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