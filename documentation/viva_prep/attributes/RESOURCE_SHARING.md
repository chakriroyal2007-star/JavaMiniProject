# Attribute: Resource & Material Sharing

This document explains how EduPulse handles study materials and file uploads.

## Tech Used
- **MultipartFile API**: Spring's built-in support for handling file uploads via HTTP.
- **FileSystem Storage**: Files are stored in a dedicated `storage/` directory on the server.
- **MIME Type Detection**: To identify if a file is a PDF, Image, or Document for appropriate UI icons.

## How We Did It
1.  **Uploading**: The `ResourceController` accepts files from teachers. We generate a unique filename (often using timestamps or UUIDs) to prevent overwriting files with the same name.
2.  **Metadata Storage**: While the actual file lives on the disk, the metadata (filename, size, upload date, associated classroom) is stored in the database.
3.  **Download & View**: Students can click on a resource to download it. For PDFs, we use the browser's native viewer to allow online reading.
4.  **Organization**: Resources are grouped by classroom, making it easy for students to find materials specific to their courses.
5.  **Clean-up Logic**: When a resource or classroom is deleted, the system is designed to remove the corresponding physical file from the storage to save space.
