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
								code="common.label.create" /></span>
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
								<form:form action="createIso"
									modelAttribute="isoCreate" method="post"
									onsubmit="buttonDisabled()" enctype="multipart/form-data">
									<input type="hidden" name="cardProgramIds" id="cardProgramIds">
									<input type="hidden" name="programManagerIds" id="programManagerList">
									<input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="admin.iso.Name.message" /><span
														class="required-field">*</span></label>
													<form:input id="isoName"
														path="isoName" maxlength="100"
														cssClass="form-control"
														onblur="this.value=this.value.trim();clientValidation('isoName','program_manager_name','isonameerrormsg')"
														onclick="clearErrorMsg('pgmmgrnameerrormsg');" />
													<div class="discriptionErrorMsg">
														<span id="isonameerrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="admin.BusinessEntityName.message" /><span
														class="required-field">*</span></label>
													<form:input path="programManagerRequest.businessName" maxlength="50"
														cssClass="form-control" id="businessEntityName"
														onblur="this.value=this.value.trim();clientValidation('businessEntityName','business_entity_name','isobusinessentityerrormsg')"
														onclick="clearErrorMsg('isobusinessentityerrormsg');" />
													<div class="discriptionErrorMsg">
														<span id="isobusinessentityerrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="merchant.label.contactperson" /><span
														class="required-field">*</span></label>
													<form:input path="programManagerRequest.contactName" maxlength="50"
														cssClass="form-control" id="contactPerson"
														onblur="this.value=this.value.trim();clientValidation('contactPerson','contact_person','isocontactpersonerrormsg')"
														onclick="clearErrorMsg('isocontactpersonerrormsg');" />
													<div class="discriptionErrorMsg">
														<span id="isocontactpersonerrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="common.label.currency" /><span
														class="required-field">*</span></label>
													<form:select cssClass="form-control" id="currency" onblur="clientValidation('currency','contact_person','isoCurrencyerrormsg')"
														path="programManagerRequest.accountCurrency" onchange="fetchPmListByCurrency(this.value)">
														<form:option value="">
															<spring:message code="iso-create.currency.label.select" />
														</form:option>
														<c:forEach items="${currencyList}" var="currency">
															<form:option value="${currency.label}">${currency.value}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg">
														<span id="isoCurrencyerrormsg" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message
															code="users.label.address" /><span
														class="required-field">*</span></label>
													<form:input cssClass="form-control" path="address"
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
													<form:input cssClass="form-control" path="city" id="city"
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
													<form:select cssClass="form-control" path="country"
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
													<form:select cssClass="form-control" path="state"
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
													<form:input cssClass="form-control" path="zipCode" id="zip"
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
													<form:input path="programManagerRequest.contactPhone" maxlength="10"
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
													<form:input path="programManagerRequest.extension" maxlength="5"
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
													<form:input path="programManagerRequest.contactEmail" id="isoEmailId"
														cssClass="form-control" maxlength="50"
														onblur="clientValidation('isoEmailId', 'email','isoEmailId_ErrorDiv')" />
													<div class="discriptionErrorMsg">
														<span id="isoEmailId_ErrorDiv"
															class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="bank.label.bankname" /></label>
													<form:input path="bankName"
														cssClass="form-control" id="bankName" onblur="clientValidation('bankName','companyname_not_mandatory','banknameerr')"/>
													<div class="discriptionErrorMsg">
														<span id="banknameerr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="merchant.label.bankaccountnumber" /></label>
													<form:input path="bankAccNum" 
														cssClass="form-control" id="bankAccNum" maxlength="<%=Constants.BANK_ACCOUNT_NUMBER.toString()%>"
														onkeypress="return numbersonly(this, event);" onblur="clientValidation('bankAccNum','bank_Code','bankaccerr')" />
													<div class="discriptionErrorMsg">
														<span id="bankaccerr" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="merchant.label.bankroutingnumber" /></label>
													<form:input path="routingNumber"
														cssClass="form-control" id="routingNumber" maxlength="<%=Constants.ACCOUNT_ROUTING_NUMBER.toString()%>"
														onkeypress="return numbersonly(this, event)" onblur="clientValidation('routingNumber','bank_Code','routingNumbererr')"/>
													<div class="discriptionErrorMsg">
														<span id="routingNumbererr" class="red-error">&nbsp;</span>
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
												</fieldset>
													<fieldset class="col-sm-12">
													<fieldset class="col-sm-5 multi-select-box">
													<label><spring:message code="admin.program.manager.list" /></label>
														<select class="pm-list form-control features left-select-box" multiple="multiple" id="programManagers">
														</select>
													</fieldset>
													<fieldset class="col-sm-1 multi-select-btn marginT60">
														<span class="left-right-btn  form-control"
															id="undo_redo_rightSelected"
															onClick="SelectMoveRows(document.getElementsByClassName('features')[0],document.getElementsByClassName('features-codes')[0],'ADD')">
															&gt; </span> <span class="right-left-btn form-control"
															id="undo_redo_leftSelected"
															onClick="SelectMoveRows(document.getElementsByClassName('features-codes')[0],document.getElementsByClassName('features')[0],'REMOVE')">
															&lt; </span>

													</fieldset>
													<label><spring:message code="admin.associated.pm" /><span
														class="required-field">*</span></label>
													<fieldset class="col-sm-5 multi-select-box">
														<select  id="selectedProgramManager"
															class="selected-pm form-control features-codes right-select-box"  MULTIPLE>
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
							<div class="discriptionErrorMsg" data-toggle="tooltip"
								data-placement="top" title="">
								<span id="ambiguityFlag" class="red-error">&nbsp;</span>
							</div>
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
							</table>
							<!-- Search Table Content End -->
											</div>
											<!--Panel Action Button Start -->
											<div class="col-sm-12 form-action-buttons">
												<div class="col-sm-5"></div>
												<div class="col-sm-7">
													<input type="submit" class="form-control button pull-right"
														id="buttonCreate"
														value="<spring:message code="common.label.create"/>"
														onclick="return validateCreateIso()"> <a
														href="showIsoCreate"
														class="form-control button pull-right"><spring:message
															code="common.label.reset" /></a>
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
	<script src="../js/multiselect.js"></script>
	
	<script type="text/javascript">
	$(document).ready(function() {
		$('#programManagers').multiselect();
	});
	
	</script>
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
		var programManagerIdList = [];
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
			//set selected pm ids
			$('#programManagerList').val(programManagerIdList);
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
		var cardProgramIdList = [];
		var selectedCpId = [];
		function addCardProgram(cardProgramId,entityId){
			var tempCardProgramIds = [];
			var j=0;
			var selectedId = 'cpId' + cardProgramId + entityId;
			
			if($('#' + selectedId).is(":checked")){
				cardProgramIdList.push(cardProgramId+'@'+entityId);
				selectedCpId.push(parseInt(cardProgramId));
			}else if(!($('#' + selectedId).is(":checked"))){
				for(var i=0; i < cardProgramIdList.length; i++){
					if(cardProgramIdList[i] != cardProgramId+'@'+entityId){
						tempCardProgramIds[j] = cardProgramIdList[i];
						j++;
					}
				}
				cardProgramIdList = tempCardProgramIds;
				var index = selectedCpId.indexOf(parseInt(cardProgramId));
				if (index > -1) {
					selectedCpId.splice(index, 1);
				}
			}
			//set selected card pogram ids
		$('#cardProgramIds').val(cardProgramIdList);	
		}
		function doUnCheckedToCardProgram(cardProgramId,entityId){
			var tempCardProgramIds = [];
			var j=0;
			for(var i=0; i < cardProgramIdList.length; i++){
				if(cardProgramIdList[i] != cardProgramId+'@'+entityId){
					tempCardProgramIds[j] = cardProgramIdList[i];
					j++;
				}
			}
			cardProgramIdList = tempCardProgramIds;
			var index = selectedCpId.indexOf(parseInt(cardProgramId));
			if (index > -1) {
				selectedCpId.splice(index, 1);
			}
		}
		function resetSelectedPmCp(){
			document.getElementById('programManagers').options.length = 0;
			document.getElementById('selectedProgramManager').options.length = 0;
			$("#serviceResults").find("tr:gt(0)").remove();
			cardProgramIdList = [];
			programManagerIdList = [];
		}
		
		function checkAmbiguity() {
			if (!validateSelectedCardProgram()) {
				return false;
			}
			var sortedCardProgramIdList = selectedCpId.sort();
			for (var i = 0; i < sortedCardProgramIdList.length; i++) {
				for (var j = i + 1; j < sortedCardProgramIdList.length; j++) {
					if (sortedCardProgramIdList[i] == sortedCardProgramIdList[j]) {
						$('#ambiguityFlag').text(
								webMessages.DUPLICATE_CARD_RPOGRAM);
						return false;
					}
				}
			}
			$('#ambiguityFlag').text('');
			return true;

		}
		
		function validateSelectedCardProgram() {
			var selectedCardProgramIdList = selectedCpId;
			if (selectedCardProgramIdList === undefined
					|| selectedCardProgramIdList.length == 0) {
				$('#ambiguityFlag').text(webMessages.SELECT_CARD_PROGRAM);
				return false;
			} else {
				$('#ambiguityFlag').text(' ');
				return true;
			}
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
	</script>
	

</body>
</html>
