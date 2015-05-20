package net.home.handlers;

import java.io.IOException;

import net.home.exception.GeneralException;
import net.home.pojo.IncomingMessage;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageHandler implements Handler{

    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class.getName());
    
    @Override
    public String handleMsg(IncomingMessage msg) {
        logger.debug("Object Message: " + msg);
        String result = " ";
        try {
            switch (msg.getMsgType()) {
            case "event":
                if ("subscribe".equals(msg.getEventKey())) {
                    return "欢迎来到Kyne的世界，您可以试着和可以和我聊天";
                }
                break;
            case "text":
                result = handleTextRequests(msg);
                break;
            case "image":
                result = handleImageRequests(msg);
                break;
            default:
                break;
            }
        } catch (GeneralException ex) {
            result = ex.getErrMsg();
        } catch (Exception e){
            logger.error("Message handle error", e);
            result = "系统错误！请联系管理员";
        }

        return result;
    }

    private String handleImageRequests(IncomingMessage msg) {
        FaceDetectHandler handler = new FaceDetectHandler();
        return handler.handleMsg(msg);
    }

    private String handleTextRequests(IncomingMessage msg) throws GeneralException{
        Handler handler;
        String content = msg.getContent();
        if ("帮助".equals(content)||"help".equals(content)) {
            return genHelp();
        } else if (content.startsWith("$")) {
            handler = new JournalHandler();
        } else if (content.startsWith("%")) {
            handler = new StatisticHandler();
        }else{
            handler = new TulingApiHandler();
        }
        return handler.handleMsg(msg);
    }

    private String genHelp() {
        StringBuffer result = new StringBuffer();
        try {
            result.append(IOUtils.toString(getClass().getResourceAsStream("mainHelp.txt"), "utf-8"));
            result.append("\n");
            result.append("\n");
            result.append(IOUtils.toString(getClass().getResourceAsStream("journalHelp.txt"), "utf-8"));
            result.append("\n");
            result.append("\n");
            result.append(IOUtils.toString(getClass().getResourceAsStream("statisticHelp.txt"), "utf-8"));
            result.append("\n");
            result.append("\n");
            result.append(IOUtils.toString(getClass().getResourceAsStream("howOldHelp.txt"), "utf-8"));
        } catch (IOException e) {
            logger.error("Get Help error", e);
        }
        return result.toString();
    }

}
