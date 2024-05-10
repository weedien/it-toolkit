package cn.weedien.toolkit;

import cn.weedien.toolkit.cmd.Command;
import cn.weedien.toolkit.cmd.CommandFactory;
import cn.weedien.toolkit.cmd.CommandUtil;
import cn.weedien.toolkit.cmd.core.CommandLine;
import cn.weedien.toolkit.cmd.core.ParsingException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

public class ToolkitApplication {

    public static void main(String[] args) throws IOException, InterruptedException {
        // 解决 Graalvm 编译结果在 Windows 控制台中文乱码问题
        if (System.getProperty("java.home") == null
                && !System.getProperty("stdout.encoding").toUpperCase(Locale.ROOT).equals("UTF-8")
                && System.getProperties().getProperty("os.name").toUpperCase(Locale.ROOT).contains("WINDOWS")) {
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "chcp", "65001").inheritIO(); // 需要使用 inheritIO
            pb.redirectOutput(ProcessBuilder.Redirect.DISCARD);
            Process p = pb.start();
            p.waitFor();
        }

        try {
            CommandLine cmd = CommandUtil.parse(args);
            Command command = CommandFactory.createCommand(cmd);
            command.execute();
        } catch (ParsingException pe) {
            System.out.println(pe.getMessage());
            if (pe.getCommand() != null && !pe.getCommand().isEmpty()) {
                System.out.printf("Run 'tk help %s' for usage.%n", pe.getCommand());
            } else {
                System.out.println("Run 'tk help' for usage.");
            }
        } catch (IOException ie) {
            if (ie instanceof FileNotFoundException) {
                System.out.println("File not found: " + ie.getMessage());
            } else {
                System.out.println("File read err: " + ie.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
