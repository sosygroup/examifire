"use strict";

var ConfirmDeleteEvent = function (){
	var confirmDelete =function(){
		$('a.confirm-delete').click(function (e) {
			e.preventDefault();
            var tthis = $(this);
			
            swal.fire({
		        title: 'Are you sure?',
		        html: "You are going to delete <b>"+tthis.data("user-firstname-lastname")+"</b><br><br>You won't be able to revert this!",
		        type: 'warning',
		        showCancelButton: true,
		        confirmButtonText: 'Delete',
		        reverseButtons: true
		    }).then(function(result) {
		        if (result.value) {
		        	swal.fire({
		                title: 'Auto close alert',
		                text: 'Deletion in process...',
		                //timer: 2000,
		                onOpen: function() {
		                    swal.showLoading();
		                    document.location.href = tthis.attr('href');
		                }
		            }).then(function(result) {
	                    if (result.dismiss === 'timer') {
	                        // the timer is finished
	                    }
	                })
		        }
		        
		    });
               
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
			ajax: '/home/admin/users/datatable.jquery',
			columns: [
				{data: 'id'},
				{data: 'username'},
				{data: 'firstname'},
				{data: 'lastname'},
				{data: 'email'},
				{data: 'roles'},
				{'': '', responsivePriority: -1}
			],
			columnDefs: [
				{	data: null,
					targets: -1,
					orderable: false,
					render: function(data, type, full, meta) {
						return '\
	                  <span class="dropdown">\
	                      <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" data-toggle="dropdown" aria-expanded="true">\
	                        <i class="la la-ellipsis-h"></i>\
	                      </a>\
	                      <div class="dropdown-menu dropdown-menu-right">\
	                          <a class="dropdown-item" href="/home/admin/users/edit/'+full['id']+'"><i class="la la-edit"></i> Edit Details</a>\
	                          <a class="dropdown-item confirm-delete" data-user-firstname-lastname="'+full['firstname']+' '+full['lastname']+'" href="/home/admin/users/delete/'+full['id']+'"><i class="la la-trash"></i> Delete User</a>\
	                      </div>\
	                  </span>';
	              }
				},
				{
					targets: -2,
					render: function(data, type, full, meta) {
						var roles = {
							1: {'ROLE_NAME':'ADMIN', 'class': 'kt-badge--danger'},
							2: {'ROLE_NAME':'TEACHER' , 'class': 'kt-badge--primary'},
							3: {'ROLE_NAME':'STUDENT' , 'class': 'kt-badge--success'},
							//4: {'ROLE_NAME': '??', 'class': 'kt-badge--brand'},
							//5: {'ROLE_NAME': '??', 'class': ' kt-badge--metal'},
							//6: {'ROLE_NAME': '??', 'class': ' kt-badge--info'},
							//7: {'ROLE_NAME': '??', 'class': ' kt-badge--warning'}
						};
						var return_string="";
						data.forEach(function(role) {
							var color_span = "kt-badge--warning";
							if (typeof roles[role.id] != 'undefined') {
								color_span = roles[role.id].class;
							}
							return_string=return_string+'<span class="kt-badge kt-badge--inline kt-badge--pill ' + color_span + '">' + role.name + '</span>';
						});
					
						return return_string;
					},
				},
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
	
	/*
    
	$('#userTable').dataTable( {
        "ajax": {
            "url":"/home/admin/users/datatable.jquery",
            "data":function(d) {
                var table = $('#userTable').DataTable()
                d.page = (table != undefined)?table.page.info().page:0
                d.size = (table != undefined)?table.page.info().length:5
                d.sort = d.columns[d.order[0].column].data + ',' + d.order[0].dir
            }
        },
        "searching":true,
        "processing": true,
        "serverSide": true,
        //"lengthMenu": [[5, 10, 15,30,50,75,100], [5, 10, 15,30,50,75,100]],
        "columns": [
            { "data": "id" },
            { "data": "username" },
            { "data": "firstname" },
            { "data": "lastname" },
            { "data": "email" },
            { "data": "roles" },
            { "": "" }
        ],
        "columnDefs": [
            { "data": null, 
              "targets": -1,
              render: function(data, type, full, meta) {
					return '\
                  <span class="dropdown">\
                      <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" data-toggle="dropdown" aria-expanded="true">\
                        <i class="la la-ellipsis-h"></i>\
                      </a>\
                      <div class="dropdown-menu dropdown-menu-right">\
                          <a class="dropdown-item" href="#"><i class="la la-edit"></i> Edit Details</a>\
                          <a class="dropdown-item" href="#"><i class="la la-leaf"></i> Update Status</a>\
                          <a class="dropdown-item" href="#"><i class="la la-print"></i> Generate Report</a>\
                      </div>\
                  </span>\
                  <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="View">\
                    <i class="la la-edit"></i>\
                  </a>';
              }
             // "defaultContent":"<h4><a class='dt-view'></a><a class='dt-edit'></a><a class='dt-delete'></a></h4>" 
            }
        ],
        "pagingType": "full_numbers"

    } );
    */	
});
