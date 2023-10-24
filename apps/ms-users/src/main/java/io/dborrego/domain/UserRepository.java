package io.dborrego.domain;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User, Long> {

}
