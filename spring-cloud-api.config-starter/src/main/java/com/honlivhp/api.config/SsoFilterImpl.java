package com.honlivhp.api.config;

import api.config.sso.ISsoHandler;
import api.config.sso.SsoCookie;

public class SsoFilterImpl extends SsoFilter {
    public SsoFilterImpl(ISsoHandler casHandler) {
        super(casHandler);
    }

    @Override
    public void ValidateComplate(SsoCookie cookie) {

    }

    @Override
    public void LogoutComplate(SsoCookie cookie) {

    }
}
