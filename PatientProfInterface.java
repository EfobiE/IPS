import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;
import javax.swing.*;

public class PatientProfInterface {

    private final String fileName;
    private final int COLUMNS = 10;
    private final PatientProfDB db;

    public PatientProfInterface(String fileName) throws IOException {
        this.fileName = fileName;
        this.db = new PatientProfDB(fileName); // create the database object for use with the given fileName.
        setupMainMenu(); // sets up, and shows, the main menu. Also returns the frame that it uses.
    }

    // the main menu
    private void setupMainMenu() {
        JFrame f = new JFrame("IPS");

        // set up the radio buttons
        JRadioButton createButton = new JRadioButton("Create Profile");
        createButton.setActionCommand("create");
        createButton.setSelected(true);

        JRadioButton deleteButton = new JRadioButton("Delete Profile");
        deleteButton.setActionCommand("delete");

        JRadioButton updateButton = new JRadioButton("Update Profile");
        updateButton.setActionCommand("update");

        JRadioButton findButton =   new JRadioButton("Find/DisplayProfile");
        findButton.setActionCommand("find");

        JRadioButton allButton =    new JRadioButton("Display All Profiles");
        allButton.setActionCommand("all");

        // group the buttons. The ButtonGroup manages which button is selected, not how they are organized on screen.
        ButtonGroup group = new ButtonGroup();
        group.add(createButton);
        group.add(deleteButton);
        group.add(updateButton);
        group.add(findButton);
        group.add(allButton);

        // set up the select button
        JButton select = new JButton("Select");
        select.addActionListener(e -> mainMenuSelect(group));

        JPanel container = new JPanel();
        container.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        // set up panel to visually place the elements on.
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
        radioPanel.add(createButton);
        radioPanel.add(deleteButton);
        radioPanel.add(updateButton);
        radioPanel.add(findButton);
        radioPanel.add(allButton);


        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(new JLabel("Integrated Patient System"));

        JPanel footer = new JPanel();
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.add(select);

        // put things in the containers so they're visible

        container.add(header);
        container.add(radioPanel);
        container.add(footer);
        f.add(container);

        // set up window

        // make it so clicking the X ends the process instead of just hiding the window
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(270,230);
        f.setLocationRelativeTo(null); // this centers the window
        f.setVisible(true);
    }

    // function called by the main menu when you click 'select'.
    private void mainMenuSelect(ButtonGroup b) {
        // find which radio button was selected using the button's ActionCommand.
        String actionCommand = b.getSelection().getActionCommand();
        System.out.println("Button clicked: " + actionCommand);

        // call functions here that bring up the additional menus, depending on what button was pressed.
        switch (actionCommand) {
            case "create" -> setupCreateMenu();
            case "delete" -> setupDeleteMenu();
            case "update" -> setupUpdateMenu();
            case "find" -> setupFindMenu();
            case "all" -> setupAllMenu();
        }
    }

