package cn.weedien.toolkit;

import cn.weedien.toolkit.cmd.core.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class CommandParserTests {

    private static final Map<String, CommandInfo> commands = new HashMap<>();

    @BeforeAll
    static void initCommands() {
        CommandInfo encrypt = new CommandInfo.Builder()
                .usage("secure encrypt [options] [argument]")
                .commandName("encrypt")
                .simpleDesc("encrypt data with password")
                .desc("""
                        Used to encrypt data, the argument can be a string or a file path.
                        AES algorithm is used by default.""")
                .options(new CommandOption[]{
                        new CommandOption.Builder()
                                .shortName("t")
                                .longName("text")
                                .hasArg(false)
                                .desc("Text to encrypt")
                                .build(),
                        new CommandOption.Builder()
                                .shortName("f")
                                .longName("file")
                                .hasArg(false)
                                .desc("File to encrypt")
                                .build(),
                        new CommandOption.Builder()
                                .shortName("o")
                                .longName("output")
                                .hasArg(true)
                                .argName("file")
                                .desc("Output file name")
                                .build(),
                        new CommandOption.Builder()
                                .shortName("x")
                                .longName("hex")
                                .hasArg(false)
                                .desc("Output in hex")
                                .build(),
                        new CommandOption.Builder()
                                .shortName("print")
                                .longName("print")
                                .hasArg(false)
                                .desc("Print to console")
                                .build(),
                        new CommandOption.Builder()
                                .shortName("p")
                                .longName("password")
                                .hasArg(true)
                                .argName("password")
                                .desc("Password for encryption/decryption")
                                .build()
                })
                .remark("This is a remark.")
                .build();
        commands.put(encrypt.getCommandName(), encrypt);
    }

    @Test
    void testPrintHelpInfo() {
        HelpInfo helpInfo = new HelpInfo.Builder()
                .header("This is a useful commandline toolkits for security.")
                .usage("secure <command> [options] [argument]")
                .commands(commands)
                .footer("Use \"secure help <command>\" for more information on a specific command.")
                .build();
        HelpFormatter formatter = new HelpFormatter(helpInfo);
        formatter.printSummary();
        formatter.printHelp("encrypt");
    }

    @Test
    void testParseCommandLine() {
        String[] args = {"encrypt", "Hello, world!", "-p", "031209", "-x", "-o", "output.txt"};

        CommandParser parser = new CommandParser();
        CommandLine commandLine = parser.parse(commands, args);
        System.out.println(commandLine);
    }

}
