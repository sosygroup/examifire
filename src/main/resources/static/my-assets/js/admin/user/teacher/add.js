"use strict";

var AccountInfo = function() {
    // Private functions
    var _initForm = function() {
    	$("#form-submit-and-add-new").click(function() {
			$("#save_option").val("add_new");
			$("#user-add-form").submit();
		});
    	
    	$("#form-submit-and-edit").click(function() {
			$("#save_option").val("edit");
			$("#user-add-form").submit();
		});
    	
    	$("#form-submit-and-list-all").click(function() {
			$("#save_option").val("list_all");
			$("#user-add-form").submit();
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
	
	$('[data-switch=true]').bootstrapSwitch();
	
    if($("#confirm_crud_operation").val() == 'add_succeeded') {
		MessageUtil.showMessage("success",false,"flaticon2-checkmark","Add successful")
	}	
	if($("#confirm_crud_operation").val() == 'add_failed') {
		MessageUtil.showMessage("danger",false,"flaticon-exclamation","Add failed")
	}
});