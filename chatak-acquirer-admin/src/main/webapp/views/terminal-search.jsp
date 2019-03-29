<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<html lang="en">
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Chatak Acquirer - Terminal Search</title>
    <!-- Bootstrap -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link href="../css/style.css" rel="stylesheet">
    <link href="../css/rome.css" rel="stylesheet">
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
						<span class="breadcrumb-text">Terminal</span>
						<span class="glyphicon glyphicon-play icon-font-size"></span>
						<span class="breadcrumb-text">Search</span>
					</div>
					<!-- Breadcrumb End -->
					<form:form action="getTerminals" name="paginationForm" method="post">
						<input type="hidden" id="pageNumberId" name="pageNumber" /> <input
							type="hidden" id="totalRecordsId" name="totalRecords" />
					    <input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>
					<form:form action="editTerminal" name="editTerminalForm" method="post">
						<input type="hidden" id="getTerminalId" name="getTerminalId" />
					    <input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error">&nbsp;${error }</span>
										<span class="green-error">&nbsp;${sucess }</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="searchTerminal" modelAttribute="terminal"  name="terminal" id="terminal">
								 <input type="hidden" name="CSRFToken" value="${tokenval}">	
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<!--Add Button Start -->
												<div class="col-sm-12 form-action-buttons">
													<div class="col-sm-5"></div>
													<div class="col-sm-7 add-button-div">
														<a href="terminal-create" class="form-control button-add pull-right"><span class="add-page-btn">+</span> Add Terminal</a>
													</div>	
												</div>
												<!--Add Button End -->
												<fieldset class="col-sm-4"> 
													<label data-toggle="tooltip" data-placement="top" title="">Merchant Code</label>
													<form:select cssClass="form-control" path="merchantId" id="merchantId" >
														<form:option value="-1">..:Select:..</form:option>
														<c:forEach items="${merchantOptions}" var="merchantOpt">
														<form:option value="${merchantOpt.value }">${merchantOpt.label }</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div> 
												</fieldset>
												<fieldset class="col-sm-4"> 
													<label data-toggle="tooltip" data-placement="top" title="">Terminal Code</label>
													<form:input cssClass="form-control" path="terminalCode" id="terminalCode" maxlength="16" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div> 
												</fieldset>
												<fieldset class="col-sm-4"> 
													<label data-toggle="tooltip" data-placement="top" title="">Status</label>
													<form:select cssClass="form-control" path="status" id="status" >
														<form:option value="-1">..:Select:..</form:option>
														<form:option value="0">Active</form:option>
														<form:option value="1">Pending</form:option>
														<form:option value="2">Suspended</form:option>
														<form:option value="3">Terminated</form:option>
														<form:option value="4">Hold</form:option>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div> 
												</fieldset>
												
											</div>
										</div>
										<div class="row">
											<div class="field-element-row">
												
												<fieldset class="col-sm-4"> 
													<label data-toggle="tooltip" data-placement="top" title="">Valid from</label>
													<div class="input-group">
														<form:input cssClass="form-control effectiveDate" path="validFromDate" id="fromDate" />
														<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
													</div>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="fromDateEr" class="red-error">&nbsp;</span>
													</div> 
												</fieldset>
												<fieldset class="col-sm-4"> 
													<label data-toggle="tooltip" data-placement="top" title="">Valid till</label>
													<div class="input-group">
														<form:input cssClass="form-control effectiveDate" path="validToDate" id="toDate" />
														<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
													</div>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="toDateEr" class="red-error">&nbsp;</span>
													</div> 
												</fieldset>
											</div>
										</div>
										<!--Panel Action Button Start -->
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit" class="form-control button pull-right" value="Search" onclick="return compareFromAndToDates()">
												<input type="button" class="form-control button pull-right" value="Reset" onclick="resetAll()">
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
						<table class="table table-striped table-bordered table-condensed" style="margin-bottom: 0px;">
							<!-- Search Table Header Start -->
							<tr>
								<td colspan="8" class="search-table-header-column">
									<span class="glyphicon glyphicon-search search-table-icon-text"></span>									
									<span>Search</span>
								</td>
							</tr>
							</table>
							<!-- Search Table Header End -->
							<!-- Search Table Content Start -->
							<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
							<tr>
								<th>Merchant Code</th>
								<th>Terminal Code</th>
								<th>Valid From</th>
								<th>Status</th>
								<th>Actions</th>
							</tr>
							</thead>
							
							<c:choose>
							<c:when test="${!(fn:length(terminals) eq 0) }">
								<c:forEach items="${terminals}" var="terminalData">
									<tr>
									<td>${terminalData.merchantCode }</td>
									<td>${terminalData.terminalCode }</td>
									<td>${terminalData.validFromDate }</td>	
									<td>${terminalData.status }</td>
									<td>									
										<a href="javascript:editTerminal('${terminalData.terminalCode}')" class="table-actionicon-margin"><span class="glyphicon glyphicon-pencil"></span></a>									
										<a href="#"><span class="glyphicon glyphicon-trash"></span></a>
									</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="7" style="color: red;">No Terminals found</td>
								</tr>
							</c:otherwise>
							</c:choose>
							</table>
							<table class="table table-striped table-bordered table-condensed">
							<c:if test="${ !(fn:length(terminals) eq 0)}">
							
							<tr class="table-footer-main">
								<td colspan="10" class="search-table-header-column">
									<div  class="col-sm-12">
										<div class="col-sm-4">
											<div class="btn-toolbar" role="toolbar">
												<div class="btn-group custom-table-footer-button">
													<button type="button" class="btn btn-default"><img src="../images/excel.png"></button>
													<button type="button" class="btn btn-default"><img src="../images/pdf.png" ></button>
													<button type="button" class="btn btn-default"><img src="../images/print-icon.png"></button>
												</div>
											</div>
										</div>
										<div class="col-sm-8">	
											
													<ul class="pagination custom-table-footer-pagination">
														<c:if test="${portalListPageNumber gt 1}">
															<li><a href="javascript:getPortalOnPageWithRecords('1','${totalRecords}')">
																	&laquo;</a></li>
															<li><a href="javascript:getPortalPrevPageWithRecords('${portalListPageNumber }','${totalRecords}')">
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
					<!-- Search Table Block End -->
				</div>
			</article>
			<!--Article Block End-->
			<jsp:include page="footer.jsp"></jsp:include>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->	
	 <script src="../js/rome.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/sortable.js"></script>
	<script src="../js/terminal.js"></script>
	<script src="../js/messages.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	Unknown Error
	<script>		
		/* Select li full area function Start */
		$("li").click(function(){
			window.location=$(this).find("a").attr("href"); 
			return false;
		});
		$(document).ready( function() {
			highlightMainContent('navListId3');
			rome(fromDate, { time: false });
			rome(toDate, { time: false });
			/* DatePicker Javascript Strat*/
			/* $('.effectiveDate').datetimepicker({
				timepicker:false,
				format : 'm/d/Y',
				formatDate:'Y/m/d',
			}); */
			/* DatePicker Javascript End*/
		});	
		
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
