package com.example.diary.ui;

import com.example.diary.dto.DiaryEntryDTO;
import com.example.diary.db.DiaryEntryDAO;
import com.example.diary.db.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class EntryForm extends JPanel {

    private JTextField titleField;
    private JTextArea contentArea;
    private JButton saveButton;
    private JButton cancelButton;
    private DiaryEntryDTO currentEntry; // For editing existing entries
    private DiaryEntryDAO dao;
    private DiaryUI diaryUI;
    private JPanel inputPanel;

    public EntryForm(DiaryEntryDAO dao, DiaryUI diaryUI) {
        this.dao = dao;
        this.diaryUI = diaryUI;
        setLayout(new BorderLayout(10, 10));
        
        // --- Style Definitions ---
        Color panelBgColor = new Color(255, 245, 250); // LavenderBlush
        Color labelTextColor = new Color(139, 69, 19); // SaddleBrown
        Font italicFont = new Font("Serif", Font.ITALIC, 14);
        Color buttonBgColor = new Color(255, 182, 193); // Baby pink

        setBackground(panelBgColor);

        // --- Input Panel ---
        inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(panelBgColor);
        inputPanel.setBorder(BorderFactory.createTitledBorder(null, "새 일기 작성", 0, 0, italicFont));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title field
        JLabel titleLabel = new JLabel("제목:");
        titleLabel.setFont(italicFont);
        titleLabel.setForeground(labelTextColor);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(titleLabel, gbc);

        titleField = new JTextField(20);
        titleField.setFont(italicFont);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        inputPanel.add(titleField, gbc);

        // Content area
        JLabel contentLabel = new JLabel("내용:");
        contentLabel.setFont(italicFont);
        contentLabel.setForeground(labelTextColor);
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.NORTHWEST; gbc.weighty = 0.0;
        inputPanel.add(contentLabel, gbc);

        contentArea = new JTextArea(15, 30);
        contentArea.setFont(italicFont);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(contentArea);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH;
        inputPanel.add(scrollPane, gbc);

        add(inputPanel, BorderLayout.CENTER);

        // --- Button Panel ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(panelBgColor);

        saveButton = new JButton("저장");
        saveButton.setBackground(buttonBgColor);
        saveButton.setForeground(Color.BLACK);
        saveButton.setFont(new Font("Serif", Font.ITALIC | Font.BOLD, 14));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEntry();
            }
        });

        cancelButton = new JButton("취소");
        cancelButton.setBackground(buttonBgColor);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFont(new Font("Serif", Font.ITALIC | Font.BOLD, 14));
        cancelButton.addActionListener(e -> clearForm()); 

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Method to load data for editing
    public void loadEntry(DiaryEntryDTO entry) {
        this.currentEntry = entry;
        if (entry != null) {
            titleField.setText(entry.getTitle());
            contentArea.setText(entry.getContent());
            inputPanel.setBorder(BorderFactory.createTitledBorder(null, "일기 수정", 0, 0, new Font("Serif", Font.ITALIC, 14)));
        } else {
            clearForm();
        }
    }

    // Method to save the entry
    private void saveEntry() {
        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();

        if (title.isEmpty() || content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "제목과 내용을 모두 입력해주세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (currentEntry == null) { // New entry
                DiaryEntryDTO newEntry = new DiaryEntryDTO(title, content);
                dao.addEntry(newEntry);
                JOptionPane.showMessageDialog(this, "일기가 성공적으로 저장되었습니다!", "저장 완료", JOptionPane.INFORMATION_MESSAGE);
            } else { // Update existing entry
                currentEntry.setTitle(title);
                currentEntry.setContent(content);
                currentEntry.setUpdatedAt(LocalDateTime.now());
                dao.updateEntry(currentEntry);
                JOptionPane.showMessageDialog(this, "일기가 성공적으로 수정되었습니다!", "수정 완료", JOptionPane.INFORMATION_MESSAGE);
            }
            clearForm(); // Clear form after saving
            diaryUI.refreshEntryList();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "데이터베이스 오류: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Method to clear the form
    private void clearForm() {
        titleField.setText("");
        contentArea.setText("");
        currentEntry = null; // Reset for new entry
        inputPanel.setBorder(BorderFactory.createTitledBorder(null, "새 일기 작성", 0, 0, new Font("Serif", Font.ITALIC, 14)));
    }
}