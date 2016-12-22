package next.support.servlet;

import next.controller.Controller;
import next.support.mapping.RequestMapping;
import next.support.resolver.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by YG-MAC on 2016. 12. 22..
 */
@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private RequestMapping requestMapping;

    @Override
    public void init(){
        logger.debug("init");
        requestMapping = new RequestMapping();
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Controller controller;
        if((controller = requestMapping.getController(req.getRequestURI().substring(req.getContextPath().length())))!= null){
            ViewResolver.resolve(controller.execute(req, res), req, res);
        }
    }
}
