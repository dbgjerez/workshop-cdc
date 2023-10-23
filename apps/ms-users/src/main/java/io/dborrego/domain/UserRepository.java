package io.dborrego.domain;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public User findByDni(String dni) {
        return find("dni", dni).firstResult();
    }

}