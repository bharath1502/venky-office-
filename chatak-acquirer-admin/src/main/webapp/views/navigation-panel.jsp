<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page  import="java.util.Calendar"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%-- <%@page import="com.chatak.acquirer.admin.constant.JSPConstants"%>//need to do--%>
<%@page import="com.chatak.pg.util.Constants"%>
<head>
<!-- Bootstrap -->
<link href="../css/style.css" rel="stylesheet">
</head>
<style>
.dropdown-menu > li
{	position:relative;
	-webkit-user-select: none; /* Chrome/Safari */        
	-moz-user-select: none; /* Firefox */
	-ms-user-select: none; /* IE10+ */
	/* Rules below not implemented in browsers yet */
	-o-user-select: none;
	user-select: none;
	cursor:pointer;
}
.dropdown-menu .sub-menu {
    left: 100%;
    position: absolute;
    top: 0;
    display:none;
    margin-top: -1px;
	border-top-left-radius:0;
	border-bottom-left-radius:0;
	border-left-color:#fff;
}
.right-caret:after,.left-caret:after
 {	content:"";
    border-bottom: 5px solid transparent;
    border-top: 5px solid transparent;
    display: inline-block;
    height: 0;
    vertical-align: middle;
    width: 0;
	margin-left:5px;
}
.right-caret:after
{	border-left: 5px solid #ffaf46;
}
.left-caret:after
{	border-right: 5px solid #ffaf46;
}
</style> 
  
<spring:message code="admin.service.dashboard.feature.id" var="dashboard"></spring:message>
<spring:message code="admin.service.unblockusers.feature.id" var="UnblockUsers"></spring:message>

<!-- SETUP OPTIONS START -->

<spring:message code="admin.services.setup.feature.id" var="setup"></spring:message>

<spring:message code="admin.services.roles.feature.id" var="roles"></spring:message>
<spring:message code="admin.services.roles.create.feature.id" var="rolesCreate"></spring:message>
<spring:message code="admin.services.roles.view.feature.id" var="rolesView"></spring:message>
<spring:message code="admin.services.roles.edit.feature.id" var="rolesEdit"></spring:message>
<spring:message code="admin.services.roles.delete.feature.id" var="rolesDelete"></spring:message>

<spring:message code="admin.services.users.feature.id" var="users"></spring:message>
<spring:message code="admin.services.users.create.feature.id" var="usersCreate"></spring:message>
<spring:message code="admin.services.users.view.feature.id" var="usersView"></spring:message>
<spring:message code="admin.services.users.edit.feature.id" var="usersEdit"></spring:message>
<spring:message code="admin.services.users.delete.feature.id" var="usersDelete"></spring:message>

<spring:message code="admin.services.switch.feature.id" var="switchInfo"></spring:message>
<spring:message code="admin.services.switch.create.feature.id" var="switchCreate"></spring:message>
<spring:message code="admin.services.switch.view.feature.id" var="switchView"></spring:message>
<spring:message code="admin.services.switch.edit.feature.id" var="switchEdit"></spring:message>

<%-- <spring:message code="admin.services.ONUSBIN.feature.id" var="ONUSBIN"></spring:message>
<spring:message code="admin.services.ONUSBIN.create.feature.id" var="ONUSBINCreate"></spring:message>
<spring:message code="admin.services.ONUSBIN.edit.feature.id" var="ONUSBINEdit"></spring:message>
<spring:message code="admin.services.ONUSBIN.view.feature.id" var="ONUSBINView"></spring:message>
<spring:message code="admin.services.ONUSBIN.delete.feature.id" var="ONUSBINDelete"></spring:message> --%>

<spring:message code="admin.services.BlackListedCard.feature.id" var="BlackListedCard"></spring:message>
<spring:message code="admin.services.BlackListedCard.create.feature.id" var="BlackListedCardCreate"></spring:message>
<spring:message code="admin.services.BlackListedCard.edit.feature.id" var="BlackListedCardEdit"></spring:message>
<%-- <spring:message code="admin.services.BlackListedCard.view.feature.id" var="BlackListedCardView"></spring:message>
 --%>

<%-- <spring:message code="admin.services.paymentScheme.feature.id" var="paymentScheme"></spring:message>
<spring:message code="admin.services.paymentScheme.create.feature.id" var="paymentSchemeCreate"></spring:message>
<spring:message code="admin.services.paymentScheme.edit.feature.id" var="paymentSchemeEdit"></spring:message>
<spring:message code="admin.services.paymentScheme.view.feature.id" var="paymentSchemeView"></spring:message>
 --%>
