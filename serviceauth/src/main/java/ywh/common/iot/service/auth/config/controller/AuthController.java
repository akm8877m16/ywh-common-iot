package ywh.common.iot.service.auth.config.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ywh.common.util.response.Msg;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class AuthController {

    @GetMapping("/current")
    public Principal user(Principal user){
        return user;
    }

    /*
    @PostMapping("/admin/addUser")
    public Msg addUser(HttpServletRequest request){
        String userName = request.getParameter()

    }
    */
}
