"use strict";

var ConfirmDeleteEvent = function (){
	// this function is called only after the datatable is fully loaded, 
	// see the function 'fnDrawCallback' in the datatable 
	var confirmDelete = function(){
		$('a.confirm-delete').click(function (e) {
			e.preventDefault();

			MessageUtil.showConfirmationMessage('warning', 'Are you sure?', 
            		"You are going to delete <b>"+$(this).data("user-firstname-lastname")+"</b><br><br>You won't be able to revert this!", 
            		'No, cancel','Yes, delete it', 'Auto close dialog', 'Deletion in progress...', $(this).attr('href'));               
        });
	}
	
	return {
		//main function to initiate the module
		init: function() {
			confirmDelete();
		}
	};
}();

var KTDatatablesAdvancedColumnRendering = function() {

	var _initDatatable = function() {
		// begin first table
		var table = $('#kt_datatable').DataTable({
			// https://datatables.net/examples/basic_init/dom.html
			// https://datatables.net/reference/option/dom
			dom :"<'row mb-5'<'col-sm-12'B>>" + 
			"<'row'<'col-sm-12 col-md-6'l><'col-sm-12 col-md-6'f>>" +
			"<'row'<'col-sm-12'tr>>" +
			"<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",
			responsive: true,
			searchDelay: 500,
			processing: true,
			serverSide: true,
			pagingType: 'full_numbers',
			ajax:{
			    url: MY_HOST_URL+'/home/admin/teacher/edit/'+$("#persistent_user_id").val()+'/courses-datatable',
			    type: 'GET'
		    },
		    columns: [
				{data: 'id'},
				{data: 'name'},
				{data: 'description'},
				{data: 'cfu_ects'},
				{data: 'academicYear'},
				{data: null,
				    className: "text-nowrap",
					responsivePriority: -1,
					orderable: false,
				    searchable: false,
					render: function(data, type, full, meta) {
						return '\
						<!--<div class="dropdown dropdown-inline">\
							<a href="javascript:;" class="btn btn-sm btn-clean btn-icon" data-toggle="dropdown">\
                                <i class="la la-cog"></i>\
                            </a>\
						  	<div class="dropdown-menu dropdown-menu-sm dropdown-menu-right">\
								<ul class="nav nav-hoverable flex-column">\
						    		<li class="nav-item"><a class="nav-link" href="#"><i class="nav-icon la la-edit"></i><span class="nav-text">Edit Details</span></a></li>\
						    		<li class="nav-item"><a class="nav-link" href="#"><i class="nav-icon la la-leaf"></i><span class="nav-text">Update Status</span></a></li>\
						    		<li class="nav-item"><a class="nav-link" href="#"><i class="nav-icon la la-print"></i><span class="nav-text">Print</span></a></li>\
								</ul>\
						  	</div>\
						</div>-->\
						<a href="'+MY_HOST_URL+'/home/admin/teacher/edit/'+full["id"]+'/account-info'+'" class="btn btn-sm btn-clean btn-icon" title="Edit details">\
							<i class="la la-edit"></i>\
						</a>\
						<a href="'+MY_HOST_URL+'/home/admin/teacher/delete/'+full["id"]+'" data-user-firstname-lastname="'+full['firstname']+' '+full['lastname']+'\
						   "class="btn btn-sm btn-clean btn-icon confirm-delete" title="Delete">\
							<i class="la la-trash"></i>\
						</a>\
					';
	              }
				}
			],
			fnDrawCallback: function( settings, json ) {
				// leave here so that each time we redraw the table, e.g., after searching,
				// we need to (re)initialize the ConfirmDeleteEvent for the delete button of the elements returned by the search
				ConfirmDeleteEvent.init();
	        },
	        initComplete : function() {
	       	},
	       	buttons: [
	            {
	            	extend: 'collection',
	                buttons: [ 'columnsToggle' ],
	                className: 'btn btn-outline-secondary'
	            },
		    	{
	                extend: 'print',
	                exportOptions: {
	                	stripHtml : false,
	                	columns: ':visible' // or [ 0, 2, 3, 4, 5, 6, 7, 8 ]
	                },
	                className: 'btn btn-outline-secondary'
	            },
				{
	                extend: 'copyHtml5',
	                exportOptions: {
	                	columns: ':visible' // or [ 0, 2, 3, 4, 5, 6, 7, 8 ]
	                },
	                className: 'btn btn-outline-secondary'
	            },
	            {
	                extend: 'excelHtml5',
	                exportOptions: {
	                	columns: ':visible' // or [ 0, 2, 3, 4, 5, 6, 7, 8 ]
	                },
	                className: 'btn btn-outline-secondary'
	            },
	            {
	                extend: 'csvHtml5',
	                exportOptions: {
	                	columns: ':visible' // or [ 0, 2, 3, 4, 5, 6, 7, 8 ]
	                },
	                className: 'btn btn-outline-secondary'
	            },
	            {
	                extend: 'pdfHtml5',
	                exportOptions: {
	                	stripHtml : false,
	                    columns: ':visible' // or [ 0, 2, 3, 4, 5, 6, 7, 8 ]
	                },
	                className: 'btn btn-outline-secondary'
	            },
			],
		});
	};

	return {
		//main function to initiate the module
		init: function() {
			_initDatatable();
		}
	};
}();

jQuery(document).ready(function() {
	KTDatatablesAdvancedColumnRendering.init();
    
    if($("#confirm_crud_operation").val() == 'update_succeeded') {
		MessageUtil.showMessage("success",false,"flaticon2-checkmark","Update successful")
	}	
	if($("#confirm_crud_operation").val() == 'update_failed') {
		MessageUtil.showMessage("danger",false,"flaticon-exclamation","Update failed")
	}
});