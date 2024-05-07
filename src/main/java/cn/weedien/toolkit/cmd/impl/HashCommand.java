package cn.weedien.toolkit.cmd.impl;

import cn.weedien.toolkit.cmd.Command;
import cn.weedien.toolkit.cmd.args.HashArgs;
import cn.weedien.toolkit.hash.HashUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class HashCommand implements Command {

    private final HashArgs args;

    public HashCommand(HashArgs args) {
        this.args = Objects.requireNonNull(args);
    }

    @Override
    public void execute() throws Exception {
        String input = null;
        String text = args.getText();
        String fileName = args.getFileName();

        if (text != null) {
            input = text;
        } else if (fileName != null) {
            Path path = Paths.get(fileName);
            input = new String(Files.readAllBytes(path));
        }

        String hash = HashUtil.getHash(input, args.getAlgorithm(), args.getSalt());
        System.out.println("Hash (" + args.getAlgorithm() + "):\n" + hash);
    }
}