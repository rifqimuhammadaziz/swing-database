package rifqimuhammadaziz.view.user;

import rifqimuhammadaziz.dao.UserDaoImpl;
import rifqimuhammadaziz.entity.User;
import rifqimuhammadaziz.view.main.MainForm;

import javax.swing.*;
import java.sql.SQLException;

public class LoginForm extends JFrame{
    private JTextField txtUsername;
    private JTextField txtPassword;
    private JButton loginButton;
    private JButton cancelButton;
    private JPanel rootPanel;

    UserDaoImpl userDao;

    public LoginForm() {
        userDao = new UserDaoImpl();

        // Button Login
        loginButton.addActionListener(e -> {
            if (txtUsername.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(rootPanel, "Please fill form correctly!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                User user = new User();
                try {
                    user.setUsername(txtUsername.getText());
                    user.setPassword(String.valueOf(txtPassword.getText()));
                    if (userDao.loginUser(user) == 1) {
                        MainForm frame = new MainForm();
                        frame.setTitle("Main Application");
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        cancelButton.addActionListener(e -> {
            System.exit(1);
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Login Form");
        frame.setContentPane(new LoginForm().rootPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
