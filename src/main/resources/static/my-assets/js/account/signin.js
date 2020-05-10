"use strict";

jQuery(document).ready(function() {
	
	if($("#confirm_crud_operation").val() == 'add_succeeded') {
		ExamifireMessageUtil.showMessage("success",false,"fas fa-check","You have been registered!")
	}
	
    var sPageURL = window.location.search.substring(1);
    if (sPageURL == 'error'){
    	ExamifireMessageUtil.showMessage("danger",false,"fas fa-exclamation-triangle","Login failed, please check your credentials!")	
    }
});