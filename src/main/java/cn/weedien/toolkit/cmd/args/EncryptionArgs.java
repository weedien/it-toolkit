package cn.weedien.toolkit.cmd.args;

import cn.weedien.toolkit.cmd.CommandUtil;
import cn.weedien.toolkit.cmd.core.CommandInfo;
import cn.weedien.toolkit.cmd.core.CommandLine;
import cn.weedien.toolkit.cmd.core.CommandOption;

/**
 * Arguments for encryption/decryption
 * <p>
 * 每添加一个或一组命令，都在命令参数类中将参数信息进行全局注册，这样每个命令都会相对独立，不会相互影响。
 * <p>
 * 另外，所有的参数合法性校验也都可以在Builder中进行，让业务逻辑部分的代码更加简洁清晰。
 */
public class EncryptionArgs {
    private String password;
    private String text;
    private String fileName;
    private String outputFileName;
    private boolean hex;
    private boolean printConsole;

    private EncryptionArgs() {
    }

    public static void register() {
        CommandOption[] sharedOptions = new CommandOption[]{
                CommandOption.builder("p").longName("password").hasArg(true).argName("password").desc("Password for encryption/decryption").build(),
                CommandOption.builder("t").longName("text").hasArg(true).argName("text").desc("Text to encrypt/decrypt").build(),
                CommandOption.builder("f").longName("file").hasArg(true).argName("file").desc("File to encrypt/decrypt").build(),
                CommandOption.builder("o").longName("output").hasArg(true).argName("file").desc("Output file name").build(),
                CommandOption.builder("x").longName("hex").hasArg(false).desc("Output in hex").build(),
                CommandOption.builder("print").hasArg(false).desc("Print to console").build()
        };
        CommandUtil.register(CommandInfo.builder().usage("encrypt [-t text] [-f file] [-p password] [option...]")
                .commandName("encrypt")
                .simpleDesc("Encrypt text or file")
                .desc("Encrypt text or file with password using AES_256_GCM")
                .options(sharedOptions)
                .hasArgs(false)
                .build());
        CommandUtil.register(CommandInfo.builder().usage("decrypt [-t text] [-f file] [-p password] [option...]")
                .commandName("decrypt")
                .simpleDesc("Decrypt text or file")
                .desc("Decrypt text or file with password using AES_256_GCM")
                .options(sharedOptions)
                .hasArgs(false)
                .build());
    }

    public static EncryptionArgs getInstance(CommandLine cmd) {
        return new EncryptionArgs.Builder()
                .password(cmd.getOption("p"))
                .text(cmd.getOption("t"))
                .fileName(cmd.getOption("f"))
                .outputFileName(cmd.getOption("o"))
                .hex(cmd.hasOption("x"))
                .printConsole(cmd.hasOption("print"))
                .build();
    }

    public static class Builder {
        private String password;
        private String text;
        private String fileName;
        private String outputFileName;
        private boolean hex;
        private boolean printConsole;

        public Builder password(String password) {
            if (password == null || password.isEmpty()) {
                throw new IllegalArgumentException("Password cannot be empty");
            }
            this.password = password;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder outputFileName(String outputFileName) {
            this.outputFileName = outputFileName;
            return this;
        }

        public Builder hex(boolean hex) {
            this.hex = hex;
            return this;
        }

        public Builder printConsole(boolean printConsole) {
            this.printConsole = printConsole;
            return this;
        }

        public EncryptionArgs build() {
            EncryptionArgs encryptionArgs = new EncryptionArgs();
            if (text == null && fileName == null) {
                throw new IllegalArgumentException("Must specify either text or file");
            }
            encryptionArgs.password = this.password;
            encryptionArgs.text = this.text;
            encryptionArgs.fileName = this.fileName;
            encryptionArgs.outputFileName = this.outputFileName == null ? this.fileName : this.outputFileName;
            encryptionArgs.hex = this.hex;
            encryptionArgs.printConsole = this.printConsole;
            return encryptionArgs;
        }
    }

    public String getPassword() {
        return password;
    }

    public String getText() {
        return text;
    }

    public String getFileName() {
        return fileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public boolean isHex() {
        return hex;
    }

    public boolean isPrintConsole() {
        return printConsole;
    }

    @Override
    public String toString() {
        return "EncodingArgs{" + "password='" + password + '\'' + ", text='" + text + '\'' + ", fileName='" + fileName + '\'' + ", outputFileName='" + outputFileName + '\'' + ", hex=" + hex + ", printConsole=" + printConsole + '}';
    }
}