<%-- <spring:message code="admin.services.merchantCategoryCode.feature.id" var="merchantCategoryCode"></spring:message>
<spring:message code="admin.services.merchantCategoryCode.create.feature.id" var="merchantCategoryCodeCreate"></spring:message>
<spring:message code="admin.services.merchantCategoryCode.view.feature.id" var="merchantCategoryCodeView"></spring:message>
<spring:message code="admin.services.merchantCategoryCode.edit.feature.id" var="merchantCategoryCodeEdit"></spring:message>
<spring:message code="admin.services.merchantCategoryCode.delete.feature.id" var="merchantCategoryCodeDelete"></spring:message> --%>

<%-- <spring:message code="admin.services.caPublicKeys.feature.id" var="caPublicKeys"></spring:message>
<spring:message code="admin.services.caPublicKeys.create.feature.id" var="caPublicKeysCreate"></spring:message>
<spring:message code="admin.services.caPublicKeys.edit.feature.id" var="caPublicKeysEdit"></spring:message>
<spring:message code="admin.services.caPublicKeys.view.feature.id" var="caPublicKeysView"></spring:message>
<spring:message code="admin.services.caPublicKeys.delete.feature.id" var="caPublicKeysDelete"></spring:message> --%>

<spring:message code="admin.services.currency.feature.id" var="currency"></spring:message>
<spring:message code="admin.services.currency.create.feature.id" var="currencyCreate"></spring:message>
<spring:message code="admin.services.currency.edit.feature.id" var="currencyEdit"></spring:message>
<spring:message code="admin.services.currency.view.feature.id" var="currencyView"></spring:message>
<spring:message code="admin.services.currency.delete.feature.id" var="currencyDelete"></spring:message>

<%-- <spring:message code="admin.services.dynamicMDR.feature.id" var="dynamicMDR"></spring:message>
<spring:message code="admin.services.dynamicMDR.create.feature.id" var="dynamicMDRCreate"></spring:message>
<spring:message code="admin.services.dynamicMDR.edit.feature.id" var="dynamicMDREdit"></spring:message> --%>

<!-- SETUP OPTIONS END -->

<!-- PROGRAM OPTIONS START -->
<spring:message code="admin.services.programs.feature.id" var="programs"></spring:message>

<spring:message code="admin.services.feePrograms.feature.id" var="feePrograms"></spring:message>
<spring:message code="admin.services.feePrograms.create.feature.id" var="feeProgramsCreate"></spring:message>
<spring:message code="admin.services.feePrograms.edit.feature.id" var="feeProgramsEdit"></spring:message>
<spring:message code="admin.services.feePrograms.view.feature.id" var="feeProgramsView"></spring:message>
<spring:message code="admin.services.feePrograms.delete.feature.id" var="feeProgramsDelete"></spring:message>

<%-- <spring:message code="admin.services.commissionPrograms.feature.id" var="commissionPrograms"></spring:message>
<spring:message code="admin.services.commissionPrograms.create.feature.id" var="commissionProgramsCreate"></spring:message>
<spring:message code="admin.services.commissionPrograms.edit.feature.id" var="commissionProgramsEdit"></spring:message> --%>
<!-- PROGRAM OPTIONS END -->

<!-- MANAGE OPTIONS START -->

<spring:message code="admin.services.manage.feature.id" var="manage"></spring:message>

<spring:message code="admin.services.pm.feature.id" var="programManager"></spring:message>
<spring:message code="admin.services.pm.create.feature.id" var="programManagerCreate"></spring:message>
<spring:message code="admin.services.pm.edit.feature.id" var="programManagerEdit"></spring:message>

 <spring:message code="admin.services.iso.feature.id" var="iso"></spring:message>
<spring:message code="admin.services.iso.create.feature.id" var="isoCreate"></spring:message>
<spring:message code="admin.services.iso.edit.feature.id" var="isoEdit"></spring:message> 

<spring:message code="admin.services.merchant.feature.id" var="merchant"></spring:message>
<spring:message code="admin.services.merchant.create.feature.id" var="merchantCreate"></spring:message>
<spring:message code="admin.services.merchant.view.feature.id" var="merchantView"></spring:message>
<spring:message code="admin.services.merchant.edit.feature.id" var="merchantEdit"></spring:message>
<spring:message code="admin.services.merchant.delete.feature.id" var="merchantDelete"></spring:message>

<%-- <spring:message code="admin.services.merchantAccount.feature.id" var="merchantAccount"></spring:message>
<spring:message code="admin.services.merchantAccount.create.feature.id" var="merchantAccountCreate"></spring:message>
<spring:message code="admin.services.merchantAccount.edit.feature.id" var="merchantAccountEdit"></spring:message>
<spring:message code="admin.services.merchantAccount.suspend.feature.id" var="merchantAccountSuspend"></spring:message>
<spring:message code="admin.services.merchantAccount.activate.feature.id" var="merchantAccountActivate"></spring:message> --%>

