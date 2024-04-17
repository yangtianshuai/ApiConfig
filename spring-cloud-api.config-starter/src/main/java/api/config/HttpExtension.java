package api.config;

import api.config.session.ServerSession;
import api.config.utility.StringUtil;
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
        response.setHeader(key, token);

        response.setHeader("Access-Control-Allow-Headers", key);
        response.setHeader("Access-Control-Expose-Headers", key);
    }


}
