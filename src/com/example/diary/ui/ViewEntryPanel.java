package com.example.diary.ui;

import com.example.diary.dto.DiaryEntryDTO;
import com.example.diary.db.DiaryEntryDAO;
import com.example.diary.db.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ViewEntryPanel extends JPanel {

    private JLabel titleLabel;
    private JTextArea contentArea;
    private JLabel createdAtLabel;
    private JLabel updatedAtLabel;
    private JButton editButton;
    private JButton deleteButton;
    private DiaryEntryDTO currentEntry;
    private DiaryEntryDAO dao;
    private DiaryUI diaryUI; // Reference to the main UI to switch panels

    public ViewEntryPanel(DiaryEntryDAO dao, DiaryUI diaryUI) {
        this.dao = dao;
        this.diaryUI = diaryUI;
        setLayout(new BorderLayout(10, 10));

        // --- Style Definitions ---
        Color panelBgColor = new Color(255, 245, 250); // LavenderBlush
        Color labelTextColor = new Color(139, 69, 19); // SaddleBrown
        Font italicFont = new Font("Serif", Font.ITALIC, 12);
        Color buttonBgColor = new Color(255, 182, 193); // Baby pink

        setBackground(panelBgColor);

        // --- Display Panel ---
        JPanel displayPanel = new JPanel(new GridBagLayout());
        displayPanel.setBackground(panelBgColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        titleLabel = new JLabel("제목: ");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 16));
        titleLabel.setForeground(labelTextColor);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        displayPanel.add(titleLabel, gbc);

        // Created At
        createdAtLabel = new JLabel("작성일: ");
        createdAtLabel.setFont(italicFont);
        createdAtLabel.setForeground(labelTextColor);
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        displayPanel.add(createdAtLabel, gbc);

        // Updated At
        updatedAtLabel = new JLabel("수정일: ");
        updatedAtLabel.setFont(italicFont);
        updatedAtLabel.setForeground(labelTextColor);
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        displayPanel.add(updatedAtLabel, gbc);

        // Content Area
        contentArea = new JTextArea();
        contentArea.setFont(new Font("Serif", Font.PLAIN, 14));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false); // Read-only
        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("내용"));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.weightx = 1.0; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH;
        displayPanel.add(scrollPane, gbc);

        add(displayPanel, BorderLayout.CENTER);

        // --- Button Panel ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(panelBgColor);

        editButton = new JButton("수정");
        editButton.setBackground(buttonBgColor);
        editButton.setForeground(Color.BLACK);
        editButton.setFont(new Font("Serif", Font.BOLD, 14));
        editButton.addActionListener(e -> {
            if (currentEntry != null) {
                diaryUI.editEntry(currentEntry);
            }
        });

        deleteButton = new JButton("삭제");
        deleteButton.setBackground(buttonBgColor);
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setFont(new Font("Serif", Font.BOLD, 14));
        deleteButton.addActionListener(e -> deleteEntry());

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void displayEntry(DiaryEntryDTO entry) {
        this.currentEntry = entry;
        if (entry != null) {
            titleLabel.setText("제목: " + entry.getTitle());
            contentArea.setText(entry.getContent());
            createdAtLabel.setText("작성일: " + entry.getCreatedAt().toLocalDate().toString());
            updatedAtLabel.setText("수정일: " + entry.getUpdatedAt().toLocalDate().toString());
        } else {
            clearDisplay();
        }
    }

    private void clearDisplay() {
        titleLabel.setText("제목: ");
        contentArea.setText("");
        createdAtLabel.setText("작성일: ");
        updatedAtLabel.setText("수정일: ");
        currentEntry = null;
    }

    private void deleteEntry() {
        if (currentEntry != null) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "정말로 이 일기를 삭제하시겠습니까?\n제목: " + currentEntry.getTitle(),
                    "삭제 확인",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    dao.deleteEntry(currentEntry.getId());
                    JOptionPane.showMessageDialog(this, "일기가 성공적으로 삭제되었습니다.", "삭제 완료", JOptionPane.INFORMATION_MESSAGE);
                    clearDisplay(); // Clear display after deletion
                    diaryUI.refreshEntryList();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "데이터베이스 오류: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "삭제할 일기를 선택해주세요.", "선택 오류", JOptionPane.WARNING_MESSAGE);
        }
    }
}
