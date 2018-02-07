package com.lc.utils.other;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;

/**
 * Created by yangpeng<yangpeng01@vcredit.com>.
 * Date: 2016/9/8
 */

public class ImageUtils {/*

//    public static void main(String args[]) throws Exception {
//        BufferedImage img1 = ImageIO.read(new File("D:/1.jpg"));
//        BufferedImage img2 = ImageIO.read(new File("D:/2.jpg"));
//        BufferedImage joinedImg = twoImagesIntoOne(img1, img2);
//        ImageIO.write(joinedImg, "jpg", new File("D:/3.jpg"));
//    }

    private static final Logger logger = Logger.getLogger(ImageUtils.class);

    public static byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }

    public static BufferedImage twoImagesIntoOne(BufferedImage img1, BufferedImage img2) {
        int offset = 20;
        int width = Math.max(img1.getWidth(), img2.getWidth());
        int height = img1.getHeight() + img2.getHeight() + offset;
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
        g2.setPaint(Color.white);
        g2.fillRect(0, 0, width, height);
        g2.setColor(oldColor);
        g2.drawImage(img1, null, 0, 0);
        g2.drawImage(img2, null, 0, img1.getHeight() + offset);
        g2.dispose();
        return newImage;
    }

    public static Boolean uploadProcessingImageBase64(JlhCreditAuthInfo auhtInfo) {
        try {
            Integer year = DateUtil.getYear(auhtInfo.getAuthTime());
            Integer month = DateUtil.getMonth(auhtInfo.getAuthTime());
            Integer day = DateUtil.getDay(auhtInfo.getAuthTime());
            String imagePath = "/data/picture/processing/" + year +"/"+ month +"/"+ day +"/" + auhtInfo.getApplyId() +"/";

            logger.info("----" + imagePath);

            File file = new File(imagePath);

            if(!file.exists()&&!file.isDirectory())
            {
                boolean isMk = file.mkdirs();
            }

            if(!"".equals(auhtInfo.getIdcardImageReverseBase64())) {
                GenerateLocalImage(auhtInfo.getIdcardImageReverseBase64(),imagePath + "reverse.png");
            }


            if(!"".equals(auhtInfo.getIdcardImageObverseBase64())) {
                GenerateLocalImage(auhtInfo.getIdcardImageObverseBase64(),imagePath + "obverse.png");
            }

            if(!"".equals(auhtInfo.getFaceBase64())) {
                GenerateLocalImage(auhtInfo.getFaceBase64(),imagePath + "face.png");
            }

            if(!"".equals(auhtInfo.getCreditAuthorizationBase64())) {
                GenerateLocalImage(auhtInfo.getCreditAuthorizationBase64(),imagePath + "auth.png");
            }

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info(ex.getMessage());
            return false;
        }

    }

    public static Boolean uploadDrawSignImageBase64(JlhDrawAgreementSign jlhDrawAgreementSign) {
        try {
            Integer year = DateUtil.getYear(jlhDrawAgreementSign.getApplyStartTime());
            Integer month = DateUtil.getMonth(jlhDrawAgreementSign.getApplyStartTime());
            Integer day = DateUtil.getDay(jlhDrawAgreementSign.getApplyStartTime());
            String imagePath = "/data/picture/draw/" + year +"/"+ month +"/"+ day +"/" + jlhDrawAgreementSign.getApplyId() +"/";

            logger.info("--uploadDrawSignImageBase64--" + imagePath);

            File file = new File(imagePath);

            if(!file.exists()&&!file.isDirectory())
            {
                boolean isMk = file.mkdirs();
            }

            if(!"".equals(jlhDrawAgreementSign.getUserSign())) {
                GenerateLocalImage(jlhDrawAgreementSign.getUserSign(),imagePath+ "sign.png");
            }


            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info(ex.getMessage());
            return false;
        }

    }

    public static JlhCreditAuthInfo getProcessingImageBase64ByApplyInfo(Long applyId, Date applyStartDate) {
        try {
            JlhCreditAuthInfo authInfo = new JlhCreditAuthInfo();

            Integer year = DateUtil.getYear(applyStartDate);
            Integer month = DateUtil.getMonth(applyStartDate);
            Integer day = DateUtil.getDay(applyStartDate);

            String imagePath = "/data/picture/processing/" + year +"/"+ month +"/"+ day +"/" + applyId +"/";

            logger.info("----" + imagePath);
            File file = new File(imagePath);

            if(!file.exists()&&!file.isDirectory())
            {
                logger.info("----not exit :" + imagePath);
                return null;
            }

            authInfo.setApplyId(applyId);
            authInfo.setIdcardImageReverseBase64(GetImageBase64(imagePath + "reverse.png"));
            authInfo.setIdcardImageObverseBase64(GetImageBase64(imagePath + "obverse.png"));
            authInfo.setFaceBase64(GetImageBase64(imagePath + "face.png"));

            file = new File(imagePath+ "auth.png");
            if(!file.exists()&&!file.isFile()) {
                authInfo.setCreditAuthorizationBase64(null);
            } else {
                authInfo.setCreditAuthorizationBase64(GetImageBase64(imagePath + "auth.png"));
            }

            return authInfo;
        } catch (Exception ex) {
            logger.info("getProcessingImageBase64ByApplyInfo" + applyId+ ex.getMessage());
            return null;
        }

    }

    public static JlhDrawAgreementSign getDrawSignImageBase64ByApplyInfo(Long applyId, Date applyStartDate) {
        try {
            JlhDrawAgreementSign authInfo = new JlhDrawAgreementSign();

            Integer year = DateUtil.getYear(applyStartDate);
            Integer month = DateUtil.getMonth(applyStartDate);
            Integer day = DateUtil.getDay(applyStartDate);

            String imagePath = "/data/picture/draw/" + year +"/"+ month +"/"+ day +"/" + applyId + "/"  + "sign.png";;

            logger.info("--getDrawSignImageBase64ByApplyInfo--" + imagePath);
            File file = new File(imagePath);

            if(!file.exists()&&!file.isDirectory())
            {
                logger.info("----not exit :" + imagePath);
                return null;
            }

            authInfo.setApplyId(applyId);
            authInfo.setApplyStartTime(applyStartDate);

            file = new File(imagePath);
            if(!file.exists()&&!file.isFile()) {
                authInfo.setUserSign(null);
            } else {
                authInfo.setUserSign(GetImageBase64(imagePath));
            }

            return authInfo;
        } catch (Exception ex) {
            logger.info("getProcessingImageBase64ByApplyInfo" + applyId+ ex.getMessage());
            return null;
        }

    }


    public static Boolean copyImageToNewOrder(Long fromApplyId, Date formApplyStartDate, Long toApplyId, Date toApplyStartDate) {
        try {

            Integer year = DateUtil.getYear(formApplyStartDate);
            Integer month = DateUtil.getMonth(formApplyStartDate);
            Integer day = DateUtil.getDay(formApplyStartDate);
            String imagePath = "/data/picture/processing/" + year +"/"+ month +"/"+ day +"/" + fromApplyId+"/";

            Integer toYear = DateUtil.getYear(toApplyStartDate);
            Integer toMonth = DateUtil.getMonth(toApplyStartDate);
            Integer toDay = DateUtil.getDay(toApplyStartDate);
            String destDir = "/data/picture/processing/" + toYear +"/"+ toMonth +"/"+ toDay +"/" + toApplyId+"/";

            FileOperateUtil.copyGeneralFile(imagePath,destDir);

            return true;
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            return false;
        }

    }

    public static String GetImageBase64(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        try {
            InputStream in = null;
            byte[] data = null;
            // 读取图片字节数组
            try {
                in = new FileInputStream(imgFile);
                data = new byte[in.available()];
                in.read(data);
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            // 对字节数组Base64编码
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(data).replaceAll("\r|\n", "");// 返回Base64编码过的字节数组字符串
        } catch (Exception e) {
            return null;
        }
    }

    //base64字符串转化成图片
    public static boolean GenerateLocalImage(String imgStr, String imgPath) {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            FileOutputStream out = new FileOutputStream(imgPath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

*/
}
