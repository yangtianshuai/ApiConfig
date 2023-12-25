package api.config.sso.oauth2;

public class OAuth2Options {
    public OAuth2Mode Mode = OAuth2Mode.Standard;

    /**
     * 回调URL
     */
    public String RedictUri;

    String ResponseType()
    {
        return Mode == OAuth2Mode.Standard ? "code" : "token";
    }
}
