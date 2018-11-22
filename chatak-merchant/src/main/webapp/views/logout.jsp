<!doctype html>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.chatak.merchant.constants.StatusConstants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title><spring:message code="common.lable.title"/></title>
	 <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link href="../css/style.css" rel="stylesheet" />
    <link rel="icon" href="../images/favicon.png" type="image/png">
   
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<div id="wrapper" class="container-fluid prepaid-admin-dashboard">
    	<header id="loginHeader"  class="col-sm-12 content-wrapper"> 
        	<div class="col-sm-4"><img class="login-logo-size" src="../images/Chatak-logo.jpg"> </div>
        </header>
        <article> 
        <div id="loginContainer" class="col-xs-12 content-wrapper login-page-content">
   		<c:if test="${not empty error}">
        	<label class="font-style-text">${error} <a href="login"><spring:message code="badRequestError.label.clickheretologinagain"/></a></label>
   	 	</c:if>
    	<c:if test="${empty error}">
        	<label class="font-style-text"> <spring:message code="logout.label.youhavesuccessfullyloggedout"/> <a href="login"><spring:message code="badRequestError.label.clickheretologinagain"/></a></label>
        </c:if>
        </div>
        </article>
        <jsp:include page="footer.jsp"/>
        <script src="../js/backbutton.js"></script>
    </div>
</body>
</html>