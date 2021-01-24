package com.example.agent.utils;

/**
 * @author Chris
 * @version 1.0.0
 * @date 2021/01/24
 */
public class LogUtils {
    public static void info(String format, Object... args) {
        if (StringUtil.isNotBlank(format)) {
            format = format.replaceAll("\\{}", "%s");
            final String msg = String.format(format, args);
            System.out.println(msg);
        } else {
            System.out.println(format);
        }
    }

    public static void error(String format, Object... args) {
        if (StringUtil.isNotBlank(format)) {
            format = format.replaceAll("\\{}", "%s");
            final String msg = String.format(format, args);
            System.err.println(msg);
        } else {
            System.err.println(format);
        }

        // error("a {}, b {}, error.", a, b, err)
        if (args[args.length - 1] instanceof Throwable) {
            Throwable t = (Throwable) args[args.length - 1];
            t.printStackTrace();
        }
    }

    public static void warn(String format, Object... args) {
        info(format, args);
    }

    public static void debug(String format, Object... args) {
        info(format, args);
    }
}
