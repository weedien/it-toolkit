package cn.weedien.toolkit.cmd.impl;

import cn.weedien.toolkit.cmd.Command;
import cn.weedien.toolkit.cmd.args.ConversionArgs;
import cn.weedien.toolkit.encode.EncodingUtil;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ConversionCommand implements Command {

    private final ConversionArgs conversionArgs;

    public ConversionCommand(ConversionArgs conversionArgs) {
        this.conversionArgs = conversionArgs;
    }

    @Override
    public void execute() throws Exception {
        String text = conversionArgs.getText();
        String fileName = conversionArgs.getFileName();
        String binary = conversionArgs.getBinary();
        String hex = conversionArgs.getHex();

        if (text != null) {
            if (conversionArgs.isToBinary()) {
                String binStr = EncodingUtil.binaryEncode(text.getBytes());
                System.out.println("Text to binary: " + binStr);
            } else {
                String hexStr = EncodingUtil.hexEncode(text.getBytes());
                System.out.println("Text to hex: " + hexStr);
            }
        } else if (fileName != null) {
            byte[] bytes = Files.readAllBytes(Paths.get(fileName));
            if (conversionArgs.isToBinary()) {
                String binStr = EncodingUtil.binaryEncode(bytes);
                System.out.println("File to binary: " + binStr);
            } else {
                String hexStr = EncodingUtil.hexEncode(bytes);
                System.out.println("File to hex: " + hexStr);
            }
        } else if (binary != null) {
            byte[] bytes = EncodingUtil.binaryDecode(binary);
            if (conversionArgs.isToHex()) {
                String hexStr = EncodingUtil.hexEncode(bytes);
                System.out.println("Binary to hex: " + hexStr);
            } else {
                String str = new String(bytes);
                System.out.println("Binary to text: " + str);
            }
        } else if (hex != null) {
            byte[] bytes = EncodingUtil.hexDecode(hex);
            if (conversionArgs.isToBinary()) {
                String binStr = EncodingUtil.binaryEncode(bytes);
                System.out.println("Hex to binary: " + binStr);
            } else {
                String str = new String(bytes);
                System.out.println("Hex to text: " + str);
            }
        }
    }
}
