<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.pg.util.Constants"%>
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
<body>
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/sorting.js"></script>
	<script src="../js/tablesorter.js"></script>
	<script src="../js/tablesorter.widgets.js"></script>
	<script src="../js/feeprogram.js"></script>
	<script src="../js/validation.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<!--Navigation Block Start -->
			<%-- <jsp:include page="header.jsp"></jsp:include> --%>
			<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>

				<form:form action="showChatakAquirerFeeEdit"
					name="showChatakAquirerFeeEdit">
					<input type="hidden" id="getAquirerId" name="getAquirerId" />
				</form:form>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text">Fee Code</span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text">Show</span>
					</div>
					
					<c:choose>
								<c:when test="${(fn:length(pgAcquirerFeeCodeList) eq 0) }">
								<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error" style="font-size: 12px;padding-left: 10px;"><span>You have not added Acquirer Fee Code. Click on "Add" to add Acquirer Fee Code.</span> <span
											class="green-error"></span>
									</div>
								</c:when>
								<c:otherwise>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first active-background">
						<a href="#">Show</a>
					</div>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder padding0">
					<div class="col-xs-12">
									<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error">${error}</span> 
										<span class="green-error">${sucess}</span>
									</div>
								</div>
						<form action="#"></form>
						<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
							<!-- Search Table Content Start -->
							<tr id="tableheader0">
								<th class="vertical-middle">Associate Name</th>
								<th class="vertical-middle"><spring:message code="fee-program-edit.label.%value"/></th>
								<th class="vertical-middle"><spring:message code="fee-program-edit.label.flatfee"/></th>
								<th class="vertical-middle"><spring:message code="common.label.edit"/></th>
							</tr>
							</thead>
							<!-- Search Table Content End -->
							<c:choose>
								<c:when test="${!(fn:length(pgAcquirerFeeCodeList) eq 0) }">
									<c:forEach items="${pgAcquirerFeeCodeList}"
										var="pgAcquirerFeeCode">
										<tr>
											<td><img
												src="../images/${pgAcquirerFeeCode.acquirerName }.png"
												height="25px" alt="${pgAcquirerFeeCode.acquirerName }" /></td>
											<td>${pgAcquirerFeeCode.percentageOfTxn }</td>
											<td>${pgAcquirerFeeCode.flatFee }</td>

											<td><a
												href="javascript:editAcquirerFee('${pgAcquirerFeeCode.acquirerFeeCodeId }')"
												title="Edit" class="table-actionicon-margin"><span
													class="glyphicon glyphicon-pencil"></span></a></td>

										</tr>
									</c:forEach>
								</c:when>
							</c:choose>
						</table>
					</div>
					</c:otherwise>
							</c:choose>
				<div class="col-sm-12 form-action-buttons">
						<div class="col-sm-5"></div>
						<div class="col-sm-7" style="margin-left: 380px;">
							<a type="button"
								class="form-control button pull-right"
								href="chatak-acquirer-fee-create-show">Add</a>
								 <a type="button"
								class="form-control button pull-right"
								href="chatak-partner-fee-show">Cancel</a>
						</div>
					</div>
				</div>

			</article>
			<footer class="footer">
				<jsp:include page="footer.jsp"></jsp:include>
			</footer>
		</div>
	</div>
</body>
</html>