<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Chatak Acquirer Admin</title>
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css" />
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<!--Header Block End -->
			<!--Navigation Block Start -->
			<!-- <nav class="col-sm-12 nav-bar" id="main-navigation"> -->
				<%-- <jsp:include page="header.jsp"></jsp:include> --%>
				<%@include file="navigation-panel.jsp"%>
			<!-- </nav> -->
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="manage.label.manage"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span>
						<span class="breadcrumb-text"><spring:message code="dcc.label.markup"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="common.label.create"/></span>
						
					</div>
					<c:if test="${fn:contains(existingFeatures,dccMarkupEdit)||fn:contains(existingFeatures,dccMarkupDelete)}">
					<div class="tab-header-container-first">
						<a href="dcc-markup-search"><spring:message code="common.label.search"/></a>
					</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,dccMarkupCreate)}">
					<div class="tab-header-container active-background">
						<a href="#"><spring:message code="common.label.create"/></a>
					</div>
					</c:if>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder padding0">
						<!-- Page Menu Start -->
						
						<div class="row margin0">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionErrorMsg col-xs-12">
										<span class="red-error" id="errorData">&nbsp;${error}</span> <span
											class="green-error">&nbsp;${sucess}</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								
		
							</div>
							<form:form modelAttribute="dccMarkup" name="dccMarkup">
								<div class="col-sm-12">
									<div class="row">
										<label class="col-sm-2"><spring:message code="merchant-details-account-create.label.merchantname"/></label>
										<div class="col-sm-4">
											<form:select cssClass="form-control" path="merchantName"
												id="dccName" onchange="getCurrencyCode(this.value)">
												<form:option value="">..:<spring:message code="sub-merchant-account-search.label.select"/>:..</form:option>
												<c:forEach items="${merchantNamesList}" var="merchant">
													<form:option value="${merchant.merchantCode}">${merchant.merchantName}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>
									<div class="dcc-table-align">
										<table id="sample" class="sample table table-bordered"></table>
									</div>
								</div>
								<div class="col-sm-12 form-action-buttons">
									<div class="col-sm-5"></div>
									<div class="col-sm-7">
										<input type="button" class="form-control button pull-right" value="<spring:message code="show-dynamic-MDR-search.label.create"/>" id="createmarkupFee">
										<input type="button" class="form-control button pull-right" value="<spring:message code="show-dynamic-MDR-search.label.resetbutton"/>" onclick="resetCreateMarkup()"> 
										<input type="button" class="form-control button pull-right" value="<spring:message code="pending-merchant-show.label.cancelbutton"/>" onclick="resetCancelMarkup()">
									</div>
								</div>



							</form:form>
						</div>
					</div>
					<!-- Content Block End -->
				</div>
			</article>
			<!--Article Block End-->
			<footer class="footer">
				<jsp:include page="footer.jsp"></jsp:include>
			</footer>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/chatak-ajax.js"></script>
	<script src="../js/messages.js"></script>
	<script type="text/javascript" src="../js/dcc-markup.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		window.onload = function() {
		//	populatedropdown("autoTransferDay");
		}
		
	</script>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script>
		/* function highlightMainContent() {
			$("#navListId2").addClass("active-background");
		} */
		/* Common Navigation Include End */
		/* DatePicker Javascript Strat*/
		$(document).ready(function() {
			$("#navListId4").addClass("active-background");
			$(window).keydown(function(event){
			    if(event.keyCode == 13) {
			      event.preventDefault();
			      return false;
			    }
			 });
		});
		
		function resetCreateMarkup() {
			window.location.href = "chatak-admin-dcc-markup-create-page";
		}
		function resetCancelMarkup() {
			window.location.href = "dcc-markup-search";
		}
	</script>
</body>
</html>