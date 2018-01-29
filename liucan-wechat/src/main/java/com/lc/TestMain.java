package com.lc;

import org.springframework.util.DigestUtils;

import java.util.Arrays;

/**
 * Created by Administrator on 2018/1/13 0013.
 */
public class TestMain {
    public static void main(String args[]){
       String str = "123456";
        System.out.println(DigestUtils.md5DigestAsHex(str.getBytes()));
    }
}
