# nextGenI - API Automation Tests

Cucumber + RestAssured API test automation for the automation-backend API.

## Prerequisites

- Java 11+
- Maven 3.6+

## Test Scenarios

1. **Case 1**: Login with invalid credentials → Validate "Invalid credentials" message
2. **Case 2**: Create agency without auth → Validate "Please authenticate" message  
3. **Case 3**: Login → Create agency with token → Get by ID → Validate data matches

## Running Tests

```
mvn clean test
```

### Customize Name and Email (Case 2 & 3)

Edit `src/test/resources/features/api.feature` and replace:
- `Name` and `email` is passed from the feature file as strings. These can later be 
   variablize from test data file.
- Case 3 uses a unique timestamped email to avoid "Email already exists" errors


```
src/test/
├── java/com/nextgeni/api/
│   ├── runner/CucumberTestRunner.java
│   ├── steps/ApiStepDefinitions.java
│   └── utils/ApiHelper.java
└── resources/
    ├── features/api.feature
    └── cucumber.properties
```


## Reports
- Path of the cucumber report
HTML report: `target/cucumber-reports/cucumber.html`