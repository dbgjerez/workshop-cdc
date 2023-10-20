package io.dborrego.processor;

import java.util.logging.Logger;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.dborrego.model.Change;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserProcessor {

    private static final Logger LOGGER = Logger.getLogger(UserProcessor.class.getName());

    @Incoming("users")
    public void reader(Change change) {
        LOGGER.warning("Recibido mensaje");
        LOGGER.info(String.format("Received %s", change));
    }

}