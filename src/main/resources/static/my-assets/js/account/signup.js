"use strict";

jQuery(document).ready(function() {
		
	if($("#confirm_crud_operation").val() == 'add_succeeded') {
		ExamifireMessageUtil.showMessage("success",false,"fas fa-check","The user has been registered!")
	}	
	if($("#confirm_crud_operation").val() == 'add_failed') {
		ExamifireMessageUtil.showMessage("danger",false,"fas fa-exclamation-triangle","Registration failed, please check the errors!")
	}
	
});