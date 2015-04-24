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
        	result = handleTestRequests(msg);
            result = TulingApiHandler.getTulingResult(msg.getContent());
            System.out.println(result);
            break;
        default:
            break;
        }
        
        
        return result;
    }

	private String handleTestRequests(IncomingMessage msg) {
		if ("帮助".equals(msg.getContent())){
			return genHelp();
		}
		return null;
	}
	
	private String genHelp(){
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎使用Kyne的平台，您可以随时找小轩轩聊天。\\\n");
		sb.append("您也可以输入 ‘轩轩的照片’");
		return sb.toString();
	}

}
