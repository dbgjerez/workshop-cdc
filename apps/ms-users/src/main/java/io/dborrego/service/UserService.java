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

    public List<User> findByDni(final String dni) {
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
    public User update(final User u, Long idUser) {
        final User user = usersRepository.findById(idUser);
        user.setFirstName(u.getFirstName());
        user.setLastName(u.getLastName());
        user.setDni(u.getDni());
        user.setEmail(u.getEmail() != null ? u.getEmail() : user.getEmail());
        user.setGender(u.getGender() != null ? u.getGender() : user.getGender());
        user.setPhone(u.getPhone().isBlank() ? u.getPhone() : user.getPhone());
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
