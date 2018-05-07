package powermacros.forms.AddReplacement;

import burp.BurpExtender;
import burp.IExtensionHelpers;
import powermacros.extract.Extraction;
import powermacros.replace.Replace;

import javax.swing.table.AbstractTableModel;
import java.util.*;

/**
 * Created by fruh on 9/8/16.
 */
public class LinkedExtractsTableModel extends AbstractTableModel {

    private List<Extraction> extractions = new LinkedList<>();

    private String[] cols = {"Name", "Type"};
    private Replace linkedReplacement;

    public LinkedExtractsTableModel() {

    }
    public LinkedExtractsTableModel(Replace linkedReplacement) {
        this();
        this.linkedReplacement = linkedReplacement;
        this.extractions = linkedReplacement.linkedExtracts.getLinkedExtractList();
    }

    @Override
    public int getRowCount() {
        return this.extractions.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public String getColumnName(int index) {
        if (index < cols.length) {
            return cols[index];
        }
        return null;
    }

    public Extraction getExtraction(int i) {
        if (i >= 0 && i < this.extractions.size()) {
            return this.extractions.get(i);
        }
        return null;
    }

    @Override
    public Object getValueAt(int row, int col) {
        String ret;

        switch (col) {
            case 0:
                ret = getExtraction(row).getId();
                break;

            case 1:
                ret = String.valueOf(getExtraction(row).getTypeString());
                break;


            default:
                ret = null;
                break;
        }
        return ret;
    }

    public void removeRow(int row) {
        extractions.remove(row);
        fireTableRowsDeleted(row, row);
    }

    public void removeAll() {
//        for (Extraction e:extractions) {
//            for (Message m : extender.getMessagesModel().getMessages()) {
//                m.getExtRefSet().remove(e.getMsgId());
//            }
//        }
//        for (Message m : extender.getMessagesModel().getMessages()) {
//            m.getExtRefSet().clear();
//        }
//        extractions.clear();
//        mapAllExtracts.clear();
//
//        // delete references
//        extender.getReplaceModel().removeAll();
//
//        fireTableDataChanged();
    }

    public int getRowById(String id) {
        int row = -1;

        if (this.extractions.size() > 0) {
            for (row = 0; row < this.extractions.size(); row++) {
                if (id .equals(this.extractions.get(row).getId())) {
                    break;
                }
            }
        }
        return row;
    }


    public String replaceExtractions(String request, IExtensionHelpers helpers) {
        for (Extraction extraction: this.extractions) {
            request = request.replace(extraction.getExtractionString(request),
                    this.linkedReplacement.getExtractReplaceMethod().getReplacedExtraction(request));
        }

        return request;
    }

}
