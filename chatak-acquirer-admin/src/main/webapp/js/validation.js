var text;
var ValidationRules = {

	title : {
		type : "alpha",
		min : "2",
		max : "30",
		mandatory : true
	},

	mobile_phone : {
		type : "numeric",
		min : "10",
		max : "16",
		mandatory : true

	},

	first_name : {
		type : "alpha",
		min : "2",
		max : "30",
		mandatory : true

	},

	fax : {
		type : "numeric",
		min : "9",
		max : "15",
		mandatory : false

	},

	middle_name : {
		type : "alpha",
		min : "1",
		max : "30",
		mandatory : false

	},

	company_name : {
		type : "specialchar",
		min : "2",
		max : "30",
		mandatory : true

	},
	
	zip_code : {
		type : "alphanumeric",
		min : "4",
		max : "7",
		mandatory : true

	},

	last_name : {
		type : "alpha",
		min : "1",
		max : "30",
		mandatory : true

	},

	citizenship : {
		type : "alpha",
		min : "1",
		max : "1000",
		mandatory : true

	},

	residence : {
		type : "alpha",
		min : "1",
		max : "1000",
		mandatory : true

	},

	ssn : {
		type : "numeric",
		min : "9",
		max : "9",
		mandatory : true

	},

	email : {
		type : "email",
		min : "0",
		max : "50",
		mandatory : true

	},

	confirmemail : {
		type : "confirmemail",
		min : "0",
		max : "50",
		mandatory : true

	},

	address : {
		type : "all",
		min : "5",
		max : "50",
		mandatory : true

	},

	address2 : {
		type : "all",
		min : "0",
		max : "50",
		mandatory : false

	},

	state : {
		type : "dropdown",
		min : "0",
		max : "100",
		mandatory : true

	},

	city : {
		type : "dropdown",
		min : "0",
		max : "100",
		mandatory : true

	},

	country : {
		type : "dropdown",
		min : "0",
		max : "100",
		mandatory : true

	},
	
	currencyValue : {
		type : "dropdown",
		min : "0",
		max : "100",
		mandatory : true

	},
	
	countryCitizen : {
		type : "alpha",
		min : "0",
		max : "100",
		mandatory : true
	},
	
	countryResidence : {
		type : "alpha",
		min : "0",
		max : "100",
		mandatory : true
	},
	
	
	zip : {
		type : "numeric",
		min : "4",
		max : "6",
		mandatory : true

	},

	paddress : {
		type : "all",
		min : "5",
		max : "50",
		mandatory : true

	},

	paddress2 : {
		type : "all",
		min : "0",
		max : "50",
		mandatory : false

	},

	pstate : {
		type : "alpha",
		min : "2",
		max : "30",
		mandatory : true

	},

	pcity : {
		type : "alpha",
		min : "2",
		max : "30",
		mandatory : true

	},

	pcountry : {
		type : "alpha",
		min : "2",
		max : "30",
		mandatory : true

	},

	pzip : {
		type : "numeric",
		min : "4",
		max : "6",
		mandatory : true

	},
	
	merchant_Code : {
		type : "numeric",
		min : "14",
		max : "16",
		mandatory : true

	},

	bank_name : {
		type : "alpha",
		min : "2",
		max : "100",
		mandatory : true

	},
	
	first_name_SplChar : {
		type : "alphanumsplchar",
		min : "2",
		max : "100",
		mandatory : true
	},
	
	first_name_NotMand : {
		type : "alphanumsplchar",
		min : "2",
		max : "100",
		mandatory : false
	},

	bank_code : {
		type : "alphanumeric",
		min : "2",
		max : "30",
		mandatory : true

	},

	bank_address1 : {
		type : "all",
		min : "2",
		max : "50",
		mandatory : true

	},

	bank_address2 : {
		type : "all",
		min : "0",
		max : "50",
		mandatory : false

	},

	bank_city : {
		type : "alpha",
		min : "2",
		max : "30",
		mandatory : true

	},

	bank_state : {
		type : "alpha",
		min : "2",
		max : "30",
		mandatory : true

	},

	bank_country : {
		type : "alpha",
		min : "2",
		max : "30",
		mandatory : true

	},

	bank_zip : {
		type : "numeric",
		min : "4",
		max : "6",
		mandatory : true

	},

	routing_number : {
		type : "alphanumeric",
		min : "9",
		max : "9",
		mandatory : true

	},

	account_number : {
		type : "numeric",
		min : "10",
		max : "12",
		mandatory : true

	},
	
	account_numberBank : {
		type : "numeric",
		min : "0",
		max : "20",
		mandatory : true

	},

	account_type : {
		type : "dropdown",
		min : "0",
		max : "100",
		mandatory : true

	},
	
	fee_Program : {
		type : "dropdown",
		min : "0",
		max : "100",
		mandatory : true

	},
	name_account : {
		type : "alpha",
		min : "2",
		max : "30",
		mandatory : false

	},

	username : {
		type : "username",
		min : "6",
		max : "20",
		mandatory : true

	},

	password : {
		type : "password",
		min : "6",
		max : "20",
		mandatory : true

	},

	confirm_password : {
		type : "confirm_password",
		min : "6",
		max : "20",
		mandatory : true

	},

	security_key : {
		type : "numeric",
		min : "4",
		max : "6",
		mandatory : true

	},
	
	fee_amount : {
		type : "numericDecimal",
		min : "1",
		max : "10",
		mandatory : false

	},
	
	mobile_optional : {
		type : "numeric",
		min : "10",
		max : "16",
		mandatory : false

	},

	passport_number : {
		type : "alphanumeric",
		min : "2",
		max : "30",
		mandatory : false

	},

	question : {
		type : "dropdown",
		min : "0",
		max : "100",
		mandatory : false

	},
	partner_Id : {
		type : "dropdown",
		min : "0",
		max : "100",
		mandatory : true

	},
	role_Id : {
		type : "dropdown",
		min : "0",
		max : "100",
		mandatory : true

	},

	security_answer : {
		type : "all",
		min : "6",
		max : "25",
		mandatory : true

	},
	
	security_Question :{
		type : "specialcharwithspace",
		min : "5",
		max : "50",
		mandatory : true
	},

	

	business_entity_name : {
		type : "alpha",
		min : "6",
		max : "30",
		mandatory : true

	},

	partner_id : {
		type : "alpha",
		min : "1",
		max : "30",
		mandatory : true

	},

	bank_name_dropdown : {
		type : "dropdown",
		min : "1",
		max : "30",
		mandatory : true

	},

	contact_person : {
		type : "alpha",
		min : "1",
		max : "30",
		mandatory : true

	},

	contact_phone : {
		type : "mobile",
		min : "8",
		max : "14",
		mandatory : true

	},

	partner_edit_status : {
		type : "dropdown",
		min : "1",
		max : "12",
		mandatory : true
		
	},

	white_label : {
		mandatory : true

	},

	partner_name_dropdown : {
		type : "dropdown",
		mandatory : true

	},
	account_program_dropdown : {
		type : "dropdown",
		mandatory : true

	},
	agent_name_dropdown : {
		type : "dropdown",
		mandatory : true

	},
	no_of_cards : {
		type : "numericNotZero",
		min : "1",
		max : "5",
		mandatory : true

	},
	status : {
		type : "dropdown",
		min : "1",
		max : "12",
		mandatory : true

	},
	startDate : {
		type : "date",
		min : "2",
		max : "12",
		mandatory : true

	},
	
	endDate : {
		type : "date",
		min : "2",
		max : "12",
		mandatory : true

	},

	fee_Short_Code : {
		type : "alphanumeric",
		min : "",
		max : "10",
		mandatory : true

	},

	fee_Description : {
		type : "all",
		min : "",
		max : "50",
		mandatory : true

	},

	transaction_Short_Code : {
		type : "alphanumeric",
		min : "",
		max : "20",
		mandatory : true

	},
	
	txn_ref_number : {
		type :"numeric",
		min : "6",
		max : "10",
		mandatory : true

	},
	
	remarks : {
		type : "alpha",
		min : "",
		max : "20",
		mandatory : true

	},
	
	amount : {
		type : "numericDecimal",
		min : "1",
		max : "13",
		mandatory : true

	},
	
	tax: {
		type : "numericDecimal",
		min : "1",
		max : "10",
		mandatory : true
	},
	
	credit_amount: {
		type : "numeric",
		min : "1",
		max : "12",
		mandatory : true

	},
	
	dollar_amount: {
		type : "dollarnumeric",
		min : "1",
		max : "18",
		mandatory : true

	},
	
	eIN_taxId: {
		type : "numericWithDash",
		min : "1",
		max : "20",
		mandatory : true

	},

	transaction_Description : {
		type : "alpha",
		min : "",
		max : "50",
		mandatory : text

	},
	//End of validation rules for Create and Edit fee code

	//Validation rules for Create and Edit fee program

	fee_Program_Name : {
		type : "alpha",
		min : "",
		max : "20",
		mandatory : true

	},

	fee_Program_Description : {
		type : "alpha",
		min : "",
		max : "20",
		mandatory : true

	},

	account_Program : {
		type : "dropdown",
		min : "",
		max : "12",
		mandatory : true

	},

//End of validation rules for Create and Edit fee program
	
	agent_Client_Id : {
		type : "numeric",
		min : "2",
		max : "10",
		mandatory : true

	},
	
	cardFee : {
		type : "numeric",
		min : "1",
		max : "7",
		mandatory : true

	},
	feeChargeTpye : {
		type : "dropdown",
		min : "",
		max : "12",
		mandatory : true

	},
	
	amount_False: {
		type : "numeric",
		min : "0",
		max : "7",
		mandatory : false
		
	},
	
	fee: {
		type : "numericDecimal",
		min : "1",
		max : "7",
		mandatory : true
		
	},
	
	percentage: {
		type : "percentage",
		min : "1",
		max : "7",
		mandatory : true
		
	},
	general_name : {
		type : "alphanumeric",
		min : "2",
		max : "30",
		mandatory : true

	},

// Validation for CardProgram starts
	
	contractName : {
		type : "all",
		min : "",
		max : "100",
		mandatory : true
	},
	
	card_Prog_Desc : {
		type : "alpha",
		min : "",
		max : "20",
		mandatory : true
	},
	
	contractId : {
		type : "numeric",
		min : "1",
		max : "3",
		mandatory : true
	},
	
	card_Design_Id : {
		type : "dropdown",
		min : "",
		max : "12",
		mandatory : true
	},
	
	pin : {
		type : "numeric",
		min : "4",
		max : "12",
		mandatory : true
	},
	
	pin_Gen_Id : {
		type : "dropdown",
		mandatory : true
	},
	
	pin_Verification_Id : {
		type : "dropdown",
		mandatory : true
	},
	
	card_Inactivity : {
		type : "alphanumeric",
		min : "",
		max : "20",
		mandatory : true
	},
	
	pin_Retry_Attempts : {
		type : "numeric",
		min : "",
		max : "2",
		mandatory : true
	},
	
	card_Expiry_Period : {
		type : "numeric",
		min : "1",
		max : "20",
		mandatory : true
	},
	
	acceptance : {
		type : "dropdown",
		mandatory : true
	},
	
	service_Code : {
		type : "numeric",
		min : "3",
		max : "3",
		mandatory : true
	},
	
	message : {
		type : "all",
		min : "",
		max : "30",
		mandatory : true
	},
	
	first_Name : {
		type : "alphanumeric",
		min : "",
		max : "20",
		mandatory : true
	},
	
	last_Name : {
		type : "alphanumeric",
		min : "",
		max : "20",
		mandatory : true
	},
	
	account_Program_Id : {
		type : "dropdown",
		mandatory : true
	},

	agent_ani: {
		type : "numeric",
		min : "6",
		max : "20",
		mandatory : true
	},
	
	card_Number : {
		type : "numeric",
		min : "12",
		max : "19",
		mandatory : true
	},
	
	remap_ToAgent : {
		type : "alphanumeric",
		min : "",
		max : "30",
		mandatory : true

	},
    pin_type : {
		type : "dropdown",
		min : "",
		max : "30",
		mandatory : true

	},
	agent_name : {
		type : "alpha",
		min : "",
		max : "30",
		mandatory : true
	},
	
	order_type : {
		type : "dropdown",
		min : "0",
		max : "100",
		mandatory : true

	},
	
	batch_Number:{
        type : "numeric",
        min : "4",
        max : "10",
        mandatory : true
        
    },
    
    area: {
    	type : "alpha",
		min : "",
		max : "30",
		mandatory : true
    },
    
    cardType: {
    	type :"dropdown",
    	mandatory : true
    },
    month: {
    	type :"dropdown",
    	mandatory :true
    },
    year: {
    	type:"dropdown",
    	mandatory :true
    },
    reason : {
		type : "all",
		min : "3",
		max : "500",
		mandatory : true
	 },
    declineReason : {
    	type : "all",
		min : "3",
		max : "500",
		mandatory : true
    },
    
  //End of card Service
    
    partner_client_id:{
		type : "alphanumeric",
		min : "4",
		max : "50",
		mandatory : true
	},
	orderType : {
		type : "dropdown",
		mandatory : true
	},
	partner_name : {
		type : "all",
		min : "2",
		max : "100",
		mandatory : true
	},
	partner_phone : {
		type : "mobilenumbertype",
		min : "10",
		max : "15",
		mandatory : true
	},
	program_manager_name : {
		type : "all",
		min : "2",
		max : "100",
		mandatory : true
	}
	 
};

