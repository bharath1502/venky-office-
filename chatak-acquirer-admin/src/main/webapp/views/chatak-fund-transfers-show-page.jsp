<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Chatak Acquirer Admin</title>
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Navigation Block Start -->
			<%-- <jsp:include page="header.jsp"></jsp:include> --%>
			<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!--Success and Failure Message Start-->
					<div class="col-xs-12">
						<div class="discriptionErrorMsg paddingTopBottom">
							<span class="red-error">&nbsp;${error}</span> <span
								class="green-error">&nbsp;${sucess} </span>
						</div>
						<span> <form:form action="getTransfersByStatus"
						commandName="transferListRequest" name="transferListRequest">
						<input type="hidden" name="CSRFToken" value="${tokenval}">
						<form:hidden path="status" id="statusId" />
						<form:hidden path="transferMode" id="transferMode" />
					</form:form>
							<table class="table table-striped table-bordered table-condensed">
								<!-- Search Table Header Start -->
								<tr>
									<td colspan="10" class="col-xs-8 search-table-header-column"><span
										class="glyphicon glyphicon-search search-table-icon-text"></span>
										<span>Transfer Requests</span></td>
								</tr>
								<tr>
									<th><center>Request type</center></th>
									<th><center>Pending Transfers</center></th>
									<th><center>Processing Transfers</center></th>
									<th><center>Executed Transfers</center></th>
									<th><center>Cancelled Transfers</center></th>
								</tr>
								<tr align="center">
									<th><center>EFT</center></th>
									<c:if test="${not empty transferRequestsCountEFT }">
										<td><a href="javascript:;"
											onclick="return getTransferList('Pending','EFT')">${transferRequestsCountEFT.pendingCount}</a></td>
									</c:if>
									<c:if test="${not empty transferRequestsCountEFT }">
										<td><a href="javascript:;"
											onclick="return getTransferList('Processing','EFT')">${transferRequestsCountEFT.processingCount}</a></td>
									</c:if>
									<c:if test="${not empty transferRequestsCountEFT }">
										<td><a href="javascript:;"
											onclick="return getTransferList('Executed','EFT')">${transferRequestsCountEFT.executedCount}</a></td>
									</c:if>
									<c:if test="${not empty transferRequestsCountEFT }">
										<td><a href="javascript:;"
											onclick="return getTransferList('Cancelled','EFT')">${transferRequestsCountEFT.canceledCount}</a></td>
									</c:if>
								</tr>
								<tr align="center">
									<th><center>Check</center></th>
									<c:if test="${not empty transferRequestsCountCheck }">
										<td><a href="javascript:;"
											onclick="return getTransferList('Pending','CHECK')">${transferRequestsCountCheck.pendingCount}</a></td>
									</c:if>
									<c:if test="${not empty transferRequestsCountCheck }">
										<td><a href="javascript:;"
											onclick="return getTransferList('Processing','CHECK')">${transferRequestsCountCheck.processingCount}</a></td>
									</c:if>
									<c:if test="${not empty transferRequestsCountCheck }">
										<td><a href="javascript:;"
											onclick="return getTransferList('Executed','CHECK')">${transferRequestsCountCheck.executedCount}</a></td>
									</c:if>
									<c:if test="${not empty transferRequestsCountCheck }">
										<td><a href="javascript:;"
											onclick="return getTransferList('Cancelled','CHECK')">${transferRequestsCountCheck.canceledCount}</a></td>
									</c:if>
								</tr>
							</table>
						</span> <!-- <img src="../images/dashboard.png" width="100%" height="400px"
							class="col-xs-12 content-wrapper"> <span
							class="col-xs-6 dashboard-image-description"> <label data-toggle="tooltip" data-placement="top" title="">Transaction
								by Card Program</label>
						</span> <span class="col-xs-6 dashboard-image-description"> <label data-toggle="tooltip" data-placement="top" title="">Transaction
								Percentage by Card Program</label>
						</span> -->
						<div class="col-sm-12 form-action-buttons">
						<div class="col-sm-5"></div>
						<div class="col-sm-7">
							<input type="button" class="form-control button pull-right"
								value="Cancel" onclick="backToHome()">
						</div>
					</div>
					</div>
					
				</div>
			</article>
			<!--Article Block End-->
			<footer class="footer">
				<jsp:include page="footer.jsp"></jsp:include>
			</footer>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/transactions.js"></script>
	<script type="text/javascript" src="../js/transfers.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script src="../js/common-lib.js"></script>
	<script>
		/* Select li full area function Start */
		$("li").click(function() {
			window.location = $(this).find("a").attr("href");
			return false;
		});
		/* Select li full area function End */
		$(document).ready(function() {
			highlightMainContent('navListId9');
		});
	</script>
</body>
</html>