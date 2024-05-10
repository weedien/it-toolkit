package cn.weedien.toolkit;

import cn.weedien.toolkit.encode.EncodingUtil;
import org.junit.jupiter.api.Test;

public class EncodingUtilTests {

    @Test
    void testBinaryEncode() {
        String bin = EncodingUtil.binaryEncode("test".getBytes());
        System.out.println(bin);
    }

    @Test
    void testBinaryDecode() {
        // String str = EncodingUtil.binaryDecode("01110100011001010111001101110100");
        // System.out.println(str);
    }
}
