package next.support.mapping;

import com.google.common.collect.Maps;
import next.controller.*;
import next.enums.HttpRequestType;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * Created by YG-MAC on 2016. 12. 22..
 */
public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(RequestMapping.class);

    public RequestMapping() {
        Reflections reflections = new Reflections("next.controller");
        Set<Class<? extends Controller>> classes = reflections.getSubTypesOf(Controller.class);
        classes.stream().forEach(clazz -> logger.info("class name - {}", clazz.getName()));

        mapping.put("/users/login", new ForwardController("/users/login.jsp"));
        mapping.put("/users/create", new ForwardController("/users/form.jsp"));

        mapping.put("/users", new ListUserController(new HttpRequestType[]{HttpRequestType.GET}));
        mapping.put("/users/loginForm", new LoginController(new HttpRequestType[]{HttpRequestType.POST}));
        mapping.put("/users/form", new CreateUserController(new HttpRequestType[]{HttpRequestType.POST, HttpRequestType.GET}));
        mapping.put("/users/logout", new LogoutController(new HttpRequestType[]{HttpRequestType.GET}));
    }

    private Map<String, Controller> mapping = Maps.newHashMap();
    public Controller getController(String url){
        return mapping.get(url);
    }
}
