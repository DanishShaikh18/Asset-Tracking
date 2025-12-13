# Asset Tracking System

A CLI-based internal asset tracking system built with Spring Boot and PostgreSQL.
This project helps organizations track assets, assign them to users, return assets, and maintain full assignment history.

## Features

- Add and view company assets
- Add users
- Assign assets to users
- Return assets
- View complete assignment history
- CLI-based (no frontend, no REST APIs)

## Prerequisites

- Java 17
- PostgreSQL ( pgadmin )

## Setup Instructions

### 1. Create PostgreSQL Database
Create this DB manually in your PostgreSQL ( pgadmin )
<br>
```
CREATE DATABASE asset_db;
```

### 2. Configure Database Credentials

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

Replace `YOUR_USERNAME` and `YOUR_PASSWORD` with your PostgreSQL credentials.

### 3. Build the Project

```bash
./mvnw spring-boot:run
```

### 4. Run the Application

```bash
./mvnw spring-boot:run
```

Or run directly:

```bash
java -jar target/asset-tracking-1.0.0.jar
```

## Usage

The application will display a menu with the following options:

```
========================================
              MAIN MENU
========================================
1. Add Asset
2. View All Assets
3. Add User
4. Assign Asset
5. Return Asset
6. View Assignment History
0. Exit
========================================
```

### Example Workflow

1. **Add an Asset**: Choose option 1, enter asset name and type
2. **Add a User**: Choose option 3, enter user name and email
3. **Assign Asset**: Choose option 4, enter asset ID and user ID
4. **Return Asset**: Choose option 5, enter asset ID
5. **View History**: Choose option 6 to see all assignments

## Business Rules

- Assets can only be assigned if their status is AVAILABLE
- When assigned, asset status changes to ASSIGNED
- When returned, asset status changes back to AVAILABLE
- All assignment history is preserved
- returnedAt = NULL indicates asset is currently assigned

## Database Schema

The application automatically creates three tables:

### assets
- id (Primary Key)
- name
- type
- status (AVAILABLE/ASSIGNED)

### users
- id (Primary Key)
- name
- email (unique)

### assignments
- id (Primary Key)
- asset_id (Foreign Key)
- user_id (Foreign Key)
- assigned_at
- returned_at (nullable)

## Technologies Used

- Spring Boot 3.x.x ( any version starting with 3 )
- Spring Data JPA
- PostgreSQL
- Java 17