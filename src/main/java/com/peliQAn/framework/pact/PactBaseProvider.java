package com.peliQAn.framework.pact;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import com.peliQAn.framework.config.PropertyManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Base class for Pact provider tests
 */
@Slf4j
@Provider("provider_name")
@PactBroker
public abstract class PactBaseProvider {
    protected static final String PACT_BROKER_URL = PropertyManager.getInstance().getProperty("pact.broker.url", "http://localhost:9292");
    protected static final String PROVIDER_BASE_URL = PropertyManager.getInstance().getProperty("api.baseUrl");

    /**
     * Setup for each Pact verification
     *
     * @param context The Pact verification context
     */
    @BeforeEach
    void setupTestTarget(PactVerificationContext context) {
        log.info("Setting up Pact test target for URL: {}", PROVIDER_BASE_URL);
        context.setTarget(new HttpTestTarget(PROVIDER_BASE_URL, 80));
    }

    /**
     * Template for Pact verification tests
     *
     * @param context The Pact verification context
     */
    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        log.info("Running Pact verification");
        context.verifyInteraction();
    }

    /**
     * State handler method
     * This is a placeholder that child classes should override with specific state handlers using @State annotation
     *
     * @param state The state name from the Pact file
     */
    @State("default state")
    public void defaultState(String state) {
        log.info("Setting up state: {}", state);
        setupStateForVerification(state);
    }

    /**
     * Setup state for Pact verification
     * This method should be implemented by subclasses to set up states required by the Pact verification
     *
     * @param state The state name from the Pact file
     */
    protected abstract void setupStateForVerification(String state);
}