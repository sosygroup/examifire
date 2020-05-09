"use strict";

var ConfirmDeleteEvent = function (){
	var confirmDelete = function(){
		$('a.confirm-delete').click(function (e) {
			e.preventDefault();

			ExamifireMessageUtil.showConfirmationMessage('warning', 'Are you sure?', 
            		"You are going to delete <b>"+$(this).data("user-firstname-lastname")+"</b><br><br>You won't be able to revert this!", 
            		'Delete', 'Auto close dialog', 'Deletion in progress...', $(this).attr('href'));               
        });
	}
	
	return {
		//main function to initiate the module
		init: function() {
			confirmDelete();
		}
	};
}();


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
	
	var saveNavigationTabActiveLink = function() {
		$("#tab_profile").click(function() {
			$("#navigation_tab_active_link").val("profile");
		});
		$("#tab_account").click(function() {
			$("#navigation_tab_active_link").val("account");
		});
		$("#tab_security").click(function() {
			$("#navigation_tab_active_link").val("security");
		});
	}
	
	
	var applySelectPickerToRole = function(){
		$('.kt-selectpicker').selectpicker();
	}

	return {
		// public functions
		init : function() {
			saveUserFormAndContinueEvent();
			saveUserFormAndCloseEvent();
			saveNavigationTabActiveLink();
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
	KTBootstrapSwitch.init();
	UserEdit.init();
	ConfirmDeleteEvent.init();
		
	if($("#confirm_crud_operation").val() == 'update_succeeded') {
		ExamifireMessageUtil.showMessage("success",false,"fas fa-check","The user has been updated!")
	}	
	if($("#confirm_crud_operation").val() == 'update_failed') {
		ExamifireMessageUtil.showMessage("danger",false,"fas fa-exclamation-triangle","Update failed, please check the errors!")
	}
	
});