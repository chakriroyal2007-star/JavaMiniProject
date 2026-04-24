# EduPulse Tech Stack Overview

This file summarizes the technologies used throughout the EduPulse project.

## Backend
- **Java 17**: The core programming language.
- **Spring Boot 3.x**: The framework used to build the standalone, production-grade application.
- **Spring Data JPA**: For object-relational mapping and database interaction.
- **Spring Security**: For handling authentication (Login/Logout) and authorization (Teacher vs. Student).

## Frontend
- **HTML5 & CSS3**: For the structure and premium design of the web pages.
- **JavaScript (Vanilla)**: For client-side interactivity, AJAX requests, and UI animations.
- **Thymeleaf**: The server-side template engine that generates HTML from Java data.
- **Google Fonts (Inter/Outfit)**: For modern and professional typography.

## Database & Storage
- **H2 Database**: Used during development for lightweight, file-based data storage.
- **Local FileSystem**: Used for storing physical assets like PDFs and images.

## Deployment & Build
- **Gradle**: The build automation tool for dependency management and packaging.
- **Docker**: For containerizing the application to ensure it runs the same everywhere.
- **Render**: The cloud platform used for hosting the live application.
