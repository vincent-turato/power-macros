package powermacros.extract;

import powermacros.replace.Replace;
import powermacros.replace.ReplaceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExtractManager {
    private static Map<String, Extraction> extModelMap = new HashMap<>();

    public static ArrayList<Extraction> getExtractionList() {
        ArrayList<Extraction> rtnList = new ArrayList<>();
        rtnList.addAll(getExtModelMap().values());
        return rtnList;
    }

    public static Map<String, Extraction> getExtModelMap() {
        return extModelMap;
    }

    public static void setExtModelMap(Map<String, Extraction> extModelMap) {
        ExtractManager.extModelMap = extModelMap;
    }

    public static Extraction getExtraction(int i) {
        ArrayList<Extraction> extractions = getExtractionList();
        if (i >= 0 && i < extractions.size()) {
            return extractions.get(i);
        }
        return null;
    }
    public static void removeExtraction(String id) {
        //Remove any linked extractions for the selected removal
        for (Replace r : ReplaceManager.getReplacementList()) {
            r.linkedExtracts.remove(id);
        }
        ExtractManager.getExtModelMap().remove(id);
    }
    public static void removeExtraction(int i) {
        if (i == -1) {
            return;
        }

        ArrayList<Extraction> tempExtracts = ExtractManager.getExtractionList();
        String removeExtractId = tempExtracts.get(i).getId();

        //Remove any linked extractions for the selected removal
        for (Replace r : ReplaceManager.getReplacementList()) {
            r.linkedExtracts.remove(removeExtractId);
        }

        ExtractManager.getExtModelMap().remove(removeExtractId);
    }

    public static void put(Extraction ext) {
        extModelMap.put(ext.getId(), ext);
    }

    public static void addExtractions(Extraction extList[]) {
        for (Extraction ext : extList) {
            put(ext);
        }
    }

    public static Extraction getExtractionById(String id) {
        return getExtModelMap().get(id);
    }

}
