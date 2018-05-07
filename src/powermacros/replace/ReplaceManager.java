package powermacros.replace;

import burp.BurpExtender;
import powermacros.extract.ExtractManager;
import powermacros.extract.Extraction;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReplaceManager {
    private static Map<String, Replace> repModelMap = new HashMap<>();
    public static Map<String, Replace> getRepModelMap() {
        return repModelMap;
    }

    public static ArrayList<Replace> getReplaceList() {
        ArrayList<Replace> rtnList = new ArrayList<>();
        rtnList.addAll(getRepModelMap().values());
        return rtnList;
    }

    public static void save(BufferedWriter writer) {

    }

    public static void putReplace(Replace rep) {
        repModelMap.put(rep.getId(), rep);
    }
    public static void removeReplace(String id) {
        ReplaceManager.repModelMap.remove(id);
    }
    public static void removeReplace(int i) {
        if (i >= 0) {
            ArrayList<Replace> tempReplace = ReplaceManager.getReplacementList();
            String removeReplaceId = tempReplace.get(i).getId();
            ReplaceManager.getRepModelMap().remove(removeReplaceId);
        }
    }

    public static ArrayList<Replace> getReplacementList() {
        ArrayList<Replace> rtnList = new ArrayList<>();
        rtnList.addAll(getRepModelMap().values());
        return rtnList;
    }

    public static Replace getReplace(int i) {
        ArrayList<Replace> replaces = getReplacementList();
        if (i >= 0 && i < replaces.size()) {
            return replaces.get(i);
        }
        return null;
    }

    public static Replace getReplaceById(String id) {
        return getRepModelMap().get(id);
    }
}
