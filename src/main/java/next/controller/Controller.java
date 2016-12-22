package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by YG-MAC on 2016. 12. 22..
 */
public abstract class Controller{

    public String execute(HttpServletRequest request, HttpServletResponse response){
        if("GET".equals(request.getMethod())){
            return doGet(request, response);
        }else if("POST".equals(request.getMethod())){
            return doPost(request, response);
        }
        return null;
    }

    protected String doGet(HttpServletRequest request, HttpServletResponse response){
        return null;
    }

    protected String doPost(HttpServletRequest request, HttpServletResponse response){
        return null;
    }
}
