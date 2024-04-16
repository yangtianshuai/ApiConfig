package api.config;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class OpenExtention {

    public static boolean accessCheck(HttpServletRequest request, String path){
        return false;
    }

    public static boolean openCheck(HttpServletRequest request, List<String> apps) {
        return false;
    }

}
