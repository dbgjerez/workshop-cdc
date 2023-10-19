package io.dborrego.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import io.dborrego.domain.User;
import io.dborrego.domain.UserRepository;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository usersRepository;

    public List<User> listAll() {
        return usersRepository.listAll();
    }

    public User findById(final Long idUser) {
        return usersRepository.findById(idUser);
    }

    @Transactional
    public User saveOrUpdate(final User u) {
        final User user = Optional.ofNullable(u)
                .map(User::getId)
                .map(id -> usersRepository
                        .findById(id))
                .orElse(new User());
        user.setDni(u.getDni());
        user.setFirstName(u.getFirstName());
        user.setGender(u.getGender());
        user.setLastName(u.getLastName());
        user.setPhone(u.getPhone());
        usersRepository.persist(user);
        return user;
    }

    @Transactional
    public Boolean deleteById(final Long id) {
        return Optional.ofNullable(id)
                .map(idUser -> usersRepository
                        .deleteById(idUser))
                .orElse(false);
    }

}
