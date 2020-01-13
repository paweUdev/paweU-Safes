package pl.paweu.safes.util;

public class StringUtil {

    public static String replace(String text, String var, String to){
        return text.replace(var, to);
    }

    public static String getStringWithoutInteger(String text){
        return text.replaceAll("[0-9]", "");
    }
}
