"use strict";

var AccountInfo = function() {
    // Private functions
    var _initForm = function() {
        $("#form_submit").click(function() {
            $("#profile_form").submit();
        });

        $("#form_reset").click(function() {
            $("#profile_form")[0].reset();
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
	AccountInfo.init();
    
    if($("#confirm_crud_operation").val() == 'update_succeeded') {
		ExamifireMessageUtil.showMessage("success",false,"flaticon2-checkmark","Update successful")
	}	
	if($("#confirm_crud_operation").val() == 'update_failed') {
		ExamifireMessageUtil.showMessage("danger",false,"flaticon-exclamation","Update failed")
	}
});