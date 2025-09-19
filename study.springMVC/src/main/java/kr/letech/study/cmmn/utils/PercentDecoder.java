package kr.letech.study.cmmn.utils;

import java.nio.charset.StandardCharsets;

public class PercentDecoder {
    public static String decode(String encoded) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < encoded.length();) {
            char c = encoded.charAt(i);
            if (c == '%') {
                String hex = encoded.substring(i+1, i+3);
                int value = Integer.parseInt(hex, 16);
                sb.append((char) value);
                i += 3;
            } else if (c == '+') {
                sb.append(' ');
                i++;
            } else {
                sb.append(c);
                i++;
            }
        }
        return new String(sb.toString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
}
