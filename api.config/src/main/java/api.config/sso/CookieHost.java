package api.config.sso;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class CookieHost {
    private static Map<String, SsoCookie> _cookies;
    public int expires = 60 * 30;

    public CookieHost()
    {
        _cookies = new ConcurrentHashMap<>();
    }

    public CookieHost(int expires)
    {
        this.expires = expires;

    }

    public SsoCookie get(String st) {

        if (Contain(st)) {
            return _cookies.get(st);
        }
        return null;
    }

    public SsoCookie GetCookie(String ticket)
    {
        AtomicReference<SsoCookie> cookie = null;
        _cookies.forEach((key,value)->{
            if (value != null)
            {
                if (value.ID == ticket)
                {
                    cookie.set(value);
                }
            }
        });
        return cookie.get();
    }

    /// <summary>
    /// 是否存在Cookie
    /// </summary>
    /// <param name="st">临时票据</param>
    /// <returns></returns>
    public boolean Contain(String st)
    {
        if (st == null)
        {
            return false;
        }
        if (_cookies.containsKey(st))
        {
            SsoCookie cookie = _cookies.get(st);
            if (cookie.Time.plusSeconds(expires).compareTo(LocalDateTime.now()) == -1)
            {
                _cookies.remove(st);
                return false;
            }
            return true;
        }
        return false;
    }
    /// <summary>
    /// 移除Cookie
    /// </summary>
    /// <param name="st"></param>
    public void Remove(String st)
    {
        if (Contain(st))
        {
            _cookies.remove(st);
        }
    }
    /// <summary>
    /// 设置Cookie
    /// </summary>
    /// <param name="st"></param>
    /// <param name="user"></param>
    public void Set(String st, SsoCookie cookie)
    {
        if (!Contain(st))
        {
            _cookies.put(st, cookie);
        }
    }
}
