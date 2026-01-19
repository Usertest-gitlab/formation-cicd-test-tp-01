package com.devops.cicd;

public class PasswordPolicy {

    public static boolean containsEightCarac(String password) {
        return password.length() >= 8;
    }
    
    public static boolean containsMaj(String password) {
        return !password.equals(password.toLowerCase());
    }
    
    public static boolean containsNum(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean containsSpeCarac(String password) {
        return password.matches(".*[!@#$%^&*()\\-_=+\\[\\]{}|;:'\",.<>?/`~°].*");
    }
    
    public static boolean isStrong(String password) {
        return containsEightCarac(password) && containsMaj(password) && containsNum(password) && containsSpeCarac(password);
    }
}