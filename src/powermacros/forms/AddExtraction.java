package powermacros.forms;

import powermacros.extract.ExtractManager;
import powermacros.extract.Extraction;
import powermacros.transforms.TransformTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddExtraction extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextField txtName;
    private JComboBox cboType;
    private JTextField txtPath;
    private JFormattedTextField fTxtRegex;
    private JLabel lblRegex;
    private JLabel lblPath;
    private JButton btnPath;

    private Extraction extractToEdit;

    private boolean isEdit = false;
    private String oldExtractionName;
    public AddExtraction(Extraction extractToEdit){
        this();
        this.isEdit = true;
        this.oldExtractionName = extractToEdit.getId();
        this.extractToEdit = extractToEdit;
        this.setupEditFields();
    }
    public void setupEditFields(){
        txtName.setText(extractToEdit.getId());
        TransformTypes replaceType = extractToEdit.getType();
        cboType.setSelectedItem(replaceType.text());

        if(replaceType.equals(TransformTypes.REGEX)){
            fTxtRegex.setVisible(true);
            fTxtRegex.setText(extractToEdit.getExtractReplaceMethod().getExtractionArgument());
        }else if(replaceType.equals(TransformTypes.PYTHON) ||
                replaceType.equals(TransformTypes.JAVASCRIPT)){
            txtPath.setVisible(true);
            txtPath.setText(extractToEdit.getExtractReplaceMethod().getExtractionArgument());
        }

    }
    public AddExtraction() {
        lblPath.setVisible(false);
        lblRegex.setVisible(false);
        btnPath.setVisible(false);
        txtPath.setVisible(false);
        fTxtRegex.setVisible(false);

        addReplaceTypesToCombo();

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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
        cboType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if(cboType.getSelectedItem().toString());
                if (cboType.getSelectedItem().toString().equals(TransformTypes.REGEX.text())){
                    lblPath.setVisible(false);
                    txtPath.setVisible(false);
                    btnPath.setVisible(false);
                    lblRegex.setVisible(true);
                    fTxtRegex.setVisible(true);
                }else if (  cboType.getSelectedItem().toString()
                                .equals(TransformTypes.JAVASCRIPT.text())
                            ||
                            cboType.getSelectedItem().toString()
                                .equals(TransformTypes.PYTHON.text())){

                    lblPath.setVisible(true);
                    txtPath.setVisible(true);
                    btnPath.setVisible(true);
                    lblRegex.setVisible(false);
                    fTxtRegex.setVisible(false);
                }
            }
        });
        btnPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              onLoadScript();
            }
        });
    }

    private void onLoadScript(){
        FileDialog fc = new FileDialog(this, "Choose a script file", FileDialog.LOAD);
        fc.setVisible(true);
        if(fc.getFile() != null){
            String path = fc.getDirectory() + fc.getFile();
            txtPath.setText(path);
        }

    }

    private void addReplaceTypesToCombo(){
        cboType.addItem(" ...");
        for (TransformTypes type:  TransformTypes.values()) {
            if(type.isImplemented()){
                cboType.addItem(type.text());
            }
        }
    }

    private void onOK() {
        String arg = "";
        String selectedTransform = cboType.getSelectedItem().toString();

        if(selectedTransform.equals(TransformTypes.REGEX.text())){
            arg = fTxtRegex.getText();
        }else if(selectedTransform.equals(TransformTypes.JAVASCRIPT.text()) ||
                 selectedTransform.equals(TransformTypes.PYTHON.text())){
            arg = txtPath.getText();
        }
        String typeArgs[] = {arg};
        Extraction newExtraction = new Extraction(txtName.getText(), cboType.getSelectedItem().toString(), typeArgs);
        if(isEdit){
            if(!(oldExtractionName.equals(txtName.getText()))){
                ExtractManager.removeExtraction(oldExtractionName);
            }
        }
        ExtractManager.put(newExtraction);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
    }

    private void createUIComponents() {
    }
}
