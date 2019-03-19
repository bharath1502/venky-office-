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
  
<spring:message code="merchant.service.dashboard.feature.id" var="dashboard"></spring:message>

<!-- SETUP OPTIONS START -->

<spring:message code="merchant.services.subMerchant.feature.id" var="subMerchant"></spring:message>
<spring:message code="merchant.services.subMerchant.create.feature.id" var="subMerchantCreate"></spring:message>
<spring:message code="merchant.services.subMerchant.view.feature.id" var="subMerchantView"></spring:message>
<spring:message code="merchant.services.subMerchant.edit.feature.id" var="subMerchantEdit"></spring:message>
<spring:message code="merchant.services.subMerchant.delete.feature.id" var="subMerchantDelete"></spring:message>

<spring:message code="merchant.services.virtualTerminal.feature.id" var="virtualTerminal"></spring:message>
<spring:message code="merchant.services.virtualTerminal.sale.feature.id" var="virtualTerminalSale"></spring:message>
<spring:message code="merchant.services.virtualTerminal.refund.feature.id" var="virtualTerminalRefund"></spring:message>

<spring:message code="merchant.services.fraud.feature.id" var="fraud"></spring:message>

<spring:message code="merchant.services.fraud.basic.feature.id" var="fraudBasic"></spring:message>
<spring:message code="merchant.services.fraud.advanced.feature.id" var="fraudAdvanced"></spring:message>

<spring:message code="merchant.services.recurring.feature.id" var="recurring"></spring:message>
<spring:message code="merchant.services.recurring.create.feature.id" var="recurringCreate"></spring:message>
<spring:message code="merchant.services.recurring.edit.feature.id" var="recurringEdit"></spring:message>
<spring:message code="merchant.services.recurring.delete.feature.id" var="recurringDelete"></spring:message>

<spring:message code="merchant.services.transaction.feature.id" var="transaction"></spring:message>

<spring:message code="merchant.services.transfer.feature.id" var="transfer"></spring:message>

<spring:message code="merchant.services.transfer.toBankAccount.feature.id" var="transferToBank"></spring:message>
<spring:message code="merchant.services.transfer.byCheck.feature.id" var="transferByCheck"></spring:message>

<spring:message code="merchant.services.reports.feature.id" var="reports"></spring:message>
<spring:message code="merchant.services.batchReport.feature.id" var="batchReport"></spring:message>
<spring:message code="merchant.services.reports.eftTransfer.feature.id" var="reportsEftTransfer"></spring:message>
<spring:message code="merchant.services.reports.merchant.transaction.revenue.feature.id" var="reportsMerchantRevenue"></spring:message>

<spring:message code="merchant.services.adjustments.feature.id" var="adjustments"></spring:message>
<spring:message code="merchant.services.adjustments.account.transfer.feature.id" var="adjustmentsAccount"></spring:message>

<spring:message code="merchant.services.schedule.report.feature.id" var="scheduleReport"></spring:message>
<spring:message code="merchant.services.funding.report.feature.id" var="dailyFundingReport"></spring:message>

<spring:message code="merchant.services.faqManagement.feature.id" var="dailyFundingReport"></spring:message>

<!--Header Block Start -->

<%-- <header class="col-sm-12 all-page-header">
 
	<!--Header Logo Start -->
	<div class="col-sm-4">
		<img src="../images/chatak_logo.jpg" height="50px" alt="Logo" />
	</div>
	<!--Header Logo End -->
	<!--Header Welcome Text and Logout button Start -->
	<div class="col-sm-6 col-xs-offset-3">
		<div class="pull-right user-settings">
			<table>
				<tr>
					<td>Welcome ${loginResponse.email }</td>
					<td align="right"><a href="logout"><span
							class="glyphicon glyphicon-log-out"></span> Logout</a></td>
				</tr>
				<tr><td align="right" id="time" style="color:#0072C6;"></td></tr>
				<!-- <tr>
					<td><div id="showTimer" style="font-weight: bold;"></div></td>
				</tr> -->
			</table>
		</div>
	</div>
	<!--Header Welcome Text and Logout button End -->
