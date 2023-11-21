package edu.metrostate.utils;

public final class ZipCodeUtils {

    private static final int ZIP_CODE_LENGTH = 5;

    private ZipCodeUtils() {
        throw new IllegalAccessError("Stop, drop, and roll!");
    }

    public static boolean isInvalidZipCode(CharSequence zipCode) {
        if (zipCode == null || zipCode.isEmpty() || zipCode.length() != ZIP_CODE_LENGTH) {
            return true;
        }
        for (int i = 0; i < ZIP_CODE_LENGTH; i++) {
            if (!Character.isDigit(zipCode.charAt(i))) {
                return true;
            }
        }
        return false;
    }

}
