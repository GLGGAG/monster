package com.orange;

import com.google.common.base.Preconditions;

/**
 * @author:GLGGAG
 * @Date:2017/11/03
 * @Description:
 */
public class Recusion {


    public static void main(String[] args) {
        System.out.println(recursion(45));
    }

    public  static long recursion(long n){
        Preconditions.checkArgument(n>= 0, "期望值参数是大于0，得到", n);
        long result=1;
        if (n==0||n==1){
            return result;
        }
        for (;n>1;n--){
            result += recursion(--n);
        }
        return result;
    }
}
