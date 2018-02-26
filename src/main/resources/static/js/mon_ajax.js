/*对Ajax进一步的封装 使其更简单和方便*/
/*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
$(function(){
    /**
     * url  请求的地址
     * data 数据 json格式数据
     * async 默认值: true。如果需要发送同步请求，请将此选项设置为 false。
     * type 请求方式("POST" 或 "GET")， 默认为 "GET"
     * dataType 预期服务器返回的数据类型，常用的如：xml、html、json、text
     * successfn 成功回调函数
     * errorfn 失败回调函数
     */
    jQuery.inheritAjax=function(url, data, async, type, dataType, successfn, errorfn) {
        async = (async==null || async=="" || typeof(async)=="undefined")? "true" : async;
        type = (type==null || type=="" || typeof(type)=="undefined")? "post" : type;
        dataType = (dataType==null || dataType=="" || typeof(dataType)=="undefined")? "json" : dataType;
        data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
        $.ajax({
            type: type,
            async: async,
            data: data,
            timeout:2000,
            url: url,
            dataType: dataType,
            success: function(d){
                successfn(d);
            },
            error: function(e){
                errorfn(e);
            },
            complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
                if(status=='timeout'){//超时,status还有success,error等值的情况
                    console.log("请求超时");
                }
            }
        });
    };
    jQuery.inheritAjaxPost=function(url, data, successfn,errorfn) {
        $.inheritAjax(url,data,true,"POST",null,successfn,errorfn,null);
    };

    jQuery.inheritAjaxGet=function(url, data, successfn, errorfn) {
        $.inheritAjax(url,data,true,"GET",null,successfn,errorfn,null);
    };
    jQuery.inheritAjaxPostSycn=function(url, data, successfn,errorfn) {
        $.inheritAjax(url,data,false,"POST",null,successfn,errorfn,null);
    };
});
/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/