package test.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCalculator extends JFrame implements ActionListener {

    private JTextField num1Field, num2Field, resultField;
    private JButton addButton, subtractButton, multiplyButton, divideButton;

    public SimpleCalculator() {
        // Frame Setup
        super("간단한 계산기"); // Set frame title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 250);
        setLocationRelativeTo(null); // Center the window

        // --- Style Definitions for "Cute Princess Style" ---
        Color frameBgColor = new Color(255, 240, 245); // GhostWhite (very light pink)
        Color panelBgColor = new Color(255, 245, 250); // LavenderBlush
        Color operatorButtonPink = new Color(255, 182, 193); // Baby pink
        Color labelTextColor = new Color(139, 69, 19); // SaddleBrown (a warm, dark pastel)
        Font italicFont = new Font("Serif", Font.ITALIC, 12); // Using Serif as a common fallback, make it italic

        // Main Panel using BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)); // Gaps between components
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding
        mainPanel.setBackground(frameBgColor); // Apply frame background to main panel

        // --- Input and Result Panel (using GridLayout) ---
        JPanel inputResultPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputResultPanel.setBackground(panelBgColor); // Apply panel background

        JLabel num1Label = new JLabel("🎀숫자 1:");
        num1Label.setFont(italicFont);
        num1Label.setForeground(labelTextColor);
        inputResultPanel.add(num1Label);
        num1Field = new JTextField();
        inputResultPanel.add(num1Field);

        JLabel num2Label = new JLabel("🎀숫자 2:");
        num2Label.setFont(italicFont);
        num2Label.setForeground(labelTextColor);
        inputResultPanel.add(num2Label);
        num2Field = new JTextField();
        inputResultPanel.add(num2Field);

        JLabel operatorLabel = new JLabel("🎀연산자:");
        operatorLabel.setFont(italicFont);
        operatorLabel.setForeground(labelTextColor);
        inputResultPanel.add(operatorLabel);

        JPanel operatorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0)); // Panel for operators to align left
        operatorPanel.setBackground(panelBgColor); // Match input panel background

        addButton = new JButton("+");
        addButton.setBackground(operatorButtonPink);
        addButton.setForeground(Color.BLACK); // Changed to black for visibility
        addButton.setFont(new Font("Serif", Font.BOLD, 14));

        subtractButton = new JButton("-");
        subtractButton.setBackground(operatorButtonPink);
        subtractButton.setForeground(Color.BLACK); // Changed to black for visibility
        subtractButton.setFont(new Font("Serif", Font.BOLD, 14));

        multiplyButton = new JButton("*");
        multiplyButton.setBackground(operatorButtonPink);
        multiplyButton.setForeground(Color.BLACK); // Changed to black for visibility
        multiplyButton.setFont(new Font("Serif", Font.BOLD, 14));

        divideButton = new JButton("/");
        divideButton.setBackground(operatorButtonPink);
        divideButton.setForeground(Color.BLACK); // Changed to black for visibility
        divideButton.setFont(new Font("Serif", Font.BOLD, 14));

        addButton.addActionListener(this);
        subtractButton.addActionListener(this);
        multiplyButton.addActionListener(this);
        divideButton.addActionListener(this);

        operatorPanel.add(addButton);
        operatorPanel.add(subtractButton);
        operatorPanel.add(multiplyButton);
        operatorPanel.add(divideButton);
        inputResultPanel.add(operatorPanel);

        JLabel resultLabel = new JLabel("🎀결과:");
        resultLabel.setFont(italicFont);
        resultLabel.setForeground(labelTextColor);
        inputResultPanel.add(resultLabel);

        resultField = new JTextField();
        resultField.setEditable(false); // Result is read-only
        resultField.setBackground(Color.WHITE); // Standard white for result field
        resultField.setForeground(Color.BLACK);
        inputResultPanel.add(resultField);

        mainPanel.add(inputResultPanel, BorderLayout.CENTER);

        // Add the main panel to the frame
        add(mainPanel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // --- Sound effect placeholder ---
        // To play a click sound, you would typically load an audio file
        // and play it here. For example, using javax.sound.sampled.
        // Since no audio file was provided and I cannot generate one,
        // this functionality is not implemented. If you have a sound file
        // (e.g., click.wav) and can provide its path, I can try to integrate it.
        // Example: playSound("path/to/click.wav");

        double num1 = 0, num2 = 0;
        boolean validInput = true;

        try {
            num1 = Double.parseDouble(num1Field.getText());
        } catch (NumberFormatException ex) {
            resultField.setText("✨숫자 1 오류✨");
            validInput = false;
        }

        if (validInput) {
            try {
                num2 = Double.parseDouble(num2Field.getText());
            } catch (NumberFormatException ex) {
                resultField.setText("✨숫자 2 오류✨");
                validInput = false;
            }
        }

        if (validInput) {
            double result = 0;
            String command = e.getActionCommand(); // Get the operator from button text

            switch (command) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        resultField.setText("🎀0으로 나눌 수 없음🎀");
                        return; // Exit early for division by zero
                    }
                    result = num1 / num2;
                    break;
                default:
                    resultField.setText("🎀알 수 없는 연산🎀");
                    return;
            }
            resultField.setText(String.valueOf(result));
        }
    }

    public static void main(String[] args) {
        // Run the GUI creation on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            new SimpleCalculator();
        });
    }
}