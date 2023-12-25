package api.config.sso.oauth2;

import api.config.sso.SsoApi;

public class OAuth2Api extends SsoApi {

    /**
     * 请求授权
     */
    public static String AUTHORIZE = "oauth2/authorize";
    /**
     * 获取授权码
     */
    public static String TOKEN = "oauth2/token";
}
