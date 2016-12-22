package next.controller;

import core.db.DataBase;
import next.enums.HttpRequestType;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    public CreateUserController(HttpRequestType[] type) {
        super(type);
    }

    @Override
    protected String doPost(HttpServletRequest req, HttpServletResponse resp){
        User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
                req.getParameter("email"));
        log.debug("User : {}", user);
        DataBase.addUser(user);

        return "redirect:/";
    }
}
