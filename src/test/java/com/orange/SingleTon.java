package com.orange;

/**
 * @author: GLGGAG
 * @Date: 2017/12/01/下午 17:36
 * @Description:
 */
public class SingleTon {


    private SingleTon(){};

    private static SingleTon singleTon=new SingleTon();
    

    public static SingleTon getSingleTon(){
        return singleTon;
    }

}