    // the menu for adding a new profile
    private void setupCreateMenu() {
        JDialog createMenu = new JDialog();

        JTextField adminIDInput      = new JTextField(COLUMNS);
        JTextField firstNameInput    = new JTextField(COLUMNS);
        JTextField lastNameInput     = new JTextField(COLUMNS);
        JTextField addressInput      = new JTextField(COLUMNS);
        JTextField phoneInput        = new JTextField(COLUMNS);
        JTextField copayInput        = new JTextField(COLUMNS);
        JTextField mdContactInput    = new JTextField(COLUMNS);
        JTextField mdPhoneInput      = new JTextField(COLUMNS);

        String[]   insuTypes         = {"Private", "Government"};
        JComboBox  insuTypeInput     = new JComboBox(insuTypes);
        String[]   patientTypes      = {"Pediatric", "Adult", "Senior"};
        JComboBox  patientTypeInput  = new JComboBox(patientTypes);
        String[]   allergyTypes      = {"None", "Food", "Medication", "Other"};
        JComboBox  allergyTypeInput  = new JComboBox(allergyTypes);
        String[]   illTypes          = {"None", "CHD", "Diabetes", "Asthma", "Other"};
        JComboBox  illTypeInput      = new JComboBox(illTypes);

        JButton    submit            = new JButton("Submit");
        submit.addActionListener(e -> {
            try {
                createMenuSubmit(adminIDInput.getText(),
                        firstNameInput.getText(),
                        lastNameInput.getText(),
                        addressInput.getText(),
                        phoneInput.getText(),
                        Float.parseFloat(copayInput.getText()),
                        Objects.requireNonNull(insuTypeInput.getSelectedItem()).toString(),
                        Objects.requireNonNull(patientTypeInput.getSelectedItem()).toString(),
                        mdContactInput.getText(),
                        mdPhoneInput.getText(),
                        Objects.requireNonNull(allergyTypeInput.getSelectedItem()).toString(),
                        Objects.requireNonNull(illTypeInput.getSelectedItem()).toString(),
                        createMenu);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        JPanel body = new JPanel();
        body.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 10;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        body.add(new JLabel("Admin ID:"), c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2; // text boxes wider than labels
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_END;
        body.add(adminIDInput, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        body.add(new JLabel("First Name:"), c);

        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2; // text boxes wider than labels
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_END;
        body.add(firstNameInput, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        body.add(new JLabel("Last Name:"), c);

        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2; // text boxes wider than labels
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_END;
        body.add(lastNameInput, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        body.add(new JLabel("Address:"), c);

        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2; // text boxes wider than labels
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_END;
        body.add(addressInput, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        body.add(new JLabel("Phone:"), c);

        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 2; // text boxes wider than labels
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_END;
        body.add(phoneInput, c);

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        body.add(new JLabel("Co-Pay:"), c);

        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 2; // text boxes wider than labels
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_END;
        body.add(copayInput, c);

        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        body.add(new JLabel("Insurance Type:"), c);

        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 2; // text boxes wider than labels
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_END;
        body.add(insuTypeInput, c);

        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        body.add(new JLabel("Patient Type:"), c);

        c.gridx = 1;
        c.gridy = 7;
        c.gridwidth = 2; // text boxes wider than labels
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_END;
        body.add(patientTypeInput, c);

        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        body.add(new JLabel("Md Contact:"), c);

        c.gridx = 1;
        c.gridy = 8;
        c.gridwidth = 2; // text boxes wider than labels
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_END;
        body.add(mdContactInput, c);

        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        body.add(new JLabel("Md Phone:"), c);

        c.gridx = 1;
        c.gridy = 9;
        c.gridwidth = 2; // text boxes wider than labels
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_END;
        body.add(mdPhoneInput, c);

        c.gridx = 0;
        c.gridy = 10;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        body.add(new JLabel("Allergies:"), c);

        c.gridx = 1;
        c.gridy = 10;
        c.gridwidth = 2; // text boxes wider than labels
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_END;
        body.add(allergyTypeInput, c);

        c.gridx = 0;
        c.gridy = 11;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        body.add(new JLabel("Illnesses:"), c);

        c.gridx = 1;
        c.gridy = 11;
        c.gridwidth = 2; // text boxes wider than labels
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_END;
        body.add(illTypeInput, c);


        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(new JLabel("Integrated Patient System"));

        JPanel footer = new JPanel();
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.add(submit);

        JPanel container = new JPanel();
        container.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        container.add(header);
        container.add(body);
        container.add(footer);

        createMenu.add(container);

        createMenu.setSize(380,500);
        createMenu.setModal(true); // this menu must be closed before you can open other menus
        createMenu.setLocationRelativeTo(null); // this centers the window
        createMenu.setVisible(true);
    }

    // function called to create profile with info from the create menu.
    private void createMenuSubmit(String adminID, String firstName, String lastName, String address, String phone, float copay, String insuType, String patientType, String mdContact, String mdPhone, String allergies, String illnesses, JDialog createMenu) throws IOException {
        db.insertNewProfile(new PatientProf(adminID,
                firstName,
                lastName,
                address,
                phone,
                copay,
                insuType,
                patientType,
                new MedCond(mdContact,mdPhone,allergies,illnesses)));
        db.writeAllPatientProf(fileName);
        createMenu.setVisible(false);
    }

    // the menu for choosing a profile to be deleted
    private void setupDeleteMenu() {
        JDialog deleteMenu = new JDialog();

        JTextField adminIDInput = new JTextField(COLUMNS);
        JTextField lastNameInput = new JTextField(COLUMNS);

        JButton delete = new JButton("Delete");
        delete.addActionListener(e -> {
            try {
                deleteMenuSelect(adminIDInput.getText(),lastNameInput.getText(), deleteMenu);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 0;
        c.gridy = 0;
        inputPanel.add(new JLabel("Admin ID:"), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_END;
        c.gridx = 1;
        c.gridy = 0;
        inputPanel.add(adminIDInput, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        c.ipadx = 10;
        c.gridx = 0;
        c.gridy = 1;
        inputPanel.add(new JLabel("Last Name:"), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_END;
        c.gridx = 1;
        c.gridy = 1;
        inputPanel.add(lastNameInput, c);

        JPanel container = new JPanel();
        container.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(new JLabel("Delete Profile"));

        JPanel footer = new JPanel();
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.add(delete);

        container.add(header);
        container.add(inputPanel);
        container.add(footer);

        deleteMenu.add(container);

        deleteMenu.setModal(true); // this menu must be closed before you can open other menus
        deleteMenu.setSize(270,220);
        deleteMenu.setLocationRelativeTo(null);
        deleteMenu.setVisible(true);
    }

    // function called when clicking "Delete" on the delete menu; deletes the profile in the database
    private void deleteMenuSelect(String adminID, String lastName, JDialog deleteMenu) throws IOException {
        deleteMenu.setVisible(false);
        boolean deleted = db.deleteProfile(adminID, lastName);
        db.writeAllPatientProf(fileName);
        if (deleted) {
            setupDeleteConfirmMenu();
        } else {
            setupDeleteFailedMenu();
        }
    }

    // confirmation that a profile was successfully deleted
    private void setupDeleteConfirmMenu() {
        JDialog deleteConfirmMenu = new JDialog();

        JButton ok = new JButton("OK");
        ok.addActionListener(e -> deleteConfirmMenu.setVisible(false));

        JPanel container = new JPanel();
        container.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(new JLabel("Delete Profile"));

        JPanel footer = new JPanel();
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.add(ok);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body,BoxLayout.Y_AXIS));
        body.add(new JLabel("Profile Deleted!"));

        container.add(header);
        container.add(body);
        container.add(footer);

        deleteConfirmMenu.add(container);

        deleteConfirmMenu.setModal(true); // this menu must be closed before you can open other menus
        deleteConfirmMenu.setSize(270,220);
        deleteConfirmMenu.setLocationRelativeTo(null);
        deleteConfirmMenu.setVisible(true);
    }

    // message that the profile to be deleted does not exist.
    private void setupDeleteFailedMenu() {
        JDialog deleteConfirmMenu = new JDialog();

        JButton ok = new JButton("OK");
        ok.addActionListener(e -> deleteConfirmMenu.setVisible(false));

        JPanel container = new JPanel();
        container.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(new JLabel("Delete Profile"));

        JPanel footer = new JPanel();
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.add(ok);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body,BoxLayout.Y_AXIS));
        body.add(new JLabel("Error: No Matching Profile"));

        container.add(header);
        container.add(body);
        container.add(footer);

        deleteConfirmMenu.add(container);

        deleteConfirmMenu.setModal(true); // this menu must be closed before you can open other menus
        deleteConfirmMenu.setSize(270,220);
        deleteConfirmMenu.setLocationRelativeTo(null);
        deleteConfirmMenu.setVisible(true);
    }

    private void setupUpdateMenu() {
        JDialog updateMenu = new JDialog();

        JTextField adminIDInput = new JTextField();
        JTextField lastNameInput = new JTextField();
        String[] updateFields = {"Address","Phone","Insurance Type","Co-Pay","Patient Type","Md Contact","Md Phone","Allergies","Illnesses"};
        JComboBox updateFieldInput = new JComboBox(updateFields);
        JButton find = new JButton("Find");
        find.addActionListener(e -> setupUpdateParameterMenu(adminIDInput.getText(), lastNameInput.getText(), Objects.requireNonNull(updateFieldInput.getSelectedItem()).toString(), updateMenu));

        JPanel container = new JPanel();
        container.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(new JLabel("Update Profile"));

        JPanel footer = new JPanel();
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.add(find);

        JPanel body = new JPanel();
        body.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 10;
        c.anchor = GridBagConstraints.LINE_START;
        c.gridwidth = 1;
        body.add(new JLabel("Admin ID:"), c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.LINE_END;
        c.gridwidth = 2;
        body.add(adminIDInput, c);

        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_START;
        c.gridwidth = 1;
        body.add(new JLabel("Last Name:"), c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.LINE_END;
        c.gridwidth = 2;
        body.add(lastNameInput, c);

        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_START;
        c.gridwidth = 1;
        body.add(new JLabel("Update Field:"), c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.LINE_END;
        c.gridwidth = 2;
        body.add(updateFieldInput, c);

        container.add(header);
        container.add(body);
        container.add(footer);

        updateMenu.add(container);

        updateMenu.setModal(true);
        updateMenu.setSize(270,230);
        updateMenu.setLocationRelativeTo(null);
        updateMenu.setVisible(true);
    }

    private void setupUpdateParameterMenu(String adminID, String lastName, String attribute, JDialog updateMenu) {
        updateMenu.setVisible(false);

        JDialog updateParameterMenu = new JDialog();

        JButton submit = new JButton("Submit");
        String[] attributes = new String[0];
        JComboBox attributeBox = new JComboBox();
        JTextField attributeInput = new JTextField();
        attributeInput.setPreferredSize(new Dimension(100, 20));
        PatientProf profile = db.findProfile(adminID, lastName);
        // set up attribute specific aspects like ComboBox options and what attribute the submit button updates.
        switch (attribute) {
            case "Insurance Type":
                attributes = new String[]{"Private", "Government"};
                submit.addActionListener(e -> {
                    try {
                        profile.updateInsuType(Objects.requireNonNull(attributeBox.getSelectedItem()).toString());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    try {
                        db.writeAllPatientProf(fileName);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    updateParameterMenu.setVisible(false);
                });
                break;
            case "Patient Type":
                attributes = new String[]{"Pediatric", "Adult", "Senior"};
                submit.addActionListener(e -> {
                    try {
                        profile.updatePatientType(Objects.requireNonNull(attributeBox.getSelectedItem()).toString());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    try {
                        db.writeAllPatientProf(fileName);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    updateParameterMenu.setVisible(false);
                });
                break;
            case "Allergies":
                attributes = new String[]{"None", "Food", "Medication", "Other"};
                submit.addActionListener(e -> {
                    try {
                        profile.getMedCondInfo().updateAlgType(Objects.requireNonNull(attributeBox.getSelectedItem()).toString());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    try {
                        db.writeAllPatientProf(fileName);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    updateParameterMenu.setVisible(false);
                });
                break;
            case "Illnesses":
                attributes = new String[]{"None", "CHD", "Diabetes", "Asthma", "Other"};
                submit.addActionListener(e -> {
                    try {
                        profile.getMedCondInfo().updateIllType(Objects.requireNonNull(attributeBox.getSelectedItem()).toString());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    try {
                        db.writeAllPatientProf(fileName);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    updateParameterMenu.setVisible(false);
                });
                break;
            case "Address":
                submit.addActionListener(e -> {
                    profile.updateAddress(attributeInput.getText());
                    try {
                        db.writeAllPatientProf(fileName);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    updateParameterMenu.setVisible(false);
                });
                break;
            case "Phone":
                submit.addActionListener(e -> {
                    profile.updatePhone(attributeInput.getText());
                    try {
                        db.writeAllPatientProf(fileName);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    updateParameterMenu.setVisible(false);
                });
                break;
            case "Co-Pay":
                submit.addActionListener(e -> {
                    profile.updateCoPay(Float.parseFloat(attributeInput.getText()));
                    try {
                        db.writeAllPatientProf(fileName);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    updateParameterMenu.setVisible(false);
                });
                break;
            case "Md Contact":
                submit.addActionListener(e -> {
                    profile.getMedCondInfo().updateMdContact(attributeInput.getText());
                    try {
                        db.writeAllPatientProf(fileName);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    updateParameterMenu.setVisible(false);
                });
                break;
            case "Md Phone":
                submit.addActionListener(e -> {
                    profile.getMedCondInfo().updateMdPhone(attributeInput.getText());
                    try {
                        db.writeAllPatientProf(fileName);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    updateParameterMenu.setVisible(false);
                });
            default:
                break;
        }

        for (String s : attributes) attributeBox.addItem(s);

        JPanel container = new JPanel();
        container.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(new JLabel("Update"));

        JPanel footer = new JPanel();
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.add(submit);

        JPanel body = new JPanel();
        body.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 5;
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.LINE_START;
        body.add(new JLabel("Traveler ID:"), c);

        c.gridx = 1;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.LINE_END;
        body.add(new JLabel(adminID), c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.LINE_START;
        body.add(new JLabel("Last Name:"), c);

        c.gridx = 1;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.LINE_END;
        body.add(new JLabel(lastName), c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.LINE_START;
        body.add(new JLabel(attribute + ":"), c);

        c.gridx = 1;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.LINE_END;
        if (Stream.of("Insurance Type", "Patient Type", "Allergies", "Illnesses").noneMatch(attribute::equals)) {
            body.add(attributeInput, c);
        } else {
            body.add(attributeBox, c);
        }

        container.add(header);
        container.add(body);
        container.add(footer);

        updateParameterMenu.add(container);

        updateParameterMenu.setModal(true);
        updateParameterMenu.setSize(270,230);
        updateParameterMenu.setLocationRelativeTo(null);
        updateParameterMenu.setVisible(true);
    }

    // function called when clicking "Find" on the find menu; tells if the profile in the database
    private void findMenuSelect(String adminID, String lastName, JDialog findMenu) throws IOException {
        findMenu.setVisible(false);
        PatientProf found = db.findProfile(adminID,lastName);
        db.writeAllPatientProf(fileName);
        if (found != null) {
            setupFindConfirmMenu(found);
        } else {
            setupFindFailedMenu();
        }
    }
    // confirmation that a profile was successfully deleted
    private void setupFindConfirmMenu(PatientProf Profile) {
        JDialog findConfirmMenu = new JDialog();

        JButton ok = new JButton("OK");
        ok.addActionListener(e -> findConfirmMenu.setVisible(false));

        JPanel container = new JPanel();
        container.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(new JLabel("Patient Profile"));

        JPanel footer = new JPanel();
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.add(ok);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body,BoxLayout.Y_AXIS));
        body.add(new JLabel("Admin ID:    "+Profile.getAdminID()));
        body.add(new JLabel("First Name:    "+Profile.getFirstName()));
        body.add(new JLabel("Last Name:    "+Profile.getLastName()));
        body.add(new JLabel("Address:    "+Profile.getAddress()));
        body.add(new JLabel("Phone:    "+Profile.getPhone()));
        body.add(new JLabel("Co-Pay:    "+Profile.getCoPay() ));
        body.add(new JLabel("Insu Type:    "+Profile.getInsuType()));
        body.add(new JLabel("Patient Type:    "+Profile.getPatientType()));
        body.add(new JLabel("Md Contact:    "+Profile.getMedCondInfo().getMdContact()));
        body.add(new JLabel("Md Phone:    "+Profile.getMedCondInfo().getMdPhone()));
        body.add(new JLabel("Allergies:    "+Profile.getMedCondInfo().getAlgType()));
        body.add(new JLabel("Illnesses:    "+Profile.getMedCondInfo().getIllType()));

        /*
        JPanel bodyColumn = new JPanel();
        bodyColumn.setLayout(new BoxLayout(bodyColumn,COLUMNS));
        bodyColumn.add(new JLabel(Profile.getAdminID()));
        bodyColumn.add(new JLabel(Profile.getFirstName()));
        bodyColumn.add(new JLabel(Profile.getLastName()));
        bodyColumn.add(new JLabel(Profile.getAddress()));
        bodyColumn.add(new JLabel(Profile.getPhone()));
        bodyColumn.add(new JLabel(""+Profile.getCoPay()));
        bodyColumn.add(new JLabel(Profile.getInsuType()));
        bodyColumn.add(new JLabel(Profile.getPatientType()));
        bodyColumn.add(new JLabel(Profile.getMedCondInfo().getMdContact()));
        bodyColumn.add(new JLabel(Profile.getMedCondInfo().getMdPhone()));
        bodyColumn.add(new JLabel(Profile.getMedCondInfo().getAlgType()));
        bodyColumn.add(new JLabel(Profile.getMedCondInfo().getIllType()));
        */

        container.add(header);
        container.add(body);
        //container.add(bodyColumn);
        container.add(footer);

        findConfirmMenu.add(container);


        findConfirmMenu.setModal(true); // this menu must be closed before you can open other menus
        findConfirmMenu.setSize(270,310);
        findConfirmMenu.setLocationRelativeTo(null);
        findConfirmMenu.setVisible(true);
    }

    // message that the profile to be deleted does not exist.
    private void setupFindFailedMenu() {
        JDialog findConfirmMenu = new JDialog();

        JButton ok = new JButton("OK");
        ok.addActionListener(e -> findConfirmMenu.setVisible(false));

        JPanel container = new JPanel();
        container.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(new JLabel("Find Profile"));

        JPanel footer = new JPanel();
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.add(ok);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body,BoxLayout.Y_AXIS));
        body.add(new JLabel("Error: No Matching Profile"));

        container.add(header);
        container.add(body);
        container.add(footer);

        findConfirmMenu.add(container);

        findConfirmMenu.setModal(true); // this menu must be closed before you can open other menus
        findConfirmMenu.setSize(270,220);
        findConfirmMenu.setLocationRelativeTo(null);
        findConfirmMenu.setVisible(true);
    }

    private void setupFindMenu() {
        //given an admin id and last name, display the matching profile.
        {
            JDialog findMenu = new JDialog();

            JTextField adminIDInput = new JTextField(COLUMNS);
            JTextField lastNameInput = new JTextField(COLUMNS);

            JButton find = new JButton("Find");
            find.addActionListener(e -> {
                try {
                    findMenuSelect(adminIDInput.getText(),lastNameInput.getText(), findMenu);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });

            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.PAGE_START;
            c.gridx = 0;
            c.gridy = 0;
            inputPanel.add(new JLabel("Admin ID:"), c);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.PAGE_END;
            c.gridx = 1;
            c.gridy = 0;
            inputPanel.add(adminIDInput, c);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.PAGE_START;
            c.ipadx = 10;
            c.gridx = 0;
            c.gridy = 1;
            inputPanel.add(new JLabel("Last Name:"), c);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.PAGE_END;
            c.gridx = 1;
            c.gridy = 1;
            inputPanel.add(lastNameInput, c);

            JPanel container = new JPanel();
            container.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

            JPanel header = new JPanel();
            header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
            header.add(new JLabel("Find Profile"));

            JPanel footer = new JPanel();
            footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
            footer.add(find);

            container.add(header);
            container.add(inputPanel);
            container.add(footer);

            findMenu.add(container);

            findMenu.setModal(true); // this menu must be closed before you can open other menus
            findMenu.setSize(270,220);
            findMenu.setLocationRelativeTo(null);
            findMenu.setVisible(true);
        }
    }

    private void allMenuSelect(String adminID, JDialog allMenu) throws IOException {
        allMenu.setVisible(false);
        PatientProf current = db.findFirstProfile();
        while (current != null) {
            if (current.getAdminID().equals(adminID)) {
                setupFindConfirmMenu(current);
            }
            current = db.findNextProfile();
        }
    }

    private void setupAllMenu()
        //display all profiles one at a time.
        {
            JDialog allMenu = new JDialog();

            JTextField adminIDInput = new JTextField(COLUMNS);
            //JTextField lastNameInput = new JTextField(COLUMNS);

            JButton find = new JButton("Display All");
            find.addActionListener(e -> {
                try {
                    allMenuSelect(adminIDInput.getText(), allMenu);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });

            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.PAGE_START;
            c.gridx = 0;
            c.gridy = 0;
            inputPanel.add(new JLabel("Admin ID:"), c);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.PAGE_END;
            c.gridx = 1;
            c.gridy = 0;
            inputPanel.add(adminIDInput, c);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.PAGE_START;
            c.ipadx = 10;
            c.gridx = 0;
            c.gridy = 1;
            //inputPanel.add(new JLabel("Last Name:"), c);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.PAGE_END;
            c.gridx = 1;
            c.gridy = 1;
            //inputPanel.add(lastNameInput, c);

            JPanel container = new JPanel();
            container.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

            JPanel header = new JPanel();
            header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
            header.add(new JLabel("Display All Profiles"));

            JPanel footer = new JPanel();
            footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
            footer.add(find);

            container.add(header);
            container.add(inputPanel);
            container.add(footer);

            allMenu.add(container);

            allMenu.setModal(true); // this menu must be closed before you can open other menus
            allMenu.setSize(270,220);
            allMenu.setLocationRelativeTo(null);
            allMenu.setVisible(true);
        }

    public static void main(String[] args) throws IOException {
        // creates the object, which displays the menu and sets up the database.
        new PatientProfInterface("src/database.txt");
    }
}