"use strict";

jQuery(document).ready(function() {
	
	if($("#confirm_crud_operation").val() == 'add_succeeded') {
		ExamifireMessageUtil.showMessage("success",false,"fas fa-check","The user has been registered!")
	}	
	
});