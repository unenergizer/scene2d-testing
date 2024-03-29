package com.valenguard.test.util;

public class Log {

    public static void println(Class clazz, String message) {
        println(clazz, message, false, true);
    }

    public static void println(Class clazz, String message, boolean isError) {
        println(clazz, message, isError, true);
    }

    public static void println(Class clazz, String message, boolean isError, boolean print) {
        if (!print) return;
        if (isError) System.err.println(buildMessage(clazz, message));
        else System.out.println(buildMessage(clazz, message));
    }

    private static String buildMessage(Class clazz, String message) {
        return "[" + clazz.getSimpleName() + "] " + message;
    }
}
