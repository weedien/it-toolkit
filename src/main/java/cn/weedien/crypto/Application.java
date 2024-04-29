package cn.weedien.crypto;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Application {

    public static void main(String[] args) {

        if (args.length < 4) {
            System.out.println("Usage: java -jar crypto.jar -m <mode> -p <password> [-f <file>] [-c] [-t <text>]");
            return;
        }

        String mode = null, password = null, fileName = null, text = null;
        boolean putConsole = false; // 将解密内容输出到命令行而不是写入文件

        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "-m":
                    mode = args[i + 1];
                    break;
                case "-p":
                    password = args[i + 1];
                    break;
                case "-f":
                    fileName = args[i + 1];
                    break;
                case "-t":
                    text = args[i + 1];
                    break;
                case "-c":
                    putConsole = true;
                    break;
                default:
                    System.out.printf("Invalid argument: %s\n", args[i]);
                    return;
            }
        }

        if (mode == null || password == null) {
            System.out.println("Mode and password are required");
            return;
        }

        if (!mode.equals("encrypt") && !mode.equals("decrypt")) {
            System.out.println("Invalid mode. Use 'encrypt' or 'decrypt'");
            return;
        }

        if (fileName != null) {
            Path path = Paths.get(fileName);
            byte[] fileContent;
            try {
                fileContent = Files.readAllBytes(path);
            } catch (Exception e) {
                System.out.printf("Failed to read file: %s\n", path);
                return;
            }

            byte[] result;
            try {
                if (mode.equals("encrypt")) {
                    result = EncryptionUtil.encryptData(fileContent, password);
                } else {
                    result = EncryptionUtil.decryptData(fileContent, password);
                }
            } catch (Exception e) {
                System.out.printf("Failed to %s file: %s\n", mode, path);
                return;
            }

            if (mode.equals("decrypt") &&  putConsole) {
                String resultString = new String(result);
                System.out.printf("File %s successfully: \n%s\n", mode, resultString);
                return;
            }

            try {
                Files.write(path, result);
            } catch (Exception e) {
                System.out.printf("Failed to write %s file: %s\n", mode, path);
                return;
            }

            System.out.printf("File %s successfully: %s\n", mode, path);
        }

        if (text != null) {
            String result;
            try {
                if (mode.equals("encrypt")) {
                    result = EncryptionUtil.encryptData(text, password);
                } else {
                    result = EncryptionUtil.decryptData(text, password);
                }
            } catch (Exception e) {
                System.out.printf("Failed to %s text\n", mode);
                return;
            }

            System.out.printf("Text %s successfully: %s\n", mode, result);
        }
    }
}
