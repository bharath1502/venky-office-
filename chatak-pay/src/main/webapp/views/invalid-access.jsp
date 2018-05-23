<html>
<head>
<link href="../../css/pg.css" rel="stylesheet">
</head>
<body>
<div style="text-align: center;color: white;">
	<h1>Chatak Online Payments</h1>
	<h1>Invalid Access. Please contact Chatak support team.</h1>
</div>
<script src="../../js/backbutton.js"></script>
<script type="text/javascript">
var getFinalReturnURL = function(response) {
	var cR = '${CHATAK_RETURN_URL}';
	if(cR === '') {
		return '#';
	}
	var queryParamSeperator = '';
	if (cR.substr(-1) === "?") {
		queryParamSeperator = '';
	} else {
		queryParamSeperator = '?&';
	}
	return (cR + queryParamSeperator + 'data=' + encodeURIComponent(JSON
			.stringify(response)));
};
setTimeout(function(){
	window.location.href = getFinalReturnURL({errorCode:'0011', errorMessage:'Invalid access, please contact support team'});
}, 6000);

</script>
</body>
</html>
