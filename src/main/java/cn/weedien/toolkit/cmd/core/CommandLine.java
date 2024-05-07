package cn.weedien.toolkit.cmd.core;

import java.util.HashMap;
import java.util.Map;

public class CommandLine {

    private String commandName;

    private String argument;

    private Map<String, String> options = new HashMap<>();

    public CommandLine() {
    }

    public CommandLine(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public String getOption(String key) {
        return options.get(key);
    }

    public String getOption(String key, String defaultValue) {
        return options.getOrDefault(key, defaultValue);
    }

    public boolean hasOption(String key) {
        return options.containsKey(key);
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    public static Builder builder() {
        return new Builder();
    }

    // builder pattern
    public static class Builder {
        private String commandName;
        private String argument;
        private final Map<String, String> options = new HashMap<>();

        public Builder commandName(String commandName) {
            this.commandName = commandName;
            return this;
        }

        public Builder argument(String argument) {
            this.argument = argument;
            return this;
        }

        public Builder option(String key, String value) {
            options.put(key, value);
            return this;
        }

        public CommandLine build() {
            CommandLine commandLine = new CommandLine();
            commandLine.setCommandName(commandName);
            commandLine.setArgument(argument);
            commandLine.setOptions(options);
            return commandLine;
        }
    }

    @Override
    public String toString() {
        return "CommandLine{" +
                "commandName='" + commandName + '\'' +
                ", argument='" + argument + '\'' +
                ", options=" + options +
                '}';
    }
}
