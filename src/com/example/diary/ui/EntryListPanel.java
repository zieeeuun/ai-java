package com.example.diary.ui;

import com.example.diary.dto.DiaryEntryDTO;
import com.example.diary.db.DiaryEntryDAO;
import com.example.diary.db.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EntryListPanel extends JPanel {

    private JList<DiaryEntryDTO> entryJList;
    private DefaultListModel<DiaryEntryDTO> listModel;
    private DiaryEntryDAO dao;
    private DiaryUI diaryUI; 
    private JComboBox<String> sortComboBox;

    public EntryListPanel(DiaryEntryDAO dao, DiaryUI diaryUI) {
        this.dao = dao;
        this.diaryUI = diaryUI;
        setLayout(new BorderLayout());

        // --- Style Definitions ---
        Color panelBgColor = new Color(255, 245, 250); // LavenderBlush
        Color labelTextColor = new Color(139, 69, 19); // SaddleBrown
        Font italicFont = new Font("Serif", Font.ITALIC, 12);

        setBackground(panelBgColor);

        // --- Sort Panel ---
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sortPanel.setBackground(panelBgColor);
        JLabel sortLabel = new JLabel("정렬:");
        sortLabel.setFont(italicFont);
        sortComboBox = new JComboBox<>(new String[]{"최신순", "오래된순"});
        sortComboBox.setFont(italicFont);
        sortComboBox.addActionListener(e -> loadEntries());
        sortPanel.add(sortLabel);
        sortPanel.add(sortComboBox);
        add(sortPanel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        entryJList = new JList<>(listModel);
        entryJList.setFont(new Font("Serif", Font.PLAIN, 14));
        entryJList.setCellRenderer(new DiaryListRenderer(italicFont, labelTextColor)); 

        entryJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && entryJList.getSelectedValue() != null) {
                DiaryEntryDTO selectedEntry = entryJList.getSelectedValue();
                diaryUI.showEntryDetails(selectedEntry);
            }
        });

        JScrollPane scrollPane = new JScrollPane(entryJList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("일기 목록"));
        add(scrollPane, BorderLayout.CENTER);

        loadEntries();
    }

    public void loadEntries() {
        try {
            List<DiaryEntryDTO> entries = dao.getAllEntries();
            
            // Apply sorting in Java for consistency across DB and Memory modes
            if (sortComboBox.getSelectedIndex() == 0) { // Latest First (Descending)
                entries.sort(Comparator.comparing(DiaryEntryDTO::getCreatedAt).reversed());
            } else { // Oldest First (Ascending)
                entries.sort(Comparator.comparing(DiaryEntryDTO::getCreatedAt));
            }

            listModel.clear();
            for (DiaryEntryDTO entry : entries) {
                listModel.addElement(entry);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "일기 목록을 불러오는 중 오류 발생: " + e.getMessage(), "데이터베이스 오류", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Custom renderer for list items
    private static class DiaryListRenderer extends DefaultListCellRenderer {
        private Font italicFont;
        private Color textColor;

        public DiaryListRenderer(Font italicFont, Color textColor) {
            this.italicFont = italicFont;
            this.textColor = textColor;
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof DiaryEntryDTO) {
                DiaryEntryDTO entry = (DiaryEntryDTO) value;
                setText("<html><b>" + entry.getTitle() + "</b><br/>" + entry.getCreatedAt().toLocalDate() + "</html>"); // Display title and date
                setFont(italicFont); // Apply italic font
                setForeground(textColor); // Apply text color
            }
            return this;
        }
    }
}