<spring:message code="admin.services.subMerchant.feature.id" var="subMerchant"></spring:message>
<spring:message code="admin.services.subMerchant.create.feature.id" var="subMerchantCreate"></spring:message>
<spring:message code="admin.services.subMerchant.view.feature.id" var="subMerchantView"></spring:message>
<spring:message code="admin.services.subMerchant.edit.feature.id" var="subMerchantEdit"></spring:message>
<spring:message code="admin.services.subMerchant.delete.feature.id" var="subMerchantDelete"></spring:message>

<%-- <spring:message code="admin.services.subMerchantAccount.feature.id" var="subMerchantAccount"></spring:message>
<spring:message code="admin.services.subMerchantAccount.create.feature.id" var="subMerchantAccountCreate"></spring:message>
<spring:message code="admin.services.subMerchantAccount.edit.feature.id" var="subMerchantAccountEdit"></spring:message>
<spring:message code="admin.services.subMerchantAccount.suspend.feature.id" var="subMerchantAccountSuspend"></spring:message>
<spring:message code="admin.services.subMerchantAccount.activate.feature.id" var="subMerchantAccountActivate"></spring:message> --%>

<spring:message code="admin.services.dccMarkup.feature.id" var="dccMarkup"></spring:message>
<spring:message code="admin.services.dccMarkup.create.feature.id" var="dccMarkupCreate"></spring:message>
<spring:message code="admin.services.dccMarkup.edit.feature.id" var="dccMarkupEdit"></spring:message>
<spring:message code="admin.services.dccMarkup.delete.feature.id" var="dccMarkupDelete"></spring:message>

<spring:message code="admin.services.exchange.rate.feature.id" var="exchangeRate"></spring:message>
<spring:message code="admin.services.exchange.rate.create.feature.id" var="exchangeRateCreate"></spring:message>
<spring:message code="admin.services.exchange.rate.edit.feature.id" var="exchangeRateEdit"></spring:message>
<spring:message code="admin.services.exchange.rate.delete.feature.id" var="exchangeRateDelete"></spring:message>

<spring:message code="admin.services.bank.feature.id" var="bank"></spring:message>
<spring:message code="admin.services.bank.create.feature.id" var="bankCreate"></spring:message>
<spring:message code="admin.services.bank.view.feature.id" var="bankView"></spring:message>
<spring:message code="admin.services.bank.edit.feature.id" var="bankEdit"></spring:message>
<spring:message code="admin.services.bank.delete.feature.id" var="bankDelete"></spring:message>

<%-- <spring:message code="admin.services.reseller.feature.id" var="reseller"></spring:message>
<spring:message code="admin.services.reseller.create.feature.id" var="resellerCreate"></spring:message>
<spring:message code="admin.services.reseller.view.feature.id" var="resellerView"></spring:message>
<spring:message code="admin.services.reseller.edit.feature.id" var="resellerEdit"></spring:message>
<spring:message code="admin.services.reseller.delete.feature.id" var="resellerDelete"></spring:message>
 --%>

<!-- MANAGE OPTIONS END -->


<spring:message code="admin.services.adjustments.feature.id" var="adjustments"></spring:message>
<spring:message code="admin.services.manualCredit.feature.id" var="manualCredit"></spring:message>
<spring:message code="admin.services.manualDebit.feature.id" var="manualDebit"></spring:message>

<spring:message code="admin.services.reports.feature.id" var="reports"></spring:message>
<spring:message code="admin.services.overviewBalanceSheet.feature.id" var="overviewBalanceSheet"></spring:message>
<spring:message code="admin.services.pendingTransactions.feature.id" var="pendingTransactions"></spring:message>
<spring:message code="admin.services.transactionsReport.feature.id" var="transactionsReport"></spring:message>
<spring:message code="admin.services.balanceReports.feature.id" var="balanceReports"></spring:message>
<spring:message code="admin.services.manualTransactions.feature.id" var="manualTransactions"></spring:message>
<spring:message code="admin.services.settlementReport.feature.id" var="settlementReport"></spring:message>
<spring:message code="admin.services.batchReport.feature.id" var="batchReport"></spring:message>

<spring:message code="admin.services.schedule.report.feature.id" var="scheduleReport"></spring:message>
<spring:message code="admin.services.funding.report.feature.id" var="dailyFundingReport"></spring:message>
<spring:message code="admin.services.runManual.feature.id" var="runManual"></spring:message>



<spring:message code="admin.services.virtualTerminal.feature.id" var="virtualTerminal"></spring:message>

<spring:message code="admin.services.virtualTerminal.sale.id" var="virtualTerminalSale"></spring:message>
<spring:message code="admin.services.virtualTerminal.refund.id" var="virtualTerminalRefund"></spring:message>
<%-- <spring:message code="admin.services.virtualTerminal.void.id" var="virtualTerminalVoid"></spring:message> --%>

