"use strict";

function myShowMessage (type, title, icon, message) {
	var content = {};
	
    content.message = message;
    content.title = title;
    if (icon){
    	content.icon = 'icon ' + icon ;
    }
    
        if (false) {
        content.url = 'www.keenthemes.com';
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
            enter: 'animated bounce',
            exit: 'animated bounce'
        }
    });
}

jQuery(document).ready(function() {
	
});