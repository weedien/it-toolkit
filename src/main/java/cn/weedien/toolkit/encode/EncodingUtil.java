package cn.weedien.toolkit.encode;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class EncodingUtil {

    public static String hexEncode(byte[] input) {
        StringBuilder result = new StringBuilder();
        for (byte b : input) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    public static byte[] hexDecode(String input) {
        byte[] bytes = new byte[input.length() / 2];
        for (int i = 0; i < input.length(); i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(input.charAt(i), 16) << 4)
                    + Character.digit(input.charAt(i + 1), 16));
        }
        return bytes;
    }

    public static String hexEncodeWithBlockSize(byte[] bytes, int blockSize) {

        String hex = hexEncode(bytes);

        // one hex = 2 chars
        blockSize = blockSize * 2;

        // better idea how to print this?
        List<String> result = new ArrayList<>();
        int index = 0;
        while (index < hex.length()) {
            result.add(hex.substring(index, Math.min(index + blockSize, hex.length())));
            index += blockSize;
        }

        return result.toString();

    }

    public static String base64Encode(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public static byte[] base64Encode(byte[] input) {
        return Base64.getEncoder().encode(input);
    }

    public static String base64Decode(String input) {
        return new String(Base64.getDecoder().decode(input));
    }

    public static byte[] base64Decode(byte[] input) {
        return Base64.getDecoder().decode(input);
    }

    public static String urlEncode(String input) {
        return URLEncoder.encode(input, StandardCharsets.UTF_8);
    }

    public static String urlDecode(String input) {
        return URLDecoder.decode(input, StandardCharsets.UTF_8);
    }
}