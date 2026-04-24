# Attribute: Classroom Management

This document explains the core functionality of managing digital classrooms.

## Tech Used
- **Spring Data JPA**: For performing CRUD operations on the `Classroom` entity.
- **Relational Database (SQL)**: To maintain the relationships between Teachers, Students, and Classrooms.
- **UUID / Unique Codes**: For generating join codes that students use to enroll.

## How We Did It
1.  **Creation Logic**: Teachers can create a classroom by providing a name, subject, and description. The system automatically assigns the logged-in teacher as the "Owner".
2.  **Unique Join Codes**: Every classroom is assigned a unique alphanumeric code. This eliminates the need for manual invites; students simply enter the code to join.
3.  **Enrollment System**: We use a Many-to-Many relationship between `User` and `Classroom`. When a student joins, they are added to the classroom's student list.
4.  **Dashboard Integration**: The `ClassroomController` fetches all classrooms associated with a user and displays them as interactive cards on the dashboard.
5.  **Role Verification**: Only teachers can create or delete classrooms, while students only have the ability to join or leave.
