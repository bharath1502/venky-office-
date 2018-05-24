<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.Calendar"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
  int year = Calendar.getInstance().get(Calendar.YEAR);
%>
<footer class="footer">
	<div class="col-sm-3 pull-right footer-logo no-padding">
		<b class="footer-logo"><spring:message code="footer.poweredby"/> </b> <img
			src="../images/chatak_logo_f.png" class="footer-logo-size" alt="Logo" />
	</div>
			<div class="col-sm-9 pull-right no-padding"><p class="footer-txt">
			<spring:message code="footer.copyright1" /><%=year%> <spring:message code="footer.copyright2" />
		</p></div>
</footer>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="../js/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../js/bootstrap.min.js"></script> <script src="../js/utils.js"></script>
<script src="../js/common-lib.js"></script>
<script src="../js/jquery.cookie.js"></script>
<script src="../js/messages.js"></script>