<spring:message code="admin.services.menu.feature.id" var="menu"></spring:message>
<spring:message code="admin.services.menu.changePassword.feature.id" var="menuChangePassword"></spring:message>
<spring:message code="admin.services.menu.myProfile.feature.id" var="menuMyProfile"></spring:message>


<!-- Reseller -->

<spring:message code="reseller.service.dashboard.feature.id" var="resellerDashboard"></spring:message>

<spring:message code="reseller.services.manage.feature.id" var="resellerManage"></spring:message>

<spring:message code="reseller.services.merchant.feature.id" var="resellerMerchant"></spring:message>
<spring:message code="reseller.services.merchant.view.feature.id" var="resellerMerchantView"></spring:message>

<spring:message code="reseller.services.subMerchant.feature.id" var="resellerSubMerchant"></spring:message>
<spring:message code="reseller.services.subMerchant.view.feature.id" var="resellerSubMerchantView"></spring:message>

<spring:message code="reseller.services.transactionReports.feature.id" var="resellerTransaction"></spring:message>
<spring:message code="reseller.services.commissionReports.feature.id" var="resellerCommission"></spring:message>
<spring:message code="reseller.services.menu.feature.id" var="resellerMenu"></spring:message>
<spring:message code="reseller.services.menu.changePassword.feature.id" var="resellerChangePassword"></spring:message>
<spring:message code="reseller.services.menu.myProfile.feature.id" var="resellerMyProfile"></spring:message>
<spring:message code="admin.services.fee.report.feature.id" var="feeReport"></spring:message>
<spring:message code="admin.services.isoRevenue.report.feature.id" var="isoRevenueReport"></spring:message>
<spring:message code="admin.services.merchantRevenue.report.feature.id" var="merchantRevenueReport"></spring:message>
<spring:message code="admin.services.pmRevenue.report.feature.id" var="pmRevenueReport"></spring:message>


<!--Header Block Start -->

<header class="col-sm-12 all-page-header">
 
	<!--Header Logo Start -->
	<div class="col-sm-4">
		<img src="../images/Chatak-logo.jpg" height="63px" alt="Logo" />
	</div>
	<!--Header Logo End -->
	<!--Header Welcome Text and Logout button Start -->
	<c:if test="${loginUserType ne 'NEW-USER'}">
	<div class="col-sm-6 col-xs-offset-3">
		<div class="pull-right user-settings">
			<table>
				<tr>
					<td><spring:message code="header.label.welcome"/> ${loginResponse.userName }</td>
					<td align="right"><a href="logout"><span
							class="glyphicon glyphicon-log-out"></span> </span> <spring:message code="header.label.logout"/></a></td>
				</tr>
				<tr>
				<td><spring:message code="header.label.lastLoginTime"/> ${loginResponse.lastLonginTime }</td>
				</tr>
				<!-- <tr>
					<td><div id="showTimer" style="font-weight: bold;"></div></td>
				</tr> -->
			</table>
		</div>
	</div>
	</c:if>
	<!--Header Welcome Text and Logout button End -->
</header>
<%-- <head>
<title><%= JSPConstants.PAGE_TITLE %></title>//need to do
<link href="../css/font-awesome.css" rel="stylesheet">
</head> --%>

