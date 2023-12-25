package api.config.sso.cas;

import api.config.sso.SsoHandler;
import api.config.sso.SsoOptions;
import api.config.utility.StringUtil;

import java.net.URLEncoder;

public class CasHandler extends SsoHandler {

    public CasHandler(SsoOptions options)
    {
        _options = options;
    }

    @Override
    public void Validate(Boolean cache_flag) {
        if (_request.Query.containsKey(CasParameter.AccessToken))
        {
            String url = _options.GetBaseURL(_request.RequestHost);
            url += "/" + CasApi.LOGIN;
            url += "?";
            url += CasParameter.SERVICE + "=" + URLEncoder.encode(_request.GetURL());
            if (!StringUtil.isNullOrEmpty(_options.AppID))
            {
                url += "&" + CasParameter.AppID + "=" + _options.AppID;
            }
            if (_request.Query.containsKey(CasParameter.AccessToken))
            {
                url += "&" + CasParameter.AccessToken + "=" + _request.Query.get(CasParameter.AccessToken);
            }

            _request.CallBack.Redirect.apply(url);
        }
        else if (!_request.Query.containsKey(CasParameter.TICKET))
        {
            String url = _options.GetBaseURL(_request.RequestHost);
            if (StringUtil.isNullOrEmpty(_options.LoginURL))
            {
                url += "/" + CasApi.LOGIN;
            }
            else
            {
                url = _options.LoginURL;
            }
            url += "?" + CasParameter.SERVICE + "=" + URLEncoder.encode(_request.GetURL());
            if (!StringUtil.isNullOrEmpty(_options.AppID))
            {
                url += "&" + CasParameter.AppID + "=" + _options.AppID;
            }
            _request.CallBack.Redirect.apply(url);
        }
        else
        {
            String ticket = "";
            if(_request.Query.containsKey(CasParameter.TICKET)){
                ticket = _request.Query.get(CasParameter.TICKET).get(0);
            }
            String service = URLEncoder.encode(_request.GetURL());
            String url = "";
            if (cache_flag && Exist(ticket))
            {
                _request.CallBack.Validate.apply(_options.Cookie.GetCookie(ticket));
            }
            else
            {
                String logoutUrl = _request.RequestHost;
                if (_options.LogoutPath.length() > 0 && _options.LogoutPath.charAt(0) != '/')
                {
                    logoutUrl += '/';
                }
                logoutUrl += _options.LogoutPath;
                String param = CasParameter.AppID + "=" + _options.AppID
                        + "&" + CasParameter.TICKET + "=" + ticket
                        + "&" + CasParameter.LogoutPath + "=" + URLEncoder.encode(logoutUrl);

                url = _options.GetBaseURL(_request.RequestHost, true) + "/" + CasApi.VALIDATE + "?" + param;

                HttpRequest(url, ticket);
            }

            url = getWebUrl();
            _request.CallBack.Redirect.apply(url);
        }
    }


}
