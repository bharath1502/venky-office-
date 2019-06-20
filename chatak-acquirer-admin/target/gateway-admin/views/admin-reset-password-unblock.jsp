<!DOCTYPE html>
<%@page import="com.chatak.acquirer.admin.constants.StatusConstants"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="com.chatak.pg.util.Constants"%>
<%
  int year = Calendar.getInstance().get(Calendar.YEAR);
%>

<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><spring:message code="common.lable.title"/></title>
<!-- Bootstrap -->
<link rel="icon" href="../images/favicon.png" type="image/png">
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
    <!--Body Wrapper block Start -->
    <div id="wrapper">
      <!--Container block Start -->
      <div class="container-fluid">
        <!--Navigation Block Start -->
        <%@include file="navigation-panel.jsp"%>
        <!--Article Block Start-->
        <article>
          <div class="col-xs-12 content-wrapper">
            <!-- Breadcrumb start -->
            <div class="breadCrumb">
              <span class="breadcrumb-text"><spring:message code="header.label.dashboard" /></span> <span
                class="glyphicon glyphicon-play icon-font-size"></span> <span
                class="breadcrumb-text"><spring:message code="header.label.unblockusers" /></span> <span
                class="glyphicon glyphicon-play icon-font-size"></span> <span
                class="breadcrumb-text"><spring:message code="reports.label.searchbutton" /></span>
            </div>
            <div class="tab-header-container-first active-background">
				<a href="#"><spring:message code="common.label.search"/> </a>
			</div>
            <form:form action="bankPaginationAction" name="paginationForm"
              method="post">
              <input type="hidden" id="pageNumberId" name="bankPageData" /> 
              <input type="hidden" id="totalRecordsId" name="totalRecords" />
              <input type="hidden" name="CSRFToken" value="${tokenval}">
            </form:form>
            <div class="main-content-holder">
              <div class="row">
                <div class="col-sm-12">
                  <!--Success and Failure Message Start-->
                  <span class="green-error" id="sucessDiv">${sucess}</span> <span
                    class="red-error" id="errorDiv">${error}</span>
                  <div class="col-sm-12">
                    <div class="discriptionErrorMsg">
                      <span class="red-error">&nbsp;</span> <span
                        class="green-error">&nbsp;</span>
                    </div>
                  </div>
                  <!--Success and Failure Message End-->
                  <!-- Page Form Start -->
                  <form:form action="performUserUnblock" name="performUserUnblockForm" method="post">
                  	<input type="hidden" name="userName" id="userName">
                 	<input type="hidden" name="entityType" id="entityType">
                 	<input type="hidden" name="CSRFToken" value="${tokenval}">
                  </form:form>
                  <form:form action="unblockUser" modelAttribute="userDataDto" method="post">
                  <input type="hidden" name="CSRFToken" value="${tokenval}">
                    <div class="col-sm-12">
                      <div class="row">
                        <div class="field-element-row">
                          <fieldset class="col-md-3 col-sm-6"> 
								<label><spring:message code="users.label.usertype" /><span class="required-field">*</span></label>
								<form:select  id="userType" path="userType" cssClass="form-control">
										<c:forEach items="${roleLevelList}" var="roleLevelList">
															         <form:option value="${roleLevelList.value}">${roleLevelList.value}</form:option>
														        </c:forEach>
								</form:select>
								<input type="hidden" id="userType" value=${userType}>
								<div class="discriptionErrorMsg">
									<span class="red-error"  id="entityTypeErrorMsg">&nbsp;</span>
								</div> 
							</fieldset>                     
                        </div>
                      </div>
                      <!--Panel Action Button Start -->
                      <div class="col-sm-12 form-action-buttons">
                        <div class="col-sm-5"></div>
                        <div class="col-sm-7">
                          <input type="submit" class="form-control btn btn-default button pull-right"
                            value='<spring:message code="reports.label.searchbutton" />' onclick="return validateBlockedUser();" />
                            <input type="button" class="form-control btn btn-default button pull-right" value='<spring:message code="accounts-manual-debit.label.resetbutton"/>' onclick="validateReset();"/>
                        </div>
                      </div>
                      <!--Panel Action Button End -->
                    </div>
                  </form:form> 
                  <!-- Page Form End -->
                </div>
              </div>
            </div> 
            <!-- Content Block End -->
            <!-- Search Table Block Start -->
            <c:if test="${flag ne false }">
              <div class="search-results-table">
                <table
                  class="table table-striped table-bordered table-condensed marginBM1">
                  <tr>
                    <td class="search-table-header-column widthP80"><span
                      class="glyphicon glyphicon-search search-table-icon-text"></span>
                      <span><spring:message code="header.label.searchsummary"/></span>
                      <span class="pull-right"><spring:message code="common.label.totalcount"/> : <label id="totalCount">${totalRecords}</label></span>
                    </td>
                  </tr>
                </table>
                <!-- Search Table Header End -->
                <!-- Search Table Content Start -->
                 <table id="serviceResults"
                  class="table table-striped table-bordered table-responsive table-condensed tablesorter marginBM1 common-table resize-table">
                  <thead>
                    <tr>
                      <th><spring:message code="manage.label.sub-merchant.firstname"/></th>
                      <th><spring:message code="manage.label.sub-merchant.lastname"/></th>
                      <th><spring:message code="pending-merchant-show.label.username"/></th>
                      <th><spring:message code="merchant-file-exportutil-email"/></th>
                      <th><spring:message code="home.label.action"/></th>
                    </tr>
                  </thead>
                  <c:choose>
                    <c:when test="${!(fn:length(blockedUserList) eq 0) }">
                      <c:forEach items="${blockedUserList}" var="blockedUserList">
                        <tr>
                          <td data-title="first Name" class="ellipsis" id="15"  title="${blockedUserList.firstName}">${blockedUserList.firstName}&nbsp;</td>
                          <td data-title="lastName" class="ellipsis" id="15" title="${blockedUserList.lastName}">${blockedUserList.lastName}&nbsp;</td>
                          <c:if test="${blockedUserList.userName ne null}">
                          <td data-title="userName" >${blockedUserList.userName}  &nbsp;</td>
                          </c:if>
                          <td data-title="email" class="ellipsis" id="15" title="${blockedUserList.email}">${blockedUserList.email}&nbsp;
                          <td>
                          	<input type="button" class="button create-card-data td-button buttonP40" value="unblock" onclick="userUnlock('${blockedUserList.userName}')">
                          </td>
                        </tr>
                      </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <td colspan="6" style="color: red;"><spring:message code="commission-program-search.label.norecordsfound"/></td>
                    </c:otherwise>
                  </c:choose>
                </table> 
                <!-- Search Table Content End -->
                <!--Panel Action Button Start -->
						<div class="col-sm-12 form-action-buttons">
							<fieldset class="col-sm-5 pull-right">
								<input type="button"
									class="form-control btn btn-default button pull-right"
									value='<spring:message code="show-all-pending-merchants.label.backbutton"/>' onclick="validatBack()">
							</fieldset>
						</div>
						<!--Panel Action Button End -->
              </div>
              </c:if>
              </div>
           </article>
            <!-- Search Table Block End -->
          </div>
        <!--Article Block End-->
        <jsp:include page="footer.jsp" />
      </div>
    <!--Body Wrapper block End -->
    <!-- Pop Up box information starts here -->     
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="../js/jquery.min.js"></script>
	<script src="../js/jquery.popupoverlay.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
   <script src="../js/utils.js"></script>
	<script src="../js/messages.js" type="text/javascript"></script>
	<script type="text/javascript" src="../js/admin-user.js"></script>
	<script src="../js/sortable.js"></script>
	<script type="text/javascript" src="../js/backbutton.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	
    <script>
      /* Select li full area function Start */
      $("li").click(function() {
      	window.location = $(this).find("a").attr("href");
      	return false;
      });
      /* Select li full area function End */
      /* Common Navigation Include Start */
      $(function() {
      	$("#main-navigation").load("main-navigation.html");
      });
      function highlightMainContent() {
      	$("#navListId2").addClass("active-background");
      }
      /* Common Navigation Include End */
      
      $(document).ready(function() {
      	$('#bankDiv').popup({
      		blur : false
      	});
      	$('input:visible:enabled:first').focus();
      });
      function closePopup() {
      	$('#bankDiv').popup("hide");
      }
      function openPopup() {
      	$('#bankDiv').popup("show");
      }
      
      $(".table-hide-btn").click(function() {
      	$(".search-results-table").slideUp();
      });
      $(document).ready(function() {
			/* Table Sorter includes Start*/
			$(function() {
				
					  // call the tablesorter plugin
					  $('#serviceResults').sortable({
						
						 divBeforeTable: '#divbeforeid',
						divAfterTable: '#divafterid',
						initialSort: false,
						locale: 'th',
						//negativeSort: [1, 2]
					});
			});
			});
    </script>
  </body>
</html>