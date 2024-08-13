package gkmasmod.utils;

public class NameHelper {
    public static String makePath(String id) {
        return "gkmasMod:" + id;
    }

    // 接受可变参数 字符串，将它们用:连接起来
    public static String addSplitWords(String... args) {

        StringBuilder sb = new StringBuilder();
        for (String s : args) {
            sb.append(s).append(":");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

}
