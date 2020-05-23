"use strict";

// Class definition
var KTProfileAside = function () {
	// Elements
	var offcanvas;

	// Private functions
	var _initAside = function () {
		// Mobile offcanvas for mobile mode
		offcanvas = new KTOffcanvas('kt_profile_aside', {
            overlay: true,
            baseClass: 'offcanvas-mobile',
            //closeBy: 'kt_user_profile_aside_close',
            toggleBy: 'kt_subheader_mobile_toggle'
        });
	}

	return {
		// public functions
		init: function() {
			_initAside();
		}
	};
}();

jQuery(document).ready(function() {
	KTProfileAside.init();
});
