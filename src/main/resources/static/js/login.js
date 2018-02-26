/**
 * Created by longpeng on 2017/12/5.
 */
/*登录按钮监听键盘点击事件*/
$(document).keypress(function (e) {
    // 回车键事件
    if (e.which === 13) {
        $('input[type="button"]').click();
    }
});

layui.use('layer', function () {
    //非空验证
    $('input[type="button"]').click(function () {
        var login = $('input[name="login"]').val();
        var pwd = $('input[name="pwd"]').val();
        preLoginAnimate();
            //登录
        var data={
                phone:"17620361566 ",
                passWord:"123456"
        };
        $.inheritAjaxPost(getUrlPath("login"),data,loginSuccessBack,$.noop);
    })
});
/*ajax请求处理处理函数（当前是请求处理  而不一定是登录成功）beigin---------------------------------------------------------------*/
var loginSuccessBack=function (d) {
    preVerifyAnimate();
    if(validRes.comSuccessRes(d.code)){
        setTimeout(function () {
            aftVerifyAnimate(d.data);
        },1500);
    }else{
        loginFailBack(d)
    }
};
var loginFailBack=function (d) {
    //错误提示框  ========================================================
    setTimeout(function () {
        $('.authent').animate({opacity: 0}, {
            duration: 600,
            queue: false
        }).removeClass('visible');
        $('.login').removeClass('test'); //平移特效
        $('.authent').removeAttr("style");
    }, 2000);
    console.log(d.msg+"====================================================");

};
/*ajax请求处理处理函数（当前是请求处理  而不一定是登录成功）end---------------------------------------------------------------*/
/*请求处理成功后判断是否响应成功之后动画效果   begin--------------------------------------------------------------------------------*/
var aftVerifyAnimate=function (data) {
    $('.authent').hide();
    $('.login').removeClass('test');
    $('.login div').fadeOut(100);
    $('.success').fadeIn(1000).html("Hi:       "+data.userName);
    setTimeout(function () {
        var routeFlag=data.userGradeType;
        window.location.href="/index.html"
    }, 1000);
};
/*请求处理成功后判断是否响应成功之后动画效果   end--------------------------------------------------------------------------------*/
/*请求处理成功后判断是否响应成功之前动画效果   begin--------------------------------------------------------------------------------*/
var preVerifyAnimate=function () {
    setTimeout(function () {
        $('.authent').show().animate({right: 90}, {
            easing: 'easeOutQuint',
            duration: 600,
            queue: false
        });
        $('.authent').animate({opacity: 0}, {
            duration: 200,
            queue: false
        }).addClass('visible');
        $('.login').removeClass('testtwo'); //平移特效
    }, 2000);
};
/*请求处理成功后判断是否响应成功之前动画效果   end--------------------------------------------------------------------------------*/
/*登录按键点击后登录之前效果  begin------------------------------------------------------------------------------------------------------*/
var preLoginAnimate=function(){
    $('.login').addClass('test'); //倾斜特效
    setTimeout(function () {
        $('.login').addClass('testtwo'); //平移特效
    }, 300);
    setTimeout(function () {
        $('.authent').show().animate({right: -320}, {
            easing: 'easeOutQuint',
            duration: 600,
            queue: false
        });
        $('.authent').animate({opacity: 1}, {
            duration: 200,
            queue: false
        }).addClass('visible');
    }, 500);
};
/*登录按键点击后登录之前效果   end---------------------------------------------------------------------------------------------------------*/

/*登录页面js效果显示  begin-------------------------------------------------------------------------------------------------------------*/
//粒子背景特效
$('body').particleground({
    dotColor: '#E8DFE8',
    lineColor: '#133b88'
});
$('input[name="pwd"]').focus(function () {
    $(this).attr('type', 'password');
});
$('input[type="text"]').focus(function () {
    $(this).prev().animate({'opacity': '1'}, 200);
});
$('input[type="text"],input[type="password"]').blur(function () {
    $(this).prev().animate({'opacity': '.5'}, 200);
});
$('input[name="login"],input[name="pwd"]').keyup(function () {
    var Len = $(this).val().length;
    if (!$(this).val() === '' && Len >= 5) {
        $(this).next().animate({
            'opacity': '1',
            'right': '30'
        }, 200);
    } else {
        $(this).next().animate({
            'opacity': '0',
            'right': '20'
        }, 200);
    }
});
/*登录页面js效果显示  end-------------------------------------------------------------------------------------------------------------*/
