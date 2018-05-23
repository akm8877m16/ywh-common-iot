package ywh.common.iot.service.auth.config.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ywh.common.entity.User;
//import ywh.common.iot.service.auth.config.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);


}
