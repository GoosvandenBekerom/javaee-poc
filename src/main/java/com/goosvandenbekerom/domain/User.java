package com.goosvandenbekerom.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    private String username;
    private String password;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Group> groups;

    public User() {}
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        groups = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<Group> getGroups() {
        return groups;
    }
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
