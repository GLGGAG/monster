
/**
 * index 主页
 * @type {angular.Module}
 */
var app = angular.module('indexApp', []);
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
/*index页面初始化请求*/
//获取当前用户信息
app.controller("userInfoController",function($scope,$http,$rootScope,$timeout){
    var userNo;
    var currentNavUserNo;
    $scope.selectTeamModel="";
    $http.get(getUrlPath("userInfo"),{}).then(function (response) {
        var resultData=response.data;
        var resultCode=resultData.code;
        if (resultCode == "2010"){
          window.location.href="login.html";
        }else if(resultCode == "0000"){
            //存放用户信息到rootScope中
            $rootScope.userInfo=resultData.data;
             userNo=resultData.data.userNo;
            mainPageDailyinit(userNo);
        }else {
            layer.msg(resultData.msg)
        }
    });

    /*获取用户日报内容*/
    var dailyContent=function (userNo,pageId,index) {
        $http.get(getUrlPath("dailyList")+"?userNo="+userNo,{}).then(function (response) {
            var re=response.data;
            var reCode=re.code;
            if (reCode == '0000'){
                if(index == 0){
                    $scope.mainDailyContentListModel=re.data.list;
                }else if(index == 1){
                    $scope.teamUserDailyContent=re.data.list;
                }
                pageRender(re.data,userNo,pageId,index);
            }else{
                layer.msg(re.msg);
            }
        });
    };

    //退出登录
    $scope.logout=function () {
        $http.get(getUrlPath("userLayout"),{}).then(function (response) {
            var re=response.data;
            var reCode=re.code;
            if (reCode == '0000'){
              window.location.href="index.html";
            }else{
                layer.msg(re.msg);
            }
        });
    };


    /*分页*/
    var pageRender=function (pageInfo,userNo,pageId,index) {
        layui.laypage.render({
            elem: pageId, //注意，这里的 test1 是 ID，不用加 # 号
            count: pageInfo.total, //数据总数，从服务端得到
            limit:pageInfo.pageSize,
            curr:pageInfo.pageNum,
            jump: function(obj, first){
                if(!first){
                    $http.get(getUrlPath("dailyList")+"?userNo="+userNo+"&"+"pageNum="+obj.curr,{}).then(function (response) {
                        var re=response.data;
                        var reCode=re.code;
                        if (reCode == '0000'){
                            if (index == 0){
                                $scope.mainDailyContentListModel=re.data.list;
                            }else if(index == 1){
                                $scope.teamUserDailyContent=re.data.list;
                            }
                        }else{
                            layer.msg(re.msg);
                        }
                    });
                }
            }
        });
    };

    /*监听table切换*/
    layui.element.on("tab(tabBrief)",function(data){
        var index=data.index;
        //切换到自己日报页面
        if(index == 0){
            mainPageDailyinit(userNo);
        }else if(index == 1){
            //查看团队成员日报页面
            $http.get(getUrlPath("userList"),{}).then(function (response) {
                var re=response.data;
                var reCode=re.code;
                if (reCode == '0000'){
                    $scope.reviewTeamDailyList=re.data;
                    //默认显示第一个用户日报内容
                    if(re.data != undefined && re.data[0].teamUser.length > 0){
                        var firstUserNo=re.data[0].teamUser[0].userNo;
                        reviewDailyContent(firstUserNo)
                    }
                }else{
                    layer.msg(re.msg);
                }
            });
            initNavElement();
        }else{
            /*员工设置页面*/
            //员工列表
            initTeamUserListConfig();
            //初始化团队
            initTeamConfig();
            initFormElement();
        }

    });

    /*重新渲染nav导航条*/
    var initNavElement=function () {
     $timeout(function () {
         layui.element.render("nav");
     },200)
    };

    /*重新渲染select*/
    var initFormElement=function () {
        $timeout(function () {
            layui.form.render();
        },200)
    };

    var initTeamUserListConfig=function(){
        $http.get(getUrlPath("userList"),{}).then(function (response) {
            var re=response.data;
            var reCode=re.code;
            if (reCode == '0000'){
                $scope.teamUserListModel=re.data;
            }else{
                layer.msg(re.msg);
            }
        });
    };

    /*团队配置*/
    var initTeamConfig=function () {
        $http.get(getUrlPath("teamConfig"),{}).then(function (response) {
            var re=response.data;
            var reCode=re.code;
            if (reCode == '0000'){
                $scope.teamListModel=re.data;
            }else{
                layer.msg(re.msg);
            }
        });
    };


    /*主页日报内容初始化*/
    var mainPageDailyinit=function (userNo) {
        dailyContent(userNo,"main_daily_page",DailyContentType.mainDailyContentListModel);
    };

    /*查看特定用户日报内容*/
    var reviewDailyContent=function (userNo) {
        dailyContent(userNo,"team_user_page",DailyContentType.teamUserDailyContent);
    };

    //监听导航点击
    layui.element.on('nav(teamNavBrief)', function(elem){
        currentNavUserNo=elem[0].getAttribute("data_type");
        reviewDailyContent(currentNavUserNo);
    });



    //select选择框监听
    layui.form.on('select(teamSelectBrief)',function(elem){
        var teamNo=elem.value;
        if(teamNo == ''|| teamNo==undefined){
            $scope.userGradeListModel=[];
            layer.msg("请选择团队");
            return;
        }
        $http.get(getUrlPath("userConfig")+"?teamNo="+teamNo,{}).then(function (response) {
            var re=response.data;
            var reCode=re.code;
            if (reCode == '0000'){
                $scope.userGradeListModel=re.data;
            }else{
                layer.msg(re.msg);
            }
        });
        initFormElement();
    });


    /*用户删除*/
    $scope.deletedUserFn=function (userNo) {
        $http.get(getUrlPath("userDelete")+"?userNo="+userNo,{}).then(function (response) {
            var re=response.data;
            var reCode=re.code;
            if (reCode == '0000'){
                layer.msg(re.msg);
                initTeamUserListConfig();
            }else{
                layer.msg(re.msg);
            }
        });
    };

    //监听创建员工表单提交
    layui.form.on("submit(create)",function(data){
        $http(commonRequestConfig(data.field,getUrlPath("userAdd"))).then(createSuccessCallback, globalErrorCallback);
        return false;
    });

    //监听日报内容表单提交
    layui.form.on("submit(dailySubmit)",function(data){
        syncEditToTextarea(data.field);
        var resultData=data.field;
        if(resultData.todayContent == '' ||
            resultData.incompleteCause == ''||
            resultData.morrowPlan == ''||
            resultData.riskPoint == ''){
            layer.msg("请填写日报内容");
            return false;
        }
        $http(commonRequestConfig(data.field,getUrlPath("dailySave"))).then(dailySuccessCallback, globalErrorCallback);
        return false;
    });




    var dailySuccessCallback=function (d) {
        var re=d.data;
        if(re.code == '0000'){
            layer.closeAll();
            layer.msg(re.msg);
            mainPageDailyinit(userNo);
        }else if(re.code == '8888'){
            layer.msg(re.msg, {
                time: 20000, //20s后自动关闭
                btn: ['确定', '取消'],
                yes:function (index) {
                    layui.$("#dailySubmit").click();
                    layer.closeAll();
                },
                btn2:function (index) {
                    layer.closeAll();
                }
            });
        }else{
            layer.msg(re.msg);
        }
    };

    var createSuccessCallback=function (d) {
        var re=d.data;
        if(re.code == '0000'){
            layer.msg(re.msg);
            initTeamUserListConfig();
        }else {
            layer.msg(re.msg);
        }
    };
    var globalErrorCallback=function () {
        layer.msg("网络异常，请稍后再试");
    };

    var commonRequestConfig=function (data,url) {
        return {
            method: 'POST',
            url: url,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            data: data,
            dataType: 'json'
        }
    };



    /*klayui  初始化*/
    layui.$("#daily_write_btn").click(function () {
        layer.open({
            title: '填写日报',
            type: 1,
            area: ['1024px', '768px'],
            content: layui.$("#daily_write_content"),
            success:function (layero, index) {
                //初始化富文本编辑器
                 buildEdit();
            },
            yes:function (index) {
                layer.close(index);
            },
            end:function () {
                //去除layui默认加上的block
                layui.$('#daily_write_content').css('display','none');
            }
        });
    });




    var todayContent;
    var incompleteCause;
    var morrowPlan;
    var riskPoint;
    var remark;
    var buildEdit=function () {
        //初始化富文本编辑器
        todayContent=layui.layedit.build("todayContent");
        incompleteCause=layui.layedit.build("incompleteCause");
        morrowPlan=layui.layedit.build("morrowPlan");
        riskPoint=layui.layedit.build("riskPoint");
        remark=layui.layedit.build("remark");
    };

    var syncEditToTextarea=function (data) {
        var _todayContent = layui.layedit.getContent(todayContent);
        data.todayContent=_todayContent;
        var _incompleteCause = layui.layedit.getContent(incompleteCause);
        data.incompleteCause=_incompleteCause;
        var _morrowPlan = layui.layedit.getContent(morrowPlan);
        data.morrowPlan=_morrowPlan;
        var _riskPoint = layui.layedit.getContent(riskPoint);
        data.riskPoint=_riskPoint;
        var _remark = layui.layedit.getContent(remark);
        data.remark=_remark;
    };


});
app.filter('to_trusted', ['$sce', function ($sce) {
    return function (text) {
        return $sce.trustAsHtml(text);
    };
}]);

