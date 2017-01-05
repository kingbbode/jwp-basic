package core.di.factory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import core.annotation.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Set<Class<?>> preInstanticateBeans;

    private Map<Class<?>, Object> beans = Maps.newHashMap();

    public BeanFactory(Set<Class<?>> preInstanticateBeans) {
        this.preInstanticateBeans = preInstanticateBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public void initialize() {
        this.preInstanticateBeans.stream().forEach(clazz -> beans.put(clazz, instantiateClass(clazz)));
        logger.debug("initialize end");
    }

    private Object instantiateClass(Class<?> clazz) {
        if (beans.containsKey(clazz)) {
            return beans.get(clazz);
        }

        Object object = instantiateConstructor(BeanFactoryUtils.getInjectedConstructor(clazz));

        if (object == null) {
            object = BeanUtils.instantiateClass(clazz);
        }

        beans.put(clazz, object);
        return object;
    }

    private Object instantiateConstructor(Constructor<?> constructor) {
        if (constructor == null) {
            return null;
        }

        List<Object> args = Lists.newArrayList();
        Arrays.stream(constructor.getParameterTypes())
                .forEach(clazz -> args.add(instantiateClass(BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans))));

        return BeanUtils.instantiateClass(constructor, args.toArray());
    }

    public Map<Class<?>, Object> getControllers() {
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        preInstanticateBeans.stream()
                .filter(clazz -> clazz.isAnnotationPresent(Controller.class))
                .forEach(clazz -> controllers.put(clazz, beans.get(clazz)));
        return controllers;
    }
}
