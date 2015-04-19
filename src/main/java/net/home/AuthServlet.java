package net.home;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
}
