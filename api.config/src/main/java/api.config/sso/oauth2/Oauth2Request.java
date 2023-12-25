package api.config.sso.oauth2;

public class Oauth2Request {
    public String app_id;
    /**
     * 返回信息类型，支持code、token两种模式
     */
    public String response_type;
    /**
     * 回调URL
     */
    public String redirect_uri;
    /**
     * 请求标识
     */
    public String state;
    /**
     * 希望获取的资源
     */
    public String scope;
}
