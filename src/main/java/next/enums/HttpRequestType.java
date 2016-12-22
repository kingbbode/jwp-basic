package next.enums;

/**
 * Created by YG-MAC on 2016. 12. 22..
 */
public enum HttpRequestType {
    POST(),
    GET;

    public boolean isGet() {
        return GET == this;
    }

    public boolean isPost() {
        return POST == this;
    }
}
