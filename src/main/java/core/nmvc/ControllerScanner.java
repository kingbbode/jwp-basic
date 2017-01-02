package core.nmvc;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * Created by YG-MAC on 2017. 1. 2..
 */
public class ControllerScanner {
    private static final Logger logger = LoggerFactory.getLogger(ControllerScanner.class);

    private Map<Class<?>, Object> controllers = Maps.newHashMap();

    public ControllerScanner(Object... basePackage) {
        instantiateControllers(new Reflections(basePackage).getTypesAnnotatedWith(Controller.class));
    }

    public Map<Class<?>, Object> getControllers() {
        return controllers;
    }

    private void instantiateControllers(Set<Class<?>> annotated) {
        for (Class clazz : annotated) {
            try {
                controllers.put(clazz, clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                logger.warn("newInstance Error - {}", e);
            }
        }
    }
}
