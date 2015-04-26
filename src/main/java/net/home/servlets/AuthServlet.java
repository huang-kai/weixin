package net.home.servlets;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.home.handlers.TulingApiHandler;
import net.home.pojo.IncomingMessage;

import org.apache.commons.io.IOUtils;

import com.thoughtworks.xstream.XStream;


public class AuthServlet extends HttpServlet{
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException{
        System.out.println("signature: " +req.getParameter("signature"));
        System.out.println("timestamp: "+req.getParameter("timestamp"));
        System.out.println("nonce: "+ req.getParameter("nonce"));
        System.out.println("echostr:" + req.getParameter("echostr"));
        resp.setStatus(HttpServletResponse.SC_OK);
        try {
            resp.getWriter().println(req.getParameter("echostr"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException{
        try {
            String msg = IOUtils.toString(req.getInputStream(),"utf-8");
            System.out.println(msg);
            XStream xstream = new XStream();
            xstream.processAnnotations(IncomingMessage.class);
            IncomingMessage inMsg = (IncomingMessage)xstream.fromXML(msg);
            System.out.println("Object Message: " + inMsg);
            String result = TulingApiHandler.getTulingResult(inMsg.getContent());
            System.out.println(result);
            
            resp.setStatus(HttpServletResponse.SC_OK);
            
            OutputStream out = resp.getOutputStream();
            out.write(formatXmlAnswer(inMsg.getFromUserName(),inMsg.getToUserName(),result).getBytes("utf-8"));
            out.flush();
            out.close();
            
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
    }
    
    public String formatXmlAnswer(String to, String from, String content) {  
        StringBuffer sb = new StringBuffer();  
        Date date = new Date();  
        sb.append("<xml><ToUserName><![CDATA[");  
        sb.append(to);  
        sb.append("]]></ToUserName><FromUserName><![CDATA[");  
        sb.append(from);  
        sb.append("]]></FromUserName><CreateTime>");  
        sb.append(date.getTime());  
        sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");  
        sb.append(content);  
        sb.append("]]></Content><FuncFlag>0</FuncFlag></xml>");  
        return sb.toString();  
    }  
}
