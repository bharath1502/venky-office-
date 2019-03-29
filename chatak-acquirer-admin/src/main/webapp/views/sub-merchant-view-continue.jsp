<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<section class="field-element-row atm-transaction-content"
	style="display: none;">
	<fieldset class="col-sm-12">
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
					code="manage.label.sub-merchant.merchantcallbackURL" /></label>
			<form:input cssClass="form-control" path="merchantCallBackURL"
				id="merchantCallBackURL" onblur="validateCallbackURL()" />
			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
				<span id="merchantCallBackURLEr" class="red-error">&nbsp;</span>
			</div>
		</fieldset>
		<fieldset class="col-sm-3">
			<label data-toggle="tooltip" data-placement="top" title=""><spring:message
					code="manage.label.sub-merchant.category" /><span
				class="required-field">*</span></label>
			<form:select cssClass="form-control" path="category" id="category">
				<form:option value="primary">
					<spring:message code="manage.option.sub-merchant.primary" />
				</form:option>
				<form:option value="secondary">Secondary<spring:message
						code="manage.option.sub-merchant.secondary" />
				</form:option>
			</form:select>
			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
				<span id="categoryEr" class="red-error">&nbsp;</span>
			</div>
		</fieldset>
		<fieldset class="col-sm-3">
			<label data-toggle="tooltip" data-placement="top" title=""><spring:message
					code="manage.label.sub-merchant.autotransferlimit" /> </label>
			<form:input cssClass="form-control" maxlength="10"
				path="autoTransferLimit" id="autoTransferLimit"
				onblur="validateAutoTransferLimit()" />
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
				maxlength="50" onblur="validatelitleMID()" />
			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
				<span id="litleMIDEr" class="red-error">&nbsp;</span>
			</div>
		</fieldset>


		<!-- ADDED VIRTUAL, POS and ONLINE TERMINALS START-->


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

								<fieldset id="virtualTerminalOptions">

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
							</fieldset>
							<br> --%>

							<fieldset class="col-sm-12">
								<form:checkbox path="online" id="online" value="0"
									onclick="validateOnlineOptions()" />
								<spring:message code="manage.label.sub-merchant.online" />

								<fieldset id="onlineOptions">

									<fieldset class="col-sm-3">
										<label data-toggle="tooltip" data-placement="top" title=""><spring:message
												code="manage.label.sub-merchant.websiteaddress" /><span
											class="required-field">*</span></label>
										<form:input cssClass="form-control" path="webSiteAddress"
											id="webSiteAddress" onblur="validateWebSiteAddressURL()" />
										<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
											<span id="webSiteAddressErrorDiv" class="red-error">&nbsp;</span>
										</div>
									</fieldset>

									<fieldset class="col-sm-3">
										<label data-toggle="tooltip" data-placement="top" title=""><spring:message
												code="manage.label.sub-merchant.returnURL" /><span
											class="required-field">*</span></label>
										<form:input cssClass="form-control validate" path="returnUrl"
											id="returnUrl" onblur="validateReturnURL()" />
										<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
											<span id="returnURLErrorDiv" class="red-error">&nbsp;</span>
										</div>
									</fieldset>

									<fieldset class="col-sm-3">
										<label data-toggle="tooltip" data-placement="top" title=""><spring:message
												code="manage.label.sub-merchant.cancelURL" /><span
											class="required-field">*</span></label>
										<form:input cssClass="form-control validate" path="cancelUrl"
											id="cancelUrl" onblur="validateCancelURL()" />
										<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
											<span id="cancelURLErrorDiv" class="red-error">&nbsp;</span>
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
	</fieldset>
	<!--Panel Action Button Start -->
	<div class="col-sm-12 button-content merchantDiv1">
		<fieldset class="col-sm-7 pull-right">
			<input type="button"
				class="form-control button pull-right marginL10 atm-prev"
				value="<spring:message code="manage.buttton.sub-merchant.previous" />">
			<input type="button" class="form-control button pull-right marginL10"
				value="<spring:message code="manage.buttton.sub-merchant.cancel" />"
				onclick="goToSubMerchantSearch()">
		</fieldset>
	</div>
	<div class="col-sm-12 button-content subMerchantDiv1"
		style="display: none;">
		<fieldset class="col-sm-7 pull-right">
			<input type="button"
				class="form-control button pull-right marginL10 atm-prev"
				value="<spring:message code="manage.buttton.sub-merchant.previous" />">
			<input type="button" class="form-control button pull-right marginL10"
				value="<spring:message code="manage.buttton.sub-merchant.cancel" />"
				onclick="goToSubMerchantSearch()">
		</fieldset>
	</div>
	<!--Panel Action Button End -->
</section>
