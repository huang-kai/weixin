package net.home.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.home.handlers.Handler;
import net.home.handlers.MessageHandler;
import net.home.pojo.IncomingMessage;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;

public class PlatformServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(PlatformServlet.class.getName());
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        logger.debug("signature: " + req.getParameter("signature"));
        logger.debug("timestamp: " + req.getParameter("timestamp"));
        logger.debug("nonce: " + req.getParameter("nonce"));
        logger.debug("echostr:" + req.getParameter("echostr"));
        resp.setStatus(HttpServletResponse.SC_OK);
        try {
            resp.getWriter().println(req.getParameter("echostr"));
        } catch (IOException e) {
            logger.error("error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        OutputStream out = null;
        try {
            String msg = IOUtils.toString(req.getInputStream(), "utf-8");
            logger.debug(msg);
            XStream xstream = new XStream();
            xstream.processAnnotations(IncomingMessage.class);
            IncomingMessage inMsg = (IncomingMessage) xstream.fromXML(msg);
            logger.debug("Object Message: " + inMsg);
            Handler handler = new MessageHandler();
            String result = handler.handleMsg(inMsg);
            logger.debug(result);

            resp.setStatus(HttpServletResponse.SC_OK);

            out = resp.getOutputStream();
            out.write(formatXmlAnswer(inMsg.getFromUserName(), inMsg.getToUserName(), result).getBytes("utf-8"));
            out.flush();

        } catch (IOException e1) {
            logger.error("Do post error", e1);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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