<!--Header Block End -->
<!--Navigation Block Start -->
<nav class="col-md-12 nav-bar">
<!-- <input type="text" id="isWindowClose" name="windowClose"> -->
<span class="glyphicon glyphicon-list menu-icon"></span>
	<ul class="navigation">
		<c:if test="${fn:contains(existingFeatures,dashboard) || fn:contains(existingFeatures,resellerDashboard)}">
			<li id="navListId1"><a href="home"><spring:message code="header.label.dashboard"/></a>
			<ul class="dropdown-menu" role="menu">
			<c:if test="${fn:contains(existingFeatures,UnblockUsers)}">
			<li style="text-align:left;"><a href="admin-reset-password-unblock"><spring:message code="header.label.unblockusers"/></a></li>
			</c:if>
			</ul>
			</li>
		</c:if>
		
		<c:if test="${fn:contains(existingFeatures,setup)}">
			<li id="navListId2" class="dropdown"><a href="#"
				class="dropdown-toggle"><spring:message code="header.label.setup"/></a> <!--Navigation Block Sub Menu Start -->
				<ul class="dropdown-menu" role="menu">
						<li class="transperent-background"><a href="javascript:void(0)"></a></li>
					<c:if test="${fn:contains(existingFeatures,roles)}">
						<li style="text-align:left;"><a href="access-role-search"><spring:message code="header.label.roles"/></a></li>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,users)}">
						<li style="text-align:left;"><a href="access-user-search?requestType=Admin"><spring:message code="header.label.user"/></a></li>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,switchInfo)}">
						<li style="text-align:left;"><a href="switch-search"><spring:message code="header.label.switchinformation"/></a></li>
					</c:if>
					<%-- <c:if test="${fn:contains(existingFeatures,ONUSBIN)}">
						<li style="text-align:left;"><a href="bin-search-show"><spring:message code="bin.label.onus"/></a></li>
					</c:if> --%>
					<c:if test="${fn:contains(existingFeatures,BlackListedCard)}">
						<li style="text-align:left;"><a href="black-listed-card-search"><spring:message code="header.label.blacklistedcard"/></a></li>
					</c:if>
					<%-- <c:if test="${fn:contains(existingFeatures,paymentScheme)}">
						<li style="text-align:left;"><a href="payment-scheme-search-urlpage"><spring:message code="header.label.paymentscheme"/></a></li>
					</c:if> --%>
					<%-- <c:if test="${fn:contains(existingFeatures,merchantCategoryCode)}">
						<li style="text-align:left;"><a href="merchant-category-code-search"><spring:message code="header.label.merchantcategorycode"/></a></li>
					</c:if> --%>
					<%-- <c:if test="${fn:contains(existingFeatures,caPublicKeys)}">
						<li style="text-align:left;"><a href="show-ca-public-keys-search-page"><spring:message code="header.label.capublickeys"/></a></li>
					</c:if> --%>
					<c:if test="${fn:contains(existingFeatures,currency)}">
						<li style="text-align:left;"><a href="show-currency-search"><spring:message code="currency-search-page.label.currency"/></a></li>
					</c:if>
					<%-- <c:if test="${fn:contains(existingFeatures,dynamicMDR)}">
						<li style="text-align:left;"><a href="show-dynamic-MDR-search"><spring:message code="header.label.dynamicMDR"/></a></li>
					</c:if> --%>
				</ul> <!--Navigation Block Sub Menu End --></li>
		</c:if>  
		<c:if test="${fn:contains(existingFeatures,programs)}">
			<li id="navListId3" class="dropdown"><a href="#"
				class="dropdown-toggle" data-toggle="dropdown"><spring:message code="commission-program-search.label.programs"/></a> <!--Navigation Block Sub Menu Start -->
				<ul class="dropdown-menu" role="menu">
						<li class="transperent-background"><a href="javascript:void(0)"></a></li>
					<c:if test="${fn:contains(existingFeatures,feePrograms)}">
						<li style="text-align:left;"><a href="show-fee-program-search"><spring:message code="fee-program-search.label.feeprogram"/></a></li>
					</c:if>
					<%-- <c:if test="${fn:contains(existingFeatures,commissionPrograms)}">
						<li style="text-align:left;"><a href="show-commission-program-search"><spring:message code="commission-program-search.label.commisisionprogram"/></a></li>
					</c:if> --%>
					</ul> <!--Navigation Block Sub Menu End --></li>
		</c:if>
		<c:if test="${fn:contains(existingFeatures,manage) || fn:contains(existingFeatures,resellerManage)}">
			<li id="navListId6" class="dropdown"><a href="#"
				class="dropdown-toggle" data-toggle="dropdown"><spring:message code="manage.label.manage1" /></a> <!--Navigation Block Sub Menu Start -->
				<ul class="dropdown-menu" role="menu">
						<li class="transperent-background"><a href="javascript:void(0)"></a></li>
					<c:if test="${fn:contains(existingFeatures, programManager)}">
						<li style="text-align:left;"><a href="showProgramManager"><spring:message code="admin.pm.message"/></a></li>
					</c:if>
					 <c:if test="${fn:contains(existingFeatures, iso)}">
					 	<li style="text-align:left;"><a href="showIsoSearch"><spring:message code="admin.iso.label"/></a></li>
					</c:if> 
					<c:if test="${fn:contains(existingFeatures,merchant) || fn:contains(existingFeatures,resellerMerchant)}">
						<li style="text-align:left;"><a href="merchant-search-page"><spring:message code="show-all-pending-merchants.label.merchant"/></a></li>
					</c:if>
					<%-- <c:if test="${fn:contains(existingFeatures,merchantAccount)}">
						<li style="text-align:left;"><a href="merchant-account-search"><spring:message code="merchant.label.merchantaccount"/></a></li>
					</c:if> --%>
					<c:if test="${fn:contains(existingFeatures,subMerchant) || fn:contains(existingFeatures,resellerSubMerchant)}">
						<li style="text-align:left;"><a href="sub-merchant-search-page"><spring:message code="manage.label.sub-merchant" /></a></li>
					</c:if>
					<%-- <c:if test="${fn:contains(existingFeatures,subMerchantAccount)}">
						<li style="text-align:left;"><a href="sub-merchant-account-search"><spring:message code="sub-merchant-account-search.label.sub-merchantaccount"/></a></li>
					</c:if> --%>
					<%-- <c:if test="${fn:contains(existingFeatures,dccMarkup)}">
					<li style="text-align:left;"><a href="dcc-markup-search"><spring:message code="dcc.label.markup"/></a></li>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,exchangeRate)}">
					<li style="text-align:left;"><a href="exchange-rate-create"><spring:message code="exchange.label.rate"/></a></li>
					</c:if> --%>
					<c:if test="${fn:contains(existingFeatures,bank)}">
						<li style="text-align:left;"><a href="bank-search"><spring:message code="pending-merchant-show.label.bank"/></a></li>
					</c:if>  
					<%-- <c:if test="${fn:contains(existingFeatures,reseller)}">
						<li style="text-align:left;"><a href="reseller-search-page"><spring:message code="pending-merchant-show.label.reseller"/></a></li>
					</c:if> --%>
					<%-- <c:if test="${fn:contains(existingFeatures,systemRevenue)}">
						<li style="text-align:left;"><a href="#">System Revenue</a></li>
					</c:if> --%>
				</ul> <!--Navigation Block Sub Menu End --></li>
		</c:if>
		<c:if test="${fn:contains(existingFeatures,adjustments)}">
		<li id="navListId5" class="dropdown">
		<a href="#" class="dropdown-toggle"><spring:message code="accounts-manual-credit.label.adjustments1"/></a>
		<ul class="dropdown-menu" role="menu">
			<li class="transperent-background"></li>
			<c:if test="${fn:contains(existingFeatures,manualCredit)}">
			<li style="text-align:left;"><a href="accounts-manual-credit"><spring:message code="accounts-manual-credit.label.manualcredit"/></a></li>
			</c:if>
			<c:if test="${fn:contains(existingFeatures,manualDebit)}">
			<li style="text-align:left;"><a href="accounts-manual-debit"><spring:message code="accounts-manual-debit.label.manualdebit"/></a></li>
			</c:if>
			<%-- <li style="text-align:left;"><a href="show-account-transfer"><spring:message code="show-account-transfer.label.accounttransfer"/> </a></li> --%>
			<!-- <li style="text-align:left;"><a href="#">Account Sweeper</a></li> -->
			<%-- <li style="text-align:left;"><a href="getLitleEFTTransactionListToDashBoard"><spring:message code="show-account-transfer.label.manualsweeper"/></a></li> --%>
		</ul>
	</li>
	</c:if>

		<c:if test="${fn:contains(existingFeatures,virtualTerminal)}">
			<li id="navListId7" class="dropdown" >
			<c:choose>
			<c:when test="${fn:contains(existingFeatures,virtualTerminalSale) && fn:contains(existingFeatures,virtualTerminalRefund)}">
					<a href="virtual-terminal-sale" class="dropdown-toggle"><spring:message
							code="virtual-terminal-void.label.virtualterminal" /></a>
				</c:when>
				<c:otherwise>
				<c:if test="${fn:contains(existingFeatures,virtualTerminalSale)}">
					<a href="virtual-terminal-sale" class="dropdown-toggle"><spring:message
							code="virtual-terminal-void.label.virtualterminal" /></a>
				</c:if>  <c:if test="${fn:contains(existingFeatures,virtualTerminalRefund)}">
					<a href="virtual-terminal-refund" class="dropdown-toggle"><spring:message
							code="virtual-terminal-void.label.virtualterminal" /></a>
				</c:if>
				</c:otherwise>
				</c:choose>
			</li>	
		</c:if>

		<c:if
			test="${fn:contains(existingFeatures,reports) || fn:contains(existingFeatures,resellerTransaction) || fn:contains(existingFeatures,resellerCommission)}">
			<li id="navListId4" class="dropdown"><a href="#"
				class="dropdown-toggle"><spring:message
						code="reports.label.reports" /></a>
				<ul class="dropdown-menu" role="menu">
					<li class="transperent-background"></li>
					<c:if
						test="${fn:contains(existingFeatures, overviewBalanceSheet)}">
						<li style="text-align: left;"><a
							href="showSystemOverviewReports"><spring:message
									code="reports.label.overviewandbalancesheet" /></a></li>
					</c:if>
					<%-- <c:if test="${fn:contains(existingFeatures, pendingTransactions)}">
						<li style="text-align: left;"><a
							href="showGlobalSysPendingTransReports"><spring:message
									code="reports.label.pendingtransactions" /></a></li>
					</c:if> --%>
					<c:if test="${fn:contains(existingFeatures, transactionsReport)}">
						<li style="text-align: left;"><a href="transactions-search"><spring:message
									code="reports.label.transactionsreports" /></a></li>
					</c:if>
					<c:if test="${fn:contains(existingFeatures, balanceReports)}">
						<li style="text-align: left;"><a
							href="showGlobalSysBalancesReports"><spring:message
									code="reports.label.balancereports" /></a></li>
					</c:if>
					<c:if test="${fn:contains(existingFeatures, manualTransactions)}">
						<li style="text-align: left;"><a
							href="showGlobalSysManualTransReports"><spring:message
									code="reports.label.balancereports.manualtransactions" /></a></li>
					</c:if>
					<c:if test="${fn:contains(existingFeatures, settlementReport)}">
						<li style="text-align: left;"><a href="getSettlementReport"><spring:message
									code="reports.label.settlementReport" /></a></li>
					</c:if>
					<c:if test="${fn:contains(existingFeatures, batchReport)}">
						<li style="text-align: left;"><a href="batch-report"><spring:message
									code="chatak-batch-report" /></a></li>
					</c:if>
					<c:if test="${fn:contains(existingFeatures, feeReport)}">
						<li style="text-align: left;"><a href="showFeeReport"><spring:message
									code="fee-report.label.fee.report" /></a></li>
									</c:if>

				 	<c:if test="${fn:contains(existingFeatures, isoRevenueReport)}"> 
						<li style="text-align: left;"><a href="showIsoRevenueReport"><spring:message
									code="admin.label.isorevenue" /></a></li>
					 </c:if>
					<%-- 
					 <c:if test="${fn:contains(existingFeatures, merchantRevenueReport)}"> 
						<li style="text-align: left;"><a href="showMerchantRevenueReport"><spring:message
									code="admin.label.merchantrevenue" /></a></li>
					 </c:if> --%>
					<c:if test="${fn:contains(existingFeatures, pmRevenueReport)}">
						<li style="text-align: left;"><a href="showPmRevenueReport"><spring:message
									code="admin.label.pmrevenue" /></a></li>
					</c:if>
				</ul></li>
		</c:if>
		<c:if test="${fn:contains(existingFeatures, scheduleReport)}">
			<li id="navListId8" class="dropdown"><a href="#"
				class="dropdown-toggle" data-toggle="dropdown"><spring:message code="chatak-report-lable-schedule-report"/></a>
				<ul class="dropdown-menu" role="menu">
						<li class="transperent-background"><a href="javascript:void(0)"></a></li>
					<c:if test="${fn:contains(existingFeatures,dailyFundingReport)}">
						<li style="text-align:left;"><a href="daily-funding-report"><spring:message code="chatak-report-lable-daily-unding-report"/></a></li>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,runManual)}">
					<li style="text-align:left;"><a class="trigger-action" href="#"><spring:message code="chatak.manual.funding.header" /></a></li>
					</c:if>
				</ul>
			</li>
	</c:if>

	<!-- <li id="navListId8" class="dropdown">
		<a href="#" class="dropdown-toggle">Issuing</a>
		<a href="switchToIssuance" class="dropdown-toggle">Issuing</a>
	</li> -->
	
	
	<li id="navListId9" class="dropdown pull-right" style="margin:0px">
		<a href="#" class="dropdown-toggle"><spring:message code="chatak-admin.lable.menu" /></a>
		<ul class="dropdown-menu" role="menu">
			<li class="transperent-background"></li>
			<li style="text-align:left;"><a href="change-password"><spring:message code="change-password.label.changepassword"/></a></li>
			<li style="text-align:left;"><a href="chatak_admin_myprofile"><spring:message code="chatak-admin-myprofile.label.myprofile"/></a></li>
			<li style="text-align:left;"><a href="download-logs"><spring:message code="chatak-admin-myprofile.label.downloadLogs"/></a></li>
		</ul>
	</li>
	
	</ul>
