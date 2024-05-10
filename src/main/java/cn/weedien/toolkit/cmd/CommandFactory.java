package cn.weedien.toolkit.cmd;

import cn.weedien.toolkit.cmd.args.ConversionArgs;
import cn.weedien.toolkit.cmd.args.EncodingArgs;
import cn.weedien.toolkit.cmd.args.EncryptionArgs;
import cn.weedien.toolkit.cmd.args.HashArgs;
import cn.weedien.toolkit.cmd.core.CommandLine;
import cn.weedien.toolkit.cmd.core.ParsingException;
import cn.weedien.toolkit.cmd.impl.*;
import cn.weedien.toolkit.cmd.type.CommandType;

import java.util.Objects;

public class CommandFactory {
    public static Command createCommand(CommandLine cmd) {
        // 帮助命令
        if (cmd.getCommandName().isEmpty() && cmd.hasOption("h")) {
            return new HelpCommand(CommandUtil.getHelpFormatter());
        } else if (Objects.equals(cmd.getCommandName(), CommandType.HELP.getValue())) {
            return new HelpCommand(cmd.getArgument(), CommandUtil.getHelpFormatter());
        }

        if (Objects.equals(cmd.getCommandName(), CommandType.ENCRYPT.getValue())) {
            EncryptionArgs encryptionArgs = EncryptionArgs.getInstance(cmd);
            return new EncryptCommand(encryptionArgs);
        } else if (Objects.equals(cmd.getCommandName(), CommandType.DECRYPT.getValue())) {
            EncryptionArgs encryptionArgs = EncryptionArgs.getInstance(cmd);
            return new DecryptCommand(encryptionArgs);
        } else if (Objects.equals(cmd.getCommandName(), CommandType.HASH.getValue())) {
            HashArgs hashArgs = HashArgs.getInstance(cmd);
            return new HashCommand(hashArgs);
        } else if (Objects.equals(cmd.getCommandName(), CommandType.ENCODE.getValue())) {
            EncodingArgs encodingArgs = EncodingArgs.getInstance(cmd);
            return new EncodingCommand(encodingArgs);
        } else if (Objects.equals(cmd.getCommandName(), CommandType.DECODE.getValue())) {
            EncodingArgs encodingArgs = EncodingArgs.getInstance(cmd);
            return new DecodingCommand(encodingArgs);
        } else if (Objects.equals(cmd.getCommandName(), CommandType.CONVERT.getValue())) {
            return new ConversionCommand(ConversionArgs.getInstance(cmd));
        } else {
            throw new ParsingException("Command not found: " + cmd.getCommandName());
        }
    }
}