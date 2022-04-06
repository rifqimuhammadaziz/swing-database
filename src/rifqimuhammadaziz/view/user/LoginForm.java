package rifqimuhammadaziz.view.user;

import rifqimuhammadaziz.dao.UserDaoImpl;
import rifqimuhammadaziz.entity.User;
import rifqimuhammadaziz.view.DepartmentDataForm;
import rifqimuhammadaziz.view.MainForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class LoginForm {
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
//                        JFrame frame = new JFrame("Department Data Table");
//                        frame.setContentPane(new MainForm().rootPanel);
//                        frame.pack();
//                        frame.setLocationRelativeTo(null);
//                        frame.setVisible(true);
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("LoginForm");
        frame.setContentPane(new LoginForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JFrame getMainFrame()
    {
        return (JFrame) SwingUtilities.getWindowAncestor( this.rootPanel );
    }

}
