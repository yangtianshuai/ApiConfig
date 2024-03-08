package api.config.sso.oauth2;

import java.time.LocalDateTime;

public class RefreshToken {
    public String Token;
    public Integer Expire;
    public LocalDateTime CreateTime;

    public RefreshToken()
    {
        CreateTime = LocalDateTime.now();
    }
}
