package io.dborrego.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import io.dborrego.domain.User;
import io.dborrego.domain.UserRepository;

@ApplicationScoped
public class UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    @Inject
    UserRepository usersRepository;

    public List<User> listAll() {
        return usersRepository.listAll();
    }

    public User findById(final Long idUser) {
        return usersRepository.findById(idUser);
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
        final User entity = usersRepository.findById(idUser);
        LOGGER.info(String.format("Encontrado usuario con dni [%s] e id [%d]", entity.getDni(), entity.getId()));
        entity.setFirstName(u.getFirstName());
        entity.setLastName(u.getLastName());
        entity.setDni(u.getDni());
        entity.setEmail(u.getEmail() != null ? u.getEmail() : entity.getEmail());
        entity.setGender(u.getGender() != null ? u.getGender() : entity.getGender());
        entity.setPhone(u.getPhone() != null ? u.getPhone() : entity.getPhone());
        usersRepository.persist(entity);
        return entity;
    }

    @Transactional
    public Boolean deleteById(final Long id) {
        return Optional.ofNullable(id)
                .map(idUser -> usersRepository
                        .deleteById(idUser))
                .orElse(false);
    }

}
