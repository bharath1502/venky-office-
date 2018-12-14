<html>
<head>
<link href="../css/pg.css" rel="stylesheet">
<script type="text/javascript">

function reload() {
	window.location.href = 'reload';
}
<% if(!com.chatak.pg.server.coreLauncher.PaymentGateway.isAcquirerStarted) { %>
setTimeout('reload()', 10000);
<%} %>
</script>
</head>
<body>
<div style="text-align: center; width:100%; margin:0; top:40%; left: 0;" class="login">
	<h1>${error }</h1>
</div>
</body>
</html>
