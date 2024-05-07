package cn.weedien.toolkit.cmd.type;

public enum CommandType {

    HELP("help"),
    ENCRYPT("encrypt"),
    DECRYPT("decrypt"),
    HASH("hash"),
    ENCODE("encode"),
    DECODE("decode"),
    ;

    private final String value;

    CommandType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
