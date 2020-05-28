"use strict";

var TeacherAccount = function() {
    // Private functions
    var _handleSubmitForm = function (){
    	var validation;
        var form =  KTUtil.getById('user-add-form');

        // Init form validation rules. For more info check the FormValidation plugin's official documentation:https://formvalidation.io/
        validation = FormValidation.formValidation(
			form,
			{
				fields: {
					firstname: {
						validators: {
							notEmpty: {
								message: '- Firstname is required'
							},
							stringLength: {
		                        max: 45,
		                        message: '- Maximum 45 characters'
		                    },
						}
					},
					lastname: {
						validators: {
							notEmpty: {
								message: '- Lastname is required'
							},
							stringLength: {
		                        max: 45,
		                        message: '- Maximum 45 characters'
		                    },
						}
					},
					email: {
                        validators: {
							notEmpty: {
								message: '- Email address is required'
							},
							emailAddress: {
	                            message: '- Invalid email address'
	                        },
						}
					},
                    
				},
				plugins: {
					trigger: new FormValidation.plugins.Trigger(),
					bootstrap: new FormValidation.plugins.Bootstrap()
				}
			}
		);
        
        $("#form-submit-and-add-new").click(function() {
            validation.validate().then(function(status) {
                if (status == 'Valid') {
                    $("#save_option").val("add_new");
                    form.submit();
                } else {
                    MessageUtil.showValidationErrorMessage();
                }
            });
        });

        $("#form-submit-and-edit").click(function() {
            validation.validate().then(function(status) {
                if (status == 'Valid') {
                    $("#save_option").val("edit");
                    form.submit();
                } else {
                    MessageUtil.showValidationErrorMessage();
                }
            });
        });

        $("#form-submit-and-list-all").click(function() {
            validation.validate().then(function(status) {
                if (status == 'Valid') {
                    $("#save_option").val("list_all");
                    form.submit();
                } else {
                    MessageUtil.showValidationErrorMessage();
                }
            });
        });
      
    }

    return {
        // public functions
        init: function() {
            _handleSubmitForm();
        }
    };
}();

jQuery(document).ready(function() {
	TeacherAccount.init();
	
	$('[data-switch=true]').bootstrapSwitch();
	
    if($("#confirm_crud_operation").val() == 'add_succeeded') {
		MessageUtil.showMessage("success",false,"flaticon2-checkmark","Add successful")
	}	
	if($("#confirm_crud_operation").val() == 'add_failed') {
		MessageUtil.showMessage("danger",false,"flaticon-exclamation","Add failed")
	}
});