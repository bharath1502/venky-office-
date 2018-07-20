<!DOCTYPE html>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.Calendar"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	<link href="https://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
	
<!-- Global declaration for pmIDs and cardProgramIds -->	
	<script type="text/javascript">
	var programManagerIdList = [];
	var cardProgramIdList = [];
	function setProgramManagerId(pmId){
		programManagerIdList.push(pmId);
	}
	function setCardProgramId(cpId){
		cardProgramIdList.push(cpId);
	}
	</script>
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
									code="admin.iso.label" /></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message
								code="commission-program-update.label.edit" /></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="marginL40">
						<c:if
							test="${fn:contains(existingFeatures,programmanagerView)||fn:contains(existingFeatures,programmanagerEdit)||fn:contains(existingFeatures,programmanagerSuspend)||fn:contains(existingFeatures,programmanagerActivate)}">
							<div class="tab-header-container">
								<a href="showIsoSearch"><spring:message
										code="common.label.search" /> </a>
							</div>
						</c:if>
						<c:if test="${fn:contains(existingFeatures,programmanagerCreate)}">
							<div class="tab-header-container active-background">
								<a href="#"><spring:message code="commission-program-update.label.edit" /></a>
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
								<form:form action="updateIso"
									modelAttribute="isoEdit" method="post"
									onsubmit="buttonDisabled()" enctype="multipart/form-data">
									<input type="hidden" name="isoRequest[0].cardProgramIds" id="cardProgramIds">
									<input type="hidden" name="isoRequest[0].programManagerIds" id="programManagerIds">
									<input type="hidden" name="CSRFToken" value="${tokenval}">
									<form:hidden path="isoRequest[0].id" id="isoId"/>
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="admin.iso.Name.message" /><span
														class="required-field">*</span></label>
													<form:input id="isoName"
														path="isoRequest[0].isoName" maxlength="100"
														cssClass="form-control"
														onblur="clientValidation('isoName','program_manager_name','isonameerrormsg')"
														onclick="clearErrorMsg('pgmmgrnameerrormsg');" />
													<div class="discriptionErrorMsg">
														<span id="isonameerrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="admin.BusinessEntityName.message" /><span
														class="required-field">*</span></label>
													<form:input path="isoRequest[0].programManagerRequest.businessName" maxlength="50"
														cssClass="form-control" id="businessEntityName"
														onblur="clientValidation('businessEntityName','business_entity_name','isobusinessentityerrormsg')"
														onclick="clearErrorMsg('isobusinessentityerrormsg');" />
													<div class="discriptionErrorMsg">
														<span id="isobusinessentityerrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="merchant.label.contactperson" /><span
														class="required-field">*</span></label>
													<form:input path="isoRequest[0].programManagerRequest.contactName" maxlength="50"
														cssClass="form-control" id="contactPerson"
														onblur="clientValidation('contactPerson','contact_person','isocontactpersonerrormsg')"
														onclick="clearErrorMsg('isocontactpersonerrormsg');" />
													<div class="discriptionErrorMsg">
														<span id="isocontactpersonerrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="common.label.currency" /><span
														class="required-field">*</span></label>
													<form:input path="isoRequest[0].programManagerRequest.accountCurrency" maxlength="50"
														cssClass="form-control" id="currency"
														onblur="clientValidation('currency','contact_person','isoCurrencyerrormsg')"
														readonly="true"
														onclick="clearErrorMsg('isoCurrencyerrormsg');" />
													<div class="discriptionErrorMsg">
														<span id="isoCurrencyerrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="users.label.address" /><span
														class="required-field">*</span></label>
													<form:input cssClass="form-control" path="isoRequest[0].address"
														id="address1" maxlength="100"
														onblur="this.value=this.value.trim();validateAddress()" />
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="address1Er" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="common.label.city" /><span class="required-field">*</span></label>
													<form:input cssClass="form-control" path="isoRequest[0].city" id="city"
														maxlength="100"
														onblur="this.value=this.value.trim();validateCity()" />
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="cityEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="common.label.country" /><span
														class="required-field">*</span></label>
													<form:select cssClass="form-control" path="isoRequest[0].country"
														id="country" onblur="validateCountry()"
														onchange="fetchState(this.value, 'state')">
														<form:option value="">..:<spring:message
																code="reports.option.select" />:..</form:option>
														<c:forEach items="${countryList}" var="country">
															<form:option value="${country.label}">${country.label}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="countryEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="common.label.state" /><span class="required-field">*</span></label>
													<form:select cssClass="form-control" path="isoRequest[0].state"
														id="state" onblur="validateState()">
														<form:option value="">..:<spring:message
																code="reports.option.select" />:..</form:option>
														<c:forEach items="${stateList}" var="item">
															<form:option value="${item.label}">${item.label}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="stateEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>

												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="common.label.zipcode" /><span
														class="required-field">*</span></label>
													<form:input cssClass="form-control" path="isoRequest[0].zipCode" id="zip"
														maxlength="7" onkeypress="generalZipCode()"
														onblur="this.value=this.value.trim();return zipCodeNotEmpty(id)" />
													<div class="discriptionErrorMsg" data-toggle="tooltip"
														data-placement="top" title="">
														<span id="zipEr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="users.label.phonenumber" /><span
														class="required-field">*</span></label>
													<form:input path="isoRequest[0].programManagerRequest.contactPhone" maxlength="10"
														cssClass="form-control" id="contactPhone"
														onblur="clientValidation('contactPhone','partner_phone','isocontactphoneerrormsg')"
														onclick="clearErrorMsg('isocontactphoneerrormsg');" />
													<div class="discriptionErrorMsg">
														<span id="isocontactphoneerrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<spring:message code="search.label.numericsOnly"
													var="placeholder" />
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="label.extension" /></label>
													<form:input path="isoRequest[0].programManagerRequest.extension" maxlength="5"
														cssClass="form-control" id="extension"
														onkeypress="return numbersonly(this, event);" />
													<div class="discriptionErrorMsg">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="userList-file-exportutil-emailId" /><span
														class="required-field">*</span></label>
													<form:input path="isoRequest[0].programManagerRequest.contactEmail" id="isoEmailId"
														cssClass="form-control" maxlength="50"
														onblur="clientValidation('isoEmailId', 'email','isoEmailId_ErrorDiv')" />
													<div class="discriptionErrorMsg">
														<span id="isoEmailId_ErrorDiv"
															class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="bank.label.bankname" /></label>
													<form:input path="isoRequest[0].bankName"
														cssClass="form-control" id="bankName" />
													<div class="discriptionErrorMsg">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="merchant.label.bankaccountnumber" /></label>
													<form:input path="isoRequest[0].bankAccNum" 
														cssClass="form-control" id="bankAccNum" maxlength="<%=Constants.BANK_ACCOUNT_NUMBER.toString()%>"
														onkeypress="return numbersonly(this, event);" />
													<div class="discriptionErrorMsg">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="merchant.label.bankroutingnumber" /></label>
													<form:input path="isoRequest[0].routingNumber"
														cssClass="form-control" id="routingNumber" maxlength="<%=Constants.ACCOUNT_ROUTING_NUMBER.toString()%>"
														onkeypress="return numbersonly(this, event)"  />
													<div class="discriptionErrorMsg">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="label.iso.logo" /></label>
													<div class="input-group">
														<span class="input-group-btn"> <span
															class="btn btn-primary btn-file"><spring:message
																	code="search.label.Browse" />&hellip; <input
																type="file" name="isoLogo"
																id="isoLogo" onchange="readURL(this);"
																onblur="return readPartnerLogo(this,'isoLogoErrorDiv')"
																onclick="clearErrorMsg('isoLogoErrorDiv');">
														</span>
														</span> <input type="text" id="load" class="form-control readonly" readonly>
													</div>
													<div class="discriptionErrorMsg">
														<span id="isoLogoErrorDiv" class="red-error">&nbsp;</span>
													</div>
													<div><a href="#" onclick="openPopup()" ><spring:message code="view.iso.logo"/></a></div>
												</fieldset>
													<fieldset class="col-sm-12">
													<fieldset class="col-sm-5 multi-select-box">
													<label><spring:message code="admin.program.manager.list" /></label>
														<select class="features form-control left-select-box" id="programManagers"  name="from"  size="8" multiple="multiple">
																<c:forEach items="${programManagers}"
																	var="programManager">
															        <option value="${programManager.id}">${programManager.programManagerName}</option>
																</c:forEach>
														</select>
													</fieldset>
													<fieldset class="col-sm-1 multi-select-btn marginT60">
															<span class="left-right-btn form-control" id="undo_redo_rightSelected"
																onClick="SelectMoveRows(document.getElementsByClassName('features')[0],document.getElementsByClassName('features-codes')[0],'ADD')">
																&gt; </span>
																
																 <span class="right-left-btn form-control" id="undo_redo_leftSelected"
																onClick="SelectMoveRows(document.getElementsByClassName('features-codes')[0],document.getElementsByClassName('features')[0],'REMOVE')">
																&lt; </span>
																
														</fieldset>
													<label><spring:message code="admin.associated.pm" /><span
														class="required-field">*</span></label>
													<fieldset class="col-sm-5 multi-select-box">
														<select  id="selectedProgramManager"
															class="features-codes form-control right-select-box"  name="to" id="undo_redo_to"  size="8" multiple="multiple">
															<c:forEach items="${selectedPmList}"
																	var="programManager">
																	<script>
																	setProgramManagerId('${programManager.id}');
																	</script>
															        <option value="${programManager.id}">${programManager.programManagerName}</option>
															</c:forEach>
														</select>
														<div class="discriptionErrorMsg">
															<span class="red-error"
																id="selectedProgramManager_ErrorDiv">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												
											</div>
											<!-- Content Block End -->
					<!-- Search Table Block Start -->
						<div class="search-results-table">
						<table
								class="table table-striped table-bordered table-condensed marginBM1">
								<!-- Search Table Header Start -->
								<tr>
									<td class="search-table-header-column widthP80"></span>
										<span><spring:message code="admin.Associated.Card.Program.List" /></span>
								</tr>
							</table>
							<!-- Search Table Header End -->
							<!-- Search Table Content Start -->
							<table id="serviceResults"
								class="table table-striped table-bordered table-responsive table-condensed tablesorter marginBM1 common-table">
								<thead>
									<tr>
										<th>Partner</th>
										<th>Card Program Name</th>
										<th>IIN</th>
										<th>Partner IIN</th>
										<th>IIN Ext</th>
										<th style="width: 15%;">Program Manager</th>
										<th>Currency</th>
										<th>Action</th>
									</tr>
								</thead>
								<c:choose>
									<c:when test="${!(fn:length(selectedCardProgramList) eq 0) }">
										<c:forEach items="${selectedCardProgramList}" var="cardProgramDetail">
												<script>
												setCardProgramId('${cardProgramDetail.cardProgramId}');
												</script>
												<tr id="rowId${cardProgramDetail.cardProgramId}">
												<td  class="ellipsis"
													id="15" >${cardProgramDetail.partnerName}&nbsp;</td>
												<td  class="ellipsis"
													id="15" >${cardProgramDetail.cardProgramName}&nbsp;</td>
												<td >${cardProgramDetail.iin}&nbsp;</td>
												<td >${cardProgramDetail.partnerCode}&nbsp;</td>
												<td >${cardProgramDetail.iinExt}&nbsp;</td>
												<td>${cardProgramDetail.programManagerName}&nbsp;</td>
												<td>${cardProgramDetail.currency}&nbsp;</td>
												<td><input id="cpId${cardProgramDetail.cardProgramId}" type="checkbox" checked="checked" onclick="addCardProgram('${cardProgramDetail.cardProgramId}')"></td>
											</tr>
										</c:forEach>
									</c:when>
								</c:choose>
							</table>
							<!-- Search Table Content End -->
											</div>
											<!--Panel Action Button Start -->
											<div class="col-sm-12 form-action-buttons">
												<div class="col-sm-5"></div>
												<div class="col-sm-7">
													<input type="submit" class="form-control button pull-right"
														id="buttonCreate"
														value="<spring:message code="common.label.update"/>"
														onclick="return validateUpdateIso()"> <a
														href="showIsoSearch"
														class="form-control button pull-right"><spring:message
															code="common.label.cancel" /></a>
												</div>
											</div>

											<!--Panel Action Button End -->
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
			<jsp:include page="footer.jsp" />
		</div>
		<!--Container block End -->
	</div>
	<!-- Pop Up box information starts here -->
	<div id="LogoDiv" class="locatioin-list-popup">
		<span class="glyphicon glyphicon-remove" onclick="closePopup()"></span>
		<h2><spring:message code="label.iso.logo"/></h2>
			<c:choose>
			 <c:when test="${not empty imageData}">
			   <img id="logoDisp"  src="${imageData}" width="50%" height="50%" name="isoLogo"/>
			 </c:when>
			 <c:otherwise>
			   <spring:message code="admin-card-search-label.NoImagefound"/>
			 </c:otherwise>
			</c:choose>
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
	<script src="../js/validation.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/program-manager.js"></script>
	<script src="../js/iso.js"></script>
	<script src="../js/bank.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
		<script src="../js/multi-select.js"></script>
	<script>
		/* Select li full area function Start */
		$("li").click(function() {
			window.location = $(this).find("a").attr("href");
			return false;
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

		/* $("#contactPhone").mask("999-999-9999"); */

		function changeProgramManager() {
			if ($('#checkDefaultProgramManager').prop('checked') == true) {
				$('#checkDefaultPMValue').val(true);
			} else {
				$('#checkDefaultPMValue').val(false);
				setDiv('sucessDiv', '');
			}
		}
		
		/* Select Services moving form Left to Right and Right to Left functionality Start */
		function SelectMoveRows(left, right, action) {
			var tempProgramManagerIds = [];
			var j=0;
			var SelID = '';
			var SelText = '';
			// Move rows from left to right from bottom to top
			if(action == 'ADD'){
				for (i = left.options.length - 1; i >= 0; i--) {
					if (left.options[i].selected == true) {
						SelID = left.options[i].value;
						SelText = left.options[i].text;
						var newRow = new Option(SelText, SelID);
						right.options[right.length] = newRow;
						left.options[i] = null;
						getCardProgramByPmId(SelID);
						programManagerIdList.push(SelID);
					}
				}				
			}else if(action == 'REMOVE'){
				for (i = left.options.length - 1; i >= 0; i--) {
					if (left.options[i].selected == true) {
						SelID = left.options[i].value;
						SelText = left.options[i].text;
						var newRow = new Option(SelText, SelID);
						right.options[right.length] = newRow;
						left.options[i] = null;
						removeCardProgramFromList(SelID);
						for(var k=0; k < programManagerIdList.length; k++){
							if(programManagerIdList[k] != SelID){
								tempProgramManagerIds[j] = programManagerIdList[k];
								j++;
							}
						}
						programManagerIdList = tempProgramManagerIds;
						j=0;
						tempProgramManagerIds = [];
					}
				}
			}
			SelectSort(right);
		}
		function SelectSort(SelList) {
			var ID = '';
			var Text = '';
			for (x = 0; x < SelList.length - 1; x++) {
				for (y = x + 1; y < SelList.length; y++) {
					if (SelList[x].text > SelList[y].text) {
						// Swap rows
						ID = SelList[x].value;
						Text = SelList[x].text;
						SelList[x].value = SelList[y].value;
						SelList[x].text = SelList[y].text;
						SelList[y].value = ID;
						SelList[y].text = Text;
					}
				}
			}
		}
		/* Select Services moving form Left to Right and Right to Left functionality End */
		function addCardProgram(cardProgramId){
			var tempCardProgramIds = [];
			var j=0;
			var selectedId = 'cpId' + cardProgramId;
			
			if($('#' + selectedId).is(":checked")){
				cardProgramIdList.push(cardProgramId);				
			}else if(!($('#' + selectedId).is(":checked"))){
				for(var i=0; i < cardProgramIdList.length; i++){
					if(cardProgramIdList[i] != cardProgramId){
						tempCardProgramIds[j] = cardProgramIdList[i];
						j++;
					}
				}
				cardProgramIdList = tempCardProgramIds;			
			}
		}
		
		function setSelectedPmAndCpId(){
			//set selected pm ids
			$('#programManagerIds').val(programManagerIdList);
			//set selected card pogram ids
			$('#cardProgramIds').val(cardProgramIdList);
		}
		function doUnCheckedToCardProgram(cardProgramId){
			var tempCardProgramIds = [];
			var j=0;
			for(var i=0; i < cardProgramIdList.length; i++){
				if(cardProgramIdList[i] != cardProgramId){
					tempCardProgramIds[j] = cardProgramIdList[i];
					j++;
				}
			}
			cardProgramIdList = tempCardProgramIds;
		}
		$(document).ready(function() {
			fetchCardProgramByIso($('#isoId').val());
		});
		function closePopup(){
			$('#LogoDiv').popup("hide");
		}
		function openPopup(){
			$('#LogoDiv').popup("show");
		}
		
		$(document).ready(function() {
			/* Table Sorter includes Start*/
			$(function() {
				
					  // call the tablesorter plugin
					  $('#serviceResults').sortable({
						
						 divBeforeTable: '#divbeforeid',
						divAfterTable: '#divafterid',
						initialSort: false,
						locale: 'th',
						//negativeSort: [1, 2]
					});
			});
			});
	</script>
	<script type="text/javascript">
	$(document).ready(function() {


		$('#programManagers').multiselect();
	});
	
	</script>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-36251023-1']);
  _gaq.push(['_setDomainName', 'jqueryscript.net']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</body>
</html>