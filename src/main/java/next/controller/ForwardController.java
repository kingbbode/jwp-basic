package next.controller;

import next.enums.HttpRequestType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by YG-MAC on 2016. 12. 22..
 */
public class ForwardController extends AbstractController {
    private String url;
    public ForwardController(String url) {
        super(new HttpRequestType[]{HttpRequestType.GET});
        this.url = url;
    }

    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) {
        return this.url;
    }
}
