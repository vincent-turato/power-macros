package powermacros.transforms;

import burp.BurpExtender;
import powermacros.extract.Extraction;
import powermacros.replace.Replace;

public class ExtractReplace {
    private TransformTypes type;
    private String id;
    private ExtractReplaceMethod extractReplaceMethod;


    public ExtractReplace(){

    }

    public ExtractReplace(String name, TransformTypes type){
        this.id = name;
        this.setType(type);
    }

    public void setExtractReplaceMethod(Extraction extraction, TransformTypes type, String typeArgs[]) {
        if(this.getType().equals(TransformTypes.JAVASCRIPT) || this.getType().equals(TransformTypes.PYTHON)){
            this.extractReplaceMethod = new ExtractReplaceScript(extraction, typeArgs[0], this.getType());
        }else if(this.getType().equals(TransformTypes.REGEX)){
            this.extractReplaceMethod = new ExtractReplaceRegex(extraction, typeArgs[0]);
        }
    }
    public void setExtractReplaceMethod(Replace replace, TransformTypes type, String typeArgs[]) {
        if(this.getType().equals(TransformTypes.JAVASCRIPT) || this.getType().equals(TransformTypes.PYTHON)){
            this.extractReplaceMethod = new ExtractReplaceScript(replace, typeArgs[0], this.getType());
        }else if(this.getType().equals(TransformTypes.REGEX)){
            this.extractReplaceMethod = new ExtractReplaceRegex(replace, typeArgs[0]);
        }
    }

    public ExtractReplaceMethod getExtractReplaceMethod(){
        if (extractReplaceMethod == null){
        }
        return extractReplaceMethod;
    }

    public TransformTypes getType(){
        return this.type;
    }
    public void setType(TransformTypes type){ this.type = type;}
    public String getTypeString() {
        return this.type.text();
    }
    public void setTypeString(String type) {
        this.type = TransformTypes.fromText(type);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


}
