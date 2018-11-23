<!DOCTYPE html>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page  import="java.util.Calendar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
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
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css" />
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<script src="../js/jquery.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/merchant-category-code.js"></script>
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->

			<!--Header Block End -->
			<!--Navigation Block Start -->
			<nav class="col-sm-12 nav-bar" id="main-navigation">
			<%@include file="navigation-panel.jsp"%>
			<%-- <jsp:include
					page="header.jsp" /> --%></nav>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="setup.label.setup"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="merchant.label.code"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="common.label.create"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<c:if test="${fn:contains(existingFeatures,merchantCategoryCodeView)||fn:contains(existingFeatures,merchantCategoryCodeEdit)||fn:contains(existingFeatures,merchantCategoryCodeCreate)||fn:contains(existingFeatures,merchantCategoryCodeDelete)}">
					<div class="tab-header-container-first">
						<a href="merchant-category-code-search"><spring:message code="common.label.search"/> </a>
					</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,merchantCategoryCodeCreate)}">
					<div class="tab-header-container active-background">
						<a href="#"><spring:message code="common.label.create"/></a>
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
										<span class="red-error">${error}</span> <span
											class="green-error">${sucess}</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="merchant-category-code-create"
									modelAttribute="mcc" name="mcc" method="post">
								<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">

												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.code"/><span
														class="required-field">*</span></label>
													<form:input path="merchantCategoryCode"
														id="merchantCategoryCode" cssClass="form-control"
														onblur="this.value=this.value.trim();validateMcc()" maxlength="4" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="merchantCategoryCodeEr">&nbsp;</span>
													</div>
												</fieldset>

												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="transaction.label.code"/> <span
														class="required-field">*</span></label>
													<form:select path="tccMultiple"
														cssClass="form-control  tcc-selectbox" multiple="true"
														id="tccMultiple" onchange="validateTCCMultiSelect()"
														onblur="validateTCCMultiSelect()">
														<c:forEach items="${tccMultipleList}" var="tccList">
															<form:option value="${tccList.value}">${tccList.value}->${tccList.label}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="tccMultipleEr" class="red-error">&nbsp;</span>
													</div>
													<form:hidden path="selectedTcc" id="selectedTcc" />
												</fieldset>

												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.description"/><span class="required-field">*</span></label>
													<form:input path="description" id="description"
														cssClass="form-control" onblur="this.value=this.value.trim();validateDescription()"
														maxlength="250" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error" id="descriptionEr">&nbsp;</span>
													</div>
												</fieldset>

											</div>
										</div>
										<!--Panel Action Button Start -->
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit" class="form-control button pull-right"
													value='<spring:message code="common.label.create"/>' onclick="return validCreateMcc()"> <input
													type="button" class="form-control button pull-right"
													value='<spring:message code="common.label.cancel"/>' onclick="openCancelConfirmationPopup()">
													<input type="button" class="form-control button pull-right"
													value='<spring:message code="common.label.reset"/>' onclick="resetMerchantCategoryCode()">
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
				</div>
				<div id="my_popup1" class="popup-void-refund voidResult">
					<span class="glyphicon glyphicon-remove closePopupMes"
						onclick="closeCancelConfirmationPopup()"></span>
					<div class="fw-b-fs15" style="padding: 20px;">
						<spring:message code="cancle.conformation.lable.currency" />
					</div>
					<div class="col-sm-12">

						<input type="button"
							class="form-control button pull-right margin5 close-btn"
							value="<spring:message code="bin.label.no"/>"
							onclick="closeCancelConfirmationPopup()"> <input
							type="submit" class="form-control button pull-right margin5"
							value="<spring:message code="bin.label.yes"/>"
							onclick="cancelCreateOrUpdateMcc()">
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
	<!--Body Wrapper block End -->

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/jquery.min.js"></script>
		<script src="../js/jquery.cookie.js"></script>
		<script src="../js/messages.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/utils.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		$(document).ready(function() {
			$("#navListId2").addClass("active-background");

			if ($('#requestType').val() == 'ADMIN') {
				$('#userType').text('Admin User');
				$('#merchantDivId').hide();
				$('#merchantName').val("");
			} else {
				$('#userType').text('Merchant User');
				$('#merchantDivId').show();
			}
		});
		$('#my_popup1').popup({
			blur : false
		});
	</script>
</body>
</html>
