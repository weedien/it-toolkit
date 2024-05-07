package cn.weedien.toolkit.cmd.impl;

import cn.weedien.toolkit.cmd.Command;
import cn.weedien.toolkit.cmd.args.EncodingArgs;
import cn.weedien.toolkit.cmd.type.EncodingType;
import cn.weedien.toolkit.encode.EncodingUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DecodingCommand implements Command {
    private final EncodingArgs args;

    public DecodingCommand(EncodingArgs args) {
        this.args = args;
    }

    private String decode(String text, EncodingType type) {
        return switch (type) {
            case EncodingType.BASE64 -> EncodingUtil.base64Decode(text);
            case EncodingType.URL -> EncodingUtil.urlDecode(text);
            default -> throw new IllegalArgumentException("Encoding type " + type + " not supported.");
        };
    }

    @Override
    public void execute() throws Exception {
        String text = args.getText();
        String fileName = args.getFileName();
        EncodingType encodingType = args.getEncodingType();

        if (text != null) {
            String decodingText = decode(text, encodingType);
            System.out.println("Decoded text: " + decodingText);
        } else if (fileName != null) {
            if (encodingType != EncodingType.BASE64) {
                throw new IllegalArgumentException("Encoding type " + encodingType + " not supported for file.");
            }
            Path path = Paths.get(args.getFileName());
            byte[] fileContent = Files.readAllBytes(path);
            byte[] decodedData = EncodingUtil.base64Decode(fileContent);
            if (args.isPrintConsole()) {
                System.out.println("Decoded text: \n" + new String(decodedData));
                return;
            }
            Files.write(Paths.get(args.getOutputFileName()), decodedData);
            System.out.println("File decoded successfully: " + args.getOutputFileName());
        }
    }
}
