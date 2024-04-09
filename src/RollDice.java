import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class RollDice extends JFrame {
    private JPanel imagePanel;     // Panel to hold the label
    private JPanel buttonPanel;    // Panel to hold the button
    private JLabel imageLabel;     // Label to show the image
    private JPanel historyPanel;     // Panel to hold the label
    private JLabel historyLabel;     // Label to show previous roll results
    private JButton button;        // Button to roll the dice

    private JMenuBar menuBar;    // The menu bar
    private JMenu fileMenu;      // The File menu
    private JMenu settingsMenu;      // The Settings menu
    private JMenuItem exitItem;  // To exit

    private JRadioButtonMenuItem dice1Item; // Selects the default dice set
    private JRadioButtonMenuItem dice2Item;   // Selects the 2nd dice set
    private JCheckBoxMenuItem visibleItem;  // Toggle visibility

    private ArrayList<Integer> rollHistory; // Array to store results 
    private int diceSetting = 1;
    private int randomNumber = 0;

   /**
      Constructor
   */
   
   public RollDice() {
        setTitle("My Dice");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        buildImagePanel();
        buildButtonPanel();
        buildHistoryPanel();
        buildMenuBar();

        add(imagePanel, BorderLayout.NORTH);
        add(historyPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);        

        rollHistory = new ArrayList<>(); // Init. roll history Array.
        Collections.fill(rollHistory, (Integer)0);

        pack();
        setVisible(true);
   }

   private void buildImagePanel() {
        imagePanel = new JPanel();
        imageLabel = new JLabel("Click the button to " +
                                "roll the dice.");
        imagePanel.add(imageLabel);
   }

   private void buildButtonPanel() {      
        buttonPanel = new JPanel();
        button = new JButton("Roll the dice!");
        button.addActionListener(new ButtonListener());
        button.setMnemonic(KeyEvent.VK_R);
        buttonPanel.add(button);
   }

   private void buildHistoryPanel() {      
        historyPanel = new JPanel();
        historyLabel = new JLabel();
        historyPanel.add(historyLabel);
   }

   private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            Random rand = new Random();
            randomNumber = rand.nextInt(7 - 1) + 1;
            
            rollHistory.add(randomNumber);
            updateHistoryLabel();
            updateDiceImage(randomNumber, diceSetting);         
        }
    }
  
    private void buildMenuBar() {
        menuBar = new JMenuBar(); 
        buildFileMenu();
        buildSettingsMenu();
        menuBar.add(fileMenu);
        menuBar.add(settingsMenu); 
        setJMenuBar(menuBar);
    }

    private void buildFileMenu() {
        exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic(KeyEvent.VK_X);
        exitItem.addActionListener(new ExitListener());

        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        
        fileMenu.add(exitItem);
    }

    private void buildSettingsMenu() {
        // Create the radio button menu items to change
        // the color of the text. Add an action listener
        // to each one.
        dice1Item = new JRadioButtonMenuItem("Dice set 1", true);
        dice1Item.setMnemonic(KeyEvent.VK_1);
        dice1Item.addActionListener(new DiceListener());

        dice2Item = new JRadioButtonMenuItem("Dice set 2");
        dice2Item.setMnemonic(KeyEvent.VK_2);
        dice2Item.addActionListener(new DiceListener());

        // Create a button group for the radio button items.
        ButtonGroup group = new ButtonGroup();
        group.add(dice1Item);
        group.add(dice2Item);

        // Create a check box menu item to make the text
        // visible or invisible.
        visibleItem = new JCheckBoxMenuItem("Roll history", true);
        visibleItem.setMnemonic(KeyEvent.VK_H);
        visibleItem.addActionListener(new VisibleListener());

        // Create a JMenu object for the Text menu.
        settingsMenu = new JMenu("Settings");
        settingsMenu.setMnemonic(KeyEvent.VK_S);

        // Add the menu items to the Text menu.
        settingsMenu.add(dice1Item);
        settingsMenu.add(dice2Item);
        settingsMenu.addSeparator(); // Add a separator bar.
        settingsMenu.add(visibleItem);
    }

    private class ExitListener implements ActionListener {
       public void actionPerformed(ActionEvent e) {
          System.exit(0);
       }
    }

    private class DiceListener implements ActionListener {
       public void actionPerformed(ActionEvent e) {
            if (dice1Item.isSelected())
               diceSetting = 1;               
               updateDiceImage(randomNumber, diceSetting);
            if (dice2Item.isSelected())
                diceSetting = 2;
                updateDiceImage(randomNumber, diceSetting);
       }
    }

    private class VisibleListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            historyLabel.setVisible(visibleItem.isSelected());
        }
    }
    
    private void updateHistoryLabel() {
        if (rollHistory.size() > 5) {
            rollHistory.remove(0); // Remove the oldest roll (at index 0)
        }
        if (randomNumber != 0) {
                StringBuilder historyText = new StringBuilder("The previous rolls were: " + System.lineSeparator());
            for (int roll : rollHistory) {
                historyText.append(roll).append(" ");
            }
            historyLabel.setText(historyText.toString());
        }
    }

    private void updateDiceImage(int randomNumber, int diceSetting) {
        if (randomNumber != 0) {            
            String imagePath = "Dice" + Integer.toString(diceSetting) + "/Die" + Integer.toString(randomNumber) + ".png";
            ImageIcon diceImage = new ImageIcon(imagePath);
            
            if (randomNumber != 0) 
            {
                imageLabel.setIcon(diceImage);
                imageLabel.setFont(new Font(imageLabel.getFont().getName(), Font.BOLD, 20));
                imageLabel.setText("The result is: " + Integer.toString(randomNumber) + ".");
                pack();
            }
            else
            {
                // Debug check & Null validation.
                imageLabel.setIcon(null);
                imageLabel.setText("Null Result");
            }
        }
    }

    public static void main(String[] args) {
        new RollDice();
   }
}
