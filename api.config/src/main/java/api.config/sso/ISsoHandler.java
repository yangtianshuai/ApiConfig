package api.config.sso;


import java.io.UnsupportedEncodingException;

public interface ISsoHandler {
    /**
     * 获取SSO配置     *
     * @return SSO配置
     */
    SsoOptions GetOptions();

    /**
     *
     * @param request
     */
    void SetRequest(SsoRequest request);

    /**
     * SSO验证     *
     * @param cache_flag 缓存标记
     */
    boolean Validate(boolean cache_flag) throws UnsupportedEncodingException;

    /**
     * 判断是否为登出请求
     *
     * @param path 请求路由
     * @return 是否为登出请求
     */
    boolean IsLogout(String path);

    /**
     * 退出登录
     * @param token 令牌
     * @param redirect_flag 是否跳转
     */
    boolean Logout(String token,boolean redirect_flag) throws UnsupportedEncodingException;
    /// <summary>
    /// 是否已存在Cookie
    /// </summary>
    /// <param name="token">令牌</param>
    /// <returns></returns>

    /**
     * 是否已存在Cookie
     * @param token 令牌
     * @return 是否存在
     */
    boolean Exist(String token);
}
