  <!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page  import="java.util.Calendar"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
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
<!-- <link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css" /> -->
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">

<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->

			<!--Header Block End -->
			<!--Navigation Block Start -->
			<nav class="col-sm-12 nav-bar" id="main-navigation"><%-- <jsp:include
					page="header.jsp" /> --%>
					<%@include file="navigation-panel.jsp"%>
					</nav>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="setup.label.setup"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span>
						<span class="breadcrumb-text"><spring:message code="blacklist.label.black"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="common.label.edit"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first">
						<a href="black-listed-card-search"><spring:message code="common.label.search"/></a>
					</div>
					<div class="tab-header-container active-background">
						<a href="#"><spring:message code="common.label.edit"/></a>
					</div>
					
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="descriptionMsg" class="red-error" style="color: red;" id="descriptionMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error">${error}</span> <span
											class="green-error">${sucess}</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="updateBlackListedCard" modelAttribute="blackListedCard" name="blackListedCard">
								<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12 paddingT20">
										<div class="row">
											<!-- Account Details Content Start -->
											<section class="field-element-row account-details-content">
											<form:hidden path="id"/>
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="blacklist.label.cardnumber"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="cardNumber"
															id="cardNumber" maxlength="19" 
															onblur="this.value=this.value.trim();validateCardNumber()"
															onkeypress="return numbersonly(this,event)" />
															<form:hidden path="cardNumber"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="cardNumberEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="submit"
															class="form-control button pull-right acc-next"
															value='<spring:message code="common.label.update"/>' onclick="return validateCardNumber()"> <input
															type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.cancel"/>' onclick="goToBlackListedCardSearch()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<!-- Account Details Content End -->
											</div>
										</div>	
								</form:form> 
								<!-- Page Form End -->
							</div>
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
	
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/common-lib.js"></script>
	
	<script src="../js/validation.js"></script>
	<script src="../js/chatak-ajax.js"></script>
	<script src="../js/black-listed-card.js"></script> 
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/messages.js"></script>
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script>
		
		$(document).ready(function() {
			$("#navListId2").addClass("active-background");
			$(window).keydown(function(event){
			    if(event.keyCode == 13) {
			      event.preventDefault();
			      return false;
			    }
			 });
		});
		</script>
	</body>
</html> 
