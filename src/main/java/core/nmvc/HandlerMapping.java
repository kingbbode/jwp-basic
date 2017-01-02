package core.nmvc;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by YG-MAC on 2017. 1. 2..
 */
public interface HandlerMapping {
    void initialize();
    Object getHandler(HttpServletRequest request);
}
