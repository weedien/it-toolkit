package cn.weedien.toolkit.cmd.impl;

import cn.weedien.toolkit.cmd.Command;
import cn.weedien.toolkit.cmd.args.EncryptionArgs;
import cn.weedien.toolkit.crypto.EncryptionUtil;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DecryptCommand implements Command {
    private final EncryptionArgs args;

    public DecryptCommand(EncryptionArgs args) {
        this.args = args;
    }

    @Override
    public void execute() throws Exception {
        validateArgs();

        if (args.getText() != null) {
            handleTextDecryption();
        } else if (args.getFileName() != null) {
            handleFileDecryption();
        }
    }

    private void validateArgs() {
        if (args.getText() == null && args.getFileName() == null) {
            throw new IllegalArgumentException("Must specify either text or file");
        }
    }

    private void handleTextDecryption() throws Exception {
        String decryptedText = EncryptionUtil.decryptData(args.getText(), args.getPassword());
        System.out.println("Decrypted text: \n" + decryptedText);
    }

    private void handleFileDecryption() throws Exception {
        Path path = Paths.get(args.getFileName());
        byte[] fileContent = Files.readAllBytes(path);

        if (args.isHex()) {
            handleHexDecryption(fileContent, path);
        } else {
            handleNonHexDecryption(fileContent, path);
        }
    }

    private void handleHexDecryption(byte[] fileContent, Path path) throws Exception {
        String decryptedData = EncryptionUtil.decryptData(new String(fileContent, StandardCharsets.UTF_8), args.getPassword());
        if (args.isPrintConsole()) {
            System.out.println("Decrypted text: \n" + decryptedData);
        } else {
            Files.write(Paths.get(args.getOutputFileName()), decryptedData.getBytes());
            System.out.println("File decrypted successfully: " + args.getOutputFileName());
        }
    }

    private void handleNonHexDecryption(byte[] fileContent, Path path) throws Exception {
        byte[] decryptedData = EncryptionUtil.decryptData(fileContent, args.getPassword());
        if (args.isPrintConsole()) {
            System.out.println("Decrypted text: \n" + new String(decryptedData));
        } else {
            Files.write(Paths.get(args.getOutputFileName()), decryptedData);
            System.out.println("File decrypted successfully: " + args.getOutputFileName());
        }
    }
}