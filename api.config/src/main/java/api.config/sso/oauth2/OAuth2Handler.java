package api.config.sso.oauth2;

import api.config.net.HttpClient;
import api.config.sso.RedirectModel;
import api.config.sso.SsoHandler;
import api.config.sso.SsoMode;
import api.config.sso.SsoOptions;
import api.config.sso.cas.CasParameter;
import api.config.utility.StringUtil;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class OAuth2Handler extends SsoHandler {
    protected OAuth2Options _options2;
    public OAuth2Handler(SsoOptions options, OAuth2Options options2)
    {
        _options = options;
        _options2 = options2;
    }

    @Override
    public Boolean Validate(Boolean cache_flag) throws UnsupportedEncodingException {
        String url = "";
        if (_request.OriginPath.toLowerCase() == _options2.RedictUri.toLowerCase())
        {
            //验证state

            String ticket = "";

            //回调地址
            if (_options2.Mode == OAuth2Mode.Simple)
            {
                //简单模式，返回：access_token、state、expires_in、scope
                if (_request.OriginQuery.containsKey(CasParameter.AccessToken)) {
                    ticket = _request.OriginQuery.get(OAuth2Parameter.AccessToken).get(0);
                }
            }
            else
            {
                //标准模式，返回：code、state、scope
                //发送：app_id、secret、grant_type、code
                ticket =  AccessToken();
            }

            if (cache_flag && Exist(ticket))
            {
                _request.CallBack.Validate.apply(_options.Cookie.GetCookie(ticket));
            }
            else
            {
                String logoutUrl = _request.OriginHost;
                if (_options.LogoutPath.charAt(0) != '/')
                {
                    logoutUrl += '/';
                }
                logoutUrl += _options.LogoutPath;

                String param = OAuth2Parameter.AppID + "=" + _options.AppID
                        + "&" + OAuth2Parameter.TICKET + "=" + ticket
                        + "&" + OAuth2Parameter.LogoutPath + "=" + URLEncoder.encode(logoutUrl,"UTF-8");

                url = _options.GetBaseURL(_request.OriginHost, true) + "/" + OAuth2Api.VALIDATE + "?" + param;

                HttpRequest(url, ticket);
            }

            //跳转原页面
            url = _request.Query.get(OAuth2Parameter.Scope).get(0);

            RedirectModel redirect = new RedirectModel(SsoMode.Service);
            redirect.url = url;
            return _request.CallBack.Redirect.apply(redirect);
        }
        else
        {
            if (!StringUtil.isNullOrEmpty(_request.Ticket))
            {
                //是否需要刷新令牌
                RefreshToken refresh_token = _options2.GetRefresh(_request.Ticket);
                if(refresh_token != null)
                {
                    long seconds = Duration.between(refresh_token.CreateTime,LocalDateTime.now()).getSeconds();
                    if (seconds >= (refresh_token.Expire - 60 * 5))
                    {
                        //需要刷新令牌
                        RefreshAccessToken(_request.Ticket, refresh_token.Token);
                    }
                }
            }
            if (_request.Query.containsKey(OAuth2Parameter.TICKET) || !StringUtil.isNullOrEmpty(_request.Ticket))
            {
                return ValidateSSO(cache_flag);
            }

            url = _options.GetBaseURL(_request.OriginHost);
            if (StringUtil.isNullOrEmpty(_options.LoginURL))
            {
                url += "/" + OAuth2Api.AUTHORIZE;
            }
            else
            {
                url = _options.LoginURL;
            }

            String state = UUID.randomUUID().toString();

            String param = OAuth2Parameter.AppID + "=" + _options.AppID
                    + "&" + OAuth2Parameter.ResponseType + "=" + _options2.ResponseType()
                    + "&" + OAuth2Parameter.RedirectUri + "=" + _options2.RedictUri
                    + "&" + OAuth2Parameter.State + "=" + state
                    + "&" + OAuth2Parameter.Scope + "=" + URLEncoder.encode(_request.GetURL(),"UTF-8");

            url = url + "?" + param;

            RedirectModel redirect = new RedirectModel(_options.Mode);
            redirect.url = url;
            redirect.repeat_check = true;
            return _request.CallBack.Redirect.apply(redirect);
        }
    }

    public String RefreshAccessToken(String access_token,String refresh_token) {

        String grant_type = "refresh_token";
        String param = OAuth2Parameter.RefreshToken + "=" + refresh_token
                + "&" + OAuth2Parameter.GrantType + "=" + grant_type;


        String url = _options.GetBaseURL(_request.OriginHost, true) + "/" + OAuth2Api.TOKEN + "?" + param;

        HttpClient client = new HttpClient();

        String response = client.get(url);

        if (!StringUtil.isNullOrEmpty(response)) {

            JSONObject res = JSONObject.parseObject(response);
            if (res.get("code").toString() == "1") {
                //expires_in = temp_ticket.Expire,
                //refresh_token
                return res.get("access_token").toString();
            }
        }

        return "";
    }

    public String AccessToken() {
        String code = _request.OriginQuery.get(OAuth2Parameter.Code).get(0);
        String grant_type = "authorization_code";
        String param = OAuth2Parameter.AppID + "=" + _options.AppID
                + "&" + OAuth2Parameter.Secret + "=" + _options.Secret
                + "&" + OAuth2Parameter.Secret + "=" + _options.Secret
                + "&" + OAuth2Parameter.GrantType + "=" + grant_type
                + "&" + OAuth2Parameter.Code + "=" + code;


        String url = _options.GetBaseURL(_request.OriginHost, true) + "/" + OAuth2Api.TOKEN + "?" + param;

        HttpClient client = new HttpClient();

        String response = client.get(url);

        if (!StringUtil.isNullOrEmpty(response)) {

            JSONObject res = JSONObject.parseObject(response);
            if (res.get("code").toString() == "1") {
                //expires_in = temp_ticket.Expire,
                //refresh_token
                return res.get("access_token").toString();
            }
        }

        return "";
    }
}
