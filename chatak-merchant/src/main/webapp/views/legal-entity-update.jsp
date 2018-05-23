<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<%@ page import="com.chatak.pg.util.Constants"%>
<section class="field-element-row legal-details-rep-content" style="display:none;">
												<fieldset class="col-sm-12">													
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="sub-merchant-create.label.entitylegalname"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalName"
															id="legalName" maxlength="101"
															onblur="this.value=this.value.trim();return clientValidation('legalName', 'first_name_SplChar','legalNameErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalNameErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="sub-merchant-create.label.ein/taxid"/>:<span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalTaxId"
															id="legalTaxId" maxlength="50"
															onblur="this.value=this.value.trim();return clientValidation('legalTaxId', 'eIN_taxId','legalTaxIdErrorDiv');"  />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalTaxIdErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="dash-board.label.type"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="legalType"
															id="legalType" onblur="return clientValidation('legalType', 'state','legalTypeErrorDiv');">
															<form:option value="">..:<spring:message code="sub-merchant-create.label.select"/>:..</form:option>
															<form:option value="1"><spring:message code="sub-merchant-create.label.associationestatetrust"/></form:option>
															<form:option value="2"><spring:message code="sub-merchant-create.label.corporation"/></form:option>
															<form:option value="3"><spring:message code="sub-merchant-create.label.governmentagency"/></form:option>
															<form:option value="4" ><spring:message code="sub-merchant-create.label.individual/soleproprietorship"/></form:option>
															<form:option value="5"><spring:message code="sub-merchant-create.label.internationalorginization"/></form:option>
															<form:option value="6"><spring:message code="sub-merchant-create.label.limitedliabilitycompany"/></form:option>
															<form:option value="7"><spring:message code="sub-merchant-create.label.partnership"/></form:option>
															<form:option value="8"><spring:message code="sub-merchant-create.label.taxexemptorganization"/></form:option>
															<form:option value="11"><spring:message code="sub-merchant-create.label.testing"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalTypeErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
									<%-- 				<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="sub-merchant-create.label.expectedannualcardsales"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalAnnualCard"
															id="legalAnnualCard" maxlength="18" onblur="this.value=this.value.trim();return clientValidation('legalAnnualCard', 'dollar_amount','legalAnnualCardErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalAnnualCardErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset> --%>
													
													
													
													
														<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="sub-merchant-create.label.expectedannualcardsales"/><span
															class="required-field">*</span></label>
															<fmt:formatNumber type="currency" value="${merchant.legalAnnualCard}" pattern="<%=Constants.AMOUNT_FORMAT%>" var="legalAnnualCard" />
														<input name="legalAnnualCard" id="legalAnnualCard" value="${ legalAnnualCard}" maxlength="12" onblur="this.value=this.value.trim();appendDollarSymbol();return clientValidation('legalAnnualCard', 'dollar_amount','legalAnnualCardErrorDiv');"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalAnnualCardErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="sub-merchant-create.label.address1"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalAddress1"
															id="legalAddress1" onblur="this.value=this.value.trim();return clientValidation('legalAddress1', 'bank_address1','legalAddress1ErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalAddress1ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="sub-merchant-create.label.address2"/></label>
														<form:input cssClass="form-control" path="legalAddress2"
															id="legalAddress2" onblur="this.value=this.value.trim();return clientValidation('legalAddress2', 'bank_address2','legalAddress2ErrorDiv');"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalAddress2ErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="search-sub-merchant.label.city"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalCity" id="legalCity"
															onblur="this.value=this.value.trim();return clientValidation('legalCity', 'bank_city','legalCityErrorDiv');" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalCityErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="search-sub-merchant.label.country"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="legalCountry"
															id="legalCountry" onblur="return clientValidation('legalCountry', 'country','legalCountryErrorDiv');"
															onchange="fetchMerchantState(this.value, 'legalState')">
															<form:option value="">..:<spring:message code="sub-merchant-create.label.select"/>:..</form:option>
															<c:forEach items="${countryList}" var="country">
																<form:option value="${country.label}">${country.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalCountryErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="sub-merchant-create.label.state"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="legalState"
															id="legalState" onblur="return clientValidation('legalState', 'state','legalStateErrorDiv');">
															<form:option value="">..:<spring:message code="sub-merchant-create.label.select"/>:..</form:option>
															<c:forEach items="${stateList}" var="item">
																<form:option value="${item.label}">${item.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalStateErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="sub-merchant-create.label.zipcode"/><span class="required-field">*</span></label>
														<form:input cssClass="form-control" path="legalPin" 
															onkeypress ="generalZipCode()"
															id="legalPin"  maxlength="7"
															onblur="this.value=this.value.trim();return zipCodeNotEmpty(id)"/>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="legalPinEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
												</fieldset>
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															class="form-control button pull-right legal-rep-next" onclick="return zipCodeNotEmpty('legalPin')"
															value="<spring:message code="sub-merchant-create.label.continue"></spring:message>"> <input type="button"
															class="form-control button pull-right marginL10 legal-rep-prev"
															value="<spring:message code="sub-merchant-create.label.previous"></spring:message>">  <input
															type="button"
															class="form-control button pull-right marginL10"
															value="<spring:message code="sub-merchant-create.label.cancel"></spring:message>" onclick="cancelCreateMerchant()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>