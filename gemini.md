# Java Swing Diary Application Plan

## 1. Project Goal
Develop a simple, aesthetically pleasing diary application using Java Swing that stores entries in a MySQL database. The application should support creating, viewing, editing, and deleting diary entries.

## 2. Core Features
-   **Entry Creation:** Allow users to write new diary entries with a title and content.
-   **Entry Viewing:** Display a list of diary entries and allow users to view individual entries.
-   **Entry Editing:** Enable modification of existing diary entries.
-   **Entry Deletion:** Allow users to remove diary entries.
-   **Database Persistence:** Store all diary data in a MySQL database.

## 3. Code Structure (High Cohesion)
To ensure maintainability and clarity, the codebase will be organized into distinct packages:

### 3.1. `com.example.diary.ui` (User Interface)
-   This package will contain all Swing-related classes for the application's GUI.
-   **`DiaryUI.java`**: The main application window, handling the overall layout, navigation, and interaction logic.
-   **`EntryForm.java`**: A panel or dialog for creating and editing diary entries (title, content input fields).
-   **`EntryListPanel.java`**: A panel to display a list of diary entries (e.g., using `JList` or `JTable`).
-   **`ViewEntryPanel.java`**: A panel to display the full content of a selected diary entry.

### 3.2. `com.example.diary.dto` (Data Transfer Objects)
-   This package will contain simple Plain Old Java Objects (POJOs) used for transferring data between layers.
-   **`DiaryEntryDTO.java`**: Represents a single diary entry, containing fields like `id`, `title`, `content`, `createdAt`, `updatedAt`.

### 3.3. `com.example.diary.db` (Database Interaction)
-   This package will handle all database-related operations.
-   **`DatabaseManager.java`**: Manages the database connection (e.g., establishing and closing connections, handling connection pooling if needed for complexity). It should store database credentials securely (though for simplicity in this plan, they might be hardcoded or read from a config).
-   **`DiaryEntryDAO.java` (Data Access Object)**: Contains methods for performing CRUD (Create, Read, Update, Delete) operations on diary entries in the database. It will use `DiaryEntryDTO` objects.

## 4. Database Schema
A single table is proposed for simplicity:

**Table: `diary_entries`**
-   `id` INT AUTO_INCREMENT PRIMARY KEY
-   `title` VARCHAR(255) NOT NULL
-   `content` TEXT NOT NULL
-   `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
-   `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

## 5. Aesthetics (Colors and Images)
-   **Color Palette:** A soft, pleasant color scheme will be used. Suggestions:
    -   Background: Light pastel colors (e.g., very light pink, cream, or sky blue).
    -   UI Elements (Labels, TextFields): Clean and contrast well with the background.
    -   Buttons: Distinctive but harmonious colors.
    -   Images: Placeholder for a cute image (e.g., a diary icon, a ribbon, or a thematic illustration) to be placed strategically (e.g., on the main window or a specific panel). The user will need to provide the image file.
-   **Simplicity:** Focus on clean layouts (e.g., `BorderLayout`, `GridLayout`, `GridBagLayout` where appropriate) and readable fonts.

## 6. Dependencies
-   **MySQL JDBC Driver:** The application will require the MySQL Connector/J JAR file to connect to the database. This should be added to the project's classpath.

## 7. Development Approach
-   Start by setting up the database connection and the DAO layer.
-   Implement the DTO.
-   Develop the UI components, integrating them with the DAO for data operations.
-   Add aesthetic improvements (colors, image integration) as requested.
