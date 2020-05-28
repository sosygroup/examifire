"use strict";

var AccountInfo = function() {
	// Private functions
    var _handleSubmitForm = function (){
    	var validation;
        var form = KTUtil.getById('profile_form');

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
	AccountInfo.init();
    
    if($("#confirm_crud_operation").val() == 'update_succeeded') {
		MessageUtil.showMessage("success",false,"flaticon2-checkmark","Update successful")
	}	
	if($("#confirm_crud_operation").val() == 'update_failed') {
		MessageUtil.showMessage("danger",false,"flaticon-exclamation","Update failed")
	}
});