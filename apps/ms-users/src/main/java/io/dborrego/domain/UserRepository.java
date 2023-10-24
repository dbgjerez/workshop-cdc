package io.dborrego.domain;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public List<User> findByDni(String dni) {
        return find("dni", dni).list();
    }

}
