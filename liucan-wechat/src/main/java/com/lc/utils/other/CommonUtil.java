package com.lc.utils.other;

import org.springframework.util.StringUtils;

/**
 * 一些零碎的工具
 * Created by liucan on 2017/10/31.
 */
public class CommonUtil {

    /**
     * emoji表情替换
     *
     * @param source 原字符串
     * @param slipStr emoji表情替换成的字符串
     * @return 过滤后的字符串
     */
    public static String filterEmoji(String source,String slipStr) {
        if(!StringUtils.isEmpty(source)){
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", slipStr);
        }else{
            return source;
        }
    }

}
