package cn.weedien.toolkit.cmd.core;

import java.util.Map;

public class CommandParser {

    public CommandLine parse(Map<String, CommandInfo> cmdMap, String[] args) {
        if (args.length == 0) {
            throw new ParsingException("No command specified");
        }

        CommandLine commandLine = new CommandLine();

        String commandName = args[0].startsWith("-") ? "" : args[0];
        commandLine.setCommandName(commandName);
        CommandInfo commandInfo = cmdMap.get(commandName);
        if (commandInfo == null) {
            throw new ParsingException("Command not found: " + commandName);
        }

        Map<String, CommandOption> options = commandInfo.getOptions();
        int i = args[0].startsWith("-") ? 0 : 1;
        while (i < args.length) {
            String arg = args[i];
            if (arg.startsWith("-")) {
                CommandOption option = findOption(arg, options);
                if (option == null) {
                    throw new ParsingException("Option not found: " + arg, commandName);
                }
                if (option.hasArg()) {
                    if (i + 1 >= args.length) {
                        throw new ParsingException("Option " + arg + " requires an argument", commandName);
                    }
                    commandLine.getOptions().put(option.getShortName(), args[i + 1]);
                    i++;
                } else {
                    commandLine.getOptions().put(option.getShortName(), null);
                }
            } else {
                if (commandLine.getArgument() != null) {
                    throw new ParsingException("Multiple arguments specified");
                }
                commandLine.setArgument(args[i]);
            }
            i++;
        }
        if (commandInfo.hasArgs() && commandLine.getArgument() == null) {
            throw new ParsingException("Argument required", commandName);
        }
        return commandLine;
    }

    private CommandOption findOption(String arg, Map<String, CommandOption> options) {
        for (String key : options.keySet()) {
            if (arg.equals("-" + key)) {
                return options.get(key);
            }
            if (options.get(key).getLongName() != null && arg.equals("--" + options.get(key).getLongName())) {
                return options.get(key);
            }
        }
        return null;
    }
}
