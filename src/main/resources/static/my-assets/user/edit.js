"use strict";

// Class definition
var UserEdit = function() {

	var saveUserFormAndContinueEvent = function() {
		$("#button-save-changes-user-edit-form-continue").click(function() {
			$("#saveAndContinue").val(true);
			$("#user-edit-form").submit();
		});
		
		$("#link-save-changes-user-edit-form-continue").click(function() {
			$("#saveAndContinue").val(true);
			$("#user-edit-form").submit();
		});
	}
	
	var saveUserFormAndExitEvent = function() {
		$("#link-save-changes-user-edit-form-exit").click(function() {
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
	
	
});