<!DOCTYPE html>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@page import="java.util.Calendar"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
<link rel="icon" href="../images/favicons.png" type="image/png">
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
	<div id="wrapper" class="main-container">
		<!--Container block Start -->
		<div class="container-fluid sub-container">
			<!--Container block Start -->
			<div class="container-fluid">
				<!--Header Block Start -->
				<header class="col-sm-12 login-page-main">
					<!--Header Logo Start -->
					<div class="col-sm-4"></div>
					<div class="col-sm-6">
						<img src="../images/logo.jpg" height="63px" alt="Logo" />
					</div>
					<!--Header Logo End -->
				</header>
				<!--Header Block End -->
				<!--Article Block Start-->
				<article class="clearfix">
					<div class="col-xs-3"></div>
					<div class="col-xs-6 content-wrapper login-page-content">
						<h3><spring:message code="merchant-forgot-password.label.forgotpassword" /></h3>
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionMsg" id="discriptionErrorMsgDiv">
										<span class="red-error">${error}</span> <span
											class="green-error">${sucess}</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="merchant-forgot-password"
									modelAttribute="forgotPasswordRequest" method="post">

									<div class="col-sm-12 login-elements-holder">
										<fieldset class="col-sm-12">
											<div class="input-group">
												<span class="input-group-addon"><img
													src="../images/user_icon.png"></span> <input type="text"
													name="userName" id="userName" class="form-control" onfocus="validateUserName()"
													placeholder="<spring:message code="login.label.username"/>" onblur="this.value=this.value.trim();validateForgotPassWordUserName()" />
											</div>
											<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
												<span class="red-error" id="userNameDiv">&nbsp;</span>
											</div>
										</fieldset>
									</div>
									<!--login Action Button Start -->
									<div class="col-sm-12 login-elements-holder" style="margin-bottom: 10px;">
										
										
										<div class="col-sm-6">
											<input type="button"
												class="form-control button login-main-button" value="<spring:message code="merchant-forgot-password.label.cancel" />"
												onclick="window.open('login.html','_self')">
										</div>
										<div class="col-sm-6">
											<input type="submit"
												class="form-control button login-main-button" value="<spring:message code="merchant-forgot-password.label.submit" />"
												onclick="validateUserName();return validateForgotPassWordUserName()">
										</div>
									</div>
									<!--login Action Button End -->
								</form:form>
							</div>
						</div>
					</div>
					<div class="col-xs-3"></div>
				</article>
				<!--Article Block End-->
				<div class="col-xs-1"></div>
				<footer class="footer1 col-sm-8 no-padding">
	            <div class="col-sm-3 pull-right footer-logo no-padding">
		         <b class="footer-logo"><spring:message code="footer.poweredby"/> </b> <img
		         	src="../images/chatak_logo_f.png" class="footer-logo-size" alt="Logo" />
	            </div>
			    <div class="col-sm-6 pull-right no-padding"><p class="footer-txt">
			         <spring:message code="footer.copyright1" /><%=year%> <spring:message code="footer.copyright2" />
		          </p></div>
           </footer>
           <div class="col-xs-3"></div>
			</div>
			<!--Container block End -->
		</div>
		</div>
		<!--Body Wrapper block End -->

		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="../js/jquery.min.js"></script>
		<script src="../js/common-lib.js"></script>

		<script src="../js/admin-user.js"></script>
		<!-- Include all compiled plugins (below), or include individual files as needed -->
		<script src="../js/bootstrap.min.js"></script> <script src="../js/utils.js"></script>
		 <script src="../js/jquery.cookie.js"></script>
	     <script src="../js/messages.js"></script>

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
				$("#navListId1").addClass("active-background");
			}
			/* Common Navigation Include End */
			
			$("img").mousedown(function(){
	    		return false;
			});
			
			$('#emailId').keypress(function( e ) {
			    if(e.which === 32) 
			        return false;
			});
			function validateUserName() {
				setDiv('discriptionErrorMsgDiv', '');
			}
		</script>
		<script src="../js/backbutton.js"></script>	
</body>
</html>