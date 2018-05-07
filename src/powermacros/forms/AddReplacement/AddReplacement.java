package powermacros.forms.AddReplacement;

import burp.BurpExtender;
import powermacros.replace.Replace;
import powermacros.replace.ReplaceManager;
import powermacros.transforms.TransformTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class AddReplacement extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;

    private JComboBox cboLinkExtract;

    private JTextField txtName;
    private JComboBox cboType;
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;
    private JButton upButton;
    private JButton downButton;
    private JTable extractTable;
    private JButton btnPath;
    private JTextField txtPath;
    private JFormattedTextField fTxtRegex;
    private JLabel lblRegex;
    private JLabel lblPath;

    private LinkedExtractsTableModel extractionModel;
    private Replace replaceToEdit;

    private boolean isEdit = false;
    private String oldReplaceId = null;
    public AddReplacement(Replace replaceToEdit) {
        this();
        isEdit = true;
        oldReplaceId = replaceToEdit.getId();
        this.replaceToEdit = replaceToEdit;
        this.extractionModel = new LinkedExtractsTableModel(replaceToEdit);
        this.extractTable.setModel(this.extractionModel);
        this.setupEditFields();
    }

    public void setupEditFields() {
        txtName.setText(replaceToEdit.getId());
        TransformTypes replaceType = replaceToEdit.getType();
        cboType.setSelectedItem(replaceType.text());
        if (replaceType.equals(TransformTypes.REGEX)) {
            fTxtRegex.setVisible(true);
            fTxtRegex.setText(replaceToEdit.getExtractReplaceMethod().getExtractionArgument());
        } else if (replaceType.equals(TransformTypes.PYTHON) ||
                replaceType.equals(TransformTypes.JAVASCRIPT)) {
            txtPath.setVisible(true);
            txtPath.setText(replaceToEdit.getExtractReplaceMethod().getExtractionArgument());
        }
    }

    public AddReplacement() {

        this.extractionModel = new LinkedExtractsTableModel();
        this.extractTable.setModel(extractionModel);
        this.replaceToEdit = new Replace();

        lblPath.setVisible(false);
        lblRegex.setVisible(false);
        btnPath.setVisible(false);
        txtPath.setVisible(false);
        fTxtRegex.setVisible(false);

        setContentPane(contentPane);
        setModal(true);

        getRootPane().setDefaultButton(buttonOK);
        addReplacementTypesToCombo();

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
                dispose();
            }
        });


        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAdd();

            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddReplacementSetup addExtractForm = new AddReplacementSetup(replaceToEdit);

                addExtractForm.setTitle("Edit existing replacement");
                //https://stackoverflow.com/questions/12988896/jframe-fixed-width
                addExtractForm.setSize(new Dimension(400, 210));
                addExtractForm.setResizable(false);
                addExtractForm.setVisible(true);
            }
        });
        cboType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cboType.getSelectedItem().toString().equals(TransformTypes.REGEX.text())) {
                    lblPath.setVisible(false);
                    txtPath.setVisible(false);
                    btnPath.setVisible(false);
                    lblRegex.setVisible(true);
                    fTxtRegex.setVisible(true);
                } else if (cboType.getSelectedItem().toString()
                        .equals(TransformTypes.JAVASCRIPT.text())
                        ||
                        cboType.getSelectedItem().toString()
                                .equals(TransformTypes.PYTHON.text())) {

                    lblPath.setVisible(true);
                    txtPath.setVisible(true);
                    btnPath.setVisible(true);
                    lblRegex.setVisible(false);
                    fTxtRegex.setVisible(false);
                }
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRemove();
            }
        });
        btnPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onLoadScript();
            }
        });
    }

    private void onLoadScript() {
        FileDialog fc = new FileDialog(this, "Choose a script file", FileDialog.LOAD);
        fc.setVisible(true);
        if (fc.getFile() != null) {
            String path = fc.getDirectory() + fc.getFile();
            txtPath.setText(path);
        }
    }

    private void onRemove() {
        int index = extractTable.getSelectedRow();
        replaceToEdit.linkedExtracts.remove(index);
        extractionModel.removeRow(index);
    }

    private void onAdd() {
        AddReplacementSetup addExtractForm = new AddReplacementSetup(replaceToEdit);
        addExtractForm.setTitle("Setup new replacement");
        this.replaceToEdit = addExtractForm.showDialog();
        this.extractionModel = new LinkedExtractsTableModel(replaceToEdit);
        this.extractTable.setModel(this.extractionModel);
    }

    private void addReplacementTypesToCombo() {
        cboType.addItem(" ...");
        for (TransformTypes type : TransformTypes.values()) {
            if (type.isImplemented()) {
                cboType.addItem(type.text());
            }
        }
    }

    private void onOK() {
        String arg = "";
        String selectedTransform = cboType.getSelectedItem().toString();

        if (selectedTransform.equals(TransformTypes.REGEX.text())) {
            arg = fTxtRegex.getText();
        } else if (selectedTransform.equals(TransformTypes.JAVASCRIPT.text()) ||
                selectedTransform.equals(TransformTypes.PYTHON.text())) {
            arg = txtPath.getText();
        }

        if(isEdit){
            if(!(oldReplaceId.equals(txtName.getText()))){
                ReplaceManager.removeReplace(oldReplaceId);
            }
        }

        String typeArgs = arg;
        replaceToEdit.setId(txtName.getText());
        replaceToEdit.setTypeString(selectedTransform);
        replaceToEdit.getExtractReplaceMethod();
        replaceToEdit.setReplaceMethod(new String[]{typeArgs});
        replaceToEdit.getExtractReplaceMethod().setExtractionArgument(typeArgs);
        ReplaceManager.putReplace(replaceToEdit);
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
    }
}
