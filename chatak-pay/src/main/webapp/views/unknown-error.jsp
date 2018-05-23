<html>
<head>
<link href="../css/pg.css" rel="stylesheet">
</head>
<body>
<div style="text-align: center;color: white;">
	<h1>Chatak Online Payments</h1>
	<h1>User session is expired, the page is redirecting please wait.</h1>
</div>
<script src="../js/backbutton.js"></script>	
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
	window.location.href = getFinalReturnURL({errorCode:'0011', errorMessage:'User session is expired, please try again'});
}, 4000);

</script>
</body>
</html>
