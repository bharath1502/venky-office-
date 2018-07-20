<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.chatak.merchant.constants.StatusConstants"%>
<html>
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
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
     <style type="text/css">
      .txnDescDiv {
          width: 500px;
          max-width: 500px;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
      }
      .txnDescDiv:hover {
          white-space: pre-wrap;
          width: 500px;
          overflow: visible;
          word-break: break-all;
      }
      .txnDescDiv1 {
          width: 400px;
          max-width: 400px;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
      }
      .txnDescDiv1:hover {
          white-space: pre-wrap;
          width: 400px;
          overflow: visible;
          word-break: break-all;
      }
      .main-content-holder .sub-panel-heading-container {
        background-color: #aaaaaa;
        overflow: hidden;
      }
      .main-content-holder .sub-panel-body{
        padding-top: 20px;
        padding-bottom: 5px      
      }
      .main-content-holder .sub-panel-body label{
        font-size: 11px; 
        font-weight: normal;     
      }
      .main-content-holder .sub-panel-body .col-sm-4 {
        width: 33.33%;
      }
      .main-content-holder .sub-panel-heading-container .sub-panel-heading {
        padding-left: 15px;
        color: #000; 
        margin:6px 0px;
      } 
      .input-currency-symbol {
        position: absolute;
        left: 20px;
        z-index: 999;
        top: 2px;
        font-size: 16px;
      }
      .input-currency-symbol+input[type="text"], .input-currency-symbol+input[type="number"]{
        padding-left: 16px;
      }
      .paddingR30 {
        padding-right: 30px;
      }
      .main-content-holder .sub-panel-buttons .btn {
        font-size: 14px;
        padding: 6px 12px;
      }
      .text-align-right {
        text-align: right;
      }
    </style>
