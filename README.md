# PeliQAn Test Automation Framework

A comprehensive BDD test automation framework for web and API testing, built using Java, Selenium, RestAssured, and Cucumber.

## Features

- BDD-style tests using Cucumber
- Page Object Model design pattern for UI tests
- REST API testing with RestAssured
- Contract testing with Pact
- Parallel test execution with TestNG
- Comprehensive reporting with Allure
- Screenshot capture on test failure
- Logging with Log4j2
- Cross-browser testing
- Support for headless mode
- Centralized configuration management

## Prerequisites

- Java 11 or higher
- Maven
- Chrome, Firefox, Edge, or Safari browser

## Project Structure

```
framework/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── peliQAn/
│   │   │           └── framework/
│   │   │               ├── api/             # API client classes
│   │   │               ├── config/          # Configuration classes
│   │   │               ├── core/            # Core framework classes
│   │   │               ├── pages/           # Page Object classes
│   │   │               ├── pact/            # Pact contract testing classes
│   │   │               └── utils/           # Utility classes
│   │   └── resources/                       # Resources like log4j2.xml
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── peliQAn/
│       │           └── framework/
│       │               ├── runners/         # TestNG runners
│       │               └── stepdefinitions/ # Cucumber step definitions
│       └── resources/
│           ├── features/                    # Cucumber feature files
│           ├── data/                        # Test data
│           └── config/                      # Test configuration
└── pom.xml                                  # Maven build file
```

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/yourusername/peliQAn-framework.git
cd peliQAn-framework
```

### Install Dependencies

```bash
mvn clean install -DskipTests
```

### Run Tests

Run all tests:
```bash
mvn clean test
```

Run specific test tags:
```bash
mvn clean test -Dcucumber.filter.tags="@smoke"
```

Run tests in parallel:
```bash
mvn clean test -DthreadCount=4
```

### Generate Reports

Generate Allure reports:
```bash
mvn allure:report
```

View Allure reports:
```bash
mvn allure:serve
```

## Configuration

Configuration is managed through `src/test/resources/config/config.properties`. Key properties include:

- `browser`: Target browser (chrome, firefox, edge, safari)
- `headless`: Run in headless mode (true, false)
- `app.baseUrl`: Base URL of the application under test
- `api.baseUrl`: Base URL for API tests
- `api.timeout`: Timeout for API requests in seconds
- `screenshot.on.failure`: Take screenshots on test failure (true, false)

## Writing Tests

### UI Tests

1. Create a Page Object class in `src/main/java/com/peliQAn/framework/pages/`
2. Create a feature file in `src/test/resources/features/`
3. Implement step definitions in `src/test/java/com/peliQAn/framework/stepdefinitions/`

### API Tests

1. Create a feature file in `src/test/resources/features/`
2. Implement step definitions in `src/test/java/com/peliQAn/framework/stepdefinitions/`
3. Use the BaseApiClient for API interactions

### Contract Tests with Pact

1. Extend PactBaseConsumer for consumer tests
2. Extend PactBaseProvider for provider tests

## Best Practices

- Follow the Page Object Model pattern for UI tests
- Keep step definitions small and focused
- Use feature files to describe business scenarios
- Prefer composition over inheritance
- Write tests that are independent of each other
- Use appropriate tags to categorize tests
- Add descriptive comments and documentation

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contributors

- Initial framework by PeliQAn Team