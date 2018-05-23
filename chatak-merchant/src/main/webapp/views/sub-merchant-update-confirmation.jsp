<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<section class="field-element-row pos-transaction-content"
	style="display: none;">
	<fieldset class="col-sm-12 padding0">
		<fieldset class="col-sm-6">
			<fieldset class="fieldset merchant-content">
				<legend class="legend content-space">
					<spring:message code="sub-merchant-create.label.basicinfo" />
				</legend>
				<table class="confirm-info-table">
					<tr>
						<td><spring:message
								code="sub-merchant-create.label.companyname" />:</td>
						<td><div id="confirmMbusinessName"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="search-sub-merchant.label.firstname" />:</td>
						<td><div id="confirmMfirstName"></div></td>
					</tr>
					<tr>
						<td><spring:message code="search-sub-merchant.label.lastname" />:</td>
						<td><div id="confirmMlastName"></div></td>
					</tr>
					<tr>
						<td><spring:message code="search-sub-merchant.label.phone" />:</td>
						<td><div id="confirmMphone"></div></td>
					</tr>
					<tr>
						<td><spring:message code="sub-merchant-create.label.fax" />:</td>
						<td><div id="confirmMfax"></div></td>
					</tr>
					<tr>
						<td><spring:message code="search-sub-merchant.label.e-mailid" />:</td>
						<td><div id="confirmMemailId"></div></td>
					</tr>
					<tr>
						<td><spring:message code="sub-merchant-create.label.address1" />:</td>
						<td><div id="confirmMaddress1"></div></td>
					</tr>
					<tr>
						<td><spring:message code="sub-merchant-create.label.address2" />:</td>
						<td><div id="confirmMaddress2"></div></td>
					</tr>
					<tr>
						<td><spring:message code="search-sub-merchant.label.city" />:</td>
						<td><div id="confirmMcity"></div></td>
					</tr>
					<tr>
						<td><spring:message code="sub-merchant-create.label.state" />:</td>
						<td><div id="confirmMstate"></div></td>
					</tr>
					<tr>
						<td><spring:message code="search-sub-merchant.label.country" />:</td>
						<td><div id="confirmMcountry"></div></td>
					</tr>
					<tr>
						<td><spring:message code="sub-merchant-create.label.zipcode" />:</td>
						<td><div id="confirmMpin"></div></td>
					</tr>
					<%-- <tr>
						<td><spring:message code="search-sub-merchant.label.status" />:</td>
						<td><div id="confirmMstatus"></div></td>
					</tr> --%>
					<tr>
						<td><spring:message
								code="sub-merchant-create.label.applicationmode" />:</td>
						<td><div id="confirmMappMode"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="sub-merchant-create.label.businessURL" />:</td>
						<td><div id="confirmMbusinessURL"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="sub-merchant-create.label.lookingfor" />?</td>
						<td><div id="confirmLookingFor"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="sub-merchant-create.label.businesstype" />:</td>
						<td><div id="confirmBusinessType"></div></td>
					</tr>
					<tr>
						<td><spring:message 
								code="dash-board.label.accountnumber" />:</td>
						<td>${accountNumber}</td>
					</tr>
				</table>
			</fieldset>
		</fieldset>

		<fieldset class="col-sm-6">
			<fieldset class="fieldset merchant-content">
				<legend class="legend content-space">
					<spring:message code="sub-merchant-create.label.bankinfo" />
				</legend>
				<table class="confirm-info-table">
					<tr>
						<td><spring:message code="sub-merchant-create.label.name"></spring:message>:</td>
						<td><div id="confirmbankAccountName"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="sub-merchant-create.label.bankroutingnumber"></spring:message>:</td>
						<td><div id="confirmbankRoutingNumber"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="sub-merchant-create.label.bankaccountnumber" />:</td>
						<td><div id="confirmbankAccountNumber"></div></td>
					</tr>

					<tr>
						<td><spring:message code="dash-board.label.type"></spring:message>:</td>
						<td><div id="confirmbankAccountType"></div></td>
					</tr>
					<tr>
						<td><spring:message code="sub-merchant-create.label.address1" />:</td>
						<td><div id="confirmbankAddress1"></div></td>
					</tr>
					<tr>
						<td><spring:message code="sub-merchant-create.label.address2" />:</td>
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
						<td><spring:message
								code="sub-merchant-create.label.nameonaccount"></spring:message>:</td>
						<td><div id="confirmbankNameOnAccount"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="sub-merchant-create.label.merchantcurrency"></spring:message>:</td>
						<td><div id="confirmCurrency"></div></td>
					</tr>
				</table>
			</fieldset>
		</fieldset>
		<fieldset class="col-sm-6">
			<fieldset class="fieldset contact-content">
				<legend class="legend content-space">
					<spring:message code="sub-merchant-create.label.additionalinfo" />
				</legend>
				<table class="confirm-info-table">
					<tr>
						<td><spring:message
								code="additional-information.label.username" />:</td>
						<td><div id="confirmMuserName"></div></td>
					</tr>
				</table>
			</fieldset>
		</fieldset>
		<fieldset class="col-sm-6">
			<fieldset class="fieldset merchant-content">
				<legend class="legend content-space">
					<spring:message code="sub-merchant-create.label.legalentity" />
				</legend>
				<table class="confirm-info-table">
					<tr>
						<td><spring:message
								code="sub-merchant-create.label.entitylegalname" />:</td>
						<td><div id="confirmlegalName"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="sub-merchant-create.label.ein/taxid" />::</td>
						<td><div id="confirmlegalTaxId"></div></td>
					</tr>
					<tr>
						<td><spring:message code="dash-board.label.type" />:</td>
						<td><div id="confirmlegalType"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="sub-merchant-create.label.expectedannualcardsales" />:</td>
						<td><div id="confirmlegalAnnualCard"></div></td>
					</tr>
					<tr>
						<td><spring:message code="sub-merchant-create.label.address1" />:</td>
						<td><div id="confirmlegalAddress1"></div></td>
					</tr>
					<tr>
						<td><spring:message code="sub-merchant-create.label.address2" />:</td>
						<td><div id="confirmlegalAddress2"></div></td>
					</tr>
					<tr>
						<td><spring:message code="search-sub-merchant.label.city" />:</td>
						<td><div id="confirmlegalCity"></div></td>
					</tr>
					<tr>
						<td><spring:message code="search-sub-merchant.label.country" />:</td>
						<td><div id="confirmlegalCountry"></div></td>
					</tr>
					<tr>
						<td><spring:message code="sub-merchant-create.label.state" />:</td>
						<td><div id="confirmlegalState"></div></td>
					</tr>
					<tr>
						<td><spring:message code="sub-merchant-create.label.zipcode" />:</td>
						<td><div id="confirmlegalPin"></div></td>
					</tr>
				</table>
			</fieldset>
		</fieldset>
		<fieldset class="col-sm-6">
			<fieldset class="fieldset merchant-content">
				<legend class="legend content-space">
					<spring:message
						code="sub-merchant-create.label.legalentityrepresentative" />
				</legend>
				<table class="confirm-info-table">
					<tr>
						<td><spring:message code="sub-merchant-create.label.ssn" />:</td>
						<td><div id="confirmlegalSSN"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="search-sub-merchant.label.firstname" />:</td>
						<td><div id="confirmlegalFirstName"></div></td>
					</tr>
					<tr>
						<td><spring:message code="search-sub-merchant.label.lastname" />:</td>
						<td><div id="confirmlegalLastName"></div></td>
					</tr>
					<tr>
						<td><spring:message code="search-sub-merchant.label.phone" />:</td>
						<td><div id="confirmlegalMobilePhone"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="sub-merchant-create.label.dateofbirth" />:</td>
						<td><div id="confirmlegalDOB"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="sub-merchant-create.label.passportnumber" />:</td>
						<td><div id="confirmlegalPassport"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="sub-merchant-create.label.countryofresidence" />:</td>
						<td><div id="confirmlegalCountryResidence"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="sub-merchant-create.label.countryofcitizenship" />:</td>
						<td><div id="confirmlegalCitizen"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="sub-merchant-create.label.homephone" />:</td>
						<td><div id="confirmlegalHomePhone"></div></td>
					</tr>
				</table>
			</fieldset>
		</fieldset>
		<fieldset class="col-sm-6">
			<fieldset class="fieldset bank-content">
				<legend class="legend content-space">
					<spring:message code="sub-merchant-create.label.configurations" />
				</legend>
				<table class="confirm-info-table">
					<tr>
						<td><spring:message
								code="configurations.label.autosettlementoptions" />:</td>
						<td><div id="confirmMautoSettlement"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="configurations.label.merchantcallbackURL" />:</td>
						<td><div id="confirmMmerchantCallBackURL"></div></td>
					</tr>
					<tr>
						<td><spring:message code="configurations.label.category" />:</td>
						<td><div id="confirmMcategory"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="configurations.label.autotransferlimit" />:</td>
						<td><div id="confirmMautoTransferLimit"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="configurations.label.vantivmerchantid" />:</td>
						<td><div id="confirmLitleMID"></div></td>
					</tr>
					<tr>
						<td><spring:message code="merchant.label.autotransferperiod" />:</td>
						<td><div id="confirmMautoTransferDay"></div></td>
					</tr>
					<tr id="hideDayTable" style="display: none;">
						<td><spring:message
								code="configurations.label.selectdayoftheweek" />:</td>
						<td><div id="confirmAutoTransferWeeklyDay"></div></td>
					</tr>
					<tr id="hideWeekyTable" style="display: none;">
						<td><spring:message
								code="configurations.label.selectdayOfmonth" />:</td>
						<td><div id="confirmAutoTransferMonthlyDay"></div></td>
					</tr>

					<tr>
						<td><spring:message
								code="configurations.label.autopaymentmethod" />:</td>
						<td><div id="confirmMautoPaymentMethod"></div></td>
					</tr>

					<tr>
						<td><spring:message code="configurations.label.feeprogram" />:</td>
						<td><div id="confirmMfeeProgram"></div></td>
					</tr>
					<tr>
						<td><spring:message code="configurations.label.processor" />:</td>
						<td><div id="confirmMprocessor"></div></td>
					</tr>
					<tr>
						<td><spring:message
								code="configurations.label.virtualterminaloptions" />:</td>
						<td><div id="confirmMvirtualTerminalList"></div></td>
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
					<%-- <tr>
						<td><spring:message code="configurations.label.pos" />:</td>
						<td><div id="confirmMposTerminal"></div></td>
					</tr> --%>
					<tr>
						<td><spring:message code="configurations.label.online" />:</td>
						<td><div id="confirmMwebSiteAddress"></div>
							<div id="confirmMreturnURL"></div>
							<div id="confirmMcancelURL"></div></td>
					</tr>
					<tr>
						<td><spring:message code="configurations.label.agentdetails" />:</td>
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
				value="<spring:message code="recurring-contract-info-edit.label.Update"/>">
			<input type="button"
				class="form-control button pull-right marginL10 pos-prev"
				value="<spring:message code="sub-merchant-create.label.previous"></spring:message>">
			<input type="button" class="form-control button pull-right marginL10"
				value="<spring:message code="sub-merchant-create.label.cancel"></spring:message>"
				onclick="openCancelConfirmationPopup()">
		</fieldset>
	</div>
	<!--Panel Action Button End -->

</section>