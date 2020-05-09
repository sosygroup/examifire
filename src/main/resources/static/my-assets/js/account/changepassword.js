"use strict";

// Class definition
var UserEdit = function() {

	var saveUserFormAndContinueEvent = function() {
		$("#link-save-and-continue").click(function() {
			$("#save_and_continue").val(true);
			$("#user-edit-form").submit();
		});
	}
	
	var saveUserFormAndCloseEvent = function() {
		$("#link-save-and-close").click(function() {
			$("#save_and_continue").val(false);
			$("#user-edit-form").submit();
		});
	}
	
	return {
		// public functions
		init : function() {
			saveUserFormAndContinueEvent();
			saveUserFormAndCloseEvent();
		}
	};
}();

jQuery(document).ready(function() {
	UserEdit.init();
	
	
	if($("#confirm_crud_operation").val() == 'update_succeeded') {
		ExamifireMessageUtil.showMessage("success",false,"fas fa-check","The password has been updated!")
	}	
	if($("#confirm_crud_operation").val() == 'update_failed') {
		ExamifireMessageUtil.showMessage("danger",false,"fas fa-exclamation-triangle","Password update failed, please check the errors!")
	}
	
	
	
});