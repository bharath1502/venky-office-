<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.chatak.merchant.constants.StatusConstants"%>
<html>
<head>
<link href="../css/pg.css" rel="stylesheet">
<script type="text/javascript">

</script>
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
<div style="text-align: center; width:100%; margin:0; top:40%; left: 0;" class="login">
	<h1><spring:message code="error.label.oopsSystemisunabletoprocessyourrequestpleasetryagain"/></h1>
</div>
<script src="../js/backbutton.js"></script>	
</body>
</html>
