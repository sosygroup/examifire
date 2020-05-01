"use strict";

// Class definition
var UserEdit = function() {

	var saveUserFormAndContinueEvent = function() {
		$("#link-save-and-continue").click(function() {
			$("#save_and_continue").val(true);
			$("#user-edit-form").submit();
		});
	}
	
	var saveUserFormAndExitEvent = function() {
		$("#link-save-and-exit").click(function() {
			$("#save_and_continue").val(false);
			$("#user-edit-form").submit();
		});
	}
	
	var deactivateTheAccountEvent = function() {
		$("#deactivate_the_account").click(function() {
			ExamifireMessageUtil.showConfirmationMessage('warning', 'Are you sure?', 
		    		"You are going to deactive the account of <b>"+$('#firstname').val()+" "+$('#lastname').val()+"</b><br><br>You won't be able to revert this!", 
		    		'Deactivate', 'Auto close dialog', 'Deactivation in progress...', $(this).data('href'));               

		});
	}
	
	var expireThePasswordEvent = function() {
		$("#expire_the_password").click(function() {
			ExamifireMessageUtil.showConfirmationMessage('warning', 'Are you sure?', 
		    		"You are going to delete <b>"+$(this).data("user-firstname-lastname")+"</b><br><br>You won't be able to revert this!", 
		    		'Delete', 'Auto close dialog', 'Deletion in progress...', $(this).data('href'));               

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
			deactivateTheAccountEvent();
			expireThePasswordEvent();
			applySelectPickerToRole();
		}
	};
}();

var KTBootstrapSwitch = function() {

	  // Private functions
	  var demos = function() {
	    // minimum setup
	    $('[data-switch=true]').bootstrapSwitch();
	  };

	  return {
	    // public functions
	    init: function() {
	      demos();
	    },
	  };
	}();
	
jQuery(document).ready(function() {
	UserEdit.init();
	KTBootstrapSwitch.init();
	
	if($("#confirm_crud_operation").val() == 'update_succeeded') {
		ExamifireMessageUtil.showMessage("success",false,"fas fa-check","The user has been updated!")
	}	
	if($("#confirm_crud_operation").val() == 'update_failed') {
		ExamifireMessageUtil.showMessage("danger",false,"fas fa-exclamation-triangle","Update failed, please check the errors!")
	}
	if($("#confirm_crud_operation").val() == 'activate_succeeded') {
		ExamifireMessageUtil.showMessage("success",false,"fas fa-check","The user account has been activated!")
	}
	if($("#confirm_crud_operation").val() == 'deactivate_succeeded') {
		ExamifireMessageUtil.showMessage("success",false,"fas fa-check","The user account has been deactivated!")
	}
	if($("#confirm_crud_operation").val() == 'expire_succeeded') {
		ExamifireMessageUtil.showMessage("success",false,"fas fa-check","The user password has been expired!")
	}
	
});