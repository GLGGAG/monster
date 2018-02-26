package com.orange.support.sequence;

/**
 * @author: GLGGAG
 * @Date: 2017/09/07/下午 16:18
 * @Description:
 */
public enum SequenceKey {
    /**
     * 用户编号
     * 小怪兽系统内部 用户编号为8位 前面3位为固定格式 后5位为随机编码
     */
    UserNo("100");



    private String prefix;

    SequenceKey(String prefix){
        this.prefix=prefix;
    }
}
