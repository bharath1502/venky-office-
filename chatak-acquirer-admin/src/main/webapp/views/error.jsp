<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<html>
<head>
<title>Error Page</title>
<link href="../css/pg.css" rel="stylesheet">
<script type="text/javascript">
	
</script>
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<div
		style="text-align: center; width: 100%; margin: 0; top: 40%; left: 0;"
		class="login">
		<h1><spring:message code="error.label.oopsSystemisunabletoprocessyourrequestpleasetryagain"/>.</h1>
	</div>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script src="../js/common-lib.js"></script>
</body>

</html>
