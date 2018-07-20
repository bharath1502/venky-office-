<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
<script language="JavaScript" type="text/javascript">
var contextPath='<%=request.getContextPath()%>';
	function validate_form() {
		if (document.profile.transactionType.value == "") {
			alert("Please select transaction type");
			document.profile.transactionType.focus();
			return false;
		}
		if (document.profile.orderId.value == "") {
			alert("Please enter valid order ID");
			document.profile.orderId.focus();
			return false;
		}
		if (document.profile.totalAmount.value == "") {
			alert("Please enter valid Amount");
			document.profile.totalAmount.focus();
			return false;
		}
		if (document.profile.merchantAmount.value == "") {
			alert("Please enter valid Amount");
			document.profile.merchantAmount.focus();
			return false;
		}
		if (document.profile.cardAssociation.value == "") {
			alert("Please provide card type");
			document.profile.cardAssociation.focus();
			return false;
		}
		if (document.profile.cardAssociation.value == "") {
			alert("Please provide card type");
			document.profile.cardAssociation.focus();
			return false;
		}
		if (document.profile.description.value == "") {
			alert("Please provide Description");
			document.profile.description.focus();
			return false;
		}
		if (document.profile.billerName.value == "") {
			alert("Please provide Bill to Address Name");
			document.profile.billerName.focus();
			return false;
		}
		if (document.profile.bta_address1.value == "") {
			alert("Please provide Bill to Address1");
			document.profile.bta_address1.focus();
			return false;
		}
		if (document.profile.bta_city.value == "") {
			alert("Please provide Bill City");
			document.profile.bta_city.focus();
			return false;
		}
		if (document.profile.bta_state.value == "") {
			alert("Please provide Bill State");
			document.profile.bta_state.focus();
			return false;
		}
		if (document.profile.bta_country.value == "") {
			alert("Please provide Bill Country");
			document.profile.bta_country.focus();
			return false;
		}
		if (document.profile.bta_zip.value == "") {
			alert("Please provide Bill Zip");
			document.profile.bta_zip.focus();
			return false;
		}

		return true;
	}

	function checkAndSubmit() {
		if (!validate_form())
			return false;
		else {
			return true;
		}
	}
