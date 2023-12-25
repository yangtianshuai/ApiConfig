package com.honlivhp.api.config;

import api.config.auth.NoSso;
import api.config.sso.ISsoHandler;
import api.config.sso.SsoCookie;
import api.config.sso.SsoOptions;
import api.config.sso.SsoRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;

public abstract class SsoFilter extends AuthFilter {

    private ISsoHandler _ssoHandler;
    private SsoOptions _options;

    private Boolean sso_pass = false;


    public SsoFilter(ISsoHandler ssoHandler){
        _ssoHandler = ssoHandler;
        _options = ssoHandler.GetOptions();
    }

    /**
     * 通过验证
     * @param cookie
     */
    public abstract void ValidateComplate(SsoCookie cookie);

    /**
     * 退出登录
     * @param cookie
     */
    public abstract void LogoutComplate(SsoCookie cookie);

    /**
     * 通过SSO,不需要验证
     */
    public void pass(){
        sso_pass = true;
    }

    public Boolean filter(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws IOException {

        if (WhiteListContain)
        {
            return true;
        }

        if(sso_pass){
            return true;
        }
        //检测是否包含NOSSO注解
        NoSso no_sso = handlerMethod.getMethodAnnotation(NoSso.class);
        if(no_sso != null) {
            return true;
        }

        SsoRequest sso_request = SsoExtension.GetRequest(request,_options.Mode);

        _ssoHandler.SetRequest(sso_request);

        sso_request.CallBack.Redirect = ((String url) -> {

            return null;
        });

        sso_request.CallBack.Validate = ((SsoCookie cookie) -> {

            return null;
        });

        sso_request.CallBack.Logout = ((SsoCookie cookie) -> {

            return null;
        });

        _ssoHandler.Validate(true);

        return true;
    }


}
