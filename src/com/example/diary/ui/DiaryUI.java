package com.example.diary.ui;

import com.example.diary.db.DatabaseManager;
import com.example.diary.db.DiaryEntryDAO;
import com.example.diary.dto.DiaryEntryDTO;

import javax.swing.*;
import java.awt.*;

public class DiaryUI extends JFrame {

    private static final String CARD_START = "start";
    private static final String CARD_MAIN = "main";
    private static final String CARD_VIEW = "view";
    private static final String CARD_FORM = "form";

    private final DiaryEntryDAO dao;
    private final com.example.diary.ui.EntryListPanel entryListPanel;
    private final com.example.diary.ui.ViewEntryPanel viewEntryPanel;
    private final com.example.diary.ui.EntryForm entryForm;
    private final JPanel rightPanel;
    private final CardLayout rightCardLayout;
    private final JPanel mainContainer;
    private final CardLayout topCardLayout;

    public DiaryUI() {
        super("나만의 일기장");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        Color frameBgColor = new Color(255, 240, 245);
        Color buttonBgColor = new Color(255, 182, 193);
        Font globalFont = new Font("Serif", Font.ITALIC, 14);

        DatabaseManager.createTableIfNotExists();
        this.dao = new DiaryEntryDAO();

        topCardLayout = new CardLayout();
        mainContainer = new JPanel(topCardLayout);

        // --- Start Screen ---
        JPanel startPanel = new JPanel(new BorderLayout());
        startPanel.setBackground(frameBgColor);

        JLabel welcomeLabel = new JLabel("나의 일기장", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.ITALIC | Font.BOLD, 36));
        welcomeLabel.setForeground(new Color(139, 69, 19));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0));
        startPanel.add(welcomeLabel, BorderLayout.NORTH);

        // Intro Image placeholder or actual if exists
        try {
            ImageIcon introIcon = new ImageIcon("assets/intro-image.png");
            if (introIcon.getIconWidth() > 0) {
                // Resize image to fit
                Image img = introIcon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(img), JLabel.CENTER);
                startPanel.add(imageLabel, BorderLayout.CENTER);
            } else {
                JLabel placeholder = new JLabel("일기장 아이콘 (이미지 없음)", JLabel.CENTER);
                placeholder.setFont(globalFont);
                startPanel.add(placeholder, BorderLayout.CENTER);
            }
        } catch (Exception e) {
            startPanel.add(new JLabel("이미지를 불러올 수 없습니다.", JLabel.CENTER), BorderLayout.CENTER);
        }

        JButton startButton = new JButton("시작하기");
        startButton.setBackground(buttonBgColor);
        startButton.setFont(new Font("Serif", Font.ITALIC | Font.BOLD, 24));
        startButton.setPreferredSize(new Dimension(200, 60));
        startButton.addActionListener(e -> topCardLayout.show(mainContainer, CARD_MAIN));
        
        JPanel buttonWrapper = new JPanel();
        buttonWrapper.setBackground(frameBgColor);
        buttonWrapper.setBorder(BorderFactory.createEmptyBorder(20, 0, 50, 0));
        buttonWrapper.add(startButton);
        startPanel.add(buttonWrapper, BorderLayout.SOUTH);

        // --- Main Diary Screen ---
        JPanel diaryMainPanel = new JPanel(new BorderLayout());
        diaryMainPanel.setBackground(frameBgColor);

        this.entryListPanel = new com.example.diary.ui.EntryListPanel(dao, this);
        this.viewEntryPanel = new com.example.diary.ui.ViewEntryPanel(dao, this);
        this.entryForm = new com.example.diary.ui.EntryForm(dao, this);

        rightCardLayout = new CardLayout();
        rightPanel = new JPanel(rightCardLayout);
        rightPanel.add(viewEntryPanel, CARD_VIEW);
        rightPanel.add(entryForm, CARD_FORM);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, entryListPanel, rightPanel);
        splitPane.setDividerLocation(300);
        splitPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(frameBgColor);
        JButton newEntryButton = new JButton("새 일기 작성");
        newEntryButton.setBackground(buttonBgColor);
        newEntryButton.setForeground(Color.BLACK);
        newEntryButton.setFont(new Font("Serif", Font.ITALIC | Font.BOLD, 14));
        newEntryButton.addActionListener(e -> newEntry());
        topPanel.add(newEntryButton);

        diaryMainPanel.add(topPanel, BorderLayout.NORTH);
        diaryMainPanel.add(splitPane, BorderLayout.CENTER);

        mainContainer.add(startPanel, CARD_START);
        mainContainer.add(diaryMainPanel, CARD_MAIN);

        add(mainContainer);

        topCardLayout.show(mainContainer, CARD_START);
        setVisible(true);
    }

    public void showEntryDetails(DiaryEntryDTO entry) {
        viewEntryPanel.displayEntry(entry);
        rightCardLayout.show(rightPanel, CARD_VIEW);
    }

    public void editEntry(DiaryEntryDTO entry) {
        entryForm.loadEntry(entry);
        rightCardLayout.show(rightPanel, CARD_FORM);
    }

    public void refreshEntryList() {
        entryListPanel.loadEntries();
    }

    private void newEntry() {
        entryForm.loadEntry(null);
        rightCardLayout.show(rightPanel, CARD_FORM);
    }

    public static void main(String[] args) {
        // Set global font to Italic
        UIManager.put("Label.font", new Font("Serif", Font.ITALIC, 14));
        UIManager.put("Button.font", new Font("Serif", Font.ITALIC, 14));
        UIManager.put("TextField.font", new Font("Serif", Font.ITALIC, 14));
        UIManager.put("TextArea.font", new Font("Serif", Font.ITALIC, 14));
        UIManager.put("List.font", new Font("Serif", Font.ITALIC, 14));
        UIManager.put("TitledBorder.font", new Font("Serif", Font.ITALIC, 14));

        SwingUtilities.invokeLater(DiaryUI::new);
    }
}
