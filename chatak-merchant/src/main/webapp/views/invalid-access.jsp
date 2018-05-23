<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<link href="../../css/pg.css" rel="stylesheet">
 <link href="../css/pg.css" rel="stylesheet">
</head>
<body>

<div style="text-align: center; width:100%; margin:0; top:40%; left: 0;" class="login">
			<h1><spring:message code="access-invalid.labe.invalidrequestuseralreadyloggedinwithanothersession"/></h1>
			<a href="login"><spring:message code="access-invalid.label.pleaseclickheretogotoLoginpage"/></a>
		</div>

<!-- <div style="text-align: center;color: white;">
	<h1>Chatak-Merchant</h1>
	<h1>Invalid access! or User is already logged in another session!</h1>
	<h1>Invalid access! Your session got timedout, please try again with new session</h1>
</div> -->

<script src="../../js/backbutton.js"></script>	
</body>
</html>
