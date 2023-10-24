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

    public User findByDni(final String dni) {
        return usersRepository.findByDni(dni);
    }

    @Transactional
    public User create(final User u) {
        final User user = new User();
        user.setDni(u.getDni());
        user.setFirstName(u.getFirstName());
        user.setLastName(u.getLastName());
        user.setGender(u.getGender());
        user.setPhone(u.getPhone());
        user.setEmail(u.getEmail());
        usersRepository.persist(user);
        return user;
    }

    @Transactional
    public User update(final User u) {
        final User user = Optional.ofNullable(u)
                .map(User::getDni)
                .map(usersRepository::findByDni)
                .orElse(new User());
        user.setFirstName(u.getFirstName());
        user.setLastName(u.getLastName());
        user.setEmail(u.getEmail().isBlank() ? user.getEmail() : u.getEmail());
        user.setGender(u.getGender().isBlank() ? user.getGender() : u.getGender());
        user.setPhone(u.getPhone().isBlank() ? user.getPhone() : u.getPhone());
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
