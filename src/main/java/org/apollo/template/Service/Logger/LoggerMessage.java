/*
 * @author Mads R, Knudsen
 * @extension By Elias B. Therkildsen
 * @version 1.0
 * @since 23.01.2024
 */

package org.apollo.template.Service.Logger;

import org.apollo.template.Service.AnsiColorCode;
import org.apollo.template.Service.ConfigLoader;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Allows for logging of messages
 */
public class LoggerMessage {
    public static final int SPACING = 40;

    /**
     * Helper class for LoggerMessage,
     * @return String CurrentTime
     */
    private static String currentTime(){
        // Get the current time
        LocalTime currentTime = LocalTime.now();
        // Define the format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        // Format the time and print to console
        return currentTime.format(formatter);
    }

    /**
     * Shows a formatted log message in the cmd.
     * @param level    LogLevel
     * @param instance Object or Class
     * @param message  String
     */
    private static void log(LogLevel level, Object instance, String message) {
        if (ConfigLoader.instance.getDebug() >= level.getDebugLevel()) {
            String className;
            if (instance instanceof String) {
                className = (String) instance;
            } else if (instance instanceof Class) {
                className = ((Class<?>) instance).getSimpleName();
            } else {
                className = instance.getClass().getSimpleName();
            }
            //System.out.println(className.length());
            System.out.printf("%s[%s]%s[ %s ]%s%s %" + (className.length() - SPACING) + "s %s%n",
                    AnsiColorCode.CYAN,
                    currentTime(),
                    level.getColorLevel(),
                    level,
                    level.getColorMessage(),
                    className,
                    message,
                    AnsiColorCode.RESET);
        }
    }

    public static void trace(Object instance, String message) {
        log(LogLevel.TRACE, instance, message);
    }

    public static void info(Object instance, String message) {
        log(LogLevel.INFO, instance, message);
    }

    public static void debug(Object instance, String message) {
        log(LogLevel.DEBUG, instance, message);
    }

    public static void error(Object instance, String message) {
        log(LogLevel.ERROR, instance, message);
    }

    public static void warning(Object instance, String message) {
        log(LogLevel.WARNING, instance, message);
    }

    public static void trace(String name, String message) {
        log(LogLevel.TRACE, name, message);
    }

    public static void info(String name, String message) {
        log(LogLevel.INFO, name, message);
    }

    public static void debug(String name, String message) {
        log(LogLevel.DEBUG, name, message);
    }

    public static void error(Class<?> clazz, String message) {
        log(LogLevel.ERROR, clazz, message);
    }

    public static void warning(Class<?> clazz, String message) {
        log(LogLevel.WARNING, clazz, message);
    }
}
