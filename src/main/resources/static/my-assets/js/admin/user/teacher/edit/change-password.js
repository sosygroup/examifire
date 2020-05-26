"use strict";

var ChangePassword = function() {
    // Private functions
    var _initForm = function() {
        $("#form_submit").click(function() {
            $("#profile_form").submit();
        });
    }

    return {
        // public functions
        init: function() {
            _initForm();
        }
    };
}();

jQuery(document).ready(function() {
	ChangePassword.init();
    
    if($("#confirm_crud_operation").val() == 'update_succeeded') {
		MessageUtil.showMessage("success",false,"flaticon2-checkmark","Update successful")
	}	
	if($("#confirm_crud_operation").val() == 'update_failed') {
		MessageUtil.showMessage("danger",false,"flaticon-exclamation","Update failed")
	}
});