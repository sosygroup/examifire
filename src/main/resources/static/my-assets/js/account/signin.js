"use strict";

// Class Definition
var KTLoginGeneral = function() {
    var _login;

    var _showForm = function() {
        _login.addClass('login-signin-on');
        KTUtil.animateClass(KTUtil.getById('kt_login_signin_form'), 'animate__animated animate__backInUp');
    }

    var _handleSignInForm = function() {
        var validation;
        var form = KTUtil.getById('kt_login_signin_form');

        // Init form validation rules. For more info check the FormValidation plugin's official documentation:https://formvalidation.io/
        validation = FormValidation.formValidation(
        	form,
			{
				fields: {
					username: {
						validators: {
							notEmpty: {
								message: 'Email is required'
							},
						}
					},
					password: {
						validators: {
							notEmpty: {
								message: 'Password is required'
							},
						}
					}
				},
				plugins: {
					trigger: new FormValidation.plugins.Trigger(),
					bootstrap: new FormValidation.plugins.Bootstrap()
				}
			}
		);

        $('#kt_login_signin_submit').on('click', function (e) {
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
            _handleSignInForm();
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
    
    if($("#confirm_crud_operation").val() == 'add_succeeded') {
		MessageUtil.showMessage("success",false,"fas fa-check","You have been registered!")
	}
	
    var sPageURL = window.location.search.substring(1);
    if (sPageURL == 'error'){
    	MessageUtil.showMessage("danger",false,"fas fa-exclamation-triangle","Login failed, please check your credentials!")	
    }
});
