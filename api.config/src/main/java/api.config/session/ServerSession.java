package api.config.session;

public abstract class ServerSession implements ISessionService {
    public int Seconds = 60*60*2;

    private static String Token_Key="token";

    public void setTokenkey(String token_key){
        Token_Key = token_key;
    }

    public static String Tokenkey(){
        return Token_Key;
    }
}