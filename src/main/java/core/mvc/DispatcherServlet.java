package core.mvc;

import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.HandlerExecution;
import core.nmvc.HandlerMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> mappings = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        mappings.add(new AnnotationHandlerMapping("next"));
        mappings.add(new LegacyHandlerMapping());
        mappings.stream().forEach(mappings -> mappings.initialize());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        try {
            Object handler = getHandler(req);
            ModelAndView mav = null;
            if (handler instanceof Controller) {
                mav = ((Controller) handler).execute(req, resp);
            } else if (handler instanceof HandlerExecution) {
                mav = ((HandlerExecution) handler).handle(req, resp);
            }

            if (mav == null) {
                throw new Exception();
            }

            View view = mav.getView();
            view.render(mav.getModel(), req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private Object getHandler(HttpServletRequest req) {
        Object result = null;
        for (HandlerMapping handlerMapping : mappings) {
            result = handlerMapping.getHandler(req);
            if (result != null) {
                return result;
            }
        }
        return result;
    }
}
