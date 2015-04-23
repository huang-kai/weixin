package net.home.handlers;

import net.home.pojo.IncomingMessage;

public class MessageHandler {
    
    public String receiveMsg(IncomingMessage msg){
        System.out.println("Object Message: " + msg);
        String result = " ";
        switch (msg.getMsgType()) {
        case "event":
            if ("subscribe".equals(msg.getEventKey())){
                return "欢迎来到Kyne的世界，您可以试着和可以和我聊天";
            }
            break;
        case "text":
            result = TulingApiHandler.getTulingResult(msg.getContent());
            System.out.println(result);
            break;
        default:
            break;
        }
        
        
        return result;
    }

}
