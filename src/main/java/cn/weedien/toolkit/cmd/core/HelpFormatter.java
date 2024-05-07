package cn.weedien.toolkit.cmd.core;

public class HelpFormatter {

    private final HelpInfo helpInfo;

    public HelpFormatter(HelpInfo helpInfo) {
        this.helpInfo = helpInfo;
    }

    public void printSummary() {
        if (helpInfo.getHeader() != null && !helpInfo.getHeader().isEmpty()) {
            System.out.println(helpInfo.getHeader() + "\n");
        }
        System.out.printf("Usage: %s%n%n", helpInfo.getUsage());
        System.out.println("Commands:\n");
        for (CommandInfo command : helpInfo.getCommands().values()) {
            // 过滤 help 命令
            if (command.getCommandName().equals("help") || command.getCommandName().isEmpty()) {
                continue;
            }
            System.out.printf("    %-10s %s%n", command.getCommandName(), command.getSimpleDesc());
        }
        System.out.println();
        if (helpInfo.getFooter() != null && !helpInfo.getFooter().isEmpty()) {
            System.out.println(helpInfo.getFooter());
        }
    }

    public void printHelp(String commandName) {
        CommandInfo commandInfo = helpInfo.getCommands().get(commandName);
        if (commandInfo == null) {
            throw new ParsingException("Command not found: " + commandName);
        }
        System.out.printf("Usage: %s%n%n", commandInfo.getUsage());
        System.out.printf("%s%n%n", commandInfo.getDesc());
        System.out.println("Options:");

        int maxOptionLength = 0;
        for (CommandOption option : commandInfo.getOptions().values()) {
            String optionName = getOptionName(option);
            maxOptionLength = Math.max(maxOptionLength, optionName.length());
        }

        for (CommandOption option : commandInfo.getOptions().values()) {
            String optionName = getOptionName(option);
            System.out.printf("    %-" + maxOptionLength + "s %s%n", optionName, option.getDesc());
        }
        System.out.println();
        if (commandInfo.getRemark() != null && !commandInfo.getRemark().isEmpty()) {
            System.out.println(commandInfo.getRemark());
        }
    }

    private static String getOptionName(CommandOption option) {
        String optionName = "-" + option.getShortName();
        if (option.getLongName() != null && !option.getLongName().isEmpty()) {
            optionName += ", --" + option.getLongName();
        }
        if (option.hasArg()) {
            optionName += " <" + option.getArgName() + ">";
        }
        return optionName;
    }
}
