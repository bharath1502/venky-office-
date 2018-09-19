<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.Calendar"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<section class="field-element-row pm-iso-carprogram-content"
	style="display: none;">
	<fieldset class="col-sm-12">
		<fieldset class="col-sm-3">
			<label data-toggle="tooltip" data-placement="top" title=""><spring:message
					code="merchant.label.associatedto" /><span class="required-field">*</span></label>
			<form:select cssClass="form-control" path="associatedTo"
				id="associatedTo"
				onchange="fetchEntityNameByPmIso(this.value, 'programManagerName');validateEntityType()"
				onblur="validateAssociatedTo()" readonly="true">
				<form:option value="">
					<spring:message code="reports.option.select" />
				</form:option>
				<form:option value="Program Manager">
					<spring:message code="admin.pm.message" />
				</form:option>
				<form:option value="ISO">
					<spring:message code="merchant.label.iso" />
				</form:option>
			</form:select>
			<div class="discriptionErrorMsg" data-toggle="tooltip"
				data-placement="top" title="">
				<span id="associatedToEr" class="red-error">&nbsp;</span> <span
					id="associatedToEr" class="green-error">&nbsp;</span>
			</div>
		</fieldset>
	</fieldset>
	<fieldset class="col-sm-12">
		<fieldset class="col-sm-5 multi-select-box">
			<label id="entityType"></label> <select
				class="pm-list form-control left-select-box" multiple="multiple"
				id="programManagerName">
					<c:forEach items="${EntityList}" var="entityType">
					<option value="${entityType.value}">${entityType.label}</option>
				</c:forEach>
			</select>
			
		</fieldset>
		<fieldset class="col-sm-1 multi-select-btn marginT30">
			<span class="left-right-btn form-control"
				onClick="SelectMoveRows(document.getElementsByClassName('pm-list')[0],document.getElementsByClassName('selected-pm')[0],'ADD')">
				&gt; </span> <span class="right-left-btn form-control"
				onClick="SelectMoveRows(document.getElementsByClassName('selected-pm')[0],document.getElementsByClassName('pm-list')[0],'REMOVE')">
				&lt; </span>
		</fieldset>
		<label id="associatedID"></label>
		<fieldset class="col-sm-5 multi-select-box">
			<select id="programManagerName"
				class="selected-pm form-control right-select-box" MULTIPLE
				onblur="validateAssociatedToEntityName(this)">
				<c:forEach items="${selectedEntityList}" var="entity">
					<script>
					setEntityId('${entity.id}');
					</script>
					<c:if test="${ not empty entity.programManagerName}">
				<option value="${entity.id}">${entity.programManagerName}</option>
				</c:if>
				<c:if test="${ not empty entity.isoName}">
				<option value="${entity.id}">${entity.isoName}</option>
				</c:if>
			</c:forEach>
			</select>
			<div class="discriptionErrorMsg" data-toggle="tooltip"
				data-placement="top" title="">
				<span id="programManagerNameEr" class="red-error">&nbsp;</span> <span
					id="programManagerNameEr" class="green-error">&nbsp;</span>
			</div>
		</fieldset>
	</fieldset>
	<!--Panel Action Button Start -->
	<!-- Search Table Block Start -->
	<c:set var="flageCheck" scope="session" value="yes" />
	<%-- <c:if test="${searchList ne flageCheck }"> --%>
	<div class="search-results-table">
		<!-- Search Table Header End -->
		<!-- Search Table Content Start -->
		<table id="serviceResults"
			class="table table-striped table-bordered table-responsive table-condensed tablesorter marginBM1 common-table">
			<thead>
				<tr>
					<th><spring:message code="admin.PartnerName.message" /></th>
					<th><spring:message code="admin.cardprogramname" /></th>
					<th><spring:message code="admin.iin" /></th>
					<th><spring:message code="merchant.label.partnerCode"/></th>
					<th><spring:message code="admin.iinext"/></th>
					<th style="width: 15%;" id="userType"><spring:message
							code="merchant.label.entityname" /></th>
					<th><spring:message
							code="reports.label.overviewandbalancesheet.currency" /></th>
					<th><spring:message
							code="commission-program-search.label.actiontable" /></th>
				</tr>
			</thead>
			<c:choose>
				<c:when test="${!(fn:length(selectedCardProgramList) eq 0) }">
					<c:forEach items="${selectedCardProgramList}"
						var="cardProgramDetail">
						<script>
							setCardProgramId('${cardProgramDetail.cardProgramId}');
						</script>
						<tr id="rowId${cardProgramDetail.cardProgramId}">
							<td class="ellipsis" id="15">${cardProgramDetail.partnerName}&nbsp;</td>
							<td class="ellipsis" id="15">${cardProgramDetail.cardProgramName}&nbsp;</td>
							<td>${cardProgramDetail.iin}&nbsp;</td>
							<td>${cardProgramDetail.partnerCode}&nbsp;</td>
							<td>${cardProgramDetail.iinExt}&nbsp;</td>
							<td>${cardProgramDetail.entityName}&nbsp;</td>
							<td>${cardProgramDetail.currency}&nbsp;</td>
							<c:if test="${cardProgramDetail.selected eq true}">
							<td><input id="cpId${cardProgramDetail.cardProgramId}" type="checkbox" checked="checked" onclick="addCardProgram('${cardProgramDetail.cardProgramId}')"></td>
							<script>
							setCardProgramId('${cardProgramDetail.cardProgramId}');
							</script>												
							</c:if>
							<c:if test="${cardProgramDetail.selected eq false}">
							<td><input id="cpId${cardProgramDetail.cardProgramId}" type="checkbox"  onclick="addCardProgram('${cardProgramDetail.cardProgramId}')"></td>												
							</c:if>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</table>
	</div>
	<!-- Search Table Content End -->
	<div class="col-sm-12 button-content">
		<fieldset class="col-sm-7 pull-right">
			<input type="button"
				value='<spring:message code="common.label.continue"/>'
				class="form-control button pull-right pic-next"> <input
				type="button" value='<spring:message code="common.label.previous"/>'
				class="form-control button pull-right marginL10 pic-prev"> <input
				type="button" class="form-control button pull-right marginL10"
				value='<spring:message code="common.label.cancel"/>'
				onclick="goToMerchantSearch()"> 
		</fieldset>
	</div>
	<!--Panel Action Button End -->
</section>
