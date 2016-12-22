package next.support.resolver;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by YG-MAC on 2016. 12. 22..
 */
public class ViewResolver {
    private static final String REDIRECT_PREFIX = "redirect:";

    public static void resolve(String url, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if(isRedirectUrl(url)) {
            resp.sendRedirect(getRedirectRealPath(url));
            return;
        }
        forward(url, req, resp);
    }

    private static boolean isRedirectUrl(String url){
        return url.startsWith(REDIRECT_PREFIX);
    }

    private static String getRedirectRealPath(String url){
        return url.replace(REDIRECT_PREFIX, "");
    }

    private static void forward(String url, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher(url);
        rd.forward(req, resp);
    }
}
