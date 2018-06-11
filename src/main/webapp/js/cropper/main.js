/**
 * Created by Administrator on 2017/9/4.
 */


$(function () {
    var console = window.console || {
        log: function () {
        }
    };

    function CropAvatar() {
        this.$avatarModal = $('#avatar-modal');
        this.$loading = this.$avatarModal.find('.loading');

        this.$avatarForm = this.$avatarModal.find('.avatar-form');
        this.$avatarUpload = this.$avatarForm.find('.avatar-upload');
        this.$avatarSrc = this.$avatarForm.find('.avatar-src');
        this.$avatarData = this.$avatarForm.find('.avatar-data');
        this.$avatarInput = this.$avatarForm.find('.avatar-input');
        this.$avatarSave = this.$avatarForm.find('#avatar-save');
        this.$avatarBtns = this.$avatarForm.find('.buttons');

        this.$avatarWrapper = this.$avatarModal.find('.avatar-wrapper .avatar-img');
        this.$avatarPreview = this.$avatarModal.find('.avatar-preview .preview-container');
        this.$btnSure = this.$avatarModal.find('#btnSure');
        this.$btnCancel = this.$avatarModal.find('.cancel');

        this.systemAvatar = true;
        this.$tabs = $("#avatar-modal .portrait-nav");
        this.init();
    }

    CropAvatar.prototype = {
        constructor: CropAvatar,

        support: {
            fileList: !!$('<input type="file">').prop('files'),
            blobURLs: !!window.URL && URL.createObjectURL,
            formData: !!window.FormData,
            overIE9: $.support.changeBubbles,
            overIE10:$.support.cors
        },

        init: function () {
            this.support.datauri = this.support.fileList && this.support.blobURLs;

            this.initPreview();
            this.addListener();
            //IE8以下浏览器隐藏上传按钮
            this.support.overIE10 && $("li", this.$tabs).filter(":not('.current')").show();
        },

        addListener: function () {
            this.$avatarInput.on('change', $.proxy(this.change, this));
            this.$avatarSave.on('click', $.proxy(this.submit, this));
            this.$avatarBtns.on('click', $.proxy(this.rotate, this));
            this.$btnSure.on('click', $.proxy(this.uploadSystemPhoto, this));
            this.$btnCancel.on('click', $.proxy(this.closeModal, this));

            // //TABS绑定是否是系统图标
            // this.$tabs.on("click", "li", function (event) {
            //     var index = this.$tabs.find("li").index(event.target);
            //     this.systemAvatar = !index;
            // }.bind(this))

        },
        closeModal: function () {
            parent.popup.closeOpenPage(window.name);
        },
        uploadSystemPhoto: function () {
            var _this = this;
            var oSrc = $(".system-head").find(".current").find("img").attr("src");
            if (oSrc == undefined || oSrc == '') {
                this.alert('请选择头像');
                return false;
            }
            var oUrl = oSrc.slice(oSrc.lastIndexOf("/images"));
            parent.saveSystemPhoto(oUrl,oSrc);
            parent.popup.closeOpenPage(window.name);
            // $.ajax({
            //     type: 'post',
            //     url: "/user/uploadSystemUserFace",
            //     data: {"photoSrc": oUrl},
            //     dataType: 'json',
            //     success: function (data) {
            //         if (data.code == TLibSSO.codeNum.SUCCESS) {
            //             _this.submitDone(data);
            //         }
            //         else {
            //             _this.alert(data.message);
            //         }
            //     },
            //     error: function (data) {
            //         parent.popup.closePopup(index);
            //         this.alert(data.responseText);
            //     }
            // });
        },

        //初始化预览图片
        initPreview: function () {
            var url = parent.getAvatarSrc();
            if (url == undefined || url == '') return;
            this.$avatarPreview.html('<img src="' + url + '" style="width:100%;">');
        },

        change: function () {
            var files;
            var file;

            if (this.support.datauri) {
                files = this.$avatarInput.prop('files');

                if (files.length > 0) {
                    file = files[0];

                    if (this.isImageFile(file)) {
                        if (this.url) {
                            URL.revokeObjectURL(this.url); // Revoke the old one
                        }

                        this.url = URL.createObjectURL(file);
                        this.startCropper();
                    }
                }
            } else {
                //IE9以下浏览器隐藏上传按钮
                if (this.support.overIE9) {
                    var fileInput = this.$avatarInput.get(0);
                    fileInput.select();
                    this.$avatarSave.focus();
                    var realPath = document.selection.createRange().text;
                    this.url = 'file:\/\/\/' + realPath.replace(/\\/g, '\/');
                    this.startCropper();
                }else{
                    file = this.$avatarInput.get(0).value;
                    if (this.isImageFile(file)){
                        this.syncUpload();
                    }
                }
             }
        },

        submit: function () {
            if (!this.$avatarSrc.val() && !this.$avatarInput.val()) {
                return false;
            }


            //if (this.support.formData) {
                this.ajaxUpload();
                return false;
            //}
        },

        isImageFile: function (file) {
            if (file.type) {
                return /^image\/\w+$/.test(file.type);
            } else {
                return /\.(jpg|jpeg|png|gif)$/.test(file);
            }
        },

        startCropper: function () {
            var _this = this;

            if (this.support.overIE10 && this.active) {
                this.$img.cropper('replace', this.url);
            } else {
                this.$img = $('<img src="' + this.url + '">');
                this.$avatarWrapper.empty().html(this.$img);
                this.$img.cropper({
                    aspectRatio: 1,
                    preview: this.$avatarPreview.selector
                });

                this.active = true;
            }
        },

        stopCropper: function () {
            if (this.active) {
                this.$img.cropper('destroy');
                this.$img.remove();
                this.active = false;
            }
        },

        ajaxUpload: function () {
            var url = this.$avatarForm.attr('action');
            var _this = this;

            var imageData = this.$img.cropper('getData');
            var json = [
                '{"x":' + imageData.x,
                '"y":' + imageData.y,
                '"height":' + imageData.height,
                '"width":' + imageData.width,
                '"rotate":' + imageData.rotate + '}'
            ].join();
            _this.$avatarData.val(json);
            if (this.support.formData){
                var data = new FormData(this.$avatarForm[0]);
                var loadingIndex = parent.popup.showLoading();
                $.ajax(url, {
                    type: 'post',
                    data: data,
                    dataType: 'json',
                    processData: false,
                    contentType: false,

                    beforeSend: function () {
                        _this.submitStart();
                    },

                    success: function (data) {
                        _this.submitDone(data);
                    },

                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        parent.popup.closePopup(window.name);
                        _this.submitFail(textStatus || errorThrown);
                    },

                    complete: function () {
                        parent.popup.closePopup(loadingIndex);
                        _this.submitEnd();
                    }
                });
            }else{
                _this.$avatarForm.ajaxSubmit({
                    url : url,
                    type : "post",
                    dataType : 'json',
                    success : function(data) {
                        //上传成功之后的操作
                        _this.submitDone(data);
                    },
                    error : function(data) {
                        //上传失败之后的操作
                        parent.popup.closePopup(window.name);
                        _this.submitFail(status || e);
                    }
                });
            }
        },

        syncUpload: function () {
            this.$avatarSave.click();
        },

        submitStart: function () {
            this.$loading.fadeIn();
        },

        submitDone: function (data) {
            if ($.isPlainObject(data) && data.code === TLibSSO.codeNum.SUCCESS) {
                if (data.data) {
                    this.url = data.data + "?v=" + new Date().getTime();

                    if (this.systemAvatar || this.uploaded) {
                        this.uploaded = false;
                        this.cropDone();
                    } else {
                        this.uploaded = true;
                        this.$avatarSrc.val(this.url);
                        this.startCropper();
                    }
                    this.$avatarInput.val('');
                    this.alert("头像上传成功!");
                    parent.parent.saveSystemPhoto(data.data,data.entity);
                    parent.popup.closeOpenPage(window.name);
                } else if (data.message) {
                    this.alert(data.message);
                }
            } else {
                this.alert(data.message);
            }
        },

        submitFail: function (msg) {
            this.alert(msg);
        },

        submitEnd: function () {
            this.$loading.fadeOut();
        },

        cropDone: function () {
            this.$avatarForm.get(0).reset();
            this.stopCropper();
            $(".alert").remove();
        },

        alert: function (msg) {
            parent.popup.alert(msg);
        }
    };

    var cropAvatar = new CropAvatar();
})