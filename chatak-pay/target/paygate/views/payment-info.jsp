<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html lang="en">
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Chatak Pay Online - Gateway</title>
    <!-- Bootstrap -->
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
    <link href="../../css/font-awesome.css" rel="stylesheet">
    <link href="../../css/style.css" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript">
    var page = 3;
    var contextPath='<%=request.getContextPath()%>';
    var year = parseInt('<%= java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)%>')-1;
	var requestId = '${CHATAK_PAY_INIT_REQUEST_ID}';
	var ky = '${CHATA_PAY_PASS_KEY}';
	var cR = '${CHATAK_PAY.returnURL}';
	var chCType = '${CHATAK_PAY.cardAssociation}';
	var cot = '${CHATAK_PAY.accessToken}';
	var tMode = '${PROCESS_MODE}';
    </script>
</head>
<body>
	<!--Body Wrapper block Start -->	
    <div class="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Header Block Start -->
			<header class="col-sm-12 all-page-header">
				<!--Header Logo Start -->				
				<div class="col-sm-4"> 
					<img src="../../images/chatak_logo.jpg" height="35px" alt="Logo"/>
				</div>
				<!--Header Logo End -->	
			</header>
			<!--Header Block End -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">					
					<!-- Breadcrumb start -->
					<div class="breadCrumb text-center">						
						<span>Payment Information</span>
					</div>
					<!-- Breadcrumb End -->
					<div id="progressingId" class="col-xs-12 content-wrapper" style="display: none;">
						<fieldset class="col-sm-12 padding0">
							<div class="process-icon"></div>
							<div id="paymentMsgDivId" style="text-align: center;">Do not use back button or refresh the page and ensure you are connected to the internet until the transaction confirmation is required.</div>
						</fieldset>
					</div>
					<!-- Content Block Start -->
					<div id="contentId" class="main-content-holder padding0">
						<div class="row margin0">
							<div class="col-sm-12 padding0">
								<!-- Page Form Start -->
									<div class="col-sm-12 padding0">
										<div class="row margin0">
											<div class="field-element-row margin0">
												<fieldset class="col-sm-12 padding0">
													<fieldset class="col-sm-3 side-nav-content">
														<nav class="side-nav">
															<ul>
															<c:if test="${PROCESS_MODE ne 'T'}">
																<li class="list list1 active" onclick="hideShow(1)">
																	<span class="fa fa-university"></span>Internet Banking
																	<span class="left-arrow arrow1"></span>
																</li>
																<li class="list list2" onclick="hideShow(2)">
																	<span class="fa fa-credit-card"></span>Credit/Debit Card
																	<span class="left-arrow arrow2"></span>
																</li>
																</c:if>
																<c:if test="${PROCESS_MODE eq 'T'}">
																	<li class="list list2 active" onclick="hideShow(2)">
																		<span class="fa fa-credit-card"></span>Saved Card
																		<span class="left-arrow arrow2" style="display: block;"></span>
																	</li>
																</c:if>
															</ul>
														</nav>
													</fieldset>
													<fieldset class="col-sm-6 middle-content">
														<fieldset class="col-sm-12 content content1 padding0">
														Internet Banking is not available at this moment!
														</fieldset>
														<fieldset class="col-sm-12 content content2 padding0">
														<form id="creditFormId" name="creditForm">
															<c:if test="${PROCESS_MODE eq 'T'}">
																<fieldset class="col-sm-12 padding0">
																<fieldset class="col-sm-12"> 
																	<label>Your Saved Card XXXX XXXX XXXX ${CARD } &nbsp; &nbsp;</label><div class="header-cards-info"></div>
																</fieldset>
																<fieldset class="col-sm-4"> 
																	<label>CVV / CVC<span class="required-field">*</span></label>
																	<input maxlength="4" name="codecredit" id="codecredit" type="text" class="form-control" placeholder="CVV/CVS Number"/>
																	<div class="discriptionErrorMsg">
																		<span class="red-error">&nbsp;</span>
																	</div> 
																</fieldset>
																<div class="col-sm-12">
																	<div class="col-sm-6"><input id="btnCreditCancel" type="button" class="form-control button pay-button" value="Cancel">	</div>
																	<div class="col-sm-6">
																	<input id="btnCredit" type="button" class="form-control button pay-button" value="PAY NOW"></div>
																</div>
																<!--Panel Action Button End -->
															</fieldset> 
															</c:if>
															<c:if test="${PROCESS_MODE ne 'T'}"> 
															<fieldset class="col-sm-12 padding0">
																<fieldset class="col-sm-12 text-center">
																	<div class="header-cards-info-all"></div>
																</fieldset>
																<fieldset class="col-sm-12"> 
																	<label>Card Info<span class="required-field">*</span></label>
																	<div class="input-group">
																		<input name="numbercredit" id="numbercredit" type="text" class="form-control card-number-info" value="" placeholder="Enter card number">
																		<span class="input-group-btn">
																			<span class="btn btn-primary btn-file">
																				<div class="cards-info"></div>
																			</span>
																		</span>
																	</div>
																	<div class="discriptionErrorMsg">
																		<span class="red-error">&nbsp;</span>
																	</div> 
																</fieldset>
																<fieldset class="col-sm-4"> 
																	<label>Expiration Date<span class="required-field">*</span></label>
																	<select name="monthcredit" id="monthcredit" class="form-control">
																		<option value="0">Month</option>
																		<option value="01">01 (Jan)</option>
																		<option value="02">02 (Feb)</option>
																		<option value="03">03 (Mar)</option>
																		<option value="04">04 (Apr)</option>
																		<option value="05">05 (May)</option>
																		<option value="06">06 (Jun)</option>
																		<option value="07">07 (Jul)</option>
																		<option value="08">08 (Aug)</option>
																		<option value="09">09 (Sep)</option>
																		<option value="10">10 (Oct)</option>
																		<option value="11">11 (Nov)</option>
																		<option value="12">12 (Dec)</option>
																	</select>
																	<div class="discriptionErrorMsg">
																		<span class="red-error">&nbsp;</span>
																	</div> 
																</fieldset>
																<fieldset class="col-sm-4"> 
																	<label>&nbsp;</label>
																	<select name="yearcredit" id="yearcredit" class="form-control">
																		<option value="0">Year</option>
																	</select>
																	<div class="discriptionErrorMsg">
																		<span class="red-error">&nbsp;</span>
																	</div> 
																</fieldset>
																<fieldset class="col-sm-4"> 
																	<label>CVV / CVC<span class="required-field">*</span></label>
																	<input maxlength="3" name="codecredit" id="codecredit" type="text" class="form-control" placeholder="CVV/CVS Number"/>
																	<div class="discriptionErrorMsg">
																		<span class="red-error">&nbsp;</span>
																	</div> 
																</fieldset>
																<fieldset class="col-sm-12"> 
																	<label>Card Holder Name<span class="required-field">*</span></label>
																	<input name="namecredit" id="namecredit" type="text" class="form-control" placeholder="Enter card holder name"/>
																	<div class="discriptionErrorMsg">
																		<span class="red-error">&nbsp;</span>
																	</div> 
																</fieldset>
															</fieldset>
															<c:if test="${saveRequired eq 'YES'}"> 
																<div class="col-sm-12">
																	<input type="checkbox" id="saveCardCheckId" name="saveCardCheckId" > <label for="saveCardCheckId">Save this card for faster checkout</label>
																</div>
															</c:if>
															<!--Panel Action Button Start -->
															<div class="col-sm-12">
																<div class="col-sm-6"><input id="btnCreditCancel" type="button" class="form-control button pay-button" value="Cancel">	</div>
																<div class="col-sm-6">
																<input id="btnCredit" type="button" class="form-control button pay-button" value="PAY NOW"></div>
															</div>
															<!--Panel Action Button End -->
															</c:if>
															</form>
														</fieldset>
														<fieldset class="col-sm-12 content content3 padding0" style="display:none;">
														<form id="debitFormId" name="debitForm">
															<c:if test="${PROCESS_MODE eq 'T'}">
															<fieldset class="col-sm-12 padding0">
																<fieldset class="col-sm-12"> 
																	<label>Your Saved Card XXXX XXXX XXXX ${CARD } &nbsp; &nbsp;</label><div class="header-cards-info"></div>
																</fieldset>
																<fieldset class="col-sm-4"> 
																	<label>CVV / CVC<span class="required-field">*</span></label>
																	<input maxlength="4" name="codedebit" id="codedebit" type="text" class="form-control" placeholder="CVV/CVS Number"/>
																	<div class="discriptionErrorMsg">
																		<span class="red-error">&nbsp;</span>
																	</div> 
																</fieldset>
																<!--Panel Action Button Start -->
																<div class="col-sm-12">																
																	<div class="col-sm-6"><input id="btnDebitCancel" type="button" class="form-control button pay-button" value="Cancel">	</div>
																	<div class="col-sm-6"><input id="btnDebit" type="button" class="form-control button pay-button" value="PAY NOW"></div>
																</div>
																<!--Panel Action Button End -->
															</fieldset> 
															</c:if>
														<c:if test="${PROCESS_MODE ne 'T'}"> 
															<fieldset class="col-sm-12 padding0">
																<fieldset class="col-sm-12 text-center">
																	<div class="header-cards-info-all"></div>
																</fieldset>
																<fieldset class="col-sm-12"> 
																	<label>Card Info<span class="required-field">*</span></label>
																	<div class="input-group">
																		<input name="numberdebit" id="numberdebit" type="text" class="form-control card-number-info" value="" placeholder="Enter card number">
																		<span class="input-group-btn">
																			<span class="btn btn-primary btn-file">
																				<div class="cards-info"></div>
																			</span>
																		</span>
																	</div>
																	<div class="discriptionErrorMsg">
																		<span class="red-error">&nbsp;</span>
																	</div> 
																</fieldset>
																<fieldset class="col-sm-4"> 
																	<label>Expiration Date<span class="required-field">*</span></label>
																	<select name="monthdebit" id="monthdebit" class="form-control">
																		<option value="0">Month</option>
																		<option value="01">01 (Jan)</option>
																		<option value="02">02 (Feb)</option>
																		<option value="03">03 (Mar)</option>
																		<option value="04">04 (Apr)</option>
																		<option value="05">05 (May)</option>
																		<option value="06">06 (Jun)</option>
																		<option value="07">07 (Jul)</option>
																		<option value="08">08 (Aug)</option>
																		<option value="09">09 (Sep)</option>
																		<option value="10">10 (Oct)</option>
																		<option value="11">11 (Nov)</option>
																		<option value="12">12 (Dec)</option>
																	</select>
																	<div class="discriptionErrorMsg">
																		<span class="red-error">&nbsp;</span>
																	</div> 
																</fieldset>
																<fieldset class="col-sm-4"> 
																	<label>&nbsp;</label>
																	<select name="yeardebit" id="yeardebit" class="form-control">
																		<option value="0">Year</option>
																	</select>
																	<div class="discriptionErrorMsg">
																		<span class="red-error">&nbsp;</span>
																	</div> 
																</fieldset>
																<fieldset class="col-sm-4"> 
																	<label>CVV / CVC<span class="required-field">*</span></label>
																	<input maxlength="3" name="codedebit" id="codedebit" type="text" class="form-control" placeholder="CVV/CVS Number"/>
																	<div class="discriptionErrorMsg">
																		<span class="red-error">&nbsp;</span>
																	</div> 
																</fieldset>
																<fieldset class="col-sm-12"> 
																	<label>Card Holder Name<span class="required-field">*</span></label>
																	<input name="namedebit" id="namedebit" type="text" class="form-control" placeholder="Enter card holder name"/>
																	<div class="discriptionErrorMsg">
																		<span class="red-error">&nbsp;</span>
																	</div> 
																</fieldset>
															</fieldset>
															<c:if test="${saveRequired eq 'YES'}"> 
																<div class="col-sm-12">
																	<input type="checkbox" id="saveCardCheckId" name="saveCardCheckId" > <label for="saveCardCheckId">Save this card for faster checkout</label>
																</div>
															</c:if>
															<!--Panel Action Button Start -->
															<div class="col-sm-12">																
																<div class="col-sm-6"><input id="btnDebitCancel" type="button" class="form-control button pay-button" value="Cancel">	</div>
																<div class="col-sm-6"><input id="btnDebit" type="button" class="form-control button pay-button" value="PAY NOW"></div>
															</div>
															<!--Panel Action Button End -->
															</c:if>
															</form>
														</fieldset>
													</fieldset>
													<fieldset class="col-sm-3 right-content">
														<table class="card-details-table">
															<tr><td colspan="2">Payment Description</td></tr>
															<tr><td>Order ID</td><td>${CHATAK_PAY.orderId}</td></tr>
															<tr><td>Name</td><td>${CHATAK_PAY.billerName}</td></tr>
															<tr><td>Email</td><td>${CHATAK_PAY.billerEmail}</td></tr>
															<tr class="total-amt-row"><td>Total</td><td><span class="fa fa-${CHATAK_PAY.currencyCode}"></span> ${CHATAK_PAY.formatedTotalAmt }</td></tr>
														</table>
													</fieldset>
												</fieldset>
											</div>
										</div>
									</div>
								<!-- Page Form End -->	
							</div>
						</div>
					</div>
					<!-- Content Block End -->						
				</div>
			</article>
			<!--Article Block End-->
			<footer class="footer">
				<p class="footer-txt">Copyright &copy; 2014-2016. Chatak All Rights Reserved.</p>
			</footer>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->	

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="../../js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../../js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../../js/jquery.jcryption.js"></script>
    <script type="text/javascript" src="../../js/common-lib.js"></script>
	<script type="text/javascript" src="../../js/chatak-pay.js"></script>
	<script src="../../js/backbutton.js"></script>	
	<script type="text/javascript">
		$("#btnCredit").bind("click", function(){
			<c:if test="${PROCESS_MODE eq 'T'}"> 
				chataPay.processPaymentToken('${CHATA_PAY_MERCHANT}', 'credit', '${CHATAK_PAY_INIT_REQUEST_ID}', '${CARD_TYPE}');
			</c:if>
			<c:if test="${PROCESS_MODE ne 'T'}"> 
				chataPay.processPayment('${CHATA_PAY_MERCHANT}', 'credit', '${CHATAK_PAY_INIT_REQUEST_ID}');
			</c:if>
		});
		$("#btnDebit").bind("click", function(){
			<c:if test="${PROCESS_MODE eq 'T'}"> 
			chataPay.processPaymentToken('${CHATA_PAY_MERCHANT}', 'debit', '${CHATAK_PAY_INIT_REQUEST_ID}', '${CARD_TYPE}');
			</c:if>
			<c:if test="${PROCESS_MODE ne 'T'}"> 
			chataPay.processPayment('${CHATA_PAY_MERCHANT}', 'debit', '${CHATAK_PAY_INIT_REQUEST_ID}');
			</c:if>
		});
		$("#btnCreditCancel").bind("click", function(){
			cancelPayment();
		});
		$("#btnDebitCancel").bind("click", function(){
			cancelPayment();
		});
		
		if ('${CARD_TYPE}' === "amex") {
			$('input[id$=codecredit]').attr('maxlength', '4');
			$('input[id$=codedebit]').attr('maxlength', '4');
		} else {
			$('input[id$=codecredit]').attr('maxlength', '3');
			$('input[id$=codedebit]').attr('maxlength', '3');
		}
		<c:if test="${PROCESS_MODE eq 'T'}"> 
			if('${CARD_TYPE}' == "VI") {
				$(".header-cards-info").css({"background-position": "0 0"});
			}else if('${CARD_TYPE}' == "MC") {
				$(".header-cards-info").css({"background-position": "-58px 0"});
			}else if('${CARD_TYPE}' == "MC") {
				$(".header-cards-info").css({"background-position": "-58px 0"});
			}else if('${CARD_TYPE}' == "AX") {
				$(".header-cards-info").css({"background-position": "-169px 0"});
			}else if('${CARD_TYPE}' == "DI") {
				$(".header-cards-info").css({"background-position": "-284px 0"});
			}else if('${CARD_TYPE}' == "DC") {
				$(".header-cards-info").css({"background-position": "-224px 0"});
			}else if('${CARD_TYPE}' == "JC") {
				$(".header-cards-info").css({"background-position": "-338px 0"});
			}
		</c:if>
		
		function cancelPayment() {
			var responseJson = {errorCode:'TXN_0995', errorMessage:'Payment is cancelled by user'};
			window.location.href = getFinalReturnURL(responseJson);
		}
		
	</script>
  </body>  
</html>