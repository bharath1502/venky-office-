<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.pg.constants.PGConstants"%>
<%@ page import="com.chatak.merchant.constants.StatusConstants"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><spring:message code="common.lable.title"/></title>
<!-- Bootstrap -->
<link rel="icon" href="../images/favicon.png" type="image/png">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/jquery.Jcrop.css" rel="stylesheet">
<link type="text/css" media="screen" rel="stylesheet" href="../css/jquery.cropbox.css">
<link href="../css/style.css" rel="stylesheet">
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css" />

</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<jsp:include page="header.jsp"></jsp:include>
			<!--Navigation Block Start -->
			<nav class="col-sm-12 nav-bar" id="main-navigation">
				<jsp:include page="navigation-panel.jsp"></jsp:include> 
			</nav>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="paypage.label.paypageconfiguration"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="paypage.label.paypagedetails"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="paypage.label.paypageconfiguration"/></a>
					</div>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error">&nbsp;${error}</span> <span
											class="green-error">&nbsp;${success}</span>
									</div>
								</div>
								<!--Success and Failure Message End-->

								<form:form action="chatak-pay-page-config" name="paypageconfig"
									modelAttribute="payPageConfig" method="post">
									<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-3">
												<legend></legend>
												<label></label>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="paypage.label.paypageheader"/><span class="required-field">*</span></label>
													<form:input id="header" cssClass="form-control"
														path="header" onblur="validateHeader()" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="headerEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
												<legend></legend>
												<label></label>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="paypage.label.paypagefooter"/><span class="required-field">*</span></label>
													<form:input id="footer" cssClass="form-control"
														path="footer" onblur="validateFooter()" />
													<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
														<span id="footerEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>
											<div class="row">
												<fieldset class="col-sm-12">
												<legend></legend>
													<div class="col-lg-12">
														<div class="field-element-row">
															<label id="lblImage"><spring:message code="paypage.label.uploadimage"/><span
																class="required-field">*</span></label><br> <input
																type="button" id="btnImageUpload" value="<spring:message code="paypage.label.chooseLogo"/>"
																style="opacity: 1" /> <input type="file"
																id="imageUpload" name="imageUpload"
																onchange="readThumbURL(this)"
																accept=".jpg,.png,.gif,.bmp,.jpeg" style="display: none" />
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span class="red-error" id="imageError">&nbsp;</span>
															</div>

															<table border="0" cellpadding="0" cellspacing="5">
																<tr>
																	<td><img id="userSelectedImage"
																		src="${payPageLogo}" alt="" /></td>
																	<td>
																		<canvas id="canvas" height="5" width="5"></canvas>
																	</td>
																</tr>
															</table>
															<br /> <input type="button" id="btnCrop" value="Crop"
																style="display: none" /> <input type="button"
																id="cropsave" value="Save" style="display: none" /> <input
																type="button" id="clearCrop" value="Clear"
																style="display: none" /> <input type="hidden"
																name="imgX1" id="imgX1" /> <input type="hidden"
																name="imgY1" id="imgY1" /> <input type="hidden"
																name="imgWidth" id="imgWidth" /> <input type="hidden"
																name="imgHeight" id="imgHeight" /> <input type="hidden"
																id="payPageLogo" name="payPageLogo"
																value="${payPageLogo}" />
														</div>

													</div>
												</fieldset>

											</div>
										</div>
										<!--Panel Action Button Start -->
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit" class="form-control button pull-right"
													value='<spring:message code="common.label.submit"/>' onclick="return validatePayPageConfig();">
												<input type="button" onclick="cancel();"
													value='<spring:message code="common.label.back"/>' class="form-control button pull-right" >
											</div>
										</div>

										<!--Panel Action Button End -->
									</div>
								</form:form>
								<!-- Page Form End -->
							</div>
						</div>
					</div>
				</div>
			</article>
			<!--Article Block End-->
			<jsp:include page="footer.jsp"></jsp:include>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->
	<script src="../js/jquery.min.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/messages.js"></script>
	<script type="text/javascript" src="../js/jquery.cropbox.js"></script>
	<script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/hammer.js/1.0.10/hammer.js"></script>
	<script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery-mousewheel/3.1.11/jquery.mousewheel.js"></script>
	<!-- Bootstrap Core JavaScript -->
	<script src="../js/pay-page-config.js"></script>
	<script src="../js/bootstrap.min.js"></script> <script src="../js/utils.js"></script>
	<script src="../js/jquery.Jcrop.js"></script>
	<script src="../js/backbutton.js"></script>
</body>
</html>