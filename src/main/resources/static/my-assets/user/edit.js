"use strict";

// Class definition
var UserEdit = function() {

	var saveUserFormAndContinueEvent = function() {
		$("#link-save-and-continue").click(function() {
			$("#saveAndContinue").val(true);
			$("#user-edit-form").submit();
		});
	}
	
	var saveUserFormAndExitEvent = function() {
		$("#link-save-and-exit").click(function() {
			$("#saveAndContinue").val(false);
			$("#user-edit-form").submit();
		});
	}
	
	var applySelectPickerToRole = function(){
		$('.kt-selectpicker').selectpicker();
	}

	return {
		// public functions
		init : function() {
			saveUserFormAndContinueEvent();
			saveUserFormAndExitEvent();
			applySelectPickerToRole();
		}
	};
}();

jQuery(document).ready(function() {
	UserEdit.init();
	if($("#confirm_crud_operation").val() == 'update_succeeded') {
		myShowMessage("success",false,"fas fa-check","The user is updated!")
	}	
	if($("#confirm_crud_operation").val() == 'update_failed') {
		myShowMessage("danger",false,"fas fa-exclamation-triangle","Please check the errors!")
	}
	
	
});