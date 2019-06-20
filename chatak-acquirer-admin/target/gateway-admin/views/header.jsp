<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page  import="java.util.Calendar"%>
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
<!--Header Block Start -->
<header class="col-sm-12 all-page-header">
 
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
					<td> <spring:message code="header.label.welcome"/>${loginResponse.email }</td>
					<td align="right"><a href="logout"><span
							class="glyphicon glyphicon-log-out"></span> <spring:message code="header.label.logout"/></a></td>
				</tr>
				<tr><td align="right" id="time" style="color:#0072C6;"></td></tr>
			</table>
		</div>
	</div>
	<!--Header Welcome Text and Logout button End -->
</header>
<!--Header Block End -->
<nav class="col-sm-12 nav-bar">
<span class="glyphicon glyphicon-list menu-icon"></span>
<ul class="navigation">
	<li id="navListId1" class="dropdown">
		<a href="home" class="dropdown-toggle"><spring:message code="header.label.dashboard"/></a>
	</li>
	<li id="navListId2" class="dropdown">
		<a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="header.label.setup"/></a>
		<div class="in-bag">
		<ul class="dropdown-menu" role="menu">
			<li class="transperent-background"></li>
			<li style="text-align:left;"><a href="access-role-search"><spring:message code="header.label.roles"/></a></li>
			<li style="text-align:left;"><a href="access-user-search?requestType=Admin"><spring:message code="header.label.user"/></a></li>
			<li style="text-align:left;"><a href="switch-search"><spring:message code="header.label.switchinformation"/></a></li>
			<li style="text-align:left;"><a href="bin-search-show"><spring:message code="header.label.onusbin"/></a></li>
			<li style="text-align:left;"><a href="black-listed-card-search"><spring:message code="header.label.blacklistedcard"/></a></li> 
			<li style="text-align:left;"><a href="payment-scheme-search-urlpage"><spring:message code="header.label.paymentscheme"/></a></li>
			<li style="text-align:left;"><a href="merchant-category-code-search"><spring:message code="header.label.merchantcategorycode"/></a></li> 
			<li style="text-align:left;"><a href="show-ca-public-keys-search-page"><spring:message code="header.label.capublickeys"/></a></li>
			<li style="text-align:left;"><a href="show-dynamic-MDR-search"><spring:message code="header.label.dynamicMDR"/></a></li>
		</ul>
		</div>
	</li>
	<li id="navListId3" class="dropdown">
		<a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="commission-program-search.label.programs"/></a> 
		<ul class="dropdown-menu" role="menu" style="width:140px">
			<li class="transperent-background"></li>
			<li style="text-align:left;"><a href="show-fee-program-search"><spring:message code="fee-program-search.label.feeprogram"/></a></li>
			</ul>
	</li>
	<li id="navListId6" class="dropdown">
		<a href="#" class="dropdown-toggle " data-toggle="dropdown"><spring:message code="manage.label.manage" /></a>
		<ul class="dropdown-menu" role="menu">
			<li class="transperent-background"></li>
			<li style="text-align:left;"><a href="merchant-search-page"><spring:message code="show-all-pending-merchants.label.merchant"/></a></li>
			<li style="text-align:left;"><a href="merchant-account-search"><spring:message code="merchant.label.merchantaccount"/></a></li>
			<li style="text-align:left;"><a href="sub-merchant-search-page"><spring:message code="manage.label.sub-merchant" /></a></li>
			<li style="text-align:left;"><a href="sub-merchant-account-search"><spring:message code="sub-merchant-account-search.label.sub-merchantaccount"/></a></li>
			<li style="text-align:left;"><a href="dcc-markup-search"><spring:message code="dcc.label.markup"/></a></li>
			<li style="text-align:left;"><a href="exchange-rate-create"><spring:message code="exchange.label.rate"/></a></li>
			<li style="text-align:left;"><a href="bank-search"><spring:message code="pending-merchant-show.label.bank"/></a></li> 
		</ul>
	</li>
	<li id="navListId5" class="dropdown">
		<a href="#" class="dropdown-toggle"><spring:message code="accounts-manual-credit.label.adjustments"/></a>
		<ul class="dropdown-menu" role="menu" style="width:130px">
			<li class="transperent-background"></li>
			<li style="text-align:left;"><a href="accounts-manual-credit"><spring:message code="accounts-manual-credit.label.manualcredit"/></a></li>
			<li style="text-align:left;"><a href="accounts-manual-debit"><spring:message code="accounts-manual-debit.label.manualdebit"/></a></li>
			<li style="text-align:left;"><a href="show-account-transfer"><spring:message code="show-account-transfer.label.accounttransfer"/> </a></li>
			<li style="text-align:left;"><a href="getLitleEFTTransactionListToDashBoard"><spring:message code="show-account-transfer.label.manualsweeper"/></a></li>
		</ul>
	</li>
	<li id="navListId4" class="dropdown">
		<a href="#" class="dropdown-toggle"><spring:message code="reports.label.reports" /></a>
		<ul class="dropdown-menu" role="menu">
			<li class="transperent-background"></li>
			<li style="text-align:left;"><a href="showSystemOverviewReports"><spring:message code="reports.label.overviewandbalancesheet" /></a></li>
			<li style="text-align:left;"><a href="showGlobalSysPendingTransReports"><spring:message code="reports.label.pendingtransactions" /></a></li>
			<li style="text-align:left;"><a href="transactions-search"><spring:message code="reports.label.transactionsreports" /> </a></li>
			<li style="text-align:left;"><a href="showGlobalSysBalancesReports"><spring:message code="reports.label.balancereports" /></a></li>
			<li style="text-align:left;"><a href="showGlobalSysManualTransReports"><spring:message code="reports.label.balancereports.manualtransactions" /></a></li>
		</ul>
	</li>
	<li id="navListId7" class="dropdown">
		<a href="virtual-terminal-sale" class="dropdown-toggle"><spring:message code="virtual-terminal-void.label.virtualterminal"/></a>
	</li>

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
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
<script>
function getLitleEFTTxns() {
	document.forms["litleEFTTxn"].submit();
}

$(document).ready( function() {
	// highlightMainContent();
	
	//setInterval("showCurrentTime()", 1000);
	//setInterval("showCurrentTime1()", 1000);
});

<%-- var months = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
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
} --%>

<%-- <%
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
} --%>
 
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
 });  */


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
</script>
	<script>
	var serverTimeStamp = <%=System.currentTimeMillis()%>;
	$(document).ready(function(){
	   showCurrentTime(serverTimeStamp);
	});
	
	</script>