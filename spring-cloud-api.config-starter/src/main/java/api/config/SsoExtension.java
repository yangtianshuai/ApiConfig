package api.config;


import api.config.sso.SsoMode;
import api.config.sso.SsoRequest;
import api.config.utility.StringUtil;
import io.micrometer.common.lang.Nullable;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SsoExtension {

    public static SsoRequest GetRequest(HttpServletRequest request, SsoMode casMode) throws IOException {
        SsoRequest _request = new SsoRequest();

        _request.OriginScheme = request.getScheme();
        _request.OriginHost = request.getRemoteHost();
        if(_request.OriginHost.equals("0:0:0:0:0:0:0:1") || _request.OriginHost.equals("::1")){
            _request.OriginHost = "127.0.0.1";
        }
        _request.OriginPath = request.getContextPath() + request.getServletPath();

        _request.Scheme = _request.OriginScheme;
        _request.Host = _request.OriginHost;
        _request.Path = _request.OriginPath;

        _request.OriginQuery = getQuery(request.getQueryString());

        if (casMode == SsoMode.Proxy) {
            String url = request.getHeader("url");
            if (StringUtil.isNullOrEmpty(url)) {
                url = request.getHeader("referer");
            }
            if (!StringUtil.isNullOrEmpty(url)) {
                url = URLDecoder.decode(url,"UTF-8");
                URL uri = new URL(url);
                _request.Scheme = uri.getProtocol();
                _request.Host = uri.getHost();
                _request.Port = uri.getPort();
                _request.Path = uri.getPath();
                _request.Query = getQuery(uri.getQuery());
            }
        } else {
            _request.Query = _request.OriginQuery;
            Cookie[] cookies = request.getCookies();

            if(cookies!=null) {
                for (Cookie cookie : cookies) {
                    if (!_request.Query.containsKey(cookie.getName())) {
                        _request.Cookie.put(cookie.getName(), cookie.getValue());
                    }
                }
            }

            if (request.getContentLength() > 0) {
                ServletInputStream reqInputStream = request.getInputStream();

                byte[] buffer = new byte[request.getContentLength()];
                byte[] buf = new byte[1024];
                int nRead = 0;
                while (!reqInputStream.isFinished() && (nRead = reqInputStream.read(buf)) != -1) {
                    System.arraycopy(buf,0,buffer,0,nRead);
                }
                _request.Body = buffer;
            }
        }

        return _request;
    }

    private static Map<String, List<String>> getQuery(String query) {

        Map<String, List<String>> parameters = new HashMap<>();
        if(StringUtil.isNullOrEmpty(query)){
            return parameters;
        }
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];
                if(parameters.containsKey(key)){
                    parameters.get(key).add(value);
                }else{
                    List<String> values = new ArrayList<>();
                    values.add(value);
                    parameters.put(key, values);
                }
            }
        }
        return parameters;
    }

    private static String sso_pass_key = "sso_pass";
    public static void SetSsoPass(HttpServletResponse response)
    {
        response.addHeader(sso_pass_key, "true");
        HttpExtension.current().addHeader(response, "Access-Control-Expose-Headers", sso_pass_key, true, ",");
    }
    public static void setCors(HttpServletResponse response,SsoRequest sso_request){
        String original_url = sso_request.Scheme + "://" + sso_request.Host + ":" + sso_request.Port;
        response.addHeader("Access-Control-Allow-Origin", original_url);
        response.addHeader("Access-Control-Allow-Credentials", "true");
    }
    public static boolean CheckSso(HttpServletResponse response)
    {
        String cas = response.getHeader(sso_pass_key);
        if(StringUtil.isNullOrEmpty(cas)){
            return false;
        }
        return cas.equals("true");
    }

    public static String getAccessToken(HttpServletRequest request){
        String key = "access_token";
        String access_token = request.getHeader(key);
        if(StringUtil.isNullOrEmpty(access_token)){
            Map<String, List<String>> queries = getQuery(request.getQueryString());
            if(queries.containsKey(key)){
                access_token = queries.get(key).get(0);
            }
        }
        return access_token;
    }

}
