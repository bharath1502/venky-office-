<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.Calendar"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
  int year = Calendar.getInstance().get(Calendar.YEAR);
%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Chatak Acquirer Admin</title>
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css" />
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
</head>
<body>
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<!--Header Block End -->
			<!--Navigation Block Start -->
			<!-- <nav class="col-sm-12 nav-bar" id="main-navigation"> -->
				<%-- <jsp:include page="header.jsp"></jsp:include> --%>
				<%@include file="navigation-panel.jsp"%>
			<!-- </nav> -->
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="manage.label.manage"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span>
						<span class="breadcrumb-text"><spring:message code="reseller-create.label.reseller"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="common.label.create"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<c:if test="${fn:contains(existingFeatures,resellerView) || fn:contains(existingFeatures,resellerEdit) || fn:contains(existingFeatures,resellerDelete)}">
					<div class="tab-header-container-first ">
						<a href="reseller-search-page"><spring:message code="common.label.search"/></a>
					</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,resellerCreate)}">
					<div class="tab-header-container active-background">
						<a href="#"><spring:message code="common.label.create"/></a>
					</div>
					</c:if>
					<!-- <div class="tab-header-container-first">
						<a href="merchant-search">Search</a>
					</div> -->
					<!-- <div class="tab-header-container-first active-background">
						<a href="#">Create</a>
					</div> -->
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder padding0">
						<!-- Page Menu Start -->
							<div class="row margin0">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionErrorMsg col-xs-12">
										<span class="red-error">&nbsp;${error}</span> <span
											class="green-error">&nbsp;${sucess}</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="createReseller" commandName="resellerData" name="resellerData">
									<div class="col-sm-12 paddingT20">
										<div class="row">
											<!-- Account Details Content Start -->
											<section class="field-element-row account-details-content" style="display:none;">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="reseller-create.label.resellername"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="resellerName"
															id="resellerName" maxlength="50" onblur="this.value=this.value.trim();validateResellerName()"
															 />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="resellerNameEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="reseller-create.label.primarycontactname"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="contactName"
															id="contactName" maxlength="50"
															onblur="this.value=this.value.trim();validateContactName()"  />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="contactNameEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""> <spring:message code="common.label.emailid"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="emailId"
															id="emailId" maxlength="50" onblur="this.value=this.value.trim();validateEmailId()"  />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="emailIdEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""> <spring:message code="reseller-create.label.phone"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="phone"
															id="phone" maxlength="10" onblur="this.value=this.value.trim();validatePhone()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="phoneEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.address1"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="address1"
															id="address1" maxlength="50" onblur="this.value=this.value.trim();validateAddress1()"   />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="address1Er" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.address2"/></label>
														<form:input cssClass="form-control" path="address2"
															id="address2" maxlength="50" onblur="this.value=this.value.trim();validateAddress2()"  />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="address2Er" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.city"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="city" id="city"
															maxlength="50" onblur="this.value=this.value.trim();validateCity()"   />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="cityEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.country"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="country"
															id="country" onblur="validateCountry()">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<c:forEach items="${countryList}" var="country">
																<form:option value="${country.label}">${country.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="countryEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.state"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="state"
															id="state" onblur="validateState()">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<c:forEach items="${stateList}" var="state">
																<form:option value="${state.label}">${state.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="stateEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.zipcode"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="zip" id="zip"
															maxlength="6" onblur="this.value=this.value.trim();validateZip()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="zipEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="common.label.status"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="status"
															id="status" onblur="validateStatus()" >
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<form:option value="0"><spring:message code="common.label.active"/></form:option>
														</form:select>
														<%-- <form:hidden path="merchantType" id="merchantType" /> --%>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="statusEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="submit" value='<spring:message code="common.label.create"/>'
															class="form-control button pull-right create-reseller"
															> <input type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.reset"/>' onclick="resetBasicInfo()"> <input
															type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.cancel"/>' onclick="cancelCreateReseller()">
													</fieldset>
												</div>
									</section>		
									</div>	
									</div>
								</form:form>
								<!-- Page Form End -->
							</div>
						</div>
					</div>
					<!-- Content Block End -->
				</div>
			</article>
			<!--Article Block End-->
			<footer class="footer">
				<jsp:include page="footer.jsp"></jsp:include>
			</footer>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/chatak-ajax.js"></script>
	<script src="../js/messages.js"></script>
	<script type="text/javascript" src="../js/merchant.js"></script>
	<script type="text/javascript" src="../js/reseller.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		window.onload = function() {
		//	populatedropdown("autoTransferDay");
		}
		function getState(countryId) {
			var cid = 2;
			var strURL = "findState?country=" + cid;
			var req = getXMLHTTP();
			if (req) {
				req.onreadystatechange = function() {
					if (req.readyState == 4) {
						// only if "OK"
						if (req.status == 200) {
							document.getElementById('statediv').innerHTML = req.responseText;
						} else {
							alert("There was a problem while using XMLHTTP:\n"
									+ req.statusText);
						}
					}
				}
				req.open("GET", strURL, true);
				req.send(null);
			}
		}
	</script>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script>
		/* function highlightMainContent() {
			$("#navListId2").addClass("active-background");
		} */
		/* Common Navigation Include End */
		/* DatePicker Javascript Strat*/
		$(document).ready(function() {
			$("#navListId6").addClass("active-background");
			$(window).keydown(function(event){
			    if(event.keyCode == 13) {
			      event.preventDefault();
			      return false;
			    }
			 });			
			
			
			var prevAppMode = "";
			$('#appMode').on('change',function(){
				var currentAppMode = $('#appMode').val();
				if(currentAppMode.length > 0 && currentAppMode != prevAppMode) {
					prevAppMode = currentAppMode;
					isParentAndAgentDetailsAvailable = false;
					populatePartnerAndAgentDetails(currentAppMode,'merchant','create',true);
				} else {
					prevAppMode = currentAppMode;
				}
			});

		});

		$(".focus-field").click(function() {
			$(this).children('.effectiveDate').focus();
		});
		$('.effectiveDate').datetimepicker({
			timepicker : false,
			format : 'm/d/Y',
			formatDate : 'Y/m/d',
		});
		
		
		/* DatePicker Javascript End*/
		$(".bank-info-details-content, .legal-details-content, .legal-details-rep-content, .free-transactions-content, .atm-transaction-content, .pos-transaction-content").hide();
		$(".account-details-content").show();
		$(".merchant-arrow").show();
		$(".contact-arrow, .bank-info-arrow, .legal-arrow, .legal-rep-arrow, .bank-legal-arrow, .bank-arrow, .configuration-arrow, .final-arrow").hide();
		$(".account-details-list, .bank-prev").click(function(){
            $(".merchant-circle-tab").addClass("active-circle");
			$(".bank-info-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .contact-circle-tab, .bank-circle-tab, .final-circle-tab").removeClass("active-circle");
			$(".merchant-arrow").show();
			$(".bank-info-arrow, .legal-arrow, .legal-rep-arrow, .contact-arrow, .bank-arrow, .final-arrow").hide();
			$(".account-details-content").show();
			$(".atm-transaction-content,.bank-info-details-content, .legal-details-content, .legal-details-rep-content, .pos-transaction-content, .free-transactions-content").hide();
		});
		
		 $(".bank-list, .acc-next, .legal-prev").click(function(){
			  if (!validateCreateMerchantStep1()
					 |resetBankInfoErrorMsg()) {
				return false;
			} 
            $(".bank-info-circle-tab").addClass("active-circle");
			$(".merchant-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .contact-circle-tab, .bank-circle-tab, .final-circle-tab").removeClass("active-circle");
			$(".bank-info-arrow").show();
			$(".merchant-arrow, .legal-arrow, .legal-rep-arrow, .contact-arrow, .configuration-arrow, .bank-arrow, .configuration-arrow, .final-arrow").hide();
			$(".bank-info-details-content").show();
			$(".account-details-content, .legal-details-content, .legal-details-rep-content, .atm-transaction-content, .pos-transaction-content, .free-transactions-content").hide();
			populatePartnerAndAgentDetails($('#appMode').val(),'merchant','create',false);
		}); 
		
		$(".legal-entiy-list, .bank-next, .legal-rep-prev").click(function(){
			  if (!validateCreateMerchantStep2()
					 |!validateCreateMerchantStep1()
					 |resetLegalEntityErrorMsg()) {
				return false;
			} 
            $(".legal-circle-tab").addClass("active-circle");
			$(".merchant-circle-tab, .bank-info-circle-tab, .legal-circle-rep-tab, .contact-circle-tab, .bank-circle-tab, .final-circle-tab").removeClass("active-circle");
			$(".legal-arrow").show();
			$(".merchant-arrow, .legal-rep-arrow, .bank-info-arrow, .contact-arrow, .configuration-arrow, .bank-arrow, .configuration-arrow, .final-arrow").hide();
			$(".legal-details-content").show();
			$(".account-details-content, .legal-details-rep-content, .bank-info-details-content, .atm-transaction-content, .pos-transaction-content, .free-transactions-content").hide();
		});

		$(".legal-entiy-rep-list, .legal-next, .free-prev").click(function(){
			 if (!validateLegalEntity()
					 |!validateCreateMerchantStep2()
					 |!validateCreateMerchantStep1()
					 |resetLegalEntityInfoErrorMsg()) {
				return false;
			} 
           	$(".legal-circle-rep-tab").addClass("active-circle");
			$(".merchant-circle-tab, .bank-info-circle-tab, .legal-circle-tab, .contact-circle-tab, .bank-circle-tab, .final-circle-tab").removeClass("active-circle");
			$(".legal-rep-arrow").show();
			$(".merchant-arrow, .bank-info-arrow, .legal-arrow, .contact-arrow, .configuration-arrow, .bank-arrow, .configuration-arrow, .final-arrow").hide();
			$(".legal-details-rep-content").show();
			$(".account-details-content, .bank-info-details-content, .legal-details-content, .atm-transaction-content, .pos-transaction-content, .free-transactions-content").hide();
		});

		$(".free-transactions-list, .legal-rep-next, .atm-prev").click(function() {
			  if (!validateCreateMerchantStep3()
					 |!validateCreateMerchantStep1()
					 |!validateCreateMerchantStep2()
					 |!validateLegalEntity()
					 |resetAdditionalInfoErrorMsg()) {
				return false;
			}  
			$(".contact-circle-tab").addClass("active-circle");
			$(".merchant-circle-tab,.bank-info-circle-tab, .bank-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .final-circle-tab").removeClass("active-circle");
			$(".contact-arrow").show();
			$(".merchant-arrow, .legal-arrow, .legal-rep-arrow, .bank-info-arrow, .configuration-arrow, .bank-arrow, .final-arrow").hide()
			$(".free-transactions-content").show();
			$(".atm-transaction-content, .legal-details-content, .legal-details-rep-content, .bank-info-details-content, .pos-transaction-content, .account-details-content").hide();
		});
		$(".atm-transactions-list, .free-next, .pos-prev").click(function() {
			  if (!validateCreateMerchantStep4()
					 |!validateCreateMerchantStep2()
					 |!validateCreateMerchantStep3()
					 |!validateCreateMerchantStep1()
					 |!validateLegalEntity()
					 |resetConfigurationsInfoErrorMsg()) { 
				return false;
			} 
			$(".bank-circle-tab").addClass("active-circle");
			$(".merchant-circle-tab,.bank-info-circle-tab, .contact-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .final-circle-tab").removeClass("active-circle");
			$(".configuration-arrow").show();
			$(".contact-arrow, .merchant-arrow, .legal-arrow, .legal-rep-arrow, .bank-info-arrow, .final-arrow").hide()
			$(".atm-transaction-content").show();
			$(".free-transactions-content, .bank-info-details-content, .legal-details-content, .legal-details-rep-content, .pos-transaction-content, .account-details-content").hide();
		});
		$(".pos-transactions-list, .atm-next").click(function() {
			  if (!validateCreateMerchantStep5()
					 |!validateCreateMerchantStep1()
					 |!validateCreateMerchantStep2()
					 |!validateCreateMerchantStep3()
					 |!validateLegalEntity()
					 |!validateCreateMerchantStep4()) {
				return false
			} 
			$(".final-circle-tab").addClass("active-circle");
			$(".merchant-circle-tab, .bank-info-circle-tab, .contact-circle-tab, .legal-circle-tab, .legal-circle-rep-tab, .bank-circle-tab").removeClass("active-circle");
			$(".final-arrow").show();
			$(".contact-arrow, .bank-arrow,.configuration-arrow, .bank-info-arrow, .legal-arrow, .legal-rep-arrow, .merchant-arrow").hide()
			$(".pos-transaction-content").show();
			$(".free-transactions-content, .bank-info-details-content, .legal-details-content, .legal-details-rep-content, .atm-transaction-content, .account-details-content").hide();
		});

		function loadRadio(data) {
			if (data == '0') {
				$("#noAutoSettlement").prop("checked", true);
				$("#allowAutoSettlement").prop("checked", false);
			} else if (data == '1') {
				$("#noAutoSettlement").prop("checked", false);
				$("#allowAutoSettlement").prop("checked", true);
			}
		}
		$('.create-reseller').click(function() {
			if(!validateCreateReseller())
			{
				return false;
			}
			
		
		});
	</script>
</body>
</html>