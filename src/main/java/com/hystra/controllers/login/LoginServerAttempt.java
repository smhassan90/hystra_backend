package com.hystra.controllers.login;


import com.google.gson.Gson;
import com.hystra.dal.LoginResponse;
import com.hystra.entity.login.LoginStatus;
import com.hystra.entity.login.User;
import com.hystra.utils.HibernateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;

/**
 * @author Syed Muhammad Hassan
 * 8th November, 2021
 */

@Controller
public class LoginServerAttempt {

    @CrossOrigin(origins = "*" )
    @RequestMapping(value = "/loginServerAttempt", method = RequestMethod.GET,params={"username","password"})
    @ResponseBody
    public String loginServerAttempt(String username, String password){
        String secret = "secret";

        User user = new User();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setStatusCode(Codes.SOMETHING_WENT_WRONG);
        try {

            List<User> users = (List<User>) HibernateUtil.getDBObjects("from User where userName ='" + username + "' and password ='" + password + "'");
            if(users!=null && users.size()>0){

                LoginStatus loginStatus = new LoginStatus();
                user = users.get(0);
                loginStatus.setStatus(1);
                loginStatus.setUsername(user.getUserName());
                loginStatus.setType(1);
                String token = UUID.randomUUID().toString();
                loginStatus.setToken(token);
                loginStatus.setPOSITION_CODE("");
                boolean isSuccessful = HibernateUtil.saveOrUpdate(loginStatus);
                if(isSuccessful){
                    loginResponse.setToken(token);
                    loginResponse.setStatusCode(Codes.ALL_OK);
                    loginResponse.setUser(user);
                }
            }else{
                loginResponse.setStatusCode(Codes.INVALID_CREDENTIALS);
            }
        }catch(Exception e){
            loginResponse.setStatusCode(Codes.SOMETHING_WENT_WRONG);
        }
        return new Gson().toJson(loginResponse);
    }

}
