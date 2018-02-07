package com.lc.utils.weiChat;

import com.lc.utils.other.DateUtil;
import com.lc.utils.other.HttpRequestUtil;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * 获取公共号token
 * @author feihaitao
 *
 */
public class WeixinH5PayUtil {
	/**
	 * 第4部支付前先下单
	 * https://api.mch.weixin.qq.com/pay/unifiedorder
	 * @param oppenID
	 * @return
	 * @throws Exception 
	 */
	public static PayOrderResponse createOrder(String oppenID,CreateOrderNotifyResponse createOrderNotifyResponse){
		PayOrderRequest request = new PayOrderRequest();
		PayOrderResponse refundResponse=null;
		request.setAppid(WeiChatConfig.pay_id_app);
		request.setMch_id(WeiChatConfig.GZH_MCH_ID);
		request.setNonce_str(RandomStringUtils.randomAlphanumeric(32));
		request.setBody(createOrderNotifyResponse.getBody());
		request.setOut_trade_no(createOrderNotifyResponse.getOut_trade_no());
		
		request.setTotal_fee(createOrderNotifyResponse.getTotal_fee());
		request.setSpbill_create_ip(createOrderNotifyResponse.getSpbill_create_ip());
		request.setNotify_url(createOrderNotifyResponse.getNotify_url());
		request.setTrade_type(createOrderNotifyResponse.getTrade_type());
		request.setTime_start(DateUtil.format(DateUtil.getCurrentDate(),DateUtil.yyyyMMddHHmmss));
		request.setTime_expire(DateUtil.format(DateUtil.addMinute(DateUtil.getCurrentDate(),10),DateUtil.yyyyMMddHHmmss));
		//request.setOpenid(oppenID);
		try {
			Map<String,Object> requestMap = new HashMap<String,Object>();
			requestMap.put("appid", request.getAppid());
			requestMap.put("mch_id", request.getMch_id());
			requestMap.put("nonce_str", request.getNonce_str());
			requestMap.put("body", request.getBody().trim());
			requestMap.put("out_trade_no", request.getOut_trade_no());
			requestMap.put("total_fee", request.getTotal_fee());
			requestMap.put("spbill_create_ip", request.getSpbill_create_ip());
			requestMap.put("notify_url", request.getNotify_url());
			requestMap.put("trade_type", request.getTrade_type());
			requestMap.put("time_start",request.getTime_start());
			requestMap.put("time_expire",request.getTime_expire());
			//requestMap.put("openid", request.getOpenid());
			request.setSign(CreateSign.signRequest(requestMap).toUpperCase());
			String requestXml = XMLUtil.convertToXml(request);
			String returnXml= HttpRequestUtil.doPost("https://api.mch.weixin.qq.com/pay/unifiedorder",requestXml);
	        refundResponse = XMLUtil.converyToJavaBean(returnXml, PayOrderResponse.class);
	        
	        if(refundResponse.getResult_code().equals("FAIL")){
	        	refundResponse.setErr_code_des(refundResponse.getErr_code_des());
	        }
		} catch (Exception e) {
		}
		return refundResponse;
	}
	/**
	 * 下单后第二次签名返回给H5页面进行支付
	 * @param payOrderResponse
	 * @return
	 */
	public static SecondSign secondSign(PayOrderResponse payOrderResponse){
		SecondSign secondSign=new SecondSign();
		secondSign.setAppId(payOrderResponse.getAppid());
		secondSign.setNonceStr(payOrderResponse.getNonce_str());
		if(payOrderResponse.getTrade_type().equals("APP")){
			secondSign.setPackageValue("Sign=WXPay");
		}else {
			secondSign.setPackageValue("prepay_id=" + payOrderResponse.getPrepay_id());
		}
		secondSign.setTimeStamp(String.valueOf(System.currentTimeMillis()/1000));
		secondSign.setPrepayid(payOrderResponse.getPrepay_id());
		secondSign.setPartnerid(WeiChatConfig.GZH_MCH_ID);
		Map<String,Object> request=new HashMap<>();
		request.put("appid", secondSign.getAppId());
		if(payOrderResponse.getTrade_type().equals("APP")){
			request.put("partnerid",secondSign.getPartnerid());
			request.put("prepayid",secondSign.getPrepayid());
		}else{
			request.put("signType", secondSign.getSignType());
		}
		request.put("package", secondSign.getPackageValue());
		request.put("noncestr", secondSign.getNonceStr());
		request.put("timestamp", secondSign.getTimeStamp());
		secondSign.setPaySign(CreateSign.signRequest(request));
		return secondSign;
	}
	public static void main(String[] args) throws Exception {

	}
}
