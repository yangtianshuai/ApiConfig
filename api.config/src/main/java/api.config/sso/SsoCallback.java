package api.config.sso;

import java.util.function.Function;

public class SsoCallback {
    /// <summary>
    /// 跳转回调
    /// </summary>
    public Function<RedirectModel,Boolean> Redirect;
    /// <summary>
    /// 验证回调
    /// </summary>
    public Function<SsoCookie,Void> Validate;
    /// <summary>
    /// 跳转
    /// </summary>
    public Function<SsoCookie,Void> Logout;
}
