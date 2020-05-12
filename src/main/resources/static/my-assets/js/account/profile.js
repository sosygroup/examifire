"use strict";
var KTAvatarDemo = function () {
	// Private functions
	var initDemos = function () {
        var avatar3 = new KTAvatar('kt_user_avatar_3');
	}
	
	var bugfixAvatarInputFile = function() {
		$("input[name='profile_avatar']").change(function (){
			if (!this.value.length){
				$('.kt-avatar__cancel').click();
			}   
		});
	}
	
	var confirmResetAvatar = function(){
		$('#link-reset-avatar').click(function (e) {
			e.preventDefault();

			ExamifireMessageUtil.showConfirmationMessage('warning', 'Are you sure?', 
            		"You are going to reset your avatar!", 
            		'Reset', 'Auto close dialog', 'Reset in progress...', $(this).attr('href'));               
        });
	}

	return {
		// public functions
		init: function() {
			initDemos();
			bugfixAvatarInputFile();
			confirmResetAvatar();
		}
	};
}();


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
	}
	
	return {
		// public functions
		init : function() {
			saveUserFormAndContinueEvent();
			saveUserFormAndCloseEvent();
			saveNavigationTabActiveLink();
		}
	};
}();

jQuery(document).ready(function() {
	UserEdit.init();
	KTAvatarDemo.init();

	
	if($("#confirm_crud_operation").val() == 'update_succeeded') {
		ExamifireMessageUtil.showMessage("success",false,"fas fa-check","Update successful!")
	}	
	if($("#confirm_crud_operation").val() == 'update_failed') {
		ExamifireMessageUtil.showMessage("danger",false,"fas fa-exclamation-triangle","Update failed, please check the errors!")
	}
	
	
	
});