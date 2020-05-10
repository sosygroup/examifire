"use strict";
//Class definition
var KTAvatarDemo = function () {
	// Private functions
	var initDemos = function () {
        var avatar3 = new KTAvatar('kt_user_avatar_3');
	}

	return {
		// public functions
		init: function() {
			initDemos();
		}
	};
}();


var UserEdit = function() {

	var saveUserFormAndAddNewEvent = function() {
		$("#link-save-and-add-new").click(function() {
			$("#save_and_add_new").val(true);
			$("#user-add-form").submit();
		});
	}
	
	var saveUserFormAndCloseEvent = function() {
		$("#link-save-and-close").click(function() {
			$("#save_and_add_new").val(false);
			$("#user-add-form").submit();
		});
	}
	
	var saveNavigationTabActiveLink = function() {
		$("#tab_profile").click(function() {
			$("#navigation_tab_active_link").val("profile");
		});
		$("#tab_account").click(function() {
			$("#navigation_tab_active_link").val("account");
		});
	}
	
	var applySelectPickerToRole = function(){
		$('.kt-selectpicker').selectpicker();
	}

	return {
		// public functions
		init : function() {
			saveUserFormAndAddNewEvent();
			saveUserFormAndCloseEvent();
			saveNavigationTabActiveLink();
			applySelectPickerToRole();
		}
	};
}();

jQuery(document).ready(function() {
	UserEdit.init();
	KTAvatarDemo.init();
	
	if($("#confirm_crud_operation").val() == 'add_succeeded') {
		ExamifireMessageUtil.showMessage("success",false,"fas fa-check","The user has been added!")
	}	
	if($("#confirm_crud_operation").val() == 'add_failed') {
		ExamifireMessageUtil.showMessage("danger",false,"fas fa-exclamation-triangle","Creation failed, please check the errors!")
	}
	
	
});