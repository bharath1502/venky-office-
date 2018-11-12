<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.Calendar"%>
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
<link rel="icon" href="../images/favicon.png" type="image/png">

<!-- <script src="../js/common-lib.js" type="text/javascript"></script> -->

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">

<form:form action="login" name="login" method="get"></form:form>

	<!--Body Wrapper block Start -->
	<div id="wrapper" class="main-container">
	
	<div class="lang-select-box-container">
	<select id="localeSelectId" onchange="setUserLocale(this.options[this.selectedIndex].value);">
      <option value="en">English</option>
      <option value="es">Spanish</option>
  	</select>
</div>
	
		<!--Container block Start -->
		<div class="container-fluid sub-container">
			<!--Header Block Start -->
			<header class="col-sm-12 login-page-main">
				<!--Header Logo Start -->
				<div class="col-sm-4"></div>
				<div class="col-sm-6 form-group">
					<img src="../images/Chatak-logo.jpg" height="63px" alt="Logo" />
				</div>
				<!--Header Logo End -->
			</header>
			<!--Header Block End -->
			<!--Article Block Start-->
			<article class="clearfix">
				<div class="col-xs-3"></div>
				<div class="col-xs-6 content-wrapper login-page-content">
					<h3><spring:message code="login.label.login" /></h3>
					<div class="row">
						<div class="col-sm-12">
							<!--Success and Failure Message Start-->
							<div class="col-xs-12">
								<div class="discriptionMsg" data-toggle="tooltip" data-placement="top" title="">
									<span class="red-error">${error }</span> <span
										class="green-error">${sucess}&nbsp;</span>
								</div>
							</div>
							<!--Success and Failure Message End-->
							<!-- Page Form Start -->
							<form:form action="authenticate" modelAttribute="loginDetails"
								name="loginDetails">
								<input type="hidden" name="CSRFToken" value="${tokenval}">
								<form:hidden id="timeZoneRegion" path="timeZoneRegion" />
								<div class="col-sm-12 login-elements-holder">

									<fieldset class="col-sm-12">
									<form:hidden path="currentLoginTime" id = "currentDateId"/>
										<div class="input-group">
											<span class="input-group-addon"><img
												src="../images/user_icon.png"></span>
											<form:input id="ackU" path="acqU" cssClass="form-control"
												autocomplete="off" ></form:input>
											<script type="text/javascript">
												document.getElementById('ackU')
														.focus();
											</script>
										</div>
										<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
											<span class="red-error">${acqUError}&nbsp;</span>
										</div>
									</fieldset>
								</div>
								<div class="col-sm-12 login-elements-holder">
									<fieldset class="col-sm-12">
										<div class="input-group">
											<span class="input-group-addon"><img
												src="../images/pass_icon.png"></span>
											<form:password id="ackP" path="acqP" cssClass="form-control"
												autocomplete="off"></form:password>
										</div>
										<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
											<span class="red-error">${acqPError}&nbsp;</span>
										</div>
									</fieldset>
								</div>
								<!--login Action Button Start -->
								<div class="col-sm-12 login-elements-holder" style="margin-bottom: 10px;">
									<div class="col-sm-6">
										<a href="forgot-password" class="pull-left"><spring:message code="login.label.forgotpassword"/>?</a>
									</div>
									<div class="col-sm-6">
										<!-- <button type="submit"
											class="form-control button login-main-button">Login</button> -->
										<input type="submit"  value='<spring:message code="login.label.loginbutton"/>'
											class="form-control1 button login-main-button width65P pull-right" id="loginSubmit" onclick="trimUserData()">
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
		         	src="../images/Chatak-lofo-f.png" class="footer-logo-size" alt="Logo" />
	            </div>
			    <div class="col-sm-6 pull-right no-padding"><p class="footer-txt">
			         <spring:message code="footer.copyright1" /><%=year%> <spring:message code="footer.copyright2" />
		          </p></div>
           </footer>
           <div class="col-xs-3"></div>
		</div>
		<!--Container block End -->
	</div>
	<div style="margin-top: -30px; margin-right: 10px; float: right;">
		<p>${currentBuildRelease}</p>
	</div>
	<!--Body Wrapper block End -->

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/utils.js"></script>
	<!-- <script type="text/javascript" src="../js/backbutton.js"></script> -->
	<script src="../js/common-lib.js"></script>
	
    <script src="../js/jquery.cookie.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../js/backbutton.js" type="text/javascript"></script>
    <script src="../js/messages.js"></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/jstimezonedetect/1.0.4/jstz.min.js'></script>
	
	<script>		
		onloadLogin = function() {
			setPlaceholder('ackU', '<spring:message code="login.label.username"/>');
			setPlaceholder('ackP', '<spring:message code="login.label.password"/>');
		};
		if(document.readyState === "complete") {
			onloadLogin();
		}
		else {
		  onloadLogin();
		}
		
	     $("img").mousedown(function(){
		    return false;
		});
	     
	     $(document).ready(function() {
			var cookieVal = getUserLocale();
			$('#localeSelectId').val(cookieVal);
			var offset = new Date().toString().match(/([A-Z]+[\+-][0-9]+)/)[1];
			var timeZoneRegion = jstz.determine().name();
			$('#currentDateId').val(offset);
			$('#timeZoneRegion').val(timeZoneRegion);
		});
		/* Common Navigation Include End */
		
		$('#ackU').keypress(function( e ) {
			    if(e.which === 32) 
			        return false;
			});
		 
		 $('#ackP').keypress(function( e ) {
			    if(e.which === 32) 
			        return false;
			});
	</script>
</body>
</html>
