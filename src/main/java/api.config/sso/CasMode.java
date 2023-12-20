package api.config.sso;

import java.util.HashMap;

public enum CasMode {
    Service(1),
    Proxy(2);

    private int value;
    static HashMap<Integer, CasMode> mappings;
    static HashMap<Integer, CasMode> getMappings()
    {
        if (mappings == null)
        {
            mappings = new HashMap<Integer, CasMode>();
        }
        return mappings;
    }

    CasMode(int value)
    {
        this.value = value;
        getMappings().put(value, this);
    }

    public int getValue()
    {
        return value;
    }

    public static CasMode forValue(int value)
    {
        return getMappings().get(value);
    }
}
