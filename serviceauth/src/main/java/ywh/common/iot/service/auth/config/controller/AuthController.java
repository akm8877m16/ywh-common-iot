package ywh.common.iot.service.auth.config.controller;

import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ywh.common.bean.RoleBean;
import ywh.common.bean.UserBean;
import ywh.common.entity.User;
import ywh.common.entity.UserRole;
import ywh.common.repository.UserRepository;
import ywh.common.util.constant.Role;
import ywh.common.util.response.Msg;
import ywh.common.util.response.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class AuthController {

    private static final Logger logger = LoggerFactory
            .getLogger(AuthController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/current")
    public Principal user(Principal user){
        return user;
    }
    /*
    @GetMapping("/admin/user")
    public Msg getUser(@RequestBody User user){
        logger.info(user.toString());
        String userName = user.getUsername();
        if(userName == null){
            return  ResultUtil.success("invalid userName or password");
        }
        User result = userRepository.findByUsername(user.getUsername());
        if(result == null){
            return ResultUtil.success("user not exist");
        }
        return ResultUtil.success(result);
    }
    */
    @PostMapping("/admin/user")
    public Msg addUser(@RequestBody User user){
        logger.info(user.toString());
        String userName = user.getUsername();
        String passWord = user.getPassword();
        if(userName == null || passWord == null){
            return  ResultUtil.success("invalid userName or password");
        }
        User result = userRepository.findByUsername(user.getUsername());
        if(result == null){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setEnabled(true);
            result = userRepository.save(user);
            return ResultUtil.success(result);
        }
        return ResultUtil.success("user already exist");
    }

    @DeleteMapping("/admin/user")
    public Msg deleteUser(@RequestBody User user){
        String userName = user.getUsername();
        if(userName == null){
            return ResultUtil.success("invalid userName");
        }
        User result = userRepository.findByUsername(userName);
        if(result == null){
            return ResultUtil.success("user not exist");
        }
        int deleteResult = userRepository.deleteByUsername(userName);
        return ResultUtil.success("user delete complete with result: "+ deleteResult);
    }

    @PostMapping("/admin/userRole")
    public Msg addUserRole(@RequestBody RoleBean roleBean){
        if(roleBean.getRole() == null || roleBean.getUsername() == null){
            return ResultUtil.success("invalid userName or role");
        }
        if(!roleBean.getRole().equals(Role.ROLE_ADMIN) && !roleBean.getRole().equals(Role.ROLE_USER)){
            return ResultUtil.success("invalid role");
        }
        User result = userRepository.findByUsername(roleBean.getUsername());
        if(result == null){
            return ResultUtil.success("user not exist");
        }
        if(result.isRoleExist(roleBean)){
            return ResultUtil.success(("user role exist"));
        }
        UserRole userRole = new UserRole(roleBean.getRole());
        result.addRole(userRole);
        userRepository.save(result);
        return ResultUtil.success(result);
    }

    @PutMapping("/admin/user/enable")
    public Msg enableUser(@RequestBody UserBean userBean){
        if(userBean.getName() == null){
            return ResultUtil.success("invalid userName");
        }
        User result = userRepository.findByUsername(userBean.getName());
        if(result == null){
            return ResultUtil.success("user not exist");
        }
        result.setEnabled(true);
        result = userRepository.save(result);
        return ResultUtil.success(result);
    }

    @PutMapping("/admin/user/disable")
    public Msg disableUser(@RequestBody UserBean userBean){
        if(userBean.getName() == null){
            return ResultUtil.success("invalid userName");
        }
        User result = userRepository.findByUsername(userBean.getName());
        if(result == null){
            return ResultUtil.success("user not exist");
        }
        result.setEnabled(false);
        result = userRepository.save(result);
        return ResultUtil.success(result);
    }

}
