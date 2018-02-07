package com.lc.utils.other;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 数据工具类.<br>
 * 数值共通处理。
 */
public class NumberUtil {

    static final String DELIMITER_HALF_COMMA = ",";

    private static final String[] CN_UPPER_NUMBER = { "零", "壹", "贰", "叁", "肆",
            "伍", "陆", "柒", "捌", "玖" };
    /**
     * 汉语中货币单位大写，这样的设计类似于占位符
     */
    private static final String[] CN_UPPER_MONETRAY_UNIT = { "分", "角", "元",
            "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾",
            "佰", "仟" };
    /**
     * 特殊字符：整
     */
    private static final String CN_FULL = "整";
    /**
     * 特殊字符：负
     */
    private static final String CN_NEGATIVE = "负";
    /**
     * 金额的精度，默认值为2
     */
    private static final int MONEY_PRECISION = 2;
    /**
     * 特殊字符：零元整
     */
    private static final String CN_ZEOR_FULL = "零元" + CN_FULL;


    private NumberUtil() {
    }

    /**
     * 数值加3位逗号。<BR>
     *
     * @param targetNumber 编辑对象字符串
     * @return 逗号编辑后字符串
     */
    public static String toCommaFormat(BigDecimal targetNumber) {

        if (targetNumber == null) {
            return "";
        }

        DecimalFormat decimalformat = new DecimalFormat("###,##0.00###################");
        return decimalformat.format(targetNumber);
    }

    public static String toStringFormat(BigDecimal targetNumber) {

        if (targetNumber == null) {
            return "";
        }

        DecimalFormat decimalformat = new DecimalFormat("#####0.00###################");
        return decimalformat.format(targetNumber);
    }

    public static BigDecimal toNumberType(String targetNumber) {
        return new BigDecimal(targetNumber);
    }

    public static BigDecimal toNumberTypeNull(String targetNumber) {
        if (StringUtils.isEmpty(targetNumber)) {
            return null;
        }

        return toNumberType(targetNumber);
    }

    public static BigDecimal toDecimalTypeNull(String targetNumber) {
        return toNumberTypeNull(targetNumber);
    }

    public static BigDecimal toNumberTypeZero(String targetNumber) {
        if (StringUtils.isEmpty(targetNumber)) {
            return new BigDecimal("0");
        }

        return toNumberType(targetNumber);
    }

    public static BigDecimal toDecimalTypeZero(String targetNumber) {
        return toNumberTypeZero(targetNumber);
    }

    /**
     * 去除逗号。<BR>
     * 把字符串的数据的逗号去掉。
     *
     * @param targetNumber 字符串
     * @return 转换后的字符串
     */
    public static String removeComma(String targetNumber) {
        if (targetNumber == null) {
            return targetNumber;
        }
        return targetNumber.replaceAll(DELIMITER_HALF_COMMA, "");
    }

    /**
     * 判断是否为整数
     *
     * @param o
     * @return
     */
    public static boolean isInteger(Object o) {
        if ((o != null) && (o != ""))
            return o.toString().matches("^[0-9]*$");
        else
            return false;
    }

    /**
     * 判断字符串是否是浮点数
     *
     * @param value
     * @return
     */
    public static boolean isDouble(Object value) {
        try {
            Double.parseDouble(String.valueOf(value));
            if (String.valueOf(value).contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断是否为数字
     *
     * @param value
     * @return
     */
    public static boolean isNumber(Object value) {
        return isInteger(value) || isDouble(value);
    }

    public static String getPercent(double targetNumber, double totalMumber, int digits) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(digits);
        String result = numberFormat.format((targetNumber / totalMumber) * 100);
        return result + "%";
    }

    public static double convertHandlingRate(double handlingRate) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        return Double.parseDouble(numberFormat.format(handlingRate * 100));
    }

    //两个float类型的费率相乘的精度调整
    public static Float formatFloat(Float rate1,Float rate2){
        Float result = null;
        try {
            BigDecimal rate3 = new BigDecimal(String.valueOf(rate1));
            BigDecimal rate4 = new BigDecimal(String.valueOf(rate2));

            result = rate3.multiply(rate4).floatValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 多个float值相乘
     * @param floatValues
     * @return Float
     */
    public static Float formatMultiplyFloat(Float...floatValues){

        if(floatValues.length == 0) {
            return null;
        }
        if(floatValues.length == 1) {
            return floatValues[0];
        }
        BigDecimal result = new BigDecimal(String.valueOf(floatValues[0]));
        for(int i=1;i < floatValues.length;i++){
            result = result.multiply(new BigDecimal(String.valueOf(floatValues[i])));
        }
        return result.floatValue();
    }

    /**
     * 两数相除
     * @param rate1 分 母
     * @param rate2 分 子
     * @return Float
     */
    public static Float formatDivideFloat(Float rate1,Float rate2){

        BigDecimal num1 = new BigDecimal(String.valueOf(rate1));
        BigDecimal num2 = new BigDecimal(String.valueOf(rate2));

        Float result = num1.divide(num2,2,BigDecimal.ROUND_HALF_UP).floatValue();
        return result;
    }

    /**
     * 返回指定精度的数字字符串
     * @param number
     * @param formatStr 举例:两位精度传 "0.00"
     * @return String
     */
    public static String formatAccuracy(BigDecimal number , String formatStr) throws Exception{
        DecimalFormat decimalFormat = new DecimalFormat(formatStr);
        return decimalFormat.format(number);
    }

    /**
     * 把输入的金额转换为汉语中人民币的大写
     *
     * @param numberOfMoney
     *            输入的金额
     * @return 对应的汉语大写
     */
    public static String number2CN(BigDecimal numberOfMoney) {
        StringBuffer sb = new StringBuffer();
        // -1, 0, or 1 as the value of this BigDecimal is negative, zero, or
        // positive.
        int signum = numberOfMoney.signum();
        // 零元整的情况
        if (signum == 0) {
            return CN_ZEOR_FULL;
        }
        //这里会进行金额的四舍五入
        long number = numberOfMoney.movePointRight(MONEY_PRECISION)
                .setScale(0, 4).abs().longValue();
        // 得到小数点后两位值
        long scale = number % 100;
        int numUnit = 0;
        int numIndex = 0;
        boolean getZero = false;
        // 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
        if (!(scale > 0)) {
            numIndex = 2;
            number = number / 100;
            getZero = true;
        }
        if ((scale > 0) && (!(scale % 10 > 0))) {
            numIndex = 1;
            number = number / 10;
            getZero = true;
        }
        int zeroSize = 0;
        while (true) {
            if (number <= 0) {
                break;
            }
            // 每次获取到最后一个数
            numUnit = (int) (number % 10);
            if (numUnit > 0) {
                if ((numIndex == 9) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
                }
                if ((numIndex == 13) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
                }
                sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                getZero = false;
                zeroSize = 0;
            } else {
                ++zeroSize;
                if (!(getZero)) {
                    sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                }
                if (numIndex == 2) {
                    if (number > 0) {
                        sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                    }
                } else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                }
                getZero = true;
            }
            // 让number每次都去掉最后一个数
            number = number / 10;
            ++numIndex;
        }
        // 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
        if (signum == -1) {
            sb.insert(0, CN_NEGATIVE);
        }
        // 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
        if (!(scale > 0)) {
            sb.append(CN_FULL);
        }
        return sb.toString();
    }
}
