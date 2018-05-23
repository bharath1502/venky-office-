<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.chatak.pg.util.Constants"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Chatak Acquirer Admin</title>
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Navigation Block Start -->
			<%-- <jsp:include page="header.jsp"></jsp:include> --%>
			<%@include file="navigation-panel.jsp"%>
			<!--Navigation Block End -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><spring:message code="show-account-transfer.label.adjustments"/></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><spring:message code="show-account-transfer.label.accounttransfer"/></span>
					</div>
					<div class="tab-header-container active-background" style="margin-left: 44px;">
						<a href="#"><spring:message code="show-account-transfer.label.accounttransfer"/></a>
					</div>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-sm-12">
									<div class="discriptionErrorMsg red-error" id="errorMsgDiv">
										<span class="red-error">&nbsp;${error}</span> 
										<span class="green-error">&nbsp;${sucess}</span>
									</div>
								<form:form action="show-account-transfer" method="post">
									<div class="col-sm-12">
										<div class="breadCrumb">
											<span class="breadcrumb-text"><spring:message code="show-account-transfer.label.transferfrom"/></span>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="col-sm-4">
											<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-account-transfer.label.accountnumber"/><span class="required-field">*</span></label>
										</div>
										<div class="col-sm-4">
											<input type="text" class="form-control" name="sourceAccountNumber" maxlength = "20" id="sourceAccountNumber" data-entity="source" onblur="this.value=this.value.trim();populateAccountDetails(this);" onkeypress="return amountValidate(this,event)"/>
											<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
												<span class="red-error" id="sourceAccountNumberEr">&nbsp;</span>
											</div>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="col-sm-4">
											<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-account-transfer.label.merchantname"/></label>
										</div>
										<div class="col-sm-4">
											<input type="text" class="form-control" name="sourceMerchantName" id="sourceMerchantName" readonly="readonly"/>
											<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
												<span class="red-error" id="sourceMerchantNameEr">&nbsp;</span>
											</div>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="col-sm-4">
											<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-account-transfer.label.availablebalance"/></label>
										</div>
										<div class="col-sm-4">
											<input type="text" class="form-control alignright" name="" id="sourceAvailableBalance" readonly="readonly"/>
											<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
												<span class="red-error" id="sourceAvailableBalanceEr">&nbsp;</span>
											</div>
										</div>
									</div>
											
									<div class="col-sm-12">
										<div class="breadCrumb">
											<span class="breadcrumb-text"><spring:message code="show-account-transfer.label.transferto"/></span>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="col-sm-4">
											<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-account-transfer.label.accountnumber"/><span class="required-field">*</span></label>
										</div>
										<div class="col-sm-4">
											<input type="text" class="form-control" name="destinationAccountNumber" id="destinationAccountNumber"  maxlength = "20"  data-entity="destination" onblur="this.value=this.value.trim();populateAccountDetails(this);" onkeypress="return amountValidate(this,event)"/>
											<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
												<span class="red-error" id="destinationAccountNumberEr">&nbsp;</span>
											</div>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="col-sm-4">
											<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-account-transfer.label.merchantname"/></label>
										</div>
										<div class="col-sm-4">
											<input type="text" class="form-control" name="destinationMerchantName" id="destinationMerchantName" readonly="readonly"/>
											<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
												<span class="red-error" id="destinationMerchantNameEr">&nbsp;</span>
											</div>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="col-sm-4">
											<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-account-transfer.label.availablebalance"/></label>
										</div>
										<div class="col-sm-4">
											<input type="text" class="form-control alignright" name="" id="destinationAvailableBalance" readonly="readonly"/>
											<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
												<span class="red-error" id="destinationAvailableBalanceEr">&nbsp;</span>
											</div>
										</div>
									</div>

									<div class="col-sm-12">
										<div class="breadCrumb">
											<span class="breadcrumb-text"><spring:message code="show-account-transfer.label.transferdetails"/></span>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="col-sm-4">
											<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-account-transfer.label.amounttotransfer"/><span class="required-field">*</span></label>
										</div>
										<div class="col-sm-4">
											<input type="text" class="form-control alignright" id="tempTransferAmount" onkeypress="return amountValidate(this,event)" onblur="this.value=this.value.trim();validateTransferAmount(this.id)"/>
											<input type="hidden" name="transferAmount" id="transferAmount" />
											<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
												<span class="red-error" id="tempTransferAmountEr">&nbsp;</span>
											</div>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="col-sm-4">
											<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-account-transfer.label.description"/><span class="required-field">*</span></label>
										</div>
										<div class="col-sm-8">
											<input type="text" class="form-control" name="description" id="description" onblur="this.value=this.value.trim();return validateTransferDescription(this.id)"/>
											<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
												<span class="red-error" id="descriptionEr">&nbsp;</span>
											</div>
										</div>
									</div>
									<%-- <div class="col-sm-12">
										<div class="col-sm-4">
											<label data-toggle="tooltip" data-placement="top" title=""><spring:message code="show-account-transfer.label.closefromaccount"/></label>
										</div>
										<div class="col-sm-1">
											<input type="checkbox" name="isAccountClose" id="isAccountClose" />
											<input type="hidden" name="accountCloseFlag" id="accountCloseFlag" />
											
											<div class="discriptionErrorMsg" data-toggle="tooltip" data-placement="top" title="">
												<span class="red-error" id="isAccountCloseEr">&nbsp;</span>
											</div>
										</div>
									</div> --%>
									<div class="col-sm-12 button-content">
										<fieldset class="col-sm-7 pull-right">
											<input type="submit" class="form-control button pull-right pos-next" onclick="return validateTransferDetails();" value='<spring:message code="show-account-transfer.label.submitbutton"/>'/> 
											<input type="button" class="form-control button pull-right marginL10 pos-prev close-fetch-details" value='<spring:message code="show-account-transfer.label.cancelbutton"/>' onclick="goToDashboard();">
										</fieldset>
									</div>
								</form:form>
							</div>
						</div>
					</div>
					<!-- Content Block End -->
				</div>
			</div>
			</article>
			<!--Article Block End-->
			<footer class="footer">
				<jsp:include page="footer.jsp"></jsp:include>
			</footer>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../js/jquery.min.js"></script>
	<script src="../js/backbutton.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
<script src="../js/utils.js"></script>
	<script src="../js/common-lib.js"></script>
	<script src="../js/messages.js"></script>
	<script src="../js/validation.js"></script>
	<script src="../js/accounts.js"></script>
	<script src="../js/transfers.js"></script>
	<script type="text/javascript" src="../js/browser-close.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#navListId5").addClass("active-background");
			
			$('#isAccountClose').on('click', function() {
				if($(this).is(':checked')) {
					var fromAccountBalance = $('#sourceAvailableBalance').val();
					if(fromAccountBalance > 0) {
						$('#tempTransferAmount').val(fromAccountBalance);
						$('#tempTransferAmount').attr('disabled',true);
						$('#isAccountCloseEr').text('');
						$('#tempTransferAmountEr').text('');
					} 
				} else {
					$('#tempTransferAmount').val('');
					$('#tempTransferAmount').attr('disabled',false);
				}
				$(this).val($(this).is(':checked'));
			});
		});
	</script>
</body>
</html>