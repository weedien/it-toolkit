package cn.weedien.toolkit.cmd;

import cn.weedien.toolkit.cmd.args.EncodingArgs;
import cn.weedien.toolkit.cmd.args.EncryptionArgs;
import cn.weedien.toolkit.cmd.args.HashArgs;
import cn.weedien.toolkit.cmd.core.*;

import java.util.Map;
import java.util.TreeMap;

public class CommandUtil {
    private static final Map<String, CommandInfo> commands = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private static final HelpFormatter helpFormatter;

    static {
        // 注册命令
        HashArgs.register();
        EncryptionArgs.register();
        EncodingArgs.register();
        // 帮助信息
        commands.put("help", CommandInfo.builder().commandName("help").simpleDesc("Show help information.").build());
        commands.put("", CommandInfo.builder().commandName("").simpleDesc("Show help information.").options(
                new CommandOption[]{
                        CommandOption.builder("h").longName("help").hasArg(false).desc("Show help information.").build()
                }
        ).build());
        HelpInfo helpInfo = HelpInfo.builder()
                .header("A toolkit developed by weedien.")
                .usage("tk [command] [options] [arguments]")
                .commands(commands)
                .footer("Use 'tk help <command>' for more information about a command.")
                .build();
        helpFormatter = new HelpFormatter(helpInfo);
    }

    public static CommandLine parse(String[] args) {
        CommandParser parser = new CommandParser();
        return parser.parse(commands, args);
    }

    public static void register(CommandInfo commandInfo) {
        commands.put(commandInfo.getCommandName(), commandInfo);
    }

    public static HelpFormatter getHelpFormatter() {
        return helpFormatter;
    }
}