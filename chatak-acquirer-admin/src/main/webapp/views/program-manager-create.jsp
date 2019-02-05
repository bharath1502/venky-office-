<!DOCTYPE html>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.Calendar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.chatak.pg.util.Constants"%>
<%@ page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<%
  int year = Calendar.getInstance().get(Calendar.YEAR);
%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><spring:message code="common.lable.title" /></title>
<!-- Bootstrap -->
<link rel="icon" href="../images/favicon.png" type="image/png">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<link href="../css/jquery.datetimepicker.css" rel="stylesheet"
	type="text/css" />
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">

			<!--Navigation Block Start -->
			<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message
								code="manage.label.manage" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message
									code="admin.pm.message" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message
								code="common.label.create" /></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="marginL40">
						<c:if
							test="${fn:contains(existingFeatures,programmanagerView)||fn:contains(existingFeatures,programmanagerEdit)||fn:contains(existingFeatures,programmanagerSuspend)||fn:contains(existingFeatures,programmanagerActivate)}">
							<div class="tab-header-container">
								<a href="showProgramManager"><spring:message
										code="common.label.search" /> </a>
							</div>
						</c:if>
						<c:if test="${fn:contains(existingFeatures,programmanagerCreate)}">
							<div class="tab-header-container active-background">
								<a href="#"><spring:message code="common.label.create" /></a>
							</div>
						</c:if>
					</div>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<span class="green-error" id="sucessDiv">${sucess}</span> <span
									class="red-error" id="errorDiv">${error}</span>

								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="showProgramManager"
									name="programManagerDetailsForm"
									modelAttribute="programManagerRequest" method="post"
									onsubmit="buttonDisabled()" enctype="multipart/form-data">
									<input type="hidden" id="programManagerBankId"
										name="programManagerBankId" />
								    <input type="hidden" name="CSRFToken" value="${tokenval}">
									<input type="hidden" id="checkDefaultPMValue"
										name="checkDefaultPMValue" value="false">
										<input type="hidden" id="standardTimeOffset"
										name="standardTimeOffset">
										<form:hidden id="deviceTimeZoneOffset" path="deviceTimeZoneOffset" />
								        <form:hidden id="deviceTimeZoneRegion" path="deviceTimeZoneRegion" />
									<div class="col-md-100 col-sm-12 ">
										<div class="col-sm-12">
											<div class="row">
												<div class="field-element-row">
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message
																code="admin.pm.Name.message" /><span
															class="required-field">*</span></label>
														<form:input path="programManagerName"
															cssClass="form-control" id="programMangerName"
															onblur="clientValidation('programMangerName', 'program_manager_name','pgmmgrNameErrormsg')"
															onclick="clearErrorMsg('pgmmgrcompanynameerrormsg');" />

														<div class="discriptionErrorMsg">
															<span id="pgmmgrNameErrormsg" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message
																code="home.label.companyname" /><span
															class="required-field">*</span></label>
														<form:input path="companyName" cssClass="form-control"
															id="companyName" maxlength="50"
															onblur="clientValidation('companyName','company_name','pgmmgrcompanynameerrormsg')"
															onclick="clearErrorMsg('pgmmgrcompanynameerrormsg');" />

														<div class="discriptionErrorMsg">
															<span id="pgmmgrcompanynameerrormsg" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message
																code="admin.BusinessEntityName.message" /><span
															class="required-field">*</span></label>
														<form:input path="businessName" maxlength="50"
															cssClass="form-control" id="businessEntityName"
															onblur="clientValidation('businessEntityName','business_entity_name','pgmmgrbusinessentityerrormsg')"
															onclick="clearErrorMsg('pgmmgrbusinessentityerrormsg');" />
														<div class="discriptionErrorMsg">
															<span id="pgmmgrbusinessentityerrormsg" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message
																code="merchant.label.contactperson" /><span
															class="required-field">*</span></label>
														<form:input path="contactName" maxlength="50"
															cssClass="form-control" id="contactPerson"
															onblur="clientValidation('contactPerson','contact_person','pgmmgrcontactpersonerrormsg')"
															onclick="clearErrorMsg('pgmmgrcontactpersonerrormsg');" />
														<div class="discriptionErrorMsg">
															<span id="pgmmgrcontactpersonerrormsg" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message
																code="users.label.phonenumber" /><span
															class="required-field">*</span></label>
														<form:input path="contactPhone" maxlength="10"
															cssClass="form-control" id="contactPhone"
															onkeypress="return numbersonly(this,event)"
															onblur="clientValidation('contactPhone','partner_phone','pgmmgrcontactphoneerrormsg');validateContactPhone()"
															onclick="clearErrorMsg('contactphoneerrormsg');" />
														<div class="discriptionErrorMsg">
															<span id="pgmmgrcontactphoneerrormsg" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<spring:message code="search.label.numericsOnly"
														var="placeholder" />
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message code="label.extension" /></label>
														<form:input path="extension" maxlength="5"
															cssClass="form-control" id="extension"
															onblur="clientValidation('extension','extension_not_mandatory','extensionerr')"
															onkeypress="return numbersonly(this, event);" />
														<div class="discriptionErrorMsg">
															<span id="extensionerr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message
																code="userList-file-exportutil-emailId" /><span
															class="required-field">*</span></label>
														<form:input path="contactEmail" id="programManagerEmailId"
															cssClass="form-control" maxlength="50"
															onblur="clientValidation('programManagerEmailId', 'email','programManagerEmailIdEmailId_ErrorDiv')" />
														<div class="discriptionErrorMsg">
															<span id="programManagerEmailIdEmailId_ErrorDiv"
																class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6 acquirerCurrencyNames">
														<label><spring:message
																code="reports.label.balancereports.currency" /><span
															class="required-field">*</span></label>
														<form:select path="acquirerCurrencyName"
															id="acquirerCurrencyName" cssClass="form-control"
															onmouseover="fetchPmDetailsByPmType()"
															onblur="clientValidation('acquirerCurrencyName','orderType','acquirerCurrencyNameErrormsg')"
															onchange="fetchBankDetailsByCurrency(this.value)">
														</form:select>
														<div class="discriptionErrorMsg">
															<span id="acquirerCurrencyNameErrormsg" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="common.label.country" /><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control" path="country"
															id="country"
															onblur="clientValidation('country','country','countryNameErrormsg')"
															onchange="fetchPmState(this.value, 'state');fetchTimeZone(this.value)">
															<form:option value="">..:<spring:message
																	code="reports.option.select" />:..</form:option>
															<c:forEach items="${countryList}" var="country">
																<form:option value="${country.label}">${country.value}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip"
															data-placement="top" title="">
															<span id="countryNameErrormsg" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message
																code="common.label.state" /><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control" path="state"
															id="state"
															onblur="clientValidation('state','state','stateNameErrormsg')">
															<form:option value="">..:<spring:message
																	code="reports.option.select" />:..</form:option>
															<c:forEach items="${stateList}" var="item">
																<form:option value="${item.label}">${item.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip"
															data-placement="top" title="">
															<span id="stateNameErrormsg" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message
																code="prepaid-admin-program-manager-pm-timezone" /><span
															class="required-field">*</span></label>
														<form:select path="pmTimeZone" id="timezone"
															cssClass="form-control"
															onblur="clientValidation('timezone','state','timezone_ErrorDiv')">
															<form:option value="">
																<spring:message code="reports.option.select" />
															</form:option>
														</form:select>
														<div class="discriptionErrorMsg">
															<span id="timezone_ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message
																code="prepaid-admin-program-manager-batch-prefix" /><span
															class="required-field">*</span></label>
														<form:input path="batchPrefix" cssClass="form-control"
															id="batchPrefix"
															onblur="clientValidation('batchPrefix','batch_prefix','pgmmgrbatchPrefixerrormsg')"
															onclick="clearErrorMsg('pgmmgrbatchPrefixerrormsg');" />

														<div class="discriptionErrorMsg">
															<span id="pgmmgrbatchPrefixerrormsg" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message
																code="prepaid-admin-program-manager-scheduler-run-time" /><span
															class="required-field">*</span></label> <input type="time"
															name="schedulerRunTime" class="form-control"
															id="schedulerRunTime" step='1' min="00:00:00"
															max="24:00:00"
															onblur="clientValidation('schedulerRunTime','txn_date','pgmmgrschedulerRunTimeerrormsg')"
															onclick="clearErrorMsg('pgmmgrschedulerRunTimeerrormsg');" />

														<div class="discriptionErrorMsg">
															<span id="pgmmgrschedulerRunTimeerrormsg"
																class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-md-3 col-sm-6 acquirerBankNames">
														<label><spring:message
																code="admin.bank.label.bankname" /><span
															class="required-field">*</span></label> <select
															id="acquirerBankName" name="acquirerBankName"
															class="form-control"
															onblur="validateBank('acquirerBankName','acquirerBankNameerrormsg')"
															onclick="clearErrorMsg('acquirerBankNameerrormsg');"
															multiple="multiple">
														</select>
														<div class="discriptionErrorMsg">
															<span id="acquirerBankNameerrormsg" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-md-3 col-sm-6">
														<label><spring:message
																code="label.program.manager.logo" /></label>
														<div class="input-group">
															<span class="input-group-btn"> <span
																class="btn btn-primary btn-file"><spring:message
																		code="search.label.Browse" />&hellip; <input
																	type="file" name="programManagerLogo"
																	id="programManagerLogo" onchange="readURL(this);"
																	onblur="return readPartnerLogo(this,'programManagerLogoErrorDiv')"
																	onclick="clearErrorMsg('programManagerLogoErrorDiv');">
															</span>
															</span> <input type="text" id="load"
																class="form-control readonly" readonly>
														</div>
														<div class="discriptionErrorMsg">
															<span id="programManagerLogoErrorDiv" class="red-error">&nbsp;</span>
														</div>

														<div class="discriptionErrorMsg">
															<span id="programManagerLogoErrorDiv" class="red-error">&nbsp;</span>
														</div>
														<div class="issuancePMlogo">
															<a href="#" onclick="openPopup()"><spring:message
																	code="admin-programmanager-search-label.ViewProgramManager" /></a>
														</div>
													</fieldset>

													<div class="col-sm-12" id="customFieldsDiv">
														<div
															style="border: 1px solid #afafaf; padding: 5px; padding-top: 15px; margin-top: 10px; overflow: hidden;">
															<div
																style="background: #fff; position: absolute; top: 2px; color: #0072c6;">
																<spring:message code="admin.pm.label.paniinrange" />
															</div>
															<fieldset class="col-md-3 col-sm-6">
																<label><spring:message
																		code="admin.pm.label.panlow" /></label> <input type="text"
																	value="" name="panRangeList[0].panLow"
																	id="panRangeList[0].panLow" class="form-control"
																	onkeypress="return numbersonly(this, event);"
																	maxlength="10"
																	onblur="return validateTextFieldData(this.value,'panRangeList[0].panLow', 'panRangeList[0].panLowEr','${6}')"
																	style="width: 200px;" />

																<div class="discriptionErrorMsg">
																	<span id="panRangeList[0].panLowEr" class="red-error">&nbsp;</span>
																</div>
															</fieldset>
															<fieldset class="col-md-3 col-sm-6">
																<label><spring:message
																		code="admin.pm.label.panhigh" /></label> <input type="text"
																	value="" name="panRangeList[0].panHigh"
																	id="panRangeList[0].panHigh" class="form-control"
																	onkeypress="return numbersonly(this, event);"
																	onblur="return validateTextFieldData(this.value,'panRangeList[0].panHigh', 'panRangeList[0].panHighEr','${6}')"
																	maxlength="10" style="width: 200px;" />

																<div class="discriptionErrorMsg">
																	<span id="panRangeList[0].panHighEr" class="red-error">&nbsp;</span>
																</div>
															</fieldset>
															<button type="button" class="addSubRow add-btn-style"
																id="mainFeeValueBtn_0" onclick='addSubrow(this)'
																style="display: inline; float: left; margin: 23px 22px;">
																<span class="glyphicon glyphicon-plus"></span>
															</button>
															<div class="added-sub-row1 row"></div>
															<br>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<br>
									<!--Panel Action Button Start -->
											<div class="col-sm-12 form-action-buttons"style="padding-top: 25px;">
												<div class="col-sm-5"></div>
												<div class="col-sm-7">
													<input type="submit" class="form-control button pull-right"
														id="myButton"
														value="<spring:message code="common.label.create"/>"
														onclick="return ProgramManagerValidation()"> <a
														href="showCreateProgramManager"
														class="form-control button pull-right"><spring:message
															code="common.label.reset" /></a>
												</div>
											</div>
											<!--Panel Action Button End -->
								</form:form>
								<!-- Page Form End -->
							</div>
						</div>
					</div>
					<!-- Content Block End -->
				</div>
			</article>
			<!--Article Block End-->
			<jsp:include page="footer.jsp" />
		</div>
		<!--Container block End -->
	</div>
	<div id="LogoDiv" class="locatioin-list-popup">
		<span class="glyphicon glyphicon-remove" onclick="closePopup()"></span>
		<h2><spring:message code="label.program.manager.logo"/></h2>
		<form:form action="updateProgramManagerLogo" id="popupForm"
			modelAttribute="programManagerRequest" method="post">
		 <input type="hidden" name="CSRFToken" value="${tokenval}">
			<c:choose>
			 <c:when test="">
			   <img id="logoDisp"  src="" width="50%" height="50%" name="programManagerLogo"/>
			 </c:when>
			 <c:otherwise>
			   <spring:message code="admin-card-search-label.NoImagefound"/>
			 </c:otherwise>
			</c:choose>
		</form:form>
		<!--Panel Action Button End -->
	</div>
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/utils.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/sortable.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/bank.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/program-manager.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	<script>
		/* Select li full area function Start */
		$("li").click(function() {
			window.location = $(this).find("a").attr("href");
			return false;
		});

		$(document).ready(function() {
			if ($('#autorepenish').prop('checked') == true) {
				$(".thresholdAmount").show();
			} else if ($('#autorepenish').prop('checked') == false) {
				$(".thresholdAmount").hide();
			}
		});

		$(".check").click(function() {
			if ($('#autorepenish').prop('checked') == true) {
				$(".thresholdAmount").show();
			} else if ($('#autorepenish').prop('checked') == false) {
				setValue('accountThresholdamount', '');
				setValue('sendFund', '');
				setValue('bankId', '');
				setValue('accountThresholdamount', '');
				$(".thresholdAmount").hide();
			}
		});

		/* Select li full area function End */
		/* Common Navigation Include Start */
		$(function() {
			$("#main-navigation").load("main-navigation.html");
		});
		function highlightMainContent() {
			$("#navListId2").addClass("active-background");
		}
		/* Common Navigation Include End */
	</script>
	<script>
		var MAX_PROGRAM_MANAGER_LOGO_FILE_SIZE = 1024 * 1024 * 1;

		function readImageURL(input) {
			if (!isValidImage(input.value)) {
				document.getElementById('image_div').innerHTML = webMessages.ALLOWED_IMAGES;
				return;
			}
			document.getElementById('image_div').innerHTML = '';
			if (input.files && input.files[0]) {
				if (parseInt(MAX_PROGRAM_MANAGER_LOGO_FILE_SIZE) < parseInt(input.files[0].size)) {
					document.getElementById('image_div').innerHTML = webMessages.IMAGE_SIZE;
					return;
				}
			}
		}

		function isValidImage(imageSrc) {
			var value = imageSrc.toUpperCase();
			if (value.indexOf('.PNG') != -1 || value.indexOf('.JPG') != -1
					|| value.indexOf('.JPEG') != -1
					|| value.indexOf('.GIF') != -1
					|| value.indexOf('.BMP') != -1) {
				return true;
			}
			return false;
		}

		
		$(document).ready(function() {
			$(".issuanceProgramManager").hide();
			$(".issuancePMlogo").hide();
			if ('${programManagerRequest.programManagerType}' == 'onboarded') {
				document.getElementById('programMangerName').readOnly = true;
				document.getElementById('companyName').readOnly = true;
				document.getElementById('businessEntityName').readOnly = true;
				document.getElementById('contactPerson').readOnly = true;
				document.getElementById('extension').readOnly = true;
				document.getElementById('batchPrefix').readOnly = true;
				document.getElementById('schedulerRunTime').readOnly = true;
				document.getElementById('contactPhone').readOnly = true;
				document.getElementById('programManagerEmailId').readOnly = true;
				$('#currencyName').attr("readonly", "readonly"); 
				$('#country').attr("readonly", "readonly");
				$('#state').attr("readonly", "readonly");
				$('#timezone').attr("readonly", "readonly");
			}

		});

		function closePopup() {
			$('#LogoDiv').popup("hide");
		}
		function openPopup() {
			$('#LogoDiv').popup("show");
		}
		document.getElementById("schedulerRunTime").value = "00:00:00";
		function changeProgramManager() {
			if ($('#checkDefaultProgramManager').prop('checked') == true) {
				$('#checkDefaultPMValue').val(true);
			} else {
				$('#checkDefaultPMValue').val(false);
				setDiv('sucessDiv', '');
			}
		}

		$(document).ready(function() {
			/* Table Sorter includes Start*/
			$(function() {

				// call the tablesorter plugin
				$('#serviceResults').sortable({

					divBeforeTable : '#divbeforeid',
					divAfterTable : '#divafterid',
					initialSort : false,
					locale : 'th',
				//negativeSort: [1, 2]
				});
			});
		});
		
		var editPage = false;
        var rangeIndex=0;
        var totalCustomFieldRows = 0;
        function addSubrow($this) {
        	if(totalCustomFieldRows>8){
        		return;
        	}
        	var currentMainPositionAr = $this.id.split('_');
        	rangeIndex++;
        	totalCustomFieldRows++;
        	var newFilterRow = "<fieldset class='col-sm-12 sub-row-field' data-sub-index="
    			+ rangeIndex
    			+ " id='custom-div"
    			+ rangeIndex
    			+"'><fieldset class='col-sm-4 marginML-subMenu create_partner_field'><label class='partner-custom-field-label'>Pan Low</label><input type='text' value='' onkeypress='return numbersonly(this, event);' onblur='return validateTextFieldData(this.value, this.id ,\"panRangeList["+rangeIndex+"].panLowEr\",${6})' name='panRangeList["
    			+rangeIndex
    			+"].panLow' id='panRangeList["
    			+ rangeIndex
    			+ "].panLow' class='form-control feePercentage txnamount' maxlength='10' style='width: 200px;'><div class='discriptionErrorMsg'><span id='panRangeList["
    			+ rangeIndex
    			+ "].panLowEr' class='red-error'>&nbsp;</span></div></fieldset>"
    			+"<fieldset class='col-sm-4 marginML-subMenu create_partner_field'><label class='partner-custom-field-label'>Pan High</label><input type='text' value='' onkeypress='return numbersonly(this, event);' onblur='return validateTextFieldData(this.value, this.id ,\"panRangeList["+rangeIndex+"].panHighEr\",${6})' name='panRangeList["
    				+rangeIndex
    				+"].panHigh' id='panRangeList["
        			+ rangeIndex
        			+ "].panHigh' class='form-control feePercentage txnamount' maxlength='10' style='width: 200px;'><div class='discriptionErrorMsg'><span id='panRangeList["
        			+ rangeIndex
        			+ "].panHighEr' class='red-error'>&nbsp;</span></div></fieldset><fieldset class='col-sm-1 textCenter ' ><span class='glyphicon glyphicon-trash delete-refund-sub-icon' style='cursor:pointer;margin-top: 60%;margin-left: 20%;'></span></fieldset></fieldset>";
        	if(editPage) {
        		if($($this).parent().children().hasClass('added-sub-row1')) {
        			$($this).parent().find('.added-sub-row1').append(newFilterRow);
        		} else {
        			$($this).parent().siblings().find('.added-sub-row1').append(newFilterRow);
        		}
        		
        	} else {
        		$($this).parent().find('.added-sub-row1').append(newFilterRow);
        	}
        }
        
        $('body')
		.on(
				'click',
				'.delete-refund-sub-icon',
				function() {
					var deleteEle = $(this)[0];
					$(this).parent().parent().remove();
					totalCustomFieldRows = totalCustomFieldRows-1;
				});
        
        function validateTextFieldData(textFieldData,textFieldId, messageLine_errDiv, textFieldLength){
    		var flag = true;
    		textFieldData = textFieldData.trim();
    			var numericValues = /^[0-9]+$/;
    			if(textFieldData == ''){
    				setDiv(messageLine_errDiv, "Please Enter Pan Range");
    				flag = false;
    			} else if (textFieldData.length < textFieldLength) {
    				setDiv(messageLine_errDiv, "Pan Range value should be more than " + textFieldLength);
    				flag = false;
    			} else {
    				setDiv(messageLine_errDiv, '');
    			}
    		return flag;
    	}
	</script>
</body>
</html>
