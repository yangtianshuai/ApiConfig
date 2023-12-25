package api.config.sso.oauth2;

import api.config.sso.SsoParameter;

public class OAuth2Parameter extends SsoParameter {
    public static String ResponseType = "response_type";
    public static String State = "state";
    public static String Scope = "scope";

    public static String Code = "code";
    public static String Secret = "secret";
    //authorization_code、password、refresh_token
    public static String GrantType = "grant_type";
}
