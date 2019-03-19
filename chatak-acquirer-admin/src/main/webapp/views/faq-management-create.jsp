<!doctype html>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
							class="glyphicon glyphicon-play icon-font-size"></span><span
							class="breadcrumb-text"><spring:message code="common.label.create"/></span> 
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<c:if test="${fn:contains(existingFeatures,faqManagementEdit)||fn:contains(existingFeatures,faqManagementCreate)||fn:contains(existingFeatures,faqManagementView)||fn:contains(existingFeatures,faqManagementDelete)}">
						<div class="tab-header-container-first">
							<a href="showFaqManagementSearch"><spring:message
									code="common.label.search" /></a>
						</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,faqManagementCreate)}">
						<div class="tab-header-container active-background">
							<a href="#"><spring:message
									code="common.label.create" /></a>
						</div>
					</c:if>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
						<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-sm-12">
								 <div class="discriptionMsg" data-toggle="tooltip" data-placement="top" title="">
								<span class="green-error" id="sucessDiv">${sucess}</span> <span
									class="red-error" id="errorDiv">${error}</span>
                                 </div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="createFaqManagement"
									modelAttribute="faqManagementRequest" name="faqManagementRequest" method="post">
								<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
											
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="prepaid-admin-label-faq-management-search-Categeory" />
														<span class="required-field">*</span> </label>
													<form:select id="categoryId" path="categoryId"
														cssClass="form-control" onblur="return clientValidation('categoryId','categoryName','categoryIdErrorDiv')"
														onchange="fetchModuleNameForCat(this.value)">
														<form:option value="">
															<spring:message
																code="prepaid-admin-module-search.label.select" />
														</form:option>
														<c:forEach items="${faqManagementCategeoryList}" var="entity">
															<form:option value="${entity.categoryId}">${entity.categoryName}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg">
														<span id="categoryIdErrorDiv" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												
												
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="prepaid-admin-label-faq-management-search-Module" /><span
														class="required-field">*</span></label>
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
													<div class="discriptionErrorMsg">
														<span id="moduleIdErrorDiv" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="prepaid-admin-label-faq-management-create-Question" /><span class="required-field">*</span></label>
													<form:input cssClass="form-control" path="questionName"
														id="questionName" maxlength="<%=StatusConstants.QUESTION.toString() %>"
														onblur="clientValidation('questionName', 'questionName','questionNameErrorDiv')" />
														
													<div class="discriptionErrorMsg">
														<span id="questionNameErrorDiv" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												
												<!-- <fieldset class="col-sm-12"></fieldset> -->
												<fieldset class="col-md-4 col-sm-6">
													<label><spring:message code="prepaid-admin-label-faq-management-create-Answer"/><span class="required-field">*</span></label>
													<form:textarea id="questionAnswer" path="questionAnswer" onblur="return clientValidation('questionAnswer','questionAnswer','questionAnswerErrorDiv')" cols="50" rows="15"
													onkeypress="return charactersonly(this,event)" /> 
													<div class="discriptionErrorMsg">
														<span id="questionAnswerErrorDiv" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>
										</div>
										<!--Panel Action Button Start -->
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit" class="form-control button pull-right" id ="myButton"
													value="<spring:message code="common.label.create"/>" onclick="return validateFormForFaqManagement();">
												<a href="showFaqManagementCreate" class="form-control button pull-right"><spring:message code="common.label.reset"/></a>
											</div>
										</div>

										<!--Panel Action Button End -->
									</div>
								</form:form>
								<!-- Page Form End -->
							</div>
						</div>
					</div>
				</div>
					<!-- Content Block End -->
				</div>
			</article>
				</div>
			<!--Article Block End-->
		<jsp:include page="footer.jsp"/>
		</div>
		<!--Container block End -->
	<script src="../js/jquery.maskedinput.js"></script>
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
	</script>

  </body>
</html>