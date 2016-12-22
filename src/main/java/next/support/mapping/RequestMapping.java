package next.support.mapping;

import com.google.common.collect.Maps;
import next.controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by YG-MAC on 2016. 12. 22..
 */
public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(RequestMapping.class);

    public RequestMapping() {
        mapping.put("/users", new ListUserController());
        LoginController loginController = new LoginController();
        mapping.put("/users/login", new ForwardController("/users/login.jsp"));
        mapping.put("/users/loginForm", loginController);
        mapping.put("/users/form", new CreateUserController());
        mapping.put("/users/create", new ForwardController("/users/form.jsp"));
        mapping.put("/users/logout", new LogoutController());
    }

    private Map<String, Controller> mapping = Maps.newHashMap();
    public Controller getController(String url){
        return mapping.get(url);
    }
}
