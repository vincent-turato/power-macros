package powermacros.forms.MainTab;
import powermacros.extract.ExtractManager;
import powermacros.extract.Extraction;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Map;

/**
 * Created by fruh on 9/8/16.
 */
public class MainExtractTableModel extends AbstractTableModel {
    private String[] cols = {"Name", "Type"};
    public MainExtractTableModel() {
    }

    @Override
    public int getRowCount() {
        return ExtractManager.getExtModelMap().size();
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

    @Override
    public Object getValueAt(int row, int col) {
        String ret;
        switch (col) {
            case 0:
                ret = ExtractManager.getExtraction(row).getId();
                break;

            case 1:
                ret = String.valueOf(ExtractManager.getExtraction(row).getTypeString());
                break;


            default:
                ret = null;
                break;
        }
        return ret;
    }

    public void remove(String id) {
        int row = getRowById(id);
        removeRow(row);
    }

    public void removeRow(int row) {
//        Extraction e = extractions.get(row);
//
//        // remove extraction reference
//        extender.getMessagesModel().getMessageById(e.getMsgId()).getExtRefSet().remove(e.getId());
//
//        for (String r:e.getRepRefSet()) {
//            extender.getReplaceModel().remove(r);
//        }
//        mapAllExtracts.remove(extractions.get(row).getId());
//        extractions.remove(row);
//;
//        fireTableRowsDeleted(row, row);
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
        List<Extraction> extractions = ExtractManager.getExtractionList();

        if (extractions.size() > 0) {
            for (row = 0; row < extractions.size(); row++) {
                if (id.equals(extractions.get(row).getId())) {
                    break;
                }
            }
        }
        return row;
    }
}
