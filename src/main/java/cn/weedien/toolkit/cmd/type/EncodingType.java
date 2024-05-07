package cn.weedien.toolkit.cmd.type;

public enum EncodingType {

    BASE64("base64"),
    URL("url"),
    HEX("hex"),
    ;

    private final String value;

    EncodingType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
