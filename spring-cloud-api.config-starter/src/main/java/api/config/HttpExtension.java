package api.config;

import api.config.session.ServerSession;
import api.config.utility.StringUtil;
import io.micrometer.common.lang.Nullable;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.Optional;

public class HttpExtension {

    private HttpExtension(){

    }

    private static String split=",";
    private static HttpExtension http;

    public static HttpExtension current() {
        if (http == null) {
            http = new HttpExtension();
        }
        return http;
    }

    public String getToken(HttpServletRequest request) {
        String token_key = ServerSession.Tokenkey();

        String token = request.getHeader(token_key);
        if (!StringUtil.isNullOrEmpty(token)) {
            return token;
        }

        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            Optional<Cookie> op = Arrays.stream(cookies).filter(t -> t.getName().equals(token_key)).findFirst();
            if(op.isPresent()){
                Cookie cookie = op.get();
                if (cookie == null) {
                    return token;
                }
                return cookie.getValue();
            }
        }
        return null;
    }
    protected void setToken(HttpServletResponse response,String key, String token) {
        addHeader(response, key, token,false);
        addHeader(response, "Access-Control-Expose-Headers", key, true);
    }

    public void addHeader(HttpServletResponse response, String key, String value) {
        addHeader(response,key,value,false);
    }

    public void addHeader(HttpServletResponse response, String key, String value, boolean check) {
        addHeader(response,key,value,check,",");
    }

    public void addHeader(HttpServletResponse response, String key, String value, boolean check, String split) {
        if (response.containsHeader(key)) {
            if (check) {
                value = response.getHeader(key) + split + value;

            } else {
                value = response.getHeader(key);
            }
            response.setHeader(key, value);
        } else {
            response.addHeader(key, value);
        }
    }
}
