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
        _request.OriginPath = request.getContextPath();

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

    private static Map<String, List<String>> getQuery(String query) throws MalformedURLException, UnsupportedEncodingException {

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
        AddHeader(response, "Access-Control-Expose-Headers", sso_pass_key, true, ",");
    }
    public static Boolean CheckSso(HttpServletResponse response)
    {
        String cas = response.getHeader(sso_pass_key);
        return cas.equals("true");
    }

    public static void AddHeader(HttpServletResponse response, String header, String value, @Nullable Boolean check,@Nullable String split)
    {
        if (response.containsHeader(header))
        {
            String header_value = response.getHeader(header);
            if (check)
            {
                if (!header_value.contains(value))
                {
                    header_value = header_value + split + value;
                }
            }
            response.setHeader(header,header_value);
        }
        else
        {
            response.addHeader(header, value);
        }
    }
}
