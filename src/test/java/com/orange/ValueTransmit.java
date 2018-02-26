package com.orange;

/**
 * @author:GLGGAG
 * @Date:2017/11/03
 * @Description:
 */
public class ValueTransmit {

    public void swap(int a, int b) {
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println("swap: a=" + a + ",b=" + b);
    }

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE/1024/1024);
    }



}
