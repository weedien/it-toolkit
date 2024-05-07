package cn.weedien.toolkit.cmd.core;

public class CommandOption {
    private String shortName;
    private String longName;
    private boolean hasArg;
    private String argName;
    private String[] availableValues;
    private String desc;

    public CommandOption() {
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }

    public boolean hasArg() {
        return hasArg;
    }

    public String getArgName() {
        return argName;
    }

    public String[] getAvailableValues() {
        return availableValues;
    }

    public String getDesc() {
        return desc;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(String option) {
        return new Builder(option);
    }

    // builder pattern
    public static class Builder {
        private String shortName;
        private String longName;
        private boolean hasArg;
        private String argName;
        private String[] availableValues;
        private String desc;

        public Builder() {
        }

        public Builder(String option) {
            this.shortName = option;
        }

        public Builder shortName(String shortName) {
            this.shortName = shortName;
            return this;
        }

        public Builder longName(String longName) {
            this.longName = longName;
            return this;
        }

        public Builder hasArg(boolean hasArg) {
            this.hasArg = hasArg;
            return this;
        }

        public Builder argName(String argName) {
            this.argName = argName;
            return this;
        }

        public Builder availableValues(String[] availableValues) {
            this.availableValues = availableValues;
            return this;
        }

        public Builder desc(String desc) {
            this.desc = desc;
            return this;
        }

        public CommandOption build() {
            CommandOption option = new CommandOption();
            option.shortName = this.shortName;
            option.longName = this.longName;
            option.hasArg = this.hasArg;
            option.argName = this.argName;
            option.availableValues = this.availableValues;
            option.desc = this.desc;
            return option;
        }
    }
}