import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class HandCricketGame extends JFrame implements ActionListener {
    private JLabel userScoreLabel, computerScoreLabel;
    private JButton userMoveButton;
    private int userScore, computerScore;
    private boolean isUserBatting;

    public HandCricketGame() {
        setTitle("Hand Cricket Game");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        userScoreLabel = new JLabel("Your Score: 0");
        add(userScoreLabel);

        computerScoreLabel = new JLabel("Computer Score: 0");
        add(computerScoreLabel);

        userMoveButton = new JButton();
        userMoveButton.addActionListener(this);
        add(userMoveButton);

        performToss(); // Perform toss at the beginning of the game
        userScore = 0;
        computerScore = 0;
    }

    private void performToss() {
        String[] options = {"Heads", "Tails"};
        int userChoice = JOptionPane.showOptionDialog(this, "Select Heads or Tails", "Toss", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        Random rand = new Random();
        int tossResult = rand.nextInt(2); // 0 for Heads, 1 for Tails

        if (userChoice == tossResult) {
            JOptionPane.showMessageDialog(this, "You won the toss!");
            int decision = JOptionPane.showOptionDialog(this, "Choose batting or bowling", "Decision", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Batting", "Bowling"}, "Batting");
            isUserBatting = decision == 0;
            if(isUserBatting){
                JOptionPane.showMessageDialog(this, "You chose to bat first!");
            }else{
                JOptionPane.showMessageDialog(this, "You chose to bowl first!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Computer won the toss!");
            isUserBatting = false;
            JOptionPane.showMessageDialog(this, "Computer chose to bat first!");
        }

        if (isUserBatting) {
            userMoveButton.setText("Bat");
        } else {
            userMoveButton.setText("Bowl");
            performComputerBatting();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == userMoveButton) {
            int userMove = Integer.parseInt(JOptionPane.showInputDialog("Enter your move (1 to 6):"));
            int computerMove = generateComputerMove();

            if (isUserBatting) {
                if (userMove == computerMove) {
                    JOptionPane.showMessageDialog(this, "You're out! Your score: " + userScore);
                    isUserBatting = false;
                    userMoveButton.setText("Bowl");
                    performComputerBatting();
                } else {
                    userScore += userMove;
                }
                userScoreLabel.setText("Your Score: " + userScore);
            } else {
                if (userMove == computerMove) {
                    JOptionPane.showMessageDialog(this, "Computer is out! Computer's score: " + computerScore);
                    // You can end the game here or switch roles for another inning.
                } else {
                    computerScore += computerMove;
                }
                computerScoreLabel.setText("Computer Score: " + computerScore);
            }
        }
    }

    private void performComputerBatting() {
        while(true) {
            int computerMove = generateComputerMove();
            int userMove = Integer.parseInt(JOptionPane.showInputDialog("Enter your bowling move (1 to 6):"));

            if (userMove == computerMove) {
                JOptionPane.showMessageDialog(this, "You got the computer out!");
                userMoveButton.setText("Bat");
                isUserBatting = true;
                break;
            } else {
                computerScore += computerMove;
                computerScoreLabel.setText("Computer Score: " + computerScore);
                JOptionPane.showMessageDialog(this, "Computer's score: " + computerScore);
            }
        }
    }

    private int generateComputerMove() {
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            HandCricketGame game = new HandCricketGame();
            game.setVisible(true);
        });
    }
}