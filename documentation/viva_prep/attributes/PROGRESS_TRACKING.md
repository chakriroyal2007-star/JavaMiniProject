# Attribute: Progress Tracking & XP System

This document explains the gamification elements used to increase student engagement.

## Tech Used
- **Logic Algorithms**: Custom Java logic to calculate XP (Experience Points) based on activity.
- **CSS Transitions**: For smooth progress bar animations in the UI.
- **Persistence Layer**: Storing "XP" and "Level" as fields in the `User` entity.

## How We Did It
1.  **Activity Tracking**: Points are awarded for specific actions:
    - Joining a classroom (+50 XP)
    - Submitting an assignment (+100 XP)
    - Completing a quiz (Points proportional to score)
2.  **Leveling System**: We defined XP thresholds. Once a student reaches a certain amount of XP, they "Level Up".
3.  **UI Feedback**: The student dashboard features a prominent progress bar and a "Level" badge, giving students a sense of accomplishment.
4.  **Teacher Oversight**: Teachers can see the XP levels of all students in their class, which helps identify the most active participants.
5.  **Motivation**: This system transforms the classroom into a more interactive experience, encouraging students to stay on top of their tasks.
