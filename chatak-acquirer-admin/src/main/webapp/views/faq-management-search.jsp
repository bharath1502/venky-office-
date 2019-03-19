<!doctype html>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.pg.util.Constants"%>

<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><spring:message code="common.lable.title"/></title>
<link rel="icon" href="../images/favicon.png" type="image/png">
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
 <link href="../css/rome.css"  rel="stylesheet">
<script src="../js/newsfeed.js" type="text/javascript"></script>
<script src="../js/validation.js" type="text/javascript"></script>
<script src="../js/jquery.min.js"></script>
<script src="../js/jquery.cookie.js"></script>
<script src="../js/prepaid-lib.js" type="text/javascript"></script>
<script src="../js/messages.js" type="text/javascript"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/custom-buttons.js"></script>
<script src="../js/backbutton.js"></script>
<script src="../js/jquery.maskedinput.js"></script>
<script src="../js/rome.js"></script> 
    <script src="../js/faqmanagement.js" type="text/javascript"></script>
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
			<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="manage.label.manage"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><a href="showFaqManagementSearch"><spring:message code="chatak-report-lable-faq-management"/></a></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="common.label.search"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
						<c:if test="${fn:contains(existingFeatures,faqManagementView) || fn:contains(existingFeatures,faqManagementEdit) || fn:contains(existingFeatures,faqManagementDelete)||fn:contains(existingFeatures,faqManagementCreate)}">
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="common.label.search"/></a>
					</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,faqManagementCreate)}">
					<div class="tab-header-container">
						<a href="showFaqManagementCreate"><spring:message code="common.label.create"/></a>
					</div>
					</c:if>
					<form:form action="showFaqManagementEdit" name="editFaqForm"
					method="post">
					<input type="hidden" id="faqIdData" name="faqIdData" />
					<input type="hidden" name="CSRFToken" value="${tokenval}">
				    </form:form>
				    <form:form action="getFaq" name="paginationForm"
									method="post">
									<input type="hidden" id="pageNumberId" name="pageNumber" /> <input
										type="hidden" id="totalRecordsId" name="totalRecords" />
										<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
							<!--Success and Failure Message Start-->
							   <div class="col-xs-12">
							 <div class="discriptionMsg" data-toggle="tooltip" data-placement="top" title="">
								<!--Success and Failure Message Start-->
								<span class="green-error" id="sucessDiv">${sucess}</span> <span
									class="red-error" id="errorDiv">${error}</span>
                                 </div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="searchFaqManagement" modelAttribute="faqManagementRequest" name="faqManagementRequest" method="post">
								<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="prepaid-admin-label-faq-management-search-Categeory" />
													 </label>
													 <form:select id="categoryId" path="categoryId"
														cssClass="form-control"
														onchange="fetchModuleNameForCat(this.value)">
														<form:option value="">
															<spring:message
																code="prepaid-admin-module-search.label.select" />
														</form:option>
														<c:forEach items="${faqManagementCategeoryList}" var="entity">
															<form:option value="${entity.categoryId}">${entity.categoryName}</form:option>
														</c:forEach>
													</form:select> 
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="prepaid-admin-label-faq-management-search-Module" /></label>
													 <form:select id="moduleId" path="moduleId"
														cssClass="form-control" onblur="return clientValidation('moduleId','moduleName','moduleIdErrorDiv')">
														<form:option value="">
															<spring:message
																code="prepaid-admin-module-search.label.select" />
														</form:option>
														<c:forEach items="${faqManagementRequestList}" var="entity">
															<form:option value="${entity.moduleId}">${entity.moduleName}</form:option>
														</c:forEach>
													</form:select> 
												</fieldset>
												
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="prepaid-admin-label-faq-management-create-Question" /></label>
													<form:input cssClass="form-control" path="questionName"
														id="questionName" maxlength="<%= StatusConstants.QUESTION.toString() %>" title="Use wild card like % * to search"
														 onkeypress = "return charactersonly(this,event)"/>
													<!-- <div class="discriptionErrorMsg">
														<span id="questionNameErrorDiv" class="red-error">&nbsp;</span>
													</div> -->
												</fieldset>
											</div>
										</div>
										<!--Panel Action Button Start -->
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit" class="form-control button pull-right" id ="myButton"
													value="<spring:message code="common.label.search"/>">
												<a href="showFaqManagementSearch" class="form-control button pull-right"><spring:message code="common.label.reset"/></a>
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
					<c:set var ="flageCheck" scope="session" value="<%=Constants.YES%>"/>
					<c:if test="${searchList ne flageCheck}">
					<div class="search-results-table">
						<table class="table table-striped table-bordered table-condensed marginBM1">
							<!-- Search Table Header Start -->
							<tr>
								<td class="search-table-header-column widthP80">
									<span class="glyphicon glyphicon-search search-table-icon-text"></span>									
									<span><spring:message code="common.label.search"/></span>
								</td>
								<td class="search-table-header-column" style ="font-weight:bold;"><spring:message code="common.label.totalcount"/> : ${totalRecords}</td>
							</tr>
							</table>
							<!-- Search Table Header End -->
							<!-- Search Table Content Start -->
							<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter marginBM1 common-table resize-table">
							<thead>
							<tr>
							    <th><spring:message code="prepaid-admin-label-faq-management-search-Categeory"/></th>
							    <th><spring:message code="prepaid-admin-label-faq-management-search-Module"/></th>
								<th><spring:message code="prepaid-admin-label-faq-management-create-Question"/></th>
								<th><spring:message code="prepaid-admin-label-faq-management-create-Answer"/></th>
								<th><spring:message code="common.label.status"/></th>
								<th class="sorter-false tablesorter-header tablesorter-headerUnSorted"><spring:message code="common.label.action"/></th>
							</tr>
							</thead>
							<c:choose>
								<c:when test="${!(fn:length(faqManagementRequestList) eq 0) }">
									<c:forEach items="${faqManagementRequestList}" var="faqManagementRequestList">
									<tr>
									 <td data-title="Category">${faqManagementRequestList.categoryName} &nbsp; </td>
									  <td data-title="Module">${faqManagementRequestList.moduleName} &nbsp; </td>
										<td data-title="Question" title="${faqManagementRequestList.questionName}">${faqManagementRequestList.questionName} &nbsp; </td>								
											 <td data-title="Answer">${faqManagementRequestList.questionAnswer} &nbsp; </td>			
										<c:choose>
										<c:when test="${faqManagementRequestList.status eq 'PendingSuspended'}">
										<td data-title="Suspended"><spring:message code="Suspended"/> &nbsp; </td>
										</c:when>
										<c:otherwise>
										<td data-title="Status">${faqManagementRequestList.status}  &nbsp; </td>
										</c:otherwise>
										</c:choose>
										 <td data-title="Action">
											<c:choose>
											            <c:when test="${faqManagementRequestList.status eq 'PendingSuspended' }">
															<a href="javascript:changeNewsFeedStatus('${faqManagementRequestList.id}','Pending','Pending')" title="<spring:message code="Pending"/>">
																	<img alt="Active" src="../images/active.png" title='<spring:message code="Pending"/>'></img>
																</a>
														</c:when>
														<c:when test="${fn:containsIgnoreCase(faqManagementRequestList.status,'Active') }">
															<a href="javascript:editFaqData('${faqManagementRequestList.faqId}')" title="<spring:message code="common.label.edit"/>"><span
														      class="glyphicon glyphicon-pencil"></span></a>
													       <%--  <a href="javascript:changeFaqStatus('${faqManagementRequestList.id}','Suspended','Suspended')" title=""Suspend">
																 <img src="../images/deactive.png" alt="Suspend" title="<spring:message code="Suspend"/>"></img></a> --%>
														</c:when>
														<c:otherwise>
															<c:if test="${fn:containsIgnoreCase(faqManagementRequestList.status,'Suspended')}">
																<a href="javascript:changeNewsFeedStatus('${faqManagementRequestList.id}','Active','Active')" title="Active">>
																	<img alt="Active" src="../images/active.png" title="Activate"></img>
																</a>
															</c:if>
														</c:otherwise>
											</c:choose> &nbsp; 
										</td> 
											</tr>                                
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="6" style="color: red;"><spring:message code="common.label.norecords"/></td>
									</tr>
								</c:otherwise>
							</c:choose>
							</table>
							<!-- Search Table Content End -->
							<table class="table table-striped table-bordered table-condensed">
								<c:if test="${!(fn:length(faqManagementRequestList) eq 0) }">
									<tr class="table-footer-main">
										<td colspan="10" class="search-table-header-column">
												<div class="col-sm-12">
													<ul class="pagination custom-table-footer-pagination marginRM15">
														<c:if test="${portalListPageNumber gt 1}">
															<li><a class="smallPageSize" href="javascript:getPortalOnPageWithRecords('1','${totalRecords}')">
																				&laquo;</a></li>
															<li><a class="smallPageSize" href="javascript:getPortalPrevPageWithRecords('${portalListPageNumber }', '${totalRecords}')">
																				&lsaquo; </a></li>
														</c:if>
		
														<c:forEach var="page" begin="${beginPortalPage }"
																end="${endPortalPage}" step="1" varStatus="pagePoint">
																<c:if test="${portalListPageNumber == pagePoint.index}">
																	<li	class="${portalListPageNumber == pagePoint.index?'active':''}">
																		 <a class="smallPageSize" href="javascript:">${pagePoint.index}</a>
																	 </li>
																</c:if>
																<c:if test="${portalListPageNumber ne pagePoint.index}">
																	<li class=""><a class="smallPageSize"
																		href="javascript:getPortalOnPageWithRecords('${pagePoint.index }','${totalRecords}')">${pagePoint.index}</a>
																	</li>
																</c:if>
															</c:forEach>
															<c:if test="${portalListPageNumber lt portalPages}">
																<li><a class="smallPageSize" href="javascript:getPortalNextPageWithRecords('${portalListPageNumber }', '${totalRecords}')">
																				&rsaquo;</a></li>
																<li><a class="smallPageSize" href="javascript:getPortalOnPageWithRecords('${portalPages }', '${totalRecords}')">&raquo;
																    </a></li>
														   </c:if>
													   </ul>
										     </div>
										</td>

									</tr>
								</c:if>
								<!-- Search Table Content End -->
						</table>
						<!--Panel Action Button Start -->
						<div class="col-sm-12 form-action-buttons">
							<fieldset class="col-sm-3 pull-right">
								<input type="button"
									class="form-control button pull-right table-hide-btn"
									value="<spring:message code="virtual-terminal-sale.label.backbutton"/>">
							</fieldset>
						</div>					
							<!--Panel Action Button End -->
					</div>
					</c:if>
					</div>
					</div>
			</article>
			<!--Article Block End-->
			<jsp:include page="footer.jsp"/>
		</div>
		<!--Container block End -->
	</div>
	
    <script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/sortable.js"></script>
	<script src="../js/rome.js"></script> 
	<script src="../js/bank.js"></script>
	<script src="../js/messages.js" type="text/javascript"></script>
    <script src="../js/faqmanagement.js" type="text/javascript"></script>
	<script>
		/* Select li full area function Start */
		$("li").click(function() {
			window.location = $(this).find("a").attr("href");
			return false;
		});
		/* Select li full area function End */
		/* Common Navigation Include Start */
		$(function() {
			$("#main-navigation").load("main-navigation.html");
		});
		function highlightMainContent() {
			$("#navListId12").addClass("active-background");
		}
		/* Common Navigation Include End */
		$(document).ready(function() {
				$('#newsFeedDiv').popup({
					blur:false
				});
				 $('input:visible:enabled:first').focus();
		});
		$(document).ready(function() {
			$('#publishPopupDiv').popup({
				blur:false
			});
		});
		function closePopup(){
			$('#newsFeedDiv').popup("hide");
		}
		function openPopup(){
			$('#newsFeedDiv').popup("show");
		}
		function closePublishPopup(){
			$('#publishPopupDiv').popup("hide");
		}
		function openPublishPopup(){
			$('#publishPopupDiv').popup("show");
		}
		/* DatePicker Javascript Strat
		rome(startDate, { time: false,"inputFormat": "DD-MM-YYYY" });
		rome(endDate, { time: false,"inputFormat": "DD-MM-YYYY" });
			DatePicker Javascript End */
			
			(function($) {
	$.fn.ellipsis = function()
	{
	    return this.each(function()
	    {
	    var el = $(this);
	
	        if(el.css("overflow") == "hidden")
	        {
	        var text = el.html();
	        var multiline = el.hasClass('multiline');
	        var t = $(this.cloneNode(true))
	                .hide()
	                .css('position', 'absolute')
	                .css('overflow', 'visible')
	                .width(multiline ? el.width() : 'auto')
	                .height(multiline ? 'auto' : el.height());
	                el.after(t);
	function height() { return t.height() > el.height(); };
	function width() { return t.width() > el.width(); };
	
	var func = multiline ? height : width;
	
	while (text.length > 7)
	{
	        text = text.substr(0, text.length - 1);
	        t.html(text + "...");
	}
	
	el.html(t.html());
	t.remove();
	            }
	        });
	};
	})(jQuery);
	
	$(document).ready(function()
	{
	    $(".ellipsis").ellipsis();  
	    
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
		
	$(".table-hide-btn").click(function(){
		$(".search-results-table").slideUp();
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