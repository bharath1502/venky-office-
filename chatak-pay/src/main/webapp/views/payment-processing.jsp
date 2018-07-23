<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
     <title>Chatak Pay Online - Gateway</title>
    <!-- Bootstrap -->
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
    <link href="../../css/font-awesome.css" rel="stylesheet">
    <link href="../../css/style.css" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript">
    	var page = 2;
    	var cR = '${CHATAK_RETURN_URL}';
    </script>
</head>
<body>
	<!--Body Wrapper block Start -->	
    <div class="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<header class="col-sm-12 all-page-header">
				<!--Header Logo Start -->				
				<div class="col-sm-4"> 
					<img src="../../images/chatak_logo.jpg" height="35px" alt="Logo"/>
				</div>
				<!--Header Logo End -->	
			</header>
			<!--Header Block End -->
			<!--Navigation Block Start -> 
			<nav class="col-sm-12 nav-bar" id="main-navigation"></nav>
			<!--Navigation Block Start -->   			
			<!--Article Block Start-->
			<article>
				<div id="progressingId" class="col-xs-12 content-wrapper">
					<fieldset class="col-sm-12 padding0">
						<div class="process-icon"></div>
						<div style="text-align: center;">Do not use back button or refresh the page and ensure you are connected to the internet until the transaction confirmation is required.</div>
					</fieldset>
				</div>
				<div id="errorId" class="col-xs-12 content-wrapper" style="display:none;">
					<fieldset class="col-sm-12 padding0">
						<div style="text-align: center; color:red;">${error } Please wait a moment.</div>
					</fieldset>
				</div>
			</article>
			<!--Article Block End-->
			<footer class="footer">
				<p class="footer-txt">Copyright &copy; 2014-2016. Chatak All Rights Reserved.</p>
			</footer>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->	

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="../../js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../../js/bootstrap.min.js"></script>
    <script src="../../js/backbutton.js"></script>
    <script type="text/javascript">
    function getFinalReturnURL(response) {
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
    	var isSuccess = true;
    	<c:if test="${ not empty error }">
	    	$('#progressingId').html('');
	    	$('#progressingId').hide();
	    	$('#errorId').show();
	    	isSuccess = false;
	    	 setTimeout(function(){
	    		 <c:if test="${ error eq msg }">
		    		 var responseJson = {errorCode:'TXN_0012', errorMessage:'${error}'};
		    		 window.location.href = getFinalReturnURL(responseJson);
	    		 </c:if>
	    		 <c:if test="${ error ne msg }">
		    		 var responseJson = {errorCode:'TXN_0997', errorMessage:'Invalid request. Please try again.'};
		    		 window.location.href = getFinalReturnURL(responseJson);
	    		 </c:if>
	 			
	    	 }, 4000);
    	</c:if>
    	if(isSuccess) {
    		window.location.href = 'payment-info';
    	}
    }, 2500);
    
    </script>
  </body>  
</html>