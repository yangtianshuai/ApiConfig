package api.config.sso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SsoRequest
{
    public Map<String, List<String>> OriginQuery;
    public Map<String, List<String>> Query;
    public Map<String, String> Cookie;
    public SsoRequest()
    {
        OriginQuery = new HashMap<>();
        Query = new HashMap<>();
        Cookie = new HashMap<>();
        CallBack = new SsoCallback();
    }

    public String Ticket;

    /**
     * 原始Scheme
     */
    public String OriginScheme;
    /**
     * 内部Request
     */
    public String OriginHost;
    public String OriginPath;

    public String Scheme;

    public String Host;
    public int Port;
    public String Path;
    public String ClientIP;

    /// <summary>
    /// Body
    /// </summary>
    public byte[] Body;

    public SsoCallback CallBack;

    public String GetURL()
    {
        return Scheme + "://" + Host + ":" + Port + Path;
    }
}
