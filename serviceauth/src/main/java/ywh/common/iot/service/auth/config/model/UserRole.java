package ywh.common.iot.service.auth.config.model;
/*
import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name="user_roles",uniqueConstraints=@UniqueConstraint(
        columnNames={"role","username"}))
public class UserRole {
    @Id
    @GeneratedValue(strategy=AUTO)
    @Column(unique=true,nullable=false)
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="username",nullable=false)
    private User user;
    @Column(nullable=false,length=45)
    private String role;

    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id=id;
    }

    public User getUser(){
        return this.user;
    }
    public void setUser(User user){
        this.user=user;
    }

    public String getRole(){
        return this.role;
    }
    public void setRole(String role){
        this.role=role;
    }

}

*/