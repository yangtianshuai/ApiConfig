package com.honlivhp.api.config;

import api.config.sso.SsoMode;
import api.config.sso.SsoRequest;
import api.config.utility.StringUtil;
import com.sun.jndi.toolkit.url.Uri;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SsoExtension {

    public static SsoRequest GetRequest(HttpServletRequest request, SsoMode casMode) throws IOException {
        SsoRequest _request = new SsoRequest();

        _request.Scheme = request.getScheme();
        _request.Host = request.getRemoteHost();
        _request.Path = request.getContextPath();

        if (casMode == SsoMode.Proxy) {
            String url = request.getHeader("url");
            if (StringUtil.isNullOrEmpty(url)) {
                url = request.getHeader("referer");
            }
            if (!StringUtil.isNullOrEmpty(url)) {
                url = URLDecoder.decode(url);
                Uri uri = new Uri(url);
                _request.Scheme = uri.getScheme();
                _request.Host = uri.getHost();
                _request.Port = uri.getPort();
                _request.Path = uri.getPath();
                _request.Query = getQuery(uri.getQuery());
            }
        } else {
            _request.Query = getQuery(request.getQueryString());
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (!_request.Query.containsKey(cookie.getName())) {
                    _request.Cookie.put(cookie.getName(), cookie.getValue());
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

    private static Map<String, List<String>> getQuery(String url) throws MalformedURLException {

        Uri uri = new Uri(URLDecoder.decode(url));
        String query = uri.getQuery();
        Map<String, List<String>> parameters = new HashMap<>();
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
}
