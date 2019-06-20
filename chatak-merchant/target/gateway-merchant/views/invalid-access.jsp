<!DOCTYPE html>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.chatak.merchant.constants.StatusConstants"%>
<html>
<head>
<title>invalid-access</title>
<link href="../../css/pg.css" rel="stylesheet">
 <link href="../css/pg.css" rel="stylesheet">
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">

<div style="text-align: center; width:100%; margin:0; top:40%; left: 0;" class="login">
			<h1><spring:message code="access-invalid.labe.invalidrequestuseralreadyloggedinwithanothersession"/></h1>
			<a href="login"><spring:message code="access-invalid.label.pleaseclickheretogotoLoginpage"/></a>
		</div>

<script src="../../js/backbutton.js"></script>	
</body>
</html>
