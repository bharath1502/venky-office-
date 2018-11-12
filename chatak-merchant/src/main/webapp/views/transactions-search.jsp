<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.pg.constants.PGConstants"%>
<%@ page import="com.chatak.merchant.constants.StatusConstants"%>
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
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css" />
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<spring:message code="process.submerchant.transactions.id"
		var="processSubMerchantTransactions"></spring:message>
	<spring:message code="execute.submerchant.transactions.id"
		var="executeSubMerchantTransactions"></spring:message>
	<spring:message code="refund.submerchant.transactions.id"
		var="refundSubMerchantTransactions"></spring:message>
	<spring:message code="void.submerchant.transactions.id"
		var="voidSubMerchantTransactions"></spring:message>

	<c:set value="${sessionScope.existingFeatuesData}"
		var="existingFeatures"></c:set>
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<jsp:include page="header.jsp"></jsp:include>
			<!--Navigation Block Start -->
			<nav class="col-sm-12 nav-bar" id="main-navigation">
				<jsp:include page="navigation-panel.jsp"></jsp:include>
				<%-- <jsp:include page="main-navigation.jsp"></jsp:include> --%>
			</nav>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message
								code="transactions-search.label.transactions" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message
								code="transactions-search.label.details" /></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message
								code="transactions-search.label.transactionstab" /></a>
					</div>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionMsg" data-toggle="tooltip"
										data-placement="top" title="">
										<span class="red-error">&nbsp;${error}</span> <span
											class="red-error redirectionError">&nbsp;${redirectionError}</span>
										<span class="green-error">&nbsp;${success}</span> <span
											class="green-error">&nbsp;${sucess}</span>
									</div>
								</div>
								<form action="transactions-search" name="resubmitForm"
									method="get"></form>
								<form action="get-transaction-report" name="downloadReport"
									method="post">
									<input type="hidden" id="downloadPageNumberId"
										name="downLoadPageNumber" /> <input type="hidden"
										id="downloadTypeId" name="downloadType" /> <input
										type="hidden" id="totalRecords" name="totalRecords" /> <input
										type="hidden" id="downloadAllRecords"
										name="downloadAllRecords" />
										<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form>

								<form action="do-transaction-void" method="post"
									name="voidIndividualTransaction">
									<input type="hidden" name="transactionID" id="transactionID" />
									<input type="hidden" name="merchantId" id="merchantID" /> <input
										type="hidden" name="terminalId" id="terminalID" />
										<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form>

								<form action="do-transaction-refund" name="doRefundForm"
									method="post">
									<input type="hidden" name="getRefundTxnId" id="getRefundTxnId" />
									<input type="hidden" name="merchantId" id="merchantRefundID" />
									<input type="hidden" name="terminalId" id="terminalRefundID" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form>

								<form action="process-bulk-settlement-action" method="post"
									name="bulkSettlement">
									<input type="hidden" name="requestObject" id="requestObjectId" />
									<input type="hidden" name="status" id="statusId" /> <input
										type="hidden" id="removedTxns" name="removedTxns" />
										<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form>
								<form:form action="process-settlement-action"
									modelAttribute="settlementDto" name="processAction" method="post">
									<form:hidden path="merchantId" id="merchantId" />
									<form:hidden path="terminalId" id="terminalId" />
									<form:hidden path="txnId" id="txnId" />
									<form:hidden path="txnType" id="txnType" />
									<form:hidden path="settlementStatus" id="settlementStatus" />
									<form:hidden path="comments" id="comments" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
								</form:form>

								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="searchTransaction" modelAttribute="transaction"
									name="transaction">
									<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="transactions-search.label.txnid" /></label>
													<form:input path="transactionId" id="transactionId"
														cssClass="form-control"
														onkeypress="return amountValidate(this,event)" onblur="clientValidation('transactionId','gateway_id','transactionIdError')"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="transactionIdError" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="transactions-search.label.processortxnid" /></label>
													<form:input path="processCode" id="processCode"
														cssClass="form-control" onblur="clientValidation('processCode','mobile_optional','processCodeError')"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="processCodeError" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="transactions-search.label.cardnumber" /></label>
													<form:input path="cardNumber" id="cardNumber"
														cssClass="form-control" maxlength="4" onblur="validateCardNum()"
														onkeypress="return numbersonly(this,event)" />
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span class="red-error" id="cardNumberErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="transactions-search.label.cardholdername" /></label>
													<form:input path="cardHolderName" id="cardHolderName"
														cssClass="form-control" onkeydown="validateSpace(this)" onblur="clientValidation('cardHolderName','passport_number','cardHolderNameError')"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="cardHolderNameError" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="transactions-search.label.fromdate" /></label>
													<div class="input-group focus-field">
														<form:input path="from_date" id="fromDate"
															cssClass="form-control effectiveDate" />
														<span class="input-group-addon"><span
															class="glyphicon glyphicon-calendar"></span></span>
													</div>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span class="red-error" id="tranFromDateErrorDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="transactions-search.label.todate" /></label>
													<div class="input-group focus-field">
														<form:input path="to_date"
															cssClass="form-control effectiveDate" id="toDate" />
														<span class="input-group-addon"><span
															class="glyphicon glyphicon-calendar"></span></span>
													</div>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="transactions-search.label.fromamtrange" /></label>
													<form:input path="fromAmtRange" id="fromAmtRange"
														cssClass="form-control" maxlength="10"
														onkeypress="return numbersonly(this,event)" onblur="clientValidation('fromAmtRange','amount_False','fromAmtRangeError')"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="fromAmtRangeError" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="transactions-search.label.toamtrange" /></label>
													<form:input path="toAmtRange" cssClass="form-control"
														id="toAmtRange" maxlength="10"
														onkeypress="return numbersonly(this,event)" onblur="clientValidation('toAmtRange','amount_False','toAmtRangeError')"/>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="toAmtRangeError" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="transactions-search.label.txntype" /></label>
													<form:select path="transaction_type" id="transactionType"
														cssClass="form-control">
														<form:option value="">..:<spring:message
																code="transactions-search.label.select" />:..</form:option>
														<form:option value="sale">
															<spring:message code="transactions-search.label.sale" />
														</form:option>
														<form:option value="refund">
															<spring:message code="transactions-search.label.refund" />
														</form:option>
														<form:option value="BAL ENQ">
															<spring:message code="reports.option.balance" />
														</form:option>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="transactions-search.label.transactions" /></label>
													<form:select path="status" id="status"
														cssClass="form-control">
														<form:option value="">..:<spring:message
																code="transactions-search.label.select" />:..</form:option>
														<form:option value="0">
															<spring:message code="transactions-search.label.approved" />
														</form:option>
														<form:option value="1">
															<spring:message code="transactions-search.label.declined" />
														</form:option>
														<form:option value="2">
															<spring:message code="transactions-search.label.failed" />
														</form:option>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
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
													onclick="return searchValidationForTransaction();validateCardNum();"
													onclick="return trimUserData();validateFromAndToDates()"
													value="<spring:message code="transactions-search.label.searchbutton"/>">
												<input type="button" class="form-control button pull-right"
													value="<spring:message code="transactions-search.label.resetbutton"/>"
													onclick="resetTxnSearch()">
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
					<form action="getTransactions" name="paginationForm" method="post"
						id="paginationForm">
						<input type="hidden" id="pageNumberId" name="pageNumber" /> <input
							type="hidden" id="totalRecordsId" name="totalRecords" /> <input
							type="hidden" id="selectedTxnsReqObj" name="requestObject" /> <input
							type="hidden" id="removedTxns" name="removedTxns" />
							<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form>
					<form action="editMerchant" name="editMercahntForm" method="post">
						<input type="hidden" id="getMerchantId" name="getMerchantId" />
						<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form>
					<!-- Search Table Block Start -->
						<div class="search-results-table">
							<c:if test="${not empty accountDetails.entityId }">

								<c:catch var="catchException">

									<fmt:setLocale value="en_US" />

									<div
										style="display: inline-block; width: 203px; color: rgb(73, 105, 175);">
										<spring:message
											code="transactions-search.label.currentbalance" />
									</div>: <c:choose>
										<c:when test="${accountDetails.currentBalance lt 0}">
											<span style="color: red;">
											${currencyAlpha} <fmt:formatNumber	value="${accountDetails.currentBalance/100}" /></span>
											<br>
										</c:when>
										<c:otherwise>
											<span>
											${currencyAlpha} <fmt:formatNumber value="${accountDetails.currentBalance/100}" /></span>
											<br>
										</c:otherwise>
									</c:choose>

									<div
										style="display: inline-block; width: 203px; color: rgb(73, 105, 175);">
										<spring:message
											code="transactions-search.label.avaliablebalance" />
									</div>: <c:choose>
										<c:when test="${accountDetails.availableBalance lt 0}">
											<span style="color: red;">
											${currencyAlpha} <fmt:formatNumber value="${accountDetails.availableBalance/100}" /></span>
											<br>
										</c:when>
										<c:otherwise>
											<span>
											${currencyAlpha} <fmt:formatNumber value="${accountDetails.availableBalance/100}" /></span>
											<br>
										</c:otherwise>
									</c:choose>
									<div
										style="display: inline-block; width: 203px; color: rgb(73, 105, 175);">
										<spring:message
											code="transactions-search.label.merchantrevenuefee" />
									</div>:
								<c:choose>
										<c:when test="${accountDetails.feeBalance lt 0}">
											<span style="color: red;">
											${currencyAlpha} <fmt:formatNumber value="${accountDetails.feeBalance/100}" /></span>
											<br>
										</c:when>
										<c:otherwise>
											<span>
											${currencyAlpha} <fmt:formatNumber value="${accountDetails.feeBalance/100}" /></span>
										</c:otherwise>
									</c:choose>
								</c:catch>
								<c:if test="${catchException != null}">
									<p>
										<spring:message code="transactions-search.label.exceptionis" />
										: ${catchException} <br />
										<spring:message code="transactions-search.label.exception" />
										: ${catchException.message}
									</p>
								</c:if>
								<br>
								<br>
							</c:if>
							<!-- Search Table Header End -->
							<!-- Search Table Content Start -->
							<c:if test="${flag ne false }">
							<table class="table table-striped table-bordered table-condensed"
								style="margin-bottom: 1px;">
								<!-- Search Table Header Start -->
								<tr>
									<td colspan="13" class="search-table-header-column"><span
										class="glyphicon glyphicon-search search-table-icon-text"></span>
										<span><spring:message code="transactions-search.label.searchbutton" /></span> <span
										style="position: absolute; width: 500px; text-align:right;"><spring:message
												code="search-sub-merchant.lable.totalcount" /> : <label
											id="totalCount">${totalRecords}</label></span></td>
									<td colspan="6" class="search-table-header-column"><span
										class="pull-right"> <input type="button"
											class="txn-button executeAll"
											value="<spring:message code="transactions-search.label.executeall"/>" />
											<input type="button" class="txn-button processAll"
											value="<spring:message code="transactions-search.label.processall"/>" />
											<input type="button" class="txn-button cancelAll"
											value="<spring:message code="transactions-search.label.cancelall"/>" />
									</span></td>
								</tr>
							</table>
							<table id="serviceResults"
								class="table table-striped table-bordered table-responsive table-condensed tablesorter" style="word-break: break-all;">
								<thead>
									<tr>
										<th style="width: 80px;"><spring:message
												code="dash-board.label.transactiontime" /></th>
										<th style="width: 80px;"><spring:message
												code="merchant.common-deviceLocalTxnTime" /></th>
										<th style="width: 100px;"><spring:message
												code="transactions-search.label.txnid" /></th>
										<th style="width: 50px;"><spring:message
												code="reports.label.balancereports.merchantorsubmerchantName" /></th>
										<th style="width: 50px;"><spring:message
												code="reports.label.balancereports.manualtransactions.merchantorsubmerchantcode" /></th>
										<th style="width: 50px;"><spring:message
												code="transactions-search.label.type" /></th>
										<th style="width: 40px;"><spring:message
												code="search-sub-merchant.label.currencycode" /></th>
										<th style="width: 40px;"><spring:message
												code="transactions-search.label.amount" /></th>
										<th style="width: 40px;"><spring:message
												code="transactions-search.label.totaltxnamt" /></th>
										<th style="width: 100px;"><spring:message
												code="transactions-search.label.proctxnid" /></th>
										<th style="width: 60px;"><spring:message
												code="transactions-search.label.cardnumber" /></th>
										<th style="width: 45px;"><spring:message
												code="transactions-search.label.txntype" /></th>
										<th style="width: 50px;"><spring:message
												code="transactions-search.label.status" /></th>
									</tr>
								</thead>
								<c:set value="${loginResponse.parentMerchantId}"
									var="parentMerchantId"></c:set>
								<c:choose>
									<c:when test="${!(fn:length(transactions) eq 0) }">
										<c:forEach items="${transactions}" var="transaction">
											<tr data-txn-obj='${transaction.txnJsonString}'
												id='${transaction.transactionId }'>
												<td>${transaction.transactionDate }</td>
												<td>${transaction.deviceLocalTxnTime}<c:if
														test="${ not empty transaction.timeZoneOffset}">
														<br>(${transaction.timeZoneOffset})
													</c:if>
												</td>
												<td class="alignright"><span class="txn-id">${transaction.transactionId }</span>

													<c:choose>
														<c:when
															test="${parentMerchantId eq null || empty parentMerchantId}">
															<c:choose>
																<c:when
																	test="${(transaction.merchantType == 'Merchant')}">
																	<c:if
																		test="${(transaction.merchantSettlementStatus == 'Processing' || transaction.merchantSettlementStatus == 'Pending')  && (transaction.refundStatus == null || transaction.refundStatus == 0) }">
																		<input type="button" name="Refund"
																			class="txn-button refund"
																			value="<spring:message code="transactions-search.label.refundbutton"/>"
																			onclick="doTransactonRefund('${transaction.transactionId}','${transaction.merchant_code}','${transaction.terminal_id }')" />
																	</c:if>
																	<c:if
																		test="${transaction.transaction_type == 'sale' && transaction.merchantSettlementStatus == 'Executed'  && (transaction.refundStatus == null || transaction.refundStatus == 0)}">
																		<input type="button" name="Refund"
																			class="txn-button refund"
																			value="<spring:message code="transactions-search.label.refundbutton"/>"
																			onclick="doTransactonRefund('${transaction.transactionId}','${transaction.merchant_code}','${transaction.terminal_id }')" />
																	</c:if>
																</c:when>
																<c:otherwise>
																	<c:choose>
																		<c:when
																			test="${fn:contains(existingFeatures,voidSubMerchantTransactions) && (transaction.merchantSettlementStatus == 'Processing' || transaction.merchantSettlementStatus == 'Pending')  && (transaction.refundStatus == null || transaction.refundStatus == 0)}">
																			<input type="button" name="Refund"
																				class="txn-button refund"
																				value="<spring:message code="transactions-search.label.refundbutton"/>"
																				onclick="doTransactonRefund('${transaction.transactionId}','${transaction.merchant_code}','${transaction.terminal_id }')" />
																		</c:when>
																		<c:when
																			test="${fn:contains(existingFeatures,refundSubMerchantTransactions) && (transaction.transaction_type == 'sale' && transaction.merchantSettlementStatus == 'Executed')  && (transaction.refundStatus == null || transaction.refundStatus == 0)}">
																			<input type="button" name="Refund"
																				class="txn-button refund"
																				value="<spring:message code="transactions-search.label.refundbutton"/>"
																				onclick="doTransactonRefund('${transaction.transactionId}','${transaction.merchant_code}','${transaction.terminal_id }')" />
																		</c:when>
																		<c:otherwise>

																		</c:otherwise>
																	</c:choose>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when
																	test="${transaction.merchantSettlementStatus == 'Processing' || transaction.merchantSettlementStatus == 'Pending'  && (transaction.refundStatus == null || transaction.refundStatus == 0)}">
																	<input type="button" name="Refund"
																		class="txn-button refund"
																		value="<spring:message code="transactions-search.label.refundbutton"/>"
																		onclick="doTransactonRefund('${transaction.transactionId}','${transaction.merchant_code}','${transaction.terminal_id }')" />
																</c:when>
																<c:when
																	test="${transaction.transaction_type == 'sale' && transaction.merchantSettlementStatus == 'Executed'  && (transaction.refundStatus == null || transaction.refundStatus == 0)}">
																	<input type="button" name="Refund"
																		class="txn-button refund"
																		value="<spring:message code="transactions-search.label.refundbutton"/>"
																		onclick="doTransactonRefund('${transaction.transactionId}','${transaction.merchant_code}','${transaction.terminal_id }')" />
																</c:when>
																<c:otherwise>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>
													</td>
												<td><div class="feeDescDiv">${transaction.merchantBusinessName }</div></td>
												<td class="alignright">${transaction.merchant_code }</td>
												<td>${transaction.merchantType }</td>
												<td class="alignright">${transaction.localCurrency }</td>
												<td class="alignright">${transaction.transactionAmount }</td>
												<td class="alignright">${transaction.txn_total_amount}</td>
												<c:choose>
													<c:when test="${(transaction.ref_transaction_id) ne null }">
														<td class="alignright">${transaction.ref_transaction_id }</td>
													</c:when>
													<c:otherwise>
														<td>NA</td>
													</c:otherwise>
												</c:choose>
												<td class="alignright"><div class="feeDescDiv">${transaction.maskCardNumber }</div></td>
												<td><div class="feeDescDiv">${fn:toUpperCase(transaction.transaction_type) }</div></td>
												<td>${transaction.status }</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when
												test="${transactionReqObject.toGetCurrentDayTxns == true}">
												<tr>
													<td colspan="14" style="color: red;"><spring:message
															code="transactions-search.label.todaynotransactions" /></td>
												</tr>
											</c:when>
											<c:otherwise>
												<tr>
													<td colspan="14" style="color: red;"><spring:message
															code="transactions-search.label.notransactions" /></td>
												</tr>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</table>
							<table class="table table-striped table-bordered table-condensed">
								<c:if test="${ !(fn:length(transactions) eq 0)}">
									<tr class="table-footer-main">
										<td colspan="12" class="search-table-header-column">
											<div class="col-sm-12">
												<div class="col-sm-3">
													<div class="btn-toolbar" role="toolbar">
														<div class="btn-group custom-table-footer-button">
															<a
																href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.XLS_FILE_FORMAT%>', ${totalRecords})">
																<button type="button" class="btn btn-default">
																	<img src="../images/excel.png">
																</button>
															</a> <a
																href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.PDF_FILE_FORMAT%>', ${totalRecords})">
																<button type="button" class="btn btn-default">
																	<img src="../images/PDF.png">
																</button>
															</a> <a> <input type="checkbox" class="autoCheck check"
																id="totalRecordsDownload"> <spring:message
																	code="common.label.downloadall" />
															</a>
														</div>
													</div>
												</div>
												<div class="col-sm-9">

													<ul class="pagination custom-table-footer-pagination">
														<c:if test="${portalListPageNumber gt 1}">
															<li onclick="bindSelectedTxns();"><a
																href="javascript:getPortalOnPageWithRecords('1','${totalRecords}')">
																	&laquo;</a></li>
															<li onclick="bindSelectedTxns();"><a
																href="javascript:getPortalPrevPageWithRecords('${portalListPageNumber }','${totalRecords}')">
																	&lsaquo; </a></li>
														</c:if>

														<c:forEach var="page" begin="${beginPortalPage }"
															end="${endPortalPage}" step="1" varStatus="pagePoint">
															<c:if test="${portalListPageNumber == pagePoint.index}">
																<li
																	class="${portalListPageNumber == pagePoint.index?'active':''}">
																	<a href="javascript:">${pagePoint.index}</a>
																</li>
															</c:if>
															<c:if test="${portalListPageNumber ne pagePoint.index}">
																<li class="" onclick="bindSelectedTxns();"><a
																	href="javascript:getPortalOnPageWithRecords('${pagePoint.index }','${totalRecords}')">${pagePoint.index}</a>
																</li>
															</c:if>
														</c:forEach>

														<c:if test="${portalListPageNumber lt portalPages}">
															<li onclick="bindSelectedTxns();"><a
																href="javascript:getPortalNextPageWithRecords('${portalListPageNumber }','${totalRecords}')">
																	&rsaquo;</a></li>
															<li onclick="bindSelectedTxns();"><a
																href="javascript:getPortalOnPageWithRecords('${portalPages }','${totalRecords}')">&raquo;
															</a></li>
														</c:if>

													</ul>

												</div>
											</div>
										</td>
									</tr>
								</c:if>
								</c:if>
								<!-- Search Table Content End -->
							</table>
						</div>
					<!-- Search Table Block End -->
				</div>
				<div id="txn-popup" class="txn-popup"></div>
				<div id="my_popup" class="locatioin-list-popup"
					style="display: none; width: 880px;">
					<span class="glyphicon glyphicon-remove" onclick="closePopup()"></span>
					<fieldset class="col-sm-12 padding0">
						<label data-toggle="tooltip" data-placement="top" title=""><spring:message
								code="transactions-search.label.comments" /><span
							class="required-field">*</span></label>
						<textarea id="comment" name="comment"
							class="form-control textareaResize"
							onblur="return validateComment()"></textarea>
						<div class="discriptionErrorMsg" data-toggle="tooltip"
							data-placement="top" title="">
							<span id="commentErr" class="red-error">&nbsp;</span>
						</div>
					</fieldset>
					<!-- Form Button Information Start -->
					<div class="col-sm-12">
						<input type="button" class="form-control button pull-right"
							value="<spring:message code="transactions-search.label.submitbutton"/>"
							onclick="return processSubmit()"> <input type="button"
							class="form-control button pull-right"
							value="<spring:message code="transactions-search.label.cancelbutton"/>"
							onclick="closePopup()">
					</div>
					<!-- Form Button Information End -->
				</div>

				<div id="select_popup" class="locatioin-list-popup"
					style="display: none; width: 880px;">
					<span class="glyphicon glyphicon-remove"
						onclick="closeSelectPopup()"></span>
					<fieldset class="col-sm-12 padding0">
						<label data-toggle="tooltip" data-placement="top" title=""><spring:message
								code="transactions-search.label.selectitem" /><span
							class="required-field"></span></label>
					</fieldset>
					<!-- Form Button Information Start -->
					<div class="col-sm-12">
						<input type="button" class="form-control button pull-right"
							value="<spring:message code="transactions-search.label.ok"/>"
							onclick="closeSelectPopup()">
					</div>
					<!-- Form Button Information End -->
				</div>
			</article>
			<!--Article Block End-->
			<jsp:include page="footer.jsp"></jsp:include>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/jquery.min.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/virtual-terminal.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/sortable.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/utils.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script>
	
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
	
		/* Common Navigation Include Start */
		$(function() {
			/* $( "#main-navigation" ).load( "main-navigation.html" );		 */
		});
		/* Common Navigation Include End */
		/* DatePicker Javascript Strat*/
		var selectedTxns;
		var removedTxn = [];
		$(document)
				.ready(
						function() {

							var errorMsg = getParameterByName('redirectionError');

							if (errorMsg.length > 0) {
								$('.redirectionError').text(errorMsg);
								var url = window.location.href.replace(
										location.search, '');
								history.pushState('', 'New Page Title', url);
							}

							highlightMainContent('navListId6');

							$(".focus-field").click(function() {
								$(this).children('.effectiveDate').focus();
							});

							$('.effectiveDate').datetimepicker({
								timepicker : false,
								format : 'd/m/Y',
								formatDate : 'd/m/Y',
								maxDate:new Date()
							});
							($(".execute").length == 0) ? $(".executeAll")
									.hide() : $(".executeAll").show();
							($(".process").length == 0) ? $(".processAll")
									.hide() : $(".processAll").show();
							($(".cancel").length == 0) ? $(".cancelAll").hide()
									: $(".cancelAll").show();

							$('.executeAll, .processAll, .cancelAll')
									.on(
											'click',
											function() {
												if ($(".transaction:checked").length == 0) {
													openSelectPopup();
													return;
												}

												get("requestObjectId").value = getSelectedTxns();
												if ($(this).hasClass(
														'executeAll')) {
													get("statusId").value = "Executed";
												} else if ($(this).hasClass(
														'processAll')) {
													get("statusId").value = "Processing";
												} else if ($(this).hasClass(
														'cancelAll')) {
													get("statusId").value = "Rejected";
												}
												document.forms["bulkSettlement"]
														.submit();

											});

							// add multiple select / deselect functionality
							$("#selectall")
									.click(
											function() {
												if ($(this).prop("checked") == false) {
													$('.transaction')
															.each(
																	function() {
																		removedTxn
																				.push(JSON
																						.parse($(
																								this)
																								.val()).txnId);
																	});
													$('.transaction').attr(
															'checked', false);
												} else {
													$('.transaction').prop(
															'checked',
															this.checked);
													removedTxn = [];
												}

											});

							// if all checkbox are selected, check the selectall checkbox
							// and viceversa
							$(".transaction")
									.click(
											function() {
												if ($(".transaction").length == $(".transaction:checked").length) {
													$("#selectall").attr(
															"checked",
															"checked");
												} else {
													$("#selectall").attr(
															'checked', false);
												}

												if (!$(this).is(':checked')) {
													removedTxn
															.push(JSON
																	.parse($(
																			this)
																			.val()).txnId);
												}
											});

							var currentTxns = "${selected_bulk_settlement_list}"
									+ "";
							selectedTxns = JSON.stringify(currentTxns);

							if (selectedTxns != undefined
									&& selectedTxns.trim().length > 0) {
								var selectedTxnxObj = JSON.parse(selectedTxns);
								var txnArray = selectedTxnxObj.actionDTOs;

								var txnId;
								for (var i = 0; i < txnArray.length; i++) {
									txnId = txnArray[i].txnId.trim();
									$('#' + txnId).find('.transaction').attr(
											'checked', true);
								}

								if ($(".transaction").length > 0
										&& $(".transaction").length == $(".transaction:checked").length) {
									$("#selectall").attr("checked", "checked");
								}
							}
						});

		function closeSelectPopup() {
			$('#select_popup').popup("hide");
		}
		function openSelectPopup() {
			$('#select_popup').popup("show");
		}

		function closePopup() {
			$('#my_popup').popup("hide");
		}

		/* DatePicker Javascript End*/

		function processSubmit() {
			get('comments').value = get('comment').value;
			if (!validateComment()) {
				return false;
			} else {
				document.forms["processAction"].submit();
				return true;
			}
		}

		function process(transactionId, merchant_code, terminal_id,
				transaction_type, status) {

			get('merchantId').value = merchant_code;
			get('terminalId').value = terminal_id;
			get('txnId').value = transactionId;
			get('txnType').value = transaction_type;
			get('settlementStatus').value = status;
			document.forms["processAction"].submit();

		}
		/* DatePicker Javascript End*/

		function getSelectedTxns() {
			var names = [];
			$('.transaction:checked').each(function() {
				names.push(this.value);
			});
			return names;
		}

		function bindSelectedTxns() {
			var names = getSelectedTxns();
			if (selectedTxns != undefined && selectedTxns.trim().length > 0) {
				var selectedTxnxObj = JSON.parse(selectedTxns);
				var txnArray = selectedTxnxObj.actionDTOs;
				if (txnArray != undefined && txnArray.length > 0) {
					var txnId;
					for (var i = 0; i < txnArray.length; i++) {
						txnId = txnArray[i].txnId.trim();
						var txn;
						for (var j = 0; j < names.length; j++) {
							txn = (names[j] != undefined ? JSON.parse(names[j])
									: "");
							if (txnId == txn.txnId) {
								delete names[j];
								break;
							}
						}
					}
				}
				$('#paginationForm').find('#selectedTxnsReqObj').val(
						names.filter(Boolean));
			} else {
				$('#paginationForm').find('#selectedTxnsReqObj').val(names);
			}

			$('#removedTxns').val(removedTxn);
		}
	</script>
	<script type="text/javascript">
		function resetTxnSearch() {
			document.forms["resubmitForm"].submit();
		}

		function getParameterByName(name) {
			name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
			var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), results = regex
					.exec(location.search);
			return results === null ? "" : decodeURIComponent(results[1]
					.replace(/\+/g, " "));
		}
	</script>
	<script src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
</body>
</html>
