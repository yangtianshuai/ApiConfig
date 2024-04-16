package api.config.session;

import api.config.cache.ICacheUnit;
import api.config.cache.MemoryCacheUnit;

import java.time.Duration;

public class SessionService extends ServerSession {

    private ICacheUnit _cache;

    public SessionService(int seconds){

        this(seconds,new MemoryCacheUnit());
    }

    public SessionService(int seconds,ICacheUnit cache){

        this.Seconds = seconds;
        this._cache = cache;
    }


    @Override
    public boolean contain(String token) {
        return _cache.get(token) != null;
    }

    @Override
    public <T> T get(String token) {
        return _cache.get(token);
    }

    @Override
    public void set(Session session) {
        _cache.set(session.token, session, Duration.ofSeconds(this.Seconds));
    }

    @Override
    public void update(Session session) {
        _cache.clear(session.token);
        set(session);
    }

    @Override
    public boolean remove(String token) {
        return _cache.clear(token);
    }
}
