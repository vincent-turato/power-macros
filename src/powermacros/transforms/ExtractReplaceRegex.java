package powermacros.transforms;

import powermacros.extract.Extraction;
import powermacros.replace.Replace;

public class ExtractReplaceRegex extends ExtractReplaceMethod {

    public ExtractReplaceRegex(Extraction extraction, String regexStr) {
        super(extraction);
        this.typeArgs = regexStr;
    }

    public ExtractReplaceRegex(Replace replace, String regexStr) {
        super(replace);
        this.typeArgs = regexStr;
    }

    @Override
    public String getReplacedExtraction(String requestResponse) {
        return this.typeArgs;
    }

}
