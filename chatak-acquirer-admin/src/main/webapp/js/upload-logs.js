function validateAdminPortalLog() {     
	 if(document.getElementById("adminLog")) {	
		 $('#selectedLog').val("adminLog");
		document.forms["poratlsLogDownload"].submit();			
	}
	  
}	
function validateMerchantPortalLog() {     
	 if(document.getElementById("merchantLog")) {
		 $('#selectedLog').val("merchantLog");
		document.forms["poratlsLogDownload"].submit();				
	} 			
}

function validateCatalinaLog() {     
	 if(document.getElementById("catalinaLog")) {
		 $('#selectedLog').val("catalinaLog");
		document.forms["poratlsLogDownload"].submit();				
	} 			
}

 function downloadFormLog(id,name){
	 $('#downloadLog').val(id);
	 $('#logName').val(name);
	 document.forms["downloadFormById"].submit();
}