</script>
<script>
	var page = 1;
	function addEvent(elem, event, fn) {
		if (elem.addEventListener) {
			elem.addEventListener(event, fn, false);
		} else {
			elem.attachEvent("on" + event, function() {
				// set the this pointer same as addEventListener when fn is called
				return (fn.call(elem, window.event));
			});
		}
	}

	function validateNumber(event) {
		if (event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9
				|| event.keyCode == 27 || event.keyCode == 13 ||
				// Allow: Ctrl+A
				(event.keyCode == 65 && event.ctrlKey === true) ||
				// Allow: home, end, left, right
				(event.keyCode >= 35 && event.keyCode <= 39) ||
				// Allow: Ctrl+C
				(event.keyCode == 67 && event.ctrlKey === true) ||
				// Allow: Ctrl+X
				(event.keyCode == 88 && event.ctrlKey === true) ||
				// Allow: Ctrl+V
				(event.keyCode == 86 && event.ctrlKey === true)) {
			// let it happen, don't do anything
			return;
		} else {
			// Ensure that it is a number and stop the keypress
			if (event.shiftKey
					|| ((event.keyCode < 48) || (event.keyCode > 57))
					&& ((event.keyCode < 96) || (event.keyCode > 105))) {
				event.preventDefault();
			}
		}
	}

	addEvent(document.getElementById("orderId"), 'keydown', validateNumber);
	addEvent(document.getElementById("totalAmount"), 'keydown', validateNumber);
	addEvent(document.getElementById("merchantAmount"), 'keydown',
			validateNumber);
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
					<img src="../../images/chatak_logo.jpg" height="35px" alt="Logo" />
				</div>
				<!--Header Logo End -->
			</header>
			<!--Header Block End -->
			<!--Navigation Block Start -> 
			<nav class="col-sm-12 nav-bar" id="main-navigation"></nav>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb text-center">
						<span>Payment Billing</span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Content Block Start -->
					<div id="contentId" class="main-content-holder padding0">
						<div class="row margin0">
							<div class="col-sm-12 padding0">
								<!-- Page Form Start -->
								<div class="col-sm-12 padding0">
									<div class="row margin0">
										<div class="field-element-row margin0">
											<fieldset class="col-sm-12 padding0">
												<fieldset class="col-sm-12 right-content">

													<form name="profile" method="post"
														action="payment-processing" onsubmit="">
														<table class="card-details-table" width="300px"
															height="100px" cellpadding="0" cellspacing="0">
															<tbody>
																<tr>
																	<td>Transaction Type</td>
																	<td><select name="transactionType">
																			<option value="">Select</option>
																			<option value="SALE" selected="selected">Sale
																				Transaction</option>
																			<option value="VOID">Void Transaction</option>
																			<option value="CREDIT">Credit/Refunds
																				Transaction</option>
																	</select></td>
																</tr>
																<tr>
																	<td>Order ID</td>
																	<td><input type="text" name="orderId" id="orderId"
																		value="123456"></td>
																</tr>
																<tr>
																	<td>Total Amount(in Cents)</td>
																	<td><input type="text" name="totalAmount"
																		id="totalAmount" value="123456"></td>
																</tr>
																<tr>
																	<td>Sub Merchant Amount(in Cents)</td>
																	<td><input type="text" name="merchantAmount"
																		id="merchantAmount" value="123"></td>
																</tr>
																<tr>
																	<td>Card Type</td>
																	<td><select name="cardAssociation">
																			<option value="">Select</option>
																			<option value="IC">Chatak Prepaid Card</option>
																	</select></td>
																</tr>
																<tr>
																	<td>Description</td>
																	<td><textarea cols="20" rows="3"
																			name="description" value="Testing"></textarea></td>
																</tr>
																<tr>
																	<td>BTA Name</td>
																	<td><input type="text" name="billerName"
																		value="Test"></td>
																</tr>
																<tr>
																	<td>BTA Email</td>
																	<td><input type="text" name="billerEmail"
																		value="a@a.com"></td>
																</tr>
																<tr>
																	<td>BTA Address1</td>
																	<td><input type="text" name="address" value="Add"></td>
																</tr>
																<tr>
																	<td>BTA Address2</td>
																	<td><input type="text" name="address2"
																		value="Add2"></td>
																</tr>
																<tr>
																	<td>BTA city</td>
																	<td><input type="text" name="billerCity"
																		value="CA"></td>
																</tr>
																<tr>
																	<td>BTA State</td>
																	<td><input type="text" name="billerState"
																		value="LA"></td>
																</tr>
																<tr>
																	<td>BTA Country</td>
																	<td><input type="text" name="billerCountry"
																		value="USA"></td>
																</tr>
																<tr>
																	<td>BTA Zip</td>
																	<td><input type="text" name="billerZip"
																		value="90035"></td>
																</tr>
																<tr>
																	<td>Return URL</td>
																	<td><input type="text" name="returnURL"></td>
																</tr>
																<tr>
																	<td>Token</td>
																	<td><input type="text" name="cot"></td>
																</tr>
																<tr>
																	<td>Process Type</td>
																	<td><input type="text" name="type" value="C"></td>
																</tr>
																<tr>
																	<td>Card Token</td>
																	<td><input type="text" name="ct"></td>
																</tr>
																<tr>
																	<td>Save Card Required</td>
																	<td><input type="text" name="saveRequired"
																		value="YES"></td>
																</tr>
																<tr>
																	<td colspan="2" align="center"><input
																		type="submit" value="Submit" name="submit"
																		onclick="if(!checkAndSubmit())return false;"></td>
																</tr>
															</tbody>
														</table>
													</form>

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
				<p class="footer-txt">Copyright &copy; 2014-2016. Chatak All
					Rights Reserved.</p>
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
	
</body>
</html>
