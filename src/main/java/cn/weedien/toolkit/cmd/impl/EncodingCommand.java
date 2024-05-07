package cn.weedien.toolkit.cmd.impl;

import cn.weedien.toolkit.cmd.Command;
import cn.weedien.toolkit.cmd.args.EncodingArgs;
import cn.weedien.toolkit.cmd.type.EncodingType;
import cn.weedien.toolkit.encode.EncodingUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EncodingCommand implements Command {

    private final EncodingArgs args;

    public EncodingCommand(EncodingArgs args) {
        this.args = args;
    }

    private String encode(String text, EncodingType type) {
        return switch (type) {
            case EncodingType.BASE64 -> EncodingUtil.base64Encode(text);
            case EncodingType.URL -> EncodingUtil.urlEncode(text);
            default -> throw new IllegalArgumentException("Encoding type " + type + " not supported.");
        };
    }

    @Override
    public void execute() throws Exception {
        String text = args.getText();
        String fileName = args.getFileName();
        EncodingType encodingType = args.getEncodingType();

        if (text != null) {
            String encodingText = encode(text, encodingType);
            System.out.printf("Encoded text (%s): %n%s%n", encodingType.getValue(), encodingText);
        } else if (fileName != null) {
            if (encodingType != EncodingType.BASE64) {
                throw new IllegalArgumentException("Encoding type " + encodingType.getValue() + " not supported for file.");
            }

            Path path = Paths.get(fileName);
            byte[] fileContent = Files.readAllBytes(path);
            byte[] encodedData;
            encodedData = EncodingUtil.base64Encode(fileContent);

            if (args.isPrintConsole()) {
                System.out.println("Encoded text: \n" + new String(encodedData));
                return;
            }
            Files.write(Paths.get(args.getOutputFileName()), encodedData);
            System.out.println("File encoded successfully: " + args.getOutputFileName());
        }
    }
}
