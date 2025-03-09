package com.peliQAn.framework.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Step definitions for API testing
 */
@Slf4j
public class ApiSteps {
    private static final String TOKEN = "sample-token"; // In real scenario, this should be obtained from token provider
    private String token;
    private Response response;
    private Map<String, Object> userData = new HashMap<>();
    
    @Given("I have a valid API token")
    public void iHaveAValidApiToken() {
        token = TOKEN;
        log.info("Using API token for authentication");
    }
    
    @Given("I have user data with name {string} and email {string}")
    public void iHaveUserDataWithNameAndEmail(String name, String email) {
        userData.put("name", name);
        userData.put("email", email);
        log.info("Created user data with name: {} and email: {}", name, email);
    }
    
    @Given("I have user data with updated name {string}")
    public void iHaveUserDataWithUpdatedName(String name) {
        userData.put("name", name);
        log.info("Created user data with updated name: {}", name);
    }
    
    @When("I send a GET request to {string}")
    public void iSendAGETRequestTo(String endpoint) {
        log.info("Sending GET request to endpoint: {}", endpoint);
        // In a real implementation, this would use the actual REST client
        // response = apiClient.get(endpoint);
        
        // For demonstration, we simulate a response
        response = simulateGetResponse(endpoint);
    }
    
    @When("I send a GET request to {string} with query parameter {string} as {string} and {string} as {string}")
    public void iSendAGETRequestToWithQueryParameters(String endpoint, String param1, String value1, String param2, String value2) {
        log.info("Sending GET request to endpoint: {} with query parameters: {}={}, {}={}", 
                endpoint, param1, value1, param2, value2);
        
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put(param1, value1);
        queryParams.put(param2, value2);
        
        // In a real implementation, this would use the actual REST client
        // response = apiClient.get(endpoint, queryParams);
        
        // For demonstration, we simulate a response
        response = simulateGetResponse(endpoint);
    }
    
    @When("I send a POST request to {string}")
    public void iSendAPOSTRequestTo(String endpoint) {
        log.info("Sending POST request to endpoint: {} with data: {}", endpoint, userData);
        
        // In a real implementation, this would use the actual REST client
        // response = apiClient.post(endpoint, userData);
        
        // For demonstration, we simulate a response
        response = simulatePostResponse(endpoint, userData);
    }
    
    @When("I send a PUT request to {string}")
    public void iSendAPUTRequestTo(String endpoint) {
        log.info("Sending PUT request to endpoint: {} with data: {}", endpoint, userData);
        
        // In a real implementation, this would use the actual REST client
        // response = apiClient.put(endpoint, userData);
        
        // For demonstration, we simulate a response
        response = simulatePutResponse(endpoint, userData);
    }
    
    @When("I send a DELETE request to {string}")
    public void iSendADELETERequestTo(String endpoint) {
        log.info("Sending DELETE request to endpoint: {}", endpoint);
        
        // In a real implementation, this would use the actual REST client
        // response = apiClient.delete(endpoint);
        
        // For demonstration, we simulate a response
        response = simulateDeleteResponse(endpoint);
    }
    
    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        log.info("Verifying response status code. Expected: {}, Actual: {}", expectedStatusCode, actualStatusCode);
        Assert.assertEquals(actualStatusCode, expectedStatusCode, "Response status code is incorrect");
    }
    
    @Then("the response should contain user details with id {string}")
    public void theResponseShouldContainUserDetailsWithId(String userId) {
        String actualUserId = response.jsonPath().getString("id");
        log.info("Verifying response contains user details with id. Expected: {}, Actual: {}", userId, actualUserId);
        Assert.assertEquals(actualUserId, userId, "User ID in response is incorrect");
    }
    
    @Then("the response time should be less than {int} milliseconds")
    public void theResponseTimeShouldBeLessThanMilliseconds(int maxResponseTime) {
        long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
        log.info("Verifying response time. Maximum allowed: {} ms, Actual: {} ms", maxResponseTime, responseTime);
        Assert.assertTrue(responseTime < maxResponseTime, 
                String.format("Response time %d ms exceeds maximum allowed %d ms", responseTime, maxResponseTime));
    }
    
    @Then("the response should contain the user id")
    public void theResponseShouldContainTheUserId() {
        String userId = response.jsonPath().getString("id");
        log.info("Verifying response contains user id. Actual: {}", userId);
        Assert.assertNotNull(userId, "User ID is missing in response");
    }
    
    @Then("the response should contain user details with name {string}")
    public void theResponseShouldContainUserDetailsWithName(String name) {
        String actualName = response.jsonPath().getString("name");
        log.info("Verifying response contains user details with name. Expected: {}, Actual: {}", name, actualName);
        Assert.assertEquals(actualName, name, "User name in response is incorrect");
    }
    
    @Then("the response should contain error message {string}")
    public void theResponseShouldContainErrorMessage(String errorMessage) {
        String actualErrorMessage = response.jsonPath().getString("message");
        log.info("Verifying response contains error message. Expected: {}, Actual: {}", errorMessage, actualErrorMessage);
        Assert.assertEquals(actualErrorMessage, errorMessage, "Error message in response is incorrect");
    }
    
    @Then("the response should contain {string} users")
    public void theResponseShouldContainUsers(String count) {
        int expectedCount = Integer.parseInt(count);
        int actualCount = response.jsonPath().getList("data").size();
        log.info("Verifying response contains expected number of users. Expected: {}, Actual: {}", expectedCount, actualCount);
        Assert.assertEquals(actualCount, expectedCount, "Number of users in response is incorrect");
    }
    
    @Then("the response should have pagination information")
    public void theResponseShouldHavePaginationInformation() {
        int page = response.jsonPath().getInt("page");
        int totalPages = response.jsonPath().getInt("total_pages");
        int perPage = response.jsonPath().getInt("per_page");
        int total = response.jsonPath().getInt("total");
        
        log.info("Verifying response has pagination information. Page: {}, Total Pages: {}, Per Page: {}, Total: {}", 
                page, totalPages, perPage, total);
        
        Assert.assertTrue(page > 0, "Page number should be positive");
        Assert.assertTrue(totalPages > 0, "Total pages should be positive");
        Assert.assertTrue(perPage > 0, "Per page should be positive");
        Assert.assertTrue(total > 0, "Total count should be positive");
    }
    
    // For demonstration purposes, these methods simulate REST API responses
    // In a real implementation, these would not be needed as the actual REST client would be used
    
    private Response simulateGetResponse(String endpoint) {
        // This is just a placeholder. In a real implementation, this would use the actual REST client.
        return null;
    }
    
    private Response simulatePostResponse(String endpoint, Map<String, Object> userData) {
        // This is just a placeholder. In a real implementation, this would use the actual REST client.
        return null;
    }
    
    private Response simulatePutResponse(String endpoint, Map<String, Object> userData) {
        // This is just a placeholder. In a real implementation, this would use the actual REST client.
        return null;
    }
    
    private Response simulateDeleteResponse(String endpoint) {
        // This is just a placeholder. In a real implementation, this would use the actual REST client.
        return null;
    }
}