package next.controller;

import next.enums.HttpRequestType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * Created by YG-MAC on 2016. 12. 22..
 */
public abstract class AbstractController implements Controller{

    private List<HttpRequestType> types;

    public AbstractController(HttpRequestType[] type) {
        this.types = Arrays.asList(type);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        HttpRequestType type = HttpRequestType.valueOf(request.getMethod());
        if(!isMatch(type)){
            return null;
        }

        if(type.isGet()){
            return doGet(request, response);
        }else if(type.isPost()){
            return doPost(request, response);
        }

        return null;
    }

    private boolean isMatch(HttpRequestType type){
        return this.types.contains(type);
    }

    protected String doGet(HttpServletRequest request, HttpServletResponse response){
        return null;
    }

    protected String doPost(HttpServletRequest request, HttpServletResponse response){
        return null;
    }
}
