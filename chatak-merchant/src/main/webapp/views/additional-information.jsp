<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<section class="field-element-row free-transactions-content"
	style="display: none;">
	<fieldset class="col-sm-12">
		<fieldset class="col-sm-3">
			<label data-toggle="tooltip" data-placement="top" title=""><spring:message
					code="additional-information.label.username" /><span
				class="required-field">*</span></label>
			<form:input cssClass="form-control" path="userName" id="userName"
				maxlength="50"
				onblur="this.value=this.value.trim();vlalidateUserName()" />
			<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
				<span id="userNameEr" class="red-error">&nbsp;</span> <span
					id="userNamegreenEr" class="green-error">&nbsp;</span>
			</div>
		</fieldset>
	</fieldset>
	<!--Panel Action Button Start -->
	<div class="col-sm-12 button-content">
		<fieldset class="col-sm-8 pull-right">
			<input type="button" class="form-control button pull-right free-next"
				value="<spring:message code="sub-merchant-create.label.continue"></spring:message>">
			<input type="button"
				class="form-control button pull-right marginL10 free-prev"
				value="<spring:message code="sub-merchant-create.label.previous"></spring:message>">
			<input type="button"
				class="form-control button pull-right marginL10"
				value="<spring:message code="sub-merchant-create.label.cancel"></spring:message>"
				onclick="cancelCreateMerchant()">
			<input type="button" class="form-control button pull-right marginL10"
				value="<spring:message code="search-sub-merchant.lable.reset"></spring:message>"
				onclick="resetAdditionalInfo()">
		</fieldset>
	</div>
	<!--Panel Action Button End -->
</section>