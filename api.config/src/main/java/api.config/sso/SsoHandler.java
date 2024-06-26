package api.config.sso;

import api.config.net.HttpClient;
import api.config.sso.cas.CasApi;
import api.config.sso.cas.CasParameter;
import api.config.utility.StringUtil;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public abstract class SsoHandler implements ISsoHandler {
    protected SsoRequest _request;
    protected SsoOptions _options;

    @Override
    public void SetRequest(SsoRequest request)
    {
        this._request = request;
    }

    public SsoOptions GetOptions()
    {
        return this._options;
    }

    @Override
    public boolean IsLogout(String path)
    {
        if (path == null)
        {
            path = _request.Path;
        }
        return path.toLowerCase().equals(_options.LogoutPath.toLowerCase());
    }

    @Override
    public boolean Logout(String token,boolean redirect_flag) throws UnsupportedEncodingException {
        SsoCookie cookie = null;
        if (StringUtil.isNullOrEmpty(token) && _request.Query.containsKey(SsoParameter.TICKET)) {
            token = _request.Query.get(SsoParameter.TICKET).get(0);
            cookie = _options.Cookie.GetCookie(token);
        } else {
            cookie = _options.Cookie.get(token);
        }
        //子系统退出
        _request.CallBack.Logout.apply(cookie);
        _options.Cookie.Remove(token);

        //认证服务通知时，不需要跳转
        if (!redirect_flag) {
            return true;
        }

        String url = _options.GetBaseURL(_request.OriginHost) + "/" + SsoApi.LOGOUT
                + "?" + SsoParameter.AppID + "=" + _options.AppID
                + "&" + SsoParameter.TICKET + "=" + token
                + "&" + SsoParameter.RedirectUri + "=" + URLEncoder.encode(_request.GetURL(),"UTF-8");

        RedirectModel redirect = new RedirectModel(_options.Mode);
        redirect.url = url;
        return _request.CallBack.Redirect.apply(redirect);
    }

    protected void HttpRequest(String url) {
        HttpRequest(url,null);
    }

    protected boolean HttpRequest(String url, String ticket) {

        HttpClient client = new HttpClient();

        //可以通过Header设置Service的参数？
        String response = client.get(url);
        if (response != null) {
            JSONObject res = JSONObject.parseObject(response);
            if (res.get("code").toString().equals("1")) {

                SsoCookie cookie = new SsoCookie();
                cookie.Raw = response;
                if (!StringUtil.isNullOrEmpty(ticket)) {
                    cookie.ID = ticket;
                } else {
                    cookie.ID = res.get("ticket").toString();
                }
                cookie.UserID = res.get("id").toString();
                cookie.Name = res.get("name").toString();
                cookie.EmployeeId = res.get("employee_id").toString();

                //添加Cookie
                _options.Cookie.Set(cookie.ID, cookie);
                _request.CallBack.Validate.apply(cookie);
                return true;
            }
        }
        return false;
    }

    protected boolean ValidateSSO(boolean cache_flag) throws UnsupportedEncodingException {
        String ticket = _request.Ticket;
        if(_request.Query.containsKey(CasParameter.TICKET)){
            ticket = _request.Query.get(CasParameter.TICKET).get(0);
        }
        //String service = URLEncoder.encode(_request.GetURL(),"UTF-8");
        String url = "";
        if (cache_flag && Exist(ticket))
        {
            _request.CallBack.Validate.apply(_options.Cookie.GetCookie(ticket));
        }
        else
        {
            String logoutUrl = _request.OriginHost;
            if (_options.LogoutPath.length() > 0 && _options.LogoutPath.charAt(0) != '/')
            {
                logoutUrl += '/';
            }
            logoutUrl += _options.LogoutPath;
            String param = CasParameter.AppID + "=" + _options.AppID
                    + "&" + CasParameter.TICKET + "=" + ticket
                    + "&" + CasParameter.LogoutPath + "=" + URLEncoder.encode(logoutUrl,"UTF-8");

            url = _options.GetBaseURL(_request.OriginHost, true) + "/" + CasApi.VALIDATE + "?" + param;

            HttpRequest(url, ticket);
        }

        RedirectModel redirect = new RedirectModel(_options.Mode);
        redirect.url = getWebUrl();
        redirect.repeat_check = true;
        return _request.CallBack.Redirect.apply(redirect);
    }

    private String getWebUrl(){
        String url = _request.GetURL();
        if (_request.Query.containsKey(SsoParameter.TICKET))
        {
            url = url.replace(SsoParameter.TICKET + "=" + _request.Query.get(SsoParameter.TICKET), "");

            if(url.charAt(url.length() - 1) == '&'){
                url = url.substring(0,url.length() - 1);
            }
            if(url.charAt(url.length() - 1) == '?'){
                url = url.substring(0,url.length() - 1);
            }
        }
        return url;
    }

    public abstract boolean Validate(boolean cache_flag) throws UnsupportedEncodingException;

    @Override
    public boolean Exist(String token)
    {
        if (StringUtil.isNullOrEmpty(token) && _request.Query.containsKey(SsoParameter.TICKET))
        {
            token = _request.Query.get(SsoParameter.TICKET).get(0);
        }
        //需要定时检测token有效性
        return _options.Cookie.Contain(token);
    }
}
