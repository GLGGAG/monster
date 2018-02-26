/*后台接口路径 beigin------------------------------------------------------------------------------------------------*/
var BasePath="192.168.1.111:8080";
var Path={
    //登录请求接口
    login:"login/verify",
    userInfo:"user/info",
    userList:"user/list",
    userAdd:"user/add",
    userConfig:"user/config",
    userLayout:"user/layout",
    userDelete:"user/delete", //phone
    dailyList:"daily/list",//需要参数  userNo
    dailySave:"daily/save",
    teamList:"team/list",
    teamMember:"team/member",
    userAdd:"user/add",
    teamConfig:"team/config",
    dailySave:"daily/save"
};
function getUrlPath(strPath) {
    var path=Path[strPath];
    return "/"+path;
}
/*后台接口路径 end---------------------------------------------------------------------------------------------------------*/
/*常用的共用常亮  begin----------------------------------------------------------------------------------------------------*/
var UserGradeType={
    Employee:"Employee",
    TeamLeader:"TeamLeader",
    DepartmentHeads:"DepartmentHeads",
    ManagerCenter:"ManagerCenter"
};
var DailyContentType={
    mainDailyContentListModel:0,
    teamUserDailyContent:1
};


/*常用的共用常亮  end----------------------------------------------------------------------------------------------------*/