<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.Calendar"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<section class="field-element-row atm-transaction-content"
												style="display: none;">
												<fieldset class="col-sm-12">
													<fieldset class="col-sm-12">
														<fieldset class="col-sm-4">
															<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.autosettlementoptions"/><span
																class="required-field">*</span></label><br> <input
																type="radio" id="allowAutoSettlement"
																name="autoSettlement" value="1"
																onclick="validateRadio()"><spring:message code="manage.option.radio.sub-merchant.yes"/> <input
																type="radio" id="noAutoSettlement" name="autoSettlement"
																value="0" onclick="validateRadio()"><spring:message code="manage.option.radio.sub-merchant.no"/>
															<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																<span id="noAutoSettlementEr" class="red-error">&nbsp;</span>
															</div>
														</fieldset>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.merchanttype"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control"
															path="merchantCategory" id="merchantCategory"
															onblur="validateMerchantCategory()">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<form:option value="Individual"><spring:message code="merchant.label.individual"/></form:option>
															<form:option value="Group"><spring:message code="merchant.label.group"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="merchantCategoryEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.feeprogram"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="feeProgram"
															id="feeProgram" onblur="validatefeeProgram()">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<c:forEach items="${feeprogramnames}" var="feename">
																<form:option value="${feename.label}">${feename.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="feeProgramEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.processor"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="processor"
															id="processor" onblur="validateProcessor()">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<c:forEach items="${processorNames}" var="processorName">
																<form:option value="${processorName.value}">${processorName.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="processorEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.merchantcallbackURL"/><!-- <span
															class="required-field">*</span> --></label>
														<form:input cssClass="form-control"
															path="merchantCallBackURL" id="merchantCallBackURL"
															maxlength="50"
															onblur="this.value=this.value.trim();validateCallbackURL()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="merchantCallBackURLEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.category"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="category"
															id="category" onblur="validateCategory()" disabled="true">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<form:option value="primary"><spring:message code="merchantaccount.label.primary"/></form:option>
															<%-- <form:option value="secondary"><spring:message code="merchantaccount.label.secondary"/></form:option> --%>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="categoryEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.autotransferlimit"/><!-- <span class="required-field">*</span> --></label>
														<form:input cssClass="form-control" maxlength="10"
															path="autoTransferLimit" id="autoTransferLimit"
															onblur="this.value=this.value.trim();validateAutoTransferLimit()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoTransferLimitEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.autopaymentmethod"/><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control"
															path="autoPaymentMethod" id="autoPaymentMethod"
															onblur="validateAutoPaymentMethod()">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<form:option value="EFT"><spring:message code="merchantaccount.label.EFT"/></form:option>
															<form:option value="Cheque"><spring:message code="merchantaccount.label.cheque"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoPaymentMethodEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.autotransferperiod"/><!-- <span class="required-field">*</span> --></label>
														<form:select cssClass="form-control"
															onchange="showAutoTransferDayFields()"
															path="autoTransferDay" id="autoTransferDay">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<form:option value="D"><spring:message code="merchantaccount.label.daily"/></form:option>
															<form:option value="W"><spring:message code="merchantaccount.label.weekly"/></form:option>
															<form:option value="M"><spring:message code="merchantaccount.label.monthly"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoTransferDayEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3" id="weeklySettlement"
														style="display: none;">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.selectdayoftheweek"/><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control"
															onblur="return clientValidation('autoTransferWeeklyDay', 'state','autoTransferWeeklyDayEr');"
															path="autoTransferWeeklyDay" id="autoTransferWeeklyDay">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<form:option value="2"><spring:message code="merchantaccount.label.monday"/></form:option>
															<form:option value="3"><spring:message code="merchantaccount.label.tuesday"/></form:option>
															<form:option value="4"><spring:message code="merchantaccount.label.wednesday"/></form:option>
															<form:option value="5"><spring:message code="merchantaccount.label.thursday"/></form:option>
															<form:option value="6"><spring:message code="merchantaccount.label.friday"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="autoTransferWeeklyDayEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3" id="monthlySettlement"
														style="display: none;">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.selectdayofmonth"/><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control"
															onblur="return clientValidation('autoTransferMonthlyDay', 'state','autoTransferMonthlyDayEr');"
															path="autoTransferMonthlyDay" id="autoTransferMonthlyDay">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
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
													<fieldset class="col-sm-3" id="vantivMerchantId"
														style="display: none;">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.vantivmerchantId"/> <span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="litleMID"
															id="litleMID" maxlength="50"
															onblur="this.value=this.value.trim();validatelitleMID()" />
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="litleMIDEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>

													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.bank"/><span
															class="required-field">*</span></label>
														<form:select cssClass="form-control" path="bankId"
															id="bankId" onblur="this.value=this.value.trim();validateBank()">
															
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error" id="bankIdEr">&nbsp;</span>
														</div>
													</fieldset>
													<%-- <fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.reseller"/></label>
														<form:select cssClass="form-control" path="resellerId"
															id="resellerId">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<c:forEach items="${resellerList}" var="resellerData">
																<form:option value="${resellerData.value}">${resellerData.label}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error" id="resellerIdEr">&nbsp;</span>
														</div>
													</fieldset> --%>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.merchantcategorycode"/> <span
															class="required-field">*</span></label>
														<form:select cssClass="form-control" path="mcc" id="mcc"
															onblur="validateMcc()">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<c:forEach items="${mccList}" var="mccList">
																<form:option value="${mccList}">${mccList}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span class="red-error" id="mccErr">&nbsp;</span>
														</div>
													</fieldset>
													
											        <fieldset class="col-sm-3">
														<label><spring:message code="merchant.label.localcurrency"/><span
															class="required-field">*</span></label>
														<form:input cssClass="form-control" path="localCurrency" id="localCurrency" onblur="this.value=this.value.trim();validatelocalCurrency()" readonly="true"/>
														<div class="discriptionErrorMsg">
															<span id="localCurrencyEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													
												<!-- Add Issuance Agent Configuration START -->
													
													<fieldset class="col-sm-12" id="issuanceAgentSettings">
														<fieldset class="col-sm-12 padding0 border-style-section">
															<fieldset class="col-sm-12">
																<div class="container-heading-separator">
																	<span><spring:message code="merchant.label.issuer.agent.configuration"/></span>
																</div>
																<div id="agentErrorId" class="red-error">&nbsp;</div>
																<div class="row">
																	<div class="field-element-row">
																		<fieldset class="col-sm-3">
																			<label data-toggle="tooltip" data-placement="top" title="">
																				<spring:message code="common.label.agentName"/>
																				
																			</label>
																			<form:select cssClass="form-control" path="agentId"
																				id="agentId" 
																				onchange="fetchAgentData(this.value)">
																				<form:option value=""><spring:message code="reports.option.select"/></form:option>
																				
																			</form:select>
																			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																				<span id="agentIdErr" class="red-error">&nbsp;</span>
																			</div>
																		</fieldset>
																		<fieldset class="col-sm-4">
																			<label>
																			<spring:message code="merchant.label.issuer.agent.accnumber"/></label>
																			<form:input cssClass="form-control" maxlength="19" onkeypress="return numbersonly(this,event)"
																						path="agentAccountNumber" id="agentAccountNumber" readonly="true"/>
																			<div class="discriptionErrorMsg">
																						<span id="agentAccountNumberEr"
																							class="red-error">&nbsp;</span>
																			</div>
																			
																		</fieldset>
																		<fieldset class="col-sm-4">
																			<label>
																			<spring:message code="merchant.label.issuer.agent.clientid"/></label>
																			<form:input cssClass="form-control" maxlength="20" onkeypress="return numbersonly(this,event)"
																						path="agentClientId" id="agentClientId" readonly="true"/>
																			<div class="discriptionErrorMsg">
																						<span id="agentClientIdEr"
																							class="red-error">&nbsp;</span>
																			</div>
																			
																		</fieldset>
																		<fieldset class="col-sm-4">
																			<label>
																			<spring:message code="merchant.label.issuer.agent.ani"/></label>
																			<form:input cssClass="form-control" maxlength="20" onkeypress="return numbersonly(this,event)"
																						path="agentANI" id="agentANI" readonly="true"/>
																			<div class="discriptionErrorMsg">
																						<span id="agentANIEr"
																							class="red-error">&nbsp;</span>
																			</div>
																			
																		</fieldset>


																		
																	</div>
																</div>
															</fieldset>
														</fieldset>
													</fieldset>
													
					<!-- Add Issuance Agent Configuration END-->

													<!-- SUPPORT TERMINALS ADDED FOR VIRTUAL, POS AND ONLINE TERMINALS START	 -->
													<fieldset class="col-sm-12" id="">
														<fieldset class="col-sm-12 padding0 border-style-section">
															<fieldset class="col-sm-12">
																<div class="container-heading-separator">
																	<span><spring:message code="merchant.label.supportterminals"/></span>
																</div>
																<div class="row">
																	<div class="field-element-row">
																		<fieldset class="col-sm-5">
																			<form:checkbox path="virtualTerminal"
																				id="virtualTerminal" value="0"
																				onclick="validateVirtualTerminal()" />
																			<spring:message code="merchant.label.virtualterminaloptions"/>

																			<fieldset id="virtualTerminalOptions"
																				style="display: none;">

																				<fieldset class="col-sm-12">
																					<form:checkbox path="refunds" id="refunds"
																						value="0" onclick="validateCheckBox()" />
																					<spring:message code="merchant.label.refunds"/>
																				</fieldset>
																				<fieldset class="col-sm-12">
																					<form:checkbox path="tipAmount" id="tipAmount"
																						value="0" onclick="validateCheckBox()" />
																					<spring:message code="merchant.label.tipamount"/>
																				</fieldset>
																				<fieldset class="col-sm-12">
																					<form:checkbox path="taxAmount" id="taxAmount"
																						value="0" onclick="validateCheckBox()" />
																					<spring:message code="merchant.label.taxamount"/>
																				</fieldset>
																				<fieldset class="col-sm-12">
																					<form:checkbox path="shippingAmount"
																						id="shippingAmount" value="0"
																						onclick="validateCheckBox()" />
																					<spring:message code="merchant.label.shippingamount"/>
																				</fieldset>
																				<%-- <fieldset class="col-sm-12">
																					<form:checkbox path="allowRepeatSale"
																						id="allowRepeatSale" value="0"
																						onclick="validateCheckBox()" />
																					<spring:message code="merchant.label.allowrepeatsale"/>
																				</fieldset>
																				<fieldset class="col-sm-12">
																					<form:checkbox path="showRecurringBilling"
																						id="showRecurringBilling" value="0"
																						onclick="validateCheckBox()" />
																					<spring:message code="merchant.label.showrecurringbilling"/>
																				</fieldset> --%>
																				<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																					<span id="refundsEr" class="red-error">&nbsp;</span>
																				</div>
																			</fieldset>
																		</fieldset>

																			<%-- <fieldset class="col-sm-4">
																			<form:checkbox path="posTerminal" id="posTerminal"
																				value="0" onclick="validatePos()" />
																			<spring:message code="merchant.label.pos"/>
																		</fieldset> --%>


																		<fieldset class="col-sm-4">
																			<form:checkbox path="online" id="online" value="0"
																				onclick="validateOnlineOptions()" />
																			<spring:message code="merchant.label.online"/>

																			<fieldset id="onlineOptions" style="display: none;">
																				<fieldset class="col-sm-12">
																					<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.websiteaddress"/><span
																						class="required-field">*</span></label>
																					<form:input cssClass="form-control validate"
																						path="webSiteAddress" id="webSiteAddress"
																						onblur="this.value=this.value.trim();validateWebSiteAddressURL()" />
																					<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																						<span id="webSiteAddrErr" class="red-error">&nbsp;</span>
																					</div>
																				</fieldset>

																				<fieldset class="col-sm-12">
																					<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.returnURL"/><span
																						class="required-field">*</span></label>
																					<form:input cssClass="form-control validate"
																						path="returnUrl" id="returnUrl"
																						onblur="this.value=this.value.trim();validateReturnURL()" />
																					<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																						<span id="returnURLErr" class="red-error">&nbsp;</span>
																					</div>
																				</fieldset>

																				<fieldset class="col-sm-12">
																					<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.cancelURL"/><span
																						class="required-field">*</span></label>
																					<form:input cssClass="form-control validate"
																						path="cancelUrl" id="cancelUrl"
												 										onblur="this.value=this.value.trim();validateCancelURL()" />
																					<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																						<span id="cancelURLErr" class="red-error">&nbsp;</span>
																					</div>
																				</fieldset>
																				<fieldset class="col-sm-12">
																					<form:checkbox path="payPageConfig" id="payPageConfig" value="0"/>
																					<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.paypageconfiguration"/></label>
																					<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																						<span id="payPageConfigErr" class="red-error">&nbsp;</span>
																					</div>
																				</fieldset>
																			</fieldset>
																		</fieldset>
																		&nbsp;
																	</div>
																</div>
															</fieldset>
														</fieldset>
													</fieldset>
													<div class="col-sm-12 padding0 hide-block">
														<fieldset class="col-sm-12">
															<fieldset class="col-sm-3 multi-select-box"
																style="padding: 0 15px;">
																<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.availablecurrency"/></label> <select
																	class="features form-control left-select-box"
																	multiple="true">
																	<c:forEach items="${currencyList}" var="currency">
																		<option value="${currency.label}">${currency.value}</option>
																	</c:forEach>

																</select>
															</fieldset>
															<fieldset class="col-sm-1 multi-select-btn "
																style="margin-top: 55px;">
																<span class="left-right-btn form-control"
																	onClick="SelectMoveRows(document.getElementsByClassName('features')[0],document.getElementsByClassName('features-codes')[0])">
																	&gt; </span> <span class="right-left-btn form-control"
																	onClick="SelectMoveRows(document.getElementsByClassName('features-codes')[0],document.getElementsByClassName('features')[0])">
																	&lt; </span>
															</fieldset>
															<fieldset class="col-sm-5 multi-select-box"
																style="padding: 0 15px;">
																<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.DCCsupportedcurrency"/><span
																	class="required-field">*</span></label> <select
																	id="currencyCodes"
																	class="features-codes form-control right-select-box"
																	multiple="true" style="float: left">
																</select>
																<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
																	<span class="red-error"
																		style="width: 166px; float: left; margin-left: -234px; margin-top: 173px;"
																		id="mccCodeErrorMsg">&nbsp;</span>
																</div>
															</fieldset>
														</fieldset>
													</div>
													<br> <br>

													<!-- SUPPORT TERMINALS ADDED FOR VIRTUAL, POS AND ONLINE TERMINALS START	 -->

													<fieldset class="col-sm-3" style="display: none;">
														<form:input cssClass="form-control"
															path="programManagerId" id="programManagerId" />
													</fieldset>
													<fieldset class="col-sm-3">
														<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="merchant.label.payOutat"/><span class="required-field">*</span></label>
														<form:select cssClass="form-control" path="payOutAt"
															id="payOutAt" onblur="validateCheckPayoutAt()">
															<form:option value=""><spring:message code="reports.option.select"/></form:option>
															<form:option value="Merchant"><spring:message code="merchant.label.merchant"/></form:option>
															<form:option value="Sub-Merchant"><spring:message code="merchant.label.submerchant"/></form:option>
														</form:select>
														<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
															<span id="payOutAtEr" class="red-error">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3" style="display: none;">
														<form:input cssClass="form-control"
															path="programManagerId" id="programManagerId" />
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="button"
															value='<spring:message code="common.label.continue"/>' class="form-control button pull-right atm-next"
															> <input type="button"
															value='<spring:message code="common.label.previous"/>' class="form-control button pull-right marginL10 atm-prev"
															> <input type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.cancel"/>' onclick="cancelCreateMerchant()">
															<input type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.reset"/>' onclick="resetConfigurationsInfo()">
														
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>



<section class="field-element-row pos-transaction-content" style="display:none;">
												<fieldset class="col-sm-12 padding0">
													<fieldset class="col-sm-6">
														<fieldset class="fieldset merchant-content">
															<legend class="legend content-space"><spring:message code="merchant.label.basciinfo"/></legend>
															<table class="confirm-info-table">
																<tr>
																	<td><spring:message code="admin.PartnerName.message"/>:</td>
																	<td><div id="confirmPartnerId"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.companyname"/>:</td>
																	<td><div id="confirmMbusinessName"></div></td>
																</tr>
																<!-- <tr>
																	<td>Merchant Code:</td>
																	<td><div id="confirmMmerchantCode"></div></td>
																</tr> -->
																<tr>
																	<td><spring:message code="merchant.label.firstname"/>:</td>
																	<td><div id="confirmMfirstName"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.lastname"/>:</td>
																	<td><div id="confirmMlastName"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.mobilephone"/>:</td>
																	<td><div id="confirmMphone"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.fax"/>:</td>
																	<td><div id="confirmMfax"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.emailID"/>:</td>
																	<td><div id="confirmMemailId"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.address1"/>:</td>
																	<td><div id="confirmMaddress1"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.address2"/>:</td>
																	<td><div id="confirmMaddress2"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.city"/>:</td>
																	<td><div id="confirmMcity"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.state"/>:</td>
																	<td><div id="confirmMstate"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.country"/>:</td>
																	<td><div id="confirmMcountry"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.zipcode"/>:</td>
																	<td><div id="confirmMpin"></div></td>
																</tr>
																<%-- <tr>
																	<td><spring:message code="common.label.status"/>:</td>
																	<td><div id="confirmMstatus"></div></td>
																</tr> --%>
																<tr>
																	<td><spring:message code="merchant.label.applicationmode"/>:</td>
																	<td><div id="confirmMappMode"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.businessURL"/>:</td>
																	<td><div id="confirmMbusinessURL"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.lookingfor"/>:</td>
																	<td><div id="confirmLookingFor"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.businesstype"/>:</td>
																	<td><div id="confirmBusinessType"></div></td>
																</tr>
															</table>
														</fieldset>
													</fieldset>
													<fieldset class="col-sm-6">
														<fieldset class="fieldset merchant-content">
															<legend class="legend content-space"><spring:message code="merchant.label.bankinfo"/></legend>
															<table class="confirm-info-table">
																<tr>
																	<td><spring:message code="merchant.label.name"/>:</td>
																	<td><div id="confirmbankAccountName"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.bankroutingnumber"/>:</td>
																	<td><div id="confirmbankRoutingNumber"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.bankaccountnumber"/>:</td>
																	<td><div id="confirmbankAccountNumber"></div></td>
																</tr>
																
																<tr>
																	<td><spring:message code="merchant.label.type"/>:</td>
																	<td><div id="confirmbankAccountType"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.address1"/>:</td>
																	<td><div id="confirmbankAddress1"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.address2"/>:</td>
																	<td><div id="confirmbankAddress2"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.city"/>:</td>
																	<td><div id="confirmbankCity"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.country"/>:</td>
																	<td><div id="confirmbankCountry"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.state"/>:</td>
																	<td><div id="confirmbankState"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.zipcode"/>:</td>
																	<td><div id="confirmbankPin"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.nameonaccount"/>:</td>
																	<td><div id="confirmbankNameOnAccount"></div></td>
																</tr>
																	<tr>
																	<td><spring:message code="merchant.label.currency"/>:</td>
																	<td><div id="confirmCurrencyIdValue"></div></td>
																</tr>
															</table>
														</fieldset>
													</fieldset>
													<fieldset class="col-sm-6">
														<fieldset class="fieldset contact-content">
															<legend class="legend content-space"><spring:message code="merchant.label.additonalinfo"/></legend>
															<table class="confirm-info-table">
																<tr>
																	<td><spring:message code="merchant.label.username"/>:</td>
																	<td><div id="confirmMuserName"></div></td>
																</tr>
															</table>
														</fieldset>
													</fieldset>
													<fieldset class="col-sm-6">
														<fieldset class="fieldset merchant-content">
															<legend class="legend content-space"><spring:message code="merchant.label.legalentity"/></legend>
															<table class="confirm-info-table">
																<tr>
																	<td><spring:message code="merchant.label.entityLegalName"/>:</td>
																	<td><div id="confirmlegalName"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.EIN/TaxID"/>:</td>
																	<td><div id="confirmlegalTaxId"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.type"/>:</td>
																	<td><div id="confirmlegalType"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.expectedannualcardsales"/>:</td>
																	<td><div id="confirmlegalAnnualCard"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.address1"/>:</td>
																	<td><div id="confirmlegalAddress1"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.address2"/>:</td>
																	<td><div id="confirmlegalAddress2"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.city"/>:</td>
																	<td><div id="confirmlegalCity"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.country"/>:</td>
																	<td><div id="confirmlegalCountry"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.state"/>:</td>
																	<td><div id="confirmlegalState"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.zipcode"/>:</td>
																	<td><div id="confirmlegalPin"></div></td>
																</tr>
															</table>
														</fieldset>
													</fieldset>
													<fieldset class="col-sm-6">
														<fieldset class="fieldset merchant-content">
															<legend class="legend content-space"><spring:message code="merchant.label.legalentityrep"/></legend>
															<table class="confirm-info-table">
																<tr>
																	<td><spring:message code="merchant.label.SSN"/>:</td>
																	<td><div id="confirmlegalSSN"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.firstname"/>:</td>
																	<td><div id="confirmlegalFirstName"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.lastname"/>:</td>
																	<td><div id="confirmlegalLastName"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.mobilephone"/>:</td>
																	<td><div id="confirmlegalMobilePhone"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.dateofbirth"/>:</td>
																	<td><div id="confirmlegalDOB"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.passportnumber"/>:</td>
																	<td><div id="confirmlegalPassport"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.countryresidence"/>:</td>
																	<td><div id="confirmlegalCountryResidence"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.countrycitizenship"/>:</td>
																	<td><div id="confirmlegalCitizen"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.homephone"/>:</td>
																	<td><div id="confirmlegalHomePhone"></div></td>
																</tr>
															</table>
														</fieldset>
													</fieldset>
													<fieldset class="col-sm-6">
														<fieldset class="fieldset bank-content">
															<legend class="legend content-space"><spring:message code="merchant.label.configuration"/></legend>
															<table class="confirm-info-table">
																<tr>
																	<td><spring:message code="merchant.label.autosettlementoptions"/>:</td>
																	<td><div id="confirmMautoSettlement"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.merchantcallbackURL"/>:</td>
																	<td><div id="confirmMmerchantCallBackURL"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.category"/>:</td>
																	<td><div id="confirmMcategory"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.autotransferlimit"/>:</td>
																	<td><div id="confirmMautoTransferLimit"></div></td>
																</tr>
																<%-- <tr>
																	<td><spring:message code="merchant.label.vantivmerchantId"/>:</td>
																	<td><div id="confirmLitleMID"></div></td>
																</tr> --%>
																<tr>
																	<td><spring:message code="merchant.label.autotransferperiod"/>:</td>
																	<td><div id="confirmMautoTransferDay"></div></td>
																</tr>
																<tr id="hideDayTable" style="display: none;">
																	<td><spring:message code="merchant.label.selecteddayoftheweek"/>:</td>
																	<td><div id="confirmAutoTransferWeeklyDay"></div></td>
																</tr>
																<tr id="hideWeekyTable" style="display: none;">
																	<td><spring:message code="merchant.label.selecteddayofmonth"/>:</td>
																	<td><div id="confirmAutoTransferMonthlyDay"></div></td>
																</tr>

																<tr>
																	<td><spring:message code="merchant.label.autopaymentmethod"/>:</td>
																	<td><div id="confirmMautoPaymentMethod"></div></td>
																</tr>
																<%-- <tr>
																	<td><spring:message code="merchant.label.DCCenable"/>:</td>
																	<td><div id="confirmDccEnable"></div></td>
																</tr> --%>
																<%-- <tr>
																	<td><spring:message code="merchant.label.selectedcurrency"/>:</td>
																	<td><div id="confirmcurrencyCodes"></div></td>
																</tr> --%>
																<tr>
																	<td><spring:message code="merchant.label.merchanttype"/>:</td>
																	<td><div id="confirmMmerchantCategory"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.feeprogram"/>:</td>
																	<td><div id="confirmMfeeProgram"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.processor"/>:</td>
																	<td><div id="confirmMprocessor"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.merchantcategorycode"/>:</td>
																	<td><div id="confirmMcc"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.bank"/>:</td>
																	<td><div id="confirmBankName"></div></td>
																</tr>
																
																<tr>
																	<td><spring:message code="merchant.label.localcurrency"/>:</td>
																	<td><div id="confirmMlocalCurrency"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="common.label.agentName"/>:</td>
																	<td><div id="confirmAgentName"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.issuer.agent.accnumber"/>:</td>
																	<td><div id="confirmAgentAccountNumber"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.issuer.agent.clientid"/>:</td>
																	<td><div id="confirmAgentClientid"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.issuer.agent.ani"/>:</td>
																	<td><div id="confirmAgentANI"></div></td>
																</tr>
																<%-- <tr>
																	<td><spring:message code="merchant.label.reseller"/>:</td>
																	<td><div id="confirmResellerName"></div></td>
																</tr> --%>
																<tr>
																	<td><spring:message code="merchant.label.virtualterminaloptions"/>:</td>
																	<td><div id="confirmMvirtualTerminalList"></div></td>
																</tr>
																
																<%-- <tr>
																<td><spring:message code="merchant.label.pos"/>:</td>
																<td><div id="confirmMposTerminal"></div></td>
																</tr> --%>
																
																<tr>
																<td><spring:message code="merchant.label.online"/>:</td>
																	<td><div id="confirmMwebSiteAddress"></div>
																		<div id="confirmMreturnURL"></div>
																		<div id="confirmMcancelURL"></div>
																	</td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.payOutat"/>:</td>
																	<td><div id="confirmMpayOutAt"></div></td>
																</tr> 
															</table>
														</fieldset>
													</fieldset>
												</fieldset>
												<!--Panel Action Button Start -->
												<div class="col-sm-12 button-content">
													<fieldset class="col-sm-7 pull-right">
														<input type="submit"
															class="form-control button pull-right pos-next"
															value='<spring:message code="common.label.create"/>' onclick="return selectedCurrency();"> <input type="button"
															value='<spring:message code="common.label.previous"/>' class="form-control button pull-right marginL10 pos-prev"
															> <input type="button"
															class="form-control button pull-right marginL10"
															value='<spring:message code="common.label.cancel"/>' onclick="cancelCreateMerchant()">
													</fieldset>
												</div>
												<!--Panel Action Button End -->
											</section>
											<script src="../js/jquery.cookie.js"></script>
											<script src="../js/messages.js"></script>
											<script type="text/javascript" src="../js/browser-close.js"></script>