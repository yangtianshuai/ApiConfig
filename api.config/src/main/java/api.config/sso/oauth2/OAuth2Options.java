package api.config.sso.oauth2;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OAuth2Options {
    public OAuth2Mode Mode = OAuth2Mode.Standard;
    /**
     * 回调URL
     */
    public String RedictUri;

    private static Map<String, RefreshToken> refresh_tokens;

    public OAuth2Options()
    {
        refresh_tokens = new ConcurrentHashMap<String, RefreshToken>();
    }

    void SetRefresh(String key, RefreshToken token)
    {
        if(!refresh_tokens.containsKey(key))
        {
            refresh_tokens.put(key, token);
        }
        else
        {
            refresh_tokens.remove(key);
            if(token != null)
            {
                refresh_tokens.put(key, token);
            }
        }
    }

    RefreshToken GetRefresh(String key)
    {
        if (refresh_tokens.containsKey(key))
        {
            return refresh_tokens.get(key);
        }
        return null;
    }

    String ResponseType()
    {
        return Mode == OAuth2Mode.Standard ? "code" : "token";
    }
}
