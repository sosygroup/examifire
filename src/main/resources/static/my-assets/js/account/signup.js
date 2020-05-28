"use strict";

// Class Definition
var KTLoginGeneral = function() {
    var _login;

    var _showForm = function(form) {
    	_login.addClass('login-signup-on');
        KTUtil.animateClass(KTUtil.getById('kt_login_signup_form'), 'animate__animated animate__backInUp');
    }

       var _handleSignUpForm = function(e) {
        var validation;
        var form = KTUtil.getById('kt_login_signup_form');

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
                    agree: {
                        validators: {
                            notEmpty: {
                                message: '- You must accept the terms and conditions'
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

        $('#kt_login_signup_submit').on('click', function (e) {
            e.preventDefault();

            validation.validate().then(function(status) {
            	if (status == 'Valid') {
                    form.submit();
                } else {
                    MessageUtil.showValidationErrorMessage();
                }
		    });
        });
    }

       // Public Functions
    return {
        // public functions
        init: function() {
            _login = $('#kt_login');

            _handleSignUpForm();
        },
    	showForm: function(){
    		_showForm();
    	}
    };
}();

// Class Initialization
jQuery(document).ready(function() {
    KTLoginGeneral.init();
    KTLoginGeneral.showForm();
    
    if($("#confirm_crud_operation").val() == 'add_failed') {
		MessageUtil.showMessage("danger",false,"fas fa-exclamation-triangle","Registration failed, please check the errors!")
	}	
});