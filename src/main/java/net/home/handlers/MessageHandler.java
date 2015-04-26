package net.home.handlers;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

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
        	result = handleTextRequests(msg);
            result = TulingApiHandler.getTulingResult(msg.getContent());
            System.out.println(result);
            break;
        default:
            break;
        }
        
        
        return result;
    }

	private String handleTextRequests(IncomingMessage msg) {
		if ("帮助".equals(msg.getContent())){
			return genHelp();
		}
		return null;
	}
	
	private String genHelp(){
	    String result = "";
	    try {
            result = IOUtils.toString(getClass().getResourceAsStream("help.txt"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    return result;
	}

}
