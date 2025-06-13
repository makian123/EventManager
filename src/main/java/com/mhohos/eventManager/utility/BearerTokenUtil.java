package com.mhohos.eventManager.utility;

import org.springframework.stereotype.Component;

public class BearerTokenUtil {
    public static String extractTokenFromHeader(String authHeader){
        return authHeader.substring(7); // Skip "Bearer "
    }
}
