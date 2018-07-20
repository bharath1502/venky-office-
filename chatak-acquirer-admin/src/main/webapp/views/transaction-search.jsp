<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
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
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text">Transactions</span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text">Search</span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<!--div class="tab-header-container-first active-background">
						<a href="#">Search </a>					
					</div>					
					<div class="tab-header-container">
						<a href="manage-account-create.html">Create</a>
					</div>					
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="descriptionMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error">&nbsp;</span> <span
											class="green-error">&nbsp;</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="searchTransaction" commandName="transaction"
									name="transaction">
								 <input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-4">
													<label data-toggle="tooltip" data-placement="top" title="">Merchant Code</label>
													<form:select cssClass="form-control" path="merchant_code"
														id="merchant_code">
														<form:option value="-1">..:Select:..</form:option>
														<c:forEach items="${merchantOptions}" var="merchantOpt">
															<form:option value="${merchantOpt.value }">${merchantOpt.label }</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-4">
													<label data-toggle="tooltip" data-placement="top" title="">Terminal Code</label>
													<form:input cssClass="form-control" path="terminal_id"
														id="terminal_id" maxlength="16" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-4">
													<label data-toggle="tooltip" data-placement="top" title="">Transaction Type</label>
													<form:select cssClass="form-control"
														path="transaction_type" id="transaction_type">
														<form:option value="0">..:Select:..</form:option>
														<form:option value="Sale">Sale</form:option>
														<form:option value="Void">Void</form:option>
														<form:option value="Capture">Capture</form:option>
														<form:option value="Auth">Auth</form:option>
														<form:option value="Refund">Refund</form:option>
														<form:option value="Reversal">Reversal</form:option>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>
										</div>
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-4">
													<label data-toggle="tooltip" data-placement="top" title="">Transaction from</label>
													<div class="input-group">
														<form:input cssClass="form-control effectiveDate" path="from_date"
															id="from_date" />
														<span class="input-group-addon"><span
															class="glyphicon glyphicon-calendar"></span></span>
													</div>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-4">
													<label data-toggle="tooltip" data-placement="top" title="">Transaction till</label>
													<div class="input-group">
														<form:input cssClass="form-control effectiveDate" path="to_date"
															id="to_date" />
														<span class="input-group-addon"><span
															class="glyphicon glyphicon-calendar"></span></span>
													</div>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-4">
													<label data-toggle="tooltip" data-placement="top" title="">Status</label>
													<form:select cssClass="form-control" path="status"
														id="status">
														<form:option value="-1">..:Select:..</form:option>
														<form:option value="0">Success</form:option>
														<form:option value="1">Failure</form:option>
														<form:option value="2">Switch Failure</form:option>
														<form:option value="3">Unknown</form:option>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>
										</div>
										<!--Panel Action Button Start -->
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit" class="form-control button pull-right"
													value="Search"> <input type="button"
													class="form-control button pull-right" value="Reset">
											</div>
										</div>
										<!--Panel Action Button End -->
									</div>
								</form:form>
								<!-- Page Form End -->
							</div>
						</div>
					</div>
					<!-- Content Block End -->
					<!-- Search Table Block Start -->
					<c:if test="${search ne null }">
						<div class="search-results-table">
							<table class="table table-striped table-bordered table-condensed" style="margin-bottom: 0px;">
								<!-- Search Table Header Start -->
								<tr>
									<td colspan="8" class="search-table-header-column"><span
										class="glyphicon glyphicon-search search-table-icon-text"></span>
										<span>Search</span></td>
								</tr>
								</table>
								<!-- Search Table Header End -->
								<!-- Search Table Content Start -->
								<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
								<thead>
								<tr>
									<th>Transaction Date Time</th>
									<th>Merchant Code</th>
									<th>Terminal Code</th>
									<th>Transaction Type</th>
									<th>Card</th>
									<th>Transaction Amount</th>
									<th>Status</th>
								</tr>
								</thead>
								<c:choose>
									<c:when test="${!(fn:length(transactions) eq 0) }">
										<c:forEach items="${transactions}" var="txnData">
											<tr>
												<td>${txnData.transactionDate }</td>
												<td>${txnData.merchant_code }</td>
												<td>${txnData.terminal_id }</td>
												<td>${txnData.transaction_type }</td>
												<td>${txnData.maskCardNumber }</td>
												<td>${txnData.transactionAmount }</td>
												<td>${txnData.statusMessage }</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="6" style="color: red;">No Transactions
												Found</td>
										</tr>
									</c:otherwise>
								</c:choose>
								</table>
							<table class="table table-striped table-bordered table-condensed">
								<tr class="table-footer-main">
									<td colspan="10" class="search-table-header-column">
										<div class="col-sm-12">
											<div class="col-sm-4">
												<div class="btn-toolbar" role="toolbar">
													<div class="btn-group custom-table-footer-button">
														<button type="button" class="btn btn-default">
															<img src="../images/excel.png">
														</button>
														<button type="button" class="btn btn-default">
															<img src="../images/pdf.png">
														</button>
														<button type="button" class="btn btn-default">
															<img src="../images/print-icon.png">
														</button>
													</div>
												</div>
											</div>
											<div class="col-sm-8">
												<!-- <ul class="pagination custom-table-footer-pagination">
												<li><a href="#">&laquo;</a></li>
												<li><a href="#">1</a></li>
												<li><a href="#">2</a></li>
												<li><a href="#">3</a></li>
												<li><a href="#">4</a></li>
												<li><a href="#">5</a></li>
												<li><a href="#">&raquo;</a></li>
											</ul>	 -->
											</div>
										</div>
									</td>
								</tr>
								<!-- Search Table Content End -->
							</table>
						</div>
					</c:if>
					<!-- Search Table Block End -->
				</div>
			</article>
			<!--Article Block End-->
			<div class="footer-container">
				<jsp:include page="footer.jsp"></jsp:include>
			</div>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script src="../js/sortable.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script src="../js/common-lib.js"></script>

	<script>
	$(document).ready(function() {
		$("#navListId4").addClass("active-background");
		$(".focus-field").click(function() {
			$(this).children('.effectiveDate').focus();
		});

		$('.effectiveDate').datetimepicker({
			timepicker : false,
			format : 'd/m/Y',
			formatDate : 'd/m/Y',
		});
	});

	</script>
	<script>
		/* Select li full area function Start */
		$("li").click(function() {
			window.location = $(this).find("a").attr("href");
			return false;
		});
		$(document).ready(function() {
			highlightMainContent('navListId4');
		});
		
		$(document).ready(function() {
			/* Table Sorter includes Start*/
			$(function() {
				
					  // call the tablesorter plugin
					  $('#serviceResults').sortable({
						
						 divBeforeTable: '#divbeforeid',
						divAfterTable: '#divafterid',
						initialSort: false,
						locale: 'th',
						//negativeSort: [1, 2]
					});
			});
			});
	</script>
</body>
</html>