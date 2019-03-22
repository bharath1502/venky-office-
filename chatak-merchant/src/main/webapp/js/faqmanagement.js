function searchFaqManagement(categoryMappingId) {
	get('categoryMappingId').value = categoryMappingId;
	document.forms["searchFaqManagement"].submit();
}