<!DOCTYPE html>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page trimDirectiveWhitespaces="true" %>
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
			<%-- <jsp:include page="header.jsp"></jsp:include> --%>
			<%@include file="navigation-panel.jsp"%>
			<!--Header Block End -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12">
					<div class="col-xs-12 content-wrapper">
						<h3>Cryptogram </h3><br>
						<h5 align="center">Click on Generate to download cryptogram key</h5>
						<br> 
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
										<span id="errorid" class="red-error">${error}</span> <span
											id="successid" class="green-error">${sucess}</span>
									</div>
								</div>
								<form:form action="cryptoDownload" method="post">
								<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12 ">
										<div class="col-xs-3"></div>
										<div class="col-sm-3">
											<input type="submit"
												class="form-control button login-main-button" value="Generate" />
										</div>
										<div class="col-sm-3">
											<input type="button"
												class="form-control button login-main-button" value="Cancel"
												onclick="cancelChangePassword()">
										</div>
										<div class="col-xs-3"></div>

									</div>
								</form:form>
							</div>
						</div>
					</div>
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

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/changePassword.js"></script>
	<script src="../js/common-lib.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>

	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script>
		/* Select li full area function Start */
		$("li").click(function() {
			window.location = $(this).find("a").attr("href");
			return false;
		});
		
		$(function() {
			$("#main-navigation").load("main-navigation.html");
			highlightMainContent();
		});
		function highlightMainContent() {
			$("#navListId4").addClass("active-background");
		}
		/* Select li full area function End */
	</script>
</body>
</html>