</nav>
<div id="triggerPopup" class="popup-void-refund">
	<div class="popup-hf-bc fw-b-fs15 txt-center">
		<span style="line-height: 2em;"><spring:message code="chatak.manual.funding.message" /></span>
	</div>
	<div class="fw-b-fs15" style="padding: 20px;"><spring:message code="chatak.manual.funding.confirmation" /></div>
	<div class="col-sm-12 popup-hf-bc">
		<input type="submit" class="form-control button pull-right margin5"
			value="<spring:message code="bin.label.yes" />" onclick="batchTgr();"> <input type="button"
			class="form-control button pull-right margin5 close-btn"
			onclick="closeBatch()" value='<spring:message code="reports.label.cancelbutton" />'>
	</div>
</div>
<div id="manualEftResultPopup" class="popup-void-refund voidResult">
	<div class="popup-hf-bc fw-b-fs15 txt-center">
		<span style="line-height: 2em;"><spring:message code="chatak.manual.funding.message" /></span>
	</div>
	<div class="col-xs-12">
		<div class="marginT20">
			<span class="red-error fw-b-fs15" id="batchErrorData">&nbsp;</span> <span
				class="green-error fw-b-fs15" id="batchSuccessData">&nbsp;</span>
		</div>
	</div>
	<div class="col-sm-12 popup-hf-bc">
		<input type="button"
			class="form-control button pull-right margin5 close-btn"
			onclick="closeBatchResult()" value="<spring:message code="home.label.closebutton" />">
	</div>
