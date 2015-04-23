package net.home.handlers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.home.util.HttpClientHelper;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONException;
import org.json.JSONObject;

public class TulingApiHandler {
    /** 
     * 调用图灵机器人api接口，获取智能回复内容，解析获取自己所需结果 
     * @param content 
     * @return 
     */  
    public static String getTulingResult(String content){  
        /** 此处为图灵api接口，参数key需要自己去注册申请，先以11111111代替 */  
        String apiUrl = "http://www.tuling123.com/openapi/api";  
        Map<String, String> param = new HashMap<String, String>();  
        param.put("key", "b8accc0dd95e7ae445fdef04faeb290d");
        param.put("info", content);
//        try {  
//            param = apiUrl+URLEncoder.encode(content,"utf-8");  
//        } catch (UnsupportedEncodingException e1) {  
//            // TODO Auto-generated catch block  
//            e1.printStackTrace();  
//        } //将参数转为url编码  
          
        /** 发送httpget请求 */   
        String result = "";  
        try {  
            HttpClientHelper clientHelper = HttpClientHelper.getInstance(null, 0);
            result = clientHelper.doGet(apiUrl,param,null);  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        /** 请求失败处理 */  
        if(null==result){  
            return "对不起，你说的话真是太高深了……";  
        }  
          
        try {  
            JSONObject json = new JSONObject(result);  
            //以code=100000为例，参考图灵机器人api文档  
            int returnCode = json.getInt("code");
            switch (returnCode) {
            case 100000:
                result = json.getString("text");
                break;
            case 200000:
                result = json.getString("text");
                break;
                
            default:
                break;
            }
            
        } catch (JSONException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return result;  
    }  
}
