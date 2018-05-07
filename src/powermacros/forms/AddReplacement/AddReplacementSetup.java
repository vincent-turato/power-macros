package powermacros.forms.AddReplacement;

import burp.BurpExtender;
import powermacros.extract.ExtractManager;
import powermacros.forms.MainTab.MainExtractTableModel;
import powermacros.extract.Extraction;
import powermacros.replace.Replace;
import powermacros.utilities.DeepCopy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class AddReplacementSetup extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox cboLinkExtract;
    private Replace replaceToEdit;

    public Replace showDialog(){
        setSize(new Dimension(400, 210));
        setResizable(false);
        setVisible(true);
        return replaceToEdit;
    }

    public AddReplacementSetup(Replace replaceToEdit) {
        this.replaceToEdit = replaceToEdit;


        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        addExtractionsToCombo();
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    private void addExtractionsToCombo(){
        Map<String, Extraction> extModelMap = ExtractManager.getExtModelMap();
        for (Map.Entry<String, Extraction> entry:  extModelMap.entrySet()) {
            cboLinkExtract.addItem(entry.getKey());
        }
    }
    private void onOK() {
        replaceToEdit.addLinkedExtraction(ExtractManager.getExtraction(cboLinkExtract.getSelectedIndex()));
        setVisible(false);
        dispose();
    }
    private void onCancel() {
        dispose();
    }
    public static void main(String[] args) {
    }

}
