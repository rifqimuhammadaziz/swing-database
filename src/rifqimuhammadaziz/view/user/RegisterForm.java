package rifqimuhammadaziz.view.user;

import rifqimuhammadaziz.dao.UserDaoImpl;
import rifqimuhammadaziz.entity.User;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
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
    private JPasswordField txtPassword;
    private JPasswordField txtPasswordConfirm;
    private JButton btnBrowseImage;
    private JLabel imagePreview;

    private UserDaoImpl userDao;
    private List<User> users;


    public RegisterForm() {
        userDao = new UserDaoImpl();
        users = new ArrayList<>();

        String imagePath = null;

        // Register Button
        btnRegister.addActionListener(e -> {
            if (    txtUsername.getText().trim().isEmpty() ||
                    txtFullName.getText().trim().isEmpty() ||
                    txtAddress.getText().trim().isEmpty() ||
                    txtPhoneNumber.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(rootPanel, "Please fill form correctly!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                User user = new User();
                user.setUsername(txtUsername.getText());
                user.setPassword(String.valueOf(txtPassword.getPassword()));
                user.setFullName(txtFullName.getText());

                if (maleRadioButton.isSelected()) {
                    user.setGender("Male");
                } else {
                    user.setGender("Female");
                }

                user.setAddress(txtAddress.getText());
                user.setPhoneNumber(txtPhoneNumber.getText());
                user.setImage(imagePreview.getText());

                try {
                    if (userDao.addData(user) == 1) {
                        users.clear();
                        //resetForm();
                        JOptionPane.showMessageDialog(rootPanel, "Success Add Data", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(rootPanel, "Failed to Add Data \nUsername " + txtUsername.getText() + " already Registered", "Register Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        txtUsername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char c = e.getKeyChar();

                if (Character.isLetterOrDigit(c) || Character.isISOControl(c)) {
                    txtUsername.setEditable(true);
                } else {
                    JOptionPane.showMessageDialog(rootPanel, "Username only contain letter and numbers", "Username error", JOptionPane.ERROR_MESSAGE);
                    txtUsername.setText("");
                }
            }
        });

        txtFullName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char c = e.getKeyChar();

                if (Character.isLetter(c) || Character.isWhitespace(c) || Character.isISOControl(c)) {
                    txtUsername.setEditable(true);
                } else {
                    JOptionPane.showMessageDialog(rootPanel, "Name only contain letter", "Name error", JOptionPane.ERROR_MESSAGE);
                    txtFullName.setText("");
                }
            }
        });

        btnBrowseImage.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

            // extension
            FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("*.images", "jpg", "png", "gif");
            fileChooser.addChoosableFileFilter(fileFilter);

            int fileState = fileChooser.showSaveDialog(null);

            // if user select a file
            if (fileState == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String path = selectedFile.getAbsolutePath();

                // display image to JLabel
//                imagePreview.setIcon(new ImageIcon(path));
                imagePreview.setIcon(resizeImage(path));

            }
            // if user cancel
            else if (fileState == JFileChooser.CANCEL_OPTION) {
                System.out.println("No Image Selected");
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

    public ImageIcon resizeImage(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage().getScaledInstance(imagePreview.getWidth(), imagePreview.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon resizedImage = new ImageIcon(image);

        return resizedImage;
    }


}
