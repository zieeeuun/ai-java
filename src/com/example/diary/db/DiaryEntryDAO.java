package com.example.diary.db;

import com.example.diary.dto.DiaryEntryDTO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DiaryEntryDAO {
    private static final List<DiaryEntryDTO> memoryEntries = new ArrayList<>();
    private static long nextMemoryId = 1L;

    // Method to add a new diary entry
    public void addEntry(DiaryEntryDTO entry) throws SQLException {
        if (!DatabaseManager.isDatabaseAvailable()) {
            DiaryEntryDTO memoryCopy = new DiaryEntryDTO();
            memoryCopy.setId(nextMemoryId++);
            memoryCopy.setTitle(entry.getTitle());
            memoryCopy.setContent(entry.getContent());
            memoryCopy.setCreatedAt(entry.getCreatedAt());
            memoryCopy.setUpdatedAt(entry.getUpdatedAt());
            memoryEntries.add(0, memoryCopy);
            return;
        }

        String sql = "INSERT INTO diary_entries (title, content, created_at, updated_at) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, entry.getTitle());
            pstmt.setString(2, entry.getContent());
            pstmt.setTimestamp(3, Timestamp.valueOf(entry.getCreatedAt()));
            pstmt.setTimestamp(4, Timestamp.valueOf(entry.getUpdatedAt()));
            pstmt.executeUpdate();
        }
    }

    // Method to get all diary entries
    public List<DiaryEntryDTO> getAllEntries() throws SQLException {
        if (!DatabaseManager.isDatabaseAvailable()) {
            return new ArrayList<>(memoryEntries);
        }

        List<DiaryEntryDTO> entries = new ArrayList<>();
        String sql = "SELECT id, title, content, created_at, updated_at FROM diary_entries ORDER BY created_at DESC";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                DiaryEntryDTO entry = new DiaryEntryDTO();
                entry.setId(rs.getLong("id"));
                entry.setTitle(rs.getString("title"));
                entry.setContent(rs.getString("content"));
                entry.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                entry.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                entries.add(entry);
            }
        }
        return entries;
    }

    // Method to get a single diary entry by ID
    public DiaryEntryDTO getEntryById(long id) throws SQLException {
        if (!DatabaseManager.isDatabaseAvailable()) {
            for (DiaryEntryDTO entry : memoryEntries) {
                if (entry.getId() != null && entry.getId() == id) {
                    return entry;
                }
            }
            return null;
        }

        String sql = "SELECT id, title, content, created_at, updated_at FROM diary_entries WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    DiaryEntryDTO entry = new DiaryEntryDTO();
                    entry.setId(rs.getLong("id"));
                    entry.setTitle(rs.getString("title"));
                    entry.setContent(rs.getString("content"));
                    entry.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    entry.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                    return entry;
                }
            }
        }
        return null; // Entry not found
    }

    // Method to update an existing diary entry
    public void updateEntry(DiaryEntryDTO entry) throws SQLException {
        if (!DatabaseManager.isDatabaseAvailable()) {
            for (DiaryEntryDTO memoryEntry : memoryEntries) {
                if (memoryEntry.getId() != null && memoryEntry.getId().equals(entry.getId())) {
                    memoryEntry.setTitle(entry.getTitle());
                    memoryEntry.setContent(entry.getContent());
                    memoryEntry.setUpdatedAt(LocalDateTime.now());
                    return;
                }
            }
            return;
        }

        String sql = "UPDATE diary_entries SET title = ?, content = ?, updated_at = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, entry.getTitle());
            pstmt.setString(2, entry.getContent());
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setLong(4, entry.getId());
            pstmt.executeUpdate();
        }
    }

    // Method to delete a diary entry by ID
    public void deleteEntry(long id) throws SQLException {
        if (!DatabaseManager.isDatabaseAvailable()) {
            memoryEntries.removeIf(entry -> entry.getId() != null && entry.getId().equals(id));
            return;
        }

        String sql = "DELETE FROM diary_entries WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }
}
