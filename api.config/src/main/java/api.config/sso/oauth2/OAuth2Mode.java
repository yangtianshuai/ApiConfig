package api.config.sso.oauth2;

public enum OAuth2Mode {
    /**
     * 简单模式
     */
    Simple(1),
    /**
     * 标准模式
     */
    Standard(2);

    private int value;
    OAuth2Mode(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }
}
