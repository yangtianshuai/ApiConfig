package api.config.sso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SsoRequest
{
    public Map<String, List<String>> Query;
    public Map<String, String> Cookie;
    public SsoRequest()
    {
        Query = new HashMap<>();
        Cookie = new HashMap<>();
        CallBack = new SsoCallback();
    }
    public String Scheme;
    /// <summary>
    /// 内部Request
    /// </summary>
    public String RequestHost;
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
