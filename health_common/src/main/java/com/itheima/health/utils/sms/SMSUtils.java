package com.itheima.health.utils.sms;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class SMSUtils {
    public static final String VALIDATE_CODE = "SMS_204295027";//发送短信验证码
    public static final String ORDER_NOTICE = "SMS_204295995";//体检预约成功通知

    public static void registerSendCode(String sendyourTelephoneNumber,String codeNumber)  {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G8d3QWV3J8jcg6UUuRM", "byW4UOaIJ7Gm0JouiTzdkHexIHdtvB");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", sendyourTelephoneNumber);
        request.putQueryParameter("SignName", "传智健康CCC");
        request.putQueryParameter("TemplateCode", VALIDATE_CODE);
        request.putQueryParameter("TemplateParam", "{\"code\":\""+codeNumber+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }


    public static void appointment_Is_Ok(String sendyourTelephoneNumber,String codeNumber)  {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G8d3QWV3J8jcg6UUuRM", "byW4UOaIJ7Gm0JouiTzdkHexIHdtvB");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", sendyourTelephoneNumber);
        request.putQueryParameter("SignName", "传智健康CCC");
        request.putQueryParameter("TemplateCode", ORDER_NOTICE);
        request.putQueryParameter("TemplateParam", "{\"code\":\""+codeNumber+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }



}
