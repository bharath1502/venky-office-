<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<section class="field-element-row pos-transaction-content" style="display:none;">
												<fieldset class="col-sm-12 padding0">
													<fieldset class="col-sm-6">
														<fieldset class="fieldset merchant-content">
															<legend class="legend content-space"><spring:message code="sub-merchant-create.label.basicinfo"/></legend>
															<table class="confirm-info-table">
																<tr>
																	<td><spring:message code="sub-merchant-create.label.companyname"/>:</td>
																	<td><div id="confirmMbusinessName"></div></td>
																</tr>
																<!-- <tr>
																	<td>Merchant Code:</td>
																	<td><div id="confirmMmerchantCode"></div></td>
																</tr> -->
																<tr>
																	<td><spring:message code="search-sub-merchant.label.firstname"/>:</td>
																	<td><div id="confirmMfirstName"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="search-sub-merchant.label.lastname"/>:</td>
																	<td><div id="confirmMlastName"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="search-sub-merchant.label.phone"/>:</td>
																	<td><div id="confirmMphone"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="sub-merchant-create.label.fax"/>:</td>
																	<td><div id="confirmMfax"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="search-sub-merchant.label.e-mailid"/>:</td>
																	<td><div id="confirmMemailId"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="sub-merchant-create.label.address1"/>:</td>
																	<td><div id="confirmMaddress1"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="sub-merchant-create.label.address2"/>:</td>
																	<td><div id="confirmMaddress2"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="search-sub-merchant.label.city"/>:</td>
																	<td><div id="confirmMcity"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="sub-merchant-create.label.state"/>:</td>
																	<td><div id="confirmMstate"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="search-sub-merchant.label.country"/>:</td>
																	<td><div id="confirmMcountry"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="sub-merchant-create.label.zipcode"/>:</td>
																	<td><div id="confirmMpin"></div></td>
																</tr>
																<%-- <tr>
																	<td><spring:message code="search-sub-merchant.label.status"/>:</td>
																	<td><div id="confirmMstatus"></div></td>
																</tr> --%>
																<tr>
																	<td><spring:message code="sub-merchant-create.label.applicationmode"/>:</td>
																	<td><div id="confirmMappMode"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="sub-merchant-create.label.businessURL"/>:</td>
																	<td><div id="confirmMbusinessURL"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="sub-merchant-create.label.lookingfor"/>?</td>
																	<td><div id="confirmLookingFor"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="sub-merchant-create.label.businesstype"/>:</td>
																	<td><div id="confirmBusinessType"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="additional-information.label.username"/>:</td>
																	<td><div id="confirmMuserName"></div></td>
																</tr>
															</table>
														</fieldset>
													</fieldset>
													
													<fieldset class="col-sm-6">
														<fieldset class="fieldset merchant-content">
															<legend class="legend content-space"><spring:message code="sub-merchant-create.label.bankinfo"/></legend>
															<table class="confirm-info-table">
																<tr>
																	<td><spring:message code="sub-merchant-create.label.name"></spring:message>:</td>
																	<td><div id="confirmbankAccountName"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="sub-merchant-create.label.bankroutingnumber"></spring:message>:</td>
																	<td><div id="confirmbankRoutingNumber"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="sub-merchant-create.label.bankaccountnumber"/>:</td>
																	<td><div id="confirmbankAccountNumber"></div></td>
																</tr>
																
																<tr>
																	<td><spring:message code="dash-board.label.type"></spring:message>:</td>
																	<td><div id="confirmbankAccountType"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="sub-merchant-create.label.address1"/>:</td>
																	<td><div id="confirmbankAddress1"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="sub-merchant-create.label.address2"/>:</td>
																	<td><div id="confirmbankAddress2"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="search-sub-merchant.label.city"></spring:message>:</td>
																	<td><div id="confirmbankCity"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="search-sub-merchant.label.country"></spring:message>:</td>
																	<td><div id="confirmbankCountry"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="sub-merchant-create.label.state"></spring:message>:</td>
																	<td><div id="confirmbankState"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="sub-merchant-create.label.zipcode"></spring:message>:</td>
																	<td><div id="confirmbankPin"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="sub-merchant-create.label.nameonaccount"></spring:message>:</td>
																	<td><div id="confirmbankNameOnAccount"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="sub-merchant-create.label.merchantcurrency"></spring:message>:</td>
																	<td><div id="confirmCurrency"></div></td>
																</tr>
															</table>
														</fieldset>
													</fieldset>
													<fieldset class="col-sm-6">
														<fieldset class="fieldset bank-content">
															<legend class="legend content-space"><spring:message code="sub-merchant-create.label.configurations"/></legend>
															<table class="confirm-info-table">
																<tr>
																	<td><spring:message code="configurations.label.merchantcallbackURL"/>:</td>
																	<td><div id="confirmMmerchantCallBackURL"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="configurations.label.category"/>:</td>
																	<td><div id="confirmMcategory"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="configurations.label.autotransferlimit"/>:</td>
																	<td><div id="confirmMautoTransferLimit"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="configurations.label.vantivmerchantid"/>:</td>
																	<td><div id="confirmLitleMID"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="merchant.label.autotransferperiod"/>:</td>
																	<td><div id="confirmMautoTransferDay"></div></td>
																</tr>
																<tr id="hideDayTable" style="display: none;">
																	<td><spring:message code="configurations.label.selectdayoftheweek"/>:</td>
																	<td><div id="confirmAutoTransferWeeklyDay"></div></td>
																</tr>
																<tr id="hideWeekyTable" style="display: none;">
																	<td><spring:message code="configurations.label.selectdayOfmonth"/>:</td>
																	<td><div id="confirmAutoTransferMonthlyDay"></div></td>
																</tr>

																<tr>
																	<td><spring:message code="configurations.label.autopaymentmethod"/>:</td>
																	<td><div id="confirmMautoPaymentMethod"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="configurations.label.processor"/>:</td>
																	<td><div id="confirmMprocessor"></div></td>
																</tr>
																<tr>
																	<td><spring:message code="configurations.label.virtualterminaloptions"/>:</td>
																	<td><div id="confirmMvirtualTerminalList"></div></td>
																	<!-- <td><div id="confirmMvirtualTerminal"></div></td> -->
																</tr>
																<tr>
																<td><spring:message code="configurations.label.online"/>:</td>
																	<td><div id="confirmMwebSiteAddress"></div>
																		<div id="confirmMreturnURL"></div>
																		<div id="confirmMcancelURL"></div>
																	</td>
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
															value="<spring:message code="search-sub-merchant.label.create"/>"> <input type="button"
															class="form-control button pull-right marginL10 pos-prev"
															value="<spring:message code="sub-merchant-create.label.previous"></spring:message>"> <input type="button"
															class="form-control button pull-right marginL10"
															value="<spring:message code="sub-merchant-create.label.cancel"></spring:message>" onclick="openCreateCancelConfirmationPopup()">
													</fieldset>
												</div>

	<div id="my_popup1" class="popup-void-refund voidResult">
		<span class="glyphicon glyphicon-remove closePopupMes"
			onclick="closeCancelConfirmationPopup()"></span>
		<div class="fw-b-fs15" style="padding: 20px;">
			<spring:message code="cancle.conformation.lable.currency" />
		</div>
		<div class="col-sm-12">

			<input type="button"
				class="form-control button pull-right margin5 close-btn"
				value="<spring:message code="bin.label.no"/>"
				onclick="closeCancelConfirmationPopup()"> <input
				type="submit" class="form-control button pull-right margin5"
				value="<spring:message code="configurations.label.yes"/>"
				onclick="cancelCreateMerchant()">
		</div>
	</div>
	<!--Panel Action Button End -->
											</section>