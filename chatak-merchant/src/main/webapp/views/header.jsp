<!--Header Block Start -->
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<header class="col-sm-12 all-page-header">
	<!--Header Logo Start -->
	<div class="col-sm-4">
		<img src="../images/logo.jpg" height="63px" alt="Logo" />
	</div>
	<!--Header Logo End -->
	<!--Header Welcome Text and Logout button Start -->
	<div class="col-sm-6 col-xs-offset-3">
		<div class="pull-right user-settings">
			<table>
				<tr>
					<td><spring:message code="header.label.welcome" />
						${loginResponse.userName }</td>
					<td align="right"><a href="logout"><span
							class="glyphicon glyphicon-log-out"></span><spring:message code="header.label.logout" /></a>
					</td>
				</tr>
				<tr>
					<td align="right" id="time" style="color: #0072C6;"></td>
				</tr>
			</table>
		</div>
	</div>
	<!--Header Welcome Text and Logout button End -->
	<script src="../js/backbutton.js"></script>
	<script src="../js/jquery.min.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/common-lib.js"></script>

	<script>
		var serverTimeStamp =
	<%=System.currentTimeMillis()%>
		;
		$(document).ready(function() {
			showCurrentTime(serverTimeStamp);
		});
	</script>
</header>