package rifqimuhammadaziz.view.user;

import rifqimuhammadaziz.dao.UserDaoImpl;
import rifqimuhammadaziz.entity.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegisterForm {
    private JTextField txtUsername;
    private JTextField txtFullName;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JTextField txtPhoneNumber;
    private JTextArea txtAddress;
    private JButton btnRegister;
    private JButton btnReset;
    private JPanel rootPanel;

    private UserDaoImpl userDao;
    private List<User> users;


    public RegisterForm() {
        userDao = new UserDaoImpl();
        users = new ArrayList<>();

        btnRegister.addActionListener(e -> {
            if (    txtUsername.getText().trim().isEmpty() ||
                    txtFullName.getText().trim().isEmpty() ||
                    txtAddress.getText().trim().isEmpty() ||
                    txtPhoneNumber.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(rootPanel, "Please fill form correctly!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                User user = new User();
                user.setUsername(txtUsername.getText());
                user.setFullName(txtFullName.getText());

                if (maleRadioButton.isSelected()) {
                    user.setGender("Male");
                } else {
                    user.setGender("Female");
                }

                user.setAddress(txtAddress.getText());
                user.setPhoneNumber(txtPhoneNumber.getText());

                try {
                    if (userDao.addData(user) == 1) {
                        users.clear();
                        //resetForm();
                        JOptionPane.showMessageDialog(rootPanel, "Success Add Data", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(rootPanel, "Failed to Add Data \nUsername " + txtUsername.getText() + "already Registered", "Register Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("RegisterForm");
        frame.setContentPane(new RegisterForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


}
