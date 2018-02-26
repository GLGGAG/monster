package com.orange;

import com.google.common.base.Preconditions;

/**
 * @author : GLGGAG
 * @since : 2017/11/14
 */
public class Teacher {

    public Student student=new Student();


    public static void main(String[] args) {
       int i=0;
       try {
           Preconditions.checkArgument(i>0,"期待i>0");
       }catch (Exception e){

       }
        System.out.println("====================");
    }

}
