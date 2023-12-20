package api.config.sso;

import java.time.LocalDateTime;

public class CasCookie {
    public CasCookie()
    {
        setTime(LocalDateTime.now());
    }

    public static String Token = "CasToken";
    private LocalDateTime Time = LocalDateTime.MIN;
    public final LocalDateTime getTime()
    {
        return Time;
    }
    public final void setTime(LocalDateTime value)
    {
        Time = value;
    }
    /**
     Cookie
     */
    private String ID;
    public final String getID()
    {
        return ID;
    }
    public final void setID(String value)
    {
        ID = value;
    }
    /**
     用户ID
     */
    private String UserID;
    public final String getUserID()
    {
        return UserID;
    }
    public final void setUserID(String value)
    {
        UserID = value;
    }

    public final String GetCookie()
    {
        return Token + "=" + getID();
    }
}
