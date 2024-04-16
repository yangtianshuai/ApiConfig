package api.config.sso.cas;

import api.config.sso.RedirectModel;
import api.config.sso.SsoHandler;
import api.config.sso.SsoOptions;
import api.config.utility.StringUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CasHandler extends SsoHandler {

    public CasHandler(SsoOptions options)
    {
        _options = options;
    }

    @Override
    public boolean Validate(boolean cache_flag) throws UnsupportedEncodingException {
        if (_request.Query.containsKey(CasParameter.AccessToken))
        {
            String url = _options.GetBaseURL(_request.OriginHost);
            url += "/" + CasApi.LOGIN;
            url += "?";
            url += CasParameter.SERVICE + "=" + URLEncoder.encode(_request.GetURL(),"UTF-8");
            if (!StringUtil.isNullOrEmpty(_options.AppID))
            {
                url += "&" + CasParameter.AppID + "=" + _options.AppID;
            }
            if (_request.Query.containsKey(CasParameter.AccessToken))
            {
                url += "&" + CasParameter.AccessToken + "=" + _request.Query.get(CasParameter.AccessToken);
            }
            RedirectModel redirect = new RedirectModel(_options.Mode);
            redirect.url = url;
            redirect.repeat_check = true;
            return _request.CallBack.Redirect.apply(redirect);
        }
        else if (!_request.Query.containsKey(CasParameter.TICKET))
        {
            String url = _options.GetBaseURL(_request.OriginHost);
            if (StringUtil.isNullOrEmpty(_options.LoginURL))
            {
                url += "/" + CasApi.LOGIN;
            }
            else
            {
                url = _options.LoginURL;
            }
            url += "?" + CasParameter.SERVICE + "=" + URLEncoder.encode(_request.GetURL(),"UTF-8");
            if (!StringUtil.isNullOrEmpty(_options.AppID))
            {
                url += "&" + CasParameter.AppID + "=" + _options.AppID;
            }
            RedirectModel redirect = new RedirectModel(_options.Mode);
            redirect.url = url;
            redirect.repeat_check = true;
            return _request.CallBack.Redirect.apply(redirect);
        }
        else
        {
            return ValidateSSO(cache_flag);
        }
    }


}
