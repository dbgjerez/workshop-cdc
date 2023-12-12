package io.dborrego.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity(name = "user")
public class User extends PanacheEntity {

    public String firstName;
    public String lastName;
    public String dni;
    public String phone;
    public String gender;

}