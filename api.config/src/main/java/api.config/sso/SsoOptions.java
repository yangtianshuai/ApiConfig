package api.config.sso;

import api.config.utility.StringUtil;

import java.util.List;

public class SsoOptions {

    /**
     * 服务API根路径
     */
    public String BaseURL;

    public String GetBaseURL(String host)
    {
        return GetBaseURL(host,false);
    }

    /*
     *
     */
    public String GetBaseURL(String host, boolean validate)
    {
        if (IPMappings != null && IPMappings.size() > 0)
        {
            IPMapping ip_mapping = IPMappings.stream().filter(t -> host.contains(t.server_ip)).findFirst().get();
            if (ip_mapping != null)
            {
                if (validate && StringUtil.isNullOrEmpty(ip_mapping.validate_url))
                {
                    return ip_mapping.validate_url;
                }
                return ip_mapping.base_url;
            }
        }
        return BaseURL;
    }
    public List<IPMapping> IPMappings;
    /// <summary>
    /// 登录页面
    /// </summary>
    public String LoginURL;
    /// <summary>
    /// 退出路径
    /// </summary>
    public String LogoutPath;
    /// <summary>
    /// 模式
    /// </summary>
    public SsoMode Mode = SsoMode.Service;
    /// <summary>
    /// 是否强制使用HTTPS协议
    /// </summary>
    public boolean ForceHTTPS = false;
    /// <summary>
    /// Cookie内部管理器
    /// </summary>
    public CookieHost Cookie = new CookieHost();
    /// <summary>
    /// 票据有效期
    /// </summary>
    public int Expires;
    public int getExpires()
    {
        return Cookie.expires;
    }
    public void setExpires(int seconds)
    {
        Cookie.expires = seconds;
    }
    /// <summary>
    /// 应用ID
    /// </summary>
    public String AppID;
    /// <summary>
    /// 应用密钥
    /// </summary>
    public String Secret;
}
