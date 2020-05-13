"use strict";


var ConfirmDeleteEvent = function (){
	// this function is called only after the datatable is fully loaded, 
	// see the function 'fnDrawCallback' in the datatable 
	var confirmDelete = function(){
		$('a.confirm-delete').click(function (e) {
			e.preventDefault();

			ExamifireMessageUtil.showConfirmationMessage('warning', 'Are you sure?', 
            		"You are going to delete <b>"+$(this).data("user-firstname-lastname")+"</b><br><br>You won't be able to revert this!", 
            		'Delete', 'Auto close dialog', 'Deletion in progress...', $(this).attr('href'));               
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

	var initTable1 = function() {
		var table = $('#kt_table_1');
		
		// begin first table
		table.DataTable({
			responsive: true,
			searchDelay: 500,
			processing: true,
			serverSide: true,
			pagingType: 'full_numbers',
			ajax:{
			    url: '/home/admin/users/datatable.jquery',
			    type: 'GET'
		    }, 
			columns: [
				{data: 'id'},
				{'Avatar': 'Avatar',
				    className: "dt-center",
					orderable: false,
				    searchable: false,
					render: function(data, type, full, meta) {
						var image_src = full['avatar'] == null ? '/my-assets/media/users/default.jpg' : 'data:image/jpg;base64,'+atob(full['avatar']);
					/*  var return_string ='\
                        <div class="kt-user-card-v2">\
                            <div class="kt-user-card-v2__pic">\
                                <img src="' + image_src + '" class="kt-img-rounded kt-marginless" alt="'+ full['firstname']+' '+full['lastname'] +'" style="width: 40px;height: 40px;">\
                            </div>\
                            <div class="kt-user-card-v2__details">\
                                <span class="kt-user-card-v2__name">' + full['firstname']+' '+full['lastname'] + '</span>\
                                <a href="#" class="kt-user-card-v2__email kt-link">' + full['email'] + '</a>\
                            </div>\
                        </div>';
					*/
						var return_string ='\
							<img src="' + image_src + '" class="kt-img-rounded kt-marginless" alt="'+ full['firstname']+' '+full['lastname'] +'" style="width: 40px;height: 40px;">\
							';
	                
						return return_string;
					},
				},
				{data: 'username'},
				{data: 'firstname'},
				{data: 'lastname'},
				{data: 'email'},
				{data: 'roles',
					orderable: false,
				    searchable: false,
					render: function(data, type, full, meta) {
						var return_string="";
						data.forEach(function(role) {
							return_string=return_string+'<span class="kt-badge kt-badge--inline kt-badge--pill kt-badge--brand my-badge-color">' + role.name + '</span>';
						});
					
						return return_string;
					},
				},
				{data: 'accountEnabled',
					render: function(data, type, full, meta) {
						var color_span="kt-badge--success my-input-switch-color__on";
						var text_span="Enabled";
						if (!data){
							color_span = "kt-badge--danger my-input-switch-color__off";
							text_span = "Disabled"
						}
						return '<span class="kt-badge kt-badge--inline kt-badge--pill ' + color_span + '">' + text_span + '</span>';
					},
				},
				{data: 'passwordNonExpired',
					render: function(data, type, full, meta) {
						var color_span="kt-badge--success my-input-switch-color__on";
						var text_span="Non Expired";
						if (!data){
							color_span = "kt-badge--danger my-input-switch-color__off";
							text_span = "Expired"
						}
						return '<span class="kt-badge kt-badge--inline kt-badge--pill ' + color_span + '">' + text_span + '</span>';
					},
				},
				{'Actions': 'Actions',
				    className: "dt-center",
					responsivePriority: -1,
					orderable: false,
				    searchable: false,
					render: function(data, type, full, meta) {
						return'\
						<div class="dropdown dropdown-inline">\
                        <button type="button" class="btn btn-default btn-hover-brand btn-elevate-hover  btn-circle btn-icon btn-sm btn-icon-md my-btn-multiple-actions-1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">\
                            <i class="flaticon-more-1"></i>\
                        </button>\
                        <div class="dropdown-menu dropdown-menu-right my-btn-multiple-actions-1__dropdown-menu">\
                        <a class="dropdown-item" href="/home/admin/users/edit/'+full['id']+'"><i class="la la-edit"></i> Edit Details</a>\
                        <a class="dropdown-item confirm-delete" data-user-firstname-lastname="'+full['firstname']+' '+full['lastname']+'" href="/home/admin/users/delete/'+full['id']+'"><i class="la la-trash"></i> Delete User</a>\
                        </div>\
                    </div>';
						
						/*'\
	                  <span class="dropdown">\
	                      <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" data-toggle="dropdown" aria-expanded="true">\
	                        <i class="la la-ellipsis-h"></i>\
	                      </a>\
	                      <div class="dropdown-menu dropdown-menu-right my-btn-multiple-actions-1__dropdown-menu">\
	                          <a class="dropdown-item" href="/home/admin/users/edit/'+full['id']+'"><i class="la la-edit"></i> Edit Details</a>\
	                          <a class="dropdown-item confirm-delete" data-user-firstname-lastname="'+full['firstname']+' '+full['lastname']+'" href="/home/admin/users/delete/'+full['id']+'"><i class="la la-trash"></i> Delete User</a>\
	                      </div>\
	                  </span>';*/
	              }
				}
			],
			fnDrawCallback: function( settings, json ) {
	        	ConfirmDeleteEvent.init();
	        },
		});
	};

	return {
		//main function to initiate the module
		init: function() {
			initTable1();
		}
	};
}();

jQuery(document).ready(function() {
	KTDatatablesAdvancedColumnRendering.init();
	
	if($("#confirm_crud_operation").val() == 'add_succeeded') {
		ExamifireMessageUtil.showMessage("success",false,"fas fa-check","The user has been added!")
	}
	
	if($("#confirm_crud_operation").val() == 'update_succeeded') {
		ExamifireMessageUtil.showMessage("success",false,"fas fa-check","The user has been updated!")
	}
	
	if($("#confirm_crud_operation").val() == 'delete_succeeded') {
		ExamifireMessageUtil.showMessage("success",false,"fas fa-check","The user has been deleted!")
	}
	
	if($("#confirm_crud_operation").val() == 'delete_failed') {
		ExamifireMessageUtil.showMessage("danger",false,"fas fa-exclamation-triangle","Deletion failed!")
	}
	
});
