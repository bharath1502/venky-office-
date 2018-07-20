<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.chatak.merchant.constants.StatusConstants"%>
<html>
<head>
<link href="../css/pg.css" rel="stylesheet">
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<div
		style="text-align: center; width: 100%; margin: 0; top: 40%; left: 0;"
		class="login">
		<h1><spring:message code="badRequestError.label.invalidrequest"/></h1>
		<a href="login"><spring:message code="badRequestError.label.clickheretologinagain"/></a>
	</div>
	<script src="../../js/backbutton.js"></script>
	<script src="../js/jquery.cookie.js"></script>	
	<script src="../js/messages.js"></script>
</body>
</html>
