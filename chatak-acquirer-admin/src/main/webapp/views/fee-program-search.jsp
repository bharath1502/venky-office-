<!DOCTYPE html>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
			<!-- <header class="col-sm-12 all-page-header">
				Header Logo Start				
				<div class="col-sm-4"> 
					<img src="images/chatak_logo.jpg" height="35px" alt="Logo"/>
				</div>
				Header Logo End	
				Header Welcome Text and Logout button Start
				<div class="col-sm-5 col-xs-offset-3">
					<div class="pull-right user-settings">
						<table>
							<tr>
								<td><a href="#"><span class="glyphicon glyphicon-log-out"></span> Logout</a></td>
							</tr>
						</table>
					</div>
				</div>
				Header Welcome Text and Logout button End	
			</header> -->
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
						<span class="breadcrumb-text"><spring:message code="fee-program-search.label.programs" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="fee-program-search.label.feeprogram" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="fee-program-search.label.search" /></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<c:if test="${fn:contains(existingFeatures,feeProgramsEdit)||fn:contains(existingFeatures,feeProgramsCreate)||fn:contains(existingFeatures,feeProgramsView)||fn:contains(existingFeatures,feeProgramsDelete)}">
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="fee-program-search.label.searchtab" /></a>
					</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,feeProgramsCreate)}">
					<div class="tab-header-container">
						<a href="show-fee-program-create"><spring:message code="fee-program-search.label.createtab" /></a>
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
										<span class="red-error">${error}</span> 
										<span id="sucessDiv" class="green-error">${sucess}</span>
									</div>
								</div>

								<form:form action="getFeeProgramDetails" name="paginationForm"
									method="post">
									<input type="hidden" id="pageNumberId" name="pageNumber" /> <input
										type="hidden" id="totalRecordsId" name="totalRecords" />
										<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

								<form:form action="editFeeProgram" name="editFeeProgramForm"
									method="post">
									<input type="hidden" id="getFeeProgramId"
										name="getFeeProgramId" />
										<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								
								<form:form action="fee-program-view" name="viewFeeProgramForm"
									method="post">
									<input type="hidden" id="getViewFeeProgramId"
										name="getFeeProgramId" />
										<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								
								<form:form action="deleteFeeProgram" name="deleteFeeProgramForm" method="post">
									<input type="hidden" id="getDeleteFeeId" name="getDeleteFeeId" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

								<form:form action="downloadFeeProgramreport" name="downloadReport" method="post">
									<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" /> 
									<input type="hidden" id="downloadTypeId" name="downloadType" />
									<input type="hidden" id="totalRecords" name="totalRecords" />
									<input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="feeProgramSearch" modelAttribute="feeProgramDTO" method="post">
								<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fee-program-search.label.feeprogramname" /></label>
													<form:input path="feeProgramName" id="feeProgramName" onblur="clientValidation('feeProgramName', 'companyname_not_mandatory','feeProgramNameEr')"
														cssClass="form-control" onkeydown="validateSpace(this)"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top">
														<span id="feeProgramNameEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<%-- <fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title="">Processor</label>
													<form:select path="processor" cssClass="form-control">
														<form:option value="">..:Select:..</form:option>
														<form:option value="VANTIV">VANTIV</form:option>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset> --%>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fee-program-search.label.status" /></label>
													<!-- <select class="form-control">
														<option>..:Select:..</option>
													</select> -->
													<form:select cssClass="form-control" id="status"
														path="status">
														<form:option value=""><spring:message code="fee-program-search.label.all" /></form:option>
														<form:option value="Active"><spring:message code="fee-program-search.label.active" /></form:option>
														<form:option value="Suspended"><spring:message code="fee-program-search.label.suspended" /></form:option>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fee-program-search.label.recordsperpage" /></label>
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
												<input type="submit" class="form-control button pull-right" onclick="return validateFeeProgramName();trimUserData()"
													value="<spring:message code="fee-program-search.label.searchbutton" />"> <input type="button"
													class="form-control button pull-right" value="<spring:message code="fee-program-search.label.resetbutton" />"
													onclick="resetFeeSearch()">
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
					
					<!-- 	<div class="search-results-table">
						<table class="table table-striped table-bordered table-condensed">
							Search Table Header Start
							<tr>
								<td colspan="7" class="search-table-header-column">
									<span class="glyphicon glyphicon-search search-table-icon-text"></span>									
									<span>Search</span>
								</td>
							</tr>
							Search Table Header End
							Search Table Content Start
							<tr>
								<th>Fee Program Name</th>
								<th>Processor</th>
								<th>Status</th>
								<th>Action</th>
							</tr>
							<tr>
								<td>abc</td>
								<td>xyz</td>
								<td>Active</td>
								<td>
									<a href="fee-program-edit.html" class="table-actionicon-margin"><span class="glyphicon glyphicon-pencil"></span></a>
								</td>
							</tr>
							<tr>
								<td>abc</td>
								<td>xyz</td>
								<td>Active</td>
								<td>
									<a href="fee-program-edit.html" class="table-actionicon-margin"><span class="glyphicon glyphicon-pencil"></span></a>
								</td>
							</tr>
							<tr>
								<td>abc</td>
								<td>xyz</td>
								<td>Active</td>
								<td>
									<a href="fee-program-edit.html" class="table-actionicon-margin"><span class="glyphicon glyphicon-pencil"></span></a>
								</td>
							</tr>	
							<tr class="table-footer-main">
								<td colspan="10" class="search-table-header-column">
									<div  class="col-sm-12">
										<div class="col-sm-3">
											<div class="btn-toolbar" role="toolbar">
												<div class="btn-group custom-table-footer-button">
													<button type="button" class="btn btn-default"><img src="../images/excel.png"></button>
													<button type="button" class="btn btn-default"><img src="../images/pdf.png" ></button>
													<button type="button" class="btn btn-default"><img src="../images/print-icon.png"></button>
												</div>
											</div>
										</div>
										<div class="col-sm-9">	
											<ul class="pagination custom-table-footer-pagination">
												<li><a href="#">&laquo;</a></li>
												<li><a href="#">1</a></li>
												<li><a href="#">2</a></li>
												<li><a href="#">3</a></li>
												<li><a href="#">4</a></li>
												<li><a href="#">5</a></li>
												<li><a href="#">&raquo;</a></li>
											</ul>	
										</div>
									</div>								
								</td>
							</tr>
							Search Table Content End	
						</table>
					</div> -->
					<c:if test="${flag ne false }">
					<div class="search-results-table">
						<table class="table table-striped table-bordered table-condensed" style="margin: 1px;">
							<!-- Search Table Header Start -->
							<tr>
								<td colspan="10" class="search-table-header-column"><span
									class="glyphicon glyphicon-search search-table-icon-text"></span>
									<span><spring:message code="fee-program-search.label.searchtable" /></span>
									<span class="pull-right"><spring:message code="fee-program-search.label.totalcount" /><label id="totalCount">${totalRecords}</label></span>
									</td>
							</tr>
							</table>
							<!-- Search Table Header End -->
							<!-- Search Table Content Start -->
							<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
							<tr>
								<th style="width:50%"><spring:message code="fee-program-search.label.feeprogramnametable" /></th>
								<!-- <th>Processor</th> -->
								<th style="width:40%"><spring:message code="fee-program-search.label.statustable" /></th>
								<th class="sorter-false tablesorter-header tablesorter-headerUnSorted"><spring:message code="fee-program-search.label.actiontable" /></th>
							</tr>
							</thead>
							<c:choose>
								<c:when test="${!(fn:length(searchList) eq 0) }">
									<c:forEach items="${searchList}" var="searchDetails">
										<tr>
											<td 
                                               class="tbl-text-align-left">${searchDetails.feeProgramName}</td>
											<%-- <td>${searchDetails.processor} --%>
											<td  class="tbl-text-align-left">${searchDetails.status}</td>
											<td>
											<c:if test="${searchDetails.status == 'Active'}">   
											   <c:if test="${fn:contains(existingFeatures,feeProgramsView)}">
											     <a href="javascript:viewFeeProgram('${searchDetails.feeProgramId}')">
											      <img src="../images/eyeimage.png" alt="<spring:message code="common.label.view"/>" 
											      title="<spring:message code="common.label.view"/>"></img>
											      <span class="glyphicon glyphicon-eye"></span></a>
											   </c:if>
											     <a href="javascript:changeStatus('${searchDetails.feeProgramId}','Suspended','Suspended','feeProgramPopupDiv')" title="Suspend">
												  <img src="../images/active.png" alt="Suspend" title="Suspend"></img></a>
												 <c:if test="${fn:contains(existingFeatures,feeProgramsEdit)}">
												 	<a href="javascript:editFeeProgram('${searchDetails.feeProgramId }')" 
												 	 title="<spring:message code="common.label.edit"/>" class="table-actionicon-margin">
												 	 <span class="glyphicon glyphicon-pencil"></span></a>
												 </c:if>
												 <c:if test="${fn:contains(existingFeatures,feeProgramsDelete)}">
												 	<a href="javascript:confirmDeleteFeeProgram('${searchDetails.feeProgramId }')">
												  	<span class="glyphicon glyphicon-trash"></span></a> 
												 </c:if>
											</c:if> 
											<c:if test="${searchDetails.status == 'Suspended'}">
												 <a href="javascript:changeStatus('${searchDetails.feeProgramId}','Active','Active','feeProgramPopupDiv')" title="Active">
												  <img alt="Active" src="../images/deactive.png" title="Activate"></img></a>
											</c:if>
											</td>			
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="7" style="color: red;"><spring:message code="fee-program-search.label.norecordfound" /></td>
									</tr>
								</c:otherwise>
							</c:choose>
							</table>
							<table class="table table-striped table-bordered table-condensed">
							<c:if test="${ !(fn:length(searchList) eq 0)}">
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
															<spring:message code="fee-program-search.label.downloadall" />
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
					</div>
					<div id="deletePopup" class="popup-void-refund">
						<span class="glyphicon glyphicon-remove closePopupMes" onclick="closePopup()"></span>
						<div class="fw-b-fs15" style="padding: 20px;">
							<spring:message code="delete.label.sure" />
						</div>
						<div class="col-sm-12">
							<input type="submit" class="form-control button pull-right margin5"
								value="<spring:message code="common.label.delete"/>" onclick="deleteAjax();"> 
							<input type="button" class="form-control button pull-right margin5 close-btn"
								onclick="closePopup()" value="<spring:message code="common.label.cancel"/>">
						</div>
					</div>
			</article>
			<!--Article Block End-->
			<div class="footer-container">
				<jsp:include page="footer.jsp"></jsp:include>
			</div>
		</div>
		<!--Container block End -->
	</div>
	<div id="deleteResultPopup" class="popup-void-refund voidResult feeprgpopup">
		<div class="popup-hf-bc fw-b-fs15 txt-center"><span style="line-height: 2em;"><spring:message code="fee.program.delete"/></span></div>
		<div class="col-xs-12">
			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
				<span class="red-error" id="refundErrorData">&nbsp;</span> 
				<span class="green-error" id="successData">&nbsp;</span>
			</div>
		</div>
		
		<div align="center" class="col-xs-12" id="deleteResultInfo">
			<div class="fw-b-fs15"><spring:message code="fee.program.delete.popup.error" /></div>
			<br>
			<br>
		</div>
		
		<div class="col-sm-12 popup-hf-bc">
			<input type="button" class="form-control button pull-right margin5 close-btn" value="<spring:message code="home.label.closebutton"/>" >
		</div>
	</div>
	<!--Body Wrapper block End -->
	
	<div id="feeProgramPopupDiv" class="locatioin-list-popup">
		<span class="glyphicon glyphicon-remove"
			onclick="closePopup();clearPopupDesc();"></span>
		<h2><spring:message code="prepaid-admin-programmanager-search-label.ChangeStatus"/></h2>
		<form:form action="feeProgramActivationSuspention" name="feeProgramActivationSuspentionForm" method="post">
			<input type="hidden" id="suspendActiveId" name="feeProgramId" /> <input
				type="hidden" id="suspendActiveStatus" name="feeProgramStatus" />
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
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/utils.js"></script>
	<script src="../js/sortable.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/feeprogram.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>	
		$(document).ready(function() {
			$('#feeProgramPopupDiv').popup({
				blur : false
			});
			$( "#navListId3" ).addClass( "active-background" );
		});
		
		var feeProgramIds;
		function confirmDeleteFeeProgram(feeProgramId) {
			$('#deletePopup').popup("show");
			feeProgramIds = feeProgramId;
		
		}
		$('.close-btn').click(function() {
			$('#deleteResultPopup').popup("hide");
		});
		function closePopup() {
			$('#feeProgramPopupDiv').popup("hide");
			$('#deletePopup').popup("hide");
		}
		function openPopup() {
			$('#feeProgramPopupDiv').popup("show");
		}
		function deleteAjax(){
		$.ajax({
			type : "GET",
			url : "deleteFeeProgram?feeProgramIds=" + feeProgramIds,
			async : false,
			success : function(response) {
				$('#deletePopup').popup("hide");
				var obj = JSON.parse(response);
				if(obj.errorCode == 'F2') {
					$('.green-error').text(obj.errorMessage);
					$('.search-results-table').hide();
					
					
				} else {
					$('#deleteResultPopup').popup("show");
				}
				
				
			},
			error : function(e) {
			}
		});
		}
		
		/* Select li full area function Start */
		$("li").click(function(){
			window.location=$(this).find("a").attr("href"); 
			return false;
		});
		/* Select li full area function End */		
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
		
		
		$(document).ready(function() {
			$(".glyphicon-trash").hide();
		});
	</script>
</body>
</html>
