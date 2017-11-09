package ru.stqa.pft.mantis.model;


import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "mantis_user_table")
public class UserData {

    @Id
    @Column(name = "id")
    private int id = Integer.MAX_VALUE;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    //@Type(type = "text")
    private String email;

    //@Column(name = "enabled")
    //@Type(type = "NumericBooleanType")
    //private Boolean enabled;

    public int getId() {
        return id;
    }

    public UserData withId(int id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserData withUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserData withEmail(String email) {
        this.email = email;
        return this;
    }

/*    public Boolean getEnabled() {
        return enabled;
    }

    public UserData withEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }*/
}
