package cn.weedien.toolkit.cmd.core;

import java.util.Map;
import java.util.TreeMap;

public class CommandInfo {

    private String usage;

    private String commandName;

    private String simpleDesc;

    private String desc;

    private boolean hasArgs;

    private String optionTips;

    private Map<String, CommandOption> options;

    private String remark;

    public CommandInfo() {
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getSimpleDesc() {
        return simpleDesc;
    }

    public void setSimpleDesc(String simpleDesc) {
        this.simpleDesc = simpleDesc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOptionTips() {
        return optionTips;
    }

    public void setOptionTips(String optionTips) {
        this.optionTips = optionTips;
    }

    public Map<String, CommandOption> getOptions() {
        return options;
    }

    public void setOptions(Map<String, CommandOption> options) {
        this.options = options;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean hasArgs() {
        return hasArgs;
    }

    public void setHasArgs(boolean hasArgs) {
        this.hasArgs = hasArgs;
    }

    public static Builder builder() {
        return new Builder();
    }

    // builder pattern
    public static class Builder {
        private String usage;
        private String commandName;
        private String simpleDesc;
        private String desc;
        private boolean hasArgs;
        private String optionTips;
        private Map<String, CommandOption> options;
        private String remark;

        public Builder usage(String usage) {
            this.usage = usage;
            return this;
        }

        public Builder commandName(String commandName) {
            this.commandName = commandName;
            return this;
        }

        public Builder simpleDesc(String simpleDesc) {
            this.simpleDesc = simpleDesc;
            return this;
        }

        public Builder desc(String desc) {
            this.desc = desc;
            return this;
        }

        public Builder hasArgs(boolean hasArgs) {
            this.hasArgs = hasArgs;
            return this;
        }

        public Builder optionTips(String optionTips) {
            this.optionTips = optionTips;
            return this;
        }

        public Builder options(CommandOption[] options) {
            Map<String, CommandOption> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            for (CommandOption option : options) {
                map.put(option.getShortName(), option);
            }
            this.options = map;
            return this;
        }

        public Builder options(Map<String, CommandOption> options) {
            this.options = options;
            return this;
        }

        public Builder remark(String remark) {
            this.remark = remark;
            return this;
        }

        public CommandInfo build() {
            CommandInfo commandInfo = new CommandInfo();
            commandInfo.usage = this.usage;
            commandInfo.commandName = this.commandName;
            commandInfo.simpleDesc = this.simpleDesc;
            commandInfo.desc = this.desc;
            commandInfo.hasArgs = this.hasArgs;
            commandInfo.optionTips = this.optionTips;
            commandInfo.options = this.options;
            commandInfo.remark = this.remark;
            return commandInfo;
        }
    }
}