var validationType;
function assigValidationType(type){
	validationType=type;
}

function clearValidationType(){
	validationType='';
}


function clientValidation(field_id, field_name,div_id) {
	
	if($('#'+field_id).is(':visible')) {
		var data=getVal(field_id);
		var name = field_name;
		
		setDiv(div_id,"");
		
		if (ValidationRules[name].mandatory==true) {
			if(!mandatorytrue(data,div_id))
				return false;
		} 
		
		if (ValidationRules[name].mandatory==false) {
			if (!mandatoryfalse(data, div_id)) 
				return true;
		} 
			
		if (ValidationRules[name].type == "alpha") {
			if(!alpha(data,div_id))
				   return false;
		} 
		
		if (ValidationRules[name].type == "numericWithDash") {
			if(!numericWithDash(data,div_id))
				   return false;
		}
		
		if (ValidationRules[name].type == "specialchar") {
			if(!specialchar(data,div_id))
				   return false;
		} 
		
		if(ValidationRules[name].type == "alphanumeric"){
			alphaNumeric(data,div_id);
			
		}
		
		if(ValidationRules[name].type == "alphanumsplchar"){
			if(!alphanumsplchar(data, div_id))
				return false;
		}
		
		if(ValidationRules[name].type == "numeric"){
			if(!numeric(data, div_id))
				return false;
		}
		if(getUserLocale() != 'es') {
		if(ValidationRules[name].type == "dollarnumeric"){
			if(!dollarnumeric(data, div_id))
				return false;
		}
		}
		if(ValidationRules[name].type == "mobile"){
			if(!mobile(data, div_id))
				return false;
		}
		
		if(ValidationRules[name].type == "numericNotZero"){
			if(!numericNotZero(data, div_id))
				return false;
		}

		if(ValidationRules[name].type == "numericDecimal"){
			if(!numericDecimal(data, div_id))
				return false;
		}

		if(ValidationRules[name].type == "percentage"){
			if(!percentage(data, div_id))
				return false;
		}
		
		if(ValidationRules[name].type == "username") {
			if(!username(data,div_id))
				return false;
		}
		
		if(ValidationRules[name].type == "password") {
			if(!password(data,div_id))
				return false;
		}
		
		if(ValidationRules[name].type == "confirm_password") {
			if(!confirm_password(data,div_id))
				return false;
		}
			
		if (data.length < ValidationRules[name].min) {
			 if(!minimum(div_id)){
				 if(name=="bin"){
				     setDiv(div_id,"Bin length should be 6*");
				     return false;
				    }
			 }
				 return false;
		} 
		if (ValidationRules[name].type == "specialcharwithspace") {
			if(!specialcharwithspace(data,div_id))
				   return false;

		} 
			
		if (data.length > ValidationRules[name].max) {
			if(!maximum(div_id))
				return false;
		}
		
		if(ValidationRules[name].type == "email") {
			 if(!email(data,div_id))
				 return false;
		}
		if(ValidationRules[name].type == "dropdown") {
			if(!dropdown(data, div_id))
				return false;
		}
		
		if(ValidationRules[name].type == "confirmemail") {
			confirmemail(data,div_id);
		}else{
			return true;
		}
	}
	else {
		return true;
	}
	
}

