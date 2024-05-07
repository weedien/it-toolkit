package cn.weedien.toolkit.cmd.args;

import cn.weedien.toolkit.cmd.CommandUtil;
import cn.weedien.toolkit.cmd.core.CommandInfo;
import cn.weedien.toolkit.cmd.core.CommandLine;
import cn.weedien.toolkit.cmd.core.CommandOption;

public class HashArgs {

    private static final String DEFAULT_ALGORITHM = "md5";

    private String algorithm;
    private String text;
    private String fileName;
    private String salt;

    private HashArgs() {
    }

    public static void register() {
        CommandOption[] options = new CommandOption[]{
                CommandOption.builder("algo").hasArg(true).argName("md5,sha256,sha384,sha512").desc("Hash algorithm").build(),
                CommandOption.builder("t").longName("text").hasArg(true).argName("text").desc("Text to hash").build(),
                CommandOption.builder("f").longName("file").hasArg(true).argName("file").desc("File to hash").build(),
                CommandOption.builder("s").longName("salt").hasArg(true).argName("salt").desc("Salt for hashing").build()
        };
        CommandUtil.register(CommandInfo.builder()
                .usage("hash [-t text] [-f file] [option...]")
                .commandName("hash")
                .simpleDesc("Hash text or file")
                .desc("Hash text or file with given algorithm")
                .hasArgs(false)
                .options(options)
                .build());
    }

    public static HashArgs getInstance(CommandLine cmd) {
        return new HashArgs.Builder()
                .algorithm(cmd.getOption("algo", DEFAULT_ALGORITHM))
                .text(cmd.getOption("t"))
                .fileName(cmd.getOption("f"))
                .salt(cmd.getOption("s"))
                .build();
    }

    public static class Builder {
        private String algorithm;
        private String text;
        private String fileName;
        private String salt;

        public Builder algorithm(String algorithm) {
            if (algorithm == null || algorithm.isEmpty()) {
                this.algorithm = DEFAULT_ALGORITHM;
            } else {
                this.algorithm = algorithm;
            }
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

        public Builder salt(String salt) {
            this.salt = salt;
            return this;
        }

        public HashArgs build() {
            HashArgs hashArgs = new HashArgs();
            if (text == null && fileName == null) {
                throw new IllegalArgumentException("Must specify either text or file");
            }
            hashArgs.algorithm = algorithm;
            hashArgs.text = text;
            hashArgs.fileName = fileName;
            hashArgs.salt = salt;
            return hashArgs;
        }
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getText() {
        return text;
    }

    public String getFileName() {
        return fileName;
    }

    public String getSalt() {
        return salt;
    }

    @Override
    public String toString() {
        return "HashArgs{" +
                "algorithm='" + algorithm + '\'' +
                ", text='" + text + '\'' +
                ", fileName='" + fileName + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
