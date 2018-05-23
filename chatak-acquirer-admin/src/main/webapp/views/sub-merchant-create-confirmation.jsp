<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<section class="field-element-row atm-transaction-content"
	style="display: none;">
	<fieldset class="col-sm-12">
		<fieldset class="col-sm-12">
			<label data-toggle="tooltip" data-placement="top" title=""><spring:message
					code="manage.label.sub-merchant.autosettlementoptions" /><span
				class="required-field">*</span></label><br> <input type="radio"
				id="allowAutoSettlement" name="autoSettlement" value="1"
				onclick="validateRadio()">
			<spring:message code="manage.option.radio.sub-merchant.yes" />
			<input type="radio" id="noAutoSettlement" name="autoSettlement"
				value="0" onclick="validateRadio()">
			<spring:message code="manage.option.radio.sub-merchant.no" />
			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
				<span id="noAutoSettlementEr" class="red-error">&nbsp;</span>
			</div>
		</fieldset>
		<fieldset class="col-sm-3">
			<label data-toggle="tooltip" data-placement="top" title=""><spring:message
					code="manage.label.sub-merchant.feeprogram" /><span
				class="required-field">*</span></label>
			<form:select cssClass="form-control" path="feeProgram"
				id="feeProgram" onblur="validatefeeProgram()">
				<form:option value="">..:<spring:message
						code="manage.option.sub-merchant.select" />:..</form:option>
				<c:forEach items="${feeprogramnames}" var="feename">
					<form:option value="${feename.label}">${feename.label}</form:option>
				</c:forEach>
			</form:select>
			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
				<span id="feeProgramEr" class="red-error">&nbsp;</span>
			</div>
		</fieldset>
		<fieldset class="col-sm-3">
			<label data-toggle="tooltip" data-placement="top" title=""><spring:message
					code="manage.label.sub-merchant.processor" /><span
				class="required-field">*</span></label>
			<form:select cssClass="form-control" path="processor" id="processor"
				onblur="validateProcessor()">
				<form:option value="">..:<spring:message
						code="manage.option.sub-merchant.select" />:..</form:option>
				<c:forEach items="${processorNames}" var="processorName">
					<form:option value="${processorName.value}">${processorName.label}</form:option>
				</c:forEach>
			</form:select>
			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
				<span id="processorEr" class="red-error">&nbsp;</span>
			</div>
		</fieldset>
		<fieldset class="col-sm-3">
			<label data-toggle="tooltip" data-placement="top" title=""><spring:message
					code="manage.label.sub-merchant.merchantcallbackURL" /> <!-- <span
															class="required-field">*</span> --></label>
			<form:input cssClass="form-control" path="merchantCallBackURL"
				id="merchantCallBackURL" maxlength="50"
				onblur="this.value=this.value.trim();validateCallbackURL()" />
			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
				<span id="merchantCallBackURLEr" class="red-error">&nbsp;</span>
			</div>
		</fieldset>
		<fieldset class="col-sm-3">
			<label data-toggle="tooltip" data-placement="top" title=""><spring:message
					code="manage.label.sub-merchant.category" /><span
				class="required-field">*</span></label>
			<form:select cssClass="form-control" path="category" id="category"
				onblur="validateCategory()" disabled="true">
				<form:option value="">.:<spring:message
						code="manage.option.sub-merchant.select" />:.</form:option>
				<form:option value="primary">
					<spring:message code="manage.option.sub-merchant.primary" />
				</form:option>
				<form:option value="secondary">
					<spring:message code="manage.option.sub-merchant.secondary" />
				</form:option>
			</form:select>
			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
				<span id="categoryEr" class="red-error">&nbsp;</span>
			</div>
		</fieldset>
		<fieldset class="col-sm-3">
			<label data-toggle="tooltip" data-placement="top" title=""><spring:message
					code="manage.label.sub-merchant.autotransferlimit" /> <!-- <span class="required-field">*</span> --></label>
			<form:input cssClass="form-control" maxlength="10"
				path="autoTransferLimit" id="autoTransferLimit"
				onblur="this.value=this.value.trim();validateAutoTransferLimit()" />
			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
				<span id="autoTransferLimitEr" class="red-error">&nbsp;</span>
			</div>
		</fieldset>

		<fieldset class="col-sm-3">
			<label data-toggle="tooltip" data-placement="top" title=""><spring:message
					code="manage.label.sub-merchant.autopaymentmethod" /> <span
				class="required-field">*</span></label>
			<form:select cssClass="form-control" path="autoPaymentMethod"
				id="autoPaymentMethod" onblur="validateAutoPaymentMethod()">
				<form:option value="">..:<spring:message
						code="manage.option.sub-merchant.select" />:..</form:option>
				<form:option value="EFT">
					<spring:message code="manage.option.sub-merchant.eft" />
				</form:option>
				<form:option value="Cheque">
					<spring:message code="manage.option.sub-merchant.cheque" />
				</form:option>
			</form:select>
			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
				<span id="autoPaymentMethodEr" class="red-error">&nbsp;</span>
			</div>
		</fieldset>
		<fieldset class="col-sm-3">
			<label data-toggle="tooltip" data-placement="top" title=""><spring:message
					code="merchant.label.autotransferperiod" /></label>
			<form:select cssClass="form-control"
				onchange="showAutoTransferDayFields()" path="autoTransferDay"
				id="autoTransferDay">
				<form:option value="">..:<spring:message
						code="manage.option.sub-merchant.select" />:..</form:option>
				<form:option value="D">
					<spring:message code="manage.option.sub-merchant.daily" />
				</form:option>
				<form:option value="W">
					<spring:message code="manage.option.sub-merchant.weekly" />
				</form:option>
				<form:option value="M">
					<spring:message code="manage.option.sub-merchant.monthly" />
				</form:option>
			</form:select>
			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
				<span id="autoTransferDayEr" class="red-error">&nbsp;</span>
			</div>
		</fieldset>
		<fieldset class="col-sm-3" id="weeklySettlement"
			style="display: none;">
			<label data-toggle="tooltip" data-placement="top" title=""><spring:message
					code="manage.label.sub-merchant.selectdayOftheweek" /><span
				class="required-field">*</span></label>
			<form:select cssClass="form-control"
				onblur="return clientValidation('autoTransferWeeklyDay', 'state','autoTransferWeeklyDayEr');"
				path="autoTransferWeeklyDay" id="autoTransferWeeklyDay">
				<form:option value="">.:<spring:message
						code="manage.option.sub-merchant.select" />:.</form:option>
				<form:option value="2">
					<spring:message code="manage.option.sub-merchant.monday" />
				</form:option>
				<form:option value="3">
					<spring:message code="manage.option.sub-merchant.tuesday" />
				</form:option>
				<form:option value="4">
					<spring:message code="manage.option.sub-merchant.wednesday" />
				</form:option>
				<form:option value="5">
					<spring:message code="manage.option.sub-merchant.thursday" />
				</form:option>
				<form:option value="6">
					<spring:message code="manage.option.sub-merchant.friday" />
				</form:option>
			</form:select>
			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
				<span id="autoTransferWeeklyDayEr" class="red-error">&nbsp;</span>
			</div>
		</fieldset>
		<fieldset class="col-sm-3" id="monthlySettlement"
			style="display: none;">
			<label data-toggle="tooltip" data-placement="top" title=""><spring:message
					code="manage.label.sub-merchant.selectdayOfmonth" /><span
				class="required-field">*</span></label>
			<form:select cssClass="form-control"
				onblur="return clientValidation('autoTransferMonthlyDay', 'state','autoTransferMonthlyDayEr');"
				path="autoTransferMonthlyDay" id="autoTransferMonthlyDay">
				<form:option value="">.:<spring:message
						code="manage.option.sub-merchant.select" />:.</form:option>
				<form:option value="1">
					<spring:message code="manage.option.sub-merchant.1" />
				</form:option>
				<form:option value="2">
					<spring:message code="manage.option.sub-merchant.2" />
				</form:option>
				<form:option value="3">
					<spring:message code="manage.option.sub-merchant.3" />
				</form:option>
				<form:option value="4">
					<spring:message code="manage.option.sub-merchant.4" />
				</form:option>
				<form:option value="5">
					<spring:message code="manage.option.sub-merchant.5" />
				</form:option>
				<form:option value="6">
					<spring:message code="manage.option.sub-merchant.6" />
				</form:option>
				<form:option value="7">
					<spring:message code="manage.option.sub-merchant.7" />
				</form:option>
				<form:option value="8">
					<spring:message code="manage.option.sub-merchant.8" />
				</form:option>
				<form:option value="9">
					<spring:message code="manage.option.sub-merchant.9" />
				</form:option>
				<form:option value="10">
					<spring:message code="manage.option.sub-merchant.10" />
				</form:option>
				<form:option value="11">
					<spring:message code="manage.option.sub-merchant.11" />
				</form:option>
				<form:option value="12">
					<spring:message code="manage.option.sub-merchant.12" />
				</form:option>
				<form:option value="13">
					<spring:message code="manage.option.sub-merchant.13" />
				</form:option>
				<form:option value="14">
					<spring:message code="manage.option.sub-merchant.14" />
				</form:option>
				<form:option value="15">
					<spring:message code="manage.option.sub-merchant.15" />
				</form:option>
				<form:option value="16">
					<spring:message code="manage.option.sub-merchant.16" />
				</form:option>
				<form:option value="17">
					<spring:message code="manage.option.sub-merchant.17" />
				</form:option>
				<form:option value="18">
					<spring:message code="manage.option.sub-merchant.18" />
				</form:option>
				<form:option value="19">
					<spring:message code="manage.option.sub-merchant.19" />
				</form:option>
				<form:option value="20">
					<spring:message code="manage.option.sub-merchant.20" />
				</form:option>
				<form:option value="21">
					<spring:message code="manage.option.sub-merchant.21" />
				</form:option>
				<form:option value="22">
					<spring:message code="manage.option.sub-merchant.22" />
				</form:option>
				<form:option value="23">
					<spring:message code="manage.option.sub-merchant.23" />
				</form:option>
				<form:option value="24">
					<spring:message code="manage.option.sub-merchant.24" />
				</form:option>
				<form:option value="25">
					<spring:message code="manage.option.sub-merchant.25" />
				</form:option>
				<form:option value="26">
					<spring:message code="manage.option.sub-merchant.26" />
				</form:option>
				<form:option value="27">
					<spring:message code="manage.option.sub-merchant.27" />
				</form:option>
				<form:option value="28">
					<spring:message code="manage.option.sub-merchant.28" />
				</form:option>
				<form:option value="29">
					<spring:message code="manage.option.sub-merchant.29" />
				</form:option>
				<form:option value="30">
					<spring:message code="manage.option.sub-merchant.30" />
				</form:option>
				<form:option value="31">
					<spring:message code="manage.option.sub-merchant.31" />
				</form:option>
			</form:select>
			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
				<span id="autoTransferMonthlyDayEr" class="red-error">&nbsp;</span>
			</div>
		</fieldset>
		<fieldset class="col-sm-3" id="vantivMerchantId"
			style="display: none;">
			<label data-toggle="tooltip" data-placement="top" title=""><spring:message
					code="manage.label.sub-merchant.vantivmerchantId" /> <span
				class="required-field">*</span></label>
			<form:input cssClass="form-control" path="litleMID" id="litleMID"
				maxlength="50"
				onblur="this.value=this.value.trim();validatelitleMID()" />
			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
				<span id="litleMIDEr" class="red-error">&nbsp;</span>
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
										<c:forEach items="${agentnamesList}" var="agentnames">
											<form:option value="${agentnames.label}">${agentnames.value}</form:option>
										</c:forEach>
									</form:select>
									<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
										<span id="agentIdErr" class="red-error">&nbsp;</span>
									</div>
								</fieldset>
								<fieldset class="col-sm-4">
									<label>
									<spring:message code="merchant.label.issuer.agent.accnumber"/></label>
									<form:input cssClass="form-control" maxlength="19" onkeypress="return numbersonly(this,event)"
												path="agentAccountNumber" id="agentAccountNumber"  readonly="true"/>
									<div class="discriptionErrorMsg">
												<span id="agentAccountNumberEr"
													class="red-error">&nbsp;</span>
									</div>
									
								</fieldset>
								<fieldset class="col-sm-4">
									<label>
									<spring:message code="merchant.label.issuer.agent.clientid"/></label>
									<form:input cssClass="form-control" maxlength="20" onkeypress="return numbersonly(this,event)"
												path="agentClientId" id="agentClientId"  readonly="true"/>
									<div class="discriptionErrorMsg">
												<span id="agentClientIdEr"
													class="red-error">&nbsp;</span>
									</div>
									
								</fieldset>
								<fieldset class="col-sm-4">
									<label>
									<spring:message code="merchant.label.issuer.agent.ani"/></label>
									<form:input cssClass="form-control" maxlength="20" onkeypress="return numbersonly(this,event)"
												path="agentANI" id="agentANI"  readonly="true"/>
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
						<span><spring:message
								code="manage.label.sub-merchant.supportterminals" /></span>
					</div>
					<div class="row">
						<div class="field-element-row">
							<fieldset class="col-sm-12">
								<form:checkbox path="virtualTerminal" id="virtualTerminal"
									value="0" onclick="validateVirtualTerminal()" />
								<spring:message
									code="manage.label.sub-merchant.virtualterminaloptions" />
								<br>

								<fieldset id="virtualTerminalOptions" style="display: none;">

									<form:checkbox path="refunds" id="refunds" value="0"
										onclick="validateCheckBox()" />
									<spring:message code="manage.label.sub-merchant.refunds" />
									<form:checkbox path="tipAmount" id="tipAmount" value="0"
										onclick="validateCheckBox()" />
									<spring:message code="manage.label.sub-merchant.tipamount" />
									<form:checkbox path="taxAmount" id="taxAmount" value="0"
										onclick="validateCheckBox()" />
									<spring:message code="manage.label.sub-merchant.taxamount" />
									<form:checkbox path="shippingAmount" id="shippingAmount"
										value="0" onclick="validateCheckBox()" />
									<spring:message code="manage.label.sub-merchant.shippingamount" />
									<%-- <form:checkbox path="allowRepeatSale" id="allowRepeatSale"
										value="0" onclick="validateCheckBox()" />
									<spring:message
										code="manage.label.sub-merchant.allowrepeatsale" />
									<form:checkbox path="showRecurringBilling"
										id="showRecurringBilling" value="0"
										onclick="validateCheckBox()" />
									<spring:message
										code="manage.label.sub-merchant.showrecurringbilling" /> --%>
									<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
										<span id="refundsEr" class="red-error">&nbsp;</span>
									</div>
								</fieldset>
							</fieldset>

							<%-- <fieldset class="col-sm-12">
								<form:checkbox path="posTerminal" id="posTerminal" value="0"
									onclick="validatePos()" />
								<spring:message code="manage.label.sub-merchant.pos" />
							</fieldset> --%>
							<br>

							<fieldset class="col-sm-12">
								<form:checkbox path="online" id="online" value="0"
									onclick="validateOnlineOptions()" />
								<spring:message code="manage.label.sub-merchant.online" />

								<fieldset id="onlineOptions" style="display: none;">
									<fieldset class="col-sm-3">
										<label data-toggle="tooltip" data-placement="top" title=""><spring:message
												code="manage.label.sub-merchant.websiteaddress" /><span
											class="required-field">*</span></label>
										<form:input cssClass="form-control validate"
											path="webSiteAddress" id="webSiteAddress"
											onblur="this.value=this.value.trim();validateWebSiteAddressURL()" />
										<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
											<span id="webSiteAddrErr" class="red-error">&nbsp;</span>
										</div>
									</fieldset>

									<fieldset class="col-sm-3">
										<label data-toggle="tooltip" data-placement="top" title=""><spring:message
												code="manage.label.sub-merchant.returnURL" /><span
											class="required-field">*</span></label>
										<form:input cssClass="form-control validate" path="returnUrl"
											id="returnUrl"
											onblur="this.value=this.value.trim();validateReturnURL()" />
										<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
											<span id="returnURLErr" class="red-error">&nbsp;</span>
										</div>
									</fieldset>

									<fieldset class="col-sm-3">
										<label data-toggle="tooltip" data-placement="top" title=""><spring:message
												code="manage.label.sub-merchant.cancelURL" /><span
											class="required-field">*</span></label>
										<form:input cssClass="form-control validate" path="cancelUrl"
											id="cancelUrl"
											onblur="this.value=this.value.trim();validateCancelURL()" />
										<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
											<span id="cancelURLErr" class="red-error">&nbsp;</span>
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
		<br> <br>

		<!-- SUPPORT TERMINALS ADDED FOR VIRTUAL, POS AND ONLINE TERMINALS START	 -->

		<%-- <fieldset class="col-sm-4">
			<label data-toggle="tooltip" data-placement="top" title=""><spring:message
					code="manage.label.sub-merchant.agentdetails" /></label>
			<form:select cssClass="form-control" path="agentId" id="agentId">
				<form:option value="">..:<spring:message
						code="manage.option.sub-merchant.select" />:..</form:option>
				<c:forEach items="${agents}" var="agent">
																<form:option value="${agent.value}">${agent.label}</form:option>
															</c:forEach>
			</form:select>
			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
				<span id="agentIdErrorDiv" class="red-error">&nbsp;</span>
			</div>
		</fieldset> --%>
		<fieldset class="col-sm-3" style="display: none;">
			<form:input cssClass="form-control" path="issuancePartnerId"
				id="issuancePartnerId" />
			<form:input cssClass="form-control" path="programManagerId"
				id="programManagerId" />
		</fieldset>
	</fieldset>
	<!--Panel Action Button Start -->
	<div class="col-sm-12 button-content">
		<fieldset class="col-sm-7 pull-right">
			<input type="button" class="form-control button pull-right atm-next"
				value="<spring:message code="manage.buttton.sub-merchant.continue" />"
				onclick="return validateCreateSubMerchantStep5()"> <input
				type="button"
				class="form-control button pull-right marginL10 atm-prev"
				value="<spring:message code="manage.buttton.sub-merchant.previous" />">
			<input type="button"
				class="form-control button pull-right marginL10"
				value="<spring:message code="manage.buttton.sub-merchant.cancel" />"
				onclick="cancelCreateSubMerchant()"> <input type="button" class="form-control button pull-right marginL10"
				value="<spring:message code="manage.buttton.sub-merchant.reset" />"
				onclick="resetConfigurationsInfo()">
		</fieldset>
	</div>
	<!--Panel Action Button End -->
