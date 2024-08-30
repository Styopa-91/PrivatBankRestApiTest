# PrivatBank REST API Test

## Overview

This project is a Spring Boot application designed to test various functionalities related to user authentication and card management, including CRUD operations on cards and categories.

## Features

- **User Authentication:** Test login functionality with basic authentication.
- **Card Management:** CRUD operations on card entities.
- **Category Management:** CRUD operations on category entities, including associations with cards.

## Getting Started

### Prerequisites

- Java 11 or later
- Maven 
- H2 database (used for testing)

### Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/styopa-91/privatbank-rest-api-test.git
   cd privatbank-rest-api-test
2. **Install Dependencies:**

   ```bash
   mvn install
3. **To run the application locally:**

   ```bash
   mvn spring-boot:run
4. **To run all tests:**

   ```bash
   mvn test

### API Endpoints

    GET /api/cards
    POST /api/cards
    PUT /api/cards
    DELETE /api/cards

    GET /api/categories
    POST /api/categories
    PUT /api/categories
    DELETE /api/categories

### Headers

    Authorization: Basic base64encoded(username:password)


