"use strict";


var KTDatatablesAdvancedColumnRendering = function() {

	var initTable1 = function() {
		var table = $('#kt_table_1');

		// begin first table
		table.DataTable({
			responsive: true,
			paging: true,
			columnDefs: [
				{
					
				}
			],
		});
	};

	var confirmDeleteuser =function(){
		$('a.confirm-delete-user').click(function (e) {
			e.preventDefault();
            var tthis = $(this);
			swal.fire({
		        title: 'Are you sure?',
		        html: "Delete the user "+tthis.data("user-firstname-lastname")+"<br><br>You won't be able to revert this!",
		        type: 'warning',
		        showCancelButton: true,
		        confirmButtonText: 'Yes, delete it!'
		    }).then(function(result) {
		        if (result.value) {
		        	document.location.href = tthis.attr('href');
		        } 
		    });
               
        });
	}
	
	return {

		//main function to initiate the module
		init: function() {
			initTable1();
			confirmDeleteuser();
		}
	};
}();

jQuery(document).ready(function() {
	KTDatatablesAdvancedColumnRendering.init();
	if($("#confirm_crud_operation").val() == 'update_succeeded') {
		myShowMessage("success",false,"fas fa-check","The user is updated!")
	}
	
	if($("#confirm_crud_operation").val() == 'delete_succeeded') {
		myShowMessage("success",false,"fas fa-check","The user is deleted!")
	}	
	
	
	
});
