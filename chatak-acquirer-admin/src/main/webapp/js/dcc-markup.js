function getCurrencyCode(merchantCode) {
	$('#columnName').empty();
	
	$.ajax({
		type : "POST",
		url : "getCurrencyCodes?merchantCode=" + merchantCode,
		async : false,
		success : function(response) {
			var data = JSON.parse(response);
			
			if (data != '') {
				var currencyCode = data.currencyCodes;
		        var i,tr;
		        var header = "<tr><th>Base Currency</th><th>Transaction Currency</th><th>MarkUp Fee</th></tr>";
				 $('#sample').html('');
					for (i = 0; i < currencyCode.length; i++) {

						var newrow = newrow+ "<tr class='testing'><td>" +data.baseCurrency + "</td><td>" + currencyCode[i]
									+ "</td><td><input type='text' id='markupId' class='form-control' required'></td></tr>"
					} 
				 $("#sample").append(header+newrow);
			}
			else
				{
				setDiv("errorData","Selected Merchant Does not support dcc enabled");
				}
				
		},
		error : function(e) {
		}
	});
}

var tbl=[];
var index = 0;
$("#createmarkupFee").click(function() {
	var merchantCode = $('#dccName').val();
	var tbl = $('table#sample tr:has(td)').map(function() {
	    var $td =  $('td', this)
	        return {
	    			baseCurrency : $td.eq(0).text(),
	    			transactionCurrency : $td.eq(1).text(),
	    			markUpFee: $td.eq(2).find("input").val()              
	               }
	}).get();

   console.log(tbl)
	$.ajax({
		type : "POST",
		url : "processMarkupFeeCreate?merchantCode=" + merchantCode + '&arrayData=' + JSON.stringify(tbl),
		async : true,
		success : function(response) {
			var obj = JSON.parse(response);	
			if(obj.errorCode ==="00"){
				//messageType="Markup Fee created successfully";
				/*setDiv("sucessData",obj.errorMessage)
				window.location.href = "dcc-markup-search" ;*/
				 window.location.href = "dcc-markup-search?succMsg="+obj.errorMessage+"";
			}
			else{
				setDiv("errorData",obj.errorMessage);
			}

		}
	});
});
	

function resetMarkupFeeSearch() {
	window.location.href = 'dcc-markup-search';
}

function editdccMarkup(merchantCode,merchantName){
	get('getMerchantCode').value = merchantCode;
	get('getMerchantName').value = merchantName;
	document.forms["editDccMarkupForm"].submit();
}

function confirmDeleteDccMarkup(merchantCode) {
	var r = confirm("Press Ok to confirm deletion");
	if (r == true) {
		get('merchantCodeId').value = merchantCode;
		document.forms["deleteDccMarkupForm"].submit();

	} else {
		return;
	}
}
var tbl=[];
var index = 0;
function updateDccMarkUp() {
	var merchantCode = $('#merchantCode').val();
	var tbl = $('table#dccEditTable tr:has(td)').map(function() {
	    var $td =  $('td', this)
	        return {
	    			baseCurrency : $td.eq(0).text(),
	    			transactionCurrency : $td.eq(1).text(),
	    			markUpFee: $td.eq(2).find("input").val()              
	               }
	}).get();

   console.log(tbl)
	$.ajax({
		type : "POST",
		url : "processMarkupFeeUpdate?merchantCode=" + merchantCode + '&arrayData=' + JSON.stringify(tbl),
		async : true,
		success : function(response) {
			var obj = JSON.parse(response);	
			if(obj.errorCode ==="00"){
				/*window.location.href = "dcc-markup-search" ;
				setDiv("sucessData",obj.errorMessage)*/
				window.location.href = "dcc-markup-search?succMsg="+obj.errorMessage+"";
			}
			else{
				setDiv("errorData",obj.errorMessage);
			}

		}
	});
}

/*function validateMarkup() {
	var reg =/ ^[1-9]\d*(\.\d+)?$/;
	var markupId = get('markupId').value;
	
	if(isEmpty(markupId)){
		setError(get('markupId'), 'Please enter Markup Fee');
		return false;
	}
	else if(!reg.test(markupId)){
		setError(get('markupId'), 'Invalid Markup Fee');
		return false;
	}
	else {
		setError(get('markupId'), '');
	}
	
}*/