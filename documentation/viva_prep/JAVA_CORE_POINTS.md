# Java Core Concepts in EduPulse

This document explains the key Java programming concepts and frameworks we used in the project.

## 1. Spring Boot (The "sntbg" / Spring Framework)
We used **Spring Boot** as the foundation. It simplifies Java development by "importing" everything we need to start a web server and connect to a database automatically.
- **Why we used it:** It eliminates the need for complex XML configurations and provides a built-in web server (Tomcat).

## 2. Annotations (The "@" Symbols)
Annotations are "labels" that tell the Java compiler or Spring framework what a class or method should do.
- **`@Entity`**: Tells Java that this class represents a table in our database.
- **`@RestController` / `@Controller`**: Tells Java that this class will handle web requests (URLs).
- **`@Service`**: Marks a class as containing the "brain" or business logic of the app.
- **`@Repository`**: Handles the database connection logic.
- **`@Autowired`**: This is **Dependency Injection**. It tells Spring to automatically create an object of a class and "inject" it where it's needed.

## 3. Java Persistence API (JPA) & Hibernate
Instead of writing long SQL queries for every small action, we used JPA.
- **Main Point:** It maps Java Objects (like a `User` class) directly to Database Tables.
- **Benefit:** We can save a user to the database simply by calling `repository.save(user)` instead of writing `INSERT INTO users...`.

## 4. Collections Framework
We used Java's built-in collections to manage groups of data.
- **`List<T>`**: Used to store lists of students in a classroom, questions in a quiz, or messages in a chat.
- **`Set<T>`**: Used when we want to ensure no duplicates (e.g., a set of unique roles for a user).

## 5. Object-Oriented Relationships
We used Java's OOP principles to model real-world classroom relationships:
- **One-To-Many**: One Classroom can have Many Resources.
- **Many-To-Many**: Many Students can be in Many Classrooms.
- **Inheritance**: (If applicable) used for role management.

## 6. MultipartFile & Streams
- **MultipartFile**: A special Java class used to handle file uploads (PDFs, Images).
- **Streams**: Used for processing data efficiently (e.g., filtering a list of classrooms to find one by its join code).

## 7. BCrypt Password Encoding
Security is a main point. We used the `BCrypt` algorithm to hash passwords, which is a standard Java security practice.
