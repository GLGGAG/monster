package com.orange;

/**
 * @author : GLGGAG
 * @since : 2017/11/14
 */
public class Student {
    public Teacher  teacher =new Teacher();


    public static void main(String[] args) {
        SingleTon singleTon = SingleTon.getSingleTon();
    }

}
