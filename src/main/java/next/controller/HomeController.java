package next.controller;

import core.db.DataBase;
import next.enums.HttpRequestType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController extends AbstractController {

    public HomeController(HttpRequestType[] type) {
        super(type);
    }

    @Override
    protected String doGet(HttpServletRequest req, HttpServletResponse resp){
        req.setAttribute("users", DataBase.findAll());
        return "index.jsp";
    }
}
