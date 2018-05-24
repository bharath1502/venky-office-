<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<html lang="en">
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><spring:message code="common.lable.title"/></title>
    <!-- Bootstrap -->
    <link rel="icon" href="../images/favicons.png" type="image/png">
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link href="../css/style.css" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<!--Body Wrapper block Start -->	
    <div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Navigation Block Start --> 
			<nav class="col-sm-12 nav-bar" id="main-navigation">
			<jsp:include page="header.jsp"></jsp:include>
			<%@include file="navigation-panel.jsp"%>
			</nav>
			<!--Navigation Block Start -->  			
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">					
					<!-- Breadcrumb start -->
					<div class="breadCrumb">						
						<span class="breadcrumb-text"><spring:message code="home.label.merchant"/></span>
						<span class="glyphicon glyphicon-play icon-font-size"></span>
						<span class="breadcrumb-text"><spring:message code="search-sub-merchant.label.search"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<!--div class="tab-header-container-first active-background">
						<a href="#">Search </a>					
					</div>					
					<div class="tab-header-container">
						<a href="manage-account-create.html">Create</a>
					</div>					
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error">&nbsp;${error}</span>
										<span class="green-error">&nbsp;${sucess} </span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								
							</div>
						</div>
					</div>
					<!-- Content Block End -->	
					
				</div>
			</article>
			<!--Article Block End-->
			<jsp:include page="footer.jsp"></jsp:include>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->	
    
	<script>		
		/* Select li full area function Start */
		$("li").click(function(){
			window.location=$(this).find("a").attr("href"); 
			return false;
		});
		/* Select li full area function End */		
		$(document).ready( function() {
			highlightMainContent('navListId1');
		});	
	</script>
	<script src="../js/backbutton.js"></script>	
  </body>  
</html>