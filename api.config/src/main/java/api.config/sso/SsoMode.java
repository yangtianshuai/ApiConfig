package api.config.sso;

import java.util.HashMap;

public enum SsoMode {
    /**
     * 服务模式
     */
    Service(1),
    /**
     * 代理模式
     */
    Proxy(2);

    private int value;
    SsoMode(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }
}
