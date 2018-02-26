package com.orange.support.message;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:GLGGAG
 * @Date:2017/10/18 16:24
 * @Description: 后台业务处理错误定义类，在此类中统一定义业务处理出错任何类型
 */
public enum ErrorMsg {
    // 错误码定义
    Success                     ("0000", "成功"),
    ParamsIsNull                 ("1000","参数为空"),
    UserInexistence              ("2000","用户不存在"),
    UserExist                    ("2001","用户已存在"),
    UserNotLogin                  ("2010","用户未登录或登录失效"),
    UserLoginError                ("2020","用户账号或密码错误"),




    DailyIsExist                ("8888","今日日报内容已存在,是否需要覆盖"),
    SystemErr                   ("9999", "系统异常");

    /**
     * 错误编号
     */
    private String code;

    /**
     * 错误提示
     */
    private String msg;

    ErrorMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 静态缓存，提高效率
     */
    private static Map<String, ErrorMsg> cache;
    static {
        cache = new HashMap<String, ErrorMsg>();
        ErrorMsg[] values = ErrorMsg.values();
        for (ErrorMsg error : values) {
            cache.put(error.code, error);
        }
    }

    /**
     * <pre>
     * 获取消息内容
     * </pre>
     *
     * @param code
     * @return
     */
    public static String getMsg(String code) {
        if (code != null && code.length() > 0) {
            ErrorMsg errorMsg = cache.get(code);
            if (errorMsg != null) {
                return errorMsg.getMsg();
            }
        }

        return ErrorMsg.SystemErr.getMsg();
    }
}