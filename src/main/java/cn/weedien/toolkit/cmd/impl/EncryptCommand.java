package cn.weedien.toolkit.cmd.impl;

import cn.weedien.toolkit.cmd.Command;
import cn.weedien.toolkit.cmd.args.EncryptionArgs;
import cn.weedien.toolkit.crypto.EncryptionUtil;
import cn.weedien.toolkit.encode.EncodingUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EncryptCommand implements Command {

    private final EncryptionArgs args;

    public EncryptCommand(EncryptionArgs args) {
        this.args = args;
    }

    @Override
    public void execute() throws Exception {
        String text = args.getText();
        String fileName = args.getFileName();
        String password = args.getPassword();

        if (text != null) {
            String encryptedText = EncryptionUtil.encryptData(text, password);
            System.out.println("Encrypted text: \n" + encryptedText);
        } else if (fileName != null) {
            Path path = Paths.get(fileName);
            byte[] fileContent = Files.readAllBytes(path);
            byte[] encryptedData = EncryptionUtil.encryptData(fileContent, password);
            if (args.isPrintConsole()) {
                System.out.println("Encrypted text: \n" + EncodingUtil.hexEncode(encryptedData));
                return;
            }
            if (args.isHex()) {
                String hexFormat = EncodingUtil.hexEncode(encryptedData);
                Files.write(Paths.get(args.getOutputFileName()), hexFormat.getBytes());
                System.out.println("File encrypted successfully: " + args.getOutputFileName());
                return;
            }
            Files.write(Paths.get(args.getOutputFileName()), encryptedData);
            System.out.println("File encrypted successfully: " + args.getOutputFileName());
        }
    }
}