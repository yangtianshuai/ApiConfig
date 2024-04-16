package api.config.session;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Session implements Serializable {

    public Session()    {
        create_time = LocalDateTime.now();
        roles = new ArrayList<>();
    }

    /**
     * 授权口令
     */
    public String token;

    /**
     * 角色
     */
    public List<String> roles;

    /**
     * 创建时间
     */
    public LocalDateTime create_time;

}
