<%-- <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<!--Header Block Start -->
<%-- <header class="col-sm-12 all-page-header">
	<!--Header Logo Start -->
	<div class="col-sm-4">
		<img src="../images/chatak_logo.jpg" height="35px" alt="Logo" />
	</div>
	<!--Header Logo End -->
	<!--Header Welcome Text and Logout button Start -->
	<div class="col-sm-5 col-xs-offset-3">
		<div class="pull-right user-settings">
			<table>
				<tr>
					<td>Welcome ${loginResponse.email }</td>
				</tr>
				<tr>
					<td align="right"><a href="logout"><span
							class="glyphicon glyphicon-log-out"></span> Logout</a></td>
				</tr>
			</table>
		</div>
	</div>
	<!--Header Welcome Text and Logout button End -->
</header> --%>
<!--Header Block End -->
<%-- <spring:message code="view.submerchant.transactions.id" var="subMerchantTransactions"></spring:message>
<spring:message code="process.submerchant.transactions.id" var="processPubMerchantTransactions"></spring:message>
<spring:message code="execute.submerchant.transactions.id" var="executeSubMerchantTransactions"></spring:message>
<spring:message code="refund.submerchant.transactions.id" var="refundSubMerchantTransactions"></spring:message>
<spring:message code="void.submerchant.transactions.id" var="voidSubMerchantTransactions"></spring:message>

<p>${subMerchantTransactions}</p> --%>

<ul class="navigation">
	<li id="navListId1"><a href="dash-board"><spring:message code="dash-board.label.dashboard"/></a></li>
	<!-- <li id="navListId9"><a href="search-sub-merchant">Sub merchants</a></li> -->
	<li id="navListId9"><a href="search-sub-merchant"><spring:message code="search-sub-merchant.label.submerchant"/></a></li>
	<li id="navListId2"><a href="virtual-terminal-sale"><spring:message code="virtual-terminal-sale.label.virtualterminal"/></a></li>
	<li id="navListId4" class="dropdown">
		<a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="fraud-basic.label.fraud"/></a> <!--Navigation Block Sub Menu Start -->
		<ul class="dropdown-menu" role="menu">
			<li class="transperent-background"></li>
			<li><a href="fraud-basic"><spring:message code="fraud-basic.label.basic"/></a></li>
			<li><a href="fraud-advanced"><spring:message code="fraud-basic.label.advanced"/></a></li>
			<li><a href="#"><spring:message code="main-navigation.label.fraudreview"/></a></li>
		</ul> <!--Navigation Block Sub Menu End -->
	</li>
	<li id="navListId5"><a href="recurring-search"><spring:message code="recurring-search.label.recurring"/></a></li>
	<li id="navListId6"><a href="transactions-search"><spring:message code="transactions-search.label.transactions"/>s</a></li>
	<li id="navListId10" class="dropdown">
		<a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="fund-transfer-eft.label.transfers"/></a> <!--Navigation Block Sub Menu Start -->
		<ul class="dropdown-menu" role="menu">
			<li class="transperent-background"></li>
			<li><a href="fund-transfer-eft"><spring:message code="fund-transfer-eft.label.sendsfundsaccount"/></a></li>
			<li><a href="fund-transfer-check"><spring:message code="fund-transfer-check.label.sendfundsbycheck"/></a></li>
		</ul> <!--Navigation Block Sub Menu End -->
	</li>
	<li id="navListId7" class="dropdown">
		<a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="reports.label.reports"/></a>
		<ul class="dropdown-menu" role="menu">
			<li class="transperent-background"></li>
			<li><a href="specific-user-eftTransfers-show"><spring:message code="main-navigation.label.EFTtransfers"/></a></li>
			<li><a href="showGlobalSysRevenueGeneratedReports"><spring:message code="reportsglobal.label.merchanttransactionrevenue"/></a></li>
		</ul>
	</li>
	<li id="navListId11" class="dropdown">
		<a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="adjustments.label.adjustments"/></a>
		<ul class="dropdown-menu" role="menu">
			<li class="transperent-background"></li>
			<li><a href="show-account-transfer"><spring:message code="main-navigation.label.accounttransfers"/></a></li>
		</ul>
	</li>
	<c:if test="${paypage eq '1'}">
	<li id="navListId12"><a href="chatak-pay-page-config"><spring:message code="paypage.label.paypageconfiguration"/></a></li>
	</c:if>
	<li id="navListId8" class="dropdown last-navigation-item pull-right">
		<a href="#" class="dropdown-toggle" data-toggle="dropdown">Menu</a> <!--Navigation Block Sub Menu Start -->
		<ul class="dropdown-menu last-navigation" role="menu">
			<li class="transperent-background"></li>
			<li><a href="merchant-change-password"><spring:message code="change-password.label.changepassword"/></a></li>
			<li><a href="chatak_merchant_myprofile"><spring:message code="myprofile.label.myprofile"/></a></li>
		</ul> <!--Navigation Block Sub Menu End -->
	</li>
</ul>
<script>
/* $(document).ready(function() {
	/* if(jQuery.isEmptyObject(parentMerchantId)){
		$('#navListId5').hide();
	} 
}); */
</script>