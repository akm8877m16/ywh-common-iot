package ywh.common.iot.service.auth.config.model;
/*
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy=AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="username",unique=true,nullable=false,length=45)
    private String username;
    @Column(name="password",nullable=false,length=60)
    private String password;
    @Column(name="enabled",nullable=false)
    private boolean enabled;
    @OneToMany(fetch=FetchType.LAZY,mappedBy="user")
    private Set<UserRole> userRole=new HashSet<UserRole>(0);

    public User(){}

    public User(String username,String password,boolean enabled){
        this.username=username;
        this.password=password;
        this.enabled=enabled;
    }

    public User(String username,String password,boolean enabled,Set<UserRole> userRole){
        this.username=username;
        this.password=password;
        this.enabled=enabled;
        this.userRole=userRole;
    }


    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username=username;
    }

    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password=password;
    }


    public boolean isEnabled(){
        return this.enabled;
    }
    public void setEnabled(boolean enabled){
        this.enabled=enabled;
    }


    public Set<UserRole> getUserRole(){
        return this.userRole;
    }
    public void setUserRole(Set<UserRole> userRole){
        this.userRole=userRole;
    }
}
*/