</header> --%>
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
		<c:if test="${fn:contains(existingFeatures,dashboard)}">
			<li id="navListId1"><a href="dash-board"><spring:message code="dash-board.label.dashboard"/></a>
			</li>
		</c:if>
		<c:if test="${empty parentMerchantId}">
			<c:if test="${fn:contains(existingFeatures,subMerchant)}">
				<li id="navListId9"><a href="search-sub-merchant"><spring:message code="search-sub-merchant.label.submerchant" /></a></li>
			</c:if>
		</c:if>

		<c:if test="${fn:contains(existingFeatures,virtualTerminal)}">
		     <li id="navListId2" class="dropdown" >
			<c:choose>
			<c:when test="${fn:contains(existingFeatures,virtualTerminalSale) && fn:contains(existingFeatures,virtualTerminalRefund)}">
					<a href="virtual-terminal-sale" class="dropdown-toggle"><spring:message
							code="virtual-terminal-sale.label.virtualterminal" /></a>
				</c:when>
				<c:otherwise>
				<c:if test="${fn:contains(existingFeatures,virtualTerminalSale)}">
					<a href="virtual-terminal-sale" class="dropdown-toggle"><spring:message
							code="virtual-terminal-sale.label.virtualterminal" /></a>
				</c:if>  <c:if test="${fn:contains(existingFeatures,virtualTerminalRefund)}">
					<a href="virtual-terminal-refund" class="dropdown-toggle"><spring:message
							code="virtual-terminal-sale.label.virtualterminal" /></a>
				</c:if>
				</c:otherwise>
				</c:choose>
			
			</li>
		</c:if>
				
		<%-- <c:if test="${fn:contains(existingFeatures,fraud)}">
			<li id="navListId4" class="dropdown"><a href="#"
				class="dropdown-toggle"><spring:message code="fraud-basic.label.fraud"/></a> <!--Navigation Block Sub Menu Start -->
				<ul class="dropdown-menu" role="menu">
						<li class="transperent-background"><a href="javascript:void(0)"></a></li>
					<c:if test="${fn:contains(existingFeatures,fraudBasic)}">
						<li style="text-align:left;"><a href="fraud-basic"><spring:message code="fraud-basic.label.basic"/></a></li>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,fraudAdvanced)}">
						<li style="text-align:left;"><a href="fraud-advanced"><spring:message code="fraud-basic.label.advanced"/></a></li>
					</c:if>
				</ul> <!--Navigation Block Sub Menu End --></li>
		</c:if>   --%>
		
		<%-- <c:if test="${fn:contains(existingFeatures,recurring)}">
			<li id="navListId5"><a href="recurring-search"><spring:message code="recurring-search.label.recurring"/></a>
			</li>
		</c:if> --%>
		
		<c:if test="${fn:contains(existingFeatures,transaction)}">
			<li id="navListId6"><a href="transactions-search"><spring:message code="transactions-search.label.transactions"/></a>
			</li>
		</c:if>
		
