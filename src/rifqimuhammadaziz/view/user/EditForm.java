package rifqimuhammadaziz.view.user;

import rifqimuhammadaziz.dao.UserDaoImpl;
import rifqimuhammadaziz.entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class EditForm extends JFrame {
    private JPanel rootPanel;
    private JTextField txtFullName;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JTextArea txtAddress;
    private JTextField txtPhonenumber;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JComboBox cbStatus;
    private JTextField txtUsername;

    private UserDaoImpl userDao;
    private User selectedUser;

    public EditForm(User users) {
        setContentPane(rootPanel);

        userDao = new UserDaoImpl();

        // ========== GET DATA FROM TABLE ==========
        txtUsername.setText(users.getUsername());
        txtUsername.setEnabled(false);
        txtFullName.setText(users.getFullName());
        if (users.getGender().equals("Male")) {
            maleRadioButton.setSelected(true);
        } else if (users.getGender().equals("Female")) {
            femaleRadioButton.setSelected(true);
        }
        txtAddress.setText(users.getAddress());
        txtPhonenumber.setText(users.getPhoneNumber());
        if (users.getStatus().equals("ACTIVE")) {
            cbStatus.setSelectedItem("ACTIVE");
        } else if (users.getStatus().equals("NON-ACTIVE")) {
            cbStatus.setSelectedItem("NON-ACTIVE");
        }

        btnUpdate.addActionListener(e -> {
            if (    txtUsername.getText().trim().isEmpty() ||
                    txtFullName.getText().trim().isEmpty() ||
                    txtAddress.getText().trim().isEmpty() ||
                    txtPhonenumber.getText().trim().isEmpty() ||
                    cbStatus.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(rootPanel, "Please fill form correctly!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                users.setFullName(txtFullName.getText().trim().isEmpty() ? null : txtFullName.getText());
                users.setAddress(txtAddress.getText().trim().isEmpty() ? null : txtAddress.getText());
                users.setPhoneNumber(txtPhonenumber.getText().trim().isEmpty() ? null : txtPhonenumber.getText());
                users.setStatus((String) cbStatus.getSelectedItem());
                try {
                    int validate = JOptionPane.showConfirmDialog(
                            null,
                            "Are you sure to Update User : " + txtUsername.getText(),
                            "Update User",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );
                    if (validate == JOptionPane.YES_OPTION) {
                        if (userDao.updateData(users) == 1) {
                           userDao.updateData(users);
                           JOptionPane.showMessageDialog(this, "User : " + txtUsername.getText(), "Update Success", JOptionPane.INFORMATION_MESSAGE);
                           onOk();
                        }
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOk();
            }
        });
    }

    private void onOk() {
        dispose();
    }
}
