# Attribute: Communication & Chat System

This document explains the real-time (or near real-time) messaging feature.

## Tech Used
- **AJAX / Fetch API**: For sending and receiving messages without reloading the entire page.
- **WebSocket (Optional/Planned)**: For instant message delivery if implemented, otherwise Long Polling or frequent updates.
- **JavaScript Emoji Libraries**: For adding expressive icons to messages.

## How We Did It
1.  **Message Entity**: A database table stores the sender, message content, timestamp, and classroom ID.
2.  **Room-Based Chat**: Messages are filtered by `classroomId` so that conversations stay private to each specific class.
3.  **UI Components**:
    - **Message Bubbles**: Styled differently for "Me" (right side) and "Others" (left side).
    - **Scroll Control**: Automatically scrolls to the bottom when a new message arrives.
4.  **Security**: The backend verifies that a user is actually a member of the classroom before allowing them to post or read messages in that chat.
5.  **Announcements**: A separate specialized "Announcement" channel allows teachers to post important updates that appear pinned or at the top of the feed.
