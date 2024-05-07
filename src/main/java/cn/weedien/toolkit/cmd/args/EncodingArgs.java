package cn.weedien.toolkit.cmd.args;

import cn.weedien.toolkit.cmd.CommandUtil;
import cn.weedien.toolkit.cmd.core.CommandInfo;
import cn.weedien.toolkit.cmd.core.CommandLine;
import cn.weedien.toolkit.cmd.core.CommandOption;
import cn.weedien.toolkit.cmd.type.EncodingType;

public class EncodingArgs {
    private String text;
    private String fileName;
    private String outputFileName;
    private EncodingType encodingType;
    private boolean printConsole;

    private EncodingArgs() {
    }

    public static void register() {
        CommandOption[] sharedOptions = new CommandOption[]{
                CommandOption.builder("t").longName("text").hasArg(true).argName("text").desc("Text to encode").build(),
                CommandOption.builder("f").longName("file").hasArg(true).argName("file").desc("File to encode").build(),
                CommandOption.builder("o").longName("output").hasArg(true).argName("file").desc("Output file name").build(),
                CommandOption.builder("type").hasArg(true).argName("base64,url").desc("Encoding type, base64 is default").build(),
                CommandOption.builder("print").hasArg(false).desc("Print to console").build()
        };
        CommandUtil.register(CommandInfo.builder().usage("encode [-t text] [-f file] [option...]")
                .commandName("encode")
                .simpleDesc("Encode text or file")
                .desc("Encode text or file")
                .hasArgs(false)
                .options(sharedOptions)
                .build());
        CommandUtil.register(CommandInfo.builder().usage("decode [-t text] [-f file] [option...]")
                .commandName("decode")
                .simpleDesc("decode text or file")
                .desc("decode text or file")
                .hasArgs(false)
                .options(sharedOptions)
                .build());
    }

    public static EncodingArgs getInstance(CommandLine cmd) {
        return new EncodingArgs.Builder()
                .text(cmd.getOption("t"))
                .fileName(cmd.getOption("f"))
                .outputFileName(cmd.getOption("o"))
                .encodingType(cmd.getOption("type"))
                .printConsole(cmd.hasOption("print"))
                .build();
    }

    public static class Builder {
        private String text;
        private String fileName;
        private String outputFileName;
        private EncodingType encodingType;
        private boolean printConsole;

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

        public Builder encodingType(String encodingType) {
            if (encodingType == null || encodingType.isEmpty()) {
                this.encodingType = EncodingType.BASE64;
                return this;
            }
            this.encodingType = EncodingType.valueOf(encodingType.toUpperCase());
            return this;
        }

        public Builder printConsole(boolean printConsole) {
            this.printConsole = printConsole;
            return this;
        }

        public EncodingArgs build() {
            EncodingArgs encodingArgs = new EncodingArgs();
            if (text == null && fileName == null) {
                throw new IllegalArgumentException("Must specify either text or file");
            }
            encodingArgs.text = this.text;
            encodingArgs.fileName = this.fileName;
            encodingArgs.outputFileName = this.outputFileName == null ? this.fileName : this.outputFileName;
            encodingArgs.encodingType = this.encodingType == null ? EncodingType.BASE64 : this.encodingType;
            encodingArgs.printConsole = this.printConsole;
            return encodingArgs;
        }
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

    public EncodingType getEncodingType() {
        return encodingType;
    }

    public boolean isPrintConsole() {
        return printConsole;
    }

    @Override
    public String toString() {
        return "EncodingArgs{" +
                "text='" + text + '\'' +
                ", fileName='" + fileName + '\'' +
                ", outputFileName='" + outputFileName + '\'' +
                ", encodingType=" + encodingType +
                ", printConsole=" + printConsole +
                '}';
    }
}