function alpha(data,div_id) {
		var regex = /^[a-zA-Z ]*$/;
		if (regex.test(data)) {
			setDiv(div_id,"");
			return true;
		} else {
			setDiv(div_id,webMessages.validationShouldcontainonlyalphabets);
			loadMsgTitleText();
			return false;
		}
	}
	
	function numericWithDash(data,div_id) {
		var regex = /^[0-9-]*$/;
		if (regex.test(data)) {
			setDiv(div_id,"");
			return true;
		} else {
			setDiv(div_id,webMessages.Shouldcontainonlynumericanddash);
			loadMsgTitleText();
			return false;
		}
	}
	
	function mandatorytrue(data,div_id) {
		if (data == "") {
			setDiv(div_id,webMessages.validationthisfieldismandatory);
			loadMsgTitleText();
			return false;
		} else {
			setDiv(div_id,"");
			return true;
		}
	}
	
	function mandatoryfalse(data,div_id) {
		if (data == "") {
			setDiv(div_id,"");
			return false;
		} 
		else{
			return true;
		}
	}

	function minimum(div_id) {
		
		setDiv(div_id,webMessages.invalidlength);
		loadMsgTitleText();
		return false;
	}

	function maximum(div_id) {
		setDiv(div_id,webMessages.exceedinglength);
		loadMsgTitleText();
		return false;
	}
	
	function numeric(data,div_id) {
		var regex =/^\d+$/;
		if(regex.test(data)){
		setDiv(div_id,"");
		return true;
		}else{
		setDiv(div_id,webMessages.shouldcontainonlynumbers);
		loadMsgTitleText();
		   return false;
		}
	}
	
	function alphanumsplchar(data,div_id) {
		var regex =/^[A-Za-z0-9@][A-Za-z0-9!#$%^&*'()+\=\[\]{};:"\\|,.<>\/? ]*$/;
		if(regex.test(data)){
		setDiv(div_id,"");
		return true;
		}else{
		setDiv(div_id,webMessages.Shouldnotstartwithspecialchar);
		loadMsgTitleText();
		   return false;
		}
	}
	
	function dollarnumeric(data,div_id) {
		//	var regex =/^\$?[1-9]\d?(?:,\d{3})*(?:\.\d{2})?$/;
		//	var regex =/^\$?\d{1,3}(,\d{3})*(\.\d+)?$/;
			var regex =/^(\d{1,3}(\,\d{3})*|(\d+))(\.\d{2})?$/; 
		if(regex.test(data)){
		setDiv(div_id,"");
		return true;
		}else{
		setDiv(div_id,webMessages.improperFormat);
		loadMsgTitleText();
		   return false;
		}
	}
	
	function mobile(data,div_id) {
		var regex =/^[0-9()+-]*$/;
		if(regex.test(data)){
		setDiv(div_id,"");
		return true;
		}else{
		setDiv(div_id,webMessages.feeProgramOnlyNumber);
		loadMsgTitleText();
		   return false;
		}
	}
	
	function numericNotZero(data,div_id) {
		var regex =/^[1-9]\d*$/;
		if(regex.test(data)){
		setDiv(div_id,"");
		return true;
		}else{
		setDiv(div_id,"*Should contain only positive numbers*");
		   return false;
		}
	}
	
	function email(data,div_id) {
		var regex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$/;
		if(regex.test(data)){
		setDiv(div_id,"");	
		return true;	
		}else{
		setDiv(div_id,"*example@mail.com*");
		return false;
		}
	}
	
	function confirmemail(data,div_id) {
		var email =document.getElementById("emailid");
		if(data==emailid.value){
		return true;
		setDiv(div_id,"");
		}else{
			setDiv(div_id,"*The two fields arent the same*");
		return false;
		}
	}
	
	function disableaddress(element){
		 if(  document.getElementById("adresscheckbox").checked==true){
			 document.getElementById('paddresserrormsg').innerHTML="";
			 document.getElementById('paddress2errormsg').innerHTML="";
			 document.getElementById('pstateerrormsg').innerHTML="";
			 document.getElementById('pcityerrormsg').innerHTML="";
			 document.getElementById('pcountryerrormsg').innerHTML="";
			 document.getElementById('pziperrormsg').innerHTML="";
			 paddressid.value=addressid.value;
			 paddressid.readOnly=true;
			 paddress2id.value=address2id.value;
			 paddressid.readOnly=true;
			 pstateid.value=stateid.value;
			 pstateid.readOnly=true;
			 pcityid.value=cityid.value;
			 pcityid.readOnly=true;
			 pcountryid.value=countryid.value;
			 pcountryid.readOnly=true;
			 pzipid.value=zipid.value;
			 pzipid.readOnly=true;
		  }else{
			 paddressid.value="";
			 paddressid.readOnly=false;
			 paddress2id.value="";
			 paddressid.readOnly=false;
			 pstateid.value="";
			 pstateid.readOnly=false;
			 pcityid.value="";
			 pcityid.readOnly=false;
			 pcountryid.value="";
			 pcountryid.readOnly=false;
			 pzipid.value="";
			 pzipid.readOnly=false;
			 }
		}
	
	function alphaNumeric(data,div_id){
		var regex = /^[A-Za-z0-9 ]+$/;
		if(regex.test(data)){
			return true;
			}else{
			setDiv(div_id,"*Can contain only alphabets and numerics*");
			 return false;
		    }	
	}
	
	function username(data,div_id){
		var regex = /^[A-Za-z0-9 _]+$/;
		if(regex.test(data)){
			return true;
			}else{
			setDiv(div_id,"*Can contain only alphabets and numerics*");
			 return false;
		    }	
	}

	function password(data,div_id) {
		var regex = /^(?=.*)(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]+$/;
		if(regex.test(data)){
			return true;
		}else{
			setDiv(div_id,"*Should contain min 1 Caps 1 Special char and 1 Number*");
			return false;
		}
	}
	
	function confirm_password(data,div_id) {
		//var password=document.getElementById("passwordid");
		var password=document.getElementById("newPassword");
		if(data==password.value){
			return true;
		}else{
			 setDiv(div_id,"*The two fields arent the same*");
			 return false;
		}
	}
	
	function dropdown(data,div_id)  {
		if(data==""){
			 setDiv(div_id,webMessages.feeProgramMandatory);
			 loadMsgTitleText();
			 return false;
		}else{
			return true;
			}
	}

function specialchar(data,div_id)  {
		if(data==""){
			 setDiv(div_id,webMessages.feeProgramMandatory);
			 loadMsgTitleText();
			 return false;
		}else if(!isValidCharsExtended(data)){
		setDiv(div_id,"Invalid input");			
			return false;
		}else
			return true;
	}

	function editPartner(partnerId){
		  get('getPartnerId').value = partnerId;
		  document.forms["editPartnerForm"].submit();
	}
	
	function resetErrorSuccessMessage() {
		document.getElementById("sucessDiv").innerHTML = "";
		document.getElementById("errorDiv").innerHTML = "";
		get('sucessDiv').value = "&nbsp;";
		get('errorDiv').value = "&nbsp;";
	}

	function clearErrorMsg(divName) {
		setDiv(divName, "&nbsp;");
		resetErrorSuccessMessage();
	}
	
	function isValidFNameLNameChars(val) {
		var firstCharAlphaRegex = /^[A-Za-z][-\'&\/.()-,A-Za-z0-9 ]*$/;
		return firstCharAlphaRegex.test(val);
	}

	function isValidAddressChars(val) {
		var firstCharAlphaRegex = /^[A-Za-z0-9#][-$#@,'.;:'&A-Za-z0-9 ]*$/;
		return firstCharAlphaRegex.test(val);
	}

	function isValidCityChars(val) {
		var firstCharAlphaRegex = /^[A-Za-z0-9][-,.;'&\/.()A-Za-z0-9 ]*$/;
		return firstCharAlphaRegex.test(val);
	}

	function isValidCharsExtended(val) {
		var firstCharAlphaRegex = /^[A-Za-z0-9@][A-Za-z0-9!#$%^&*()+\=\[\]{};:"\\|,.<>\/? ]*$/;
		return firstCharAlphaRegex.test(val);
	}
	
	function isValidCharsExtendedWithSpace(val) {
		  var firstCharAlphaRegex = /^[A-Za-z0-9!@#$%^&*()+\=\[\]{};:"\\|,.<>\/? ]*$/;
		  return firstCharAlphaRegex.test(val);
		 }
	
	/*Create bin page validation*/
	function createBinValidation() {
		flag = true;
		if (!clientValidation('bin','bin','binerrormsg')
				| !clientValidation('bankId','bin_bank_dropdown','bankerrormsg')
				| !clientValidation('schema','bin_payment_schema_dropdown','schemaerrormsg')
				| !clientValidation('partnerName','bin_partner_name','partnernameerrormsg')
				| !validateEffectiveDate()
			) {
			flag = false;
			return flag;
		}
		return flag;
	}
	
	function validateEffectiveDate(){
		var today = new Date(); 
		var todayDate = today.getDate(); 
		var todayMonth = today.getMonth() + 1; 
		var todayYear = today.getFullYear(); 

		var currentEffDT = todayDate+'/'+todayMonth+'/'+todayYear; 

		var selectedEffDT = getVal('effectiveDate');

		if (selectedEffDT == '' || selectedEffDT.length == 0){
			setDiv("dateerrormsg",webMessages.feeProgramMandatory);
			loadMsgTitleText();
			return false;
		}else if(Date.parse(currentEffDT) > Date.parse(selectedEffDT)){
			setDiv("dateerrormsg","Effective Date should not be older date");
			return false;
		}else{
			setDiv("dateerrormsg"," ");
			return true;
		}
	}
	/*End of create bin page validation*/
	
	/*Edit bin page validation*/
	function editBinValidation() {
		flag = true;
		if (!clientValidation('schema','bin_payment_schema_dropdown','schemaerrormsg')
				| !clientValidation('partnerName','bin_partner_name','partnernameerrormsg')
				| !clientValidation('effectiveDate','bin_date','dateerrormsg')) {
			flag = false;
			return flag;
		}
		return flag;
	}

	/*End of edit bin page validation*/

	function readPartnerLogo(input,div_id) {
		if (!mandatorytrue(input.value, div_id)) {
			return false;
		}
		if (!isValidImage(input.value)) {
			document.getElementById(div_id).innerHTML = message.invalidImageFormat;
			return false;
		}
		document.getElementById(div_id).innerHTML = '';
		if (input.files && input.files[0]) {
			if (parseInt(MAX_FILE_SIZE) < parseInt(input.files[0].size)) {
				document.getElementById(div_id).innerHTML = message.IN_IMAGE_SIZE;
				return false;
			}
		}
		return true;
	}

	function isValidImage(imageSrc) {
		var value = imageSrc.toUpperCase();
		if (value.indexOf('.PNG') != -1 || value.indexOf('.JPG') != -1
				|| value.indexOf('.JPEG') != -1 || value.indexOf('.GIF') != -1
				|| value.indexOf('.BMP') != -1) {
			return true;
		}
		return false;
	}

	/*Reset Search bin*/
	
	function numericDecimal(data,div_id) {
		var regex =/^[0-9]+(\.[0-9][0-9]?)?$/;  
		if(regex.test(data)){
		setDiv(div_id,"");
		return true;
		}else{
		setDiv(div_id,webMessages.Enterpropervalue);
		   return false;
		}
	}
	
	function percentage(data,div_id) {
		var regex =/^\d{0,2}(\.\d{0,2}){0,1}$/;
		if(regex.test(data)){
		setDiv(div_id,"");
		return true;
		}else{
		setDiv(div_id,webMessages.feeProgramOnlyNumber);
		loadMsgTitleText();
		   return false;
		}
	}
	
	function specialcharwithspace(data,div_id)  {
		if(data==""){
			 setDiv(div_id,webMessages.feeProgramMandatory);
			 loadMsgTitleText();
			 return false;
		}else if(!isValidCharsExtendedWithSpace(data)){
		setDiv(div_id,webMessages.Invalidinput);
		loadMsgTitleText();			
			return false;
	}else
			return true;
	}
	
	function validateFromDate(divId, errorMsg) {
		var flag = true;
		var fromDate = document.getElementById('startDate').value;
		if (!eairlyDate(fromDate)) {
			setDiv(errorMsg, "From date can not be future date");
			flag = false;
			return flag;
		} else {
			setDiv(errorMsg, "&nbsp;");
			return flag;
		}
		setDiv(errorMsg, "&nbsp;");
		return flag;
	}

	function validateToDate(divId, errorMsg) {
		var flag = true;
		var toDate = document.getElementById('endDate').value;
		if (!eairlyDate(toDate)) {
			setDiv(errorMsg, "To date should be greater then present date");
			flag = false;
			return flag;
		} else {
			setDiv(errorMsg, "&nbsp;");
		}if (flag) {
			flag = validateDates(divId, errorMsg);
			if (!flag)
				return flag;
		}
		setDiv(errorMsg, "&nbsp;");
		return flag;
	}
	
	function validateDates(divId, errorMsg) {
		var flag = true;
		var toDate = document.getElementById('endDate').value;
		var fromDate = document.getElementById('startDate').value;

		var toDay = parseInt(toDate.split("/")[0]);
		var toMonth = parseInt(toDate.split("/")[1]);
		var toYear = parseInt(toDate.split("/")[2]);
		var newToDate = new Date(toYear, toMonth, toDay);

		var fromDay = parseInt(fromDate.split("/")[0]);
		var fromMonth = parseInt(fromDate.split("/")[1]);
		var fromYear = parseInt(fromDate.split("/")[2]);
		var newFromDate = new Date(fromYear, fromMonth, fromDay);

		if (toDate != "") {
			if (newFromDate > newToDate) {
				flag = false;
				setDiv(errorMsg, "from date can not be greater than to date");
			} else {
				setDiv(errorMsg, "&nbsp;");
			}
		}
		return flag;
	}

	function futureDate(idate) {
		var flag = true;
		var today = new Date();
		var todayDate = today.getDate();
		var todayMonth = today.getMonth() + 1;
		var todayYear = today.getFullYear();

		var newDay = parseInt(idate.split("/")[0]);
		var newMonth = parseInt(idate.split("/")[1]);
		var newYear = parseInt(idate.split("/")[2]);

		var newDate = new Date(newYear, newMonth, newDay);
		var currentDate = new Date(todayYear, todayMonth, todayDate);

		if (newDate > currentDate) {
			flag = false;
			return flag;
		}
		return flag;
	}
	
	function eairlyDate(idate) {
		var flag = true;
		var today = new Date();
		var todayDate = today.getDate();
		var todayMonth = today.getMonth() + 1;
		var todayYear = today.getFullYear();

		var newDay = parseInt(idate.split("/")[0]);
		var newMonth = parseInt(idate.split("/")[1]);
		var newYear = parseInt(idate.split("/")[2]);

		var newDate = new Date(newYear, newMonth, newDay);
		var currentDate = new Date(todayYear, todayMonth, todayDate);

		if (newDate < currentDate) {
			flag = false;
			return flag;
		}
		return flag;
	}
	
//	function validateRadio(err) {
//	if (get('No').checked == false
//			&& get('Yes').checked == false) {
//		setDiv(err, webMessages.pleaseSelectOne);
//		return false;
//	}else{
//		setDiv(err, '');
//		return true;
//		
//	}
//}