</section>
<section class="field-element-row pos-transaction-content"
	style="display: none;">
	<fieldset class="col-sm-12 padding0">
		<fieldset class="col-sm-6">
			<fieldset class="fieldset merchant-content">
				<legend class="legend content-space">
					<spring:message code="manage.label.sub-merchant.basicinfo" />
				</legend>
				<table class="confirm-info-table">
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.companyname" />:</td>
						<td><div id="confirmMbusinessName"></div></td>
					</tr>
					<!-- <tr>
																	<td>Merchant Code:</td>
																	<td><div id="confirmMmerchantCode"></div></td>
																</tr> -->
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.firstname" />:</td>
						<td><div id="confirmMfirstName"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.lastname" />:</td>
						<td><div id="confirmMlastName"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.phone" />:</td>
						<td><div id="confirmMphone"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.fax" />:</td>
						<td><div id="confirmMfax"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.E-mailID" />:</td>
						<td><div id="confirmMemailId"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.address1" />:</td>
						<td><div id="confirmMaddress1"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.address2" />:</td>
						<td><div id="confirmMaddress2"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.city" />:</td>
						<td><div id="confirmMcity"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.state" />:</td>
						<td><div id="confirmMstate"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.country" />:</td>
						<td><div id="confirmMcountry"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.zipcode" />:</td>
						<td><div id="confirmMpin"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.status" />:</td>
						<td><div id="confirmMstatus"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.applicationmode" />:</td>
						<td><div id="confirmMappMode"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.businessurl" />:</td>
						<td><div id="confirmMbusinessURL"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.lookingfor" /></td>
						<td><div id="confirmLookingFor"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.businesstype" />:</td>
						<td><div id="confirmBusinessType"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.merchantcode" />:</td>
						<td><div id="confirmMerchantCode"></div></td>
					</tr>
				</table>
			</fieldset>
		</fieldset>

		<fieldset class="col-sm-6">
			<fieldset class="fieldset merchant-content">
				<legend class="legend content-space">
					<spring:message code="manage.label.sub-merchant.bankinfo" />
				</legend>
				<table class="confirm-info-table">
					<tr>
						<td><spring:message code="manage.label.sub-merchant.name" />:</td>
						<td><div id="confirmbankAccountName"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.bankroutingnumber" />:</td>
						<td><div id="confirmbankRoutingNumber"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.bankaccountnumber" />:</td>
						<td><div id="confirmbankAccountNumber"></div></td>
					</tr>

					<tr>
						<td><spring:message code="manage.label.sub-merchant.type" />:</td>
						<td><div id="confirmbankAccountType"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.address1" />:</td>
						<td><div id="confirmbankAddress1"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.address2" />:</td>
						<td><div id="confirmbankAddress2"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.city" />:</td>
						<td><div id="confirmbankCity"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.country" />:</td>
						<td><div id="confirmbankCountry"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.state" />:</td>
						<td><div id="confirmbankState"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.zipcode" />:</td>
						<td><div id="confirmbankPin"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.nameonaccount" />:</td>
						<td><div id="confirmbankNameOnAccount"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="merchant.label.currency" />:</td>
						<td><div id="confirmMcurrencyId"></div></td>
					</tr>
				</table>
			</fieldset>
		</fieldset>
		<fieldset class="col-sm-6">
			<fieldset class="fieldset contact-content">
				<legend class="legend content-space">
					<spring:message code="manage.label.sub-merchant.additionalinfo" />
				</legend>
				<table class="confirm-info-table">
					<tr>
						<td><spring:message code="manage.label.sub-merchant.username" />:</td>
						<td><div id="confirmMuserName"></div></td>
					</tr>
				</table>
			</fieldset>
		</fieldset>
		<fieldset class="col-sm-6">
			<fieldset class="fieldset merchant-content">
				<legend class="legend content-space">
					<spring:message code="manage.label.sub-merchant.legalentity" />
				</legend>
				<table class="confirm-info-table">
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.entitylegalname" />:</td>
						<td><div id="confirmlegalName"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.EINorTaxID" />:</td>
						<td><div id="confirmlegalTaxId"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.type" />:</td>
						<td><div id="confirmlegalType"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.expectedsnnualcardsales" />:</td>
						<td><div id="confirmlegalAnnualCard"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.firstname" />:</td>
						<td><div id="confirmlegalFirstName"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.lastname" />:</td>
						<td><div id="confirmlegalLastName"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.mobilephone" />:</td>
						<td><div id="confirmlegalMobilePhone"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.address1" />:</td>
						<td><div id="confirmlegalAddress1"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.address2" />:</td>
						<td><div id="confirmlegalAddress2"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.city" />:</td>
						<td><div id="confirmlegalCity"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.country" />:</td>
						<td><div id="confirmlegalCountry"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.state" />:</td>
						<td><div id="confirmlegalState"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.zipcode" />:</td>
						<td><div id="confirmlegalPin"></div></td>
					</tr>
				</table>
			</fieldset>
		</fieldset>
		<fieldset class="col-sm-6">
			<fieldset class="fieldset merchant-content">
				<legend class="legend content-space">
					<spring:message
						code="manage.label.sub-merchant.legalentityrepresentative" />
				</legend>
				<table class="confirm-info-table">
					<tr>
						<td><spring:message code="manage.label.sub-merchant.ssn" />:</td>
						<td><div id="confirmlegalSSN"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.dateofbirth" />:</td>
						<td><div id="confirmlegalDOB"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.passportnumber" />:</td>
						<td><div id="confirmlegalPassport"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.countryofresidence" />:</td>
						<td><div id="confirmlegalCountryResidence"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.countryofcitizenship" />:</td>
						<td><div id="confirmlegalCitizen"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.homephone" />:</td>
						<td><div id="confirmlegalHomePhone"></div></td>
					</tr>
				</table>
			</fieldset>
		</fieldset>
		<fieldset class="col-sm-6">
			<fieldset class="fieldset bank-content">
				<legend class="legend content-space">
					<spring:message code="manage.label.sub-merchant.configurations" />
				</legend>
				<table class="confirm-info-table">
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.autosettlementoptions" />:</td>
						<td><div id="confirmMautoSettlement"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.merchantcallbackURL" />:</td>
						<td><div id="confirmMmerchantCallBackURL"></div></td>
					</tr>
					<tr>
						<td><spring:message code="manage.label.sub-merchant.category" />:</td>
						<td><div id="confirmMcategory"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.autotransferlimit" />:</td>
						<td><div id="confirmMautoTransferLimit"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.vantivmerchantId" />:</td>
						<td><div id="confirmLitleMID"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="merchant.label.autotransferperiod" />:</td>
						<td><div id="confirmMautoTransferDay"></div></td>
					</tr>
					<tr id="hideDayTable" style="display: none;">
						<td><spring:message
								code="manage.label.sub-merchant.selectdayOftheweek" />:</td>
						<td><div id="confirmAutoTransferWeeklyDay"></div></td>
					</tr>
					<tr id="hideWeekyTable" style="display: none;">
						<td><spring:message
								code="manage.label.sub-merchant.selectdayOfmonth" />:
						</td>
						<td><div id="confirmAutoTransferMonthlyDay"></div></td>
					</tr>

					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.autopaymentmethod" />:</td>
						<td><div id="confirmMautoPaymentMethod"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.feeprogram" />:</td>
						<td><div id="confirmMfeeProgram"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.processor" />:</td>
						<td><div id="confirmMprocessor"></div></td>
					</tr>
					<tr>
						<td><spring:message code="common.label.agentName" />:</td>
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
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.virtualterminaloptions" />:</td>
						<td><div id="confirmMvirtualTerminalList"></div></td>
						<!-- <td><div id="confirmMvirtualTerminal"></div></td> -->
					</tr>

					<%-- <tr>
						<td><spring:message code="manage.label.sub-merchant.pos" />:</td>
						<td><div id="confirmMposTerminal"></div></td>
					</tr> --%>

					<tr>
						<td><spring:message code="manage.label.sub-merchant.online" />:</td>
						<td><div id="confirmMwebSiteAddress"></div>
							<div id="confirmMreturnURL"></div>
							<div id="confirmMcancelURL"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="manage.label.sub-merchant.agentdetails" />:</td>
						<td><div id="confirmAgent"></div></td>
					</tr>
				</table>
			</fieldset>
		</fieldset>
	</fieldset>
	<!--Panel Action Button Start -->
	<div class="col-sm-12 button-content">
		<fieldset class="col-sm-7 pull-right">
			<input type="submit" class="form-control button pull-right pos-next"
				value="<spring:message code="common.label.create" />">
			<input type="button"
				class="form-control button pull-right marginL10 pos-prev"
				value="<spring:message code="manage.buttton.sub-merchant.previous" />">
			<input type="button" class="form-control button pull-right marginL10"
				value="<spring:message code="manage.buttton.sub-merchant.cancel" />"
				onclick="cancelCreateSubMerchant()">
		</fieldset>
	</div>
	<!--Panel Action Button End -->
</section>