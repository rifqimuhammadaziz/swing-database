package rifqimuhammadaziz.view.user;

import rifqimuhammadaziz.entity.User;

import javax.swing.*;
import java.awt.*;

public class EditForm extends Container {
    JPanel rootPanel;
    private JTextField txtFullName;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JTextArea txtAddress;
    private JTextField txtPhonenumber;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JComboBox cbStatus;
    private JTextField txtUsername;


    public EditForm(User users) {
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

        // Update User
        // TODO: 4/8/2022 : Update User 
    }
}
