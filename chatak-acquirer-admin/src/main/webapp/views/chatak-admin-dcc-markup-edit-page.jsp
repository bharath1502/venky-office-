<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css" />
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<!--Header Block End -->
			<!--Navigation Block Start -->
			<!-- <nav class="col-sm-12 nav-bar" id="main-navigation"> -->
				<%-- <jsp:include page="header.jsp"></jsp:include> --%>
				<%@include file="navigation-panel.jsp"%>
			<!-- </nav> -->
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="manage.label.manage"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span>
						<span class="breadcrumb-text"><spring:message code="dcc.label.markup"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="manage.label.sub-merchant.edit"/></span>
						
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<!-- <div class="tab-header-container-first">
						<a href="merchant-search">Search</a>
					</div> -->
					<div class="tab-header-container-first">
						<a href="dcc-markup-search"><spring:message code="common.label.search"/></a>
					</div>
					<div class="tab-header-container active-background">
						<a href="#"><spring:message code="manage.label.sub-merchant.edit"/></a>
					</div>
					
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder padding0">
						<!-- Page Menu Start -->
						
						<div class="row margin0">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionErrorMsg col-xs-12">
										<span class="red-error">&nbsp;${error }</span> <span
											class="green-error">&nbsp;${sucess }</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								
		
							</div>
								<div class="col-sm-12">
									<div class="row">
										<label class="col-sm-2"><spring:message code="merchant-details-account-create.label.merchantname"/></label>
										<div class="col-sm-4">
											<input name="merchantName" id="dccName" value="${merchantName}" class="form-control" readonly="true" />
										</div>
										<input hidden="merchantCode" id="merchantCode" value="${merchantCode}">
									</div>
									<div class="dcc-table-align">
										<table id="dccEditTable" class="dccTable table table-bordered">
										<tr><th><spring:message code="admin.lable.basecurrency"></spring:message></th><th><spring:message code="show-dynamic-MDR-create-page.label.transactioncurrency"></spring:message></th><th><spring:message code="admin.lable.markupfee"></spring:message></th></tr>
										<c:choose>
										 <c:when test="${not empty dccMarkupList}">
										<c:forEach items="${dccMarkupList}" var="dccData">
   								        <tr>      
								        <td>${dccData.baseCurrency}</td>
								        <td>${dccData.transactionCurrency}</td>
								        <td><input type="text" class="form-control" value="${dccData.markUpFee}"></td>
   								        </tr> 
                                       </c:forEach>
                                       </c:when>
                                        <c:otherwise>
                                         <td class="noRecords" colspan="3"><spring:message code="commission-program-search.label.norecordsfound"/></td>
                                        </c:otherwise>
                                        </c:choose>
										</table>
									</div>
								</div>
								<div class="col-sm-12 button-content">
									<fieldset class="col-sm-7 pull-right">
										<input type="submit" class="form-control button pull-right bank-next" id="editpage" value="<spring:message code="commission-program-update.label.updatebutton"/>" onclick="updateDccMarkUp()"> 
										<input type="button" class="form-control button pull-right marginL10 bank-prev" value="<spring:message code="commission-program-update.label.cancelbutton"/>" onclick="resetCancelMarkup()"/>
									</fieldset>
								</div>
						</div>
					</div>
					<!-- Content Block End -->
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
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/utils.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/chatak-ajax.js"></script>
	<script src="../js/dcc-markup.js"></script>
	<script src="../js/messages.js"></script>
	<script type="text/javascript" src="../js/dcc-markup.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		window.onload = function() {
		//	populatedropdown("autoTransferDay");
		}
		
	</script>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script>
		$(document).ready(function() {

		$("#navListId4").addClass("active-background");
		});
		
		function resetCancelMarkup() {
			window.location.href = "dcc-markup-search";
		}
	</script>
</body>
</html>