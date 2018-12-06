<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.Calendar"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<%
  int year = Calendar.getInstance().get(Calendar.YEAR);
%>
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

<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->

			<!--Header Block End -->
			<!--Navigation Block Start -->
			<%-- <nav class="col-sm-12 nav-bar" id="main-navigation"><jsp:include
					page="header.jsp" /></nav> --%>
					<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="manage.label.manage"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span>
						<span class="breadcrumb-text"><spring:message code="exchange.label.rate"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="common.label.create"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<!-- <div class="tab-header-container-first">
						<a href="switch-search">Search
						</a>
					</div> -->
					<c:if test="${fn:contains(existingFeatures,exchangeRateCreate)||
					fn:contains(existingFeatures,exchangeRateEdit)||fn:contains(existingFeatures,exchangeRateDelete)}">
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="common.label.create"/></a>
					</div>
					</c:if>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error">${error}</span> <span
											class="green-error">${sucess}</span>
									</div>
								</div>
								
								<%-- <form action="getExchangeInfo" name="paginationForm" method="post">
									<input type="hidden" id="pageNumberId" name="pageNumber" /> 
									<input type="hidden" id="totalRecordsId" name="totalRecords" />
								</form> --%>
								
								<form:form action="editExchangeRate" name="editExchangeRateForm" method="post">
									<input type="hidden" id="getExchangeId" name="getExchangeId" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								
								<form:form action="deleteExchangeRate" name="deleteExchangeRateForm" method="post">
									<input type="hidden" id="getExchangeRateId" name="getExchangeRateId" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>
								
								<%-- <form action="get-exchange-report" name="downloadReport" method="post">
									<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" /> 
									<input type="hidden" id="downloadTypeId" name="downloadType" />
									<input type="hidden" id="totalRecords" name="totalRecords" />
									<input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
								</form> --%>
								
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="createExchangeRate" modelAttribute="exchangeRate" name="exchangeRate">
								<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12 paddingT20">
										<div class="row">
											<!-- Account Details Content Start -->
											<section class="field-element-row account-details-content">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="exchange.label.source"/><span class="required-field">*</span></label> 
														<form:select cssClass="form-control" path="sourceCurrency" id="sourceCurrency" onblur="validateSourceCurrency()">
														<form:option value="">.:<spring:message code="sub-merchant-account-search.label.select"/>:.</form:option>
														<c:forEach items="${currencyList}" var="currency">
														<form:option value="${currency.label}">${currency.value}</form:option>
														</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="sourceCurrencyEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="exchange.label.destination"/><span class="required-field">*</span></label> 
														<form:select cssClass="form-control" path="destCurrency" id="destCurrency" onblur="validateDestinationCurrency()">
														<form:option value="">.:<spring:message code="sub-merchant-account-search.label.select"/>:.</form:option>
														<c:forEach items="${currencyList}" var="currency">
														<form:option value="${currency.label}">${currency.value}</form:option>
														</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="destCurrencyEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="exchange.label.rate"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="exchangeRate" 
															 id="exRate" onblur="this.value=this.value.trim();validateExchangeRate()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="exRateEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="exchange.label.decimalposition"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="souCurDecPos"
															id="souCurDecPos" maxlength="5" onkeypress="return isNumberKey(event)" onblur="this.value=this.value.trim();validateSouCurDecPos()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="souCurDecPosEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="exchange.label.destinationposition"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="destCurDecPos"
															id="destCurDecPos" maxlength="5" onkeypress="return isNumberKey(event)" onblur="this.value=this.value.trim();validateDestCurDecPos()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="destCurDecPosEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="submit"
															class="form-control button pull-right acc-next"
															value='<spring:message code="common.label.create"/>' onclick="return validateCreateExchangeRate()"> <input type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.reset"/>' onclick="resetExchangeRateInfo()">  <input
															type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.cancel"/>' onclick="cancelCreateExchangeRate()"> 
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<!-- Account Details Content End -->
											</div>
										</div>	
								</form:form> 
								<!-- Page Form End -->
							</div>
						</div>
					</div>
					<!-- Content Block End -->
					
					<!-- Search Table Block Start -->
					<div class="search-results-table">
						<table class="table table-striped table-bordered table-condensed" style="margin: 1px;">
							<!-- Search Table Header Start -->
							<tr>
								<td colspan="10" class="search-table-header-column">
									<span><spring:message code="exchange.label.existingrecords"/></span>
									<span class="pull-right"><spring:message code="common.label.totalcount"/> : <label id="totalCount">${totalRecords}</label></span>
									</td>
							</tr>
							</table>
							<!-- Search Table Header End -->
							<!-- Search Table Content Start -->
							<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter">
							<thead>
							<tr>
								<th style="width: 150px;"><spring:message code="exchange.label.source"/></th>
								<th style="width: 150px;"><spring:message code="exchange.label.destination"/></th>
								<th style="width: 150px;"><spring:message code="exchange.label.rate"/></th>
								<th style="width: 150px;"><spring:message code="exchange.label.decimalposition"/></th>
								<th style="width: 150px;"><spring:message code="exchange.label.destinationposition"/></th>
								<th class="sorter-false tablesorter-header tablesorter-headerUnSorted"><spring:message code="common.label.action"/></th>
							</tr>
							</thead>
							<c:choose>
								<c:when test="${!(fn:length(exchangeRateData) eq 0) }">
									<c:forEach items="${exchangeRateData}" var="exchangeData">
										<tr>
											<td>${exchangeData.sourceCurrency }</td>
											<td>${exchangeData.destCurrency }</td>
											<td>${exchangeData.exchangeRate }</td>
											<td>${exchangeData.souCurDecPos }</td>
											<td>${exchangeData.destCurDecPos }</td>
											<td style="white-space:nowrap;">
													<a href="javascript:editExchangeRate('${exchangeData.id }')" title="Edit" class="table-actionicon-margin">
													<span class="glyphicon glyphicon-pencil"></span></a>
													<a href="javascript:deleteExchangeRate('${exchangeData.id }')" title="Delete" class="table-actionicon-margin">
													<span class="glyphicon glyphicon-trash"></span></a>
												</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="10" style="color: red;"><spring:message code="exchange.label.noexchangefound"/></td>
									</tr>
								</c:otherwise>
							</c:choose>
							</table>
					</div>
					<!-- Search Table Block End -->
				</div>
			</article>
			<!--Article Block End-->
		<footer class="footer">
				<jsp:include page="footer.jsp"></jsp:include>
			</footer>
		</div>
		<!--Container block End -->
	</div>
	
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/chatak-ajax.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/sortable.js"></script>
	<script type="text/javascript" src="../js/exchange-rate.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script>
		
		$(document).ready(function() {
			$("#navListId6").addClass("active-background");
			$(window).keydown(function(event){
			    if(event.keyCode == 13) {
			      event.preventDefault();
			      return false;
			    }
			 });
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
		
	
	
