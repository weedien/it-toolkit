package cn.weedien.toolkit;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ApiTest {

    @Test
    void test() {
        System.out.printf("    %-10s %s", "test", "test");
    }

    @Test
    void testReadNotExistsFile() throws IOException {
        Path path = Paths.get("not-exists.txt");
        byte[] bytes = Files.readAllBytes(path);
        System.out.println(new String(bytes));
    }
}
