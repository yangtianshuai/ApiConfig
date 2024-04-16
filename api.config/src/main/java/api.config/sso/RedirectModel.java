package api.config.sso;

public class RedirectModel {

    public RedirectModel(SsoMode mode){
        repeat_check = false;
        this.mode = mode;
    }
    public String url;
    public boolean repeat_check;
    public SsoMode mode;
}
