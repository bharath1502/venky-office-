var password = '';
var chataPay = function() {

	var cardAsocType = '';
	var setCardType = '';
	// Function Validate MOD10 for Card Number
	function mod10(cardNumber) {
		var ar = new Array(cardNumber.length);
		var i = 0, sum = 0;
		for (i = 0; i < cardNumber.length; ++i) {
			ar[i] = parseInt(cardNumber.charAt(i));
		}
		for (i = ar.length - 2; i >= 0; i -= 2) {
			ar[i] *= 2;
			if (ar[i] > 9)
				ar[i] -= 9;
		}
		for (i = 0; i < ar.length; ++i) {
			sum += ar[i];
		}
		return (((sum % 10) == 0) ? true : false);
	}

	var CHATAKPG = {};

	var Cards = {
		maestro : /^(50|63|66|5[6-8]|6[8-9]|600[0-9]|6010|601[2-9]|60[2-9]|61|620|621|6220|6221[0-1])/,
		maestro16d : /^(508125|508126|508159|508192|508227|504809)/,
	};
	var setLen = {
		maestro : 19,
		maestro16d : 16,
	};

	// Card Expiry Validation
	CHATAKPG.expired = function(month, year) {
		var now = new Date();
		var fullYear = ((year + '').length < 4) ? '20' + year : year;
		var expiresIn = new Date(fullYear, month, 0, 0, 0);
		expiresIn.setMonth(expiresIn.getMonth());
		if (now.getTime() < expiresIn.getTime())
			return false;
		return true;
	};
	var regCvv = RegExp("^[0-9]{3,4}$");
	/***************************************************************************
	 * ***********************************************************************\
	 * Validate Card Number and relevant details function called on cnumber
	 * mod10 is true. \
	 **************************************************************************/
	var txtcname = "";
	CHATAKPG.validateCard = function(cardNumber, cardType, cardMonth, cardYear,
			cardCvv, cardName, type) {

		var regName = RegExp("^[a-zA-Z'-. ]{3,70}$");

		var getCvvMaxLen = $('#code' + type).attr('maxlength');

		if (!mod10(cardNumber)) {
			return false;
		}
		if (cardName == "" || !cardName.match(regName)) {
			$('#cname2').focus();
			return false;
		}
		// Avoid validation for Maestro cards
		if (cardType != 'maestro') {
			if (cardMonth == '0') {
				$('#expmon').focus();
				return false;
			}
			if (cardYear == '0') {
				$('#expyr').focus();
				return false;
			}
			if (CHATAKPG.expired(cardMonth, cardYear)) {
				$('#expmon').focus();
				return false;
			}
			if (cardCvv == "" || !regCvv.test(cardCvv)
					|| cardCvv.length != getCvvMaxLen) {
				$('input[id$=code' + type + ']').focus();
				return false;
			}
		}
		return true;

	};

	CHATAKPG.CardType = function(n, type, cardNumberValidate) {
		var n = $('input#number' + type).val();
		cardNumber = $('#number' + type).val();
		cardName = $('#name' + type).val();
		cvvNo = $('#code' + type);
		cardMonth = $('#month' + type).val();
		cardYear = $('#year' + type).val();

		// Function to Validation Cards Number and get Cards Type
		var tmplen = n.split("-").join("").substring(0, 6); // tmplen stores
		// first 6 digit of Cards number
		var setCardNumber = n;

		if (tmplen.length >= 6) {

			// Get Cards Type in Value (r)
			var P = tmplen;
			var T = ""; // first 6 digit of card number store in P to match with
			// Regex in CARD
			for ( var tmplen in Cards) {
				var Q = Cards[tmplen]; // get Card Label (visa,master...)
				if (P.match(Q)) {
					var T = tmplen;
					setCardType = T;
					$('#cardTypeid').addClass(tmplen);
					cardAsocType = tmplen;
					 if (tmplen === "maestro" || tmplen === "maestro16d") {
						cardAsocType = 'ME';
						$(".cards-info").css("background-position", "0 -109px");
					} else {
						cardAsocType = '';
						$(".cards-info").css("background-position", "0 0");
					}
				} // condition to match regex and get the card type.
			}

			// Get Card Length in Value (L)
			var getCardType = T;
			for ( var T in setLen) {
				var matchLen = setLen[T];
				var ValidLen = getCardType.match(T);
				if (getCardType.match(T)) {
					var L = matchLen;
					$('#number' + type).attr('maxlength', matchLen);
				}
			}
		}

		if (cardNumberValidate) {
			if (getCardType == "") {
				return false;
			} else if (getCardType == "maestro") {
				$('input#number' + type).attr('maxlength', '23');
				$(".cards-info").css("background-position", "0 -109px");
			}

			if (cardAsocType == '' /* && chCType != cardAsocType */) {
				return false;
			}

			// visa and Master
			if (setCardNumber.length === L) {
				if (!mod10(setCardNumber)) {
					return false;
				}
				if ((getCardType == "maestro" || getCardType == "maestro16d")
						&& document.form1.paymentMode.value == 'DEBIT') {
					$('#month' + type).prop('value', '12');
					$('#year' + type).prop('value', '2060');
					$("input[id$=code" + type + "]").prop('value', '111');
				}
				if ((getCardType == "maestro" || getCardType == "maestro16d")
						&& document.form1.paymentMode.value != 'DEBIT') {
					return false;
				}
				return {
					setCardType : getCardType,
					setCardNumber : setCardNumber
				};
			}

			else {
				$('#code' + type).attr('maxlength', '3');
				$('#month' + type).prop('value', '0');
				$('#year' + type).prop('value', '0');
				$("input[id$=code" + type + "]").prop('value', '');
				$(".cards-info").css("background-position", "0 0");
				return false;
			}
		} else {
			return false;
		}

	};

	var validateCard = function(cNumber, cMon, cYear, cCode, cName, type) {
		if (cNumber === "") {
			$('input#number' + type).focus();
			return false;
		}
		if (cMon === "") {
			$('input#month' + type).focus();
			return false;
		}
		if (cYear === "") {
			$('input#year' + type).focus();
			return false;
		}
		if (cCode === "") {
			$('input#code' + type).focus();
			return false;
		}
		if (cName === "") {
			$('input#name' + type).focus();
			return false;
		} else {
			return CHATAKPG.validateCard(cNumber, setCardType, cMon, cYear,
					cCode, cName, type);
		}
	};

	return {
		CHATAKPG : CHATAKPG,

		checkSession : function() {
			setTimeout(function() {
				var url = contextPath + '/pg/' + page + '/check-access';
				invokeREST(HTTP_METHOD.GET, url, {}, successCheckSession,
						failureCheckSession);
			}, 1000); // Slower the API call

		},
		processPaymentToken : function(mId, type, requestId, newCardAsocType) {

			cardAsocType = newCardAsocType;
			var cCode = $('#code' + type).val().trim();
			var getCvvMaxLen = $('#code' + type).attr('maxlength');

			if (cCode == "" || !regCvv.test(cCode)
					|| cCode.length != getCvvMaxLen) {
				$('input[id$=code' + type + ']').focus();
				return false;
			}

			$('#progressingId').show();
			$('#contentId').hide();
			var encCode = $.jCryption.encrypt(cCode, password);

			var requestBody = {
				cvv : encCode,
				type : cardAsocType,
				requestId : requestId
			};
			var url = contextPath + '/pg/sale/' + mId + '/processToken';
			debug('url', url);
			invokeREST(HTTP_METHOD.POST, url, requestBody, successPayProcess,
					failurePayProcess);
		},
		processPayment : function(mId, type, requestId) {

			var cNumber = $('#number' + type).val().trim();
			var cMon = $('#month' + type).val();
			var cYear = $('#year' + type).val();
			var cCode = $('#code' + type).val().trim();
			var cName = $('#name' + type).val().trim();

			if (validateCard(cNumber, cMon, cYear, cCode, cName, type)) {
				$('#progressingId').show();
				$('#contentId').hide();
				var encNumber = $.jCryption.encrypt(cNumber, password);
				var encMY = $.jCryption.encrypt((cMon + '' + cYear), password);
				var encCode = $.jCryption.encrypt(cCode, password);
				var saveCardCheck = (document.getElementById('saveCardCheckId') && document
						.getElementById('saveCardCheckId').checked) ? 'Y' : 'N';

				var requestBody = {
					number : encNumber,
					expMonthYear : encMY,
					cvv : encCode,
					type : cardAsocType,
					name : cName,
					requestId : requestId,
					saveCard : saveCardCheck
				};
				var url = contextPath + '/pg/sale/' + mId + '/process';
				debug('url', url);
				invokeREST(HTTP_METHOD.POST, url, requestBody,
						successPayProcess, failurePayProcess);
			} else {
				$('#progressingId').hide();
				$('#contentId').show();
			}
		}

	};
}();

