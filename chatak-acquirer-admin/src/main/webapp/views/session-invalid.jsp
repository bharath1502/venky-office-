<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<html>
<head>
<title>Session Invalid</title>
<link href="../css/pg.css" rel="stylesheet">
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<div
		style="text-align: center; width: 100%; margin: 0; top: 40%; left: 0;"
		class="login">
		<h1><spring:message code="session-invalid.label.invalidaccessiontimeout"/></h1>
		<a href="login"><spring:message code="access-invalid.label.pleaseclickheretogotoLoginpage"/></a>
	</div>
	<script src="../js/jquery.min.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/common-lib.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script src="../js/messages.js"></script>
</body>
</html>
