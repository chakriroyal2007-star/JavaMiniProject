# Multi-Threading in EduPulse

This document explains how multi-threading works in our Spring Boot application.

## 1. Implicit Multi-threading (The Web Server)
Even though we didn't write `new Thread()` in our code, the application is **highly multi-threaded** by nature.
- **How it works:** We use an embedded **Tomcat Server**. When 10 different students log in at the same time, Tomcat assigns a **separate Thread** to each student.
- **Benefit:** This allows the application to handle multiple requests "concurrently" (at the same time) without making one student wait for another to finish.

## 2. Database Connection Pooling
We use **HikariCP** (the default connection pool in Spring Boot).
- **How it works:** It maintains a "pool" of database connections. Multiple threads (user requests) can borrow a connection, run a query, and return it.
- **Why it matters:** Without multi-threading, only one user could talk to the database at a time, making the app very slow.

## 3. Stateless Controllers
Because the app is multi-threaded, we designed our **Controllers and Services to be Stateless**.
- **The Concept:** We don't store student-specific data inside variables in the Controller. Instead, we pass data through method arguments. 
- **Why:** This prevents "Race Conditions" where one student's data might accidentally overwrite another student's data if they both click "Submit" at the same second.

## 4. Asynchronous Processes (Optional Explanation)
If asked about background tasks:
- We primarily use **Synchronous** processing for reliability (e.g., waiting for the quiz score to calculate before showing the result).
- However, the underlying framework is built to support **Asynchronous** tasks (like sending emails or large file processing) using the `@Async` annotation if needed for future scaling.
