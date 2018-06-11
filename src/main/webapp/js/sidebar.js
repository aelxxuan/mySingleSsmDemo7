$(function() {
    $('#side-menu').metisMenu();

    $(window).bind("load resize",function() {
        time_height();
    });

    function time_height() {
        var topOffset = 71;
        width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse');
            $("#page-wrapper").css({"margin-left":"0px"});
            //$("body").css({"background":"#fff"});
            topOffset = 100; // 2-row-menu
        } else {
            $('div.navbar-collapse').removeClass('collapse');
            $("#page-wrapper").css({"margin-left":"250px"});
            //$("body").css({"background":"#2f4050"});
        }
        //var height = this.window.innerHeight < 979 ? 1100 :this.window.innerHeight;
        //var height1 = height - topOffset;
        //if (height1 < 1) height = 1;
        //if (height1 > topOffset) {
               // $("#page-wrapper").css("height", (height) + "px");
       // }
    }
    var url = window.location;
    var element = $('ul.nav a').filter(function() {
        if(url.hash){
            var uri=$(this).attr('url');
            if(uri){
                return url.hash.substring(1)==uri.replace(/\.\.\//,"");
            }
        }else {
            return false;
        }
    }).addClass('active').parent().parent().addClass('in').parent();
    if (element.is('li')) {
        element.addClass('active');
    }
    //菜单的隐藏和展示
    $('.menu-control').on('click', function () {
        $('body').toggleClass('mini-navbar');
        $("#page-wrapper").toggleClass("page-wrapper-pl");
    })
    window.addEventListener('hashchange', function () {
        var url = window.location;
        var element = $('ul.nav a').filter(function() {
            $(this).removeClass("active");
            if(url.hash){
                var uri=$(this).attr('url');
                if(uri){
                    return url.hash.substring(1)==uri.replace(/\.\.\//,"");
                }
            }else {
                return false;
            }
        }).addClass('active').parent().parent().addClass('in').parent();
        if (element.is('li')) {
            element.addClass('active');
        }
    })

    });
