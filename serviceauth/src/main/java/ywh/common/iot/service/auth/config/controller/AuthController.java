package ywh.common.iot.service.auth.config.controller;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import ywh.common.bean.RoleBean;
import ywh.common.bean.UserBean;
import ywh.common.entity.User;
import ywh.common.entity.UserRole;
import ywh.common.repository.UserRepository;
import ywh.common.util.constant.Role;
import ywh.common.util.response.Msg;
import ywh.common.util.response.ResultUtil;

import javax.transaction.Transactional;
import java.security.Principal;

@Api(tags = "认证服务接口", description = "认证服务模块相关接口")
@RestController
public class AuthController {

    private static final Logger logger = LoggerFactory
            .getLogger(AuthController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/current")
    @ApiIgnore
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
    @ApiOperation(value = "新增用户",notes = "目前只有拥有ROLE_ADMIN权限的用户才能添加新用户")
    @PostMapping("/admin/user")
    public Msg addUser(@RequestBody @ApiParam(name="用户对象",value="传入json格式，username password为必填项，新用户默认开启可用",required=true) UserBean userBean){
        String userName = userBean.getUserName();
        String passWord = userBean.getPassWord();
        if(userName == null || passWord == null){
            return  ResultUtil.success("invalid userName or password");
        }
        User result = userRepository.findByUsername(userName);
        if(result == null){
            User user = new User();
            user.setUsername(userName);
            user.setPassword(bCryptPasswordEncoder.encode(passWord));
            user.setMoilbePhone(userBean.getMobilePhone());
            Boolean enabled = userBean.getEnabled();
            if(enabled == null){
                user.setEnabled(true);
            }else{
             user.setEnabled(enabled);
            }
            result = userRepository.save(user);
            return ResultUtil.success(result);
        }
        return ResultUtil.success("user already exist");
    }

    @ApiOperation(value = "删除用户，该接口会删除用户与相关设备信息，建议优先使用禁用账户接口",notes = "目前只有拥有ROLE_ADMIN权限的用户才能删除用户")
    @DeleteMapping("/admin/user")
    @Transactional
    public Msg deleteUser(@RequestBody @ApiParam(name="用户对象",value="传入json格式，username为必填项，新用户默认开启可用",required=true)  UserBean userBean){
        String userName = userBean.getUserName();
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

    @ApiOperation(value = "添加用户角色",notes = "目前只有拥有ROLE_ADMIN权限的用户才能添加权限")
    @PostMapping("/admin/userRole")
    public Msg addUserRole(@RequestBody @ApiParam(name="角色对象",value="传入json格式，username，role为必填项,role只有两个:ROLE_USER,ROLE_ADMIN",required=true)  RoleBean roleBean){
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

    @ApiOperation(value = "启用用户账户",notes = "目前只有拥有ROLE_ADMIN权限的用户才能启用用户账户")
    @PutMapping("/admin/user/enable")
    public Msg enableUser(@RequestBody @ApiParam(name="用户对象",value="传入json格式，username为必填项",required=true) UserBean userBean){
        if(userBean.getUserName() == null){
            return ResultUtil.success("invalid userName");
        }
        User result = userRepository.findByUsername(userBean.getUserName());
        if(result == null){
            return ResultUtil.success("user not exist");
        }
        result.setEnabled(true);
        result = userRepository.save(result);
        return ResultUtil.success(result);
    }

    @ApiOperation(value = "禁用用户账户",notes = "目前只有拥有ROLE_ADMIN权限的用户才能禁用用户账户")
    @PutMapping("/admin/user/disable")
    public Msg disableUser(@RequestBody  @ApiParam(name="用户对象",value="传入json格式，username为必填项",required=true) UserBean userBean){
        if(userBean.getUserName() == null){
            return ResultUtil.success("invalid userName");
        }
        User result = userRepository.findByUsername(userBean.getUserName());
        if(result == null){
            return ResultUtil.success("user not exist");
        }
        result.setEnabled(false);
        result = userRepository.save(result);
        return ResultUtil.success(result);
    }

}
