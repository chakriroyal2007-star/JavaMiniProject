# 🏛️ EduPulse: Comprehensive Project Architecture & Flow

This document provides a detailed breakdown of the **EduPulse** Classroom Management System, covering its attributes, implementation details, and end-to-end system flow.

---

## 🏗️ 1. Project Modules & Attributes

The system is divided into five core modules. Each module handles a specific part of the user experience.

### A. Authentication & Security (The Gatekeeper)
- **Attributes:** `UserRegistration`, `SecureLogin`, `BCryptHashing`, `SessionManagement`.
- **How we did it:** Used **Spring Security** to create a custom login flow. All passwords are encrypted using **BCrypt** before being saved to the database. We defined two distinct roles: `ROLE_TEACHER` and `ROLE_STUDENT`.

### B. Classroom & Resource Module (The Library)
- **Attributes:** `ClassroomCreation`, `JoinCodeGeneration` (8-char unique), `FileUpload` (Multi-part), `DocumentSharing`.
- **How we did it:** Teachers can create rooms that generate a unique code. Resources are uploaded to a `storage/` folder and mapped via a database table that links the file to the classroom.

### C. Interactive Quiz Module (The Challenge)
- **Attributes:** `TimedQuizzes`, `PasswordProtection`, `AutoGrading`, `ConfettiRewards`.
- **How we did it:** Quizzes are stored as JSON-like structures in the database. When a student submits, the `QuizService` compares their answers with the correct ones in real-time and calculates a percentage score.

### D. Gamification Engine (The Heart)
- **Attributes:** `XPTracking`, `LevelingSystem`, `AchievementTitles`.
- **How we did it:** Every time a student passes a quiz, the system awards **+500 XP**. A custom formula in the User entity (`level = (xp / 1000) + 1`) automatically updates the student's rank and unlocks new titles like "Scholar" or "Grandmaster".

### E. Discussion Hub (The Social Hub)
- **Attributes:** `GroupChat`, `DirectMessaging`, `FileAttachments`, `RealTimePolling`.
- **How we did it:** We used **RESTful APIs** and **JavaScript Polling**. Every 3 seconds, the student's browser asks the server, "Are there new messages?". This ensures the chat feels live without needing complex server setups.

---

## 🔄 2. Step-by-Step System Flow

Here is exactly how a user interacts with the system:

### Phase 1: Onboarding
1.  **Landing Page:** User arrives at the beautiful landing page with the portrait of Amma.
2.  **Registration:** User signs up and chooses a role (Teacher or Student).
3.  **Login:** User enters credentials; Spring Security validates them and redirects them to the correct dashboard.

### Phase 2: Classroom Setup (Teacher)
1.  **Creation:** Teacher clicks "Create Classroom" and gives it a name.
2.  **Invite:** The system generates a code (e.g., `ABC-123-XY`). The teacher shares this with students.
3.  **Content:** Teacher uploads a PDF or creates a Quiz with a secret password.

### Phase 3: Learning & Interaction (Student)
1.  **Join:** Student enters the classroom code to enroll.
2.  **Engage:** Student downloads resources or starts a quiz.
3.  **Quiz Flow:** 
    - Student enters the access key.
    - Countdown timer starts.
    - Student submits -> Confetti celebration triggers -> **XP is rewarded**.

### Phase 4: Feedback Loop
1.  **Leaderboard:** Both see a live leaderboard showing who has the most XP.
2.  **Chat:** If someone is stuck, they message the group in the Discussion Hub.
3.  **Level Up:** As the student does more work, their XP bar grows, and their title changes on their profile.

---

## 🛠️ 3. Technical Implementation (The "How")

### The Database (H2)
- We used an **embedded H2 database**. It stores everything in a local file (`classroom_db.mv.db`), so data is not lost when the app restarts.
- **JPA (Java Persistence API)** acts as the bridge, converting Java Objects into SQL tables automatically.

### The Frontend (Aesthetics)
- **Vanilla JS & Bootstrap 5:** We avoided heavy frameworks to keep the site fast. 
- **Canvas Confetti:** A lightweight API to give students a "hit of dopamine" after finishing a task.
- **Polling:** `setInterval()` in JavaScript keeps the chat and dashboard updated in real-time.

### The Backend (Spring Boot 4.0 & Java 25)
- **Controllers:** Handle URLs like `/student/quizzes` or `/teacher/upload`.
- **Services:** Contain the "brain" (logic for grading quizzes or calculating levels).
- **Repositories:** Communicate with the database.

---

## 💎 4. Software Design Principles (The Professional Edge)

To keep the project professional, we followed several core engineering principles:

- **SOLID Principles:** 
  - *Single Responsibility:* Each class does one thing. `QuizService` only handles quizzes; `ChatMessageRepository` only handles messages.
  - *Interface Segregation:* We used Spring Data interfaces to keep our database interactions clean and modular.
- **DRY (Don't Repeat Yourself):** We used **Lombok** (`@Data`, `@Builder`) to eliminate redundant code (getters/setters), keeping the project lean.
- **RESTful Design:** Every interaction between the frontend and backend follows standard HTTP methods (`GET`, `POST`), making the app compatible with any modern browser.

---

## 🛡️ 5. Error Handling & Robustness

- **Transactional Safety:** We used the `@Transactional` annotation in Java. This ensures that if a quiz submission fails halfway through, the database "rolls back" so no partial or broken data is saved.
- **Graceful UI Feedback:** Instead of showing ugly system errors, the frontend uses `alert()` and red labels to explain issues (like "Incorrect Access Key" or "File Too Large") in plain English.
- **Default Fallbacks:** If a student has no XP, the system defaults to "Level 1" and "Novice" title automatically, ensuring the UI never breaks.

---

## 🔮 6. Future Roadmap (Scaling the Project)

If we were to expand EduPulse for thousands of users, our next steps would be:
1.  **Database Upgrade:** Move from H2 to **PostgreSQL** for permanent, high-performance cloud storage.
2.  **Real-Time Upgrade:** Replace Polling with **WebSockets (STOMP)** for instant, zero-latency chat.
3.  **Mobile App:** Create a Flutter or React Native app that connects to our existing Spring Boot API.
4.  **AI Insights:** Use a machine learning model to analyze student quiz performance and suggest extra resources.

---
*Created for CSE-A Classroom by CSE A Students.*
