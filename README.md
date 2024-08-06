# Money Transfer Application

Welcome to the **Money Transfer Application** is a comprehensive banking system designed to manage user accounts, handle internal and external transfers, and support various financial operations. This application is currently in the process of transitioning to a microservices architecture.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [API Documentation](#api-documentation)
- [Entity-Relationship Diagram](#entity-relationship-diagram)
- [Installation and Setup](#installation-and-setup)
- [Running the Application](#running-the-application)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Features

- **User Accounts and Profiles**: Create and manage user profiles and link them to bank accounts.
- **Internal and External Transfers**: Transfer funds between accounts within the app or to external accounts.
- **Account Management**: View account balances, transaction history, and manage personal information.
- **Currency Conversion**: Convert between supported currencies for international transfers.
- **Favorite Recipients**: Add and manage favorite recipients for quick and easy transfers.
- Transactions: View transaction history with pagination and sorting options.
- Authentication: Secure login, token-based authentication, and logout functionality.
- Validation and Error Handling: Comprehensive validation and error handling.


## Technologies Used

- **Java & Kotlin**: Primary programming languages used for backend development.
- **Spring Boot 3**: Framework for building the microservices.
- **Spring Security 6**: Provides authentication and authorization mechanisms.
- **JWT Token**: For secure token-based authentication.
- **Java Mail Sender**: For sending emails.
- **Swagger**: API documentation and testing.
- **Caching**: Improve performance with caching mechanisms.
- **PostgreSQL**: Relational database for data storage.
- **Zipkin**: Distributed tracing for monitoring and troubleshooting.
- **Heroku**: Deployment platform for the application and PostgreSQL database.

## API Documentation

- [Swagger API Documentation](https://money-transfer-430a47bbe633.herokuapp.com/swagger-ui/index.html#/authentication-controller/register)

## Entity-Relationship Diagram

- [ERD Diagram](https://github.com/MuhammedHussein3/money-transfer-bm/blob/master/Money-Transfer-Erd.jpg)

## Installation and Setup

To set up the Money Transfer Application locally, follow these steps:

1. **Clone the repository:**

    ```bash
    git clone https://github.com/your-username/money-transfer-application.git
    ```

2. **Navigate to the project directory:**

    ```bash
    cd money-transfer-application
    ```

3. **Install dependencies:**

    Ensure you have [Java](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html), [Kotlin](https://kotlinlang.org/docs/command-line.html), and [Maven](https://maven.apache.org/download.cgi) installed. Then, run:

    ```bash
    mvn install
    ```

4. **Configure application properties:**

    Edit `src/main/resources/application.properties` to set up your database and other configuration settings.

5. **Run the application:**

    ```bash
    mvn spring-boot:run
    ```
## Deployment
The application is deployed on Heroku

## Running the Application

The application will be available at [https://money-transfer-430a47bbe633.herokuapp.com/](https://money-transfer-430a47bbe633.herokuapp.com/). You can access various endpoints for managing accounts, transactions, and more.

## Contributing

Contributions are welcome! If you would like to contribute to the project, please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Make your changes.
4. Commit your changes (`git commit -am 'Add new feature'`).
5. Push to the branch (`git push origin feature/your-feature`).
6. Create a new Pull Request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

Muhammad Hussein - [LinkedIn](https://linkedin.com/in/muhammad-hussein-a260a32a1) - [Email](muhammadhussein2312@gmail.com)

Omar Hossam - [LinkedIn](https://www.linkedin.com/me?trk=p_mwlite_feed-secondary_nav)

Project Link: (https://github.com/MuhammedHussein3/money-transfer-bm)

