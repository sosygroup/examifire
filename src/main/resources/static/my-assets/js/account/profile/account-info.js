"use strict";

// Class definition
var Avatar = function() {
    var image;
    var $modal;
    var cropper;

    var _initAvatar = function() {
        image = $("#modal_image_avatar").get(0);
        $modal = $('#modal');

        var addSpinnerAvatar = function(){
        	$('#avatar_account_info').addClass('spinner spinner-center spinner-track spinner-primary spinner-lg');
            $('#avatar_aside').addClass('spinner spinner-center spinner-track spinner-primary spinner-lg');
        };
        
        var removeSpinnerAvatar = function(){
        	$('#avatar_account_info').removeClass('spinner spinner-center spinner-track spinner-primary spinner-lg');
            $('#avatar_aside').removeClass('spinner spinner-center spinner-track spinner-primary spinner-lg');
        };
        
        $("#file_avatar").change(function(e) {
            var _this = this;
            var files = e.target.files;
            var done = function(url) {
                $("#file_avatar").val('');
                image.src = url;
                $modal.modal('show');
            };
            var reader;
            var file;
            var url;

            if (files && files.length > 0) {
                file = files[0];

                if (URL) {
                    done(URL.createObjectURL(file));
                } else if (FileReader) {
                    reader = new FileReader();
                    reader.onload = function(e) {
                        done(reader.result);
                    };
                    reader.readAsDataURL(file);
                }
            }
        });

        $modal.on('shown.bs.modal', function() {
          cropper = new Cropper(image, {
        		dragMode: 'move',
        	    aspectRatio: 1,
        	    viewMode: 3,
            });
        }).on('hidden.bs.modal', function() {
        	cropper.destroy();
            cropper = null;
        });

        $("span[data-action='remove']").click(function() {
            addSpinnerAvatar();
            
            // We need to include the csrf token within all your Ajax requests, 
            // by setting it as request header.
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            
        	$.ajax('/home/profile/avatar/remove', {
                type: "POST",
                processData: false,
                contentType: false,
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function(data) {
                    window.location.href = data;
                },
                error: function(data) {
                    window.location.href = data.redirect;
                    removeSpinnerAvatar();
                }
            });
        });

        $("#modal_change_avatar").click(function() {
        	var initialAvatarURL;
            var canvas;
            
            addSpinnerAvatar();
            $modal.modal('hide');
            

            if (cropper) {
                canvas = cropper.getCroppedCanvas({
                    width: 160,
                    height: 160,
                });

                canvas.toBlob(function(blob) {
                    var formData = new FormData();
                    // We need to include the csrf token within all your Ajax requests, 
                    // by setting it as request header. 
                    var token = $("meta[name='_csrf']").attr("content");
                    var header = $("meta[name='_csrf_header']").attr("content");

                    formData.append('avatar', blob);

                    $.ajax('/home/profile/avatar/change', {
                        type: "POST",
                        data: formData,
                        processData: false,
                        contentType: false,
                        beforeSend: function(xhr) {
                            xhr.setRequestHeader(header, token);
                        },
                        success: function(data) {
                            window.location.href = data;
                        },
                        error: function(data) {
                            window.location.href = data.redirect;
                            removeSpinnerAvatar();
                        }
                    });

                });
            }
        });
    }


    return {
        // public functions
        init: function() {
            _initAvatar();
        }
    };
}();


var PersonalInfo = function() {
    // Private functions
    var _initForm = function() {
        $("#form_submit").click(function() {
            $("#profile_form").submit();
        });

        $("#form_reset").click(function() {
            $("#profile_form")[0].reset();
        });
    }

    return {
        // public functions
        init: function() {
            _initForm();
        }
    };
}();

jQuery(document).ready(function() {
    PersonalInfo.init();
    Avatar.init();


});