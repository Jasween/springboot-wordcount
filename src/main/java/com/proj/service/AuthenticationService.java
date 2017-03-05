package com.proj.service;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by jasweensingh on 05/03/17.
 */
@Service
public class AuthenticationService {

    @Value("${application.username}")
    private String username;

    @Value("${application.password}")
    private String password;


    public boolean isUserAuthorized(String securityKey) {
        if (!StringUtils.isEmpty(securityKey) ){
            byte[] byteDecodeArray = Base64.decode(securityKey.split(" ")[1]);
            String decipherStr = new String(byteDecodeArray);
            if (decipherStr.indexOf(":") != -1){
                String[] credentials = decipherStr.split(":");
                return credentials[0].equalsIgnoreCase(username) && credentials[1].equalsIgnoreCase(password);
            }
        }
        return false;
    }

}
