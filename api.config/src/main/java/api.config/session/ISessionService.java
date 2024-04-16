package api.config.session;

import java.time.Duration;

public interface ISessionService {

    /**
     * 是否含有Session
     * @param token 口令
     * @return
     */
    boolean contain(String token);

    /**
     * 获取Session
     */
    <T> T get(String token);

    /**
     * 设置Session
     * @param session Session对象
     */
    void set(Session session);

    /**
     * 刷新Session
     * @param session
     */
    void update(Session session);

    /**
     * 移除Session
     * @param session
     * @return
     */
    boolean remove(String token);
}