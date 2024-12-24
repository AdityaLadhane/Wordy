import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*; // For java.util.List, ArrayList, etc.

public class LinkedInPinpointClone extends JFrame {
    private JPanel gridPanel;
    private JLabel messageLabel, scoreLabel;
    private JTextField guessField;
    private JButton submitButton;
    private int score = 0;
    private int revealedWords = 0;
    private Map<String, String> wordCategoryMap; // Word to Category mapping
    private java.util.List<String> hiddenWords; // Words in the current game
    private java.util.List<JLabel> wordLabels; // Labels to display the hidden words
    private String currentCategory;

    public LinkedInPinpointClone() {
        setTitle("Pinpoint Game");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize word pool and categories
        initializeWordPool();

        // UI Components
        JPanel topPanel = new JPanel(new BorderLayout());
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messageLabel = new JLabel("Guess the category!");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        topPanel.add(scoreLabel, BorderLayout.WEST);
        topPanel.add(messageLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        gridPanel = new JPanel(new GridLayout(1, 5));
        wordLabels = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            JLabel wordLabel = new JLabel("*****", SwingConstants.CENTER); // Hidden words
            wordLabel.setFont(new Font("Arial", Font.BOLD, 18));
            wordLabels.add(wordLabel);
            gridPanel.add(wordLabel);
        }
        add(gridPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        guessField = new JTextField(20);
        submitButton = new JButton("Submit Guess");
        submitButton.addActionListener(new SubmitGuessListener());
        bottomPanel.add(guessField);
        bottomPanel.add(submitButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Start the game
        startGame();

        setVisible(true);
    }

    private void initializeWordPool() {
        wordCategoryMap = new HashMap<>();
        wordCategoryMap.put("Apple", "Fruit");
        wordCategoryMap.put("Banana", "Fruit");
        wordCategoryMap.put("Mango", "Fruit");
        wordCategoryMap.put("Orange", "Fruit");
        wordCategoryMap.put("Grapes", "Fruit");

        wordCategoryMap.put("Dog", "Animal");
        wordCategoryMap.put("Cat", "Animal");
        wordCategoryMap.put("Cow", "Animal");
        wordCategoryMap.put("Horse", "Animal");
        wordCategoryMap.put("Elephant", "Animal");
    }

    private void startGame() {
        // Reset state
        score = 0;
        revealedWords = 0;
        scoreLabel.setText("Score: 0");
        guessField.setText("");
        messageLabel.setText("Guess the category!");

        // Randomly select a category and its words
        Set<String> categories = new HashSet<>(wordCategoryMap.values());
        java.util.List<String> categoryList = new ArrayList<>(categories);
        currentCategory = categoryList.get(new Random().nextInt(categoryList.size()));

        // Filter words by category
        hiddenWords = new ArrayList<>();
        for (Map.Entry<String, String> entry : wordCategoryMap.entrySet()) {
            if (entry.getValue().equals(currentCategory)) {
                hiddenWords.add(entry.getKey());
            }
        }
        Collections.shuffle(hiddenWords);

        // Reset word labels
        for (int i = 0; i < wordLabels.size(); i++) {
            wordLabels.get(i).setText("*****");
        }
    }

    private class SubmitGuessListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String guess = guessField.getText().trim();
            if (guess.equalsIgnoreCase(currentCategory)) {
                if (revealedWords < hiddenWords.size()) {
                    wordLabels.get(revealedWords).setText(hiddenWords.get(revealedWords));
                    revealedWords++;
                    score += 10;
                    scoreLabel.setText("Score: " + score);

                    if (revealedWords == hiddenWords.size()) {
                        messageLabel.setText("Congratulations! All words revealed.");
                        submitButton.setEnabled(false); // Disable further guesses
                    } else {
                        messageLabel.setText("Correct! Keep going.");
                    }
                }
            } else {
                messageLabel.setText("Incorrect. Try again!");
            }
            guessField.setText(""); // Clear input field
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LinkedInPinpointClone::new);
    }
}
