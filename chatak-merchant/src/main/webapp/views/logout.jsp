<!doctype html>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title><spring:message code="common.lable.title"/></title>
	 <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link href="../css/style.css" rel="stylesheet" />
    <link rel="icon" href="../images/favicons.png" type="image/png">
   
</head>
<body>
	<div id="wrapper" class="container-fluid prepaid-admin-dashboard">
    	<header id="loginHeader"  class="col-sm-12 content-wrapper"> 
        	<div class="col-sm-4"><img class="login-logo-size" src="../images/logo.jpg"> </div>
        </header>
        <article> 
        <div id="loginContainer" class="col-xs-12 content-wrapper login-page-content">
        	<label class="font-style-text"> <spring:message code="logout.label.youhavesuccessfullyloggedout"/> <a href="login"><spring:message code="badRequestError.label.clickheretologinagain"/></a></label>
        </div>
        </article>
        <jsp:include page="footer.jsp"/>
    </div>
</body>
</html>