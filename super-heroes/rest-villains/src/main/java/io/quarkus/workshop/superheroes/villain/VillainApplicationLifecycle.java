package io.quarkus.workshop.superheroes.villain;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.jboss.logging.Logger;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;

@ApplicationScoped
public class VillainApplicationLifecycle {
    private static final Logger logger = Logger.getLogger(VillainApplicationLifecycle.class);

    void onStart(@Observes StartupEvent startupEvent) {
        logger.info("====== LIFECYCLE LISTENER: Startup ========");
        logger.info("Profile is " + ProfileManager.getActiveProfile());
    }

    void onEnd(@Observes ShutdownEvent shutdownEvent) {
        logger.info("====== LIFECYCLE LISTENER: Shutdown ========");

    }
}
