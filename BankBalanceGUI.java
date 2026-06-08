import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankBalanceGUI extends JFrame implements ActionListener {

    private BankAccount account;
    private JLabel balanceLabel;
    private JTextField amountField;

    public BankBalanceGUI() {
        account = new BankAccount();
        account.setFirstName("Wooyoung");
        account.setLastName("Cheon");
        account.setAccountID(358538);

        // IMPROVEMENT #1:
        // The program now obtains the initial balance from the user.
        double initialBalance = getInitialBalanceFromUser();
        account.deposit(initialBalance);

        // Basic window settings
        setTitle("Bank Balance GUI - Improved Version");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        // Title panel
        JLabel titleLabel = new JLabel("Bank Balance Application", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel);

        // Balance display panel
        JPanel displayPanel = new JPanel();
        balanceLabel = new JLabel("Balance: $" + String.format("%.2f", account.getBalance()), SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        displayPanel.add(balanceLabel);
        add(displayPanel);

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Amount: $"));
        amountField = new JTextField(10);
        inputPanel.add(amountField);
        add(inputPanel);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton showBtn = new JButton("Show Balance");

        depositBtn.addActionListener(this);
        withdrawBtn.addActionListener(this);
        showBtn.addActionListener(this);

        buttonPanel.add(depositBtn);
        buttonPanel.add(withdrawBtn);
        buttonPanel.add(showBtn);
        add(buttonPanel);

        setVisible(true);
    }

    // IMPROVEMENT #1:
    // Gets the initial balance from the user instead of setting it internally.
    private double getInitialBalanceFromUser() {
        while (true) {
            String input = JOptionPane.showInputDialog(
                    this,
                    "Enter the initial account balance:",
                    "Initial Balance",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (input == null) {
                return 0.0;
            }

            try {
                double amount = Double.parseDouble(input);

                if (amount >= 0) {
                    return amount;
                }

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a positive number or zero."
                );
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid number."
                );
            }
        }
    }

    // Handle button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // IMPROVEMENT #2:
        // Show Balance now has its own distinct function.
        if (command.equals("Show Balance")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Current Balance: $" + String.format("%.2f", account.getBalance()),
                    "Current Balance",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        try {
            double amount = Double.parseDouble(amountField.getText());

            if (command.equals("Deposit")) {
                account.deposit(amount);
            } else if (command.equals("Withdraw")) {
                account.withdrawal(amount);
            }

            updateBalanceLabel();
            amountField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
        }
    }

    // Updates the balance shown in the GUI
    private void updateBalanceLabel() {
        balanceLabel.setText("Balance: $" + String.format("%.2f", account.getBalance()));
    }

    // Main method
    public static void main(String[] args) {
        new BankBalanceGUI();
    }
}