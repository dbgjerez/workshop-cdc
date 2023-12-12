package io.dborrego.service;

import java.util.List;

import io.dborrego.domain.User;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UserService {

    public List<User> listAll() {
        return User.findAll().list();
    }

    public User findById(final Long idUser) {
        return User.findById(idUser);
    }

    public User create(final User u) {
        u.persist();
        return u;
    }

    public void update(final User u, final Long idUser) throws NotFoundException {
        final PanacheEntityBase user = User.findByIdOptional(idUser)
                .map(aux -> merge((User) aux, u))
                .orElseThrow(() -> new NotFoundException("User not found with id: " + idUser));
        user.persist();
    }

    private User merge(User dbUser, User u) {
        dbUser.dni = u.dni;
        dbUser.firstName = u.firstName;
        dbUser.gender = u.gender;
        dbUser.lastName = u.lastName;
        dbUser.phone = u.phone;
        return dbUser;
    }

    @Transactional
    public Boolean deleteById(final Long id) {
        return User.deleteById(id);
    }

}
