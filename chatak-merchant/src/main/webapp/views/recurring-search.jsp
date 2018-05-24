<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.chatak.pg.util.Constants" %>
<html lang="en">
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Chatak Acquirer Merchant</title>
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
<body>
	<!--Body Wrapper block Start -->	
    <div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			
			<!--Header Block Start --> 
				<jsp:include page="header.jsp"></jsp:include>
			<!-- Header Block End -->
			<!--Navigation Block Start -->
			<nav class="col-sm-12 nav-bar" id="main-navigation">
				<%-- <jsp:include page="main-navigation.jsp"></jsp:include> --%>
				<%@include file="navigation-panel.jsp"%>
			</nav>
			<!--Navigation Block End -->  			
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">					
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="recurring-search.label.recurring"/></span>
						<span class="glyphicon glyphicon-play icon-font-size"></span>
						<span class="breadcrumb-text"><spring:message code="recurring-search.label.search"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<c:if test="${fn:contains(existingFeatures,recurringEdit) || fn:contains(existingFeatures,recurringDelete)}">
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="recurring-search.label.search"/> </a>					
					</div>					
					</c:if>
					<c:if test="${fn:contains(existingFeatures,recurringCreate)}">
					<div class="tab-header-container">
						<a href="recurring-create"><spring:message code="recurring-search.label.create"/></a>
						
					</div>					
					</c:if>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionErrorMsg" style="text-align:center;font-size:10pt">
										<span class="red-error">&nbsp;${error}</span>
										<span class="green-error">&nbsp;${success }</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
									<form action="getRecurrings" name="paginationForm" method="post">
										<input type="hidden" id="pageNumberId" name="pageNumber" /> 
										<input type="hidden" id="totalRecordsId" name="totalRecords" />
									</form>
									
									<form action="deleteRecurringCustomer" name="deleteRecurringForm" method="post">
										<input type="hidden" name="getCustInfoId" id="getCustInfoId" />
									</form>
									<form action="get-recurring-report" name="downloadReport" method="post">
										<input type="hidden" id="downloadPageNumberId"name="downLoadPageNumber" /> 
										<input type="hidden" id="downloadTypeId" name="downloadType" />
										<input type="hidden" id="totalRecords" name="totalRecords"/>
										<input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
									</form>
								<form action="editRecurring" name="editRecurringForm" method="post">
									<input type="hidden" id="getRecurringCustInfoId" name="getRecurringCustInfoId" />
								</form>
									
									<form action="recurring-search" name="resubmitForm" method="get"></form>	
								<form:form action="recurringSearch" commandName="recurring" name="recurring" method="post">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">												
												<fieldset class="col-sm-3"> 
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.customerid"/></label>
													<form:input path="customerId" cssClass="form-control" maxlength="50"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div> 
												</fieldset>
												<fieldset class="col-sm-3"> 
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.firstname"/></label>
													<form:input path="firstName" cssClass="form-control" maxlength="250"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div> 
												</fieldset>
												<fieldset class="col-sm-3"> 
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.lastname"/></label>
													<form:input path="lastName" cssClass="form-control" maxlength="250"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div> 
												</fieldset>
												<fieldset class="col-sm-3"> 
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.mobilephone"/></label>
													<form:input id="mobileNumber" path="mobileNumber" cssClass="form-control" maxlength="10" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div> 
												</fieldset>
												<fieldset class="col-sm-3"> 
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.emailid"/></label>
													<form:input path="emailId" cssClass="form-control" maxlength="50"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span> 
													</div> 
												</fieldset>	
												<fieldset class="col-sm-3"> 
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.city"/></label>
													<form:input path="city" cssClass="form-control" maxlength="20"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div> 
												</fieldset>	
												<fieldset class="col-sm-3"> 
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="recurring-search.label.company"/></label>
													<form:input path="businessName" cssClass="form-control" maxlength="10"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div> 
												</fieldset>								
												<fieldset class="col-sm-3"> 
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="dash-board.label.status"/></label>
														<form:select path="status" cssClass="form-control">
															<form:option value="">..:<spring:message code="sub-merchant-create.label.select"/>:..</form:option>
															<form:option value="Active"><spring:message code="search-sub-merchant.lable.active"/></form:option>
															<form:option value="In Active"><spring:message code="recurring-search.label.inactive"/></form:option>
														</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div> 
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="search-sub-merchant.lable.recordsperpage"/></label>
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
												<input type="submit" class="form-control button pull-right" value="<spring:message code="recurring-search.label.search"></spring:message>" id="search-button">
												<input type="button" class="form-control button pull-right" value="<spring:message code="search-sub-merchant.lable.reset"></spring:message>" onclick="resetAll()">
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
					<c:if test="${flagStatus eq 'True' }">
					<div class="search-results-table" id="search-result-table">
						<table class="table table-striped table-bordered table-condensed" style="margin: 1px;">
							<!-- Search Table Header Start -->
							<tr>
								<td colspan="8" class="search-table-header-column">
									<span class="glyphicon glyphicon-search search-table-icon-text"></span>									
									<span><spring:message code="recurring-search.label.search"/></span>
									<span class="pull-right"><spring:message code="search-sub-merchant.lable.totalcount"/> : <label id="totalCount">${totalRecords}</label></span>
								</td>
							</tr>
							</table>
							<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
							<!-- Search Table Header End -->
							<!-- Search Table Content Start -->
							<tr>
								<th style="width: 130px;"><spring:message code="recurring-search.label.customerid"/></th>
								<th style="width: 100px;"><spring:message code="recurring-search.label.firstname"/></th>
								<th style="width: 100px;"><spring:message code="recurring-search.label.lastname"/></th>
								<th style="width: 120px;"><spring:message code="recurring-search.label.mobilephone"/></th>
								<th style="width: 180px;"><spring:message code="recurring-search.label.emailid"/></th>
								<th style="width: 100px;"><spring:message code="recurring-search.label.city"/></th>
								<th style="width: 100px;"><spring:message code="recurring-search.label.company"/></th>
								<th style="width: 60px;"><spring:message code="dash-board.label.status"/></th>
								<th style="width: 60px;" class="sorter-false tablesorter-header tablesorter-headerUnSorted"><spring:message code="search-sub-merchant.lable.action"/></th>
							</tr>
							</thead>
							<c:choose>
								<c:when test="${!(fn:length(recurringList) eq 0) }">
									<c:forEach items="${recurringList }" var="recurring">
										<tr>
											<td><div class="feeDescDiv tbl-text-align-left">${recurring.customerId }</div></td>
											<td><div class="feeDescDiv tbl-text-align-left">${recurring.firstName }</div></td>
											<td><div class="feeDescDiv tbl-text-align-left">${recurring.lastName }</div></td>
											<td>${recurring.mobileNumber }</td>
											<td><div class="feeDescDiv">${recurring.emailId }</div></td>
											<td><div class="feeDescDiv tbl-text-align-left">${recurring.city }</div></td>
											<td><div class="feeDescDiv tbl-text-align-left">${recurring.businessName }</div></td>
											<td class="tbl-text-align-left ">${recurring.status }</td>
											<td>
												<c:choose>
													<c:when test="${recurring.status eq 'Active' }">
													<c:if test="${fn:contains(existingFeatures,recurringEdit)}">
														<a href="javascript:editRecurring('${recurring.recurringCustInfoId}')" title="Edit" class="table-actionicon-margin"><span class="glyphicon glyphicon-pencil"></span></a>
														</c:if>
														<c:if test="${fn:contains(existingFeatures,recurringDelete)}"> 
														<a href="javascript:confirmDeleteRecurring('${recurring.recurringCustInfoId}')" title="Delete"><span class="glyphicon glyphicon-trash"></span></a>
														</c:if>
													</c:when>
													<c:when test="${recurring.status eq 'Inactive' }">
													<c:if test="${fn:contains(existingFeatures,recurringEdit)}">
														<a href="javascript:editRecurring('${recurring.recurringCustInfoId}')" title="Edit" class="table-actionicon-margin"><span class="glyphicon glyphicon-pencil"></span></a>
														</c:if>
														</c:when>
													<c:otherwise>
													
													</c:otherwise>
												</c:choose>
											</td>
									</c:forEach>								
								</c:when>	
								<c:otherwise>
										<tr>
											<td colspan="8" style="color: red;"><spring:message code="recurring-search.label.norecordfound"/></td><td></td>
										</tr>
								</c:otherwise>						
							</c:choose>
							</table>
							<table class="table table-striped table-bordered table-condensed">
							<c:if test="${ !(fn:length(recurringList) eq 0)}">
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
																<img src="../images/PDF.png" >
															</button>
														</a>
														
															<input type="checkbox" class="autoCheck check" id="totalRecordsDownload">
															<spring:message code="search-sub-merchant.lable.downloadall"/>
													
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
			<jsp:include page="footer.jsp"></jsp:include>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->	
    
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="../js/common-lib.js"></script>
    <script src="../js/backbutton.js"></script>
    <script src="../js/jquery.min.js"></script>
    <script src="../js/sorting.js"></script>
	<script src="../js/tablesorter.js"></script>
	<script src="../js/tablesorter.widgets.js"></script>
	<script src="../js/jquery.maskedinput.js"></script>
    <script src="../js/recurring.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../js/bootstrap.min.js"></script> <script src="../js/utils.js"></script>	
    <script src="../js/jquery.cookie.js"></script>
	 <script src="../js/messages.js"></script>
	<script>
	var sortProperty = "${sortProperty}" + "";

	if ($.trim(sortProperty).length > 0) {
		var sortPropArr = sortProperty.split(",");
		$('#serviceResults').tablesorter(
				{
					sortList : [ [ parseInt(sortPropArr[0]),
							parseInt(sortPropArr[1]) ] ]
				});
	}

	/* Common Navigation Include End */
	$(document).ready( function() {
	highlightMainContent('navListId5');
	
	/*  $("#search-result-table").hide(); */
	});
    
	 $("#mobileNumber").focusin(function(){
		 $("#mobileNumber").mask("<%=Constants.PHONE_NUMBER_FORMAT%>");
		});
		
     $("#mobileNumber").focusout(function(){
    	 
			 if($("#mobileNumber").val().replace(/-|_/g,"").length==0){
				 $("#mobileNumber").attr("placeholder", "");
			 }
			 else {
				 $("#mobileNumber").unmask(); 
			 } 
		});
	/* 	$("#search-button").click(function () {
			$("#search-result-table").show();
		}); */
		/* Common Navigation Include Start */
		/* $(function(){
			$( "#main-navigation" ).load( "main-navigation.html" );
		});
		function highlightMainContent(){
			$( "#navListId5" ).addClass( "active-background" );
		}	 */
	</script>
	<script src="../js/backbutton.js"></script>	
  </body>  
</html>