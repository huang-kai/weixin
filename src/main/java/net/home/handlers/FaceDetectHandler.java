package net.home.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.home.pojo.IncomingMessage;
import net.home.util.HttpClientHelper;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FaceDetectHandler implements Handler{
    
    private static final Logger logger = LoggerFactory.getLogger(FaceDetectHandler.class.getName());
    
    private static final String API_KEY = "66c963888c3bfe1759897ce8af8091ab";
    private static final String API_SECRET = "7ZPF4foXSR7ng3gzt_d4QGqCftkAjUSO";
    private static final String API_URL = "http://apicn.faceplusplus.com/v2/detection/detect";

    @Override
    public String handleMsg(IncomingMessage msg) {
        String url = msg.getPicUrl();
        logger.debug("Picture url is: {0}", url);
        Map<String, String> param = new HashMap<String, String>();
        param.put("api_key", API_KEY);
        param.put("api_secret", API_SECRET);
        param.put("url", url);
        HttpClientHelper httpClient = HttpClientHelper.getInstance(null, 0);
        try {
            String result = httpClient.doGet(API_URL, param, null);
            JSONObject face = new JSONObject(result);
            int age = face.getJSONArray("face").getJSONObject(0).getJSONObject("attribute").getJSONObject("age").getInt("value");
            logger.debug("Your age: "+ age);
            StringBuilder sb = new StringBuilder();
            sb.append("您的年龄大约"+age+"岁");
            sb.append("\n");
            double smile = face.getJSONArray("face").getJSONObject(0).getJSONObject("attribute").getJSONObject("smiling").getDouble("value");
            logger.debug("Your smile: "+smile);
            if (smile<=10){
                sb.append("看上去您今天心情不太好。");
            }else if (smile<=50&&smile>10){
                sb.append("笑一笑，10年少");
            }else if (smile<=80 && smile>50){
                sb.append("嗯！您今天心情不错！");
            }else if (smile>80){
                sb.append("笑得太厉害容易把牙笑掉了。");
            }
            return sb.toString();
        } catch (IOException e) {
            logger.error("Detect Face error", e);
        }
        return "error";
    }

}
