<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.Calendar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	int year = Calendar.getInstance().get(Calendar.YEAR);
%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><spring:message code="common.lable.title"/></title>
<!-- Bootstrap -->
<link rel="icon" href="../images/favicon.png" type="image/png">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<link href="../css/split-fee-program.css" rel="stylesheet">
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css" />
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body>
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->

			<!--Header Block End -->
			<!--Navigation Block Start -->
		   <header class="col-sm-12 all-page-header">
				<!--Header Logo Start -->
				<!--Navigation Block Start -->
				<%@include file="navigation-panel.jsp"%>
				<!--Navigation Block Start -->
				<!--Header Logo End -->
			</header>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>

				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="label.log.file" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span>  
					</div>
					<div class="col-sm-12">
						<span class="green-error" id="sucessDiv">${sucess}</span> <span
							class="red-error" id="errorDiv">${error}</span>
					</div>

					<form:form action="downloadFormLog" name="downloadFormById"
						method="post">
						<input type="hidden" id="downloadLog" name="downloadLog" />
						<input type="hidden" id="logName" name="logName" />
					</form:form>

			       <form:form action="chatak-process-download-logs"
						name="poratlsLogDownload" method="post">
						<input type="hidden" name="selectedLog" id="selectedLog" />
					</form:form>

					<fieldset class="col-sm-12">
						<input type="radio" name="adminLog" id="adminLog"
                    onclick="validateAdminPortalLog()" /><spring:message code="label.admin.portal.logs" /> &nbsp <input
							type="radio" name="merchantLog" id="merchantLog"
							onclick="validateMerchantPortalLog()" /><spring:message code="label.merchant.portal.logs" /> &nbsp <input
							type="radio" name="catalinaLog" id="catalinaLog"
							onclick="validateCatalinaLog()" /><spring:message code="label.catalina.logs" /> &nbsp 
					</fieldset>
					<div class="search-results-table1 result-table-align">
						<!--  Search Table Content Start -->
						<table id="serviceResults"
							class="table table-striped table-responsive table-condensed common-table resize-table border-bottom">
							<thead>
								<tr>
									<th style="width: 80%; text-align: left;"><spring:message code="label.portal.logs.download" /></th>
								</tr>
							</thead>
							<c:choose>
								<c:when test="${!(fn:length(adminLog) eq 0) }">
									<c:forEach items="${adminLog}" var="adminLog">
										<tr>
											<td><a id="downloadLog"
												href="javascript:downloadFormLog('${adminLog}','adminLog')">${adminLog}
											</a>&nbsp;</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:when test="${!(fn:length(merchantLog) eq 0) }">
									<c:forEach items="${merchantLog}" var="merchantLog">
										<tr>
											<td><a id="downloadLog"
												href="javascript:downloadFormLog('${merchantLog}','merchantLog')">${merchantLog}
											</a>&nbsp;</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:when test="${!(fn:length(catalinaLog) eq 0) }">
									<c:forEach items="${catalinaLog}" var="catalinaLog">
										<tr>
											<td><a id="downloadLog"
												href="javascript:downloadFormLog('${catalinaLog}','catalinaLog')">${catalinaLog}
											</a>&nbsp;</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="9" style="color: red;"><spring:message code="label.portal.no.logs" /></td>
									</tr>
								</c:otherwise>
							</c:choose>
						</table>
					</div> 
					</div>
			</article>
		<!--Article Block End-->
		<jsp:include page="footer.jsp" />
		</div>
	</div>
	<!--Container block End -->

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/feeprogram.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script type="text/javascript" src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/sorting.js"></script>
	<script src="../js/tablesorter.js"></script>
	<script src="../js/tablesorter.widgets.js"></script>
	<script src="../js/upload-logs.js"></script>
	<script>
		/* if (document.getElementById('portalLog').checked) {			
		} else if (document.getElementById('serviceLog').checked) {
		} else if (document.getElementById('agentLog').checked) {
		} else {
			document.getElementById('agentLog').checked
		} */

		/* var radio=document.getElementsByName("disableme");
		var len=radio.length;
		for(var i=0;i<len;i++)
		{
		 if  (radio[i].checked=true){
			 radio[i].checked;
		 }
		 else {
			 radio[i].checked=true;
		 }
		}	 */
	</script>
</body>
</html>