</div>
<div id="processingDiv" class="general-popup">
        <div class="processing"><img alt="Processing" src="../images/processing.gif"></div>
</div>

<!-- Common Alert PopUp STARTS here -->
<div id="alertPopUp" class="locatioin-list-popup">
<h3  id="alertPopUpMsg"></h3>
<div class="col-sm-12 form-action-buttons">
		<div class="col-sm-12">
			<input type="button" name="Ok" value="Ok"
				class="form-control button pull-right" onclick="closeAlertPopup()">
		</div>
</div>
</div>
<!-- Common Alert PopUp ENDS here --> 

<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
<script src="../js/jquery.cookie.js"></script>
<script src="../js/common-lib.js"></script>
<script src="../js/messages.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jstimezonedetect/1.0.4/jstz.min.js"></script> 
<script>
function getLitleEFTTxns() {
	document.forms["litleEFTTxn"].submit();
}

$(document).ready( function() {
	// highlightMainContent();
	
	//setInterval("showCurrentTime()", 1000);
	//setInterval("showCurrentTime1()", 1000);
});
$('.trigger-action').on('click', function() {
	 /  alert("popup"); /
			$('#triggerPopup').popup({
				autoopen: true,
				blur : false
			});
 });
 
function closeBatch() {
    /*  alert("close"); */
     $('#triggerPopup').popup("hide");
}

 function closeBatchResult() {
        /*  alert("close"); */
         $('#manualEftResultPopup').popup("hide");
  }


	function batchTgr() {
		$('#triggerPopup').popup("hide");

		$('#processingDiv').popup({
			autoopen : true,
			blur : false
		});

		var csrfToken = $("input[name=CSRFToken]").val();
		$.ajax({
			type : "POST",
			url : "process-manualFunding-trigger",
			data : {

				CSRFToken : csrfToken
			},
			success : function(response) {
				$('#processingDiv').popup("hide");
				$('#processingDiv').popup({
					autoopen : false
				});

				var obj = JSON.parse(response);
				if (obj.errorCode == '00') {
					$('#batchSuccessData').text(
							'<spring:message code="chatak.manual.funding.success" />');
				} else if (obj.errorCode == '01') {
					$('#batchErrorData').text(
							'<spring:message code="chatak.manual.funding.no.transactions" />');
					$('#manualEftResultPopup').popup("hide");
				} else {
					$('#batchErrorData').text('<spring:message code="chatak.manual.funding.failed" />');
					$('#manualEftResultPopup').popup("hide");
					$('#batchErrorData').text(response.errorMessage);
				}
				$('#manualEftResultPopup').popup("show");

			},
			error : function(e) {
			}
		});
	}
	$(function() {
		$(".dropdown-toggle").hover(function(e) {
			$(".sub-menu").hide();
		});
		$(".dropdown-menu > li > a.trigger").hover(
				function(e) {
					var current = $(this).next();
					var grandparent = $(this).parent().parent();
					if ($(this).hasClass('left-caret')
							|| $(this).hasClass('right-caret'))
						$(this).toggleClass('right-caret left-caret');
					grandparent.find('.left-caret').not(this).toggleClass(
							'right-caret left-caret');
					grandparent.find(".sub-menu:visible").not(current).hide();
					current.toggle();
					e.stopPropagation();
				},
				function() {
					var root = $(this).closest('.dropdown');
					root.find('.left-caret').toggleClass(
							'right-caret left-caret');
				});
		$(".dropdown-menu > li > a:not(.trigger)").on("click", function() {
			var root = $(this).closest('.dropdown');
			root.find('.left-caret').toggleClass('right-caret left-caret');
		});
	});
	
	$(document).ready(function() {
		getUserOffsetAndRegion();
	});
	
	function getUserOffsetAndRegion(){
		var offset = new Date().toString().match(/([A-Z]+[\+-][0-9]+)/)[1];
		var region = jstz.determine().name();
		if(!(typeof region != 'undefined' && region != null && region != '')){
			region = offset;
		}
		$('#deviceTimeZoneOffset').val(offset);
		$('#deviceTimeZoneRegion').val(region);
		$('#deviceTzOffset').val(offset);
		$('#deviceTzRegion').val(region);
	}
	
	$(document).ready(function() {
		$('#alertPopUp').popup({
			blur:"false"
		});
	});
	
	function closeAlertPopup() {
		$('#alertPopUp').popup("hide");
	}
	function showAlertPopup(message) {
		$('#alertPopUpMsg').text(message);
		$('#alertPopUp').popup("show");
	}
</script>
<script>
</script>
<!--Navigation Block End -->
