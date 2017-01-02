package core.nmvc;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

public class AnnotationHandlerMapping implements HandlerMapping{
    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    private ControllerScanner controllerScanner;

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        controllerScanner = new ControllerScanner(basePackage);
        for(Map.Entry<Class<?>, Object> entry : controllerScanner.getControllers().entrySet()){
            String controllerValue = entry.getKey().getAnnotation(Controller.class).value();
            for(Method method : ReflectionUtils.getAllMethods(entry.getKey(),ReflectionUtils.withAnnotation(RequestMapping.class))){
                putHandler(controllerValue, method, entry.getValue());
            }
        }
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }

    private void putHandler(String controllerValue, Method method, Object instance) {
        if (method.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);

            handlerExecutions.put(new HandlerKey(controllerValue + requestMapping.value(), requestMapping.method()), new HandlerExecution(method, instance));
        }
    }
}
