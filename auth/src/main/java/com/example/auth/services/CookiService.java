package com.example.auth.services;


import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;

@Service
public class CookiService {
    int minuteConverter = 60 * 1000; // 60 sekund * 1000 milisekund

    public Cookie generateCookie(String name,String value,int exp){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        int expInMinutes = exp*minuteConverter;
        cookie.setMaxAge(expInMinutes);
        cookie.setHttpOnly(true);
        return cookie;
    }

    public Cookie removeCookie(Cookie[] cookies, String name){
        for (Cookie cookie:cookies){
            if (cookie.getName().equals(name)){
                cookie.setPath("/");
                cookie.setMaxAge(0);
                cookie.setHttpOnly(true);
                return cookie;
            }
        }
        return null;
    }
}
