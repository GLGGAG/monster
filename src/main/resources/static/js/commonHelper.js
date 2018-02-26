/*通用响应校验  begin---------------------------------------------------------------------------------------------------*/
var validRes = {
    comSuccessRes : function (code) {
        return code == '0000';
    }
};
var vailLogin ={
    isSuccessLogin :function(code){
        if (code == '2010'){
            window.location.href="/login.html"
        }
    }
};
/*通用响应校验  end---------------------------------------------------------------------------------------------------*/