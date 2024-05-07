package cn.weedien.toolkit.cmd.impl;

import cn.weedien.toolkit.cmd.Command;
import cn.weedien.toolkit.cmd.core.HelpFormatter;

public class HelpCommand implements Command {

    private String commandName;

    private HelpFormatter formatter;

    public HelpCommand() {
    }

    public HelpCommand(HelpFormatter formatter) {
        this.formatter = formatter;
    }

    public HelpCommand(String commandName, HelpFormatter formatter) {
        this.commandName = commandName;
        this.formatter = formatter;
    }

    @Override
    public void execute() throws Exception {
        if (commandName == null) {
            formatter.printSummary();
        } else {
            formatter.printHelp(commandName);
        }
    }
}
