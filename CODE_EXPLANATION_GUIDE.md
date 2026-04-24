# 💻 EduPulse: The Code Explanation Guide (For Viva)

This document is your "Technical Map." Use this to explain the source code to your examiners. It follows the **MVC (Model-View-Controller)** architecture.

---

## 📂 1. The Project Structure
Our project follows the industry-standard Spring Boot structure:
- **`model/`**: The Data (The Database Tables).
- **`repository/`**: The Queries (Talking to the Database).
- **`service/`**: The Brain (Business Logic & Formulas).
- **`controller/`**: The Doors (API Endpoints for the Frontend).
- **`config/`**: The Settings (Security & Web configuration).

---

## 🗂️ 2. The Core Code Files

### 🔵 The Models (The Data Structure)
- **`User.java`**: Represents a user. 
  - *Key feature:* The `@Builder.Default` for `xp` and `level`. This is where we defined that every new user starts at Level 1 with 0 XP.
- **`Quiz.java` & `Question.java`**: These have a "One-to-Many" relationship. One quiz can have many questions. We used `CascadeType.ALL` so that when a quiz is deleted, its questions are also deleted automatically.

### 🟢 The Repositories (Database Access)
- **`UserRepository.java`**: Extends `JpaRepository`. This allows us to find users by email or check if a user exists with just one line of code: `findByEmail(email)`.

### 🟡 The Services (The Business Logic)
- **`QuizService.java`**: This is the most important file.
  - *Grading Logic:* It iterates through questions, compares student answers to the correct ones, and calculates a percentage.
  - *Gamification:* It has the logic: `xpGained = score * 100`. It then updates the user's level based on the new XP total.
- **`FileStorageService.java`**: Handles file uploads. It generates a **UUID (Unique ID)** for every file so that if two students upload a file named `notes.pdf`, they don't overwrite each other.

### 🔴 The Controllers (The Entry Points)
- **`TeacherController.java`**: Contains all the actions a teacher can do (Upload resource, Create Quiz, Delete Quiz).
- **`ChatController.java`**: Handles both Group and Personal messages. It uses `@RequestParam MultipartFile` to allow sending files in chat.

---

## 🔒 3. Security & Configuration
- **`SecurityConfig.java`**: 
  - We use **Spring Security**. 
  - It defines that `/teacher/**` URLs require the `TEACHER` role, and `/student/**` requires the `STUDENT` role. 
  - It also uses `BCryptPasswordEncoder` to hash passwords, ensuring even we cannot see the users' real passwords in the database.

---

## 🎨 4. The Frontend (HTML/JS)
- **`index.html`**: The landing page. We used **CSS Animations** (`@keyframes animate-in`) to make the page fade in smoothly.
- **`student.html` & `teacher.html`**: These are "Single Page Applications" (SPA). We use JavaScript `fetch()` calls to update the data without ever refreshing the page.
- **`api.js`**: A helper library we wrote to store the common API URLs, making the code cleaner.

---

## 🛠️ 5. Deployment & DevOps
- **`Dockerfile`**: We used a **Multi-stage build**. 
  - Stage 1: Compiles the Java code using Gradle.
  - Stage 2: Creates a tiny, fast image with just the Java Runtime (JRE) to run the app.
- **`render.yaml`**: The blueprint for the cloud. It tells Render to use Java 21 and run the specific JAR file we built.

---

## 💡 How to explain a specific feature (Example: The Quiz)
*"Mam, when a student takes a quiz, the frontend sends a JSON object to the `StudentController`. The controller passes this to the `QuizService`, which calculates the score. Then, the Service updates the User model's XP, and JPA saves it to the H2 database. Finally, we trigger the JavaScript Confetti API on the frontend to celebrate the student's success."*
