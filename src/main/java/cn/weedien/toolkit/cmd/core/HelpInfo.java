package cn.weedien.toolkit.cmd.core;

import java.util.Map;

public class HelpInfo {

    private String header;

    private String usage;

    private Map<String, CommandInfo> commands;

    private String footer;

    public HelpInfo() {
    }

    public String getHeader() {
        return header;
    }

    public String getUsage() {
        return usage;
    }

    public Map<String, CommandInfo> getCommands() {
        return commands;
    }

    public String getFooter() {
        return footer;
    }

    public static Builder builder() {
        return new Builder();
    }

    // builder pattern
    public static class Builder {
        private String header;
        private String usage;
        private Map<String, CommandInfo> commands;
        private String footer;

        public Builder header(String header) {
            this.header = header;
            return this;
        }

        public Builder usage(String usage) {
            this.usage = usage;
            return this;
        }

        public Builder commands(Map<String, CommandInfo> commands) {
            this.commands = commands;
            return this;
        }

        public Builder footer(String footer) {
            this.footer = footer;
            return this;
        }

        public HelpInfo build() {
            HelpInfo helpInfo = new HelpInfo();
            helpInfo.header = this.header;
            helpInfo.usage = this.usage;
            helpInfo.commands = this.commands;
            helpInfo.footer = this.footer;
            return helpInfo;
        }
    }
}
