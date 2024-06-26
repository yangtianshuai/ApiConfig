package api.config;

import api.config.auth.NoSso;
import api.config.cache.CacheUnit;
import api.config.sso.*;
import api.config.utility.StringUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;

public abstract class SsoFilter extends AuthFilter {

    private ISsoHandler _ssoHandler;
    private SsoOptions _options;

    private boolean sso_pass = false;

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

    protected abstract void setCookie(Cookie cookie);

    public abstract String GetCookieID(HttpServletRequest request);

    /**
     * 通过SSO,不需要验证
     */
    public void pass(){
        sso_pass = true;
    }

    public boolean filter(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws IOException {

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
        sso_request.ClientIP = ClientIp;
        sso_request.Ticket = GetCookieID(request);
        if (StringUtil.isNullOrEmpty(sso_request.Ticket)
                && _ssoHandler.IsLogout(sso_request.OriginPath) && sso_request.OriginQuery.containsKey(SsoParameter.TICKET))
        {
            sso_request.Ticket = sso_request.OriginQuery.get(SsoParameter.TICKET).get(0);
        }
        _ssoHandler.SetRequest(sso_request);

        //String mapping_url = _ssoHandler.GetOptions().GetBaseURL(request.OriginHost, true);

        sso_request.CallBack.Redirect = ((RedirectModel rt) -> {
            if (rt.repeat_check)
            {
                String key = sso_request.ClientIP + sso_request.GetURL();
                key = Base64.getEncoder().encodeToString(key.getBytes());
                LocalDateTime create_time = CacheUnit.Current.get(key);
                if (create_time != null)
                {
                    rt.url = "";
                }
                else
                {
                    CacheUnit.Current.set(key, LocalDateTime.now(), Duration.ofSeconds(5));
                }
            }

            if (response.isCommitted())
            {
                return true;
            }

            if (sso_request.GetURL().equals(rt.url) && SsoExtension.CheckSso(response))
            {
                return true;
            }

            if (StringUtil.isNullOrEmpty(rt.url))
            {
                response.setStatus(403);
                return false;
            }

            response.setHeader("redirect-url", rt.url);
            SsoExtension.setCors(response,sso_request);

            int redirect_status = 302;
            if (rt.mode.equals(SsoMode.Proxy))
            {
                response.setHeader("Access-Control-Expose-Headers", "redirect-url");
            }
            response.setStatus(redirect_status);
            return false;
        });

        sso_request.CallBack.Logout = ((SsoCookie cookie) -> {
            if (response.isCommitted())
            {
                return null;
            }
            if (cookie != null)
            {
                LogoutComplate(cookie);
            }
            return null;
        });

        if (_ssoHandler.Exist(sso_request.Ticket))
        {
            //已经通过验证
            if (_ssoHandler.IsLogout(sso_request.OriginPath))
            {
                boolean redirect_flag = true;
                if(sso_request.OriginQuery.containsKey(SsoParameter.Redirect)){
                    redirect_flag = sso_request.OriginQuery.get(SsoParameter.Redirect).get(0) != "no";
                }
                return _ssoHandler.Logout(sso_request.Ticket, redirect_flag);
            }
            else
            {
                SsoExtension.setCors(response,sso_request);
                SsoExtension.SetSsoPass(response);
            }
            return true;
        }

        sso_request.CallBack.Validate = ((SsoCookie cookie) -> {
            if (response.isCommitted())
            {
                return null;
            }
            if (cookie != null && !StringUtil.isNullOrEmpty(cookie.ID))
            {
                //context.HttpContext.Response.SetSsoPass();
                try
                {
                    ValidateComplate(cookie);
                }
                catch (Exception ex)
                {
                    //_logger.Error(ex.Message, ex.StackTrace);
                }
            }
            return null;
        });

        return _ssoHandler.Validate(true);
    }


}