</head>
<body oncontextmenu="disableRightClick(<%=StatusConstants.ALLOW_RIGHT_CLICK%>)">
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
		<jsp:include page="header.jsp"></jsp:include>
			<!--Navigation Block Start -->
			<nav class="col-sm-12 nav-bar" id="main-navigation">
				<%-- <jsp:include page="main-navigation.jsp"></jsp:include> --%>
				<%@include file="navigation-panel.jsp"%>
			</nav>
			<!--Navigation Block End -->
			<!--Article Block Start-->
			<article>
		          <div class="col-xs-12 content-wrapper">
            <!-- Breadcrumb start -->
            <div class="breadCrumb">
              <span class="breadcrumb-text"><spring:message code="adjustments.label.adjustments"/></span> <span class="glyphicon glyphicon-play icon-font-size"></span> <span class="breadcrumb-text"><spring:message code="adjustments.label.accounttoaccounttransfer"/></span>
            </div>
            
            <div class="discriptionErrorMsg red-error" id="errorMsgDiv">
				<span class="red-error">&nbsp;${error}</span> 
				<span class="green-error">&nbsp;${success}</span>
			</div>
            
            <form action="show-account-transfer" method="post">
            <input type="hidden" name="CSRFToken" value="${tokenval}">
            <div class="main-content-holder padding0">
              <header class="sub-panel-heading-container"> 
                <h6 class="sub-panel-heading"> <spring:message code="adjustments.label.transferfrom"/>:</h6>
              </header>
              <div class="row sub-panel-body">
                <div class="col-sm-12">
                  <div class="col-sm-6 form-group">
                  
                    <label class="col-sm-7 row"> <spring:message code="adjustments.label.accountnumber"/>: </label>
					<div class="col-sm-5 row">
						<select class="form-control" id="fromAccId" name="sourceAccountNumber">
							<c:forEach items="${accounts}" var="account">
								<option data-acc-balance="${account.value}" value="${account.label}">${account.label}</option>
							</c:forEach>
						</select>
						<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
							<span class="red-error" id="fromAccIdEr">&nbsp;</span>
						</div>
					</div>
				</div>
                  <div class="col-sm-6 form-group">
                    <div class="pull-right">
                      <label data-toggle="tooltip" data-placement="top" title=""> <spring:message code="adjustments.label.availablebalance"/>: <span id="fromAvailBal"> </span></label>
                    </div>
                  </div>
                </div>
              </div>
            </div><!-- .main-content-holder -->
            
            <div class="main-content-holder padding0">
              <header class="sub-panel-heading-container"> 
                <h6 class="sub-panel-heading"> <spring:message code="adjustments.label.transferto"/>:</h6>
              </header>
              <div class="row sub-panel-body">
                <div class="col-sm-12">
                  <div class="col-sm-6 form-group">
                    <label class="col-sm-7 row"> <input type="radio" id="ownAccount" name="transfer-to-account" checked value="other-account"> <spring:message code="adjustments.label.myaccountnumber"/>: <span class="required-field">*</span></label>
						<div class="col-sm-5 row">
							<select class="form-control" id="toAccId" name="destinationAccountNumber">
								<option value="">.:<spring:message code="sub-merchant-create.label.select"/>:.</option>
								<c:forEach items="${accounts}" var="account">
									<option value="${account.label}">${account.label}</option>
								</c:forEach>
							</select>
							<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
								<span class="red-error" id="toAccIdEr">&nbsp;</span>
							</div>
						</div>
					</div>
                  <div class="col-sm-12 form-group">
                    <label class="col-sm-12 row"> <input type="radio" id="otherAccount" name="transfer-to-account" value="other-account"><spring:message code="adjustments.label.someoneelsesystemaccount"/> : </label>
                    <div id="other-bank-account" class="col-sm-12 row">
                      <div class="row"> 
                        <label class="col-sm-4 text-align-right">
                         <spring:message code="adjustments.label.recipientusername"/>: 
                        </label>
                        <div class="col-sm-4">
                          <input type="text" class="form-control" id="recipientsUserName">
                          <div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
								<span class="red-error" id="recipientsUserNameEr">&nbsp;</span>
						  </div>
                        </div>
                      </div>
                      
                      <div class="row"> 
                        <label class="col-sm-4 text-align-right">
                         <spring:message code="adjustments.label.recipientaccountnumber"/>:<span class="required-field">*</span>
                        </label>
                        <div class="col-sm-4">
                          <input type="text" class="form-control" id="recipientsAccountNo" name="destinationAccountNumber" onkeypress="return numbersonly(this,event)" onblur="validateRecipientAccNo(this.id);" />
                          <div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
								<span class="red-error" id="recipientsAccountNoEr">&nbsp;</span>
						  </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div><!-- .main-content-holder -->
            
            <div class="main-content-holder padding0">
              <header class="sub-panel-heading-container"> 
                <h6 class="sub-panel-heading"> <spring:message code="adjustments.label.transferdetails"/>:</h6>
              </header>
              <div class="row sub-panel-body">
                <div class="col-sm-12">
                  <div class="col-sm-6 form-group">
                    <label class="col-sm-7 row"><spring:message code="adjustments.label.amounttotransfer"/>: <span class="required-field">*</span></label>
                    <div class="col-sm-5 row">
                      <span class="input-currency-symbol">$</span>
                      <input type="text" class="form-control" name="transferAmount" id="transferAmount" onkeypress="return amountValidate(this,event)" onblur="validateTransferAmount(this.id)" />
                      <div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
						<span class="red-error" id="transferAmountEr">&nbsp;</span>
					 </div>
                    </div>
                  </div>
                  <div class="col-sm-12">
                    <label class="col-sm-3 paddingL0 form-group"><spring:message code="adjustments.label.description"/>: <span class="required-field">*</span></label>
                    <div class="col-sm-9 row">
                      <input type="text" class="form-control" name="description" id="description" onblur="this.value=this.value.trim();return validateTransferDescription(this.id)">
                      <div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
						<span class="red-error" id="descriptionEr">&nbsp;</span>
					</div>
                    </div>
                  </div>
                </div>
              </div>
            </div><!-- .main-content-holder -->
            
            <div class="col-xs-8 pull-right">
               	<input type="submit" class="form-control button pull-right" value='<spring:message code="common.label.submit"/>' onclick="return validateTransferDetails();"/>
               	<input type="button" class="form-control button pull-right" value='<spring:message code="common.label.cancel"/>' onclick="return goToDashboard();" />
            </div><!-- .main-content-holder -->
             </form>
          </div>
			</article>
			<!--Article Block End-->
			<jsp:include page="footer.jsp"></jsp:include>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/jquery.min.js"></script>
	<script src="../js/backbutton.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script> <script src="../js/utils.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/validation.js"></script>
	<script>
 	$(document).ready(function() {
 		$("#navListId11").addClass("active-background");
 		
 		setTimeout(function() {
	      $('.green-error').text('');
	      $('.red-error').text('');
	    }, 5000);
 		
		$('#fromAvailBal').text($('#fromAccId option:selected').attr('data-acc-balance') + ' USD');

			$('#other-bank-account').hide();

			$('#otherAccount').change(function() {
				$('#toAccId').val('');
				$('#toAccIdEr').text('');
				$('#recipientsAccountNoEr').text('');
				$('#toAccId').prop('disabled', true);
				$('#other-bank-account').show();
			});

			$('#ownAccount').change(function() {
				$('#toAccId').prop('disabled', false);
				$('#recipientsUserName').val('');
				$('#recipientsAccountNo').val('');
				$('#other-bank-account').hide();
			});

			$('#fromAccId').on('change', function() {
				$('#fromAvailBal').text($(this).find('option:selected').attr('data-acc-balance') + ' USD');
				$('#fromAccIdEr').text('');
			});
			
			$('#toAccId').on('change', function() {
				if($(this).val() == $('#fromAccId').val()) {
					$('#toAccIdEr').text(webMessages.pleaseEnterDifferentAccountNumber);
				} else {
					$('#toAccIdEr').text('');
				}
			});
		});
 	
		function validateTransferDetails() {
			var flag = true;
			
			if(!validateTransferDescription('description') | !validateTransferAmount('transferAmount')) {
				flag = false;
			}
			
			if(parseInt($('#fromAccId option:selected').attr('data-acc-balance')) == 0) {
				$('#fromAccIdEr').text(webMessages.selectTheAccountNoWhichHasAmount);
				flag = false;
			} 
			
			if ($('#ownAccount').is(':checked')) {
				if ($('#toAccId').val().length == 0) {
					$('#toAccIdEr').text(webMessages.pleaseSelectAccountNumber);
					flag = false;
				} else if ($('#toAccId').val() == $('#fromAccId').val()) {
					$('#toAccIdEr').text(webMessages.pleaseSelectDifferentAccountNumber);
					flag = false;
				} else {
					$('#toAccIdEr').text('');
				}
			} else if ($('#otherAccount').is(':checked')) {
				if($('#recipientsAccountNo').val().trim().length == 0) {
					$('#recipientsAccountNoEr').text(webMessages.pleaseEnterRecipientsAccountNumber);
				} else if ($('#recipientsAccountNo').val().trim() == $('#fromAccId').val()) {
					$('#recipientsAccountNoEr').text(webMessages.pleaseEnterDifferentAccountNumber);
					flag = false;
				} else {
					$('#toAccIdEr').text('');
					$('#recipientsAccountNoEr').text('');
				}
				
				if(!isValidRecipientAccNo) {
					flag = false;
					validateRecipientAccNo('recipientsAccountNo');
				}
			}
			
			return flag;
		}
		
		function validateTransferDescription(id) {
			if($('#'+id).val().trim().length == 0) {
				$('#'+id+'Er').text(webMessages.enterDescription);
				return false;
			} else {
				$('#'+id+'Er').text('');
			}
			return true;
		}
		
		function validateTransferAmount(id) {
			if($('#'+id).val().trim().length == 0) {
				$('#'+id+'Er').text(webMessages.enterTheAmountToTransfer);
				return false;
			} /* else if(parseInt($('#'+id).val()) == 0) {
				$('#'+id+'Er').text(webMessages.enterValidAmountToTransfer);
				return false; 
			} */else {
				$('#'+id+'Er').text('');
			}
			return true;
		}
		
		var isValidRecipientAccNo = false;
		
		function validateRecipientAccNo(id) {
			if($('#'+id).val().trim().length == 0) {
				$('#'+id+'Er').text(webMessages.pleaseEnterRecipientsAccountNumber);
			} else if ($('#'+id).val().trim() == $('#fromAccId').val()) {
				$('#'+id+'Er').text(webMessages.pleaseEnterDifferentAccountNumber);
			} else {
					
				$.ajax({
					type : "POST",
					url : "chatak-fetch-account-details",
					data: {accountNumber: $('#'+id).val().trim()},
					success : function(response) {
							$('#recipientsAccountNoEr').text('');
							var jsonObj =  JSON.parse(response);
						
							if(jsonObj.errorCode != '00') {
								$('#recipientsAccountNoEr').text(jsonObj.errorMessage);
							} else {
								isValidRecipientAccNo = true;
							}
					},
					error : function(e) {
					}
				});
			}
		}
	</script>
</body>
</html>