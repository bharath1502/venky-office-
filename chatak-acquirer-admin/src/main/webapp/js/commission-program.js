function commissionProgramEdit(commissionProgramId) {
	get('getCommissionId').value = commissionProgramId;
	document.forms["commissionProgramEditForm"].submit();
}

function resetCommissionSearch() {
	window.location.href = 'show-commission-program-search';
}

function resetCommissionCreate() {
	window.location.href = 'show-commission-program-create';
}

function setError(ele, errorMsg) {
	get((ele.id+'Er')).innerHTML = errorMsg;
}

function commissionNames($this) 

{
	var Id = $($this).attr('id'); 
	var value =document.getElementById(Id).value.trim();
	var spaceRegx = /^[A-Za-z0-9@][A-Za-z0-9!#$%^&*'()+\=\[\]{};:"\\|,.<>\/? ]*$/;
	 if(value=="")
	 {
		 setError(get(Id), webMessages.commissionProgramCommissionName);
		 loadMsgTitleText();
			return false;
	 }
	 else if(value.length < 3)
	{
		 setError(get(Id), webMessages.commissionProgramCantlessthan3);
		 loadMsgTitleText();
			return false;
	}
	 else if (!spaceRegx.test(value)) {
			setError(get(Id), webMessages.commissionProgramInvalidCommissionName);
			loadMsgTitleText();
			return false;
		}
	 else
	 {
		 setError(get(Id),'');
			return true;
	 }
}

function statuss($this) 
{
	var Id = $($this).attr('id'); 
	 var value =document.getElementById(Id).value.trim();
	 if(value=="")
	 {
		 setError(get(Id), webMessages.commissionProgramStatus);
		 loadMsgTitleText();
			return false;
	 }
	 else
	 {
		 setError(get(Id),'');
			return true;
	 }
}

function commissionType($this) 
{
	var Id = $($this).attr('id'); 
	 var value =document.getElementById(Id).value.trim();
	 if(value=="")
	 {
		 setError(get(Id), webMessages.commissionProgramCommissionType);
		 loadMsgTitleText();
			return false;
	 }
	 else
	 {
		 setError(get(Id),'');
			return true;
	 }
}

function flatFee($this) 
{     
	var Id = $($this).attr('id'); 
   	 var value =document.getElementById(Id).value.trim();
   	 var reg = /([0-9]+\.)?[0-9]+/;
	 if(value=="")
	 {
		 setError(get(Id), webMessages.commissionProgramFlatFee);
		 loadMsgTitleText();
			return false;
	 }
	 else if (!reg.test(value)) 
	 {
		setError(get(Id), webMessages.commissionProgramInvalidFlatFee);
		loadMsgTitleText();
		return false;
	 }
	 else
	 {
		 setError(get(Id),'');
			return true;
	 }
}

function toVolume($this) 
{
	var Id = $($this).attr('id'); 
	var value =document.getElementById(Id).value.trim();
  	var reg = /([0-9]+\.)?[0-9]+/;
	 if(value=="")
	 {
		 setError(get(Id), webMessages.commissionProgramToVolume);
		 loadMsgTitleText();
			return false;
	 }else if (!reg.test(value)) 
	 {
			setError(get(Id), webMessages.commissionProgramInvalidToVolume);
			loadMsgTitleText();
			return false;
	 }
	 else
	 {
		 setError(get(Id),'');
			return true;
	 }
}

function fromVolume($this) 
{     
	var Id = $($this).attr('id');
	 var value =document.getElementById(Id).value.trim();
	 var reg = /([0-9]+\.)?[0-9]+/;
	 if(value=="")
	 {
		 setError(get(Id), webMessages.commissionProgramFromVolume);
		 loadMsgTitleText();
			return false;
	 }
	 else if (!reg.test(value)) 
	 {
			setError(get(Id), webMessages.commissionProgramInvalidFromVolume);
			loadMsgTitleText();
			return false;
	 }
	 else
	 {
		 setError(get(Id),'');
			return true;
	 }
}

function amount($this) 
{    
	var Id = $($this).attr('id');
	 var value =document.getElementById(Id).value.trim();
	 var reg = /([0-9]+\.)?[0-9]+/;
	 if(value=="")
	 {
		 setError(get(Id), webMessages.commissionProgramAmount);
		 loadMsgTitleText();
			return false;
	 }
	 else if (!reg.test(value)) 
	 {
			setError(get(Id), webMessages.commissionProgramInvalidAmount);
			loadMsgTitleText();
			return false;
	 }
	 else
	 {
		 setError(get(Id),'');
			return true;
	 }
}

function validateMerchantOnBoardingFee()
{    
	var merchantOnBoardingFee = get('merchantOnBoardingFee').value.trim();
	var reg = /([0-9]+\.)?[0-9]+/;
	if(merchantOnBoardingFee != ""){
	 if (!reg.test(merchantOnBoardingFee)) 
	 {
			setError(get('merchantOnBoardingFee'), webMessages.commissionProgramInvalidMerchantOnBoardingFee);
			loadMsgTitleText();
			return false;
	 }
	 else
	 {
		 setError(get('merchantOnBoardingFee'),'');
		 loadMsgTitleText();
			return true;
	 }
	}
	else
	 {
		 setError(get('merchantOnBoardingFee'),'');
			return true;
	 }
}

function showValue(id)
	{
		var val = document.getElementById(id).value;
		if(val=="flat")
		{  $(".from input , .to input , .amnt input").each(function() {
			$(this).val('');
	    });
			$( ".flat").show();
			$( ".from").hide();
			$( ".to").hide();
			$( ".amnt").hide();
			$( ".delete-icon").hide();
			$( ".delete-icons").hide();
			$(".remove-row").remove();
			setError(get("otherCommissionDTO[0].flatFee"), '');
		}
		else if (val=="slab")
		{
			{  $(".flat input").each(function() {
				$(this).val('');
		    });
			$( ".from").show();
			$( ".to").show();
			$( ".flat").hide();
			$( ".amnt").show();
			$( ".delete-icons").show();
			setError(get("otherCommissionDTO[0].fromValue"), '');
			setError(get("otherCommissionDTO[0].toValue"), '');
			setError(get("otherCommissionDTO[0].amount"), '');
			setError(get("otherCommissionDTO[0].flatFee"), '');
			}
		}
		else
		{
			$( ".from").hide();
			$( ".to").hide();
			$( ".flat").hide();
			$( ".amnt").hide();
			$( ".delete-icon").hide();
		}
	}

function showValues(id)
	{
		var val = document.getElementById(id).value;
		if(val=="flat")
		{
			$( ".flat").show();
			$(".from input , .to input , .amnt input").each(function() {
				$(this).val('0');
		    });
   
			$( ".from").hide();
			$( ".to").hide();
			$( ".amnt").hide();
			$( ".delete-icon").hide();
			$( ".delete-icons").hide();
			$(".remove-row").remove();
			setError(get("otherCommissionDTO[0].flatFee"), '');
		}
		else if (val=="slab")
		{   
			$(".from input , .to input , .amnt input").each(function() {
				$(this).val('');
		    });
     	var count = $('.added-row > div').length;	
    	$('.added-row > div').not(":first").remove();
			$( ".flat").hide();
	       $('.filter-content .from').show();
		    $('.filter-content .to').show();
		    $('.filter-content .amnt').show();
	       
			setError(get("otherCommissionDTO[0].fromValue"), '');
			setError(get("otherCommissionDTO[0].toValue"), '');
			setError(get("otherCommissionDTO[0].amount"), '');
			setError(get("otherCommissionDTO[0].flatFee"), '');
			 $('.discriptionErrorMsg span').each(function() {
					$(this).val('');
			    });
		}
		else
		{
			$( ".from").hide();
			$( ".to").hide();
			$( ".flat").hide();
			$( ".amnt").hide();
			$( ".delete-icon").hide();
		}
	}