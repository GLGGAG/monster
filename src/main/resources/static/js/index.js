/**
 * Created by longpeng on 2017/12/6.
 */
var SITE_BASE = 'http://localhost:8080';

layui.use(['element', 'laypage', 'layer', 'form'], function(){
    var element = layui.element,
        laypage = layui.laypage,
        layer = layui.layer,
        form = layui.form;

    form.on('submit(creat)', function(data){
        layer.msg('新建员工成功！');
        return false;
    });
    // element.on('collapse(team)', function(data){
    //     console.log(data)
    // });
    // 数据过滤
    Vue.filter('time', function (value) {
        var date = new Date(value);
        Y = date.getFullYear() + '年';
        M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '月';
        D = date.getDate() + '日';
        return Y + M + D;
    });

    // $.get(SITE_BASE + '/indexList_back.html', function (ret) {
    //     Child = {
    //         template: ret
    //     }
    // });
    //--------------------VUE---------------------------------
    var app = new Vue({
        el: '#app',
        data: {
            UserGradeType: '',
            userNo: null,    //用户编号
            list: [],         //每页的三条数据
            pageSize: null,  //每页数
            total: null,     //总数
            pageNum: null,    //当前页
            todayContent: '',  //今日内容
            incompleteCause: '',  //未完成原因
            morrowPlan: '',  //明日计划
            riskPoint: '',  //风险对策
            remark: '', //备注
            teamList: [
                {
                    teamName: '',
                    teamNo: ''
                }
            ],
            memberList: [
                {
                    userName: '',
                    userNo: ''
                }
            ],
            userList: []
        },
        mounted: function () {
            this.renderData();
        },
        methods: {
            renderData: function () {
                var self = this;
                invokeAPI(getUrlPath("userInfo"),{},'get')
                    .done(function (result) {
                        vailLogin.isSuccessLogin(result.code);
                        if(validRes.comSuccessRes(result.code)){
                            self.UserGradeType = result.data.userGradeType;
                            self.userNo = result.data.userNo;
                            if (self.UserGradeType !== "ManagerCenter") {
                                self.getList();
                                form.on('submit(sbm)', function(data){
                                    self.submit();
                                    return false;
                                });
                            } else {
                                self.renderTeam();
                                self.renderUser()
                            }
                        }
                    })
                    .fail(function (err) {
                        layer.msg(' 详情: ' + err.responseText);
                    });
            },
            getList: function () {
                var self = this;
                invokeAPI(getUrlPath("dailyList"),{"userNo":self.userNo},'get')
                    .done(function (d) {
                        if(validRes.comSuccessRes(d.code)){
                            self.list = d.data.list;
                            self.pageSize = d.data.pageSize;
                            self.total = d.data.total;
                            self.pageNum = d.data.pageNum;
                            //调用分页
                            laypage.render({
                                elem: 'page',
                                count: self.total,
                                limit: self.pageSize,
                                jump: function(obj, first){
                                    invokeAPI(getUrlPath("dailyList"),{"userNo":self.userNo,"pageNum":obj.curr,"pageSize":obj.limit},'get')
                                        .done(function (d) {
                                            self.list = d.data.list;
                                        })
                                        .fail(function (err) {
                                            layer.msg(' 详情: ' + err.responseText);
                                        });
                                }
                            });
                        }
                    })
                    .fail(function (err) {
                        layer.msg(' 详情: ' + err.responseText);
                    });
                if (self.UserGradeType === "TeamLeader") {
                    self.renderTeam()
                }
                if (self.UserGradeType === "DepartmentHeads") {
                    self.renderTeam();
                    self.renderUser()
                }
            },
            submit: function () {
                var self = this;
                invokeAPI(getUrlPath("dailySave"),
                    {"todayContent":self.todayContent,
                    "incompleteCause":self.incompleteCause,
                    "morrowPlan":self.morrowPlan,
                    "riskPoint":self.riskPoint,
                    "remark":self.remark},'get')
                    .done(function (d) {
                        if(validRes.comSuccessRes(d.code)){
                            layer.msg('日报提交成功！');
                            self.getList();
                            self.reset()
                        } else if (d.code === "8888") {
                            layer.open({
                                title: '提示',
                                content: d.msg,
                                icon: 1,
                                yes: function(index, layero){
                                    self.submit();
                                    self.reset()
                                }
                            });
                        } else {
                            layer.msg(d.msg);
                        }
                    })
                    .fail(function (err) {
                        layer.msg(' 详情: ' + err.responseText);
                    });
            },
            reset: function () {
                var self = this;
                self.todayContent = '';
                self.incompleteCause = '';
                self.morrowPlan = '';
                self.riskPoint = '';
                self.remark = '';
            },
            renderTeam: function () {
                var self = this;
                invokeAPI(getUrlPath('teamList'),{},'get')
                    .done(function (d) {
                        if(validRes.comSuccessRes(d.code)){
                            self.teamList = d.data;
                            for (var i=0;i<self.teamList.length;i++) {
                                invokeAPI(getUrlPath('teamMember'),{"teamNo": self.teamList[i].teamNo},'get')
                                    .done(function (res) {
                                        if(validRes.comSuccessRes(res.code)){
                                            self.memberList= res.data;
                                            element.render('collapse');
                                        }
                                    })
                            }
                        }
                    })
                    .fail(function (err) {
                        layer.msg(' 详情: ' + err.responseText);
                    });
            },
            renderUser: function () {
                var self = this;
            },
            getMember: function (index) {
                new Vue({
                    el: '#user' + app.$data.memberList[index].userNo,
                    data: function() {
                        return{
                            UserNo: app.$data.memberList[index].userNo,
                            UserList: [],
                            pageSize: null,  //每页数
                            total: null,     //总数
                            pageNum: null    //当前页
                        }
                    },
                    mounted: function () {
                        this.getData();
                    },
                    methods: {
                        getData: function () {
                            var self = this, htm = '';
                            invokeAPI(getUrlPath("dailyList"), {"userNo": self.UserNo}, 'get')
                                .done(function (u) {
                                    self.UserList = u.data.list;
                                    self.pageSize = u.data.pageSize;
                                    self.total = u.data.total;
                                    self.pageNum = u.data.pageNum;
                                    element.render('collapse');
                                    for (var u=0;u<self.UserList.length;u++) {
                                        htm += '<div class="layui-colla-content" >\n' +
                                            '    <ul class="layui-timeline">\n' +
                                            '        <li class="layui-timeline-item">\n' +
                                            '            <i class="layui-icon layui-timeline-axis">&#xe63f;</i>\n' +
                                            '            <div class="layui-timeline-content layui-text">\n' +
                                            '                <h3 class="layui-timeline-title">'+ self.UserList[u].createTime + '</h3>\n' +
                                            '                <table class="layui-table">\n' +
                                            '                    <colgroup>\n' +
                                            '                        <col width="100">\n' +
                                            '                        <col width="100">\n' +
                                            '                        <col width="100">\n' +
                                            '                        <col width="100">\n' +
                                            '                        <col width="100">\n' +
                                            '                    </colgroup>' +
                                            '                    <thead>' +
                                            '                    <tr>' +
                                            '                        <th>当天工作内容</th>' +
                                            '                        <th>未完成工作原因</th>' +
                                            '                        <th>次日工作计划</th>' +
                                            '                        <th>风险点和对策</th>' +
                                            '                        <th>备注</th>' +
                                            '                    </tr>' +
                                            '                    </thead>' +
                                            '                    <tbody>' +
                                            '                    <tr>' +
                                            '                        <td>' + self.UserList[u].todayContent + '</td>' +
                                            '                        <td>' +  self.UserList[u].incompleteCause + '</td>\n' +
                                            '                        <td>' +  self.UserList[u].morrowPlan + '</td>\n' +
                                            '                        <td>' +  self.UserList[u].riskPoint + '</td>\n' +
                                            '                        <td>' +  self.UserList[u].remark + '</td>\n' +
                                            '                    </tr>\n' +
                                            '                    </tbody>\n' +
                                            '                </table>\n' +
                                            '               <div id="page-all" class="text-center"></div>' +
                                            '            </div>\n' +
                                            '        </li>\n' +
                                            '    </ul>\n' +
                                            '</div>'
                                    }
                                    $('#user' + app.$data.memberList[index].userNo).find('h2').after(htm);
                                    //调用分页
                                    laypage.render({
                                        elem: 'page-all',
                                        count: self.total,
                                        limit: self.pageSize,
                                        jump: function (obj, first) {
                                            invokeAPI(getUrlPath("dailyList"), {
                                                "userNo": self.UserNo,
                                                "pageNum": obj.curr,
                                                "pageSize": obj.limit
                                            }, 'get')
                                                .done(function (l) {
                                                    self.userList = l.data.list;
                                                    element.render('collapse');
                                                })
                                                .fail(function (err) {
                                                    layer.msg(' 详情: ' + err.responseText);
                                                });
                                        }
                                    });
                                })
                                .fail(function (err) {
                                    layer.msg(' 详情: ' + err.responseText);
                                });
                        }
                    }
                })
            }
        }
    });
});

function invokeAPI(apiName, data, type) {
    return $.ajax({
        type: type,
        url: SITE_BASE  +  apiName,
        mimeType:'text/plain',
        contentType: 'application/json',
        data: data,
        dataType: 'json'
    });
}
