package powermacros.extract;

import powermacros.transforms.ExtractReplace;
import powermacros.transforms.TransformTypes;

import java.util.Set;

public class Extraction extends ExtractReplace {
    public Extraction(String name, TransformTypes type, String typeArgs[]) {
        super(name, type);
        setExtractReplaceMethod(this, this.getType(), typeArgs);
    }

    public Extraction(String name, String type, String typeArgs[]) {
        super(name, TransformTypes.fromText(type));
        setExtractReplaceMethod(this, this.getType(), typeArgs);
    }

    public String getExtractionString(String request) {
        return this.getExtractReplaceMethod().getReplacedExtraction(request);
    }
}