<%-- 		<c:if test="${fn:contains(existingFeatures,transfer)}">
			<li id="navListId10" class="dropdown"><a href="#"
				class="dropdown-toggle" data-toggle="dropdown"><spring:message code="fund-transfer-eft.label.transfers"/></a> <!--Navigation Block Sub Menu Start -->
				<ul class="dropdown-menu" role="menu">
						<li class="transperent-background"><a href="javascript:void(0)"></a></li>
					<c:if test="${fn:contains(existingFeatures,transferToBank)}">
						<li style="text-align:left;"><a href="fund-transfer-eft"><spring:message code="fund-transfer-eft.label.sendsfundsaccount"/></a></li>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,transferByCheck)}">
						<li style="text-align:left;"><a href="fund-transfer-check"><spring:message code="fund-transfer-check.label.sendfundsbycheck"/></a></li>
					</c:if>
					</ul> <!--Navigation Block Sub Menu End --></li>
		</c:if> --%>
		
		<c:if test="${fn:contains(existingFeatures,reports)}">
			<li id="navListId7" class="dropdown"><a href="#"
				class="dropdown-toggle" data-toggle="dropdown"><spring:message code="reports.label.reports"/></a> <!--Navigation Block Sub Menu Start -->
				<ul class="dropdown-menu" role="menu">
						<li class="transperent-background"><a href="javascript:void(0)"></a></li>
					<%-- <c:if test="${fn:contains(existingFeatures,reportsEftTransfer)}">
						<li style="text-align:left;"><a href="specific-user-eftTransfers-show"><spring:message code="main-navigation.label.EFTtransfers"/></a></li>
					</c:if> --%>
					<c:if test="${fn:contains(existingFeatures,reportsMerchantRevenue)}">
						<li style="text-align:left;"><a href="showGlobalSysRevenueGeneratedReports"><spring:message code="reportsglobal.label.merchanttransactionrevenue"/></a></li>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,batchReport)}">
			        <li style="text-align:left;"><a href="merchant-batch-report"><spring:message code="chatak-batch-report" /></a></li>
			        </c:if>
					</ul> <!--Navigation Block Sub Menu End --></li>
		</c:if>
			<c:if test="${fn:contains(existingFeatures, scheduleReport)}">
			<li id="navListId8" class="dropdown"><a href="#"
				class="dropdown-toggle" data-toggle="dropdown"><spring:message code="chatak-report-lable-schedule-report"/></a>
				<ul class="dropdown-menu" role="menu">
						<li class="transperent-background"><a href="javascript:void(0)"></a></li>
						<c:if test="${fn:contains(existingFeatures,dailyFundingReport)}">
							<li style="text-align:left;"><a href="merchant-daily-funding-report"><spring:message code="chatak-report-lable-daily-funding-report"/></a></li>
						</c:if>
				</ul>
			</li>
	</c:if>
		
		<%-- <c:if test="${fn:contains(existingFeatures,adjustments)}">
		<li id="navListId11" class="dropdown">
		<a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="adjustments.label.adjustments"/></a>
		<ul class="dropdown-menu" role="menu">
			<li class="transperent-background"></li>
			<c:if test="${fn:contains(existingFeatures,adjustmentsAccount)}">
			<li><a href="show-account-transfer"><spring:message code="main-navigation.label.accounttransfers"/></a></li>
			</c:if>
		</ul>
	</li>
	</c:if> --%>
	
	<c:if test="${paypage eq '1'}">
		<li id="navListId12" class="dropdown"><a href="chatak-pay-page-config"><spring:message code="paypage.label.paypageconfiguration"/></a> </li>
	</c:if>
	
	<li id="navListId8" class="dropdown last-navigation-item pull-right">
		<a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="menu-label-menu"/></a> <!--Navigation Block Sub Menu Start -->
		<ul class="dropdown-menu last-navigation" role="menu">
			<li class="transperent-background"></li>
			
			<li><a href="merchant-change-password"><spring:message code="change-password.label.changepassword"/></a></li>
			<li><a href="chatak_merchant_myprofile"><spring:message code="myprofile.label.myprofile"/></a></li>
			<li ><a href="faq-management-search"><spring:message code="merchant.faq.Management.message"/></a></li>
					
			
		</ul> <!--Navigation Block Sub Menu End -->
	</li>
	</ul>
