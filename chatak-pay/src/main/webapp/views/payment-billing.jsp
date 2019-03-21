<html><head><title>Payment Billing</title><script language="JavaScript" type="text/javascript">
var contextPath='<%=request.getContextPath()%>';
function validate_form()
{
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
</head><body><form name="profile" method="post" action="payment-processing" onsubmit="">
<table width="300px" height="100px" cellpadding="0" cellspacing="0">
 <tbody><tr>
  <td>Transaction Type</td>
  <td>
  <select name="transactionType">
  <option value="">Select</option>
  <option value="SALE" selected="selected">Sale Transaction</option>
  <option value="VOID">Void Transaction</option>
  <option value="CREDIT">Credit/Refunds Transaction</option>
  </select>
  </td>
 </tr>
 <tr>
 <td>Order ID</td>
 <td><input type="text" name="orderId" id="orderId" value="123456" ></td>
 </tr>
  <tr>
 <td>Total Amount(in Cents)</td>
 <td><input type="text" name="totalAmount" id="totalAmount" value="123456"></td>
 </tr>
  <tr>
 <td>Sub Merchant Amount(in Cents)</td>
 <td><input type="text" name="merchantAmount" id="merchantAmount" value="123"></td>
 </tr>
 <tr>
  <td>Card Type</td>
  <td>
  <select name="cardAssociation">
  <option value="">Select</option>
	<option value="IC">Chatak Prepaid Card</option>
  </select>
  </td>
 </tr>
 <tr><td>Description</td>
 <td><textarea cols="20" rows="3" name="description"  value="Testing"></textarea></td>
 </tr>
  <tr><td>BTA Name</td>
 <td><input type="text" name="billerName"  value="Test"></td> 
 </tr>
 <tr><td>BTA Email</td>
 <td><input type="text" name="billerEmail"  value="a@a.com"></td>
 </tr>
 <tr><td>BTA Address1</td>
 <td><input type="text" name="address"  value="Add"></td>
 </tr>
 <tr><td>BTA Address2</td>
 <td><input type="text" name="address2"  value="Add2"></td>
 </tr>
 <tr><td>BTA city</td>
 <td><input type="text" name="billerCity"  value="CA"></td>
 </tr>
  <tr><td>BTA State</td>
 <td><input type="text" name="billerState"   value="LA"></td>
 </tr>
  <tr><td>BTA Country</td>
 <td><input type="text" name="billerCountry"   value="USA"></td>
 </tr>
   <tr><td>BTA Zip</td>
 <td><input type="text" name="billerZip"  value="90035"></td>
 </tr>
 <tr><td>Return URL</td>
 <td><input type="text" name="returnURL"></td>
 </tr>
 <tr><td>Token</td>
 <td><input type="text" name="cot"></td>
 </tr>
  <tr><td>Process Type</td>
 <td><input type="text" name="type" value="C"></td>
 </tr>
  <tr><td>Card Token</td>
 <td><input type="text" name="ct"></td>
 </tr>
 <tr><td>Save Card Required</td>
 <td><input type="text" name="saveRequired" value="YES"></td>
 </tr>
 <tr><td colspan="2" align="center"><input type="submit" value="Submit" name="submit" onclick="if(!checkAndSubmit())return false;"></td></tr>
</tbody></table>
</form>
<script type="text/javascript" src="../../js/jquery.min.js"></script>
<script type="text/javascript" src="../../js/common-lib.js"></script>
<script src="../../js/backbutton.js"></script>	
<script>
var page = 1;
    function addEvent(elem, event, fn) {
        if (elem.addEventListener) {
            elem.addEventListener(event, fn, false);
        } else {
            elem.attachEvent("on" + event, function() {
                // set the this pointer same as addEventListener when fn is called
                return(fn.call(elem, window.event));
            });
        }
    }

    function validateNumber(event){
        if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 27 || event.keyCode == 13 ||
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
        }
        else {
            // Ensure that it is a number and stop the keypress
            if (event.shiftKey || (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 )) {
                event.preventDefault();
            }
        }
    }
	
	
addEvent(document.getElementById("orderId"),'keydown',validateNumber);	
addEvent(document.getElementById("totalAmount"),'keydown',validateNumber);	
addEvent(document.getElementById("merchantAmount"),'keydown',validateNumber);	
</script></body></html>