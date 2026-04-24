# Attribute: User Authentication & Authorization

This document explains how the security system in EduPulse is implemented.

## Tech Used
- **Java Spring Security**: The industry-standard framework for securing Spring-based applications.
- **BCrypt Password Encoder**: Used for hashing passwords securely before storing them in the database.
- **Session-Based Authentication**: Traditional web security using cookies and JSESSIONID.
- **Thymeleaf Spring Security Extras**: For conditional rendering in HTML based on user roles (e.g., showing the "Create Classroom" button only to teachers).

## How We Did It
1.  **Custom User Details Service**: We implemented a service that loads user data from our database using the `UserRepository`.
2.  **Role-Based Access Control (RBAC)**: We defined two primary roles: `ROLE_TEACHER` and `ROLE_STUDENT`.
3.  **Security Configuration**: In `SecurityConfig.java`, we configured:
    - **Public Access**: Login and Registration pages are accessible to everyone.
    - **Protected Routes**: Any path starting with `/teacher/**` requires the Teacher role, and `/student/**` requires the Student role.
    - **Login Flow**: Redirects users to their respective dashboards based on their role after a successful login.
    - **Logout**: Clears the session and redirects to the login page.
4.  **Registration Logic**: The `AuthController` handles new user creation, ensuring passwords are encoded using `BCrypt` for security compliance.
