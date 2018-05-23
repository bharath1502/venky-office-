HTTP_METHOD = {
	POST : "POST",
	GET : "GET"
};

CONTENT_TYPE = {
	APP_JSON : "application/json",
	JSON : "json"
};

function invokeAjax(httpMethod, url, data, successCallback, failureCallback) {
	$.ajax({
		type : httpMethod,
		dataType : CONTENT_TYPE.JSON,
		url : url,
		data : data,
		success : successCallback,
		error : failureCallback
	});
}


/*function vlalidateUser() {
	var userName = get('userName').value.trim();
	if (isEmpty(userName)) {
		setError(get('userName'), 'Please enter user name');
		return false;
		
	}else if (!isEmpty(userName)) {
	invokeAjax(HTTP_METHOD.GET, 'uniqueUser', 'userName=' + userName,
					getSuccess, getFailure);
	}

		setError(get('userName'), '');
		document.getElementById('userNamegreenEr').innerHTML="";
		setLable('confirmMuserName', userName);
	}
*/

/*function vlalidateUserName() {
	var reg =  /^[A-Za-z0-9. _]+$/;
	var userName = get('userName').value.trim();

	if (isEmpty(userName)) {
		setError(get('userName'), 'Please enter user name ');
		document.getElementById('userNamegreenEr').innerHTML= "";
		return false;
	}else if (reg.test(userName) == false) {
		setError(get('userName'), '*Can contain only alphabets and numerics*');
		document.getElementById('userNamegreenEr').innerHTML= "";
		return false;
	} else {
		return invokeAjax(HTTP_METHOD.GET, 'uniqueUser', 'userName=' + userName,
				getSuccess, getFailure);
		
		
	}
}

function getSuccess(resp) {
	
	if (resp && resp.errorCode === '00')
		{
		
	setError(get('userName'), '');
	document.getElementById('userNamegreenEr').innerHTML= "username avilable";
	setLable('confirmMuserName', get('userName').value.trim());
	return true;
		}
	else if(resp && resp.errorCode === 'R1')
		{
		setError(get('userName'), 'username not avilable');
		document.getElementById('userNamegreenEr').innerHTML= "";
		return false;
		}
}

function getFailure(resp) {
	setError(get('userName'), '');
	document.getElementById('userNamegreenEr').innerHTML= "";
	return false;
}*/

