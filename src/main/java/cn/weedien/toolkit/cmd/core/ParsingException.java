package cn.weedien.toolkit.cmd.core;

public class ParsingException extends RuntimeException {

    private String command;

    public ParsingException(String message) {
        super(message);
    }

    public ParsingException(String message, String command) {
        super(message);
        this.command = command;
    }

    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getCommand() {
        return command;
    }
}
