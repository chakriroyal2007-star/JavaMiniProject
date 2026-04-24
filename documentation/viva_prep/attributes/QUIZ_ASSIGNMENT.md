# Attribute: Quiz & Assignment System

This document explains the evaluation and assessment engine of EduPulse.

## Tech Used
- **Thymeleaf Dynamic Forms**: To build interactive quiz interfaces where students can select answers.
- **JSON Serialization**: To store quiz questions and options in a flexible format if needed, or structured SQL tables.
- **Business Logic Layer**: To calculate grades and track completion status.

## How We Did It
1.  **Quiz Creation**: Teachers can create quizzes with multiple-choice questions. They specify the correct answer for automated grading.
2.  **Assignment Submission**: Students can upload their work (PDF/Doc) for specific assignments. The system tracks the "Submission Date" to flag late entries.
3.  **Automated Grading**: For quizzes, the system compares student answers with the key stored in the database and instantly calculates the score.
4.  **Feedback Loop**: Teachers can view all submissions for an assignment and provide marks or comments.
5.  **Persistence**: Every attempt and grade is stored in the database, allowing students to view their performance history over time.
