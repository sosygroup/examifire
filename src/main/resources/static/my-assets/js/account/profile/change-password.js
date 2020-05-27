"use strict";

var ChangePassword = function() {
	// Private functions
    var _handleSubmitForm = function (){
    	var validation;
        var form = KTUtil.getById('profile_form');

        // Init form validation rules. For more info check the FormValidation plugin's official documentation:https://formvalidation.io/
        validation = FormValidation.formValidation(
			form,
			{
				fields: {
					old_password: {
                        validators: {
                            notEmpty: {
                                message: '- The password is required'
                            },
                        }
                    },
					password: {
                        validators: {
                            notEmpty: {
                                message: '- The password is required'
                            },
                            regexp: {
                                regexp: '^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\\s).{8,15}$',
                                message: '- The password must have 8 to 15 characters which contain at least one lowercase letter, one uppercase letter, one numeric digit, and one special character'
                            },
                        }
                    },
                    confirm_password: {
                        validators: {
                            notEmpty: {
                                message: '- The password confirmation is required'
                            },
                            identical: {
                                compare: function() {
                                    return form.querySelector('[name="password"]').value;
                                },
                                message: '- The password and its confirm are not the same'
                            }
                        }
                    },
                    
				},
				plugins: {
					trigger: new FormValidation.plugins.Trigger(),
					bootstrap: new FormValidation.plugins.Bootstrap()
				}
			}
		);
        
        $("#form_submit").click(function() {
            validation.validate().then(function(status) {
                if (status == 'Valid') {
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
	ChangePassword.init();
    
    if($("#confirm_crud_operation").val() == 'update_succeeded') {
		MessageUtil.showMessage("success",false,"flaticon2-checkmark","Update successful")
	}	
	if($("#confirm_crud_operation").val() == 'update_failed') {
		MessageUtil.showMessage("danger",false,"flaticon-exclamation","Update failed")
	}
});