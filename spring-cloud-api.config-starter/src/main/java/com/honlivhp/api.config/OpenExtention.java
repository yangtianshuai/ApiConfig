package com.honlivhp.api.config;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class OpenExtention {

    public static Boolean accessCheck(HttpServletRequest request, String path){
        return false;
    }

    public static boolean openCheck(HttpServletRequest request, List<String> apps) {
        return false;
    }

}
