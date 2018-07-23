/**
 * 
 */
function getTransferList(status,transferMode) {
	get('statusId').value = status;
	get('transferMode').value=transferMode;
	document.forms["transferListRequest"].submit();
}

function back2Home(){
	window.location.href = "home";
}

function processAction(pgTransfersId,action){
	get('action').value = action;
	get('pgTransfersId').value=pgTransfersId;
	document.forms["fundTransferActionModel"].submit();
}

var sourceAccountNo = "";
var destAccountNo = "";
function populateAccountDetails($this) {
	var reqObj = $this;
	
	
	if($(reqObj).attr('data-entity') == 'source') {
		sourceAccountNo = $(reqObj).val().trim();
		if(sourceAccountNo.length < 10) {
			$('#sourceMerchantName').val('');
			$('#sourceAvailableBalance').val('');
			$('#sourceAccountNumberEr').text(webMessages.entercorrectaccountnumber);
			loadMsgTitleText();
			return;
		}
	} else if($(reqObj).attr('data-entity') == 'destination') {
		destAccountNo = $(reqObj).val().trim();
		if(destAccountNo.length < 10) {
			$('#destinationMerchantName').val('');
			$('#destinationAvailableBalance').val('');
			$('#destinationAccountNumberEr').text(webMessages.entercorrectaccountnumber);
			loadMsgTitleText();
			return;
		}
	}
	
	if(sourceAccountNo == destAccountNo) {
		if($(reqObj).attr('data-entity') == 'source') {
			$('#sourceAccountNumberEr').text(webMessages.enterdifferentaccountnumber);
			loadMsgTitleText();
		} else if($(reqObj).attr('data-entity') == 'destination') {
			$('#destinationAccountNumberEr').text(webMessages.enterdifferentaccountnumber);
			loadMsgTitleText();
		}
	} else {
		$('#sourceAccountNumberEr,#destinationAccountNumberEr').text('');
		var csrfToken = $("input[name=CSRFToken]").val();
		$.ajax({
			type : "POST",
			url : "chatak-fetch-account-details",
			data: {accountNumber: $(reqObj).val(), CSRFToken: csrfToken},
			success : function(response) {
					
					var jsonObj =  JSON.parse(response);
					
					if(jsonObj.errorCode == '00') {
						if($(reqObj).attr('data-entity') == 'source') {
							$('#sourceMerchantName').val(jsonObj.merchantName);
							$('#sourceAvailableBalance').val((jsonObj.availableBalance/100).toString().indexOf(".") > 0 ? (jsonObj.availableBalance/100).toString().split(".")[1].length == 1 ? (jsonObj.availableBalance/100) + "0" : jsonObj.availableBalance/100 : (jsonObj.availableBalance/100) + ".00");
						} else if($(reqObj).attr('data-entity') == 'destination') {
							$('#destinationMerchantName').val(jsonObj.merchantName);
							$('#destinationAvailableBalance').val((jsonObj.availableBalance/100).toString().indexOf(".") > 0 ? (jsonObj.availableBalance/100).toString().split(".")[1].length == 1 ? (jsonObj.availableBalance/100) + "0" : jsonObj.availableBalance/100 : (jsonObj.availableBalance/100) + ".00");
						}
					} else {
						if($(reqObj).attr('data-entity') == 'source') {
							$('#sourceMerchantName').val('');
							$('#sourceAvailableBalance').val('');
							$('#sourceAccountNumberEr').text(webMessages.entervalidaccountnumber);
							loadMsgTitleText();
						} else if($(reqObj).attr('data-entity') == 'destination') {
							$('#destinationMerchantName').val('');
							$('#destinationAvailableBalance').val('');
							$('#destinationAccountNumberEr').text(webMessages.entervalidaccountnumber);
							loadMsgTitleText();
						}
					}
			},
			error : function(e) {
			}
		});
	}
	
}

function validateTransferAmount(id) {
	if($('#'+id).val().trim().length == 0) {
		$('#'+id+'Er').text(webMessages.entertheamounttotransfer);
		loadMsgTitleText();
		return false;
	} else if(parseInt($('#'+id).val()) == 0) {
		$('#'+id+'Er').text(webMessages.entertheamounttotransfer);
		loadMsgTitleText();
		return false;
	} else {
		$('#'+id+'Er').text('');
	}
	return true;
}

function validateTransferDescription(id) {
	if($('#'+id).val().trim().length == 0) {
		$('#'+id+'Er').text(webMessages.enterdescription);
		loadMsgTitleText();
		return false;
	} else {
		$('#'+id+'Er').text('');
	}
	return true;
}

function validateTransferDetails() {
	var flag = true;
	if($('#sourceAccountNumber').val().trim().length == 0 | $('#destinationAccountNumber').val().trim().length == 0 | 
			!validateTransferAmount('tempTransferAmount') | !validateTransferDescription('description')) {
		flag = false;
	}
	if($('#sourceAccountNumber').val().trim().length == 0) {
		$('#sourceAccountNumberEr').text(webMessages.enteraccountnumber );
		loadMsgTitleText();
		flag = false;
	}
	if($('#destinationAccountNumber').val().trim().length == 0) {
		$('#destinationAccountNumberEr').text(webMessages.enteraccountnumber );
		loadMsgTitleText();
		flag = false;
	}
		
	$('#accountCloseFlag').val($('#isAccountClose').is(':checked'));
	$('#transferAmount').val($('#tempTransferAmount').val());
	return flag;
}
