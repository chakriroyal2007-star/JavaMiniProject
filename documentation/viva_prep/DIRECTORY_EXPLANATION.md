# Project Directory Structure - EduPulse

This document provides a detailed explanation of the project structure for the EduPulse Classroom Management System.

## Root Directory
- **.gradle/**: Contains Gradle-specific build configuration and cache files.
- **.vscode/**: Configuration for Visual Studio Code (workspace settings, extensions).
- **bin/**: Compiled binary files (output of the build process).
- **build/**: The main build output directory where the final JAR file and compiled classes reside.
- **db/**: Contains database-related scripts or local database storage files.
- **gradle/**: Contains the Gradle wrapper files that ensure consistent build environments across different machines.
- **src/**: The source code of the application.
- **storage/**: A local storage directory for uploaded files (resources, assignments, profile pictures).
- **Dockerfile**: Instructions for building a Docker image of the application for containerized deployment.
- **build.gradle**: The core build configuration file defining dependencies, plugins, and project metadata.
- **render.yaml**: Configuration file for deploying the application to Render (cloud platform).
- **system.properties**: Specifies the Java version for deployment environments.

## Source Code (`src/main/java/com/classroom/cse_a_classroom`)
- **config/**: Security and application configurations (e.g., `SecurityConfig.java`).
- **controller/**: The "Entry Points" of the application. Handles HTTP requests and maps them to service methods.
- **service/**: Contains the "Business Logic". This layer processes data, handles calculations, and coordinates between controllers and repositories.
- **model/**: Defines the "Data Entities". These classes represent database tables (User, Classroom, Resource, etc.).
- **repository/**: The "Data Access Layer". Uses Spring Data JPA to interact with the database.
- **dto/**: "Data Transfer Objects" used to pass data between layers without exposing the full database models.
- **ui/**: Helper classes for UI-specific logic or terminal-based debugging components.

## Resources (`src/main/resources`)
- **static/**: Contains static assets like CSS, JavaScript, and Images.
- **templates/**: Contains Thymeleaf HTML templates that are dynamically rendered by the Spring Boot server.
- **application.properties**: The main configuration file for the Spring Boot application (database connection, server port, etc.).
