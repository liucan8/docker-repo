package com.lc.utils.other;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by feihaitao on 2017/5/22.
 */
public class Pinyin {


    /**
     * 汉字转拼音的方法
     * @param name 汉字
     * @return 拼音
     */
    public static String chineseToPinyin(String name){
        String pinyinName = "";
        char[] nameChar = name.toCharArray();
        HanyuPinyinOutputFormat defaultFormat =
                new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray
                            (nameChar[i], defaultFormat)[0];
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                pinyinName += nameChar[i];
            }
        }

        return pinyinName+ getNumbers(name);
    }

    //截取数字
    public static String getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    /**
     * 判断一个字符串是否含有数字
     * @param content
     * @return
     */
    public static boolean hasDigit(String content) {

        boolean flag = false;

        Pattern p = Pattern.compile(".*\\d+.*");

        Matcher m = p.matcher(content);

        if (m.matches())

            flag = true;

        return flag;

    }

    /**
     * 生成渠道名称
     * @return
     */
    public static  String getChannelName(String name){
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        name=chineseToPinyin(name);
        String date=sdf.format(new Date());
        if(Pinyin.hasDigit(name)){
            name=name+"_"+date;
        }else{
            name=name+date;
        }
        return name;

    }

    /**
     * 汉字转拼音的方法--如果名称里还有英文，对应的拼音也用英文代替
     * @param name 汉字
     * @return 拼音
     */
    public static String chineseToPinyinChannel(String name){
        String pinyinName = "";
        char[] nameChar = name.toCharArray();
        HanyuPinyinOutputFormat defaultFormat =
                new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray
                            (nameChar[i], defaultFormat)[0];
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                pinyinName += nameChar[i];
            }
        }

        return pinyinName+ getNumbers(name);
    }

    /**
     * 生成渠道名称：如果名称里还有英文，对应的拼音也用英文代替
     * @param name
     * @return
     */
    public static  String createChannelName(String name){
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        name=chineseToPinyinChannel(name);
        String date=sdf.format(new Date());
        if(Pinyin.hasDigit(name)){
            name=name+"_"+date;
        }else{
            name=name+date;
        }
        return name;

    }

//    public static void main(String[] args) {
//        String pingying=Pinyin.createChannelName("我们的祖国11111");
//        System.out.println(pingying);
//    }
}
