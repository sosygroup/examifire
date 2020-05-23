"use strict";

//Class definition
var ExamifireMessageUtil = function() {
	
	var showMessage = function(type, title, icon, message) {
		// sample calls:
		// ExamifireMessageUtil.showMessage("success",false,"fas fa-check","The user has been updated!")
		// ExamifireMessageUtil.showMessage("danger",false,"fas fa-exclamation-triangle","Deletion failed!")
		var content = {};
		
	    content.message = message;
	    content.title = title;
	    if (icon){
	    	content.icon = 'icon ' + icon ;
	    }
	    
	    if (false) {
	        content.url = 'www.examifire.com';
	        content.target = '_blank';
	    }

	    var notify = $.notify(content, { 
	    	type: type,
	        allow_dismiss: true,
	        newest_on_top: false,
	        mouse_over:  false,
	        showProgressbar:  false,
	        spacing: 10,                    
	        timer: 2000,
	        placement: {
	            from: 'top', 
	            align: 'right'
	        },
	        offset: {
	            x: 30, 
	            y: 30
	        },
	        delay: 1000,
	        z_index: 10000,
	        animate: {
	            enter: 'animate__animated animate__bounce',
	            exit: 'animate__animated animate__bounce'
	        }
	    });
	}
	
	var showConfirmationMessage = function(type, title, html_message, confirm_button_text, 
			after_confirm_dialog_title, after_confirm_dialog_message, request_url){
		
		// sample call:
		/* ExamifireMessageUtil.showConfirmationMessage('warning', 'Are you sure?', 
		      "You are going to delete <b>"+$(this).data("user-firstname-lastname")+"</b><br><br>You won't be able to revert this!", 
		      'Delete', 'Auto close alert', 'Deletion in progress...', $(this).attr('href'));               
		*/
		swal.fire({
	        title: title,
	        html: html_message,
	        type: type,
	        showCancelButton: true,
	        confirmButtonText: confirm_button_text,
	        reverseButtons: true,
	    }).then(function(result) {
	        if (result.value) {
	        	swal.fire({
	                title: after_confirm_dialog_title,
	                text: after_confirm_dialog_message,
	                onOpen: function() {
	                    swal.showLoading();
	                    // The document.location.href object is used to get the current page address (URL) and to redirect the browser to a new page 
	                    document.location.href = request_url;
	                },
	            })
	        }
	        
	    });
	}
	
	return {
		// public functions
		init : function() {
		},
		showMessage : function(type, title, icon, message){
			showMessage(type, title, icon, message);
		},
		showConfirmationMessage : function(type, title, html_message, confirm_button_text, 
				after_confirm_dialog_title, after_confirm_dialog_message, request_url){
			showConfirmationMessage(type, title, html_message, confirm_button_text, 
					after_confirm_dialog_title, after_confirm_dialog_message, request_url);
		}
	};
	
}();

jQuery(document).ready(function() {
	ExamifireMessageUtil.init();
});