<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<section class="field-element-row atm-transaction-content" style="display:none;">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-3">
													<legend></legend>
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="configurations.label.processor"/><span class="required-field">*</span></label>
														<!-- <select class="form-control">
															<option>..:Select:..</option>
														</select> -->
														<form:select cssClass="form-control" path="processor" id="processor" onblur="validateProcessor()">
																<form:option value="">..:<spring:message code="sub-merchant-create.label.select" />:..</form:option>
															<c:forEach items="${processorNames}" var="processorName">
																<form:option value="${processorName.value}">${processorName.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="processorEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
													<legend></legend>
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="configurations.label.merchantcallbackURL"/></label>
														<form:input cssClass="form-control"
															path="merchantCallBackURL" id="merchantCallBackURL"
															onblur="validateCallbackURL()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="merchantCallBackURLEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
													<legend></legend>
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="configurations.label.category"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="category"
															id="category" onblur="validateCategory()">
															<form:option value="">.:<spring:message code="sub-merchant-create.label.select"/>:.</form:option>
															<form:option value="primary"><spring:message code="configurations.label.primary"/></form:option>
															<form:option value="secondary"><spring:message code="configurations.label.secondary"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="categoryEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
													<legend></legend>
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="configurations.label.autotransferlimit"/></label>
														<form:input cssClass="form-control" maxlength="10"
															path="autoTransferLimit" id="autoTransferLimit"
															onblur="validateAutoTransferLimit()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoTransferLimitEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
													<legend></legend>
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="configurations.label.autopaymentmethod"/> <span
															class="required-field">*</span></label>
														<form:select cssClass="form-control"
															path="autoPaymentMethod" id="autoPaymentMethod"
															onblur="validateAutoPaymentMethod()">
															<form:option value="">..:<spring:message code="sub-merchant-create.label.select"/>:..</form:option>
															<form:option value="EFT">EFT</form:option>
															<form:option value="Cheque"><spring:message code="configurations.label.cheque"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoPaymentMethodEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
													<legend></legend>
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.autotransferperiod"/></label>
														<form:select cssClass="form-control" onchange="showAutoTransferDayFields()"
															path="autoTransferDay" id="autoTransferDay">
															<form:option value="">..:<spring:message code="sub-merchant-create.label.select"/>:..</form:option>
															<form:option value="D"><spring:message code="configurations.label.daily"/></form:option>
															<form:option value="W"><spring:message code="configurations.label.weekly"/></form:option>
															<form:option value="M"><spring:message code="configurations.label.monthly"/></form:option>

														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoTransferDayEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													
													<fieldset class="col-sm-3" id="weeklySettlement" style="display: none;">
													<legend></legend>
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="configurations.label.selectdayoftheweek"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" onblur="return clientValidation('autoTransferWeeklyDay', 'state','autoTransferWeeklyDayEr');"
															path="autoTransferWeeklyDay" id="autoTransferWeeklyDay">
															<form:option value="">.:<spring:message code="sub-merchant-create.label.select"/>:.</form:option>
															<form:option value="1"><spring:message code="configurations.label.monday"/></form:option>
															<form:option value="2"><spring:message code="configurations.label.tuesday"/></form:option>
															<form:option value="3"><spring:message code="configurations.label.wednesday"/></form:option>
															<form:option value="4"><spring:message code="configurations.label.thursday"/></form:option>
															<form:option value="5"><spring:message code="configurations.label.friday"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoTransferWeeklyDayEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													
													<fieldset class="col-sm-3" id="monthlySettlement" style="display: none;">
													<legend></legend>
													<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="configurations.label.selectdayOfmonth"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" onblur="return clientValidation('autoTransferMonthlyDay', 'state','autoTransferMonthlyDayEr');"
															path="autoTransferMonthlyDay" id="autoTransferMonthlyDay">
															<form:option value="">.:<spring:message code="sub-merchant-create.label.select"/>:.</form:option>
															<form:option value="1">1</form:option>
															<form:option value="2">2</form:option>
															<form:option value="3">3</form:option>
															<form:option value="4">4</form:option>
															<form:option value="5">5</form:option>
															<form:option value="6">6</form:option>
															<form:option value="7">7</form:option>
															<form:option value="8">8</form:option>
															<form:option value="9">9</form:option>
															<form:option value="10">10</form:option>
															<form:option value="11">11</form:option>
															<form:option value="12">12</form:option>
															<form:option value="13">13</form:option>
															<form:option value="14">14</form:option>
															<form:option value="15">15</form:option>
															<form:option value="16">16</form:option>
															<form:option value="17">17</form:option>
															<form:option value="18">18</form:option>
															<form:option value="19">19</form:option>
															<form:option value="20">20</form:option>
															<form:option value="21">21</form:option>
															<form:option value="22">22</form:option>
															<form:option value="23">23</form:option>
															<form:option value="24">24</form:option>
															<form:option value="25">25</form:option>
															<form:option value="26">26</form:option>
															<form:option value="27">27</form:option>
															<form:option value="28">28</form:option>
															<form:option value="29">29</form:option>
															<form:option value="30">30</form:option>
															<form:option value="31">31</form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoTransferMonthlyDayEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													
													<fieldset class="col-sm-3" id="vantivMerchantId" style="display: none;">
													<legend></legend>
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="configurations.label.vantivmerchantid"/> <span class="required-field">*</span></label>
														<form:input cssClass="form-control" maxlength="50"
															path="litleMID" id="litleMID"
															onblur="validatelitleMID()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="litleMIDEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													
													<!-- Add Issuance Agent Configuration START -->
													
											<fieldset class="col-sm-12" id="issuanceAgentSettings">
												<fieldset class="col-sm-12 padding0 border-style-section">
													<fieldset class="col-sm-12">
													<legend></legend>
														<div class="container-heading-separator">
															<span><spring:message code="merchant.label.issuer.agent.configuration"/></span>
														</div>
														<div class="row">
															<div class="field-element-row">
																<fieldset class="col-sm-3">
																<legend></legend>
																	<label data-toggle="tooltip" data-placement="top" title="">
																		<spring:message code="common.label.agentName"/>
																		<span class="required-field">*</span>
																	</label>
																	<form:select cssClass="form-control" path="agentId"
																		id="agentId" onblur="this.value=this.value.trim();return validateAgentName()"
																		onchange="fetchAgentData(this.value)">
																		<form:option value=""><spring:message code="reports.option.select"/></form:option>
																		<c:forEach items="${agentnamesList}" var="agentnames">
																			<form:option value="${agentnames.label}">${agentnames.value}</form:option>
																		</c:forEach>
																	</form:select>
																	<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																		<span id="agentIdErr" class="red-error">&nbsp;</span>
																	</div>
																</fieldset>
																<fieldset class="col-sm-4">
																<legend></legend>
																	<label>
																	<spring:message code="merchant.label.issuer.agent.accnumber"/></label>
																	<form:input cssClass="form-control"
																				path="agentAccountNumber" id="agentAccountNumber" readonly="true"/>
																	<div class="discriptionErrorMsg">
																				<span id="agentAccountNumberErrorDiv"
																					class="red-error">&nbsp;</span>
																	</div>
																	
																</fieldset>
																<fieldset class="col-sm-4">
																<legend></legend>
																	<label>
																	<spring:message code="merchant.label.issuer.agent.clientid"/></label>
																	<form:input cssClass="form-control"
																				path="agentClientId" id="agentClientId" readonly="true"/>
																	<div class="discriptionErrorMsg">
																				<span id="agentClientIdErrorDiv"
																					class="red-error">&nbsp;</span>
																	</div>
																	
																</fieldset>
																<fieldset class="col-sm-4">
																<legend></legend>
																	<label>
																	<spring:message code="merchant.label.issuer.agent.ani"/></label>
																	<form:input cssClass="form-control"
																				path="agentANI" id="agentANI" readonly="true"/>
																	<div class="discriptionErrorMsg">
																				<span id="agentANIErrorDiv"
																					class="red-error">&nbsp;</span>
																	</div>
																	
																</fieldset>
								
								
																
															</div>
														</div>
													</fieldset>
												</fieldset>
											</fieldset>
																					
													<!-- Add Issuance Agent Configuration END-->
													
													<!-- ADDED VIRTUAL, POS and ONLINE TERMINALS START-->
													<fieldset class="col-sm-12" id="">
														<fieldset class="col-sm-12 padding0 border-style-section">
															<fieldset class="col-sm-12">
															<legend></legend>
																<div class="container-heading-separator">
																	<span><spring:message code="configurations.label.supportterminals"/></span>
																</div>
																<div class="row">
																	<div class="field-element-row">

																		<fieldset class="col-sm-12">
																		<legend></legend>

																			<form:checkbox path="virtualTerminal" id="virtualTerminal" value="0"
																				onclick="validateVirtualTerminal()" />
																			<spring:message code="configurations.label.virtualterminaloptions"/><br>
																			<fieldset id="virtualTerminalOptions">
																			<legend></legend>
																				<form:checkbox path="refunds" id="refunds" value="0"
																					onclick="validateCheckBox()" />
																				<spring:message code="configurations.label.refunds"/>
																				<form:checkbox path="tipAmount" id="tipAmount"
																					value="0" onclick="validateCheckBox()" />
																				<spring:message code="configurations.label.tipamount"/>
																				<form:checkbox path="taxAmount" id="taxAmount"
																					value="0" onclick="validateCheckBox()" />
																				<spring:message code="configurations.label.taxamount"/>
																				<form:checkbox path="shippingAmount"
																					id="shippingAmount" value="0"
																					onclick="validateCheckBox()" />
																				<spring:message code="configurations.label.shippingamount"/>
																				<%-- <form:checkbox path="allowRepeatSale"
																					id="allowRepeatSale" value="0"
																					onclick="validateCheckBox()" />
																					<spring:message code="configurations.label.allowrepeatsale"/>
																				<form:checkbox path="showRecurringBilling"
																					id="showRecurringBilling" value="0"
																					onclick="validateCheckBox()" />
																				<spring:message code="configurations.label.showrecurringbilling"/> --%>
																				<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																					<span id="refundsEr" class="red-error">&nbsp;</span>
																				</div>
																			</fieldset>
																		</fieldset>
																		<%-- <fieldset class="col-sm-12">
																			<form:checkbox path="posTerminal" id="posTerminal" value="0" onclick="validatePos()" />
																			<spring:message code="configurations.label.pos"/>
																		</fieldset> --%>
																		<br>
																		<fieldset class="col-sm-12">
																		<legend></legend>
																			<form:checkbox path="online" id="online" value="0" onclick="validateOnlineOptions()" />
																			<spring:message code="configurations.label.online"/>
																			<fieldset id="onlineOptions">
																				<fieldset class="col-sm-3">
																				<legend></legend>
																					<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="configurations.label.websiteaddress"/><span class="required-field">*</span></label>
																					<form:input cssClass="form-control"
																						path="webSiteAddress" id="webSiteAddress" 
																						onblur="validateWebSiteAddressURL()"/>
																					<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																						<span id="webSiteAddressErrorDiv"
																							class="red-error">&nbsp;</span>
																					</div>
																				</fieldset>
																				<fieldset class="col-sm-3">
																				<legend></legend>
																					<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="configurations.label.returnURL"/><span class="required-field">*</span></label>
																					<form:input cssClass="form-control validate"
																						path="returnUrl" id="returnUrl" 
																						onblur="validateReturnURL()"/>
																					<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																						<span id="returnURLErrorDiv"
																							class="red-error">&nbsp;</span>
																					</div>
																				</fieldset>
																				<fieldset class="col-sm-3">
																				<legend></legend>
																					<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="configurations.label.cancelURL"/><span class="required-field">*</span></label>
																					<form:input cssClass="form-control validate"
																						path="cancelUrl" id="cancelUrl" 
																						onblur="validateCancelURL()" />
																					<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																						<span id="cancelURLErrorDiv"
																							class="red-error">&nbsp;</span>
																					</div>
																				</fieldset>
																			</fieldset>
																		</fieldset>&nbsp;
																	</div>
																</div>
															</fieldset>
														</fieldset>
													</fieldset>
													
<!-- ADDED VIRTUAL, POS and ONLINE TERMINALS END-->

													<%-- <fieldset class="col-sm-3" id="agentDetails">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="configurations.label.agentdetails"/></label>
														<form:select cssClass="form-control" path="agentId"
															id="agentId" >
															<form:option value="">..:<spring:message code="sub-merchant-create.label.select"/>:..</form:option>
															<c:forEach items="${agents}" var="agent">
																<form:option value="${agent.value}">${agent.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="agentIdErrorDiv" class="red-error">&nbsp;</span>
														</div>
													</fieldset> --%>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
													<legend></legend>
														<!-- <input type="button"
															class="form-control button pull-right atm-next"
															value="Continue" onclick="validateRadio();">  -->
															<input type="button"
															class="form-control button pull-right marginL10 atm-prev"
															value="<spring:message code="sub-merchant-create.label.previous"></spring:message>">
															<input type="button"
															class="form-control button pull-right marginL10"
															value="<spring:message code="sub-merchant-create.label.cancel"></spring:message>" onclick="cancelCreateMerchant()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>