package com.peliQAn.framework.api;

import com.peliQAn.framework.config.PropertyManager;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Base class for API clients with common methods
 */
@Slf4j
public abstract class BaseApiClient {
    protected final String baseUrl;
    protected final int timeout;
    protected final RequestSpecification requestSpec;
    protected final ResponseSpecification responseSpec;

    protected BaseApiClient() {
        PropertyManager propertyManager = PropertyManager.getInstance();
        this.baseUrl = propertyManager.getProperty("api.baseUrl");
        this.timeout = propertyManager.getIntProperty("api.timeout", 30);

        // Set up base request specification
        this.requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();

        // Set up base response specification
        this.responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
        
        log.info("Initialized API client with base URL: {}", baseUrl);
    }

    /**
     * Send GET request to endpoint
     *
     * @param endpoint API endpoint path
     * @return Response object
     */
    @Step("Send GET request to {endpoint}")
    protected Response get(String endpoint) {
        log.info("Sending GET request to: {}{}", baseUrl, endpoint);
        return RestAssured.given()
                .spec(requestSpec)
                .when()
                .get(endpoint)
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }

    /**
     * Send GET request to endpoint with query parameters
     *
     * @param endpoint API endpoint path
     * @param queryParams Query parameters
     * @return Response object
     */
    @Step("Send GET request to {endpoint} with query parameters")
    protected Response get(String endpoint, Map<String, Object> queryParams) {
        log.info("Sending GET request to: {}{} with query parameters: {}", baseUrl, endpoint, queryParams);
        return RestAssured.given()
                .spec(requestSpec)
                .queryParams(queryParams)
                .when()
                .get(endpoint)
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }

    /**
     * Send POST request to endpoint with request body
     *
     * @param endpoint API endpoint path
     * @param requestBody Request body object
     * @return Response object
     */
    @Step("Send POST request to {endpoint}")
    protected Response post(String endpoint, Object requestBody) {
        log.info("Sending POST request to: {}{}", baseUrl, endpoint);
        return RestAssured.given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post(endpoint)
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }

    /**
     * Send PUT request to endpoint with request body
     *
     * @param endpoint API endpoint path
     * @param requestBody Request body object
     * @return Response object
     */
    @Step("Send PUT request to {endpoint}")
    protected Response put(String endpoint, Object requestBody) {
        log.info("Sending PUT request to: {}{}", baseUrl, endpoint);
        return RestAssured.given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .put(endpoint)
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }

    /**
     * Send PATCH request to endpoint with request body
     *
     * @param endpoint API endpoint path
     * @param requestBody Request body object
     * @return Response object
     */
    @Step("Send PATCH request to {endpoint}")
    protected Response patch(String endpoint, Object requestBody) {
        log.info("Sending PATCH request to: {}{}", baseUrl, endpoint);
        return RestAssured.given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .patch(endpoint)
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }

    /**
     * Send DELETE request to endpoint
     *
     * @param endpoint API endpoint path
     * @return Response object
     */
    @Step("Send DELETE request to {endpoint}")
    protected Response delete(String endpoint) {
        log.info("Sending DELETE request to: {}{}", baseUrl, endpoint);
        return RestAssured.given()
                .spec(requestSpec)
                .when()
                .delete(endpoint)
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }

    /**
     * Create custom request specification with authentication
     *
     * @param token Authentication token
     * @return RequestSpecification
     */
    protected RequestSpecification getAuthSpec(String token) {
        return RestAssured.given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token);
    }

    /**
     * Get response time in specified time unit
     *
     * @param response Response object
     * @param timeUnit Time unit
     * @return Response time in specified time unit
     */
    protected long getResponseTime(Response response, TimeUnit timeUnit) {
        return response.getTimeIn(timeUnit);
    }

    /**
     * Validate response against JSON schema
     *
     * @param response Response object
     * @param schemaPath Path to JSON schema file
     */
    @Step("Validate response against JSON schema")
    protected void validateResponseSchema(Response response, String schemaPath) {
        response.then().assertThat().body(io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
        log.info("Response validated against schema: {}", schemaPath);
    }
}