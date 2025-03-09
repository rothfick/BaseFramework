package com.peliQAn.framework.pact.examples;

import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.peliQAn.framework.pact.PactBaseProvider;
import lombok.extern.slf4j.Slf4j;

/**
 * Example Pact provider test for User API
 */
@Slf4j
@Provider("user-provider")
@PactFolder("pacts")
public class UserApiProviderPactTest extends PactBaseProvider {

    /**
     * Setup state for "User with ID 1 exists"
     * In a real application, this would set up test data in the database
     */
    @State("User with ID 1 exists")
    public void userWithId1Exists() {
        log.info("Setting up state: User with ID 1 exists");
        // In a real implementation, this would create test data in the database
        // For example:
        // userRepository.save(new User(1, "Test User", "test.user@example.com"));
    }

    /**
     * Generic state setup method
     */
    @Override
    protected void setupStateForVerification(String state) {
        log.info("Setting up state for verification: {}", state);
        // This is a fallback for states not handled by specific methods
        switch (state) {
            case "User with ID 1 exists":
                userWithId1Exists();
                break;
            default:
                log.warn("State not recognized: {}", state);
        }
    }
}