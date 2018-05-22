package ywh.common.iot.service.auth.config.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="username",unique=true,nullable=false,length=45)
    private String username;

    @Column(name="password",nullable=false,length=100)
    private String password;

    @Column(name="enabled",nullable=false)
    private boolean enabled;

    @Column()
    private String moilbePhone;

    public String getMoilbePhone() {
        return moilbePhone;
    }

    public void setMoilbePhone(String moilbePhone) {
        this.moilbePhone = moilbePhone;
    }

    @OneToMany(fetch=FetchType.LAZY,mappedBy="user")
    private Set<UserRole> userRoles=new HashSet<UserRole>(0);

    public User(){}

    public User(String username, String password){
        this(username,password,true);
    }

    public User(String username,String password,boolean enabled){
        this.username=username;
        this.password=password;
        this.enabled=enabled;
    }

    public User(String username,String password,boolean enabled,Set<UserRole> userRole){
        this.username=username;
        this.password=password;
        this.enabled=enabled;
        this.userRoles=userRole;
    }

    public void addRole(UserRole userRole){
        userRoles.add(userRole);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        return this.userRoles;
    }

    public void setUserRole(Set<UserRole> userRole){
        this.userRoles=userRole;
    }
}
