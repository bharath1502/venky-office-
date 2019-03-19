function validateFormForFaqManagement(){
	if (! clientValidation('categoryId','categoryName','categoryIdErrorDiv')
			| !clientValidation('moduleId','moduleName','moduleIdErrorDiv')
			| !clientValidation('questionName', 'questionName','questionNameErrorDiv')
			| !clientValidation('questionAnswer', 'questionAnswer','questionAnswerErrorDiv')
			
			){
		return false;
	}
	return true;	
}

function editFaqData(faqId){
	get('faqIdData').value = faqId;
	document.forms["editFaqForm"].submit();
}

function changeFaqStatus(faqId, status, statusName) {
	setDiv("sts", webMessages.Faq_status + status + '?');
	get('faqId').value = id;
	get('status').value = status;
}


function fetchModuleNameForCat(id) {
	
	if(id == ""){
		return;
	}
	 doAjaxForModule(id)
}

function doAjaxForModule(categoryId){
	
	
	$.ajax({
		type : "GET",
		url : "fetchModuleNameForCat?categoryId=" + categoryId,
		success : function(response) {
			 // we have the response
			   var obj = JSON.parse(response);
			   document.getElementById('moduleId').options.length = 0;
			   var selectOption1 = document.createElement("option");
			   selectOption1.innerHTML = webMessages.Select;
			   selectOption1.value = "";
			   $("#moduleId").append(selectOption1);
			   
			   if ( obj.errorMessage == "SUCCESS") {
					var moduleData = obj.faqManagementList;
					
					for ( var i = 0; i < moduleData.length; i++) {

						var moduleNames = moduleData[i].moduleName;
						var moduleIds = moduleData[i].moduleId;

							var newOption = document.createElement("option");
							newOption.value = moduleIds;
							newOption.innerHTML = moduleNames;

							$("#moduleId").append(newOption);
					}
			   }
		},
		error : function(e) {
		}
	});
			   
}
