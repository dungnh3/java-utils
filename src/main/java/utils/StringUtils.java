package utils;

import java.nio.charset.Charset;

public class StringUtils {

    private final static Charset UTF8_CHARSET = Charset.forName("UTF-8");
    private static final char[] u = {
            'Ú', 'Ù', 'Ủ', 'Ũ', 'Ụ', 'U',
            'Ứ', 'Ừ', 'Ử', 'Ữ', 'Ự', 'Ư',
            'ú', 'ù', 'ủ', 'ũ', 'ụ',
            'ứ', 'ừ', 'ử', 'ữ', 'ự', 'ư',};
    private static final char[] a = {
            'Á', 'À', 'Ả', 'Ã', 'Ạ', 'A',
            'Ắ', 'Ằ', 'Ẩ', 'Ẵ', 'Ặ', 'Ă',
            'Ấ', 'Ầ', 'Ẩ', 'Ẫ', 'Ậ', 'Â',
            'á', 'à', 'ả', 'ã', 'ạ',
            'ắ', 'ằ', 'ẳ', 'ẵ', 'ặ', 'ă',
            'ấ', 'ầ', 'ẩ', 'ẫ', 'ậ', 'â'};
    private static final char[] e = {
            'É', 'È', 'Ẻ', 'Ẽ', 'Ẹ', 'E',
            'Ế', 'Ề', 'Ể', 'Ễ', 'Ệ', 'Ê',
            'é', 'è', 'ẻ', 'ẽ', 'ẹ',
            'ế', 'ề', 'ể', 'ễ', 'ệ', 'ê'
    };
    private static final char[] o = {
            'Ó', 'Ò', 'Ỏ', 'Õ', 'Ọ', 'O',
            'Ố', 'Ồ', 'Ỗ', 'Ỗ', 'Ộ', 'Ô',
            'Ớ', 'Ờ', 'Ỡ', 'Ỡ', 'Ợ', 'Ơ',
            'ó', 'ò', 'ỏ', 'õ', 'ọ',
            'ố', 'ồ', 'ổ', 'ỗ', 'ộ', 'ô',
            'ớ', 'ờ', 'ở', 'ỡ', 'ợ', 'ơ'
    };
    private static final char[] i = {
            'Í', 'Ì', 'Ỉ', 'Ĩ', 'Ị', 'I',
            'í', 'ì', 'ỉ', 'ĩ', 'ị',};
    private static final char[] y = {
            'Ý', 'Ỳ', 'Ỷ', 'Ỹ', 'Ỵ', 'Y',
            'ý', 'ỳ', 'ỷ', 'ỹ', 'ỵ',};
    private static final char[] d = {'Đ', 'đ'};

    public static String decodeUTF8(byte[] bytes) {
        return new String(bytes, UTF8_CHARSET);
    }

    public static byte[] encodeUTF8(String string) {
        return string.getBytes(UTF8_CHARSET);
    }

    public static String filterUTF8(String input) {

        int length = input.length();
        char current_character;
        boolean added = false;
        StringBuilder strbdr = new StringBuilder(/*length*/);
        for (int j = 0; j < length; j++) {
            added = false;
            current_character = input.charAt(j);
//            System.out.println(current_character);
            for (char c : u) {
                if (c == current_character) {
//                    System.out.println("check "+ c +" "+current_character);
                    strbdr.append('u');
                    added = true;
                    break;
                }
            }
            if (added == true) {
                continue;
            }
            for (char c : a) {
                if (c == current_character) {
                    strbdr.append('a');
                    added = true;
                    break;
                }
            }
            if (added == true) {
                continue;
            }
            for (char c : e) {
                if (c == current_character) {
                    strbdr.append('e');
                    added = true;
                    break;
                }
            }
            if (added == true) {
                continue;
            }
            for (char c : o) {
                if (c == current_character) {
                    strbdr.append('o');
                    added = true;
                    break;
                }
            }
            if (added == true) {
                continue;
            }
            for (char c : i) {
                if (c == current_character) {
                    strbdr.append('i');
                    added = true;
                    break;
                }
            }
            if (added == true) {
                continue;
            }
            for (char c : y) {
                if (c == current_character) {
                    strbdr.append('y');
                    added = true;
                    break;
                }
            }
            if (added == true) {
                continue;
            }
            for (char c : d) {
                if (c == current_character) {
                    strbdr.append('d');
                    added = true;
                    break;
                }
            }
            if (added == true) {
                continue;
            }
            strbdr.append(current_character);
        }
        return strbdr.toString();
    }

    public static String removeTabAndNewLine(String str) {
        if (str != null) {
            return str.trim().replaceAll("\r", "").replaceAll("\n", " ").replaceAll("\t", " ");
        } else {
            return "";
        }
    }
}