</nav>

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
<script src="../js/bootstrap.min.js"></script> <script src="../js/utils.js"></script>
<script >
$(document).ready(function() {
	$('#alertPopUp').popup({
		blur:false
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
<%-- <script>
function getLitleEFTTxns() {
	document.forms["litleEFTTxn"].submit();
}

$(document).ready( function() {
	// highlightMainContent();
	
	//setInterval("showCurrentTime()", 1000);
	//setInterval("showCurrentTime1()", 1000);
});

var months = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
var time = <%=System.currentTimeMillis()%>;
function showCurrentTime() {
	var date = new Date(time);
	var showdate = date.getDate() + " " + months[date.getMonth()] + " " +date.getFullYear() ;
	var hrs = date.getHours();
	var mins = date.getMinutes();
	var secs = date.getSeconds();
	hrs = (hrs > 9) ? hrs : "0"+hrs;
	mins = (mins > 9) ? mins : "0"+mins;
	secs = (secs > 9) ? secs : "0"+secs;
	//var showtime =  hrs + " : " + mins + " : " + secs;
	var showtime =  hrs + " : " + mins;
	time = time+1000;
	$('#showTimer').text(showtime + "-" + showdate);
}

<%
Calendar c = Calendar.getInstance();
int year = c.get(Calendar.YEAR);
int month = c.get(Calendar.MONTH);
int date = c.get(Calendar.DATE);
int hrs = c.get(Calendar.HOUR);
int mins = c.get(Calendar.MINUTE);
int secs = c.get(Calendar.SECOND);
%>

function showCurrentTime1(){
	var date = new Date(<%=year%> , <%=month%> , <%=date%> , <%=hrs%> , <%=mins%> , <%=secs%> );
	var tempMonth = date.getMonth() + 1;
	var month = (tempMonth > 9) ? tempMonth : "0" + tempMonth;
	var currentDate = (date.getDate() > 9) ? date.getDate() : "0" + date.getDate();
	var showdate = month + " / " + currentDate + " / " + date.getFullYear();
	var hrs = date.getHours();
	var mins = date.getMinutes();
	var secs = date.getSeconds();
	hrs = (hrs > 9) ? hrs : "0"+hrs;
	mins = (mins > 9) ? mins : "0"+mins;
	secs = (secs > 9) ? secs : "0"+secs;
	//var showtime =  hrs + " : " + mins + " : " + secs;
	var showtime =  hrs + ":" + mins;
	$('#showTimer').text(showtime + " - " + showdate);
}
 
   /* $(function(){
	$(".dropdown-menu > li > a.triger").on("click",function(e){
		var current=$(this).next();
		var grandparent=$(this).parent().parent();
		if($(this).hasClass('left-caret')||$(this).hasClass('right-caret'))
			$(this).toggleClass('right-caret left-caret');
		grandparent.find('.left-caret').not(this).toggleClass('right-caret left-caret');
		grandparent.find(".sub-menu:visible").not(current).hide();
		current.toggle();
		e.stopPropagation();
	}); 
	$(".dropdown-menu > li > a:not(.trigger)").on("click",function(){
		var root=$(this).closest('.dropdown');
		root.find('.left-caret').toggleClass('right-caret left-caret');
		root.find('.sub-menu:visible').hide();
	});
 });   */


   $(function(){
	   $(".dropdown-toggle").hover(function(e){
		   $(".sub-menu").hide();
		   });
	$(".dropdown-menu > li > a.trigger").hover(function(e){
		var current=$(this).next();
		var grandparent=$(this).parent().parent();
		if($(this).hasClass('left-caret')||$(this).hasClass('right-caret'))
			$(this).toggleClass('right-caret left-caret');
		grandparent.find('.left-caret').not(this).toggleClass('right-caret left-caret');
	 	grandparent.find(".sub-menu:visible").not(current).hide();
		current.toggle();
		e.stopPropagation();
	},function(){
		var root=$(this).closest('.dropdown');
		root.find('.left-caret').toggleClass('right-caret left-caret');
		//root.find('.sub-menu:visible').hide();
	});
	$(".dropdown-menu > li > a:not(.trigger)").on("click",function(){
		var root=$(this).closest('.dropdown');
		root.find('.left-caret').toggleClass('right-caret left-caret');
	//	root.find('.sub-menu:visible').hide();
	});
});  
</script> --%>
<%-- <script>
	var serverTimeStamp = <%=System.currentTimeMillis()%>;
	$(document).ready(function(){
	   showCurrentTime(serverTimeStamp);
	});
	
</script> --%>
		
		
<!--Navigation Block End -->