/*function getStatesSuccess(resp) {
	select = document.getElementById("state");
	select.options.length = 0;
	if (resp && resp.errorCode === '0' && resp.stateList != null) {
		$('#state').append(
				'<option value="" selected="selected">Select*</option>');
		$.each(resp.stateList, function(index, state) {
			if(resp.stateList.length == 1 && index == 0){
				$('#state').html(
						'<option value="' + state + '" selected="selected">' + state + '</option>');
			}
			else{
				$('#state').append(
						'<option value="' + state + '">' + state + '</option>');
			}
		});

	}
}

function getStatesFailure(resp) {
	
}

function loadbillingStates(country) {
	$('#billingstates option[value!=""]').remove();
	if (!isEmpty(country)) {
		invokeREST(HTTP_METHOD.GET, 'common/getStates', 'country=' + country,
				getbillingStatesSuccess, getbillingStatesFailure);
	}
}

function getbillingStatesSuccess(resp) {
	select = document.getElementById("billingstates");
	select.options.length = 0;
	if (resp && resp.errorCode === '0' && resp.stateList != null) {
		$('#billingstates').append(
		'<option value="" selected="selected">Select...</option>');
		$.each(resp.stateList, function(index, state) {
			if(state=='N/A')
			{
			$('#billingstates').append(
					'<option value="' + state + '" selected>' + state + '</option>');
			}else 
			$('#billingstates').append(
					'<option value="' + state + '">' + state + '</option>');
		});

	}

}


function getbillingStatesFailure(resp) {
	
}

function getCompanyAddress(name) {
	if(name.length > 1) {
		invokeREST(HTTP_METHOD.GET, 'common/getCompanyAddress', 'name=' + name,			getCompanyAddressSuccess, getCompanyAddressFailure);
	}
}

function getCompanyAddressSuccess(resp) {
	hide('address-details-fields');
	hide('address-fields');
	var addressListHTML = '';
	setDiv('address-details-fields', addressListHTML);
	if (resp && resp.errorCode === '0' && resp.addresses != null) {
		globalAdresses = resp.addresses;
		globalCompanyAr = new Array();
		$.each(
						resp.addresses,
						function(index, add) {
							globalCompanyAr.push(add.companyName);
							globalCompanyAr.push(add.id);
							var addContent = getValidValue(add.address) + '<br /> &nbsp;  &nbsp; &nbsp; &nbsp;'
							+ getValidValue(add.address2) + '&nbsp; &nbsp; '+ getValidValue(add.city) + '<br /> &nbsp;  &nbsp; &nbsp; &nbsp;'
							+ getValidValue(add.country) +'&nbsp; &nbsp; '+ getValidValue(add.state) + '<br /> &nbsp;  &nbsp; &nbsp; &nbsp;'
							+ getValidValue(add.postalCode) + '<br /> &nbsp;  &nbsp; &nbsp; &nbsp;'
							+ getValidValue(add.website);							
						addressListHTML += '<fieldset class="location-field"><input type="radio" onclick="setComapanyAddress('+index+'),unhidejoin();" value="'+add.id+'" name="location" > &nbsp; <span>'
								+ addContent + '</span><br /><img id="addImg'+index+'" style="display:none" class="feature-img" src="../images/PLdefault_company_picture.jpg"></fieldset>';
						});
		setAutocompleteCompanyValues();			
		addressListHTML +='<fieldset class="location-field"> <input type="radio" onclick="setComapanyAddress(-1)" value="addNew"  name="location" > &nbsp; <span>Add new address</span></fieldset>';
		setDiv('address-details-fields', addressListHTML);
	} else {		
		globalAdresses = null;
		addressListHTML +='<fieldset class="location-field"><input type="radio" onclick="setComapanyAddress(-1)" value="addNew" name="location" checked> &nbsp; <span>Add new address</span></fieldset>';
		setDiv('address-details-fields', addressListHTML);
		show('address-details-fields');
		show('address-fields');
	}	
	
}

function getCompanyAddressFailure(resp) {
	//hide('address-details-fields');
}

function joinUser() {	
	invokeREST(HTTP_METHOD.POST,'join-user', getUserData(), joinUserSuccess,
			joinUserFailure);
}

function joinUserSuccess(resp) {
	if (resp && resp.result === 'true') {
		document.getElementById("join").setAttribute("disabled", "disabled");
		$(':input').not(':button').val('').removeAttr('checked');
		window.location = "join-marketplace";
	} else {
		setDiv('errorMsg', 'Failure in registration');
	}
}

function joinUserFailure(resp) {
	setDiv('errorMsg', 'Failure in registration');
}

function getUserData() {
	// var email = 'k@k.com';
	// var pass = 'Asd@123';
	// var fName = 'F';
	// var lName = 'L';
	// var jTitle = 'Mr';
	// var phone = '99999999';
	// var compName = 'ABC';
	// var country = 'USA';
	// var address = 'add';
	// var address2 = 'add2';
	// var city = 'CA';
	// var state = 'Alaska';
	// var postalCode = '12345';
	// var website = 'www.t.com';

	var email = getVal("emailAddress");
	var pass = getVal("password");
	var fName = getVal("firstname");
	var lName = getVal("lastname");
	var jTitle = getVal("jobTitle");
	var phone = getVal("phoneNumber");
	var compName = getVal("company");
	var country = getVal("country");
	var address = getVal("addressLine1");
	var address2 = getVal("addressLine2");
	var city = getVal("city");
	var state = getVal("state");
	var postalCode = getVal("zipcode");
	var website = getVal("website");
	var timezone = getVal("defaultTimezone");

	var queryString = 'email=' + email + '&pass=' + pass + '&fName=' + fName
			+ '&lName=' + lName + '&jTitle=' + jTitle + '&phone=' + phone;
	queryString += '&compName=' + compName + '&country=' + country
			+ '&address=' + address + '&address2=' + address2;
	queryString += '&city=' + city + '&state=' + state + '&postalCode='
			+ postalCode + '&website=' + website + '&timezone=' + timezone;
	return queryString;
}

function getValidValue(val) {
	if(!val || val == null || val == 'null') {
		return '';
	}
	return val;
}


function getChecked(id)
{
	if(get(id).checked==true)
	{
	return true;
	
	}else
		{
		return false;
		}
}


function joinMarket()
{
	// alert(get('accept1').cheaked+get('accept2').cheaked);
	
if((getChecked('accept1') && getChecked('accept2')) && (getChecked('COAL_BUYER')||getChecked('FUEL_BUYER')||getChecked('NAT_GAS_BUYER')||getChecked('NAT_GAS_CAPACITY_BUYER')||getChecked('POWER_BUYER')||getChecked('RAIL_CAR_BUYER')
		||getChecked('STORAGE_BUYER')||getChecked('COAL_SELLER')||getChecked('FUEL_SELLER')||getChecked('NAT_GAS_SELLER')||getChecked('NAT_GAS_CAPACITY_SELLER')||getChecked('POWER_SELLER')||getChecked('RAIL_CAR_SELLER')
		||getChecked('STORAGE_SELLER')))
{

	

invokeREST(HTTP_METHOD.GET,'join-market', getJoinmarketplaceData(),joinMarketPlaceSuccess,
		failureCallbackFailure)	;


}
else{
	alert("false");
}
}

function validateBeforeAcceptAgreement() {
	var flag = false;
	
		if (!(document.getElementById("accept1").checked && document
			.getElementById("accept2").checked)) {
			$("#errorMarketplace").removeClass('hidden');
			setDiv("errorMarketplace","You must accept the agreement before proceeding.");
		flag = false;
	
	} else {
		with (document.joinMarket) {
			for ( var i = 0; i < buyingCommodities.length; i++) {
				if (buyingCommodities[i].checked) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				for ( var i = 0; i < sellingCommodities.length; i++) {
					if (sellingCommodities[i].checked) {
						flag = true;
						break;
					}
				} 
			}
			if (!flag) {
				$("#errorMarketplace").removeClass('hidden');
				setDiv("errorMarketplace","You must select a Pricelock product.");
			}
		}
	}
	
	
	return flag;
	
}

function openPopup(){
	$('#my_popup').popup("show");
}

function validateBeforeDeferAgreement() {
	var flag = false;
	
		with (document.joinMarket) {
			for ( var i = 0; i < buyingCommodities.length; i++) {
				if (buyingCommodities[i].checked) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				for ( var i = 0; i < sellingCommodities.length; i++) {
					if (sellingCommodities[i].checked) {
						flag = true;
						break;
					}
				} 
			}
			if (!flag) {
				$("#errorMarketplace").removeClass('hidden');
				setDiv("errorMarketplace","You must select a Pricelock product.");
			}
	}
		if(flag) {
			openPopup();
		} 
	
	return flag;
	
}

function validateAcceptAgreement() {
	var flag = false;
	
		if (document.getElementById("accept1").checked && document
			.getElementById("accept2").checked) {
		flag = true;
	} else {
		$("#errorMarketplace").removeClass('hidden');
		setDiv("errorMarketplace","You must accept the agreement before proceeding.");
	} 
	
	return flag;
	
}

function getCheckedVal(id)
{
	
	if(get(id).checked==true)
		{
		return getVal(id);
		
		}else
			{
			return null;
			}
	}



function getJoinmarketplaceData()
{
	
	
	var byorSell=getCheckedVal('buyer_seller');
	
	var buyCoal=getCheckedVal('COAL_BUYER');
	var buyMotorFuel=getCheckedVal('FUEL_BUYER');
	var buyNatGas=getCheckedVal('NAT_GAS_BUYER');
	var buyNatGasCapacity=getCheckedVal('NAT_GAS_CAPACITY_BUYER');
	var buyPower=getCheckedVal('POWER_BUYER');
	var buyRailcar=getCheckedVal('RAIL_CAR_BUYER');
	var buyNatGasStorage=getCheckedVal('STORAGE_BUYER');
	
	var sellCoal=getCheckedVal('COAL_SELLER');
	var sellMotorFuel=getCheckedVal('FUEL_SELLER');
	var sellNatGas=getCheckedVal('NAT_GAS_SELLER');
	var sellNatGasCapacity=getCheckedVal('NAT_GAS_CAPACITY_SELLER');
	var sellPower=getCheckedVal('POWER_SELLER');
	var sellRailcar=getCheckedVal('RAIL_CAR_SELLER');
	var sellNatGasStorage=getCheckedVal('STORAGE_SELLER');
	
	var proctcost=getCheckedVal('Protection_cost');
	
	var query= 'byorSell=' + byorSell + '&buyCoal=' + buyCoal + '&buyMotorFuel=' + buyMotorFuel
			+ '&buyNatGas=' + buyNatGas + '&buyNatGasCapacity=' + buyNatGasCapacity + '&buyPower=' + buyPower;
	query += '&buyRailcar=' + buyRailcar + '&buyNatGasStorage=' + buyNatGasStorage
			+ '&sellCoal=' + sellCoal + '&sellMotorFuel=' + sellMotorFuel;
	query += '&sellNatGas=' + sellNatGas + '&sellNatGasCapacity=' + sellNatGasCapacity + '&sellPower='
			+ sellPower + '&sellRailcar=' + sellRailcar  + '&sellNatGasStorage='
			+ sellNatGasStorage + '&proctcost=' + proctcost;
	return query;
	
	
}


function joinMarketPlaceSuccess(resp) {
	if (resp &&resp.result === 'true') {
		window.location = "url-auction-home";
	} else {
		window.location = "join-marketplace";
	}
}

function failureCallbackFailure(resp) {
	window.location = "join-marketplace";
}
*/