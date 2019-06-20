<!DOCTYPE html>
<%@ page import="com.chatak.merchant.constants.StatusConstants"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><spring:message code="common.lable.title"/></title>
<link rel="icon" href="../images/favicon.png" type="image/png">
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<link href="../css/rome.css" rel="stylesheet">
<script src="../js/jquery.min.js" type="text/javascript"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../js/jquery.min.js"></script>
<script src="../js/jquery.cookie.js"></script>
<script src="../js/messages.js"></script>
<script src="../js/common-lib.js"></script>
<script src="../js/faqmanagement.js"></script>
<script src="../js/prepaid-lib.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>

</head>
<body
	oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
		<jsp:include page="header.jsp"></jsp:include>
			<!--Header Block End -->
			<!--Navigation Block Start -->
			<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block End -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><a
							href="faq-management-search"><spring:message
									code="merchant.faq.Management.message" /> </a></span>
					</div>
					<div>
						<form:form action="searchFaqManagementReport" modelAttribute="faqManagementRequest"
							name="searchFaqManagement" method="post">
							<input type="hidden" id="categoryMappingId"
								name="categoryMappingId" />
							<input type="hidden" name="CSRFToken" value="${tokenval}">
						</form:form>


					</div>
				</div>
			</article>
			<div class="content-wrapper">
				<div class="row">
					<div class="col-sm-12">
						<!--Success and Failure Message Start-->
						<div class="col-xs-12">
							<span class="green-error" id="sucessDiv">${sucess}</span> <span
								class="red-error" id="errorDiv">${error}</span>
						</div>
						<div class="tabbable tabs-left">
							<ul class="nav nav-tabs">
								<li class=""><a href="javascript:searchFaqManagement('9')"><spring:message
											code="dash-board.label.dashboard" /> </a></li>
								<li class=""><a href="javascript:searchFaqManagement('10')"><spring:message
											code="search-sub-merchant.label.submerchant" /> </a></li>
								<li class=""><a href="javascript:searchFaqManagement('11')"><spring:message
											code="virtual-terminal-sale.label.virtualterminal" />
								</a></li>
								<li class=""><a href="javascript:searchFaqManagement('12')"><spring:message
											code="transactions-search.label.transactions" /> </a></li>
								<li class=""><a href="javascript:searchFaqManagement('13')"><spring:message
											code="reports.label.reports" /> </a></li>
								<li class=""><a href="javascript:searchFaqManagement('14')"><spring:message
											code="chatak-report-lable-schedule-report" /> </a></li>
							</ul>
							<div class="tab-content">
								<div class="tab-pane active" id="dashboard">
									<div class="questionalign col-sm-8">
										<table>
											<c:forEach var="element" items="${faqManagementRequestList}">
												<tr class="">
													<td><h5>
															<span class="bold">Q.</span>${element.questionName}</h5></td>
												</tr>
												<tr class="">
													<td><p>
															<span class="bold">Ans:</span>${element.questionAnswer}</p></td>
												</tr>
											</c:forEach>

										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>