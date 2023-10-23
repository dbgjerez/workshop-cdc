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
                final String operation = extractOperation.apply(change);
                final String db = extractDatabase.apply(change);
                final String table = extractTable.apply(change);
                LOGGER.info(String.format("Received operation [%s] in db [%s] and table [%s] - %s",
                                operation, db, table, change));
                final UserDTO user = extractUser(change.getPayload().getAfter());
                if (operation.equals("c")) {
                        userClient.create(user);
                } else {
                        userClient.update(user, user.getDni());
                }
        }

        @Incoming("users-pt")
        public void readerPt(Change change) {
                final String operation = extractOperation.apply(change);
                final String db = extractDatabase.apply(change);
                final String table = extractTable.apply(change);
                LOGGER.info(String.format("Received operation [%s] in db [%s] and table [%s] - %s",
                                operation, db, table, change));
                final UserDTO user = extractUser(change.getPayload().getAfter());
                if (operation.equals("c")) {
                        userClient.create(user);
                } else {
                        userClient.update(user, user.getDni());
                }
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
                        return user;
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