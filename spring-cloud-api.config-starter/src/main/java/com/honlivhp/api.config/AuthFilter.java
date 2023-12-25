package com.honlivhp.api.config;

import api.config.auth.NoAuthorization;
import api.config.auth.NoSso;
import api.config.auth.Open;
import api.config.open.OpenOptions;
import api.config.setting.AppSetting;
import api.config.sso.SsoCookie;
import api.config.sso.SsoRequest;
import api.config.utility.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AuthFilter implements HandlerInterceptor {
    /**
     * 白名单包含？
     */
    protected Boolean WhiteListContain;
    /**
     * 包含NoAuthorization注解
     */
    protected Boolean NoAuthAttr;
    /// <summary>
    /// 请求客户端IP
    /// </summary>
    protected String ClientIp;

    protected Object[] Attrs;
    protected List<String> AccessTokens;

    protected abstract Boolean filter(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws IOException;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        ClientIp = request.getRemoteHost();
        String hosts = AppSetting.getSetting("WhiteList");
        if (hosts != null && hosts.length() > 0) {
            if (Arrays.stream(hosts.split(","))
                    .filter(t-> t.toString().equals(ClientIp)).count()>0) {
                WhiteListContain = true;
            }
        }
        //AccessTokens = AppSetting.getSetting("AccessToken", List<>.class);
//        String access_token = request.GetAccessToken();
//        if (!StringUtil.isNullOrEmpty(access_token))
//        {
//            if (AccessTokens.contains(access_token))
//            {
//                WhiteListContain = true;
//            }
//        }

        Boolean isDefined = false;
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        NoAuthorization no_auth = handlerMethod.getMethodAnnotation(NoAuthorization.class);
        if (no_auth != null) {
            NoAuthAttr = true;
        }

        //开放接口，暂未完全实现
        List<String> apps = new ArrayList<>();
        Boolean access_token_flag = true;
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
