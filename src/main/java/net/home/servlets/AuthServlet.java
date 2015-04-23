package net.home.servlets;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.home.pojo.IncomingMessage;

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
        System.out.println("signature: " +req.getParameter("signature"));
        System.out.println("timestamp: "+req.getParameter("timestamp"));
        System.out.println("nonce: "+ req.getParameter("nonce"));
        System.out.println("echostr:" + req.getParameter("echostr"));
        try {
            System.out.println("message:" + req.getInputStream());
            XStream xstream = new XStream();
            IncomingMessage inMsg = (IncomingMessage)xstream.fromXML(req.getInputStream());
            System.out.println("Object Message: " + inMsg);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        resp.setStatus(HttpServletResponse.SC_OK);
        try {
            resp.getWriter().println("cat love mouse!");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
