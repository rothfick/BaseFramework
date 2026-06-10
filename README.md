# BaseFramework

Java BDD automation framework for UI, API, and contract-testing foundations.

BaseFramework is a Selenium, Cucumber, TestNG, RestAssured, Pact, and Allure based automation framework. It is designed as a reusable baseline for projects that need both browser-level functional testing and service-level validation in one consistent structure.

The repository is useful as a QA Automation portfolio project because it shows framework-level thinking rather than only isolated test scripts.

## What This Project Demonstrates

- BDD-style test organization with Cucumber feature files;
- UI automation with Selenium WebDriver;
- API testing with RestAssured;
- JSON schema validation support;
- contract-testing examples with Pact;
- TestNG execution;
- Allure reporting for Cucumber, TestNG, and REST calls;
- WebDriverManager-based browser setup;
- reusable page objects;
- reusable API client base class;
- centralized configuration through properties;
- screenshot utilities and listener hooks;
- Java 11 Maven project organization.

## Technology Stack

| Area | Tools |
|---|---|
| Language | Java 11 |
| Build | Maven |
| BDD | Cucumber 7 |
| Test runner | TestNG |
| UI automation | Selenium WebDriver 4 |
| Browser setup | WebDriverManager |
| API testing | RestAssured |
| Contract testing | Pact |
| Reporting | Allure |
| JSON | Jackson, JSON schema validator |
| Logging | Log4j |

## Repository Structure

```text
src/main/java/com/peliQAn/framework/
  api/
    BaseApiClient.java

  config/
    PropertyManager.java

  core/
    DriverFactory.java

  pact/
    PactBaseConsumer.java
    PactBaseProvider.java

  pages/
    BasePage.java
    LoginPage.java
    DashboardPage.java

  utils/
    ScreenshotUtils.java

src/test/java/com/peliQAn/framework/
  runners/
    CucumberTestRunner.java

  stepdefinitions/
    ApiSteps.java
    CommonSteps.java
    Hooks.java
    LoginSteps.java

  pact/examples/
    UserApiConsumerPactTest.java
    UserApiProviderPactTest.java

src/test/resources/
  config/config.properties
  features/api.feature
  features/login.feature
```

## Test Layers

### UI layer

The UI layer uses:

- `DriverFactory` for browser creation;
- `BasePage` for shared page-object behavior;
- `LoginPage` and `DashboardPage` as sample page objects;
- Cucumber steps for readable scenario definitions.

### API layer

The API layer uses:

- `BaseApiClient` as a reusable RestAssured entry point;
- `ApiSteps` for BDD-style endpoint validation;
- `api.feature` as an executable business-readable API scenario layer.

### Contract-testing layer

The Pact package includes:

- base consumer setup;
- base provider setup;
- example consumer/provider tests.

This shows the intended direction for teams that want to catch integration mismatches before deployment.

## Running Locally

Requirements:

- Java 11;
- Maven;
- browser available locally;
- target application/API endpoints configured in `src/test/resources/config/config.properties`.

Install dependencies:

```bash
mvn clean install
```

Run tests:

```bash
mvn test
```

Serve Allure report:

```bash
mvn allure:serve
```

## What To Review First

1. `pom.xml` for the complete automation stack.
2. `DriverFactory.java` for browser setup.
3. `BaseApiClient.java` for API testing foundation.
4. `PactBaseConsumer.java` and `PactBaseProvider.java` for contract testing.
5. `api.feature` and `login.feature` for BDD scenario style.
6. `CucumberTestRunner.java` for execution setup.

## Recruiter Signal

This project shows the skills expected from a QA Automation Engineer who can build and maintain a framework:

- UI + API automation;
- BDD collaboration style;
- contract-testing awareness;
- Java/Maven ecosystem;
- reporting and diagnostics;
- reusable architecture;
- practical test organization.

It is relevant for roles involving Java QA Automation, SDET, API testing, Cucumber, Selenium, RestAssured, Pact, and framework ownership.
