"use strict";

//Class definition
var MessageUtil = function() {
	
	var _showMessage = function(type, title, icon, message) {
		// sample calls:
		// MessageUtil.showMessage("success",false,"fas fa-check","The user has been updated!")
		// MessageUtil.showMessage("danger",false,"fas fa-exclamation-triangle","Deletion failed!")
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
	
	var _showConfirmationMessage = function(data){
		swal.fire({
	        title: data.title,
	        html: data.html_message,
	        icon: data.type,
	        showCancelButton: true,
	        confirmButtonText: data.confirm_button_text,
	        cancelButtonText: data.cancel_button_text,
	        reverseButtons: true,
	    }).then(function(result) {
	        if (result.value) {
	        	swal.fire({
	                title: data.after_confirm_dialog_title,
	                text: data.after_confirm_dialog_message,
	                onOpen: function() {
	                    swal.showLoading();
	                    // The document.location.href object is used to get the current page address (URL) and to redirect the browser to a new page 
	                    document.location.href = data.request_url;
	                },
	            })
	        }
	        
	    });
	}
	
	return {
		// public functions
		showMessage : function(type, title, icon, message){
			_showMessage(type, title, icon, message);
		},
		showConfirmationMessage : function(type, title, html_message, cancel_button_text, confirm_button_text, 
				after_confirm_dialog_title, after_confirm_dialog_message, request_url){
			// sample call:
			/* MessageUtil.showConfirmationMessage('warning', 'Are you sure?', 
	            		"You are going to delete <b>"+$(this).data("user-firstname-lastname")+"</b><br><br>You won't be able to revert this!", 
	            		'No, cancel','Yes, delete it', 'Auto close dialog', 'Deletion in progress...', $(this).attr('href'));               
			*/
			
			var data = {};
			data['type']= type;
			data['title']= title;
			data['html_message']= html_message;
			data['cancel_button_text']= cancel_button_text;
			data['confirm_button_text']= confirm_button_text;
			data['after_confirm_dialog_title']= after_confirm_dialog_title;
			data['after_confirm_dialog_message']= after_confirm_dialog_message;
			data['request_url']= request_url;
			_showConfirmationMessage(data);
		}
	};
	
}();

var UserUtil = function (){
	
	var _isAdmin = function(roles){
		return roles.find(r => r.name === "ADMIN") ? true : false;
	}
	
	return {
		// public functions
		isAdmin : function(roles){
			return _isAdmin(roles);
		},
	}
}();