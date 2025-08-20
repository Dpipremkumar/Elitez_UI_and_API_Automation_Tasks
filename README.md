# ElitezAutomationTask
**🚀 Test Automation Framework – Saucedemo & GoREST**
**📌 Project Overview**

This repository contains a hybrid automation framework built using Cucumber (BDD) with Page Object Model (POM) and Object Repository principles.
The project covers both UI automation (Saucedemo) and API automation (GoREST), demonstrating how API calls can be validated independently from UI tests.

**🛠️ Tech Stack**

  Language: Java

  UI Automation: Selenium WebDriver + Cucumber + TestNG

  API Automation: Rest Assured + Cucumber

  Build Tool: Maven

  Logging & Reporting: Log4j, Cucumber HTML Reports

  Design Pattern: Page Object Model (POM) + Object Repository

**📂 Project Structure**
src
 └── main
     ├── java
     │    ├── pages           # Page Objects for UI
     │    └── utils           # Helpers (API Utils, Config, Logging)
src
 └── test
     ├── java
     │    ├── runner          # Cucumber Test Runners
     │    ├── stepdefinitions # Step Definitions (UI + API)
     └── resources
          └── features        # Gherkin Feature Files
               ├── saucedemo.feature   # UI Scenarios
               └── gorest.feature      # API Scenarios

**✅ Scenarios Covered**
🔹 UI Automation – Saucedemo

  Sort Product

  Verify sorting by Name (A→Z ).

  Checkout Process

  Add product(s) to cart.

  Proceed to checkout.

  Fill customer details and complete the purchase.

**🔹 API Automation – RestAPI**

  Create a new user

  Get user details

  Update user details

**⚙️ How to Run the Tests**
📍 Prerequisites

Install Java (>= 11)

Install Maven

Clone this repository

  Go to the CucumberSuite.java file and run it from the src/test/runners folder.
