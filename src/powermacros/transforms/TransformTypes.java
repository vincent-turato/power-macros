package powermacros.transforms;

public enum TransformTypes {
    STARTEND("Start/end string"),
    REGEX("Regex"),
    PYTHON("python"),
    JAVASCRIPT("JavaScript");

    private String type;

    TransformTypes(String type) {
        this.type = type;
    }

    public String text() {
        return this.type;
    }

    public boolean isImplemented() {
        if (this.equals(REGEX) ||
                this.equals(JAVASCRIPT)) {
            return true;
        } else {
            return false;
        }
    }

    public static TransformTypes fromText(String text) {
        for (TransformTypes t : TransformTypes.values()) {
            if (t.text().equals(text)) {
                return t;
            }
        }
        return null;
    }
}
