# Asset Tracking System - Web Application

A full-stack web-based internal asset tracking system built with Spring Boot, REST APIs, and vanilla JavaScript.

## Features

- **Dashboard**: Real-time statistics for assets and users
- **Asset Management**: Add, view, assign, and return assets
- **User Management**: Add and view users
- **Assignment History**: Complete tracking of all asset assignments
- **Search Functionality**: Filter assets by name or type
- **Status Badges**: Visual indicators (Green = AVAILABLE, Red = ASSIGNED)
- **Smart Validation**: Buttons disabled based on asset status

## Technology Stack

### Backend
- Java 17
- Spring Boot 3.1.5
- Spring Data JPA
- PostgreSQL
- REST APIs

### Frontend
- HTML5
- CSS3
- Vanilla JavaScript
- Fetch API

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+

## Setup Instructions

### 1. Create PostgreSQL Database

```sql
CREATE DATABASE asset_db;
```

### 2. Configure Database Credentials

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

### 3. Build the Project

```bash
./mvnw clean install
```

### 4. Run the Application

```bash
./mvnw spring-boot:run
```

The application will start on **http://localhost:8080**

## Accessing the Application

Open your browser and navigate to:
- **Dashboard**: http://localhost:8080/index.html
- **Assets**: http://localhost:8080/assets.html
- **Users**: http://localhost:8080/users.html
- **History**: http://localhost:8080/history.html

## REST API Endpoints

### Assets
- `GET /api/assets` - Get all assets
- `POST /api/assets` - Add new asset
- `GET /api/assets/count` - Get total assets count
- `GET /api/assets/available/count` - Get available assets count
- `GET /api/assets/assigned/count` - Get assigned assets count

### Users
- `GET /api/users` - Get all users
- `POST /api/users` - Add new user
- `GET /api/users/count` - Get total users count

### Assignments
- `POST /api/assign` - Assign asset to user
- `POST /api/return` - Return asset
- `GET /api/history` - Get assignment history

## API Request Examples

### Add Asset
```json
POST /api/assets
{
  "name": "Dell Laptop XPS 15",
  "type": "Laptop"
}
```

### Add User
```json
POST /api/users
{
  "name": "John Doe",
  "email": "john.doe@company.com"
}
```

### Assign Asset
```json
POST /api/assign
{
  "assetId": 1,
  "userId": 1
}
```

### Return Asset
```json
POST /api/return
{
  "assetId": 1
}
```

## Business Rules

- Assets can only be assigned if status is AVAILABLE
- When assigned, asset status automatically changes to ASSIGNED
- When returned, asset status automatically changes to AVAILABLE
- All assignment history is preserved (no deletions)
- `returnedAt = NULL` indicates asset is currently assigned

## Project Structure

```
asset-tracking/
├── src/
│   └── main/
│       ├── java/com/example/assettracking/
│       │   ├── controller/
│       │   │   ├── AssetController.java
│       │   │   ├── UserController.java
│       │   │   └── AssignmentController.java
│       │   ├── service/
│       │   │   ├── AssetService.java
│       │   │   ├── UserService.java
│       │   │   └── AssignmentService.java
│       │   ├── repository/
│       │   │   ├── AssetRepository.java
│       │   │   ├── UserRepository.java
│       │   │   └── AssignmentRepository.java
│       │   ├── model/
│       │   │   ├── Asset.java
│       │   │   ├── User.java
│       │   │   └── Assignment.java
│       │   └── AssetTrackingApplication.java
│       └── resources/
│           ├── static/
│           │   ├── index.html
│           │   ├── assets.html
│           │   ├── users.html
│           │   ├── history.html
│           │   ├── css/
│           │   │   └── style.css
│           │   └── js/
│           │       └── app.js
│           └── application.properties
└── pom.xml
```

## Features Walkthrough

### Dashboard
- View real-time counts of total, available, and assigned assets
- View total users
- Quick navigation to all sections

### Asset Management
- Add new assets with name and type
- View all assets in a table
- Search assets by name or type (client-side filtering)
- Assign button is disabled for already assigned assets
- Return button is disabled for available assets
- Status badges show asset availability (green/red)

### User Management
- Add new users with name and email
- View all users in a table
- Email validation (unique constraint)

### Assignment History
- View complete history of all assignments
- Shows assigned and returned dates
- Active assignments clearly marked
- Returned assignments show return timestamp

## Troubleshooting

### Application won't start
- Check if PostgreSQL is running
- Verify database credentials in `application.properties`
- Ensure database `asset_db` exists

### Cannot connect to database
- Verify PostgreSQL is running on port 5432
- Check username and password
- Ensure PostgreSQL accepts connections from localhost

### Frontend not loading
- Ensure application is running on http://localhost:8080
- Check browser console for errors
- Clear browser cache

## Development

To make changes:
1. Backend changes: Modify Java files and restart application
2. Frontend changes: Modify HTML/CSS/JS files and refresh browser (no restart needed)

## License

This project is deployed now and will have frequent updates 