var successCheckSession = function(response, statusText, xhr, $form) {
	var responseObj = JSON.parse(response);
	if (responseObj && responseObj.status && responseObj.status === 'S') {
		$('#progressingId').hide();
		$('#contentId').show();
	} else {
		window.location.href = 'invalid-access';
	}
};

var failureCheckSession = function(response, status, err) {
	var responseObj = JSON.parse(response);
	if (responseObj && responseObj.status && responseObj.status === 'S') {
		$('#progressingId').hide();
		$('#contentId').show();
	} else {
		window.location.href = 'invalid-access';
	}
};

var getFinalReturnURL = function(response) {
	var queryParamSeperator = '';
	if (cR.substr(-1) === "?") {
		queryParamSeperator = '';
	} else {
		queryParamSeperator = '?&';
	}
	return (cR + queryParamSeperator + 'data=' + encodeURIComponent(JSON
			.stringify(response)));
};

var successPayProcess = function(response, statusText, xhr, $form) {
	$('#paymentMsgDivId').html(
			" Please wait... Your Payment has been processed successfully.");
	setTimeout(function() {
		window.location.href = getFinalReturnURL(response);
	}, 2000);

};

var failurePayProcess = function(response, status, err) {
	$('#paymentMsgDivId').html(
			" Please wait... Your Payment has been processed successfully.");
	setTimeout(function() {
		window.location.href = getFinalReturnURL(response);
	}, 2000);
};

