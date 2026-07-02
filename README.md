# Casekaro Add-to-Cart Automation

This is my QA automation assignment for Casekaro (casekaro.com). It automates the flow of searching for an iPhone 16 Pro case, adding all 3 material options to the cart, and validating everything worked correctly.

## What it does

1. Opens the Casekaro website
2. Clicks on "Mobile Covers" from the top menu
3. Searches for "Apple" in the phone model search box
4. Checks that no other brands (like Samsung or OnePlus) show up in the results — this is a negative test, to make sure the search filter is actually working and not just showing everything
5. Searches again for "iPhone 16 Pro" and picks the exact match from the dropdown — I made sure this doesn't accidentally click "iPhone 16 Pro Max" instead, since that model also contains the text "iPhone 16 Pro"
6. Clicks "Choose Options" on the first product shown
7. Adds all 3 material variants — Hard, Soft, and Glass — to the cart one by one
8. Opens the cart and checks that all 3 items are actually there
9. Prints out the material, price, and product link for every item in the cart, so the results can be checked in the console

## Tech used

- **Java**
- **Playwright** — for controlling the browser and interacting with the website
- **Cucumber** — to write the test steps in plain English (Gherkin) so they're easy to read even for someone non-technical
- **JUnit 4** — runs the tests behind the scenes
- **Maven** — manages the project and all the dependencies

## Project structure

```
casekaro-automation-project/
├── pom.xml                                          → project setup and dependencies
└── src/test/
    ├── java/
    │   ├── runners/TestRunner.java                  → tells Cucumber where to find the tests
    │   └── stepdefinitions/CasekaroSteps.java        → the actual automation code
    └── resources/features/
        └── Assignment.feature                        → the test written in plain English
```

## How to run it

1. Open the project in IntelliJ (or any IDE that supports Maven)
2. Let Maven download the dependencies
3. Right-click `TestRunner.java` and run it
4. A browser window will open and you can watch it go through each step
5. Once it's done, check the console output at the bottom — that's where the cart item details (material, price, link) get printed
