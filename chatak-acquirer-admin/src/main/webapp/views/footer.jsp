<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.Calendar"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
  int year = Calendar.getInstance().get(Calendar.YEAR);
%>

<footer class="footer">
<div class="col-sm-3 pull-right footer-logo" style="padding: 0px;"> 
					<b class="footer-logo"><spring:message code="footer.poweredby"/></b>
					<img src="../images/Chatak-lofo-f.png" class="footer-logo-size" alt="Logo"/>
				</div>
	<div class="col-sm-6 pull-left" style="padding: 0px;"><p class="footer-txt" style="text-align: left;"><spring:message code="footer.copyright1" /><%=year%> <spring:message code="footer.copyright2" /></p></div>
</footer>

<script src="../js/common-lib.js"></script>