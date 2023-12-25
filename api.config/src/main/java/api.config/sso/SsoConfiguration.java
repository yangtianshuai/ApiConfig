package api.config.sso;

import api.config.sso.cas.CasHandler;
import api.config.sso.oauth2.OAuth2Handler;
import api.config.sso.oauth2.OAuth2Options;
import api.config.utility.StringUtil;

public class SsoConfiguration {

    private static SsoOptions _options;
    private static OAuth2Options oauth2_options;

    private SsoConfiguration(SsoOptions options){
        _options = options;
    }

    private static SsoConfiguration sso_configration;
    public static SsoConfiguration get(SsoOptions options){
        if(sso_configration == null){
            sso_configration = new SsoConfiguration(options);
        }
        return sso_configration;
    }

    public ISsoHandler useCas(){
        return new CasHandler(_options);
    }

    public ISsoHandler useOAuth2(OAuth2Options options){
        if(oauth2_options == null){
            if(options == null){
                options = new OAuth2Options();
            }
            if(StringUtil.isNullOrEmpty(options.RedictUri)){
                options.RedictUri = "/sso/user/call_back";
            }
            oauth2_options = options;
        }
        return new OAuth2Handler(_options,oauth2_options);
    }
}
