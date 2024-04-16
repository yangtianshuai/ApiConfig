package api.config.session;

public interface ISession {
    /**
     * 是否含有Session
     *
     * @param token 口令
     * @return
     */
    boolean contain(String token);

    /**
     * 获取Session
     * @param token
     * @return
     */
    Session get(String token);

    /**
     * 设置Session
     * @param session
     * @return
     */
    boolean set(Session session);

    /**
     * 移除Session
     * @param session
     * @return
     */
    boolean remove(Session session);
}