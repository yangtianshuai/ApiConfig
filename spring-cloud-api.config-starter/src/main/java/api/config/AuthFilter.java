package api.config;

import api.config.auth.NoAuthorization;
import api.config.auth.Open;
import api.config.open.OpenOptions;
import api.config.session.ServerSession;
import api.config.setting.AppSetting;
import api.config.utility.StringUtil;
import io.micrometer.common.lang.Nullable;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AuthFilter implements HandlerInterceptor {

    /**
     * 白名单包含？
     */
    protected boolean WhiteListContain = false;
    /**
     * 包含NoAuthorization注解
     */
    protected boolean NoAuthAttr = false;
    /**
     * 请求客户端IP
     */
    protected String ClientIp;

    protected Object[] Attrs;
    protected List<String> AccessTokens;

    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public void setToken(String token)
    {
        String token_key = ServerSession.Tokenkey();
        HttpExtension.current().setToken(response,token_key,token);

        Cookie cookie = new Cookie(token_key,token);
        cookie.setPath("/");
        this.response.addCookie(cookie);
    }

    public String getToken() {
        return HttpExtension.current().getToken(request);
    }

    protected abstract boolean filter(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws IOException;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        this.request = request;
        this.response = response;

        ClientIp = request.getRemoteHost();

        List<String> whiteList = AppSetting.getSetting("white-list", List.class);
        if (whiteList != null && whiteList.size() > 0) {
            if (whiteList.contains(ClientIp)) {
                WhiteListContain = true;
            }
        }
        List<String> accessTokens = AppSetting.getSetting("access-token", List.class);
        String access_token = SsoExtension.getAccessToken(request);
        if (!StringUtil.isNullOrEmpty(access_token))
        {
            if (accessTokens.contains(access_token))
            {
                WhiteListContain = true;
            }
        }

        boolean isDefined = false;
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        NoAuthorization no_auth = handlerMethod.getMethodAnnotation(NoAuthorization.class);
        if (no_auth != null) {
            NoAuthAttr = true;
        }

        //开放接口，暂未完全实现
        List<String> apps = new ArrayList<>();
        boolean access_token_flag = true;
        Open open = handlerMethod.getMethodAnnotation(Open.class);
        if (open != null) {
//            apps = open.GetApps();
//            access_token_flag = !open.AccessToken();
        }
        String route = request.getPathInfo();
        if (!StringUtil.isNullOrEmpty(route) && OpenOptions.OpenApps.containsKey(route)) {
            apps.addAll(OpenOptions.OpenApps.get(route));
            access_token_flag = access_token_flag || OpenExtention.accessCheck(request,route);
        }

        if (access_token_flag && OpenExtention.openCheck(request,apps)) {
            NoAuthAttr = true;
            WhiteListContain = true;
        }

        return filter(request,response,handlerMethod);
    }

    @Override
    public  void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public  void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
