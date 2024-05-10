package cn.weedien.toolkit.cmd.args;

import cn.weedien.toolkit.cmd.CommandUtil;
import cn.weedien.toolkit.cmd.core.CommandInfo;
import cn.weedien.toolkit.cmd.core.CommandLine;
import cn.weedien.toolkit.cmd.core.CommandOption;

public class ConversionArgs {

    private String text;
    private String fileName;
    private String binary;
    private String hex;
    private boolean toBinary;
    private boolean toHex;
    private boolean toText;

    public ConversionArgs() {
    }

    public static void register() {
        CommandOption[] sharedOptions = new CommandOption[]{
                CommandOption.builder("t").longName("text").hasArg(true).argName("text").desc("Text to convert").build(),
                CommandOption.builder("f").longName("file").hasArg(true).argName("file").desc("File to convert").build(),
                CommandOption.builder("b").longName("binary").hasArg(true).argName("binary").desc("Binary to convert").build(),
                CommandOption.builder("h").longName("hex").hasArg(true).argName("hex").desc("Hex to convert").build(),
                CommandOption.builder("to-bin").hasArg(false).desc("Convert to binary").build(),
                CommandOption.builder("to-hex").hasArg(false).desc("Convert to hex").build(),
                CommandOption.builder("to-txt").hasArg(false).desc("Convert to text").build()
        };
        CommandUtil.register(CommandInfo.builder().usage("convert [-t text] [-f file] [-b binary] [-h hex] [-to-bin] [-to-hex]")
                .commandName("convert")
                .simpleDesc("Convert text, file, binary or hex")
                .desc("Convert text, file, binary or hex")
                .hasArgs(false)
                .options(sharedOptions)
                .build());
    }

    public static ConversionArgs getInstance(CommandLine cmd) {
        return new ConversionArgs.Builder()
                .text(cmd.getOption("t"))
                .fileName(cmd.getOption("f"))
                .binary(cmd.getOption("b"))
                .hex(cmd.getOption("h"))
                .toBinary(cmd.hasOption("to-bin"))
                .toHex(cmd.hasOption("to-hex"))
                .toText(cmd.hasOption("to-txt"))
                .build();
    }

    public static class Builder {
        private String text;
        private String fileName;
        private String binary;
        private String hex;
        private boolean toBinary;
        private boolean toHex;
        private boolean toText;

        private boolean flag;

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder binary(String binary) {
            this.binary = binary;
            return this;
        }

        public Builder hex(String hex) {
            this.hex = hex;
            return this;
        }

        public Builder toBinary(boolean toBinary) {
            if (toBinary) {
                this.toBinary = true;
                if (flag) {
                    throw new IllegalArgumentException("Cannot specify multiple conversion types");
                }
                this.flag = true;
            }
            return this;
        }

        public Builder toHex(boolean toHex) {
            if (toHex) {
                this.toHex = true;
                if (flag) {
                    throw new IllegalArgumentException("Cannot specify multiple conversion types");
                }
                this.flag = true;
            }
            return this;
        }

        public Builder toText(boolean toText) {
            if (toText) {
                this.toText = true;
                if (flag) {
                    throw new IllegalArgumentException("Cannot specify multiple conversion types");
                }
                this.flag = true;
            }
            return this;
        }

        public ConversionArgs build() {
            ConversionArgs args = new ConversionArgs();
            if (text == null && fileName == null && binary == null && hex == null) {
                throw new IllegalArgumentException("Must specify either text, file, binary or hex");
            }
            if (toBinary && binary != null) {
                throw new IllegalArgumentException("No need to specify -to-bin when binary is provided");
            }
            if (toHex && hex != null) {
                throw new IllegalArgumentException("No need to specify -to-hex when hex is provided");
            }
            if (toText && text != null) {
                throw new IllegalArgumentException("No need to specify -to-txt when text is provided");
            }
            if (toBinary && toHex) {
                throw new IllegalArgumentException("Cannot specify both to binary and to hex");
            }
            args.text = this.text;
            args.fileName = this.fileName;
            args.binary = this.binary;
            args.hex = this.hex;
            args.toBinary = this.toBinary;
            args.toHex = this.toHex;
            return args;
        }
    }

    public String getText() {
        return text;
    }

    public String getFileName() {
        return fileName;
    }

    public String getBinary() {
        return binary;
    }

    public String getHex() {
        return hex;
    }

    public boolean isToBinary() {
        return toBinary;
    }

    public boolean isToHex() {
        return toHex;
    }

    public boolean isToText() {
        return toText;
    }

    @Override
    public String toString() {
        return "ConversionArgs{" +
                "text='" + text + '\'' +
                ", fileName='" + fileName + '\'' +
                ", binary='" + binary + '\'' +
                ", hex='" + hex + '\'' +
                ", toBinary=" + toBinary +
                ", toHex=" + toHex +
                '}';
    }
}
