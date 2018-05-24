<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Chatak Acquirer Merchant</title>
<!-- Bootstrap -->
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
			<!--Header Block Start -->
			<jsp:include page="header.jsp"></jsp:include>

			<!--Header Block End -->
			<!--Navigation Block Start -->
			<nav class="col-sm-12 nav-bar" id="main-navigation">
				<%-- <jsp:include page="main-navigation.jsp"></jsp:include> --%>
				<%@include file="navigation-panel.jsp"%>
			</nav>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="fraud-basic.label.fraud"/></span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text"><spring:message code="fraud-basic.label.basic"/></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container-first active-background">
						<a href="#"><spring:message code="fraud-basic.label.basic"/></a>
					</div>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-xs-12">
									<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
										<span class="red-error" id="error">${error}</span> <span
											class="green-error">${success}</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="create-fraud" commandName="fraudDTO">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<!-- IP Filter Content Start -->
												<fieldset class="col-sm-6 padding0 left-side-filter">
													<fieldset class="col-sm-12">
														<div class="">
															<span><spring:message code="fraud-basic.label.ipfilter"/>:</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-12">
														<label data-toggle="tooltip" data-placement="top" title=""><input type="checkbox"
															class="ip-checkbox marginL0" id="ipCheckbox" /><spring:message code="fraud-basic.label.ipfilter"/>
															<span class="required-field" >*</span></label>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-8">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fraud-basic.label.addip"/><span class="required-field">*</span></label>
														<input type="text" class="form-control add-ip" value=""
															id="addIP" maxlength=15 onblur="this.value=this.value.trim();validateIP()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="addIPEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-4">
														<label data-toggle="tooltip" data-placement="top" title="">&nbsp;</label> <input type="button"
															class="form-control button add-ip-button" value='<spring:message code="fraud-basic.label.addip"/>'>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-8">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fraud-basic.label.deniediplist"/><span class="required-field">*</span></label>
														<form:select path="iPMultiple"
															cssClass="form-control height100 ip-selectbox"
															multiple="true" id="iPMultiple">
															<c:forEach items="${iPMultipleList}" var="ipList">
																<form:option value="${ipList}">${ipList}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="iPMultipleEr" class="red-error">&nbsp;</span>
														</div>
														<form:hidden path="deniedIp" id="deniedIp" />
													</fieldset>
													<fieldset class="col-sm-4">
														<label data-toggle="tooltip" data-placement="top" title="">&nbsp;</label> <input type="button"
															class="form-control button remove-select-ip"
															value='<spring:message code="fraud-basic.label.removeselectedbutton"/>'> <input type="button"
															class="form-control button remove-all-ip"
															value='<spring:message code="fraud-basic.label.removeallbutton"/>'>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!-- IP Filter Content End -->
												<!-- Country Filter Content Start -->
												<fieldset class="col-sm-6 padding0">
													<fieldset class="col-sm-12">
														<div class="">
															<span><spring:message code="fraud-basic.label.countryfilter"/>:</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-12">
														<label data-toggle="tooltip" data-placement="top" title=""><input type="checkbox"
															class="country-checkbox marginL0" id="countryCheckbox" /><spring:message code="fraud-basic.label.countryfilter"/> <span class="required-field">*</span></label>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-8">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fraud-basic.label.addcountry"/><span class="required-field">*</span></label>
														<!--input type="text" class="form-control add-country" value="" /-->
														<form:select cssClass="form-control add-country"
															id="country" multiple="false" onblur=" validateCountry()"
															path="isoCountryList">
															<form:option value=""><spring:message code="fraud-basic.label.select"/></form:option>
															<c:forEach items="${isoCountryList}" var="isoCountry">
																<form:option value="${isoCountry.value}">${isoCountry.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="countryEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-4">
														<label data-toggle="tooltip" data-placement="top" title="">&nbsp;</label> <input type="button"
															class="form-control button add-country-button"
															value='<spring:message code="fraud-basic.label.addcountrybutton"/>'>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-8">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fraud-basic.label.deniedcountryllist"/> <span
															class="required-field">*</span></label>
														<form:select path="countryMultiple"
															cssClass="form-control height100 country-selectbox"
															id="countryMultiple" multiple="true">
															<c:forEach items="${countryMultipleList}"
																var="countryList">
																<c:forEach items="${isoCountryList}" var="isoCountry">
																	<c:if test="${countryList eq isoCountry.value}">
																		<form:option value="${isoCountry.value}">${isoCountry.label}</form:option>
																	</c:if>
																</c:forEach>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="countryMultipleEr" class="red-error">&nbsp;</span>
														</div>
														<form:hidden path="deniedCountry" id="deniedCountry" />
													</fieldset>
													<fieldset class="col-sm-4">
														<label data-toggle="tooltip" data-placement="top" title="">&nbsp;</label> <input type="button"
															class="form-control button remove-select-country"
															value='<spring:message code="fraud-basic.label.removeselectedbutton"/>'> <input type="button"
															class="form-control button remove-all-country"
															value='<spring:message code="fraud-basic.label.removeallbutton"/>'>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!-- Country Filter Content End -->
												<!-- Email Filter Content Start -->
												<div class="col-sm-12">
													<hr />
												</div>
												<fieldset class="col-sm-6 padding0 left-side-filter">
													<fieldset class="col-sm-12">
														<div class="">
															<span><spring:message code="fraud-basic.label.emailfilter"/>:</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-12">
														<label data-toggle="tooltip" data-placement="top" title=""><input type="checkbox"
															class="email-checkbox marginL0" id="emailCheckbox" /><spring:message code="fraud-basic.label.emailfilter"/> <span class="required-field">*</span></label>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-8">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fraud-basic.label.addemail"/><span class="required-field">*</span></label>
														<input type="text" class="form-control add-email" value=""
															id="emailId" onblur="this.value=this.value.trim();validateEmailId()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="emailIdEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-4">
														<label data-toggle="tooltip" data-placement="top" title="">&nbsp;</label> <input type="button"
															class="form-control button add-email-button"
															value='<spring:message code="fraud-basic.label.addemailbutton"/>'>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-8">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fraud-basic.label.deniedemaillist"/><span
															class="required-field">*</span></label>
														<form:select path="eMailMultiple"
															cssClass="form-control height100 email-selectbox"
															multiple="true" id="eMailMultiple">
															<c:forEach items="${eMailMultipleList}" var="eMailList">
																<form:option value="${eMailList}">${eMailList}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="eMailMultipleEr" class="red-error">&nbsp;</span>
														</div>
														<form:hidden path="deniedEMail" id="deniedEMail" />
													</fieldset>
													<fieldset class="col-sm-4">
														<label data-toggle="tooltip" data-placement="top" title="">&nbsp;</label> <input type="button"
															class="form-control button remove-select-email"
															value='<spring:message code="fraud-basic.label.removeselectedbutton"/>'> <input type="button"
															class="form-control button remove-all-email"
															value='<spring:message code="fraud-basic.label.removeallbutton"/>'>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!-- Email Filter Content End -->
												<!-- BIN Filter Content Start -->
												<fieldset class="col-sm-6 padding0">
													<fieldset class="col-sm-12">
														<div class="">
															<span><spring:message code="fraud-basic.label.binfilter"/>:</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-12">
														<label data-toggle="tooltip" data-placement="top" title=""><input type="checkbox"
															class="bin-checkbox marginL0" id="binCheckbox" /><spring:message code="fraud-basic.label.binfilter"/> <span class="required-field">*</span></label>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-8">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fraud-basic.label.addbin"/><span class="required-field">*</span></label>
														<input type="text" class="form-control add-bin" value=""
															id="bin" maxlength=6 onblur="this.value=this.value.trim();validateBin()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="binEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-4">
														<label data-toggle="tooltip" data-placement="top" title="">&nbsp;</label> <input type="button"
															class="form-control button add-bin-button"
															value='<spring:message code="fraud-basic.label.addbinbutton"/>'>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-8">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="fraud-basic.label.deniedbinlist"/><span
															class="required-field">*</span></label>
														<form:select path="binMultiple"
															cssClass="form-control height100 bin-selectbox"
															id="binMultiple" multiple="true">
															<c:forEach items="${binMultipleList}" var="binList">
																<form:option value="${binList}">${binList}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="binMultipleEr" class="red-error">&nbsp;</span>
														</div>
														<form:hidden path="deniedBin" id="deniedBin" />
													</fieldset>
													<fieldset class="col-sm-4">
														<label data-toggle="tooltip" data-placement="top" title="">&nbsp;</label> <input type="button"
															class="form-control button remove-select-bin"
															value='<spring:message code="fraud-basic.label.removeselectedbutton"/>'> <input type="button"
															class="form-control button remove-all-bin"
															value='<spring:message code="fraud-basic.label.removeallbutton"/>'>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<!-- BIN Filter Content End -->
											</div>
										</div>
										<!--Panel Action Button Start -->
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit" class="form-control button pull-right"
													value='<spring:message code="fraud-basic.label.savebutton"/>'onclick="return validateSave();"> <input
													type="button" class="form-control button pull-right"
													value='<spring:message code="fraud-basic.label.cancelbutton"/>' onclick="cancel();">
											</div>
										</div>
										<!--Panel Action Button End -->
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
			<jsp:include page="footer.jsp"></jsp:include>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script> <script src="../js/utils.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/fraud.js"></script>

	<script>
		/* Common Navigation Include Start */
		/* Common Navigation Include End */
		/* IP Filter Functionality Start */
		/* IP Filter Checkbox selection Start */
		$(".add-ip").attr("disabled", "disabled");
		$('.ip-checkbox').click(function() {
			if (this.checked) {
				$(".add-ip").removeAttr("disabled");
			} else {
				$(".add-ip").val("");
				setError(get('addIP'), '');
				$(".add-ip").attr("disabled", "disabled");
			}
		});
		/* IP Filter Checkbox selection End */
		/* Adding IP Filter Values in Select Box Start */
		$(".add-ip-button").click(
				function() {

					var isDisabled = $(".add-ip").is(':disabled');
					if (!isDisabled) {
						if (!validateIP()) {
							return false;
						}
					} else {
						return false;
					}

					$(".ip-selectbox").append(
							'<option value="' + $(".add-ip").val() + '">'
									+ $(".add-ip").val() + '</option>');
					$(".add-ip").val("");
				});
		/* Adding IP Filter Values in Select Box End */
		/* Remove Selected IP Filter From Select Box Functionality Start */
		$(".remove-select-ip").click(function() {
			$(".ip-selectbox option:selected").remove();
		});
		/* Remove Selected IP Filter From Select Box Functionality End */
		/* Remove All IP Filter From Select Box Functionality Start */
		$(".remove-all-ip").click(function() {
			$(".ip-selectbox option").remove();
		});
		/* Remove All IP Filter From Select Box Functionality End */
		/* IP Filter Functionality End */
		/* Country Filter Functionality Start */
		/* Country Filter Checkbox selection Start */
		$(".add-country").attr("disabled", "disabled");
		$('.country-checkbox').click(function() {

			if (this.checked) {
				$(".add-country").removeAttr("disabled");
			} else {
				$(".add-country").val("");
				setError(get('country'), '');
				$(".add-country").attr("disabled", "disabled");
			}
		});
		/* Country Filter Checkbox selection End */
		/* Adding Country Filter Values in Select Box Start */
		$(".add-country-button").click(
				function() {

					var isDisabled = $(".add-country").is(':disabled');
					if (!isDisabled) {
						if (!validateCountry()) {
							return false;
						}
					} else {
						return false;
					}

					$(".country-selectbox").append(
							'<option value="' + $(".add-country").val() + '">'
									+ $(".add-country option:selected").text()
									+ '</option>');
					$(".add-country").val("");
				});
		/* Adding Country Filter Values in Select Box End */
		/* Remove Selected Country Filter From Select Box Functionality Start */
		$(".remove-select-country").click(function() {
			$(".country-selectbox option:selected").remove();
		});
		/* Remove Selected Country Filter From Select Box Functionality End */
		/* Remove All Country Filter From Select Box Functionality Start */
		$(".remove-all-country").click(function() {
			$(".country-selectbox option").remove();
		});
		/* Remove All Country Filter From Select Box Functionality End */
		/* Country Filter Functionality End */
		/* Email Filter Functionality Start */
		/* Email Filter Checkbox selection Start */
		$(".add-email").attr("disabled", "disabled");
		$('.email-checkbox').click(function() {
			if (this.checked) {
				$(".add-email").removeAttr("disabled");
			} else {
				$(".add-email").val("");
				setError(get('emailId'), '');
				$(".add-email").attr("disabled", "disabled");
			}
		});
		/* Email Filter Checkbox selection End */
		/* Adding Email Filter Values in Select Box Start */
		$(".add-email-button").click(
				function() {

					var isDisabled = $(".add-email").is(':disabled');
					if (!isDisabled) {
						if (!validateEmailId()) {
							return false;
						}
					} else {
						return false;
					}

					$(".email-selectbox").append(
							'<option value="' + $(".add-email").val() + '">'
									+ $(".add-email").val() + '</option>');
					$(".add-email").val("");
				});
		/* Adding Email Filter Values in Select Box End */
		/* Remove Selected Email Filter From Select Box Functionality Start */
		$(".remove-select-email").click(function() {
			$(".email-selectbox option:selected").remove();
		});
		/* Remove Selected Email Filter From Select Box Functionality End */
		/* Remove All Email Filter From Select Box Functionality Start */
		$(".remove-all-email").click(function() {
			$(".email-selectbox option").remove();
		});
		/* Remove All Email Filter From Select Box Functionality End */
		/* Email Filter Functionality End */
		/* BIN Filter Functionality Start */
		/* BIN Filter Checkbox selection Start */
		$(".add-bin").attr("disabled", "disabled");
		$('.bin-checkbox').click(function() {
			if (this.checked) {
				$(".add-bin").removeAttr("disabled");
			} else {
				$(".add-bin").val("");
				setError(get('bin'), '');
				$(".add-bin").attr("disabled", "disabled");
			}
		});
		/* BIN Filter Checkbox selection End */
		/* Adding BIN Filter Values in Select Box Start */
		$(".add-bin-button").click(
				function() {

					var isDisabled = $(".add-bin").is(':disabled');
					if (!isDisabled) {
						if (!validateBin()) {
							return false;
						}
					} else {
						return false;
					}

					$(".bin-selectbox").append(
							'<option value="' + $(".add-bin").val() + '">'
									+ $(".add-bin").val() + '</option>');
					$(".add-bin").val("");
				});
		/* Adding BIN Filter Values in Select Box End */
		/* Remove Selected BIN Filter From Select Box Functionality Start */
		$(".remove-select-bin").click(function() {
			$(".bin-selectbox option:selected").remove();
		});
		/* Remove Selected BIN Filter From Select Box Functionality End */
		/* Remove All BIN Filter From Select Box Functionality Start */
		$(".remove-all-bin").click(function() {
			$(".bin-selectbox option").remove();
		});
		/* Remove All BIN Filter From Select Box Functionality End */
		/* BIN Filter Functionality End */
		$(document).ready(function() {
			highlightMainContent('navListId4');

			/*  $("#search-result-table").hide(); */
		});
	</script>
	<script src="../js/backbutton.js"></script>
</body>
</html>