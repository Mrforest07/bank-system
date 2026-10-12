# JDBC Bank Management System

A console-based Bank Management System built with Java, JDBC, MySQL, and Maven. This project demonstrates database connectivity, CRUD operations, transaction management, and batch processing using core Java and JDBC.

## Features

- Create bank accounts with automatically generated account IDs
- View all accounts
- Deposit money into an account
- Transfer money between accounts
- Delete accounts
- Use prepared statements to execute parameterized SQL queries
- Manage database transactions with commit and rollback
- Prevent transfers when the sender has insufficient funds
- Use JDBC batch processing for inserting multiple accounts
- Keep database credentials outside the source code using environment variables

## Tech Stack

- **Java:** 27
- **JDBC:** Java Database Connectivity
- **Database:** MySQL 8.0
- **Build Tool:** Apache Maven
- **JDBC Driver:** MySQL Connector/J 9.4.0
- **IDE:** IntelliJ IDEA

## Prerequisites

Before running the project, install:

- JDK 27
- MySQL Server 8.0 or a compatible version
- Apache Maven, or an IDE with Maven support

## Database Setup

1. Start your MySQL server.
2. Open MySQL Workbench or another MySQL client.
3. Create the database:

   ```sql
   CREATE DATABASE bankdb;
   ```

4. Select the database and create the accounts table:

   ```sql
   USE bankdb;

   CREATE TABLE accounts (
       account_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
       holder_name VARCHAR(100) NOT NULL,
       balance DECIMAL(12,2) NOT NULL DEFAULT 0.00
   );
   ```

## Environment Variables

Configure the following environment variables in your IDE or terminal. Replace the example values with your own local database settings.

| Variable | Purpose | Example |
|---|---|---|
| `DB_URL` | MySQL connection URL | `jdbc:mysql://localhost:3306/bankdb` |
| `DB_USER` | MySQL username | `your_mysql_username` |
| `DB_PASSWORD` | MySQL password | Set your own password locally |

**Security:** Never commit your real database password, API keys, or other secrets to GitHub.

## How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/Mrforest07/bank-system.git
   cd bank-system
   ```

2. Open the project in IntelliJ IDEA or another Java IDE.
3. Configure the three environment variables listed above in the run configuration.
4. Reload the Maven project so that the MySQL JDBC dependency is downloaded.
5. Run `org.example.Main`.

Alternatively, if Maven is installed and the environment variables are configured in your terminal:

```bash
mvn clean package
```

Then run the `Main` class from your IDE.

## Application Menu

The console application provides these operations:

1. Create Account
2. View All Accounts
3. Deposit Money
4. Delete Account
5. Transfer Money
6. Exit

## Concepts Demonstrated

- JDBC connection management
- `PreparedStatement` and parameterized SQL
- CRUD operations
- Generated keys
- `BigDecimal` for monetary values
- Transactions with commit and rollback
- Transaction-safe money transfers
- Batch inserts using `addBatch()` and `executeBatch()`
- Resource management with try-with-resources
- Separation of responsibilities between the model, DAO, service, and main application

## Project Structure

```text
bank-system/
├── pom.xml
├── .gitignore
└── src/
    └── main/
        └── java/
            └── org/
                └── example/
                    ├── Account.java
                    ├── AccountDAO.java
                    ├── BankService.java
                    └── Main.java
```

## Future Improvements

- Add JUnit unit and integration tests
- Introduce connection pooling
- Add logging and improved exception handling
- Add account authentication and authorization
- Build a REST API using Spring Boot
- Add a web interface

## Author

**Mrforest07**

GitHub: [Mrforest07](https://github.com/Mrforest07)

---

*This is an educational project for learning Java backend development and database programming. It is not intended for handling real financial transactions.*
