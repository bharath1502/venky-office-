<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.Calendar"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div id="deletePopup" class="popup-void-refund">
					<span class="glyphicon glyphicon-remove closePopupMes"
						onclick="closePopup()"></span>
					<div class="fw-b-fs15" style="padding: 20px;">
						<spring:message code="delete.label.sure" />
					</div>
					<div class="col-sm-12">
						<input type="submit"
							class="form-control button pull-right margin5"
							value="<spring:message code="common.label.delete"/>"
							onclick="deleteData();"> <input type="button"
							class="form-control button pull-right margin5 close-btn"
							onclick="closePopup()"
							value="<spring:message code="common.label.cancel"/>">
					</div>
				</div>
				<script src="../js/jquery.popupoverlay.js"></script>
				<script>
				$('#deletePopup').popup("hide");
				$(document).ready(function() {
					$('#deletePopup').popup({
						autoopen : false,
						blur : false
					});
				});
				</script>