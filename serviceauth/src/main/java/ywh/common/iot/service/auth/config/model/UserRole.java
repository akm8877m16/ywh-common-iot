package ywh.common.iot.service.auth.config.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name="authorities",uniqueConstraints=@UniqueConstraint(
        columnNames={"username","authority"}))
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="username",nullable=false,referencedColumnName = "username")
    private User user;

    @Column(nullable=false,length=45)
    private String authority;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser(){
        return this.user;
    }

    public void setUser(User user){
        this.user=user;
    }

    public String getRole(){
        return this.authority;
    }

    public void setRole(String role){
        this.authority=role;
    }

}
