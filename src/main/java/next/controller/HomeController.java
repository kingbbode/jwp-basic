package next.controller;

import core.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController extends Controller {

    @Override
    protected String doGet(HttpServletRequest req, HttpServletResponse resp){
        req.setAttribute("users", DataBase.findAll());
        return "index.jsp";
    }
}
