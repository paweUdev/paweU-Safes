package pl.paweu.safes.util;


public final class IntegerUtil {

    public static int getIntegerFromString(String text) {
        return Integer.parseInt(text.replaceAll("[\\D]", ""));
    }

    public static boolean isInteger(String integer){
        try {
            Integer.parseInt(integer);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
}
