package ywh.common.iot.service.auth.config.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ywh.common.bean.DeviceBean;
import ywh.common.entity.Device;
import ywh.common.entity.User;
import ywh.common.repository.UserRepository;
import ywh.common.util.response.Msg;
import ywh.common.util.response.ResultUtil;

@RestController()
public class DeviceController {

    private static final Logger logger = LoggerFactory
            .getLogger(DeviceController.class);

    @Autowired
    UserRepository userRepository;

    @PostMapping("/admin/device")
    public Msg addDevice(@RequestBody DeviceBean deviceBean){
        if(deviceBean.getUsername() == null){
            return ResultUtil.success("inValid username");
        }
        User result = userRepository.findByUsername(deviceBean.getUsername());
        if(result == null){
            return ResultUtil.success("user not exist");
        }
        Device device = new Device(deviceBean.getSn());
        if(result.isDeviceExist(device)){
            return ResultUtil.success("device exist");
        }
        result.addDevice(device);
        result = userRepository.save(result);
        return ResultUtil.success(result);
    }
}
