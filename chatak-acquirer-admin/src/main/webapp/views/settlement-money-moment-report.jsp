<!DOCTYPE html>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.Calendar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.chatak.pg.util.Constants"%>
<%@ page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<%
	int year = Calendar.getInstance().get(Calendar.YEAR);
%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><spring:message code="common.lable.title" /></title>
<!-- Bootstrap -->
<link rel="icon" href="../images/favicon.png" type="image/png">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css" />
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">

			<!--Navigation Block Start -->
			<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message
								code="home.label.incomingsettlementreport" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message
								code="show-all-pending-merchants.label.details" /></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="marginL40">
						<%-- 	<c:if
							test="${fn:contains(existingFeatures,programmanagerView)||fn:contains(existingFeatures,programmanagerEdit)||fn:contains(existingFeatures,programmanagerSuspend)||fn:contains(existingFeatures,programmanagerActivate)}">
							<div class="tab-header-container">
								<a href="showProgramManager"><spring:message
										code="common.label.search" /> </a>
							</div>
						</c:if> --%>
						<c:if test="${fn:contains(existingFeatures,programmanagerCreate)}">
							<div class="tab-header-container active-background">
								<a><spring:message code="common.label.create" /></a>
							</div>
						</c:if>
					</div>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<form:form action="getAllMatchedTxnsByPgTxnId" name="getAllMatchedTxnsByPgTxnId"
								method="post">
								<input type="hidden" id="pgTxnIds" name="pgTxnIds" />
								<input type="hidden" id="getModelView" name="getModelView" />
								<input type="hidden" name="CSRFToken" value="${tokenval}">
							</form:form>
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<span class="green-error" id="sucessDiv">${sucess}</span> <span
									class="red-error" id="errorDiv">${error}</span>

								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
							
								<form:form action="executeSettlementData"
									name="executeSettlementData" method="post">
									<input type="hidden" id="timeZoneOffset" name="timeZoneOffset" />
                                    <input type="hidden" id="timeZoneRegion" name="timeZoneRegion" />
									<input type="hidden" name="CSRFToken" value="${tokenval}">
									<fieldset class="col-md-3 col-sm-6">
										<label><spring:message code="admin.label.pmamount" /><span
											class="required-field">*</span></label>
											<span ><fmt:formatNumber value="${pmDebitAmount}" pattern="<%=Constants.SETTELEMENT_AMOUNT_FORMAT %>"/></span>
									</fieldset>

									<div class="search-results-table">
								<table class="table table-striped table-bordered table-condensed" style="margin: 1px;">
							<tr>
								<td colspan="11" class="search-table-header-column"><span
									class="glyphicon glyphicon-search search-table-icon-text"></span>
									<span><spring:message code="home.label.isorevenue"/></span>
								</td>
							</tr>
							</table>
					<table class="table table-striped table-bordered table-responsive table-condensed tablesorter">
						<thead>
							<tr>
								<th style="width: 150px;"><spring:message code="admin.iso.label.message"/></th>
								<th style="width: 150px;"><spring:message code="bank.label.bankname"/></th>
								<th style="width: 150px;"><spring:message code="merchant.label.bankaccountnumber"/></th>
								<th style="width: 150px;"><spring:message code="merchant.label.bankroutingnumber"/></th>
								<th style="width: 150px;"><spring:message code="home.label.grossamount"/>(${isoRevenueCurrency})</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${!(fn:length(isoDetailsList) eq 0)}">
										<c:forEach items="${isoDetailsList}" var="isoDetails">
											<tr>
												<td><a href="javascript:showAllTxnsByPGTxnIds('${isoDetails.pGTransactionIds}','MONEY_MOMENT');" style="text-decoration: underline;">${isoDetails.isoName}</a></td>
									            <td>${isoDetails.bankName}</td>
									            <td>${isoDetails.bankAccNum}</td>
									            <td>${isoDetails.routingNumber}</td>
									            <td><fmt:formatNumber value ="${isoDetails.amount}" pattern="<%=Constants.SETTELEMENT_AMOUNT_FORMAT %>"/></td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr><td colspan="7" style="color: red; text-align: center;"><spring:message code="home.label.norecordsfound"/></td></tr>
									</c:otherwise>
							</c:choose>
						</tbody>
					</table>						
						<table class="table table-striped table-bordered table-condensed" style="margin: 1px;">
							<tr>
								<td colspan="11" class="search-table-header-column"><span
									class="glyphicon glyphicon-search search-table-icon-text"></span>
									<span><spring:message code="home.label.isomappedtxn"/></span>
								</td>
							</tr>
							</table>
					<table class="table table-striped table-bordered table-responsive table-condensed tablesorter">
						<thead>
							<tr>
	                           <th style="width: 150px;"><spring:message code="dcc.label.merchantname"/></th>						
								<th style="width: 150px;"><spring:message code="reports.label.transactions.merchantcode"/></th>
								<th style="width: 150px;"><spring:message code="bank-file-exportutil-bankname"/></th>
								<th style="width: 150px;"><spring:message code="merchant.label.bankaccountnumber"/></th>
								<th style="width: 150px;"><spring:message code="merchant.label.bankroutingnumber"/></th>
								<th style="width: 150px;"><spring:message code="admin.label.merchantamount"/>(${isoCurrency})</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${!(fn:length(isoMappedMerchantTotalRevenue) eq 0)}">
										<c:forEach items="${isoMappedMerchantTotalRevenue}" var="isoMappedTxn">
											<tr>
											     <td>${isoMappedTxn.businessName}</td>
												<td><a href="javascript:showAllTxnsByPGTxnIds('${isoMappedTxn.pGTransactionIds}','MONEY_MOMENT');" style="text-decoration: underline;">${isoMappedTxn.merchantCode}</a></td>
									            <td>${isoMappedTxn.bankNmae}</td>
									            <td>${isoMappedTxn.bankAccountNumber}</td>
									            <td>${isoMappedTxn.bankRoutingNumber}</td>
									            <td><fmt:formatNumber value="${isoMappedTxn.entityTotalRevenueAmount}" pattern = "<%=Constants.SETTELEMENT_AMOUNT_FORMAT%>"/></td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr><td colspan="7" style="color: red; text-align: center;"><spring:message code="home.label.norecordsfound"/></td></tr>
									</c:otherwise>
							</c:choose>
						</tbody>
					</table>
						<table class="table table-striped table-bordered table-condensed" style="margin: 1px;">
							<tr>
								<td colspan="11" class="search-table-header-column"><span
									class="glyphicon glyphicon-search search-table-icon-text"></span>
									<span><spring:message code="home.label.pmmappedtxn"/></span>
								</td>
							</tr>
							</table>
					<table class="table table-striped table-bordered table-responsive table-condensed tablesorter">
						<thead>
							<tr>
							     <th style="width: 150px;"><spring:message code="dcc.label.merchantname"/></th>
								<th style="width: 150px;"><spring:message code="reports.label.transactions.merchantcode"/></th>
									<th style="width: 150px;"><spring:message code="bank-file-exportutil-bankname"/></th>
								<th style="width: 150px;"><spring:message code="merchant.label.bankaccountnumber"/></th>
								<th style="width: 150px;"><spring:message code="merchant.label.bankroutingnumber"/></th>
								<th style="width: 150px;"><spring:message code="admin.label.merchantamount"/>(${pmCurrency})</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
						<c:when test="${!(fn:length(pmMappedMerchantTotalRevenue) eq 0)}">
										<c:forEach items="${pmMappedMerchantTotalRevenue}" var="pmMappedTxn">
											<tr>
											    <td>${pmMappedTxn.businessName}</td>
												<td><a href="javascript:showAllTxnsByPGTxnIds('${pmMappedTxn.pGTransactionIds}','MONEY_MOMENT');" style="text-decoration: underline;">${pmMappedTxn.merchantCode}</a></td>
												<td>${pmMappedTxn.bankNmae}</td>
												<td>${pmMappedTxn.bankAccountNumber}</td>
												<td>${pmMappedTxn.bankRoutingNumber}</td>
												<td><fmt:formatNumber value = "${pmMappedTxn.entityTotalRevenueAmount}" pattern = "<%=Constants.SETTELEMENT_AMOUNT_FORMAT%>"/></td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr><td colspan="7" style="color: red; text-align: center;"><spring:message code="home.label.norecordsfound"/></td></tr>
									</c:otherwise>
							</c:choose>
						</tbody>
					</table>
					</div>
									<!--Panel Action Button Start -->
									<div class="col-sm-12 form-action-buttons">
										<div class="col-sm-5"></div>
										<div class="col-sm-7">
											<input type="submit" class="form-control button pull-right" onclick="return setTimeZone()"
												value="<spring:message code="admin.label.confirm"/>"  >
												<!-- onclick=" return executeSettlement() -->
											<input type="button" class="form-control button pull-right"
												value="<spring:message code="reports.label.cancelbutton"/>"
												onclick="showSettlementPage()">
										</div>
									</div>
									<!--Panel Action Button End -->
								</form:form>
								<!-- Page Form End -->
							</div>
						</div>
					</div>
					<!-- Content Block End -->
				</div>
			</article>
			<!--Article Block End-->
			<jsp:include page="footer.jsp" />
		</div>
		<!--Container block End -->
	</div>
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
	<script src='https://cdnjs.cloudflare.com/ajax/libs/jstimezonedetect/1.0.4/jstz.min.js'></script>
	<script src="../js/utils.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/sorting.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/bank.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/program-manager.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		/* Select li full area function Start */
		$("li").click(function() {
			window.location = $(this).find("a").attr("href");
			return false;
		});

		$(document).ready(function() {
			if('${hashMap}' == 0){
				$("#serviceResults").hide();
			}else{
				$("#serviceResults").show();
			}if('${settlementEntityList}' == 0 && '${acquiringTransactionNotfoundIssuanceList}' == 0 && '${issuanceTransactionNotfoundAcquiringList}' == 0){
				$("#browseFileHide").show();
			}else{
				$("#browseFileHide").hide();
			}
			
			
			if ($('#autorepenish').prop('checked') == true) {
				$(".thresholdAmount").show();
			} else if ($('#autorepenish').prop('checked') == false) {
				$(".thresholdAmount").hide();
			}
		});

		$(".check").click(function() {
			if ($('#autorepenish').prop('checked') == true) {
				$(".thresholdAmount").show();
			} else if ($('#autorepenish').prop('checked') == false) {
				setValue('accountThresholdamount', '');
				setValue('sendFund', '');
				setValue('bankId', '');
				setValue('accountThresholdamount', '');
				$(".thresholdAmount").hide();
			}
		});

		/* Select li full area function End */
		/* Common Navigation Include Start */
		$(function() {
			$("#main-navigation").load("main-navigation.html");
		});
		function highlightMainContent() {
			$("#navListId2").addClass("active-background");
		}
		/* Common Navigation Include End */
	</script>
	<script>
		var MAX_PROGRAM_MANAGER_LOGO_FILE_SIZE = 1024 * 1024 * 1;

		function readImageURL(input) {
			if (!isValidImage(input.value)) {
				document.getElementById('image_div').innerHTML = webMessages.ALLOWED_IMAGES;
				return;
			}
			document.getElementById('image_div').innerHTML = '';
			if (input.files && input.files[0]) {
				if (parseInt(MAX_PROGRAM_MANAGER_LOGO_FILE_SIZE) < parseInt(input.files[0].size)) {
					document.getElementById('image_div').innerHTML = webMessages.IMAGE_SIZE;
					return;
				}
			}
		}

		function isValidImage(imageSrc) {
			var value = imageSrc.toUpperCase();
			if (value.indexOf('.PNG') != -1 || value.indexOf('.JPG') != -1
					|| value.indexOf('.JPEG') != -1
					|| value.indexOf('.GIF') != -1
					|| value.indexOf('.BMP') != -1) {
				return true;
			}
			return false;
		}

		$(document).ready(function() {
			$(".issuanceProgramManager").hide();
			$(".issuancePMlogo").hide();
			$(".acquirerCurrencyNames").hide();
			$(".acquirerBankNames").hide();
			$(".acquirerCardProgram").hide();
			
			
		});

		function closePopup() {
			$('#LogoDiv').popup("hide");
		}
		function openPopup() {
			$('#LogoDiv').popup("show");
		}
		document.getElementById("schedulerRunTime").value = "00:00:00";
		function changeProgramManager() {
			if ($('#checkDefaultProgramManager').prop('checked') == true) {
				$('#checkDefaultPMValue').val(true);
			} else {
				$('#checkDefaultPMValue').val(false);
				setDiv('sucessDiv', '');
			}
		}
		/* function executeSettlement() {
			get('programViewId').value = 12;
			//document.forms["getSettlementDetails"].submit();
		} */
		
		function showAllTxnsByPGTxnIds(pgTxnId, moneyMoment) {
			get('pgTxnIds').value = pgTxnId;
			get('getModelView').value = moneyMoment;
			document.forms["getAllMatchedTxnsByPgTxnId"].submit();
		}
		
	</script>
</body>
</html>
