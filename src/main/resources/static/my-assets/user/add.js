"use strict";

// Class definition
var UserEdit = function() {

	var saveUserFormAndAddNewEvent = function() {
		$("#link-save-and-add-new").click(function() {
			$("#save_and_add_new").val(true);
			$("#user-add-form").submit();
		});
	}
	
	var saveUserFormAndExitEvent = function() {
		$("#link-save-and-exit").click(function() {
			$("#save_and_add_new").val(false);
			$("#user-add-form").submit();
		});
	}
	
	var applySelectPickerToRole = function(){
		$('.kt-selectpicker').selectpicker();
	}

	return {
		// public functions
		init : function() {
			saveUserFormAndAddNewEvent();
			saveUserFormAndExitEvent();
			applySelectPickerToRole();
		}
	};
}();

jQuery(document).ready(function() {
	UserEdit.init();
	if($("#confirm_crud_operation").val() == 'add_succeeded') {
		ExamifireMessageUtil.showMessage("success",false,"fas fa-check","The user has been added!")
	}	
	if($("#confirm_crud_operation").val() == 'add_failed') {
		ExamifireMessageUtil.showMessage("danger",false,"fas fa-exclamation-triangle","Creation failed, please check the errors!")
	}
	
	
});