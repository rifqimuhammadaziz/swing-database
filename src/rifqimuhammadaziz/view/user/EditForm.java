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
    }
}
