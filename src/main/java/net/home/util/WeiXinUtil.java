package net.home.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.json.JSONObject;

public class WeiXinUtil {
    
    private final static String WEI_XIN_API_TOKEN="https://api.weixin.qq.com/cgi-bin/token";
    
    public static String getAccessToken(){
        HttpClientHelper httpClient = HttpClientHelper.getInstance(null, 0);
        Properties properties = new Properties();
        String accessToken = "";
        try {
            properties.load(WeiXinUtil.class.getResourceAsStream("WeiXinCredentials.properties"));
            Map<String, String> param = new HashMap<String, String>();
            param.put("grant_type", "client_credential");
            param.put("appid", properties.getProperty("appid"));
            param.put("secret", properties.getProperty("secret"));
            String response = httpClient.doGet(WEI_XIN_API_TOKEN, param, null);
            JSONObject result = new JSONObject(response);
            accessToken = result.getString("access_token");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return accessToken;
    }
    
//    public static String uploadTempImage()

}
