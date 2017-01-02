package core.ref;

import next.model.Question;
import next.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());
        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> logger.debug("type : {}, name : {}", field.getType(), field.getName()));
        Arrays.stream(clazz.getMethods()).forEach(method -> logger.debug("method : {}, return type: {}", method.getName(), method.getReturnType()));
    }

    @Test
    public void newInstanceWithConstructorArgs() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());
        User user = null;
        for(Constructor<?> constructor : clazz.getDeclaredConstructors()){
            if(constructor.getParameterCount() == 4) {
                user = (User) constructor.newInstance("kingbbode", "kingbbode", "용근", "kingbbode@gmail.com");
                break;
            }
        }
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getUserId(), "kingbbode");
        Assert.assertEquals(user.getPassword(), "kingbbode");
        Assert.assertEquals(user.getName(), "용근");
        Assert.assertEquals(user.getEmail(), "kingbbode@gmail.com");
    }

    @Test
    public void privateFieldAccess() {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());
        Student student = new Student();
        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
            try {
                if ("name".equals(field.getName())) {
                    field.setAccessible(true);
                    field.set(student, "용근");
                } else if ("age".equals(field.getName())) {
                    field.setAccessible(true);
                    field.set(student, 23);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        Assert.assertEquals(student.getName(), "용근");
    }
}
