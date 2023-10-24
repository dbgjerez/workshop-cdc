package io.dborrego.processor;

import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Logger;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.dborrego.client.UserClient;
import io.dborrego.client.UserDTO;
import io.dborrego.model.Change;
import io.dborrego.model.PayloadChange;
import io.dborrego.model.SourceChange;
import io.dborrego.model.UserChange;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserProcessor {

        private static final Logger LOGGER = Logger.getLogger(UserProcessor.class.getName());

        private static final String NOT_FOUND = "NOT_FOUND";

        @RestClient
        UserClient userClient;

        @Incoming("users-fr")
        public void readerFr(Change change) {
                try {
                        processChange(change, "users-fr");
                } catch (RuntimeException e) {
                        LOGGER.warning(String.format("Error no controlado al procesar mensaje: %s",
                                        change != null ? change.toString() : "change is null"));
                }
        }

        @Incoming("users-pt")
        public void readerPt(Change change) {
                try {
                        processChange(change, "users-pt");
                } catch (RuntimeException e) {
                        LOGGER.warning(String.format("Error no controlado al procesar el mensaje [%s] por [%s]",
                                        change != null ? change.toString() : "change is null",
                                        e.getMessage()));
                }
        }

        private void processChange(Change change, String channel) {
                final String operation = extractOperation.apply(change);
                LOGGER.info(String.format("Operación a realizar: %s", operation));
                final String db = extractDatabase.apply(change);
                final String table = extractTable.apply(change);
                LOGGER.info(String.format("Cambio a realizar en canal %s, base de datos %s y tabla %s", channel, db,
                                table));
                LOGGER.info(String.format("Usuario anterior: %s",
                                change.getPayload().getBefore() != null ? change.getPayload().getBefore() : "null"));
                LOGGER.info(String.format("Usuario posterior: %s",
                                change.getPayload() != null ? change.getPayload().getAfter().toString() : "null"));
                final UserDTO user = extractUser(change.getPayload().getAfter());
                try {
                        if (user.getDni() == null) {
                                throw new Exception("El dni del usuario no puede ser null");
                        }
                        if (operation.equals("c")) {
                                final Optional<UserDTO> findState = tmpGetDni(user.getDni());
                                if (findState.isPresent()) {
                                        LOGGER.info(String.format("Encontrado otro usuario con dni [%s]",
                                                        findState.get().getDni()));
                                        userClient.update(user, findState.get().getId());
                                } else {
                                        userClient.create(user);
                                }
                        } else if (operation.equals("u")) {
                                userClient.update(user, tmpGetDni(user.getDni()).get().getId());
                        }
                } catch (Exception e) {
                        LOGGER.warning(String.format("Error, with operation %s for %s", operation, e.getStackTrace()));
                }
        }

        /**
         * TODO this is a bad workaroud while the client and ms-user are broken
         * 
         * @param dni
         */
        private Optional<UserDTO> tmpGetDni(String dni) {
                return userClient.getAllUsers()
                                .stream()
                                .peek(u -> LOGGER.info(String.format("Filtrando usuario %s con dni %s",
                                                u.getFirstName(), u.getDni())))
                                .filter(u -> u.getDni() != null && u.getDni().equals(dni))
                                .findAny();
        }

        private UserDTO extractUser(UserChange after) {
                final UserDTO user = new UserDTO();
                user.setDni(after.getDni());
                user.setFirstName(after.getFirstName());
                user.setLastName(after.getLastName());
                if (after.getGender() != null && !after.getGender().isEmpty()) {
                        user.setGender(after.getGender());
                }
                if (after.getPhone() != null && after.getPhone().isEmpty()) {
                        user.setPhone(after.getPhone());
                }
                if (after.getEmail() != null && after.getEmail().isBlank()) {
                        user.setEmail(after.getEmail());
                }
                return user;
        }

        Function<Change, String> extractOperation = change -> Optional.ofNullable(change)
                        .map(Change::getPayload)
                        .map(PayloadChange::getOperation)
                        .orElse(NOT_FOUND);

        Function<Change, String> extractDatabase = change -> Optional.ofNullable(change)
                        .map(Change::getPayload)
                        .map(PayloadChange::getSource)
                        .map(SourceChange::getDatabase)
                        .orElse(NOT_FOUND);

        Function<Change, String> extractTable = change -> Optional.ofNullable(change)
                        .map(Change::getPayload)
                        .map(PayloadChange::getSource)
                        .map(SourceChange::getTable)
                        .orElse(NOT_FOUND);

}