var getValue = function(elementId) {
	return document.getElementsByName(elementId).value;
};

/* Plus And Minus Images Show And Hide functionality End */
$(document).on("keyup", "input#numbercredit", function() {
	chataPay.CHATAKPG.CardType(this.value, 'credit', false);
});
$(document).on("keyup", "input#numberdebit", function() {
	chataPay.CHATAKPG.CardType(this.value, 'debit', false);
});
$("#numbercredit").blur(function(){
	chataPay.CHATAKPG.CardType(this.value, 'credit', true);
});
$("#numberdebit").blur(function(){
	chataPay.CHATAKPG.CardType(this.value, 'debit', true);
});
/* Decrypting the form inputs */

$(document).ready(
		function() {

			chataPay.checkSession();

			$('#codecredit').one('focus', function() {
				$(this).attr('type', 'password');
			});

			$('#codecredit').on('click', function() {
				$(this).attr('type', 'password');
			});
			$('#codedebit').one('focus', function() {
				$(this).attr('type', 'password');
			});

			$('#codedebit').on('click', function() {
				$(this).attr('type', 'password');
			});

			// By default select the Credit card section
			hideShow(2);
			var hashObj = new jsSHA(ky, "ASCII");
			password = hashObj.getHash("SHA-512", "HEX");

			$.jCryption.authenticate(password, "encrypt?action=generate",
					"encrypt?action=handshake", function(AESKey) {

					}, function() {
						// Authentication failed
					});
		});

for ( var k = 0; k < 21; k++) {
	year += 1;
	var shortYr = (year + '').substring(2, year.length);
	$("#yeardebit").append(
			"<option value='" + shortYr + "'>" + year + "</option>");
	$("#yearcredit").append(
			"<option value='" + shortYr + "'>" + year + "</option>");
}

/* Plus And Minus Images Show And Hide functionality Start */
$(".content2, .content3, .arrow2, .arrow3").hide();
function hideShow(id) {
	// $(".content"+id).toggle();
	for ( var i = 1; i <= 3; i++) {
		if (i != id) {
			$(".content, .left-arrow").hide();
			$(".list").removeClass("active");
			$(".content" + id).show();
			$(".arrow" + id).show();
			$(".list" + id).addClass("active");
		}
	}
}

var IDLE_MINUTES = 4;
var IDLE_TIMEOUT = IDLE_MINUTES * 60; // seconds and 2 mins

var _idleSecondsCounter = 0;
document.onclick = function() {
	_idleSecondsCounter = 0;
};
document.onmousemove = function() {
	_idleSecondsCounter = 0;
};
document.onkeypress = function() {
	_idleSecondsCounter = 0;
};
document.onkeyup = function() {
	_idleSecondsCounter = 0;
};
window.setInterval(CheckIdleTime, 1000);

function CheckIdleTime() {
	_idleSecondsCounter++;
	if (_idleSecondsCounter >= IDLE_TIMEOUT) {
		document.location.href = contextPath + "/pg/session-expired";
	}
