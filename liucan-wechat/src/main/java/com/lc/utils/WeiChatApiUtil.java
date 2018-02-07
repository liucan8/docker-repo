package com.lc.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lc.utils.other.JsonUtil;
import com.lc.utils.weiChat.*;

import java.math.BigDecimal;
import java.util.*;


/**
 * 调用微信api
 * Created by feihaitao on 2017/2/20.
 */

public class WeiChatApiUtil {

    /**
     * 第二步  通过code换取网页授权access_token
     * @return
     * @throws Exception
     *
     * code 一个支付流程只用一个code 再次支付需要重新获取code
     */
    public static AccessTokenResponse getAccessToken(String code, String appId, String appSecret ) throws Exception{
        AccessTokenResponse accessTokenResponse=null;

        try {
            String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+appSecret+"&code="+code+"&grant_type=authorization_code";
            String request= HttpRequestUtil.doGet(url);
            accessTokenResponse= JsonUtil.jsonToBean1(request, AccessTokenResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessTokenResponse;
    }

        /**
         * 获取token
         * @return
         */
        public static String getToken(String appId,String appSecret){
            String token="";
            try {
                String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"+"&appid="+appId+"&secret="+appSecret;
                String request= HttpRequestUtil.doGet(url);
                JSONObject jsonObject=JSON.parseObject(request);
                token=(String) jsonObject.get("access_token");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return token;
        }


    /**
     * 获取token
     * @return
     */
    public static String getToken1(){
        String token="";
        try {
            String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"+"&appid=wx87e3fc0191dcd3d7&secret=e99c241b36eb055b19e338bb540c595d";
            String request= HttpRequestUtil.doGet(url);
            JSONObject jsonObject=JSON.parseObject(request);
            token=(String) jsonObject.get("access_token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 上传多媒体文件
     * https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE
     * @param token
     * @return 获取 mediaId
     */
    public static UploadResponse uploadImg(String token){
        UploadResponse response=null;
        try {
            String uploadimgUrl="https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+token+"&type=image";
            Map<String, String> fileMap = new HashMap<String, String>();
            fileMap.put("media", "D:\\20160506150937.jpg");
            String resurt= HttpPostUploadUtil.formUpload(uploadimgUrl, null, fileMap);
            response=JsonUtil.jsonToBean1(resurt, UploadResponse.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }


    /**
     * 获取用户基本信息
     * https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
     * @return
     */
    public static WeixinUseResponse getWeixiUse(String accessToken, String oppenId){
        WeixinUseResponse response=new WeixinUseResponse();
        try {
            String url="https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+oppenId+"&lang=zh_CN";
            String request=HttpRequestUtil.doGet(url);
            response=JsonUtil.jsonToBean1(request, WeixinUseResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

        /**
         * menu初始化
         * @return
         */
        public static Menu initMenu(){
            Menu menu=new Menu();

            ViewButton enterJLH=new ViewButton();
            enterJLH.setName("进入借乐花");
            enterJLH.setType("view");
            enterJLH.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WeiChatConfig.appId+"&redirect_uri="+Redirect.enterJLH_redirect_controller+"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");

            ViewButton valentineDay =new ViewButton();
            valentineDay.setName("情人节大礼");
            valentineDay.setType("view");
            valentineDay.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WeiChatConfig.appId+"&redirect_uri="+Redirect.valentineDay_redirect_controller+"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");

            //  http://jlh.wechat.vcash.cn/login?open_id=onaF1v4LINYGlm4qiGjZUFe1NLYM&product=jielehua
            //https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WeiChatConfig.appId+"&redirect_uri="+Redirect.introduce_redirect_controller+"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect
            ViewButton introduce=new ViewButton();
            introduce.setName("转介绍");
            introduce.setType("view");
            introduce.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WeiChatConfig.appId+"&redirect_uri="+Redirect.introduce_redirect_controller+"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");

            ViewButton contactCustomer=new ViewButton();
            contactCustomer.setName("联系客服");
            contactCustomer.setType("view");
            contactCustomer.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WeiChatConfig.appId+"&redirect_uri="+Redirect.contactCustomer_redirect_controller+"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");;

            ViewButton problems=new ViewButton();
            problems.setType("view");
            problems.setName("常见问题");
            problems.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WeiChatConfig.appId+"&redirect_uri="+Redirect.problems_redirect_controller+"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");

            ViewButton aboutJLH=new ViewButton();
            aboutJLH.setType("view");
            aboutJLH.setName("关于借乐花");
            aboutJLH.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WeiChatConfig.appId+"&redirect_uri="+Redirect.aboutJLH_redirect_controller+"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");

            ClickButton more=new ClickButton();
            more.setType("click");
            more.setName("更多精彩");
            more.setSub_button(new Button[]{introduce,contactCustomer,problems,aboutJLH});


            ViewButton sync=new ViewButton();
            sync.setName("账号同步");
            sync.setType("view");
            sync.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WeiChatConfig.appId+"&redirect_uri="+Redirect.sync_redirect_controller+"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");

            menu.setButton(new Button[]{enterJLH,more,sync});
            return menu;
        }

        /**
         * 自定义菜单创建
         * http请求方式：POST（请使用https协议）
         *  https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN
         * @return
         * @throws Exception
         */
        public static String createMenu(String token,String requests) throws Exception{
            String url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+token+"";
            String request=HttpRequestUtil.doPost(url,requests);
            return  request;
        }

    /**
     * 文子消息
     *
     * @param ToUserName
     * @param FromUserName
     * @return
     */
    public static String initTextMessage(String ToUserName,String FromUserName){
        TextMessage textMessage=new TextMessage();
        String JLH="\"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WeiChatConfig.appId+"&redirect_uri="+Redirect.enterJLH_redirect_controller+"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect\"";
        String problems="\"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WeiChatConfig.appId+"&redirect_uri="+Redirect.problems_redirect_controller+"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect\"";
        String about="\"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WeiChatConfig.appId+"&redirect_uri="+Redirect.aboutJLH_redirect_controller+"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect\"";
        String content="Hello! 欢迎关注借乐花!\n" +
                "我是花花，您的应急周转小帮手\n" +
                "花呗额度3步快速变现,\n" +
                "月息低至0.75%,\n" +
                "90天内想贷就贷!\n" +
                "以上这些惊喜，花花都能为您实现\n" +
                "是不是已经按捺不住你的洪荒之力了？那\n" +
                "还不快戳下方【进入借乐花】呆萌的花花\n" +
                "马上来到您身边\n\n" +
                "我是花花-借乐花！花呗额度轻松变现！\n" +
                "点击<a href="+JLH+">【进入借乐花】</a>,让您无忧贷款.\n" +
                "欲了解更多详情，您可以点击以下链接，\n" +
                "获取相关信息！\n" +
                "<a href="+problems+">【常见问题】</a>\n" +
                "<a href="+about+">【关于借乐花】</a>" ;
        textMessage.setContent(content);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType("text");
        textMessage.setFromUserName(ToUserName);
        textMessage.setToUserName(FromUserName);
        String message=XMLUtil.textMessage(textMessage);
        return message;
    }

    /**
     * 微信推广 推送
     * @param ToUserName
     * @param FromUserName

     * 千呼万唤始出来，万众瞩目的“借乐花”APP，终于在程序猿大大的不懈努力下出世啦！
    额度依旧诱人，服务依然贴心，快点击下载吧！马上体验焕然一新的花花！

     * @return
     */
    public  static  String download(String ToUserName,String FromUserName,String download){
        {
            TextMessage textMessage=new TextMessage();
            String content="千呼万唤始出来!\n" +
                    "万众瞩目的\"借乐花\"APP,\n" +
                    "终于在程序猿大大的不懈努力下出世啦!\n\n" +
                    "额度依旧诱人！服务依然贴心!\n" +
                    "<a href=\""+download+"\">快点击下载吧</a>!马上体验焕然一新的花花！\n";
            textMessage.setContent(content);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType("text");
            textMessage.setFromUserName(ToUserName);
            textMessage.setToUserName(FromUserName);
            String message=XMLUtil.textMessage(textMessage);
            return message;
        }
    }

    public  static String autoReply(String ToUserName,String FromUserName,String content){
        TextMessage textMessage=new TextMessage();
        textMessage.setContent(content);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType("text");
        textMessage.setFromUserName(ToUserName);
        textMessage.setToUserName(FromUserName);
        String message=XMLUtil.textMessage(textMessage);
        return message;
    }

    public static  String transferCustomerService(TransferCustomerService transferCustomerService){
        String message=XMLUtil.transferCustomerService(transferCustomerService);
        return  message;

    }

    /**
     * 创建二维码
     * https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN
     */
    public static QrcodeResponse createQrcode(String scene_str,String token){
        try {
            QrcodeRequest qrcodeRequest=new QrcodeRequest();
            qrcodeRequest.setAction_name("QR_LIMIT_STR_SCENE");
            QrcodeActionInfo actionInfo=new QrcodeActionInfo();
            QrcodeScene scene=new QrcodeScene();
            scene.setScene_str(scene_str);
            actionInfo.setScene(scene);
            qrcodeRequest.setAction_info(actionInfo);
            String url="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+token+"";
            String reruest=JsonUtil.beanToJson(qrcodeRequest);
            String response=HttpRequestUtil.doPost(url,reruest);
            QrcodeResponse qrcodeResponse=JsonUtil.jsonToBean1(response, QrcodeResponse.class);
            System.out.println("url=="+qrcodeResponse.getUrl());
            return qrcodeResponse;
        }catch (Exception e){

        }
       return  null;
    }

    /**
     * 获取二维码

     * @return
     */
    public static String getCodeUrl(String ticket){
        String url="";
        try {
            url="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticket;
           System.out.println(url);
           // request=HttpRequestUtil.doGet(url);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 获取ticket
     * @return
     */
    public static String getticket(String token){
        String ticket="";
        try {
            //https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
            String url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+token+"&type=jsapi";
            String request=HttpRequestUtil.doGet(url);
            JSONObject jsonObject=JSON.parseObject(request);
            ticket=(String) jsonObject.get("ticket");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticket;
    }

    /**
     * 根据openId进行自主推送
     * @param openIds
     * @param token
     * @return
     */
    public  static  String massSend(List<String> openIds,String token,String content){
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("touser",openIds);
            jsonObject.put("msgtype","text");
            JSONObject object=new JSONObject();
            object.put("content",content);
            jsonObject.put("text",object);
            String url="https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token="+token;
            String request= HttpRequestUtil.doPost(url,jsonObject.toString());
            System.out.println(request);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public  static  String deleteMass(BigDecimal msg_id,String token){
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("msg_id",msg_id);
            String url="https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token="+token;
            System.out.println(jsonObject.toString());
            String request= HttpRequestUtil.doPost(url,jsonObject.toString());
            System.out.println(request);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 微信消息模板推送
     * @param token
     * @param templateRequest
     * @return
     */
    public  static  String templateSend(String token,TemplateRequest templateRequest,List<TemplateDataRequest> dataRequests,String remark){
        try {
            String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token;
            JSONObject data=new JSONObject();
            int i=0;
            for (TemplateDataRequest dataRequest:dataRequests){
                if(i==0){
                    data.put("first",JSON.toJSON(dataRequest));
                }else{
                    data.put("keyword"+i,JSON.toJSON(dataRequest));
                }
                i++;
            }
            JSONObject remarkJson=new JSONObject();
            remarkJson.put("value",remark);
            data.put("remark",remarkJson);
            templateRequest.setData(data);
            String par=JSONObject.toJSONString(templateRequest);
            System.out.println(par);
            String request= HttpRequestUtil.doPost(url,par);
            System.out.println(request);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";

    }






    public static void main(String[] args) {
        try {

           String token= getToken("wx87e3fc0191dcd3d7","e99c241b36eb055b19e338bb540c595d");
            TemplateRequest templateRequest =new TemplateRequest();
            templateRequest.setTouser("odrsqwBgmhULN4hHUw2Q9byJq8Lk");
            templateRequest.setTemplate_id("4x7AmCwCP6r0kp9IgtpuuZmK6gdC8isfHg8RWhIV2j0");
            List<TemplateDataRequest> dataRequests=new ArrayList<>();
            TemplateDataRequest templateDataRequest=new TemplateDataRequest();
            templateDataRequest.setValue("尊敬的许胜客户，你贷款的1个亿已放款");
            dataRequests.add(templateDataRequest);
            TemplateDataRequest keyword1=new TemplateDataRequest();
            keyword1.setValue("可申请贷款");
            dataRequests.add(keyword1);
            TemplateDataRequest keyword2=new TemplateDataRequest();
            keyword2.setValue("10000000000万");
            TemplateDataRequest keyword3=new TemplateDataRequest();
            dataRequests.add(keyword2);
            dataRequests.add(keyword3);
            templateSend(token,templateRequest,dataRequests,"详情请联系110");
           /* setIndustry(token);
            getIndustry(token);*/
            //deleteMass(new BigDecimal("3147483650"),token);
           /* List<String> openIds =new ArrayList<>();
            openIds.add("odrsqwBgmhULN4hHUw2Q9byJq8Lk");
            openIds.add("odrsqwBtixF0EUMWVd9wDu5uCXU0");*/
            //openIds.add("odrsqwG3VZeeJvHIVBPu0jz8dOVg1");
            //massSend(openIds,token,"自主推送测试");
           /* String date="2017-01-20 12:09:17";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1 = simpleDateFormat.parse(date);
            long ts = date1.getTime();

            System.out.println(ts);*/
        }catch (Exception e){

        }


       // getCodeUrl(createQrcode("12","-9B2LU801zJ2QidcqaktcX4brfvm9vJwr_lVdoRFPWOYh3dOhZoYxrmNs83_XX2ktM_KynwuOzbpj2oYK2TrEfyi6ODx2CYuh62Bss-01mdKoVKuRJQAv4Q5HQRVI2cdEFCdAGAVWT").getTicket());
        /* try {
            String token=getToken();
            System.out.println(token+"--");
            UploadResponse uploadResponse=uploadImg(token);
            System.out.println( uploadResponse.getMedia_id());
            String url="http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="+token+"&media_id="+uploadResponse.getMedia_id();
            System.out.println(url);
            DownloadFileUtil a=new DownloadFileUtil(url,"d://","69.jpg");
            a.run();
            System.out.println(FileUtil.encodeBase64File("d://69.jpg"));
        }catch (Exception e){
            e.printStackTrace();;
        }*/


        //getImage(token,uploadResponse.getMedia_id());
        ///System.out.println(System.currentTimeMillis());
       /* UUID uuid = UUID.randomUUID();
        getCodeUrl(createQrcode(uuid+"_JLH",getToken("wxf54d8bb270b14b5e","81d5e766af4b3d520f23dcce2d8602a8")).getTicket());
        System.out.println("qrscene_"+uuid+"_JLH");*/
         // Menu menu=initMenu();
            try {
               /* String json= JsonUtil.beanToJson(menu);
                //String request=createMenu(getToken("wxf54d8bb270b14b5e","81d5e766af4b3d520f23dcce2d8602a8"),json);
                String request=createMenu(getToken("wx87e3fc0191dcd3d7","e99c241b36eb055b19e338bb540c595d"),json);

               System.out.println(request);
               System.out.println(json);*/
            }catch (Exception e){
                e.printStackTrace();
            }

        }
}
