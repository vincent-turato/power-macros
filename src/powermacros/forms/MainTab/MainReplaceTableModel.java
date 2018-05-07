package powermacros.forms.MainTab;

import burp.BurpExtender;
import powermacros.replace.BurpAction;
import powermacros.replace.Replace;
import powermacros.replace.ReplaceManager;

import javax.swing.table.AbstractTableModel;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by fruh on 9/8/16.
 */
public class MainReplaceTableModel extends AbstractTableModel {
//    private static List<Replace> replacesLast  = new LinkedList<>();
    private String[] cols = {"Linked extraction", "Replacement name", "Extraction type", "Replacement type"};


    public MainReplaceTableModel(){

    }

    @Override
    public int getRowCount() {
        return ReplaceManager.getRepModelMap().size();
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

    public int getRowById(String id) {
        int row = -1;
        List<Replace> replaces = ReplaceManager.getReplacementList();
        if (replaces.size() > 0) {
            for (row = 0; row < replaces.size(); row++) {
                if (id.equals(replaces.get(row).getId())) {
                    break;
                }
            }
        }
        return row;
    }

    @Override
    public Object getValueAt(int row, int col) {
        String ret;

        switch (col) {
            case 0:
                if(ReplaceManager.getReplace(row).linkedExtracts.getLinkedExtractMap().size() > 1){
                    ret = "[Multiple]";
                }else if (ReplaceManager.getReplace(row).linkedExtracts.getLinkedExtractMap().size() == 1) {
                    ret = ReplaceManager.getReplace(row).linkedExtracts.getLinkedExtractList().get(0).getId();
                }else{
                     Map<String, Replace> tester = ReplaceManager.getRepModelMap();
                    ret = "None";
                }
                break;

            case 1:
                ret = String.valueOf(ReplaceManager.getReplace(row).getId());
                break;

            case 2:
                if(ReplaceManager.getReplace(row).linkedExtracts.getLinkedExtractMap().size() > 1){
                    ret = "[Multiple]";
                }else if (ReplaceManager.getReplace(row).linkedExtracts.getLinkedExtractMap().size() == 1){
                    ret = ReplaceManager.getReplace(row).linkedExtracts.getLinkedExtractList().get(0).getTypeString();
                }else{
                    ret = "None";
                }
                break;

            case 3:
                ret = ReplaceManager.getReplace(row).getTypeString();
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
//        Replace r = replaces.get(row);

//        if (r.getMsgId() != null) {
//            extender.getMessagesModel().getMessageById(r.getMsgId()).getRepRefSet().remove(r.getId());
//        }
//        repModelMap.remove(r.getId());
//        if (replacesLast.contains(r)) {
//            replacesLast.remove(r);
//        }
//        replaces.remove(row);
//
//        fireTableRowsDeleted(row, row);
    }

    public void removeAll() {
//        repModelMap.clear();
//        replaces.clear();
//        replacesLast.clear();
//
//        fireTableDataChanged();
    }

    public void addReplaceLast(Replace rep) {
//        replaces.add(rep);
//        replacesLast.add(rep);
//        repModelMap.put(rep.getId(), rep);
//
//        fireTableRowsInserted(replaces.size() - 1, replaces.size() - 1);
    }


//    public List<Replace> getReplacesLast() {
//        return replacesLast;
//    }

}
