<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
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
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script src="../js/common-lib.js"></script>
</body>
</html>
