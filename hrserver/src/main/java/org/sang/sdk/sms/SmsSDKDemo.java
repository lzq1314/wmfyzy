package org.sang.sdk.sms;

import org.sang.sdk.sms.yun.SmsSingleSender;
import org.sang.sdk.sms.yun.SmsSingleSenderResult;

public class SmsSDKDemo {
    public static void main(String[] args) {
    	try {
    		//请根据实际 accesskey 和 secretkey 进行开发，以下只作为演示 sdk 使用
    		String accesskey = "5fbcbe1c3e8e4b1900eb71f8";
    		String secretkey ="ded6b4af0cc24644aa9886aca0701921";
    		//手机号码
    		String phoneNumber = "18746017546";
    		 //初始化单发
	    	SmsSingleSender singleSender = new SmsSingleSender(accesskey, secretkey);
	    	SmsSingleSenderResult singleSenderResult;
	
	    	 //普通单发,注意前面必须为【】符号包含，置于头或者尾部。
	    singleSenderResult = singleSender.send(0, "86", phoneNumber, "【Kewail科技】尊敬的用户：您的验证码：123456，工作人员不会索取，请勿泄漏。", "", "");
	    	System.out.println(singleSenderResult);
	    	
	    	
	    	//语音验证码发送
    		//SmsVoiceVerifyCodeSender smsVoiceVerifyCodeSender = new SmsVoiceVerifyCodeSender(accesskey,secretkey);
    		//SmsVoiceVerifyCodeSenderResult smsVoiceVerifyCodeSenderResult = smsVoiceVerifyCodeSender.send("86",phoneNumber, "444144",2,"");
    		//System.out.println(smsVoiceVerifyCodeSenderResult);

    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
