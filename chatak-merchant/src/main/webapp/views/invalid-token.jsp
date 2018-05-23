<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<link href="../css/pg.css" rel="stylesheet">
</head>
<body>
<div style="text-align: center; width:100%; margin:0; top:40%; left: 0;" class="login">
	<h1><spring:message code="invalid-token.label.invalidtoken"/>.</h1>
	<a href="login"><spring:message code="badRequestError.label.clickheretologinagain"/></a>
</div>
</body>
</html>
