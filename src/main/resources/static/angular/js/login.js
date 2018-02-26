/**
 * layui初始化
 */
var mylayui=layui.use([ 'layer'], function() {
    var layer = layui.layer;
});
/**
 * 登录功能实现
 * @type {angular.Module}
 */
var app = angular.module('loginApp', []);
app.config(function ($httpProvider) {
    //覆盖掉angular默认的序列化方法 post提交参数后台才会接收到
    $httpProvider.defaults.transformRequest = [function(data) {
        /**
         * The workhorse; converts an object to x-www-form-urlencoded serialization.
         * @param {Object} obj
         * @return {String}
         */
        var param = function(obj) {
            var query = '';
            var name, value, fullSubName, subName, subValue, innerObj, i;

            for (name in obj) {
                value = obj[name];

                if (value instanceof Array) {
                    for (i = 0; i < value.length; ++i) {
                        subValue = value[i];
                        fullSubName = name + '[' + i + ']';
                        innerObj = {};
                        innerObj[fullSubName] = subValue;
                        query += param(innerObj) + '&';
                    }
                } else if (value instanceof Object) {
                    for (subName in value) {
                        subValue = value[subName];
                        fullSubName = name + '[' + subName + ']';
                        innerObj = {};
                        innerObj[fullSubName] = subValue;
                        query += param(innerObj) + '&';
                    }
                } else if (value !== undefined && value !== null) {
                    query += encodeURIComponent(name) + '='
                        + encodeURIComponent(value) + '&';
                }
            }
            return query.length ? query.substr(0, query.length - 1) : query;
        };
        return angular.isObject(data) && String(data) !== '[object File]'
            ? param(data)
            : data;
    }];
});
app.controller('loginController', function($scope,$http) {
    $scope.loginClick=function() {
        //必要校验
        var phone = $scope.phone;
        if (!/^1[34578]\d{9}$/.test(phone)) {
            mylayui.layer.msg("手机号码格式不对");
            return;
        }
        var pwd = $scope.password;
        if (pwd === "" || pwd === undefined || pwd.trim() === "") {
            mylayui.layer.msg("密码不能为空");
            return;
        }
        //后台请求
        var data = {
            "phone": phone,
            "passWord": pwd
        };
        $http(commonRequestConfig(data)).then(loginSuccessCallback, globalErrorCallback);
    }
});
var loginSuccessCallback=function (d) {
    var re=d.data;
    if(re.code == '0000'){
        window.location.href="index.html";
    }else {
        mylayui.layer.msg(re.msg);
    }
};
var globalErrorCallback=function () {
    mylayui.layer.msg("网络异常，请稍后再试");
};
var commonRequestConfig=function (data) {
   return {
       method: 'POST',
       url: getUrlPath("login"),
       headers: {
           'Content-Type': 'application/x-www-form-urlencoded'
       },
       data: data,
       dataType: 'json'
   }
};

