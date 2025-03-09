package com.peliQAn.framework.pact;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.peliQAn.framework.config.PropertyManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Base class for Pact consumer tests
 */
@Slf4j
@ExtendWith(PactConsumerTestExt.class)
public abstract class PactBaseConsumer {
    protected static final String PACT_BROKER_URL = PropertyManager.getInstance().getProperty("pact.broker.url", "http://localhost:9292");
    protected static final String PACT_REPORTS_PATH = PropertyManager.getInstance().getProperty("pact.reports.path", "target/pact-reports");

    /**
     * Setup for Pact test
     * 
     * @param mockServer The mock server instance
     * @return Base URL for the mock server
     */
    protected String setupPactTest(MockServer mockServer) {
        String url = mockServer.getUrl();
        log.info("Setting up Pact test with mock server at: {}", url);
        return url;
    }

    /**
     * Build Pact interaction
     * This method should be implemented by subclasses with @Pact annotation
     * 
     * @return The built Pact interaction
     */
    @Pact(provider = "provider_name", consumer = "consumer_name")
    public abstract RequestResponsePact createPact();
}