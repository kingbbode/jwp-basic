package next.controller;

import core.db.DataBase;
import next.enums.HttpRequestType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListUserController extends AbstractController {

    public ListUserController(HttpRequestType[] type) {
        super(type);
    }

    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return "redirect:/users/loginForm";
        }
        request.setAttribute("users", DataBase.findAll());
        return "/user/list.jsp";
    }
}
