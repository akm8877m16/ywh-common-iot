package ywh.common.iot.service.auth.config.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AuthController {

    @GetMapping("/current")
    public Principal user(Principal user){
        return user;
    }
}
