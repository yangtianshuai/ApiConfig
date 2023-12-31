package api.config.sso;

import java.time.LocalDateTime;

public class SsoCookie {
    public SsoCookie()
    {
        Time = LocalDateTime.now();
    }

    public static String Token = "SsoToken";
    LocalDateTime Time = LocalDateTime.MIN;
    /**
     Cookie
     */
    public String ID;
    /**
     用户ID
     */
    public String UserID;
    public String Name;

    public String EmployeeId;
    public String Raw;

    public final String GetCookie()
    {
        return Token + "=" + ID;
    }

}
