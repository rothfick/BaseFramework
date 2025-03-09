package com.peliQAn.framework.pact.examples;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.peliQAn.framework.pact.PactBaseConsumer;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Example Pact consumer test for User API
 */
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "user-provider")
public class UserApiConsumerPactTest extends PactBaseConsumer {

    /**
     * Define the Pact contract for getting a user by ID
     */
    @Override
    @Pact(consumer = "user-consumer", provider = "user-provider")
    public RequestResponsePact createPact() {
        return new PactDslWithProvider()
                .given("User with ID 1 exists")
                .uponReceiving("A request to get a user by ID")
                .path("/api/users/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody()
                        .integerType("id", 1)
                        .stringType("name", "Test User")
                        .stringType("email", "test.user@example.com"))
                .toPact();
    }

    /**
     * Test the contract with a mock server
     */
    @Test
    @PactTestFor(pactMethod = "createPact")
    void testGetUserById(MockServer mockServer) {
        // Arrange
        String url = setupPactTest(mockServer);
        
        // Act
        Response response = RestAssured.given()
                .baseUri(url)
                .when()
                .get("/api/users/1")
                .then()
                .statusCode(200)
                .extract()
                .response();
                
        // Assert
        assertThat(response.jsonPath().getInt("id"), is(1));
        assertThat(response.jsonPath().getString("name"), equalTo("Test User"));
        assertThat(response.jsonPath().getString("email"), equalTo("test.user@example.com"));
    }
}