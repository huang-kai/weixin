package net.home.handlers;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

import net.home.exception.ErrCode;
import net.home.exception.GeneralException;
import net.home.pojo.IncomingMessage;

public class MessageHandler implements Handler{

    public String handleMsg(IncomingMessage msg) {
        System.out.println("Object Message: " + msg);
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
                System.out.println(result);
                break;
            default:
                break;
            }
        } catch (GeneralException ex) {
            result = ex.getErrMsg();
        }

        return result;
    }

    private String handleTextRequests(IncomingMessage msg) throws GeneralException{
        Handler handler;
        String content = msg.getContent();
        if ("帮助".equals(content)) {
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
        String result = "";
        try {
            result = IOUtils.toString(getClass().getResourceAsStream("help.txt"), "utf-8");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

}
